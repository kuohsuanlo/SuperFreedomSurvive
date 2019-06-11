package SuperFreedomSurvive.Sign.Function;

import SuperFreedomSurvive.SYSTEM.MySQL;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.Statement;

final public class Spawn {
    // 重生點

    static final public void Use(Player player, Block block) {

        int X = (int) block.getLocation().getX();
        int Y = (int) block.getLocation().getY();
        int Z = (int) block.getLocation().getZ();
        String world = block.getLocation().getWorld().getName();

        // 取得字串內容
        Sign sign = (Sign) block.getState();
        // 取得告示牌內容
        String[] text = sign.getLines();

        if (block.getType() == Material.SIGN) {
            // 直立的
            //player.setBedSpawnLocation(block.getLocation());

            try {
                Connection con = MySQL.getConnection(); // 連線 MySQL
                Statement sta = con.createStatement(); // 取得控制庫

                sta.executeUpdate("DELETE FROM `player-spawn-location_data` WHERE `Player` = '" + player.getName() + "'");
                sta.executeUpdate("INSERT INTO `player-spawn-location_data` (`World`, `Player`, `X`, `Y`, `Z`, `Time`) VALUES ('" + world + "','" + player.getName() + "', '" + X + "', '" + Y + "', '" + Z + "', '" + SuperFreedomSurvive.SYSTEM.Time.Get() + "')");

                sta.close(); // 關閉連線

            } catch (Exception ex) {
                ex.printStackTrace();
            }

            player.sendMessage("[告示牌系統]  設置重生點成功");

        } else {
            player.sendMessage("[告示牌系統]  §c只能是直立告示牌");
        }
    }
}
