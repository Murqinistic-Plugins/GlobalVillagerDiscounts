package dev.murqin.globaldiscounts.command.subcommand;

import dev.murqin.globaldiscounts.lang.Messages;
import dev.murqin.globaldiscounts.service.DiscountService;
import dev.murqin.globaldiscounts.util.VillagerTargeter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

/**
 * Köylüyü kilitleyen admin komutu.
 * Kilitli köylülerin share durumunu oyuncular değiştiremez.
 */
public class LockCommand implements SubCommand {

    private final DiscountService discountService;
    private final VillagerTargeter targeter;

    public LockCommand(DiscountService discountService, VillagerTargeter targeter) {
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

        if (discountService.isLocked(villager)) {
            sender.sendMessage(Messages.ALREADY_LOCKED());
            return;
        }

        discountService.lock(villager);
        sender.sendMessage(Messages.LOCK_SUCCESS());
        sender.sendMessage(Messages.INFO_PROFESSION() + formatProfession(villager));
    }
    
    private String formatProfession(Villager villager) {
        return villager.getProfession().name().toLowerCase().replace("_", " ");
    }

    @Override
    public String getName() {
        return "lock";
    }

    @Override
    public String getDescription() {
        return "Lock villager (players can't change share)";
    }
}
