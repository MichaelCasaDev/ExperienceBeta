package ExperienceBeta.michaeldev.commands.subcommands;

import ExperienceBeta.michaeldev.commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;


public class ReloadCommand extends SubCommand {
    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public String getDescription() {
        return "Reload plugin config file";
    }

    @Override
    public String getSyntax() {
        return "/ba reload";
    }

    @Override
    public void perform(Player player, String[] args){
        final Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("ExperienceBeta");
        final FileConfiguration config = plugin.getConfig();

        if(player.hasPermission("ba.reload")){
            plugin.reloadConfig();
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("Reload_Message") ));

        } else {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("ReloadNoPerms") ));

        }
    }
}
