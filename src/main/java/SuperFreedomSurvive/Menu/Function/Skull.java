package SuperFreedomSurvive.Menu.Function;

import SuperFreedomSurvive.Menu.Open;
import SuperFreedomSurvive.Player.Pay;
import SuperFreedomSurvive.SYSTEM.MySQL;
import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import de.tr7zw.nbtapi.NBTList;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.inventory.meta.tags.ItemTagType;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

final public class Skull implements Listener {


    static final public org.bukkit.NamespacedKey key = SuperFreedomSurvive.SYSTEM.NamespacedKey.getKey("PagesNumber");

    // 購買頭顱
    final public static void menu(Player player) {
        page(player, 0);

    }


    final public static void page(Player player, int pages_number) {

        // 顯示容器
        Inventory inv = Bukkit.createInventory(null, 54, "§z頭顱商店 第" + (pages_number + 1) + "頁");

        ItemStack item;
        ItemMeta meta;
        SkullMeta skull_meta;


        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫


            // 按照頁數取得 45 筆資料
            // 存入內容
            ResultSet res = sta.executeQuery("SELECT * FROM `store_skull-list_data` ORDER BY `Tag`, `Name` LIMIT 45 OFFSET " + 45 * pages_number);

            int total = 0;
            while (res.next()) {
                // 寫入資料

                // ---------------------------------------------------------------------------------------
                item = new ItemStack(Material.PLAYER_HEAD);
                //meta = item.getItemMeta();
                /*
                meta.setDisplayName("§r§a" + res.getString("Name"));
                //ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1)
                */

                NBTItem nbti = new NBTItem(item);

                NBTCompound disp = nbti.addCompound("display");
                disp.setString("Name", res.getString("Name"));
                NBTList<String> ntblist = disp.getStringList("Lore");
                ntblist.add("§r§f - 類型 " + res.getString("Tag"));
                ntblist.add("§r§f (點擊) 購買此頭顱");
                ntblist.add("§r§f - 消耗 " + res.getInt("Price") + " 顆綠寶石");
                ntblist.add("§r§f - 已經被購買 " + res.getInt("Purchases") + " 次");

                NBTCompound skull = nbti.addCompound("SkullOwner");
                skull.setString("Name", res.getString("Name"));
                skull.setString("Id", res.getString("UUID"));

                NBTCompound texture = skull.addCompound("Properties").addCompound("textures");
                texture.setString("Value", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUv" + res.getString("Value"));

                nbti.addCompound("PagesNumber").setInteger("pages", pages_number);

                //player.sendMessage( Bukkit.getServer().getPlayer( player_name ).getUniqueId().toString() );
                // 設置完成
                inv.setItem(total, nbti.getItem());

                ++total;
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
                meta.getCustomTagContainer().setCustomTag(key, ItemTagType.INTEGER, pages_number - 1); // 登入key
                // 寫入資料
                item.setItemMeta(meta);
                // 設置完成
                inv.setItem(45, item);
            }


            res = sta.executeQuery("Select Count(*) FROM `store_skull-list_data`");
            res.next();
            if (res.getInt("Count(*)") > (pages_number + 1) * 45) {
                // --------------------------------------     46     --------------------------------------
                // 下一頁
                item = new ItemStack(Material.LADDER);
                meta = item.getItemMeta();
                meta.setDisplayName("§e下一面");
                meta.setLore(Arrays.asList(
                        ("§r§f (點擊) 前往下一頁頁面")
                ));
                meta.getCustomTagContainer().setCustomTag(key, ItemTagType.INTEGER, pages_number + 1); // 登入key
                // 寫入資料
                item.setItemMeta(meta);
                // 設置完成
                inv.setItem(46, item);
            }


            // --------------------------------------     52     --------------------------------------
            inv.setItem(52, Open.PreviousPage());


            // --------------------------------------     53     --------------------------------------
            inv.setItem(53, Open.TurnOff());



            player.openInventory(inv);


            res.close(); // 關閉查詢
            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
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
            if (event.getView().getTitle().matches("§z頭顱商店\\s第[0-9]{1,10}頁")) {
                // 是沒錯

                event.setCancelled(true);

                // 取得點擊的頭顱
                try {

                    if (event.getRawSlot() < 45) {
                        if (event.getCursor() != null) {
                            // 取得資料
                            Connection con = MySQL.getConnection(); // 連線 MySQL
                            Statement sta = con.createStatement(); // 取得控制庫

                            // 總數
                            int total = 0;

                            // 按照頁數取得 45 筆資料
                            // 存入內容
                            ResultSet res = sta.executeQuery("SELECT * FROM `store_skull-list_data` WHERE `UUID` = '" + new NBTItem(event.getCurrentItem()).getCompound("SkullOwner").getString("Id") + "' LIMIT 1");
                            res.last();
                            // 最後一行 行數 是否 > 0
                            if (res.getRow() > 0) {
                                // 有資料
                                // 跳回 初始行 必須使用 next() 才能取得資料
                                res.beforeFirst();
                                res.next();

                                // 檢查物資是否夠
                                if (Pay.Have(player, res.getInt("Price"))) {
                                    // 足夠 扣除
                                    if (Pay.Recover(player, res.getInt("Price"))) {


                                        // 給予頭顱
                                        // ---------------------------------------------------------------------------------------
                                        ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1);


                                        NBTItem nbti = new NBTItem(item);

                                        NBTCompound skull = nbti.addCompound("SkullOwner");
                                        skull.setString("Name", res.getString("Name"));
                                        skull.setString("Id", res.getString("UUID"));

                                        NBTCompound texture = skull.addCompound("Properties").addCompound("textures");
                                        texture.setString("Value", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUv" + res.getString("Value"));


                                        //player.sendMessage( Bukkit.getServer().getPlayer( player_name ).getUniqueId().toString() );
                                        // 設置完成
                                        player.getInventory().addItem(nbti.getItem());
                                        // ---------------------------------------------------------------------------------------


                                        sta.executeUpdate("UPDATE `store_skull-list_data` SET `Purchases` = `Purchases` + 1 WHERE `UUID` = '" + new NBTItem(event.getCurrentItem()).getCompound("SkullOwner").getString("Id") + "'");

                                        player.sendMessage("[商店系統]  購買成功");

                                        page(player, new NBTItem(event.getCurrentItem()).getCompound("PagesNumber").getInteger("pages"));


                                    }
                                } else {
                                    player.sendMessage("[商店系統]  §c所需物資不夠! 需要 " + res.getInt("Price") + " 顆 綠寶石");
                                }

                            }

                            res.close(); // 關閉查詢
                            sta.close(); // 關閉連線

                        }

                    } else if (event.getRawSlot() == 45) {
                        if (event.getCurrentItem() != null) {
                            if (event.getCurrentItem().getItemMeta() != null) {
                                if (event.getCurrentItem().getItemMeta().getCustomTagContainer().getCustomTag(key, ItemTagType.INTEGER) != null) {
                                    page(player, event.getCurrentItem().getItemMeta().getCustomTagContainer().getCustomTag(key, ItemTagType.INTEGER));
                                }
                            }
                        }

                    } else if (event.getRawSlot() == 46) {
                        if (event.getCurrentItem() != null) {
                            if (event.getCurrentItem().getItemMeta() != null) {
                                if (event.getCurrentItem().getItemMeta().getCustomTagContainer().getCustomTag(key, ItemTagType.INTEGER) != null) {
                                    page(player, event.getCurrentItem().getItemMeta().getCustomTagContainer().getCustomTag(key, ItemTagType.INTEGER));
                                }
                            }
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

    // 是否有足夠的數量
    static final public boolean Enough_Emerald(Player player, int need) {
        // 檢查玩家身上物資夠不夠
        int emerald_amount = 0;
        PlayerInventory inventory = player.getInventory();
        HashMap all = inventory.all(Material.EMERALD);
        for (Object key : all.keySet()) {
            emerald_amount = emerald_amount + ((ItemStack) all.get(key)).getAmount();
        }
        // 足夠
// 不足
        return emerald_amount >= need;
    }

    // 收取綠寶石
    static final public void Charge_Emerald(Player player, int need) {
        // 一次 1 個
        ItemStack item = new ItemStack(Material.EMERALD, 1);
        for (; need > 0; ) {
            player.getInventory().removeItem(item);
            need = need - 1;
        }
    }




















/*
    static final public void Run ( Player player , Inventory inventory , ArrayList<String> name , ArrayList<String> price , ArrayList<String> purchases , ArrayList<String> uuid , int pages_number ) {


        player.openInventory(inventory);

        new BukkitRunnable() {
            @Override
            final public void run () {

            ItemStack item;
            ItemMeta meta;
            SkullMeta skull_meta;
            ResultSet res;
            long spend;
            long last_time = new Date().getTime();

                // 是
                for (int total = 0; total < name.size(); total++) {

                    new BukkitRunnable() {
                        @Override
                        final public void run () {

                    // ---------------------------------------------------------------------------------------
                    item = new ItemStack(Material.PLAYER_HEAD);
                    meta = item.getItemMeta();
                    meta.setDisplayName("§r§a" + name.get(total));
                    //ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1)
                    meta.setLore(Arrays.asList(
                            ("§r§f (點擊) 購買此頭顱"),
                            ("§r§f - 消耗 " + price.get(total) + " 顆綠寶石"),
                            ("§r§f - 已經被購買 " + purchases.get(total) + " 次")
                    ));
                    meta.getCustomTagContainer().setCustomTag(key, ItemTagType.INTEGER, pages_number); // 登入key

                    /*
                    // 最花費時間的處理
                    if (uuid.get(total) == null) {
                        skull_meta.setOwningPlayer(Bukkit.getOfflinePlayer(name.get(total)));
                    } else {
                        skull_meta.setOwningPlayer(Bukkit.getServer().getOfflinePlayer(UUID.fromString(uuid.get(total))));
                    }.



                    item.setItemMeta(meta);

                    NBTItem nbti = new NBTItem(item);
                    nbti.setString("SkullOwner",name.get(total));

                    //player.sendMessage( Bukkit.getServer().getPlayer( player_name ).getUniqueId().toString() );
                    // 設置完成
                    inventory.setItem(total, nbti.getItem());

                    //player.sendMessage(""+finalTotal+":"+name.get(finalTotal));

                            /*
                            //if ((total + 1) % tip == 0) {
                            // 計算花費時間
                            spend = (new Date().getTime()) - last_time;
                            if (spend > 2000) {
                                //player.closeInventory();
                                //player.openInventory(inventory);
                                player.sendTitle(
                                        "",
                                        "正在緩存資料中 " + finalTotal + "/" + name.size(),
                                        0,
                                        60,
                                        0
                                );
                                last_time = new Date().getTime();
                                new BukkitRunnable() {
                                    @Override
                                    final public void run() {
                                        player.openInventory(inventory);
                                    }
                                }.runTask(ServerPlugin.SYSTEM.Plugin.get());
                            }


                }



                    new BukkitRunnable() {
                        @Override
                        final public void run() {
                            player.openInventory(inventory);
                        }
                    }.runTask(ServerPlugin.SYSTEM.Plugin.get());

                    cancel();
                    return;

                }
        }.runTaskAsynchronously(ServerPlugin.SYSTEM.Plugin.get());
    }
    */
}

