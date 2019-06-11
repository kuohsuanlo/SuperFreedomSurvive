package SuperFreedomSurvive.Arms;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Iterator;

final public class Search {

    // 運算使用

    // 範圍計算 尋找第一隻
    // 位置 範圍 連續(後方的實體都會受到傷害) 特效類型
    static final public Location Range(Location location, int distance) {

        double X = location.getX();
        double Y = location.getY();
        double Z = location.getZ();

        double radians = Math.toRadians(location.getYaw() + 90);

        double pitch = Math.toRadians(location.getPitch());

        // 開始實體尋找計算
        for (int d = 3; d <= distance; ++d) {

            // 計算位置
            location.setX(X + d * Math.cos(radians) * Math.cos(pitch));
            location.setY(Y + d * Math.sin(pitch) * -1);
            location.setZ(Z + d * Math.sin(radians) * Math.cos(pitch));

            Collection collection = location.getWorld().getNearbyEntities(
                    location,
                    2,
                    2,
                    2
            );

            if (collection.size() > 0) {
                // 有實體
                // 取得第一個實體的位置
                Iterator iterator = collection.iterator();
                Entity entity = (Entity) iterator.next();

                // 是否為玩家自己
                if (entity instanceof Player) {
                    if (location.getWorld().getPVP()) {
                        // 允許
                        return entity.getLocation();
                    }

                } else if (entity instanceof Item) {
                    // 掉落物 略過

                } else {
                    // 不是玩家 允許
                    //Animation.Follow( "explosion_3D" , entity , Particle.FLAME , 3 , 40 , 10 , null );
                    return entity.getLocation();

                }

            } else if (d == distance) {
                // 最遠都沒發現實體
                return location;
                //Animation.Fixed( "explosion_3D" , location , Particle.FLAME , 3 , 40 , 10 , null );

            }
        }
        return null;
    }

}
