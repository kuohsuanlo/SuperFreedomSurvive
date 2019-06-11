package SuperFreedomSurvive.Monster;

import SuperFreedomSurvive.SYSTEM.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Data {
    // 怪物控制

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
            ResultSet res = sta.executeQuery("SELECT (ID) FROM `custom-monster_data` WHERE `World` = '" + world + "' AND `X` = '" + X + "' AND `Y` = '" + Y + "' AND `Z` = '" + Z + "' LIMIT 1");
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
    static final public String Add(Location location) {

        int X = new BigDecimal(location.getX()).setScale(0, BigDecimal.ROUND_FLOOR).intValue();
        int Y = new BigDecimal(location.getY()).setScale(0, BigDecimal.ROUND_FLOOR).intValue();
        int Z = new BigDecimal(location.getZ()).setScale(0, BigDecimal.ROUND_FLOOR).intValue();
        String world = location.getWorld().getName();

        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫

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

            // 重新寫入資料
            sta.executeUpdate("DELETE FROM `custom-monster_data` WHERE `World` = '" + world + "' AND `X` = '" + X + "' AND `Y` = '" + Y + "' AND `Z` = '" + Z + "' ");
            sta.executeUpdate("INSERT INTO `custom-monster_data` (`World`,`X`,`Y`,`Z`,`ID`) VALUES ('" + world + "','" + X + "','" + Y + "','" + Z + "','" + random + "')");

            sta.close(); // 關閉連線

            return random;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


    // 是否有資料
    static final public String GetID(Location location) {

        int X = new BigDecimal(location.getX()).setScale(0, BigDecimal.ROUND_FLOOR).intValue();
        int Y = new BigDecimal(location.getY()).setScale(0, BigDecimal.ROUND_FLOOR).intValue();
        int Z = new BigDecimal(location.getZ()).setScale(0, BigDecimal.ROUND_FLOOR).intValue();
        String world = location.getWorld().getName();

        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫

            // 檢查是否資料符合
            ResultSet res = sta.executeQuery("SELECT (ID) FROM `custom-monster_data` WHERE `World` = '" + world + "' AND `X` = '" + X + "' AND `Y` = '" + Y + "' AND `Z` = '" + Z + "' LIMIT 1");
            // 跳到最後一行
            res.last();
            // 最後一行 行數 是否 > 0
            if (res.getRow() > 0) {
                // 有資料

                String ID = res.getString("ID");

                res.close(); // 關閉查詢
                sta.close(); // 關閉連線

                return ID;

            } else {
                // 無資料

                res.close(); // 關閉查詢
                sta.close(); // 關閉連線

                return null;

            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }


    // 是否有資料
    static final public Location GetLocation(String ID) {

        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫

            // 檢查是否資料符合
            ResultSet res = sta.executeQuery("SELECT (World),(X),(Y),(Z) FROM `custom-monster_data` WHERE `ID` = '" + ID + "' LIMIT 1");
            // 跳到最後一行
            res.last();
            // 最後一行 行數 是否 > 0
            if (res.getRow() > 0) {
                // 有資料

                Location location = new Location(
                        Bukkit.getWorld( res.getString("World") ),
                        res.getInt("X"),
                        res.getInt("Y"),
                        res.getInt("Z")
                );

                res.close(); // 關閉查詢
                sta.close(); // 關閉連線

                return location;

            } else {
                // 無資料

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
            sta.executeUpdate("DELETE FROM `custom-monster_data` WHERE `World` = '" + world + "' AND `X` = '" + X + "' AND `Y` = '" + Y + "' AND `Z` = '" + Z + "' ");

            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
