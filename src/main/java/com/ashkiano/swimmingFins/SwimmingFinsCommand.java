package com.ashkiano.swimmingFins;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class SwimmingFinsCommand implements CommandExecutor {
    public static String SWIMMING_FINS_LORE;

    private final JavaPlugin plugin;

    private final String swimmingFinsUsePermission;

    public SwimmingFinsCommand(String permission, JavaPlugin plugin) {
        this.swimmingFinsUsePermission = permission;
        this.plugin = plugin;
        SWIMMING_FINS_LORE = ChatColor.GRAY + plugin.getConfig().getString("swimmingFins-lore", "Enjoy each pond!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by a player!");
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission(swimmingFinsUsePermission)) {
            player.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
            return true;
        }

        String swimmingFinsName = plugin.getConfig().getString("swimmingFins-name", "Swimming fins");
        ItemStack swimmingFins = new ItemStack(Material.LEATHER_BOOTS);
        LeatherArmorMeta meta = (LeatherArmorMeta) swimmingFins.getItemMeta();
        meta.setColor(Color.BLUE);
        meta.setLore(Arrays.asList(SWIMMING_FINS_LORE));
        meta.setDisplayName(swimmingFinsName);
        swimmingFins.setItemMeta(meta);

        player.getInventory().addItem(swimmingFins);

        return true;
    }
}
