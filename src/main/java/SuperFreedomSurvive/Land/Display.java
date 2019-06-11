package SuperFreedomSurvive.Land;

import SuperFreedomSurvive.SYSTEM.MySQL;
import org.bukkit.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

final public class Display implements Listener {


    static final public ArrayList<String> array_player = new ArrayList<String>();
    static final public ArrayList<Location> array_location = new ArrayList<Location>();
    static final public ArrayList<byte[]> array_byte = new ArrayList<byte[]>();


    static final public boolean Have(String name) {
        // 是否有此世界在註冊表中
        for (int i = 0, s = array_player.size(); i < s; ++i) {
            if (array_player.get(i).equals(name)) {
                // 有
                return true;
            }
        }
        // 沒有
        return false;
    }

    static final public Location Get_Location(String name) {
        // 是否有此世界在註冊表中
        for (int i = 0, s = array_player.size(); i < s; ++i) {
            if (array_player.get(i).equals(name)) {
                // 有
                return array_location.get(i);
            }
        }
        // 沒有
        return null;
    }

    static final public byte[] Get_Byte(String name) {
        // 是否有此世界在註冊表中
        for (int i = 0, s = array_player.size(); i < s; ++i) {
            if (array_player.get(i).equals(name)) {
                // 有
                return array_byte.get(i);
            }
        }
        // 沒有
        return null;
    }

    // 加入到註冊表
    static final public boolean Add(String name, Location location, byte[] bytes) {
        // 增加到註冊表中
        if (Have(name)) {
            // 已經有了
            return false;
        } else {
            // 加入
            array_player.add(name);
            array_location.add(location);
            array_byte.add(bytes);
            return true;
        }
    }

    // 從註冊表中刪除
    static final public boolean Remove(String name) {
        boolean yes = false;
        for (int i = 0 ; i < array_player.size(); ++i) {
            if (array_player.get(i).equals(name)) {
                // 有
                array_player.remove(i);
                array_location.remove(i);
                array_byte.remove(i);
                yes = true;
            }
        }
        // 沒有
        return yes;
    }


    // 顯示調用
    static final public void Message(Player player, String text) {
        // 顯示文字
        /*
        player.sendTitle(
                "",
                text,
                0,
                30,
                10
        );
        */
        player.sendActionBar(text);

    }


    static public boolean now = false;

    static public boolean now_2 = false;

    static public ArrayList<Object[]> cancel_list = new ArrayList<Object[]>();

    // 加入到註冊表
    static final public void Cancel_Add(int Min_X, int Min_Y, int Min_Z, int Max_X, int Max_Y, int Max_Z, String World) {
        // 增加到註冊表中
        // 加入
        cancel_list.add(
                new Object[]{
                        Min_X,
                        Min_Y,
                        Min_Z,
                        Max_X,
                        Max_Y,
                        Max_Z,
                        World
                }
        );
    }

    // 從註冊表中取得並刪除
    static final public Object[] Cancel_Get() {
        if (cancel_list.size() >= 1) {
            Object[] obj = cancel_list.get(0);
            cancel_list.remove(0);
            return obj;
        } else {
            return null;
        }
    }


    static final public void Start() {
        // 顯示領地範圍

        // 每 1 秒跑一次
        new BukkitRunnable() {
            @Override
            final public void run() {

                if (!Bukkit.getPluginManager().isPluginEnabled(SuperFreedomSurvive.SYSTEM.Plugin.get())) {
                    // 插件停止
                    cancel();
                    return;
                }

                if (!now) {
                    try {
                        now = true;

                        Collection collection = Bukkit.getServer().getOnlinePlayers();
                        Iterator iterator = collection.iterator();

                        new BukkitRunnable() {
                            @Override
                            final public void run() {
                                if (now) {
                                    if (iterator.hasNext()) {

                                        Player player = ((Player) iterator.next());
                                        if (player.isOnline()) {

                                            try {

                                                // 檢測 主手 / 副手 的物品
                                                if (
                                                        player.getInventory().getItemInMainHand().getType() == Material.WOODEN_HOE ||
                                                        player.getInventory().getItemInOffHand().getType() == Material.WOODEN_HOE ||
                                                        player.getInventory().getItemInMainHand().getType() == Material.WOODEN_SHOVEL ||
                                                        player.getInventory().getItemInOffHand().getType() == Material.WOODEN_SHOVEL
                                                ) {

                                                    int Start_X = player.getLocation().getBlockX() - 40;
                                                    int Start_Y = player.getLocation().getBlockY() - 40;
                                                    if (Start_Y < 0) {
                                                        Start_Y = 0;
                                                    }
                                                    if (Start_Y > 255) {
                                                        Start_Y = 255;
                                                    }
                                                    int Start_Z = player.getLocation().getBlockZ() - 40;

                                                    int End_X = player.getLocation().getBlockX() + 40;
                                                    int End_Y = player.getLocation().getBlockY() + 40;
                                                    if (End_Y < 0) {
                                                        End_Y = 0;
                                                    }
                                                    if (End_Y > 255) {
                                                        End_Y = 255;
                                                    }
                                                    int End_Z = player.getLocation().getBlockZ() + 40;

                                                    String world = player.getWorld().getName();

                                                    boolean ok = false;
                                                    Location old_location = Get_Location(player.getName());
                                                    if (old_location != null) {
                                                        if (old_location.getY() < 0) {
                                                            old_location.setY(0);
                                                        }
                                                        if (old_location.getY() > 255) {
                                                            old_location.setY(255);
                                                        }
                                                        if (
                                                                old_location.getWorld().getName().equals(world) &&
                                                                        old_location.getX() >= Start_X + 5 && old_location.getX() <= End_X - 5 &&
                                                                        old_location.getY() >= Start_Y + 5 && old_location.getY() <= End_Y - 5 &&
                                                                        old_location.getZ() >= Start_Z + 5 && old_location.getZ() <= End_Z - 5
                                                        ) {

                                                        } else {
                                                            ok = true;
                                                        }
                                                    } else {
                                                        ok = true;
                                                    }


                                                    Connection con = MySQL.getConnection(); // 連線 MySQL
                                                    Statement sta = con.createStatement(); // 取得控制庫
                                                    ResultSet res;
                                                    String test = "";
                                                    byte[] bytes;
                                                    if (
                                                            player.getInventory().getItemInMainHand().getType() == Material.WOODEN_HOE ||
                                                                    player.getInventory().getItemInOffHand().getType() == Material.WOODEN_HOE
                                                    ) {
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
                                                                " ORDER BY `Level` DESC LIMIT 30");

                                                        while (res.next()) {
                                                            test += res.getString("ID");
                                                        }

                                                        res.close(); // 關閉查詢

                                                        MessageDigest sha = MessageDigest.getInstance("SHA-1");
                                                        sha.update(test.getBytes());
                                                        bytes = sha.digest();

                                                        if (Arrays.equals(Get_Byte(player.getName()), bytes)) {
                                                        } else {
                                                            Remove(player.getName());
                                                            ok = true;
                                                        }
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
                                                                " ORDER BY `Level` LIMIT 30");

                                                        while (res.next()) {
                                                            test += res.getString("ID");
                                                        }

                                                        res.close(); // 關閉查詢

                                                        MessageDigest sha = MessageDigest.getInstance("SHA-1");
                                                        sha.update(test.getBytes());
                                                        bytes = sha.digest();

                                                        if (Arrays.equals(Get_Byte(player.getName()), bytes)) {
                                                        } else {
                                                            Remove(player.getName());
                                                            ok = true;
                                                        }
                                                    }

                                                    if (ok) {

                                                        String qu = "";
                                                        // 領地 特殊寫法
                                                        if (
                                                                player.getInventory().getItemInMainHand().getType() == Material.WOODEN_HOE ||
                                                                        player.getInventory().getItemInOffHand().getType() == Material.WOODEN_HOE
                                                        ) {

                                                            qu = "SELECT (Min_X),(Min_Y),(Min_Z),(Max_X),(Max_Y),(Max_Z),(Level) FROM `land-protection_data` WHERE `World` = '" + world + "' AND " +
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
                                                                    " ORDER BY `Level` DESC LIMIT 20";

                                                        } else {

                                                            qu = "SELECT (Min_X),(Min_Y),(Min_Z),(Max_X),(Max_Y),(Max_Z),(Level) FROM `land-protection_data` WHERE `World` = '" + world + "' AND " +
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
                                                                    " ORDER BY `Level` LIMIT 20";

                                                        }

                                                        int finalStart_Y = Start_Y;
                                                        int finalEnd_Y = End_Y;

                                                        ResultSet finalRes = sta.executeQuery(qu);

                                                        new BukkitRunnable() {

                                                            //while (res.next()) {
                                                            @Override
                                                            final public void run() {

                                                                try {
                                                                    if (finalRes == null) {

                                                                        finalRes.close(); // 關閉查詢
                                                                        res.close(); // 關閉查詢
                                                                        sta.close(); // 關閉連線

                                                                        cancel();
                                                                        return;
                                                                    }
                                                                    if (!finalRes.next()) {

                                                                        finalRes.close(); // 關閉查詢
                                                                        res.close(); // 關閉查詢
                                                                        sta.close(); // 關閉連線

                                                                        cancel();
                                                                        return;
                                                                    }
                                                                    // 檢測 主手 / 副手 的物品
                                                                    if (
                                                                            player.getInventory().getItemInMainHand().getType() == Material.WOODEN_HOE ||
                                                                                    player.getInventory().getItemInOffHand().getType() == Material.WOODEN_HOE ||
                                                                                    player.getInventory().getItemInMainHand().getType() == Material.WOODEN_SHOVEL ||
                                                                                    player.getInventory().getItemInOffHand().getType() == Material.WOODEN_SHOVEL
                                                                    ) {

                                                                    } else {

                                                                        finalRes.close(); // 關閉查詢
                                                                        sta.close(); // 關閉連線

                                                                        cancel();
                                                                        return;
                                                                    }

                                                                    // 計算每個邊的長度
                                                                    int L_X = finalRes.getInt("Max_X") - finalRes.getInt("Min_X");
                                                                    int L_Y = finalRes.getInt("Max_Y") - finalRes.getInt("Min_Y");
                                                                    int L_Z = finalRes.getInt("Max_Z") - finalRes.getInt("Min_Z");
                                                                    int Max_X = finalRes.getInt("Max_X");
                                                                    int Max_Y = finalRes.getInt("Max_Y");
                                                                    int Max_Z = finalRes.getInt("Max_Z");
                                                                    int Min_X = finalRes.getInt("Min_X");
                                                                    int Min_Y = finalRes.getInt("Min_Y");
                                                                    int Min_Z = finalRes.getInt("Min_Z");
                                                                    int level = finalRes.getInt("Level");

                                                                    BlockData block_data = Material.WHITE_STAINED_GLASS.createBlockData();

                                                                    if (level == 0) {
                                                                        block_data = Material.WHITE_STAINED_GLASS.createBlockData();
                                                                    } else if (level == 1) {
                                                                        block_data = Material.PINK_STAINED_GLASS.createBlockData();
                                                                    } else if (level == 2) {
                                                                        block_data = Material.RED_STAINED_GLASS.createBlockData();
                                                                    } else if (level == 3) {
                                                                        block_data = Material.ORANGE_STAINED_GLASS.createBlockData();
                                                                    } else if (level == 4) {
                                                                        block_data = Material.YELLOW_STAINED_GLASS.createBlockData();
                                                                    } else if (level == 5) {
                                                                        block_data = Material.LIME_STAINED_GLASS.createBlockData();
                                                                    } else if (level == 6) {
                                                                        block_data = Material.GREEN_STAINED_GLASS.createBlockData();
                                                                    } else if (level == 7) {
                                                                        block_data = Material.LIGHT_BLUE_STAINED_GLASS.createBlockData();
                                                                    } else if (level == 8) {
                                                                        block_data = Material.BLUE_STAINED_GLASS.createBlockData();
                                                                    } else if (level == 9) {
                                                                        block_data = Material.PURPLE_STAINED_GLASS.createBlockData();

                                                                    } else if (level == 10) {
                                                                        block_data = Material.WHITE_WOOL.createBlockData();
                                                                    } else if (level == 11) {
                                                                        block_data = Material.PINK_WOOL.createBlockData();
                                                                    } else if (level == 12) {
                                                                        block_data = Material.RED_WOOL.createBlockData();
                                                                    } else if (level == 13) {
                                                                        block_data = Material.ORANGE_WOOL.createBlockData();
                                                                    } else if (level == 14) {
                                                                        block_data = Material.YELLOW_WOOL.createBlockData();
                                                                    } else if (level == 15) {
                                                                        block_data = Material.LIME_WOOL.createBlockData();
                                                                    } else if (level == 16) {
                                                                        block_data = Material.GREEN_WOOL.createBlockData();
                                                                    } else if (level == 17) {
                                                                        block_data = Material.LIGHT_BLUE_WOOL.createBlockData();
                                                                    } else if (level == 18) {
                                                                        block_data = Material.BLUE_WOOL.createBlockData();
                                                                    } else if (level == 19) {
                                                                        block_data = Material.PURPLE_WOOL.createBlockData();
                                                                    }


                                                                    ////////////////////////////////////////////////////////////////////////////////////
                                                                    for (int F_X = Min_X; F_X <= Min_X + L_X; ) {
                                                                        if (Min_Y >= finalStart_Y && Min_Y <= finalEnd_Y && Min_Z >= Start_Z && Min_Z <= End_Z) {
                                                                            if (F_X >= Start_X && F_X <= End_X) {
                                                                                player.sendBlockChange(new Location(
                                                                                        player.getWorld(),
                                                                                        F_X,
                                                                                        Min_Y,
                                                                                        Min_Z
                                                                                ), block_data);
                                                                                ++F_X;
                                                                            } else if (F_X > End_X) {
                                                                                break;
                                                                            } else if (F_X < Start_X) {
                                                                                F_X = Start_X;
                                                                            }
                                                                        } else {
                                                                            break;
                                                                        }
                                                                    }
                                                                    for (double F_Y = Min_Y; F_Y <= Min_Y + L_Y; ) {
                                                                        if (Min_X >= Start_X && Min_X <= End_X && Min_Z >= Start_Z && Min_Z <= End_Z) {
                                                                            if (F_Y >= finalStart_Y && F_Y <= finalEnd_Y) {
                                                                                player.sendBlockChange(new Location(
                                                                                        player.getWorld(),
                                                                                        Min_X,
                                                                                        F_Y,
                                                                                        Min_Z
                                                                                ), block_data);
                                                                                ++F_Y;
                                                                            } else if (F_Y > finalEnd_Y) {
                                                                                break;
                                                                            } else if (F_Y < finalStart_Y) {
                                                                                F_Y = finalStart_Y;
                                                                            }
                                                                        } else {
                                                                            break;
                                                                        }
                                                                    }
                                                                    for (double F_Z = Min_Z; F_Z <= Min_Z + L_Z; ) {
                                                                        if (Min_X >= Start_X && Min_X <= End_X && Min_Y >= finalStart_Y && Min_Y <= finalEnd_Y) {
                                                                            if (F_Z >= Start_Z && F_Z <= End_Z) {
                                                                                player.sendBlockChange(new Location(
                                                                                        player.getWorld(),
                                                                                        Min_X,
                                                                                        Min_Y,
                                                                                        F_Z
                                                                                ), block_data);
                                                                                ++F_Z;
                                                                            } else if (F_Z > End_Z) {
                                                                                break;
                                                                            } else if (F_Z < Start_Z) {
                                                                                F_Z = Start_Z;
                                                                            }
                                                                        } else {
                                                                            break;
                                                                        }
                                                                    }


                                                                    for (double F_X = Min_X; F_X <= Min_X + L_X; ) {
                                                                        if (Min_Y + L_Y >= finalStart_Y && Min_Y + L_Y <= finalEnd_Y && Min_Z >= Start_Z && Min_Z <= End_Z) {
                                                                            if (F_X >= Start_X && F_X <= End_X) {
                                                                                player.sendBlockChange(new Location(
                                                                                        player.getWorld(),
                                                                                        F_X,
                                                                                        Min_Y + L_Y,
                                                                                        Min_Z
                                                                                ), block_data);
                                                                                ++F_X;
                                                                            } else if (F_X > End_X) {
                                                                                break;
                                                                            } else if (F_X < Start_X) {
                                                                                F_X = Start_X;
                                                                            }
                                                                        } else {
                                                                            break;
                                                                        }
                                                                    }
                                                                    for (double F_Z = Min_Z; F_Z <= Min_Z + L_Z; ) {
                                                                        if (Min_Y + L_Y >= finalStart_Y && Min_Y + L_Y <= finalEnd_Y && Min_X >= Start_X && Min_X <= End_X) {
                                                                            if (F_Z >= Start_Z && F_Z <= End_Z) {
                                                                                player.sendBlockChange(new Location(
                                                                                        player.getWorld(),
                                                                                        Min_X,
                                                                                        Min_Y + L_Y,
                                                                                        F_Z
                                                                                ), block_data);
                                                                                ++F_Z;
                                                                            } else if (F_Z > End_Z) {
                                                                                break;
                                                                            } else if (F_Z < Start_Z) {
                                                                                F_Z = Start_Z;
                                                                            }
                                                                        } else {
                                                                            break;
                                                                        }
                                                                    }


                                                                    for (double F_X = Max_X; F_X >= Max_X - L_X; ) {
                                                                        if (Max_Y + 1 >= finalStart_Y && Max_Y <= finalEnd_Y && Max_Z >= Start_Z && Max_Z <= End_Z) {
                                                                            if (F_X >= Start_X && F_X <= End_X) {
                                                                                player.sendBlockChange(new Location(
                                                                                        player.getWorld(),
                                                                                        F_X,
                                                                                        Max_Y,
                                                                                        Max_Z
                                                                                ), block_data);
                                                                                --F_X;
                                                                            } else if (F_X > End_X) {
                                                                                F_X = End_X;
                                                                            } else if (F_X < Start_X) {
                                                                                break;
                                                                            }
                                                                        } else {
                                                                            break;
                                                                        }
                                                                    }
                                                                    for (double F_Y = Max_Y; F_Y >= Max_Y - L_Y; ) {
                                                                        if (Max_X >= Start_X && Max_X <= End_X && Max_Z >= Start_Z && Max_Z <= End_Z) {
                                                                            if (F_Y >= finalStart_Y && F_Y <= finalEnd_Y) {
                                                                                player.sendBlockChange(new Location(
                                                                                        player.getWorld(),
                                                                                        Max_X,
                                                                                        F_Y,
                                                                                        Max_Z
                                                                                ), block_data);
                                                                                --F_Y;
                                                                            } else if (F_Y > finalEnd_Y) {
                                                                                F_Y = finalEnd_Y;
                                                                            } else if (F_Y < finalStart_Y) {
                                                                                break;
                                                                            }
                                                                        } else {
                                                                            break;
                                                                        }
                                                                    }
                                                                    for (double F_Z = Max_Z; F_Z >= Max_Z - L_Z; ) {
                                                                        if (Max_Y >= finalStart_Y && Max_Y <= finalEnd_Y && Max_X >= Start_X && Max_X <= End_X) {
                                                                            if (F_Z >= Start_Z && F_Z <= End_Z) {
                                                                                player.sendBlockChange(new Location(
                                                                                        player.getWorld(),
                                                                                        Max_X,
                                                                                        Max_Y,
                                                                                        F_Z
                                                                                ), block_data);
                                                                                --F_Z;
                                                                            } else if (F_Z > End_Z) {
                                                                                F_Z = End_Z;
                                                                            } else if (F_Z < Start_Z) {
                                                                                break;
                                                                            }
                                                                        } else {
                                                                            break;
                                                                        }
                                                                    }


                                                                    for (double F_X = Max_X; F_X >= Max_X - L_X; ) {
                                                                        if (Min_Y >= finalStart_Y && Min_Y <= finalEnd_Y && Max_Z >= Start_Z && Max_Z <= End_Z) {
                                                                            if (F_X >= Start_X && F_X <= End_X) {
                                                                                player.sendBlockChange(new Location(
                                                                                        player.getWorld(),
                                                                                        F_X,
                                                                                        Min_Y,
                                                                                        Max_Z
                                                                                ), block_data);
                                                                                --F_X;
                                                                            } else if (F_X > End_X) {
                                                                                F_X = End_X;
                                                                            } else if (F_X < Start_X) {
                                                                                break;
                                                                            }
                                                                        } else {
                                                                            break;
                                                                        }
                                                                    }
                                                                    for (double F_Z = Max_Z; F_Z >= Max_Z - L_Z; ) {
                                                                        if (Min_Y >= finalStart_Y && Min_Y <= finalEnd_Y && Max_X >= Start_X && Max_X <= End_X) {
                                                                            if (F_Z >= Start_Z && F_Z <= End_Z) {
                                                                                player.sendBlockChange(new Location(
                                                                                        player.getWorld(),
                                                                                        Max_X,
                                                                                        Min_Y,
                                                                                        F_Z
                                                                                ), block_data);
                                                                                --F_Z;
                                                                            } else if (F_Z > End_Z) {
                                                                                F_Z = End_Z;
                                                                            } else if (F_Z < Start_Z) {
                                                                                break;
                                                                            }
                                                                        } else {
                                                                            break;
                                                                        }
                                                                    }


                                                                    for (double F_Y = Min_Y; F_Y <= Min_Y + L_Y; ) {
                                                                        if (Max_X >= Start_X && Max_X <= End_X && Min_Z >= Start_Z && Min_Z <= End_Z) {
                                                                            if (F_Y >= finalStart_Y && F_Y <= finalEnd_Y) {
                                                                                player.sendBlockChange(new Location(
                                                                                        player.getWorld(),
                                                                                        Max_X,
                                                                                        F_Y,
                                                                                        Min_Z
                                                                                ), block_data);
                                                                                ++F_Y;
                                                                            } else if (F_Y > finalEnd_Y) {
                                                                                break;
                                                                            } else if (F_Y < finalStart_Y) {
                                                                                F_Y = finalStart_Y;
                                                                            }
                                                                        } else {
                                                                            break;
                                                                        }
                                                                    }
                                                                    for (double F_Y = Min_Y; F_Y <= Min_Y + L_Y; ) {
                                                                        if (Min_X >= Start_X && Min_X <= End_X && Max_Z >= Start_Z && Max_Z <= End_Z) {
                                                                            if (F_Y >= finalStart_Y && F_Y <= finalEnd_Y) {
                                                                                player.sendBlockChange(new Location(
                                                                                        player.getWorld(),
                                                                                        Min_X,
                                                                                        F_Y,
                                                                                        Max_Z
                                                                                ), block_data);
                                                                                ++F_Y;
                                                                            } else if (F_Y > finalEnd_Y) {
                                                                                break;
                                                                            } else if (F_Y < finalStart_Y) {
                                                                                F_Y = finalStart_Y;
                                                                            }
                                                                        } else {
                                                                            break;
                                                                        }
                                                                    }

                                                                    Remove(player.getName());
                                                                    Add(player.getName(), player.getLocation(), bytes);


                                                                } catch (Exception ex) {
                                                                    ex.printStackTrace();
                                                                }
                                                            }

                                                        }.runTaskTimerAsynchronously(SuperFreedomSurvive.SYSTEM.Plugin.get(), 0L, 5L);

                                                    }

                                                } else {
                                                    if (Have(player.getName())) {

                                                        Remove(player.getName());


                                                        int Start_X = player.getLocation().getBlockX() - 20;
                                                        int Start_Y = player.getLocation().getBlockY() - 20;
                                                        if (Start_Y < 0) {
                                                            Start_Y = 0;
                                                        }
                                                        if (Start_Y > 255) {
                                                            Start_Y = 255;
                                                        }
                                                        int Start_Z = player.getLocation().getBlockZ() - 20;

                                                        int End_X = player.getLocation().getBlockX() + 20;
                                                        int End_Y = player.getLocation().getBlockY() + 20;
                                                        if (End_Y < 0) {
                                                            End_Y = 0;
                                                        }
                                                        if (End_Y > 255) {
                                                            End_Y = 255;
                                                        }
                                                        int End_Z = player.getLocation().getBlockZ() + 20;

                                                        String world = player.getWorld().getName();

                                                        Connection con = MySQL.getConnection(); // 連線 MySQL
                                                        Statement sta = con.createStatement(); // 取得控制庫
                                                        ResultSet res;

                                                        // 使用陣列先記錄資料
                                                        ArrayList<String> all = new ArrayList<String>();

                                                        // 建立新連線
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
                                                                " ORDER BY `Level` DESC LIMIT 22");

                                                        while (res.next()) {
                                                            all.add(res.getString("ID"));
                                                        }

                                                        res.close(); // 關閉查詢

                                                        // 建立新連線
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
                                                                " ORDER BY `Level` LIMIT 22");

                                                        while (res.next()) {
                                                            boolean ok = true;
                                                            for (int i = 0, s = all.size(); i < s; ++i) {
                                                                if (all.get(i).equals(res.getString("ID"))) {
                                                                    ok = false;
                                                                } else {

                                                                }
                                                            }
                                                            if (ok) {
                                                                all.add(res.getString("ID"));
                                                            }
                                                        }

                                                        res.close(); // 關閉查詢
                                                        sta.close(); // 關閉連線

                                                        new BukkitRunnable() {

                                                            int i = 0;

                                                            @Override
                                                            final public void run() {
                                                                if (i >= all.size()) {

                                                                    Remove(player.getName());

                                                                    cancel();
                                                                    return;
                                                                }
                                                                // 檢測 主手 / 副手 的物品
                                                                if (
                                                                        player.getInventory().getItemInMainHand().getType() == Material.WOODEN_HOE ||
                                                                                player.getInventory().getItemInOffHand().getType() == Material.WOODEN_HOE ||
                                                                                player.getInventory().getItemInMainHand().getType() == Material.WOODEN_SHOVEL ||
                                                                                player.getInventory().getItemInOffHand().getType() == Material.WOODEN_SHOVEL
                                                                ) {
                                                                    cancel();
                                                                    return;
                                                                } else {
                                                                }

                                                                try {
                                                                    Connection con = MySQL.getConnection(); // 連線 MySQL
                                                                    Statement sta = con.createStatement(); // 取得控制庫
                                                                    ResultSet res = sta.executeQuery("SELECT (Min_X),(Min_Y),(Min_Z),(Max_X),(Max_Y),(Max_Z),(Owner),(User),(Level),(World),(Unable_Delete) FROM `land-protection_data` WHERE `ID` = '" + all.get(i) + "' LIMIT 1");
                                                                    res.next();

                                                                    Cancel_Display(
                                                                            player,
                                                                            res.getInt("Min_X"),
                                                                            res.getInt("Min_Y"),
                                                                            res.getInt("Min_Z"),
                                                                            res.getInt("Max_X"),
                                                                            res.getInt("Max_Y"),
                                                                            res.getInt("Max_Z"),
                                                                            res.getString("World")
                                                                    );

                                                                    res.close(); // 關閉查詢
                                                                    sta.close(); // 關閉連線

                                                                    i = i + 1;

                                                                } catch (Exception ex) {
                                                                    ex.printStackTrace();
                                                                }
                                                            }
                                                        }.runTaskTimerAsynchronously(SuperFreedomSurvive.SYSTEM.Plugin.get(), 0L, 5L);

                                                    }
                                                }

                                            } catch (Exception ex) {
                                                ex.printStackTrace();
                                            }
                                        }
                                    } else {
                                        now = false;
                                        cancel();
                                        return;
                                    }
                                } else {
                                    cancel();
                                    return;
                                }
                            }
                        }.runTaskTimerAsynchronously(SuperFreedomSurvive.SYSTEM.Plugin.get(), 0L, 1L);



                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }.runTaskTimerAsynchronously(SuperFreedomSurvive.SYSTEM.Plugin.get(), 0L, 20L);

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        // 撤銷顯示
        new BukkitRunnable() {
            @Override
            final public void run() {

                if (!Bukkit.getPluginManager().isPluginEnabled(SuperFreedomSurvive.SYSTEM.Plugin.get())) {
                    // 插件停止
                    cancel();
                    return;
                }

                if (!now_2) {
                    now_2 = true;

                    Object[] obj = Cancel_Get();
                    if (obj != null) {
                        int Min_X = (int) obj[0];
                        int Min_Y = (int) obj[1];
                        int Min_Z = (int) obj[2];
                        int Max_X = (int) obj[3];
                        int Max_Y = (int) obj[4];
                        int Max_Z = (int) obj[5];
                        String world = (String) obj[6];

                        Collection collection = Bukkit.getServer().getOnlinePlayers();
                        Iterator iterator = collection.iterator();

                        while (iterator.hasNext()) {

                            Player player = ((Player) iterator.next());
                            if (player.isOnline()) {

                                if (player.getWorld().getName().equals(world)) {

                                    ////////////////////////////////////////////////////////////////////////////////////
                                    Cancel_Display(
                                            player,
                                            Min_X,
                                            Min_Y,
                                            Min_Z,
                                            Max_X,
                                            Max_Y,
                                            Max_Z,
                                            world
                                    );

                                    Remove(player.getName());

                                }
                            }
                        }
                    }
                    now_2 = false;
                }
            }
        }.runTaskTimerAsynchronously(SuperFreedomSurvive.SYSTEM.Plugin.get(), 0L, 1L);


    }

    @EventHandler
    final public void onPlayerJoinEvent(PlayerJoinEvent event) {
        // 離開事件
        Remove(event.getPlayer().getName());
    }


    static final public void Cancel_Display(
            Player player,
            int Min_X,
            int Min_Y,
            int Min_Z,
            int Max_X,
            int Max_Y,
            int Max_Z,
            String world
    ) {
        int L_X = Max_X - Min_X;
        int L_Y = Max_Y - Min_Y;
        int L_Z = Max_Z - Min_Z;

        if (player.isOnline()) {

            int Start_X = player.getLocation().getBlockX() - 40;
            int Start_Y = player.getLocation().getBlockY() - 40;
            if (Start_Y < 0) {
                Start_Y = 0;
            }
            if (Start_Y > 255) {
                Start_Y = 255;
            }
            int Start_Z = player.getLocation().getBlockZ() - 40;

            int End_X = player.getLocation().getBlockX() + 40;
            int End_Y = player.getLocation().getBlockY() + 40;
            if (End_Y < 0) {
                End_Y = 0;
            }
            if (End_Y > 255) {
                End_Y = 255;
            }
            int End_Z = player.getLocation().getBlockZ() + 40;

            ////////////////////////////////////////////////////////////////////////////////////
            for (int F_X = Min_X; F_X <= Min_X + L_X; ) {
                if (Min_Y >= Start_Y && Min_Y <= End_Y && Min_Z >= Start_Z && Min_Z <= End_Z) {
                    if (F_X >= Start_X && F_X <= End_X) {
                        Location loc = new Location(
                                player.getWorld(),
                                F_X,
                                Min_Y,
                                Min_Z
                        );
                        player.sendBlockChange(loc, loc.getWorld().getBlockAt(loc).getBlockData());
                        ++F_X;
                    } else if (F_X > End_X) {
                        break;
                    } else if (F_X < Start_X) {
                        F_X = Start_X;
                    }
                } else {
                    break;
                }
            }
            for (double F_Y = Min_Y; F_Y <= Min_Y + L_Y; ) {
                if (Min_X >= Start_X && Min_X <= End_X && Min_Z >= Start_Z && Min_Z <= End_Z) {
                    if (F_Y >= Start_Y && F_Y <= End_Y) {
                        Location loc = new Location(
                                player.getWorld(),
                                Min_X,
                                F_Y,
                                Min_Z
                        );
                        player.sendBlockChange(loc, loc.getWorld().getBlockAt(loc).getBlockData());
                        ++F_Y;
                    } else if (F_Y > End_Y) {
                        break;
                    } else if (F_Y < Start_Y) {
                        F_Y = Start_Y;
                    }
                } else {
                    break;
                }
            }
            for (double F_Z = Min_Z; F_Z <= Min_Z + L_Z; ) {
                if (Min_X >= Start_X && Min_X <= End_X && Min_Y >= Start_Y && Min_Y <= End_Y) {
                    if (F_Z >= Start_Z && F_Z <= End_Z) {
                        Location loc = new Location(
                                player.getWorld(),
                                Min_X,
                                Min_Y,
                                F_Z
                        );
                        player.sendBlockChange(loc, loc.getWorld().getBlockAt(loc).getBlockData());
                        ++F_Z;
                    } else if (F_Z > End_Z) {
                        break;
                    } else if (F_Z < Start_Z) {
                        F_Z = Start_Z;
                    }
                } else {
                    break;
                }
            }


            for (double F_X = Min_X; F_X <= Min_X + L_X; ) {
                if (Min_Y + L_Y >= Start_Y && Min_Y + L_Y <= End_Y && Min_Z >= Start_Z && Min_Z <= End_Z) {
                    if (F_X >= Start_X && F_X <= End_X) {
                        Location loc = new Location(
                                player.getWorld(),
                                F_X,
                                Min_Y + L_Y,
                                Min_Z
                        );
                        player.sendBlockChange(loc, loc.getWorld().getBlockAt(loc).getBlockData());
                        ++F_X;
                    } else if (F_X > End_X) {
                        break;
                    } else if (F_X < Start_X) {
                        F_X = Start_X;
                    }
                } else {
                    break;
                }
            }
            for (double F_Z = Min_Z; F_Z <= Min_Z + L_Z; ) {
                if (Min_Y + L_Y >= Start_Y && Min_Y + L_Y <= End_Y && Min_X >= Start_X && Min_X <= End_X) {
                    if (F_Z >= Start_Z && F_Z <= End_Z) {
                        Location loc = new Location(
                                player.getWorld(),
                                Min_X,
                                Min_Y + L_Y,
                                F_Z
                        );
                        player.sendBlockChange(loc, loc.getWorld().getBlockAt(loc).getBlockData());
                        ++F_Z;
                    } else if (F_Z > End_Z) {
                        break;
                    } else if (F_Z < Start_Z) {
                        F_Z = Start_Z;
                    }
                } else {
                    break;
                }
            }


            for (double F_X = Max_X; F_X >= Max_X - L_X; ) {
                if (Max_Y + 1 >= Start_Y && Max_Y <= End_Y && Max_Z >= Start_Z && Max_Z <= End_Z) {
                    if (F_X >= Start_X && F_X <= End_X) {
                        Location loc = new Location(
                                player.getWorld(),
                                F_X,
                                Max_Y,
                                Max_Z
                        );
                        player.sendBlockChange(loc, loc.getWorld().getBlockAt(loc).getBlockData());
                        --F_X;
                    } else if (F_X > End_X) {
                        F_X = End_X;
                    } else if (F_X < Start_X) {
                        break;
                    }
                } else {
                    break;
                }
            }
            for (double F_Y = Max_Y; F_Y >= Max_Y - L_Y; ) {
                if (Max_X >= Start_X && Max_X <= End_X && Max_Z >= Start_Z && Max_Z <= End_Z) {
                    if (F_Y >= Start_Y && F_Y <= End_Y) {
                        Location loc = new Location(
                                player.getWorld(),
                                Max_X,
                                F_Y,
                                Max_Z
                        );
                        player.sendBlockChange(loc, loc.getWorld().getBlockAt(loc).getBlockData());
                        --F_Y;
                    } else if (F_Y > End_Y) {
                        F_Y = End_Y;
                    } else if (F_Y < Start_Y) {
                        break;
                    }
                } else {
                    break;
                }
            }
            for (double F_Z = Max_Z; F_Z >= Max_Z - L_Z; ) {
                if (Max_Y >= Start_Y && Max_Y <= End_Y && Max_X >= Start_X && Max_X <= End_X) {
                    if (F_Z >= Start_Z && F_Z <= End_Z) {
                        Location loc = new Location(
                                player.getWorld(),
                                Max_X,
                                Max_Y,
                                F_Z
                        );
                        player.sendBlockChange(loc, loc.getWorld().getBlockAt(loc).getBlockData());
                        --F_Z;
                    } else if (F_Z > End_Z) {
                        F_Z = End_Z;
                    } else if (F_Z < Start_Z) {
                        break;
                    }
                } else {
                    break;
                }
            }


            for (double F_X = Max_X; F_X >= Max_X - L_X; ) {
                if (Min_Y >= Start_Y && Min_Y <= End_Y && Max_Z >= Start_Z && Max_Z <= End_Z) {
                    if (F_X >= Start_X && F_X <= End_X) {
                        Location loc = new Location(
                                player.getWorld(),
                                F_X,
                                Min_Y,
                                Max_Z
                        );
                        player.sendBlockChange(loc, loc.getWorld().getBlockAt(loc).getBlockData());
                        --F_X;
                    } else if (F_X > End_X) {
                        F_X = End_X;
                    } else if (F_X < Start_X) {
                        break;
                    }
                } else {
                    break;
                }
            }
            for (double F_Z = Max_Z; F_Z >= Max_Z - L_Z; ) {
                if (Min_Y >= Start_Y && Min_Y <= End_Y && Max_X >= Start_X && Max_X <= End_X) {
                    if (F_Z >= Start_Z && F_Z <= End_Z) {
                        Location loc = new Location(
                                player.getWorld(),
                                Max_X,
                                Min_Y,
                                F_Z
                        );
                        player.sendBlockChange(loc, loc.getWorld().getBlockAt(loc).getBlockData());
                        --F_Z;
                    } else if (F_Z > End_Z) {
                        F_Z = End_Z;
                    } else if (F_Z < Start_Z) {
                        break;
                    }
                } else {
                    break;
                }
            }


            for (double F_Y = Min_Y; F_Y <= Min_Y + L_Y; ) {
                if (Max_X >= Start_X && Max_X <= End_X && Min_Z >= Start_Z && Min_Z <= End_Z) {
                    if (F_Y >= Start_Y && F_Y <= End_Y) {
                        Location loc = new Location(
                                player.getWorld(),
                                Max_X,
                                F_Y,
                                Min_Z
                        );
                        player.sendBlockChange(loc, loc.getWorld().getBlockAt(loc).getBlockData());
                        ++F_Y;
                    } else if (F_Y > End_Y) {
                        break;
                    } else if (F_Y < Start_Y) {
                        F_Y = Start_Y;
                    }
                } else {
                    break;
                }
            }
            for (double F_Y = Min_Y; F_Y <= Min_Y + L_Y; ) {
                if (Min_X >= Start_X && Min_X <= End_X && Max_Z >= Start_Z && Max_Z <= End_Z) {
                    if (F_Y >= Start_Y && F_Y <= End_Y) {
                        Location loc = new Location(
                                player.getWorld(),
                                Min_X,
                                F_Y,
                                Max_Z
                        );
                        player.sendBlockChange(loc, loc.getWorld().getBlockAt(loc).getBlockData());
                        ++F_Y;
                    } else if (F_Y > End_Y) {
                        break;
                    } else if (F_Y < Start_Y) {
                        F_Y = Start_Y;
                    }
                } else {
                    break;
                }
            }

        }
        Remove(player.getName());
    }
}
