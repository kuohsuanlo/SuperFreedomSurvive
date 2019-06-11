package SuperFreedomSurvive.Prohibited;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityInteractEvent;

final public class AutomationFishing implements Listener {

    // 禁止自動釣魚

    @EventHandler(priority = EventPriority.HIGHEST)
    final public void onEntityInteractEvent(EntityInteractEvent event) {
        //  容器交互
        try {
            if (event.getEntityType() == EntityType.FISHING_HOOK) {
                // 是釣魚竿

                event.setCancelled(true);

                /*
                if (event.getBlock().getType().name().matches(".*PRESSURE_PLATE.*")) {
                    // 壓力版
                    event.setCancelled(true);

                } else if (event.getBlock().getType() == Material.TRIPWIRE || event.getBlock().getType() == Material.TRIPWIRE_HOOK) {
                    // 絆線勾
                    event.setCancelled(true);

                }
                */
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}