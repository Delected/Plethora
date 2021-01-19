import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import commands.general.Help;
import commands.moderation.Kick;
import commands.moderation.Softban;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;

public class BotInstance extends ListenerAdapter {
    public static void main(String[] args) throws LoginException {
        JDA jda = JDABuilder.createDefault("NzgyNDI3MTk4MjkyMDk5MDgy.X8MCHA.vNXu5ydxWa2nRAdLZR0jV8i3uIQ").build();


        CommandClientBuilder b = new CommandClientBuilder();
        b.setPrefix("/");
        b.setActivity(Activity.watching("a bunch of idiots"));
        b.setStatus(OnlineStatus.DO_NOT_DISTURB);
        b.useHelpBuilder(false);
        b.setOwnerId("251747460026859520");
        b.addCommands(new Help(), new Kick(), new Softban());

        CommandClient c = b.build();

        jda.addEventListener(c);
    }
}

