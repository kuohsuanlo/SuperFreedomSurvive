package SuperFreedomSurvive.Land;

import SuperFreedomSurvive.SYSTEM.MySQL;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

final public class Cache {
    // 緩存

    static final public void set(Player player, String ID) {
        // 設定緩存
        // 確認是否有層數
        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫

            // 建立緩存
            sta.executeUpdate("DELETE FROM `land-permissions_set-land_cache` WHERE `Player` = '" + player.getName() + "'");
            sta.executeUpdate("INSERT INTO `land-permissions_set-land_cache` (`Player`, `ID`) VALUES ('" + player.getName() + "', '" + ID + "')");

            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    static final public String get(Player player) {
        // 取得緩存
        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫
            ResultSet res = sta.executeQuery("SELECT * FROM `land-permissions_set-land_cache` WHERE `Player` = '" + player.getName() + "' LIMIT 1");
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
                sta.close(); // 關閉連線

                return ID;

            }

            res.close(); // 關閉查詢
            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return null;
    }

}
