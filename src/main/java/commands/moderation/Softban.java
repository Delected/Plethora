package commands.moderation;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Invite;
import net.dv8tion.jda.api.exceptions.HierarchyException;

public class Softban extends Command {
    public Softban() {
        this.name = "softban";
        this.botPermissions = new Permission[]{Permission.MESSAGE_WRITE, Permission.BAN_MEMBERS};
        this.category = new Category("moderation");
        this.help = "Kicks a user from the server, but deletes all their messages.";
    }
    @Override
    protected void execute(CommandEvent e) {
        String[] msg = e.getMessage().getContentRaw().split(" ");

        if(!e.getMember().hasPermission(Permission.KICK_MEMBERS)) {
            e.reply("You are missing the `KICK_MEMBERS` permission!");
            return;
        }
        if (msg.length != 2) {
            e.reply("I am not sure what you mean. Please use /softban <user>");
            return;
        }
        if (e.getMessage().getMentionedMembers().isEmpty()) {
            try {
                e.getMessage().delete().queue();
                e.reply(msg[1]);
                e.getGuild().ban(msg[1], 7).queue();
                e.reply("The user has been successfully soft-banned!");
            } catch (HierarchyException hex) {
                e.reply("I was not able to soft-ban this user. Make sure my role is above theirs!");
            } catch (Exception exc) {
                e.reply("You must specify a user that is in the server, either by ID or by mention.");
            }
        }
        else {
            try {
                e.getMessage().delete().queue();
                e.reply(e.getMessage().getMentionedMembers().get(0).toString());
                e.getGuild().ban(e.getMessage().getMentionedMembers().get(0), 7).queue();
                e.reply("The user has been successfully soft-banned!");
            } catch (HierarchyException he) {
                e.reply("I was not able to soft-ban this user. Make sure my role is above theirs");
            } catch (Exception ex) {
                e.reply("There was an error soft-banning this user. Are they in the server?");
            }

        }

    }
}
