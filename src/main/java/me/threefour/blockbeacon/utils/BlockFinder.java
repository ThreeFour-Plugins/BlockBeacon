package me.threefour.blockbeacon.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Skull;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BlockFinder {
    
    // Maps common search terms to actual block materials
    private static final Map<String, Material> BLOCK_MAPPINGS = new HashMap<>();
    
    // Map of block categories for easier searching
    private static final Map<String, List<Material>> BLOCK_CATEGORIES = new HashMap<>();
    
    static {
        // Initialize common block mappings (aliases)
        // Skulls/heads
        BLOCK_MAPPINGS.put("playerhead", Material.PLAYER_HEAD);
        BLOCK_MAPPINGS.put("head", Material.PLAYER_HEAD);
        BLOCK_MAPPINGS.put("skull", Material.PLAYER_HEAD);
        
        // Valuable blocks
        BLOCK_MAPPINGS.put("diamond", Material.DIAMOND_BLOCK);
        BLOCK_MAPPINGS.put("gold", Material.GOLD_BLOCK);
        BLOCK_MAPPINGS.put("iron", Material.IRON_BLOCK);
        BLOCK_MAPPINGS.put("emerald", Material.EMERALD_BLOCK);
        BLOCK_MAPPINGS.put("netherite", Material.NETHERITE_BLOCK);
        
        // Common utility blocks
        BLOCK_MAPPINGS.put("beacon", Material.BEACON);
        BLOCK_MAPPINGS.put("stone", Material.STONE);
        BLOCK_MAPPINGS.put("dirt", Material.DIRT);
        BLOCK_MAPPINGS.put("grass", Material.GRASS_BLOCK);
        BLOCK_MAPPINGS.put("wood", Material.OAK_WOOD);
        BLOCK_MAPPINGS.put("log", Material.OAK_LOG);
        BLOCK_MAPPINGS.put("chest", Material.CHEST);
        BLOCK_MAPPINGS.put("furnace", Material.FURNACE);
        BLOCK_MAPPINGS.put("crafting", Material.CRAFTING_TABLE);
        BLOCK_MAPPINGS.put("workbench", Material.CRAFTING_TABLE);
        BLOCK_MAPPINGS.put("anvil", Material.ANVIL);
        BLOCK_MAPPINGS.put("enchanting", Material.ENCHANTING_TABLE);
        BLOCK_MAPPINGS.put("enchant", Material.ENCHANTING_TABLE);
        BLOCK_MAPPINGS.put("bookshelf", Material.BOOKSHELF);
        
        // Redstone components
        BLOCK_MAPPINGS.put("redstone", Material.REDSTONE_BLOCK);
        BLOCK_MAPPINGS.put("piston", Material.PISTON);
        BLOCK_MAPPINGS.put("observer", Material.OBSERVER);
        BLOCK_MAPPINGS.put("lever", Material.LEVER);
        BLOCK_MAPPINGS.put("button", Material.STONE_BUTTON);
        BLOCK_MAPPINGS.put("repeater", Material.REPEATER);
        BLOCK_MAPPINGS.put("comparator", Material.COMPARATOR);
        
        // Storage
        BLOCK_MAPPINGS.put("barrel", Material.BARREL);
        BLOCK_MAPPINGS.put("shulker", Material.SHULKER_BOX);
        BLOCK_MAPPINGS.put("enderchest", Material.ENDER_CHEST);
        BLOCK_MAPPINGS.put("hopper", Material.HOPPER);
        BLOCK_MAPPINGS.put("dropper", Material.DROPPER);
        BLOCK_MAPPINGS.put("dispenser", Material.DISPENSER);
        
        // Ores
        BLOCK_MAPPINGS.put("diamondore", Material.DIAMOND_ORE);
        BLOCK_MAPPINGS.put("goldore", Material.GOLD_ORE);
        BLOCK_MAPPINGS.put("ironore", Material.IRON_ORE);
        BLOCK_MAPPINGS.put("emeraldore", Material.EMERALD_ORE);
        BLOCK_MAPPINGS.put("redstonore", Material.REDSTONE_ORE);
        BLOCK_MAPPINGS.put("lapisore", Material.LAPIS_ORE);
        BLOCK_MAPPINGS.put("coal", Material.COAL_ORE);
        BLOCK_MAPPINGS.put("copper", Material.COPPER_ORE);
        
        // Rare/Special
        BLOCK_MAPPINGS.put("spawner", Material.SPAWNER);
        BLOCK_MAPPINGS.put("command", Material.COMMAND_BLOCK);
        BLOCK_MAPPINGS.put("bedrock", Material.BEDROCK);
        BLOCK_MAPPINGS.put("endportal", Material.END_PORTAL_FRAME);
        BLOCK_MAPPINGS.put("portalframe", Material.END_PORTAL_FRAME);
        BLOCK_MAPPINGS.put("dragoneg", Material.DRAGON_EGG);
        
        // Nether blocks
        BLOCK_MAPPINGS.put("netherrack", Material.NETHERRACK);
        BLOCK_MAPPINGS.put("soulsand", Material.SOUL_SAND);
        BLOCK_MAPPINGS.put("soulsoil", Material.SOUL_SOIL);
        BLOCK_MAPPINGS.put("ancientdebris", Material.ANCIENT_DEBRIS);
        BLOCK_MAPPINGS.put("netherbrick", Material.NETHER_BRICKS);
        
        // End blocks
        BLOCK_MAPPINGS.put("endstone", Material.END_STONE);
        BLOCK_MAPPINGS.put("purpur", Material.PURPUR_BLOCK);
        BLOCK_MAPPINGS.put("chorus", Material.CHORUS_FLOWER);
        
        // Initialize block categories
        initializeBlockCategories();
    }
    
    /**
     * Initialize categories of blocks for easier searching
     */
    private static void initializeBlockCategories() {
        // Woods category (all wood types)
        List<Material> woods = Arrays.stream(Material.values())
                .filter(m -> m.name().endsWith("_WOOD") || m.name().endsWith("_LOG"))
                .collect(Collectors.toList());
        BLOCK_CATEGORIES.put("woods", woods);
        BLOCK_CATEGORIES.put("logs", woods);
        
        // Tree categories by type
        Map<String, List<Material>> woodTypes = new HashMap<>();
        for (String type : Arrays.asList("OAK", "BIRCH", "SPRUCE", "JUNGLE", "ACACIA", "DARK_OAK", "MANGROVE", "CHERRY", "CRIMSON", "WARPED")) {
            String lowerType = type.toLowerCase();
            List<Material> typeWoods = Arrays.stream(Material.values())
                    .filter(m -> m.name().startsWith(type) && 
                            (m.name().endsWith("_WOOD") || m.name().endsWith("_LOG") || 
                             m.name().endsWith("_PLANKS") || m.name().endsWith("_FENCE") ||
                             m.name().endsWith("_STAIRS") || m.name().endsWith("_SLAB")))
                    .collect(Collectors.toList());
            BLOCK_CATEGORIES.put(lowerType, typeWoods);
        }
        
        // Skulls/heads category
        List<Material> skulls = Arrays.stream(Material.values())
                .filter(m -> m.name().endsWith("_HEAD") || m.name().endsWith("_SKULL"))
                .collect(Collectors.toList());
        BLOCK_CATEGORIES.put("skulls", skulls);
        BLOCK_CATEGORIES.put("heads", skulls);
        
        // Ores category
        List<Material> ores = Arrays.stream(Material.values())
                .filter(m -> m.name().endsWith("_ORE"))
                .collect(Collectors.toList());
        BLOCK_CATEGORIES.put("ores", ores);
        
        // Deepslate ores
        List<Material> deepslateOres = Arrays.stream(Material.values())
                .filter(m -> m.name().contains("DEEPSLATE") && m.name().endsWith("_ORE"))
                .collect(Collectors.toList());
        BLOCK_CATEGORIES.put("deepslateores", deepslateOres);
        
        // Storage blocks category
        List<Material> storageBlocks = Arrays.stream(Material.values())
                .filter(m -> m.name().contains("_BLOCK") && (
                        m.name().contains("DIAMOND") || 
                        m.name().contains("GOLD") || 
                        m.name().contains("IRON") || 
                        m.name().contains("EMERALD") || 
                        m.name().contains("COPPER") || 
                        m.name().contains("NETHERITE") ||
                        m.name().contains("COAL") ||
                        m.name().contains("REDSTONE") ||
                        m.name().contains("LAPIS")
                ))
                .collect(Collectors.toList());
        BLOCK_CATEGORIES.put("valuable", storageBlocks);
        BLOCK_CATEGORIES.put("valuables", storageBlocks);
        
        // Containers
        List<Material> containers = Arrays.asList(
                Material.CHEST, Material.TRAPPED_CHEST, Material.ENDER_CHEST, 
                Material.BARREL, Material.SHULKER_BOX, Material.HOPPER,
                Material.DROPPER, Material.DISPENSER);
        BLOCK_CATEGORIES.put("containers", containers);
        
        // Utility blocks
        List<Material> utility = Arrays.asList(
                Material.CRAFTING_TABLE, Material.FURNACE, Material.BLAST_FURNACE, 
                Material.SMOKER, Material.ANVIL, Material.ENCHANTING_TABLE, 
                Material.GRINDSTONE, Material.SMITHING_TABLE, Material.LOOM, 
                Material.CARTOGRAPHY_TABLE, Material.FLETCHING_TABLE, Material.BREWING_STAND);
        BLOCK_CATEGORIES.put("utilities", utility);
        BLOCK_CATEGORIES.put("utility", utility);
        BLOCK_CATEGORIES.put("workstations", utility);
        
        // Redstone components
        List<Material> redstone = Arrays.stream(Material.values())
                .filter(m -> m.name().contains("REDSTONE") || 
                             m.name().equals("PISTON") || 
                             m.name().equals("STICKY_PISTON") || 
                             m.name().equals("OBSERVER") || 
                             m.name().equals("REPEATER") || 
                             m.name().equals("COMPARATOR") || 
                             m.name().equals("LEVER") || 
                             m.name().contains("_BUTTON") || 
                             m.name().contains("PRESSURE_PLATE") || 
                             m.name().contains("TRIPWIRE") || 
                             m.name().equals("DAYLIGHT_DETECTOR") || 
                             m.name().equals("TARGET"))
                .collect(Collectors.toList());
        BLOCK_CATEGORIES.put("redstone", redstone);
        
        // Colored blocks
        List<Material> coloredBlocks = new ArrayList<>();
        for (String color : Arrays.asList("WHITE", "ORANGE", "MAGENTA", "LIGHT_BLUE", 
                                         "YELLOW", "LIME", "PINK", "GRAY", "LIGHT_GRAY", 
                                         "CYAN", "PURPLE", "BLUE", "BROWN", "GREEN", 
                                         "RED", "BLACK")) {
            coloredBlocks.addAll(Arrays.stream(Material.values())
                    .filter(m -> m.name().startsWith(color + "_"))
                    .collect(Collectors.toList()));
        }
        BLOCK_CATEGORIES.put("colored", coloredBlocks);
        
        // Nether blocks
        List<Material> netherBlocks = Arrays.stream(Material.values())
                .filter(m -> m.name().contains("NETHER") || 
                            m.name().contains("SOUL") || 
                            m.name().equals("ANCIENT_DEBRIS") ||
                            m.name().contains("CRIMSON") ||
                            m.name().contains("WARPED") ||
                            m.name().equals("BASALT") ||
                            m.name().equals("BLACKSTONE"))
                .collect(Collectors.toList());
        BLOCK_CATEGORIES.put("nether", netherBlocks);
        
        // End blocks
        List<Material> endBlocks = Arrays.stream(Material.values())
                .filter(m -> m.name().contains("END_") || 
                            m.name().contains("PURPUR") || 
                            m.name().contains("CHORUS") ||
                            m.name().equals("DRAGON_EGG"))
                .collect(Collectors.toList());
        BLOCK_CATEGORIES.put("end", endBlocks);
        
        // All solid blocks
        List<Material> solidBlocks = Arrays.stream(Material.values())
                .filter(Material::isBlock)
                .filter(Material::isSolid)
                .collect(Collectors.toList());
        BLOCK_CATEGORIES.put("solid", solidBlocks);
        
        // All transparent blocks
        List<Material> transparentBlocks = Arrays.stream(Material.values())
                .filter(Material::isBlock)
                .filter(m -> !m.isSolid())
                .collect(Collectors.toList());
        BLOCK_CATEGORIES.put("transparent", transparentBlocks);
        
        // Decoration blocks
        List<Material> decorationBlocks = Arrays.stream(Material.values())
                .filter(m -> m.name().contains("FLOWER") || 
                            m.name().contains("POTTED") || 
                            m.name().contains("BANNER") ||
                            m.name().contains("CARPET") ||
                            m.name().contains("CANDLE") ||
                            m.name().contains("LANTERN") ||
                            m.name().contains("POT") ||
                            m.name().contains("TORCH"))
                .collect(Collectors.toList());
        BLOCK_CATEGORIES.put("decoration", decorationBlocks);
        BLOCK_CATEGORIES.put("decorations", decorationBlocks);
    }
    
    /**
     * Finds blocks of a specific type around a player
     * 
     * @param player The player to search around
     * @param blockType The type of block to search for
     * @param radius The radius to search in
     */
    public static void findBlocks(Player player, String blockType, int radius) {
        // If the block type is a category, handle it differently
        if (BLOCK_CATEGORIES.containsKey(blockType)) {
            findBlocksByCategory(player, blockType, radius);
            return;
        }
        
        Material targetMaterial = getMaterialFromString(blockType);
        
        if (targetMaterial == null) {
            player.sendMessage(ChatColor.RED + "Unknown block type: " + blockType);
            player.sendMessage(ChatColor.YELLOW + "Try using one of these categories: " + 
                    String.join(", ", BLOCK_CATEGORIES.keySet()));
            return;
        }
        
        // Run the search asynchronously to avoid lag
        new BukkitRunnable() {
            @Override
            public void run() {
                List<Location> foundLocations = new ArrayList<>();
                Location playerLoc = player.getLocation();
                int playerX = playerLoc.getBlockX();
                int playerY = playerLoc.getBlockY();
                int playerZ = playerLoc.getBlockZ();
                
                // Search for blocks in a cubic area around the player
                for (int x = playerX - radius; x <= playerX + radius; x++) {
                    for (int y = Math.max(playerY - radius, 0); y <= Math.min(playerY + radius, player.getWorld().getMaxHeight()); y++) {
                        for (int z = playerZ - radius; z <= playerZ + radius; z++) {
                            Location loc = new Location(player.getWorld(), x, y, z);
                            // Only check loaded chunks
                            if (loc.getWorld().isChunkLoaded(x >> 4, z >> 4)) {
                                Block block = loc.getBlock();
                                if (block.getType() == targetMaterial) {
                                    foundLocations.add(loc);
                                }
                            }
                        }
                    }
                }
                
                // Back to the main thread to send messages
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        sendResultsToPlayer(player, blockType, radius, foundLocations);
                    }
                }.runTask(Bukkit.getPluginManager().getPlugin("BlockBeacon"));
            }
        }.runTaskAsynchronously(Bukkit.getPluginManager().getPlugin("BlockBeacon"));
    }
    
    /**
     * Finds blocks from a category around a player
     */
    private static void findBlocksByCategory(Player player, String category, int radius) {
        List<Material> materials = BLOCK_CATEGORIES.get(category);
        
        player.sendMessage(ChatColor.GREEN + "Searching for " + category + " blocks within " + 
                radius + " blocks... (" + materials.size() + " block types)");
        
        // Run the search asynchronously to avoid lag
        new BukkitRunnable() {
            @Override
            public void run() {
                List<Location> foundLocations = new ArrayList<>();
                Map<Material, Integer> countByType = new HashMap<>();
                Location playerLoc = player.getLocation();
                int playerX = playerLoc.getBlockX();
                int playerY = playerLoc.getBlockY();
                int playerZ = playerLoc.getBlockZ();
                
                // Search for blocks in a cubic area around the player
                for (int x = playerX - radius; x <= playerX + radius; x++) {
                    for (int y = Math.max(playerY - radius, 0); y <= Math.min(playerY + radius, player.getWorld().getMaxHeight()); y++) {
                        for (int z = playerZ - radius; z <= playerZ + radius; z++) {
                            Location loc = new Location(player.getWorld(), x, y, z);
                            // Only check loaded chunks
                            if (loc.getWorld().isChunkLoaded(x >> 4, z >> 4)) {
                                Block block = loc.getBlock();
                                if (materials.contains(block.getType())) {
                                    foundLocations.add(loc);
                                    // Count by type for summary
                                    countByType.put(block.getType(), 
                                            countByType.getOrDefault(block.getType(), 0) + 1);
                                }
                            }
                        }
                    }
                }
                
                // Back to the main thread to send messages
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        sendResultsToPlayer(player, category, radius, foundLocations);
                        
                        // Send type breakdown
                        if (!foundLocations.isEmpty()) {
                            player.sendMessage(ChatColor.GOLD + "Block type breakdown:");
                            countByType.entrySet().stream()
                                    .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                                    .limit(5)
                                    .forEach(entry -> {
                                        player.sendMessage(ChatColor.YELLOW + "• " + 
                                                formatMaterialName(entry.getKey().name()) + 
                                                ": " + entry.getValue());
                                    });
                            
                            if (countByType.size() > 5) {
                                player.sendMessage(ChatColor.YELLOW + "... and " + 
                                        (countByType.size() - 5) + " more types.");
                            }
                        }
                    }
                }.runTask(Bukkit.getPluginManager().getPlugin("BlockBeacon"));
            }
        }.runTaskAsynchronously(Bukkit.getPluginManager().getPlugin("BlockBeacon"));
    }
    
    /**
     * Sends search results to the player
     */
    private static void sendResultsToPlayer(Player player, String blockType, int radius, List<Location> foundLocations) {
        if (foundLocations.isEmpty()) {
            player.sendMessage(ChatColor.YELLOW + "No " + blockType + " blocks found within " + radius + " blocks.");
        } else {
            player.sendMessage(ChatColor.GREEN + "Found " + foundLocations.size() + " " + 
                    blockType + " blocks within " + radius + " blocks:");
            
            int limit = Math.min(foundLocations.size(), 10); // Limit to 10 blocks to avoid spam
            for (int i = 0; i < limit; i++) {
                Location loc = foundLocations.get(i);
                String coords = String.format("X: %d, Y: %d, Z: %d", 
                        loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
                player.sendMessage(ChatColor.AQUA + "• " + coords);
            }
            
            if (foundLocations.size() > 10) {
                player.sendMessage(ChatColor.YELLOW + "... and " + (foundLocations.size() - 10) + " more.");
            }
        }
    }
    
    /**
     * Converts a string to a Material
     */
    private static Material getMaterialFromString(String blockType) {
        // First check our mapping for common terms
        if (BLOCK_MAPPINGS.containsKey(blockType)) {
            return BLOCK_MAPPINGS.get(blockType);
        }
        
        // Try direct material name
        try {
            return Material.valueOf(blockType.toUpperCase());
        } catch (IllegalArgumentException e) {
            // Not a valid material name
            
            // Check if it ends with _block and try without that suffix
            if (blockType.endsWith("_block")) {
                String withoutBlock = blockType.substring(0, blockType.length() - 6).toUpperCase();
                try {
                    return Material.valueOf(withoutBlock + "_BLOCK");
                } catch (IllegalArgumentException ignored) {
                    // Still not valid
                }
            }
            
            // Try adding _BLOCK if it doesn't have it
            if (!blockType.endsWith("_block")) {
                try {
                    return Material.valueOf(blockType.toUpperCase() + "_BLOCK");
                } catch (IllegalArgumentException ignored) {
                    // Still not valid
                }
            }
            
            // Try other common suffixes
            String[] suffixes = {"_PLANKS", "_LOG", "_WOOD", "_SLAB", "_STAIRS", "_FENCE", "_WALL", "_ORE"};
            for (String suffix : suffixes) {
                try {
                    return Material.valueOf(blockType.toUpperCase() + suffix);
                } catch (IllegalArgumentException ignored) {
                    // Still not valid
                }
            }
            
            // Try color prefixes for common colored blocks
            String[] colors = {"WHITE_", "ORANGE_", "MAGENTA_", "LIGHT_BLUE_", "YELLOW_", "LIME_", 
                              "PINK_", "GRAY_", "LIGHT_GRAY_", "CYAN_", "PURPLE_", "BLUE_", 
                              "BROWN_", "GREEN_", "RED_", "BLACK_"};
            String[] colorTargets = {"WOOL", "CARPET", "CONCRETE", "CONCRETE_POWDER", "TERRACOTTA", 
                                    "STAINED_GLASS", "STAINED_GLASS_PANE", "GLAZED_TERRACOTTA", 
                                    "BANNER", "BED", "CANDLE", "SHULKER_BOX"};
            
            if (Arrays.asList("wool", "carpet", "concrete", "terracotta", "glass", "banner", 
                             "bed", "candle", "shulker").contains(blockType.toLowerCase())) {
                for (String colorTarget : colorTargets) {
                    if (colorTarget.toLowerCase().contains(blockType.toLowerCase())) {
                        try {
                            return Material.valueOf("WHITE_" + colorTarget);
                        } catch (IllegalArgumentException ignored) {
                            // Still not valid
                        }
                    }
                }
            }
            
            // Try fuzzy matching
            String searchTerm = blockType.toUpperCase();
            for (Material material : Material.values()) {
                if (material.isBlock() && material.name().contains(searchTerm)) {
                    return material;
                }
            }
        }
        
        return null;
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