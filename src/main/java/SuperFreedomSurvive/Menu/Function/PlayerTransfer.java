package SuperFreedomSurvive.Menu.Function;

import SuperFreedomSurvive.Player.Data;
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
import org.bukkit.inventory.meta.tags.ItemTagType;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

final public class PlayerTransfer implements Listener {

    private static org.bukkit.NamespacedKey player_key = SuperFreedomSurvive.SYSTEM.NamespacedKey.getKey("Player");

    // 選單接口
    public static void menu(Player player) {
        // 顯示容器
        Inventory inv = Bukkit.createInventory(null, 54, "§z傳送系統");


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
                meta.setDisplayName("§r§f" + Data.getNick(player_.getName()));
                //meta.setDisplayName("§r§a" + player_.getName() + "()");
                //ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1)
                meta.setLore(Arrays.asList(
                        ("§r§f   ID: §a" + player_.getName()),
                        ("§r§f (點擊) 對這玩家發送出傳送請求")
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
        meta.setDisplayName("§b找不到你要的玩家?");
        meta.setLore(Arrays.asList(
                ("§r§f - 使用指令 /tpa 試試看!")
        ));
        // 寫入資料
        item.setItemMeta(meta);
        // 設置完成
        inv.setItem(45, item);

        // --------------------------------------     46     --------------------------------------
        // 沒找到你要的?
        item = new ItemStack(Material.REDSTONE_BLOCK, 1);
        meta = item.getItemMeta();
        meta.setDisplayName("§e邀請所有人(未完成)");
        meta.setLore(Arrays.asList(
                ("§r§f (點擊) 對所有玩家發出傳送請求")
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
    final public void onInventoryClickEvent(InventoryClickEvent event) {
        // 判斷是否為玩家

        if (event.getWhoClicked() instanceof Player) {
            // 是玩家
            Player player = (Player) event.getWhoClicked();

            // 檢查容器名稱
            if (event.getView().getTitle().equalsIgnoreCase("§z傳送系統")) {
                // 是沒錯

                event.setCancelled(true);

                // 檢查點擊的是第幾個位置
                if (event.getRawSlot() < 45) {
                    try {
                        // 頭顱

                        if (event.getCurrentItem() == null) {return;}

                        // 點擊傳送
                        ItemMeta meta = event.getCurrentItem().getItemMeta();

                        if (meta == null) { return; }
                        if (meta.getCustomTagContainer().getCustomTag(player_key,ItemTagType.STRING) == null) { return; }

                        //player.sendMessage( meta.getDisplayName() );
                        // 使用物品名稱進行玩家使用 tpa 指令
                        player.chat("/tpa " + meta.getCustomTagContainer().getCustomTag(player_key,ItemTagType.STRING));

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }


                } else if (event.getRawSlot() == 45) {
                    // 關閉
                    player.chat("/tpa ");

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

}
