package SuperFreedomSurvive.Player;

import SuperFreedomSurvive.SYSTEM.MySQL;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Calendar;

final public class Login implements Listener {
    // 登入控制


    //
    @EventHandler
    final public void onPlayerLoginEvent(PlayerLoginEvent event) {
        Player player = event.getPlayer();

        // 檢查是否為不正當帳號
        // 使用 https://mcleaks.themrgong.xyz/api/v3/isuuidmcleaks/UUID 此 API
        new BukkitRunnable() {
            @Override
            final public void run() {
                try {
                    if ((boolean) SuperFreedomSurvive.SYSTEM.Http.Call("https://mcleaks.themrgong.xyz/api/v3/isuuidmcleaks/" + player.getUniqueId().toString()).get("isMcleaks")) {
                        player.kickPlayer("不正當的帳號");
                        //ServerPlugin.Player.Delete.All(player.getName(), player.getUniqueId().toString());
                        //_Violation.Add(player, "不正當的帳號", 100);
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                    cancel();
                    return;
                }
            }
        }.runTaskAsynchronously(SuperFreedomSurvive.SYSTEM.Plugin.get());

        // 使否違規已經超標
        //if (_Violation.Exceed(player.getName())) {
        //    event.setResult(PlayerLoginEvent.Result.KICK_OTHER);
        //    event.setKickMessage("違規已經超標");
        //
        //}
    }

    // 是否為第一次登入
    @EventHandler
    final public void onPlayerJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        // 檢查是否為不正當帳號
        // 使用 https://mcleaks.themrgong.xyz/api/v3/isuuidmcleaks/UUID 此 API
        new BukkitRunnable() {
            @Override
            final public void run() {
                try {
                    if ((boolean) SuperFreedomSurvive.SYSTEM.Http.Call("https://mcleaks.themrgong.xyz/api/v3/isuuidmcleaks/" + player.getUniqueId().toString()).get("isMcleaks")) {
                        player.kickPlayer("不正當的帳號");
                        //ServerPlugin.Player.Delete.All(player.getName(), player.getUniqueId().toString());
                        //_Violation.Add(player, "不正當的帳號", 100);
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                    cancel();
                    return;
                }
            }
        }.runTaskAsynchronously(SuperFreedomSurvive.SYSTEM.Plugin.get());

        // 使否違規已經超標
        //if (_Violation.Exceed(player.getName())) {
        //    player.kickPlayer("違規已經超標");
        //
        //
        //} else {
        // 沒超標

        try {

            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫

            // 是否在聊天室裡
            ResultSet res = sta.executeQuery("SELECT * FROM `player-chatroom_data` WHERE `Player` = '" + player.getName() + "' LIMIT 1");
            // 跳到最後一行
            res.last();
            // 最後一行 行數 是否 > 0
            if (res.getRow() > 0) {
                // 已經有加入聊天室
                player.sendMessage("§b[聊天室]  預設聊天頻道是聊天室 發送訊息開頭加上 ` 或是 ~ 則是公開頻道");

            }

            res.close(); // 關閉查詢

            // 檢查是否有資料
            res = sta.executeQuery("SELECT * FROM `player_data` WHERE `Player` = '" + player.getName() + "'");
            // 跳到最後一行
            res.last();
            // 最後一行 行數 是否 > 0
            if (res.getRow() > 0) {
                // 有資料
                // 跳回 初始行 必須使用 next() 才能取得資料
                res.beforeFirst();
                res.next();

                if (!res.getBoolean("Presence")) {
                    // 登記為存在

                    res.close(); // 關閉查詢


                    // 發送廣播訊息
                    Bukkit.broadcastMessage("§e玩家 " + player.getName() + " 的回歸!");


                    sta.executeUpdate("UPDATE `player_data` SET `Presence` = '1' WHERE `Player` = '" + player.getName() + "'");

                    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                    sta.executeUpdate("INSERT INTO `player-additional_data` (`Player`) VALUES ('" + player.getName() + "')");
                    sta.executeUpdate("INSERT INTO `player-value_data` (`Player`) VALUES ('" + player.getName() + "')");
                    sta.executeUpdate("INSERT INTO `player-statistics_data` (`Player`, `Login_Total`) VALUES ('" + player.getName() + "', '0')");

                    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                    // 創建禮包
                    //      新手支援
                    SuperFreedomSurvive.Menu.Function.Kit.sendKit(player.getName(), null, "§e新手支援", "§r§f 吃飽飽 才能趕工", Material.COOKED_BEEF, 32, null, SuperFreedomSurvive.SYSTEM.Time.Get(), null);
                    SuperFreedomSurvive.Menu.Function.Kit.sendKit(player.getName(), null, "§e新手支援", "§r§f 能做許多事情", Material.EMERALD, 64, null, SuperFreedomSurvive.SYSTEM.Time.Get(), null);
                    SuperFreedomSurvive.Menu.Function.Kit.sendKit(player.getName(), null, "§e新手支援", "§r§f 別中暑了", Material.LEATHER_HELMET, 1, null, SuperFreedomSurvive.SYSTEM.Time.Get(), null);
                    SuperFreedomSurvive.Menu.Function.Kit.sendKit(player.getName(), null, "§e新手支援", "§r§f 別凍傷了", Material.LEATHER_CHESTPLATE, 1, null, SuperFreedomSurvive.SYSTEM.Time.Get(), null);
                    SuperFreedomSurvive.Menu.Function.Kit.sendKit(player.getName(), null, "§e新手支援", "§r§f 別凍傷了", Material.LEATHER_LEGGINGS, 1, null, SuperFreedomSurvive.SYSTEM.Time.Get(), null);
                    SuperFreedomSurvive.Menu.Function.Kit.sendKit(player.getName(), null, "§e新手支援", "§r§f 別凍傷了", Material.LEATHER_BOOTS, 1, null, SuperFreedomSurvive.SYSTEM.Time.Get(), null);

                    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                    // 給予時鐘 並帶有說明
                    ItemStack item = new ItemStack(Material.CLOCK);
                    ItemMeta potion = item.getItemMeta();
                    potion.setDisplayName("§e神奇的時鐘");
                    potion.setLore(Arrays.asList(
                            ("§r§f (右鍵) 打開選單"),
                            ("§r§f 介面會需要等待一下"),
                            ("§r§f 所有時鐘都擁有此效果"),
                            ("§r§f Shift + F鍵 同樣功能")
                    ));
                    // 寫入資料
                    item.setItemMeta(potion);
                    // 添加到背包
                    player.getInventory().addItem(item);


                    // 傳送
                    SuperFreedomSurvive.Player.Teleport.SpawnLocation(player, "w");

                }

                res.close(); // 關閉查詢


                //////////////////////////////////////////////////////////////////////////////////////
                {
                    // 每日登入獎勵
                    // 今天日期
                    Calendar calendar = Calendar.getInstance();
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH) + 1; // 1月的值為0
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    String time = year + "-" + month + "-" + day + " 00:00:00";

                    // 30 天後
                    calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 30);
                    year = calendar.get(Calendar.YEAR);
                    month = calendar.get(Calendar.MONTH) + 1; // 1月的值為0
                    day = calendar.get(Calendar.DAY_OF_MONTH);
                    String end_time = year + "-" + month + "-" + day + " 00:00:00";

                    res = sta.executeQuery("SELECT * FROM `player_data` WHERE `Player` = '" + player.getName() + "' AND `Login_Last_Time` <= '" + time + "'");
                    // 跳到最後一行
                    res.last();
                    // 最後一行 行數 是否 > 0
                    if (res.getRow() > 0) {
                        // 有資料
                        // 跳回 初始行 必須使用 next() 才能取得資料
                        res.beforeFirst();
                        res.next();

                        SuperFreedomSurvive.Menu.Function.Kit.sendKit(player.getName(), null, "§e每日登入賞", "§r§f 明天也要繼續玩歐!", Material.EMERALD, 8, null, SuperFreedomSurvive.SYSTEM.Time.Get(), end_time);
                    }
                    res.close(); // 關閉查詢
                }
                //////////////////////////////////////////////////////////////////////////////////////



                // 改變資料
                sta.executeUpdate("UPDATE `player_data` SET `Login_Last_Time` = '" + SuperFreedomSurvive.SYSTEM.Time.Get() + "' WHERE `Player` = '" + player.getName() + "'");
                sta.executeUpdate("UPDATE `player-statistics_data` SET `Login_Total` = `Login_Total` + 1 WHERE `Player` = '" + player.getName() + "'");




                res = sta.executeQuery("SELECT * FROM `player-location_data` WHERE `Player` = '" + player.getName() + "' LIMIT 1");
                // 跳到最後一行
                res.last();
                // 最後一行 行數 是否 > 0
                if (res.getRow() > 0) {
                    // 有資料
                    // 跳回 初始行 必須使用 next() 才能取得資料
                    res.beforeFirst();
                    res.next();

                    String world = res.getString("World");
                    int X = res.getInt("X");
                    int Y = res.getInt("Y");
                    int Z = res.getInt("Z");

                    res.close(); // 關閉查詢

                    // 不適用於觀察者
                    if (player.getGameMode() != GameMode.SPECTATOR) {

                        // 如果在世界 world 先傳送到安全座標
                        if (player.getWorld().getName().equals("w")) {
                            // 傳送
                            player.teleport(Bukkit.getWorld("w").getSpawnLocation());
                        }

                        // 傳送
                        SuperFreedomSurvive.Player.Teleport.Location(
                                player,
                                world,
                                X,
                                Y,
                                Z
                        );

                    }
                }

                res.close(); // 關閉查詢

            } else {
                res.close(); // 關閉查詢


                // 發送廣播訊息
                Bukkit.broadcastMessage("§e玩家 " + player.getName() + " 的初次登入!");
                //Bukkit.getLogger().info("§e玩家 " + player.getName() + " 的初次登入!");


                // 第一次登入
                // 創建資料
                sta.executeUpdate("INSERT INTO `player_data` (`Player`, `UUID`, `Login_First_Time`, `Login_Last_Time`) VALUES ('" + player.getName() + "', '" + player.getUniqueId().toString() + "', '" + SuperFreedomSurvive.SYSTEM.Time.Get() + "', '" + SuperFreedomSurvive.SYSTEM.Time.Get() + "')");

                ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                sta.executeUpdate("INSERT INTO `player-additional_data` (`Player`) VALUES ('" + player.getName() + "')");
                sta.executeUpdate("INSERT INTO `player-value_data` (`Player`) VALUES ('" + player.getName() + "')");
                sta.executeUpdate("INSERT INTO `player-statistics_data` (`Player`, `Login_Total`) VALUES ('" + player.getName() + "', '0')");

                ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                // 創建禮包
                //      新手支援
                SuperFreedomSurvive.Menu.Function.Kit.sendKit(player.getName(),null,"§e新手支援","§r§f 吃飽飽 才能趕工",Material.COOKED_BEEF,32,null, SuperFreedomSurvive.SYSTEM.Time.Get(),null);
                SuperFreedomSurvive.Menu.Function.Kit.sendKit(player.getName(),null,"§e新手支援","§r§f 能做許多事情",Material.EMERALD,64,null, SuperFreedomSurvive.SYSTEM.Time.Get(),null);
                SuperFreedomSurvive.Menu.Function.Kit.sendKit(player.getName(),null,"§e新手支援","§r§f 別中暑了",Material.LEATHER_HELMET,1,null, SuperFreedomSurvive.SYSTEM.Time.Get(),null);
                SuperFreedomSurvive.Menu.Function.Kit.sendKit(player.getName(),null,"§e新手支援","§r§f 別凍傷了",Material.LEATHER_CHESTPLATE,1,null, SuperFreedomSurvive.SYSTEM.Time.Get(),null);
                SuperFreedomSurvive.Menu.Function.Kit.sendKit(player.getName(),null,"§e新手支援","§r§f 別凍傷了",Material.LEATHER_LEGGINGS,1,null, SuperFreedomSurvive.SYSTEM.Time.Get(),null);
                SuperFreedomSurvive.Menu.Function.Kit.sendKit(player.getName(),null,"§e新手支援","§r§f 別凍傷了",Material.LEATHER_BOOTS,1,null, SuperFreedomSurvive.SYSTEM.Time.Get(),null);

                ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


                // 給予時鐘 並帶有說明
                ItemStack item = new ItemStack(Material.CLOCK);
                ItemMeta potion = item.getItemMeta();
                potion.setDisplayName("§e神奇的時鐘");
                potion.setLore(Arrays.asList(
                        ("§r§f (右鍵) 打開選單"),
                        ("§r§f 介面會需要等待一下"),
                        ("§r§f 所有時鐘都擁有此效果"),
                        ("§r§f Shift + F鍵 同樣功能")
                ));
                // 寫入資料
                item.setItemMeta(potion);
                // 添加到背包
                player.getInventory().addItem(item);


                // 傳送
                SuperFreedomSurvive.Player.Teleport.SpawnLocation(player, "w");

                new BukkitRunnable() {
                    @Override
                    final public void run() {
                        // 打開初始選單
                        SuperFreedomSurvive.Menu.Function.Help.menu(player);
                    }
                }.runTaskLaterAsynchronously(SuperFreedomSurvive.SYSTEM.Plugin.get(), 100L);

            }



            // 顯示名稱
            String nick = SuperFreedomSurvive.Player.Data.getNick(player.getName());
            if (nick != null) {
                player.setDisplayName(nick + "§r§f");
                player.setPlayerListName(nick);
                player.setCustomName(nick);

            } else {
                player.setDisplayName(player.getName());
                player.setPlayerListName(player.getName());
                player.setCustomName(player.getName());

            }

            // 視野距離
            player.setViewDistance(SuperFreedomSurvive.Player.Data.getViewDistance(player.getName()));



            res.close(); // 關閉查詢
            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //}
    }
}
