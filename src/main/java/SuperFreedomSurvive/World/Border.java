package SuperFreedomSurvive.World;

import SuperFreedomSurvive.SYSTEM.MySQL;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

final public class Border {
    // 邊界

    // 取得邊界
    static final public int Size(String name) {
        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫
            ResultSet res = sta.executeQuery("SELECT * FROM `world-list_data` WHERE `Name` = '" + name + "' LIMIT 1");
            // 跳到最後一行
            res.last();
            // 最後一行 行數 是否 > 0
            if (res.getRow() > 0) {

                // 有資料
                // 跳回 初始行 必須使用 next() 才能取得資料
                res.beforeFirst();
                res.next();

                int size = res.getInt("Size") / 2;

                res.close(); // 關閉查詢
                sta.close(); // 關閉連線

                return size;

            }

            res.close(); // 關閉查詢
            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
        return 0;
    }
}
