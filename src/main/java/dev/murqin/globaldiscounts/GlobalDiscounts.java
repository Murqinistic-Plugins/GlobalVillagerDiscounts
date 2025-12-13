package dev.murqin.globaldiscounts;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Merchant;
import org.bukkit.inventory.MerchantInventory;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

/**
 * GlobalVillagerDiscounts - Synchronizes villager trade discounts across all players.
 * 
 * <p>This plugin implements a "Sticky Discount" system where discounts earned by any player
 * (through curing) are stored in the Villager's PersistentDataContainer
 * and applied to all future trades for any player.</p>
 * 
 * @author murqin
 * @version 1.0.0
 */
public final class GlobalDiscounts extends JavaPlugin implements Listener {

    private static final String DISCOUNT_KEY_PREFIX = "discount_";
    private NamespacedKey processedKey;

    @Override
    public void onEnable() {
        processedKey = new NamespacedKey(this, "processed");
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("GlobalVillagerDiscounts enabled! Discounts will now be shared across all players.");
    }

    @Override
    public void onDisable() {
        getLogger().info("GlobalVillagerDiscounts disabled.");
    }

    private NamespacedKey getDiscountKey(int index) {
        return new NamespacedKey(this, DISCOUNT_KEY_PREFIX + index);
    }

    /**
     * Handles inventory open events to capture and apply discounts.
     * This is where discounts are actually calculated by Minecraft.
     */
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onInventoryOpen(InventoryOpenEvent event) {
        if (!(event.getInventory() instanceof MerchantInventory merchantInventory)) {
            return;
        }

        if (!(event.getPlayer() instanceof Player player)) {
            return;
        }

        Merchant merchant = merchantInventory.getMerchant();
        
        if (!(merchantInventory.getHolder() instanceof Villager villager)) {
            return;
        }

        // Skip Hero of the Village - their discounts are temporary
        boolean hasHeroEffect = player.hasPotionEffect(org.bukkit.potion.PotionEffectType.HERO_OF_THE_VILLAGE);
        
        // Step 1: Capture current discounts (only if player doesn't have HotV)
        if (!hasHeroEffect) {
            captureDiscounts(villager, merchant, player);
        }
        
        // Step 2: Apply stored discounts to player (only if player doesn't have HotV)
        if (!hasHeroEffect) {
            applyStoredDiscounts(villager, merchant, player);
        }
    }

    /**
     * Captures and stores the best discounts from current trades into the villager's PDC.
     * Only stores the discount if it's better than what's already stored.
     */
    private void captureDiscounts(Villager villager, Merchant merchant, Player player) {
        PersistentDataContainer pdc = villager.getPersistentDataContainer();
        List<MerchantRecipe> recipes = merchant.getRecipes();

        for (int i = 0; i < recipes.size(); i++) {
            MerchantRecipe recipe = recipes.get(i);
            int currentDiscount = recipe.getSpecialPrice();
            
            // Only store if there's an actual discount (negative special price)
            if (currentDiscount < 0) {
                NamespacedKey key = getDiscountKey(i);
                Integer storedDiscount = pdc.get(key, PersistentDataType.INTEGER);
                
                // Store if no previous discount exists or if this discount is better (more negative)
                if (storedDiscount == null || currentDiscount < storedDiscount) {
                    pdc.set(key, PersistentDataType.INTEGER, currentDiscount);
                }
            }
        }
        
        // Mark this villager as processed
        pdc.set(processedKey, PersistentDataType.BYTE, (byte) 1);
    }

    /**
     * Applies stored discounts from the villager's PDC to the merchant's recipes.
     * Ensures the final price is always at least 1 to prevent free items.
     */
    private void applyStoredDiscounts(Villager villager, Merchant merchant, Player player) {
        PersistentDataContainer pdc = villager.getPersistentDataContainer();
        List<MerchantRecipe> originalRecipes = merchant.getRecipes();
        List<MerchantRecipe> modifiedRecipes = new ArrayList<>();

        boolean hasChanges = false;
        
        for (int i = 0; i < originalRecipes.size(); i++) {
            MerchantRecipe original = originalRecipes.get(i);
            NamespacedKey key = getDiscountKey(i);
            Integer storedDiscount = pdc.get(key, PersistentDataType.INTEGER);
            int currentDiscount = original.getSpecialPrice();
            
            // Apply stored discount if it's better than current
            if (storedDiscount != null && storedDiscount < 0 && storedDiscount < currentDiscount) {
                MerchantRecipe modified = cloneRecipeWithDiscount(original, storedDiscount);
                modifiedRecipes.add(modified);
                hasChanges = true;
            } else {
                modifiedRecipes.add(original);
            }
        }

        // Only update recipes if there are actual changes
        if (hasChanges) {
            merchant.setRecipes(modifiedRecipes);
        }
    }

    /**
     * Clones a MerchantRecipe and applies the specified discount.
     * Ensures the final price is always at least 1 emerald.
     */
    private MerchantRecipe cloneRecipeWithDiscount(MerchantRecipe original, int discount) {
        MerchantRecipe modified = new MerchantRecipe(
            original.getResult(),
            original.getUses(),
            original.getMaxUses(),
            original.hasExperienceReward(),
            original.getVillagerExperience(),
            original.getPriceMultiplier(),
            original.getDemand(),
            discount
        );

        modified.setIngredients(original.getIngredients());

        // Safety check: Ensure the adjusted price is at least 1
        int basePrice = original.getIngredients().get(0).getAmount();
        int adjustedPrice = basePrice + discount;
        
        if (adjustedPrice < 1) {
            int safeDiscount = -(basePrice - 1);
            modified = new MerchantRecipe(
                original.getResult(),
                original.getUses(),
                original.getMaxUses(),
                original.hasExperienceReward(),
                original.getVillagerExperience(),
                original.getPriceMultiplier(),
                original.getDemand(),
                safeDiscount
            );
            modified.setIngredients(original.getIngredients());
        }

        return modified;
    }
}
