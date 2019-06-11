package SuperFreedomSurvive.__Arms.Use;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

final public class Skill {
    // 發動技能
    static final public void Skill(Player player, String skill, Location location, int LV) {

        Bukkit.getWorld(player.getWorld().getName()).spawnParticle(Particle.EXPLOSION_HUGE, location, LV / 8, 0, 0, 0, LV);
        SuperFreedomSurvive.__Arms.Use.Damage.New(player, LV / 10, location.getWorld(), location, LV / 10);
        location.getWorld().playSound(location, "minecraft:entity.generic.explode", 3, 1);
        //Bukkit.getWorld( player.getWorld().getName() ).createExplosion( location , 1F * ( LV / 10 ));

    }
}
