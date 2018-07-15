package net.bigfootslair.MoneyCrate.main;

import net.bigfootslair.MoneyCrate.commands.CommandGiveCrate;
import net.bigfootslair.MoneyCrate.commands.CommandGiveCrateAll;
import net.bigfootslair.MoneyCrate.listeners.InteractListener;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    public static String[] ROMAN_NUMERALS = {"0", "I", "II", "III", "IV", "V"};

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(new InteractListener(), this);
        this.getCommand("givemoneycrate").setExecutor(new CommandGiveCrate());
        this.getCommand("givemoneycrateall").setExecutor(new CommandGiveCrateAll());
    }

    public static String Colorize(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }
}
