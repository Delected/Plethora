package commands.moderation;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.exceptions.HierarchyException;

public class Kick extends Command {
    public Kick() {
        this.name = "kick";
        this.botPermissions = new Permission[]{Permission.MESSAGE_WRITE, Permission.KICK_MEMBERS};
        this.category = new Category("moderation");
        this.help = "Kicks a user from the server.";
    }
    @Override
    protected void execute(CommandEvent e) {
        String[] msg = e.getMessage().getContentRaw().split(" ");

        if(!e.getMember().hasPermission(Permission.KICK_MEMBERS)) {
            e.reply("You are missing the `KICK_MEMBERS` permission!");
            return;
        }
        if (msg.length < 3) {
            e.reply("I am not sure what you mean. Please use /kick <user> <reason>");
            return;
        }
        StringBuilder reason = new StringBuilder();
        for (int i = 2 ; i < msg.length ; i++) {
            reason.append(msg[i]).append(" ");
        }
        if (e.getMessage().getMentionedMembers().isEmpty()) {
            try {
                e.getMessage().delete().queue();
                e.getGuild().kick(msg[1], reason.toString()).queue();
                e.reply("The user has been successfully kicked!");
            } catch (HierarchyException hex) {
                e.reply("I was not able to kick this user. Make sure my role is above theirs!");
            } catch (Exception exc) {
                e.reply("You must specify a user that is in the server, either by ID or by mention.");
            }
        }
        else {
            try {
                e.getMessage().delete().queue();
                e.getGuild().kick(e.getMessage().getMentionedMembers().get(0), reason.toString()).queue();
                e.reply("The user has been successfully kicked!");
            } catch (HierarchyException he) {
                e.reply("I was not able to kick this user. Make sure my role is above theirs");
            } catch (Exception ex) {
                e.reply("There was an error kicking this user. Are they in the server?");
            }

        }

    }
}
