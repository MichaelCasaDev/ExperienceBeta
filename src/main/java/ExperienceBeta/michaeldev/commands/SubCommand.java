package ExperienceBeta.michaeldev.commands;

import org.bukkit.entity.Player;

import java.io.IOException;

public abstract class SubCommand  {


    //name of the subcommand
    public abstract String getName();

    //ex. "This is a subcommand that let's a shark eat someone"
    public abstract String getDescription();

    //How to use command
    public abstract String getSyntax();

    //code for the subcommand
    public abstract void perform(Player player, String args[]) throws IOException;
}
