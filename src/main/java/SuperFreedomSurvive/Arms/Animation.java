package SuperFreedomSurvive.Arms;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import SuperFreedomSurvive.Arms.Animations.*;
import org.bukkit.scheduler.BukkitRunnable;

final public class Animation {
    // 顯示動畫街口


    // 跟隨顯示 座標 效果 密度 速度
    static final public void Follow(String type, Entity entity, Particle particle, int size, int density, int speed, Particle.DustOptions dust_options) {
        // 每 tps 讀取一次
        new BukkitRunnable() {

            int time = 0; // 最初時間
            int all_time = speed; // 最後時間

            @Override
            final public void run() {
                if (time > all_time) {
                    cancel(); // 結束
                    return;
                }

                distribution(type, entity.getLocation(), particle, size, density, time, all_time, dust_options); // 分發動畫

                time = time + 1;

            }
        }.runTaskTimer(SuperFreedomSurvive.SYSTEM.Plugin.get(), 0L, 1L);
    }


    // 固定顯示 座標 效果 密度 速度
    static final public void Fixed(String type, Location location, Particle particle, int size, int density, int speed, Particle.DustOptions dust_options) {
        // 每 tps 讀取一次
        new BukkitRunnable() {

            int time = 0; // 最初時間
            int all_time = speed; // 最後時間

            @Override
            final public void run() {
                if (time > all_time) {
                    cancel(); // 結束
                    return;
                }

                distribution(type, location, particle, size, density, time, all_time, dust_options); // 分發動畫

                ++time;

            }
        }.runTaskTimer(SuperFreedomSurvive.SYSTEM.Plugin.get(), 0L, 1L);
    }


    // 進行類型判斷 並且分發給特效街口
    static final public void distribution(String type, Location location, Particle particle, int size, int density, int time, int all_time, Particle.DustOptions dust_options) {
        switch (type) {

            case "diffusion_3D": // 擴散特效
                diffusion.Display_3D(location, particle, size, density, time, all_time, dust_options);
                break;
            case "diffusion_2D": // 擴散特效
                diffusion.Display_2D(location, particle, size, density, time, all_time, dust_options);
                break;

            case "shrink_3D": // 收縮特效
                shrink.Display_3D(location, particle, size, density, time, all_time, dust_options);
                break;
            case "shrink_2D": // 收縮特效
                shrink.Display_2D(location, particle, size, density, time, all_time, dust_options);
                break;

            case "protection": // 保護罩特效
                protection.Display(location, particle, size, density, time, all_time, dust_options);
                break;

            case "rise": // 上升圓圈特效
                rise.Display(location, particle, size, density, time, all_time, dust_options);
                break;
            case "drop": // 下降圓圈特效
                drop.Display(location, particle, size, density, time, all_time, dust_options);
                break;

            case "rotate": // 旋轉特效
                rotate.Display(location, particle, size, density, time, all_time, dust_options);
                break;

            case "circle": // 圓圈特效
                circle.Display(location, particle, size, density, time, all_time, dust_options);
                break;


        }
    }
}