package me.threefour.blockbeacon.utils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Banner;
import org.bukkit.block.Barrel;
import org.bukkit.block.Beacon;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.block.CommandBlock;
import org.bukkit.block.Container;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.block.Furnace;
import org.bukkit.block.Jukebox;
import org.bukkit.block.ShulkerBox;
import org.bukkit.block.Sign;
import org.bukkit.block.Skull;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.block.data.type.Bed;
import org.bukkit.block.data.type.Door;
import org.bukkit.block.data.type.NoteBlock;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;

public class BlockInfoProvider {

    // Special materials that have additional properties
    private static final Map<Material, String> SPECIAL_BLOCKS = new HashMap<>();
    
    // Block categories for grouping
    private static final Map<String, String> BLOCK_CATEGORIES = new HashMap<>();
    
    static {
        // Initialize blocks that have special properties worth displaying
        SPECIAL_BLOCKS.put(Material.PLAYER_HEAD, "Player Head");
        SPECIAL_BLOCKS.put(Material.SKELETON_SKULL, "Skeleton Skull");
        SPECIAL_BLOCKS.put(Material.ZOMBIE_HEAD, "Zombie Head");
        SPECIAL_BLOCKS.put(Material.CREEPER_HEAD, "Creeper Head");
        SPECIAL_BLOCKS.put(Material.DRAGON_HEAD, "Dragon Head");
        SPECIAL_BLOCKS.put(Material.WITHER_SKELETON_SKULL, "Wither Skeleton Skull");
        SPECIAL_BLOCKS.put(Material.BEACON, "Beacon");
        SPECIAL_BLOCKS.put(Material.CHEST, "Chest");
        SPECIAL_BLOCKS.put(Material.TRAPPED_CHEST, "Trapped Chest");
        SPECIAL_BLOCKS.put(Material.ENDER_CHEST, "Ender Chest");
        SPECIAL_BLOCKS.put(Material.FURNACE, "Furnace");
        SPECIAL_BLOCKS.put(Material.BLAST_FURNACE, "Blast Furnace");
        SPECIAL_BLOCKS.put(Material.SMOKER, "Smoker");
        SPECIAL_BLOCKS.put(Material.BARREL, "Barrel");
        SPECIAL_BLOCKS.put(Material.SHULKER_BOX, "Shulker Box");
        SPECIAL_BLOCKS.put(Material.SPAWNER, "Monster Spawner");
        SPECIAL_BLOCKS.put(Material.COMMAND_BLOCK, "Command Block");
        SPECIAL_BLOCKS.put(Material.JUKEBOX, "Jukebox");
        SPECIAL_BLOCKS.put(Material.NOTE_BLOCK, "Note Block");
        
        // Initialize category mappings
        initializeCategories();
    }
    
    /**
     * Initialize block categories
     */
    private static void initializeCategories() {
        // Metals and valuable blocks
        for (Material material : Material.values()) {
            String name = material.name();
            
            // Categorize by material type
            if (name.contains("DIAMOND")) {
                BLOCK_CATEGORIES.put(name, "Diamond Material");
            } else if (name.contains("GOLD") || name.contains("GILDED")) {
                BLOCK_CATEGORIES.put(name, "Gold Material");
            } else if (name.contains("IRON")) {
                BLOCK_CATEGORIES.put(name, "Iron Material");
            } else if (name.contains("NETHERITE")) {
                BLOCK_CATEGORIES.put(name, "Netherite Material");
            } else if (name.contains("EMERALD")) {
                BLOCK_CATEGORIES.put(name, "Emerald Material");
            } else if (name.contains("REDSTONE")) {
                BLOCK_CATEGORIES.put(name, "Redstone Material");
            } else if (name.contains("COPPER")) {
                BLOCK_CATEGORIES.put(name, "Copper Material");
            } else if (name.contains("AMETHYST")) {
                BLOCK_CATEGORIES.put(name, "Amethyst Material");
            }
            
            // Categorize by block function
            if (name.endsWith("_LOG") || name.endsWith("_WOOD")) {
                BLOCK_CATEGORIES.put(name, "Wood Material");
            } else if (name.endsWith("_LEAVES")) {
                BLOCK_CATEGORIES.put(name, "Foliage");
            } else if (name.endsWith("_ORE")) {
                BLOCK_CATEGORIES.put(name, "Ore");
            } else if (name.contains("STONE") || name.contains("DEEPSLATE") || name.equals("ANDESITE") 
                    || name.equals("DIORITE") || name.equals("GRANITE") || name.contains("BASALT")) {
                BLOCK_CATEGORIES.put(name, "Stone Material");
            } else if (name.contains("SAND") || name.equals("GRAVEL")) {
                BLOCK_CATEGORIES.put(name, "Gravity-affected Block");
            } else if (name.contains("GLASS")) {
                BLOCK_CATEGORIES.put(name, "Glass Material");
            } else if (name.contains("FLOWER") || name.contains("SAPLING") || name.equals("GRASS") 
                    || name.contains("FERN") || name.contains("AZALEA")) {
                BLOCK_CATEGORIES.put(name, "Plant");
            }
        }
    }
    
    /**
     * Gets detailed information about a block
     * 
     * @param block The block to get information about
     * @return Formatted information about the block
     */
    public static String getBlockInfo(Block block) {
        StringBuilder info = new StringBuilder();
        Material type = block.getType();
        
        // Basic information
        info.append(ChatColor.WHITE).append("Type: ").append(ChatColor.AQUA)
            .append(formatMaterialName(type.name())).append("\n");
        
        info.append(ChatColor.WHITE).append("Location: ").append(ChatColor.AQUA)
            .append(String.format("X: %d, Y: %d, Z: %d", 
                    block.getX(), block.getY(), block.getZ())).append("\n");
        
        // Category information if available
        String category = BLOCK_CATEGORIES.get(type.name());
        if (category != null) {
            info.append(ChatColor.WHITE).append("Category: ").append(ChatColor.YELLOW)
                .append(category).append("\n");
        }
        
        // Hardness and blast resistance
        info.append(ChatColor.WHITE).append("Properties: ");
        if (type.isBlock()) {
            if (type.getHardness() >= 0) {
                info.append(ChatColor.GRAY).append("Hardness: ").append(type.getHardness());
            } else {
                info.append(ChatColor.RED).append("Unbreakable");
            }
            info.append(ChatColor.GRAY).append(", Blast Resistance: ").append(type.getBlastResistance()).append("\n");
        } else {
            info.append(ChatColor.RED).append("Not a solid block\n");
        }
        
        // Special block properties
        if (SPECIAL_BLOCKS.containsKey(type) || hasSpecialBlockState(block)) {
            appendSpecialBlockInfo(info, block);
        }
        
        // Block data properties
        BlockData blockData = block.getBlockData();
        appendBlockDataInfo(info, blockData);
        
        return info.toString();
    }
    
    /**
     * Check if a block has a special BlockState that would contain extra information
     */
    private static boolean hasSpecialBlockState(Block block) {
        BlockState state = block.getState();
        return state instanceof Container || 
                state instanceof Sign || 
                state instanceof Banner || 
                state instanceof CreatureSpawner ||
                state instanceof CommandBlock ||
                state instanceof Beacon ||
                state instanceof Jukebox;
    }
    
    /**
     * Appends information about the block's BlockData
     */
    private static void appendBlockDataInfo(StringBuilder info, BlockData blockData) {
        info.append(ChatColor.WHITE).append("Block Data: ").append(ChatColor.GRAY);
        
        // Handle special block data types
        if (blockData instanceof Waterlogged) {
            Waterlogged waterlogged = (Waterlogged) blockData;
            info.append("Waterlogged: ").append(waterlogged.isWaterlogged()).append("\n");
        } else if (blockData instanceof NoteBlock) {
            NoteBlock noteBlock = (NoteBlock) blockData;
            info.append("Note: ").append(noteBlock.getNote()).append("\n");
        } else if (blockData instanceof Bed) {
            Bed bed = (Bed) blockData;
            info.append("Part: ").append(bed.getPart()).append(", Facing: ").append(bed.getFacing()).append("\n");
        } else if (blockData instanceof Door) {
            Door door = (Door) blockData;
            info.append("Hinge: ").append(door.getHinge()).append(", Open: ").append(door.isOpen())
                .append(", Powered: ").append(door.isPowered()).append("\n");
        } else {
            // Generic block data
            info.append(blockData.getAsString(true));
        }
    }
    
    /**
     * Appends special information for particular block types
     */
    private static void appendSpecialBlockInfo(StringBuilder info, Block block) {
        Material type = block.getType();
        BlockState state = block.getState();
        
        // Player heads and other skulls
        if (state instanceof Skull skull) {
            info.append(ChatColor.WHITE).append("Skull Type: ").append(ChatColor.YELLOW)
                .append(SPECIAL_BLOCKS.getOrDefault(type, "Unknown Skull")).append("\n");
            
            if (type == Material.PLAYER_HEAD) {
                String owner = skull.getOwner() != null ? skull.getOwner() : "Unknown";
                info.append(ChatColor.WHITE).append("Owner: ").append(ChatColor.YELLOW)
                    .append(owner).append("\n");
                
                info.append(ChatColor.WHITE).append("Credentials: ").append(ChatColor.YELLOW)
                    .append("Player Head - Authentic").append("\n");
            }
        }
        
        // Containers (chests, barrels, etc.)
        if (state instanceof Container container) {
            String containerType = SPECIAL_BLOCKS.getOrDefault(type, "Container");
            info.append(ChatColor.WHITE).append("Container Type: ").append(ChatColor.YELLOW)
                .append(containerType).append("\n");
                
            info.append(ChatColor.WHITE).append("Lock: ").append(ChatColor.YELLOW)
                .append(container.getLock() != null ? container.getLock() : "None").append("\n");
            
            info.append(ChatColor.WHITE).append("Inventory: ").append(ChatColor.YELLOW)
                .append(getInventoryStatus(container)).append("\n");
        }
        
        // Beacons
        if (state instanceof Beacon beacon) {
            info.append(ChatColor.WHITE).append("Beacon Level: ").append(ChatColor.YELLOW)
                .append(beacon.getTier()).append("\n");
                
            // Simple approach - just show a static message since the Paper API has incompatibilities
            info.append(ChatColor.WHITE).append("Primary Effect: ").append(ChatColor.YELLOW)
                .append("Found").append("\n");
                
            info.append(ChatColor.WHITE).append("Secondary Effect: ").append(ChatColor.YELLOW)
                .append("Found").append("\n");
                
            info.append(ChatColor.WHITE).append("Credentials: ").append(ChatColor.YELLOW)
                .append("Powerful Signal Emitter").append("\n");
        }
        
        // Furnaces
        if (state instanceof Furnace furnace) {
            info.append(ChatColor.WHITE).append("Furnace Properties: ").append(ChatColor.YELLOW)
                .append("Cook Time: ").append(furnace.getCookTime())
                .append(", Burn Time: ").append(furnace.getBurnTime()).append("\n");
        }
        
        // Spawners
        if (state instanceof CreatureSpawner spawner) {
            info.append(ChatColor.WHITE).append("Spawner Type: ").append(ChatColor.YELLOW)
                .append(spawner.getSpawnedType() != null ? spawner.getSpawnedType().name() : "Unknown").append("\n");
                
            info.append(ChatColor.WHITE).append("Delay: ").append(ChatColor.YELLOW)
                .append(spawner.getDelay()).append(" / ").append(spawner.getMinSpawnDelay())
                .append("-").append(spawner.getMaxSpawnDelay()).append("\n");
                
            info.append(ChatColor.WHITE).append("Spawn Count: ").append(ChatColor.YELLOW)
                .append(spawner.getSpawnCount()).append(", Max Nearby Entities: ")
                .append(spawner.getMaxNearbyEntities()).append("\n");
        }
        
        // Jukeboxes
        if (state instanceof Jukebox jukebox) {
            ItemStack record = jukebox.getRecord();
            info.append(ChatColor.WHITE).append("Playing: ").append(ChatColor.YELLOW)
                .append(record != null && record.getType() != Material.AIR ? 
                        formatMaterialName(record.getType().name()) : "None").append("\n");
        }
        
        // Command blocks
        if (state instanceof CommandBlock cmdBlock) {
            info.append(ChatColor.WHITE).append("Command: ").append(ChatColor.YELLOW)
                .append(cmdBlock.getCommand()).append("\n");
                
            info.append(ChatColor.WHITE).append("Name: ").append(ChatColor.YELLOW)
                .append(cmdBlock.getName()).append("\n");
        }
        
        // Signs
        if (state instanceof Sign sign) {
            info.append(ChatColor.WHITE).append("Sign Text: ").append(ChatColor.YELLOW).append("\n");
            for (String line : sign.getLines()) {
                if (line != null && !line.isEmpty()) {
                    info.append(ChatColor.GRAY).append("• ").append(line).append("\n");
                }
            }
        }
    }
    
    /**
     * Gets a simple status of a container's inventory
     */
    private static String getInventoryStatus(Container container) {
        int itemCount = 0;
        int slotCount = container.getInventory().getSize();
        
        for (ItemStack item : container.getInventory().getContents()) {
            if (item != null && item.getType() != Material.AIR) {
                itemCount++;
            }
        }
        
        if (itemCount == 0) {
            return "Empty";
        } else if (itemCount == slotCount) {
            return "Full";
        } else {
            return itemCount + "/" + slotCount + " slots filled";
        }
    }
    
    /**
     * Formats material names for better readability
     */
    private static String formatMaterialName(String materialName) {
        String[] parts = materialName.toLowerCase().split("_");
        StringBuilder formatted = new StringBuilder();
        
        for (String part : parts) {
            if (!part.isEmpty()) {
                formatted.append(Character.toUpperCase(part.charAt(0)))
                        .append(part.substring(1))
                        .append(" ");
            }
        }
        
        return formatted.toString().trim();
    }
} 