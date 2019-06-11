package SuperFreedomSurvive.Player;

import SuperFreedomSurvive.SYSTEM.MySQL;
import SuperFreedomSurvive.World.Data;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

final public class Spawn implements Listener {

    @EventHandler
    final public void onPlayerRespawnEvent(PlayerRespawnEvent event) {
        // 玩家重生事件
        event.setRespawnLocation(Bukkit.getWorld("w").getSpawnLocation());

        Player player = event.getPlayer();

        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫

            ResultSet res = sta.executeQuery("SELECT * FROM `player-spawn-location_data` WHERE `Player` = '" + player.getName() + "'");
            // 跳到最後一行
            res.last();
            // 最後一行 行數 是否 > 0
            if (res.getRow() > 0) {
                // 有資料
                // 跳回 初始行 必須使用 next() 才能取得資料
                res.beforeFirst();
                res.next();

                if (!Data.Have(res.getString("World"))) {
                    return;
                }

                // 檢查是否有權限
                if (SuperFreedomSurvive.Land.Permissions.Inspection(
                        event.getPlayer(),
                        res.getString("World"),
                        res.getInt("X"),
                        res.getInt("Y"),
                        res.getInt("Z"),
                        "Permissions_PlayerRespawn"
                )) {
                    // 有
                    SuperFreedomSurvive.Player.Teleport.Location(
                            player,
                            res.getString("World"),
                            res.getInt("X"),
                            res.getInt("Y"),
                            res.getInt("Z")
                    );

                    res.close(); // 關閉查詢

                } else {
                    // 沒有

                    res.close(); // 關閉查詢

                    // 禁止事件
                    new BukkitRunnable() {
                        @Override
                        final public void run() {
                            SuperFreedomSurvive.Land.Display.Message(event.getPlayer(), "§c領地禁止 死亡後重生");
                        }
                    }.runTaskLater(SuperFreedomSurvive.SYSTEM.Plugin.get(), 40L);
                }
            }

            res.close(); // 關閉查詢
            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    @EventHandler
    final public void onPlayerBedEnterEvent(PlayerBedEnterEvent event) {
        // 玩家與床互動

        if (event.isCancelled()) {

        } else {

            // 檢查是否有權限
            if (SuperFreedomSurvive.Land.Permissions.Inspection(event.getPlayer(), event.getBed().getLocation(), "Permissions_PlayerBedEnter")) {
                // 有
                Player player = event.getPlayer();
                int X = (int) event.getBed().getLocation().getX();
                int Y = (int) event.getBed().getLocation().getY();
                int Z = (int) event.getBed().getLocation().getZ();
                String world = event.getBed().getLocation().getWorld().getName();

                try {
                    Connection con = MySQL.getConnection(); // 連線 MySQL
                    Statement sta = con.createStatement(); // 取得控制庫

                    sta.executeUpdate("DELETE FROM `player-spawn-location_data` WHERE `Player` = '" + player.getName() + "'");
                    sta.executeUpdate("INSERT INTO `player-spawn-location_data` (`World`, `Player`, `X`, `Y`, `Z`, `Time`) VALUES ('" + world + "','" + player.getName() + "', '" + X + "', '" + Y + "', '" + Z + "', '" + SuperFreedomSurvive.SYSTEM.Time.Get() + "')");

                    sta.close(); // 關閉連線

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
