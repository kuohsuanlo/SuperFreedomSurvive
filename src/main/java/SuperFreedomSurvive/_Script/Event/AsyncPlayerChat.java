package SuperFreedomSurvive._Script.Event;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

final public class AsyncPlayerChat implements Listener {
    // 聊天事件

    // 新增
    static final public void menu(Player player) {
        // 條件顯示
        Inventory inv = Bukkit.createInventory(null, 36, "§z事件觸發條件");

        ItemStack item;
        ItemMeta meta;

        // --------------------------------------     0     --------------------------------------
        item = new ItemStack(Material.SIGN);
        meta = item.getItemMeta();
        meta.setDisplayName("§e當輸入 ok 的時候");
        meta.setLore(Arrays.asList(
                ("§r§f (點擊) 確定使用此觸發器")
        ));
        // 寫入資料
        item.setItemMeta(meta);
        // 設置完成
        inv.setItem(0, item);


        player.openInventory(inv);

    }

    // 使用
    @EventHandler
    final public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent event) {
        //  當玩家聊天時觸發這事件

    }


}
