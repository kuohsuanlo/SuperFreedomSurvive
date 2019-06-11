package SuperFreedomSurvive.Function;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Scoreboard;

final public class DisplayHealth implements Listener {


    // 玩家加入顯示血量
    @EventHandler
    final public void onPlayerJoinEvent(PlayerJoinEvent event) {

        // 建立積分版
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        scoreboard.registerNewObjective("HP", "health", "HP").setDisplaySlot(DisplaySlot.BELOW_NAME);
        event.getPlayer().setScoreboard(scoreboard);

    }


    // 召喚生物
    @EventHandler
    final public void onCreatureSpawnEvent(CreatureSpawnEvent event) {
        if (event.isCancelled()) {

        } else {
/*
            // 更改名稱 當前HP/最大HP
            double health_max = Math.floor(event.getEntity().getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() * 100) / 100;
            // 要換算藥水效果
            if ( event.getEntity().getPotionEffect(PotionEffectType.HEALTH_BOOST) != null ) {
                health_max = (double)health_max + ((1 + event.getEntity().getPotionEffect(PotionEffectType.HEALTH_BOOST).getAmplifier()) * 4);
            }
            double health = Math.floor(event.getEntity().getHealth() * 100) / 100;
            //double final_damage = Math.floor(event.getFinalDamage() * 100) / 100;
            event.getEntity().setCustomName( "" + health + " / " + health_max + " HP" );
            event.getEntity().setCustomNameVisible(false);
*/
            /*
            // 建立積分版
            Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
            scoreboard.registerNewObjective("HP", "health" , "HP" ).setDisplaySlot(DisplaySlot.BELOW_NAME);
            event.getEntity().getScoreboardTags().add("");

            // 沒被取消
            //Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
            //scoreboard.registerNewObjective( "HP", "health" , "HP" ).setDisplaySlot(DisplaySlot.BELOW_NAME);
            //scoreboard.registerNewTeam("Show_Health");
            //event.getEntity().getScoreboardTags().add("Show_Health");
            //scoreboard.getTeam("Show_Health").addEntry( event.getEntity().getUniqueId().toString() );
            */
        }
    }


    // 顯示造成的傷害量
    @EventHandler
    final public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
        // 是否為玩家造成這次傷害
        if (event.getEntity() instanceof LivingEntity && event.getDamager() instanceof Player) {
            // 是否已經取消事件
            if (event.isCancelled()) {
                // 已經被取消
            } else {
                // 沒有被取消

                // 是否為正砍造成的傷害
                if (event.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK)) {
                    try {
                        Player player = (Player) event.getDamager();
                        LivingEntity entity = (LivingEntity) event.getEntity();
                        //ntity.setS
                        //event.getH

                        if (entity.getHealth() > 0) {

                            double health_max = Math.floor(entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() * 100) / 100;
                            // 要換算藥水效果
                            if (entity.getPotionEffect(PotionEffectType.HEALTH_BOOST) != null) {
                                health_max = health_max + ((1 + entity.getPotionEffect(PotionEffectType.HEALTH_BOOST).getAmplifier()) * 4);
                            }

                            double health = Math.floor(entity.getHealth() * 100) / 100;
                            //event.getFinalDamage​()
                            double final_damage = Math.floor(event.getFinalDamage() * 100) / 100;
                            //event.setDamage(final_damage);


                            /*
                            if ( health - hurt < 0 ) {
                                entity.setCustomName("§cHP(0/" + health_max + ")");

                            } else {
                                entity.setCustomName("§cHP(" + ( Math.ceil( ( health - hurt ) * 100 ) / 100 ) + "/" + health_max + ")");

                            }
                            //player.sendMessage(""+ );
                            //player.sendMessage(""+health_max);
                            */
                            // 造成傷害
                            // 顯示訊息
                            if ((Math.floor((health - final_damage) * 100) / 100) < 0) {

                                player.sendTitle(
                                        "§c- " + final_damage + " HP",
                                        "§c剩餘 0 HP",
                                        0,
                                        10,
                                        2
                                );

                                // 更改名稱 當前HP/最大HP
                                //event.getEntity().setCustomName("0 / " + health_max + " HP");
                                //event.getEntity().setCustomNameVisible(false);

                            } else {

                                player.sendTitle(
                                        "§c- " + final_damage + " HP",
                                        "§c剩餘 " + (Math.floor((health - final_damage) * 100) / 100) + " HP",
                                        0,
                                        10,
                                        2
                                );

                                // 更改名稱 當前HP/最大HP
                                //event.getEntity().setCustomName("" + (Math.floor((health - final_damage) * 100) / 100) + " / " + health_max + " HP");
                                //event.getEntity().setCustomNameVisible(false);

                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }
}
