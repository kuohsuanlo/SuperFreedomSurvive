package SuperFreedomSurvive.Climate;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class Loop {

    private static World world = Time.world;
    private static long time = world.getFullTime() / 24000;
    private static Calendar calendar = Time.getCalendar();
    private static Season season = Time.getSeason();


    public static void start () {
        new BukkitRunnable() {
            @Override
            final public void run () {
                try {




                // 檢查是否又過了一天
                if ( time != world.getFullTime() / 24000 ) {
                    // 是
                    // 提醒每一位玩家
                    Collection collection = Bukkit.getServer().getOnlinePlayers();
                    for (Object o : collection) {
                        Player player = ((Player) o);

                        player.sendTitle(
                                "",
                                "§e" + Time.getMonth() + " 月 " + Time.getDay() + " 日  " + Objects.requireNonNull(Time.getSeason()).getName(),
                                0,
                                90,
                                10
                        );
                    }

                    time = world.getFullTime() / 24000; // 重新定義
                }











                // 檢查季節是否有改變
                if ( season != Time.getSeason() ) {
                    // 有
                    // 重新將已經讀取的所有區塊進行季節切換
                    List<World> worlds = Bukkit.getWorlds();
                    for (int i = 0, s = worlds.size(); i < s; ++i ) {
                        // 世界類型是否為主世界
                        if ( worlds.get(i).getEnvironment() == World.Environment.NORMAL ) {
                            // 轉換區塊為當前季節

                            // 取得所有區塊
                            Chunk[] chunks = worlds.get(i).getLoadedChunks();
                            for (int c = 0, l = chunks.length; c < l; ++c) {
                                // 轉換區塊為當前季節
                                Function.chunkBiomeConversionSeason( chunks[c] );
                            }
                        }
                    }

                    season = Time.getSeason(); // 重新定義
                }











                // 檢查月是否有改變
                if ( calendar != Time.getCalendar() ) {
                    // 有
                    if ( Function.getRandomWeather() ) {
                        // 下雨
                        Time.setWeather(true);

                    } else {
                        // 不下雨
                        Time.setWeather(false);

                    }

                    calendar = Time.getCalendar(); // 重新定義
                }















                // 使用季節檢測玩家
                if ( Time.getSeason() == Season.SPRING ) {
                    // 春季




                } else if ( Time.getSeason() == Season.SUMMER ) {
                    // 夏季
                    Collection collection = Bukkit.getServer().getOnlinePlayers();
                    for (Object o : collection) {
                        Player player = ((Player) o);
                        Location location = player.getLocation(); // 取得座標


                        if ( player.getWorld().getEnvironment() == World.Environment.NORMAL ) {
                            ///////////////////////////////////////////////////////////////////////////////////////////////////////////
                            // 沒戴帽子 且 陽光強度 >= 15 時間在 450 ~ 7700
                            // 沒穿衣服、褲子、鞋子 且方塊光線強度 <= 4 時間在 12566 ~ 22916
                            run_0:
                            {
                                // 如果是創造或觀察者 跳過此玩家
                                if (player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR) {
                                    break run_0;
                                }

                                EntityEquipment equipment = player.getEquipment(); // 取得裝備庫存

                                if (equipment != null) {
                                    if (
                                            equipment.getHelmet() == null || equipment.getHelmet().getType() == Material.AIR
                                    ) {
                                        // 避免在耕地/半磚上造成錯誤
                                        Location block_location = location;
                                        block_location.setY(block_location.getY() + 1.6);

                                        Block block = player.getWorld().getBlockAt(block_location); // 取得當前方塊

                                        block_location = null;

                                        // 光線量是否 >= 15 且 時間在 450 ~ 7700
                                        if (block.getLightFromSky() >= 15 && Time.getTime() >= 450 && Time.getTime() <= 7700) {
                                            // 中暑 (扣飽食度)
                                            if (player.getFoodLevel() > 0)
                                                player.setFoodLevel(player.getFoodLevel() - 1);

                                            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 40, 1, false, false, false), true); // 緩速
                                            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 40, 3, false, false, false), true); // 挖掘疲劳
                                            player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 40, 3, false, false, false), true); // 虛弱
                                            player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 40, 1, false, false, false), true); // 噁心

                                            // 顯示粒子效果
                                            player.getWorld().spawnParticle(Particle.VILLAGER_ANGRY, location, 20, 0.5, 0.5, 0.5, 1);

                                            player.sendTitle(
                                                    "",
                                                    "§c中暑狀態",
                                                    0,
                                                    20,
                                                    10
                                            );
                                        }
                                    }
                                }
                            }
                            ///////////////////////////////////////////////////////////////////////////////////////////////////////////
                            // 顯示粒子效果
                            // 頭上是否有方塊
                            if (location.getWorld().getHighestBlockYAt(location.getBlockX(), location.getBlockZ()) > location.getBlockY()) {
                                // 顯示粒子效果
                                player.getWorld().spawnParticle(Particle.SMOKE_NORMAL, location, 50, 20, 20, 20, 0);
                            } else {
                                // 顯示更多粒子效果
                                player.getWorld().spawnParticle(Particle.SMOKE_NORMAL, location, 20, 10, 10, 10, 0);
                            }
                            ///////////////////////////////////////////////////////////////////////////////////////////////////////////
                        }
                    }




                } else if ( Time.getSeason() == Season.FALL ) {
                    // 秋季
                    Collection collection = Bukkit.getServer().getOnlinePlayers();
                    for (Object o : collection) {
                        Player player = ((Player) o);
                        Location location = player.getLocation(); // 取得座標


                        if (player.getWorld().getEnvironment() == World.Environment.NORMAL) {
                            ///////////////////////////////////////////////////////////////////////////////////////////////////////////
                            // 下雨的話粒子效果在增加
                            // 就算沒有下雪 一樣顯示粒子
                            if (Time.getWeather()) {
                                // 頭上是否有方塊
                                if (location.getWorld().getHighestBlockYAt(location.getBlockX(), location.getBlockZ()) > location.getBlockY()) {
                                    // 顯示粒子效果
                                    player.getWorld().spawnParticle(Particle.WATER_DROP, location, 400, 40, 40, 40, 0);
                                } else {
                                    // 顯示更多粒子效果
                                    player.getWorld().spawnParticle(Particle.WATER_DROP, location, 1000, 20, 20, 20, 0);
                                }
                            }
                            ///////////////////////////////////////////////////////////////////////////////////////////////////////////
                        }
                    }




                } else if ( Time.getSeason() == Season.WINTER ) {
                    // 冬季
                    Collection collection = Bukkit.getServer().getOnlinePlayers();
                    for (Object o : collection) {
                        Player player = ((Player) o);
                        Location location = player.getLocation(); // 取得座標


                        if (player.getWorld().getEnvironment() == World.Environment.NORMAL) {
                            ///////////////////////////////////////////////////////////////////////////////////////////////////////////
                            // 沒穿衣服、褲子、鞋子 且方塊光線強度 <= 4 時間在 12566 ~ 22916
                            run_0:
                            {
                                // 如果是創造或觀察者 跳過此玩家
                                if (player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR) {
                                    break run_0;
                                }

                                EntityEquipment equipment = player.getEquipment(); // 取得裝備庫存
                                if (equipment != null) {
                                    if (
                                            equipment.getChestplate() == null || equipment.getChestplate().getType() == Material.AIR
                                                    || equipment.getLeggings() == null || equipment.getLeggings().getType() == Material.AIR
                                                    || equipment.getBoots() == null || equipment.getBoots().getType() == Material.AIR
                                    ) {
                                        // 避免在耕地/半磚上造成錯誤
                                        Location block_location = location;
                                        block_location.setY(block_location.getY() + 1.6);

                                        Block block = player.getWorld().getBlockAt(block_location); // 取得當前方塊

                                        block_location = null;

                                        // 光線量是否 < 4 且 時間在 12566 ~ 22916
                                        if (block.getLightFromBlocks() <= 4 && Time.getTime() >= 12566 && Time.getTime() <= 22916) {
                                            // 凍傷 (扣飽食度)
                                            if (player.getFoodLevel() > 0)
                                                player.setFoodLevel(player.getFoodLevel() - 1);

                                            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 40, 1, false, false, false), true); // 緩速
                                            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 40, 3, false, false, false), true); // 挖掘疲劳
                                            player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 40, 3, false, false, false), true); // 虛弱
                                            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 40, 0, false, false, false), true); // 致盲

                                            // 顯示粒子效果
                                            player.getWorld().spawnParticle(Particle.SPIT, location, 20, 0.5, 0.5, 0.5, 1);

                                            player.sendTitle(
                                                    "",
                                                    "§c凍傷狀態",
                                                    0,
                                                    20,
                                                    10
                                            );
                                        }
                                    }
                                }
                            }
                            ///////////////////////////////////////////////////////////////////////////////////////////////////////////
                            // 就算沒有下雪 一樣顯示粒子
                            if (!Time.getWeather()) {
                                // 頭上是否有方塊
                                if (location.getWorld().getHighestBlockYAt(location.getBlockX(), location.getBlockZ()) > location.getBlockY()) {
                                    // 顯示粒子效果
                                    player.getWorld().spawnParticle(Particle.SPIT, location, 100, 40, 40, 40, 0);
                                } else {
                                    // 顯示更多粒子效果
                                    player.getWorld().spawnParticle(Particle.SPIT, location, 200, 20, 20, 20, 0);
                                }
                            }
                            ///////////////////////////////////////////////////////////////////////////////////////////////////////////
                        }
                    }



                }











                // 每10tps有 1/2000 機率降雨
                // 每10tps有 1/2000 機率停雨
                if ( Time.getCalendar().isAlwaysWeather() || Time.getCalendar().isAlwaysNoWeather() ) {

                } else {
                    if (Time.getWeather()) {
                        if ((int) (Math.random() * 100000) <= 50) {
                            // 停雨
                            Time.setWeather(false);

                            //Bukkit.broadcastMessage("晴天");

                            // 讓所有玩家看見晴天
                            Collection collection = Bukkit.getServer().getOnlinePlayers();
                            for (Object o : collection) {
                                Player player = ((Player) o);

                                if ( player.getWorld().getEnvironment() == World.Environment.NORMAL ) {
                                    player.setPlayerWeather(WeatherType.CLEAR);
                                }
                            }
                        }

                    } else {
                        if ((int) (Math.random() * 100000) <= 50) {
                            // 降雨
                            Time.setWeather(true);

                            //Bukkit.broadcastMessage("降雨");

                            // 讓所有玩家看見下雨
                            Collection collection = Bukkit.getServer().getOnlinePlayers();
                            for (Object o : collection) {
                                Player player = ((Player) o);

                                if ( player.getWorld().getEnvironment() == World.Environment.NORMAL ) {
                                    player.setPlayerWeather(WeatherType.DOWNFALL);
                                }
                            }
                        }

                    }
                }










                {
                    // 是否手持物為時鐘
                    Collection collection = Bukkit.getServer().getOnlinePlayers();
                    Iterator iterator = collection.iterator();
                    while (iterator.hasNext()) {
                        Player player = ((Player) iterator.next());

                        if (
                                player.getInventory().getItemInMainHand().getType() == Material.CLOCK ||
                                player.getInventory().getItemInOffHand().getType() == Material.CLOCK
                        ) {
                            player.sendTitle(
                                    "",
                                    "§e" + Time.getMonth() + " 月 " + Time.getDay() + " 日 " + Math.round( Time.getTime() / 20 ) + " 秒  " + Time.getSeason().getName(),
                                    0,
                                    20,
                                    10
                            );

                        }
                    }
                    collection = null;
                }










                // 打雷
                if (Time.getCalendar().hasThunder()) {
                    // 可打雷
                    if (Time.getWeather()) {
                        // 下雨狀態 機率增加
                        if ( (int)(Math.random() * 10000) <= 20 ) {
                            // 打雷
                            List<World> worlds = Bukkit.getWorlds();
                            for (int i = 0, s = worlds.size(); i < s; ++i ) {
                                // 世界類型是否為主世界
                                if ( worlds.get(i).getEnvironment() == World.Environment.NORMAL ) {
                                    worlds.get(i).setThundering(true);
                                    worlds.get(i).setThunderDuration( (int)(Math.random() * 4000) );
                                }
                            }
                        }

                    } else {
                        // 沒下雨 機率少少
                        if ( (int)(Math.random() * 100000) <= 20 ) {
                            // 打雷
                            List<World> worlds = Bukkit.getWorlds();
                            for (int i = 0, s = worlds.size(); i < s; ++i ) {
                                // 世界類型是否為主世界
                                if ( worlds.get(i).getEnvironment() == World.Environment.NORMAL ) {
                                    worlds.get(i).setThundering(true);
                                    worlds.get(i).setThunderDuration( (int)(Math.random() * 1000) );
                                }
                            }
                        }

                    }
                }











                // 將所有世界天氣統一
                List<World> worlds = Bukkit.getWorlds();
                if (Time.getWeather()) {
                    for (World w : worlds) {
                        if (w.getEnvironment() == World.Environment.NORMAL) {
                            w.setStorm(true);
                            w.setWeatherDuration(24000);
                            w.setGameRule(GameRule.DO_WEATHER_CYCLE,false);
                        }
                    }

                    // 讓所有玩家看見下雨
                    Collection collection = Bukkit.getServer().getOnlinePlayers();
                    for (Object o : collection) {
                        Player player = ((Player) o);

                        if ( player.getWorld().getEnvironment() == World.Environment.NORMAL ) {
                            player.setPlayerWeather(WeatherType.DOWNFALL);
                        }
                    }
                } else {
                    for (World w : worlds) {
                        if (w.getEnvironment() == World.Environment.NORMAL) {
                            w.setStorm(false);
                            w.setWeatherDuration(1);
                            w.setGameRule(GameRule.DO_WEATHER_CYCLE,false);
                        }
                    }

                    // 讓所有玩家看見晴天
                    Collection collection = Bukkit.getServer().getOnlinePlayers();
                    for (Object o : collection) {
                        Player player = ((Player) o);

                        if ( player.getWorld().getEnvironment() == World.Environment.NORMAL ) {
                            player.setPlayerWeather(WeatherType.CLEAR);
                        }
                    }
                }




/*
                if ( Bukkit.getWorld("w").hasStorm() ) {
                    Bukkit.getLogger().info("y");
                } else {
                    Bukkit.getLogger().info("n");
                }
                Bukkit.getLogger().info(Bukkit.getWorld("w").getWeatherDuration() + "");
*/











                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }.runTaskTimer(SuperFreedomSurvive.SYSTEM.Plugin.get(), 0L, 10L);
    }


}
