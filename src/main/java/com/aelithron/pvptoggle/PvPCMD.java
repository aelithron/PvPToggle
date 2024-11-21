package com.aelithron.pvptoggle;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PvPCMD implements CommandExecutor {
    private PvPToggle plugin;
    public PvPCMD(PvPToggle plugin) {
        this.plugin = plugin;
    }
    String prefix = CoreTools.getInstance().getPrefix();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String name, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(prefix + ChatColor.RED + "You must be a player to use this command.");
            return false;
        }
        Player player = (Player) sender;
        if (args.length == 1 && (args[0].equalsIgnoreCase("t") || args[0].equalsIgnoreCase("toggle"))) {
            if (CombatTimerManager.getInstance().checkStatus(player)) {
                player.sendMessage(prefix + ChatColor.RED + "You cannot toggle PvP while in combat!");
                return false;
            }
            ToggleManager.getInstance().togglePvP(player);
            player.sendMessage(prefix + ChatColor.GREEN + "Your PvP status is now " + ChatColor.BOLD + (ToggleManager.getInstance().checkStatus(player) ? "on" : "off") + ChatColor.GREEN + ".");
            return true;
        }
        if (args.length == 2 && (args[0].equalsIgnoreCase("l") || args[0].equalsIgnoreCase("list"))) {
            sender.sendMessage(prefix + ChatColor.DARK_AQUA + "PvP Status List");
            for (Player players : plugin.getServer().getOnlinePlayers()) {
                boolean status = ToggleManager.getInstance().checkStatus(player);
                if (status) {
                    sender.sendMessage(ChatColor.GREEN + player.getName() + " • Enabled");
                } else {
                    sender.sendMessage(ChatColor.RED + player.getName() + " • Disabled");
                }
            }
            return true;
        }
        player.sendMessage(prefix + ChatColor.GREEN + "Your PvP status is " + ChatColor.BOLD + (ToggleManager.getInstance().checkStatus(player) ? "on" : "off") + ChatColor.GREEN + ".");
        player.sendMessage(ChatColor.AQUA + "Use '/pvp t' to toggle your PvP status.");
        player.sendMessage(ChatColor.AQUA + "Use '/pvp l' to show PvP status for all players.");
        return true;
    }
}
