package SuperFreedomSurvive.Land;

import SuperFreedomSurvive.Player.Pay;
import SuperFreedomSurvive.SYSTEM.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static SuperFreedomSurvive.Menu.Function.Land.*;

public class Setting {


    public static void newLand(Player player) {
        // 創建領地
        
        try {
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

                int Start_X = 0;
                int Start_Y = 0;
                int Start_Z = 0;
                int X = res.getInt("X");
                int Y = res.getInt("Y");
                int Z = res.getInt("Z");
                int End_X = res.getInt("End_X");
                int End_Y = res.getInt("End_Y");
                int End_Z = res.getInt("End_Z");
                String world = res.getString("World");
                String player_name = res.getString("Player");


                if (res.getInt("Type") == 2) {

                    res.close(); // 關閉查詢

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
                    int need_amount = (int) Math.ceil((float) (End_X - Start_X + 1) * (End_Y - Start_Y + 1) * (End_Z - Start_Z + 1) / 10000);
                    // 計算數量是否足夠
                    if (Pay.Have(player, need_amount)) {
                        // 足夠
                        // 檢查是否有其他人的領地

                        // 超複雜運算
                        //      演算 全在裡面   及 覆蓋到其他領地
                        res = sta.executeQuery("SELECT (Min_X),(Min_Y),(Min_Z),(Max_X),(Max_Y),(Max_Z),(Owner),(User),(Level) FROM `land-protection_data` WHERE `World` = '" + world + "' AND `Level` = '0' AND " +
                                "  ( (   `Min_X` >= '" + Start_X + "' AND `Max_X` <= '" + End_X + "' )" +
                                " OR (   `Min_X` <= '" + Start_X + "' AND `Max_X` >= '" + End_X + "' )" +
                                " OR ( ( `Min_X` >= '" + Start_X + "' AND `Max_X` >= '" + End_X + "' ) AND `Min_X` <= '" + End_X + "' )  " +
                                " OR ( ( `Min_X` <= '" + Start_X + "' AND `Max_X` <= '" + End_X + "' ) AND `Max_X` >= '" + Start_X + "' ) ) AND (" +
                                "    (   `Min_Y` >= '" + Start_Y + "' AND `Max_Y` <= '" + End_Y + "' )" +
                                " OR (   `Min_Y` <= '" + Start_Y + "' AND `Max_Y` >= '" + End_Y + "' )" +
                                " OR ( ( `Min_Y` >= '" + Start_Y + "' AND `Max_Y` >= '" + End_Y + "' ) AND `Min_Y` <= '" + End_Y + "' )  " +
                                " OR ( ( `Min_Y` <= '" + Start_Y + "' AND `Max_Y` <= '" + End_Y + "' ) AND `Max_Y` >= '" + Start_Y + "' ) ) AND (" +
                                "    (   `Min_Z` >= '" + Start_Z + "' AND `Max_Z` <= '" + End_Z + "' )" +
                                " OR (   `Min_Z` <= '" + Start_Z + "' AND `Max_Z` >= '" + End_Z + "' )" +
                                " OR ( ( `Min_Z` >= '" + Start_Z + "' AND `Max_Z` >= '" + End_Z + "' ) AND `Min_Z` <= '" + End_Z + "' )  " +
                                " OR ( ( `Min_Z` <= '" + Start_Z + "' AND `Max_Z` <= '" + End_Z + "' ) AND `Max_Z` >= '" + Start_Z + "' ) ) " +
                                " ORDER BY `Level` LIMIT 20");
                        // 跳到最後一行
                        res.last();
                        // 最後一行 行數 是否 > 0
                        if (res.getRow() > 0) {
                            // ! 是 ! 禁止創建
                            // 跳回 初始行 必須使用 next() 才能取得資料
                            res.beforeFirst();
                            while (res.next()) {
                                // 領地類型
                                if (res.getString("User") == null) {
                                    // 出租領地
                                    player.sendMessage(
                                            "[領地系統]  等待規劃領地 (擁有者 §a" + res.getString("Owner") +
                                                    "§f)  x:" + res.getInt("Min_X") +
                                                    " y:" + res.getInt("Min_Y") +
                                                    " z:" + res.getInt("Min_Z") +
                                                    "  到  x:" + res.getInt("Max_X") +
                                                    " y:" + res.getInt("Max_Y") +
                                                    " z:" + res.getInt("Max_Z") +
                                                    "  "
                                    );
                                } else if (res.getString("Owner").equals(res.getString("User"))) {
                                    // User 與 Owner 同名稱
                                    // 有使用者
                                    player.sendMessage(
                                            "[領地系統]  §a" + res.getString("User") +
                                                    "§f 的領地  x:" + res.getInt("Min_X") +
                                                    " y:" + res.getInt("Min_Y") +
                                                    " z:" + res.getInt("Min_Z") +
                                                    "  到  x:" + res.getInt("Max_X") +
                                                    " y:" + res.getInt("Max_Y") +
                                                    " z:" + res.getInt("Max_Z") +
                                                    "  "
                                    );
                                } else {
                                    // User 與 Owner 同名稱
                                    // 有使用者
                                    player.sendMessage(
                                            "[領地系統]  §a" + res.getString("User") +
                                                    "§f 的領地 (擁有者 §a" + res.getString("Owner") +
                                                    "§f)  x:" + res.getInt("Min_X") +
                                                    " y:" + res.getInt("Min_Y") +
                                                    " z:" + res.getInt("Min_Z") +
                                                    "  到  x:" + res.getInt("Max_X") +
                                                    " y:" + res.getInt("Max_Y") +
                                                    " z:" + res.getInt("Max_Z") +
                                                    "  "
                                    );
                                }
                            }
                            player.sendMessage("[領地系統]  §c創建新領地 失敗 有其他領地在你圈的範圍內");

                        } else {
                            // 允許創建

                            res.close(); // 關閉查詢

                            // 收取綠寶石
                            if (Pay.Recover(player, need_amount)) {

                                // 創建唯一ID
                                String random = "";
                                String[] RegSNContent = {
                                        "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
                                        "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
                                        "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
                                        "_", "+", "-", ".", "~", "/", "=", "[", "]", "(", ")", "<", ">", "`", "!", "@", "#", "$", "^", "&", "*", "{", "}", ":", ";", "?", "|"
                                };
                                for (int i = 0; i < 20; i++)
                                    random += RegSNContent[(int) (Math.random() * RegSNContent.length)];


                                sta.executeUpdate("INSERT INTO `land-protection_data` (`Min_X`, `Min_Y`, `Min_Z`, `Max_X`, `Max_Y`, `Max_Z`, `Level`, `Owner`, `User`, `World`, `ID`, `Time`) VALUES ('" + Start_X + "', '" + Start_Y + "', '" + Start_Z + "', '" + End_X + "', '" + End_Y + "', '" + End_Z + "', '0', '" + player_name + "', '" + player_name + "', '" + world + "','" + random + "','" + SuperFreedomSurvive.SYSTEM.Time.Get() + "')");
                                player.sendMessage("[領地系統]  創建新領地成功!");

                                sta.executeUpdate("DELETE FROM `land-protection_new-land_cache` WHERE `Player` = '" + player.getName() + "'");

                            }
                        }

                        res.close(); // 關閉查詢
                        res.close(); // 關閉查詢

                    } else {

                        // ! 不夠
                        player.sendMessage("[領地系統]  §c創建新領地 所需物資不夠 需要 " + need_amount + " 個 綠寶石");
                    }

                    //ItemStack item = new ItemStack( Material.EMERALD, 64);
                    //inventory.remove(item);

                } else {
                    res.close(); // 關閉查詢

                    player.sendMessage("[領地系統]  §c請先圈好區域");
                }

            } else {
                player.sendMessage("[領地系統]  §c請先圈好區域");

            }

            res.close(); // 關閉查詢
            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
























    public static void newLandLevel(Player player) {
        // 創建子領地
        
        try {
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

                int Start_X = 0;
                int Start_Y = 0;
                int Start_Z = 0;
                int X = res.getInt("X");
                int Y = res.getInt("Y");
                int Z = res.getInt("Z");
                int End_X = res.getInt("End_X");
                int End_Y = res.getInt("End_Y");
                int End_Z = res.getInt("End_Z");
                String world = res.getString("World");
                String player_name = res.getString("Player");
                String ID = res.getString("ID");


                if (res.getInt("Type") == 2) {

                    res.close(); // 關閉查詢

                    // 計算
                    // Math.ceil( ( ( End_X - X + 1 ) * ( End_Y - Y + 1 ) * ( End_Z - Z + 1 ) ) / 100 )
                    //      X
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


                    res = sta.executeQuery("SELECT (Min_X),(Min_Y),(Min_Z),(Max_X),(Max_Y),(Max_Z),(Owner),(Level),(ID) FROM `land-protection_data` WHERE `World` = '" + world + "' AND `User` = '" + player.getName() + "' AND `ID` = '" + ID + "' ORDER BY `Level` DESC LIMIT 1");
                    // 跳到最後一行
                    res.last();
                    // 最後一行 行數 是否 > 0
                    if (res.getRow() > 0) {
                        int level = res.getInt("Level");

                        res.close(); // 關閉查詢

                        // 超複雜運算
                        //      演算 全在裡面   及 覆蓋到其他領地
                        res = sta.executeQuery("SELECT (Min_X),(Min_Y),(Min_Z),(Max_X),(Max_Y),(Max_Z),(Owner),(User),(Level) FROM `land-protection_data` WHERE `World` = '" + world + "' AND `Level` > '" + level + "' AND " +
                                "  ( (   `Min_X` >= '" + Start_X + "' AND `Max_X` <= '" + End_X + "' )" +
                                " OR (   `Min_X` <= '" + Start_X + "' AND `Max_X` >= '" + End_X + "' )" +
                                " OR ( ( `Min_X` >= '" + Start_X + "' AND `Max_X` >= '" + End_X + "' ) AND `Min_X` <= '" + End_X + "' )  " +
                                " OR ( ( `Min_X` <= '" + Start_X + "' AND `Max_X` <= '" + End_X + "' ) AND `Max_X` >= '" + Start_X + "' ) ) AND (" +
                                "    (   `Min_Y` >= '" + Start_Y + "' AND `Max_Y` <= '" + End_Y + "' )" +
                                " OR (   `Min_Y` <= '" + Start_Y + "' AND `Max_Y` >= '" + End_Y + "' )" +
                                " OR ( ( `Min_Y` >= '" + Start_Y + "' AND `Max_Y` >= '" + End_Y + "' ) AND `Min_Y` <= '" + End_Y + "' )  " +
                                " OR ( ( `Min_Y` <= '" + Start_Y + "' AND `Max_Y` <= '" + End_Y + "' ) AND `Max_Y` >= '" + Start_Y + "' ) ) AND (" +
                                "    (   `Min_Z` >= '" + Start_Z + "' AND `Max_Z` <= '" + End_Z + "' )" +
                                " OR (   `Min_Z` <= '" + Start_Z + "' AND `Max_Z` >= '" + End_Z + "' )" +
                                " OR ( ( `Min_Z` >= '" + Start_Z + "' AND `Max_Z` >= '" + End_Z + "' ) AND `Min_Z` <= '" + End_Z + "' )  " +
                                " OR ( ( `Min_Z` <= '" + Start_Z + "' AND `Max_Z` <= '" + End_Z + "' ) AND `Max_Z` >= '" + Start_Z + "' ) ) " +
                                " ORDER BY `Level` LIMIT 20");
                        // 跳到最後一行
                        res.last();
                        // 最後一行 行數 是否 > 0
                        if (res.getRow() > 0) {
                            // ! 是 ! 禁止創建
                            // 跳回 初始行 必須使用 next() 才能取得資料
                            res.beforeFirst();
                            while (res.next()) {
                                // 領地類型
                                if (res.getString("User") == null) {
                                    // 待規劃領地
                                    player.sendMessage(
                                            "[領地系統]  有人使用中 非擁有者本人 層數 " + (res.getInt("Level") + 1) +
                                                    "  x:" + res.getInt("Min_X") +
                                                    " y:" + res.getInt("Min_Y") +
                                                    " z:" + res.getInt("Min_Z") +
                                                    "  到  x:" + res.getInt("Max_X") +
                                                    " y:" + res.getInt("Max_Y") +
                                                    " z:" + res.getInt("Max_Z") +
                                                    "  "
                                    );
                                } else {
                                    // User 與 Owner 同名稱
                                    // 有使用者
                                    player.sendMessage(
                                            "[領地系統]  層數 " + (res.getInt("Level") + 1) +
                                                    "  x:" + res.getInt("Min_X") +
                                                    " y:" + res.getInt("Min_Y") +
                                                    " z:" + res.getInt("Min_Z") +
                                                    "  到  x:" + res.getInt("Max_X") +
                                                    " y:" + res.getInt("Max_Y") +
                                                    " z:" + res.getInt("Max_Z") +
                                                    "  "
                                    );
                                }
                                player.sendMessage("[領地系統]  §c創建新領地 失敗 有其他子領地在你圈的範圍內");

                            }

                        } else {
                            // 允許創建

                            res.close(); // 關閉查詢

                            // 創建唯一ID
                            String random = "";
                            String[] RegSNContent = {
                                    "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
                                    "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
                                    "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
                                    "_", "+", "-", ".", "~", "/", "=", "[", "]", "(", ")", "<", ">", "`", "!", "@", "#", "$", "^", "&", "*", "{", "}", ":", ";", "?", "|"
                            };
                            for (int i = 0; i < 20; i++)
                                random += RegSNContent[(int) (Math.random() * RegSNContent.length)];

                            sta.executeUpdate("INSERT INTO `land-protection_data` (`Min_X`, `Min_Y`, `Min_Z`, `Max_X`, `Max_Y`, `Max_Z`, `Level`, `Owner`, `User`, `World`, `ID`, `Time`) VALUES ('" + Start_X + "', '" + Start_Y + "', '" + Start_Z + "', '" + End_X + "', '" + End_Y + "', '" + End_Z + "', '" + (level + 1) + "', '" + player_name + "', '" + player_name + "', '" + world + "','" + random + "','" + SuperFreedomSurvive.SYSTEM.Time.Get() + "')");
                            player.sendMessage("[領地系統]  創建新子領地成功! 第 " + (level + 2) + " 層");

                            sta.executeUpdate("DELETE FROM `land-protection_new-land-level_cache` WHERE `Player` = '" + player.getName() + "'");

                        }

                        res.close(); // 關閉查詢

                    } else {
                        player.sendMessage("[領地系統]  §c失敗! 領地已經沒有使用權限 / 不存在");

                    }

                    res.close(); // 關閉查詢

                } else {
                    res.close(); // 關閉查詢

                    player.sendMessage("[領地系統]  §c請先圈好區域");

                }
            } else {
                player.sendMessage("[領地系統]  §c請先圈好區域");
            }


            res.close(); // 關閉查詢
            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

























    public static void extendLand(Player player,int distance) {
        // 延伸領地

        int X = new BigDecimal(player.getLocation().getX()).setScale(0, BigDecimal.ROUND_FLOOR).intValue();
        int Y = new BigDecimal(player.getLocation().getY()).setScale(0, BigDecimal.ROUND_FLOOR).intValue();
        int Z = new BigDecimal(player.getLocation().getZ()).setScale(0, BigDecimal.ROUND_FLOOR).intValue();
        String world = player.getLocation().getWorld().getName();

        // 確認是否有層數
        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫
            // 跳到最後一行
            ResultSet res = sta.executeQuery("SELECT (Owner),(User),(ID) FROM `land-protection_data` WHERE `World` = '" + world + "' AND `Min_X` <= '" + X + "' AND `Min_Y` <= '" + Y + "' AND `Min_Z` <= '" + Z + "' AND `Max_X` >= '" + X + "' AND `Max_Y` >= '" + Y + "' AND `Max_Z` >= '" + Z + "' AND `Level` = '0' LIMIT 1");
            res.last();
            // 最後一行 行數 是否 > 0
            if (res.getRow() > 0) {
                // 有領地

                // 跳回 初始行 必須使用 next() 才能取得資料
                res.beforeFirst();
                // 行數下一行
                res.next();

                String ID = res.getString("ID");

                res.close(); // 關閉查詢

                // 是否有權限
                if (SuperFreedomSurvive.Land.Permissions.Ownership(player, ID)) {
                    // 是

                    res.close(); // 關閉查詢

                    res = sta.executeQuery("SELECT (Min_X),(Min_Y),(Min_Z),(Max_X),(Max_Y),(Max_Z),(Owner),(User),(Level),(World),(Unable_Delete) FROM `land-protection_data` WHERE `ID` >= '" + ID + "' LIMIT 1");
                    res.last();

                    // 是否不可刪除
                    if (res.getBoolean("Unable_Delete")) {
                        // 不可刪除
                        player.sendMessage("[領地系統]  §c此領地不可延伸");

                        res.close(); // 關閉查詢

                    } else {

                        int Min_X = res.getInt("Min_X");
                        int Min_Y = res.getInt("Min_Y");
                        int Min_Z = res.getInt("Min_Z");
                        int Max_X = res.getInt("Max_X");
                        int Max_Y = res.getInt("Max_Y");
                        int Max_Z = res.getInt("Max_Z");
                        int New_Min_X = res.getInt("Min_X");
                        int New_Min_Y = res.getInt("Min_Y");
                        int New_Min_Z = res.getInt("Min_Z");
                        int New_Max_X = res.getInt("Max_X");
                        int New_Max_Y = res.getInt("Max_Y");
                        int New_Max_Z = res.getInt("Max_Z");

                        int wrld_size = SuperFreedomSurvive.World.Border.Size(player.getLocation().getWorld().getName());

                        res.close(); // 關閉查詢

                        double rotation = (player.getLocation().getYaw() - 90) % 360;
                        if (rotation < 0) {
                            rotation += 360.0;
                        }

                        if (player.getLocation().getPitch() >= 45) {
                            // y--
                            player.sendMessage("[領地系統]  向 -Y 延伸");
                            New_Min_Y = Min_Y - distance;
                            if (New_Min_Y < 0) {
                                player.sendMessage("[領地系統]  §c超出最低高度");
                                return;
                            }

                        } else if (player.getLocation().getPitch() <= -45) {
                            // y++
                            player.sendMessage("[領地系統]  向 +Y 延伸");
                            New_Max_Y = Max_Y + distance;
                            if (New_Max_Y > 255) {
                                player.sendMessage("[領地系統]  §c超出最高高度");
                                return;
                            }

                        } else if (rotation >= 315 || rotation <= 45) {
                            // x--
                            player.sendMessage("[領地系統]  向 -X 延伸");
                            New_Min_X = Min_X - distance;
                            if (New_Min_X < wrld_size * -1) {
                                player.sendMessage("[領地系統]  §c超出地圖邊界");
                                return;
                            }

                        } else if (rotation > 45 && rotation <= 135) {
                            // z--
                            player.sendMessage("[領地系統]  向 -Z 延伸");
                            New_Min_Z = Min_Z - distance;
                            if (New_Min_Z < wrld_size * -1) {
                                player.sendMessage("[領地系統]  §c超出地圖邊界");
                                return;
                            }

                        } else if (rotation > 135 && rotation <= 225) {
                            // x++
                            player.sendMessage("[領地系統]  向 +X 延伸");
                            New_Max_X = Max_X + distance;
                            if (New_Max_X > wrld_size) {
                                player.sendMessage("[領地系統]  §c超出地圖邊界");
                                return;
                            }

                        } else if (rotation > 225 && rotation <= 315) {
                            // z++
                            player.sendMessage("[領地系統]  向 +Z 延伸");
                            New_Max_Z = Max_Z + distance;
                            if (New_Max_Z > wrld_size) {
                                player.sendMessage("[領地系統]  §c超出地圖邊界");
                                return;
                            }

                        }

                        // 完成 計算空間差額
                        int need_amount = (int) Math.ceil(
                                Math.abs(
                                        ((float) (New_Max_X - New_Min_X + 1) * (float) (New_Max_Y - New_Min_Y + 1) * (float) (New_Max_Z - New_Min_Z + 1)) -
                                                ((float) (Max_X - Min_X + 1) * (float) (Max_Y - Min_Y + 1) * (float) (Max_Z - Min_Z + 1))
                                ) / 10000);

                        // 計算數量是否足夠
                        if (Pay.Have(player, need_amount)) {
                            // 足夠
                            // 檢查是否有其他人的領地

                            // 超複雜運算
                            //      演算 全在裡面   及 覆蓋到其他領地
                            res = sta.executeQuery("SELECT (Min_X),(Min_Y),(Min_Z),(Max_X),(Max_Y),(Max_Z),(Owner),(User),(Level) FROM `land-protection_data` WHERE `World` = '" + world + "' AND `Level` = '0' AND `ID` != '" + ID + "' AND " +
                                    "  ( (   `Min_X` >= '" + New_Min_X + "' AND `Max_X` <= '" + New_Max_X + "' )" +
                                    " OR (   `Min_X` <= '" + New_Min_X + "' AND `Max_X` >= '" + New_Max_X + "' )" +
                                    " OR ( ( `Min_X` >= '" + New_Min_X + "' AND `Max_X` >= '" + New_Max_X + "' ) AND `Min_X` <= '" + New_Max_X + "' )  " +
                                    " OR ( ( `Min_X` <= '" + New_Min_X + "' AND `Max_X` <= '" + New_Max_X + "' ) AND `Max_X` >= '" + New_Min_X + "' ) ) AND (" +
                                    "    (   `Min_Y` >= '" + New_Min_Y + "' AND `Max_Y` <= '" + New_Max_Y + "' )" +
                                    " OR (   `Min_Y` <= '" + New_Min_Y + "' AND `Max_Y` >= '" + New_Max_Y + "' )" +
                                    " OR ( ( `Min_Y` >= '" + New_Min_Y + "' AND `Max_Y` >= '" + New_Max_Y + "' ) AND `Min_Y` <= '" + New_Max_Y + "' )  " +
                                    " OR ( ( `Min_Y` <= '" + New_Min_Y + "' AND `Max_Y` <= '" + New_Max_Y + "' ) AND `Max_Y` >= '" + New_Min_Y + "' ) ) AND (" +
                                    "    (   `Min_Z` >= '" + New_Min_Z + "' AND `Max_Z` <= '" + New_Max_Z + "' )" +
                                    " OR (   `Min_Z` <= '" + New_Min_Z + "' AND `Max_Z` >= '" + New_Max_Z + "' )" +
                                    " OR ( ( `Min_Z` >= '" + New_Min_Z + "' AND `Max_Z` >= '" + New_Max_Z + "' ) AND `Min_Z` <= '" + New_Max_Z + "' )  " +
                                    " OR ( ( `Min_Z` <= '" + New_Min_Z + "' AND `Max_Z` <= '" + New_Max_Z + "' ) AND `Max_Z` >= '" + New_Min_Z + "' ) ) " +
                                    " ORDER BY `Level` LIMIT 20");
                            // 跳到最後一行
                            res.last();
                            // 最後一行 行數 是否 > 0
                            if (res.getRow() > 0) {
                                // ! 是 ! 禁止創建
                                // 跳回 初始行 必須使用 next() 才能取得資料
                                res.beforeFirst();
                                while (res.next()) {
                                    // 領地類型
                                    if (res.getString("User") == null) {
                                        // 出租領地
                                        player.sendMessage(
                                                "[領地系統]  等待規劃領地 (擁有者 §a" + res.getString("Owner") +
                                                        "§f)  x:" + res.getInt("Min_X") +
                                                        " y:" + res.getInt("Min_Y") +
                                                        " z:" + res.getInt("Min_Z") +
                                                        "  到  x:" + res.getInt("Max_X") +
                                                        " y:" + res.getInt("Max_Y") +
                                                        " z:" + res.getInt("Max_Z") +
                                                        "  "
                                        );
                                    } else if (res.getString("Owner").equals(res.getString("User"))) {
                                        // User 與 Owner 同名稱
                                        // 有使用者
                                        player.sendMessage(
                                                "[領地系統]  §a" + res.getString("User") +
                                                        "§f 的領地  x:" + res.getInt("Min_X") +
                                                        " y:" + res.getInt("Min_Y") +
                                                        " z:" + res.getInt("Min_Z") +
                                                        "  到  x:" + res.getInt("Max_X") +
                                                        " y:" + res.getInt("Max_Y") +
                                                        " z:" + res.getInt("Max_Z") +
                                                        "  "
                                        );
                                    } else {
                                        // User 與 Owner 同名稱
                                        // 有使用者
                                        player.sendMessage(
                                                "[領地系統]  §a" + res.getString("User") +
                                                        "§f 的領地 (擁有者 §a" + res.getString("Owner") +
                                                        "§f)  x:" + res.getInt("Min_X") +
                                                        " y:" + res.getInt("Min_Y") +
                                                        " z:" + res.getInt("Min_Z") +
                                                        "  到  x:" + res.getInt("Max_X") +
                                                        " y:" + res.getInt("Max_Y") +
                                                        " z:" + res.getInt("Max_Z") +
                                                        "  "
                                        );
                                    }
                                }
                                player.sendMessage("[領地系統]  §c延伸領地 失敗 有其他領地在你圈的範圍內");

                            } else {
                                // 允許創建

                                // 收取綠寶石
                                if (Pay.Recover(player, need_amount)) {

                                    if (need_amount <= 0) {
                                        player.sendMessage("[領地系統]  延伸領地成功!");
                                    } else {

                                        res.close(); // 關閉查詢

                                        sta.executeUpdate("UPDATE `land-protection_data` SET `Min_X` = '" + New_Min_X + "' , `Min_Y` = '" + New_Min_Y + "' , `Min_Z` = '" + New_Min_Z + "' , `Max_X` = '" + New_Max_X + "' , `Max_Y` = '" + New_Max_Y + "' , `Max_Z` = '" + New_Max_Z + "'  WHERE `ID` = '" + ID + "'");
                                        player.sendMessage("[領地系統]  延伸領地成功!");

                                        SuperFreedomSurvive.Land.Display.Cancel_Add(
                                                Min_X,
                                                Min_Y,
                                                Min_Z,
                                                Max_X,
                                                Max_Y,
                                                Max_Z,
                                                world
                                        );
                                    }
                                }
                            }

                            res.close(); // 關閉查詢

                        } else {
                            // ! 不夠
                            player.sendMessage("[領地系統]  §c延伸領地 所需物資不夠 需要 " + need_amount + " 個 綠寶石");
                        }

                    }

                    res.close(); // 關閉查詢

                } else {
                    player.sendMessage("[領地系統]  §c你無權限編輯");
                }
            } else {
                player.sendMessage("[領地系統]  §c你所站位置無領地");
            }

            res.close(); // 關閉查詢
            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

























    public static void conversionLandUserIsNull(Player player) {
        // 轉換為待分配
        
        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫
            ResultSet res;

            String ID = SuperFreedomSurvive.Land.Cache.get(player); // 取得緩存
            // 檢查是否有權限
            if (SuperFreedomSurvive.Land.Permissions.Ownership(player, ID)) {
                // 是

                res = sta.executeQuery("SELECT (Min_X),(Min_Y),(Min_Z),(Max_X),(Max_Y),(Max_Z),(Owner),(User),(Level),(World),(Unable_Delete) FROM `land-protection_data` WHERE `ID` = '" + ID + "' LIMIT 1");

                // 跳回 初始行 必須使用 next() 才能取得資料
                res.beforeFirst();
                res.next();

                int Start_X = res.getInt("Min_X");
                int Start_Y = res.getInt("Min_Y");
                int Start_Z = res.getInt("Min_Z");
                int End_X = res.getInt("Max_X");
                int End_Y = res.getInt("Max_Y");
                int End_Z = res.getInt("Max_Z");
                String world = res.getString("World");
                int level = res.getInt("Level");


                // 是否不可刪除
                if (res.getBoolean("Unable_Delete")) {
                    // 不可刪除
                    player.sendMessage("[領地系統]  §c此領地不可轉讓");

                    res.close(); // 關閉查詢

                } else {
                    player.sendMessage("[領地系統]  轉換為待分配成功");

                    res.close(); // 關閉查詢

                    // 超複雜運算
                    //      演算 全在裡面   及 覆蓋到的領地 通通轉移使用權
                    sta.executeUpdate("UPDATE `land-protection_data` SET `User` = NULL WHERE `World` = '" + world + "' AND `Level` >= '" + level + "' AND " +
                            "  ( (   `Min_X` >= '" + Start_X + "' AND `Max_X` <= '" + End_X + "' )" +
                            " OR (   `Min_X` <= '" + Start_X + "' AND `Max_X` >= '" + End_X + "' )" +
                            " OR ( ( `Min_X` >= '" + Start_X + "' AND `Max_X` >= '" + End_X + "' ) AND `Min_X` <= '" + End_X + "' )  " +
                            " OR ( ( `Min_X` <= '" + Start_X + "' AND `Max_X` <= '" + End_X + "' ) AND `Max_X` >= '" + Start_X + "' ) ) AND (" +
                            "    (   `Min_Y` >= '" + Start_Y + "' AND `Max_Y` <= '" + End_Y + "' )" +
                            " OR (   `Min_Y` <= '" + Start_Y + "' AND `Max_Y` >= '" + End_Y + "' )" +
                            " OR ( ( `Min_Y` >= '" + Start_Y + "' AND `Max_Y` >= '" + End_Y + "' ) AND `Min_Y` <= '" + End_Y + "' )  " +
                            " OR ( ( `Min_Y` <= '" + Start_Y + "' AND `Max_Y` <= '" + End_Y + "' ) AND `Max_Y` >= '" + Start_Y + "' ) ) AND (" +
                            "    (   `Min_Z` >= '" + Start_Z + "' AND `Max_Z` <= '" + End_Z + "' )" +
                            " OR (   `Min_Z` <= '" + Start_Z + "' AND `Max_Z` >= '" + End_Z + "' )" +
                            " OR ( ( `Min_Z` >= '" + Start_Z + "' AND `Max_Z` >= '" + End_Z + "' ) AND `Min_Z` <= '" + End_Z + "' )  " +
                            " OR ( ( `Min_Z` <= '" + Start_Z + "' AND `Max_Z` <= '" + End_Z + "' ) AND `Max_Z` >= '" + Start_Z + "' ) ) ");

                }

                res.close(); // 關閉查詢

            } else {
                player.sendMessage("[領地系統]  §c你無權限編輯");
            }

            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }




    public static void conversionLandUser(Player player,String player_key) {
        // 轉換領地給玩家
        
        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫
            ResultSet res;

            String ID = SuperFreedomSurvive.Land.Cache.get(player); // 取得緩存

            // 檢查是否有權限
            if (SuperFreedomSurvive.Land.Permissions.Ownership(player, ID)) {
                // 是

                // 查詢資料
                res = sta.executeQuery("SELECT (Min_X),(Min_Y),(Min_Z),(Max_X),(Max_Y),(Max_Z),(Owner),(User),(Level),(World),(Unable_Delete) FROM `land-protection_data` WHERE `ID` = '" + ID + "' LIMIT 1");

                // 跳回 初始行 必須使用 next() 才能取得資料
                res.beforeFirst();
                res.next();

                int Start_X = res.getInt("Min_X");
                int Start_Y = res.getInt("Min_Y");
                int Start_Z = res.getInt("Min_Z");
                int End_X = res.getInt("Max_X");
                int End_Y = res.getInt("Max_Y");
                int End_Z = res.getInt("Max_Z");
                String world = res.getString("World");
                int level = res.getInt("Level");


                // 是否不可刪除
                if (res.getBoolean("Unable_Delete")) {
                    // 不可刪除
                    player.sendMessage("[領地系統]  §c此領地不可轉讓");

                    res.close(); // 關閉查詢

                } else {

                    res.close(); // 關閉查詢

                    sta.executeUpdate("UPDATE `land-protection_data` SET `User` = '" + player_key + "' WHERE `ID` = '" + ID + "' LIMIT 1");

                    // 轉移給的玩家 顯示訊息
                    Player player_to = Bukkit.getServer().getPlayer(player_key);

                    player.sendMessage("[領地系統]  你將目前領地使用權限轉移給 §r§a" + player_key);

                    // 超複雜運算
                    //      演算 全在裡面   及 覆蓋到的領地 通通轉移使用權
                    sta.executeUpdate("UPDATE `land-protection_data` SET `User` = '" + player_key + "' WHERE `World` = '" + world + "' AND `Level` >= '" + level + "' AND " +
                            "  ( (   `Min_X` >= '" + Start_X + "' AND `Max_X` <= '" + End_X + "' )" +
                            " OR (   `Min_X` <= '" + Start_X + "' AND `Max_X` >= '" + End_X + "' )" +
                            " OR ( ( `Min_X` >= '" + Start_X + "' AND `Max_X` >= '" + End_X + "' ) AND `Min_X` <= '" + End_X + "' )  " +
                            " OR ( ( `Min_X` <= '" + Start_X + "' AND `Max_X` <= '" + End_X + "' ) AND `Max_X` >= '" + Start_X + "' ) ) AND (" +
                            "    (   `Min_Y` >= '" + Start_Y + "' AND `Max_Y` <= '" + End_Y + "' )" +
                            " OR (   `Min_Y` <= '" + Start_Y + "' AND `Max_Y` >= '" + End_Y + "' )" +
                            " OR ( ( `Min_Y` >= '" + Start_Y + "' AND `Max_Y` >= '" + End_Y + "' ) AND `Min_Y` <= '" + End_Y + "' )  " +
                            " OR ( ( `Min_Y` <= '" + Start_Y + "' AND `Max_Y` <= '" + End_Y + "' ) AND `Max_Y` >= '" + Start_Y + "' ) ) AND (" +
                            "    (   `Min_Z` >= '" + Start_Z + "' AND `Max_Z` <= '" + End_Z + "' )" +
                            " OR (   `Min_Z` <= '" + Start_Z + "' AND `Max_Z` >= '" + End_Z + "' )" +
                            " OR ( ( `Min_Z` >= '" + Start_Z + "' AND `Max_Z` >= '" + End_Z + "' ) AND `Min_Z` <= '" + End_Z + "' )  " +
                            " OR ( ( `Min_Z` <= '" + Start_Z + "' AND `Max_Z` <= '" + End_Z + "' ) AND `Max_Z` >= '" + Start_Z + "' ) ) ");


                    player_to.sendMessage(
                            "[領地系統]  世界 " + world +
                                    " 從 x " + Start_X +
                                    " y " + Start_Y +
                                    " z " + Start_Z +
                                    "  到 x " + End_X +
                                    " y " + End_Y +
                                    " z " + End_Z +
                                    "§r§f   一共 " + ((End_X - Start_X + 1) * (End_Y - Start_Y + 1) * (End_Z - Start_Z + 1) + " 塊"));
                    player_to.sendMessage("[領地系統]  §a" + player.getName() + "§f 將領地使用權限轉讓給了你");


                }

                res.close(); // 關閉查詢

            } else {
                player.sendMessage("[領地系統]  §c你無權限編輯");
            }

            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

























    public static void addLandShare(Player player,String player_key) {
        // 領地新增共用人
        
        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫
            ResultSet res;

            String ID = SuperFreedomSurvive.Land.Cache.get(player); // 取得緩存

            // 檢查是否有權限
            if (SuperFreedomSurvive.Land.Permissions.Ownership(player, ID)) {
                // 是

                // 點擊目標是否為自己
                if (player.getName().equals(player_key)) {
                    // ! 不允許自己新增自己
                    player.sendMessage("[領地系統]  玩家 §a" + player_key + " §f是你自己...");

                } else {
                    // 不是自己 允許繼續運行
                    // 檢查是否已經 > 44 個
                    res = sta.executeQuery("SELECT * FROM `land-share_data` WHERE `ID` = '" + ID + "' LIMIT 46");
                    res.last();
                    // 最後一行 行數 是否 > 44
                    if (res.getRow() > 44) {
                        // 超過
                        player.sendMessage("[領地系統]  §c共用人太多了 已經達到 45 位了");

                        res.close(); // 關閉查詢

                    } else {
                        // 沒超過

                        res.close(); // 關閉查詢

                        // 取得當前點擊的玩家名稱
                        // 判斷是否共用裡 已經有此玩家
                        //if (  )
                        //meta.getDisplayName().replace("§r§a", "");
                        res = sta.executeQuery("SELECT * FROM `land-share_data` WHERE `ID` = '" + ID + "' AND `Player` = '" + player_key + "' LIMIT 46");
                        res.last();
                        // 最後一行 行數 是否 > 0
                        if (res.getRow() > 0) {
                            // ! 已經有這名玩家了
                            player.sendMessage("[領地系統]  §c玩家 §a" + player_key + " §c已經在共用名單裡");

                        } else {
                            // 沒有 允許新增
                            // 寫入資料
                            sta.executeUpdate("INSERT INTO `land-share_data` (`ID`, `Player`) VALUES ('" + ID + "', '" + player_key + "')");
                            player.sendMessage("[領地系統]  領地共用 玩家 §a" + player_key + " §f新增成功");

                        }

                        res.close(); // 關閉查詢

                    }

                    res.close(); // 關閉查詢

                }

            } else {
                player.sendMessage("[領地系統]  §c你無權限編輯");
            }

            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }













    public static void removeLandShare(Player player,String player_key) {
        // 領地刪除共用人

        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫

            String ID = SuperFreedomSurvive.Land.Cache.get(player); // 取得緩存

            /// 檢查是否有權限
            if (SuperFreedomSurvive.Land.Permissions.Ownership(player, ID)) {
                // 是

                // 刪除該名玩家
                sta.executeUpdate("DELETE FROM `land-share_data` WHERE `Player` = '" + player_key + "' AND `ID` = '" + ID + "' LIMIT 1");

                player.sendMessage("[領地系統]  刪除成功");
                // 重開本介面
                land_share_delete(player);

            } else {
                player.sendMessage("[領地系統]  §c你無權限編輯");
            }

            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
