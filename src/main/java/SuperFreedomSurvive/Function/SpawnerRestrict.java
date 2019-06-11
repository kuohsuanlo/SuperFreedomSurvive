package SuperFreedomSurvive.Function;

import org.bukkit.Material;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

// 生怪磚生出的動物不得收納 且 掉落物不會有肉類 福利則是 繁殖動物獲得經驗
final public class SpawnerRestrict implements Listener {


    // 召喚生物時
    @EventHandler
    final public void onCreatureSpawnEvent(CreatureSpawnEvent event) {
        if (event.isCancelled()) {

        } else {
            try {
                if (event.getEntity() == null) { return; }

                // 沒有 檢查
                if (event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.BREEDING) {
                    // 因為繁殖 給予額外經驗值
                    Entity exp = event.getEntity().getWorld().spawnEntity(event.getEntity().getLocation(), EntityType.EXPERIENCE_ORB);
                    ((ExperienceOrb) exp).setExperience(29 + (int) (Math.random() * 51));

                } else if (event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.SPAWNER) {
                    // 因為生怪磚
                    // 加上標籤 無法生長
                    if (event.getEntityType().isAlive()) {
                        if ( event.getEntity() instanceof Ageable ) {
                            Ageable ageable = ((Ageable) event.getEntity());
                            ageable.setAgeLock(true);
                        }
                    }
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    // 點擊實體時
    @EventHandler
    final public void onPlayerInteractEntityEvent(PlayerInteractEntityEvent event) {
        if (event.isCancelled()) {

        } else {
            try {
                if (event.getRightClicked() == null) { return; }

                // 無法生長 標籤是否 true
                if (event.getRightClicked().getType().isAlive()) {
                    if ( event.getRightClicked() instanceof Ageable ) {
                        if (((Ageable) event.getRightClicked()).getAgeLock()) {
                            event.setCancelled(true);
                        }
                    }
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    // 生物死亡時
    @EventHandler
    final public void onEntityDeathEvent(EntityDeathEvent event) {
        if (event.isCancelled()) {

        } else {

            try {
                if (event.getEntity() == null) { return; }

                // 無法生長 標籤是否 true
                if (event.getEntityType().isAlive()) {

                    if ( event.getEntity() instanceof Ageable ) {
                        if (((Ageable) event.getEntity()).getAgeLock()) {
                            //event.setCancelled(true);
                            event.setDroppedExp(0);
                            List<ItemStack> list = event.getDrops();
                            for (int i = list.size() - 1; i >= 0; --i) {
                                switch (list.get(i).getType()) {
                                    case PORKCHOP:
                                        list.remove(i);
                                        list.add(new ItemStack(Material.COOKIE));
                                        break;
                                    case BEEF:
                                        list.remove(i);
                                        break;
                                    case CHICKEN:
                                        list.remove(i);
                                        break;
                                    case RABBIT:
                                    case RABBIT_FOOT:
                                        list.remove(i);
                                        break;
                                    case MUTTON:
                                        list.remove(i);
                                        break;
                                }
                            }
                        }
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}
