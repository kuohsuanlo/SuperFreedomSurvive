package SuperFreedomSurvive.World;

import SuperFreedomSurvive.SYSTEM.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

final public class Loop {

    final static public ArrayList<String> world_await = new ArrayList<String>();

    static final public boolean Have(String name) {
        // 是否有此世界在註冊表中
        for (int i = 0 ; i < world_await.size() ; ++i) {
            if (world_await.get(i).equals(name)) {
                // 有
                return true;
            }
        }
        // 沒有
        return false;
    }

    // 加入到註冊表
    static final public boolean Add(String name) {
        // 增加到註冊表中
        if (Have(name)) {
            // 已經有了
            return false;
        } else {
            // 加入
            world_await.add(name);
            return true;
        }
    }

    // 從註冊表中刪除
    static final public void Remove(String name) {
        for (int i = 0 ; i < world_await.size() ; ++i) {
            if (world_await.get(i).equals(name)) {
                // 有
                world_await.remove(i);
            }
        }
        // 沒有
    }

    // 取得全部
    static final public ArrayList Get() {
        return world_await;
    }


    static String now = null;
    static int max_time = 400;
    static int time = 0;


    // 世界生成排隊迴圈
    static final public void Start() {


        new BukkitRunnable() {
            @Override
            final public void run() {

                if (!Bukkit.getPluginManager().isPluginEnabled(SuperFreedomSurvive.SYSTEM.Plugin.get())) {
                    // 插件停止
                    cancel();
                    return;
                }

                try {

                    // 檢查是否有世界生城在等待排隊中
                    if (Get().size() > 0) {
                        // 有 只創使用一個
                        String world_name = (String) Get().get(0); // 取得第一位資料

                        // 檢查世界是否存在
                        if (SuperFreedomSurvive.World.List.Have(world_name)) {
                            // 已經存在
                            Remove(world_name); // 從註冊表移除

                        } else {
                            // 不存在 使用異步加載
                            if (now == null) {

                                now = world_name;

                                Connection con = MySQL.getConnection(); // 連線 MySQL
                                Statement sta = con.createStatement(); // 取得控制庫
                                ResultSet res = sta.executeQuery("SELECT * FROM `world-list_data` WHERE `Enable` = '1' AND `Name` = '" + world_name + "' LIMIT 1");
                                // 跳到最後一行
                                res.last();
                                // 最後一行 行數 是否 > 0
                                if (res.getRow() > 0) {
                                    // 跳回 初始行 必須使用 next() 才能取得資料
                                    res.beforeFirst();
                                    res.next();

                                    WorldCreator world_creator = new WorldCreator(world_name); // 開始世界生城

                                    if (res.getInt("Environment") == 0) {
                                        world_creator.environment(World.Environment.NORMAL);
                                    } else if (res.getInt("Environment") == 1) {
                                        world_creator.environment(World.Environment.NETHER);
                                    } else if (res.getInt("Environment") == 2) {
                                        world_creator.environment(World.Environment.THE_END);
                                    }

                                    world_creator.generateStructures(res.getBoolean("Structures")); // 是否有環境結構
                                    //world_creator.type(world_type); // 世界類型

                                    World world = world_creator.createWorld();
                                    //World world = Bukkit.getServer().createWorld(world_creator); // 生成

                                    world.setKeepSpawnInMemory(true); // 不使用記憶體緩存

                                    // 完成
                                    SuperFreedomSurvive.World.List.Add(world_name); // 登入註冊表
                                    Remove(world_name); // 從註冊表移除
                                    now = null;

                                    Bukkit.getLogger().info("世界 " + world_name + " 已加載");

                                    Data.Setting(world); // 設定資料

                                } else {
                                    // 沒有資料
                                    Remove(world_name); // 從註冊表移除
                                }

                                res.close(); // 關閉查詢
                                sta.close(); // 關閉連線

                            } else {
                                if (time > max_time) {
                                    time = 0;
                                    now = null;
                                    Remove(world_name); // 從註冊表移除
                                } else {
                                    ++time;
                                }
                            }
                        }
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                    cancel();
                    return;
                }
            }
        }.runTaskTimer(SuperFreedomSurvive.SYSTEM.Plugin.get(), 0L, 1L);
    }


}
