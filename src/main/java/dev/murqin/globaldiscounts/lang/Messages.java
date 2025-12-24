package dev.murqin.globaldiscounts.lang;

/**
 * Mesaj key'leri için sabit referanslar.
 * LanguageManager üzerinden mesajları çeker.
 */
public class Messages {

    private static LanguageManager languageManager;

    public static void init(LanguageManager manager) {
        languageManager = manager;
    }

    // Hatalar
    public static String PLAYER_ONLY() { return languageManager.get("player-only"); }
    public static String NO_PERMISSION() { return languageManager.get("no-permission"); }
    public static String LOOK_AT_VILLAGER() { return languageManager.get("look-at-villager"); }
    public static String SHARE_USAGE() { return languageManager.get("share-usage"); }

    // Info komutu
    public static String INFO_HEADER() { return languageManager.get("info-header"); }
    public static String INFO_PROFESSION() { return languageManager.get("info-profession"); }
    public static String INFO_SYNC_STATUS() { return languageManager.get("info-sync-status"); }
    public static String INFO_SYNC_ACTIVE() { return languageManager.get("info-sync-active"); }
    public static String INFO_SYNC_DISABLED() { return languageManager.get("info-sync-disabled"); }
    public static String INFO_NO_DISCOUNTS() { return languageManager.get("info-no-discounts"); }
    public static String INFO_DISCOUNTS_HEADER() { return languageManager.get("info-discounts-header"); }
    public static String INFO_UUID() { return languageManager.get("info-uuid"); }

    // Share komutu
    public static String SHARE_ENABLED_HEADER() { return languageManager.get("share-enabled-header"); }
    public static String SHARE_DISABLED_HEADER() { return languageManager.get("share-disabled-header"); }
    public static String SHARE_CLEARED() { return languageManager.get("share-cleared"); }

    // Clear komutu
    public static String CLEAR_HEADER() { return languageManager.get("clear-header"); }
    public static String CLEAR_COUNT() { return languageManager.get("clear-count"); }
    public static String CLEAR_SUFFIX() { return languageManager.get("clear-suffix"); }

    // ClearAll komutu
    public static String CLEARALL_RESULT(int villagers, int discounts) {
        return languageManager.get("clearall-result", villagers, discounts);
    }

    // Yardım
    public static String HELP_HEADER_PLAYER() { return languageManager.get("help-header-player"); }
    public static String HELP_HEADER_ADMIN() { return languageManager.get("help-header-admin"); }

    // Lock mesajları
    public static String INFO_LOCK_STATUS() { return languageManager.get("info-lock-status"); }
    public static String INFO_LOCKED() { return languageManager.get("info-locked"); }
    public static String INFO_UNLOCKED() { return languageManager.get("info-unlocked"); }
    public static String VILLAGER_LOCKED() { return languageManager.get("villager-locked"); }
    public static String ALREADY_LOCKED() { return languageManager.get("already-locked"); }
    public static String NOT_LOCKED() { return languageManager.get("not-locked"); }
    public static String LOCK_SUCCESS() { return languageManager.get("lock-success"); }
    public static String UNLOCK_SUCCESS() { return languageManager.get("unlock-success"); }

    // Log mesajları
    public static String LOG_CAREER_CHANGE(Object uuid, int count) {
        return languageManager.get("log-career-change", count, uuid);
    }
    public static String LOG_ENABLED() { return languageManager.get("log-enabled"); }
    public static String LOG_DISABLED() { return languageManager.get("log-disabled"); }
}
