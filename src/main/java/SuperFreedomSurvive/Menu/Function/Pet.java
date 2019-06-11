package SuperFreedomSurvive.Menu.Function;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

final public class Pet implements Listener {

    // 設定羈絆

    final public static void menu(Player player) {
        // 顯示容器
        Inventory inv = Bukkit.createInventory(null, 54, "§z羈絆系統");

        ItemStack item;
        ItemMeta meta;


        // --------------------------------------     0     --------------------------------------
        item = new ItemStack(Material.EGG);
        meta = item.getItemMeta();
        meta.setDisplayName("§e創建自己的羈絆");
        meta.setLore(Arrays.asList(
                ("§r§f - 怪物一覽"),
                ("§r§f - 製作怪物")
        ));
        // 寫入資料
        item.setItemMeta(meta);
        // 設置完成
        inv.setItem(0, item);


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
            if (event.getView().getTitle().equalsIgnoreCase("§z羈絆系統")) {
                // 是沒錯

                event.setCancelled(true);

                if (event.getRawSlot() == 52) {
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
}
