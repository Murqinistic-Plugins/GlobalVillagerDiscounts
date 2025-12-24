package dev.murqin.globaldiscounts.command.subcommand;

import dev.murqin.globaldiscounts.lang.Messages;
import dev.murqin.globaldiscounts.service.DiscountService;
import dev.murqin.globaldiscounts.util.VillagerTargeter;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

import java.util.List;

/**
 * Köylü indirim bilgilerini gösteren komut.
 * Admin ise UUID gösterir, değilse göstermez.
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
            sender.sendMessage(Messages.PLAYER_ONLY());
            return;
        }

        Villager villager = targeter.getTargetVillager(player).orElse(null);
        if (villager == null) {
            sender.sendMessage(Messages.LOOK_AT_VILLAGER());
            return;
        }

        boolean syncEnabled = discountService.isSyncEnabled(villager);
        boolean isLocked = discountService.isLocked(villager);
        boolean isAdmin = player.hasPermission("gvd.admin");
        
        sender.sendMessage(Messages.INFO_HEADER());
        
        // UUID sadece adminlere gösterilir
        if (isAdmin) {
            sender.sendMessage(Messages.INFO_UUID() + villager.getUniqueId());
        }
        
        sender.sendMessage(Messages.INFO_PROFESSION() + formatProfession(villager));
        sender.sendMessage(Messages.INFO_SYNC_STATUS() + 
                          (syncEnabled ? Messages.INFO_SYNC_ACTIVE() : Messages.INFO_SYNC_DISABLED()));
        sender.sendMessage(Messages.INFO_LOCK_STATUS() + 
                          (isLocked ? Messages.INFO_LOCKED() : Messages.INFO_UNLOCKED()));
        
        List<String[]> discounts = discountService.getStoredDiscounts(villager);
        
        if (discounts.isEmpty()) {
            sender.sendMessage(Messages.INFO_NO_DISCOUNTS());
        } else {
            sender.sendMessage(Messages.INFO_DISCOUNTS_HEADER());
            for (String[] discount : discounts) {
                sender.sendMessage(ChatColor.GRAY + "  " + discount[0] + 
                                  ": " + ChatColor.AQUA + discount[1]);
            }
        }
    }
    
    private String formatProfession(Villager villager) {
        return villager.getProfession().name().toLowerCase().replace("_", " ");
    }

    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String getDescription() {
        return "Show villager discount info";
    }
}
