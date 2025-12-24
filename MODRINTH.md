# GlobalVillagerDiscounts

**Synchronize villager curing discounts across all players on your server!**

## Description

GlobalVillagerDiscounts shares villager trade discounts among all players. When any player earns a discount by **curing a zombie villager**, that discount becomes available to **everyone**.

### How It Works

1. Player cures a zombie villager → earns discount
2. Plugin captures and stores the discount (by recipe hash)
3. Any other player trading with that villager gets the same discount
4. Best discount always wins
5. Discounts auto-clear on profession change

> **Note:** Hero of the Village discounts are **not** shared (temporary effect).

## Features

- 🔌 **Plug & Play** - No configuration needed
- 💾 **Persistent** - Discounts survive restarts
- 🛡️ **Safe** - Prices never below 1 emerald
- ⚡ **Lightweight** - Minimal performance impact
- 🔧 **Admin Controls** - Per-villager management
- 🔄 **Smart Storage** - Recipe-based, not index-based
- 🧹 **Auto-Cleanup** - Clears on profession change

## Commands

### Player Commands
| Command | Description |
|---------|-------------|
| `/gvd info` | Show villager discount info |
| `/gvd share on` | Enable discount sharing for target villager |
| `/gvd share off` | Disable discount sharing for target villager |

### Admin Commands
| Command | Description |
|---------|-------------|
| `/gvd clear` | Clear discounts for target villager |
| `/gvd clearall` | Clear ALL discounts from ALL villagers |
| `/gvd disable` | Disable sync for target villager |
| `/gvd enable` | Re-enable sync for target villager |

> **Usage:** Look at a villager first, then run the command (except `clearall`).

**Permission:** `gvd.admin` for admin commands (default: OP)

## Requirements

- **Server:** Spigot, Bukkit, Paper, or Purpur 1.21+
- **Java:** 21

## Compatibility

| Server | Supported |
|--------|-----------|
| Spigot | ✅ |
| Bukkit | ✅ |
| Paper | ✅ |
| Purpur | ✅ |
| Folia | ❌ |

## Important Notes

- Discounts stored **separately** from vanilla gossip
- If plugin removed, synced discounts stop working
- Profession change = discounts cleared automatically

## Source Code

[GitHub](https://github.com/murqin/GlobalVillagerDiscounts)

## License

MIT License
