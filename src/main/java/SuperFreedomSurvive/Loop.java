package SuperFreedomSurvive;

import SuperFreedomSurvive.SYSTEM.MySQL;
import SuperFreedomSurvive.World.Data;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

class Loop {
    // 循環檢查資料
    static void Start() {

/*
        new BukkitRunnable() {
            @Override
            final public void run() {
                // 每0.1秒 跑一次
                try {
                    Connection con;
                    Statement sta ;
                    ResultSet res;



                    // ----------------------------------------------------------------------------------------------------------------------
                    // 等待傳送的玩家列表
                    ArrayList<Object> r = ServerPlugin.Player.Teleport.Get();
                    if ( r != null ) {
                        Player player = (Player) r.get(0);
                        Location location = (Location) r.get(1);
                        if (ServerPlugin.World.List.Have(location.getWorld().getName())) {
                            // 有


                        }
                    }







                } catch (Exception ex) {
                    //cancel();
                    //return;
                }
            }
        }.runTaskTimer(ServerPlugin.SYSTEM.Plugin.get(), 0L, 1L);
        */
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        new BukkitRunnable() {
            @Override
            final public void run() {

                if (!Bukkit.getPluginManager().isPluginEnabled(SuperFreedomSurvive.SYSTEM.Plugin.get())) {
                    // 插件停止
                    cancel();
                    return;
                }


                // 每 30 秒 跑一次
                try {
                    Connection con = MySQL.getConnection(); // 連線 MySQL
                    Statement sta = con.createStatement(); // 取得控制庫
                    ResultSet res;


                    // ----------------------------------------------------------------------------------------------------------------------
                    {
                        // 檢查地圖是否可以開始
                        res = sta.executeQuery("SELECT * FROM `world-list_data` WHERE `Start_Time` <= '" + SuperFreedomSurvive.SYSTEM.Time.Get() + "' AND `End_Time` >= '" + SuperFreedomSurvive.SYSTEM.Time.Get() + "' AND `Enable` = '0' LIMIT 1");
                        if (res.next()) {
                            // 地圖加載
                            String name = res.getString("Name");
                            //ServerPlugin.World.Load.Load(name);
                            // 更動資料
                            sta.executeUpdate("UPDATE `world-list_data` SET `Enable` = '1' WHERE `Name` = '" + name + "' LIMIT 1");
                            Bukkit.getLogger().info("世界 " + name + " 已開始");

                        }

                        res.close(); // 關閉查詢
                    }


                    // ----------------------------------------------------------------------------------------------------------------------
                    {
                        // 檢查地圖是否已經到期
                        res = sta.executeQuery("SELECT * FROM `world-list_data` WHERE `End_Time` <= '" + SuperFreedomSurvive.SYSTEM.Time.Get() + "' AND `Enable` = '1' LIMIT 1");
                        if (res.next()) {
                            // 解除地圖加載
                            String name = res.getString("Name");
                            Data.Unload(name, false);
                            Data.Delete(name);
                            // 更動資料
                            sta.executeUpdate("UPDATE `world-list_data` SET `Enable` = '0' WHERE `Name` = '" + name + "' LIMIT 1");
                            Bukkit.getLogger().info("世界 " + name + " 已到期");

                        }

                        res.close(); // 關閉查詢
                    }


                    // ----------------------------------------------------------------------------------------------------------------------
                    {
                        // 檢查地圖是否完全無玩家
                        ArrayList worlds = SuperFreedomSurvive.World.List.Get();
                        for (int i = worlds.size() - 1; i >= 0; --i) {

                            //Bukkit.getServer().getLogger().info( worlds.get(i).toString() );
                            if (
                                    worlds.get(i).equals("w") ||
                                            worlds.get(i).equals("w_nether") ||
                                            worlds.get(i).equals("w_the_end")
                            ) {

                            } else {
                                // 檢查是否沒有任務正在運行
                                res = sta.executeQuery("SELECT * FROM `player-additional-wood-axe_data` ORDER BY `Time` LIMIT 1");
                                res.last();
                                // 最後一行 行數 是否 > 0
                                if (res.getRow() > 0) {
                                    // 有資料
                                    // 跳回 初始行 必須使用 next() 才能取得資料
                                    res.beforeFirst();
                                    res.next();

                                    if (res.getString("World").equals(worlds.get(i))) {

                                    } else {

                                        //Bukkit.getWorld((String) worlds.get(i)).save(); // 保存世界
                                        if (Bukkit.getWorld((String) worlds.get(i)).getPlayers().size() <= 0) {
                                            // 停用世界
                                            Data.Unload((String) worlds.get(i), true);

                                        }
                                    }

                                } else {
                                    //Bukkit.getWorld((String) worlds.get(i)).save(); // 保存世界
                                    if (Bukkit.getWorld((String) worlds.get(i)).getPlayers().size() <= 0) {
                                        // 停用世界
                                        Data.Unload((String) worlds.get(i), true);
                                    }
                                }

                                res.close(); // 關閉查詢

                            }
                        }
                    }




                    // ----------------------------------------------------------------------------------------------------------------------
                    {
                        // 更新全部玩家最後登入時間 + 紀錄座標
                        // 取得所有玩家清單
                        Collection collection = Bukkit.getServer().getOnlinePlayers();
                        Iterator iterator = collection.iterator();
                        // 總數
                        while (iterator.hasNext()) {
                            Player player = ((Player) iterator.next());
                            sta.executeUpdate("UPDATE `player_data` SET `Login_Last_Time` = '" + SuperFreedomSurvive.SYSTEM.Time.Get() + "' WHERE `Player` = '" + player.getName() + "' LIMIT 1");

                            sta.executeUpdate("DELETE FROM `player-location_data` WHERE `Player` = '" + player.getName() + "'");

                            int X = new BigDecimal(player.getLocation().getX()).setScale(0, BigDecimal.ROUND_FLOOR).intValue();
                            int Y = new BigDecimal(player.getLocation().getY()).setScale(0, BigDecimal.ROUND_FLOOR).intValue();
                            int Z = new BigDecimal(player.getLocation().getZ()).setScale(0, BigDecimal.ROUND_FLOOR).intValue();
                            String world = player.getLocation().getWorld().getName();
                            sta.executeUpdate("INSERT INTO `player-location_data` (`Player`,`World`,`X`,`Y`,`Z`) VALUES ('" + player.getName() + "', '" + world + "', '" + X + "', '" + Y + "', '" + Z + "')");
                        }
                    }


                    // ----------------------------------------------------------------------------------------------------------------------
                    {
                        // 檢查是否有玩家太久沒登入
                        // 最久 30 天
                        Calendar calendar = Calendar.getInstance();

                        // 30 天前
                        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 30);
                        int year = calendar.get(Calendar.YEAR);
                        int month = calendar.get(Calendar.MONTH) + 1; // 1月的值為0
                        int day = calendar.get(Calendar.DAY_OF_MONTH);
                        int hour = calendar.get(Calendar.HOUR_OF_DAY); // HOUR_OF_DAY 24小時制    HOUR 12小時制
                        int minute = calendar.get(Calendar.MINUTE);
                        int second = calendar.get(Calendar.SECOND);
                        String min_time = year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;

                        res = sta.executeQuery("SELECT * FROM `player_data` WHERE `Login_Last_Time` <= '" + min_time + "' AND `Presence` = '1' ORDER BY `Login_Last_Time` LIMIT 10");
                        while (res.next()) {
                            // 移除該玩家全部資料
                            SuperFreedomSurvive.Player.Delete.All(res.getString("Player"), res.getString("UUID"));
                            Bukkit.getLogger().info("玩家 " + res.getString("Player") + " 資料已被清除");
                        }

                        //Bukkit.getLogger().info(min_time);

                        res.close(); // 關閉查詢
                    }



                    // ----------------------------------------------------------------------------------------------------------------------
                    {
                        // 刪除過期的裡包
                        sta.executeUpdate("DELETE FROM `player-kit_data` WHERE `End_Time` <= '" + SuperFreedomSurvive.SYSTEM.Time.Get() + "' ");
                    }







                    res.close(); // 關閉查詢
                    sta.close(); // 關閉連線

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        }.runTaskTimer(SuperFreedomSurvive.SYSTEM.Plugin.get(), 0L, 600L);
    }
}
