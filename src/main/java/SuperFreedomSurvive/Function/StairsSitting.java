package SuperFreedomSurvive.Function;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

final public class StairsSitting implements Listener {
    // 點擊樓梯座下

    @EventHandler
    final public void onVehicleCreateEvent(PlayerInteractEvent event) {
        /*
        try {
            if (event.getHand() == EquipmentSlot.HAND) {
                // 是否為右鍵類型
                if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    if ( event.getClickedBlock().getType().name().matches(".*STAIRS.*") ) {
                        // 是否手上有物品
                        if (event.getPlayer().getInventory().getItemInMainHand().getType() == Material.AIR ) {
                            if (!event.getPlayer().isSneaking()) {
                                Minecart s = (Minecart) event.getPlayer().getWorld().spawnEntity(event.getClickedBlock().getLocation(), EntityType.MINECART);
                                s.addPassenger(event.getPlayer());
                                s.setMaxSpeed(0);
                                s.remove();
                                //s.remove();

                            }
                        }
                    }
                }
            }

        } catch (Exception ex) {
            // 出錯
            //event.getPlayer().sendMessage(ex.getMessage());
        }
        */
    }
}
