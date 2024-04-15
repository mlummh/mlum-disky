package info.itsthesky.disky.elements.events.messages;

import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptParser;
import info.itsthesky.disky.DiSky;
import info.itsthesky.disky.api.events.DiSkyEvent;
import info.itsthesky.disky.api.events.SimpleDiSkyEvent;
import info.itsthesky.disky.core.SkriptUtils;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.concrete.NewsChannel;
import net.dv8tion.jda.api.entities.channel.concrete.PrivateChannel;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.ThreadChannel;
import net.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

public class MessageEvent extends DiSkyEvent<MessageReceivedEvent> {

	static {
		register("Message Receive", MessageEvent.class, BukkitMessageEvent.class,
				"[:global] message receive[d]")
				.description("Fired when any bot receive an actual message.",
						"This will be fired, by default, both guild & private messages, use the 'event is from guild' condition to avoid confusion.")
				.examples("on message received:",
						"\tif message is from guild:",
						"\t\treply with \"I just received '%event-message%' from %mention tag of event-channel%!\"",
						"\telse:",
						"\t\treply with \"I just received '%event-message%' from %mention tag of event-user%!\"");

		SkriptUtils.registerBotValue(BukkitMessageEvent.class);

		SkriptUtils.registerValue(BukkitMessageEvent.class, Message.class,
				event -> event.getJDAEvent().getMessage());
		SkriptUtils.registerValue(BukkitMessageEvent.class, Guild.class,
				event -> event.getJDAEvent().getGuild());
		SkriptUtils.registerValue(BukkitMessageEvent.class, Member.class,
				event -> event.getJDAEvent().getMember());
		SkriptUtils.registerValue(BukkitMessageEvent.class, User.class,
				event -> event.getJDAEvent().getAuthor());
		SkriptUtils.registerValue(BukkitMessageEvent.class, MessageChannel.class,
				event -> event.getJDAEvent().getChannel());

		SkriptUtils.registerValue(BukkitMessageEvent.class, GuildChannel.class,
				event -> event.getJDAEvent().isFromGuild() ? event.getJDAEvent().getGuildChannel() : null);
		SkriptUtils.registerValue(BukkitMessageEvent.class, TextChannel.class,
				event -> event.getJDAEvent().isFromType(ChannelType.TEXT) ? event.getJDAEvent().getChannel().asTextChannel() : null);
		SkriptUtils.registerValue(BukkitMessageEvent.class, NewsChannel.class,
				event -> event.getJDAEvent().isFromType(ChannelType.NEWS) ? event.getJDAEvent().getChannel().asNewsChannel() : null);
		SkriptUtils.registerValue(BukkitMessageEvent.class, ThreadChannel.class,
				event -> event.getJDAEvent().isFromType(ChannelType.GUILD_PUBLIC_THREAD) || event.getJDAEvent().isFromType(ChannelType.GUILD_PRIVATE_THREAD) ? event.getJDAEvent().getChannel().asThreadChannel() : null);

		SkriptUtils.registerValue(BukkitMessageEvent.class, PrivateChannel.class,
				event -> !event.getJDAEvent().isFromGuild() ? event.getJDAEvent().getChannel().asPrivateChannel() : null);
	}

	private boolean globalEvent;

	@Override
	public boolean init(Literal<?> @NotNull [] exprs, int matchedPattern, SkriptParser.@NotNull ParseResult parser) {
		globalEvent = parser.hasTag("global");
		return super.init(exprs, matchedPattern, parser);
	}

	@Override
	public boolean check(@NotNull Event event) {
		if (!super.check(event)) return false;
		if (!((BukkitMessageEvent) event).isFromGuild()) return false;
		if (!((BukkitMessageEvent) event).getJDAEvent().getGuild().getId().equals(DiSky.getConfiguration().getString("GuildID"))) {
			return globalEvent;
		}
		return !globalEvent;
	}

	public static class BukkitMessageEvent extends SimpleDiSkyEvent<MessageReceivedEvent> implements info.itsthesky.disky.api.events.specific.MessageEvent {
		public BukkitMessageEvent(MessageEvent event) {}

		@Override
		public MessageChannel getMessageChannel() {
			return getJDAEvent().getChannel();
		}

		@Override
		public boolean isFromGuild() {
			return getJDAEvent().isFromGuild();
		}
	}
}