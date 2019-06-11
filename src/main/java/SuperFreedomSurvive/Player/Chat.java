package SuperFreedomSurvive.Player;

import SuperFreedomSurvive.SYSTEM.MySQL;
import net.md_5.bungee.api.chat.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

final public class Chat implements Listener {

    static final public void Distribution(AsyncPlayerChatEvent event) {
        event.setCancelled(true);

        // 是否為空白
        if (event.getMessage().equals("")) {
            return;

        } else if (event.getMessage().matches("\\s")) {
            return;

        }

        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫
            ResultSet res;

            // 檢查是否在聊天室
            res = sta.executeQuery("SELECT * FROM `player-chatroom_data` WHERE `Player` = '" + event.getPlayer().getName() + "' LIMIT 1");
            // 跳到最後一行
            res.last();
            // 最後一行 行數 是否 > 0
            if (res.getRow() > 0) {
                // 在聊天室

                res.close(); // 關閉查詢

                // 前軸是否為 ` 或是 ~
                if (event.getMessage().matches("(\\`|\\~).*")) {
                    // 公開頻道

                    event.setMessage((event.getMessage()).substring(1)); // 裁切字串

                    // 是否為空白
                    if (event.getMessage().equals("")) {
                        return;

                    } else if (event.getMessage().matches("\\s")) {
                        return;

                    }

                    public_send(event.getPlayer(), event.getMessage());// 公開頻道

                } else {
                    // 聊天室
                    chatroom_send(event.getPlayer(), "§b<" + event.getPlayer().getDisplayName() + "§b> " + event.getMessage());

                }

            } else {
                // 不再

                res.close(); // 關閉查詢

                public_send(event.getPlayer(), event.getMessage());// 公開頻道

            }

            res.close(); // 關閉查詢
            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // 公開頻道
    static final public void public_send(Player player, String chat) {
        try {

            chat = chat.replace("\\", "").replace("'", "").replace("\"", ""); // 安全過濾字串

            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫
            ResultSet res;

            // 檢查間隔
            Calendar calendar = Calendar.getInstance();

            // 30 秒內 是否超過 5 則訊息
            calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND) - 30);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1; // 1月的值為0
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int hour = calendar.get(Calendar.HOUR_OF_DAY); // HOUR_OF_DAY 24小時制    HOUR 12小時制
            int minute = calendar.get(Calendar.MINUTE);
            int second = calendar.get(Calendar.SECOND);
            String time = year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;
            res = sta.executeQuery("SELECT * FROM `player-chat-record_data` WHERE `Player` = '" + player.getName() + "' AND `Time` >= '" + time + "' LIMIT 6");
            res.last();
            // 最後一行 行數 是否 > 5
            if (res.getRow() >= 5) {
                // 有資料

                res.close(); // 關閉查詢

                player.sendMessage("[聊天系統]  §c發言太頻繁 請等待一段時間後再嘗試");

            } else {
                // 允許

                res.close(); // 關閉查詢

                // 檢查之前是否有相同發言
                res = sta.executeQuery("SELECT * FROM `player-chat-record_data` WHERE `Player` = '" + player.getName() + "' AND `Time` >= '" + time + "' AND `Content` = '" + chat + "' LIMIT 1");
                res.last();
                // 最後一行 行數 是否 > 0
                if (res.getRow() > 0) {

                    res.close(); // 關閉查詢

                    player.sendMessage("[聊天系統]  §c請不要重複發言相同的言論");

                } else {
                    // 允許

                    res.close(); // 關閉查詢

                    // 發送到後台
                    Bukkit.getLogger().info("(" + player.getName() + ")" + "<" + player.getDisplayName() + "> " + chat);

/*
                    // 轉 base64
                    final Base64.Decoder decoder = Base64.getDecoder();
                    final Base64.Encoder encoder = Base64.getEncoder();
                    final String text = ;
                    final byte[] textByte = text.getBytes("UTF-8");
                    //編碼
                    final String encodedText = encoder.encodeToString(textByte);
                    //System.out.println(encodedText);
                    //解碼
                    //System.out.println(new String(decoder.decode(encodedText), "UTF-8"));
                    */
/*
                    TextComponent component = new TextComponent("");
                    player.sendMessage( component.fromLegacyText("{\"text\":\"<" + player.getDisplayName() + "> " + chat + "\",\"color\":\"white\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/info player\"},\"hoverEvent\":{\"action\":\"show_item\",\"value\":\"minecraft:stick\"}}]") );
*/
                    ItemStack item = new ItemStack(Material.ENDER_PEARL);
                    ItemMeta meta = item.getItemMeta();
                    meta.setDisplayName("§e傳送系統");
                    meta.setLore(Arrays.asList(
                            ("§r§f - 傳送玩家")
                    ));
                    // 寫入資料
                    item.setItemMeta(meta);

                    // 發送特殊訊息
                    TextComponent text = new TextComponent("<" + player.getDisplayName() + "> " + chat);
                    text.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§fID: §a" + player.getName()).create()));
                    text.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/msg " + player.getName() + " "));
                    Collection collection = Bukkit.getServer().getOnlinePlayers();
                    Iterator iterator = collection.iterator();
                    // 總數
                    while (iterator.hasNext()) {
                        ((Player) iterator.next()).sendMessage(text);
                    }

                    // 改變資料
                    sta.executeUpdate("UPDATE `player-statistics_data` SET `Dialogue_Total` = `Dialogue_Total` + 1 WHERE `Player` = '" + player.getName() + "'");
                    // 加入紀錄
                    sta.executeUpdate("INSERT INTO `player-chat-record_data` (`Player`, `Time`, `Content`) VALUES ('" + player.getName() + "', '" + SuperFreedomSurvive.SYSTEM.Time.Get() + "', '" + chat + "')");

                    // 防掛機機制更新資料
                    SuperFreedomSurvive.Prohibited.StandingStill.Remove(player.getName());
                    SuperFreedomSurvive.Prohibited.StandingStill.Add(player);


                }
            }

            res.close(); // 關閉查詢
            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    // 聊天室發送
    static final public void chatroom_send(Player player, String chat) {

        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫
            ResultSet res = sta.executeQuery("SELECT * FROM `player-chatroom_data` WHERE `Player` = '" + player.getName() + "' LIMIT 1");
            // 跳到最後一行
            res.last();
            // 最後一行 行數 是否 > 0
            if (res.getRow() > 0) {
                // 已經有加入聊天室

                String ID = res.getString("ID");


                // 發送到後台
                Bukkit.getLogger().info("§b[" + ID + "](" + player.getName() + ")" + chat);

                res.close(); // 關閉查詢

                res = sta.executeQuery("SELECT * FROM `player-chatroom_data` WHERE `ID` = '" + ID + "' LIMIT 18");
                while (res.next()) {

                    // ---------------------------------------------------------------------------------------
                    // 判斷是否在線上
                    if (Bukkit.getServer().getPlayer(res.getString("Player")) == null) {
                        // ! 離線

                    } else {
                        // 線上狀態
                        // 發送特殊訊息
                        TextComponent text = new TextComponent(chat);
                        text.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§fID: §a" + player.getName()).create()));
                        text.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/msg " + player.getName() + " "));
                        Bukkit.getServer().getPlayer(res.getString("Player")).spigot().sendMessage(text);

                        //Bukkit.getServer().getPlayer(res.getString("Player")).sendMessage(chat);

                    }
                }
            }

            res.close(); // 關閉查詢
            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
