package SuperFreedomSurvive.Additional;

import SuperFreedomSurvive.SYSTEM.MySQL;
import SuperFreedomSurvive.World.Data;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

final public class WoodAxe implements Listener {
    // 小木斧

    // 檢查手持武器
    @EventHandler( ignoreCancelled = true)
    final public void onPlayerInteractEvent(PlayerInteractEvent event) {
        // 沒有被取消
        try {

            if (event.getItem() == null) { return; }

            // 檢查是否為木鋤 或是 木鏟
            if (event.getItem().getType() == Material.WOODEN_AXE) {
                // 是

                Player player = event.getPlayer();

                // 是否有購買特殊效果

                // 是否有購買補助效果
                if (Event.Have(event.getPlayer(), "Function_Wood-Axe_Time")) {


                    ItemStack hand_item = player.getInventory().getItemInOffHand();
                    int X = (int) event.getClickedBlock().getLocation().getX();
                    int Y = (int) event.getClickedBlock().getLocation().getY();
                    int Z = (int) event.getClickedBlock().getLocation().getZ();
                    String world = event.getClickedBlock().getLocation().getWorld().getName();

                    // 是否在自己有權限的領地
                    if (
                            SuperFreedomSurvive.Land.Permissions.Ownership(player, event.getClickedBlock().getLocation()) ||
                            SuperFreedomSurvive.Land.Permissions.LandShare(player, event.getClickedBlock().getLocation()) ||
                            SuperFreedomSurvive.Player.Permissions.Inspection(player, "Permissions_Land")
                    ) {

                        event.setCancelled(true);

                        // 檢查是左手點擊 還是右手點擊
                        if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
                            // 左手點擊一个方塊
                            // 規劃開始


                            // 是否有標籤
                            if (hand_item.hasItemMeta()) {
                                player.sendMessage("[小木斧]  §c有特殊標籤 不可放置");

                            } else if (
                                    hand_item.getType().getHardness() < 0 &&
                                            hand_item.getType() != Material.BARRIER
                            ) {
                                player.sendMessage("[小木斧]  §c無法破壞的方塊 不可放置");

                            } else {

                                if (hand_item.getType().isBlock()) {

                                        /*
                                        if (hand_item.getType() == Material.AIR) {
                                            player.sendMessage("[小木斧]  副手為空 則模式為清除 方塊不掉落");
                                        } else {
                                            player.sendMessage("[小木斧]  副手為方塊 則模式為放置 只替換空氣");
                                        }
                                        */

                                    if (player.getGameMode() == GameMode.CREATIVE) {
                                        if (hand_item.getType() == Material.AIR) {
                                            player.sendMessage("[小木斧]  起點  副手 >> 空氣");
                                        } else {
                                            player.sendMessage("[小木斧]  起點  副手 >> 方塊  背包中有 無限 個");
                                        }

                                    } else {
                                        // 檢查玩家身上物資夠量
                                        int amount = 0;
                                        PlayerInventory inventory = player.getInventory();
                                        HashMap all = inventory.all(hand_item.getType());
                                        for (Object key : all.keySet()) {
                                            amount = amount + ((ItemStack) all.get(key)).getAmount();
                                        }

                                        if (hand_item.getType() == Material.AIR) {
                                            player.sendMessage("[小木斧]  起點  副手 >> 空氣");
                                        } else {
                                            player.sendMessage("[小木斧]  起點  副手 >> 方塊  背包中有 " + amount + " 個 ");
                                        }

                                    }
                                    Connection con = MySQL.getConnection(); // 連線 MySQL
                                    Statement sta = con.createStatement(); // 取得控制庫

                                    sta.executeUpdate("DELETE FROM `player-additional-wood-axe_cache` WHERE `Player` = '" + player.getName() + "'");
                                    sta.executeUpdate("INSERT INTO `player-additional-wood-axe_cache` (`World`, `Player`, `X`, `Y`, `Z`, `Material`, `ID`) VALUES ('" + world + "','" + player.getName() + "', '" + X + "', '" + Y + "', '" + Z + "', '" + hand_item.getType().name() + "', '" + SuperFreedomSurvive.Land.Permissions.ID(event.getClickedBlock().getLocation()) + "')");

                                    sta.close(); // 關閉連線

                                } else {
                                    player.sendMessage("[小木斧]  §c副手必須是方塊");
                                }
                            }


                        } else if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                            // 右手點擊一个方塊

                            //player.sendMessage("[小木斧]  副手為空 將要快速創建的方塊類型放在副手");
/*
                                Connection con = MySQL.getConnection(); // 連線 MySQL
                                Statement sta = con.createStatement(); // 取得控制庫

                                sta.executeUpdate("DELETE FROM `player-additional-wood-axe_cache` WHERE `Player` = '" + player.getName() + "'");
                                sta.executeUpdate("INSERT INTO `player-additional-wood-axe_cache` (`World`, `Player`, `X`, `Y`, `Z`, `Material`) VALUES ('" + world + "','" + player.getName() + "', '" + X + "', '" + Y + "', '" + Z + "', 'AIR')");
*/


                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    // 點擊方塊
    @EventHandler(ignoreCancelled = true)
    final public void onVehicleCreateEvent(PlayerInteractEvent event) {
        try {
            // 是否為右鍵類型
            if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (event.getHand() == EquipmentSlot.OFF_HAND) {
                    // 檢查是否為木鋤 或是 木鏟
                    if (event.getPlayer().getInventory().getItemInMainHand().getType() == Material.WOODEN_AXE) {
                        // 是

                        Player player = event.getPlayer();

                        // 是否有購買特殊效果

                        // 是否有購買補助效果
                        if (Event.Have(event.getPlayer(), "Function_Wood-Axe_Time")) {

                            ItemStack hand_item = player.getInventory().getItemInOffHand();
                            int X = (int) event.getClickedBlock().getLocation().getX();
                            int Y = (int) event.getClickedBlock().getLocation().getY();
                            int Z = (int) event.getClickedBlock().getLocation().getZ();
                            String world = event.getClickedBlock().getLocation().getWorld().getName();

                            // 是否在自己有權限的領地
                            if (
                                    SuperFreedomSurvive.Land.Permissions.Ownership(player, event.getClickedBlock().getLocation()) ||
                                            SuperFreedomSurvive.Land.Permissions.LandShare(player, event.getClickedBlock().getLocation()) ||
                                            SuperFreedomSurvive.Player.Permissions.Inspection(player, "Permissions_Land")
                            ) {
                                // 規劃結束

                                event.setCancelled(true);

                                // 是否有標籤
                                if (hand_item.hasItemMeta()) {
                                    player.sendMessage("[小木斧]  §c有特殊標籤 不可放置");

                                } else if (
                                        hand_item.getType().getHardness() < 0 &&
                                                hand_item.getType() != Material.BARRIER
                                ) {
                                    player.sendMessage("[小木斧]  §c無法破壞的方塊 不可放置");

                                } else {

                                    Connection con = MySQL.getConnection(); // 連線 MySQL
                                    Statement sta = con.createStatement(); // 取得控制庫

                                    ResultSet res = sta.executeQuery("SELECT * FROM `player-additional-wood-axe_cache` WHERE `Player` = '" + player.getName() + "' LIMIT 1");
                                    res.last();
                                    // 最後一行 行數 是否 > 0
                                    if (res.getRow() > 0) {
                                        // 有資料
                                        // 跳回 初始行 必須使用 next() 才能取得資料
                                        res.beforeFirst();
                                        res.next();

                                        int Start_X = X;
                                        int Start_Y = Y;
                                        int Start_Z = Z;
                                        int End_X = res.getInt("X");
                                        int End_Y = res.getInt("Y");
                                        int End_Z = res.getInt("Z");
                                        String ID = res.getString("ID");
                                        int Level = SuperFreedomSurvive.Land.Permissions.Level(ID);
                                        long need = (Math.abs(End_X - Start_X) + 1) * (Math.abs(End_Y - Start_Y) + 1) * (Math.abs(End_Z - Start_Z) + 1);

                                        if (res.getString("World").equals(player.getLocation().getWorld().getName())) {

                                            res.close(); // 關閉查詢

                                            if (
                                                    ID.equals(SuperFreedomSurvive.Land.Permissions.ID(event.getClickedBlock().getLocation())) ||
                                                            SuperFreedomSurvive.Player.Permissions.Inspection(player, "Permissions_Land")
                                            ) {
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

                                                if (
                                                        hand_item.getType().name().matches(".*GLASS.*") &&
                                                                player.getGameMode() == GameMode.CREATIVE &&
                                                                need > 10000000
                                                ) {
                                                    // ! 超過
                                                    event.getPlayer().sendMessage("[小木斧]  §c太大了 創造模式放置 >> 玻璃方塊  總塊數已經超越 10,000,000 塊");

                                                } else if (
                                                        hand_item.getType() != Material.AIR &&
                                                                !hand_item.getType().name().matches(".*GLASS.*") &&
                                                                player.getGameMode() == GameMode.CREATIVE &&
                                                                need > 100000
                                                ) {
                                                    // ! 超過
                                                    event.getPlayer().sendMessage("[小木斧]  §c太大了 創造模式放置 >> 非玻璃方塊  總塊數已經超越 100,000 塊");

                                                } else if (
                                                        hand_item.getType() == Material.AIR &&
                                                                player.getGameMode() == GameMode.CREATIVE &&
                                                                need > 10000000
                                                ) {
                                                    // ! 超過
                                                    event.getPlayer().sendMessage("[小木斧]  §c太大了 創造模式放置 >> 空氣  總塊數已經超越 10,000,000 塊");

                                                } else if (
                                                        hand_item.getType() == Material.AIR &&
                                                                player.getGameMode() != GameMode.CREATIVE &&
                                                                need > 216000
                                                ) {
                                                    // ! 超過
                                                    event.getPlayer().sendMessage("[小木斧]  §c太大了 放置 >> 空氣  總塊數已經超越 216,000 塊");

                                                } else if (
                                                        hand_item.getType() != Material.AIR &&
                                                                player.getGameMode() != GameMode.CREATIVE &&
                                                                need > 2304
                                                ) {
                                                    // ! 超過
                                                    event.getPlayer().sendMessage("[小木斧]  §c太大了 放置 >> 方塊  總塊數已經超越 2,304 塊");

                                                } else {

                                                    // 檢查是否附蓋到其他領地
                                                    res = sta.executeQuery("SELECT (Min_X),(Min_Y),(Min_Z),(Max_X),(Max_Y),(Max_Z),(Owner),(User),(Level) FROM `land-protection_data` WHERE `World` = '" + world + "' AND `ID` != '" + ID + "' AND `Level` > '" + Level + "' AND" +
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

                                                    // 跳到最後一行
                                                    res.last();
                                                    boolean ok = true;
                                                    // 最後一行 行數 是否 > 0
                                                    if (res.getRow() > 0) {
                                                        // ! 是 ! 禁止創建
                                                        ok = false;
                                                    }

                                                    res.close(); // 關閉查詢

                                                    if (ok) {
                                                        if (hand_item.getType().isBlock()) {

                                                            if (player.getGameMode() == GameMode.CREATIVE) {
                                                                if (hand_item.getType() == Material.AIR) {
                                                                    player.sendMessage("[小木斧]  終點  副手 >> 空氣  替換 " + need + " 塊方塊");
                                                                } else {
                                                                    player.sendMessage("[小木斧]  終點  副手 >> 方塊  背包中有 無限 個  替換 " + need + " 塊方塊");
                                                                }

                                                            } else {
                                                                // 檢查玩家身上物資夠量
                                                                int amount = 0;
                                                                PlayerInventory inventory = player.getInventory();
                                                                HashMap all = inventory.all(hand_item.getType());
                                                                for (Object key : all.keySet()) {
                                                                    amount = amount + ((ItemStack) all.get(key)).getAmount();
                                                                }

                                                                if (hand_item.getType() == Material.AIR) {
                                                                    player.sendMessage("[小木斧]  終點  副手 >> 空氣  替換 " + need + " 塊方塊");
                                                                } else {
                                                                    player.sendMessage("[小木斧]  終點  副手 >> 方塊  背包中有 " + amount + " 個  需要 " + need + " 塊方塊");
                                                                }
                                                            }

                                                            player.sendMessage("[小木斧]  §a確定要放置方塊請在 3 秒內按下 shift");

                                                            int finalEnd_X = End_X;
                                                            int finalStart_X = Start_X;
                                                            int finalEnd_Y = End_Y;
                                                            int finalStart_Y = Start_Y;
                                                            int finalEnd_Z = End_Z;
                                                            int finalStart_Z = Start_Z;
                                                            Material material = hand_item.getType();

                                                            new BukkitRunnable() {

                                                                int frequency = 0;

                                                                @Override
                                                                final public void run() {
                                                                    if (frequency > 60) {
                                                                        cancel();
                                                                        return;
                                                                    }

                                                                    try {
                                                                        Connection con = MySQL.getConnection(); // 連線 MySQL
                                                                        Statement sta = con.createStatement(); // 取得控制庫
                                                                        ResultSet res = sta.executeQuery("SELECT * FROM `player-additional-wood-axe_cache` WHERE `Player` = '" + player.getName() + "'");
                                                                        // 跳到最後一行
                                                                        res.last();
                                                                        // 最後一行 行數 是否 > 0
                                                                        if (res.getRow() > 0) {
                                                                            // 有資料

                                                                            res.close(); // 關閉查詢

                                                                            if (player.isSneaking()) {

                                                                                // 計算物資是否足夠
                                                                                boolean enough = false;

                                                                                if (player.getGameMode() == GameMode.CREATIVE) {
                                                                                    enough = true;
                                                                                } else if (material == Material.AIR) {
                                                                                    enough = true;
                                                                                } else {
                                                                                    enough = SuperFreedomSurvive.Player.Pay.Have( player , (int)need , material );
                                                                                }

                                                                                if (enough) {

                                                                                    if (player.getGameMode() == GameMode.CREATIVE) {

                                                                                    } else if (hand_item.getType() == Material.AIR) {

                                                                                    } else {
                                                                                        // 收取
                                                                                        if ( SuperFreedomSurvive.Player.Pay.Recover( player , (int)need , material ) ) {
                                                                                            // 成功
                                                                                        } else {
                                                                                            // 失敗
                                                                                            player.sendMessage("[小木斧]  §c方塊量不足 需要 " + need + " 個");

                                                                                            cancel();
                                                                                            return;
                                                                                        }
                                                                                    }


                                                                                    res = sta.executeQuery("Select Count(*) FROM `player-additional-wood-axe_data`");
                                                                                    res.next();
                                                                                    player.sendMessage("[小木斧]  已加入等待列隊 目前一共有 " + res.getInt("Count(*)") + " 個任務正在排隊");

                                                                                    res.close(); // 關閉查詢

                                                                                    sta.executeUpdate("DELETE FROM `player-additional-wood-axe_cache` WHERE `Player` = '" + player.getName() + "'");
                                                                                    sta.executeUpdate("INSERT INTO `player-additional-wood-axe_data` (`World`, `Start_X`, `Start_Y`, `Start_Z`, `End_X`, `End_Y`, `End_Z`, `Material`, `Time`) VALUES ('" + world + "', '" + finalStart_X + "', '" + finalStart_Y + "', '" + finalStart_Z + "', '" + finalEnd_X + "', '" + finalEnd_Y + "', '" + finalEnd_Z + "', '" + material.name() + "', '" + SuperFreedomSurvive.SYSTEM.Time.Get() + "')");


                                                                                } else {
                                                                                    player.sendMessage("[小木斧]  §c方塊量不足 需要 " + need + " 個");

                                                                                }

                                                                                res.close(); // 關閉查詢
                                                                                sta.close(); // 關閉連線

                                                                                cancel();
                                                                                return;
                                                                            }
                                                                        }

                                                                        res.close(); // 關閉查詢
                                                                        sta.close(); // 關閉連線

                                                                    } catch (Exception ex) {
                                                                        ex.printStackTrace();
                                                                        cancel();
                                                                        return;
                                                                    }

                                                                    frequency++;
                                                                }
                                                            }.runTaskTimer(SuperFreedomSurvive.SYSTEM.Plugin.get(), 0L, 1L);
                                                        } else {
                                                            player.sendMessage("[小木斧]  §c副手必須是方塊");
                                                        }

                                                    } else {
                                                        player.sendMessage("[小木斧]  §c不可涵蓋到其他領地/子領地");
                                                    }
                                                }
                                            } else {
                                                player.sendMessage("[小木斧]  §c禁止跨領地/子領地");
                                            }
                                        } else {
                                            player.sendMessage("[小木斧]  §c禁止跨世界規劃");
                                        }

                                    } else {
                                        player.sendMessage("[小木斧]  §c請先左鍵規劃 起點 位置");
                                    }

                                    res.close(); // 關閉查詢
                                    sta.close(); // 關閉連線
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    static boolean now = false;
    static String now_world = null;
    static boolean stop = false;
    //static final public int schedule = 0;

    static final public void Stop() {
        stop = true;
    }

    static final public String GetRunWorld() {
        return now_world;
    }

    // 開始創建
    static final public void Start() {
        // 迴圈
        new BukkitRunnable() {
            @Override
            final public void run() {

                if (!Bukkit.getPluginManager().isPluginEnabled(SuperFreedomSurvive.SYSTEM.Plugin.get())) {
                    // 插件停止
                    cancel();
                    return;
                }

                if (now) {

                } else {
                    try {
                        Connection con = MySQL.getConnection(); // 連線 MySQL
                        Statement sta = con.createStatement(); // 取得控制庫

                        ResultSet res = sta.executeQuery("SELECT * FROM `player-additional-wood-axe_data` ORDER BY `Time` LIMIT 1");
                        res.last();
                        // 最後一行 行數 是否 > 0
                        if (res.getRow() > 0) {
                            // 有資料
                            // 跳回 初始行 必須使用 next() 才能取得資料
                            res.beforeFirst();
                            res.next();

                            now = true;
                            now_world = res.getString("World");

                            new BukkitRunnable() {

                                int Start_X = res.getInt("Start_X");
                                int Start_Y = res.getInt("Start_Y");
                                int Start_Z = res.getInt("Start_Z");
                                int End_X = res.getInt("End_X");
                                int End_Y = res.getInt("End_Y");
                                int End_Z = res.getInt("End_Z");

                                String material = res.getString("Material");
                                String world = res.getString("World");
                                int schedule_X = 0;
                                int schedule_Y = 0;
                                int schedule_Z = 0;
                                World _world_ = null;
                                Block block;

                                @Override
                                final public void run() {
                                    // 生成

                                    // 世界是否已加載
                                    if (SuperFreedomSurvive.World.List.Have(world)) {

                                        if ( _world_ == null ) _world_ = Bukkit.getWorld(world);

                                        if (stop) {
                                            stop = false;

                                            now = false;
                                            now_world = null;

                                            cancel();
                                            return;
                                        } else {

                                            int L = 0;
                                            if (
                                                    material.matches(".*GLASS.*") && !Material.valueOf(material).isOccluding()
                                            ) {
                                                // 玻璃
                                                L = 500;

                                            } else if (Material.valueOf(material) == Material.AIR) {
                                                // 空氣
                                                L = 500;

                                            } else {
                                                // 其他
                                                L = 5;

                                            }

                                            for (int i = 0; i < L; i++) {
                                                if (Start_Z + schedule_Z > End_Z) {
                                                    // 刪除本次資料
                                                    try {
                                                        Connection con = MySQL.getConnection(); // 連線 MySQL
                                                        Statement sta = con.createStatement(); // 取得控制庫

                                                        sta.executeUpdate("DELETE FROM `block-special_data` WHERE `World` = '" + world + "' AND `X` >= '" + Start_X + "' AND `Y` >= '" + Start_Y + "' AND `Z` >= '" + Start_Z + "' AND `X` <= '" + End_X + "' AND `Y` <= '" + End_Y + "' AND `Z` <= '" + End_Z + "'");

                                                        sta.executeUpdate("DELETE FROM `player-additional-wood-axe_data` ORDER BY `Time` LIMIT 1");

                                                        sta.close(); // 關閉連線

                                                        now = false;
                                                        now_world = null;
                                                        _world_ = null;
                                                        block = null;

                                                    } catch (Exception ex) {
                                                        ex.printStackTrace();
                                                        cancel();
                                                        return;

                                                    }
                                                    cancel();
                                                    return;
                                                }


                                                // 設定方塊
                                                if ( _world_ == null ) continue;

                                                block = _world_.getBlockAt(
                                                        Start_X + schedule_X,
                                                        Start_Y + schedule_Y,
                                                        Start_Z + schedule_Z
                                                );

                                                // 方塊 是否可破壞 及 放置的方塊 是否可破壞
                                                if (block.getType().getHardness() >= 0) {
                                                    block.setType(Material.valueOf(material), false);

                                                } else if (block.getType() == Material.BARRIER) {
                                                    block.setType(Material.valueOf(material), false);

                                                }

                                                ++schedule_X;

                                                if (Start_X + schedule_X > End_X) {
                                                    schedule_X = 0;
                                                    schedule_Y++;
                                                }

                                                if (Start_Y + schedule_Y > End_Y) {
                                                    schedule_Y = 0;
                                                    schedule_Z++;
                                                }
                                            }
                                        }
                                    } else {
                                        _world_ = null;

                                        if (stop) {
                                            stop = false;

                                            now = false;
                                            now_world = null;

                                            cancel();
                                            return;
                                        } else {
                                            Data.Load(world);
                                        }
                                    }
                                }
                            }.runTaskTimer(SuperFreedomSurvive.SYSTEM.Plugin.get(), 0L, 1L);
                        }

                        res.close(); // 關閉查詢
                        sta.close(); // 關閉連線

                    } catch (Exception ex) {
                        ex.printStackTrace();
                        cancel();
                        return;
                    }
                }
            }
        }.runTaskTimer(SuperFreedomSurvive.SYSTEM.Plugin.get(), 0L, 1L);
    }
}
