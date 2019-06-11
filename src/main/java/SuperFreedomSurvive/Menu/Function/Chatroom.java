package SuperFreedomSurvive.Menu.Function;

import SuperFreedomSurvive.Player.Data;
import SuperFreedomSurvive.SYSTEM.MySQL;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.inventory.meta.tags.ItemTagType;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

final public class Chatroom implements Listener {
    // 聊天室








    static final public org.bukkit.NamespacedKey player_key = SuperFreedomSurvive.SYSTEM.NamespacedKey.getKey("Player");




    final public static void menu(Player player) {
        // 顯示容器
        Inventory inv = Bukkit.createInventory(null, 27, "§z聊天室系統");

        ItemStack item;
        ItemMeta meta;

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

                res.close(); // 關閉查詢

                SkullMeta skull_meta;
                int total = 0;
                res = sta.executeQuery("SELECT * FROM `player-chatroom_data` WHERE `ID` = '" + ID + "' LIMIT 18");
                while (res.next()) {

                    // ---------------------------------------------------------------------------------------
                    // 判斷是否在線上
                    if (Bukkit.getServer().getPlayer(res.getString("Player")) == null) {
                        // ! 離線
                        // a130a76d-3dab-429e-bd17-3b2b84201f80
                        inv.setItem(total, SuperFreedomSurvive.Block.Skull.Get("a130a76d-3dab-429e-bd17-3b2b84201f80", "§r§f" + SuperFreedomSurvive.Player.Data.getNick(res.getString("Player")) + " §r§c(離線中)", new String[]{"§r§f   ID: §a" + res.getString("Player")}));

                    } else {
                        // 線上狀態
                        item = new ItemStack(Material.PLAYER_HEAD);
                        meta = item.getItemMeta();
                        meta.setDisplayName("§r§f" + SuperFreedomSurvive.Player.Data.getNick(res.getString("Player")));
                        meta.setLore(Arrays.asList(
                                ("§r§f   ID: §a" + res.getString("Player"))
                        ));

                        // 寫入資料
                        item.setItemMeta(meta);

                        skull_meta = (SkullMeta) item.getItemMeta();
                        // 取得玩家名稱 並獲取UUID 設置為頭顱新的主人
                        skull_meta.setOwningPlayer(Bukkit.getPlayer(res.getString("Player")));
                        item.setItemMeta(skull_meta);
                        //player.sendMessage( Bukkit.getServer().getPlayer( player_name ).getUniqueId().toString() );
                        // 設置完成
                        inv.setItem(total, item);
                    }

                    // 總數 + 1
                    ++total;

                }

                res.close(); // 關閉查詢

                // 離開聊天室
                // --------------------------------------     19     --------------------------------------
                item = new ItemStack(Material.TROPICAL_FISH_BUCKET);
                meta = item.getItemMeta();
                meta.setDisplayName("§e邀請玩家");
                // 寫入資料
                item.setItemMeta(meta);
                // 設置完成
                inv.setItem(19, item);

                // 離開聊天室
                // --------------------------------------     20     --------------------------------------
                item = new ItemStack(Material.LAVA_BUCKET);
                meta = item.getItemMeta();
                meta.setDisplayName("§e離開聊天室");
                // 寫入資料
                item.setItemMeta(meta);
                // 設置完成
                inv.setItem(20, item);


            } else {
                // 還沒有加入聊天室
                for (int i = 0; i < 18; ++i) {
                    item = new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE);
                    meta = item.getItemMeta();
                    meta.setDisplayName("§e請先創建聊天室/加入聊天室");
                    // 寫入資料
                    item.setItemMeta(meta);
                    // 設置完成
                    inv.setItem(i, item);

                }
            }

            res.close(); // 關閉查詢
            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }


        // 創建聊天室
        // --------------------------------------     18     --------------------------------------
        item = new ItemStack(Material.WATER_BUCKET);
        meta = item.getItemMeta();
        meta.setDisplayName("§e創建聊天室");
        // 寫入資料
        item.setItemMeta(meta);
        // 設置完成
        inv.setItem(18, item);


        // --------------------------------------     25     --------------------------------------
        inv.setItem(25, SuperFreedomSurvive.Menu.Open.PreviousPage());


        // --------------------------------------     26     --------------------------------------
        inv.setItem(26, SuperFreedomSurvive.Menu.Open.TurnOff());



        /* new PotionEffect( new PotionEffectType.ABSORPTION) */

        // 寫入到容器頁面
        player.openInventory(inv);

    }


    // 阻止玩家拿走物品
    @EventHandler
    final public void onInventoryClickEvent(InventoryClickEvent event) {
        // 判斷是否為玩家

        if (event.getWhoClicked() instanceof Player) {
            // 是玩家
            Player player = (Player) event.getWhoClicked();

            // 檢查容器名稱
            if (event.getView().getTitle().equalsIgnoreCase("§z聊天室系統")) {
                // 是沒錯

                event.setCancelled(true);


                if (event.getRawSlot() == 0) {


                } else if (event.getRawSlot() == 18) {
                    // 創建聊天室

                    // 檢查是否不再聊天室內
                    try {
                        Connection con = MySQL.getConnection(); // 連線 MySQL
                        Statement sta = con.createStatement(); // 取得控制庫
                        ResultSet res = sta.executeQuery("SELECT * FROM `player-chatroom_data` WHERE `Player` = '" + player.getName() + "' LIMIT 1");
                        // 跳到最後一行
                        res.last();
                        // 最後一行 行數 是否 > 0
                        if (res.getRow() > 0) {
                            // 已經有加入聊天室
                            player.sendMessage("[聊天室]  §c你已經在聊天室中");

                        } else {
                            // 創建新聊天室

                            res.close(); // 關閉查詢

                            // 創建唯一ID
                            String random = "";
                            String[] RegSNContent = {
                                    "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
                                    "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
                                    "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
                            };
                            for (int i = 0; i < 10; i++)
                                random += RegSNContent[(int) (Math.random() * RegSNContent.length)];

                            sta.executeUpdate("INSERT INTO `player-chatroom_data` (`Player`, `ID`) VALUES ('" + player.getName() + "', '" + random + "')");

                            //player.sendMessage("[聊天室]  創建成功");
                            SuperFreedomSurvive.Player.Chat.chatroom_send(player, "§b[聊天室]  " + player.getName() + " 加入了聊天室");
                            player.sendMessage("§b[聊天室]  預設聊天頻道是聊天室 發送訊息開頭加上 ` 或是 ~ 則是公開頻道");

                            menu(player); // 重開界面

                        }

                        res.close(); // 關閉查詢
                        sta.close(); // 關閉連線

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }


                } else if (event.getRawSlot() == 19) {
                    // 邀請玩家
                    try {
                        Connection con = MySQL.getConnection(); // 連線 MySQL
                        Statement sta = con.createStatement(); // 取得控制庫

                        ResultSet res = sta.executeQuery("SELECT * FROM `player-chatroom_data` WHERE `Player` = '" + player.getName() + "' LIMIT 1");
                        // 跳到最後一行
                        res.last();
                        // 最後一行 行數 是否 > 0
                        if (res.getRow() > 0) {
                            // 檢查資料
                            // 跳回 初始行 必須使用 next() 才能取得資料
                            res.beforeFirst();
                            // 行數下一行
                            res.next();

                            String ID = res.getString("ID");

                            res = sta.executeQuery("SELECT * FROM `player-chatroom_data` WHERE `ID` = '" + ID + "' LIMIT 19");
                            // 跳到最後一行
                            res.last();
                            // 最後一行 行數 是否 > 0
                            if (res.getRow() >= 18) {
                                // 檢查人數
                                res.close(); // 關閉查詢

                                player.sendMessage("[聊天室]  §c人數已滿");

                            } else {
                                res.close(); // 關閉查詢

                                invite(player);
                            }
                        }

                        res.close(); // 關閉查詢
                        sta.close(); // 關閉連線

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }


                } else if (event.getRawSlot() == 20) {
                    // 離開聊天室
                    try {
                        Connection con = MySQL.getConnection(); // 連線 MySQL
                        Statement sta = con.createStatement(); // 取得控制庫

                        ResultSet res = sta.executeQuery("SELECT * FROM `player-chatroom_data` WHERE `Player` = '" + player.getName() + "' LIMIT 1");
                        // 跳到最後一行
                        res.last();
                        // 最後一行 行數 是否 > 0
                        if (res.getRow() > 0) {
                            res.close(); // 關閉查詢

                            SuperFreedomSurvive.Player.Chat.chatroom_send(player, "§b[聊天室]  " + player.getName() + " 離開了聊天室");
                            player.sendMessage("§b[聊天室]  預設聊天頻道是公開頻道");

                            sta.executeUpdate("DELETE FROM `player-chatroom_data` WHERE `Player` = '" + player.getName() + "' ");
                            sta.executeUpdate("DELETE FROM `player-chatroom_cache` WHERE `Player_Join` = '" + player.getName() + "' ");

                            //player.sendMessage("[聊天室]  離開成功");

                            menu(player); // 重開界面
                        }

                        res.close(); // 關閉查詢
                        sta.close(); // 關閉連線

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }


                } else if (event.getRawSlot() == 25) {
                    // 上一頁
                    SuperFreedomSurvive.Menu.Open.open(player);

                } else if (event.getRawSlot() == 26) {
                    // 關閉
                    event.getWhoClicked().closeInventory();

                } else {
                    //event.setCancelled(true);
                }
            }
        }
    }


    static final public void invite(Player player) {
        // 顯示容器
        Inventory inv = Bukkit.createInventory(null, 54, "§z聊天室邀請玩家");


        ItemStack item;
        ItemMeta meta;
        SkullMeta skull_meta;

        // 取得所有玩家清單
        Collection collection = Bukkit.getServer().getOnlinePlayers();
        Iterator iterator = collection.iterator();
        // 總數
        int total = 0;
        while (iterator.hasNext()) {
            // 讀取全部玩家 名稱
            Player player_ = ((Player) iterator.next());

            // 每排 9 格  共 5 排  = 45
            if (total < 45) {
                // 是需要的資料

                // ---------------------------------------------------------------------------------------
                item = new ItemStack(Material.PLAYER_HEAD);
                meta = item.getItemMeta();
                meta.setDisplayName("§r§f" + Data.getNick(player_.getName()));
                //ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1)
                meta.setLore(Arrays.asList(
                        ("§r§f   ID: §a" + player_.getName()),
                        ("§r§f (點擊) 對這玩家發送出邀請加入聊天室請求")
                ));

                meta.getCustomTagContainer().setCustomTag(player_key, ItemTagType.STRING, player_.getName()); // 創建特殊類型資料

                // 寫入資料
                item.setItemMeta(meta);

                skull_meta = (SkullMeta) item.getItemMeta();
                // 取得玩家名稱 並獲取UUID 設置為頭顱新的主人
                skull_meta.setOwningPlayer(player_);
                item.setItemMeta(skull_meta);
                //player.sendMessage( Bukkit.getServer().getPlayer( player_name ).getUniqueId().toString() );
                // 設置完成
                inv.setItem(total, item);
                // ---------------------------------------------------------------------------------------

            }

            // 總數 + 1
            ++total;
        }


        // --------------------------------------     52     --------------------------------------
        inv.setItem(52, SuperFreedomSurvive.Menu.Open.PreviousPage());

        // --------------------------------------     53     --------------------------------------
        inv.setItem(53, SuperFreedomSurvive.Menu.Open.TurnOff());


        // 寫入到容器頁面
        player.openInventory(inv);
    }


    // 阻止玩家拿走物品
    @EventHandler
    final public void onInventoryClickEvent_(InventoryClickEvent event) {
        // 判斷是否為玩家

        if (event.getWhoClicked() instanceof Player) {
            // 是玩家
            Player player = (Player) event.getWhoClicked();

            // 檢查容器名稱
            if (event.getView().getTitle().equalsIgnoreCase("§z聊天室邀請玩家")) {
                // 是沒錯

                event.setCancelled(true);

                // 檢查點擊的是第幾個位置

                if (event.getRawSlot() < 45) {
                    try {
                        // 頭顱
                        if ( event.getCurrentItem() == null) { return; }

                        ItemMeta meta = event.getCurrentItem().getItemMeta();

                        if (meta == null) { return; }
                        if (meta.getCustomTagContainer().getCustomTag(player_key,ItemTagType.STRING) == null) { return; }

                        // 是否上線中
                        if (Bukkit.getPlayer(meta.getCustomTagContainer().getCustomTag(player_key,ItemTagType.STRING)) != null) {

                            if (player.getName().equals(meta.getCustomTagContainer().getCustomTag(player_key,ItemTagType.STRING))) {
                                player.sendMessage("[聊天室]  §a" + meta.getCustomTagContainer().getCustomTag(player_key,ItemTagType.STRING) + " §c是你自己");
                                return;

                            }

                            // 發送請求
                            try {
                                Connection con = MySQL.getConnection(); // 連線 MySQL
                                Statement sta = con.createStatement(); // 取得控制庫

                                //檢查是否已經有請求在等候
                                ResultSet res = sta.executeQuery("SELECT * FROM `player-chatroom_cache` WHERE `Player` = '" + meta.getCustomTagContainer().getCustomTag(player_key,ItemTagType.STRING) + "' AND `Player_Join` = '" + player.getName() + "' AND `Time` >= '" + (new Date().getTime() - 1000 * 30) + "'");
                                res.last();
                                // 最後一行 行數 是否 > 0
                                if (res.getRow() > 0) {
                                    // ! 有資料

                                    res.close(); // 關閉查詢

                                    // 對 使用指令 的玩家 發出提示
                                    player.sendMessage("[聊天室]  §c邀請玩家 §a" + meta.getCustomTagContainer().getCustomTag(player_key,ItemTagType.STRING) + " §c加入聊天室的請求 目前已經存在");

                                } else {
                                    // 無資料

                                    res.close(); // 關閉查詢

                                    res = sta.executeQuery("SELECT * FROM `player-chatroom_data` WHERE `Player` = '" + player.getName() + "' LIMIT 1");
                                    // 跳到最後一行
                                    res.last();
                                    // 最後一行 行數 是否 > 0
                                    if (res.getRow() > 0) {

                                        // 跳回 初始行 必須使用 next() 才能取得資料
                                        res.beforeFirst();
                                        // 行數下一行
                                        res.next();

                                        String ID = res.getString("ID");

                                        res.close(); // 關閉查詢

                                        res = sta.executeQuery("SELECT * FROM `player-chatroom_data` WHERE `ID` = '" + ID + "' LIMIT 19");
                                        // 跳到最後一行
                                        res.last();
                                        // 最後一行 行數 是否 > 0
                                        if (res.getRow() >= 18) {
                                            // 檢查人數
                                            res.close(); // 關閉查詢

                                            player.sendMessage("[聊天室]  §c人數已滿");

                                        } else {
                                            res.close(); // 關閉查詢

                                            res = sta.executeQuery("SELECT * FROM `player-chatroom_data` WHERE `Player` = '" + meta.getCustomTagContainer().getCustomTag(player_key,ItemTagType.STRING) + "' AND `ID` = '" + ID + "'");
                                            res.last();
                                            // 最後一行 行數 是否 > 0
                                            if (res.getRow() > 0) {
                                                // ! 有資料

                                                res.close(); // 關閉查詢

                                                player.sendMessage("[聊天室]  §c此玩家已經在聊天室裡");
                                                return;

                                            } else {

                                                res.close(); // 關閉查詢

                                                // 插入一個項目
                                                sta.executeUpdate("INSERT INTO `player-chatroom_cache` (`Player`, `Player_Join`, `Time`, `ID`) VALUES ('" + meta.getCustomTagContainer().getCustomTag(player_key,ItemTagType.STRING) + "', '" + player.getName() + "', " + new Date().getTime() + ", '" + ID + "')");

                                                // 對 使用指令 的玩家 發出提示
                                                player.sendMessage("[聊天室]  已對玩家 §a" + meta.getCustomTagContainer().getCustomTag(player_key,ItemTagType.STRING) + "§f 發送加入聊天室請求");

                                                // 發送特殊訊息
                                                TextComponent text = new TextComponent("[聊天室]  玩家 §a" + player.getName() + "§f 邀請你加入聊天室 ");

                                                TextComponent text_y = new TextComponent(" (接受) ");
                                                text_y.setColor(ChatColor.GREEN);
                                                text_y.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("點擊 加入聊天室(有效30秒內)").create()));
                                                text_y.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/chatroom " + ID));

                                                Bukkit.getPlayer(meta.getCustomTagContainer().getCustomTag(player_key,ItemTagType.STRING)).sendMessage(text, text_y);
                                                //Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + meta.getDisplayName().replace("§r§a", "") + " [{\"text\":\"[聊天室]  玩家 §a" + player.getName() + "§f 邀請你加入聊天室 \"},{\"text\":\" (接受) \",\"color\":\"green\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/chatroom " + ID + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"點擊 加入聊天室(有效30秒)\",\"color\":\"green\"}]}}}]");

                                                // 釋放 MySQL 記憶體
                                                // 刪除時間超過 30 秒的全部數據
                                                sta.executeUpdate("DELETE FROM `player-chatroom_cache` WHERE `Time` <= '" + (new Date().getTime() - 1000 * 30) + "'");

                                            }
                                        }
                                    } else {
                                        // 沒有在聊天室內

                                    }

                                }

                                res.close(); // 關閉查詢
                                sta.close(); // 關閉連線

                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        } else {
                            player.sendMessage("[聊天室]  §c玩家不再線上");

                        }

                        //Bukkit.dispatchCommand( player , "tpa " +  meta.getDisplayName().replace("§r§a","") );

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }


                } else if (event.getRawSlot() == 52) {
                    // 上一頁
                    menu(player);

                } else if (event.getRawSlot() == 53) {
                    // 關閉
                    event.getWhoClicked().closeInventory();

                }

            }
        }


    }

}
