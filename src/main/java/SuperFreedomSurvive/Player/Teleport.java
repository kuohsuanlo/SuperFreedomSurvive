package SuperFreedomSurvive.Player;

import SuperFreedomSurvive.Additional.Event;
import SuperFreedomSurvive.SYSTEM.MySQL;
import SuperFreedomSurvive.World.Data;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


final public class Teleport {

    final static public void Location(Player player, String name, int X, int Y, int Z) {

        Data.Load(name); // 調用地圖載入

        if (!Data.Have(name)) {
            return;
        }

        new BukkitRunnable() {
            int total_time = 0; // 總時間
            int max_time = 400; // 最大可等待時間 20秒

            @Override
            final public void run() {

                try {

                    // 是否已經超出時間了
                    if (total_time > max_time) {
                        cancel(); // 關閉線程
                        return;
                    } else {
                        // 檢查註冊表中是否有此世界
                        if (SuperFreedomSurvive.World.List.Have(name)) {
                            // 有

                            // 座標
                            Location location = new Location(
                                    Bukkit.getWorld(name),
                                    X + 0.5,
                                    Y,
                                    Z + 0.5
                            );

                            Tp(player, location); // 進行傳送


                            cancel(); // 關閉線程
                            return;

                        } else {
                            // 沒有
                        }
                    }

                    ++total_time; // 時間 + 1

                } catch (Exception ex) {
                    ex.printStackTrace();
                    cancel(); // 關閉線程
                    return;
                }

            }
        }.runTaskTimer(SuperFreedomSurvive.SYSTEM.Plugin.get(), 0L, 1L);
    }


    static final public void SpawnLocation(Player player, String name) {

        Data.Load(name); // 調用地圖載入

        if (!Data.Have(name)) {
            return;
        }

        new BukkitRunnable() {
            int total_time = 0; // 總時間
            int max_time = 400; // 最大可等待時間 20秒

            @Override
            final public void run() {

                try {

                    // 是否已經超出時間了
                    if (total_time > max_time) {
                        cancel(); // 關閉線程
                        return;
                    } else {

                        // 檢查註冊表中是否有此世界
                        if (SuperFreedomSurvive.World.List.Have(name)) {
                            // 有

                            Location location = Bukkit.getWorld(name).getSpawnLocation(); // 座標

                            Tp(player, location); // 進行傳送

                            cancel(); // 關閉線程
                            return;


                        } else {
                            // 沒有
                        }
                    }

                    ++total_time; // 時間 + 1

                } catch (Exception ex) {
                    ex.printStackTrace();
                    cancel(); // 關閉線程
                    return;
                }
            }
        }.runTaskTimer(SuperFreedomSurvive.SYSTEM.Plugin.get(), 0L, 1L);
    }


    // 正式使用 tp
    final static public void Tp(Player player, Location location) {

        try {
            Connection con;
            Statement sta;
            ResultSet res;

            Data.Setting(location.getWorld()); // 設定世界

            // 把所有坐騎傳送到同樣地點
            Entity vehicle = player.getVehicle();
            if (vehicle != null) {
                player.leaveVehicle();
                vehicle.teleportAsync(location);
                // 傳送
                player.teleportAsync(location); // 傳送玩家過去
            } else {
                // 傳送
                player.teleportAsync(location); // 傳送玩家過去
            }


            // 玩家騎上
            if (vehicle != null) {
                vehicle.addPassenger(player);
            }

            con = MySQL.getConnection(); // 連線 MySQL
            sta = con.createStatement(); // 取得控制庫
            res = sta.executeQuery("SELECT * FROM `world-list_data` WHERE `Enable` = '1' AND `Name` = '" + location.getWorld().getName() + "' LIMIT 1");
            // 跳到最後一行
            res.last();
            // 最後一行 行數 是否 > 0
            if (res.getRow() > 0) {
                // 有資料
                // 跳回 初始行 必須使用 next() 才能取得資料
                res.beforeFirst();
                res.next();

                int game_mode = res.getInt("GameMode");

                if (game_mode == 2) {
                    // 冒險
                    player.setGameMode(GameMode.ADVENTURE);
                } else if (game_mode == 3) {
                    // 觀察者
                    player.setGameMode(GameMode.SPECTATOR);
                } else {
                    // 都不是 生存
                    player.setGameMode(GameMode.SURVIVAL);
                }

                if (res.getBoolean("KEEP_INVENTORY")) {
                    // 有防噴

                } else {
                    // 沒防噴
                    player.sendMessage("[提示]  §4§l你所在的世界沒有防噴裝保護");
                }

                res.close(); // 關閉查詢

                // 是否在領地內
                if (SuperFreedomSurvive.Land.Permissions.Is(location)) {
                    // 在領地內
                    // 是否有權限
                    if (SuperFreedomSurvive.Land.Permissions.Inspection(player, location, "Permissions_PlayerToggleFlight")) {
                        // 是
                        player.setAllowFlight(true); // 允許飛行
                    } else { // 不是
                        // 沒有
                        player.setFlying(false); // 禁止飛行
                        player.setAllowFlight(false); // 禁止飛行

                    }
                } else {
                    // 不再領地內
                    // 是否有購買補助效果
                    if (Event.Have(player, "Function_Flight_Time")) {
                        player.setAllowFlight(true); // 允許飛行
                    } else {
                        player.setFlying(false); // 禁止飛行
                        player.setAllowFlight(false); // 禁止飛行
                    }
                }
            }

            res.close(); // 關閉查詢
            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        /*
        if ( ServerPlugin.Player.Teleport.Have( player ) ) {

        } else {





            Add(player, location);
        }
        */
    }
/*
        player.teleport(location); // 傳送玩家過去

        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫
            ResultSet res = sta.executeQuery("SELECT * FROM `world-list_data` WHERE `Enable` = '1' AND `Name` = '" + location.getWorld().getName() + "' LIMIT 1");
            // 跳到最後一行
            res.last();
            // 最後一行 行數 是否 > 0
            if (res.getRow() > 0) {
                // 有資料
                // 跳回 初始行 必須使用 next() 才能取得資料
                res.beforeFirst();
                res.next();


                String game_mode = res.getString("GameMode");




                if (game_mode.equals("ADVENTURE")) {
                    // 冒險
                    player.setGameMode(GameMode.ADVENTURE);
                } else if (game_mode.equals("SPECTATOR")) {
                    // 觀察者
                    player.setGameMode(GameMode.SPECTATOR);
                } else {
                    // 都不是 生存
                    player.setGameMode(GameMode.SURVIVAL);
                }


                // 是否在領地內
                if (ServerPlugin.Land.Permissions.Is(location)) {
                    // 在領地內
                    // 是否有權限
                    if (ServerPlugin.Land.Permissions.Inspection(player, location, "Permissions_PlayerToggleFlight")) {
                        // 是
                        player.setAllowFlight(true); // 允許飛行

                    } else { // 不是
                        // 檢查是否有權限
                        if (ServerPlugin.Player.Permissions.Inspection(player, "Permissions_Land")) {
                            // 有
                            player.setAllowFlight(true); // 允許飛行

                        } else {
                            // 沒有

                            player.setFlying(false); // 禁止飛行
                            player.setAllowFlight(false); // 禁止飛行

                        }
                    }
                } else {
                    // 不再領地內

                    player.setFlying(false); // 禁止飛行
                    player.setAllowFlight(false); // 禁止飛行

                }



            }
        } catch (Exception ex) {
            // 出錯
            //player.sendMessage(ex.getMessage());
        }

*/

        /*
        new BukkitRunnable() {
            @Override
            final public void run() {


                try {
                    Connection con = MySQL.getConnection(); // 連線 MySQL
                    Statement sta = con.createStatement(); // 取得控制庫
                    ResultSet res = sta.executeQuery("SELECT * FROM `world-list_data` WHERE `Name` = '" + name + "' LIMIT 1");
                    // 跳到最後一行
                    res.last();
                    // 最後一行 行數 是否 > 0
                    if (res.getRow() > 0) {
                        // 有資料
                        // 跳回 初始行 必須使用 next() 才能取得資料
                        res.beforeFirst();
                        res.next();


                        WorldCreator world_creator = new WorldCreator( name );
                        // 是否有環境結構
                        world_creator.generateStructures( res.getBoolean("Structures") );
                        // 基活
                        World world = Bukkit.getServer().createWorld(world_creator);


                        new BukkitRunnable() {
                            boolean pvp = res.getBoolean("PVP");
                            String difficulty = res.getString("Difficulty");
                            int size = res.getInt("Size");
                            String game_mode = res.getString("GameMode");


                            @Override
                            final public void run() {

                                //world.setKeepSpawnInMemory(false);
                                //world.setAutoSave(true);
                                // 是否允許PVP

                                world.setPVP( pvp );
                                // 難度
                                world.setDifficulty(Difficulty.valueOf( difficulty ));



                                // 世界邊界設定
                                WorldBorder world_border = world.getWorldBorder();
                                // 中心點
                                world_border.setCenter(0, 0);
                                // 範圍 以直徑做計
                                world_border.setSize( size );


                                new BukkitRunnable() {
                                    @Override
                                    final public void run() {
                                        if ( Bukkit.getServer().getWorld( name ) != null ) {
                                            if ( Bukkit.getServer().getOfflinePlayer( player.getUniqueId() ).isOnline() ) {

                                                if (game_mode.equals("ADVENTURE")) {
                                                    // 冒險
                                                    player.setGameMode(GameMode.ADVENTURE);
                                                } else if (game_mode.equals("SPECTATOR")) {
                                                    // 觀察者
                                                    player.setGameMode(GameMode.SPECTATOR);
                                                } else {
                                                    // 都不是 生存
                                                    player.setGameMode(GameMode.SURVIVAL);
                                                }

                                                Location location = new Location(
                                                        Bukkit.getServer().createWorld( world_creator ),
                                                        X + 0.5,
                                                        Y,
                                                        Z + 0.5
                                                );
                                                player.teleport( location );



                                                // 是否在領地內
                                                if ( ServerPlugin.Land.Permissions.Is( location ) ) {
                                                    // 在領地內

                                                    // 是否有權限
                                                    if ( ServerPlugin.Land.Permissions.Inspection( player , location ,"Permissions_PlayerToggleFlight" ) ) {
                                                        //是
                                                        // 允許飛行
                                                        player.setAllowFlight(true);

                                                    } else {
                                                        // 不是
                                                        // 檢查是否有權限
                                                        if ( ServerPlugin.Player.Permissions.Inspection(player,"Permissions_Land") ) {
                                                            // 有
                                                            // 允許飛行
                                                            player.setAllowFlight(true);

                                                        } else {
                                                            // 沒有
                                                            // 檢查是否無視

                                                            // 禁止飛行
                                                            player.setFlying(false);
                                                            player.setAllowFlight(false);

                                                        }
                                                    }
                                                } else {
                                                    // 不再領地內

                                                    // 禁止飛行
                                                    player.setFlying(false);
                                                    player.setAllowFlight(false);

                                                }

                                                cancel();
                                                return;
                                            }
                                        }
                                    }
                                }.runTaskTimer( ServerPlugin.SYSTEM.Plugin.get() , 1L , 5L );


                                return;
                            }
                        }.runTask( ServerPlugin.SYSTEM.Plugin.get() );



                    } else if ( name.equals("w") || name.equals("w_nether") || name.equals("w_the_end") ) {

                        WorldCreator world_creator = new WorldCreator( name );

                        // 是否有環境結構
                        world_creator.generateStructures( true );

                        // 基活
                        World world = Bukkit.getServer().createWorld(world_creator);
                        new BukkitRunnable() {
                            @Override
                            final public void run() {
                                //world.setKeepSpawnInMemory(false);
                                //world.setAutoSave(true);

                                new BukkitRunnable() {
                                    @Override
                                    final public void run() {
                                        if ( Bukkit.getServer().getWorld( name ) != null ) {
                                            if ( Bukkit.getServer().getOfflinePlayer( player.getUniqueId() ).isOnline() ) {

                                                // 生存
                                                player.setGameMode(GameMode.SURVIVAL);

                                                Location location = new Location(
                                                        Bukkit.getServer().createWorld( world_creator ),
                                                        X + 0.5,
                                                        Y,
                                                        Z + 0.5
                                                );
                                                player.teleport( location );



                                                // 是否在領地內
                                                if ( ServerPlugin.Land.Permissions.Is( location ) ) {
                                                    // 在領地內

                                                    // 是否有權限
                                                    if ( ServerPlugin.Land.Permissions.Inspection( player , location ,"Permissions_PlayerToggleFlight" ) ) {
                                                        //是
                                                        // 允許飛行
                                                        player.setAllowFlight(true);

                                                    } else {
                                                        // 不是
                                                        // 檢查是否有權限
                                                        if ( ServerPlugin.Player.Permissions.Inspection(player,"Permissions_Land") ) {
                                                            // 有
                                                            // 允許飛行
                                                            player.setAllowFlight(true);

                                                        } else {
                                                            // 沒有
                                                            // 檢查是否無視

                                                            // 禁止飛行
                                                            player.setFlying(false);
                                                            player.setAllowFlight(false);

                                                        }
                                                    }
                                                } else {
                                                    // 不再領地內

                                                    // 禁止飛行
                                                    player.setFlying(false);
                                                    player.setAllowFlight(false);

                                                }

                                                cancel();
                                                return;

                                            }
                                        }
                                    }

                                }.runTaskTimer( ServerPlugin.SYSTEM.Plugin.get() , 1L , 5L );


                                return;
                            }
                        }.runTask( ServerPlugin.SYSTEM.Plugin.get() );


                    }


                    //cancel();  // 終止線程
                } catch (Exception ex) {
                    // 出錯
                    //player.sendMessage(ex.getMessage());
                }

                return;



            }




    }





    static final public void SpawnLocation ( Player player , String name ) {
        new BukkitRunnable() {
            @Override
            final public void run() {

                try {
                    Connection con = MySQL.getConnection(); // 連線 MySQL
                    Statement sta = con.createStatement(); // 取得控制庫
                    ResultSet res = sta.executeQuery("SELECT * FROM `world-list_data` WHERE `Name` = '" + name + "' LIMIT 1");
                    // 跳到最後一行
                    res.last();
                    // 最後一行 行數 是否 > 0
                    if (res.getRow() > 0) {
                        // 有資料
                        // 跳回 初始行 必須使用 next() 才能取得資料
                        res.beforeFirst();
                        res.next();


                        WorldCreator world_creator = new WorldCreator( name );
                        // 是否有環境結構
                        world_creator.generateStructures( res.getBoolean("Structures") );
                        // 基活
                        World world = Bukkit.getServer().createWorld(world_creator);


                        new BukkitRunnable() {
                            boolean pvp = res.getBoolean("PVP");
                            String difficulty = res.getString("Difficulty");
                            int size = res.getInt("Size");
                            String game_mode = res.getString("GameMode");


                            @Override
                            final public void run() {
                                //world.setKeepSpawnInMemory(false);
                                //world.setAutoSave(true);
                                // 是否允許PVP

                                world.setPVP( pvp );
                                // 難度
                                world.setDifficulty(Difficulty.valueOf( difficulty ));



                                // 世界邊界設定
                                WorldBorder world_border = world.getWorldBorder();
                                // 中心點
                                world_border.setCenter(0, 0);
                                // 範圍 以直徑做計
                                world_border.setSize( size );



                                new BukkitRunnable() {
                                    @Override
                                    final public void run() {
                                        if ( Bukkit.getServer().getWorld( name ) != null ) {
                                            if ( Bukkit.getServer().getOfflinePlayer( player.getUniqueId() ).isOnline() ) {

                                                if (game_mode.equals("ADVENTURE")) {
                                                    // 冒險
                                                    player.setGameMode(GameMode.ADVENTURE);
                                                } else if (game_mode.equals("SPECTATOR")) {
                                                    // 觀察者
                                                    player.setGameMode(GameMode.SPECTATOR);
                                                } else {
                                                    // 都不是 生存
                                                    player.setGameMode(GameMode.SURVIVAL);
                                                }



                                                Location location = Bukkit.getServer().createWorld( world_creator ).getSpawnLocation();
                                                player.teleport( location );



                                                // 是否在領地內
                                                if ( ServerPlugin.Land.Permissions.Is( location ) ) {
                                                    // 在領地內

                                                    // 是否有權限
                                                    if ( ServerPlugin.Land.Permissions.Inspection( player , location ,"Permissions_PlayerToggleFlight" ) ) {
                                                        //是
                                                        // 允許飛行
                                                        player.setAllowFlight(true);

                                                    } else {
                                                        // 不是
                                                        // 檢查是否有權限
                                                        if ( ServerPlugin.Player.Permissions.Inspection(player,"Permissions_Land") ) {
                                                            // 有
                                                            // 允許飛行
                                                            player.setAllowFlight(true);

                                                        } else {
                                                            // 沒有
                                                            // 檢查是否無視

                                                            // 禁止飛行
                                                            player.setFlying(false);
                                                            player.setAllowFlight(false);

                                                        }
                                                    }
                                                } else {
                                                    // 不再領地內

                                                    // 禁止飛行
                                                    player.setFlying(false);
                                                    player.setAllowFlight(false);

                                                }


                                                cancel();
                                                return;

                                            }
                                        }
                                    }
                                }.runTaskTimer( ServerPlugin.SYSTEM.Plugin.get() , 1L , 5L );


                                return;
                            }
                        }.runTask( ServerPlugin.SYSTEM.Plugin.get() );





                    } else if ( name.equals("w") || name.equals("w_nether") || name.equals("w_the_end") ) {

                        WorldCreator world_creator = new WorldCreator( name );

                        // 是否有環境結構
                        world_creator.generateStructures( true );

                        // 基活
                        World world = Bukkit.getServer().createWorld(world_creator);
                        new BukkitRunnable() {
                            @Override
                            final public void run() {
                                //world.setKeepSpawnInMemory(false);
                                //world.setAutoSave(true);

                                new BukkitRunnable() {
                                    @Override
                                    final public void run() {
                                        if ( Bukkit.getServer().getWorld( name ) != null ) {
                                            if ( Bukkit.getServer().getOfflinePlayer( player.getUniqueId() ).isOnline() ) {

                                                // 生存
                                                player.setGameMode(GameMode.SURVIVAL);


                                                Location location = Bukkit.getServer().createWorld( world_creator ).getSpawnLocation();
                                                player.teleport( location );


                                                // 是否在領地內
                                                if ( ServerPlugin.Land.Permissions.Is( location ) ) {
                                                    // 在領地內

                                                    // 是否有權限
                                                    if ( ServerPlugin.Land.Permissions.Inspection( player , location ,"Permissions_PlayerToggleFlight" ) ) {
                                                        //是
                                                        // 允許飛行
                                                        player.setAllowFlight(true);

                                                    } else {
                                                        // 不是
                                                        // 檢查是否有權限
                                                        if ( ServerPlugin.Player.Permissions.Inspection(player,"Permissions_Land") ) {
                                                            // 有
                                                            // 允許飛行
                                                            player.setAllowFlight(true);

                                                        } else {
                                                            // 沒有
                                                            // 檢查是否無視

                                                            // 禁止飛行
                                                            player.setFlying(false);
                                                            player.setAllowFlight(false);

                                                        }
                                                    }
                                                } else {
                                                    // 不再領地內

                                                    // 禁止飛行
                                                    player.setFlying(false);
                                                    player.setAllowFlight(false);

                                                }


                                                cancel();
                                                return;

                                            }
                                        }
                                    }
                                }.runTaskTimer( ServerPlugin.SYSTEM.Plugin.get() , 1L , 5L );


                                return;
                            }
                        }.runTask( ServerPlugin.SYSTEM.Plugin.get() );



                    }



                    //cancel();  // 終止線程
                } catch (Exception ex) {
                    // 出錯
                    //player.sendMessage(ex.getMessage());
                }
                return;


            }
        }.runTaskAsynchronously(ServerPlugin.SYSTEM.Plugin.get());


    }
    */
}
