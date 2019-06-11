package SuperFreedomSurvive.__Arms.Use;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Iterator;

final public class Damage {
    // 傷害計算
    /*
    如果是玩家的話 檢查PVP是否開啟
    如果是實體 不能是
        物品展示框
        盔甲座
        礦車
        船

     */
    static final public void New(Player player, double damage, World world, Location location, int range) {
        // 製造傷害
        // 取得位置的所有實體
        Collection collection = Bukkit.getWorld(player.getWorld().getName()).getNearbyEntities(
                location,
                range,
                range,
                range
        );
        Iterator iterator = collection.iterator();
        // 總數

        while (iterator.hasNext()) {
            Entity entity = (Entity) iterator.next();
            // 檢查實體類型
            if (entity instanceof LivingEntity) {
                LivingEntity living_entity = (LivingEntity) entity;


                if (living_entity.getType() == EntityType.PLAYER) {
                    // 是玩家 地圖是否有PVP
                    if (world.getPVP()) {
                        // 有
                        // 造成傷害
                        living_entity.damage(damage);
                        Display(player, damage);

                    } else {
                        // 沒有 掠過
                    }

                } else if (
                        living_entity.getType().name().matches(".*MINECART.*") ||
                                living_entity.getType() == EntityType.BOAT ||
                                living_entity.getType().name().matches(".*ARMOR_STAND.*") ||
                                living_entity.getType() == EntityType.ITEM_FRAME
                ) {
                    // 掠過

                } else {
                    // 檢查是否擁有權限
                    // 檢查是否有權限
                    if (SuperFreedomSurvive.Land.Permissions.Inspection(player, living_entity.getLocation(), "Permissions_" + "EntityDamageByEntity")) {
                        // 有
                        // 造成傷害
                        living_entity.damage(damage);
                        Display(player, damage);

                    } else {
                        // 沒有
                        // 掠過
                        SuperFreedomSurvive.Land.Display.Message(player, "§c領地禁止 " + "傷害實體");
                    }
                }
            }

        }


    }


    static final public void Display(Player player, double damage) {
        // 顯示造成的傷害
        player.sendTitle(
                "§c- " + damage + " HP",
                "",
                0,
                10,
                2
        );

    }

}
