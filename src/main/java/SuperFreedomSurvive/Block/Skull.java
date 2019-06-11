package SuperFreedomSurvive.Block;

import SuperFreedomSurvive.SYSTEM.MySQL;
import de.tr7zw.nbtapi.*;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

final public class Skull {
    static final public ItemStack Get(String uuid, String name, String[] lore) {
        try {
            // 取得資料
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫

            // 總數
            int total = 0;

            // 按照頁數取得 45 筆資料
            // 存入內容
            ResultSet res = sta.executeQuery("SELECT * FROM `store_skull-list_data` WHERE `UUID` = '" + uuid + "' LIMIT 1");
            res.last();
            // 最後一行 行數 是否 > 0
            if (res.getRow() > 0) {
                // 有資料
                // 跳回 初始行 必須使用 next() 才能取得資料
                res.beforeFirst();
                res.next();

                // 給予頭顱
                // ---------------------------------------------------------------------------------------
                ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1);

                NBTItem nbti = new NBTItem(item);

                NBTCompound disp = nbti.addCompound("display");
                disp.setString("Name", "{\"text\":\"" + name + "\"}");

                if (lore != null) {
                    NBTList<String> ntblist = disp.getStringList("Lore");
                    for (int i = 0, s = lore.length; i < s; ++i) {
                        ntblist.add(lore[i]);
                    }
                }

                NBTCompound skull = nbti.addCompound("SkullOwner");
                //skull.setString("Name", name);
                skull.setString("Id", res.getString("UUID"));

                NBTCompound texture = skull.addCompound("Properties").addCompound("textures");
                texture.setString("Value", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUv" + res.getString("Value"));


                //player.sendMessage( Bukkit.getServer().getPlayer( player_name ).getUniqueId().toString() );
                // 設置完成

                res.close(); // 關閉查詢
                sta.close(); // 關閉連線

                return nbti.getItem();
            }

            res.close(); // 關閉查詢
            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return null;
    }
}
