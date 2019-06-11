package SuperFreedomSurvive.Definition;

import SuperFreedomSurvive.SYSTEM.MySQL;
import org.bukkit.event.EventHandler;
import SuperFreedomSurvive.Definition.Block.*;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.FixedMetadataValue;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

final public class UseBlock implements Listener {

    static final public org.bukkit.metadata.FixedMetadataValue block_key(String value) {
        return new FixedMetadataValue(SuperFreedomSurvive.SYSTEM.Plugin.get(), value);
    }


    @EventHandler(ignoreCancelled = true)
    final public void onBlockBreakEvent(BlockBreakEvent event) {
        //  當一個方塊被玩家破壞的時候
        // 資料庫裡面是否有數據
        if (SuperFreedomSurvive.Block.Data.Have(event.getBlock().getLocation())) {
            // 有
            // 檢查是否有權限
            if (SuperFreedomSurvive.Land.Permissions.Inspection(event.getPlayer(), event.getBlock().getLocation(), "Permissions_BlockBreak")) {
                // 有
                // 取得數據
                switch (SuperFreedomSurvive.Block.Data.Get(event.getBlock().getLocation())) {
                    case "lucky_block":
                        // 幸運方塊
                        lucky_block.Use(event);
                        break;
                    case "spawner_block":
                        // 生怪磚
                        spawner_block.Use(event);
                        break;
                    case "custom_spawner_block":
                        // 自訂怪物生怪磚
                        custom_spawner_block.Use(event);
                        break;



                }
            }
        }
    }


    @EventHandler(ignoreCancelled = true)
    final public void onBlockDamageEvent(BlockDamageEvent event) {
        //  當一個方塊被玩家左鍵點擊的時候
        // 檢查是否有權限
        if (SuperFreedomSurvive.Land.Permissions.Inspection(event.getPlayer(), event.getBlock().getLocation(), "Permissions_BlockDamage")) {
            // 有

            // 資料庫裡面是否有數據
            if (SuperFreedomSurvive.Block.Data.Have(event.getBlock().getLocation())) {
                // 有

                // 取得數據
                switch (SuperFreedomSurvive.Block.Data.Get(event.getBlock().getLocation())) {



                }
            } else {
                switch (event.getBlock().getType().name()) {
                    case "BARRIER":
                        // 屏障
                        barrier_block.Use(event);


                }
            }
        }
    }

    // 點擊方塊
    @EventHandler()
    final public void onPlayerInteractEvent(PlayerInteractEvent event) {
        //  當一個方塊被玩家右鍵點擊的時候

        if ( event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK ) {

            try {
                if (event.getClickedBlock() == null) {
                    return;
                }

                // 資料庫裡面是否有數據
                if (SuperFreedomSurvive.Block.Data.Have(event.getClickedBlock().getLocation())) {
                    // 有

                    // 取得數據
                    switch (SuperFreedomSurvive.Block.Data.Get(event.getClickedBlock().getLocation())) {
                        case "custom_spawner_block":
                            custom_spawner_block.Use(event);
                            break;


                    }
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    final public void onBlockPistonExtendEvent(BlockPistonExtendEvent event) {
        //  活塞改變此方塊
        try {
            // 資料庫裡面是否有數據

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

            // 檢查是否資料符合
            ResultSet res = sta.executeQuery("SELECT (Data) FROM `block-special_data` WHERE `World` = '" + world + "' AND `X` >= '" + Start_X + "' AND `Y` >= '" + Start_Y + "' AND `Z` >= '" + Start_Z + "' AND `X` <= '" + End_X + "' AND `Y` <= '" + End_Y + "' AND `Z` <= '" + End_Z + "'");
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


    @EventHandler(ignoreCancelled = true)
    final public void onBlockPistonRetractEvent(BlockPistonRetractEvent event) {
        //  活塞改變此方塊
        try {

            // 資料庫裡面是否有數據

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

            // 檢查是否資料符合
            ResultSet res = sta.executeQuery("SELECT (Data) FROM `block-special_data` WHERE `World` = '" + world + "' AND `X` >= '" + Start_X + "' AND `Y` >= '" + Start_Y + "' AND `Z` >= '" + Start_Z + "' AND `X` <= '" + End_X + "' AND `Y` <= '" + End_Y + "' AND `Z` <= '" + End_Z + "'");
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


}
