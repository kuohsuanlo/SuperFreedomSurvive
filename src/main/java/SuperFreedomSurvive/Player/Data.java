package SuperFreedomSurvive.Player;

import SuperFreedomSurvive.SYSTEM.MySQL;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Data {
    // 玩家暱稱

    static public String getNick(String name) {
        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫
            // 檢查是否有資料
            ResultSet res = sta.executeQuery("SELECT (Nick) FROM `player-value_data` WHERE `Player` = '" + name + "'");
            // 跳到最後一行
            res.last();
            // 最後一行 行數 是否 > 0
            if (res.getRow() > 0) {
                // 有資料
                // 跳回 初始行 必須使用 next() 才能取得資料
                res.beforeFirst();
                res.next();

                String nick = res.getString("Nick");
                if ( nick == null ) {
                    return name;
                }

                res.close(); // 關閉查詢
                sta.close(); // 關閉連線

                return nick;

            }

            res.close(); // 關閉查詢
            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    static public void setNick(String player,String nick) {
        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫

            // 改變資料
            if (nick == null) {
                sta.executeUpdate("UPDATE `player-value_data` SET `Nick` = NULL WHERE `Player` = '" + player + "'");
            } else {
                sta.executeUpdate("UPDATE `player-value_data` SET `Nick` = '" + nick + "' WHERE `Player` = '" + player + "'");
            }

            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }




    static public int getViewDistance(String name) {
        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫
            // 檢查是否有資料
            ResultSet res = sta.executeQuery("SELECT (ViewDistance) FROM `player-value_data` WHERE `Player` = '" + name + "'");
            // 跳到最後一行
            res.last();
            // 最後一行 行數 是否 > 0
            if (res.getRow() > 0) {
                // 有資料
                // 跳回 初始行 必須使用 next() 才能取得資料
                res.beforeFirst();
                res.next();

                int v = res.getInt("ViewDistance");

                res.close(); // 關閉查詢
                sta.close(); // 關閉連線

                return v;

            }

            res.close(); // 關閉查詢
            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }
    static public void setViewDistance(String player,int v) {
        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫

            // 改變資料
            sta.executeUpdate("UPDATE `player-value_data` SET `ViewDistance` = '" + v + "' WHERE `Player` = '" + player + "'");

            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
