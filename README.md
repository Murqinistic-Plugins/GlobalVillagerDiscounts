<p align="center">
  <img src="logo.png" alt="GlobalVillagerDiscounts logo" width="128" style="border-radius: 24px;" />
</p>

# GlobalVillagerDiscounts 💸

> **Synchronize and share cured zombie villager trade discounts across all online players on your Paper server.**

[![Minecraft: 26.1+](https://img.shields.io/badge/Minecraft-26.1%2B-brightgreen.svg?style=flat-square&logo=minecraft)](https://www.minecraft.net/)
[![Java: 25](https://img.shields.io/badge/Java-25-orange.svg?style=flat-square)](https://openjdk.org/)
[![Platform: Spigot / Paper](https://img.shields.io/badge/Spigot%20%2F%20Paper-Compatible-blue.svg?style=flat-square)](#)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg?style=flat-square)](LICENSE)
[![bStats](https://img.shields.io/bstats/servers/28505?style=flat-square)](https://bstats.org/plugin/bukkit/GlobalVillagerDiscounts/28505)

GlobalVillagerDiscounts introduces a cooperative villager economy structure. When any single player successfully **cures a zombie villager**, the resulting trade discount is securely saved and applied **globally**, making that exact discount instantly accessible to **every** player trading with that villager.

---

## 📖 How It Works

1. **Cure & Capture:** A player cures a zombie villager and earns a discount.
2. **Persistence:** The plugin captures and logs the unique recipe hash and discount metrics.
3. **Sharing:** Any other player trading with that specific villager immediately gets the cured price discount.
4. **Calculated Economy:** If multiple players cure a villager, the best active discount is always selected.
5. **Professions Sync:** Curing records auto-clear instantly upon the villager's profession change.

> [!NOTE]
> Temporarily applied Hero of the Village discounts are strictly excluded from being synchronized.

---

## ✨ Features

- **🔌 Plug & Play:** Drop the `.jar` file and play. Zero complex backend DB setup required.
- **💾 Database Persistence:** All discounts are stored locally and fully survive server restarts.
- **🛡️ Inflation Protection:** Safeguards server economies by ensuring prices never drop below exactly 1 Emerald.
- **⚡ High-Performance:** Handled asynchronously to ensure virtually zero performance overhead or tick lagging.
- **🔒 Admin Controls:** Lock individual villagers to prevent players from changing or toggling global sharing states.
- **🌍 Localization:** Fully translated into English and Turkish by default.

---

## 🔧 Commands

All commands are run looking directly at the target villager.

| Command | Permission | Description |
|---------|------------|-------------|
| `/gvd info` | *None* | Displays raw villager UUID, lock state, and share settings. *(UUID visible to Admins only)* |
| `/gvd share <on\|off>` | *None* | Enables or disables discount sharing for the target villager. |
| `/gvd clear` | `gvd.admin` | Clears all registered discounts for the target villager. |
| `/gvd clearall` | `gvd.admin` | Clears the entire database of all recorded villager discounts. |
| `/gvd lock` | `gvd.admin` | Locks the villager, preventing non-OP players from changing share states. |
| `/gvd unlock` | `gvd.admin` | Unlocks the villager for player configuration modifications. |

---

## 🌍 Adding Translations

The plugin includes default localization assets for **English (en)** and **Turkish (tr)**.

To provision a new language:
1. Locate `config.yml` and check `language: en`.
2. Access `plugins/GlobalVillagerDiscounts/lang/` and duplicate `en.yml` to `de.yml` (e.g. for German).
3. Translate all values.
4. Set `language: de` in your `config.yml`.
5. Restart your server instance.

---

## 📋 Technical Requirements

- **Minecraft Server Engine:** Spigot, Paper, or Purpur (Version 26.1 or higher)
- **Java Virtual Machine:** JDK 25
- **Folia Compatibility:** Unsupported due to Folia's multi-threaded region ticking design.

---

## 📥 Installation Steps

1. Download the compiled plugin build from [Modrinth](https://modrinth.com/plugin/globalvillagerdiscounts).
2. Save the `.jar` package into your server's `plugins/` directory.
3. Restart the server.

---

## 📄 License

Licensed under the terms of the MIT License. See [LICENSE](LICENSE) for more details.
