package SuperFreedomSurvive.Menu.Function;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.tags.ItemTagType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Customize implements Listener {
    // 自訂物品


    static final private org.bukkit.NamespacedKey lore_number = SuperFreedomSurvive.SYSTEM.NamespacedKey.getKey("LoreNumber");


    final public static void menu(Player player) {
        Inventory inv = Bukkit.createInventory(null, 18, "§z自訂物品系統");

        ItemStack item;
        ItemMeta meta;

        // --------------------------------------     0     --------------------------------------
        item = new ItemStack(Material.WRITABLE_BOOK);
        meta = item.getItemMeta();
        meta.setDisplayName("§e編輯模式");
        meta.setLore(Arrays.asList(
                ("§r§f - 自訂物品說明"),
                ("§r§f - 加入擁有專屬標籤的物品")
        ));
        // 寫入資料
        item.setItemMeta(meta);
        // 設置完成
        inv.setItem(0, item);


        // --------------------------------------     1     --------------------------------------
        item = new ItemStack(Material.HOPPER);
        meta = item.getItemMeta();
        meta.setDisplayName("§e複製模式(未完成)");
        meta.setLore(Arrays.asList(
                ("§r§f - 將物品的說明複製到其他物品上")
        ));
        // 寫入資料
        item.setItemMeta(meta);
        // 設置完成
        inv.setItem(1, item);




        // --------------------------------------     16     --------------------------------------
        inv.setItem(16, SuperFreedomSurvive.Menu.Open.PreviousPage());

        // --------------------------------------     17     --------------------------------------
        inv.setItem(17, SuperFreedomSurvive.Menu.Open.TurnOff());

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
            if (event.getView().getTitle().equalsIgnoreCase("§z自訂物品系統")) {
                // 是沒錯

                event.setCancelled(true);

                // 檢查點擊的是第幾個位置
                switch (event.getRawSlot()) {
                    case 0:
                        // 製作/編輯模式
                        Reproduction(player);
                        break;

                    case 1:
                        // 複製模式

                        break;

                    case 16:
                        // 上一頁
                        SuperFreedomSurvive.Menu.Open.open(player);
                        break;

                    case 17:
                        // 關閉
                        event.getWhoClicked().closeInventory();
                        break;

                    default:
                        break;
                }
            }
        }
    }





    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // 製作模式
    static public void Reproduction(Player player) {
        Inventory inv = Bukkit.createInventory(null, 54, "§z自訂物品 編輯模式");

        ItemStack item;
        ItemMeta meta;

        Rroduction(player,inv);
    }

    static public void Rroduction(Player player,Inventory inv) {
        //Inventory inv = Bukkit.createInventory(null, 54, "§z自訂物品 編輯模式");

        ItemStack item;
        ItemMeta meta;

        // 檢查第 45 位置是否有物品
        ItemStack main_item = inv.getItem(45);
        if ( main_item != null && main_item.getType() != Material.AIR) {
            // 有

            // 移除舊數據
            for (int i = 0; i < 45; ++i) {
                item = new ItemStack(Material.AIR);
                inv.setItem(i, item);
            }


            // 取得物品數據
            ItemMeta main_meta = main_item.getItemMeta();
            List<String> lore = main_meta.getLore();
            int i = 0;

            // 取得內容
            if ( lore != null ) {
                item = new ItemStack(Material.PAPER);
                meta = item.getItemMeta();
                int s = lore.size();
                if (s > 45) s = 45;

                for (; i < s; ) {
                    meta.setDisplayName( lore.get(i) );
                    if (s >= 45) {
                        meta.setLore(Arrays.asList(
                                ("§r§f (點擊) 修改"),
                                ("§r§f (Shift+右鍵) 刪除")
                        ));
                    } else if (i < 44) {
                        meta.setLore(Arrays.asList(
                                ("§r§f (點擊) 修改"),
                                ("§r§f (Shift+左鍵) 在此行之後新增一行"),
                                ("§r§f (Shift+右鍵) 刪除")
                        ));
                    } else {
                        meta.setLore(Arrays.asList(
                                ("§r§f (點擊) 修改"),
                                ("§r§f (Shift+右鍵) 刪除")
                        ));
                    }
                    meta.getCustomTagContainer().setCustomTag(lore_number, ItemTagType.INTEGER, i); // 登入key
                    // 寫入資料
                    item.setItemMeta(meta);
                    // 設置完成
                    inv.setItem(i, item);

                    ++i;
                }
            }

            if ( i < 45 ) {
                item = new ItemStack(Material.MAP);
                meta = item.getItemMeta();
                meta.setDisplayName("§e新增一行說明");
                meta.setLore(Arrays.asList(
                        ("§r§f (點擊) 新增")
                ));
                meta.getCustomTagContainer().setCustomTag(lore_number, ItemTagType.INTEGER, -1); // 登入key
                // 寫入資料
                item.setItemMeta(meta);
                // 設置完成
                inv.setItem(i, item);
            }








        } else {
            // 沒有物品
            for (int i = 0; i < 45; ++i) {
                item = new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE);
                meta = item.getItemMeta();
                meta.setDisplayName("§e請先放置物品在左下角的位置");
                // 寫入資料
                item.setItemMeta(meta);
                // 設置完成
                inv.setItem(i, item);
            }
        }


        //if (main_item != null) inv.setItem(45, main_item);

        ////////////////////////////////////////////////////////////////////////////////////////////
        item = new ItemStack(Material.ANVIL);
        meta = item.getItemMeta();
        meta.setDisplayName("§e更新頁面");
        meta.setLore(Arrays.asList(
                ("§r§f (點擊) 更新頁面"),
                ("§r§f - 放好物品後點擊")
        ));
        // 寫入資料
        item.setItemMeta(meta);
        // 設置完成
        inv.setItem(46, item);





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
            Inventory inv = event.getClickedInventory();

            // 檢查容器名稱
            if (event.getView().getTitle().equalsIgnoreCase("§z自訂物品 編輯模式")) {
                // 是沒錯


                if (event.getRawSlot() >= 0 && event.getRawSlot() < 54 && event.getRawSlot() != 45)
                    event.setCancelled(true);

                if (event.getRawSlot() >= 0 && event.getRawSlot() <= 44) {
                    // 編輯說明
                    if (inv != null) {

                        // 檢查第 45 位置是否有物品
                        ItemStack item = inv.getItem(45);
                        if (item == null) return;
                        if (item.getType() == Material.AIR) return;
                        if (item.getItemMeta() == null) return;
                        ItemMeta meta = item.getItemMeta();

                        List<String> lore = meta.getLore();
                        if (lore == null) lore = new ArrayList<String>();

                        // 有
                        if (event.getCurrentItem() == null) return;
                        if (event.getCurrentItem().getItemMeta() == null) return;
                        if (!event.getCurrentItem().getItemMeta().getCustomTagContainer().hasCustomTag(lore_number, ItemTagType.INTEGER)) return;

                        // 有數據資料
                        int number = event.getCurrentItem().getItemMeta().getCustomTagContainer().getCustomTag(lore_number, ItemTagType.INTEGER);


                        if (number == -1) {
                            // 新增說明
                            if (lore.size() >= 45) {
                                player.sendMessage("[自訂物品]  §c說明行數已經過多");
                                return;
                            }

                            player.closeInventory();
                            player.sendMessage("[自訂物品]  §a請輸入內容  支持使用&顏色代碼 最多60字");

                            List<String> finalLore1 = lore;

                            SuperFreedomSurvive.SYSTEM.Input.setRegistry(player,
                                    new BukkitRunnable() {
                                        int time = 1200;
                                        String message = null;


                                        @Override
                                        final public void run() {
                                            if (time < 0) {
                                                player.sendMessage("[自訂物品]  §c等待輸入太久了 自動取消");
                                                Rroduction(player, inv);
                                                cancel();
                                                return;
                                            }
                                            message = SuperFreedomSurvive.SYSTEM.Input.getMessage(player);

                                            if (message != null) {
                                                // 有資料

                                                message = SuperFreedomSurvive.Function.ColorCodeTransform.Conversion(message);

                                                if (message.length() > 60) {
                                                    message = message.substring(0, 60);
                                                }

                                                finalLore1.add("§r§f" + message);
                                                player.sendMessage("[自訂物品]  新增成功");

                                                meta.setLore(finalLore1);
                                                item.setItemMeta(meta);
                                                inv.setItem(45, item);
                                                Rroduction(player, inv);

                                                cancel();
                                                return;
                                            }
                                            --time;
                                        }
                                    }.runTaskTimer(SuperFreedomSurvive.SYSTEM.Plugin.get(), 0L, 1L)
                            );

                        } else {
                            if (event.isShiftClick() && event.isLeftClick()) {
                                // 插入一行
                                if (number == 44) return;
                                if (lore.size() >= 45) {
                                    player.sendMessage("[自訂物品]  §c說明行數已經過多");
                                    return;
                                }

                                player.closeInventory();
                                player.sendMessage("[自訂物品]  §a請輸入內容  支持使用&顏色代碼 最多60字");


                                SuperFreedomSurvive.SYSTEM.Input.setRegistry(player,
                                        new BukkitRunnable() {
                                            int time = 1200;
                                            String message = null;

                                            List<String> lore = meta.getLore();

                                            @Override
                                            final public void run() {
                                                if (time < 0) {
                                                    player.sendMessage("[自訂物品]  §c等待輸入太久了 自動取消");
                                                    Rroduction(player, inv);
                                                    cancel();
                                                    return;
                                                }
                                                message = SuperFreedomSurvive.SYSTEM.Input.getMessage(player);

                                                if (message != null) {
                                                    // 有資料

                                                    message = SuperFreedomSurvive.Function.ColorCodeTransform.Conversion(message);

                                                    if (message.length() > 60) {
                                                        message = message.substring(0, 60);
                                                    }

                                                    if (lore.size() >= 45) return;
                                                    lore.add(number + 1, "§r§f" + message);
                                                    player.sendMessage("[自訂物品]  新增成功");

                                                    meta.setLore(lore);
                                                    item.setItemMeta(meta);
                                                    inv.setItem(45, item);
                                                    Rroduction(player, inv);

                                                    cancel();
                                                    return;
                                                }
                                                --time;
                                            }
                                        }.runTaskTimer(SuperFreedomSurvive.SYSTEM.Plugin.get(), 0L, 1L)
                                );

                            } else if (event.isShiftClick() && event.isRightClick()) {
                                // 刪除此行

                                player.closeInventory();
                                lore.remove(number);
                                player.sendMessage("[自訂物品]  刪除成功");

                                meta.setLore(lore);
                                item.setItemMeta(meta);
                                inv.setItem(45, item);
                                Rroduction(player, inv);

                            } else {
                                // 修改

                                player.closeInventory();
                                player.sendMessage("[自訂物品]  §a請輸入內容  支持使用&顏色代碼 最多60字");

                                List<String> finalLore = lore;

                                SuperFreedomSurvive.SYSTEM.Input.setRegistry(player,
                                        new BukkitRunnable() {
                                            int time = 1200;
                                            String message = null;

                                            @Override
                                            final public void run() {
                                                if (time < 0) {
                                                    player.sendMessage("[自訂物品]  §c等待輸入太久了 自動取消");
                                                    Rroduction(player, inv);
                                                    cancel();
                                                    return;
                                                }
                                                message = SuperFreedomSurvive.SYSTEM.Input.getMessage(player);

                                                if (message != null) {
                                                    // 有資料

                                                    message = SuperFreedomSurvive.Function.ColorCodeTransform.Conversion(message);

                                                    if (message.length() > 60) {
                                                        message = message.substring(0, 60);
                                                    }

                                                    finalLore.set(number, "§r§f" + message);
                                                    player.sendMessage("[自訂物品]  編輯成功");

                                                    meta.setLore(finalLore);
                                                    item.setItemMeta(meta);
                                                    inv.setItem(45, item);
                                                    Rroduction(player, inv);

                                                    cancel();
                                                    return;
                                                }
                                                --time;
                                            }
                                        }.runTaskTimer(SuperFreedomSurvive.SYSTEM.Plugin.get(), 0L, 1L)
                                );
                            }
                        }
                    }

                } else if (event.getRawSlot() == 45) {
                    // 放置要編輯的物品

                } else if (event.getRawSlot() == 46) {
                    // 更新頁面
                    player.closeInventory();

                    if (inv != null) {
                        Rroduction(player, inv);
                    }

                } else if (event.getRawSlot() == 52) {
                    // 上一頁
                    player.closeInventory();

                    // 檢查第 45 位置是否有物品
                    ItemStack item = event.getInventory().getItem(45);
                    if (item == null) {
                        menu(player);
                        return;
                    }
                    if (item.getType() == Material.AIR) {
                        menu(player);
                        return;
                    }

                    player.getWorld().dropItem(player.getLocation(), item);

                    menu(player);

                } else if (event.getRawSlot() == 53) {
                    // 關閉
                    player.closeInventory();

                    // 檢查第 45 位置是否有物品
                    ItemStack item = event.getInventory().getItem(45);
                    if (item == null) return;
                    if (item.getType() == Material.AIR) return;

                    player.getWorld().dropItem(player.getLocation(), item);


                    event.getWhoClicked().closeInventory();

                }
            }
        }
    }
    @EventHandler
    final public void onInventoryDragEvent(InventoryDragEvent event) {
        if (event.getView().getTitle().equalsIgnoreCase("§z自訂物品 編輯模式")) {
            event.setCancelled(true);
        }
    }
    // 關閉庫存事件
    @EventHandler
    final public void onInventoryCloseEvent(InventoryCloseEvent event) {

        Player player = (Player) event.getPlayer();
        if (event.getView().getTitle().equalsIgnoreCase("§z自訂物品 編輯模式")) {
            if (event.getReason() == InventoryCloseEvent.Reason.PLUGIN) {
            } else if (event.getReason() == InventoryCloseEvent.Reason.UNKNOWN) {
            } else {
                // 檢查第 45 位置是否有物品
                ItemStack item = event.getInventory().getItem(45);
                if (item == null) return;
                if (item.getType() == Material.AIR) return;

                player.getWorld().dropItem(player.getLocation(), item);

            }
        }
    }
}
