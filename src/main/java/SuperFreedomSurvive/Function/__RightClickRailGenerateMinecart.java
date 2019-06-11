package SuperFreedomSurvive.Function;

import org.bukkit.event.Listener;


//      右鍵鐵軌產生礦車
final public class __RightClickRailGenerateMinecart implements Listener {
    /*
    @EventHandler
    final public void onPlayerInteractEvent(PlayerInteractEvent event) {
        try {
            if (event.isCancelled()) {
                // 已經被取消了
            } else {
                // 沒有被取消

                if (event.getAction() == Action.RIGHT_CLICK_BLOCK ) {
                    // 是點擊方塊
                    // 是否為 鐵軌 並且手上只能是空手
                    if ( event.getClickedBlock().getType().name().matches(".*RAIL.*") ) {
                        if ( event.getItem() == null ) {
                            // 是
                            int X = (int) event.getClickedBlock().getLocation().getX();
                            int Y = (int) event.getClickedBlock().getLocation().getY();
                            int Z = (int) event.getClickedBlock().getLocation().getZ();
                            String world = event.getClickedBlock().getWorld().getName();
                            // 檢查是否有權限
                            if (
                                    ServerPlugin.Land.Permissions.Inspection(event.getPlayer(), world, X, Y, Z, "Permissions_Entity_Minecart_Change") &&
                                    ServerPlugin.Land.Permissions.Inspection(event.getPlayer(), world, X, Y, Z, "Permissions_Entity_Minecart_Use")
                            ) {
                                // 有
                                Location location = new Location(
                                        event.getClickedBlock().getLocation().getWorld(),
                                        event.getClickedBlock().getLocation().getX() + 0.5,
                                        event.getClickedBlock().getLocation().getY(),
                                        event.getClickedBlock().getLocation().getZ() + 0.5
                                );

                                // 創建礦車
                                Entity minecart = Bukkit.getWorld(event.getClickedBlock().getWorld().getName()).spawnEntity(location, EntityType.MINECART);
                                // 設置為無法攻擊的
                                minecart.setInvulnerable(true);
                                // 坐上礦車
                                minecart.addPassenger(event.getPlayer());
                            }
                        }
                    }
                }
            }

        } catch (Exception ex) {
        }


    }


    // 實體離開礦車
    @EventHandler
    final public void onVehicleExitEvent(VehicleExitEvent event) {
        int X = (int)event.getVehicle().getLocation().getX();
        int Y = (int)event.getVehicle().getLocation().getY();
        int Z = (int)event.getVehicle().getLocation().getZ();
        String world = event.getVehicle().getWorld().getName();

        if (event.isCancelled()) {
            // 已經被取消了
        } else {
            // 沒有被取消
            if (event.getExited() instanceof Player) {
                if ( event.getVehicle().getType() == EntityType.MINECART ) {
                    // 礦車
                    // 檢查是否有權限
                    if (
                            ServerPlugin.Land.Permissions.Inspection((Player)event.getExited(), world, X, Y, Z, "Permissions_Entity_Minecart_Change") &&
                            ServerPlugin.Land.Permissions.Inspection((Player)event.getExited(), world, X, Y, Z, "Permissions_Entity_Minecart_Use")
                    ) {
                        // 有
                        // 檢查是否為不可傷害標記
                        if ( event.getVehicle().isInvulnerable() ) {
                            //event.getVehicle().removePassenger( event.getExited() );
                            // 將礦車移除
                            event.getVehicle().setInvulnerable( false );
                            event.getVehicle().getVehicle().;
                        }
                    } else {
                        // 沒有

                    }
                }
            }
        }
    }
    */


}
