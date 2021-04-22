package ExperienceBeta.michaeldev;

import ExperienceBeta.michaeldev.commands.CommandManager;
import ExperienceBeta.michaeldev.events.PlayerJoin;
import org.bukkit.plugin.java.JavaPlugin;

public final class ExperienceBeta extends JavaPlugin {

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        //Command Manager [ /ba <command> ]
        getCommand("ba").setExecutor(new CommandManager());

        //Events
        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);

        //Console
        getLogger().info("\n" +
                "---------------------------\n" +
                "ExperienceBeta Loaded Succesfully!" +
                "\n---------------------------");

    }
}