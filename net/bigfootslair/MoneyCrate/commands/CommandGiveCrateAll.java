package net.bigfootslair.MoneyCrate.commands;

import net.bigfootslair.MoneyCrate.main.Main;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class CommandGiveCrateAll implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("moneycrate.givecrateall")) {
            if (args.length == 2) {
                int tier, amount;
                tier = 1;
                amount = 1;
                try {
                    tier = Integer.parseInt(args[0]);
                    amount = Integer.parseInt(args[1]);
                } catch (NumberFormatException ex) {
                    sender.sendMessage(ChatColor.RED + "You must specify a valid tier and amount.");
                }

                if (amount > 64) {
                    amount = 64;
                }

                if (tier > 5) {
                    tier = 5;
                }

                List<String> crateLore;

                switch (tier) {
                    case 1:
                        crateLore = Arrays.asList(Main.Colorize("&7Yield: &2$1,000&7-&2$10,000&7"));
                        break;
                    case 2:
                        crateLore = Arrays.asList(Main.Colorize("&7Yield: &2$10,000&7-&2$50,000&7"));
                        break;
                    case 3:
                        crateLore = Arrays.asList(Main.Colorize("&7Yield: &2$50,000&7-&2$200,000&7"));
                        break;
                    case 4:
                        crateLore = Arrays.asList(Main.Colorize("&7Yield: &2$200,000&7-&2$500,000&7"));
                        break;
                    case 5:
                        crateLore = Arrays.asList(Main.Colorize("&7Yield: &2$500,000&7-&2$2,000,000&7"));
                        break;
                    default:
                        crateLore = Arrays.asList("If you see this message, please report it to figboot.");
                }

                ItemStack crate;
                ItemMeta crateMeta = (crate = new ItemStack(Material.DRAGON_EGG, amount)).getItemMeta();

                crateMeta.setDisplayName(Main.Colorize("&5&lTier " + Main.ROMAN_NUMERALS[tier] + " Ender Money Pouch"));
                crateMeta.setLore(crateLore);
                crate.setItemMeta(crateMeta);

                for (Player target : Bukkit.getOnlinePlayers()) {
                    net.minecraft.server.v1_8_R3.ItemStack stack = CraftItemStack.asNMSCopy(crate);
                    NBTTagCompound tag = stack.getTag();
                    tag.setInt("CrateTier", tier);
                    stack.setTag(tag);


                    if (!target.getInventory().addItem(CraftItemStack.asBukkitCopy(stack)).isEmpty()) {
                        target.getWorld().dropItem(target.getLocation(), CraftItemStack.asBukkitCopy(stack));
                    }
                }
            } else {
                sender.sendMessage(ChatColor.RED + "Usage: /givemoneycrateall <tier> <amount>");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "You don't have permission to execute that command.");
        }
        return true;
    }
}
