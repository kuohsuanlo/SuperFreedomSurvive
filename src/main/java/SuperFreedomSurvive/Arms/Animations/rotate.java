package SuperFreedomSurvive.Arms.Animations;

import org.bukkit.Location;
import org.bukkit.Particle;

final public class rotate {
    // 旋轉特效

    static final public void Display(Location location, Particle particle, int size, int density, int time, int all_time, Particle.DustOptions dust_options) {
        // 顯示旋轉動畫
        for (int i = 0; i < size * 4; ++i) {
            // 360度做運算
            for (int degree = 0 + (time * 12) + (i * 40); degree <= 360 + (time * 12) + (i * 40); degree = degree + density) {
                double radians = Math.toRadians(degree);
                location.getWorld().spawnParticle(particle, location.getX() + (Math.cos(radians) * size), location.getY() + ((double) (i) / 6), location.getZ() + (Math.sin(radians) * size), 1, 0, 0, 0, 0, dust_options);

            }
        }
    }


}
