package dev.murqin.globaldiscounts.command.subcommand;

import dev.murqin.globaldiscounts.service.DiscountService;
import dev.murqin.globaldiscounts.util.VillagerTargeter;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

import java.util.List;

/**
 * Köylü indirim bilgilerini gösteren komut.
 */
public class InfoCommand implements SubCommand {

    private final DiscountService discountService;
    private final VillagerTargeter targeter;

    public InfoCommand(DiscountService discountService, VillagerTargeter targeter) {
        this.discountService = discountService;
        this.targeter = targeter;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.RED + "Bu komut bir oyuncu tarafından kullanılmalıdır.");
            return;
        }

        Villager villager = targeter.getTargetVillager(player).orElse(null);
        if (villager == null) {
            sender.sendMessage(ChatColor.RED + "Bu komutu kullanmak için bir köylüye bakın.");
            return;
        }

        boolean isAdmin = player.hasPermission("gvd.admin");
        boolean syncEnabled = discountService.isSyncEnabled(villager);
        
        sender.sendMessage(ChatColor.GREEN + "=== Köylü Bilgisi ===");
        
        // UUID sadece adminlere gösterilir
        if (isAdmin) {
            sender.sendMessage(ChatColor.YELLOW + "UUID: " + ChatColor.WHITE + villager.getUniqueId());
        }
        
        sender.sendMessage(ChatColor.YELLOW + "Meslek: " + ChatColor.WHITE + villager.getProfession());
        sender.sendMessage(ChatColor.YELLOW + "Senkronizasyon: " + 
                          (syncEnabled ? ChatColor.GREEN + "Aktif" : ChatColor.RED + "Kapalı"));
        
        List<String[]> discounts = discountService.getStoredDiscounts(villager);
        
        if (discounts.isEmpty()) {
            sender.sendMessage(ChatColor.GRAY + "  Kayıtlı indirim yok.");
        } else {
            sender.sendMessage(ChatColor.YELLOW + "İndirimler:");
            for (String[] discount : discounts) {
                sender.sendMessage(ChatColor.GRAY + "  " + discount[0] + 
                                  ": " + ChatColor.AQUA + discount[1] + " zümrüt");
            }
        }
    }

    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String getDescription() {
        return "Senkronize indirim bilgisini göster";
    }
}
