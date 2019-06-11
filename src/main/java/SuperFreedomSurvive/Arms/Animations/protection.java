package SuperFreedomSurvive.Arms.Animations;

import org.bukkit.Location;
import org.bukkit.Particle;

final public class protection {
    // 保護罩特效類
    // 圓球狀顯示

    static final public void Display(Location location, Particle particle, int size, int density, int time, int all_time, Particle.DustOptions dust_options) {
        // 顯示保護罩動畫
        // 360度做運算
        for (int degree = 0; degree < 360; degree = degree + density) {
            for (int pitch = 0; pitch < 360; pitch = pitch + density) {
                double radians = Math.toRadians(degree);
                location.getWorld().spawnParticle(particle, location.getX() + (Math.cos(radians) * Math.cos(pitch) * size), location.getY() + (Math.sin(pitch) * size) + 1, location.getZ() + (Math.sin(radians) * Math.cos(pitch) * size), 1, 0, 0, 0, 0, dust_options);

            }
        }
    }
}
