package SuperFreedomSurvive.Player;

final public class _Kit {
    /*
    static final public void Send(String numbering) {
        // 發放全體禮包

        new BukkitRunnable() {
            @Override
            final public void run() {
                try {

                    Connection con = MySQL.getConnection(); // 連線 MySQL
                    Statement sta = con.createStatement(); // 取得控制庫
                    ResultSet res = sta.executeQuery("SELECT * FROM `kit_data` WHERE `Numbering` = '" + numbering + "' LIMIT 1");
                    res.last();
                    // 最後一行 行數 是否 > 0
                    if (res.getRow() > 0) {
                        // 有資料

                        // 進行發放
                        res = sta.executeQuery("SELECT (Player) FROM `player_data` WHERE `Presence` = '1'");
                        ArrayList<String> all = new ArrayList<String>();
                        while (res.next()) {
                            all.add(res.getString("Player"));
                        }

                        res.close(); // 關閉查詢

                        // 開始取得內容
                        for (int i = 0, s = all.size(); i < s; i++) {
                            // 寫入禮包
                            sta.executeUpdate("INSERT INTO `kit_player_data` (`Player`, `Numbering`, `Time`) VALUES ('" + all.get(i) + "', '" + numbering + "', '" + ServerPlugin.SYSTEM.Time.Get() + "')");

                        }

                    }

                    res.close(); // 關閉查詢
                    sta.close(); // 關閉連線

                } catch (Exception ex) {
                    ex.printStackTrace();
                    cancel();
                    return;
                }

            }
        }.runTaskAsynchronously(ServerPlugin.SYSTEM.Plugin.get());

    }
*/
}
