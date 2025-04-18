package me.threefour.blockbeacon.listeners;

import me.threefour.blockbeacon.BlockBeacon;
import me.threefour.blockbeacon.utils.BlockInfoProvider;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Skull;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class BlockInteractListener implements Listener {
    
    private final BlockBeacon plugin;
    
    public BlockInteractListener(BlockBeacon plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        // Ignore off-hand interactions to prevent duplicate events
        if (event.getHand() != EquipmentSlot.HAND) {
            return;
        }
        
        // Only process right-click on blocks
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();
        
        // Check if player is sneaking (shift) - this allows normal block interaction when not sneaking
        if (!player.isSneaking()) {
            return;
        }
        
        if (block != null) {
            // Get the block information
            String blockInfo = BlockInfoProvider.getBlockInfo(block);
            
            // Send the information to the player
            player.sendMessage(ChatColor.GOLD + "Block Information:");
            player.sendMessage(blockInfo);
            
            // Prevent the normal block interaction (like opening containers)
            event.setCancelled(true);
        }
    }
} 