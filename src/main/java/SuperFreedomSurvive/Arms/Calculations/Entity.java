package SuperFreedomSurvive.Arms.Calculations;

import org.bukkit.Location;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

final public class Entity {

    // 取得實體快速接口
    static final public ArrayList<org.bukkit.entity.LivingEntity> Range(Location location, int distance) {
        Collection collection = location.getWorld().getNearbyEntities(
                location,
                distance,
                distance,
                distance
        );
        if (collection.size() > 0) {
            // 有實體
            // 取得全部實體
            ArrayList<org.bukkit.entity.LivingEntity> all = new ArrayList<org.bukkit.entity.LivingEntity>();

            Iterator iterator = collection.iterator();
            while (iterator.hasNext()) {
                org.bukkit.entity.Entity entity = (org.bukkit.entity.Entity) iterator.next();

                if (entity instanceof Player) {
                    if (location.getWorld().getPVP()) {
                        all.add((LivingEntity) entity);

                    }
                } else if (entity instanceof Item) {

                } else if (entity instanceof LivingEntity) {
                    all.add((LivingEntity) entity);
                }

            }
            return all;
        }
        return null;
    }


}
