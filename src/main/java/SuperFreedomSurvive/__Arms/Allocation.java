package SuperFreedomSurvive.__Arms;

import SuperFreedomSurvive.SYSTEM.NamespacedKey;
import SuperFreedomSurvive.__Arms.Use.Magic;
import SuperFreedomSurvive.SYSTEM.MySQL;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.tags.ItemTagType;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

final public class Allocation implements Listener {
    // 事件
    @EventHandler
    final public void onPlayerInteractEvent(PlayerInteractEvent event) {

        // 確認是否為法杖
        try {

            if (event.getItem() == null) { return; }

            // 是否為右手使用
            if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                ItemStack item = event.getItem();
                ItemMeta meta = item.getItemMeta();

                // 檢查是否有指定數據
                if (meta.getCustomTagContainer().getCustomTag(NamespacedKey.getKey("Arms_ID"), ItemTagType.STRING) != null) {
                    String Arms_ID = meta.getCustomTagContainer().getCustomTag(NamespacedKey.getKey("Arms_ID"), ItemTagType.STRING);

                    Connection con = MySQL.getConnection(); // 連線 MySQL
                    Statement sta = con.createStatement(); // 取得控制庫
                    ResultSet res = sta.executeQuery("SELECT * FROM `arms_data` WHERE `Arms_ID` = '" + Arms_ID + "' LIMIT 1");
                    // 跳到最後一行
                    res.last();
                    // 最後一行 行數 是否 > 0
                    if (res.getRow() > 0) {
                        // 有資料
                        // 跳回 初始行 必須使用 next() 才能取得資料
                        res.beforeFirst();
                        res.next();

                        // 準備資料
                        int CD = res.getInt("CD");
                        int ST = res.getInt("ST");
                        int LV = res.getInt("LV");
                        String arms_type = res.getString("Arms_Type");
                        String attributes = res.getString("Attributes");
                        String skill = res.getString("Skill");
                        int distance = res.getInt("Distance");


                        res.close(); // 關閉查詢

                        // 確認當前無 詠唱
                        res = sta.executeQuery("SELECT * FROM `arms_using-weapons_sing_cache` WHERE `Player` = '" + event.getPlayer().getName() + "' AND `Time` >= '" + (new Date().getTime()) + "' LIMIT 1");
                        // 跳到最後一行
                        res.last();
                        // 最後一行 行數 是否 > 0
                        if (res.getRow() > 0) {
                            // ! 有資料 禁止使用

                            res.close(); // 關閉查詢

                        } else {
                            // 無資料 可以使用

                            res.close(); // 關閉查詢

                            // 確認當前無 CD冷卻中
                            res = sta.executeQuery("SELECT * FROM `arms_using-weapons_cooling_cache` WHERE `Player` = '" + event.getPlayer().getName() + "' AND `Arms_ID` = '" + Arms_ID + "' AND `Time` >= '" + (new Date().getTime()) + "' LIMIT 1");
                            // 跳到最後一行
                            res.last();
                            // 最後一行 行數 是否 > 0
                            if (res.getRow() > 0) {
                                // ! 有資料 禁止使用

                                res.close(); // 關閉查詢

                            } else {
                                // 無資料 可以使用

                                res.close(); // 關閉查詢

                                // 寫入詠唱緩存
                                // 先刪除舊的
                                sta.executeUpdate("DELETE FROM `arms_using-weapons_sing_cache` WHERE `Time` <= '" + (new Date().getTime()) + "'");
                                sta.executeUpdate("DELETE FROM `arms_using-weapons_cooling_cache` WHERE `Time` <= '" + (new Date().getTime()) + "'");
                                // 結束 時間寫入資料庫     總時間為 ( 技能冷卻 + 詠唱時間 )
                                sta.executeUpdate("INSERT INTO `arms_using-weapons_sing_cache` (`Player`, `Time`) VALUES ('" + event.getPlayer().getName() + "', " + (new Date().getTime() + (ST * 50)) + ")");
                                sta.executeUpdate("INSERT INTO `arms_using-weapons_cooling_cache` (`Player`, `Arms_ID`, `Time`) VALUES ('" + event.getPlayer().getName() + "', '" + Arms_ID + "' , " + (new Date().getTime() + (ST * 50) + (CD * 50)) + ")");


                                // 正式開始施放

                                // 檢查類型
                                if (arms_type.equals("magic")) {
                                    // 為魔法
                                    Magic.Start(event.getPlayer(), CD, ST, skill, LV, attributes, distance);

                                }
                            }
                        }
                    }

                    res.close(); // 關閉查詢
                    sta.close(); // 關閉連線

                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
