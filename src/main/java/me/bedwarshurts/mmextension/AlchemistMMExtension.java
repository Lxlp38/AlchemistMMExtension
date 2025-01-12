package me.bedwarshurts.mmextension;

import me.bedwarshurts.mmextension.listeners.EntityDamageListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class AlchemistMMExtension extends JavaPlugin {

    public void onEnable() {
        if (Bukkit.getPluginManager().getPlugin("MythicMobs") == null) {
            getLogger().severe("MythicMobs is not installed! Disabling AlchemistMMExtension...");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        Bukkit.getPluginManager().registerEvents(new MythicMobsHook(), this);
        Bukkit.getPluginManager().registerEvents(new EntityDamageListener(), this);

        getLogger().info("AlchemistMMExtension has been enabled! Made by bedwarshurts");
    }

    public void onDisable() {
        getLogger().info("AlchemistMMExtension has been disabled!");
    }
}
