package SuperFreedomSurvive._Script;

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
import SuperFreedomSurvive._Script.Event.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;

final public class Edit implements Listener {

    static final public org.bukkit.NamespacedKey type_key = SuperFreedomSurvive.SYSTEM.NamespacedKey.getKey("Type");
    static final public org.bukkit.NamespacedKey type_new_key = SuperFreedomSurvive.SYSTEM.NamespacedKey.getKey("Type_New");

    // 選單接口
    final public static void view(Player player) {
        // 查看領地街口

        // 顯示容器
        Inventory inv = Bukkit.createInventory(null, 36, "§z領地腳本選擇當前領地");

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

        // --------------------------------------     35     --------------------------------------
        inv.setItem(35, SuperFreedomSurvive.Menu.Open.TurnOff());


        // ---------------------------------------------------------------------------------------


    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
            if (event.getView().getTitle().equalsIgnoreCase("§z領地腳本選擇當前領地")) {
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


                            // 查詢是否有此權限
                            if (SuperFreedomSurvive.Land.Permissions.Ownership(player, ID)) {
                                // 有
                                // 建立緩存
                                SuperFreedomSurvive.Land.Cache.set(player, ID);

                                // 跳轉到編輯頁面
                                menu(player);

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


                } else if (event.getRawSlot() == 35) {
                    // 關閉
                    event.getWhoClicked().closeInventory();
                }

            }
        }
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    final public static void menu(Player player) {

        String ID = SuperFreedomSurvive.Land.Cache.get(player); // 設定緩存

        // 是否在自己有權限的領地
        if (
                SuperFreedomSurvive.Land.Permissions.Ownership(player, ID) ||
                        SuperFreedomSurvive.Land.Permissions.LandShare(player, ID)
        ) {

            // 腳本編輯
            Inventory inv = Bukkit.createInventory(null, 54, "§z領地腳本編輯");


            ItemStack item;
            ItemMeta meta;
            // --------------------------------------     0     --------------------------------------
            item = new ItemStack(Material.GOLD_NUGGET);
            meta = item.getItemMeta();
            meta.setDisplayName("§e新增事件");
            meta.setLore(Arrays.asList(
                    ("§r§f (點擊) 新增一個事件")
            ));

            meta.getCustomTagContainer().setCustomTag(type_key, ItemTagType.STRING, "NEW"); // 登入key

            // 寫入資料
            item.setItemMeta(meta);
            // 設置完成
            inv.setItem(0, item);


            // --------------------------------------     52     --------------------------------------
            inv.setItem(52, SuperFreedomSurvive.Menu.Open.PreviousPage());

            // --------------------------------------     53     --------------------------------------
            inv.setItem(53, SuperFreedomSurvive.Menu.Open.TurnOff());

            player.openInventory(inv); // 打開頁面


        } else {
            player.sendMessage("[領地系統]  §c你無權限編輯");

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
            if (event.getView().getTitle().equalsIgnoreCase("§z領地腳本編輯")) {
                // 是沒錯

                event.setCancelled(true);

                // 是否在自己有權限的領地
                if (
                        SuperFreedomSurvive.Land.Permissions.Ownership(player, player.getLocation()) ||
                                SuperFreedomSurvive.Land.Permissions.LandShare(player, player.getLocation())
                ) {

                    try {

                        if (event.getRawSlot() == 52) {
                            // 上一頁
                            view(player);

                        } else if (event.getRawSlot() == 53) {
                            // 關閉
                            event.getWhoClicked().closeInventory();

                        } else {

                            // 檢查點擊類型
                            if (event.getCurrentItem().getItemMeta().getCustomTagContainer().getCustomTag(type_key, ItemTagType.STRING).equals("NEW")) {
                                // 新增事件
                                New_List(player);

                            }

                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }


    static final public void New_List(Player player) {
        // 清單
        // 進行修改

        String ID = SuperFreedomSurvive.Land.Cache.get(player); // 設定緩存

        // 是否在自己有權限的領地
        if (
                SuperFreedomSurvive.Land.Permissions.Ownership(player, ID) ||
                        SuperFreedomSurvive.Land.Permissions.LandShare(player, ID)
        ) {

            // 腳本編輯
            Inventory inv = Bukkit.createInventory(null, 54, "§z領地腳本新增事件");


            inv.setItem(0, Get_Item(Material.PAPER, "新增聊天事件", "AsyncPlayerChat"));


            // --------------------------------------     52     --------------------------------------
            inv.setItem(52, SuperFreedomSurvive.Menu.Open.PreviousPage());

            // --------------------------------------     53     --------------------------------------
            inv.setItem(53, SuperFreedomSurvive.Menu.Open.TurnOff());

            player.openInventory(inv); // 打開頁面


        } else {
            player.sendMessage("[領地系統]  你無權限編輯");

        }
    }

    // 快速給予物品接口
    static final public ItemStack Get_Item(Material material, String name, String key) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§e" + name);
        meta.setLore(Arrays.asList(
                ("§r§f (點擊) 新增這個事件並開始編輯")
        ));

        meta.getCustomTagContainer().setCustomTag(type_new_key, ItemTagType.STRING, key); // 登入key

        // 寫入資料
        item.setItemMeta(meta);
        // 設置完成
        return item;
    }


    // 阻止玩家拿走物品
    @EventHandler
    final public void onInventoryClickEvent_(InventoryClickEvent event) {
        // 判斷是否為玩家

        if (event.getWhoClicked() instanceof Player) {
            // 是玩家
            Player player = (Player) event.getWhoClicked();

            // 檢查容器名稱
            if (event.getView().getTitle().equalsIgnoreCase("§z領地腳本新增事件")) {
                // 是沒錯

                event.setCancelled(true);

                // 是否在自己有權限的領地
                if (
                        SuperFreedomSurvive.Land.Permissions.Ownership(player, player.getLocation()) ||
                                SuperFreedomSurvive.Land.Permissions.LandShare(player, player.getLocation())
                ) {

                    if (event.getRawSlot() == 52) {
                        // 上一頁
                        menu(player);

                    } else if (event.getRawSlot() == 53) {
                        // 關閉
                        event.getWhoClicked().closeInventory();

                    } else {

                        //try {

                        // 檢查標籤
                        if (event.getCurrentItem().getItemMeta().getCustomTagContainer().getCustomTag(type_new_key, ItemTagType.STRING) != null) {
                            switch (event.getCurrentItem().getItemMeta().getCustomTagContainer().getCustomTag(type_new_key, ItemTagType.STRING)) {
                                case "AsyncPlayerChat":
                                    // 聊天事件
                                    AsyncPlayerChat.menu(player);
                                    break;

                            }
                        }

                        //} catch (Exception ex) {
                        // 出錯
                        //}

                    }
                }
            }
        }
    }
}
