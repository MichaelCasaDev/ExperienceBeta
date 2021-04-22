package ExperienceBeta.michaeldev.commands;

import ExperienceBeta.michaeldev.commands.subcommands.AccessCommand;
import ExperienceBeta.michaeldev.commands.subcommands.ReloadCommand;
import ExperienceBeta.michaeldev.commands.subcommands.SetSpawnCommand;
import ExperienceBeta.michaeldev.commands.subcommands.SpawnCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.IOException;
import java.util.ArrayList;

public class CommandManager implements CommandExecutor {

    private final ArrayList<SubCommand> subcommands = new ArrayList<>();

    public CommandManager(){
        //subcommands.add(new CommandFile());
        subcommands.add(new ReloadCommand());
        subcommands.add(new AccessCommand());
        subcommands.add(new SetSpawnCommand());
        subcommands.add(new SpawnCommand());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        final Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("ExperienceBeta");
        final FileConfiguration config = plugin.getConfig();

        Player p = (Player) sender;

        if (args.length > 0){ // /ba <command>
            for (int i = 0; i < getSubcommands().size(); i++){
                if (args[0].equalsIgnoreCase(getSubcommands().get(i).getName())){
                    try {
                        getSubcommands().get(i).perform(p, args);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }else if(args.length == 0){ // /ba
            if(p.hasPermission("ba.help")){
                p.sendMessage(ChatColor.translateAlternateColorCodes('&',"&3Beta Access System - Commands"));
                p.sendMessage(ChatColor.translateAlternateColorCodes('&',"&f--------------------------------&f\n"));
                for (int i = 0; i < getSubcommands().size(); i++){
                    p.sendMessage(getSubcommands().get(i).getSyntax() + " - " + getSubcommands().get(i).getDescription());
                }
                p.sendMessage(ChatColor.translateAlternateColorCodes('&',"\n &6 Developed by MichaelDevC"));
                p.sendMessage(ChatColor.translateAlternateColorCodes('&',"&f--------------------------------"));
            } else {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&',config.getString("HelpNoPermission")));
            }

        }

        return true;
    }

    public ArrayList<SubCommand> getSubcommands(){
        return subcommands;
    }

}
