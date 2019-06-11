package SuperFreedomSurvive.Menu;

import SuperFreedomSurvive.SYSTEM.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;

final public class Open implements Listener {
    // 打開介面條件
    // 全部事件都要存在 玩家切換主手/副手武器時 + 玩家潛行狀態

    @EventHandler
    final public void onPlayerSwapHandItemsEvent(PlayerSwapHandItemsEvent event) {
        //  玩家切換主手/副手武器時
        if (event.getPlayer().isSneaking()) {
            // 潛行狀態

            event.setCancelled(true);

            open(event.getPlayer());


        }
    }


    // 打開介面條件
    // 玩家拿時鐘點擊右鍵時
    @EventHandler
    final public void onPlayerInteractEvent(PlayerInteractEvent event) {
        try {

            if (event.getItem() == null) { return; }

            //if ( event.getAction().equals( Action.RIGHT_CLICK_AIR ) || event.getAction().equals( Action.RIGHT_CLICK_BLOCK ) ) {
            // 檢查是否為右手
            if (event.getAction().name() == "RIGHT_CLICK_BLOCK" || event.getAction().name() == "RIGHT_CLICK_AIR") {
                // 檢查是否為時鐘
                if (event.getItem().getType().name().equals(Material.CLOCK.name())) {
                    // 打開選單
                    open(event.getPlayer());


                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public static void open(Player player) {
        // 顯示容器
        try {


            Inventory inv = Bukkit.createInventory(null, 27, "§z功能選單");

            ItemStack item;
            ItemMeta meta;


            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            // --------------------------------------     0     --------------------------------------
            item = new ItemStack(Material.DARK_OAK_DOOR, 1);
            meta = item.getItemMeta();
            meta.setDisplayName("§e領地系統");
            meta.setLore(Arrays.asList(
                    ("§r§f - 創建領地"),
                    ("§r§f - 創建子領地"),
                    ("§r§f - 延伸領地"),
                    ("§r§f - 查看當前領地"),
                    ("§r§f - 設定領地權限"),
                    ("§r§f - 新增/刪除共用人"),
                    ("§r§f - 轉移使用權"),
                    ("§r§f - 刪除領地")
            ));
            // 寫入資料
            item.setItemMeta(meta);
            // 設置完成
            inv.setItem(0, item);


            // --------------------------------------     1     --------------------------------------
            item = new ItemStack(Material.ENDER_PEARL);
            meta = item.getItemMeta();
            meta.setDisplayName("§e傳送系統");
            meta.setLore(Arrays.asList(
                    ("§r§f - 傳送玩家")
            ));
            // 寫入資料
            item.setItemMeta(meta);
            // 設置完成
            inv.setItem(1, item);


            // --------------------------------------     2     --------------------------------------
            item = new ItemStack(Material.ENDER_EYE);
            meta = item.getItemMeta();
            meta.setDisplayName("§e多世界系統");
            meta.setLore(Arrays.asList(
                    ("§r§f - 傳送到其他世界"),
                    ("§r§f - 購買個人世界"),
                    ("§r§f - 前往個人世界")
            ));
            // 寫入資料
            item.setItemMeta(meta);
            // 設置完成
            inv.setItem(2, item);


            // --------------------------------------     3     --------------------------------------
            item = new ItemStack(Material.LIGHT_GRAY_BANNER);
            meta = item.getItemMeta();
            meta.setDisplayName("§e定點系統");
            meta.setLore(Arrays.asList(
                    ("§r§f - 傳送到定點的座標"),
                    ("§r§f - 查看公開定點"),
                    ("§r§f - 新增定點"),
                    ("§r§f - 刪除定點")
            ));
            // 寫入資料
            item.setItemMeta(meta);
            // 設置完成
            inv.setItem(3, item);


            // --------------------------------------     4     --------------------------------------
            item = new ItemStack(Material.TURTLE_EGG);
            meta = item.getItemMeta();
            meta.setDisplayName("§e禮包系統");
            try {
                Connection con = MySQL.getConnection(); // 連線 MySQL
                Statement sta = con.createStatement(); // 取得控制庫
                ResultSet res = sta.executeQuery("SELECT * FROM `player-kit_data` WHERE `Player` = '" + player.getName() + "' ORDER BY `Start_Time` LIMIT 1");
                // 跳到最後一行
                res.last();
                // 最後一行 行數 是否 > 0
                if (res.getRow() > 0) {
                    // 有資料

                    res.close(); // 關閉查詢

                    meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    meta.addEnchant(Enchantment.BINDING_CURSE, 1, false);
                    meta.setLore(Arrays.asList(
                            ("§r§f 你有未領的禮包"),
                            ("§r§f - 查看禮包"),
                            ("§r§f - 使用禮包")
                    ));

                } else {
                    meta.setLore(Arrays.asList(
                            ("§r§f - 查看禮包"),
                            ("§r§f - 使用禮包")
                    ));

                }

                res.close(); // 關閉查詢
                sta.close(); // 關閉連線

            } catch (Exception ex) {
                ex.printStackTrace();
            }
            // 寫入資料
            item.setItemMeta(meta);
            // 設置完成
            inv.setItem(4, item);


            // --------------------------------------     5     --------------------------------------
            item = new ItemStack(Material.BREWING_STAND);
            meta = item.getItemMeta();
            meta.setDisplayName("§e補助系統");
            meta.setLore(Arrays.asList(
                    ("§r§f - 使用綠寶石購買特殊效果")
            ));
            // 寫入資料
            item.setItemMeta(meta);
            // 設置完成
            inv.setItem(5, item);


            // --------------------------------------     6     --------------------------------------
            // 使用UUID 設置為頭顱新的主人
            // 設置完成
            inv.setItem(6, SuperFreedomSurvive.Block.Skull.Get("c6257b24-1c09-4824-b20c-caa7bb99abb3", "§e購買裝飾頭顱", new String[]{"§r§f - 購買各式各樣的頭顱"}));


            // --------------------------------------     7     --------------------------------------
            item = new ItemStack(Material.SIGN);
            meta = item.getItemMeta();
            meta.setDisplayName("§e指令告示牌系統");
            meta.setLore(Arrays.asList(
                    ("§r§f - 查看全部的指令告示牌說明")
            ));
            // 寫入資料
            item.setItemMeta(meta);
            // 設置完成
            inv.setItem(7, item);


            // --------------------------------------     8     --------------------------------------
            item = new ItemStack(Material.CRAFTING_TABLE);
            meta = item.getItemMeta();
            meta.setDisplayName("§e合成系統");
            meta.setLore(Arrays.asList(
                    ("§r§f - 查看全部可合成的特殊物品")
            ));
            // 寫入資料
            item.setItemMeta(meta);
            // 設置完成
            inv.setItem(8, item);


            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            // --------------------------------------     9     --------------------------------------
            item = new ItemStack(Material.WRITABLE_BOOK);
            meta = item.getItemMeta();
            meta.setDisplayName("§e聊天室");
            meta.setLore(Arrays.asList(
                    ("§r§f - 創建聊天室"),
                    ("§r§f - 查看成員"),
                    ("§r§f - 邀請玩家"),
                    ("§r§f - 離開聊天室")
            ));
            // 寫入資料
            item.setItemMeta(meta);
            // 設置完成
            inv.setItem(9, item);


            // --------------------------------------     10     --------------------------------------
            item = new ItemStack(Material.ITEM_FRAME);
            meta = item.getItemMeta();
            meta.setDisplayName("§e自訂物品系統(未完成)");
            meta.setLore(Arrays.asList(
                    ("§r§f - 編輯物品說明"),
                    ("§r§f - 複製物品說明")
            ));
            // 寫入資料
            item.setItemMeta(meta);
            // 設置完成
            inv.setItem(10, item);




            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            // --------------------------------------     22     --------------------------------------
            item = new ItemStack(Material.PAPER);
            item = new ItemStack(Material.PLAYER_HEAD);
            meta = item.getItemMeta();
            meta.setDisplayName("§r§a個人資料");
            //ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1)
            meta.setLore(Arrays.asList(
                    ("§r§f - 查看自己的一些資料"),
                    ("§r§f - 設置一些功能")
            ));
            // 寫入資料
            item.setItemMeta(meta);
            SkullMeta skull_meta = (SkullMeta) item.getItemMeta();
            // 取得玩家名稱 並獲取UUID 設置為頭顱新的主人
            skull_meta.setOwningPlayer(player);
            item.setItemMeta(skull_meta);
            // 設置完成
            inv.setItem(22, item);


            // --------------------------------------     23     --------------------------------------
            item = new ItemStack(Material.PAPER);
            meta = item.getItemMeta();
            meta.setDisplayName("§e伺服器功能一覽/介紹");
            meta.setLore(Arrays.asList(
                    ("§r§f - 查看目前擁有的所有大/小功能"),
                    ("§r§f - 使用方式/簡單的說明")
            ));
            // 寫入資料
            item.setItemMeta(meta);
            // 設置完成
            inv.setItem(23, item);


            // --------------------------------------     24     --------------------------------------
            item = new ItemStack(Material.SUNFLOWER);
            meta = item.getItemMeta();
            meta.setDisplayName("§e贊助!");
            meta.setLore(Arrays.asList(
                    ("§r§f (點擊) 顯示網址"),
                    ("§r§f - 喜歡本伺服器及獨特的插件?"),
                    ("§r§f - 不妨試試這個")
            ));
            // 寫入資料
            item.setItemMeta(meta);
            // 設置完成
            inv.setItem(24, item);


            // --------------------------------------     26     --------------------------------------
            inv.setItem(26, SuperFreedomSurvive.Menu.Open.TurnOff());



/*

        // --------------------------------------     9     --------------------------------------
        item = new ItemStack ( Material.LEAD );
        meta = item.getItemMeta();
        meta.setDisplayName("§e羈絆系統(同時開發中)");
        meta.setLore( Arrays.asList(
                ("§r§f - 設定屬於自己的羈絆")
        ));
        // 寫入資料
        item.setItemMeta(meta);
        // 設置完成
        inv.setItem(9 , item );



        // --------------------------------------     10     --------------------------------------
        item = new ItemStack ( Material.DIAMOND_SWORD );
        meta = item.getItemMeta();
        meta.setDisplayName("§e武器系統(同時開發中)");
        meta.setLore( Arrays.asList(
                ("§r§f - 合成一覽"),
                ("§r§f - 強化"),
                ("§r§f - 進階"),
                ("§r§f - 修復"),
                ("§r§f - 附魔")
        ));
        // 寫入資料
        item.setItemMeta(meta);
        // 設置完成
        inv.setItem(10 , item );




        // --------------------------------------     11     --------------------------------------
        item = new ItemStack ( Material.ZOMBIE_SPAWN_EGG );
        meta = item.getItemMeta();
        meta.setDisplayName("§e怪物系統(取消 使用特殊生怪磚代替)");
        meta.setLore( Arrays.asList(
                ("§r§f - 怪物一覽"),
                ("§r§f - 製作怪物")
        ));
        // 寫入資料
        item.setItemMeta(meta);
        // 設置完成
        inv.setItem(11 , item );



        // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------








        PotionMeta potion = (PotionMeta)item.getItemMeta();
        potion.setColor(Color.fromRGB( 0 , 205 , 255));
        potion.setDisplayName("§e藥水效果");
        potion.setLore( Arrays.asList(
                ("§r§f - 藥水效果")
        ));
        // 寫入資料
        item.setItemMeta(potion);
        */


            // 寫入到容器頁面
            player.openInventory(inv);


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
            if (event.getView().getTitle().equalsIgnoreCase("§z功能選單")) {
                // 是沒錯

                event.setCancelled(true);

                // 檢查點擊的是第幾個位置
                switch (event.getRawSlot()) {
                    case 0:
                        // 領地系統
                        SuperFreedomSurvive.Menu.Function.Land.menu(player);
                        break;

                    case 1:
                        // 傳送系統
                        SuperFreedomSurvive.Menu.Function.PlayerTransfer.menu(player);
                        break;

                    case 2:
                        // 多世界系統
                        SuperFreedomSurvive.Menu.Function.World.menu(player);
                        break;

                    case 3:
                        // 座標系統
                        SuperFreedomSurvive.Menu.Function.Warp.menu(player);
                        break;

                    case 4:
                        // 禮包系統
                        SuperFreedomSurvive.Menu.Function.Kit.menu(player);
                        break;

                    case 5:
                        // 補助系統
                        SuperFreedomSurvive.Menu.Function.Additional.menu(player);
                        break;

                    case 6:
                        // 購買頭顱
                        SuperFreedomSurvive.Menu.Function.Skull.menu(player);
                        break;

                    case 7:
                        // 指令告示牌系統
                        SuperFreedomSurvive.Menu.Function.Sign.menu(player);
                        break;

                    case 8:
                        // 合成系統
                        SuperFreedomSurvive.Menu.Function.Composite.menu(player);
                        break;


                    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                    case 9:
                        // 聊天室系統
                        SuperFreedomSurvive.Menu.Function.Chatroom.menu(player);
                        break;

                    case 10:
                        // 自訂物品系統
                        SuperFreedomSurvive.Menu.Function.Customize.menu(player);
                        break;


                    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                    case 22:
                        // 個人資料
                        SuperFreedomSurvive.Menu.Function.Self.menu(player);
                        break;

                    case 23:
                        // 伺服器功能一覽/介紹
                        SuperFreedomSurvive.Menu.Function.Help.menu(player);
                        break;

                    case 24:
                        // 贊助
                        player.sendMessage("[贊助]  直接使用PayPal贊助 https://www.paypal.me/XUANCat");
                        //player.sendMessage("[購買]  購買LINE貼圖支持 https://line.me/S/sticker/7142059");

                        player.closeInventory();
                        break;

                    case 26:
                        // 關閉
                        event.getWhoClicked().closeInventory();
                        break;

                    default:
                        break;
                }

/*
                } else if (event.getRawSlot() == 9) {
                    // 羈絆系統
                    ServerPlugin.Menu.Function.Pet.menu(player);

                } else if (event.getRawSlot() == 10) {
                    // 武器系統
                    ServerPlugin.Menu.Function.Arms.menu(player);

                } else if (event.getRawSlot() == 11) {
                    // 怪物系統
                    ServerPlugin.Menu.Function.Monster.menu(player);
*/


            }
        }
    }


    // 上一頁
    final public static ItemStack PreviousPage() {
        ItemStack item = new ItemStack(Material.NETHER_STAR);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§r§e返回上一頁");
        // 寫入資料
        item.setItemMeta(meta);
        return item;
    }

    // 關閉
    final public static ItemStack TurnOff() {
        ItemStack item = new ItemStack(Material.END_CRYSTAL);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§r§e關閉選單");
        // 寫入資料
        item.setItemMeta(meta);
        return item;
    }

}
