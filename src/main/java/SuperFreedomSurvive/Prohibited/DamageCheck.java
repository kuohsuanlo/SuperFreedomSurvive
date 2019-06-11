package SuperFreedomSurvive.Prohibited;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.Collection;
import java.util.Iterator;

final public class DamageCheck implements Listener {
    // 禁止沒有指向怪物卻造成傷害

    @EventHandler(priority = EventPriority.HIGHEST)
    final public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
        // 造成傷害

        // 是否為玩家造成這次傷害
        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();

            boolean allow = false;

            // 檢查玩家指向方向是否在位置內
            for (int d = 0; d < 3; d++) {
                // 環繞計算直線
                double radians = Math.toRadians(player.getLocation().getYaw() + 90);
                double pitch = Math.toRadians(player.getLocation().getPitch());
                // 計算位置
                Location location = new Location(
                        player.getLocation().getWorld(),
                        player.getLocation().getX() + d * Math.cos(radians) * Math.cos(pitch),
                        player.getLocation().getY() + d * Math.sin(pitch) * -1,
                        player.getLocation().getZ() + d * Math.sin(radians) * Math.cos(pitch)
                );
                // 取得怪物
                Collection collection = Bukkit.getWorld(player.getWorld().getName()).getNearbyEntities(
                        location,
                        2,
                        2,
                        2
                );
                Iterator iterator = collection.iterator(); // 清單
                while (iterator.hasNext()) {
                    // 取得實體
                    Entity entity = (Entity) iterator.next();
                    // 檢查是否與被攻擊的一致
                    if (event.getEntity().getUniqueId() == entity.getUniqueId()) {
                        allow = true;
                    }
                }
            }

            if (allow) {
                // 允許
            } else {
                // 禁止
                event.setCancelled(true);
            }
        }
    }
}
