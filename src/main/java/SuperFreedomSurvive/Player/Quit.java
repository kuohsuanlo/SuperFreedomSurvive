package SuperFreedomSurvive.Player;

import SuperFreedomSurvive.SYSTEM.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Collection;
import java.util.Iterator;

final public class Quit implements Listener {

    @EventHandler
    final public void onPlayerQuitEvent(PlayerQuitEvent event) {
        // 離開事件

        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫
            sta.executeUpdate("DELETE FROM `player-location_data` WHERE `Player` = '" + event.getPlayer().getName() + "'");
            sta.executeUpdate("INSERT INTO `player-location_data` (`Player`,`World`,`X`,`Y`,`Z`) VALUES ('" + event.getPlayer().getName() + "', '" + event.getPlayer().getLocation().getWorld().getName() + "', '" + event.getPlayer().getLocation().getX() + "', '" + event.getPlayer().getLocation().getY() + "', '" + event.getPlayer().getLocation().getZ() + "')");

            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    // 玩家全數離開
    static final public void All() {
        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫
            // 更新全部玩家最後登入時間 + 紀錄座標
            // 取得所有玩家清單
            Collection collection = Bukkit.getServer().getOnlinePlayers();
            Iterator iterator = collection.iterator();
            // 總數
            while (iterator.hasNext()) {
                Player player = ((Player) iterator.next());
                sta.executeUpdate("UPDATE `player_data` SET `Login_Last_Time` = '" + SuperFreedomSurvive.SYSTEM.Time.Get() + "' WHERE `Player` = '" + player.getName() + "'");


                sta.executeUpdate("DELETE FROM `player-location_data` WHERE `Player` = '" + player.getName() + "'");

                int X = new BigDecimal(player.getLocation().getX()).setScale(0, BigDecimal.ROUND_FLOOR).intValue();
                int Y = new BigDecimal(player.getLocation().getY()).setScale(0, BigDecimal.ROUND_FLOOR).intValue();
                int Z = new BigDecimal(player.getLocation().getZ()).setScale(0, BigDecimal.ROUND_FLOOR).intValue();
                String world = player.getLocation().getWorld().getName();
                sta.executeUpdate("INSERT INTO `player-location_data` (`Player`,`World`,`X`,`Y`,`Z`) VALUES ('" + player.getName() + "', '" + world + "', '" + X + "', '" + Y + "', '" + Z + "')");
            }

            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
