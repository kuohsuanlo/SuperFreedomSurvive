package SuperFreedomSurvive.Menu.Function;

import SuperFreedomSurvive.Land.Setting;
import SuperFreedomSurvive.Player.Data;
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
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.inventory.meta.tags.CustomItemTagContainer;
import org.bukkit.inventory.meta.tags.ItemTagType;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

final public class Land implements Listener {

    static final public org.bukkit.NamespacedKey player_key = SuperFreedomSurvive.SYSTEM.NamespacedKey.getKey("Player");


    // 選單接口
    final public static void menu(Player player) {
        // 顯示容器
        Inventory inv = Bukkit.createInventory(null, 18, "§z領地系統");

        ItemStack item;
        ItemMeta meta;
        // --------------------------------------     0     --------------------------------------
        item = new ItemStack(Material.MAP);
        meta = item.getItemMeta();
        meta.setDisplayName("§e使用工具選擇範圍");
        meta.setLore(Arrays.asList(
                ("§r§f (提醒) 木鋤 = 創建領地"),
                ("§r§f        木鏟 = 創建子領地"),
                ("§r§f        "),
                ("§r§f (說明) 製作圈地需要的工具 並拿在手上"),
                ("§r§f        選擇好立方體空間 在對角線的兩點位置"),
                ("§r§f        <左鍵> 設置圈地開始點"),
                ("§r§f        <右鍵> 不斷確認尺寸 (不影響開始點)"),
                ("§r§f        "),
                ("§r§f (注意) 領地有分層數 可以不斷規劃小空間"),
                ("§r§f (價格) 每 10000 塊方塊/ 1 綠寶石")
        ));
        // 寫入資料
        item.setItemMeta(meta);
        // 設置完成
        inv.setItem(0, item);

        // --------------------------------------     1     --------------------------------------
        item = new ItemStack(Material.WOODEN_HOE);
        meta = item.getItemMeta();
        meta.setDisplayName("§r§e創建領地");
        meta.setLore(Arrays.asList(
                ("§r§f (點擊) 將圈好的範圍創建 領地"),
                ("§r§f (消耗) 每 10,000 格/消耗 1 綠寶石")
        ));
        // 寫入資料
        item.setItemMeta(meta);
        // 設置完成
        inv.setItem(1, item);


        // --------------------------------------     2     --------------------------------------
        item = new ItemStack(Material.WOODEN_SHOVEL);
        meta = item.getItemMeta();
        meta.setDisplayName("§r§e創建子領地");
        meta.setLore(Arrays.asList(
                ("§r§f (點擊) 將圈好的範圍創建 子領地"),
                ("§r§f - 不消耗綠寶石  但是 子區域 不能超過上一個領地範圍"),
                ("§r§f - 規劃子領地層數 最初是 1  最大可至 20 層")
        ));
        // 寫入資料
        item.setItemMeta(meta);
        // 設置完成
        inv.setItem(2, item);

        // --------------------------------------     3     --------------------------------------
        item = new ItemStack(Material.COBWEB);
        meta = item.getItemMeta();
        meta.setDisplayName("§r§e延伸領地大小");
        meta.setLore(Arrays.asList(
                ("§r§f (點擊) 選擇延伸的距離"),
                ("§r§f - 僅能對領地生效(子領地無效)"),
                ("§r§f - 抬頭 +Y"),
                ("§r§f - 低頭 -Y"),
                ("§r§f - 朝向 X/Z +X/-X/+Z/-Z")
        ));
        // 寫入資料
        item.setItemMeta(meta);
        // 設置完成
        inv.setItem(3, item);

        // --------------------------------------     4     --------------------------------------
        item = new ItemStack(Material.WRITABLE_BOOK);
        meta = item.getItemMeta();
        meta.setDisplayName("§r§e查看當前領地");
        meta.setLore(Arrays.asList(
                ("§r§f (點擊) 查看現在位置的領地"),
                ("§r§f - 察看/位置/新增/刪除/設置權限/轉移使用權")
        ));
        // 寫入資料
        item.setItemMeta(meta);
        // 設置完成
        inv.setItem(4, item);

        // --------------------------------------     5     --------------------------------------
        item = new ItemStack(Material.BOOK);
        meta = item.getItemMeta();
        meta.setDisplayName("§r§e全部領地清單");
        meta.setLore(Arrays.asList(
                ("§r§f (點擊) 查看自己的全部領地"),
                ("§r§f - 察看/位置/新增/刪除/設置權限/轉移使用權")
        ));
        // 寫入資料
        item.setItemMeta(meta);
        // 設置完成
        inv.setItem(5, item);


        // --------------------------------------     16     --------------------------------------
        inv.setItem(16, SuperFreedomSurvive.Menu.Open.PreviousPage());

        // --------------------------------------     17     --------------------------------------
        inv.setItem(17, SuperFreedomSurvive.Menu.Open.TurnOff());


        // ---------------------------------------------------------------------------------------

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
            if (event.getView().getTitle().equalsIgnoreCase("§z領地系統")) {
                // 是沒錯

                event.setCancelled(true);

                // 檢查點擊的是第幾個位置
                if (event.getRawSlot() == 1) {
                    // 創建領地
                    Setting.newLand(player);


                } else if (event.getRawSlot() == 2) {
                    // 創建子領地
                    Setting.newLandLevel(player);


                } else if (event.getRawSlot() == 3) {
                    // 朝面向方向延伸領地
                    land_extend(player);

                } else if (event.getRawSlot() == 4) {
                    // 查看當前領地
                    view(player);

                } else if (event.getRawSlot() == 16) {
                    // 上一頁
                    SuperFreedomSurvive.Menu.Open.open(player);

                } else if (event.getRawSlot() == 17) {
                    // 關閉
                    event.getWhoClicked().closeInventory();
                }

            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    // 朝面向方向延伸領地
    final public static void land_extend(Player player) {

        int X = new BigDecimal(player.getLocation().getX()).setScale(0, BigDecimal.ROUND_FLOOR).intValue();
        int Y = new BigDecimal(player.getLocation().getY()).setScale(0, BigDecimal.ROUND_FLOOR).intValue();
        int Z = new BigDecimal(player.getLocation().getZ()).setScale(0, BigDecimal.ROUND_FLOOR).intValue();
        String world = player.getLocation().getWorld().getName();

        // 確認是否有層數
        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫
            // 跳到最後一行
            ResultSet res = sta.executeQuery("SELECT (Owner),(User),(ID),(Unable_Delete) FROM `land-protection_data` WHERE `World` = '" + world + "' AND `Min_X` <= '" + X + "' AND `Min_Y` <= '" + Y + "' AND `Min_Z` <= '" + Z + "' AND `Max_X` >= '" + X + "' AND `Max_Y` >= '" + Y + "' AND `Max_Z` >= '" + Z + "' AND `Level` = '0' LIMIT 1");
            res.last();
            // 最後一行 行數 是否 > 0
            if (res.getRow() > 0) {
                // 有領地
                // 跳回 初始行 必須使用 next() 才能取得資料
                res.beforeFirst();
                // 行數下一行
                res.next();


                // 是否有權限
                if (SuperFreedomSurvive.Land.Permissions.Ownership(player, res.getString("ID"))) {
                    // 是

                    // 是否不可刪除
                    if (res.getBoolean("Unable_Delete")) {
                        // 不可刪除
                        player.sendMessage("[領地系統]  §c此領地不可延伸");

                        res.close(); // 關閉查詢

                    } else {

                        res.close(); // 關閉查詢

                        // 顯示容器
                        Inventory inv = Bukkit.createInventory(null, 18, "§z領地延伸");

                        ItemStack item;
                        ItemMeta meta;

                        // --------------------------------------     0     --------------------------------------
                        item = new ItemStack(Material.MAP, 1);
                        meta = item.getItemMeta();
                        meta.setDisplayName("§e往面向的座標延伸 1 格");
                        meta.setLore(Arrays.asList(
                                ("§r§f (點擊) 延伸")
                        ));
                        // 寫入資料
                        item.setItemMeta(meta);
                        // 設置完成
                        inv.setItem(0, item);

                        // --------------------------------------     1     --------------------------------------
                        item = new ItemStack(Material.MAP, 2);
                        meta = item.getItemMeta();
                        meta.setDisplayName("§e往面向的座標延伸 2 格");
                        meta.setLore(Arrays.asList(
                                ("§r§f (點擊) 延伸")
                        ));
                        // 寫入資料
                        item.setItemMeta(meta);
                        // 設置完成
                        inv.setItem(1, item);


                        // --------------------------------------     2     --------------------------------------
                        item = new ItemStack(Material.MAP, 4);
                        meta = item.getItemMeta();
                        meta.setDisplayName("§e往面向的座標延伸 4 格");
                        meta.setLore(Arrays.asList(
                                ("§r§f (點擊) 延伸")
                        ));
                        // 寫入資料
                        item.setItemMeta(meta);
                        // 設置完成
                        inv.setItem(2, item);

                        // --------------------------------------     3     --------------------------------------
                        item = new ItemStack(Material.MAP, 8);
                        meta = item.getItemMeta();
                        meta.setDisplayName("§e往面向的座標延伸 8 格");
                        meta.setLore(Arrays.asList(
                                ("§r§f (點擊) 延伸")
                        ));
                        // 寫入資料
                        item.setItemMeta(meta);
                        // 設置完成
                        inv.setItem(3, item);

                        // --------------------------------------     4     --------------------------------------
                        item = new ItemStack(Material.MAP, 16);
                        meta = item.getItemMeta();
                        meta.setDisplayName("§e往面向的座標延伸 16 格");
                        meta.setLore(Arrays.asList(
                                ("§r§f (點擊) 延伸")
                        ));
                        // 寫入資料
                        item.setItemMeta(meta);
                        // 設置完成
                        inv.setItem(4, item);

                        // --------------------------------------     5     --------------------------------------
                        item = new ItemStack(Material.MAP, 32);
                        meta = item.getItemMeta();
                        meta.setDisplayName("§e往面向的座標延伸 32 格");
                        meta.setLore(Arrays.asList(
                                ("§r§f (點擊) 延伸")
                        ));
                        // 寫入資料
                        item.setItemMeta(meta);
                        // 設置完成
                        inv.setItem(5, item);

                        // --------------------------------------     6     --------------------------------------
                        item = new ItemStack(Material.MAP, 64);
                        meta = item.getItemMeta();
                        meta.setDisplayName("§e往面向的座標延伸 64 格");
                        meta.setLore(Arrays.asList(
                                ("§r§f (點擊) 延伸")
                        ));
                        // 寫入資料
                        item.setItemMeta(meta);
                        // 設置完成
                        inv.setItem(6, item);

                        // --------------------------------------     7     --------------------------------------
                        item = new ItemStack(Material.MAP);
                        meta = item.getItemMeta();
                        meta.setDisplayName("§e往面向的座標延伸 128 格");
                        meta.setLore(Arrays.asList(
                                ("§r§f (點擊) 延伸")
                        ));
                        // 寫入資料
                        item.setItemMeta(meta);
                        // 設置完成
                        inv.setItem(7, item);

                        // --------------------------------------     8     --------------------------------------
                        item = new ItemStack(Material.MAP);
                        meta = item.getItemMeta();
                        meta.setDisplayName("§e往面向的座標延伸 256 格");
                        meta.setLore(Arrays.asList(
                                ("§r§f (點擊) 延伸")
                        ));
                        // 寫入資料
                        item.setItemMeta(meta);
                        // 設置完成
                        inv.setItem(8, item);


                        // --------------------------------------     16     --------------------------------------
                        inv.setItem(16, SuperFreedomSurvive.Menu.Open.PreviousPage());

                        // --------------------------------------     17     --------------------------------------
                        inv.setItem(17, SuperFreedomSurvive.Menu.Open.TurnOff());

                        player.openInventory(inv);

                    }
                } else {
                    player.sendMessage("[領地系統]  §c你無權限編輯");
                }
            } else {
                player.sendMessage("[領地系統]  §c你所站位置無領地");
            }

            res.close(); // 關閉查詢
            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    // 阻止玩家拿走物品
    @EventHandler
    final public void onInventoryClick__(InventoryClickEvent event) {
        // 判斷是否為玩家

        if (event.getWhoClicked() instanceof Player) {
            // 是玩家
            Player player = (Player) event.getWhoClicked();

            // 檢查容器名稱
            if (event.getView().getTitle().equalsIgnoreCase("§z領地延伸")) {
                // 是沒錯

                event.setCancelled(true);

                if (event.getRawSlot() >= 0 && event.getRawSlot() < 9) {
                    // 延伸領地

                    // 計算
                    int distance = 0;
                    switch ( event.getRawSlot()) {
                        case 0:
                            distance = 1;
                            break;
                        case 1:
                            distance = 2;
                            break;
                        case 2:
                            distance = 4;
                            break;
                        case 3:
                            distance = 8;
                            break;
                        case 4:
                            distance = 16;
                            break;
                        case 5:
                            distance = 32;
                            break;
                        case 6:
                            distance = 64;
                            break;
                        case 7:
                            distance = 128;
                            break;
                        case 8:
                            distance = 256;
                            break;
                        default:
                            break;
                    }

                    Setting.extendLand(player,distance);


                } else if (event.getRawSlot() == 16) {
                    // 上一頁
                    menu(player);

                } else if (event.getRawSlot() == 17) {
                    // 關閉
                    event.getWhoClicked().closeInventory();

                }
            }
        }
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    // 選單接口
    final public static void view(Player player) {
        // 查看領地街口

        // 顯示容器
        Inventory inv = Bukkit.createInventory(null, 36, "§z查看當前領地");

        ItemStack item;
        ItemMeta meta;

        // 取得位置


        int X = new BigDecimal(player.getLocation().getX()).setScale(0, BigDecimal.ROUND_FLOOR).intValue();
        int Y = new BigDecimal(player.getLocation().getY()).setScale(0, BigDecimal.ROUND_FLOOR).intValue();
        int Z = new BigDecimal(player.getLocation().getZ()).setScale(0, BigDecimal.ROUND_FLOOR).intValue();
        String world = player.getLocation().getWorld().getName();


        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫
            ResultSet res = sta.executeQuery("SELECT (Min_X),(Min_Y),(Min_Z),(Max_X),(Max_Y),(Max_Z),(Owner),(User),(Level),(Time) FROM `land-protection_data` WHERE `World` = '" + world + "' AND `Min_X` <= '" + X + "' AND `Min_Y` <= '" + Y + "' AND `Min_Z` <= '" + Z + "' AND `Max_X` >= '" + X + "' AND `Max_Y` >= '" + Y + "' AND `Max_Z` >= '" + Z + "' ORDER BY `Level` LIMIT 20");
            // 跳到最後一行
            res.last();
            // 最後一行 行數 是否 > 0
            if (res.getRow() > 0) {
                // 有領地
                // 跳回 初始行 必須使用 next() 才能取得資料
                res.beforeFirst();
                while (res.next()) {
                    // 跑完全部資料
                    // 檢查領地類型
                    if (res.getString("User") == null) {
                        // 待分配領地
                        item = new ItemStack(Material.MINECART);
                        meta = item.getItemMeta();
                        meta.setDisplayName("§d第 " + (res.getRow()) + " 層");
                        meta.setLore(Arrays.asList(
                                ("§r§f 待分配領地"),
                                ("§r§f - 擁有者 §a" + res.getString("Owner")),
                                ("§r§f - 領地詳細資料"),
                                ("§r§f   從 x " + res.getInt("Min_X")),
                                ("§r§f      y " + res.getInt("Min_Y")),
                                ("§r§f      z " + res.getInt("Min_Z")),
                                ("§r§f   到 x " + res.getInt("Max_X")),
                                ("§r§f      y " + res.getInt("Max_Y")),
                                ("§r§f      z " + res.getInt("Max_Z")),
                                ("§r§f   創建時間 " + res.getString("Time"))
                        ));
                        // 寫入資料
                        item.setItemMeta(meta);
                        // 設置完成
                        inv.setItem((res.getRow() - 1), item);

                    } else if (!res.getString("Owner").equals(res.getString("User"))) {
                        // 有人使用中 非擁有者本人
                        item = new ItemStack(Material.FURNACE_MINECART);
                        meta = item.getItemMeta();
                        meta.setDisplayName("§d第 " + (res.getRow()) + " 層");
                        meta.setLore(Arrays.asList(
                                ("§r§f 有人使用中 非擁有者本人"),
                                ("§r§f - 擁有者 §a" + res.getString("Owner")),
                                ("§r§f - 使用者 §a" + res.getString("User")),
                                ("§r§f   從 x " + res.getInt("Min_X")),
                                ("§r§f      y " + res.getInt("Min_Y")),
                                ("§r§f      z " + res.getInt("Min_Z")),
                                ("§r§f   到 x " + res.getInt("Max_X")),
                                ("§r§f      y " + res.getInt("Max_Y")),
                                ("§r§f      z " + res.getInt("Max_Z")),
                                ("§r§f   創建時間 " + res.getString("Time"))
                        ));
                        // 寫入資料
                        item.setItemMeta(meta);
                        // 設置完成
                        inv.setItem((res.getRow() - 1), item);

                    } else {
                        // 一般領地
                        // 是否為第一層
                        if (res.getRow() == 1) {
                            item = new ItemStack(Material.BEACON);
                            meta = item.getItemMeta();
                            meta.setDisplayName("§d第 " + (res.getRow()) + " 層");
                            meta.setLore(Arrays.asList(
                                    ("§r§f 一般領地"),
                                    ("§r§f - 擁有者 §a" + res.getString("Owner")),
                                    ("§r§f   從 x " + res.getInt("Min_X")),
                                    ("§r§f      y " + res.getInt("Min_Y")),
                                    ("§r§f      z " + res.getInt("Min_Z")),
                                    ("§r§f   到 x " + res.getInt("Max_X")),
                                    ("§r§f      y " + res.getInt("Max_Y")),
                                    ("§r§f      z " + res.getInt("Max_Z")),
                                    ("§r§f   創建時間 " + res.getString("Time"))
                            ));
                            // 寫入資料
                            item.setItemMeta(meta);
                            // 設置完成
                            inv.setItem((res.getRow() - 1), item);

                        } else {
                            // 一般子領地
                            item = new ItemStack(Material.CONDUIT);
                            meta = item.getItemMeta();
                            meta.setDisplayName("§d第 " + (res.getRow()) + " 層");
                            meta.setLore(Arrays.asList(
                                    ("§r§f 一般子領地"),
                                    ("§r§f - 擁有者 §a" + res.getString("Owner")),
                                    ("§r§f   從 x " + res.getInt("Min_X")),
                                    ("§r§f      y " + res.getInt("Min_Y")),
                                    ("§r§f      z " + res.getInt("Min_Z")),
                                    ("§r§f   到 x " + res.getInt("Max_X")),
                                    ("§r§f      y " + res.getInt("Max_Y")),
                                    ("§r§f      z " + res.getInt("Max_Z")),
                                    ("§r§f   創建時間 " + res.getString("Time"))
                            ));
                            // 寫入資料
                            item.setItemMeta(meta);
                            // 設置完成
                            inv.setItem((res.getRow() - 1), item);

                        }

                    }

                }


                res.close(); // 關閉查詢

                // 寫入到容器頁面
                player.openInventory(inv);

            } else {
                // 無領地
                player.sendMessage("[領地系統]  §c你所站位置無領地");
            }

            res.close(); // 關閉查詢
            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }


        // --------------------------------------     34     --------------------------------------
        inv.setItem(34, SuperFreedomSurvive.Menu.Open.PreviousPage());


        // --------------------------------------     35     --------------------------------------
        inv.setItem(35, SuperFreedomSurvive.Menu.Open.TurnOff());


        // ---------------------------------------------------------------------------------------


    }


    // 阻止玩家拿走物品
    @EventHandler
    final public void onInventoryClick(InventoryClickEvent event) {
        // 判斷是否為玩家

        if (event.getWhoClicked() instanceof Player) {
            // 是玩家
            Player player = (Player) event.getWhoClicked();
            int X = new BigDecimal(player.getLocation().getX()).setScale(0, BigDecimal.ROUND_FLOOR).intValue();
            int Y = new BigDecimal(player.getLocation().getY()).setScale(0, BigDecimal.ROUND_FLOOR).intValue();
            int Z = new BigDecimal(player.getLocation().getZ()).setScale(0, BigDecimal.ROUND_FLOOR).intValue();
            String world = player.getWorld().getName();

            // 檢查容器名稱
            if (event.getView().getTitle().equalsIgnoreCase("§z查看當前領地")) {
                // 是沒錯

                event.setCancelled(true);

                // 檢查點擊的是第幾個位置
                if (event.getRawSlot() < 20) {
                    // 設置領地 / 承租領地

                    // 確認是否有層數
                    try {
                        Connection con = MySQL.getConnection(); // 連線 MySQL
                        Statement sta = con.createStatement(); // 取得控制庫
                        // 跳到最後一行
                        ResultSet res = sta.executeQuery("SELECT (Owner),(User),(ID) FROM `land-protection_data` WHERE `World` = '" + world + "' AND `Min_X` <= '" + X + "' AND `Min_Y` <= '" + Y + "' AND `Min_Z` <= '" + Z + "' AND `Max_X` >= '" + X + "' AND `Max_Y` >= '" + Y + "' AND `Max_Z` >= '" + Z + "' AND `Level` = '" + event.getRawSlot() + "' LIMIT 1");
                        res.last();
                        // 最後一行 行數 是否 > 0
                        if (res.getRow() > 0) {
                            // 有領地

                            // 跳回 初始行 必須使用 next() 才能取得資料
                            res.beforeFirst();
                            // 行數下一行
                            res.next();

                            String ID = res.getString("ID");

                            res.close(); // 關閉查詢

                            // 查詢是否有此權限
                            if (SuperFreedomSurvive.Land.Permissions.Ownership(player, ID)) {
                                // 有
                                // 建立緩存
                                SuperFreedomSurvive.Land.Cache.set(player, ID);

                                // 跳轉到編輯頁面
                                set_land(player, ID);

                            } else {
                                player.sendMessage("[領地系統]  §c你無權限編輯");

                            }


                        } else {
                            // ! 否
                            //if (ServerPlugin.Land.Permissions.Is(player.getLocation()) ) {
                            //    player.sendMessage("[領地系統]  你無權限編輯");
                            //}
                        }

                        res.close(); // 關閉查詢
                        sta.close(); // 關閉連線

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }


                } else if (event.getRawSlot() == 34) {
                    // 上一頁
                    menu(player);

                } else if (event.getRawSlot() == 35) {
                    // 關閉
                    event.getWhoClicked().closeInventory();
                }

            }
        }


    }


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    // 選單接口
    final public static void set_land(Player player, String ID) {
        // 設定領地

        ItemStack item;
        ItemMeta meta;

        try {
            // 查詢是否有此權限
            if (SuperFreedomSurvive.Land.Permissions.Ownership(player, ID)) {
                // 有

                // 給予介面

                Connection con = MySQL.getConnection(); // 連線 MySQL
                Statement sta = con.createStatement(); // 取得控制庫
                ResultSet res = sta.executeQuery("SELECT * FROM `land-protection_data` WHERE `ID` = '" + ID + "' LIMIT 1");
                res.next();

                // 顯示容器
                Inventory inv = Bukkit.createInventory(null, 18, "§z領地設定");


                // --------------------------------------     0     --------------------------------------
                item = new ItemStack(Material.GRASS_BLOCK);
                meta = item.getItemMeta();
                meta.setDisplayName("§e方塊 權限系列");
                meta.setLore(Arrays.asList(
                        ("§r§f - 僅領地區域內生效"),
                        ("§r§f - 自己不受權限控制")
                ));
                // 寫入資料
                item.setItemMeta(meta);
                // 設置完成
                inv.setItem(0, item);

                // --------------------------------------     1     --------------------------------------
                item = new ItemStack(Material.PLAYER_HEAD);
                meta = item.getItemMeta();
                meta.setDisplayName("§e玩家 權限系列");
                meta.setLore(Arrays.asList(
                        ("§r§f - 僅領地區域內生效"),
                        ("§r§f - 自己不受權限控制")
                ));
                // 寫入資料
                item.setItemMeta(meta);
                // 設置完成
                inv.setItem(1, item);

                // --------------------------------------     2     --------------------------------------
                item = new ItemStack(Material.CHEST);
                meta = item.getItemMeta();
                meta.setDisplayName("§e容器 權限系列");
                meta.setLore(Arrays.asList(
                        ("§r§f - 僅領地區域內生效"),
                        ("§r§f - 自己不受權限控制")
                ));
                // 寫入資料
                item.setItemMeta(meta);
                // 設置完成
                inv.setItem(2, item);

                // --------------------------------------     3     --------------------------------------
                item = new ItemStack(Material.ZOMBIE_HEAD);
                meta = item.getItemMeta();
                meta.setDisplayName("§e實體 權限系列");
                meta.setLore(Arrays.asList(
                        ("§r§f - 僅領地區域內生效"),
                        ("§r§f - 自己不受權限控制")
                ));
                // 寫入資料
                item.setItemMeta(meta);
                // 設置完成
                inv.setItem(3, item);


                // --------------------------------------     4     --------------------------------------
                item = new ItemStack(Material.TROPICAL_FISH_BUCKET);
                meta = item.getItemMeta();
                meta.setDisplayName("§e新增共用者");
                meta.setLore(Arrays.asList(
                        ("§r§f - 讓使其他人也能使用"),
                        ("§r§f - 其他人無法控制權線")
                ));
                // 寫入資料
                item.setItemMeta(meta);
                // 設置完成
                inv.setItem(4, item);


                // --------------------------------------     5     --------------------------------------
                item = new ItemStack(Material.BUCKET);
                meta = item.getItemMeta();
                meta.setDisplayName("§e刪除共用者");
                meta.setLore(Arrays.asList(
                        ("§r§f - 刪除給予共用的玩家")
                ));
                // 寫入資料
                item.setItemMeta(meta);
                // 設置完成
                inv.setItem(5, item);


                // --------------------------------------     6     --------------------------------------
                item = new ItemStack(Material.MINECART);
                meta = item.getItemMeta();
                meta.setDisplayName("§e轉讓");
                meta.setLore(Arrays.asList(
                        ("§r§f - 讓使用者變更為其他人"),
                        ("§r§f - 擁有者不變"),
                        ("§r§f - 擁有者有使用權限 但無法控制權限")
                ));
                // 寫入資料
                item.setItemMeta(meta);
                // 設置完成
                inv.setItem(6, item);


                // --------------------------------------     7     --------------------------------------
                item = new ItemStack(Material.LAVA_BUCKET);
                meta = item.getItemMeta();
                meta.setDisplayName("§e刪除");
                meta.setLore(Arrays.asList(
                        ("§r§f - 包含裡面所有的 子領地"),
                        ("§r§f - 立即刪除領地 小心點擊!")
                ));
                // 寫入資料
                item.setItemMeta(meta);
                // 設置完成
                inv.setItem(7, item);

                // --------------------------------------     8     --------------------------------------
                // 自己也受影響
                inv.setItem(8, getItemStack(res.getBoolean("UserUsePermissions"), "自己是否也受到權限影響", Material.MAGMA_BLOCK));


                // --------------------------------------     16     --------------------------------------
                inv.setItem(16, SuperFreedomSurvive.Menu.Open.PreviousPage());


                // --------------------------------------     17     --------------------------------------
                inv.setItem(17, SuperFreedomSurvive.Menu.Open.TurnOff());


                // ---------------------------------------------------------------------------------------

                res.close(); // 關閉查詢
                sta.close(); // 關閉連線

                // 寫入到容器頁面
                player.openInventory(inv);


            } else {
                // ! 否
                player.sendMessage("[領地系統]  §c你無權限編輯");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    // 阻止玩家拿走物品
    @EventHandler
    final public void onInventoryClick_(InventoryClickEvent event) {
        // 判斷是否為玩家

        if (event.getWhoClicked() instanceof Player) {
            // 是玩家
            Player player = (Player) event.getWhoClicked();

            // 檢查容器名稱
            if (event.getView().getTitle().equalsIgnoreCase("§z領地設定")) {
                // 是沒錯
                event.setCancelled(true);

                try {

                    if (event.getRawSlot() == 17) {
                        // 關閉
                        event.getWhoClicked().closeInventory();

                    } else if (event.getRawSlot() == 16) {
                        // 上一頁
                        view(player);

                    } else {

                        String ID = SuperFreedomSurvive.Land.Cache.get(player); // 取得緩存
                        // 檢查是否有權限
                        if (SuperFreedomSurvive.Land.Permissions.Ownership(player, ID)) {
                            // 是

                            // 檢查點擊的是第幾個位置
                            switch (event.getRawSlot()) {
                                case 0:
                                    // 領地系統
                                    // 方塊 權限系列
                                    set_land_block(player);
                                    break;

                                case 1:
                                    // 領地系統
                                    // 玩家 權限系列
                                    set_land_player(player);
                                    break;

                                case 2:
                                    // 領地系統
                                    // 容器 權限系列
                                    set_land_interact(player);
                                    break;

                                case 3:
                                    // 領地系統
                                    // 實體 權限系列
                                    set_land_entity(player);
                                    break;

                                case 4:
                                    // 領地系統
                                    // 領地新增共用人
                                    land_share_new(player);
                                    break;

                                case 5:
                                    // 領地系統
                                    // 領地刪除共用人
                                    land_share_delete(player);
                                    break;

                                case 6:
                                    // 領地系統
                                    // 轉讓領地
                                    land_transfer(player);
                                    break;

                                case 7:
                                    // 領地系統
                                    // 刪除領地

                                    Connection con = MySQL.getConnection(); // 連線 MySQL
                                    Statement sta = con.createStatement(); // 取得控制庫
                                    ResultSet res = sta.executeQuery("SELECT (Min_X),(Min_Y),(Min_Z),(Max_X),(Max_Y),(Max_Z),(Owner),(User),(Level),(World),(Unable_Delete) FROM `land-protection_data` WHERE `ID` = '" + ID + "' LIMIT 1");
                                    res.next();
                                    if (res.getBoolean("Unable_Delete")) {
                                        // 不可刪除
                                        player.sendMessage("[領地系統]  §c此領地不可刪除");

                                    } else {
                                        // 可刪除
                                        SuperFreedomSurvive.Land.DeleteLand.ByID(ID); // 刪除領地
                                        //sta.executeUpdate("DELETE FROM `land-protection_data` WHERE `ID` = '" + ID + "'");

                                        player.sendMessage("[領地系統]  領地刪除成功");

                                        // 返回上上一頁
                                        menu(player);

                                    }

                                    res.close(); // 關閉查詢
                                    sta.close(); // 關閉連線
                                    break;


                                case 8:
                                    // 自己也受到影響
                                    set_land_permissions("UserUsePermissions", ID);
                                    set_land(player, ID);
                                    break;

                                case 18:
                                    // 返回上一頁
                                    view(player);
                                    break;

                                default:
                                    break;
                            }


                        } else {
                            // ! 無領地
                            player.sendMessage("[領地系統]  §c你無權限編輯");

                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    final public static void set_land_block(Player player) {
        // 顯示容器

        ItemStack item;
        ItemMeta meta;

        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫
            ResultSet res;

            String ID = SuperFreedomSurvive.Land.Cache.get(player); // 取得緩存
            // 檢查是否有權限
            if (SuperFreedomSurvive.Land.Permissions.Ownership(player, ID)) {
                // 是

                res = sta.executeQuery("SELECT * FROM `land-protection_data` WHERE `ID` = '" + ID + "' LIMIT 1");
                res.next();


                // 給予介面

                // 顯示容器
                Inventory inv = Bukkit.createInventory(null, 54, "§z領地系統 方塊 權限系列");


                inv.setItem(0, getItemStack(res.getBoolean("Permissions_BlockBreak"), "破壞方塊", Material.DIAMOND_PICKAXE));
                inv.setItem(1, getItemStack(res.getBoolean("Permissions_BlockDamage"), "觸碰方塊", Material.GOLDEN_PICKAXE));
                inv.setItem(2, getItemStack(res.getBoolean("Permissions_BlockExp"), "方塊產生經驗", Material.EXPERIENCE_BOTTLE));
                inv.setItem(3, getItemStack(res.getBoolean("Permissions_BlockExplode"), "爆炸破壞", Material.TNT));
                inv.setItem(4, getItemStack(res.getBoolean("Permissions_BlockFertilize"), "骨粉使用", Material.BONE_MEAL));
                inv.setItem(5, getItemStack(res.getBoolean("Permissions_BlockIgnite"), "點燃方塊", Material.FLINT_AND_STEEL));
                inv.setItem(6, getItemStack(res.getBoolean("Permissions_BlockMultiPlace"), "放置多架構方塊(床/門)", Material.OAK_DOOR));
                inv.setItem(7, getItemStack(res.getBoolean("Permissions_BlockPlace"), "放置方塊", Material.COBBLESTONE));
                inv.setItem(8, getItemStack(res.getBoolean("Permissions_SignChange"), "告示牌輸入", Material.SIGN));
                inv.setItem(9, getItemStack(res.getBoolean("Permissions_SpongeAbsorb"), "海綿吸水", Material.SPONGE));
                inv.setItem(10, getItemStack(res.getBoolean("Permissions_TrampleFarmland"), "踐踏農田", Material.FARMLAND));
                inv.setItem(11, getItemStack(res.getBoolean("Permissions_BlockChange"), "方塊自然更改/破壞", Material.SAND));

                res.close(); // 關閉查詢

                // --------------------------------------     52     --------------------------------------
                inv.setItem(52, SuperFreedomSurvive.Menu.Open.PreviousPage());


                // --------------------------------------     53     --------------------------------------
                inv.setItem(53, SuperFreedomSurvive.Menu.Open.TurnOff());


                // ---------------------------------------------------------------------------------------
                // 寫入到容器頁面
                player.openInventory(inv);


            } else {
                player.sendMessage("[領地系統]  §c你無權限編輯");

            }

            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    final public static void set_land_player(Player player) {
        // 顯示容器

        ItemStack item;
        ItemMeta meta;

        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫
            ResultSet res;

            String ID = SuperFreedomSurvive.Land.Cache.get(player); // 取得緩存
            // 檢查是否有權限
            if (SuperFreedomSurvive.Land.Permissions.Ownership(player, ID)) {
                // 是

                res = sta.executeQuery("SELECT * FROM `land-protection_data` WHERE `ID` = '" + ID + "' LIMIT 1");
                res.next();

                // 給予介面

                // 顯示容器
                Inventory inv = Bukkit.createInventory(null, 54, "§z領地系統 玩家 權限系列");


                inv.setItem(0, getItemStack(res.getBoolean("Permissions_AsyncPlayerChat"), "發送訊息", Material.PAPER));
                //inv.setItem(1, getItemStack(res.getBoolean("Permissions_PlayerAnimation"), "使用動作", Material.LEATHER));
                inv.setItem(1, getItemStack(res.getBoolean("Permissions_PlayerArmorStandManipulate"), "盔甲座 裝備拿取/放置", Material.ARMOR_STAND));
                inv.setItem(2, getItemStack(res.getBoolean("Permissions_PlayerBedEnter"), "使用床", Material.RED_BED));
                inv.setItem(3, getItemStack(res.getBoolean("Permissions_PlayerBucketEmpty"), "使用桶子(使用)", Material.BUCKET));
                inv.setItem(4, getItemStack(res.getBoolean("Permissions_PlayerBucket"), "使用桶子(撈起)", Material.TROPICAL_FISH_BUCKET));
                inv.setItem(5, getItemStack(res.getBoolean("Permissions_PlayerCommandPreprocess"), "執行命令", Material.COMMAND_BLOCK));
                inv.setItem(6, getItemStack(res.getBoolean("Permissions_PlayerDropItem"), "丟棄物品", Material.ROTTEN_FLESH));
                inv.setItem(7, getItemStack(res.getBoolean("Permissions_PlayerEditBook"), "編輯書本", Material.WRITABLE_BOOK));
                inv.setItem(8, getItemStack(res.getBoolean("Permissions_PlayerEggThrow"), "孵化雞蛋", Material.EGG));
                inv.setItem(9, getItemStack(res.getBoolean("Permissions_PlayerExpChange"), "獲得經驗值", Material.EXPERIENCE_BOTTLE));
                inv.setItem(10, getItemStack(res.getBoolean("Permissions_PlayerFish"), "釣魚", Material.FISHING_ROD));
                inv.setItem(11, getItemStack(res.getBoolean("Permissions_PlayerInteractEntity"), "點擊實體", Material.VILLAGER_SPAWN_EGG));
                inv.setItem(12, getItemStack(res.getBoolean("Permissions_PlayerItemConsume"), "食物/藥水消耗", Material.COOKED_BEEF));
                inv.setItem(13, getItemStack(res.getBoolean("Permissions_PlayerItemMend"), "經驗修補耐久", Material.ENCHANTED_BOOK));
                //inv.setItem(15, getItemStack(res.getBoolean("Permissions_PlayerMove"), "移動位置", Material.OAK_BOAT));
                inv.setItem(14, getItemStack(res.getBoolean("Permissions_PlayerPickupArrow"), "拾取弓箭", Material.ARROW));
                inv.setItem(15, getItemStack(res.getBoolean("Permissions_PlayerPortal"), "使用傳送門", Material.END_PORTAL_FRAME));
                inv.setItem(16, getItemStack(res.getBoolean("Permissions_PlayerShearEntity"), "修剪羊毛", Material.SHEARS));
                inv.setItem(17, getItemStack(res.getBoolean("Permissions_PlayerTeleport"), "傳送", Material.ENDER_PEARL));
                inv.setItem(18, getItemStack(res.getBoolean("Permissions_PlayerJump"), "跳躍", Material.SLIME_BLOCK));
                inv.setItem(19, getItemStack(res.getBoolean("Permissions_PlayerToggleFlight"), "飛行", Material.ELYTRA));
                inv.setItem(20, getItemStack(res.getBoolean("Permissions_PlayerToggleSneak"), "潛行", Material.FIREWORK_STAR));
                inv.setItem(21, getItemStack(res.getBoolean("Permissions_PlayerToggleSprint"), "奔跑", Material.DIAMOND_HORSE_ARMOR));
                inv.setItem(22, getItemStack(res.getBoolean("Permissions_PlayerVelocity"), "移動變速", Material.KELP));
                inv.setItem(23, getItemStack(res.getBoolean("Permissions_PlayerWarp"), "定點傳送過來", Material.FEATHER));
                inv.setItem(24, getItemStack(res.getBoolean("Permissions_PlayerNewWarp"), "新增定點", Material.LIGHT_GRAY_BANNER));
                inv.setItem(25, getItemStack(res.getBoolean("Permissions_PlayerRespawn"), "死亡後重生", Material.TOTEM_OF_UNDYING));
                inv.setItem(26, getItemStack(res.getBoolean("Permissions_PlayerInventory"), "拿取/放入物品到容器中", Material.HOPPER));
                inv.setItem(27, getItemStack(res.getBoolean("Permissions_PlayerEatCake"), "吃蛋糕", Material.CAKE));
                inv.setItem(28, getItemStack(res.getBoolean("Permissions_PlayerReadyArrow"), "射出弓箭", Material.BOW));
                inv.setItem(29, getItemStack(res.getBoolean("Permissions_PlayerLaunchProjectile"), "丟出投射物", Material.SNOWBALL));

                res.close(); // 關閉查詢

                // --------------------------------------     52     --------------------------------------
                inv.setItem(52, SuperFreedomSurvive.Menu.Open.PreviousPage());


                // --------------------------------------     53     --------------------------------------
                inv.setItem(53, SuperFreedomSurvive.Menu.Open.TurnOff());


                // ---------------------------------------------------------------------------------------
                // 寫入到容器頁面
                player.openInventory(inv);


            } else {
                player.sendMessage("[領地系統]  §c你無權限編輯");

            }

            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    final public static void set_land_interact(Player player) {
        // 顯示容器

        ItemStack item;
        ItemMeta meta;

        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫
            ResultSet res;

            String ID = SuperFreedomSurvive.Land.Cache.get(player); // 取得緩存
            // 檢查是否有權限
            if (SuperFreedomSurvive.Land.Permissions.Ownership(player, ID)) {
                // 是

                res = sta.executeQuery("SELECT * FROM `land-protection_data` WHERE `ID` = '" + ID + "' LIMIT 1");
                res.next();

                // 給予介面

                // 顯示容器
                Inventory inv = Bukkit.createInventory(null, 54, "§z領地系統 容器 權限系列");


                inv.setItem(0, getItemStack(res.getBoolean("Permissions_Interact_Chest"), "物件 箱子", Material.CHEST));
                inv.setItem(1, getItemStack(res.getBoolean("Permissions_Interact_TrappedChest"), "物件 陷阱儲物箱", Material.TRAPPED_CHEST));
                inv.setItem(2, getItemStack(res.getBoolean("Permissions_Interact_EnderChest"), "物件 末影箱子", Material.ENDER_CHEST));
                inv.setItem(3, getItemStack(res.getBoolean("Permissions_Interact_CraftingTable"), "物件 工作臺", Material.CRAFTING_TABLE));
                inv.setItem(4, getItemStack(res.getBoolean("Permissions_Interact_Furnace"), "物件 熔爐", Material.FURNACE));
                inv.setItem(5, getItemStack(res.getBoolean("Permissions_Interact_Hopper"), "物件 漏斗", Material.HOPPER));
                inv.setItem(6, getItemStack(res.getBoolean("Permissions_Interact_Cauldron"), "物件 鍋釜", Material.CAULDRON));
                inv.setItem(7, getItemStack(res.getBoolean("Permissions_Interact_BrewingStand"), "物件 釀造台", Material.BREWING_STAND));
                inv.setItem(8, getItemStack(res.getBoolean("Permissions_Interact_EnchantTable"), "物件 附魔台", Material.ENCHANTING_TABLE));
                inv.setItem(9, getItemStack(res.getBoolean("Permissions_Interact_Beacon"), "物件 烽火台", Material.BEACON));
                inv.setItem(10, getItemStack(res.getBoolean("Permissions_Interact_ShulkerBox"), "物件 界伏盒", Material.SHULKER_BOX));
                inv.setItem(11, getItemStack(res.getBoolean("Permissions_Interact_Jukebox"), "物件 唱片機", Material.JUKEBOX));
                inv.setItem(12, getItemStack(res.getBoolean("Permissions_Interact_Note_Block"), "物件 音符盒", Material.NOTE_BLOCK));
                inv.setItem(13, getItemStack(res.getBoolean("Permissions_Interact_Anvil"), "物件 鐵砧", Material.ANVIL));
                inv.setItem(14, getItemStack(res.getBoolean("Permissions_Interact_Painting"), "物件 繪畫", Material.PAINTING));
                inv.setItem(15, getItemStack(res.getBoolean("Permissions_Interact_Sign"), "物件 告示牌", Material.SIGN));
                inv.setItem(16, getItemStack(res.getBoolean("Permissions_Interact_FlowerPot"), "物件 花盆", Material.FLOWER_POT));
                inv.setItem(17, getItemStack(res.getBoolean("Permissions_Interact_Dispenser"), "物件 發射器", Material.DISPENSER));
                inv.setItem(18, getItemStack(res.getBoolean("Permissions_Interact_Dropper"), "物件 投擲器", Material.DROPPER));
                inv.setItem(19, getItemStack(res.getBoolean("Permissions_Interact_Lever"), "物件 控制桿", Material.LEVER));
                inv.setItem(20, getItemStack(res.getBoolean("Permissions_Interact_PressurePlate"), "物件 壓力版", Material.OAK_PRESSURE_PLATE));
                inv.setItem(21, getItemStack(res.getBoolean("Permissions_Interact_Button"), "物件 按鈕", Material.STONE_BUTTON));
                inv.setItem(22, getItemStack(res.getBoolean("Permissions_Interact_Trapdoor"), "物件 地板門", Material.OAK_FENCE_GATE));
                inv.setItem(23, getItemStack(res.getBoolean("Permissions_Interact_FenceGate"), "物件 柵欄門", Material.JUNGLE_FENCE));
                inv.setItem(24, getItemStack(res.getBoolean("Permissions_Interact_DaylightDetector"), "物件 日光感測器", Material.DAYLIGHT_DETECTOR));
                inv.setItem(25, getItemStack(res.getBoolean("Permissions_Interact_Door"), "物件 門", Material.SPRUCE_DOOR));
                inv.setItem(26, getItemStack(res.getBoolean("Permissions_Interact_Repeater"), "物件 紅石中繼器", Material.REPEATER));
                inv.setItem(27, getItemStack(res.getBoolean("Permissions_Interact_Comparator"), "物件 紅石比較器", Material.COMPARATOR));
                inv.setItem(28, getItemStack(res.getBoolean("Permissions_Interact_Spawner"), "物件 刷怪磚", Material.SPAWNER));

                res.close(); // 關閉查詢

                // --------------------------------------     52     --------------------------------------
                inv.setItem(52, SuperFreedomSurvive.Menu.Open.PreviousPage());


                // --------------------------------------     53     --------------------------------------
                inv.setItem(53, SuperFreedomSurvive.Menu.Open.TurnOff());


                // ---------------------------------------------------------------------------------------
                // 寫入到容器頁面
                player.openInventory(inv);


            } else {
                player.sendMessage("[領地系統]  §c你無權限編輯");

            }

            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    final public static void set_land_entity(Player player) {

        ItemStack item;
        ItemMeta meta;

        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫
            ResultSet res;

            String ID = SuperFreedomSurvive.Land.Cache.get(player); // 取得緩存
            // 檢查是否有權限
            if (SuperFreedomSurvive.Land.Permissions.Ownership(player, ID)) {
                // 是

                res = sta.executeQuery("SELECT * FROM `land-protection_data` WHERE `ID` = '" + ID + "' LIMIT 1");
                res.next();

                // 給予介面

                // 顯示容器
                Inventory inv = Bukkit.createInventory(null, 54, "§z領地系統 實體 權限系列");

                inv.setItem(0, getItemStack(res.getBoolean("Permissions_EntityChangeBlock"), "實體 破壞/放置方塊", Material.WITHER_SKELETON_SKULL));
                inv.setItem(1, getItemStack(res.getBoolean("Permissions_EntityDamageByEntity"), "玩家 攻擊實體", Material.DIAMOND_SWORD));
                inv.setItem(2, getItemStack(res.getBoolean("Permissions_Entity_Player"), "玩家 攻擊玩家", Material.PLAYER_HEAD));
                inv.setItem(3, getItemStack(res.getBoolean("Permissions_Entity_ArmorStand"), "實體 盔甲座 破壞/放置/編輯", Material.ARMOR_STAND));
                inv.setItem(4, getItemStack(res.getBoolean("Permissions_Entity_ItemFrame_Use"), "實體 物品展示框 使用", Material.ITEM_FRAME));
                inv.setItem(5, getItemStack(res.getBoolean("Permissions_Entity_ItemFrame_Change"), "實體 物品展示框 破壞/放置", Material.ITEM_FRAME));
                inv.setItem(6, getItemStack(res.getBoolean("Permissions_Entity_Minecart_Use"), "實體 礦車 進/出", Material.MINECART));
                inv.setItem(7, getItemStack(res.getBoolean("Permissions_Entity_Minecart_Change"), "實體 礦車 破壞/放置", Material.MINECART));
                inv.setItem(8, getItemStack(res.getBoolean("Permissions_Entity_Minecart_Interact"), "實體 礦車 容器交互", Material.CHEST_MINECART));
                inv.setItem(9, getItemStack(res.getBoolean("Permissions_Entity_Boat_Use"), "實體 船 進/出", Material.OAK_BOAT));
                inv.setItem(10, getItemStack(res.getBoolean("Permissions_Entity_Boat_Change"), "實體 船 破壞/放置", Material.OAK_BOAT));
                inv.setItem(11, getItemStack(res.getBoolean("Permissions_Entity_Egg_Storag"), "實體 收納蛋 收納", Material.POLAR_BEAR_SPAWN_EGG));
                inv.setItem(12, getItemStack(res.getBoolean("Permissions_Entity_Egg_Place"), "實體 收納蛋 放出", Material.POLAR_BEAR_SPAWN_EGG));

                res.close(); // 關閉查詢

                // --------------------------------------     52     --------------------------------------
                inv.setItem(52, SuperFreedomSurvive.Menu.Open.PreviousPage());


                // --------------------------------------     53     --------------------------------------
                inv.setItem(53, SuperFreedomSurvive.Menu.Open.TurnOff());


                // ---------------------------------------------------------------------------------------
                // 寫入到容器頁面
                player.openInventory(inv);


            } else {
                player.sendMessage("[領地系統]  §c你無權限編輯");

            }

            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    // 快速給予 ItemStack 接口
    final public static ItemStack getItemStack(Boolean status, String name, Material material) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§r§e" + name);
        if (status) {
            // true
            meta.setLore(Arrays.asList(
                    ("§r§f§l   目前狀態 §a允許"),
                    ("§r§f (點擊) 切換狀態")
                    //("§r§f  方塊類 - 領地內方塊控制"),
                    //("§r§f  玩家類 - 其他玩家控制")
            ));

        } else {
            // false
            meta.setLore(Arrays.asList(
                    ("§r§f§l   目前狀態 §c禁止"),
                    ("§r§f (點擊) 切換狀態")
                    //("§r§f  方塊類 - 領地內方塊控制"),
                    //("§r§f  玩家類 - 其他玩家控制")
            ));

        }


        // 寫入資料
        item.setItemMeta(meta);
        // 返回
        return item;
        // 設置完成
        //inv.setItem( 35 , item );


    }


    // 阻止玩家拿走物品
    @EventHandler
    final public void onInventory__(InventoryClickEvent event) {
        // 判斷是否為玩家

        if (event.getWhoClicked() instanceof Player) {

            // 是玩家
            Player player = (Player) event.getWhoClicked();
            int X = new BigDecimal(player.getLocation().getX()).setScale(0, BigDecimal.ROUND_FLOOR).intValue();
            int Y = new BigDecimal(player.getLocation().getY()).setScale(0, BigDecimal.ROUND_FLOOR).intValue();
            int Z = new BigDecimal(player.getLocation().getZ()).setScale(0, BigDecimal.ROUND_FLOOR).intValue();
            String world = player.getWorld().getName();

            // 檢查容器名稱
            if (event.getView().getTitle().equalsIgnoreCase("§z領地系統 方塊 權限系列")) {
                // 是沒錯

                event.setCancelled(true);

                String ID = SuperFreedomSurvive.Land.Cache.get(player); // 取得緩存
                // 檢查是否有權限
                if (SuperFreedomSurvive.Land.Permissions.Ownership(player, ID)) {
                    // 是

                    switch (event.getRawSlot()) {
                        case 0:
                            set_land_permissions("Permissions_BlockBreak", ID);
                            break;
                        case 1:
                            set_land_permissions("Permissions_BlockDamage", ID);
                            break;
                        case 2:
                            set_land_permissions("Permissions_BlockExp", ID);
                            break;
                        case 3:
                            set_land_permissions("Permissions_BlockExplode", ID);
                            break;
                        case 4:
                            set_land_permissions("Permissions_BlockFertilize", ID);
                            break;
                        case 5:
                            set_land_permissions("Permissions_BlockIgnite", ID);
                            break;
                        case 6:
                            set_land_permissions("Permissions_BlockMultiPlace", ID);
                            break;
                        case 7:
                            set_land_permissions("Permissions_BlockPlace", ID);
                            break;
                        case 8:
                            set_land_permissions("Permissions_SignChange", ID);
                            break;
                        case 9:
                            set_land_permissions("Permissions_SpongeAbsorb", ID);
                            break;
                        case 10:
                            set_land_permissions("Permissions_TrampleFarmland", ID);
                            break;
                        case 11:
                            set_land_permissions("Permissions_BlockChange", ID);
                            break;
                        case 52:
                            // 返回
                            set_land(player, ID);
                            break;
                        case 53:
                            // 關閉
                            event.getWhoClicked().closeInventory();
                            break;
                        default:
                            break;
                    }
                    if (event.getRawSlot() <= 11) {
                        // 重開本介面
                        set_land_block(player);
                    }

                } else {
                    player.sendMessage("[領地系統]  §c你無權限編輯");

                }


            } else if (event.getView().getTitle().equalsIgnoreCase("§z領地系統 玩家 權限系列")) {
                // 是沒錯

                event.setCancelled(true);

                String ID = SuperFreedomSurvive.Land.Cache.get(player); // 取得緩存
                // 檢查是否有權限
                if (SuperFreedomSurvive.Land.Permissions.Ownership(player, ID)) {
                    // 是

                    switch (event.getRawSlot()) {
                        case 0:
                            set_land_permissions("Permissions_AsyncPlayerChat", ID);
                            break;
                        case 1:
                            set_land_permissions("Permissions_PlayerArmorStandManipulate", ID);
                            break;
                        case 2:
                            set_land_permissions("Permissions_PlayerBedEnter", ID);
                            break;
                        case 3:
                            set_land_permissions("Permissions_PlayerBucketEmpty", ID);
                            break;
                        case 4:
                            set_land_permissions("Permissions_PlayerBucket", ID);
                            break;
                        case 5:
                            set_land_permissions("Permissions_PlayerCommandPreprocess", ID);
                            break;
                        case 6:
                            set_land_permissions("Permissions_PlayerDropItem", ID);
                            break;
                        case 7:
                            set_land_permissions("Permissions_PlayerEditBook", ID);
                            break;
                        case 8:
                            set_land_permissions("Permissions_PlayerEggThrow", ID);
                            break;
                        case 9:
                            set_land_permissions("Permissions_PlayerExpChange", ID);
                            break;
                        case 10:
                            set_land_permissions("Permissions_PlayerFish", ID);
                            break;
                        case 11:
                            set_land_permissions("Permissions_PlayerInteractEntity", ID);
                            break;
                        case 12:
                            set_land_permissions("Permissions_PlayerItemConsume", ID);
                            break;
                        case 13:
                            set_land_permissions("Permissions_PlayerItemMend", ID);
                            break;
                        case 14:
                            set_land_permissions("Permissions_PlayerPickupArrow", ID);
                            break;
                        case 15:
                            set_land_permissions("Permissions_PlayerPortal", ID);
                            break;
                        case 16:
                            set_land_permissions("Permissions_PlayerShearEntity", ID);
                            break;
                        case 17:
                            set_land_permissions("Permissions_PlayerTeleport", ID);
                            break;
                        case 18:
                            set_land_permissions("Permissions_PlayerJump", ID);
                            break;
                        case 19:
                            set_land_permissions("Permissions_PlayerToggleFlight", ID);
                            break;
                        case 20:
                            set_land_permissions("Permissions_PlayerToggleSneak", ID);
                            break;
                        case 21:
                            set_land_permissions("Permissions_PlayerToggleSprint", ID);
                            break;
                        case 22:
                            set_land_permissions("Permissions_PlayerVelocity", ID);
                            break;
                        case 23:
                            set_land_permissions("Permissions_PlayerWarp", ID);
                            break;
                        case 24:
                            set_land_permissions("Permissions_PlayerNewWarp", ID);
                            break;
                        case 25:
                            set_land_permissions("Permissions_PlayerRespawn", ID);
                            break;
                        case 26:
                            set_land_permissions("Permissions_PlayerInventory", ID);
                            break;
                        case 27:
                            set_land_permissions("Permissions_PlayerEatCake", ID);
                            break;
                        case 28:
                            set_land_permissions("Permissions_PlayerReadyArrow", ID);
                            break;
                        case 29:
                            set_land_permissions("Permissions_PlayerLaunchProjectile", ID);
                            break;
                        case 52:
                            // 返回
                            set_land(player, ID);
                            break;
                        case 53:
                            // 關閉
                            event.getWhoClicked().closeInventory();
                            break;
                        default:
                            break;
                    }
                    if (event.getRawSlot() <= 29) {
                        // 重開本介面
                        set_land_player(player);

                    }

                } else {
                    player.sendMessage("[領地系統]  §c你無權限編輯");

                }


            } else if (event.getView().getTitle().equalsIgnoreCase("§z領地系統 容器 權限系列")) {
                // 是沒錯

                event.setCancelled(true);

                String ID = SuperFreedomSurvive.Land.Cache.get(player); // 取得緩存
                // 檢查是否有權限
                if (SuperFreedomSurvive.Land.Permissions.Ownership(player, ID)) {
                    // 是

                    switch (event.getRawSlot()) {
                        case 0:
                            set_land_permissions("Permissions_Interact_Chest", ID);
                            break;
                        case 1:
                            set_land_permissions("Permissions_Interact_TrappedChest", ID);
                            break;
                        case 2:
                            set_land_permissions("Permissions_Interact_EnderChest", ID);
                            break;
                        case 3:
                            set_land_permissions("Permissions_Interact_CraftingTable", ID);
                            break;
                        case 4:
                            set_land_permissions("Permissions_Interact_Furnace", ID);
                            break;
                        case 5:
                            set_land_permissions("Permissions_Interact_Hopper", ID);
                            break;
                        case 6:
                            set_land_permissions("Permissions_Interact_Cauldron", ID);
                            break;
                        case 7:
                            set_land_permissions("Permissions_Interact_BrewingStand", ID);
                            break;
                        case 8:
                            set_land_permissions("Permissions_Interact_EnchantTable", ID);
                            break;
                        case 9:
                            set_land_permissions("Permissions_Interact_Beacon", ID);
                            break;
                        case 10:
                            set_land_permissions("Permissions_Interact_ShulkerBox", ID);
                            break;
                        case 11:
                            set_land_permissions("Permissions_Interact_Jukebox", ID);
                            break;
                        case 12:
                            set_land_permissions("Permissions_Interact_Note_Block", ID);
                            break;
                        case 13:
                            set_land_permissions("Permissions_Interact_Anvil", ID);
                            break;
                        case 14:
                            set_land_permissions("Permissions_Interact_Painting", ID);
                            break;
                        case 15:
                            set_land_permissions("Permissions_Interact_Sign", ID);
                            break;
                        case 16:
                            set_land_permissions("Permissions_Interact_FlowerPot", ID);
                            break;
                        case 17:
                            set_land_permissions("Permissions_Interact_Dispenser", ID);
                            break;
                        case 18:
                            set_land_permissions("Permissions_Interact_Dropper", ID);
                            break;
                        case 19:
                            set_land_permissions("Permissions_Interact_Lever", ID);
                            break;
                        case 20:
                            set_land_permissions("Permissions_Interact_PressurePlate", ID);
                            break;
                        case 21:
                            set_land_permissions("Permissions_Interact_Button", ID);
                            break;
                        case 22:
                            set_land_permissions("Permissions_Interact_Trapdoor", ID);
                            break;
                        case 23:
                            set_land_permissions("Permissions_Interact_FenceGate", ID);
                            break;
                        case 24:
                            set_land_permissions("Permissions_Interact_DaylightDetector", ID);
                            break;
                        case 25:
                            set_land_permissions("Permissions_Interact_Door", ID);
                            break;
                        case 26:
                            set_land_permissions("Permissions_Interact_Repeater", ID);
                            break;
                        case 27:
                            set_land_permissions("Permissions_Interact_Comparator", ID);
                            break;
                        case 28:
                            set_land_permissions("Permissions_Interact_Spawner", ID);
                            break;
                        case 52:
                            // 返回
                            set_land(player, ID);
                            break;
                        case 53:
                            // 關閉
                            event.getWhoClicked().closeInventory();
                            break;
                        default:
                            break;
                    }
                    if (event.getRawSlot() <= 28) {
                        // 重開本介面
                        set_land_interact(player);

                    }


                } else {
                    player.sendMessage("[領地系統]  §c你無權限編輯");

                }


            } else if (event.getView().getTitle().equalsIgnoreCase("§z領地系統 實體 權限系列")) {
                // 是沒錯

                event.setCancelled(true);

                String ID = SuperFreedomSurvive.Land.Cache.get(player); // 取得緩存
                // 檢查是否有權限
                if (SuperFreedomSurvive.Land.Permissions.Ownership(player, ID)) {
                    // 是

                    switch (event.getRawSlot()) {
                        case 0:
                            set_land_permissions("Permissions_EntityChangeBlock", ID);
                            break;
                        case 1:
                            set_land_permissions("Permissions_EntityDamageByEntity", ID);
                            break;
                        case 2:
                            set_land_permissions("Permissions_Entity_Player", ID);
                            break;
                        case 3:
                            set_land_permissions("Permissions_Entity_ArmorStand", ID);
                            break;
                        case 4:
                            set_land_permissions("Permissions_Entity_ItemFrame_Use", ID);
                            break;
                        case 5:
                            set_land_permissions("Permissions_Entity_ItemFrame_Change", ID);
                            break;
                        case 6:
                            set_land_permissions("Permissions_Entity_Minecart_Use", ID);
                            break;
                        case 7:
                            set_land_permissions("Permissions_Entity_Minecart_Change", ID);
                            break;
                        case 8:
                            set_land_permissions("Permissions_Entity_Minecart_Interact", ID);
                            break;
                        case 9:
                            set_land_permissions("Permissions_Entity_Boat_Use", ID);
                            break;
                        case 10:
                            set_land_permissions("Permissions_Entity_Boat_Change", ID);
                            break;
                        case 11:
                            set_land_permissions("Permissions_Entity_Egg_Storag", ID);
                            break;
                        case 12:
                            set_land_permissions("Permissions_Entity_Egg_Place", ID);
                            break;
                        case 52:
                            // 返回
                            set_land(player, ID);
                            break;
                        case 53:
                            // 關閉
                            event.getWhoClicked().closeInventory();
                            break;
                        default:
                            break;
                    }
                    if (event.getRawSlot() <= 12) {
                        // 重開本介面
                        set_land_entity(player);

                    }

                } else {
                    player.sendMessage("[領地系統]  §c你無權限編輯");

                }


            }
        }


    }

    // 快速給設置權限 接口
    final public static void set_land_permissions(String permissions, String ID) {
        try {
            // 讀取當前狀態
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫

            // 檢查是否在保護區域內
            ResultSet res = sta.executeQuery("SELECT (" + permissions + ") FROM `land-protection_data` WHERE `ID` = '" + ID + "' LIMIT 1");
            // 跳到最後一行
            res.last();
            // 最後一行 行數 是否 > 0
            if (res.getRow() > 0) {
                // 有資料
                // 跳回 初始行 必須使用 next() 才能取得資料
                res.beforeFirst();
                res.next();
                if (res.getBoolean(permissions)) {
                    // 當前值為 true
                    // 改為 false

                    res.close(); // 關閉查詢

                    sta.executeUpdate("UPDATE `land-protection_data` SET `" + permissions + "` = '0' WHERE `ID` = '" + ID + "'");
                } else {
                    // 當前值為 false
                    // 改為 true

                    res.close(); // 關閉查詢

                    sta.executeUpdate("UPDATE `land-protection_data` SET `" + permissions + "` = '1' WHERE `ID` = '" + ID + "'");
                }

            }

            res.close(); // 關閉查詢
            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }


        // 設置完成
        //inv.setItem( 35 , item );


    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    // 領地轉讓
    final public static void land_transfer(Player player) {

        String ID = SuperFreedomSurvive.Land.Cache.get(player); // 取得緩存
        // 檢查是否有權限
        if (SuperFreedomSurvive.Land.Permissions.Ownership(player, ID)) {
            // 是

            // 顯示容器
            Inventory inv = Bukkit.createInventory(null, 54, "§z領地轉讓");


            ItemStack item;
            ItemMeta meta;
            SkullMeta skull_meta;


            // --------------------------------------     0     --------------------------------------
            item = new ItemStack(Material.BARRIER);
            meta = item.getItemMeta();
            meta.setDisplayName("§e沒有使用者");
            meta.setLore(Arrays.asList(
                    ("§r§f (點擊) 將此領地設定為待分配"),
                    ("§r§f - 擁有者擁有權限")
            ));
            // 寫入資料
            item.setItemMeta(meta);
            // 設置完成
            inv.setItem(0, item);


            // 取得所有玩家清單
            Collection collection = Bukkit.getServer().getOnlinePlayers();
            Iterator iterator = collection.iterator();
            // 總數
            int total = 1;
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
                            ("§r§f (點擊) 將使用權限轉讓給此玩家")
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


            // 加入剩餘的
            // --------------------------------------     45     --------------------------------------
            // 沒找到你要的?
            item = new ItemStack(Material.MAP, 1);
            meta = item.getItemMeta();
            meta.setDisplayName("§b小心點擊!!!");
            meta.setLore(Arrays.asList(
                    ("§r§f按錯一概不負責~~"),
                    ("§r§f擁有者不變 但無法控制權限"),
                    ("§r§f擁有者一樣能夠正常使用此領地")
            ));
            // 寫入資料
            item.setItemMeta(meta);
            // 設置完成
            inv.setItem(45, item);

            // --------------------------------------     52     --------------------------------------
            inv.setItem(52, SuperFreedomSurvive.Menu.Open.PreviousPage());


            // --------------------------------------     53     --------------------------------------
            inv.setItem(53, SuperFreedomSurvive.Menu.Open.TurnOff());


            // 寫入到容器頁面
            player.openInventory(inv);


        } else {
            player.sendMessage("[領地系統]  §c你無權限編輯");

        }
    }


    // 阻止玩家拿走物品
    @EventHandler
    final public void onInventoryClickEvent_(InventoryClickEvent event) {
        // 判斷是否為玩家

        if (event.getWhoClicked() instanceof Player) {
            // 是玩家
            Player player = (Player) event.getWhoClicked();

            // 檢查容器名稱
            if (event.getView().getTitle().equalsIgnoreCase("§z領地轉讓")) {
                // 是沒錯

                event.setCancelled(true);

                // 檢查點擊的是第幾個位置


                if (event.getRawSlot() == 0) {
                    // 轉換為待分配
                    Setting.conversionLandUserIsNull(player);


                } else if (event.getRawSlot() < 45 ) {
                    // 轉換領地給玩家
                    if (event.getCurrentItem() == null) { return; }
                    if (event.getCurrentItem().getItemMeta() == null) { return; }
                    // 頭顱
                    ItemMeta meta = event.getCurrentItem().getItemMeta();
                    CustomItemTagContainer tag = meta.getCustomTagContainer();

                    if (tag.getCustomTag(player_key, ItemTagType.STRING) == null) { return; }

                    Setting.conversionLandUser(player,tag.getCustomTag(player_key, ItemTagType.STRING));


                } else if (event.getRawSlot() == 52) {
                    // 上一頁
                    set_land(player, SuperFreedomSurvive.Land.Cache.get(player));


                } else if (event.getRawSlot() == 45) {


                } else if (event.getRawSlot() == 53) {
                    // 關閉
                    event.getWhoClicked().closeInventory();

                }

            }
        }


    }


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    // 領地新增共用人
    final public static void land_share_new(Player player) {

        String ID = SuperFreedomSurvive.Land.Cache.get(player); // 取得緩存
        // 檢查是否有權限
        if (SuperFreedomSurvive.Land.Permissions.Ownership(player, ID)) {
            // 是

            // 顯示容器
            Inventory inv = Bukkit.createInventory(null, 54, "§z領地新增共用人");


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
                    meta.setDisplayName("§r§f" + player_.getDisplayName());
                    //ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1)
                    meta.setLore(Arrays.asList(
                            ("§r§f   ID: §a" + player_.getName()),
                            ("§r§f (點擊) 與玩家共用此領地")
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


            // 加入剩餘的
            // --------------------------------------     45     --------------------------------------
            // 沒找到你要的?
            item = new ItemStack(Material.MAP, 1);
            meta = item.getItemMeta();
            meta.setDisplayName("§b找不到玩家?");
            meta.setLore(Arrays.asList(
                    ("§r§f那我也沒辦法幫助你~"),
                    ("§r§f最大可設置 45 個共用人")
            ));
            // 寫入資料
            item.setItemMeta(meta);
            // 設置完成
            inv.setItem(45, item);

            // --------------------------------------     52     --------------------------------------
            inv.setItem(52, SuperFreedomSurvive.Menu.Open.PreviousPage());


            // --------------------------------------     53     --------------------------------------
            inv.setItem(53, SuperFreedomSurvive.Menu.Open.TurnOff());


            // 寫入到容器頁面
            player.openInventory(inv);


        } else {
            player.sendMessage("[領地系統]  §c你無權限編輯");

        }
    }


    // 阻止玩家拿走物品
    @EventHandler
    final public void onInventoryClickEvent____(InventoryClickEvent event) {
        // 判斷是否為玩家

        if (event.getWhoClicked() instanceof Player) {
            // 是玩家
            Player player = (Player) event.getWhoClicked();

            // 檢查容器名稱
            if (event.getView().getTitle().equalsIgnoreCase("§z領地新增共用人")) {
                // 是沒錯

                event.setCancelled(true);

                // 檢查點擊的是第幾個位置


                if (event.getRawSlot() < 45) {
                    // 領地新增共用人

                    if (event.getCurrentItem() == null) { return; }
                    if (event.getCurrentItem().getItemMeta() == null) { return; }

                    ItemMeta meta = event.getCurrentItem().getItemMeta();
                    CustomItemTagContainer tag = meta.getCustomTagContainer();

                    if (tag.getCustomTag(player_key,ItemTagType.STRING) == null) { return; }

                    Setting.addLandShare(player,tag.getCustomTag(player_key,ItemTagType.STRING));


                } else if (event.getRawSlot() == 52) {
                    // 上一頁
                    set_land(player, SuperFreedomSurvive.Land.Cache.get(player));


                } else if (event.getRawSlot() == 45) {


                } else if (event.getRawSlot() == 53) {
                    // 關閉
                    event.getWhoClicked().closeInventory();

                }

            }
        }
    }


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    // 領地刪除共用人
    final public static void land_share_delete(Player player) {

        //new BukkitRunnable() {
        //    @Override
        //    final public void run() {
        // 顯示容器
        Inventory inv = Bukkit.createInventory(null, 54, "§z領地刪除共用人");

        ItemStack item;
        ItemMeta meta;
        SkullMeta skull_meta;

        //long spend;
        //long last_time = new Date().getTime();

        // 取得所有玩家清單

        // 檢查是否有權限
        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫
            ResultSet res;

            String ID = SuperFreedomSurvive.Land.Cache.get(player); // 取得緩存
            // 檢查是否有權限
            if (SuperFreedomSurvive.Land.Permissions.Ownership(player, ID)) {
                // 是

                // 取得全部共用人
                int total = 0;
                res = sta.executeQuery("SELECT * FROM `land-share_data` WHERE `ID` = '" + ID + "' LIMIT 45");
                while (res.next()) {

                    // ---------------------------------------------------------------------------------------
                    // 判斷是否在線上
                    Player player_to = Bukkit.getServer().getPlayer(res.getString("Player"));
                    // 被傳送玩家是否在線上
                    if (player_to == null) {
                        // ! 離線
                        // a130a76d-3dab-429e-bd17-3b2b84201f80
                        item = SuperFreedomSurvive.Block.Skull.Get("a130a76d-3dab-429e-bd17-3b2b84201f80", "§r§f" + Data.getNick(res.getString("Player")) + " §r§c(離線中)", new String[]{"§r§f   ID: §a" + res.getString("Player"),"§r§f (點擊) 取消與此玩家共用領地"});
                        meta = item.getItemMeta();

                        meta.getCustomTagContainer().setCustomTag(player_key, ItemTagType.STRING, res.getString("Player")); // 創建特殊類型資料

                        item.setItemMeta(meta);

                        inv.setItem(total, item);

                    } else {
                        // 線上狀態
                        item = new ItemStack(Material.PLAYER_HEAD);
                        meta = item.getItemMeta();
                        meta.setDisplayName("§r§f" + Data.getNick(res.getString("Player")));
                        //ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1)
                        meta.setLore(Arrays.asList(
                                ("§r§f   ID: §a" + res.getString("Player")),
                                ("§r§f (點擊) 將玩家從領地共用清單中刪除")
                        ));

                        meta.getCustomTagContainer().setCustomTag(player_key, ItemTagType.STRING, res.getString("Player")); // 創建特殊類型資料

                        // 寫入資料
                        item.setItemMeta(meta);

                        skull_meta = (SkullMeta) item.getItemMeta();
                        // 取得玩家名稱 並獲取UUID 設置為頭顱新的主人
                        skull_meta.setOwningPlayer(Bukkit.getPlayer(res.getString("Player")));
                        item.setItemMeta(skull_meta);
                        //player.sendMessage( Bukkit.getServer().getPlayer( player_name ).getUniqueId().toString() );
                        // 設置完成
                        inv.setItem(total, item);

                                /*
                                item = new ItemStack(Material.PLAYER_HEAD);
                                meta = item.getItemMeta();
                                meta.setDisplayName("§r§a" + res.getString("Player"));
                                //ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1)
                                meta.setLore(Arrays.asList(
                                        ("§r§f (點擊) 取消與此玩家共用領地")
                                ));
                                // 寫入資料
                                item.setItemMeta(meta);

                                skull_meta = (SkullMeta) item.getItemMeta();
                                // 取得UUID 設置為頭顱新的主人
                                skull_meta.setOwningPlayer(Bukkit.getServer().getOfflinePlayer(UUID.fromString(res.getString("UUID"))));

                                item.setItemMeta(skull_meta);
                                //player.sendMessage( Bukkit.getServer().getPlayer( player_name ).getUniqueId().toString() );
                                // 設置完成
                                inv.setItem(total, item);
                                // ---------------------------------------------------------------------------------------

                                spend = (new Date().getTime()) - last_time;
                                if (spend > 2000) {
                                    new BukkitRunnable() {
                                        @Override
                                        final public void run () {
                                            player.openInventory(inv);
                                        }
                                    }.runTask(ServerPlugin.SYSTEM.Plugin.get());
                                    last_time = new Date().getTime();
                                }
                                */
                    }

                    // 總數 + 1
                    ++total;

                }

                res.close(); // 關閉查詢


                // 加入剩餘的
                // --------------------------------------     45     --------------------------------------
                // 沒找到你要的?
                item = new ItemStack(Material.MAP, 1);
                meta = item.getItemMeta();
                meta.setDisplayName("§b將玩家從領地共用清單刪除");
                meta.setLore(Arrays.asList(
                        ("§r§f點擊頭像即可!")
                ));
                // 寫入資料
                item.setItemMeta(meta);
                // 設置完成
                inv.setItem(45, item);

                // --------------------------------------     52     --------------------------------------
                inv.setItem(52, SuperFreedomSurvive.Menu.Open.PreviousPage());

                // --------------------------------------     53     --------------------------------------
                inv.setItem(53, SuperFreedomSurvive.Menu.Open.TurnOff());

/*
                        new BukkitRunnable() {
                            @Override
                            final public void run () {
                                // 寫入到容器頁面
                                player.openInventory(inv);
                            }
                        }.runTask(ServerPlugin.SYSTEM.Plugin.get());
*/
                // 寫入到容器頁面
                player.openInventory(inv);


            } else {
                player.sendMessage("[領地系統]  §c你無權限編輯");
            }

            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }


        //    }
        //}.runTaskAsynchronously(ServerPlugin.SYSTEM.Plugin.get());
    }


    // 阻止玩家拿走物品
    @EventHandler
    final public void onInventoryClickEvent_____(InventoryClickEvent event) {
        // 判斷是否為玩家

        if (event.getWhoClicked() instanceof Player) {
            // 是玩家
            Player player = (Player) event.getWhoClicked();

            // 檢查容器名稱
            if (event.getView().getTitle().equalsIgnoreCase("§z領地刪除共用人")) {
                // 是沒錯

                event.setCancelled(true);

                // 檢查點擊的是第幾個位置


                if (event.getRawSlot() < 45) {
                    // 領地刪除共用人

                    // 頭顱
                    if (event.getCurrentItem() == null) { return; }
                    if (event.getCurrentItem().getItemMeta() == null) { return; }

                    ItemMeta meta = event.getCurrentItem().getItemMeta();
                    CustomItemTagContainer tag = meta.getCustomTagContainer();

                    if (tag.getCustomTag(player_key,ItemTagType.STRING) == null) { return; }

                    Setting.removeLandShare(player,tag.getCustomTag(player_key,ItemTagType.STRING));

                } else if (event.getRawSlot() == 52) {
                        // 上一頁
                        set_land(player, SuperFreedomSurvive.Land.Cache.get(player));

                } else if (event.getRawSlot() == 45) {


                } else if (event.getRawSlot() == 53) {
                    // 關閉
                    event.getWhoClicked().closeInventory();

                }

            }
        }
    }



///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // 全部領地清單
    final public static void land_all(Player player) {

        //new BukkitRunnable() {
        //    @Override
        //    final public void run() {
        // 顯示容器
        Inventory inv = Bukkit.createInventory(null, 54, "§z全部領地清單");

        ItemStack item;
        ItemMeta meta;
    }

}
