package SuperFreedomSurvive.Monster;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class Event implements Listener {

    // 生物死亡時
    @EventHandler(ignoreCancelled = true)
    final public void onEntityDeathEvent(EntityDeathEvent event) {
        if ( event.getEntity().getScoreboardTags().contains("BanDrop") ) {
            event.getDrops().clear();
            event.setDroppedExp(0);
        }
    }
}