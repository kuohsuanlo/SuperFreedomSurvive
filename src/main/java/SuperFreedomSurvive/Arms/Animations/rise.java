package SuperFreedomSurvive.Arms.Animations;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.scheduler.BukkitRunnable;

final public class rise {
    // 上升圓圈特效

    static final public void Display(Location location, Particle particle, int size, int density, int time, int all_time, Particle.DustOptions dust_options) {
        // 顯示上升圓圈
        new BukkitRunnable() {
            double _time = 1; // 最初時間
            int _all_time = size * 10; // 最後時間

            @Override
            final public void run() {
                if (time % (size * 10 * 2) == 0) {
                    if (_time > _all_time) {
                        cancel(); // 結束
                        return;
                    }
                    // 360度做運算
                    for (int degree = 0 + (int) (Math.random() * density); degree < 360; degree = degree + (int) (Math.random() * density)) {
                        double radians = Math.toRadians(degree);
                        location.getWorld().spawnParticle(particle, location.getX() + (Math.cos(radians) * size), location.getY() + (_time / 5), location.getZ() + (Math.sin(radians) * size), 1, 0, 0, 0, 0, dust_options);
                    }
                    _time = _time + 1;
                } else {
                    cancel(); // 結束
                    return;
                }
            }
        }.runTaskTimer(SuperFreedomSurvive.SYSTEM.Plugin.get(), 0L, 1L);
    }
}
