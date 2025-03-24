package me.bedwarshurts.mmextension.mechanics.inventory;

import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.api.skills.INoTargetSkill;
import io.lumine.mythic.api.skills.SkillMetadata;
import io.lumine.mythic.api.skills.SkillResult;
import io.lumine.mythic.bukkit.BukkitAdapter;
import io.lumine.mythic.core.players.PlayerData;
import io.lumine.mythic.core.skills.variables.types.StringVariable;
import io.lumine.mythic.core.utils.annotations.MythicMechanic;
import me.bedwarshurts.mmextension.utils.ItemUtils;
import me.bedwarshurts.mmextension.utils.SkillUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

@MythicMechanic(author = "bedwarshurts", name = "hotbarsnapshot", aliases = {}, description = "Saves and replaces a player's hotbar for a duration")
public final class HotbarSnapshotMechanic implements INoTargetSkill {

    private final ItemStack[] replacementItems;
    private final int durationTicks;
    private final String itemsArg;

    public HotbarSnapshotMechanic(MythicLineConfig mlc) {
        this.itemsArg = mlc.getString("items", "air");
        this.replacementItems = new ItemStack[9];
        this.durationTicks = mlc.getInteger("duration", 60);
    }

    @Override
    public SkillResult cast(SkillMetadata data) {
        String[] items = itemsArg.split("],");
        for (int i = 0; i < 9; i++) {
            if (i < items.length) {
                if (!items[i].contains("[")) continue;
                ItemStack item = ItemUtils.buildItem(items[i]);
                if (item == null) {
                    replacementItems[i] = new ItemStack(Material.AIR);
                    continue;
                }
                ItemMeta meta = item.getItemMeta();
                meta.getPersistentDataContainer().set(
                        new NamespacedKey(JavaPlugin.getProvidingPlugin(getClass()), "caster"),
                        PersistentDataType.STRING,
                        data.getCaster().getEntity().getUniqueId().toString()
                );
                meta.getPersistentDataContainer().set(
                        new NamespacedKey(JavaPlugin.getProvidingPlugin(getClass()), "hotbarsnapshot"),
                        PersistentDataType.STRING,
                        "true"
                );
                replacementItems[i] = item;
            } else {
                replacementItems[i] = new ItemStack(Material.AIR);
            }
        }

        boolean success = false;
        for (AbstractEntity target : data.getEntityTargets()) {
            if (!target.isPlayer()) continue;
            success = true;

            Player player = BukkitAdapter.adapt(target.asPlayer());

            PlayerData mythicPlayer = SkillUtils.getMythicPlayer(player);
            if (mythicPlayer == null) continue;

            try {
                mythicPlayer.getVariables().put("originalHotbar", new StringVariable(InventorySerializer.toBase64(player.getInventory().getContents())));
            } catch (IOException ignored) {
            }

            for (int slot = 0; slot < 9; slot++) {
                player.getInventory().setItem(slot, replacementItems[slot]);
            }

            Bukkit.getScheduler().runTaskLater(JavaPlugin.getProvidingPlugin(getClass()), () -> {
                if (!player.isOnline()) return;
                RestoreHotbarMechanic.restoreHotbar(player);
            }, durationTicks);
        }
        return success ? SkillResult.SUCCESS : SkillResult.INVALID_TARGET;
    }
}