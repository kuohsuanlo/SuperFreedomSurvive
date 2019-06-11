package SuperFreedomSurvive.Prohibited;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

final public class AnimalExcess {
    // 生物過量
    // 最多 40

    static final public void Start() {

        // 每 1 秒 檢查一次
        new BukkitRunnable() {
            @Override
            final public void run() {

                List<World> worlds = Bukkit.getWorlds();
                for (int i = 0, s = worlds.size(); i < s; ++i) {
                    // 逐一檢查
                    Chunk[] chunks = worlds.get(i).getLoadedChunks();
                    for (int c = 0, s_ = chunks.length; c < s_; ++c) {
                        Entity[] entities = chunks[c].getEntities();
                        ArrayList<LivingEntity> entitys = new ArrayList<LivingEntity>();

                        for (int e = 0, s__ = entities.length; e < s__; e++) {
                            if (entities[e] instanceof LivingEntity) {
                                if (entities[e] instanceof Player) {
                                } else if (entities[e] instanceof Item) {
                                } else if (entities[e] instanceof ArmorStand) {
                                } else if (entities[e] instanceof ItemFrame) {
                                } else if (entities[e] instanceof ExperienceOrb) {
                                } else if (entities[e] instanceof Arrow) {
                                } else if (entities[e] instanceof Projectile) {

                                } else {
                                    if (((LivingEntity) entities[e]).hasAI()) {
                                        entitys.add((LivingEntity) entities[e]);
                                    }
                                }
                            }
                        }
                        //if ( entitys.size() > 20 ) {
                        // 超過 40 隻 直接死亡
                        for (int e = entitys.size() - 1; e > 40; e--) {
                            if (entitys.get(e).isInvulnerable()) {
                                // 無法攻擊的 跳過
                            } else {
                                entitys.get(e).remove();
                                //entitys.get(e).setHealth(0);
                            }
                        }
                        //}
                    }
                }

            }
        }.runTaskTimer(SuperFreedomSurvive.SYSTEM.Plugin.get(), 0L, 20L);

    }
}
