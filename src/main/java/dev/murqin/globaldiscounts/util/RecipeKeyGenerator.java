package dev.murqin.globaldiscounts.util;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

/**
 * Tarif anahtarı oluşturucu.
 * Her tarif için benzersiz NamespacedKey üretir ve önbelleğe alır.
 */
public class RecipeKeyGenerator {

    private static final String DISCOUNT_KEY_PREFIX = "d_";
    private final Plugin plugin;
    private final Map<String, NamespacedKey> keyCache = new HashMap<>();

    public RecipeKeyGenerator(Plugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Sonuç ve ilk malzemeye göre tarif için benzersiz bir anahtar oluşturur.
     * Girdisi olmayan tarifler için null döner.
     * 
     * @param recipe Ticaret tarifi
     * @return Benzersiz NamespacedKey veya null
     */
    public NamespacedKey generate(MerchantRecipe recipe) {
        if (recipe.getIngredients().isEmpty()) {
            return null;
        }
        ItemStack result = recipe.getResult();
        ItemStack ingredient = recipe.getIngredients().get(0);
        
        // Sonuç türü + malzeme türü + malzeme miktarından hash oluştur
        String hash = result.getType().name() + "_" + ingredient.getType().name() + "_" + ingredient.getAmount();
        
        return keyCache.computeIfAbsent(hash, k -> new NamespacedKey(plugin, DISCOUNT_KEY_PREFIX + k.hashCode()));
    }
}
