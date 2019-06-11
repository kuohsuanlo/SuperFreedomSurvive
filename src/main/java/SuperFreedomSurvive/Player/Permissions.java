package SuperFreedomSurvive.Player;

import org.bukkit.entity.Player;

final public class Permissions {

    static final public boolean Inspection(Player player, String name) {

        if (player != null && name != null) {
            // 檢查玩家是否有權限
            switch (name) {
                case "Permissions_Land":
                    if ( player.isOp() ) return true;
                    break;
                default:
                    return false;
            }

            /*
            try {
                Connection con = MySQL.getConnection(); // 連線 MySQL
                Statement sta = con.createStatement(); // 取得控制庫
                ResultSet res = sta.executeQuery("SELECT * FROM `player-permissions_data` WHERE `Player` = '" + player.getName() + "' AND `" + name + "` = '1' LIMIT 1");
                res.last();
                // 最後一行 行數 是否 > 0
                if (res.getRow() > 0) {
                    // 有資料

                    res.close(); // 關閉查詢
                    sta.close(); // 關閉連線

                    return true;
                } else {

                    res.close(); // 關閉查詢
                    sta.close(); // 關閉連線

                    return false;
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
        */
        } else {
            return false;
        }
        return false;
    }

}
