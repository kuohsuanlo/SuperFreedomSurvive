package SuperFreedomSurvive.Land;

import SuperFreedomSurvive.SYSTEM.MySQL;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

final public class NewLand implements Listener {
    // 新增領地
    // 使用木鋤來新增領地

    @EventHandler
    final public void onPlayerInteractEvent(PlayerInteractEvent event) {
        // 圈地
        try {
            if ( event.getItem() == null ) { return; }

            // 檢查是否為木鋤 或是 木鏟
            if (event.getItem().getType() == Material.WOODEN_HOE) {
                // 是

                String world;

                Player player = event.getPlayer();

                if (player.getGameMode() == GameMode.SPECTATOR) {
                    // 是觀察者
                    return;
                }

                // 檢查是左手點擊 還是右手點擊
                if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
                    // 左手點擊一个方塊
                    // 圈地起點

                    event.setCancelled(true);

                    int X = (int) event.getClickedBlock().getLocation().getX();
                    int Y = (int) event.getClickedBlock().getLocation().getY();
                    int Z = (int) event.getClickedBlock().getLocation().getZ();
                    world = event.getClickedBlock().getLocation().getWorld().getName();

                    Connection con = MySQL.getConnection(); // 連線 MySQL
                    Statement sta = con.createStatement(); // 取得控制庫

                    ResultSet res = sta.executeQuery("SELECT (Min_X),(Min_Y),(Min_Z),(Max_X),(Max_Y),(Max_Z),(Owner),(Level) FROM `land-protection_data` WHERE `World` = '" + world + "' AND `Min_X` <= '" + X + "' AND `Min_Y` <= '" + Y + "' AND `Min_Z` <= '" + Z + "' AND `Max_X` >= '" + X + "' AND `Max_Y` >= '" + Y + "' AND `Max_Z` >= '" + Z + "' AND `Level` = '0' LIMIT 20");
                    // 跳到最後一行
                    res.last();
                    // 最後一行 行數 是否 > 0
                    if (res.getRow() > 0) {
                        // ! 是 ! 禁止創建
                        // 跳回 初始行 必須使用 next() 才能取得資料
                        res.beforeFirst();
                        while (res.next()) {
                            player.sendMessage(
                                    "[領地系統]  §a" + res.getString("Owner") +
                                            "§f  的領地  x:" + res.getInt("Min_X") +
                                            " y:" + res.getInt("Min_Y") +
                                            " z:" + res.getInt("Min_Z") +
                                            "  到  x:" + res.getInt("Max_X") +
                                            " y:" + res.getInt("Max_Y") +
                                            " z:" + res.getInt("Max_Z") +
                                            "  "
                            );

                        }

                        player.sendMessage("[領地系統]  §c失敗 開始點規劃在 其他領地內");

                    } else {
                        // 允許
                        event.getPlayer().sendMessage("[領地系統]  圈地 起點設置在  X:" + X + " Y:" + Y + " Z:" + Z);

                        res.close(); // 關閉查詢

                        sta.executeUpdate("DELETE FROM `land-protection_new-land_cache` WHERE `Player` = '" + player.getName() + "'");
                        sta.executeUpdate("INSERT INTO `land-protection_new-land_cache` (`World`,`Player`, `Type`, `X`, `Y`, `Z`) VALUES ('" + world + "','" + player.getName() + "', '1', '" + X + "', '" + Y + "', '" + Z + "')");

                        sta.close(); // 關閉連線


                    }

                    res.close(); // 關閉查詢
                    sta.close(); // 關閉連線


                } else if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    // 右手點擊一个方塊
                    // 圈地終點

                    event.setCancelled(true);

                    int End_X = (int) event.getClickedBlock().getLocation().getX();
                    int End_Y = (int) event.getClickedBlock().getLocation().getY();
                    int End_Z = (int) event.getClickedBlock().getLocation().getZ();
                    world = event.getClickedBlock().getLocation().getWorld().getName();


                    Connection con = MySQL.getConnection(); // 連線 MySQL
                    Statement sta = con.createStatement(); // 取得控制庫
                    ResultSet res = sta.executeQuery("SELECT * FROM `land-protection_new-land_cache` WHERE `Player` = '" + player.getName() + "'");
                    // 跳到最後一行
                    res.last();
                    // 最後一行 行數 是否 > 0
                    if (res.getRow() > 0) {
                        // 跳回 初始行 必須使用 next() 才能取得資料
                        res.beforeFirst();
                        res.next();

                        // 是否同個世界
                        if (world.equals(res.getString("World"))) {
                            // 有起點
                            int X = res.getInt("X");
                            int Y = res.getInt("Y");
                            int Z = res.getInt("Z");

                            // 格數是否大於 23000000 格
                            if ((Math.abs(End_X - X) + 1) * (Math.abs(End_Y - Y) + 1) * (Math.abs(End_Z - Z) + 1) > 23000000) {
                                // ! 超過
                                event.getPlayer().sendMessage("[領地系統]  §c太大了 總塊數已經超越 23,000,000 塊");
                            } else {
                                // 沒超越

                                res.close(); // 關閉查詢

                                res = sta.executeQuery("SELECT (Min_X),(Min_Y),(Min_Z),(Max_X),(Max_Y),(Max_Z),(Owner),(Level) FROM `land-protection_data` WHERE `World` = '" + world + "' AND `Min_X` <= '" + End_X + "' AND `Min_Y` <= '" + End_Y + "' AND `Min_Z` <= '" + End_Z + "' AND `Max_X` >= '" + End_X + "' AND `Max_Y` >= '" + End_Y + "' AND `Max_Z` >= '" + End_Z + "' AND `Level` = '0' LIMIT 20");
                                // 跳到最後一行
                                res.last();
                                // 最後一行 行數 是否 > 0
                                if (res.getRow() > 0) {
                                    // ! 是 ! 禁止創建
                                    // 跳回 初始行 必須使用 next() 才能取得資料
                                    res.beforeFirst();
                                    while (res.next()) {
                                        player.sendMessage(
                                                "[領地系統]  §a" + res.getString("Owner") +
                                                        "§f  的領地  x:" + res.getInt("Min_X") +
                                                        " y:" + res.getInt("Min_Y") +
                                                        " z:" + res.getInt("Min_Z") +
                                                        "  到  x:" + res.getInt("Max_X") +
                                                        " y:" + res.getInt("Max_Y") +
                                                        " z:" + res.getInt("Max_Z") +
                                                        "  "
                                        );

                                    }

                                    player.sendMessage("[領地系統]  §c失敗 結束點規劃在 其他領地內");

                                } else {
                                    // 允許
                                    // 進行寫入
                                    sta.executeUpdate("DELETE FROM `land-protection_new-land_cache` WHERE `Player` = '" + player.getName() + "'");
                                    sta.executeUpdate("INSERT INTO `land-protection_new-land_cache` (`World`, `Player`, `Type`, `X`, `Y`, `Z`, `End_X`, `End_Y`, `End_Z`) VALUES ('" + world + "','" + player.getName() + "', '2', '" + X + "', '" + Y + "', '" + Z + "','" + End_X + "','" + End_Y + "','" + End_Z + "')");

                                    // 計算
                                    // Math.ceil( ( ( End_X - X + 1 ) * ( End_Y - Y + 1 ) * ( End_Z - Z + 1 ) ) / 100 )
                                    //      X
                                    int Start_X;
                                    int Start_Y;
                                    int Start_Z;

                                    if (X < End_X) {
                                        Start_X = X;
                                    } else {
                                        Start_X = End_X;
                                        End_X = X;
                                    }
                                    //      Y
                                    if (Y < End_Y) {
                                        Start_Y = Y;
                                    } else {
                                        Start_Y = End_Y;
                                        End_Y = Y;
                                    }
                                    //      Z
                                    if (Z < End_Z) {
                                        Start_Z = Z;
                                    } else {
                                        Start_Z = End_Z;
                                        End_Z = Z;
                                    }
                                    event.getPlayer().sendMessage("[領地系統]  終點與起點間隔方塊數量  X:" + (Math.abs(End_X - Start_X) + 1) + " Y:" + (Math.abs(End_Y - Start_Y) + 1) + " Z:" + (Math.abs(End_Z - Start_Z) + 1) + "  共" + (Math.abs(End_X - Start_X) + 1) * (Math.abs(End_Y - Start_Y) + 1) * (Math.abs(End_Z - Start_Z) + 1) + "塊");

                                }

                                res.close(); // 關閉查詢
                                res.close(); // 關閉查詢

                            }

                        } else {
                            // ! 禁止不同世界
                            event.getPlayer().sendMessage("[領地系統]  §c禁止跨世界圈地");

                        }

                    } else {
                        // ! 沒起點
                        event.getPlayer().sendMessage("[領地系統]  §c請先左鍵設置 圈地起點");

                    }

                    res.close(); // 關閉查詢
                    sta.close(); // 關閉連線

                }


            } else if (event.getItem().getType() == Material.WOODEN_SHOVEL) {
                // 木鏟
                // 是

                String world;

                Player player = event.getPlayer();

                if (player.getGameMode() == GameMode.SPECTATOR) {
                    // 是觀察者
                    return;
                }

                // 檢查是左手點擊 還是右手點擊
                if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
                    // 左手點擊一个方塊
                    // 圈地起點

                    event.setCancelled(true);

                    int X = (int) event.getClickedBlock().getLocation().getX();
                    int Y = (int) event.getClickedBlock().getLocation().getY();
                    int Z = (int) event.getClickedBlock().getLocation().getZ();
                    world = event.getClickedBlock().getLocation().getWorld().getName();

                    Connection con = MySQL.getConnection(); // 連線 MySQL
                    Statement sta = con.createStatement(); // 取得控制庫

                    ResultSet res = sta.executeQuery("SELECT (Min_X),(Min_Y),(Min_Z),(Max_X),(Max_Y),(Max_Z),(Owner),(Level),(ID) FROM `land-protection_data` WHERE `World` = '" + world + "' AND `Min_X` <= '" + X + "' AND `Min_Y` <= '" + Y + "' AND `Min_Z` <= '" + Z + "' AND `Max_X` >= '" + X + "' AND `Max_Y` >= '" + Y + "' AND `Max_Z` >= '" + Z + "' AND `User` = '" + player.getName() + "' ORDER BY `Level` LIMIT 21");
                    // 跳到最後一行
                    res.last();
                    if (res.getRow() > 20) {
                        // 最後一行 行數 是否 > 20
                        // ! 禁止
                        player.sendMessage("[領地系統]  §c該區子領地已經達 20 層");

                        // 最後一行 行數 是否 > 0
                    } else if (res.getRow() > 0) {
                        // 允許創建
                        // 跳回 初始行 必須使用 next() 才能取得資料
                        player.sendMessage(
                                "[領地系統]  第 " + (res.getInt("Level") + 1) +
                                        " 層    x:" + res.getInt("Min_X") +
                                        " y:" + res.getInt("Min_Y") +
                                        " z:" + res.getInt("Min_Z") +
                                        "  到  x:" + res.getInt("Max_X") +
                                        " y:" + res.getInt("Max_Y") +
                                        " z:" + res.getInt("Max_Z") +
                                        "  ");
                        // 覆蓋前一次的
                        String ID = res.getString("ID");


                        event.getPlayer().sendMessage("[領地系統]  子圈地 起點設置在  X:" + X + " Y:" + Y + " Z:" + Z);

                        sta.executeUpdate("DELETE FROM `land-protection_new-land-level_cache` WHERE `Player` = '" + player.getName() + "'");
                        sta.executeUpdate("INSERT INTO `land-protection_new-land-level_cache` (`World`,`Player`, `Type`, `X`, `Y`, `Z`, `ID`) VALUES ('" + world + "','" + player.getName() + "', '1', '" + X + "', '" + Y + "', '" + Z + "','" + ID + "')");


                    } else {
                        // 失敗
                        player.sendMessage("[領地系統]  §c失敗 開始點 必須在你 有使用權 的領地");

                    }

                    res.close(); // 關閉查詢
                    sta.close(); // 關閉連線


                } else if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    // 右手
                    // 圈地終點

                    event.setCancelled(true);

                    int End_X = (int) event.getClickedBlock().getLocation().getX();
                    int End_Y = (int) event.getClickedBlock().getLocation().getY();
                    int End_Z = (int) event.getClickedBlock().getLocation().getZ();
                    world = event.getClickedBlock().getLocation().getWorld().getName();

                    Connection con = MySQL.getConnection(); // 連線 MySQL
                    Statement sta = con.createStatement(); // 取得控制庫
                    ResultSet res = sta.executeQuery("SELECT * FROM `land-protection_new-land-level_cache` WHERE `Player` = '" + player.getName() + "'");
                    // 跳到最後一行
                    res.last();
                    // 最後一行 行數 是否 > 0
                    if (res.getRow() > 0) {
                        // 跳回 初始行 必須使用 next() 才能取得資料
                        res.beforeFirst();
                        res.next();

                        // 是否同個世界
                        if (world.equals(res.getString("World"))) {
                            // 有起點
                            int X = res.getInt("X");
                            int Y = res.getInt("Y");
                            int Z = res.getInt("Z");
                            String ID = res.getString("ID");

                            // 格數是否大於 23000000 格
                            if ((Math.abs(End_X - X) + 1) * (Math.abs(End_Y - Y) + 1) * (Math.abs(End_Z - Z) + 1) > 23000000) {
                                // ! 超過
                                event.getPlayer().sendMessage("[領地系統]  §c太大了 總塊數已經超越 23,000,000 塊");
                            } else {
                                // 沒超越

                                res.close(); // 關閉查詢

                                res = sta.executeQuery("SELECT (Min_X),(Min_Y),(Min_Z),(Max_X),(Max_Y),(Max_Z),(Owner),(Level),(ID) FROM `land-protection_data` WHERE `World` = '" + world + "' AND `Min_X` <= '" + End_X + "' AND `Min_Y` <= '" + End_Y + "' AND `Min_Z` <= '" + End_Z + "' AND `Max_X` >= '" + End_X + "' AND `Max_Y` >= '" + End_Y + "' AND `Max_Z` >= '" + End_Z + "' AND `User` = '" + player.getName() + "' AND `ID` = '" + ID + "' LIMIT 1");
                                // 跳到最後一行
                                res.last();
                                if (res.getRow() > 0) {
                                    // 是 允許創建

                                    res.close(); // 關閉查詢

                                    // 進行寫入
                                    sta.executeUpdate("DELETE FROM `land-protection_new-land-level_cache` WHERE `Player` = '" + player.getName() + "'");
                                    sta.executeUpdate("INSERT INTO `land-protection_new-land-level_cache` (`World`, `Player`, `Type`, `X`, `Y`, `Z`, `End_X`, `End_Y`, `End_Z`, `ID`) VALUES ('" + world + "','" + player.getName() + "', '2', '" + X + "', '" + Y + "', '" + Z + "','" + End_X + "','" + End_Y + "','" + End_Z + "','" + ID + "')");


                                    // 計算
                                    // Math.ceil( ( ( End_X - X + 1 ) * ( End_Y - Y + 1 ) * ( End_Z - Z + 1 ) ) / 100 )
                                    //      X
                                    int Start_X;
                                    int Start_Y;
                                    int Start_Z;

                                    if (X < End_X) {
                                        Start_X = X;
                                    } else {
                                        Start_X = End_X;
                                        End_X = X;
                                    }
                                    //      Y
                                    if (Y < End_Y) {
                                        Start_Y = Y;
                                    } else {
                                        Start_Y = End_Y;
                                        End_Y = Y;
                                    }
                                    //      Z
                                    if (Z < End_Z) {
                                        Start_Z = Z;
                                    } else {
                                        Start_Z = End_Z;
                                        End_Z = Z;
                                    }
                                    event.getPlayer().sendMessage("[領地系統]  終點與起點間隔方塊數量  X:" + (Math.abs(End_X - Start_X) + 1) + " Y:" + (Math.abs(End_Y - Start_Y) + 1) + " Z:" + (Math.abs(End_Z - Start_Z) + 1) + "  共" + (Math.abs(End_X - Start_X) + 1) * (Math.abs(End_Y - Start_Y) + 1) * (Math.abs(End_Z - Start_Z) + 1) + "塊");


                                } else {
                                    // ! 禁止
                                    player.sendMessage("[領地系統]  §c失敗 結束點規劃在 其他領地/子領地內");
                                }

                            }

                        } else {
                            // ! 禁止不同世界
                            event.getPlayer().sendMessage("[領地系統]  §c禁止跨世界圈地");

                        }

                    } else {
                        // ! 沒起點
                        event.getPlayer().sendMessage("[領地系統]  §c請先左鍵設置 圈地起點");

                    }

                    res.close(); // 關閉查詢
                    sta.close(); // 關閉連線

                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
