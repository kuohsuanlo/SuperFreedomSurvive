package SuperFreedomSurvive.Land.Examine;

import SuperFreedomSurvive.SYSTEM.MySQL;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.event.*;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;


final public class PermissionBlock implements Listener {
    // 控制所有 在領地內的破壞 / 放置 / 物品使用 / 傷害
    // static ConsoleCommandSender console = Bukkit.getConsoleSender();


    @EventHandler(priority = EventPriority.HIGHEST)
    final public void onEntityChangeBlockEvent(EntityChangeBlockEvent event) {
        // 實體更改方塊 (不包含玩家)

        if ( event.getEntityType().isAlive() ) {
            // 實體更改方塊

            // 檢查是否有權限
            if (SuperFreedomSurvive.Land.Permissions.Inspection(null, event.getBlock().getLocation(), "Permissions_EntityChangeBlock")) {
                // 有
            } else {
                // 沒有
                // 禁止事件 改動資料
                event.setCancelled(true);
            }

        } else {
            // 方塊自然更改

            // 檢查是否有權限
            if (SuperFreedomSurvive.Land.Permissions.Inspection(null, event.getBlock().getLocation(), "Permissions_BlockChange")) {
                // 有
            } else {
                // 沒有
                // 禁止事件 改動資料
                event.getEntity().remove();
                //event.setCancelled(true);
            }

        }
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    final public void onBlockBreakEvent(BlockBreakEvent event) {
        //  當一個方塊被玩家破壞的時候

        // 檢查是否有權限
        if (SuperFreedomSurvive.Land.Permissions.Inspection(event.getPlayer(), event.getBlock().getLocation(), "Permissions_BlockBreak")) {
            // 有
        } else {
            // 沒有
            SuperFreedomSurvive.Land.Display.Message(event.getPlayer(), "§c領地禁止 破壞方塊");
            // 禁止事件 改動資料
            event.setCancelled(true);
        }
    }

    /*
        @EventHandler( priority = EventPriority.HIGHEST )
        final public void onBlockBurnEvent(BlockBurnEvent event){
            //  當一個方塊被火燒掉的時
            UserPermissionsExamination( event , event ,"BlockBurn" );
        }
    */
    @EventHandler(priority = EventPriority.HIGHEST)
    final public void onBlockDamageEvent(BlockDamageEvent event) {
        //  方塊被玩家損壞的事件

        // 檢查是否有權限
        if (SuperFreedomSurvive.Land.Permissions.Inspection(event.getPlayer(), event.getBlock().getLocation(), "Permissions_BlockDamage")) {
            // 有
        } else {
            // 沒有
            SuperFreedomSurvive.Land.Display.Message(event.getPlayer(), "§c領地禁止 觸碰方塊");
            // 禁止事件 改動資料
            event.setCancelled(true);
        }
    }

    /*
        @EventHandler( priority = EventPriority.HIGHEST )
        final public void onBlockDispenseEvent(BlockDispenseEvent event){
            //  物品被方塊(比如發射器,投擲器等)射出
            UserPermissionsExamination( event , event ,"BlockDispense" );
        }
    */
    @EventHandler(priority = EventPriority.HIGHEST)
    final public void onBlockExpEvent(BlockExpEvent event) {
        //  方塊產生經驗的時候(比如煤礦被被打破會掉落經驗)

        // 檢查是否有權限
        if (SuperFreedomSurvive.Land.Permissions.Inspection(null, event.getBlock().getLocation(), "Permissions_BlockExp")) {
            // 有
        } else {
            // 禁止事件 改動資料
            event.setExpToDrop(0);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    final public void onBlockExplodeEvent(BlockExplodeEvent event) {
        //  未知的 方塊爆炸

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
    }

    /*
        @EventHandler( priority = EventPriority.HIGHEST )
        final public void onBlockFadeEvent(BlockFadeEvent event){
            //  方塊因為自然條件消退，融化，消失
            UserPermissionsExamination( event , event ,"BlockFade" );
        }
    */
    @EventHandler(priority = EventPriority.HIGHEST)
    final public void onBlockFertilizeEvent(BlockFertilizeEvent event) {
        //  骨粉改變，加快生長速度

        // 檢查是否有權限
        if (SuperFreedomSurvive.Land.Permissions.Inspection(event.getPlayer(), event.getBlock().getLocation(), "Permissions_BlockFertilize")) {
            // 有
        } else {
            // 沒有
            SuperFreedomSurvive.Land.Display.Message(event.getPlayer(), "§c領地禁止 骨粉使用");
            // 禁止事件 改動資料
            event.setCancelled(true);
        }
    }

    /*
        @EventHandler( priority = EventPriority.HIGHEST )
        final public void onBlockFormEvent(BlockFormEvent event){
            //  當一個方塊因為自然變化被放置、更改或者蔓延時(比如下雪)
            UserPermissionsExamination( event , event ,"BlockForm" );
        }

        @EventHandler( priority = EventPriority.HIGHEST )
        final public void onBlockFromToEvent(BlockFromToEvent event){
            //  液體流動/龍蛋自己傳送的事件(源方塊到目標方塊)
            UserPermissionsExamination( event , event ,"BlockFromTo" );
        }

        @EventHandler( priority = EventPriority.HIGHEST )
        final public void onBlockGrowEvent(BlockGrowEvent event){
            //  當一個方塊在世界中自然生長(如小麥生長)
            UserPermissionsExamination( event , event ,"BlockGrow" );
        }
    */
    @EventHandler(priority = EventPriority.HIGHEST)
    final public void onBlockIgniteEvent(BlockIgniteEvent event) {
        //  當一個方塊被點燃

        // 檢查是否有權限
        if (SuperFreedomSurvive.Land.Permissions.Inspection(event.getPlayer(), event.getBlock().getLocation(), "Permissions_BlockIgnite")) {
            // 有
        } else {
            // 沒有
            if (event.getPlayer() != null) {
                SuperFreedomSurvive.Land.Display.Message(event.getPlayer(), "§c領地禁止 點燃方塊");
            }
            // 禁止事件 改動資料
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    final public void onBlockMultiPlaceEvent(BlockMultiPlaceEvent event) {
        //  當玩家放一個方塊，而連鎖放置了第二個方塊時(例如玩家放置一個床)

        // 檢查是否有權限
        if (SuperFreedomSurvive.Land.Permissions.Inspection(event.getPlayer(), event.getBlock().getLocation(), "Permissions_BlockMultiPlace")) {
            // 有
        } else {
            // 沒有
            SuperFreedomSurvive.Land.Display.Message(event.getPlayer(), "§c領地禁止 放置多架構方塊");
            // 禁止事件 改動資料
            event.setCancelled(true);
        }
    }

    /*
        // 太耗費資源 需要小心
        @EventHandler( priority = EventPriority.HIGHEST )
        final public void onBlockPhysicsEvent(BlockPhysicsEvent event){
            //  方塊物理事件(例如是沙子掉落、流水)
            String name = null;
            if ( event.getChangedType() == Material.WATER ) {
                // 水
                name = "Water";
            } else if ( event.getChangedType() == Material.LAVA ){
                // 岩漿
                name = "Lava";

            } else if (
                    event.getChangedType() == Material.SAND         ||
                    event.getChangedType() == Material.RED_SAND     ||
                    event.getChangedType() == Material.SOUL_SAND    ||
                    event.getChangedType() == Material.GRAVEL
            ){
                // 沙 / 礫石
                name = "Sand";

            }


            if ( name != null ) {
                UserPermissionsExamination( event , event ,"Physical_" + name );
            }
        }
    */
/*
    @EventHandler( priority = EventPriority.HIGHEST )
    final public void onBlockPistonExtendEvent(BlockPistonExtendEvent event){
        //  活塞臂推出
        UserPermissionsExamination( event , event ,"BlockPiston" );
    }

    @EventHandler( priority = EventPriority.HIGHEST )
    final public void onBlockPistonRetractEvent(BlockPistonRetractEvent event){
        //  活塞臂縮回
        UserPermissionsExamination( event , event ,"BlockPiston" );
    }
*/
    @EventHandler(priority = EventPriority.HIGHEST)
    final public void onBlockPlaceEvent(BlockPlaceEvent event) {
        //  當一個方塊被玩家放置的時候

        // 檢查是否有權限
        if (SuperFreedomSurvive.Land.Permissions.Inspection(event.getPlayer(), event.getBlock().getLocation(), "Permissions_BlockPlace")) {
            // 有
        } else {
            // 沒有
            SuperFreedomSurvive.Land.Display.Message(event.getPlayer(), "§c領地禁止 放置方塊");
            // 禁止事件 改動資料
            event.setCancelled(true);
        }
    }

    /*
        @EventHandler
        final public void onBlockRedstoneEvent(BlockRedstoneEvent event){
            //  當方塊接受到的紅石信號變化時
            int X = (int)event.getBlock().getLocation().getX();
            int Y = (int)event.getBlock().getLocation().getY();
            int Z = (int)event.getBlock().getLocation().getZ();

            try {
                Connection con = MySQL.getConnection(); // 連線 MySQL
                Statement sta = con.createStatement(); // 取得控制庫

                // 檢查是否在保護區域內
                ResultSet res = sta.executeQuery("SELECT (Permissions_BlockRedstone) FROM `land-protection_data` WHERE `min_X` <= '" + X + "' AND `min_Y` <= '" + Y + "' AND `min_Z` <= '" + Z + "' AND `max_X` >= '" + X + "' AND `max_Y` >= '" + Y + "' AND `max_Z` >= '" + Z + "' ORDER BY `Level` DESC LIMIT 1");
                // 跳到最後一行
                res.last();
                // 最後一行 行數 是否 > 0
                if( res.getRow() > 0 ) {
                    // 有資料
                    // 跳回 初始行 必須使用 next() 才能取得資料
                    res.beforeFirst();
                    res.next();
                    if( res.getBoolean("Permissions_" + "BlockRedstone" ) ){
                        // 權限 開放
                        // res.getBoolean()

                    } else {
                        // 權限 封閉
                        // 禁止事件 改動資料
                        event.setNewCurrent(0);
                    }

                } else {
                    // 無資料

                }
            } catch (Exception ex) {
                // 出錯
            }
        }

        @EventHandler( priority = EventPriority.HIGHEST )
        final public void onBlockSpreadEvent(BlockSpreadEvent event){
            //  當一個方塊基於自然法則地蔓延時(菌絲蔓延)
            UserPermissionsExamination( event , event ,"BlockSpread" );
        }

        @EventHandler( priority = EventPriority.HIGHEST )
        final public void onCauldronLevelChangeEvent(CauldronLevelChangeEvent event){
            //  鍋釜水位發生變化
            UserPermissionsExamination( event , event ,"CauldronLevelChange" );
        }

        @EventHandler( priority = EventPriority.HIGHEST )
        final public void onLeavesDecayEvent(LeavesDecayEvent event){
            //  當樹葉消失時
            UserPermissionsExamination( event , event ,"LeavesDecay" );
        }

        @EventHandler( priority = EventPriority.HIGHEST )
        final public void onMoistureChangeEvent(MoistureChangeEvent event){
            //  土壤溼度改變
            UserPermissionsExamination( event , event ,"MoistureChange" );
        }

        @EventHandler( priority = EventPriority.HIGHEST )
        final public void onNotePlayEvent(NotePlayEvent event){
            //  音符盒播放音符
            UserPermissionsExamination( event , event ,"NotePlay" );
        }
    */
    @EventHandler(priority = EventPriority.HIGHEST)
    final public void onSignChangeEvent(SignChangeEvent event) {
        //  在玩家設置牌子上的內容子時觸發

        // 檢查是否有權限
        if (SuperFreedomSurvive.Land.Permissions.Inspection(event.getPlayer(), event.getBlock().getLocation(), "Permissions_SignChange")) {
            // 有
        } else {
            // 沒有
            SuperFreedomSurvive.Land.Display.Message(event.getPlayer(), "§c領地禁止 告示牌編輯");
            // 禁止事件 改動資料
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    final public void onSpongeAbsorbEvent(SpongeAbsorbEvent event) {
        //  海綿吸水
        int Min_X = 0;
        int Min_Y = 0;
        int Min_Z = 0;
        int Max_X = 0;
        int Max_Y = 0;
        int Max_Z = 0;
        List list = event.getBlocks();
        String world = null;
        // 取得最大 和 最小距離
        for (int i = 0, s = list.size(); i < s; ++i) {
            Block block = ((BlockState) list.get(i)).getBlock();
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
                        " AND `Permissions_SpongeAbsorb` = '0' ORDER BY `Level` LIMIT 2");
                // 跳到最後一行
                res.last();
                // 最後一行 行數 是否 > 0
                if (res.getRow() > 0) {
                    // 不允許
                    event.setCancelled(true);
                }

                res.close(); // 關閉查詢
                sta.close(); // 關閉連線

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    final public void onPlayerInteractEvent(PlayerInteractEvent event) {
        // 踐踏農田的時候
        if (event.getAction() == Action.PHYSICAL && event.getClickedBlock().getType() == Material.FARMLAND) {
            // 檢查是否有權限
            if (SuperFreedomSurvive.Land.Permissions.Inspection(null, event.getClickedBlock().getLocation(), "Permissions_TrampleFarmland")) {
                // 有
            } else {
                // 沒有
                event.setCancelled(true);
            }
        }
    }

    // 如果活塞放置的是在領地外 則不可以影響到領地
    @EventHandler(priority = EventPriority.HIGHEST)
    final public void onBlockPistonRetractEvent(BlockPistonExtendEvent event) {
        //  活塞改變此方塊
        try {

            // 資料庫裡面是否有數據
            String ID = null;
            if (SuperFreedomSurvive.Land.Permissions.Is(event.getBlock().getLocation())) {
                ID = SuperFreedomSurvive.Land.Permissions.ID(event.getBlock().getLocation());
            } else {
            }

            // 取得塊列表 並且取得第一塊 與最後一塊 的座標
            int Start_X;
            int Start_Y;
            int Start_Z;

            if ( event.getBlocks() == null ) {
                return;
            }
            if ( event.getBlocks().size() < 1 ) {
                return;
            }

            int X = event.getBlocks().get(0).getX();
            int Y = event.getBlocks().get(0).getY();
            int Z = event.getBlocks().get(0).getZ();

            int End_X = event.getBlocks().get(event.getBlocks().size() - 1).getX();
            int End_Y = event.getBlocks().get(event.getBlocks().size() - 1).getY();
            int End_Z = event.getBlocks().get(event.getBlocks().size() - 1).getZ();

            if (X < End_X) {
                Start_X = X;
            } else {
                Start_X = End_X;
                End_X = X;
            }
            //      Y
            if (Y < End_Y) {
                Start_Y = Y;
            } else {
                Start_Y = End_Y;
                End_Y = Y;
            }
            //      Z
            if (Z < End_Z) {
                Start_Z = Z;
            } else {
                Start_Z = End_Z;
                End_Z = Z;
            }

            String world = event.getBlocks().get(0).getWorld().getName();

            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫
            ResultSet res;

            // 檢查資料
            if (ID != null) {
                res = sta.executeQuery("SELECT (ID) FROM `land-protection_data` WHERE `World` = '" + world + "' AND " +
                        "  ( (   `Min_X` >= '" + Start_X + "' AND `Max_X` <= '" + End_X + "' )" +
                        " OR (   `Min_X` <= '" + Start_X + "' AND `Max_X` >= '" + End_X + "' )" +
                        " OR ( ( `Min_X` >= '" + Start_X + "' AND `Max_X` >= '" + End_X + "' ) AND `Min_X` <= '" + End_X + "' )  " +
                        " OR ( ( `Min_X` <= '" + Start_X + "' AND `Max_X` <= '" + End_X + "' ) AND `Max_X` >= '" + Start_X + "' ) ) AND (" +
                        "    (   `Min_Y` >= '" + Start_Y + "' AND `Max_Y` <= '" + End_Y + "' )" +
                        " OR (   `Min_Y` <= '" + Start_Y + "' AND `Max_Y` >= '" + End_Y + "' )" +
                        " OR ( ( `Min_Y` >= '" + Start_Y + "' AND `Max_Y` >= '" + End_Y + "' ) AND `Min_Y` <= '" + End_Y + "' )  " +
                        " OR ( ( `Min_Y` <= '" + Start_Y + "' AND `Max_Y` <= '" + End_Y + "' ) AND `Max_Y` >= '" + Start_Y + "' ) ) AND (" +
                        "    (   `Min_Z` >= '" + Start_Z + "' AND `Max_Z` <= '" + End_Z + "' )" +
                        " OR (   `Min_Z` <= '" + Start_Z + "' AND `Max_Z` >= '" + End_Z + "' )" +
                        " OR ( ( `Min_Z` >= '" + Start_Z + "' AND `Max_Z` >= '" + End_Z + "' ) AND `Min_Z` <= '" + End_Z + "' )  " +
                        " OR ( ( `Min_Z` <= '" + Start_Z + "' AND `Max_Z` <= '" + End_Z + "' ) AND `Max_Z` >= '" + Start_Z + "' ) ) " +
                        " AND `ID` != '" + ID + "' ORDER BY `Level` LIMIT 2");
            } else {
                res = sta.executeQuery("SELECT (ID) FROM `land-protection_data` WHERE `World` = '" + world + "' AND " +
                        "  ( (   `Min_X` >= '" + Start_X + "' AND `Max_X` <= '" + End_X + "' )" +
                        " OR (   `Min_X` <= '" + Start_X + "' AND `Max_X` >= '" + End_X + "' )" +
                        " OR ( ( `Min_X` >= '" + Start_X + "' AND `Max_X` >= '" + End_X + "' ) AND `Min_X` <= '" + End_X + "' )  " +
                        " OR ( ( `Min_X` <= '" + Start_X + "' AND `Max_X` <= '" + End_X + "' ) AND `Max_X` >= '" + Start_X + "' ) ) AND (" +
                        "    (   `Min_Y` >= '" + Start_Y + "' AND `Max_Y` <= '" + End_Y + "' )" +
                        " OR (   `Min_Y` <= '" + Start_Y + "' AND `Max_Y` >= '" + End_Y + "' )" +
                        " OR ( ( `Min_Y` >= '" + Start_Y + "' AND `Max_Y` >= '" + End_Y + "' ) AND `Min_Y` <= '" + End_Y + "' )  " +
                        " OR ( ( `Min_Y` <= '" + Start_Y + "' AND `Max_Y` <= '" + End_Y + "' ) AND `Max_Y` >= '" + Start_Y + "' ) ) AND (" +
                        "    (   `Min_Z` >= '" + Start_Z + "' AND `Max_Z` <= '" + End_Z + "' )" +
                        " OR (   `Min_Z` <= '" + Start_Z + "' AND `Max_Z` >= '" + End_Z + "' )" +
                        " OR ( ( `Min_Z` >= '" + Start_Z + "' AND `Max_Z` >= '" + End_Z + "' ) AND `Min_Z` <= '" + End_Z + "' )  " +
                        " OR ( ( `Min_Z` <= '" + Start_Z + "' AND `Max_Z` <= '" + End_Z + "' ) AND `Max_Z` >= '" + Start_Z + "' ) ) " +
                        " ORDER BY `Level` LIMIT 2");
            }
            // 跳到最後一行
            res.last();
            // 最後一行 行數 是否 > 0
            if (res.getRow() > 0) {
                // 有資料
                event.setCancelled(true);
            }

            res.close(); // 關閉查詢
            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    final public void onBlockPistonRetractEvent(BlockPistonRetractEvent event) {
        //  活塞改變此方塊
        try {

            // 資料庫裡面是否有數據
            String ID = null;
            if (SuperFreedomSurvive.Land.Permissions.Is(event.getBlock().getLocation())) {
                ID = SuperFreedomSurvive.Land.Permissions.ID(event.getBlock().getLocation());
            } else {
            }

            // 取得塊列表 並且取得第一塊 與最後一塊 的座標
            int Start_X;
            int Start_Y;
            int Start_Z;

            if ( event.getBlocks() == null ) {
                return;
            }
            if ( event.getBlocks().size() < 1 ) {
                return;
            }

            int X = event.getBlocks().get(0).getX();
            int Y = event.getBlocks().get(0).getY();
            int Z = event.getBlocks().get(0).getZ();

            int End_X = event.getBlocks().get(event.getBlocks().size() - 1).getX();
            int End_Y = event.getBlocks().get(event.getBlocks().size() - 1).getY();
            int End_Z = event.getBlocks().get(event.getBlocks().size() - 1).getZ();

            if (X < End_X) {
                Start_X = X;
            } else {
                Start_X = End_X;
                End_X = X;
            }
            //      Y
            if (Y < End_Y) {
                Start_Y = Y;
            } else {
                Start_Y = End_Y;
                End_Y = Y;
            }
            //      Z
            if (Z < End_Z) {
                Start_Z = Z;
            } else {
                Start_Z = End_Z;
                End_Z = Z;
            }

            String world = event.getBlocks().get(0).getWorld().getName();

            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫
            ResultSet res;

            // 檢查資料
            if (ID != null) {
                res = sta.executeQuery("SELECT (ID) FROM `land-protection_data` WHERE `World` = '" + world + "' AND " +
                        "  ( (   `Min_X` >= '" + Start_X + "' AND `Max_X` <= '" + End_X + "' )" +
                        " OR (   `Min_X` <= '" + Start_X + "' AND `Max_X` >= '" + End_X + "' )" +
                        " OR ( ( `Min_X` >= '" + Start_X + "' AND `Max_X` >= '" + End_X + "' ) AND `Min_X` <= '" + End_X + "' )  " +
                        " OR ( ( `Min_X` <= '" + Start_X + "' AND `Max_X` <= '" + End_X + "' ) AND `Max_X` >= '" + Start_X + "' ) ) AND (" +
                        "    (   `Min_Y` >= '" + Start_Y + "' AND `Max_Y` <= '" + End_Y + "' )" +
                        " OR (   `Min_Y` <= '" + Start_Y + "' AND `Max_Y` >= '" + End_Y + "' )" +
                        " OR ( ( `Min_Y` >= '" + Start_Y + "' AND `Max_Y` >= '" + End_Y + "' ) AND `Min_Y` <= '" + End_Y + "' )  " +
                        " OR ( ( `Min_Y` <= '" + Start_Y + "' AND `Max_Y` <= '" + End_Y + "' ) AND `Max_Y` >= '" + Start_Y + "' ) ) AND (" +
                        "    (   `Min_Z` >= '" + Start_Z + "' AND `Max_Z` <= '" + End_Z + "' )" +
                        " OR (   `Min_Z` <= '" + Start_Z + "' AND `Max_Z` >= '" + End_Z + "' )" +
                        " OR ( ( `Min_Z` >= '" + Start_Z + "' AND `Max_Z` >= '" + End_Z + "' ) AND `Min_Z` <= '" + End_Z + "' )  " +
                        " OR ( ( `Min_Z` <= '" + Start_Z + "' AND `Max_Z` <= '" + End_Z + "' ) AND `Max_Z` >= '" + Start_Z + "' ) ) " +
                        " AND `ID` != '" + ID + "' ORDER BY `Level` LIMIT 2");
            } else {
                res = sta.executeQuery("SELECT (ID) FROM `land-protection_data` WHERE `World` = '" + world + "' AND " +
                        "  ( (   `Min_X` >= '" + Start_X + "' AND `Max_X` <= '" + End_X + "' )" +
                        " OR (   `Min_X` <= '" + Start_X + "' AND `Max_X` >= '" + End_X + "' )" +
                        " OR ( ( `Min_X` >= '" + Start_X + "' AND `Max_X` >= '" + End_X + "' ) AND `Min_X` <= '" + End_X + "' )  " +
                        " OR ( ( `Min_X` <= '" + Start_X + "' AND `Max_X` <= '" + End_X + "' ) AND `Max_X` >= '" + Start_X + "' ) ) AND (" +
                        "    (   `Min_Y` >= '" + Start_Y + "' AND `Max_Y` <= '" + End_Y + "' )" +
                        " OR (   `Min_Y` <= '" + Start_Y + "' AND `Max_Y` >= '" + End_Y + "' )" +
                        " OR ( ( `Min_Y` >= '" + Start_Y + "' AND `Max_Y` >= '" + End_Y + "' ) AND `Min_Y` <= '" + End_Y + "' )  " +
                        " OR ( ( `Min_Y` <= '" + Start_Y + "' AND `Max_Y` <= '" + End_Y + "' ) AND `Max_Y` >= '" + Start_Y + "' ) ) AND (" +
                        "    (   `Min_Z` >= '" + Start_Z + "' AND `Max_Z` <= '" + End_Z + "' )" +
                        " OR (   `Min_Z` <= '" + Start_Z + "' AND `Max_Z` >= '" + End_Z + "' )" +
                        " OR ( ( `Min_Z` >= '" + Start_Z + "' AND `Max_Z` >= '" + End_Z + "' ) AND `Min_Z` <= '" + End_Z + "' )  " +
                        " OR ( ( `Min_Z` <= '" + Start_Z + "' AND `Max_Z` <= '" + End_Z + "' ) AND `Max_Z` >= '" + Start_Z + "' ) ) " +
                        " ORDER BY `Level` LIMIT 2");
            }
            // 跳到最後一行
            res.last();
            // 最後一行 行數 是否 > 0
            if (res.getRow() > 0) {
                // 有資料
                event.setCancelled(true);
            }

            res.close(); // 關閉查詢
            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

/*
    @EventHandler(priority = EventPriority.HIGHEST)
    final public void onInventoryPickupItemEvent(InventoryPickupItemEvent event) {
        // 漏斗傳遞物品

        // 檢查是否有權限
        if (ServerPlugin.Land.Permissions.Inspection(null, event.getInventory().getLocation(), "Permissions_InventoryPickupItem")) {
            // 有
            Bukkit.getLogger().info("y");
        } else {
            Bukkit.getLogger().info("n");
            // 沒有
            event.setCancelled(true);
        }
    }

 */
}
