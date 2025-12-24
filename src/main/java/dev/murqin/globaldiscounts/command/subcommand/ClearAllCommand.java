package dev.murqin.globaldiscounts.command.subcommand;

import dev.murqin.globaldiscounts.lang.Messages;
import dev.murqin.globaldiscounts.service.DiscountService;
import org.bukkit.command.CommandSender;

/**
 * Tüm köylülerin indirimlerini temizleyen admin komutu.
 */
public class ClearAllCommand implements SubCommand {

    private final DiscountService discountService;

    public ClearAllCommand(DiscountService discountService) {
        this.discountService = discountService;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        int[] result = discountService.clearAllDiscounts();
        int villagersCleared = result[0];
        int discountsCleared = result[1];
        
        sender.sendMessage(Messages.CLEARALL_RESULT(villagersCleared, discountsCleared));
    }

    @Override
    public String getName() {
        return "clearall";
    }

    @Override
    public String getDescription() {
        return "Clear ALL discounts from ALL villagers";
    }
}
