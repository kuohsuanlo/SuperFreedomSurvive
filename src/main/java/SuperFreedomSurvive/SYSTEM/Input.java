package SuperFreedomSurvive.SYSTEM;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;

public class Input implements Listener {
    // 對話輸入模塊

    static final public HashMap<Player, String> map = new HashMap<Player, String>() {};
    static final public HashMap<Player, BukkitTask> run = new HashMap<Player, BukkitTask>() {};

    // 註冊
    static final public void setRegistry(Player player,BukkitTask task) {
        map.put(player, null);
        // 停止其他線程運行
        if ( run.get(player) != null ) {
            run.get(player).cancel();
        }
        run.put(player,task);
    }

    // 取得資料
    static final public String getMessage(Player player) {
        //Bukkit.getLogger().info("Get");
        try {
            if (map.containsKey(player)) {
                if (map.get(player) != null) {
                    String text = map.get(player);

                    map.remove(player);

                    // 停止其他線程運行
                    run.get(player).cancel();

                    return text;
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    // 觸發聊天事件
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    final public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent event) {
        event.setMessage(event.getMessage().replace("\\", "").replace("'", "").replace("\'", "")); // 安全過濾字串

        // 是否為空白
        if (event.getMessage().equals("")) {
            return;
        } else if (event.getMessage().matches("\\s")) {
            return;
        }

        //Bukkit.getLogger().info("AsyncPlayerChatEvent");
        try {
            if (map.containsKey(event.getPlayer())) {
                map.put(event.getPlayer(), event.getMessage());
                event.setCancelled(true);
            } else {
                SuperFreedomSurvive.Player.Chat.Distribution(event);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
