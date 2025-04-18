package me.threefour.blockbeacon.commands;

import me.threefour.blockbeacon.BlockBeacon;
import me.threefour.blockbeacon.utils.BlockFinder;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class FindBlockCommand implements CommandExecutor {
    
    private final BlockBeacon plugin;
    
    public FindBlockCommand(BlockBeacon plugin) {
        this.plugin = plugin;
        plugin.getCommand("findblock").setExecutor(this);
    }
    
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be used by players!");
            return true;
        }
        
        if (args.length < 1) {
            player.sendMessage(ChatColor.RED + "Usage: /findblock <blocktype|category> [radius]");
            return true;
        }
        
        String blockType = args[0].toLowerCase();
        int radius = 50; // Default radius
        
        if (args.length >= 2) {
            try {
                radius = Integer.parseInt(args[1]);
                if (radius <= 0 || radius > 100) {
                    player.sendMessage(ChatColor.RED + "Radius must be between 1 and 100!");
                    return true;
                }
            } catch (NumberFormatException e) {
                player.sendMessage(ChatColor.RED + "Invalid radius! Using default of 50 blocks.");
            }
        }
        
        player.sendMessage(ChatColor.GREEN + "Searching for " + blockType + " blocks within " + radius + " blocks...");
        
        // Use the BlockFinder utility to find the blocks
        BlockFinder.findBlocks(player, blockType, radius);
        
        return true;
    }
} 