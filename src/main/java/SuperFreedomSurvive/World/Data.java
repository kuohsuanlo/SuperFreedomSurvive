package SuperFreedomSurvive.World;

import SuperFreedomSurvive.SYSTEM.MySQL;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

final public class Data {
    // 世界控制 調用api


    // 世界是否存在註冊表中
    static final public boolean Have(String name) {
        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫

            ResultSet res = sta.executeQuery("SELECT * FROM `world-list_data` WHERE `Enable` = '1' AND `Name` = '" + name + "' LIMIT 1");
            // 跳到最後一行
            res.last();
            // 最後一行 行數 是否 > 0
            if (res.getRow() > 0) {

                res.close(); // 關閉查詢
                sta.close(); // 關閉連線

                return true;
            } else {

                res.close(); // 關閉查詢
                sta.close(); // 關閉連線

                return false;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }



    // 取得世界擁有者
    static final public String getOwner(String name) {
        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫

            ResultSet res = sta.executeQuery("SELECT * FROM `world-list_data` WHERE `Name` = '" + name + "' LIMIT 1");
            // 跳到最後一行
            res.last();
            // 最後一行 行數 是否 > 0
            if (res.getRow() > 0) {

                String owner = res.getString("Owner");
                res.close(); // 關閉查詢
                sta.close(); // 關閉連線

                return owner;
            } else {

                res.close(); // 關閉查詢
                sta.close(); // 關閉連線

                return null;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }



    // 僅載入一個指定的世界
    static final public void Load(String name) {

        // 檢查世界是否存在
        if (SuperFreedomSurvive.World.List.Have(name)) {
            // 已經存在
        } else {
            SuperFreedomSurvive.World.Loop.Add(name); // 加入世界到排隊中

        }
    }


    static final public void Delete(String name) {
        // 刪除世界

        // 檢查世界是否存在
        //if ( ServerPlugin.World.List.Have( name ) ) {

        // 是否屬於基本世界
        if (
                name.equals("w") ||
                name.equals("w_nether") ||
                name.equals("w_the_end")
        ) {
            //ServerPlugin.World.List.Remove(name); // 從註冊表刪除

        } else {

            // 刪除此世界互動
            //Bukkit.unloadWorld(name, false);
            try {
                Connection con = MySQL.getConnection(); // 連線 MySQL
                Statement sta = con.createStatement(); // 取得控制庫

                if (name.equals(SuperFreedomSurvive.Additional.WoodAxe.GetRunWorld())) {
                    SuperFreedomSurvive.Additional.WoodAxe.Stop();
                }

                Bukkit.unloadWorld(name, false); // 停止世界

                new BukkitRunnable() {
                    @Override
                    final public void run() {
                        SuperFreedomSurvive.SYSTEM.File.Delete.DeleteAll(new File("./" + name)); // 刪除地圖數據
                    }
                }.runTaskAsynchronously(SuperFreedomSurvive.SYSTEM.Plugin.get());

                // 刪除資料數據
                sta.executeUpdate("DELETE FROM `world-list_data` WHERE `Name` = '" + name + "' ");

                sta.executeUpdate("DELETE FROM `block-special_data` WHERE `World` = '" + name + "' ");
                SuperFreedomSurvive.Land.DeleteLand.ByWorld(name);
                sta.executeUpdate("DELETE FROM `player-location_data` WHERE `World` = '" + name + "' ");
                sta.executeUpdate("DELETE FROM `player-spawn-location_data` WHERE `World` = '" + name + "' ");
                sta.executeUpdate("DELETE FROM `player-warp_data` WHERE `World` = '" + name + "' ");

                sta.executeUpdate("DELETE FROM `custom-monster_data` WHERE `World` = '" + name + "' ");

                sta.executeUpdate("DELETE FROM `player-additional-wood-axe_data` WHERE `World` = '" + name + "' ");

                SuperFreedomSurvive.World.List.Remove(name); // 從註冊表刪除

                sta.close(); // 關閉連線

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        //}
    }


    static public void Unload(String name, boolean save) {
        // 關閉世界
        // 檢查世界是否存在
        if (SuperFreedomSurvive.World.List.Have(name)) {
            // 存在

            // 是否屬於基本世界
            if (
                    name.equals("w") ||
                    name.equals("w_nether") ||
                    name.equals("w_the_end")
            ) {
                SuperFreedomSurvive.World.List.Remove(name); // 從註冊表刪除

            } else {
                // 將該世界的玩家全部都移動到主城
                List players = Bukkit.getWorld(name).getPlayers();
                for (int i = 0, s = players.size(); i < s; ++i) {
                    ((Player) players.get(i)).teleportAsync(Bukkit.getWorld("w").getSpawnLocation());
                }

                if (save) {
                    // 安全的停止並保存區塊
                    for (int i = 0 ; i < Bukkit.getWorld(name).getLoadedChunks().length ; ++i) {
                        Bukkit.getWorld(name).unloadChunk(Bukkit.getWorld(name).getLoadedChunks()[i]);
                    }
                }

                if (name.equals(SuperFreedomSurvive.Additional.WoodAxe.GetRunWorld())) {
                    SuperFreedomSurvive.Additional.WoodAxe.Stop();
                }

                Bukkit.unloadWorld(Bukkit.getWorld(name), save); // 停止世界
                //Bukkit.world

                SuperFreedomSurvive.World.List.Remove(name); // 從註冊表刪除

                Bukkit.getLogger().info("世界 " + name + " 已停止");

            }
        } else {
            // 不存在
        }

    }

/*
    static final public void Copy( String owner , String old_name , String new_name ) {
        // 複製世界
        // 不包含 ServerPlugin.World.Load.Load( );

        // 檢查世界是否存在
        if ( ServerPlugin.World.List.Have( new_name ) ) {
            // 已經存在
            return;

        } else {
            // 不存在
            // 資料庫複製
            try {
                // 使用 複製
                ServerPlugin.SYSTEM.File.Copy.CopyAll("./" + old_name, "./" + new_name);


                Connection con = MySQL.getConnection(); // 連線 MySQL
                Statement sta = con.createStatement(); // 取得控制庫
                ResultSet res = sta.executeQuery("SELECT * FROM `world-list_data` WHERE `Name` = '" + old_name + "' LIMIT 1");
                // 跳到最後一行
                res.last();
                // 最後一行 行數 是否 > 0
                if (res.getRow() > 0) {

                    // 有資料
                    // 跳回 初始行 必須使用 next() 才能取得資料
                    res.beforeFirst();
                    res.next();

                    // 取得內部資料
                    int size = res.getInt("Size");
                    int pvp = res.getInt("PVP");
                    String difficulty = res.getString("Difficulty");
                    String game_mode = res.getString("GameMode");
                    int structures = res.getInt("Structures");

                    // 新增數據
                    sta.executeUpdate("INSERT INTO `world-list_data` (`Name`, `Owner`, `Size`, `PVP`, `Difficulty`, `GameMode`, `Structures`) VALUES ('" + new_name + "', '" + owner + "', '" + size + "', '" + pvp + "', '" + difficulty + "', '" + game_mode + "', '" + structures + "')");

                    ServerPlugin.World.Loop.Add( new_name ); // 加入到排隊

                }


            } catch (Exception ex) {
                // 出錯
            }
        }
    }
*/

    // 取得設定資料
    static final public void Setting(World world) {

        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫
            ResultSet res = sta.executeQuery("SELECT * FROM `world-list_data` WHERE `Name` = '" + world.getName() + "' LIMIT 1");
            // 跳到最後一行
            res.last();
            // 最後一行 行數 是否 > 0
            if (res.getRow() > 0) {

                // 有資料
                // 跳回 初始行 必須使用 next() 才能取得資料
                res.beforeFirst();
                res.next();

                boolean pvp = res.getBoolean("PVP");
                int difficulty = res.getInt("Difficulty");
                int size = res.getInt("Size");
                boolean keep_inventory = res.getBoolean("KEEP_INVENTORY");
                boolean natural_regeneration = res.getBoolean("NATURAL_REGENERATION");
                int max_entity_cramming = res.getInt("MAX_ENTITY_CRAMMING");
                boolean mob_griefing = res.getBoolean("MOB_GRIEFING");
                boolean do_weather_cycle = res.getBoolean("DO_WEATHER_CYCLE");
                boolean do_fire_tick = res.getBoolean("DO_FIRE_TICK");
                boolean do_mob_loot = res.getBoolean("DO_MOB_LOOT");
                boolean do_mob_spawning = res.getBoolean("DO_MOB_SPAWNING");
                boolean do_tile_drops = res.getBoolean("DO_TILE_DROPS");

                res.close(); // 關閉查詢

                // 是否允許PVP
                world.setPVP(pvp);
                                    /*
                                    Difficulty.PEACEFUL
                                    Difficulty.EASY
                                    Difficulty.HARD
                                    Difficulty.NORMAL
                                    */
                // 難度
                if (difficulty == 0) {
                    world.setDifficulty(Difficulty.PEACEFUL);
                } else if (difficulty == 1) {
                    world.setDifficulty(Difficulty.EASY);
                } else if (difficulty == 2) {
                    world.setDifficulty(Difficulty.NORMAL);
                } else if (difficulty == 3) {
                    world.setDifficulty(Difficulty.HARD);
                }

                // 世界邊界設定
                WorldBorder world_border = world.getWorldBorder();
                // 中心點
                world_border.setCenter(0, 0);
                // 範圍 以直徑做計
                world_border.setSize(size);


                // 是否防噴裝
                world.setGameRule(GameRule.KEEP_INVENTORY, keep_inventory);

                // 玩家自然回復生命
                world.setGameRule(GameRule.NATURAL_REGENERATION, natural_regeneration);

                // 生物太多擁擠窒息
                world.setGameRule(GameRule.MAX_ENTITY_CRAMMING, max_entity_cramming);

                // 生物拾取物品
                world.setGameRule(GameRule.MOB_GRIEFING, mob_griefing);

                // 天氣變化
                world.setGameRule(GameRule.DO_WEATHER_CYCLE, do_weather_cycle);

                // 火焰自然熄滅/蔓延
                world.setGameRule(GameRule.DO_FIRE_TICK, do_fire_tick);

                // 生物死亡掉落物品
                world.setGameRule(GameRule.DO_MOB_LOOT, do_mob_loot);

                // 生物自然生成
                world.setGameRule(GameRule.DO_MOB_SPAWNING, do_mob_spawning);

                // 破壞方塊掉落物品
                world.setGameRule(GameRule.DO_TILE_DROPS, do_tile_drops);

            }

            res.close(); // 關閉查詢
            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    static final public World New(
            String name,
            String owner,
            World.Environment environment,
            boolean structures,
            long seed,
            WorldType world_type,
            String settings,
            int size,
            GameMode game_mode,
            boolean transfer,
            boolean pvp,
            Difficulty difficulty,
            String group,
            Material ID,
            String description,
            boolean keep_inventory,
            boolean natural_regeneration,
            int max_entity_cramming,
            boolean mob_griefing,
            boolean do_weather_cycle,
            boolean do_fire_tick,
            boolean do_mob_loot,
            boolean do_mob_spawning,
            boolean do_tile_drops
    ) {
        // 創建世界
        // 檢查世界是否存在
        if (SuperFreedomSurvive.World.List.Have(name)) {
            // 已經存在
            return Bukkit.getWorld(name);

        } else {
            // 不存在

            // 是否屬於基本世界
            if (
                    name.equals("w") ||
                            name.equals("w_nether") ||
                            name.equals("w_the_end")
            ) {
                SuperFreedomSurvive.World.List.Add(name); // 從註冊表中新增
                return Bukkit.getWorld(name);

            } else {

                WorldCreator world_creator = new WorldCreator(name);

                world_creator.environment(environment); // 世界環境類型

                world_creator.generateStructures(structures); // 是否有環境結構

                if (seed != 0) {
                    world_creator.seed(seed); // 世界種子碼
                }

                world_creator.type(world_type); // 世界類型

                if (settings != null) {
                    //world_creator.generator( "" );
                    world_creator.generatorSettings(settings); // 世界生成使用方案
                }

                World world = world_creator.createWorld(); // 正式生成

                world.setKeepSpawnInMemory(false); // 不被記憶體緩存


                //world.setAutoSave(auto_save); // 是否自動儲存

                world.setPVP(pvp); // 是否允許PVP

                world.setDifficulty(difficulty); // 難度

                // 世界邊界設定
                WorldBorder world_border = world.getWorldBorder();

                world_border.setCenter(0, 0); // 中心點

                world_border.setSize(size); // 範圍 以直徑做計
                //world.save();

                // 是否防噴裝
                world.setGameRule(GameRule.KEEP_INVENTORY, keep_inventory);

                // 玩家自然回復生命
                world.setGameRule(GameRule.NATURAL_REGENERATION, natural_regeneration);

                // 生物太多擁擠窒息
                world.setGameRule(GameRule.MAX_ENTITY_CRAMMING, max_entity_cramming);

                // 生物拾取物品
                world.setGameRule(GameRule.MOB_GRIEFING, mob_griefing);

                // 天氣變化
                world.setGameRule(GameRule.DO_WEATHER_CYCLE, do_weather_cycle);

                // 火焰自然熄滅/蔓延
                world.setGameRule(GameRule.DO_FIRE_TICK, do_fire_tick);

                // 生物死亡掉落物品
                world.setGameRule(GameRule.DO_MOB_LOOT, do_mob_loot);

                // 生物自然生成
                world.setGameRule(GameRule.DO_MOB_SPAWNING, do_mob_spawning);

                // 破壞方塊掉落物品
                world.setGameRule(GameRule.DO_TILE_DROPS, do_tile_drops);


                // 寫入到資料庫
                try {
                    Connection con = MySQL.getConnection(); // 連線 MySQL
                    Statement sta = con.createStatement(); // 取得控制庫


                    int structures_ = 0;
                    if (structures) {
                        structures_ = 1;
                    } else {
                        structures_ = 0;
                    }

                    int pvp_ = 0;
                    if (pvp) {
                        pvp_ = 1;
                    } else {
                        pvp_ = 0;
                    }

                    int transfer_ = 0;
                    if (transfer) {
                        transfer_ = 1;
                    } else {
                        transfer_ = 0;
                    }

                    String ID_ = "";
                    if (ID == null) {
                        ID_ = "NULL";
                    } else {
                        ID_ = "'" + ID.name() + "'";
                    }

                    String group_ = "";
                    if (group == null) {
                        group_ = "NULL";
                    } else {
                        group_ = "'" + group + "'";
                    }


                    int keep_inventory_ = 0;
                    if (keep_inventory) {
                        keep_inventory_ = 1;
                    } else {
                        keep_inventory_ = 0;
                    }

                    int natural_regeneration_ = 0;
                    if (natural_regeneration) {
                        natural_regeneration_ = 1;
                    } else {
                        natural_regeneration_ = 0;
                    }

                    int mob_griefing_ = 0;
                    if (mob_griefing) {
                        mob_griefing_ = 1;
                    } else {
                        mob_griefing_ = 0;
                    }

                    int do_weather_cycle_ = 0;
                    if (do_weather_cycle) {
                        do_weather_cycle_ = 1;
                    } else {
                        do_weather_cycle_ = 0;
                    }

                    int do_fire_tick_ = 0;
                    if (do_fire_tick) {
                        do_fire_tick_ = 1;
                    } else {
                        do_fire_tick_ = 0;
                    }

                    int do_mob_loot_ = 0;
                    if (do_mob_loot) {
                        do_mob_loot_ = 1;
                    } else {
                        do_mob_loot_ = 0;
                    }

                    int do_mob_spawning_ = 0;
                    if (do_mob_spawning) {
                        do_mob_spawning_ = 1;
                    } else {
                        do_mob_spawning_ = 0;
                    }

                    int do_tile_drops_ = 0;
                    if (do_tile_drops) {
                        do_tile_drops_ = 1;
                    } else {
                        do_tile_drops_ = 0;
                    }

                    int difficulty_ = 0;
                    if (difficulty == Difficulty.PEACEFUL) {
                        difficulty_ = 0;
                    } else if (difficulty == Difficulty.EASY) {
                        difficulty_ = 1;
                    } else if (difficulty == Difficulty.NORMAL) {
                        difficulty_ = 2;
                    } else if (difficulty == Difficulty.HARD) {
                        difficulty_ = 3;
                    }

                    int environment_ = 0;
                    if (environment == World.Environment.NORMAL) {
                        environment_ = 0;
                    } else if (environment == World.Environment.NETHER) {
                        environment_ = 1;
                    } else if (environment == World.Environment.THE_END) {
                        environment_ = 2;
                    }

                    int game_mode_ = 0;
                    if (game_mode == GameMode.SURVIVAL) {
                        game_mode_ = 0;
                    } else if (game_mode == GameMode.ADVENTURE) {
                        game_mode_ = 2;
                    } else if (game_mode == GameMode.SPECTATOR) {
                        game_mode_ = 3;
                    }


                    sta.executeUpdate("INSERT INTO `world-list_data` (" +
                            "`Enable`," +
                            " `Hide`," +
                            " `Name`," +
                            " `Environment`," +
                            " `Owner`," +
                            " `Size`," +
                            " `PVP`," +
                            " `Difficulty`," +
                            " `GameMode`," +
                            " `Structures`," +
                            " `Transfer`," +
                            " `Group`," +
                            " `ID`," +
                            " `Description`," +
                            " `Lore`," +
                            " `Start_Time`," +
                            " `End_Time`," +
                            " `KEEP_INVENTORY`," +
                            " `NATURAL_REGENERATION`," +
                            " `MAX_ENTITY_CRAMMING`," +
                            " `MOB_GRIEFING`," +
                            " `DO_WEATHER_CYCLE`," +
                            " `DO_FIRE_TICK`," +
                            " `DO_MOB_LOOT`," +
                            " `DO_MOB_SPAWNING`," +
                            " `DO_TILE_DROPS`" +
                            ")" +
                            "VALUES (" +
                            " '1'," +
                            " '0'," +
                            " '" + name + "'," +
                            " '" + environment_ + "'," +
                            " '" + owner + "'," +
                            " '" + size + "'," +
                            " '" + pvp_ + "'," +
                            " '" + difficulty_ + "'," +
                            " '" + game_mode_ + "'," +
                            " '" + structures_ + "'," +
                            " '" + transfer_ + "'," +
                            " " + group_ + "," +
                            " " + ID_ + "," +
                            " '" + description + "'," +
                            " NULL," +
                            " '" + SuperFreedomSurvive.SYSTEM.Time.Get() + "'," +
                            " NULL," +
                            " '" + keep_inventory_ + "'," +
                            " '" + natural_regeneration_ + "'," +
                            " '" + max_entity_cramming + "'," +
                            " '" + mob_griefing_ + "'," +
                            " '" + do_weather_cycle_ + "'," +
                            " '" + do_fire_tick_ + "'," +
                            " '" + do_mob_loot_ + "'," +
                            " '" + do_mob_spawning_ + "'," +
                            " '" + do_tile_drops_ + "'" +
                            ")");


                    sta.close(); // 關閉連線

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                SuperFreedomSurvive.World.List.Add(name); // 從註冊表中新增

                return world;
            }
        }
    }


}
