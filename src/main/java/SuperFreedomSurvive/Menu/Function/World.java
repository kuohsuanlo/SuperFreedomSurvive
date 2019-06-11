package SuperFreedomSurvive.Menu.Function;

import SuperFreedomSurvive.Function.ColorCodeTransform;
import SuperFreedomSurvive.Player.Pay;
import SuperFreedomSurvive.SYSTEM.Input;
import SuperFreedomSurvive.SYSTEM.MySQL;
import SuperFreedomSurvive.World.Data;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.tags.ItemTagType;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

final public class World implements Listener {
    // 多世界系統


    static final public org.bukkit.NamespacedKey key = SuperFreedomSurvive.SYSTEM.NamespacedKey.getKey("WorldName");


    // 選單接口
    final public static void menu(Player player) {
        // 顯示容器
        Inventory inv = Bukkit.createInventory(null, 54, "§z多世界系統");

        ItemStack item;
        ItemMeta meta;


        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////   個人世界   //////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫

            // 總數
            int total = 0;

            // 取得世界清單
            // 存入內容
            ResultSet res = sta.executeQuery("SELECT * FROM `world-list_data` WHERE `Enable` = '1' AND `Hide` = '0' AND `Group` IS NULL AND `Owner` = '" + player.getName() + "' ORDER BY `Start_Time` LIMIT 9");
            while (res.next()) {
                // ---------------------------------------------------------------------------------------
                item = new ItemStack(Material.getMaterial(res.getString("ID")));
                meta = item.getItemMeta();
                meta.setDisplayName("§e" + res.getString("Description"));

                // 字串
                ArrayList<String> lore = new ArrayList<String>();

                lore.add("§r§a (左鍵) 傳送");
                lore.add("§r§f (右鍵) 設定");
                lore.add("§r§f (Shift+右鍵) 刪除此世界");

                if (res.getInt("Difficulty") == 0) {
                    lore.add("§r§f - 難度 和平");
                } else if (res.getInt("Difficulty") == 1) {
                    lore.add("§r§f - 難度 簡單");
                } else if (res.getInt("Difficulty") == 2) {
                    lore.add("§r§f - 難度 普通");
                } else if (res.getInt("Difficulty") == 3) {
                    lore.add("§r§f - 難度 困難");
                }

                if (res.getBoolean("PVP")) {
                    lore.add("§r§f - PVP 允許");
                } else {
                    lore.add("§r§f - PVP 禁止");
                }

                if (res.getInt("Difficulty") == 0) {
                    lore.add("§r§f - 難度 和平");
                } else if (res.getInt("Difficulty") == 1) {
                    lore.add("§r§f - 難度 簡單");
                } else if (res.getInt("Difficulty") == 2) {
                    lore.add("§r§f - 難度 普通");
                } else if (res.getInt("Difficulty") == 3) {
                    lore.add("§r§f - 難度 困難");
                }

                if (res.getInt("Environment") == 0) {
                    lore.add("§r§f - 環境 正常");
                } else if (res.getInt("Environment") == 1) {
                    lore.add("§r§f - 環境 地獄");
                } else if (res.getInt("Environment") == 2) {
                    lore.add("§r§f - 環境 終界");
                }

                if (res.getBoolean("KEEP_INVENTORY")) {
                    lore.add("§r§f - 防噴裝保護 有");
                } else {
                    lore.add("§r§f - 防噴裝保護 沒有");
                }

                if (res.getBoolean("NATURAL_REGENERATION")) {
                    lore.add("§r§f - 自然回復生命 有");
                } else {
                    lore.add("§r§f - 自然回復生命 沒有");
                }

                lore.add("§r§f - 生物擁擠超標窒息 " + res.getInt("MAX_ENTITY_CRAMMING"));

                if (res.getBoolean("MOB_GRIEFING")) {
                    lore.add("§r§f - 生物拾取物品 有");
                } else {
                    lore.add("§r§f - 生物拾取物品 沒有");
                }

                if (res.getBoolean("DO_WEATHER_CYCLE")) {
                    lore.add("§r§f - 日夜變化 有");
                } else {
                    lore.add("§r§f - 日夜變化 沒有");
                }

                if (res.getBoolean("DO_FIRE_TICK")) {
                    lore.add("§r§f - 火焰自然熄滅/蔓延 有");
                } else {
                    lore.add("§r§f - 火焰自然熄滅/蔓延 沒有");
                }

                if (res.getBoolean("DO_MOB_LOOT")) {
                    lore.add("§r§f - 生物死亡掉落物品 有");
                } else {
                    lore.add("§r§f - 生物死亡掉落物品 沒有");
                }

                if (res.getBoolean("DO_MOB_SPAWNING")) {
                    lore.add("§r§f - 生物自然生成 有");
                } else {
                    lore.add("§r§f - 生物自然生成 沒有");
                }

                if (res.getBoolean("DO_TILE_DROPS")) {
                    lore.add("§r§f - 破壞方塊掉落物品 有");
                } else {
                    lore.add("§r§f - 破壞方塊掉落物品 沒有");
                }

                lore.add("§r§f - 地圖邊界 " + res.getInt("Size") / 2);

                lore.add("§r§f - 創建日期 " + res.getString("Start_Time"));

                meta.setLore(lore);
                // 寫入資料
                item.setItemMeta(meta);
                // 設置完成
                inv.setItem(total, item);

                ++total;

            }

            res.close(); // 關閉查詢
            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }

/*
        // --------------------------------------     0     --------------------------------------
        item = new ItemStack ( Material.CHEST );
        meta = item.getItemMeta();
        meta.setDisplayName("§b個人世界");
        meta.setLore( Arrays.asList(
                ("§r§a (點擊) 傳送"),
                ("§r§e 你有全部的控制權限"),
                ("§r§e 保護區範圍 1024 格"),
                ("§r§f 難度 : 簡單"),
                ("§r§f 防噴裝保護 : 有"),
                ("§r§f PVP : 允許"),
                ("§r§f 地圖邊界 : 1024")
        ));
        // 寫入資料
        item.setItemMeta(meta);
        // 設置完成
        inv.setItem(0 , item );
*/




/*

        // --------------------------------------     2     --------------------------------------
        item = new ItemStack ( Material.NETHERRACK );
        meta = item.getItemMeta();
        meta.setDisplayName("§b地域");
        meta.setLore( Arrays.asList(
                ("§r§f (點擊) 傳送"),
                ("§r§f - 保護區範圍 100 格"),
                ("§r§f - 不定時重刷"),
                ("§r§f 難度 : 困難"),
                ("§r§f 防噴裝保護 : 沒有"),
                ("§r§f PVP : 禁止"),
                ("§r§f 地圖邊界 : 100000")
        ));
        // 寫入資料
        item.setItemMeta(meta);
        // 設置完成
        inv.setItem(2 , item );




        // --------------------------------------     3     --------------------------------------
        item = new ItemStack ( Material.END_STONE );
        meta = item.getItemMeta();
        meta.setDisplayName("§b終界");
        meta.setLore( Arrays.asList(
                ("§r§f (點擊) 傳送"),
                ("§r§f - 保護區範圍 100 格"),
                ("§r§f - 不定時重刷"),
                ("§r§f 難度 : 困難"),
                ("§r§f 防噴裝保護 : 沒有"),
                ("§r§f PVP : 禁止"),
                ("§r§f 地圖邊界 : 100000")
        ));
        // 寫入資料
        item.setItemMeta(meta);
        // 設置完成
        inv.setItem(3 , item );

*/


        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////   全部世界   //////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        // --------------------------------------     9     --------------------------------------
        item = new ItemStack(Material.CHISELED_QUARTZ_BLOCK);
        meta = item.getItemMeta();
        meta.setDisplayName("§b主城");
        meta.setLore(Arrays.asList(
                ("§r§a (點擊) 傳送"),
                ("§r§f 防噴裝保護 : 有"),
                ("§r§f PVP : 禁止")
        ));
        // 寫入資料
        item.setItemMeta(meta);
        // 設置完成
        inv.setItem(9, item);


        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫

            // 總數
            int total = 10;

            // 取得世界清單
            // 存入內容
            ResultSet res = sta.executeQuery("SELECT * FROM `world-list_data` WHERE `Enable` = '1' AND `Hide` = '0' AND `Group` = 'SERVER' AND `Start_Time` < '" + SuperFreedomSurvive.SYSTEM.Time.Get() + "' AND `End_Time` > '" + SuperFreedomSurvive.SYSTEM.Time.Get() + "' ORDER BY `Start_Time` LIMIT 35");
            while (res.next()) {
                // ---------------------------------------------------------------------------------------
                item = new ItemStack(Material.getMaterial(res.getString("ID")));
                meta = item.getItemMeta();
                meta.setDisplayName(res.getString("Description"));

                // 字串
                ArrayList<String> lore = new ArrayList<String>();

                if (res.getBoolean("Transfer")) {
                    lore.add("§r§a (點擊) 傳送");
                } else {
                    lore.add("§r§c 不可直接傳送");
                }

                if (res.getString("Lore") != null) {
                    //lore.add( "§r§f 訊息 :" );
                    // 正則切割
                    Pattern p = Pattern.compile("\\|");
                    String[] str = p.split(res.getString("Lore"));
                    // 轉換
                    for (int ii = 0, s = str.length; ii < s; ++ii) {
                        lore.add(str[ii]);
                    }
                }



                if (res.getInt("Difficulty") == 0) {
                    lore.add("§r§f - 難度 和平");
                } else if (res.getInt("Difficulty") == 1) {
                    lore.add("§r§f - 難度 簡單");
                } else if (res.getInt("Difficulty") == 2) {
                    lore.add("§r§f - 難度 普通");
                } else if (res.getInt("Difficulty") == 3) {
                    lore.add("§r§f - 難度 困難");
                }

                if (res.getBoolean("PVP")) {
                    lore.add("§r§f - PVP 允許");
                } else {
                    lore.add("§r§f - PVP 禁止");
                }

                if (res.getInt("Difficulty") == 0) {
                    lore.add("§r§f - 難度 和平");
                } else if (res.getInt("Difficulty") == 1) {
                    lore.add("§r§f - 難度 簡單");
                } else if (res.getInt("Difficulty") == 2) {
                    lore.add("§r§f - 難度 普通");
                } else if (res.getInt("Difficulty") == 3) {
                    lore.add("§r§f - 難度 困難");
                }

                if (res.getInt("Environment") == 0) {
                    lore.add("§r§f - 環境 正常");
                } else if (res.getInt("Environment") == 1) {
                    lore.add("§r§f - 環境 地獄");
                } else if (res.getInt("Environment") == 2) {
                    lore.add("§r§f - 環境 終界");
                }

                if (res.getBoolean("KEEP_INVENTORY")) {
                    lore.add("§r§f - 防噴裝保護 有");
                } else {
                    lore.add("§r§f - 防噴裝保護 沒有");
                }

                if (res.getBoolean("NATURAL_REGENERATION")) {
                    lore.add("§r§f - 自然回復生命 有");
                } else {
                    lore.add("§r§f - 自然回復生命 沒有");
                }

                lore.add("§r§f - 生物擁擠超標窒息 " + res.getInt("MAX_ENTITY_CRAMMING"));

                if (res.getBoolean("MOB_GRIEFING")) {
                    lore.add("§r§f - 生物拾取物品 有");
                } else {
                    lore.add("§r§f - 生物拾取物品 沒有");
                }

                if (res.getBoolean("DO_WEATHER_CYCLE")) {
                    lore.add("§r§f - 日夜變化 有");
                } else {
                    lore.add("§r§f - 日夜變化 沒有");
                }

                if (res.getBoolean("DO_FIRE_TICK")) {
                    lore.add("§r§f - 火焰自然熄滅/蔓延 有");
                } else {
                    lore.add("§r§f - 火焰自然熄滅/蔓延 沒有");
                }

                if (res.getBoolean("DO_MOB_LOOT")) {
                    lore.add("§r§f - 生物死亡掉落物品 有");
                } else {
                    lore.add("§r§f - 生物死亡掉落物品 沒有");
                }

                if (res.getBoolean("DO_MOB_SPAWNING")) {
                    lore.add("§r§f - 生物自然生成 有");
                } else {
                    lore.add("§r§f - 生物自然生成 沒有");
                }

                if (res.getBoolean("DO_TILE_DROPS")) {
                    lore.add("§r§f - 破壞方塊掉落物品 有");
                } else {
                    lore.add("§r§f - 破壞方塊掉落物品 沒有");
                }

                lore.add("§r§f - 地圖邊界 " + res.getInt("Size") / 2);

                lore.add("§r§f - 創建日期 " + res.getString("Start_Time"));
                lore.add("§r§f - 結束時間 " + res.getString("End_Time"));

                meta.getCustomTagContainer().setCustomTag(key, ItemTagType.STRING, res.getString("Name")); // 登入key

                meta.setLore(lore);
                // 寫入資料
                item.setItemMeta(meta);
                // 設置完成
                inv.setItem(total, item);

                ++total;

            }


            res.close(); // 關閉查詢
            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }


        // --------------------------------------     45     --------------------------------------
        item = new ItemStack(Material.MAP);
        meta = item.getItemMeta();
        meta.setDisplayName("§b多世界?");
        meta.setLore(Arrays.asList(
                ("§r§f - 顯示在這裡的都是能自由出入的世界"),
                ("§r§f - 個人世界理論上只有你能自由出入"),
                ("§r§f   除非允許他人傳送 否則是無法前往"),
                ("§r§f - 個人世界可以調整許多的遊戲規則")
        ));
        // 寫入資料
        item.setItemMeta(meta);
        // 設置完成
        inv.setItem(45, item);


        // --------------------------------------     46     --------------------------------------
        item = new ItemStack(Material.MILK_BUCKET);
        meta = item.getItemMeta();
        meta.setDisplayName("§b購買個人世界(正常環境-空島)");
        meta.setLore(Arrays.asList(
                ("§r§f (點擊) 購買"),
                ("§r§f - 消耗 1024 顆綠寶石"),
                ("§r§f - 最多可同時擁有 9 個"),
                ("§r§f - 大小為 2048 * 256 * 2048")
        ));
        // 寫入資料
        item.setItemMeta(meta);
        // 設置完成
        inv.setItem(46, item);

        // --------------------------------------     47     --------------------------------------
        item = new ItemStack(Material.TROPICAL_FISH_BUCKET);
        meta = item.getItemMeta();
        meta.setDisplayName("§b購買個人世界(正常環境-生態域)");
        meta.setLore(Arrays.asList(
                ("§r§f (點擊) 購買"),
                ("§r§f - 消耗 2176 顆綠寶石"),
                ("§r§f - 最多可同時擁有 9 個"),
                ("§r§f - 大小為 2048 * 256 * 2048")
        ));
        // 寫入資料
        item.setItemMeta(meta);
        // 設置完成
        inv.setItem(47, item);

        // --------------------------------------     48     --------------------------------------
        item = new ItemStack(Material.COD_BUCKET);
        meta = item.getItemMeta();
        meta.setDisplayName("§b購買個人世界(地域環境)");
        meta.setLore(Arrays.asList(
                ("§r§f (點擊) 購買"),
                ("§r§f - 消耗 2176 顆綠寶石"),
                ("§r§f - 最多可同時擁有 9 個"),
                ("§r§f - 大小為 2048 * 256 * 2048")
        ));
        // 寫入資料
        item.setItemMeta(meta);
        // 設置完成
        inv.setItem(48, item);

        // --------------------------------------     49     --------------------------------------
        item = new ItemStack(Material.SALMON_BUCKET);
        meta = item.getItemMeta();
        meta.setDisplayName("§b購買個人世界(終界環境)");
        meta.setLore(Arrays.asList(
                ("§r§f (點擊) 購買"),
                ("§r§f - 消耗 2176 顆綠寶石"),
                ("§r§f - 最多可同時擁有 9 個"),
                ("§r§f - 大小為 2048 * 256 * 2048")
        ));
        // 寫入資料
        item.setItemMeta(meta);
        // 設置完成
        inv.setItem(49, item);


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
    final public void onInventoryClickEvent(InventoryClickEvent event) {
        // 判斷是否為玩家

        if (event.getWhoClicked() instanceof Player) {
            // 是玩家
            Player player = (Player) event.getWhoClicked();

            // 檢查容器名稱
            if (event.getView().getTitle().equalsIgnoreCase("§z多世界系統")) {
                // 是沒錯

                event.setCancelled(true);


                if (event.getRawSlot() < 9) {


                    // 個人空間
                    try {
                        Connection con = MySQL.getConnection(); // 連線 MySQL
                        Statement sta = con.createStatement(); // 取得控制庫

                        // 取得世界
                        ResultSet res = sta.executeQuery("SELECT (Name) FROM `world-list_data` WHERE `Enable` = '1' AND `Group` IS NULL AND `Owner` = '" + player.getName() + "' ORDER BY `Start_Time` LIMIT 1 OFFSET " + event.getRawSlot());
                        //跳到最後一行
                        res.last();
                        // 最後一行 行數 是否 > 0
                        if (res.getRow() > 0) {
                            // 有資料
                            // 跳回 初始行 必須使用 next() 才能取得資料
                            res.beforeFirst();
                            res.next();

                            // 檢查 點擊是否為
                            if (event.isShiftClick() && event.isRightClick()) {
                                // shift + 右鍵點擊
                                // 刪除
                                Data.Unload(res.getString("Name"), false);
                                Data.Delete(res.getString("Name"));

                                res.close(); // 關閉查詢

                                player.sendMessage("[多世界系統]  刪除成功");

                                menu(player); // 重開本介面

                            } else if (event.isRightClick()) {
                                // 右鍵點擊
                                // 設定
                                Set_World(player, res.getString("Name"));

                            } else if (event.isLeftClick()) {
                                // 左鍵點擊
                                // 傳送
                                SuperFreedomSurvive.Player.Teleport.SpawnLocation(player, res.getString("Name"));

                            }
                        }

                        res.close(); // 關閉查詢
                        sta.close(); // 關閉連線

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }


                    //ServerPlugin.Player.Teleport.SpawnLocation( player , "w_p/" + player.getName() );


                } else if (event.getRawSlot() == 9) {
                    // 主城
                    // 前往
                    SuperFreedomSurvive.Player.Teleport.SpawnLocation(player, "w");

                /*
                } else if (event.getRawSlot() == 2) {
                    // 地域
                    // 前往
                    ServerPlugin.Player.Teleport.SpawnLocation( player , "w_nether" );


                } else if (event.getRawSlot() == 3) {
                    // 終界
                    // 前往
                    ServerPlugin.Player.Teleport.SpawnLocation( player , "w_the_end" );
                */


                } else if (event.getRawSlot() > 9 && event.getRawSlot() < 45) {
                    // 目標地圖
                    // 使用名稱直接嘗試傳送
                    try {
                        ItemMeta meta = event.getCurrentItem().getItemMeta();

                        Connection con = MySQL.getConnection(); // 連線 MySQL
                        Statement sta = con.createStatement(); // 取得控制庫

                        if ( meta == null ) { return; }

                        String name = event.getCurrentItem().getItemMeta().getCustomTagContainer().getCustomTag(key, ItemTagType.STRING);

                        // 取得世界
                        ResultSet res = sta.executeQuery("SELECT (Name),(Transfer) FROM `world-list_data` WHERE `Enable` = '1' AND `Group` = 'SERVER' AND `Start_Time` < '" + SuperFreedomSurvive.SYSTEM.Time.Get() + "' AND `End_Time` > '" + SuperFreedomSurvive.SYSTEM.Time.Get() + "' AND `Name` = '" + name + " 'ORDER BY `Start_Time` LIMIT 1");
                        //跳到最後一行
                        res.last();
                        // 最後一行 行數 是否 > 0
                        if (res.getRow() > 0) {
                            // 有資料
                            // 跳回 初始行 必須使用 next() 才能取得資料
                            res.beforeFirst();
                            res.next();

                            if (res.getBoolean("Transfer")) {
                                SuperFreedomSurvive.Player.Teleport.SpawnLocation(player, res.getString("Name"));

                                res.close(); // 關閉查詢

                            } else {
                                res.close(); // 關閉查詢

                                player.sendMessage("[多世界系統]  §c請以其他管道進入該世界");
                            }

                        }

                        res.close(); // 關閉查詢
                        sta.close(); // 關閉連線

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }


                    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                } else if (event.getRawSlot() == 46) {
                    // 購買個人世界
                    if (Pay.Have(player, 1024)) {

                        // 個人空間
                        try {
                            Connection con = MySQL.getConnection(); // 連線 MySQL
                            Statement sta = con.createStatement(); // 取得控制庫

                            // 取得世界
                            ResultSet res = sta.executeQuery("SELECT (Name) FROM `world-list_data` WHERE `Enable` = '1' AND `Group` IS NULL AND `Owner` = '" + player.getName() + "' ORDER BY `Start_Time` LIMIT 10");
                            //跳到最後一行
                            res.last();
                            // 最後一行 行數 是否 > 0
                            if (res.getRow() >= 9) {
                                // 有資料

                                res.close(); // 關閉查詢

                                player.sendMessage("[多世界系統]  §c你已經擁有最多 9 個個人世界了");


                            } else {
                                // 收取綠寶石

                                res.close(); // 關閉查詢

                                if (Pay.Recover(player, 1024)) {

                                    new_private_world(player, org.bukkit.World.Environment.NORMAL, WorldType.FLAT);

                                    menu(player);

                                }
                            }

                            res.close(); // 關閉查詢
                            sta.close(); // 關閉連線

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        player.sendMessage("[多世界系統]  §c所需物資不夠! 需要 1024 顆 綠寶石");
                    }


                } else if (event.getRawSlot() >= 47 && event.getRawSlot() <= 49) {
                    // 購買個人世界
                    if (Pay.Have(player, 2176)) {

                        // 個人空間
                        try {
                            Connection con = MySQL.getConnection(); // 連線 MySQL
                            Statement sta = con.createStatement(); // 取得控制庫

                            // 取得世界
                            ResultSet res = sta.executeQuery("SELECT (Name) FROM `world-list_data` WHERE `Enable` = '1' AND `Group` IS NULL AND `Owner` = '" + player.getName() + "' ORDER BY `Start_Time` LIMIT 10");
                            //跳到最後一行
                            res.last();
                            // 最後一行 行數 是否 > 0
                            if (res.getRow() >= 9) {
                                // 有資料

                                res.close(); // 關閉查詢

                                player.sendMessage("[多世界系統]  §c你已經擁有最多 9 個個人世界了");


                            } else {
                                // 收取綠寶石

                                res.close(); // 關閉查詢

                                if (Pay.Recover(player, 2176)) {

                                    if (event.getRawSlot() == 47) {
                                        //new_private_world(player, org.bukkit.World.Environment.NORMAL, WorldType.LARGE_BIOMES);
                                        new_private_world(player, org.bukkit.World.Environment.NORMAL, WorldType.NORMAL);

                                    } else if (event.getRawSlot() == 48) {
                                        new_private_world(player, org.bukkit.World.Environment.NETHER, WorldType.NORMAL);

                                    } else if (event.getRawSlot() == 49) {
                                        new_private_world(player, org.bukkit.World.Environment.THE_END, WorldType.NORMAL);

                                    }

                                    menu(player);

                                }
                            }

                            res.close(); // 關閉查詢
                            sta.close(); // 關閉連線

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        player.sendMessage("[多世界系統]  §c所需物資不夠! 需要 2176 顆 綠寶石");
                    }


                    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                } else if (event.getRawSlot() == 52) {
                    // 上一頁
                    SuperFreedomSurvive.Menu.Open.open(player);

                } else if (event.getRawSlot() == 53) {
                    // 關閉
                    event.getWhoClicked().closeInventory();

                } else {
                    //event.setCancelled(true);
                }

            }
        }
    }

    static final public void new_private_world(Player player, org.bukkit.World.Environment environment, WorldType world_type) {
        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫

            // 創建唯一ID
            String random = "";
            String[] RegSNContent = {
                    "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
                    "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"
            };
            for (int i = 0; i < 8; i++)
                random += RegSNContent[(int) (Math.random() * RegSNContent.length)];

            org.bukkit.World world = null;

            // 空島 要鋪泥土
            if (environment == org.bukkit.World.Environment.NORMAL && world_type == WorldType.FLAT) {
                world = Data.New(
                        "w_p/" + random,
                        player.getName(),
                        environment,
                        false,
                        0,
                        world_type,
                        "{\"biome\":\"minecraft:the_void\",\"layers\":[{\"block\":\"minecraft:air\",\"height\":1}]}",
                        2048,
                        GameMode.SURVIVAL,
                        true,
                        true,
                        Difficulty.EASY,
                        null,
                        Material.CHEST,
                        "個人世界",
                        true,
                        true,
                        10,
                        true,
                        true,
                        true,
                        true,
                        true,
                        true
                );

                for (int x = -5; x <= 5; ++x) {
                    for (int z = -5; z <= 5; ++z) {
                        world.getBlockAt(x, 59, z).setType(Material.GRASS_BLOCK);
                    }
                }
            } else {
                world = Data.New(
                        "w_p/" + random,
                        player.getName(),
                        environment,
                        false,
                        0,
                        world_type,
                        null,
                        2048,
                        GameMode.SURVIVAL,
                        true,
                        true,
                        Difficulty.EASY,
                        null,
                        Material.CHEST,
                        "個人世界",
                        true,
                        true,
                        10,
                        true,
                        true,
                        true,
                        true,
                        true,
                        true
                );
            }

            // 地獄 要撤銷一點基岩
            if (environment == org.bukkit.World.Environment.NETHER) {
                for (int y = 129; y >= 100; --y) {
                    world.getBlockAt(0, y, 0).setType(Material.AIR, false);
                    world.getBlockAt(1, y, 0).setType(Material.AIR, false);
                    world.getBlockAt(0, y, 1).setType(Material.AIR, false);
                    world.getBlockAt(1, y, 1).setType(Material.AIR, false);
                }
            }

            // 設置世界出生地
            world.setSpawnLocation(0, 60, 0);

            // 創建唯一ID
            String random_ = "";
            String[] RegSNContent_ = {
                    "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
                    "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
                    "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
                    "_", "+", "-", ".", "~", "/", "=", "[", "]", "(", ")", "<", ">"
            };
            for (int i = 0; i < 20; i++)
                random_ += RegSNContent_[(int) (Math.random() * RegSNContent_.length)];

            // 將全部世界都設為該玩家的保護點
            sta.executeUpdate("INSERT INTO `land-protection_data` (`Min_X`, `Min_Y`, `Min_Z`, `Max_X`, `Max_Y`, `Max_Z`, `Level`, `Owner`, `User`, `World`, `ID`, `Time`, `Unable_Delete`) VALUES ('-1024', '0', '-1024', '1024', '255', '1024', '0', '" + player.getName() + "', '" + player.getName() + "', '" + ("w_p/" + random) + "', '" + random_ + "','" + SuperFreedomSurvive.SYSTEM.Time.Get() + "','1')");

            player.sendMessage("[多世界系統]  創建成功");


            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    static final public void Set_World(Player player, String name) {
        // 設定世界資料


        ItemStack item;
        ItemMeta meta;

        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫
            ResultSet res;

            res = sta.executeQuery("SELECT * FROM `world-list_data` WHERE `Name` = '" + name + "' LIMIT 1");
            res.next();

            // 是否有權限
            if (player.getName().equals(res.getString("Owner"))) {
                // 給予介面

                // 顯示容器
                Inventory inv = Bukkit.createInventory(null, 27, "§z個人世界設定");


                /////////////////////////////////////// 0 ///////////////////////////////////////
                item = new ItemStack(Material.getMaterial(res.getString("ID")));
                meta = item.getItemMeta();
                meta.setDisplayName("§r§e" + res.getString("Description"));
                meta.setLore(Arrays.asList(
                        ("§r§f (鼠標物品點擊) 修改顯示物品"),
                        ("§r§f (鼠標空手點擊) 修改顯示名稱")
                ));
                meta.getCustomTagContainer().setCustomTag(key, ItemTagType.STRING, name); // 登入key
                // 寫入資料
                item.setItemMeta(meta);
                // 設置完成
                inv.setItem(0, item);


                /////////////////////////////////////////////////////////////////////////////////

                inv.setItem(1, getItemStack_Boolean(res.getBoolean("PVP"), name, "PVP", Material.DIAMOND_SWORD));
                inv.setItem(2, getItemStack_Boolean(res.getBoolean("KEEP_INVENTORY"), name, "防噴裝保護", Material.TOTEM_OF_UNDYING));
                inv.setItem(3, getItemStack_Boolean(res.getBoolean("NATURAL_REGENERATION"), name, "自然回復生命", Material.ENCHANTED_GOLDEN_APPLE));
                inv.setItem(4, getItemStack_Boolean(res.getBoolean("MOB_GRIEFING"), name, "生物拾取物品", Material.DIAMOND_HELMET));
                inv.setItem(5, getItemStack_Boolean(res.getBoolean("DO_WEATHER_CYCLE"), name, "日夜變化", Material.CLOCK));
                inv.setItem(6, getItemStack_Boolean(res.getBoolean("DO_FIRE_TICK"), name, "火焰自然熄滅/蔓延", Material.BLAZE_POWDER));
                inv.setItem(7, getItemStack_Boolean(res.getBoolean("DO_MOB_LOOT"), name, "生物死亡掉落物品", Material.ROTTEN_FLESH));
                inv.setItem(8, getItemStack_Boolean(res.getBoolean("DO_MOB_SPAWNING"), name, "生物自然生成", Material.ZOMBIE_SPAWN_EGG));
                inv.setItem(9, getItemStack_Boolean(res.getBoolean("DO_TILE_DROPS"), name, "破壞方塊掉落物品", Material.COBBLESTONE));

                /////////////////////////////////////////////////////////////////////////////////

                /////////////////////////////////////// 10 ///////////////////////////////////////

                item = new ItemStack(Material.BOOK);
                meta = item.getItemMeta();
                meta.setDisplayName("§r§e難度");
                if (res.getInt("Difficulty") == 0) {
                    meta.setLore(Arrays.asList(
                            "§r§f§l   難度 : 和平",
                            "§r§f (點擊) 切換設定狀態"
                    ));
                } else if (res.getInt("Difficulty") == 1) {
                    meta.setLore(Arrays.asList(
                            "§r§f§l   難度 : 簡單",
                            "§r§f (點擊) 切換設定狀態"
                    ));
                } else if (res.getInt("Difficulty") == 2) {
                    meta.setLore(Arrays.asList(
                            "§r§f§l   難度 : 普通",
                            "§r§f (點擊) 切換設定狀態"
                    ));
                } else if (res.getInt("Difficulty") == 3) {
                    meta.setLore(Arrays.asList(
                            "§r§f§l   難度 : 困難",
                            "§r§f (點擊) 切換設定狀態"
                    ));
                }
                meta.getCustomTagContainer().setCustomTag(key, ItemTagType.STRING, name); // 登入key

                // 寫入資料
                item.setItemMeta(meta);
                // 設置完成
                inv.setItem(10, item);
                /////////////////////////////////////////////////////////////////////////////////

                res.close(); // 關閉查詢


                // --------------------------------------     11     --------------------------------------
                item = new ItemStack(Material.LIME_BED);
                meta = item.getItemMeta();
                meta.setDisplayName("§e設置世界重生點");
                meta.setLore(Arrays.asList(
                        "§r§f (點擊) 將當前位置設為世界重生點",
                        "§r§f - 必須在此世界中才能設置此功能"
                ));
                meta.getCustomTagContainer().setCustomTag(key, ItemTagType.STRING, name); // 登入key
                // 寫入資料
                item.setItemMeta(meta);
                // 設置完成
                inv.setItem(11, item);


                // --------------------------------------     12     --------------------------------------
                item = new ItemStack(Material.FLOWER_POT);
                meta = item.getItemMeta();
                meta.setDisplayName("§e關閉個人世界");
                meta.setLore(Arrays.asList(
                        "§r§f (點擊) 暫時關閉個人世界",
                        "§r§f - 必須在此世界中才能設置此功能",
                        "§r§f - 世界內所有玩家傳送到主城"
                ));
                meta.getCustomTagContainer().setCustomTag(key, ItemTagType.STRING, name); // 登入key
                // 寫入資料
                item.setItemMeta(meta);
                // 設置完成
                inv.setItem(12, item);


                // --------------------------------------     52     --------------------------------------
                inv.setItem(25, SuperFreedomSurvive.Menu.Open.PreviousPage());


                // --------------------------------------     53     --------------------------------------
                inv.setItem(26, SuperFreedomSurvive.Menu.Open.TurnOff());


                // ---------------------------------------------------------------------------------------
                // 寫入到容器頁面
                player.openInventory(inv);


            } else {
                player.sendMessage("[多世界系統]  §c你無權限編輯");

            }

            res.close(); // 關閉查詢
            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    // 快速給予 ItemStack 接口
    final public static ItemStack getItemStack_Boolean(Boolean status, String name, String type, Material id) {
        ItemStack item = new ItemStack(id);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§r§e" + type);
        if (status) {
            // true
            meta.setLore(Arrays.asList(
                    ("§r§f§l   目前狀態 §a有"),
                    ("§r§f (點擊) 切換設定狀態")
            ));

        } else {
            // false
            meta.setLore(Arrays.asList(
                    ("§r§f§l   目前狀態 §c沒有"),
                    ("§r§f (點擊) 切換設定狀態")
            ));

        }

        meta.getCustomTagContainer().setCustomTag(key, ItemTagType.STRING, name); // 登入key

        // 寫入資料
        item.setItemMeta(meta);
        // 返回
        return item;
        // 設置完成
        //inv.setItem( 35 , item );
    }


    // 阻止玩家拿走物品
    @EventHandler
    final public void onInventoryClickEvent_(InventoryClickEvent event) {
        // 判斷是否為玩家

        if (event.getWhoClicked() instanceof Player) {
            // 是玩家
            Player player = (Player) event.getWhoClicked();

            // 檢查容器名稱
            if (event.getView().getTitle().equalsIgnoreCase("§z個人世界設定")) {
                // 是沒錯

                if (event.getRawSlot() < 27) {
                    event.setCancelled(true);
                }

                if (event.isShiftClick()) {
                    event.setCancelled(true);
                }

                try {

                    if (event.getCurrentItem() == null) { return; }
                    if (event.getCurrentItem().getItemMeta() == null) { return; }

                    String name = event.getCurrentItem().getItemMeta().getCustomTagContainer().getCustomTag(key, ItemTagType.STRING);

                    if (event.getRawSlot() == 0) {
                        ItemStack item = event.getCursor();

                        if (item.getType() == Material.AIR) {
                            // 修改文字

                            player.closeInventory();
                            player.sendMessage("[多世界系統]  §a請輸入要顯示的名稱  支持使用&顏色代碼");

                            SuperFreedomSurvive.SYSTEM.Input.setRegistry(player,
                                    new BukkitRunnable() {
                                        int time = 1200;
                                        String message = null;

                                        @Override
                                        final public void run() {
                                            if (time < 0) {
                                                player.sendMessage("[多世界系統]  §c等待輸入太久了 自動取消");
                                                cancel();
                                                return;
                                            }
                                            message = Input.getMessage(player);
                                            if (message != null) {
                                                // 有資料

                                                message = ColorCodeTransform.Conversion(message);

                                                if (message.length() > 40) {
                                                    message = message.substring(0, 40);
                                                }

                                                try {
                                                    Connection con = MySQL.getConnection(); // 連線 MySQL
                                                    Statement sta = con.createStatement(); // 取得控制庫
                                                    ResultSet res = sta.executeQuery("SELECT * FROM `world-list_data` WHERE `Name` = '" + name + "' AND `Owner` = '" + player.getName() + "' LIMIT 1");
                                                    res.last();
                                                    // 最後一行 行數 是否 > 0
                                                    if (res.getRow() > 0) {
                                                        // 有資料
                                                        // 跳回 初始行 必須使用 next() 才能取得資料
                                                        res.beforeFirst();
                                                        res.next();

                                                        res.close(); // 關閉查詢

                                                        sta.executeUpdate("UPDATE `world-list_data` SET `Description` = '" + message + "' WHERE `Name` = '" + name + "' AND `Owner` = '" + player.getName() + "'LIMIT 1;");

                                                        player.sendMessage("[多世界系統]  設置成功");

                                                        Set_World(player, name); // 重開本介面

                                                    }

                                                    res.close(); // 關閉查詢
                                                    sta.close(); // 關閉連線

                                                } catch (Exception ex) {
                                                    ex.printStackTrace();
                                                    menu(player);
                                                }

                                                cancel();
                                                return;
                                            }
                                            --time;
                                        }
                                    }.runTaskTimer(SuperFreedomSurvive.SYSTEM.Plugin.get(), 0L, 1L)
                            );


                        } else {
                            // 修改圖示
                            String material = item.getType().name();

                            try {
                                Connection con = MySQL.getConnection(); // 連線 MySQL
                                Statement sta = con.createStatement(); // 取得控制庫

                                ResultSet res = sta.executeQuery("SELECT * FROM `world-list_data` WHERE `Name` = '" + name + "' AND `Owner` = '" + player.getName() + "' LIMIT 1");
                                res.last();
                                // 最後一行 行數 是否 > 0
                                if (res.getRow() > 0) {
                                    // 有資料
                                    // 跳回 初始行 必須使用 next() 才能取得資料
                                    res.beforeFirst();
                                    res.next();
                                    // 跑完全部資料
                                    // 檢查是否是我們要的資料
                                    // 是需要的資料
                                    // 進行編輯

                                    res.close(); // 關閉查詢

                                    sta.executeUpdate("UPDATE `world-list_data` SET `ID` = '" + material + "' WHERE `Name` = '" + name + "' AND `Owner` = '" + player.getName() + "'LIMIT 1;");

                                    player.sendMessage("[多世界系統]  設置成功");

                                    Set_World(player, name); // 重開本介面

                                }

                                res.close(); // 關閉查詢
                                sta.close(); // 關閉連線

                            } catch (Exception ex) {
                                ex.printStackTrace();
                                menu(player);
                            }

                        }

                    } else if (event.getRawSlot() == 1) {
                        Set_World_Boolean(name, "PVP");
                        Set_World(player, name); // 重開本介面

                    } else if (event.getRawSlot() == 2) {
                        Set_World_Boolean(name, "KEEP_INVENTORY");
                        Set_World(player, name); // 重開本介面

                    } else if (event.getRawSlot() == 3) {
                        Set_World_Boolean(name, "NATURAL_REGENERATION");
                        Set_World(player, name); // 重開本介面

                    } else if (event.getRawSlot() == 4) {
                        Set_World_Boolean(name, "MOB_GRIEFING");
                        Set_World(player, name); // 重開本介面

                    } else if (event.getRawSlot() == 5) {
                        Set_World_Boolean(name, "DO_WEATHER_CYCLE");
                        Set_World(player, name); // 重開本介面

                    } else if (event.getRawSlot() == 6) {
                        Set_World_Boolean(name, "DO_FIRE_TICK");
                        Set_World(player, name); // 重開本介面

                    } else if (event.getRawSlot() == 7) {
                        Set_World_Boolean(name, "DO_MOB_LOOT");
                        Set_World(player, name); // 重開本介面

                    } else if (event.getRawSlot() == 8) {
                        Set_World_Boolean(name, "DO_MOB_SPAWNING");
                        Set_World(player, name); // 重開本介面

                    } else if (event.getRawSlot() == 9) {
                        Set_World_Boolean(name, "DO_TILE_DROPS");
                        Set_World(player, name); // 重開本介面

                    } else if (event.getRawSlot() == 10) {

                        // 讀取當前狀態
                        Connection con = MySQL.getConnection(); // 連線 MySQL
                        Statement sta = con.createStatement(); // 取得控制庫

                        // 檢查是否在保護區域內
                        ResultSet res = sta.executeQuery("SELECT (Difficulty) FROM `world-list_data` WHERE `Name` = '" + name + "' LIMIT 1");
                        // 跳到最後一行
                        res.last();
                        // 最後一行 行數 是否 > 0
                        if (res.getRow() > 0) {
                            // 有資料
                            // 跳回 初始行 必須使用 next() 才能取得資料
                            res.beforeFirst();
                            res.next();

                            if (res.getInt("Difficulty") == 0) {
                                res.close(); // 關閉查詢
                                sta.executeUpdate("UPDATE `world-list_data` SET `Difficulty` = '1' WHERE `Name` = '" + name + "'");

                            } else if (res.getInt("Difficulty") == 1) {
                                res.close(); // 關閉查詢
                                sta.executeUpdate("UPDATE `world-list_data` SET `Difficulty` = '2' WHERE `Name` = '" + name + "'");

                            } else if (res.getInt("Difficulty") == 2) {
                                res.close(); // 關閉查詢
                                sta.executeUpdate("UPDATE `world-list_data` SET `Difficulty` = '3' WHERE `Name` = '" + name + "'");

                            } else if (res.getInt("Difficulty") == 3) {
                                res.close(); // 關閉查詢
                                sta.executeUpdate("UPDATE `world-list_data` SET `Difficulty` = '0' WHERE `Name` = '" + name + "'");

                            }
                            Set_World(player, name); // 重開本介面
                        }

                        if (SuperFreedomSurvive.World.List.Have(name)) {
                            Data.Setting(Bukkit.getWorld(name));
                        }

                        res.close(); // 關閉查詢
                        sta.close(); // 關閉連線


                    } else if (event.getRawSlot() == 11) {
                        // 設置世界重生點
                        if (player.getWorld().getName().equals(name)) {
                            if (Data.Have(name)) {

                                Bukkit.getWorld(name).setSpawnLocation(player.getLocation());
                                player.sendMessage("[多世界系統]  設置成功");
                                Set_World(player, name); // 重開本介面

                            } else {
                                player.sendMessage("[多世界系統]  §c設置失敗 世界未被載入");

                            }

                        } else {
                            // 錯誤
                            player.sendMessage("[多世界系統]  §c你必須在此世界中才能設置此功能");

                        }


                    } else if (event.getRawSlot() == 12) {
                        // 設置世界重生點
                        if (player.getWorld().getName().equals(name)) {
                            if (Data.Have(name)) {

                                Data.Unload(name, true);
                                //Bukkit.getWorld().setSpawnLocation(player.getLocation() );
                                player.sendMessage("[多世界系統]  關閉成功");
                                //Set_World(player, name); // 重開本介面

                            } else {
                                player.sendMessage("[多世界系統]  §c設置失敗 世界未被載入");

                            }

                        } else {
                            // 錯誤
                            player.sendMessage("[多世界系統]  §c你必須在此世界中才能設置此功能");

                        }


                    }
                    if (event.getRawSlot() == 25) {
                        // 上一頁
                        menu(player);

                    } else if (event.getRawSlot() == 26) {
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

    // 快速給設置權限 接口
    final public static void Set_World_Boolean(String name, String data) {
        try {
            // 讀取當前狀態
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫

            //Bukkit.getConsoleSender().sendMessage(name);
            //Bukkit.getConsoleSender().sendMessage(data);

            // 檢查是否在保護區域內
            ResultSet res = sta.executeQuery("SELECT (" + data + ") FROM `world-list_data` WHERE `Name` = '" + name + "' LIMIT 1");
            // 跳到最後一行
            res.last();
            // 最後一行 行數 是否 > 0
            if (res.getRow() > 0) {
                // 有資料
                // 跳回 初始行 必須使用 next() 才能取得資料
                res.beforeFirst();
                res.next();
                if (res.getBoolean(data)) {
                    res.close(); // 關閉查詢

                    // 當前值為 true
                    // 改為 false
                    sta.executeUpdate("UPDATE `world-list_data` SET `" + data + "` = '0' WHERE `Name` = '" + name + "'");
                } else {
                    res.close(); // 關閉查詢

                    // 當前值為 false
                    // 改為 true
                    sta.executeUpdate("UPDATE `world-list_data` SET `" + data + "` = '1' WHERE `Name` = '" + name + "'");
                }


                if (SuperFreedomSurvive.World.List.Have(name)) {
                    Data.Setting(Bukkit.getWorld(name));
                }
            }

            res.close(); // 關閉查詢
            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @EventHandler
    final public void onInventoryDragEvent(InventoryDragEvent event) {
        if (event.getView().getTitle().equalsIgnoreCase("§z個人世界設定")) {
            event.setCancelled(true);
        }
    }


}
