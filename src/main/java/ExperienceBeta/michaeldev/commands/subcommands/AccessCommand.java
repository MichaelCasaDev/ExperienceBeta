package ExperienceBeta.michaeldev.commands.subcommands;

import ExperienceBeta.michaeldev.commands.SubCommand;
import ExperienceBeta.michaeldev.events.PlayerJoin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;


public class AccessCommand extends SubCommand {
    @Override
    public String getName() {
        return "access";
    }

    @Override
    public String getDescription() {
        return "Access to the server with the provided key.";
    }

    @Override
    public String getSyntax() {
        return "/ba access <key>";
    }


    static int inCorrectKey = 0;


    @Override
    public void perform(Player player, String[] args){
        final Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("ExperienceBeta");
        final FileConfiguration config = plugin.getConfig();


        String playername = player.getName();
        String keyconfig = config.getString(playername);

        //Join to the other script to catch the player movement
        PlayerJoin PlayerJoinClass = new PlayerJoin();


        if(args.length == 2){
            String keyarg = args[1]; //Key is in the ARGS 1

            if(keyarg.equals(keyconfig)){
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("WelcomeBetaAccessMessage")));
                PlayerJoinClass.PlayerIsLogged = true;
                PlayerJoinClass.SetPlayerJoinNew(player);
                //Teleport player to the spawn
                ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                String command = "spawn " + player.getName();
                Bukkit.dispatchCommand(console, command);
            } else {
                if(inCorrectKey > config.getInt("KeyFailureMaXTimes")){
                    ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                    String command = "ekick " + player.getName() + " -s " + ChatColor.translateAlternateColorCodes('&', config.getString("PlayerKeyExcedKickMessage"));
                    Bukkit.dispatchCommand(console, command);
                } else {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("KeyNotValid")));
                    PlayerJoinClass.PlayerIsLogged = false;
                    inCorrectKey++;
                }
            }
        } else {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("Access_NoKey")));
        }
    }
}