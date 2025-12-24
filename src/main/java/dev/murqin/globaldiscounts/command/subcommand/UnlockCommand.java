package dev.murqin.globaldiscounts.command.subcommand;

import dev.murqin.globaldiscounts.lang.Messages;
import dev.murqin.globaldiscounts.service.DiscountService;
import dev.murqin.globaldiscounts.util.VillagerTargeter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

/**
 * Köylünün kilidini açan admin komutu.
 */
public class UnlockCommand implements SubCommand {

    private final DiscountService discountService;
    private final VillagerTargeter targeter;

    public UnlockCommand(DiscountService discountService, VillagerTargeter targeter) {
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

        if (!discountService.isLocked(villager)) {
            sender.sendMessage(Messages.NOT_LOCKED());
            return;
        }

        discountService.unlock(villager);
        sender.sendMessage(Messages.UNLOCK_SUCCESS());
        sender.sendMessage(Messages.INFO_PROFESSION() + formatProfession(villager));
    }
    
    private String formatProfession(Villager villager) {
        return villager.getProfession().name().toLowerCase().replace("_", " ");
    }

    @Override
    public String getName() {
        return "unlock";
    }

    @Override
    public String getDescription() {
        return "Unlock villager";
    }
}
