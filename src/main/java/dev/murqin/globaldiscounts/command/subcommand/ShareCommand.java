package dev.murqin.globaldiscounts.command.subcommand;

import dev.murqin.globaldiscounts.service.DiscountService;
import dev.murqin.globaldiscounts.util.VillagerTargeter;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

/**
 * Oyuncunun köylü için indirim paylaşımını açıp kapatan komut.
 * Kullanım: /gvd share on|off
 */
public class ShareCommand implements SubCommand {

    private final DiscountService discountService;
    private final VillagerTargeter targeter;

    public ShareCommand(DiscountService discountService, VillagerTargeter targeter) {
        this.discountService = discountService;
        this.targeter = targeter;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.RED + "Bu komut bir oyuncu tarafından kullanılmalıdır.");
            return;
        }

        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Kullanım: /gvd share <on|off>");
            return;
        }

        String action = args[0].toLowerCase();
        if (!action.equals("on") && !action.equals("off")) {
            sender.sendMessage(ChatColor.RED + "Kullanım: /gvd share <on|off>");
            return;
        }

        Villager villager = targeter.getTargetVillager(player).orElse(null);
        if (villager == null) {
            sender.sendMessage(ChatColor.RED + "Bu komutu kullanmak için bir köylüye bakın.");
            return;
        }

        if (action.equals("on")) {
            discountService.enableSync(villager);
            sender.sendMessage(ChatColor.GREEN + "=== Paylaşım Açıldı ===");
            sender.sendMessage(ChatColor.GRAY + "Meslek: " + ChatColor.WHITE + villager.getProfession());
        } else {
            int cleared = discountService.clearDiscounts(villager);
            discountService.disableSync(villager);
            sender.sendMessage(ChatColor.YELLOW + "=== Paylaşım Kapatıldı ===");
            sender.sendMessage(ChatColor.GRAY + "Meslek: " + ChatColor.WHITE + villager.getProfession());
            sender.sendMessage(ChatColor.GRAY + "Temizlenen indirim: " + ChatColor.WHITE + cleared);
        }
    }

    @Override
    public String getName() {
        return "share";
    }

    @Override
    public String getDescription() {
        return "Köylü için indirim paylaşımını aç/kapat (on|off)";
    }
}
