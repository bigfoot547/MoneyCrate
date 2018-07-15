package net.bigfootslair.MoneyCrate.listeners;

import net.bigfootslair.MoneyCrate.main.Main;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.ThreadLocalRandom;

public class InteractListener implements Listener {
    @EventHandler
    public void onPlaceChest(PlayerInteractEvent evt) {
        if (evt.getItem() == null) {
            return;
        }

        if ((evt.getAction() == Action.RIGHT_CLICK_BLOCK || evt.getAction() == Action.RIGHT_CLICK_AIR) && evt.getItem().getType() == Material.DRAGON_EGG) {
            net.minecraft.server.v1_8_R3.ItemStack stack = CraftItemStack.asNMSCopy(evt.getItem());
            NBTTagCompound comp = stack.getTag();
            if (!comp.hasKey("CrateTier")) {
                return;
            }

            if (comp.hasKeyOfType("CrateTier", 3)) { // If CrateTier is an int...
                int tier = comp.getInt("CrateTier");
                Player p = evt.getPlayer();
                if (tier < 0 || tier > 5) {
                    evt.setCancelled(true);
                    return;
                }

                int amount;
                switch (tier) {
                    case 1:
                        amount = ThreadLocalRandom.current().nextInt(1000, 10001);
                        break;
                    case 2:
                        amount = ThreadLocalRandom.current().nextInt(10001, 50001);
                        break;
                    case 3:
                        amount = ThreadLocalRandom.current().nextInt(50001, 200001);
                        break;
                    case 4:
                        amount = ThreadLocalRandom.current().nextInt(200001, 500001);
                        break;
                    case 5:
                        amount = ThreadLocalRandom.current().nextInt(500001, 2000001);
                        break;
                    default:
                        return;
                }
                p.sendMessage(Main.Colorize("&2&l(!) &aYou just opened a tier " + Main.ROMAN_NUMERALS[tier] + " Money Crate!"));

                ItemStack newItem = evt.getItem();
                newItem.setAmount(newItem.getAmount() - 1);
                if (newItem.getAmount() <= 0) {
                    p.getInventory().clear(p.getInventory().getHeldItemSlot());
                } else {
                    p.getInventory().setItem(p.getInventory().getHeldItemSlot(), newItem);
                }

                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "eco give " + p.getName() + " " + Integer.toString(amount));
                p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);

                evt.setCancelled(true);
            }
        }
    }
}
