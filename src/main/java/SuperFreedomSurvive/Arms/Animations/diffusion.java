package SuperFreedomSurvive.Arms.Animations;

import org.bukkit.Location;
import org.bukkit.Particle;

final public class diffusion {
    // 爆炸特效類
    // 擴散特效

    static final public void Display_3D(Location location, Particle particle, int size, int density, int time, int all_time, Particle.DustOptions dust_options) {
        // 爆炸動畫
        // 3D做運算
        for (int degree = 0; degree < 360; degree = degree + density) {
            for (int pitch = 0; pitch < 360; pitch = pitch + density) {
                double radians = Math.toRadians(degree);
                location.getWorld().spawnParticle(particle, location.getX() + (Math.cos(radians) * Math.cos(pitch) * size * ((double) time / all_time)), location.getY() + (Math.sin(pitch) * size * ((double) time / all_time)), location.getZ() + (Math.sin(radians) * Math.cos(pitch) * size * ((double) time / all_time)), 1, 0, 0, 0, 0, dust_options);

            }
        }
    }

    static final public void Display_2D(Location location, Particle particle, int size, int density, int time, int all_time, Particle.DustOptions dust_options) {
        // 爆炸動畫
        // 2D做運算
        for (int degree = 0; degree < 360; degree = degree + density) {
            double radians = Math.toRadians(degree);
            location.getWorld().spawnParticle(particle, location.getX() + (Math.cos(radians) * size * ((double) time / all_time)), location.getY(), location.getZ() + (Math.sin(radians) * size * ((double) time / all_time)), 1, 0, 0, 0, 0, dust_options);

        }
    }
}
