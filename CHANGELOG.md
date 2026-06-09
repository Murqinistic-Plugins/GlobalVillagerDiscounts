# Changelog

## [1.5.0] - 2026-06-09

### ⚡ Performance & Safety
- **NamespacedKey Caching**: Cached generated `NamespacedKey` instances in `RecipeKeyGenerator` to prevent CPU overhead and regex checks on GUI open.
- **Vector Math Optimization**: Replaced `distance()` with `distanceSquared()` in `VillagerTargeter` to avoid heavy `Math.sqrt` calculations.
- **Salt-Based Lazy Clear**: Implemented a salt-based clearing mechanism in `DiscountService` to make `/gvd clearall` an O(1) operation and fix the bug where unloaded villagers retained discounts.
- **Null Safety**: Added checks to prevent exceptions on empty-ingredient custom recipes.

## [1.3.0] - 2024-12-24

### ✨ New Features

- **File-Based Language System**
  - English (en) and Turkish (tr) included
  - Users can add their own translations by creating `lang/xx.yml` files
  - Configure with `language: xx` in config.yml

- **Villager Locking**
  - `/gvd lock` - Lock villagers so players can't change share settings
  - `/gvd unlock` - Unlock villagers
  - Locked status visible in `/gvd info`

- **Tab Completion**
  - Auto-complete for all commands
  - Shows available subcommands and options

- **Simplified Command Structure**
  - No more `/gvd admin` prefix
  - Commands have permission-based access

### 🔄 Changed

| Command | Permission | Description |
|---------|------------|-------------|
| `/gvd info` | - | UUID only visible for admins |
| `/gvd share on/off` | - | Blocked on locked villagers (non-admin) |
| `/gvd clear` | `gvd.admin` | Clear target villager's discounts |
| `/gvd clearall` | `gvd.admin` | Clear ALL discounts |
| `/gvd lock` | `gvd.admin` | Lock villager |
| `/gvd unlock` | `gvd.admin` | Unlock villager |

### 🐛 Bug Fixes

- Fixed profession display showing raw CraftProfession object
- Fixed career change log not respecting language setting

---

## [1.2.0] - 2024-12-24

### Added
- Codebase polished and refactored
- bStats integration (Plugin ID: 28505)
- Share command (`/gvd share on/off`)

### Changed
- Split monolithic code into modular structure
- Separated services, listeners, commands, and utilities

---

## [1.1.0] - Initial Release

- Core discount synchronization functionality
- Admin commands for villager management
- Persistent storage using PDC
- Career change auto-cleanup
