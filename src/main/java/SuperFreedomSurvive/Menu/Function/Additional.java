package SuperFreedomSurvive.Menu.Function;

import SuperFreedomSurvive.Player.Pay;
import SuperFreedomSurvive.SYSTEM.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

final public class Additional implements Listener {

    // 選單接口
    // 補助系統
    final public static void menu(Player player) {

        // 顯示容器
        Inventory inv = Bukkit.createInventory(null, 54, "§z補助系統");

        ItemStack item;
        ItemMeta meta;


        inv.setItem(0, get_Tiem(
                player,
                Material.ELYTRA,
                "Function_Flight_Time",
                "§r§e野外自由飛行",
                new String[]{
                        "§r§f (點擊) 加購 1 小時",
                        "§r§f - 消耗 16 顆綠寶石",
                        "§r§f - 功用 能在野外飛行",
                        "§r§f - 提醒 沒有權限的領地無法飛行"
                })
        );

        inv.setItem(1, get_Tiem(
                player,
                Material.GOLDEN_PICKAXE,
                "Function_Rapid-Destruction_Time",
                "§r§e領地內快速破壞",
                new String[]{
                        "§r§f (點擊) 加購 1 小時",
                        "§r§f - 消耗 16 顆綠寶石",
                        "§r§f - 功用 讓你在領地內可以快速破壞",
                        "§r§f - 提醒 你必須是 使用者 或是 共用人",
                        "§r§f        在 野外 是無效的",
                        "§r§f        一樣要手持工具 才會掉落方塊(扣耐久)",
                        "§r§f        對容器(例如箱子) 不會被快速破壞"
                })
        );

        inv.setItem(2, get_Tiem(
                player,
                Material.REDSTONE,
                "Function_Potion-Duration-Double_Time",
                "§r§e藥水雙倍時間",
                new String[]{
                        "§r§f (點擊) 加購 1 小時",
                        "§r§f - 消耗 4 顆綠寶石",
                        "§r§f - 功能 藥水持續時間 x 2 倍",
                        "§r§f - 提醒 只能在此效果生效狀態下 接觸藥水效果"
                })
        );

        inv.setItem(3, get_Tiem(
                player,
                Material.GLOWSTONE_DUST,
                "Function_Potion-Lv-Double_Time",
                "§r§e藥水雙倍效果",
                new String[]{
                        "§r§f (點擊) 加購 1 小時",
                        "§r§f - 消耗 4 顆綠寶石",
                        "§r§f - 功能 藥水效果等級 x 2 倍",
                        "§r§f - 提醒 只能在此效果生效狀態下 接觸藥水效果"
                })
        );

        inv.setItem(4, get_Tiem(
                player,
                Material.EXPERIENCE_BOTTLE,
                "Function_Obtain-Experience-Double_Time",
                "§r§e經驗值雙倍",
                new String[]{
                        "§r§f (點擊) 加購 1 小時",
                        "§r§f - 消耗 6 顆綠寶石",
                        "§r§f - 功能 獲得經驗值 x 2 倍",
                        "§r§f - 提醒 只能在此效果生效狀態下 吸取經驗值"
                })
        );

        inv.setItem(5, get_Tiem(
                player,
                Material.WOODEN_AXE,
                "Function_Wood-Axe_Time",
                "§r§e允許小木斧快速建造",
                new String[]{
                        "§r§f (點擊) 加購 10 分鐘",
                        "§r§f - 消耗 12 顆綠寶石",
                        "§r§f - 功能 允許使用小木斧快速建造",
                        "§r§f - 提醒 一樣需要消耗方塊",
                        "§r§f        你必須是 使用者 或是 共用人",
                        "§r§f        在 野外 是無效的 且 不可跨領地/子領地",
                        "§r§f - 副手 有方塊 替換範圍內所有方塊",
                        "§r§f        無方塊 範圍所有方塊替換為空氣 不會掉落",
                        "§r§f - 放置方塊 一次最大同時塊數 2,304",
                        "§r§f   放置空氣 一次最大同時塊數 216,000"
                })
        );


        // --------------------------------------     52     --------------------------------------
        inv.setItem(52, SuperFreedomSurvive.Menu.Open.PreviousPage());


        // --------------------------------------     53     --------------------------------------
        inv.setItem(53, SuperFreedomSurvive.Menu.Open.TurnOff());


        // 寫入到容器頁面
        player.openInventory(inv);

    }

    static final public ItemStack get_Tiem(Player player, Material material, String type, String name, String[] lore) {
        // 取得物品快速接口
        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫
            // 存入內容
            ResultSet res = sta.executeQuery("SELECT * FROM `player-additional_data` WHERE `" + type + "` > '" + SuperFreedomSurvive.SYSTEM.Time.Get() + "' AND `Player` = '" + player.getName() + "' LIMIT 1");
            res.last();
            // 最後一行 行數 是否 > 0
            if (res.getRow() > 0) {
                // 有資料
                // 跳回 初始行 必須使用 next() 才能取得資料
                res.beforeFirst();
                res.next();

                ItemStack item = new ItemStack(material);
                ItemMeta meta = item.getItemMeta();


                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                meta.addEnchant(Enchantment.BINDING_CURSE, 1, false);

                meta.setDisplayName(name); // 設定顯示名稱

                ArrayList<String> all_lore = new ArrayList<String>();
                all_lore.add("§r§b - 持續到 " + res.getString(type));

                for (int i = 0, s = lore.length; i < s; ++i) {
                    all_lore.add(lore[i]);
                }

                meta.setLore(all_lore);
                // 寫入資料
                item.setItemMeta(meta);
                // 設置完成

                res.close(); // 關閉查詢
                sta.close(); // 關閉連線

                return item;


            } else {
                // 無
                ItemStack item = new ItemStack(material);
                ItemMeta meta = item.getItemMeta();

                meta.setDisplayName(name); // 設定顯示名稱

                ArrayList<String> all_lore = new ArrayList<String>();
                all_lore.add("§r§f - 目前無購買");

                for (int i = 0, s = lore.length; i < s; ++i) {
                    all_lore.add(lore[i]);
                }

                meta.setLore(all_lore);
                // 寫入資料
                item.setItemMeta(meta);
                // 設置完成

                res.close(); // 關閉查詢
                sta.close(); // 關閉連線

                return item;

            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }


    // 阻止玩家拿走物品
    @EventHandler
    final public void onInventoryClickEvent(InventoryClickEvent event) {
        // 判斷是否為玩家

        if (event.getWhoClicked() instanceof Player) {
            // 是玩家
            Player player = (Player) event.getWhoClicked();

            // 檢查容器名稱
            if (event.getView().getTitle().equalsIgnoreCase("§z補助系統")) {
                // 是沒錯

                event.setCancelled(true);


                if (event.getRawSlot() == 0) {
                    // 功能 全地圖飛行
                    // 是否足夠
                    if (Pay.Have(player, 16)) {
                        // 足夠
                        // 收取綠寶石
                        if (Pay.Recover(player, 16)) {
                            Increase_Time(player, "Function_Flight_Time", "1 HOUR");
                        }

                    } else {
                        player.sendMessage("[補助系統]  §c所需物資不夠! 需要 16 顆 綠寶石");
                    }

                } else if (event.getRawSlot() == 1) {
                    // 功能 快速破壞
                    // 是否足夠
                    if (Pay.Have(player, 16)) {
                        // 足夠
                        // 收取綠寶石
                        if (Pay.Recover(player, 16)) {
                            Increase_Time(player, "Function_Rapid-Destruction_Time", "1 HOUR");
                        }

                    } else {
                        player.sendMessage("[補助系統]  §c所需物資不夠! 需要 16 顆 綠寶石");
                    }

                } else if (event.getRawSlot() == 2) {
                    // 功能 藥水持續時間2倍
                    // 是否足夠
                    if (Pay.Have(player, 4)) {
                        // 足夠
                        // 收取綠寶石
                        if (Pay.Recover(player, 4)) {
                            Increase_Time(player, "Function_Potion-Duration-Double_Time", "1 HOUR");
                        }

                    } else {
                        player.sendMessage("[補助系統]  §c所需物資不夠! 需要 2 顆 綠寶石");
                    }

                } else if (event.getRawSlot() == 3) {
                    //     功能 藥水效果等級2倍
                    // 是否足夠
                    if (Pay.Have(player, 4)) {
                        // 足夠
                        // 收取綠寶石
                        if (Pay.Recover(player, 4)) {
                            Increase_Time(player, "Function_Potion-Lv-Double_Time", "1 HOUR");
                        }

                    } else {
                        player.sendMessage("[補助系統]  §c所需物資不夠! 需要 2 顆 綠寶石");
                    }

                } else if (event.getRawSlot() == 4) {
                    // 功能 獲得經驗值2倍
                    // 是否足夠
                    if (Pay.Have(player, 6)) {
                        // 足夠
                        // 收取綠寶石
                        if (Pay.Recover(player, 6)) {
                            Increase_Time(player, "Function_Obtain-Experience-Double_Time", "1 HOUR");
                        }

                    } else {
                        player.sendMessage("[補助系統]  §c所需物資不夠! 需要 6 顆 綠寶石");
                    }

                } else if (event.getRawSlot() == 5) {
                    // 功能 允許小木斧快速建造
                    // 是否足夠
                    if (Pay.Have(player, 12)) {
                        // 足夠
                        // 收取綠寶石
                        if (Pay.Recover(player, 12)) {
                            Increase_Time(player, "Function_Wood-Axe_Time", "10 MINUTE");
                        }

                    } else {
                        player.sendMessage("[補助系統]  §c所需物資不夠! 需要 12 顆 綠寶石");
                    }

                } else if (event.getRawSlot() == 52) {
                    // 上一頁
                    SuperFreedomSurvive.Menu.Open.open(player);

                } else if (event.getRawSlot() == 53) {
                    // 關閉
                    event.getWhoClicked().closeInventory();

                }
            }
        }
    }


/*
        ItemStack item = new ItemStack(Material.EMERALD, 1);
        for (; need > 0; ) {
            player.getInventory().removeItem(item);
            need = need - 1;
        }
*/

    // 增加時間
    static final public void Increase_Time(Player player, String type, String interval) {
        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫
            // 取得內容
            ResultSet res = sta.executeQuery("SELECT * FROM `player-additional_data` WHERE `" + type + "` > '" + SuperFreedomSurvive.SYSTEM.Time.Get() + "' AND `Player` = '" + player.getName() + "' LIMIT 1");
            res.last();
            // 最後一行 行數 是否 > 0
            if (res.getRow() > 0) {
                // 有資料

                res.close(); // 關閉查詢

/*
                // 取得當前時間 並延後 1 小時
                Date date = res.getDate( type );

                //SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);

                // 1 小時候
                calendar.set( Calendar.MINUTE , calendar.get(Calendar.MINUTE) + 60 );
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH) + 1; // 1月的值為0
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int hour = calendar.get(Calendar.HOUR_OF_DAY); // HOUR_OF_DAY 24小時制    HOUR 12小時制
                int minute = calendar.get(Calendar.MINUTE);
                int second = calendar.get(Calendar.SECOND);
                String min_time =  year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;

                // + interval 1 HOUR WHERE
*/
                // 改變資料
                sta.executeUpdate("UPDATE `player-additional_data` SET `" + type + "` = `" + type + "` + interval " + interval + " WHERE `Player` = '" + player.getName() + "'");
                player.sendMessage("[補助系統]  購買成功");
                menu(player); // 重開本介面

            } else {
                // 使用現在時間 並延後 1 小時

                res.close(); // 關閉查詢

                sta.executeUpdate("UPDATE `player-additional_data` SET `" + type + "` = now() + interval " + interval + " WHERE `Player` = '" + player.getName() + "'");
                player.sendMessage("[補助系統]  購買成功");
                menu(player); // 重開本介面

            }

            res.close(); // 關閉查詢
            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
