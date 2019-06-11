package SuperFreedomSurvive.Menu.Function;

import SuperFreedomSurvive.Menu.Open;
import SuperFreedomSurvive.Player.Pay;
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
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;

public class Self implements Listener {
    // 個人資料

    // 選單接口
    final public static void menu(Player player) {
        // 顯示容器
        Inventory inv = Bukkit.createInventory(null, 54, "§z個人資料");

        ItemStack item;
        ItemMeta meta;

        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫
            ResultSet res;



            // ------------------------------------------------------------------------------------
            item = new ItemStack(Material.PLAYER_HEAD);
            meta = item.getItemMeta();
            meta.setDisplayName("§r§a頭顱");
            //meta.setDisplayName("§r§a" + player_.getName() + "()");
            //ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1)
            meta.setLore(Arrays.asList(
                    ("§r§f (點擊) 購買自己的頭顱"),
                    ("§r§f - 消耗 6 顆綠寶石")
            ));

            // 寫入資料
            item.setItemMeta(meta);

            SkullMeta skull = (SkullMeta) item.getItemMeta();
            // 取得玩家名稱 並獲取UUID 設置為頭顱新的主人
            skull.setOwningPlayer(player);
            item.setItemMeta(skull);
            //player.sendMessage( Bukkit.getServer().getPlayer( player_name ).getUniqueId().toString() );
            // 設置完成
            inv.setItem(0, item);


            // ------------------------------------------------------------------------------------
            res = sta.executeQuery("SELECT * FROM `player-spawn-location_data` WHERE `Player` = '" + player.getName() + "'");
            // 跳到最後一行
            res.last();
            // 最後一行 行數 是否 > 0
            if (res.getRow() > 0) {
                // 有資料
                // 跳回 初始行 必須使用 next() 才能取得資料
                res.beforeFirst();
                res.next();

                item = new ItemStack(Material.RED_BED);
                meta = item.getItemMeta();
                meta.setDisplayName("§e重生點");
                meta.setLore(Arrays.asList(
                        ("§r§f 世界 " + res.getString("World")),
                        ("§r§f X " + res.getInt("X")),
                        ("§r§f Y " + res.getInt("Y")),
                        ("§r§f Z " + res.getInt("Z")),
                        ("§r§f 時間 " + res.getString("Time"))
                ));
            } else {
                item = new ItemStack(Material.WHITE_BED);
                meta = item.getItemMeta();
                meta.setDisplayName("§e重生點");
                meta.setLore(Arrays.asList(
                        ("§r§f 未設置")
                ));
            }
            // 寫入資料
            item.setItemMeta(meta);
            // 設置完成
            inv.setItem(1, item);
            res.close(); // 關閉查詢




            // ------------------------------------------------------------------------------------
            // 擁有的經驗值
            item = new ItemStack(Material.EXPERIENCE_BOTTLE);
            meta = item.getItemMeta();

            int exp = 0;
            int level = player.getLevel();

            if (level <= 16) {
                exp += (int) (Math.pow(level, 2) + 6 * level);
            } else if (level <= 31) {
                exp += (int) (2.5 * Math.pow(level, 2) - 40.5 * level + 360.0);
            } else {
                exp += (int) (4.5 * Math.pow(level, 2) - 162.5 * level + 2220.0);
            }

            if (level <= 15) {
                exp += Math.round((2 * level + 7) * player.getExp());
            } else if (level <= 30) {
                exp += Math.round((5 * level - 38) * player.getExp());
            } else {
                exp += Math.round((9 * level - 158) * player.getExp());
            }

            meta.setDisplayName("§e經驗值");
            meta.setLore(Arrays.asList(
                    ("§r§f " + exp + " EXP" )
            ));
            // 寫入資料
            item.setItemMeta(meta);
            // 設置完成
            inv.setItem(2, item);



            // ------------------------------------------------------------------------------------
            res = sta.executeQuery("SELECT * FROM `player_data` WHERE `Player` = '" + player.getName() + "'");
            // 跳到最後一行
            res.last();
            // 最後一行 行數 是否 > 0
            if (res.getRow() > 0) {
                // 有資料
                // 跳回 初始行 必須使用 next() 才能取得資料
                res.beforeFirst();
                res.next();

                ///////////////////////////////////////////////////////
                item = new ItemStack(Material.FISHING_ROD);
                meta = item.getItemMeta();
                meta.setDisplayName("§e首次登入伺服器時間");
                meta.setLore(Arrays.asList(
                        ("§r§f " + res.getString("Login_First_Time"))
                ));
                // 寫入資料
                item.setItemMeta(meta);
                // 設置完成
                inv.setItem(3, item);

            } else {
                return;
            }
            res.close(); // 關閉查詢


            // ------------------------------------------------------------------------------------
            {
                item = new ItemStack(Material.NAME_TAG);
                meta = item.getItemMeta();
                meta.setDisplayName("§e顯示名稱");
                String nick = SuperFreedomSurvive.Player.Data.getNick(player.getName());
                if (nick == null) {
                    meta.setLore(Arrays.asList(
                            ("§r§f   -- 無自訂 --"),
                            ("§r§f (點擊) 修改"),
                            ("§r§f - 消耗 32 顆綠寶石 + 12 個命名牌"),
                            ("§r§f (Shift+右鍵) 移除")
                    ));
                } else {
                    meta.setLore(Arrays.asList(
                            ("§r§f   " + nick + "§f"),
                            ("§r§f (左鍵點擊) 修改"),
                            ("§r§f - 消耗 32 顆綠寶石 + 12 個命名牌"),
                            ("§r§f (Shift+右鍵) 移除")
                    ));
                }
                // 寫入資料
                item.setItemMeta(meta);
                // 設置完成
                inv.setItem(4, item);
            }


            // ------------------------------------------------------------------------------------
            {
                item = new ItemStack(Material.ENDER_EYE);
                meta = item.getItemMeta();
                meta.setDisplayName("§e視野距離");
                meta.setLore(Arrays.asList(
                        ("§r§f   " + SuperFreedomSurvive.Player.Data.getViewDistance(player.getName())),
                        ("§r§f (點擊) 修改")
                ));
                // 寫入資料
                item.setItemMeta(meta);
                // 設置完成
                inv.setItem(5, item);
            }







            res.close(); // 關閉查詢
            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // --------------------------------------     52     --------------------------------------
        inv.setItem(52, Open.PreviousPage());

        // --------------------------------------     53     --------------------------------------
        inv.setItem(53, Open.TurnOff());


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
            if (event.getView().getTitle().equalsIgnoreCase("§z個人資料")) {
                // 是沒錯

                event.setCancelled(true);


                switch (event.getRawSlot()) {
                    case 0:
                        // 購買頭顱

                        // 綠寶石是否足夠
                        if (Pay.Have(player, 6)) {
                            // 足夠
                            // 收取綠寶石
                            if (Pay.Recover(player, 6)) {
                                ItemStack item = new ItemStack(Material.PLAYER_HEAD);
                                SkullMeta skull = (SkullMeta) item.getItemMeta();
                                skull.setOwningPlayer(player);
                                item.setItemMeta(skull);
                                // 設置完成
                                player.getInventory().addItem(item); // 給予玩家

                            }
                        } else {
                            player.sendMessage("[購買頭顱]  §c所需物資不夠! 需要 6 顆綠寶石");
                        }
                        break;

                    case 4:
                        // 顯示名稱
                        if (event.isShiftClick() && event.isRightClick()) {
                            // shift + 右鍵點擊
                            // 刪除
                            SuperFreedomSurvive.Player.Data.setNick(player.getName(), null);
                            player.sendMessage("[自訂名稱]  移除成功");

                            player.setDisplayName(player.getName());
                            player.setPlayerListName(player.getName());
                            player.setCustomName(player.getName());

                            menu(player);

                        } else {
                            // 修改顯示名稱
                            player.closeInventory();
                            player.sendMessage("[自訂名稱]  §a請輸入要顯示的名稱 支持使用&顏色代碼 最多26字");

                            SuperFreedomSurvive.SYSTEM.Input.setRegistry(player,
                                    new BukkitRunnable() {
                                        int time = 1200;
                                        String message = null;

                                        @Override
                                        final public void run() {
                                            if (time < 0) {
                                                player.sendMessage("[自訂名稱]  §c等待輸入太久了 自動取消");
                                                cancel();
                                                return;
                                            }
                                            message = SuperFreedomSurvive.SYSTEM.Input.getMessage(player);

                                            if (message != null) {
                                                // 有資料

                                                message = SuperFreedomSurvive.Function.ColorCodeTransform.Conversion(message);

                                                if (message.length() > 26) {
                                                    message = message.substring(0, 26);
                                                }

                                                // 綠寶石是否足夠
                                                // 是否足夠
                                                if (Pay.Have(player, 32) && Pay.Have(player, 12, Material.NAME_TAG)) {
                                                    // 足夠
                                                    // 收取綠寶石
                                                    if (Pay.Recover(player, 32) && Pay.Recover(player, 12, Material.NAME_TAG)) {

                                                        SuperFreedomSurvive.Player.Data.setNick(player.getName(), message);
                                                        player.sendMessage("[自訂名稱]  設置成功");

                                                        player.setDisplayName(message + "§r§f");
                                                        player.setPlayerListName(message);
                                                        player.setCustomName(message);

                                                        menu(player);
                                                    }

                                                } else {
                                                    player.sendMessage("[自訂名稱]  §c所需物資不夠! 需要 32 顆綠寶石 + 12 個命名牌");
                                                    menu(player);
                                                }

                                                cancel();
                                                return;
                                            }
                                            --time;
                                        }
                                    }.runTaskTimer(SuperFreedomSurvive.SYSTEM.Plugin.get(), 0L, 1L)
                            );
                        }
                        break;

                    case 5:
                        // 修改視野距離
                        player.closeInventory();
                        player.sendMessage("[視野距離]  §a請輸入要更改的視野距離  範圍 1 ~ 8");

                        SuperFreedomSurvive.SYSTEM.Input.setRegistry(player,
                                new BukkitRunnable() {
                                    int time = 1200;
                                    String message = null;

                                    @Override
                                    final public void run() {
                                        if (time < 0) {
                                            player.sendMessage("[視野距離]  §c等待輸入太久了 自動取消");
                                            cancel();
                                            return;
                                        }
                                        message = SuperFreedomSurvive.SYSTEM.Input.getMessage(player);

                                        if (message != null) {
                                            // 有資料

                                            if (message.matches("[0-9]{1,9}")) {

                                                int V = Integer.parseInt(message);

                                                if (V >= 1 && V <= 8) {

                                                    SuperFreedomSurvive.Player.Data.setViewDistance(player.getName(), V);
                                                    player.sendMessage("[視野距離]  設置成功");

                                                    player.setViewDistance(V); // 設置視野距離

                                                    menu(player);

                                                } else {
                                                    player.sendMessage("[視野距離]  §c數值必須 1 ~ 8");
                                                    menu(player);
                                                }
                                            } else {
                                                player.sendMessage("[視野距離]  §c只能是整數數字/超出範圍");
                                                menu(player);
                                            }
                                            cancel();
                                            return;
                                        }
                                        --time;
                                    }
                                }.runTaskTimer(SuperFreedomSurvive.SYSTEM.Plugin.get(), 0L, 1L)
                        );
                        break;

                    case 52:
                        // 上一頁
                        SuperFreedomSurvive.Menu.Open.open(player);
                        break;

                    case 53:
                        // 關閉
                        event.getWhoClicked().closeInventory();
                        break;

                    default:
                        break;

                }
            }
        }
    }
}
