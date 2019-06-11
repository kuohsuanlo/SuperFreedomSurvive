package SuperFreedomSurvive.Sign;

import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

final public class Edit implements Listener {

    // 編輯告示牌
    @EventHandler
    final public void onVehicleCreateEvent(PlayerInteractEvent event) {
        if (event.isCancelled()) {
            // 被取消

        } else {
            // 沒有被取消
            try {
                // 是否為右鍵類型
                if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    if (event.getClickedBlock().getType() == Material.SIGN || event.getClickedBlock().getType() == Material.WALL_SIGN) {
                        // 是告示牌
                        if (event.getPlayer().isSneaking()) {
                            // 蹲下狀態

                            // 檢查是否有權限
                            if (SuperFreedomSurvive.Land.Permissions.Inspection(event.getPlayer(), event.getClickedBlock().getLocation(), "Permissions_SignChange")) {
                                // 有
                                Sign sign = (Sign) event.getClickedBlock().getState();
                                event.getPlayer().openSign(sign);

                            } else {
                                // 沒有
                                SuperFreedomSurvive.Land.Display.Message(event.getPlayer(), "§c領地禁止 告示牌編輯");
                                // 禁止事件 改動資料
                                event.setCancelled(true);

                            }
                        }
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }

}
