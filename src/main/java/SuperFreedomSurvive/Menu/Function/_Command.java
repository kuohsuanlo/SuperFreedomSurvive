package SuperFreedomSurvive.Menu.Function;

import SuperFreedomSurvive.SYSTEM.MySQL;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class _Command implements Listener {
    // 自訂命令

    final public static void menu(Player player) {
        page_0(player);
    }

    // 第一頁
    final public static void page_0(Player player) {


        GetItem(player,Material.DARK_OAK_DOOR,"開啟領地選單" , "Land#Menu" , null);
    }

    // 快速取得物品接口
    final public static ItemStack GetItem(Player player , Material material , String name , String type , List<String> lore) {

        ItemStack item = new ItemStack(material, 1);
        ItemMeta meta = item.getItemMeta();
        String cmd = null;

        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫
            ResultSet res = sta.executeQuery("SELECT * FROM `player-command_data` WHERE `Player` = '" + player.getName() + "' LIMIT 1");
            // 跳到最後一行
            res.last();
            // 最後一行 行數 是否 > 0
            if (res.getRow() > 0) {
                // 有資料
                // 跳回 初始行 必須使用 next() 才能取得資料
                res.beforeFirst();
                res.next();

                cmd = res.getString( "Command_" + type );

                res.close(); // 關閉查詢

            }


            res.close(); // 關閉查詢
            sta.close(); // 關閉連線

            meta.setDisplayName(name); // 設定顯示名稱

            if ( cmd != null ) {
                lore.add(0, "§r§f   當前設定: §e/" + cmd );
            } else {
                lore.add(0, "§r§f   當前設定: §e無" );
            }
            meta.setLore(lore); // 設定詳細說明

            item.setItemMeta(meta);

            return item;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

}
