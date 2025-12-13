# GlobalVillagerDiscounts

[![Minecraft Version](https://img.shields.io/badge/Minecraft-1.21%2B-brightgreen.svg)](https://www.minecraft.net/)
[![Java Version](https://img.shields.io/badge/Java-21-orange.svg)](https://openjdk.org/)
[![Spigot](https://img.shields.io/badge/Spigot%20%2F%20Paper-Compatible-blue.svg)](https://www.spigotmc.org/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

**Synchronize villager trade discounts across all players on your server!**

## 📖 Description

GlobalVillagerDiscounts is a lightweight, plug-and-play Spigot/Paper plugin that ensures villager trade discounts are shared among all players. When any player earns a discount through **curing zombie villagers**, that discount becomes permanently available to **everyone** on the server.

### How It Works

The plugin implements a **"Sticky Discount"** system:

1. When a player opens a villager's trade menu, the plugin captures any active curing discounts
2. These discounts are stored in the villager's `PersistentDataContainer` (survives restarts)
3. When any other player opens the same villager's trade menu, the stored discounts are applied
4. The best discount is always kept (if multiple players earn different discounts)

> **Note:** Hero of the Village discounts are **not** shared as they are temporary player effects.

## ✨ Features

- 🔌 **Plug & Play** - No configuration needed, just drop in and go
- 💾 **Persistent Storage** - Discounts survive server restarts
- 🛡️ **Safe Pricing** - Prices can never go below 1 emerald
- ⚡ **Lightweight** - Minimal performance impact
- 🔄 **Best Discount Wins** - Always keeps the most favorable discount
- 📦 **Zero Dependencies** - Only requires Spigot/Paper API
- 🚫 **No Stacking** - Discounts don't infinitely stack between players

## 📋 Requirements

- **Minecraft Server:** Spigot or Paper 1.21+
- **Java:** 21 (LTS)

## 📥 Installation

1. Download the latest `GlobalVillagerDiscounts-1.0.0.jar` from [Releases](https://github.com/murqin/GlobalVillagerDiscounts/releases)
2. Place the JAR file in your server's `plugins` folder
3. Restart your server
4. Done! No configuration required.

## 🔧 Building from Source

```bash
# Clone the repository
git clone https://github.com/murqin/GlobalVillagerDiscounts.git
cd GlobalVillagerDiscounts

# Build with Maven
mvn clean package

# The JAR will be in target/GlobalVillagerDiscounts-1.0.0.jar
```

## 🎮 Usage

Simply play the game as normal! The plugin works automatically in the background:

1. **Cure a zombie villager** to earn permanent discounts
2. Open the villager's trade menu (discounts are captured and stored)
3. Other players can now access the same discounts when trading with that villager

## 🤝 Compatibility

| Server Software | Supported |
|-----------------|-----------|
| Spigot          | ✅        |
| Paper           | ✅        |
| Purpur          | ✅        |
| Folia           | ❌        |

## 📜 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👤 Author

**murqin**

- GitHub: [@murqin](https://github.com/murqin)

## 🙏 Acknowledgments

- Thanks to the Spigot and Paper teams for their excellent APIs
- Inspired by the community need for shared villager economy

---

<p align="center">
  Made with ❤️ for the Minecraft community
</p>
