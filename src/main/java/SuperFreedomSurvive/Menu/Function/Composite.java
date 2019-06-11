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
import SuperFreedomSurvive.Definition.Item.*;

import java.util.Arrays;

final public class Composite implements Listener {


    // 選單接口
    // 合成表查看
    final public static void menu(Player player) {

        // 顯示容器
        Inventory inv = Bukkit.createInventory(null, 54, "§z合成表清單");

        ItemStack item;
        ItemMeta meta;


        // 動物收納蛋
        inv.setItem(0, storag_animal_egg.Get());
        // 超好吃餅乾
        inv.setItem(1, delicious_cookie.Get());
        // 幸運方塊
        inv.setItem(2, lucky_block.Get());
        // 屏障
        inv.setItem(3, barrier_block.Get());
        // 生怪磚
        inv.setItem(4, spawner_block.Get());
        // 自訂怪物生怪磚
        inv.setItem(5, custom_spawner_block.Get());
        // 附魔金蘋果
        inv.setItem(6, enchanted_golden_apple.Get());
        // 經驗值回收瓶
        inv.setItem(7, experience_collect_bottle.Get());
        // 生命核心
        inv.setItem(8, max_health_core.Get());


        // --------------------------------------     45     --------------------------------------
        // 沒找到你要的?
        item = new ItemStack(Material.MAP);
        meta = item.getItemMeta();
        meta.setDisplayName("§b點擊你想要看的合成物品");
        meta.setLore(Arrays.asList(
                ("§r§f - 然後準備材料製作他")
        ));
        // 寫入資料
        item.setItemMeta(meta);
        // 設置完成
        inv.setItem(45, item);


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
            if (event.getView().getTitle().equalsIgnoreCase("§z合成表清單")) {
                // 是沒錯

                event.setCancelled(true);


                // 檢查點擊的是第幾個位置
                if (event.getRawSlot() == 0) {
                    // 動物收納蛋
                    storag_animal_egg.OpenComposite(player);
                } else if (event.getRawSlot() == 1) {
                    // 超好吃餅乾
                    delicious_cookie.OpenComposite(player);
                } else if (event.getRawSlot() == 2) {
                    // 幸運方塊
                    lucky_block.OpenComposite(player);
                } else if (event.getRawSlot() == 3) {
                    // 屏障
                    barrier_block.OpenComposite(player);
                } else if (event.getRawSlot() == 4) {
                    // 生怪磚
                    spawner_block.OpenComposite(player);
                } else if (event.getRawSlot() == 5) {
                    // 自訂怪物生怪磚
                    custom_spawner_block.OpenComposite(player);
                } else if (event.getRawSlot() == 6) {
                    // 附魔金蘋果
                    enchanted_golden_apple.OpenComposite(player);
                } else if (event.getRawSlot() == 7) {
                    // 經驗值回收瓶
                    experience_collect_bottle.OpenComposite(player);
                } else if (event.getRawSlot() == 8) {
                    // 生命核心
                    max_health_core.OpenComposite(player);


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


    // 阻止玩家拿走物品
    @EventHandler
    final public void onInventoryClickEvent__(InventoryClickEvent event) {
        // 判斷是否為玩家

        if (event.getWhoClicked() instanceof Player) {
            // 是玩家
            Player player = (Player) event.getWhoClicked();

            // 檢查容器名稱
            if (event.getView().getTitle().equalsIgnoreCase("§z合成方式")) {
                event.setCancelled(true);

                if (event.getRawSlot() == 34) {
                    // 上一頁
                    menu(player);

                } else if (event.getRawSlot() == 35) {
                    // 關閉
                    event.getWhoClicked().closeInventory();

                }

            }
        }
    }


}
