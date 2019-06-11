package SuperFreedomSurvive.Function;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.inventory.ItemStack;

final public class AnimalWelfare implements Listener {
    // 動物福利

    // 剪羊毛會額外掉落 2~8 個
    @EventHandler(priority = EventPriority.LOW)
    final public void onPlayerShearEntityEvent(PlayerShearEntityEvent event) {
        if (event.isCancelled()) {

        } else {
            // 檢查是否有權限
            if (SuperFreedomSurvive.Land.Permissions.Inspection(event.getPlayer(), event.getEntity().getLocation(), "Permissions_PlayerShearEntity")) {
                // 有

                event.getEntity().getLocation().getWorld().dropItem(
                        event.getEntity().getLocation(),
                        new ItemStack(Material.WHITE_WOOL, 1 + (int) (Math.random() * 6))
                );
            }
        }
    }
}
