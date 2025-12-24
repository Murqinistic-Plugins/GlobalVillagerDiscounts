package dev.murqin.globaldiscounts.command;

import dev.murqin.globaldiscounts.command.subcommand.SubCommand;
import dev.murqin.globaldiscounts.lang.Messages;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Ana komut yönlendiricisi.
 * Basit komut yapısı - admin prefix yok.
 */
public class GvdCommandExecutor implements CommandExecutor, TabCompleter {

    private final Map<String, SubCommand> commands = new HashMap<>();
    private final Map<String, Boolean> adminOnly = new HashMap<>();

    /**
     * Komut kaydeder.
     * @param subCommand Komut
     * @param requiresAdmin Admin permission gerektiriyor mu
     */
    public void registerCommand(SubCommand subCommand, boolean requiresAdmin) {
        String name = subCommand.getName().toLowerCase();
        commands.put(name, subCommand);
        adminOnly.put(name, requiresAdmin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("gvd")) {
            return false;
        }

        if (args.length == 0) {
            sendHelp(sender);
            return true;
        }

        String subCommandName = args[0].toLowerCase();
        SubCommand subCommand = commands.get(subCommandName);

        if (subCommand == null) {
            sendHelp(sender);
            return true;
        }

        // Admin-only komut kontrolü
        if (adminOnly.getOrDefault(subCommandName, false) && !sender.hasPermission("gvd.admin")) {
            sender.sendMessage(Messages.NO_PERMISSION());
            return true;
        }

        // Alt argümanları ayarla
        String[] subArgs = new String[args.length - 1];
        System.arraycopy(args, 1, subArgs, 0, args.length - 1);
        
        subCommand.execute(sender, subArgs);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> completions = new ArrayList<>();
        
        if (args.length == 1) {
            String partial = args[0].toLowerCase();
            
            for (Map.Entry<String, SubCommand> entry : commands.entrySet()) {
                String name = entry.getKey();
                boolean isAdminOnly = adminOnly.getOrDefault(name, false);
                
                // Admin-only komutları sadece adminlere göster
                if (isAdminOnly && !sender.hasPermission("gvd.admin")) {
                    continue;
                }
                
                if (name.startsWith(partial)) {
                    completions.add(name);
                }
            }
        } else if (args.length == 2 && args[0].equalsIgnoreCase("share")) {
            String partial = args[1].toLowerCase();
            if ("on".startsWith(partial)) completions.add("on");
            if ("off".startsWith(partial)) completions.add("off");
        }
        
        return completions;
    }

    private void sendHelp(CommandSender sender) {
        sender.sendMessage(Messages.HELP_HEADER_PLAYER());
        
        boolean isAdmin = sender.hasPermission("gvd.admin");
        
        for (Map.Entry<String, SubCommand> entry : commands.entrySet()) {
            String name = entry.getKey();
            SubCommand cmd = entry.getValue();
            boolean isAdminOnly = adminOnly.getOrDefault(name, false);
            
            // Admin-only komutları sadece adminlere göster
            if (isAdminOnly && !isAdmin) {
                continue;
            }
            
            String prefix = isAdminOnly ? ChatColor.RED + "[Admin] " : "";
            sender.sendMessage(prefix + ChatColor.YELLOW + "/gvd " + name + " " + 
                              ChatColor.GRAY + "- " + cmd.getDescription());
        }
    }
}
