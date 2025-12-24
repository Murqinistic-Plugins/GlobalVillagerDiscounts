package dev.murqin.globaldiscounts.lang;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

/**
 * Dosya tabanlı çoklu dil yöneticisi.
 * Kullanıcılar kendi dil dosyalarını ekleyebilir.
 */
public class LanguageManager {

    private final Plugin plugin;
    private final Map<String, String> messages = new HashMap<>();
    private FileConfiguration langConfig;
    private String currentLanguage;

    public LanguageManager(Plugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Dil dosyasını yükler.
     * Önce plugin data klasöründen, yoksa resources'dan yükler.
     */
    public void loadLanguage(String language) {
        this.currentLanguage = language;
        messages.clear();

        // Varsayılan dil dosyalarını çıkar
        saveDefaultLanguages();

        // Dil dosyasını yükle
        File langFile = new File(plugin.getDataFolder(), "lang/" + language + ".yml");
        
        if (!langFile.exists()) {
            plugin.getLogger().warning("Language file not found: " + language + ".yml, falling back to en.yml");
            langFile = new File(plugin.getDataFolder(), "lang/en.yml");
        }

        if (langFile.exists()) {
            langConfig = YamlConfiguration.loadConfiguration(langFile);
            
            // Eksik key'leri İngilizce'den doldur
            fillMissingKeys();
            
            // Mesajları cache'e yükle
            for (String key : langConfig.getKeys(false)) {
                String value = langConfig.getString(key, "");
                messages.put(key, ChatColor.translateAlternateColorCodes('&', value));
            }
        } else {
            plugin.getLogger().severe("No language files found! Using hardcoded defaults.");
            loadHardcodedDefaults();
        }
    }

    /**
     * Varsayılan dil dosyalarını çıkarır.
     */
    private void saveDefaultLanguages() {
        File langDir = new File(plugin.getDataFolder(), "lang");
        if (!langDir.exists()) {
            langDir.mkdirs();
        }

        // en.yml
        File enFile = new File(langDir, "en.yml");
        if (!enFile.exists()) {
            plugin.saveResource("lang/en.yml", false);
        }

        // tr.yml
        File trFile = new File(langDir, "tr.yml");
        if (!trFile.exists()) {
            plugin.saveResource("lang/tr.yml", false);
        }
    }

    /**
     * Eksik key'leri İngilizce varsayılanlardan doldurur.
     */
    private void fillMissingKeys() {
        InputStream defaultStream = plugin.getResource("lang/en.yml");
        if (defaultStream != null) {
            FileConfiguration defaultConfig = YamlConfiguration.loadConfiguration(
                new InputStreamReader(defaultStream, StandardCharsets.UTF_8)
            );
            
            for (String key : defaultConfig.getKeys(false)) {
                if (!langConfig.contains(key)) {
                    langConfig.set(key, defaultConfig.getString(key));
                }
            }
        }
    }

    /**
     * Hardcoded varsayılanları yükler (fallback).
     */
    private void loadHardcodedDefaults() {
        messages.put("player-only", ChatColor.RED + "This command must be used by a player.");
        messages.put("no-permission", ChatColor.RED + "You don't have permission.");
        messages.put("look-at-villager", ChatColor.RED + "Look at a villager to use this command.");
        messages.put("share-usage", ChatColor.RED + "Usage: /gvd share <on|off>");
        messages.put("info-header", ChatColor.GREEN + "=== Villager Info ===");
        messages.put("info-profession", ChatColor.GRAY + "Profession: " + ChatColor.WHITE);
        messages.put("info-sync-status", ChatColor.GRAY + "Sync Status: ");
        messages.put("info-sync-active", ChatColor.GREEN + "Active");
        messages.put("info-sync-disabled", ChatColor.RED + "Disabled");
        messages.put("info-no-discounts", ChatColor.GRAY + "  No stored discounts.");
        messages.put("info-discounts-header", ChatColor.YELLOW + "Discounts:");
        messages.put("info-uuid", ChatColor.YELLOW + "UUID: " + ChatColor.WHITE);
        messages.put("share-enabled-header", ChatColor.GREEN + "=== Sharing Enabled ===");
        messages.put("share-disabled-header", ChatColor.YELLOW + "=== Sharing Disabled ===");
        messages.put("share-cleared", ChatColor.GRAY + "Cleared discounts: " + ChatColor.WHITE);
        messages.put("clear-header", ChatColor.GREEN + "=== Discounts Cleared ===");
        messages.put("clear-count", ChatColor.GRAY + "Cleared: " + ChatColor.WHITE);
        messages.put("clear-suffix", " discount(s)");
        messages.put("clearall-result", ChatColor.GREEN + "Cleared %d discounts from %d villagers.");
        messages.put("help-header-player", ChatColor.GREEN + "=== GlobalVillagerDiscounts ===");
        messages.put("help-header-admin", ChatColor.GREEN + "=== GVD Admin Commands ===");
        messages.put("log-career-change", "Cleared %d synced discounts from villager %s due to career change.");
        messages.put("log-enabled", "GlobalVillagerDiscounts enabled!");
        messages.put("log-disabled", "GlobalVillagerDiscounts disabled.");
    }

    /**
     * Mesajı key ile getirir.
     */
    public String get(String key) {
        return messages.getOrDefault(key, ChatColor.RED + "Missing: " + key);
    }

    /**
     * Formatlanmış mesajı getirir.
     */
    public String get(String key, Object... args) {
        String message = get(key);
        try {
            return String.format(message, args);
        } catch (Exception e) {
            return message;
        }
    }

    public String getCurrentLanguage() {
        return currentLanguage;
    }
}
