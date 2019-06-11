package SuperFreedomSurvive.Player;

final public class _Violation {
    // 違規處禮

    /*
    // 增加違規紀錄
    static final public void Add(Player player, String reason, int severity) {
        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫

            // 創建資料
            sta.executeUpdate("INSERT INTO `player-violation_data` (`Player`, `Reason`, `Severity`, `Time`) VALUES ('" + player.getName() + "', '" + reason + "', '" + severity + "', '" + ServerPlugin.SYSTEM.Time.Get() + "')");

            sta.executeUpdate("UPDATE `player-statistics_data` SET `Violation_Fraction` = `Violation_Fraction` + " + severity + " WHERE `Player` = '" + player.getName() + "'");

            // 公開頻道公開違規
            if (severity <= 10) {
                Bukkit.broadcastMessage("玩家 " + player.getName() + " 碰觸" + reason + " 被登記違規");
            } else if (severity < 20) {
                Bukkit.broadcastMessage("玩家 " + player.getName() + " 碰觸" + reason + " 被登記嚴重違規");
            } else {
                Bukkit.broadcastMessage("玩家 " + player.getName() + " 碰觸" + reason + " 被登記最嚴重違規");
            }

            if (Exceed(player.getName())) {
                player.kickPlayer("違規已經超標");
            }

            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    // 檢查是否有超過
    static final public boolean Exceed(String player) {
        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫

            ResultSet res = sta.executeQuery("SELECT * FROM `player-statistics_data` WHERE `Player` = '" + player + "' LIMIT 1");
            // 跳到最後一行
            res.last();
            // 最後一行 行數 是否 > 0
            if (res.getRow() > 0) {
                // 有資料
                // 跳回 初始行 必須使用 next() 才能取得資料
                res.beforeFirst();
                res.next();

                int fraction = res.getInt("Violation_Fraction");

                // 公開頻道公開違規
                if (fraction >= 100) {

                    res.close(); // 關閉查詢
                    sta.close(); // 關閉連線

                    return true;
                } else {

                    res.close(); // 關閉查詢
                    sta.close(); // 關閉連線

                    return false;
                }
            }

            res.close(); // 關閉查詢
            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return false;
    }

     */

}
