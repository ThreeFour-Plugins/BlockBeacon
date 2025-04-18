package me.threefour.blockbeacon;

import me.threefour.blockbeacon.commands.FindBlockCommand;
import me.threefour.blockbeacon.listeners.BlockInteractListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class BlockBeacon extends JavaPlugin {

    @Override
    public void onEnable() {
        // Initialize the command executor
        new FindBlockCommand(this);
        
        // Register event listeners
        getServer().getPluginManager().registerEvents(new BlockInteractListener(this), this);
        
        // Log startup message
        getLogger().info("BlockBeacon has been enabled!");
        getLogger().info("Use /findblock <blocktype|category> [radius] to find blocks");
    }

    @Override
    public void onDisable() {
        getLogger().info("BlockBeacon has been disabled!");
    }
}
