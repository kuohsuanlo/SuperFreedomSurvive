package SuperFreedomSurvive.Player;

import SuperFreedomSurvive.SYSTEM.MySQL;
import SuperFreedomSurvive.World.Data;
import org.bukkit.Bukkit;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

final public class Delete {
    // 刪除玩家全部資料
    static final public void All(String name, String uuid) {
        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫
            ResultSet res;

            // 踢出玩家
            if (Bukkit.getServer().getPlayer(name) == null) {
                // ! 不在
            } else {
                // 線上 中
                Bukkit.getServer().getPlayer(name).getInventory().clear();
                Bukkit.getServer().getPlayer(name).kickPlayer("伺服器已經將你的資料數據刪除");
            }


            // 刪除數據庫全部 有此玩家的資料
            //sta.executeUpdate("DELETE FROM `arms_using-weapons_cooling_cache` WHERE `Player` = '" + name + "' ");
            //sta.executeUpdate("DELETE FROM `arms_using-weapons_sing_cache` WHERE `Player` = '" + name + "' ");
            //sta.executeUpdate("DELETE FROM `block-special_data` WHERE `World` = '"w_p"/" + name + "' ");
            sta.executeUpdate("DELETE FROM `player-kit_data` WHERE `Player` = '" + name + "' ");
            //ta.executeUpdate("DELETE FROM `land-share_data` WHERE `Player` = '" + name + "' ");
            //sta.executeUpdate("DELETE FROM `player-chat-record_data` WHERE `Player` = '" + name + "'");
            sta.executeUpdate("DELETE FROM `player-location_data` WHERE `Player` = '" + name + "' ");
            sta.executeUpdate("DELETE FROM `player-statistics_data` WHERE `Player` = '" + name + "' ");
            //sta.executeUpdate("DELETE FROM `player-permissions_data` WHERE `Player` = '" + name + "' ");
            sta.executeUpdate("DELETE FROM `player-additional_data` WHERE `Player` = '" + name + "' ");
            sta.executeUpdate("DELETE FROM `player-value_data` WHERE `Player` = '" + name + "' ");
            //sta.executeUpdate("DELETE FROM `player_data` WHERE `Player` = '" + name + "' ");
            sta.executeUpdate("DELETE FROM `player-warp_data` WHERE `Player` = '" + name + "' ");
            //sta.executeUpdate("DELETE FROM `world-list_data` WHERE `Owner` = '" + name + "' OR `User` = '" + name + "'");
            sta.executeUpdate("DELETE FROM `player-spawn-location_data` WHERE `Player` = '" + name + "' ");
            sta.executeUpdate("DELETE FROM `player-chatroom_data` WHERE `Player` = '" + name + "' ");

            SuperFreedomSurvive.Land.DeleteLand.ByPlayer(name);

            res = sta.executeQuery("SELECT * FROM `world-list_data` WHERE `Owner` = '" + name + "'");
            while (res.next()) {
                // 刪除個人空間
                //ServerPlugin.Land.DeleteLand.ByWorld(name);
                Data.Unload(res.getString("Name"), false);
                Data.Delete(res.getString("Name"));
            }

            res.close(); // 關閉查詢

            //Bukkit.unloadWorld( "w_p/" + name , false );

            // 刪除玩家全部儲存數據
            SuperFreedomSurvive.SYSTEM.File.Delete.DeleteAll(new File("./w/playerdata/" + uuid + ".dat"));
            SuperFreedomSurvive.SYSTEM.File.Delete.DeleteAll(new File("./w/stats/" + uuid + ".json"));
            SuperFreedomSurvive.SYSTEM.File.Delete.DeleteAll(new File("./w/advancements/" + uuid + ".json"));

            //ServerPlugin.SYSTEM.File.Delete.DeleteAll( new File("./"w_p"/" + name + "" ) );

            // 登記為刪除
            sta.executeUpdate("UPDATE `player_data` SET `Presence` = '0' WHERE `Player` = '" + name + "'");

            res.close(); // 關閉查詢
            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
