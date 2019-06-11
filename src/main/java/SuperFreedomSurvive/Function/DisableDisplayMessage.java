package SuperFreedomSurvive.Function;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

final public class DisableDisplayMessage implements Listener {
    // 停用顯示訊息

    // 停用死亡訊息
    @EventHandler(priority = EventPriority.HIGHEST)
    final public void onPlayerDeathEvent(PlayerDeathEvent event) {
        event.setDeathMessage(null);
    }

    // 停用登入訊息
    @EventHandler(priority = EventPriority.HIGHEST)
    final public void onPlayerJoinEvent(PlayerJoinEvent event) {
        event.setJoinMessage(null);
    }

    // 停用登出訊息
    @EventHandler(priority = EventPriority.HIGHEST)
    final public void onPlayerQuitEvent(PlayerQuitEvent event) {
        event.setQuitMessage(null);
    }
}
