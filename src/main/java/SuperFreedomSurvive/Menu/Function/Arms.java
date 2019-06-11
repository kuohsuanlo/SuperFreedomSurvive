package SuperFreedomSurvive.Menu.Function;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import SuperFreedomSurvive.Arms.Registers.*;

final public class Arms implements Listener {

    // 武器合成

    final public static void menu(Player player) {
        // 顯示容器
        Inventory inv = Bukkit.createInventory(null, 54, "§z武器系統");

        ItemStack item;
        ItemMeta meta;

        // 合成

        // 熔岩魔法仗
        inv.setItem(0, flame_magic_wand.level_initial());


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
            if (event.getView().getTitle().equalsIgnoreCase("§z武器系統")) {
                // 是沒錯

                event.setCancelled(true);


                if (event.getRawSlot() == 0) {
                    // 熔岩魔法仗
                    player.getInventory().addItem(flame_magic_wand.level_initial());

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
}
