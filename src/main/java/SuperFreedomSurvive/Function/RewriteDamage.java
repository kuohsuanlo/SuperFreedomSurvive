package SuperFreedomSurvive.Function;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityPotionEffectEvent;

final public class RewriteDamage implements Listener {
    // 傷害效果改寫

    @EventHandler(priority = EventPriority.HIGHEST)
    final public void onEntityPotionEffectEvent(EntityPotionEffectEvent event) {
        // 當獲得藥水效果
        if (event.isCancelled()) {
            // 被取消

        } else {
            // 沒有被取消
            if (event.getEntity() instanceof Player) {
                Player player = (Player) event.getEntity();


            }
        }
    }

    // 受到傷害
    @EventHandler(priority = EventPriority.HIGHEST)
    final public void onEntityDamageEvent(EntityDamageEvent event) {
        if (event.isCancelled()) {
            // 被取消

        } else {
            // 沒有被取消
            if (event.getEntity() instanceof LivingEntity) {
                LivingEntity entity = (LivingEntity) event.getEntity();
                /*
                double max_hp = entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
                // 要換算藥水效果
                if ( entity.getPotionEffect(PotionEffectType.HEALTH_BOOST) != null ) {
                    max_hp = (double)max_hp + ((1 + entity.getPotionEffect(PotionEffectType.HEALTH_BOOST).getAmplifier()) * 4);
                }
                */
                double max_hp = entity.getMaxHealth();

                if (event.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) {
                    // 方塊爆炸
                    event.setDamage((max_hp * 0.01) * (event.getDamage() * 2));

                } else if (event.getCause() == EntityDamageEvent.DamageCause.CONTACT) {
                    // 仙人掌
                    event.setDamage(max_hp * 0.01 + 3);

                } else if (event.getCause() == EntityDamageEvent.DamageCause.CRAMMING) {
                    // 超量窒息
                    event.setDamage(max_hp * 0.2);

                } else if (event.getCause() == EntityDamageEvent.DamageCause.DRAGON_BREATH) {
                    // 龍呼吸
                    event.setDamage(max_hp * 0.12 + 14);

                } else if (event.getCause() == EntityDamageEvent.DamageCause.DROWNING) {
                    // 潛水窒息
                    event.setDamage(max_hp * 0.05 + 4);

                } else if (event.getCause() == EntityDamageEvent.DamageCause.DRYOUT) {
                    // 當應該在水中的實體卻不是
                    event.setDamage(max_hp * 0.2);

                } else if (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {
                    // 實體爆炸
                    event.setDamage((max_hp * 0.01) * (event.getDamage() * 2));

                } else if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
                    // 摔落
                    event.setDamage((event.getDamage() * event.getDamage()) * 0.25);

                } else if (event.getCause() == EntityDamageEvent.DamageCause.FIRE) {
                    // 火
                    event.setDamage(max_hp * 0.02 + 6);

                } else if (event.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK) {
                    // 火燒傷
                    event.setDamage(max_hp * 0.01 + 3);

                } else if (event.getCause() == EntityDamageEvent.DamageCause.FLY_INTO_WALL) {
                    // 卡牆壁
                    event.setDamage(max_hp * 0.02 + 5);

                } else if (event.getCause() == EntityDamageEvent.DamageCause.HOT_FLOOR) {
                    // 熔岩方塊
                    event.setDamage(max_hp * 0.03 + 5);

                } else if (event.getCause() == EntityDamageEvent.DamageCause.LAVA) {
                    // 熔岩
                    event.setDamage(max_hp * 0.04 + 8);

                } else if (event.getCause() == EntityDamageEvent.DamageCause.LIGHTNING) {
                    // 雷
                    event.setDamage(max_hp * 0.04 + 4);

                } else if (event.getCause() == EntityDamageEvent.DamageCause.MAGIC) {
                    // 傷害藥水等
                    event.setDamage((max_hp * 0.01) * (event.getDamage() * 2));

                } else if (event.getCause() == EntityDamageEvent.DamageCause.MELTING) {
                    // 融化
                    event.setDamage(max_hp * 0.3);

                } else if (event.getCause() == EntityDamageEvent.DamageCause.POISON) {
                    // 毒
                    if (max_hp - (max_hp * 0.02 + 3) <= 1) {
                        event.setDamage(0);
                        event.setCancelled(true);
                    } else {
                        event.setDamage(max_hp * 0.02 + 3);
                    }

                } else if (event.getCause() == EntityDamageEvent.DamageCause.PROJECTILE) {
                    // 投射物攻擊
                    event.setDamage(((max_hp * 0.005) * (event.getDamage() * 16)));

                } else if (event.getCause() == EntityDamageEvent.DamageCause.STARVATION) {
                    // 飢餓
                    event.setDamage(max_hp * 0.02 + 4);

                } else if (event.getCause() == EntityDamageEvent.DamageCause.SUFFOCATION) {
                    // 方塊窒息
                    event.setDamage(max_hp * 0.04 + 4);

                } else if (event.getCause() == EntityDamageEvent.DamageCause.THORNS) {
                    // 荊棘附魔
                    event.setDamage((event.getDamage() * event.getDamage()) * 0.25);

                } else if (event.getCause() == EntityDamageEvent.DamageCause.VOID) {
                    // 虛空
                    event.setDamage(max_hp * 1);

                } else if (event.getCause() == EntityDamageEvent.DamageCause.WITHER) {
                    // 凋零
                    event.setDamage(max_hp * 0.03 + 8);

                }
            }
        }
    }


}
