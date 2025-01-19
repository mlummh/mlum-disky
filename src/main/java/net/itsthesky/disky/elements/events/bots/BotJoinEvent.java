package net.itsthesky.disky.elements.events.bots;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.itsthesky.disky.api.events.DiSkyEvent;
import net.itsthesky.disky.api.events.SimpleDiSkyEvent;
import net.itsthesky.disky.core.SkriptUtils;

public class BotJoinEvent extends DiSkyEvent<GuildJoinEvent> {

	static {
		register("Bot Join Event", BotJoinEvent.class, BukkitBotJoinEvent.class,
				"bot [guild] join[ed]")
				.description("Fired when any bot join a new guild.");

		SkriptUtils.registerBotValue(BukkitBotJoinEvent.class);

		SkriptUtils.registerValue(BukkitBotJoinEvent.class, Guild.class, BotJoinEvent::apply);
	}

	private static Guild apply(BukkitBotJoinEvent e) {
		return e.getJDAEvent().getGuild();
	}

	public static class BukkitBotJoinEvent extends SimpleDiSkyEvent<GuildJoinEvent> {
		public BukkitBotJoinEvent(BotJoinEvent event) {
			
		}
	}
	
}
