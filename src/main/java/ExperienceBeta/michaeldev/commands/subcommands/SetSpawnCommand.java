package ExperienceBeta.michaeldev.commands.subcommands;

import ExperienceBeta.michaeldev.commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;


import java.io.File;
import java.io.IOException;

public class SetSpawnCommand extends SubCommand {

    @Override
    public String getName() {
        return "setspawn";
    }

    @Override
    public String getDescription() {
        return "Set the spawn where the player join";
    }

    @Override
    public String getSyntax() {
        return "/ba setspawn";
    }

    private static File file;
    private static FileConfiguration cconfig;

    @Override
    public void perform(Player player, String[] args) throws IOException {

        final Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("ExperienceBeta");
        final FileConfiguration config = plugin.getConfig();

        //Finds or generates the custom config file
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("ExperienceBeta").getDataFolder(), "spawn.yml");

        cconfig = YamlConfiguration.loadConfiguration(file);


        int spawnX;
        int spawnY;
        int spawnZ;


        if (player.hasPermission("ba.setspawn")) {

            spawnX = player.getLocation().getBlockX();
            spawnY = player.getLocation().getBlockY();
            spawnZ = player.getLocation().getBlockZ();

            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    //owww
                }
            }
            cconfig.set("SpawnPosition.X", spawnX);
            cconfig.set("SpawnPosition.Y", spawnY);
            cconfig.set("SpawnPosition.Z", spawnZ);
            cconfig.set("SpawnPosition.WorldName", player.getLocation().getWorld().getName());

            cconfig.save(file);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("SpawnSetOK")));


        } else {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("SpawnSetERROR")));
        }
    }
}