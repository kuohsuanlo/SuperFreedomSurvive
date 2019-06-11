package SuperFreedomSurvive.Arms;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

import java.util.ArrayList;

final public class Singing implements Listener {

    // 詠唱註冊

    // 創建2維陣列組
    final static public ArrayList<String> all = new ArrayList<String>();

    static final public boolean Have(String name) {
        // 是否有此世界在註冊表中
        for (int i = 0, s = all.size(); i < s; ++i) {
            if (all.get(i).equals(name)) {
                // 有
                return true;
            }
        }
        // 沒有
        return false;
    }

    // 加入到註冊表
    static final public boolean Add(String name) {
        // 增加到註冊表中
        if (Have(name)) {
            // 已經有了
            return false;
        } else {
            // 加入
            all.add(name);
            return true;
        }
    }

    // 從註冊表中刪除
    static final public boolean Remove(String name) {
        boolean yes = false;
        for (int i = 0, s = all.size(); i < s; ++i) {
            if (all.get(i).equals(name)) {
                // 有
                all.remove(i);
                yes = true;
            }
        }
        // 沒有
        return yes;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // 切換物品時
    @EventHandler
    final public void onPlayerItemHeldEvent(PlayerItemHeldEvent event) {
        if (Have(event.getPlayer().getName())) {
            event.setCancelled(true);
        }
    }

    // 玩家點擊庫存時
    @EventHandler
    final public void onPlayerInteractEvent(PlayerInteractEvent event) {
        if (Have(event.getPlayer().getName())) {
            event.setCancelled(true);
        }
    }

    // 丟棄物品時
    @EventHandler
    final public void onPlayerDropItemEvent(PlayerDropItemEvent event) {
        if (Have(event.getPlayer().getName())) {
            event.setCancelled(true);
        }
    }

    // 切換主手副手物品時
    @EventHandler
    final public void onPlayerSwapHandItemsEvent(PlayerSwapHandItemsEvent event) {
        if (Have(event.getPlayer().getName())) {
            event.setCancelled(true);
        }
    }

    // 點擊庫存位置時
    @EventHandler
    final public void onInventoryClickEvent(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            // 是玩家
            Player player = (Player) event.getWhoClicked();
            if (Have(player.getName())) {
                event.setCancelled(true);
            }
        }
    }


}
