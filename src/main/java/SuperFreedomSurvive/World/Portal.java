package SuperFreedomSurvive.World;

import SuperFreedomSurvive.SYSTEM.MySQL;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

final public class Portal implements Listener {

    // 玩家使用傳送門
    @EventHandler(priority = EventPriority.HIGH)
    final public void noPlayerPortalEvent(PlayerPortalEvent event) {

        event.useTravelAgent(false); // 不創建門戶

        // 檢查是否有權限
        //if ( ServerPlugin.Land.Permissions.Inspection( event.getPlayer() , event.getPlayer().getLocation() , "Permissions_PlayerPortal" ) ){
        // 有
        if (event.isCancelled()) {

        } else {
            try {
                Connection con = MySQL.getConnection(); // 連線 MySQL
                Statement sta = con.createStatement(); // 取得控制庫
                ResultSet res = sta.executeQuery("SELECT * FROM `world-list_data` WHERE `Name` = '" + event.getPlayer().getWorld().getName() + "' LIMIT 1");
                // 跳到最後一行
                res.last();
                // 最後一行 行數 是否 > 0
                if (res.getRow() > 0) {

                    // 有資料
                    // 跳回 初始行 必須使用 next() 才能取得資料
                    res.beforeFirst();
                    res.next();

                    if (res.getInt("Environment") == 1) {
                        // 已經在地獄
                        SuperFreedomSurvive.Player.Teleport.SpawnLocation(event.getPlayer(), "w");

                    } else if (res.getInt("Environment") == 2) {
                        // 已經在終界
                        SuperFreedomSurvive.Player.Teleport.SpawnLocation(event.getPlayer(), "w");

                    } else {
                        if (event.getPlayer().getWorld().getBlockAt(event.getPlayer().getLocation()).getType() == Material.NETHER_PORTAL) {
                            // 地域
                            SuperFreedomSurvive.Player.Teleport.SpawnLocation(event.getPlayer(), "w_nether");

                        } else if (event.getPlayer().getWorld().getBlockAt(event.getPlayer().getLocation()).getType() == Material.END_PORTAL) {
                            // 終界
                            SuperFreedomSurvive.Player.Teleport.SpawnLocation(event.getPlayer(), "w_the_end");

                        }
                    }
                }

                res.close(); // 關閉查詢
                sta.close(); // 關閉連線

            } catch (Exception ex) {
                ex.printStackTrace();
            }

            /*
            // 檢查玩家所在位置
            if (event.getPlayer().getWorld().getName().equals("w_nether")) {
                      // 已經在地獄
                        ServerPlugin.Player.Teleport.SpawnLocation(event.getPlayer(), "w");

            } else if (event.getPlayer().getWorld().getName().equals("w_the_end")) {
                // 已經在終界
                ServerPlugin.Player.Teleport.SpawnLocation(event.getPlayer(), "w");

            } else {
                // 都不是

                if (event.getPlayer().getWorld().getBlockAt(event.getPlayer().getLocation()).getType() == Material.NETHER_PORTAL) {
                    // 地域
                    ServerPlugin.Player.Teleport.SpawnLocation(event.getPlayer(), "w_nether");

                } else if (event.getPlayer().getWorld().getBlockAt(event.getPlayer().getLocation()).getType() == Material.END_PORTAL) {
                    // 終界
                    ServerPlugin.Player.Teleport.SpawnLocation(event.getPlayer(), "w_the_end");

                }
            }
        }
        */
            //} else {
            //    ServerPlugin.Land.Display.Message( event.getPlayer() ,"§c領地禁止 使用傳送門" );
            //}
        }
    }
}
