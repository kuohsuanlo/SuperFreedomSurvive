package SuperFreedomSurvive.Arms.Registers;

import SuperFreedomSurvive.Arms.Use;
import org.bukkit.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.tags.ItemTagType;

import java.util.Arrays;

final public class flame_magic_wand {

    static org.bukkit.NamespacedKey durable = SuperFreedomSurvive.SYSTEM.NamespacedKey.getKey("Durable"); // 創建特殊類型資料
    static org.bukkit.NamespacedKey random = SuperFreedomSurvive.SYSTEM.NamespacedKey.getKey("Random"); // 創建特殊類型資料
    static org.bukkit.NamespacedKey arms_key = Use.arms_key; // 創建特殊類型資料


    static final public ItemStack level_initial() {
        return level_initial(120);
    }

    static final public ItemStack level_initial(int loss) {
        ItemStack item = new ItemStack(Material.STICK);
        ItemMeta meta = item.getItemMeta();

        meta.getCustomTagContainer().setCustomTag(arms_key, ItemTagType.STRING, "#level_initial__flame_magic_wand"); // 登入key

        meta.getCustomTagContainer().setCustomTag(durable, ItemTagType.INTEGER, loss); // 耐久度

        meta.getCustomTagContainer().setCustomTag(random, ItemTagType.STRING, SuperFreedomSurvive.Arms.Calculations.Random.Create(10)); // 確保不可疊加

        meta.setDisplayName("§e初級 熔岩魔法仗(開發未完成-測試中)");
        meta.setLore(Arrays.asList(
                ("§r§f耐久度:" + meta.getCustomTagContainer().getCustomTag(durable, ItemTagType.INTEGER) + " / 120"),
                ("§r§f傷害:5 HP + 額外燃燒效果 10 秒"),
                ("§r§f詠唱:6 秒"),
                ("§r§f條件:指向方向第 1 隻怪物"),
                ("§r§f範圍:5 格"),
                ("§r§f距離:最遠 10 格")
        ));

        item.setItemMeta(meta); // 寫入資料
        return item; // 設置完成

    }

    // 初級
    static final public void level_initial(PlayerInteractEvent event) {
        /*
        Player player = event.getPlayer();
        ItemStack player_item = event.getItem();
        ItemMeta player_item_meta = player_item.getItemMeta();
        CustomItemTagContainer player_item_tag = player_item_meta.getCustomTagContainer();

        int distance = 10; // 距離
        int range = 3; // 範圍
        int damage = 5; // 傷害
        int sing_tick = 120; // 詠唱時間

        // 檢查當前是否有詠唱
        if ( ! ServerPlugin.Arms.Singing.Have( player.getName() ) ) {

            // 詠唱排隊
            ServerPlugin.Arms.Singing.Add( player.getName() );

            // 特效
            // 跟隨
            Animation.Follow("rotate", player, Particle.FLAME, 1, 360, sing_tick, null);

            new BukkitRunnable() {

                int tick = 0;

                @Override
                final public void run() {
                    if ( tick >= sing_tick ) {

                        // 搜尋怪物位置
                        Location location = Search.Range(player.getLocation(), distance);

                        // 特效
                        // 固定
                        Animation.Fixed("diffusion_3D", location, Particle.FLAME, range, 40, 4, null);

                        // 音效
                        // location.getWorld().playSound( location , "minecraft:entity.generic.explode" , 3 , 1 );

                        // 造成傷害
                        ArrayList<LivingEntity> entity = ServerPlugin.Arms.Calculations.Entity.Range(location, range); // 取得實體清單
                        if (entity != null) {

                            // 計算 是否有權限
                            int Min_X = 0;
                            int Min_Y = 0;
                            int Min_Z = 0;
                            int Max_X = 0;
                            int Max_Y = 0;
                            int Max_Z = 0;
                            String world = null;
                            // 取得最大 和 最小距離
                            for (int i = 0; i < entity.size(); ++i) {
                                Location location_ = entity.get(i).getLocation();
                                world = entity.get(i).getWorld().getName();
                                if (i == 0) {
                                    Min_X = location_.getBlockX();
                                    Min_Y = location_.getBlockY();
                                    Min_Z = location_.getBlockZ();
                                    Max_X = location_.getBlockX();
                                    Max_Y = location_.getBlockY();
                                    Max_Z = location_.getBlockZ();
                                } else {
                                    if (location_.getBlockX() < Min_X) {
                                        Min_X = location_.getBlockX();
                                    } else if (location_.getBlockX() > Max_X) {
                                        Max_X = location_.getBlockX();
                                    }
                                    //      Y
                                    if (location_.getBlockY() < Min_Y) {
                                        Min_Y = location_.getBlockY();
                                    } else if (location_.getBlockY() > Max_Y) {
                                        Max_Y = location_.getBlockY();
                                    }
                                    //      Z
                                    if (location_.getBlockZ() < Min_Z) {
                                        Min_Z = location_.getBlockZ();
                                    } else if (location_.getBlockZ() > Max_Z) {
                                        Max_Z = location_.getBlockZ();
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
                                            " AND `Permissions_EntityDamageByEntity` = '0' ORDER BY `Level` LIMIT 2");
                                    // 跳到最後一行
                                    res.last();
                                    // 最後一行 行數 是否 > 0
                                    if (res.getRow() > 0) {
                                        // 不允許
                                        event.setCancelled(true);
                                        ServerPlugin.Land.Display.Message( player ,"§c領地禁止 傷害實體" );

                                    } else {


                                        for (int i = 0; i < entity.size(); ++i) {

                                            if (entity.get(i) instanceof Player) {

                                                if (!((Player) entity.get(i)).getName().equals(player.getName())) {
                                                    if (entity.get(i).getHealth() - damage < 0) {
                                                        entity.get(i).setHealth(0);
                                                    } else {
                                                        entity.get(i).setHealth(entity.get(i).getHealth() - damage);
                                                        entity.get(i).setFireTicks(200);
                                                    }
                                                }

                                            } else {

                                                if (entity.get(i).getHealth() - damage < 0) {
                                                    entity.get(i).setHealth(0);
                                                } else {
                                                    entity.get(i).setHealth(entity.get(i).getHealth() - damage);
                                                    entity.get(i).setFireTicks(200);
                                                }

                                            }
                                        }



                                    }
                                } catch (Exception ex) {
                                    // 出錯
                                    cancel();
                                    return;
                                    //Bukkit.getLogger().info(ex.getMessage());
                                }
                            }
                        }

                        ServerPlugin.Arms.Singing.Remove( player.getName() ); // 移除

                        // 耐久計算
                        if (player_item_tag.getCustomTag(durable, ItemTagType.INTEGER) - 1 <= 0) {
                            // 空了 爆掉
                            player.getInventory().setItemInMainHand(null);

                            event.getPlayer().playSound(event.getPlayer().getLocation(), "minecraft:entity.item.break", 1, 1);

                        } else {
                            // 還有耐久
                            player.getInventory().setItemInMainHand(level_initial( player_item_tag.getCustomTag(durable, ItemTagType.INTEGER) - 1 ));

                        }
                        cancel();
                        return;

                    }

                    ++tick;

                }
            }.runTaskTimer(ServerPlugin.SYSTEM.Plugin.get(),0L,1L);


        }


*/
    }
}
