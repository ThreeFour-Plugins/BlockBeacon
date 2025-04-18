# BlockBeacon

![BlockBeacon Logo](https://i.imgur.com/cYbN9zZ.png)

BlockBeacon is a powerful Minecraft plugin that allows players to find and inspect any block in the game world. Whether you're looking for valuable resources, special blocks like player heads, or just want to analyze the blocks around you, BlockBeacon makes it easy!

## ✨ Features

- 🔍 **Find any block type** within a specified radius
- 📋 **View detailed information** about blocks including credentials and properties
- 🗂️ **Block categories** to easily find groups of related blocks
- 🧠 **Smart material detection** that understands common names and aliases
- 📊 **Type breakdowns** when searching by category
- ⚡ **Optimized performance** using async methods to prevent server lag
- 💯 **Supports ALL Minecraft blocks** including special blocks like heads, containers, etc.

## 📥 Installation

1. Download the latest release JAR file from the [releases page](https://github.com/ThreeFour-Plugins/BlockBeacon/releases)
2. Place the JAR file in your server's `plugins` folder
3. Restart your server
4. Done! The plugin is ready to use

## 🎮 Commands

### `/findblock <blocktype|category> [radius]`

Finds all blocks of the specified type or category within the given radius (default: 50 blocks).

#### Examples:
- `/findblock diamond` - Find diamond blocks within 50 blocks
- `/findblock playerhead 100` - Find player heads within 100 blocks
- `/findblock ores 30` - Find all ore blocks within 30 blocks
- `/findblock nether` - Find all nether-related blocks within 50 blocks

## 🖱️ Block Inspection

**Shift + Right-click** on any block to view detailed information about it, including:

- Basic block properties (hardness, blast resistance)
- Special block information (container contents, beacon effects, etc.)
- Block category and type
- Block credentials for special blocks
- Technical block data

## 📚 Block Categories

BlockBeacon comes with many pre-defined block categories that you can use with the `/findblock` command:

| Category | Description |
|----------|-------------|
| `woods` | All wood logs and planks |
| `heads` or `skulls` | All player and mob heads/skulls |
| `ores` | All ore blocks |
| `deepslateores` | All deepslate ore variants |
| `valuable` | Valuable blocks like diamond, gold, iron blocks |
| `containers` | Storage blocks like chests, barrels, etc. |
| `utilities` | Utility blocks like crafting tables, furnaces |
| `redstone` | Redstone components and mechanisms |
| `colored` | All colored blocks (wool, concrete, glass, etc.) |
| `nether` | Blocks from the Nether dimension |
| `end` | Blocks from the End dimension |
| `decoration` | Decorative blocks like flowers, pots, banners |
| `solid` | All solid blocks |
| `transparent` | All non-solid blocks |

In addition, you can search by specific wood types (`oak`, `birch`, `spruce`, etc.).

## 🌟 Block Aliases

The plugin supports many common block aliases, allowing you to search using familiar terms:

- `head`, `playerhead`, `skull` -> Player Head
- `diamond`, `gold`, `iron`, etc. -> The respective block type
- `crafting`, `workbench` -> Crafting Table
- `enderchest` -> Ender Chest
- and many more!

## 📊 Performance

BlockBeacon is designed with performance in mind:
- All block searching is done asynchronously to prevent server lag
- Smart chunk loading checks to avoid loading unnecessary chunks
- Efficient category-based searching with type summaries

## 📸 Screenshots

![Finding Blocks](https://i.imgur.com/example1.png)
*Finding diamond blocks within 50 blocks*

![Block Information](https://i.imgur.com/example2.png)
*Detailed information about a player head block*

## 🤝 Contributing

Contributions are welcome! Feel free to submit issues or pull requests.

## 📜 License

BlockBeacon is licensed under the MIT License - see the LICENSE file for details.

## 👨‍💻 Author

Made with ❤️ by Amineos

---