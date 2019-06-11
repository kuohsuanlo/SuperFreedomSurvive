package SuperFreedomSurvive.__Arms;

import SuperFreedomSurvive.SYSTEM.MySQL;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

import java.util.Date;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

final public class PlayerSinging implements Listener {

    // 檢查是否在詠唱中 禁止 攻擊怪物 / 移動 / 使用指令 / 切換武器
/*
    @EventHandler
    final public void onPlayerMoveEvent(PlayerMoveEvent event){
        //  玩家移动事件
        org.bukkit.entity.Player player = event.getPlayer();
        if (
                event.getTo().getX() != event.getPlayer().getLocation().getX() ||
                event.getTo().getZ() != event.getPlayer().getLocation().getZ()
        ){
            try {
                Connection con = MySQL.getConnection(); // 連線 MySQL
                Statement sta = con.createStatement(); // 取得控制庫

                // 檢查是否在保護區域內
                ResultSet res = sta.executeQuery("SELECT * FROM `using-weapons_cache` WHERE `Player` = '" + player.getName() + "' AND `Time` >= '" + (new Date().getTime()) + "' LIMIT 1");
                // 跳到最後一行
                res.last();
                // 最後一行 行數 是否 > 0
                if (res.getRow() > 0) {
                    // 有資料
                    // 禁止事件 改動資料
                    event.setCancelled(true);


                } else {
                    // 無資料

                }
            } catch (Exception ex) {
                // 出錯
            }
        }
    }


    @EventHandler
    final public void onPlayerToggleFlightEvent(PlayerToggleFlightEvent event){
        //  玩家飛行事件
        org.bukkit.entity.Player player = event.getPlayer();

        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫

            // 檢查是否在保護區域內
            ResultSet res = sta.executeQuery("SELECT * FROM `using-weapons_cache` WHERE `Player` = '" + player.getName() + "' AND `Time` >= '" + ( new Date().getTime() ) + "' LIMIT 1");
            // 跳到最後一行
            res.last();
            // 最後一行 行數 是否 > 0
            if( res.getRow() > 0 ) {
                // 有資料
                // 禁止事件 改動資料
                event.setCancelled(true);


            } else {
                // 無資料

            }
        } catch (Exception ex) {
            // 出錯
        }
    }

*/
    @EventHandler
    final public void onPlayerItemHeldEvent(PlayerItemHeldEvent event) {
        //  玩家改變持有物品事件
        org.bukkit.entity.Player player = event.getPlayer();

        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫

            // 檢查是否在保護區域內
            ResultSet res = sta.executeQuery("SELECT * FROM `arms_using-weapons_sing_cache` WHERE `Player` = '" + player.getName() + "' AND `Time` >= '" + (new Date().getTime()) + "' LIMIT 1");
            // 跳到最後一行
            res.last();
            // 最後一行 行數 是否 > 0
            if (res.getRow() > 0) {
                // 有資料
                // 禁止事件 改動資料
                event.setCancelled(true);

            } else {
                // 無資料

            }

            res.close(); // 關閉查詢
            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

/*
    @EventHandler
    final public void onPlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent event){
        //  玩家使用指令事件
        org.bukkit.entity.Player player = event.getPlayer();

        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫

            // 檢查是否在保護區域內
            ResultSet res = sta.executeQuery("SELECT * FROM `using-weapons_cache` WHERE `Player` = '" + player.getName() + "' AND `Time` >= '" + ( new Date().getTime() ) + "' LIMIT 1");
            // 跳到最後一行
            res.last();
            // 最後一行 行數 是否 > 0
            if( res.getRow() > 0 ) {
                // 有資料
                // 禁止事件 改動資料
                event.setCancelled(true);


            } else {
                // 無資料

            }
        } catch (Exception ex) {
            // 出錯
        }
    }


    @EventHandler
    final public void onPlayerInteractEvent(PlayerInteractEvent event){
        //  玩家使用容器事件
        org.bukkit.entity.Player player = event.getPlayer();

        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫

            // 檢查是否在保護區域內
            ResultSet res = sta.executeQuery("SELECT * FROM `using-weapons_cache` WHERE `Player` = '" + player.getName() + "' AND `Time` >= '" + ( new Date().getTime() ) + "' LIMIT 1");
            // 跳到最後一行
            res.last();
            // 最後一行 行數 是否 > 0
            if( res.getRow() > 0 ) {
                // 有資料
                // 禁止事件 改動資料
                event.setCancelled(true);


            } else {
                // 無資料

            }
        } catch (Exception ex) {
            // 出錯
        }
    }
*/


}
