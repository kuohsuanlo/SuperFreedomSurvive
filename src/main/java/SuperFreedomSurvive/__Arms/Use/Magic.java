package SuperFreedomSurvive.__Arms.Use;

import SuperFreedomSurvive.Arms.Animation;
import SuperFreedomSurvive.__Arms.Fast_Display;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Iterator;

final public class Magic {
    // 開始魔法效果 及 技能施放
    static final public void Start(Player player, int CD, int ST, String skill, int LV, String attributes, int distance) {
        try {

            // 初始化
            Particle particle;
            Particle.DustOptions dustOptions;

            // 屬性類別
            //particle = Particle.REDSTONE;
            if (attributes.equals("fire")) {
                // 火
                dustOptions = new Particle.DustOptions(Color.fromRGB(255, 43, 0), 1);

            } else if (attributes.equals("ice")) {
                // 冰
                dustOptions = new Particle.DustOptions(Color.fromRGB(0, 179, 255), 1);

            } else if (attributes.equals("shine")) {
                // 光
                dustOptions = new Particle.DustOptions(Color.fromRGB(247, 255, 0), 1);

            } else if (attributes.equals("dark")) {
                // 闇
                dustOptions = new Particle.DustOptions(Color.fromRGB(85, 0, 171), 1);

            } else {
                // 都不是
                dustOptions = new Particle.DustOptions(Color.fromRGB(0, 0, 0), 1);
            }


            new BukkitRunnable() {
                // 詠唱開始
                //Double.parseDouble( (new DecimalFormat("###.00")).format(( ST / 10 )) )
                double all_time = ST;
                int time = ST;
                // 產生詠唱特效
                // 起始點
                int start = 0;

                @Override
                final public void run() {
                    if (time < 0) {

                        // 技能發動
                        Location locationoc = player.getLocation();
                        double X = locationoc.getX();
                        double Y = locationoc.getY();
                        double Z = locationoc.getZ();
                        // 圓弧( 絕對值( Yaw ) )
                        // 不超過最遠 distance 距離
                        double radians = Math.toRadians(locationoc.getYaw() + 90);

                        double pitch = Math.toRadians(locationoc.getPitch());


                        // 開始實體尋找計算
                        for (int d = 2; d <= distance; ++d) {

                            // 計算位置
                            locationoc.setX(X + d * Math.cos(radians) * Math.cos(pitch));
                            locationoc.setY(Y + d * Math.sin(pitch) * -1);
                            locationoc.setZ(Z + d * Math.sin(radians) * Math.cos(pitch));

                            Collection collection = Bukkit.getWorld(player.getWorld().getName()).getNearbyEntities(
                                    locationoc,
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
                                    if (entity.getName().equals(player.getName())) {
                                        // ! 禁止

                                    } else {
                                        // 不是自己 允許
                                        Animation.Follow("protection", entity, Particle.FLAME, 1, 40, 20, null);
                                        //Bukkit.getWorld(entity.getWorld().getName()).spawnEntity(entity.getLocation(), EntityType.PRIMED_TNT);
                                        //Bukkit.getWorld(entity.getWorld().getName()).spawnParticle( Particle.EXPLOSION_HUGE , entity.getLocation() , LV / 8 , 0 , 0 , 0 , LV );
                                        //Bukkit.getWorld(entity.getWorld().getName()).createExplosion(entity.getLocation(), 1F * ( LV / 10 ));
                                        break;

                                    }

                                } else {
                                    // 不是玩家 允許
                                    Animation.Follow("protection", entity, Particle.FLAME, 1, 40, 20, null);
                                    //Skill.Skill(player, skill, entity.getLocation(), LV);
                                }

                            } else if (d == distance) {
                                // 最遠都沒發現實體
                                Animation.Fixed("protection", locationoc, Particle.FLAME, 1, 40, 20, null);
                                //Skill.Skill( player , skill , locationoc , LV);
                                //Bukkit.getWorld(player.getWorld().getName()).spawnEntity(locationoc, EntityType.PRIMED_TNT);
                                //Bukkit.getWorld(player.getWorld().getName()).spawnParticle( Particle.EXPLOSION_HUGE , locationoc , LV / 8 , 0 , 0 , 0 , LV );
                                //Bukkit.getWorld(player.getWorld().getName()).createExplosion(locationoc, 1F * ( LV / 10 ));

                            }
                        }


                        cancel();  // 終止線程
                        return;
                    }

                    if (start >= 360) {
                        start = 0;
                    }

                    // 告知玩家剩餘的時間
                    // 換算 % 值
                    int percentage = Integer.parseInt((new DecimalFormat("###")).format((time / all_time * 100)));
                    String schedule = "";
                    for (int i = 0; i < percentage; i = i + 2) {
                        schedule += "|";
                    }
                    // 顯示訊息
                    player.sendTitle(
                            "",
                            schedule + "   詠唱中   " + schedule,
                            0,
                            10,
                            0
                    );

                    // 按照等級來做改變
                    // LV / 10 範圍
                    for (int radius = 1; radius < (LV * 0.1) + 1; ++radius) {
                        // 隨機數 0 ~ 9
                        //int random = (int)(Math.random()*10);
                        Fast_Display.circle_2D(player.getWorld().getName(), start + (radius * 15), 4, 120, player.getLocation().getX(), player.getLocation().getY() + (radius * 0.2), player.getLocation().getZ(), Particle.REDSTONE, 0, dustOptions);
                    }
                    //display_circle( player.getWorld().getName() , 4 , 10 , player.getLocation().getX() , player.getLocation().getY() , player.getLocation().getZ() , Particle.REDSTONE , 0 , dustOptions_ );
                    Fast_Display.circle_2D(player.getWorld().getName(), start, 2, 40, player.getLocation().getX(), player.getLocation().getY() + 1, player.getLocation().getZ(), Particle.ENCHANTMENT_TABLE, 1, null);

                    --time;
                    start = start + 7;

                }

            }.runTaskTimer(SuperFreedomSurvive.SYSTEM.Plugin.get(), 0L, 1L);


        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
