package dev.murqin.globaldiscounts;

import dev.murqin.globaldiscounts.command.GvdCommandExecutor;
import dev.murqin.globaldiscounts.command.subcommand.*;
import dev.murqin.globaldiscounts.lang.LanguageManager;
import dev.murqin.globaldiscounts.lang.Messages;
import dev.murqin.globaldiscounts.listener.CareerChangeListener;
import dev.murqin.globaldiscounts.listener.TradeListener;
import dev.murqin.globaldiscounts.service.DiscountService;
import dev.murqin.globaldiscounts.util.RecipeKeyGenerator;
import dev.murqin.globaldiscounts.util.VillagerTargeter;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * GlobalVillagerDiscounts - Köylü ticaret indirimlerini tüm oyuncular arasında senkronize eder.
 * 
 * @author murqin
 * @version 1.3.0
 */
public final class GlobalVillagerDiscounts extends JavaPlugin {

    private static final int BSTATS_PLUGIN_ID = 28505;

    @Override
    public void onEnable() {
        // Config'i yükle
        saveDefaultConfig();
        
        // Dil sistemini başlat
        LanguageManager languageManager = new LanguageManager(this);
        String language = getConfig().getString("language", "en").toLowerCase();
        languageManager.loadLanguage(language);
        Messages.init(languageManager);
        
        // bStats metrikleri
        new Metrics(this, BSTATS_PLUGIN_ID);
        
        // Utility'leri oluştur
        RecipeKeyGenerator keyGenerator = new RecipeKeyGenerator(this);
        VillagerTargeter targeter = new VillagerTargeter();
        
        // Ana servisi oluştur
        DiscountService discountService = new DiscountService(this, keyGenerator);
        
        // Listener'ları kaydet
        getServer().getPluginManager().registerEvents(new TradeListener(discountService), this);
        getServer().getPluginManager().registerEvents(new CareerChangeListener(discountService), this);
        
        // Komut sistemini kur
        GvdCommandExecutor commandExecutor = new GvdCommandExecutor();
        
        // Komutları kaydet (isim, admin-only)
        commandExecutor.registerCommand(new InfoCommand(discountService, targeter), false);
        commandExecutor.registerCommand(new ShareCommand(discountService, targeter), false);
        commandExecutor.registerCommand(new ClearCommand(discountService, targeter), true);      // Admin-only
        commandExecutor.registerCommand(new ClearAllCommand(discountService), true);              // Admin-only
        commandExecutor.registerCommand(new LockCommand(discountService, targeter), true);        // Admin-only
        commandExecutor.registerCommand(new UnlockCommand(discountService, targeter), true);      // Admin-only
        
        getCommand("gvd").setExecutor(commandExecutor);
        getCommand("gvd").setTabCompleter(commandExecutor);
        
        getLogger().info(Messages.LOG_ENABLED());
    }

    @Override
    public void onDisable() {
        getLogger().info(Messages.LOG_DISABLED());
    }
}
