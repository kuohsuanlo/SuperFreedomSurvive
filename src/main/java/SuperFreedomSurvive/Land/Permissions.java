package SuperFreedomSurvive.Land;

import SuperFreedomSurvive.SYSTEM.MySQL;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

final public class Permissions {


    static final public boolean Inspection(Player player, Location location, String permissions) {

        // 是否強制允許
        if (SuperFreedomSurvive.Player.Permissions.Inspection(player, "Permissions_Land")) {
            return true;
        }

        if (location != null) {
            int X = new BigDecimal(location.getX()).setScale(0, BigDecimal.ROUND_FLOOR).intValue();
            int Y = new BigDecimal(location.getY()).setScale(0, BigDecimal.ROUND_FLOOR).intValue();
            int Z = new BigDecimal(location.getZ()).setScale(0, BigDecimal.ROUND_FLOOR).intValue();
            String world = location.getWorld().getName();

            return Inspection(player, world, X, Y, Z, permissions);
        }
        return false;
    }

    static final public boolean Inspection(Player player, String world, int X, int Y, int Z, String permissions) {

        // 是否強制允許
        if (SuperFreedomSurvive.Player.Permissions.Inspection(player, "Permissions_Land")) {
            return true;
        }

        // 檢查是否有權限
        boolean allow = false;
        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫

            // 檢查是否在保護區域內
            ResultSet res = sta.executeQuery("SELECT (" + permissions + "),(User),(Owner),(UserUsePermissions),(ID) FROM `land-protection_data` WHERE `World` = '" + world + "' AND `min_X` <= '" + X + "' AND `min_Y` <= '" + Y + "' AND `min_Z` <= '" + Z + "' AND `max_X` >= '" + X + "' AND `max_Y` >= '" + Y + "' AND `max_Z` >= '" + Z + "' ORDER BY `Level` DESC LIMIT 1");
            // 跳到最後一行
            res.last();
            // 最後一行 行數 是否 > 0
            if (res.getRow() > 0) {
                // 有資料
                // 跳回 初始行 必須使用 next() 才能取得資料
                res.beforeFirst();
                res.next();
                if (res.getBoolean(permissions)) {
                    // 權限 開放
                    allow = true;

                } else {
                    // 權限 封閉
                    if (player != null) {
                        // 檢查 使用者是否為自己 或是 領地擁有者為自己
                        if (player.getName().equals(res.getString("User")) || player.getName().equals(res.getString("Owner"))) {
                            // 是自己
                            // 自己是否也適用於權限控制
                            allow = !res.getBoolean("UserUsePermissions");
                        } else {
                            // 不是自己
                            // 檢查是否有共用權 或是 無視領地保護權限
                            // 允許此事件
                            if ( LandShare(player, res.getString("ID")) || SuperFreedomSurvive.Player.Permissions.Inspection(player, "Permissions_Land") ) {
                                allow = true;
                            }
                        }
                    } else {
                        // 不允許
                        allow = false;

                    }
                }

            } else {
                // 無資料
                allow = true;

            }

            res.close(); // 關閉查詢
            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
            allow = false;
        }
        return allow;

    }


    // 確定是否為共用人
    static final public boolean LandShare(Player player, Location location) {

        // 是否強制允許
        if (SuperFreedomSurvive.Player.Permissions.Inspection(player, "Permissions_Land")) {
            return true;
        }

        int X = new BigDecimal(location.getX()).setScale(0, BigDecimal.ROUND_FLOOR).intValue();
        int Y = new BigDecimal(location.getY()).setScale(0, BigDecimal.ROUND_FLOOR).intValue();
        int Z = new BigDecimal(location.getZ()).setScale(0, BigDecimal.ROUND_FLOOR).intValue();
        String world = location.getWorld().getName();

        try {

            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫

            // 檢查是否在保護區域內
            ResultSet res = sta.executeQuery("SELECT (ID) FROM `land-protection_data` WHERE `World` = '" + world + "' AND `min_X` <= '" + X + "' AND `min_Y` <= '" + Y + "' AND `min_Z` <= '" + Z + "' AND `max_X` >= '" + X + "' AND `max_Y` >= '" + Y + "' AND `max_Z` >= '" + Z + "' ORDER BY `Level` DESC LIMIT 1");
            // 跳到最後一行
            res.last();
            // 最後一行 行數 是否 > 0
            if (res.getRow() > 0) {
                // 有資料
                // 跳回 初始行 必須使用 next() 才能取得資料
                res.beforeFirst();
                res.next();

                String ID = res.getString("ID");

                res.close(); // 關閉查詢
                sta.close(); // 關閉連線

                return LandShare(player.getName(), ID);

            } else {

                res.close(); // 關閉查詢
                sta.close(); // 關閉連線

                return false;

            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    // 確定是否為共用人
    static final public boolean LandShare(Player player, String ID) {

        // 是否強制允許
        if (SuperFreedomSurvive.Player.Permissions.Inspection(player, "Permissions_Land")) {
            return true;
        }

        return LandShare(player.getName(), ID);

    }

    // 確定是否為共用人
    static final public boolean LandShare(String name, String ID) {

        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫

            // 檢查是否在保護區域內
            ResultSet res = sta.executeQuery("SELECT * FROM `land-share_data` WHERE `ID` = '" + ID + "' AND `Player` = '" + name + "' LIMIT 1");
            // 跳到最後一行
            res.last();
            // 最後一行 行數 是否 > 0
            if (res.getRow() > 0) {
                // 有資料

                res.close(); // 關閉查詢
                sta.close(); // 關閉連線

                return true;

            } else {
                // 無資料

                res.close(); // 關閉查詢
                sta.close(); // 關閉連線

                return false;

            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }


    // 檢查是否有領地
    static final public boolean Is(Location location) {

        int X = new BigDecimal(location.getX()).setScale(0, BigDecimal.ROUND_FLOOR).intValue();
        int Y = new BigDecimal(location.getY()).setScale(0, BigDecimal.ROUND_FLOOR).intValue();
        int Z = new BigDecimal(location.getZ()).setScale(0, BigDecimal.ROUND_FLOOR).intValue();
        String world = location.getWorld().getName();

        // 檢查是否有權限
        return Is(world, X, Y, Z);

    }

    // 檢查是否有領地
    static final public boolean Is(String world, int X, int Y, int Z) {
        // 檢查是否有權限
        boolean allow = false;
        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫

            // 檢查是否在保護區域內
            ResultSet res = sta.executeQuery("SELECT (ID) FROM `land-protection_data` WHERE `World` = '" + world + "' AND `min_X` <= '" + X + "' AND `min_Y` <= '" + Y + "' AND `min_Z` <= '" + Z + "' AND `max_X` >= '" + X + "' AND `max_Y` >= '" + Y + "' AND `max_Z` >= '" + Z + "' ORDER BY `Level` DESC LIMIT 1");
            // 跳到最後一行
            res.last();
            // 最後一行 行數 是否 > 0
            // 是否有資料
            allow = res.getRow() > 0;

            res.close(); // 關閉查詢
            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
            allow = false;

        }

        return allow;
    }


    // 檢查是否有編輯權限
    static final public boolean Ownership(Player player, Location location) {

        int X = new BigDecimal(location.getX()).setScale(0, BigDecimal.ROUND_FLOOR).intValue();
        int Y = new BigDecimal(location.getY()).setScale(0, BigDecimal.ROUND_FLOOR).intValue();
        int Z = new BigDecimal(location.getZ()).setScale(0, BigDecimal.ROUND_FLOOR).intValue();
        String world = location.getWorld().getName();

        return Ownership(player, world, X, Y, Z);
    }

    static final public boolean Ownership(Player player, String world, int X, int Y, int Z) {
        // 是否強制允許
        if (SuperFreedomSurvive.Player.Permissions.Inspection(player, "Permissions_Land")) {
            return true;
        }

        try {

            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫

            // 檢查是否在保護區域內
            ResultSet res = sta.executeQuery("SELECT (ID) FROM `land-protection_data` WHERE `World` = '" + world + "' AND `min_X` <= '" + X + "' AND `min_Y` <= '" + Y + "' AND `min_Z` <= '" + Z + "' AND `max_X` >= '" + X + "' AND `max_Y` >= '" + Y + "' AND `max_Z` >= '" + Z + "' ORDER BY `Level` DESC LIMIT 1");
            // 跳到最後一行
            res.last();
            // 最後一行 行數 是否 > 0
            if (res.getRow() > 0) {
                // 有資料
                // 跳回 初始行 必須使用 next() 才能取得資料
                res.beforeFirst();
                res.next();

                String ID = res.getString("ID");

                res.close(); // 關閉查詢
                sta.close(); // 關閉連線

                return Ownership(player, ID);

            } else {

                res.close(); // 關閉查詢
                sta.close(); // 關閉連線

                return false;

            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }


    // 檢查是否有編輯權限
    static final public boolean Ownership(Player player, String ID) {

        // 檢查是否有權限
        if (ID == null) {
            return false;

        } else {

            // 是否強制允許
            if (SuperFreedomSurvive.Player.Permissions.Inspection(player, "Permissions_Land")) {
                return true;
            }

            try {
                Connection con = MySQL.getConnection(); // 連線 MySQL
                Statement sta = con.createStatement(); // 取得控制庫

                // 檢查是否在保護區域內
                ResultSet res = sta.executeQuery("SELECT (Owner),(User) FROM `land-protection_data` WHERE `ID` = '" + ID + "' LIMIT 1");
                // 跳到最後一行
                res.last();
                // 最後一行 行數 是否 > 0
                if (res.getRow() > 0) {
                    // 跳回 初始行 必須使用 next() 才能取得資料
                    res.beforeFirst();
                    res.next();

                    // 檢查類型是否符合
                    if (
                            res.getString("Owner").equals(player.getName()) &&
                                    res.getString("User") == null
                    ) {
                        // 擁有者是自己 使用者沒有人

                        res.close(); // 關閉查詢
                        sta.close(); // 關閉連線

                        return true;

                    } else if (
                            res.getString("Owner").equals(player.getName()) &&
                                    res.getString("User").equals(player.getName())
                    ) {
                        // 擁有者 和 使用者都是自己

                        res.close(); // 關閉查詢
                        sta.close(); // 關閉連線

                        return true;

                    } else if (
                            res.getString("User").equals(player.getName())
                    ) {
                        // 使用者是自己

                        res.close(); // 關閉查詢
                        sta.close(); // 關閉連線

                        return true;

                    } else {
                        // 都不是

                        res.close(); // 關閉查詢
                        sta.close(); // 關閉連線

                        return false;

                    }
                } else {
                    // 無資料

                    res.close(); // 關閉查詢
                    sta.close(); // 關閉連線

                    return false;

                }

            } catch (Exception ex) {
                ex.printStackTrace();
                return false;

            }
        }
    }


    // 取得ID
    static final public String ID(Location location) {

        int X = new BigDecimal(location.getX()).setScale(0, BigDecimal.ROUND_FLOOR).intValue();
        int Y = new BigDecimal(location.getY()).setScale(0, BigDecimal.ROUND_FLOOR).intValue();
        int Z = new BigDecimal(location.getZ()).setScale(0, BigDecimal.ROUND_FLOOR).intValue();
        String world = location.getWorld().getName();

        return ID(world, X, Y, Z);
    }

    static final public String ID(String world, int X, int Y, int Z) {
        try {

            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫

            // 檢查是否在保護區域內
            ResultSet res = sta.executeQuery("SELECT (ID) FROM `land-protection_data` WHERE `World` = '" + world + "' AND `min_X` <= '" + X + "' AND `min_Y` <= '" + Y + "' AND `min_Z` <= '" + Z + "' AND `max_X` >= '" + X + "' AND `max_Y` >= '" + Y + "' AND `max_Z` >= '" + Z + "' ORDER BY `Level` DESC LIMIT 1");
            // 跳到最後一行
            res.last();
            // 最後一行 行數 是否 > 0
            if (res.getRow() > 0) {
                // 有資料
                // 跳回 初始行 必須使用 next() 才能取得資料
                res.beforeFirst();
                res.next();

                String ID = res.getString("ID");

                res.close(); // 關閉查詢
                sta.close(); // 關閉連線

                return ID;

            } else {

                res.close(); // 關閉查詢
                sta.close(); // 關閉連線

                return null;

            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }


    // 取得ID
    static final public int Level(String ID) {
        try {

            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫

            // 檢查是否在保護區域內
            ResultSet res = sta.executeQuery("SELECT (Level) FROM `land-protection_data` WHERE `ID` = '" + ID + "' LIMIT 1");
            // 跳到最後一行
            res.last();
            // 最後一行 行數 是否 > 0
            if (res.getRow() > 0) {
                // 有資料
                // 跳回 初始行 必須使用 next() 才能取得資料
                res.beforeFirst();
                res.next();

                int level = res.getInt("Level");

                res.close(); // 關閉查詢
                sta.close(); // 關閉連線

                return level;

            } else {

                res.close(); // 關閉查詢
                sta.close(); // 關閉連線

                return 0;

            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

}
