package SuperFreedomSurvive.SYSTEM;

import org.bukkit.configuration.file.FileConfiguration;

import java.sql.*;

final public class MySQL {

    // 聲明數據
    private static Connection con;
    //final public static Statement sta = Statement();
    //final public static ResultSet res;
    private static String URL = null;
    private static String USER = null;
    private static String PASSWORD = null;

    // 連線 MySQL
    final public static void connect() {
        // 取得配置文件
        FileConfiguration configuration = SuperFreedomSurvive.SYSTEM.Plugin.get().getConfig();
        String url = configuration.getString("URL");
        String user = configuration.getString("USER");
        String password = configuration.getString("PASSWORD");

        if ( url != null && user != null && password != null ) {

            MySQL.URL = url;
            MySQL.USER = user;
            MySQL.PASSWORD = password;

            if (!isConnected()) {
                try {
                    // 連線
                    con = DriverManager.getConnection(MySQL.URL, MySQL.USER, MySQL.PASSWORD);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    // 斷開 MySQL
    final public static void disconnect() {
        //if ( isConnected() ) {
        try {
            // 斷開連線
            con.close();
            con = null;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //}
    }

    // 回報 con 是否有連線 MySQL
    final public static boolean isConnected() {
        return con != null;
    }


    // 取得 con 庫
    final public static Connection getConnection() {
        if (isConnected()) {
            // 有連線 直接返回
            return con;
        } else {
            // 返回
            connect();
            return con;
        }
    }
}