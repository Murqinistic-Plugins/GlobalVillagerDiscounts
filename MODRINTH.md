# GlobalVillagerDiscounts

**Synchronize villager curing discounts across all players on your server!**

## Description

GlobalVillagerDiscounts shares villager trade discounts among all players. When any player earns a discount by **curing a zombie villager**, that discount becomes available to **everyone**.

### How It Works

1. Player cures a zombie villager → earns discount
2. Plugin captures and stores the discount
3. Any other player trading with that villager gets the same discount
4. Discounts auto-clear on profession change

> **Note:** Hero of the Village discounts are **not** shared.

## Features

- 🔌 **Plug & Play** - No configuration needed
- 💾 **Persistent** - Discounts survive restarts
- 🛡️ **Safe** - Prices never below 1 emerald
- ⚡ **Lightweight** - Minimal performance impact
- 🌍 **Multi-Language** - English & Turkish, add your own!
- 🔒 **Villager Locking** - Lock villagers to prevent player changes
- 📊 **Tab Completion** - Easy command usage

## Commands

| Command | Permission | Description |
|---------|------------|-------------|
| `/gvd info` | - | Show villager info (UUID only for admins) |
| `/gvd share on/off` | - | Enable/disable discount sharing |
| `/gvd clear` | `gvd.admin` | Clear target villager's discounts |
| `/gvd clearall` | `gvd.admin` | Clear ALL discounts |
| `/gvd lock` | `gvd.admin` | Lock villager (players can't change share) |
| `/gvd unlock` | `gvd.admin` | Unlock villager |

> **Usage:** Look at a villager first, then run the command.

## Language Support

Built-in: **English (en)** & **Turkish (tr)**

```yaml
# config.yml
language: en  # or tr
```

### Add Your Own Language
1. Copy `lang/en.yml` to `lang/de.yml`
2. Translate all messages
3. Set `language: de` in config.yml
4. Restart server

## Requirements

- **Server:** Spigot, Paper, or Purpur 26.1+
- **Java:** 25

## Compatibility

| Server | Supported |
|--------|-----------|
| Spigot | ✅ |
| Paper | ✅ |
| Purpur | ✅ |
| Folia | ❌ |

## Source Code

[GitHub](https://github.com/Murqinistic-Plugins/GlobalVillagerDiscounts)

## License

MIT License
