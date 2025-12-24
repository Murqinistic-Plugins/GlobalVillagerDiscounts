package dev.murqin.globaldiscounts.command.subcommand;

import dev.murqin.globaldiscounts.lang.Messages;
import dev.murqin.globaldiscounts.service.DiscountService;
import dev.murqin.globaldiscounts.util.VillagerTargeter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

/**
 * Köylü için indirim paylaşımını açıp kapatan komut.
 * Kilitli köylülerde sadece admin değiştirebilir.
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
            sender.sendMessage(Messages.PLAYER_ONLY());
            return;
        }

        if (args.length == 0) {
            sender.sendMessage(Messages.SHARE_USAGE());
            return;
        }

        String action = args[0].toLowerCase();
        if (!action.equals("on") && !action.equals("off")) {
            sender.sendMessage(Messages.SHARE_USAGE());
            return;
        }

        Villager villager = targeter.getTargetVillager(player).orElse(null);
        if (villager == null) {
            sender.sendMessage(Messages.LOOK_AT_VILLAGER());
            return;
        }

        // Kilitli köylü kontrolü - admin değilse değiştiremez
        if (discountService.isLocked(villager) && !player.hasPermission("gvd.admin")) {
            sender.sendMessage(Messages.VILLAGER_LOCKED());
            return;
        }

        if (action.equals("on")) {
            discountService.enableSync(villager);
            sender.sendMessage(Messages.SHARE_ENABLED_HEADER());
            sender.sendMessage(Messages.INFO_PROFESSION() + formatProfession(villager));
        } else {
            int cleared = discountService.clearDiscounts(villager);
            discountService.disableSync(villager);
            sender.sendMessage(Messages.SHARE_DISABLED_HEADER());
            sender.sendMessage(Messages.INFO_PROFESSION() + formatProfession(villager));
            sender.sendMessage(Messages.SHARE_CLEARED() + cleared);
        }
    }
    
    private String formatProfession(Villager villager) {
        return villager.getProfession().name().toLowerCase().replace("_", " ");
    }

    @Override
    public String getName() {
        return "share";
    }

    @Override
    public String getDescription() {
        return "Toggle discount sharing (on|off)";
    }
}
