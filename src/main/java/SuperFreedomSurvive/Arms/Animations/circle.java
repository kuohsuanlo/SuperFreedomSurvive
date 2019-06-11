package SuperFreedomSurvive.Arms.Animations;

import org.bukkit.Location;
import org.bukkit.Particle;

final public class circle {
    // 圓圈特效


    static final public void Display(Location location, Particle particle, int size, int density, int time, int all_time, Particle.DustOptions dust_options) {
        // 360度做運算
        for (int degree = 0; degree < 360; degree = degree + density) {
            double radians = Math.toRadians(degree);
            location.getWorld().spawnParticle(particle, location.getX() + (Math.cos(radians) * size), location.getY(), location.getZ() + (Math.sin(radians) * size), 1, 0, 0, 0, 0, dust_options);

        }
    }
}
