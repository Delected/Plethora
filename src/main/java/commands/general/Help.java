package commands.general;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import commands.moderation.Kick;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;

import java.awt.Color;

public class Help extends Command {
    public Help() {
        this.name = "help";
        this.aliases = new String[]{"helpme", "h"};
        this.botPermissions = new Permission[]{Permission.MESSAGE_WRITE, Permission.MESSAGE_MANAGE};
        this.category = new Category("general");
        this.help = "Sends the user a help message.";
    }
    @Override
    protected void execute(CommandEvent e) {
        String[] msg = e.getMessage().getContentRaw().split(" ");
        EmbedBuilder emb = new EmbedBuilder();
        if (msg.length == 1) {
            e.getAuthor().openPrivateChannel().submit().thenCompose(channel -> channel.sendMessage(emb.build()).submit()).whenComplete((message, error) -> {
                        if (error != null) { e.reply(e.getAuthor().getAsMention() + " I couldn't send you a DM. Please make sure they are enabled, or use `/help -c`");}
                        else { e.reply(e.getAuthor().getAsMention() + " sent you a DM!"); }
                    });

        }
        else {
            if (msg[1].equalsIgnoreCase("-c")) {
                e.getMessage().delete().queue();
                EmbedBuilder em = new EmbedBuilder();
                em.setTitle("__Plethora Help__");
                em.setColor(new Color(156, 83, 212));
                em.setDescription("**Click on an emoji to get started.**" +
                        "\n \n" +
                        "`\uD83D\uDCBC` Moderation Commands\n" +
                        "`\uD83D\uDEE0` Utility Commands\n" +
                        "`\uD83C\uDF89` Fun Commands\n" +
                        "`\uD83D\uDD0D` General Commands");

                e.getChannel().sendMessage(em.build()).queue(message -> {
                    message.addReaction("\uD83D\uDCBC").queue();
                    message.addReaction("\uD83D\uDEE0").queue();
                    message.addReaction("\uD83C\uDF89").queue();
                    message.addReaction("\uD83D\uDD0D").queue();
                });

            }
            else {
                e.getMessage().delete().queue();
                e.reply("I am not sure what you mean. Please use `/help` or `/help -c`");
            }
        }
    }
}
