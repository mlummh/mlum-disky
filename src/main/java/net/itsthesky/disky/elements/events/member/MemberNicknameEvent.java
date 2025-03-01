package net.itsthesky.disky.elements.events.member;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateNicknameEvent;
import net.itsthesky.disky.api.events.DiSkyEvent;
import net.itsthesky.disky.api.events.SimpleDiSkyEvent;
import net.itsthesky.disky.core.SkriptUtils;

public class MemberNicknameEvent extends DiSkyEvent<GuildMemberUpdateNicknameEvent> {

    static {
        register("Member Nickname Event", MemberNicknameEvent.class, BukkitMemberUpdateNicknameEvent.class,
                "[discord] [guild] member nickname (change|update)")
                .description("Fired when a member changes their nickname.")
                .examples("on member nickname change:");


        SkriptUtils.registerBotValue(MemberNicknameEvent.BukkitMemberUpdateNicknameEvent.class);

        SkriptUtils.registerValue(MemberNicknameEvent.BukkitMemberUpdateNicknameEvent.class, String.class,
                event -> event.getJDAEvent().getNewValue(), 0);
        SkriptUtils.registerValue(MemberNicknameEvent.BukkitMemberUpdateNicknameEvent.class, String.class,
                event -> event.getJDAEvent().getNewValue(), 1);
        SkriptUtils.registerValue(MemberNicknameEvent.BukkitMemberUpdateNicknameEvent.class, String.class,
                event -> event.getJDAEvent().getOldValue(), -1);

        SkriptUtils.registerValue(MemberNicknameEvent.BukkitMemberUpdateNicknameEvent.class, Guild.class,
                event -> event.getJDAEvent().getGuild());

        SkriptUtils.registerValue(MemberNicknameEvent.BukkitMemberUpdateNicknameEvent.class, Member.class,
                event -> event.getJDAEvent().getMember(), 0);
    }

    public static class BukkitMemberUpdateNicknameEvent extends SimpleDiSkyEvent<GuildMemberUpdateNicknameEvent> {
        public BukkitMemberUpdateNicknameEvent(MemberNicknameEvent event) {
        }
    }
}