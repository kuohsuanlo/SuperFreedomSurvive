package SuperFreedomSurvive.Menu.Function;

import SuperFreedomSurvive.Menu.Open;
import SuperFreedomSurvive.Player.Pay;
import SuperFreedomSurvive.SYSTEM.MySQL;
import SuperFreedomSurvive.World.Data;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.tags.ItemTagType;
import org.bukkit.scheduler.BukkitRunnable;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

final public class Warp implements Listener {


    static final private org.bukkit.NamespacedKey id_key = SuperFreedomSurvive.SYSTEM.NamespacedKey.getKey("WarpID");
    static final private org.bukkit.NamespacedKey number_key = SuperFreedomSurvive.SYSTEM.NamespacedKey.getKey("PagesNumber");


    // 座標系統
    final public static void menu(Player player) {
        // 顯示容器
        Inventory inv = Bukkit.createInventory(null, 54, "§z定點系統");

        ItemStack item;
        ItemMeta meta;


        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫
            ResultSet res = sta.executeQuery("SELECT * FROM `player-warp_data` WHERE `Player` = '" + player.getName() + "' ORDER BY `Time` LIMIT 45");
            res.last();
            // 最後一行 行數 是否 > 0
            if (res.getRow() > 0) {
                // 有資料
                // 跳回 初始行 必須使用 next() 才能取得資料
                res.beforeFirst();
                while (res.next()) {
                    // 跑完全部資料
                    //res.getInt("X")

                    Material material = Material.getMaterial(res.getString("Material"));
                    if (material == null) continue;

                    item = new ItemStack(material);
                    meta = item.getItemMeta();
                    meta.setDisplayName("§r§e" + res.getString("Name"));
                    if (res.getBoolean("Public")) {
                        // 公開傳送點
                        if (res.getString("Deadline") == null) {
                            // 永久公開點
                            meta.setLore(Arrays.asList(
                                    ("§r§f - §e公開傳送點"),
                                    ("§r§f - 編號 " + res.getString("ID")),
                                    ("§r§f - 到期日 §e永久"),
                                    ("§r§f - 世界 " + res.getString("World")),
                                    ("§r§f - 位置 X " + res.getInt("X")),
                                    ("§r§f        Y " + res.getInt("Y")),
                                    ("§r§f        Z " + res.getInt("Z")),
                                    ("§r§f - 創建時間 " + res.getString("Time")),
                                    ("§r§f (左鍵點擊) 前往此位置"),
                                    ("§r§f (鼠標物品+右鍵) 修改顯示物品"),
                                    ("§r§f (鼠標空手+右鍵) 修改顯示名稱"),
                                    ("§r§f (Shift+右鍵) 刪除此位置定點")
                            ));
                        } else {
                            // 非永久公開點
                            meta.setLore(Arrays.asList(
                                    ("§r§f - §e公開傳送點"),
                                    ("§r§f - 編號 " + res.getString("ID")),
                                    ("§r§f - 到期日 " + res.getString("Deadline")),
                                    ("§r§f - 世界 " + res.getString("World")),
                                    ("§r§f - 位置 X " + res.getInt("X")),
                                    ("§r§f        Y " + res.getInt("Y")),
                                    ("§r§f        Z " + res.getInt("Z")),
                                    ("§r§f - 創建時間 " + res.getString("Time")),
                                    ("§r§f (左鍵點擊) 前往此位置"),
                                    ("§r§f (鼠標物品+右鍵) 修改顯示物品"),
                                    ("§r§f (鼠標空手+右鍵) 修改顯示名稱"),
                                    ("§r§f (Shift+右鍵) 刪除此位置定點")
                            ));
                        }
                    } else {
                        // 私人傳送點
                        meta.setLore(Arrays.asList(
                                ("§r§f - 私人傳送點"),
                                ("§r§f - 世界 " + res.getString("World")),
                                ("§r§f - 位置 X " + res.getInt("X")),
                                ("§r§f        Y " + res.getInt("Y")),
                                ("§r§f        Z " + res.getInt("Z")),
                                ("§r§f - 創建時間 " + res.getString("Time")),
                                ("§r§f (左鍵點擊) 前往此位置"),
                                ("§r§f (鼠標物品+右鍵) 修改顯示物品"),
                                ("§r§f (鼠標空手+右鍵) 修改顯示名稱"),
                                ("§r§f (Shift+右鍵) 刪除此位置定點")
                        ));
                    }
                    // 寫入資料
                    item.setItemMeta(meta);
                    // 設置完成
                    inv.setItem((res.getRow() - 1), item);

                }

            }

            res.close(); // 關閉查詢
            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }


        // --------------------------------------     45     --------------------------------------
        // 沒找到你要的?
        item = new ItemStack(Material.MAP);
        meta = item.getItemMeta();
        meta.setDisplayName("§b更改圖示與名稱方法?");
        meta.setLore(Arrays.asList(
                ("§r§f - 首先先將要當圖示的物品 用鐵占重新命名"),
                ("§r§f   回到此介面 從背包裡拿起來"),
                ("§r§f   拿起改名物品滑到 要設定的定點上面右鍵"),
                ("§r§f   注意! 之後物品會被丟出背包! 記得撿回來!"),
                ("§r§f   不支持NBT數據!! 特殊數據通通不支持"),
                ("§r§f   經告!! 隨便連續點擊亂放 機率被吃物品")
        ));
        // 寫入資料
        item.setItemMeta(meta);
        // 設置完成
        inv.setItem(45, item);


        // --------------------------------------     46     --------------------------------------
        item = new ItemStack(Material.ENDER_EYE);
        meta = item.getItemMeta();
        meta.setDisplayName("§r§e查看全部公開定點");
        meta.setLore(Arrays.asList(
                ("§r§f - 查看/傳送其他人開放的定點位置")
        ));
        // 寫入資料
        item.setItemMeta(meta);
        // 設置完成
        inv.setItem(46, item);


        // --------------------------------------     47     --------------------------------------
        item = new ItemStack(Material.LIGHT_GRAY_BANNER);
        meta = item.getItemMeta();
        meta.setDisplayName("§r§e新增定點");
        meta.setLore(Arrays.asList(
                ("§r§f (點擊) 將此位置新增到定點紀錄內"),
                ("§r§f - 最大可擁有 45 個 定點位置")
        ));
        // 寫入資料
        item.setItemMeta(meta);
        // 設置完成
        inv.setItem(47, item);


        // --------------------------------------     48     --------------------------------------
        item = new ItemStack(Material.PINK_BANNER);
        meta = item.getItemMeta();
        meta.setDisplayName("§r§e新增公開定點(24小時)");
        meta.setLore(Arrays.asList(
                ("§r§f (點擊) 將此位置新增到定點紀錄內"),
                ("§r§f - 花費 2 顆綠寶石"),
                ("§r§f - 最大可擁有 45 個 定點位置"),
                ("§r§f - 有效時間內 別人可從 查看全部公開定點 看到")
        ));
        // 寫入資料
        item.setItemMeta(meta);
        // 設置完成
        inv.setItem(48, item);


        // --------------------------------------     49     --------------------------------------
        item = new ItemStack(Material.RED_BANNER);
        meta = item.getItemMeta();
        meta.setDisplayName("§r§e新增公開定點(永久)");
        meta.setLore(Arrays.asList(
                ("§r§f (點擊) 將此位置新增到定點紀錄內"),
                ("§r§f - 花費 64 顆綠寶石"),
                ("§r§f - 最大可擁有 45 個 定點位置"),
                ("§r§f - 有效時間內 別人可從 查看全部公開定點 看到")
        ));
        // 寫入資料
        item.setItemMeta(meta);
        // 設置完成
        inv.setItem(49, item);


        // --------------------------------------     52     --------------------------------------
        inv.setItem(52, SuperFreedomSurvive.Menu.Open.PreviousPage());


        // --------------------------------------     53     --------------------------------------
        inv.setItem(53, SuperFreedomSurvive.Menu.Open.TurnOff());


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
            if (event.getView().getTitle().equalsIgnoreCase("§z定點系統")) {
                // 是沒錯


                if (event.getRawSlot() < 54) {
                    event.setCancelled(true);
                }

                if (event.isShiftClick()) {
                    event.setCancelled(true);
                }


                // 是否為空白
                if (event.getRawSlot() < 54 && event.getCurrentItem() == null) {
                    event.setCancelled(true);
                    return;

                } else if (event.getRawSlot() < 54 && event.getCurrentItem().getType() == Material.AIR) {
                    event.setCancelled(true);
                    return;

                }


                if (event.getRawSlot() < 45) {

                    // 檢查 點擊是否為
                    if (event.isShiftClick() && event.isRightClick()) {
                        // shift + 右鍵點擊
                        // 刪除
                        try {
                            Connection con = MySQL.getConnection(); // 連線 MySQL
                            Statement sta = con.createStatement(); // 取得控制庫
                            ResultSet res = sta.executeQuery("SELECT * FROM `player-warp_data` WHERE `Player` = '" + player.getName() + "' ORDER BY `Time` LIMIT 45");
                            res.last();
                            // 最後一行 行數 是否 > 0
                            if (res.getRow() > 0) {
                                // 有資料
                                // 跳回 初始行 必須使用 next() 才能取得資料
                                res.beforeFirst();
                                while (res.next()) {
                                    // 跑完全部資料
                                    // 檢查是否是我們要的資料
                                    if (res.getRow() - 1 == event.getRawSlot()) {
                                        // 是需要的資料

                                        String world = res.getString("World");
                                        int X = res.getInt("X");
                                        int Y = res.getInt("Y");
                                        int Z = res.getInt("Z");
                                        String time = res.getString("Time");
                                        String name = res.getString("Name");

                                        res.close(); // 關閉查詢

                                        sta.executeUpdate("DELETE FROM `player-warp_data` WHERE `Player` = '" + player.getName() + "' AND `World` = '" + world + "' AND `X` = '" + X + "' AND `Y` = '" + Y + "' AND `Z` = '" + Z + "' AND `Time` = '" + time + "' AND `Name` = '" + name + "' LIMIT 1");
                                        player.sendMessage("[定點系統]  刪除成功");
                                        // 重開本介面
                                        menu(player);

                                        res.close(); // 關閉查詢
                                        sta.close(); // 關閉連線

                                        return;

                                    }
                                }
                            }

                            res.close(); // 關閉查詢
                            sta.close(); // 關閉連線

                        } catch (Exception ex) {
                            ex.printStackTrace();
                            menu(player);
                        }


                    } else if (event.isRightClick()) {
                        // 右鍵點擊
                        // 重新命名 / 修改圖示

                        ItemStack item = event.getCursor();

                        if ( item.getType() == Material.AIR ) {
                            // 修改文字

                            player.closeInventory();
                            player.sendMessage("[定點系統]  §a請輸入要顯示的名稱  支持使用&顏色代碼 最多40字");

                            SuperFreedomSurvive.SYSTEM.Input.setRegistry(player,
                                    new BukkitRunnable() {
                                        int time = 1200;
                                        String message = null;
                                        int raw_slot = event.getRawSlot();

                                        @Override
                                        final public void run() {
                                            if (time < 0) {
                                                player.sendMessage("[定點系統]  §c等待輸入太久了 自動取消");
                                                cancel();
                                                return;
                                            }
                                            message = SuperFreedomSurvive.SYSTEM.Input.getMessage(player);

                                            if (message != null) {
                                                // 有資料

                                                message = SuperFreedomSurvive.Function.ColorCodeTransform.Conversion(message);

                                                if (message.length() > 40) {
                                                    message = message.substring(0, 40);
                                                }

                                                try {
                                                    Connection con = MySQL.getConnection(); // 連線 MySQL
                                                    Statement sta = con.createStatement(); // 取得控制庫
                                                    ResultSet res = sta.executeQuery("SELECT * FROM `player-warp_data` WHERE `Player` = '" + player.getName() + "' ORDER BY `Time` LIMIT 45");
                                                    res.last();
                                                    // 最後一行 行數 是否 > 0
                                                    if (res.getRow() > 0) {
                                                        // 有資料
                                                        // 跳回 初始行 必須使用 next() 才能取得資料
                                                        res.beforeFirst();
                                                        while (res.next()) {
                                                            // 跑完全部資料
                                                            // 檢查是否是我們要的資料
                                                            if (res.getRow() - 1 == raw_slot) {
                                                                // 是需要的資料
                                                                // 進行編輯

                                                                String world = res.getString("World");
                                                                int X = res.getInt("X");
                                                                int Y = res.getInt("Y");
                                                                int Z = res.getInt("Z");
                                                                String time = res.getString("Time");
                                                                String old_name = res.getString("Name");
                                                                String old_material = res.getString("Material");

                                                                res.close(); // 關閉查詢

                                                                sta.executeUpdate("UPDATE `player-warp_data` SET `Name` = '" + message + "' WHERE `Player` = '" + player.getName() + "' AND `World` = '" + world + "' AND `X` = '" + X + "' AND `Y` = '" + Y + "' AND `Z` = '" + Z + "' AND `Time` = '" + time + "' AND `Name` = '" + old_name + "' AND `Material` = '" + old_material + "' LIMIT 1;");

                                                                player.sendMessage("[定點系統]  設置成功");

                                                                // 重開本介面
                                                                menu(player);

                                                                break;
                                                            }
                                                        }
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
                                ResultSet res = sta.executeQuery("SELECT * FROM `player-warp_data` WHERE `Player` = '" + player.getName() + "' ORDER BY `Time` LIMIT 45");
                                res.last();
                                // 最後一行 行數 是否 > 0
                                if (res.getRow() > 0) {
                                    // 有資料
                                    // 跳回 初始行 必須使用 next() 才能取得資料
                                    res.beforeFirst();
                                    while (res.next()) {
                                        // 跑完全部資料
                                        // 檢查是否是我們要的資料
                                        if (res.getRow() - 1 == event.getRawSlot()) {
                                            // 是需要的資料
                                            // 進行編輯

                                            String world = res.getString("World");
                                            int X = res.getInt("X");
                                            int Y = res.getInt("Y");
                                            int Z = res.getInt("Z");
                                            String time = res.getString("Time");
                                            String old_name = res.getString("Name");
                                            String old_material = res.getString("Material");

                                            res.close(); // 關閉查詢

                                            sta.executeUpdate("UPDATE `player-warp_data` SET `Material` = '" + material + "' WHERE `Player` = '" + player.getName() + "' AND `World` = '" + world + "' AND `X` = '" + X + "' AND `Y` = '" + Y + "' AND `Z` = '" + Z + "' AND `Time` = '" + time + "' AND `Name` = '" + old_name + "' AND `Material` = '" + old_material + "' LIMIT 1;");

                                            player.sendMessage("[定點系統]  設置成功");

                                            // 重開本介面
                                            menu(player);

                                            break;
                                        }
                                    }
                                }

                                res.close(); // 關閉查詢
                                sta.close(); // 關閉連線

                            } catch (Exception ex) {
                                ex.printStackTrace();
                                menu(player);
                            }

                        }


                    } else if (event.isLeftClick()) {
                        // 左鍵點擊
                        // 傳送
                        try {
                            Connection con = MySQL.getConnection(); // 連線 MySQL
                            Statement sta = con.createStatement(); // 取得控制庫
                            ResultSet res = sta.executeQuery("SELECT * FROM `player-warp_data` WHERE `Player` = '" + player.getName() + "' ORDER BY `Time` LIMIT 45");
                            res.last();
                            // 最後一行 行數 是否 > 0
                            if (res.getRow() > 0) {
                                // 有資料
                                // 跳回 初始行 必須使用 next() 才能取得資料
                                res.beforeFirst();
                                while (res.next()) {
                                    // 跑完全部資料
                                    // 檢查是否是我們要的資料
                                    if (res.getRow() - 1 == event.getRawSlot()) {
                                        // 是需要的資料
                                        // 傳送過去

                                        int X = res.getInt("X");
                                        int Y = res.getInt("Y");
                                        int Z = res.getInt("Z");
                                        String world = res.getString("World");

                                        if (!Data.Have(world)) {
                                            return;
                                        }

                                        // 檢查是否有領地權限
                                        if (SuperFreedomSurvive.Land.Permissions.Inspection(player, world, X, Y, Z, "Permissions_PlayerWarp")) {
                                            // 有權限

                                            // 關閉介面
                                            player.closeInventory();

                                            res.close(); // 關閉查詢

                                            res = sta.executeQuery("SELECT * FROM `world-list_data` WHERE `Enable` = '1' AND `Name` = '" + world + "' LIMIT 1");
                                            // 跳到最後一行
                                            res.last();
                                            // 最後一行 行數 是否 > 0
                                            if (res.getRow() > 0) {

                                                res.close(); // 關閉查詢

                                                // 傳送
                                                SuperFreedomSurvive.Player.Teleport.Location(player, world, X, Y, Z);

                                            }

                                            res.close(); // 關閉查詢

                                        } else {
                                            player.sendMessage("[領地系統]  §c領地禁止 定點傳送");
                                            SuperFreedomSurvive.Land.Display.Message(player, "§c領地禁止 定點傳送");
                                        }

                                        break;
                                    }
                                }
                            }

                            res.close(); // 關閉查詢
                            sta.close(); // 關閉連線

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        // 重開本介面
                        menu(player);
                    }


                } else if (event.getRawSlot() == 46) {
                    // 查看全部公開定點
                    Look_All_Public(player, 0);


                } else if (event.getRawSlot() == 47) {
                    // 新增定點位置
                    // 取得座標
                    int X = new BigDecimal(player.getLocation().getX()).setScale(0, BigDecimal.ROUND_FLOOR).intValue();
                    int Y = new BigDecimal(player.getLocation().getY()).setScale(0, BigDecimal.ROUND_FLOOR).intValue();
                    int Z = new BigDecimal(player.getLocation().getZ()).setScale(0, BigDecimal.ROUND_FLOOR).intValue();

                    String world = player.getWorld().getName();

                    if (!Data.Have(world)) {
                        return;
                    }

                    try {
                        Connection con = MySQL.getConnection(); // 連線 MySQL
                        Statement sta = con.createStatement(); // 取得控制庫
                        ResultSet res = sta.executeQuery("SELECT * FROM `player-warp_data` WHERE `Player` = '" + player.getName() + "' ORDER BY `Time` LIMIT 46");
                        res.last();
                        // 最後一行 行數 是否 > 45
                        if (res.getRow() > 44) {

                            res.close(); // 關閉查詢

                            player.sendMessage("[定點系統]  §c新增失敗 已達最多 45 個位置紀錄");

                        } else {

                            res.close(); // 關閉查詢

                            // 檢查是否有領地權限
                            if (SuperFreedomSurvive.Land.Permissions.Inspection(player, player.getLocation(), "Permissions_PlayerNewWarp")) {
                                // 有權限
                                sta.executeUpdate("INSERT INTO `player-warp_data` (`Player`, `World`, `X`, `Y`, `Z`, `Time`) VALUES ('" + player.getName() + "', '" + world + "', '" + X + "', '" + Y + "', '" + Z + "', '" + SuperFreedomSurvive.SYSTEM.Time.Get() + "')");

                                player.sendMessage("[定點系統]  新增成功");
                                // 重開本介面
                                menu(player);
                            } else {
                                player.sendMessage("[領地系統]  §c領地禁止 新增定點");
                                SuperFreedomSurvive.Land.Display.Message(player, "§c領地禁止 新增定點");
                            }


                        }

                        res.close(); // 關閉查詢
                        sta.close(); // 關閉連線

                    } catch (Exception ex) {
                        ex.printStackTrace();
                        menu(player);
                    }


                } else if (event.getRawSlot() == 48) {
                    // 新增公開定點位置 24 小時
                    // 取得座標
                    int X = new BigDecimal(player.getLocation().getX()).setScale(0, BigDecimal.ROUND_FLOOR).intValue();
                    int Y = new BigDecimal(player.getLocation().getY()).setScale(0, BigDecimal.ROUND_FLOOR).intValue();
                    int Z = new BigDecimal(player.getLocation().getZ()).setScale(0, BigDecimal.ROUND_FLOOR).intValue();

                    String world = player.getWorld().getName();

                    if (!Data.Have(world)) {
                        return;
                    }

                    try {
                        Connection con = MySQL.getConnection(); // 連線 MySQL
                        Statement sta = con.createStatement(); // 取得控制庫
                        ResultSet res = sta.executeQuery("SELECT * FROM `player-warp_data` WHERE `Player` = '" + player.getName() + "' ORDER BY `Time` LIMIT 46");
                        res.last();
                        // 最後一行 行數 是否 > 45
                        if (res.getRow() > 44) {

                            res.close(); // 關閉查詢

                            player.sendMessage("[定點系統]  §c新增失敗 已達最多 45 個位置紀錄");

                        } else {

                            res.close(); // 關閉查詢

                            // 檢查是否有領地權限
                            if (SuperFreedomSurvive.Land.Permissions.Inspection(player, player.getLocation(), "Permissions_PlayerNewWarp")) {
                                // 有權限
                                // 檢查物資是否足夠

                                if (Pay.Have(player, 2)) {
                                    // 足夠

                                    // 收取綠寶石
                                    if (Pay.Recover(player, 2)) {

                                        // 創建唯一ID
                                        String random = "";
                                        String[] RegSNContent = {
                                                "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
                                                "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
                                                "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
                                                "_", "+", "-", ".", "~", "/", "=", "[", "]", "(", ")", "<", ">", "`", "!", "@", "#", "$", "^", "&", "*", "{", "}", ":", ";", "?", "|"
                                        };
                                        for (int i = 0; i < 12; i++)
                                            random += RegSNContent[(int) (Math.random() * RegSNContent.length)];

                                        // 創建時間
                                        Calendar calendar = Calendar.getInstance();
                                        // 1 天後
                                        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 1);

                                        int year = calendar.get(Calendar.YEAR);
                                        int month = calendar.get(Calendar.MONTH) + 1; // 1月的值為0
                                        int day = calendar.get(Calendar.DAY_OF_MONTH);
                                        int hour = calendar.get(Calendar.HOUR_OF_DAY); // HOUR_OF_DAY 24小時制    HOUR 12小時制
                                        int minute = calendar.get(Calendar.MINUTE);
                                        int second = calendar.get(Calendar.SECOND);
                                        String deadline = year + "-" + month + "-" + day + "-" + hour + ":" + minute + ":" + second;

                                        sta.executeUpdate("INSERT INTO `player-warp_data` (`Player`, `Public`, `World`, `X`, `Y`, `Z`, `Time`, `Deadline`, `ID`) VALUES ('" + player.getName() + "', '1', '" + world + "', '" + X + "', '" + Y + "', '" + Z + "', '" + SuperFreedomSurvive.SYSTEM.Time.Get() + "', '" + deadline + "', '" + random + "')");

                                        player.sendMessage("[定點系統]  新增成功");
                                        // 重開本介面
                                        menu(player);

                                    }
                                } else {
                                    // 不夠
                                    player.sendMessage("[定點系統]  §c新增公開定點 所需物資不夠 需要 2 個 綠寶石");
                                }
                            } else {
                                player.sendMessage("[領地系統]  §c領地禁止 新增定點");
                                SuperFreedomSurvive.Land.Display.Message(player, "§c領地禁止 新增定點");
                            }
                        }

                        res.close(); // 關閉查詢
                        sta.close(); // 關閉連線

                    } catch (Exception ex) {
                        ex.printStackTrace();
                        menu(player);
                    }


                } else if (event.getRawSlot() == 49) {
                    // 新增公開定點位置 永久
                    // 取得座標
                    int X = new BigDecimal(player.getLocation().getX()).setScale(0, BigDecimal.ROUND_FLOOR).intValue();
                    int Y = new BigDecimal(player.getLocation().getY()).setScale(0, BigDecimal.ROUND_FLOOR).intValue();
                    int Z = new BigDecimal(player.getLocation().getZ()).setScale(0, BigDecimal.ROUND_FLOOR).intValue();

                    String world = player.getWorld().getName();

                    if (!Data.Have(world)) {
                        return;
                    }

                    try {
                        Connection con = MySQL.getConnection(); // 連線 MySQL
                        Statement sta = con.createStatement(); // 取得控制庫
                        ResultSet res = sta.executeQuery("SELECT * FROM `player-warp_data` WHERE `Player` = '" + player.getName() + "' ORDER BY `Time` LIMIT 46");
                        res.last();
                        // 最後一行 行數 是否 > 45
                        if (res.getRow() > 44) {

                            res.close(); // 關閉查詢

                            player.sendMessage("[定點系統]  §c新增失敗 已達最多 45 個位置紀錄");

                        } else {

                            res.close(); // 關閉查詢

                            // 檢查是否有領地權限
                            if (SuperFreedomSurvive.Land.Permissions.Inspection(player, player.getLocation(), "Permissions_PlayerNewWarp")) {
                                // 有權限
                                // 檢查物資是否足夠
                                int emerald_amount = 0;
                                PlayerInventory inventory = player.getInventory();
                                HashMap all = inventory.all(Material.EMERALD);
                                for (Object key : all.keySet()) {
                                    emerald_amount = emerald_amount + ((ItemStack) all.get(key)).getAmount();
                                }

                                if (Pay.Have(player, 64)) {
                                    // 足夠

                                    // 收取綠寶石
                                    if (Pay.Recover(player, 64)) {

                                        // 創建唯一ID
                                        String random = "";
                                        String[] RegSNContent = {
                                                "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
                                                "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
                                                "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
                                                "_", "+", "-", ".", "~", "/", "=", "[", "]", "(", ")", "<", ">", "`", "!", "@", "#", "$", "^", "&", "*", "{", "}", ":", ";", "?", "|"
                                        };
                                        for (int i = 0; i < 12; i++)
                                            random += RegSNContent[(int) (Math.random() * RegSNContent.length)];

                                        sta.executeUpdate("INSERT INTO `player-warp_data` (`Player`, `Public`, `World`, `X`, `Y`, `Z`, `Time`, `Deadline`, `ID`) VALUES ('" + player.getName() + "', '1', '" + world + "', '" + X + "', '" + Y + "', '" + Z + "', '" + SuperFreedomSurvive.SYSTEM.Time.Get() + "', NULL, '" + random + "')");

                                        player.sendMessage("[定點系統]  新增成功");
                                        // 重開本介面
                                        menu(player);

                                    }
                                } else {
                                    // 不夠
                                    player.sendMessage("[定點系統]  §c新增公開定點 所需物資不夠 需要 64 個 綠寶石");
                                }
                            } else {
                                player.sendMessage("[領地系統]  §c領地禁止 新增定點");
                                SuperFreedomSurvive.Land.Display.Message(player, "§c領地禁止 新增定點");
                            }
                        }

                        res.close(); // 關閉查詢
                        sta.close(); // 關閉連線

                    } catch (Exception ex) {
                        ex.printStackTrace();
                        menu(player);
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

            }
        }
    }

    @EventHandler
    final public void onInventoryDragEvent(InventoryDragEvent event) {
        if (event.getView().getTitle().equalsIgnoreCase("§z定點系統")) {
            event.setCancelled(true);
        }
    }









/*
    // 更改名稱
    @EventHandler
    final public void onInventoryClickEvent__(InventoryClickEvent event) {
        try {
            //  點擊容器中的欄位時
            if (event.getWhoClicked() instanceof Player) {
                Player player = (Player)event.getWhoClicked();

                if (event.getInventory().getType() == InventoryType.ANVIL) {
                    // 鐵鉆事件

                    player.sendMessage(event.getSlot()+"");

                    // 檢查物品
                    if (event.getInventory().getItem( 2 ) != null ) {
                        if ( event.getInventory().getItem( 2 ).getItemMeta().getCustomTagContainer().getCustomTag( warp_edit_key , ItemTagType.INTEGER ) != null ) {
                            // 確定為指定物品
                            player.sendMessage("y");

                        }
                    }

                }
            }
        } catch (Exception ex) {
            // 出錯
            //player.sendMessage(ex.getMessage());
        }
    }

*/


    // 查看全部公開定點位置
    static final public void Look_All_Public(Player player, int pages_number) {


        // 顯示容器
        Inventory inv = Bukkit.createInventory(null, 54, "§z公開定點清單");

        ItemStack item;
        ItemMeta meta;

        // 總數
        int total = 0;

        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫


            // 按照頁數取得 45 筆資料
            // 存入內容
            ResultSet res = sta.executeQuery("SELECT * FROM `player-warp_data` WHERE `Public` = '1' AND ( `Deadline` > '" + SuperFreedomSurvive.SYSTEM.Time.Get() + "' OR `Deadline` IS NULL ) ORDER BY `Time` DESC LIMIT 45 OFFSET " + 45 * pages_number);

            while (res.next()) {
                // 取得
                // ---------------------------------------------------------------------------------------
                item = new ItemStack(Material.valueOf(res.getString("Material")));
                meta = item.getItemMeta();
                meta.setDisplayName("§r§e" + res.getString("Name"));
                //ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1)

                // 公開傳送點
                if (res.getString("Deadline") == null) {
                    // 永久公開點
                    meta.setLore(Arrays.asList(
                            ("§r§f (點擊) 傳送"),
                            ("§r§f - 編號 " + res.getString("ID")),
                            ("§r§f - 到期日 §e永久"),
                            ("§r§f - 世界 " + res.getString("World")),
                            ("§r§f - 位置 X " + res.getInt("X")),
                            ("§r§f        Y " + res.getInt("Y")),
                            ("§r§f        Z " + res.getInt("Z")),
                            ("§r§f - 創建時間 " + res.getString("Time")),
                            ("§r§f - 玩家 " + res.getString("Player") + " 所設置")
                    ));
                } else {
                    // 非永久公開點
                    meta.setLore(Arrays.asList(
                            ("§r§f (點擊) 傳送"),
                            ("§r§f - 編號 " + res.getString("ID")),
                            ("§r§f - 到期日 " + res.getString("Deadline")),
                            ("§r§f - 世界 " + res.getString("World")),
                            ("§r§f - 位置 X " + res.getInt("X")),
                            ("§r§f        Y " + res.getInt("Y")),
                            ("§r§f        Z " + res.getInt("Z")),
                            ("§r§f - 創建時間 " + res.getString("Time")),
                            ("§r§f - 玩家 " + res.getString("Player") + " 所設置")
                    ));
                }
                meta.getCustomTagContainer().setCustomTag(id_key, ItemTagType.STRING, res.getString("ID")); // 登入key
                meta.getCustomTagContainer().setCustomTag(number_key, ItemTagType.INTEGER, pages_number); // 登入key

                item.setItemMeta(meta);

                // 設置完成
                inv.setItem(total, item);

                total++;
            }

            res.close(); // 關閉查詢

            if (pages_number > 0) {
                // --------------------------------------     45     --------------------------------------
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
                inv.setItem(45, item);
            }


            res = sta.executeQuery("Select Count(*) FROM `player-warp_data` WHERE `Public` = '1' AND ( `Deadline` > '" + SuperFreedomSurvive.SYSTEM.Time.Get() + "' OR `Deadline` IS NULL )");
            res.next();
            if (res.getInt("Count(*)") > (pages_number + 1) * 45) {

                res.close(); // 關閉查詢

                // --------------------------------------     46     --------------------------------------
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
                inv.setItem(46, item);
            }

            // --------------------------------------     52     --------------------------------------
            inv.setItem(52, Open.PreviousPage());

            // --------------------------------------     53     --------------------------------------
            inv.setItem(53, Open.TurnOff());

            // 寫入到容器頁面
            player.openInventory(inv);

            res.close(); // 關閉查詢
            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
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
            if (event.getView().getTitle().equalsIgnoreCase("§z公開定點清單")) {
                // 是沒錯

                event.setCancelled(true);

                try {

                    if (event.getCurrentItem().getItemMeta() == null) { return; }

                    if (event.getRawSlot() < 45) {
                        String id = event.getCurrentItem().getItemMeta().getCustomTagContainer().getCustomTag(id_key, ItemTagType.STRING);

                        Connection con = MySQL.getConnection(); // 連線 MySQL
                        Statement sta = con.createStatement(); // 取得控制庫
                        ResultSet res = sta.executeQuery("SELECT * FROM `player-warp_data` WHERE `ID` = '" + id + "' AND ( `Deadline` IS NULL OR `Deadline` > '" + SuperFreedomSurvive.SYSTEM.Time.Get() + "') ORDER BY `Deadline` LIMIT 1");
                        res.last();
                        // 最後一行 行數 是否 > 0
                        if (res.getRow() > 0) {
                            // 有資料
                            // 跳回 初始行 必須使用 next() 才能取得資料
                            res.beforeFirst();
                            res.next();

                            int X = res.getInt("X");
                            int Y = res.getInt("Y");
                            int Z = res.getInt("Z");
                            String world = res.getString("World");

                            res.close(); // 關閉查詢

                            if (!Data.Have(world)) {
                                return;
                            }

                            // 是否有權限
                            // 檢查是否有領地權限
                            if (SuperFreedomSurvive.Land.Permissions.Inspection(player, world, X, Y, Z, "Permissions_PlayerWarp")) {
                                // 有權限

                                // 關閉介面
                                player.closeInventory();

                                res = sta.executeQuery("SELECT * FROM `world-list_data` WHERE `Enable` = '1' AND `Name` = '" + world + "' LIMIT 1");
                                // 跳到最後一行
                                res.last();
                                // 最後一行 行數 是否 > 0
                                if (res.getRow() > 0) {

                                    res.close(); // 關閉查詢

                                    // 傳送
                                    SuperFreedomSurvive.Player.Teleport.Location(player, world, X, Y, Z);

                                }

                                res.close(); // 關閉查詢

                            } else {
                                player.sendMessage("[領地系統]  §c領地禁止 定點傳送");
                                SuperFreedomSurvive.Land.Display.Message(player, "§c領地禁止 定點傳送");
                            }
                        } else {
                            player.sendMessage("[定點系統]  §c此編號不存在 / 已經過期");
                        }

                        res.close(); // 關閉查詢
                        sta.close(); // 關閉連線

                    } else if (event.getRawSlot() == 45) {
                        if (event.getCursor() != null) {
                            if (event.getCurrentItem().getItemMeta().getCustomTagContainer().hasCustomTag(number_key, ItemTagType.INTEGER))
                            Look_All_Public(player, event.getCurrentItem().getItemMeta().getCustomTagContainer().getCustomTag(number_key, ItemTagType.INTEGER));
                        }

                    } else if (event.getRawSlot() == 46) {
                        if (event.getCursor() != null) {
                            if (event.getCurrentItem().getItemMeta().getCustomTagContainer().hasCustomTag(number_key, ItemTagType.INTEGER))
                            Look_All_Public(player, event.getCurrentItem().getItemMeta().getCustomTagContainer().getCustomTag(number_key, ItemTagType.INTEGER));
                        }


                    } else if (event.getRawSlot() == 52) {
                        // 上一頁
                        menu(player);

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


}







/*

    // 修改圖示 / 名稱
    final public static void set(Player player) {
        // 顯示容器
        //Inventory inv = Bukkit.createInventory(null, 54, "定點修改");

        ItemStack item;
        ItemMeta meta;
        Inventory inv = Bukkit.createInventory( null ,    InventoryType.ANVIL , "定點修改" );




        // 寫入到容器頁面
        player.openInventory( inv );

    }

    // 阻止玩家拿走物品
    @EventHandler
    final public void onInventoryClick(InventoryClickEvent event) {
        // 判斷是否為玩家

        if (event.getWhoClicked() instanceof Player) {
            // 是玩家
            Player player = (Player) event.getWhoClicked();

            // 檢查容器名稱
            if (event.getView().getTitle().equalsIgnoreCase("定點修改")) {
                // 是沒錯

                event.setCancelled(true);

                if (event.getRawSlot() == 3) {
                    player.sendMessage("[定點系統]  Y");

                }
            }
        }
    }
    */
