package SuperFreedomSurvive.Land;

import SuperFreedomSurvive.SYSTEM.MySQL;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

final public class DeleteLand {
    // 刪除領地

    // 使用ID
    static final public void ByID(String ID) {
        ByID(ID, true);
    }

    static final public void ByID(String ID, boolean itself) {
        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫
            ResultSet res = sta.executeQuery("SELECT (Min_X),(Min_Y),(Min_Z),(Max_X),(Max_Y),(Max_Z),(Owner),(User),(Level),(World),(Unable_Delete) FROM `land-protection_data` WHERE `ID` = '" + ID + "' LIMIT 1");
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

                res.close(); // 關閉查詢

            } else {

                res.close(); // 關閉查詢

                // 開始刪除資料
                new BukkitRunnable() {
                    @Override
                    final public void run() {
                        // 超複雜運算
                        // 建立新連線
                        try {
                            Connection con = MySQL.getConnection(); // 連線 MySQL
                            Statement sta = con.createStatement(); // 取得控制庫
                            ResultSet res;

                            res = sta.executeQuery("SELECT (Min_X),(Min_Y),(Min_Z),(Max_X),(Max_Y),(Max_Z),(Owner),(User),(Level),(World),(Unable_Delete),(ID) FROM `land-protection_data` WHERE `World` = '" + world + "' AND `Level` >= '" + level + "' AND `ID` != '" + ID + "' AND " +
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
                                    " ORDER BY `Level` LIMIT 100");

                            // 跳到最後一行
                            res.last();
                            // 最後一行 行數 是否 > 0
                            if (res.getRow() > 0) {
                                // 有資料
                                // 跳回 初始行 必須使用 next() 才能取得資料
                                res.beforeFirst();

                                while (res.next()) {

                                    SuperFreedomSurvive.Land.Display.Cancel_Add(
                                            res.getInt("Min_X"),
                                            res.getInt("Min_Y"),
                                            res.getInt("Min_Z"),
                                            res.getInt("Max_X"),
                                            res.getInt("Max_Y"),
                                            res.getInt("Max_Z"),
                                            res.getString("World")
                                    );

                                    String ID = res.getString("ID");
                                    new BukkitRunnable() {
                                        @Override
                                        final public void run() {
                                            // 超複雜運算
                                            // 建立新連線
                                            try {
                                                Connection con = MySQL.getConnection(); // 連線 MySQL
                                                Statement sta = con.createStatement(); // 取得控制庫

                                                // 刪除 領地資料
                                                sta.executeUpdate("DELETE FROM `land-protection_data` WHERE `ID` = '" + ID + "'");
                                                // 刪除 領地共用資料
                                                sta.executeUpdate("DELETE FROM `land-share_data` WHERE `ID` = '" + ID + "'");

                                                sta.close(); // 關閉連線

                                            } catch (Exception ex) {
                                                ex.printStackTrace();
                                            }
                                        }
                                    }.runTask(SuperFreedomSurvive.SYSTEM.Plugin.get());
                                }

                            } else {
                                if (itself) {
                                    // 刪除本身
                                    SuperFreedomSurvive.Land.Display.Cancel_Add(
                                            Start_X,
                                            Start_Y,
                                            Start_Z,
                                            End_X,
                                            End_Y,
                                            End_Z,
                                            world
                                    );

                                    // 刪除 領地資料
                                    sta.executeUpdate("DELETE FROM `land-protection_data` WHERE `ID` = '" + ID + "'");
                                    // 刪除 領地共用資料
                                    sta.executeUpdate("DELETE FROM `land-share_data` WHERE `ID` = '" + ID + "'");

                                }

                                res.close(); // 關閉查詢
                                sta.close(); // 關閉連線

                                cancel();
                                return;
                            }

                            res.close(); // 關閉查詢
                            sta.close(); // 關閉連線

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }.runTaskTimer(SuperFreedomSurvive.SYSTEM.Plugin.get(), 0L, 2L);
            }

            res.close(); // 關閉查詢
            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    // 全世界的都刪除
    static final public void ByWorld(String name) {
        new BukkitRunnable() {
            @Override
            final public void run() {
                try {
                    Connection con = MySQL.getConnection(); // 連線 MySQL
                    Statement sta = con.createStatement(); // 取得控制庫

                    ResultSet res = sta.executeQuery("SELECT (Min_X),(Min_Y),(Min_Z),(Max_X),(Max_Y),(Max_Z),(Owner),(User),(Level),(World),(Unable_Delete),(ID) FROM `land-protection_data` WHERE `World` = '" + name + "' LIMIT 100");
                    // 跳到最後一行
                    res.last();
                    // 最後一行 行數 是否 > 0
                    if (res.getRow() > 0) {
                        // 有資料
                        // 跳回 初始行 必須使用 next() 才能取得資料
                        res.beforeFirst();

                        while (res.next()) {

                            String ID = res.getString("ID");
                            new BukkitRunnable() {
                                @Override
                                final public void run() {
                                    // 超複雜運算
                                    // 建立新連線
                                    try {
                                        Connection con = MySQL.getConnection(); // 連線 MySQL
                                        Statement sta = con.createStatement(); // 取得控制庫
                                        // 刪除 領地資料
                                        sta.executeUpdate("DELETE FROM `land-protection_data` WHERE `ID` = '" + ID + "'");
                                        // 刪除 領地共用資料
                                        sta.executeUpdate("DELETE FROM `land-share_data` WHERE `ID` = '" + ID + "'");

                                        sta.close(); // 關閉連線

                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                    }
                                }
                            }.runTaskAsynchronously(SuperFreedomSurvive.SYSTEM.Plugin.get());
                        }

                    } else {

                        res.close(); // 關閉查詢
                        sta.close(); // 關閉連線

                        cancel();
                        return;
                    }

                    res.close(); // 關閉查詢
                    sta.close(); // 關閉連線

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }.runTaskTimer(SuperFreedomSurvive.SYSTEM.Plugin.get(), 0L, 2L);
    }


    // 使用玩家
    static final public void ByPlayer(String player) {
        // 開始刪除資料
        new BukkitRunnable() {

            @Override
            final public void run() {
                try {
                    Connection con = MySQL.getConnection(); // 連線 MySQL
                    Statement sta = con.createStatement(); // 取得控制庫

                    ResultSet res = sta.executeQuery("SELECT (Min_X),(Min_Y),(Min_Z),(Max_X),(Max_Y),(Max_Z),(Owner),(User),(Level),(World),(Unable_Delete),(ID) FROM `land-protection_data` WHERE `User` = '" + player + "' OR `Owner` = '" + player + "' LIMIT 100");
                    // 跳到最後一行
                    res.last();
                    // 最後一行 行數 是否 > 0
                    if (res.getRow() > 0) {
                        // 有資料
                        // 跳回 初始行 必須使用 next() 才能取得資料
                        res.beforeFirst();

                        while (res.next()) {

                            if (res.getString("Owner").equals(player)) {
                                ByID(res.getString("ID"), true);

                            } else if (
                                    !res.getString("Owner").equals(player) &&
                                     res.getString("User").equals(player)
                            ) {

                                String ID = res.getString("ID");
                                new BukkitRunnable() {
                                    @Override
                                    final public void run() {
                                        // 超複雜運算
                                        // 建立新連線
                                        try {
                                            Connection con = MySQL.getConnection(); // 連線 MySQL
                                            Statement sta = con.createStatement(); // 取得控制庫
                                            // 改寫資料
                                            sta.executeUpdate("UPDATE `land-protection_data` SET `User` = NULL WHERE `ID` = '" + ID + "'");
                                            // 刪除 領地共用資料
                                            sta.executeUpdate("DELETE FROM `land-share_data` WHERE `ID` = '" + ID + "'");

                                            sta.close(); // 關閉連線

                                        } catch (Exception ex) {
                                            ex.printStackTrace();
                                        }
                                    }
                                }.runTask(SuperFreedomSurvive.SYSTEM.Plugin.get());

                                ByID(res.getString("ID"), false);

                            } else {
                                ByID(res.getString("ID"), true);

                            }
                        }

                    } else {
                        // 刪除 領地共用資料
                        sta.executeUpdate("DELETE FROM `land-share_data` WHERE `Player` = '" + player + "'");

                        res.close(); // 關閉查詢
                        sta.close(); // 關閉連線

                        cancel();
                        return;
                    }

                    res.close(); // 關閉查詢
                    sta.close(); // 關閉連線

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }.runTaskTimer(SuperFreedomSurvive.SYSTEM.Plugin.get(), 0L, 2L);
    }
}
