package SuperFreedomSurvive.Function;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;

final public class RewriteRstore implements Listener {
    // 恢復效果改寫


    // 恢復生命
    @EventHandler(priority = EventPriority.HIGHEST)
    final public void onEntityRegainHealthEvent(EntityRegainHealthEvent event) {
        if (event.isCancelled()) {
            // 被取消

        } else {
            // 沒有被取消
            if (event.getEntity() instanceof LivingEntity) {
                LivingEntity entity = (LivingEntity) event.getEntity();

                double max_hp = entity.getMaxHealth();

                if (event.getRegainReason() == EntityRegainHealthEvent.RegainReason.ENDER_CRYSTAL) {
                    // 終界龍受到水晶回復
                    event.setAmount(max_hp);

                } else if (event.getRegainReason() == EntityRegainHealthEvent.RegainReason.EATING) {
                    // 當透過食物恢復時
                    event.setAmount(event.getAmount() * 4);

                } else if (event.getRegainReason() == EntityRegainHealthEvent.RegainReason.MAGIC) {
                    // 當透過魔法恢復時
                    event.setAmount(event.getAmount() * 4);

                } else if (event.getRegainReason() == EntityRegainHealthEvent.RegainReason.MAGIC_REGEN) {
                    // 當透過藥水恢復時
                    event.setAmount(event.getAmount() * 4);

                }
            }
        }
    }

}
