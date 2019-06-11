package SuperFreedomSurvive.Land;

import SuperFreedomSurvive.Additional.Event;
import SuperFreedomSurvive.SYSTEM.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

final public class PlayerMove implements Listener {

    // 創建2維陣列組
    final static public ArrayList<String> player_list = new ArrayList<String>();
    final static public ArrayList<String> player_ID_list = new ArrayList<String>();

    static final public boolean Have(String name) {
        // 是否有此世界在註冊表中
        for (int i = 0, s = player_list.size(); i < s; ++i) {
            if (player_list.get(i).equals(name)) {
                // 有
                return true;
            }
        }
        // 沒有
        return false;
    }

    // 加入到註冊表
    static final public boolean Add(String name, String ID) {
        // 增加到註冊表中
        if (Have(name)) {
            // 已經有了
            return false;
        } else {
            // 加入
            player_list.add(name);
            player_ID_list.add(ID);
            return true;
        }
    }

    // 從註冊表中刪除
    static final public boolean Remove(String name) {
        for (int i = 0, s = player_list.size(); i < s; ++i) {
            if (player_list.get(i).equals(name)) {
                // 有
                player_list.remove(i);
                player_ID_list.remove(i);
                return true;
            }
        }
        // 沒有
        return false;
    }

    static final public String Get(String name) {
        // 是否有此世界在註冊表中
        for (int i = 0, s = player_list.size(); i < s; ++i) {
            if (player_list.get(i).equals(name)) {
                // 有
                return player_ID_list.get(i);
            }
        }
        // 沒有
        return "";
    }


    @EventHandler
    final public void onPlayerJoinEvent(PlayerJoinEvent event) {
        // 玩家加入遊戲事件

        Remove(event.getPlayer().getName()); // 刪除舊緩存

    }


    // 檢查是否擁有飛行權限
    static final public void Start() {
        // 每 1 秒 檢測一次
        new BukkitRunnable() {
            @Override
            final public void run() {

                if (!Bukkit.getPluginManager().isPluginEnabled(SuperFreedomSurvive.SYSTEM.Plugin.get())) {
                    // 插件停止
                    cancel();
                    return;
                }

                Collection collection = Bukkit.getServer().getOnlinePlayers();
                Iterator iterator = collection.iterator();

                // 總數
                while (iterator.hasNext()) {
                    Player player = ((Player) iterator.next());


                    // 取得座標
                    int X = new BigDecimal(player.getLocation().getX()).setScale(0, BigDecimal.ROUND_FLOOR).intValue();
                    int Y = new BigDecimal(player.getLocation().getY()).setScale(0, BigDecimal.ROUND_FLOOR).intValue();
                    int Z = new BigDecimal(player.getLocation().getZ()).setScale(0, BigDecimal.ROUND_FLOOR).intValue();
                    String world = player.getLocation().getWorld().getName();

                    // 在 MySQL 查詢是否站在領地之中
                    try {
                        Connection con = MySQL.getConnection(); // 連線 MySQL
                        Statement sta = con.createStatement(); // 取得控制庫

                        // 查詢 並返回結果
                        ResultSet res = sta.executeQuery("SELECT (User),(Owner),(ID) FROM `land-protection_data` WHERE `World` = '" + world + "' AND `Min_X` <= '" + X + "' AND `Min_Y` <= '" + Y + "' AND `Min_Z` <= '" + Z + "' AND `Max_X` >= '" + X + "' AND `Max_Y` >= '" + Y + "' AND `Max_Z` >= '" + Z + "' ORDER BY `Level` DESC LIMIT 1");
                        // 取得查詢筆數
                        // 跳到最後一行
                        res.last();

                        // 最後一行 行數 是否 > 0
                        if (res.getRow() > 0) {
                            // 有資料

                            // 跳回 初始行 必須使用 next() 才能取得資料
                            res.beforeFirst();
                            // 行數下一行
                            res.next();

                            String user = res.getString("User");
                            String owner = res.getString("Owner");
                            String ID = res.getString("ID");

                            res.close(); // 關閉查詢

                            // 返回第一行
                            // 領地是否無使用者
                            if (user == null) {
                                // ! 無使用者

                                // 是否之前已經 站在相同使用者的領地內
                                if (
                                        Have(player.getName()) &&
                                                Get(player.getName()).equals(ID)
                                ) {
                                    // ! 同樣的 使用者領地
                                    // 不顯示訊息


                                } else {
                                    // 不一樣的 使用者的領地

                                    Remove(player.getName());
                                    Add(player.getName(), ID);

                                    // 顯示訊息
                                    SuperFreedomSurvive.Land.Display.Message(player, "§e玩家 §a" + owner + "§e 規劃的領地");
                                }

                            } else {
                                // 有使用者
                                // 是否之前已經 站在相同使用者的領地內
                                if (
                                        Have(player.getName()) &&
                                                Get(player.getName()).equals(ID)
                                ) {
                                    // ! 同樣的 使用者領地
                                    // 不顯示訊息

                                } else {
                                    // 不一樣的 使用者的領地

                                    Remove(player.getName());
                                    Add(player.getName(), ID);

                                    // 顯示訊息
                                    SuperFreedomSurvive.Land.Display.Message(player, "§e玩家 §a" + user + "§e 的領地");
                                }
                            }


                        } else {
                            // ! 無資料
                            // 是否之前已經 沒有在領地內
                            if (Have(player.getName())) {

                                Remove(player.getName()); // 刪除舊緩存

                                // 顯示訊息
                                SuperFreedomSurvive.Land.Display.Message(player, "野外");
                            } else {
                                // ! 同樣的 在野外
                            }
                        }

                        res.close(); // 關閉查詢


                        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////


                        // 是否在領地內
                        if (SuperFreedomSurvive.Land.Permissions.Is(player.getLocation())) {
                            // 在領地內
                            // 是否有權限
                            if (SuperFreedomSurvive.Land.Permissions.Inspection(player, player.getLocation(), "Permissions_PlayerToggleFlight")) {
                                // 是
                                player.setAllowFlight(true); // 允許飛行
                            } else { // 不是
                                // 沒有

                                // 玩家生存類型
                                if (player.getGameMode() == GameMode.CREATIVE) {
                                    // 不適用於創造
                                } else if (player.getGameMode() == GameMode.SPECTATOR) {
                                    // 不適用於觀察者
                                } else {
                                    player.setFlying(false); // 禁止飛行
                                    player.setAllowFlight(false); // 禁止飛行
                                }

                            }
                        } else {
                            // 不再領地內
                            // 是否有購買補助效果
                            if (Event.Have(player, "Function_Flight_Time")) {
                                player.setAllowFlight(true); // 允許飛行
                            } else {

                                // 玩家生存類型
                                if (player.getGameMode() == GameMode.CREATIVE) {
                                    // 不適用於創造
                                } else if (player.getGameMode() == GameMode.SPECTATOR) {
                                    // 不適用於觀察者
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


                    // 不適用於觀察者
                    if (
                            player.getGameMode() == GameMode.SPECTATOR ||
                            player.getGameMode() == GameMode.CREATIVE
                    ) {
                    } else {
                        // 是否在領地內
                        if (ServerPlugin.Land.Permissions.Is(player.getLocation())) {
                            // 在領地內
                            // 是否有權限
                            if (ServerPlugin.Land.Permissions.Inspection(player, player.getLocation(), "Permissions_PlayerToggleFlight")) {
                                //是
                                // 允許飛行
                                player.setAllowFlight(true);

                            } else {
                                // 不是
                                // 禁止飛行
                                player.setFlying(false);
                                player.setAllowFlight(false);

                            }
                        } else {
                            // 不再領地內
                            player.setFlying(false);
                            player.setAllowFlight(false);
                        }
                    }
*/
                }
            }
        }.runTaskTimer(SuperFreedomSurvive.SYSTEM.Plugin.get(), 0L, 10L);
    }


}
