package SuperFreedomSurvive.Menu.Function;

import SuperFreedomSurvive.Menu.Open;
import SuperFreedomSurvive.SYSTEM.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.tags.ItemTagType;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

final public class Kit implements Listener {
    // 選單接口

    static final private org.bukkit.NamespacedKey id_key = SuperFreedomSurvive.SYSTEM.NamespacedKey.getKey("KitID");
    static final private org.bukkit.NamespacedKey number_key = SuperFreedomSurvive.SYSTEM.NamespacedKey.getKey("PagesNumber");

    // 禮包系統
    final public static void menu(Player player) {
        menu(player,0);
    }
    final public static void menu(Player player, int pages_number) {

        // 顯示容器
        Inventory inv = Bukkit.createInventory(null, 54, "§z禮包系統");

        ItemStack item;
        ItemMeta meta;

        // 總數
        int total = 0;

        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫


            // 按照頁數取得 45 筆資料
            // 存入內容
            ResultSet res = sta.executeQuery("SELECT * FROM `player-kit_data` WHERE `Player` = '" + player.getName() + "' AND ( `End_Time` > '" + SuperFreedomSurvive.SYSTEM.Time.Get() + "' OR `End_Time` IS NULL ) ORDER BY `Start_Time` DESC LIMIT 45 OFFSET " + 45 * pages_number);

            while (res.next()) {
                // 取得
                // ---------------------------------------------------------------------------------------
                item = new ItemStack(Material.valueOf(res.getString("Material")) , res.getInt("Amount"));
                meta = item.getItemMeta();
                if (res.getString("DisplayName") != null) meta.setDisplayName("§r§e" + res.getString("DisplayName"));

                // 字串
                ArrayList<String> lore = new ArrayList<String>();
                lore.add("§r§f (點擊) 取得內容物品");

                if (res.getString("Lore") != null) {
                    //lore.add( "§r§f 訊息 :" );
                    // 正則切割
                    Pattern p = Pattern.compile("\\|");
                    String[] str = p.split(res.getString("Lore"));
                    // 轉換
                    for (int ii = 0, s_ = str.length; ii < s_; ++ii) {
                        lore.add(str[ii]);
                    }
                }

                if (res.getString("Sender") == null) {
                    lore.add("§r§f - 發信人 §e伺服器");
                } else {
                    lore.add("§r§f - 發信人 §a" + res.getString("Sender"));
                }

                lore.add("§r§f - 取得時間 " + res.getString("Start_Time"));
                if (res.getString("End_Time") == null) {
                    lore.add("§r§f - 到期時間 §e永久");
                } else {
                    lore.add("§r§f - 到期時間 " + res.getString("End_Time"));
                }

                meta.setLore(lore);

                meta.getCustomTagContainer().setCustomTag(id_key, ItemTagType.STRING, res.getString("ID")); // 登入key
                meta.getCustomTagContainer().setCustomTag(number_key, ItemTagType.INTEGER, pages_number); // 登入key

                item.setItemMeta(meta);

                // 設置完成
                inv.setItem(total, item);

                total++;
            }

            res.close(); // 關閉查詢


            if (pages_number > 0) {
                // --------------------------------------     46     --------------------------------------
                // 上一頁
                item = new ItemStack(Material.LADDER);
                meta = item.getItemMeta();
                meta.setDisplayName("§e上一面");
                meta.setLore(Arrays.asList(
                        ("§r§f (點擊) 前往上一頁頁面")
                ));
                meta.getCustomTagContainer().setCustomTag(number_key, ItemTagType.INTEGER, pages_number - 1); // 登入key
                // 寫入資料
                item.setItemMeta(meta);
                // 設置完成
                inv.setItem(46, item);
            }


            res = sta.executeQuery("Select Count(*) FROM `player-kit_data` WHERE `Player` = '" + player.getName() + "' AND ( `End_Time` > '" + SuperFreedomSurvive.SYSTEM.Time.Get() + "' OR `End_Time` IS NULL )");
            res.next();
            if (res.getInt("Count(*)") > (pages_number + 1) * 45) {

                res.close(); // 關閉查詢

                // --------------------------------------     47     --------------------------------------
                // 下一頁
                item = new ItemStack(Material.LADDER);
                meta = item.getItemMeta();
                meta.setDisplayName("§e下一面");
                meta.setLore(Arrays.asList(
                        ("§r§f (點擊) 前往下一頁頁面")
                ));
                meta.getCustomTagContainer().setCustomTag(number_key, ItemTagType.INTEGER, pages_number + 1); // 登入key
                // 寫入資料
                item.setItemMeta(meta);
                // 設置完成
                inv.setItem(47, item);
            }

            // --------------------------------------     52     --------------------------------------
            inv.setItem(52, Open.PreviousPage());

            // --------------------------------------     53     --------------------------------------
            inv.setItem(53, Open.TurnOff());


            res.close(); // 關閉查詢
            sta.close(); // 關閉連線


            // 寫入到容器頁面
            player.openInventory(inv);

        } catch (Exception ex) {
            ex.printStackTrace();
        }


        // --------------------------------------     45     --------------------------------------
        // 沒找到你要的?
        item = new ItemStack(Material.MAP);
        meta = item.getItemMeta();
        meta.setDisplayName("§b超過時間未領取會自動消失");
        // 寫入資料
        item.setItemMeta(meta);
        // 設置完成
        inv.setItem(45, item);


        // --------------------------------------     52     --------------------------------------
        inv.setItem(52, SuperFreedomSurvive.Menu.Open.PreviousPage());


        // --------------------------------------     53     --------------------------------------
        inv.setItem(53, SuperFreedomSurvive.Menu.Open.TurnOff());



        /* new PotionEffect( new PotionEffectType.ABSORPTION) */

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
            if (event.getView().getTitle().equalsIgnoreCase("§z禮包系統")) {
                // 是沒錯

                event.setCancelled(true);

                try {

                    if (event.getCurrentItem() == null) return;
                    if (event.getCurrentItem().getItemMeta() == null) return;

                    if (event.getRawSlot() < 45) {
                        // 取得禮包內容

                        String ID = event.getCurrentItem().getItemMeta().getCustomTagContainer().getCustomTag(id_key, ItemTagType.STRING);

                        try {
                            Connection con = MySQL.getConnection(); // 連線 MySQL
                            Statement sta = con.createStatement(); // 取得控制庫

                            // 找內容
                            ResultSet res = sta.executeQuery("SELECT * FROM `player-kit_data` WHERE `Player` = '" + player.getName() + "' AND `ID` = '" + ID + "' AND ( `End_Time` > '" + SuperFreedomSurvive.SYSTEM.Time.Get() + "' OR `End_Time` IS NULL ) LIMIT 1");
                                    // 給玩家物品

                                    res.last();
                                    // 最後一行 行數 是否 > 0
                                    if (res.getRow() > 0) {
                                        // 有資料
                                        // 跳回 初始行 必須使用 next() 才能取得資料
                                        res.beforeFirst();
                                        res.next();

                                        Material material = Material.getMaterial(res.getString("Material"));
                                        if (material == null) return;
                                        int amount = res.getInt("Amount");
                                        //String key = res.getString("Key");

                                        res.close(); // 關閉查詢

                                        ItemStack item = new ItemStack(material, amount);
                                        ItemMeta meta = item.getItemMeta();

                                        // 寫入資料
                                        item.setItemMeta(meta);

                                        // 添加到背包
                                        player.getInventory().addItem(item);

                                        // 刪除完禮物資料
                                        sta.executeUpdate("DELETE FROM `player-kit_data` WHERE `Player` = '" + player.getName() + "' AND `ID` = '" + ID + "' AND ( `End_Time` > '" + SuperFreedomSurvive.SYSTEM.Time.Get() + "' OR `End_Time` IS NULL ) LIMIT 1");

                                        player.sendMessage("[禮包系統]  領取成功");

                                        // 重開本介面
                                        if (event.getCurrentItem().getItemMeta().getCustomTagContainer().hasCustomTag(number_key, ItemTagType.INTEGER))
                                            menu(player, event.getCurrentItem().getItemMeta().getCustomTagContainer().getCustomTag(number_key, ItemTagType.INTEGER));
                                        //menu(player); // 重開本介面

                                    }

                            res.close(); // 關閉查詢
                            sta.close(); // 關閉連線

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }


                    } else if (event.getRawSlot() == 46) {
                        if (event.getCursor() != null) {
                            if (event.getCurrentItem().getItemMeta().getCustomTagContainer().hasCustomTag(number_key, ItemTagType.INTEGER))
                                menu(player, event.getCurrentItem().getItemMeta().getCustomTagContainer().getCustomTag(number_key, ItemTagType.INTEGER));
                        }

                    } else if (event.getRawSlot() == 47) {
                        if (event.getCursor() != null) {
                            if (event.getCurrentItem().getItemMeta().getCustomTagContainer().hasCustomTag(number_key, ItemTagType.INTEGER))
                                menu(player, event.getCurrentItem().getItemMeta().getCustomTagContainer().getCustomTag(number_key, ItemTagType.INTEGER));
                        }


                    } else if (event.getRawSlot() == 52) {
                        // 上一頁
                        SuperFreedomSurvive.Menu.Open.open(player);

                    } else if (event.getRawSlot() == 53) {
                        // 關閉
                        event.getWhoClicked().closeInventory();

                    } else {
                        //event.setCancelled(true);
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }






    // 新增一個裡包給玩家
    static public void sendKit(String player,String sender,String display_name,String lore,Material material,int amount,String namespaced_key,String start_time,String end_time) {

        try {

            if (sender == null) {
                sender = "NULL";
            } else {
                sender = "'" + sender + "'";
            }
            if (display_name == null) {
                display_name = "NULL";
            } else {
                display_name = "'" + display_name + "'";
            }
            if (lore == null) {
                lore = "NULL";
            } else {
                lore = "'" + lore + "'";
            }
            if (namespaced_key == null) {
                namespaced_key = "NULL";
            } else {
                namespaced_key = "'" + namespaced_key + "'";
            }
            if (end_time == null) {
                end_time = "NULL";
            } else {
                end_time = "'" + end_time + "'";
            }

            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫

            // 創建唯一ID
            String random = "";
            String[] RegSNContent = {
                    "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
                    "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
                    "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
                    "_", "+", "-", ".", "~", "/", "=", "[", "]", "(", ")", "<", ">", "`", "!", "@", "#", "$", "^", "&", "*", "{", "}", ":", ";", "?", "|"
            };
            for (int i = 0; i < 10; i++)
                random += RegSNContent[(int) (Math.random() * RegSNContent.length)];
            sta.executeUpdate("INSERT INTO `player-kit_data` (`Player`, `Sender`, `DisplayName`, `Lore`, `Material`, `Amount`, `NamespacedKey`, `Start_Time`, `End_Time`, `ID`) VALUES ('" + player + "', " + sender + ", " + display_name + ", " + lore + ", '" + material.name() + "', '" + amount +"', " + namespaced_key + ", '" + start_time + "', " + end_time + ", '" + random + "')");

            sta.cancel();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
