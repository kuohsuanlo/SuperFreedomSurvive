package SuperFreedomSurvive.Land.Examine;

import SuperFreedomSurvive.SYSTEM.MySQL;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.vehicle.VehicleDamageEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

final public class PermissionEntity implements Listener {


    // 破壞物品展示框
    @EventHandler(priority = EventPriority.HIGHEST)
    final public void onHangingBreakByEntityEvent(HangingBreakByEntityEvent event) {

        Player player = null;

        // 為玩家
        if (event.getRemover() instanceof Player) {
            player = (Player) event.getRemover();
        }

        // 檢查是否有權限
        if (SuperFreedomSurvive.Land.Permissions.Inspection(player, event.getEntity().getLocation(), "Permissions_Entity_ItemFrame_Change")) {
            // 有
        } else {
            // 沒有
            // 禁止事件 改動資料
            event.setCancelled(true);

            if (player != null) {
                SuperFreedomSurvive.Land.Display.Message((Player) event.getRemover(), "§c領地禁止 破壞物品展示框");
            }
        }
    }

    // 刪除物品展示框
    @EventHandler(priority = EventPriority.HIGHEST)
    final public void onHangingBreakEvent(HangingBreakEvent event) {

        if (
                event.getCause() == HangingBreakEvent.RemoveCause.OBSTRUCTION ||
                event.getCause() == HangingBreakEvent.RemoveCause.PHYSICS ||
                event.getCause() == HangingBreakEvent.RemoveCause.ENTITY
        ) {
            return;
        }

        // 檢查是否有權限
        if (SuperFreedomSurvive.Land.Permissions.Inspection(null, event.getEntity().getLocation(), "Permissions_Entity_ItemFrame_Change")) {
            // 有
        } else {
            // 沒有
            // 禁止事件 改動資料
            event.setCancelled(true);
        }
    }

    // 放置物品展示框
    @EventHandler(priority = EventPriority.HIGHEST)
    final public void onHangingPlaceEvent(HangingPlaceEvent event) {

        // 檢查是否有權限
        if (SuperFreedomSurvive.Land.Permissions.Inspection(event.getPlayer(), event.getBlock().getLocation(), "Permissions_Entity_ItemFrame_Change")) {
            // 有
        } else {
            // 沒有
            SuperFreedomSurvive.Land.Display.Message(event.getPlayer(), "§c領地禁止 放置物品展示框");
            // 禁止事件 改動資料
            event.setCancelled(true);

        }

    }


    @EventHandler(priority = EventPriority.HIGHEST)
    final public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
        //  當玩家與實體交互
        try {

            String name = null;
            String directions = null;
            Player player = null;

            if (event.getDamager() instanceof Player) {
                player = (Player) event.getDamager();
            }

            if (event.getEntityType() == EntityType.ARMOR_STAND) {
                // 盔甲座
                name = "ArmorStand";
                directions = "破壞盔甲座";

            } else if (event.getEntityType() == EntityType.ITEM_FRAME) {
                // 物品展示框
                name = "ItemFrame_Change";
                directions = "破壞物品展示框";

            } else if (event.getEntityType() == EntityType.PLAYER) {
                // 為玩家
                return;

            }

            String permissions;
            if (name != null) {
                // 有資料 使用資料方式來決定
                permissions = "Permissions_Entity_" + name;

            } else {
                // 無資料 使用全域設置
                permissions = "Permissions_EntityDamageByEntity";

            }


            // 檢查是否有權限
            if (SuperFreedomSurvive.Land.Permissions.Inspection(player, event.getEntity().getLocation(), permissions)) {
                // 有
            } else {
                // 沒有
                // 禁止事件 改動資料
                event.setCancelled(true);
                // 顯示訊息

                // 是否為玩家造成這次傷害
                if (player != null) {
                    if (directions != null) {
                        SuperFreedomSurvive.Land.Display.Message(player, "§c領地禁止 " + directions);
                    } else {
                        SuperFreedomSurvive.Land.Display.Message(player, "§c領地禁止 傷害實體");
                    }
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }



    // 當玩家攻擊玩家
    @EventHandler(priority = EventPriority.HIGHEST)
    final public void onEntityDamageByEntityEvent_(EntityDamageByEntityEvent event) {
        if (event.getEntityType() == EntityType.PLAYER) {
            // 玩家攻擊玩家

            if (event.getDamager() instanceof Player && event.getEntityType() == EntityType.PLAYER) {
                Player player = (Player) event.getDamager();
                // 檢查是否有權限
                if (SuperFreedomSurvive.Land.Permissions.Inspection(player, event.getEntity().getLocation(), "Permissions_Entity_Player")) {
                    // 有
                } else {
                    // 沒有
                    // 禁止事件 改動資料
                    event.setCancelled(true);
                    // 顯示訊息
                    SuperFreedomSurvive.Land.Display.Message(player, "§c領地禁止  攻擊玩家");

                }
            }
        }
    }




    @EventHandler(priority = EventPriority.HIGHEST)
    final public void onEntityExplodeEvent(EntityExplodeEvent event) {
        // 當爆炸發生
        int Min_X = 0;
        int Min_Y = 0;
        int Min_Z = 0;
        int Max_X = 0;
        int Max_Y = 0;
        int Max_Z = 0;
        Block block;
        List list = event.blockList();
        String world = null;
        // 取得最大 和 最小距離
        for (int i = 0, s = list.size(); i < s; ++i) {
            //if (((Block) list.get(i)).getState() != null) {
            //block = ((BlockState) list.get(i)).getBlock();
            //} else {
            block = (Block) list.get(i);
            //}
            Location location = block.getLocation();
            world = block.getWorld().getName();
            if (i == 0) {
                Min_X = location.getBlockX();
                Min_Y = location.getBlockY();
                Min_Z = location.getBlockZ();
                Max_X = location.getBlockX();
                Max_Y = location.getBlockY();
                Max_Z = location.getBlockZ();
            } else {
                if (location.getBlockX() < Min_X) {
                    Min_X = location.getBlockX();
                } else if (location.getBlockX() > Max_X) {
                    Max_X = location.getBlockX();
                }
                //      Y
                if (location.getBlockY() < Min_Y) {
                    Min_Y = location.getBlockY();
                } else if (location.getBlockY() > Max_Y) {
                    Max_Y = location.getBlockY();
                }
                //      Z
                if (location.getBlockZ() < Min_Z) {
                    Min_Z = location.getBlockZ();
                } else if (location.getBlockZ() > Max_Z) {
                    Max_Z = location.getBlockZ();
                }
            }
        }
        if (world != null) {
            //Bukkit.getLogger().info(Min_X+"/"+Min_Y+"/"+Min_Z+"/"+Max_X+"/"+Max_Y+"/"+Max_Z);
            try {
                Connection con = MySQL.getConnection(); // 連線 MySQL
                Statement sta = con.createStatement(); // 取得控制庫

                ResultSet res = sta.executeQuery("SELECT (ID) FROM `land-protection_data` WHERE `World` = '" + world + "' AND " +
                        "  ( (   `Min_X` >= '" + Min_X + "' AND `Max_X` <= '" + Max_X + "' )" +
                        " OR (   `Min_X` <= '" + Min_X + "' AND `Max_X` >= '" + Max_X + "' )" +
                        " OR ( ( `Min_X` >= '" + Min_X + "' AND `Max_X` >= '" + Max_X + "' ) AND `Min_X` <= '" + Max_X + "' )  " +
                        " OR ( ( `Min_X` <= '" + Min_X + "' AND `Max_X` <= '" + Max_X + "' ) AND `Max_X` >= '" + Min_X + "' ) ) AND (" +
                        "    (   `Min_Y` >= '" + Min_Y + "' AND `Max_Y` <= '" + Max_Y + "' )" +
                        " OR (   `Min_Y` <= '" + Min_Y + "' AND `Max_Y` >= '" + Max_Y + "' )" +
                        " OR ( ( `Min_Y` >= '" + Min_Y + "' AND `Max_Y` >= '" + Max_Y + "' ) AND `Min_Y` <= '" + Max_Y + "' )  " +
                        " OR ( ( `Min_Y` <= '" + Min_Y + "' AND `Max_Y` <= '" + Max_Y + "' ) AND `Max_Y` >= '" + Min_Y + "' ) ) AND (" +
                        "    (   `Min_Z` >= '" + Min_Z + "' AND `Max_Z` <= '" + Max_Z + "' )" +
                        " OR (   `Min_Z` <= '" + Min_Z + "' AND `Max_Z` >= '" + Max_Z + "' )" +
                        " OR ( ( `Min_Z` >= '" + Min_Z + "' AND `Max_Z` >= '" + Max_Z + "' ) AND `Min_Z` <= '" + Max_Z + "' )  " +
                        " OR ( ( `Min_Z` <= '" + Min_Z + "' AND `Max_Z` <= '" + Max_Z + "' ) AND `Max_Z` >= '" + Min_Z + "' ) ) " +
                        " AND `Permissions_BlockExplode` = '0' ORDER BY `Level` LIMIT 2");
                // 跳到最後一行
                res.last();
                // 最後一行 行數 是否 > 0
                if (res.getRow() > 0) {
                    // 不允許
                    event.setCancelled(true);
                }

                res.close(); // 關閉查詢

                ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                res = sta.executeQuery("SELECT (World),(X),(Y),(Z) FROM `block-special_data` WHERE `World` = '" + world + "' AND `X` >= '" + Min_X + "' AND `Y` >= '" + Min_Y + "' AND `Z` >= '" + Min_Z + "' AND `X` <= '" + Max_X + "' AND `Y` <= '" + Max_Y + "' AND `Z` <= '" + Max_Z + "'");
                // 跳到最後一行
                res.last();
                // 最後一行 行數 是否 > 0
                if (res.getRow() > 0) {
                    // 跳回 初始行 必須使用 next() 才能取得資料
                    res.beforeFirst();
                    // 將所有塊還原
                    while (res.next()) {
                        for (int i = list.size() - 1; i >= 0; --i) {
                            block = (Block) list.get(i);
                            //}
                            if (
                                    block.getLocation().getWorld().getName().equals(res.getString("World")) &&
                                            block.getLocation().getBlockX() == res.getInt("X") &&
                                            block.getLocation().getBlockY() == res.getInt("Y") &&
                                            block.getLocation().getBlockZ() == res.getInt("Z")
                            ) {
                                event.blockList().remove(i);
                            }
                        }
                    }
                }

                res.close(); // 關閉查詢
                sta.close(); // 關閉連線

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
/*
            // 檢查是否有權限
            if ( ServerPlugin.Land.Permissions.Inspection( null , event.getLocation() , "Permissions_BlockExplode" ) ){
                // 有
            } else {
                // 沒有
                // 禁止事件 改動資料
                event.setCancelled(true);
            }
*/
    }


    // 實體進入礦車
    @EventHandler(priority = EventPriority.HIGHEST)
    final public void onVehicleEnterEvent(VehicleEnterEvent event) {

        if (event.getEntered() instanceof Player) {
            if (event.getVehicle().getType().name().matches(".*MINECART.*")) {
                // 礦車
                // 檢查是否有權限
                if (SuperFreedomSurvive.Land.Permissions.Inspection((Player) event.getEntered(), event.getVehicle().getLocation(), "Permissions_Entity_Minecart_Use")) {
                    // 有
                } else {
                    // 沒有

                    SuperFreedomSurvive.Land.Display.Message((Player) event.getEntered(), "§c領地禁止 進出礦車");
                    // 禁止事件 改動資料
                    event.setCancelled(true);

                }

            } else if (event.getVehicle().getType() == EntityType.BOAT) {
                // 船
                // 檢查是否有權限
                if (SuperFreedomSurvive.Land.Permissions.Inspection((Player) event.getEntered(), event.getVehicle().getLocation(), "Permissions_Entity_Boat_Use")) {
                    // 有
                } else {
                    // 沒有
                    SuperFreedomSurvive.Land.Display.Message((Player) event.getEntered(), "§c領地禁止 進出船");
                    // 禁止事件 改動資料
                    event.setCancelled(true);

                }
            }
        }
    }

    // 實體離開礦車
    @EventHandler(priority = EventPriority.HIGHEST)
    final public void onVehicleExitEvent(VehicleExitEvent event) {


        if (event.getExited() instanceof Player) {
            if (event.getVehicle().getType().name().matches(".*MINECART.*")) {
                // 礦車
                // 檢查是否有權限
                if (SuperFreedomSurvive.Land.Permissions.Inspection((Player) event.getExited(), event.getVehicle().getLocation(), "Permissions_Entity_Minecart_Use")) {
                    // 有
                } else {
                    // 沒有
                    SuperFreedomSurvive.Land.Display.Message((Player) event.getExited(), "§c領地禁止 進出礦車");
                    // 禁止事件 改動資料
                    event.setCancelled(true);

                }

            } else if (event.getVehicle().getType() == EntityType.BOAT) {
                // 船
                // 檢查是否有權限
                if (SuperFreedomSurvive.Land.Permissions.Inspection((Player) event.getExited(), event.getVehicle().getLocation(), "Permissions_Entity_Boat_Use")) {
                    // 有
                } else {
                    // 沒有
                    SuperFreedomSurvive.Land.Display.Message((Player) event.getExited(), "§c領地禁止 進出船");
                    // 禁止事件 改動資料
                    event.setCancelled(true);


                }
            }

        }
    }

    // 實體破壞礦車
    @EventHandler(priority = EventPriority.HIGHEST)
    final public void onVehicleDamageEvent(VehicleDamageEvent event) {


        if (event.getAttacker() instanceof Player) {
            if (event.getVehicle().getType().name().matches(".*MINECART.*")) {
                // 礦車
                // 檢查是否有權限
                if (SuperFreedomSurvive.Land.Permissions.Inspection((Player) event.getAttacker(), event.getVehicle().getLocation(), "Permissions_Entity_Minecart_Change")) {
                    // 有
                } else {
                    // 沒有
                    SuperFreedomSurvive.Land.Display.Message((Player) event.getAttacker(), "§c領地禁止 破壞礦車");
                    // 禁止事件 改動資料
                    event.setCancelled(true);

                }

            } else if (event.getVehicle().getType() == EntityType.BOAT) {
                // 船
                // 檢查是否有權限
                if (SuperFreedomSurvive.Land.Permissions.Inspection((Player) event.getAttacker(), event.getVehicle().getLocation(), "Permissions_Entity_Boat_Change")) {
                    // 有
                } else {
                    // 沒有
                    SuperFreedomSurvive.Land.Display.Message((Player) event.getAttacker(), "§c領地禁止 破壞船");
                    // 禁止事件 改動資料
                    event.setCancelled(true);

                }
            }
        }

    }

    // 實體放置物件時
    @EventHandler(priority = EventPriority.HIGHEST)
    final public void onPlayerInteractEvent(PlayerInteractEvent event) {
        try {

            if (event.getItem() == null) { return; }
            if (event.getClickedBlock() == null) { return; }

            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (event.getItem().getType().name().matches(".*MINECART.*")) {
                    // 礦車

                    // 檢查是否有權限
                    if (SuperFreedomSurvive.Land.Permissions.Inspection(event.getPlayer(), event.getClickedBlock().getLocation(), "Permissions_Entity_Minecart_Change")) {
                        // 有
                    } else {
                        // 沒有
                        SuperFreedomSurvive.Land.Display.Message(event.getPlayer(), "§c領地禁止 放置礦車");
                        // 禁止事件 改動資料
                        event.setCancelled(true);

                    }
                } else if (event.getItem().getType().name().matches(".*BOAT.*")) {
                    // 船

                    // 檢查是否有權限
                    if (SuperFreedomSurvive.Land.Permissions.Inspection(event.getPlayer(), event.getClickedBlock().getLocation(), "Permissions_Entity_Boat_Change")) {
                        // 有
                    } else {
                        // 沒有
                        SuperFreedomSurvive.Land.Display.Message(event.getPlayer(), "§c領地禁止 放置船");

                        // 禁止事件 改動資料
                        event.setCancelled(true);

                    }
                } else if (event.getItem().getType().name().matches(".*ARMOR_STAND.*")) {
                    // 盔甲座

                    // 檢查是否有權限
                    if (SuperFreedomSurvive.Land.Permissions.Inspection(event.getPlayer(), event.getClickedBlock().getLocation(), "Permissions_Entity_ArmorStand")) {
                        // 有
                    } else {
                        // 沒有
                        SuperFreedomSurvive.Land.Display.Message(event.getPlayer(), "§c領地禁止 放置盔甲座");

                        // 禁止事件 改動資料
                        event.setCancelled(true);

                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    // 與容器礦車互動時
    @EventHandler(priority = EventPriority.HIGHEST)
    final public void onPlayerInteractEntityEvent(PlayerInteractEntityEvent event) {
        //  當玩家點擊一個實體 時調用此事件
        if (
                event.getRightClicked().getType() == EntityType.MINECART_CHEST ||
                event.getRightClicked().getType() == EntityType.MINECART_FURNACE ||
                event.getRightClicked().getType() == EntityType.MINECART_HOPPER ||
                event.getRightClicked().getType() == EntityType.MINECART_TNT
        ) {
            // 礦車

            // 檢查是否有權限
            if (SuperFreedomSurvive.Land.Permissions.Inspection(event.getPlayer(), event.getRightClicked().getLocation(), "Permissions_Entity_Minecart_Interact")) {
                // 有
            } else {
                // 沒有
                SuperFreedomSurvive.Land.Display.Message(event.getPlayer(), "§c領地禁止 礦車容器交互");

                // 禁止事件 改動資料
                event.setCancelled(true);

            }


        }
    }

}
/*

            } else if (
                    event.getEntityType() == EntityType.MINECART_CHEST ||
                    event.getEntityType() == EntityType.MINECART_FURNACE ||
                    event.getEntityType() == EntityType.MINECART_HOPPER ||
                    event.getEntityType() == EntityType.MINECART_TNT
            ) {
                // 礦車
                name = "Minecart";
                directions = "礦車"
 */
