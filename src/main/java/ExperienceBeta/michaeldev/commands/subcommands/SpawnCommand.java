package ExperienceBeta.michaeldev.commands.subcommands;

import ExperienceBeta.michaeldev.commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;

public class SpawnCommand extends SubCommand {
    @Override
    public String getName() {
        return "spawn";
    }

    @Override
    public String getDescription() {
        return "Teleport to the beta access spawn";
    }

    @Override
    public String getSyntax() {
        return "/ba spawn";
    }


    Location l;
    Player playerToTeleport;

    private static File file;
    private static FileConfiguration config;

    @Override
    public void perform(Player player, String[] args) {
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("ExperienceBeta").getDataFolder(), "spawn.yml");

        config = YamlConfiguration.loadConfiguration(file);

        Location spawnTeleportTo = null;



        final Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("ExperienceBeta");
        final FileConfiguration cconfig = plugin.getConfig();

        if(player.hasPermission("ba.spawn")){
            if(config.getString("SpawnPosition.WorldName") == null){
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', cconfig.getString("NoSpawnFound")));
            } else {
                if(args.length < 1) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', cconfig.getString("TeleportingToSpawn")));
                    playerToTeleport = Bukkit.getPlayer(args[1]);
                    playerToTeleport.teleport(l);
                } else {
                    l = new Location(Bukkit.getWorld(config.getString("SpawnPosition.WorldName")), config.getInt("SpawnPosition.X"), config.getInt("SpawnPosition.Y"), config.getInt("SpawnPosition.Z"));
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', cconfig.getString("TeleportingToSpawn")));
                    player.teleport(l);
                }
            }
        } else {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',cconfig.getString("SpawnNoPermission")));
        }
    }
}
