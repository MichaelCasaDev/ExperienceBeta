package ExperienceBeta.michaeldev.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.io.IOException;


public class PlayerJoin implements Listener {

    public static boolean PlayerIsLogged;

    private Player playerJoined;

    final Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("ExperienceBeta");
    final FileConfiguration config = plugin.getConfig();


    private static File file;
    private static FileConfiguration cconfig;
    Location l;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) throws InterruptedException {
            //Finds or generates the custom config file
            file = new File(Bukkit.getServer().getPluginManager().getPlugin("ExperienceBeta").getDataFolder(), "spawn.yml");

            cconfig = YamlConfiguration.loadConfiguration(file);

            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException es) {
                    //owww
                }
            }

            Player player = e.getPlayer();
            playerJoined = e.getPlayer();
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("AccessWelcomeMessage")));
            CheckPerms(playerJoined);
            SetPlayerJoinNew(playerJoined);

    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        SetPlayerJoinNew(playerJoined);
    }

    private void CheckPerms(Player player) throws InterruptedException {
        if(player.hasPermission("ba.bypass")){

            //Teleport player to the spawn
            ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
            String command = "spawn " + player.getName();
            Bukkit.dispatchCommand(console, command);

            PlayerIsLogged = true;
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("AccessBypass")));
        } else {
            PlayerIsLogged = false;
            SetPlayerJoinNew(player);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("AccessBypass_Failed")));
        }
    }


    public void SetPlayerJoinNew(Player player) {
        if(PlayerIsLogged) { //Un lock player
            player.removePotionEffect(PotionEffectType.BLINDNESS);
            player.removePotionEffect(PotionEffectType.JUMP);
            player.setWalkSpeed((float) 0.2);
            player.setFlySpeed((float) 0.2);
        } else { //Lock player
            //Teleport player to beta access spawn
            l = new Location(Bukkit.getWorld(cconfig.getString("SpawnPosition.WorldName")), cconfig.getInt("SpawnPosition.X"), cconfig.getInt("SpawnPosition.Y"), cconfig.getInt("SpawnPosition.Z"));
            playerJoined.teleport(l);
            //Add some cool Effects
            player.setAllowFlight(false);
            player.setWalkSpeed(0);
            player.setFlySpeed(0);
            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100000, 255), true);
            player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 100000, 128), true);
        }
    }
};

