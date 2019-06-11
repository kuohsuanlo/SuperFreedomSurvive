package SuperFreedomSurvive.Prohibited;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Date;

final public class StandingStill implements Listener {
    // 禁止掛機

    final static public ArrayList<String> player = new ArrayList<String>();
    final static public ArrayList<Location> move = new ArrayList<Location>();
    final static public ArrayList<String> time = new ArrayList<String>();
    final static public ArrayList<String> item_main = new ArrayList<String>();
    final static public ArrayList<String> item_off = new ArrayList<String>();

    static final public boolean Have(String name) {
        // 是否有此世界在註冊表中
        for (int i = 0; i < player.size(); ++i) {
            if (player.get(i).equals(name)) {
                // 有
                return true;
            }
        }
        // 沒有
        return false;
    }

    static final public Location Get_Location(String name) {
        // 是否有此世界在註冊表中
        for (int i = 0; i < player.size(); ++i) {
            if (player.get(i).equals(name)) {
                // 有
                return move.get(i);
            }
        }
        // 沒有
        return null;
    }

    static final public Object[] Get(String name) {
        // 是否有此世界在註冊表中
        for (int i = 0; i < player.size(); ++i) {
            if (player.get(i).equals(name)) {
                // 有
                return new Object[]{
                        player.get(i),
                        move.get(i),
                        time.get(i),
                        item_main.get(i),
                        item_off.get(i)
                };
            }
        }
        // 沒有
        return null;
    }

    // 加入到註冊表
    static final public boolean Add(Player pla) {
        // 增加到註冊表中

        // 演算
        boolean ok = false;
        if (Have(pla.getName())) {

            Object[] obj = Get(pla.getName());

            //Bukkit.getLogger().info((String)obj[3] + "/" + (String)obj[4]);

            if (obj == null) {

            } else {
                ok = !obj[3].equals(pla.getInventory().getItemInMainHand().getType().name()) ||
                        !obj[4].equals(pla.getInventory().getItemInOffHand().getType().name());
            }

        } else {
            ok = true;
        }

        if (ok) {
            Remove(pla.getName());
            player.add(pla.getName());
            move.add(pla.getLocation());
            time.add((new Date()).getTime() + "");
            item_main.add(pla.getInventory().getItemInMainHand().getType().name());
            item_off.add(pla.getInventory().getItemInOffHand().getType().name());
        }
        // Remove( pla.getName()
        return true;

    }

    // 從註冊表中刪除
    static final public boolean Remove(String name) {
        boolean yes = false;
        for (int i = 0; i < player.size(); ++i) {
            if (player.get(i).equals(name)) {
                // 有
                player.remove(i);
                move.remove(i);
                time.remove(i);
                item_main.remove(i);
                item_off.remove(i);
                yes = true;
            }
        }
        // 沒有
        return yes;
    }


    // 1800000 30分鐘
    static final public void Start() {
        new BukkitRunnable() {
            @Override
            final public void run() {

                for (int i = 0; i < player.size(); ++i) {

                    long ti = Long.parseLong(time.get(i));
                    String pl = player.get(i);
                    if (ti < (new Date()).getTime() - 1800000) {
                        Player player_ = Bukkit.getPlayer(pl);
                        if (player_ != null) {
                            if (player_.isOnline()) {
                                if (player_.getGameMode() == GameMode.SPECTATOR) {
                                    // 觀察者
                                    Remove(player_.getName());

                                } else {
                                    player_.kickPlayer("你已經掛機太久了");
                                }
                            }
                        }
                    }
                }

            }
        }.runTaskTimer(SuperFreedomSurvive.SYSTEM.Plugin.get(), 0L, 20L);
    }


    @EventHandler
    final public void onPlayerMoveEvent(PlayerMoveEvent event) {
        //  當玩家移動時
        Player player = event.getPlayer();

        Location old_loc = Get_Location(player.getName());
        Location new_loc = event.getTo();
        if (old_loc == null) {
            Add(player);
        } else {
            if (new_loc.getWorld().getName().equals(old_loc.getWorld().getName())) {
                // 開始計算
                if (new_loc.getX() <= old_loc.getX() - 6 ||
                        new_loc.getX() >= old_loc.getX() + 5 ||
                        new_loc.getY() <= old_loc.getY() - 6 ||
                        new_loc.getY() >= old_loc.getY() + 6 ||
                        new_loc.getZ() <= old_loc.getZ() - 5 ||
                        new_loc.getZ() >= old_loc.getZ() + 6
                ) {
                    Add(player);
                }
            } else {
                Add(player);
            }
        }
    }

    @EventHandler
    final public void onPlayerJoinEvent(PlayerJoinEvent event) {
        // 玩家登入時
        Player player = event.getPlayer();
        Remove(player.getName());
        Add(player);

    }

    @EventHandler
    final public void onPlayerQuitEvent(PlayerQuitEvent event) {
        // 離開事件
        Remove(event.getPlayer().getName());
    }
/*
    @EventHandler
    final public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent event) {
        // 發送文字
        if (event.isCancelled()) {
            // 被取消
        } else {
            // 沒被取消
        }
    }
    */
}
