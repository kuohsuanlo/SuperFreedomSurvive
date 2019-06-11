package SuperFreedomSurvive.Block;

import SuperFreedomSurvive.SYSTEM.MySQL;
import org.bukkit.Location;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

final public class Data {
    // 方塊特殊資料儲存界面


    // 是否相符
    static final public boolean Match(Location location, String kay) {

        int X = new BigDecimal(location.getX()).setScale(0, BigDecimal.ROUND_FLOOR).intValue();
        int Y = new BigDecimal(location.getY()).setScale(0, BigDecimal.ROUND_FLOOR).intValue();
        int Z = new BigDecimal(location.getZ()).setScale(0, BigDecimal.ROUND_FLOOR).intValue();
        String world = location.getWorld().getName();

        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫

            // 檢查是否資料符合
            ResultSet res = sta.executeQuery("SELECT (Data) FROM `block-special_data` WHERE `World` = '" + world + "' AND `X` = '" + X + "' AND `Y` = '" + Y + "' AND `Z` = '" + Z + "' AND `Data` = '" + Conversion(kay) + "' LIMIT 1");
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


    // 是否有資料
    static final public boolean Have(Location location) {

        int X = new BigDecimal(location.getX()).setScale(0, BigDecimal.ROUND_FLOOR).intValue();
        int Y = new BigDecimal(location.getY()).setScale(0, BigDecimal.ROUND_FLOOR).intValue();
        int Z = new BigDecimal(location.getZ()).setScale(0, BigDecimal.ROUND_FLOOR).intValue();
        String world = location.getWorld().getName();

        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫

            // 檢查是否資料符合
            ResultSet res = sta.executeQuery("SELECT (Data) FROM `block-special_data` WHERE `World` = '" + world + "' AND `X` = '" + X + "' AND `Y` = '" + Y + "' AND `Z` = '" + Z + "' LIMIT 1");
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


    // 設定資料
    static final public void Set(Location location, String key) {

        int X = new BigDecimal(location.getX()).setScale(0, BigDecimal.ROUND_FLOOR).intValue();
        int Y = new BigDecimal(location.getY()).setScale(0, BigDecimal.ROUND_FLOOR).intValue();
        int Z = new BigDecimal(location.getZ()).setScale(0, BigDecimal.ROUND_FLOOR).intValue();
        String world = location.getWorld().getName();

        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫

            // 重新寫入資料
            sta.executeUpdate("DELETE FROM `block-special_data` WHERE `World` = '" + world + "' AND `X` = '" + X + "' AND `Y` = '" + Y + "' AND `Z` = '" + Z + "' ");
            sta.executeUpdate("INSERT INTO `block-special_data` (`World`,`X`,`Y`,`Z`,`Data`) VALUES ('" + world + "','" + X + "','" + Y + "','" + Z + "','" + Conversion(key) + "')");

            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    // 設定資料
    static final public String Get(Location location) {

        int X = new BigDecimal(location.getX()).setScale(0, BigDecimal.ROUND_FLOOR).intValue();
        int Y = new BigDecimal(location.getY()).setScale(0, BigDecimal.ROUND_FLOOR).intValue();
        int Z = new BigDecimal(location.getZ()).setScale(0, BigDecimal.ROUND_FLOOR).intValue();
        String world = location.getWorld().getName();

        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫

            // 重新寫入資料
            // 取得資料
            ResultSet res = sta.executeQuery("SELECT (Data) FROM `block-special_data` WHERE `World` = '" + world + "' AND `X` = '" + X + "' AND `Y` = '" + Y + "' AND `Z` = '" + Z + "' LIMIT 1");
            res.last();
            // 最後一行 行數 是否 > 0
            if (res.getRow() > 0) {
                // 有資料
                // 跳回 初始行 必須使用 next() 才能取得資料
                res.beforeFirst();
                res.next();

                int data = res.getInt("Data");

                res.close(); // 關閉查詢
                sta.close(); // 關閉連線

                return Conversion(data);

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


    // 移除資料
    static final public void Remove(Location location) {

        int X = new BigDecimal(location.getX()).setScale(0, BigDecimal.ROUND_FLOOR).intValue();
        int Y = new BigDecimal(location.getY()).setScale(0, BigDecimal.ROUND_FLOOR).intValue();
        int Z = new BigDecimal(location.getZ()).setScale(0, BigDecimal.ROUND_FLOOR).intValue();
        String world = location.getWorld().getName();

        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫

            // 重新寫入資料
            sta.executeUpdate("DELETE FROM `block-special_data` WHERE `World` = '" + world + "' AND `X` = '" + X + "' AND `Y` = '" + Y + "' AND `Z` = '" + Z + "' ");

            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    // 轉換列表 為了節省資料
    static final public int Conversion(String original) {
        switch (original) {
            case "lucky_block":
                return 1;
            case "spawner_block":
                return 2;
            case "custom_spawner_block":
                return 3;
        }
        return 0;
    }

    static final public String Conversion(int original) {
        switch (original) {
            case 1:
                return "lucky_block";
            case 2:
                return "spawner_block";
            case 3:
                return "custom_spawner_block";
        }
        return null;
    }

}
