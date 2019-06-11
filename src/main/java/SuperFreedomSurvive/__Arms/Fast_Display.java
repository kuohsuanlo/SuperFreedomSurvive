package SuperFreedomSurvive.__Arms;

import org.bukkit.Bukkit;
import org.bukkit.Particle;

final public class Fast_Display {


    // 快速顯示 2D 平面圓形 ( 不含 Y 高度運算 )
    static final public void circle_2D(String world, int start, int radius, int intensive, double X, double Y, double Z, Particle particle, double extra, Particle.DustOptions dustOptions) {
        try {
            // 360度做運算
            for (int degree = 0 + start; degree < 360 + start; degree = degree + intensive) {
                double radians = Math.toRadians(degree);
                Bukkit.getWorld(world).spawnParticle(particle, X + (Math.cos(radians) / 2 * radius), Y, Z + (Math.sin(radians) / 2 * radius), 1, 0, 0, 0, extra, dustOptions);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
