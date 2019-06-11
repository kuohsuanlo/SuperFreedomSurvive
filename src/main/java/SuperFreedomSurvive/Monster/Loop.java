package SuperFreedomSurvive.Monster;

import SuperFreedomSurvive.SYSTEM.MySQL;
import SuperFreedomSurvive.World.Data;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.boss.BossBar;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Loop {

    static public void Start() {
        new BukkitRunnable() {
            @Override
            final public void run() {
                try {
                    Connection con = MySQL.getConnection(); // 連線 MySQL
                    Statement sta = con.createStatement(); // 取得控制庫
                    ResultSet res = null;


                    // 準備資料
                    ArrayList<Location> array = new ArrayList<Location>();


                    // 世界類型是否為個人世界
                    List<World> worlds = Bukkit.getWorlds();
                    for (int i = 0, s = worlds.size(); i < s; ++i ) {
                        if ( Data.getOwner(worlds.get(i).getName()) != null && worlds.get(i).getDifficulty() != Difficulty.PEACEFUL ) {
                            // 是沒錯
                            // 取得世界內的所有玩家
                            List<Player> players = worlds.get(i).getPlayers();

                            for (int p = 0, ss = players.size(); p < ss; ++p ) {

                                Location location = players.get(p).getLocation();

                                // 取得玩家位置 並寫入數組
                                array.add(location);
                                /*
                                // 取得玩家位置
                                String[] d = {
                                        location.getWorld().getName(),
                                        location.getBlockX() + "",
                                        location.getBlockY() + "",
                                        location.getBlockZ() + ""
                                };
*/
                            }
                        }
                    }






                    // 將取得的所有玩家位置進行查詢
                    for ( int i = 0 , s = array.size() ; i < s ; ++i ) {

                        res = sta.executeQuery("SELECT * FROM `custom-monster_data` WHERE `Enable` = '1' AND" +
                                "`World` = '" + array.get(i).getWorld().getName() + "' AND " +
                                "`Spawn_Min_X` <= '" + array.get(i).getBlockX() + "' AND " +
                                "`Spawn_Min_Y` <= '" + array.get(i).getBlockY() + "' AND " +
                                "`Spawn_Min_Z` <= '" + array.get(i).getBlockZ() + "' AND " +
                                "`Spawn_Max_X` >= '" + array.get(i).getBlockX() + "' AND " +
                                "`Spawn_Max_Y` >= '" + array.get(i).getBlockY() + "' AND " +
                                "`Spawn_Max_Z` >= '" + array.get(i).getBlockZ() + "' AND " +
                                "`Time_Spawn` <= '" + (new Date().getTime()) + "' " +
                                "ORDER BY `Time_Spawn` DESC LIMIT 5");

                        while (res.next()) {

                            // 取得必要資料
                            World world = array.get(i).getWorld();
                            int x = res.getInt("X");
                            int y = res.getInt("Y");
                            int z = res.getInt("Z");
                            String ID = res.getString("ID");

                            String entity_name = res.getString("Entity_Name");
                            EntityType entity_type = Value.conversionEntityType( res.getInt("Entity_Type") );
                            Material entity_helmet = Value.conversionHelmet( res.getInt("Entity_Helmet") );
                            Material entity_chestplate = Value.conversionChestplate( res.getInt("Entity_Chestplate") );
                            Material entity_leggings = Value.conversionLeggings( res.getInt("Entity_Leggings") );
                            Material entity_boots = Value.conversionBoots( res.getInt("Entity_Boots") );
                            Material entity_offHand = Value.conversionHelmet( res.getInt("Entity_OffHand") );
                            Material entity_mainHand = Value.conversionHelmet( res.getInt("Entity_MainHand") );

                            boolean entity_enchant_helmet = res.getBoolean("Entity_Enchant_Helmet");
                            boolean entity_enchant_chestplate = res.getBoolean("Entity_Enchant_Chestplate");
                            boolean entity_enchant_leggings = res.getBoolean("Entity_Enchant_Leggings");
                            boolean entity_enchant_boots = res.getBoolean("Entity_Enchant_Boots");
                            boolean entity_enchant_offHand = res.getBoolean("Entity_Enchant_OffHand");
                            boolean entity_enchant_mainHand = res.getBoolean("Entity_Enchant_MainHand");

                            boolean entity_bossBar = res.getBoolean("Entity_BossBar");

                            int attribute_generic_armor = res.getInt("Attribute_GENERIC_ARMOR");
                            int attribute_generic_armor_toughness = res.getInt("Attribute_GENERIC_ARMOR_TOUGHNESS");
                            int attribute_generic_attack_damage = res.getInt("Attribute_GENERIC_ATTACK_DAMAGE");
                            int attribute_generic_follow_range = res.getInt("Attribute_GENERIC_FOLLOW_RANGE");
                            int attribute_generic_knockback_resistance = res.getInt("Attribute_GENERIC_KNOCKBACK_RESISTANCE");
                            int attribute_generic_max_health = res.getInt("Attribute_GENERIC_MAX_HEALTH");
                            int attribute_generic_movement_speed = res.getInt("Attribute_GENERIC_MOVEMENT_SPEED");

                            double spawn_x = res.getInt("Spawn_X") + 0.5;
                            int spawn_y = res.getInt("Spawn_Y");
                            double spawn_z = res.getInt("Spawn_Z") + 0.5;
                            int spawn_amount = res.getInt("Spawn_Amount");
                            int spawn_delay = res.getInt("Spawn_Delay");
                            //int spawn_player_distance = res.getInt("Spawn_Player_Distance");
                            int spawn_range = res.getInt("Spawn_Range") - 1;

                            long time_spawn = res.getLong("Time_Spawn");




                            // 安全檢查
                            if ( entity_type == null ) continue;




                            // 循環怪物數量
                            for (int a = 0; a < spawn_amount ; a++) {

                                // 開始生成
                                // 計算隨機位置
                                double random_min_x = spawn_x - spawn_range;
                                int random_min_y = spawn_y - spawn_range;
                                double random_min_z = spawn_z - spawn_range;

                                double random_max_x = spawn_x + spawn_range;
                                int random_max_y = spawn_y + spawn_range;
                                double random_max_z = spawn_z + spawn_range;

                                // 抽選位置
                                double random_x = random_min_x + (int) (Math.random() * (spawn_range * 2));
                                int random_y = random_min_y + (int) (Math.random() * (spawn_range * 2));
                                double random_z = random_min_z + (int) (Math.random() * (spawn_range * 2));

                                if (random_x > random_max_x) random_x = random_max_x;
                                if (random_y > random_max_y) random_y = random_max_y;
                                if (random_z > random_max_z) random_z = random_max_z;

                                // 準備座標位置
                                Location location = new Location(
                                        world,
                                        random_x,
                                        random_y,
                                        random_z
                                );

                                // 生成
                                Entity entity = world.spawnEntity(location, entity_type);

                                if (entity instanceof LivingEntity) {
                                    LivingEntity living_entity = (LivingEntity) entity;

                                    // 禁止所有掉落物 經驗值為0
                                    living_entity.addScoreboardTag("BanDrop");
                                    //living_entity.setMetadatA("",Value.ban_drop);

                                    // 設置顯示裝備
                                    // 掉落機率通通為 0
                                    EntityEquipment equipment = living_entity.getEquipment();
                                    if (equipment != null) {

                                        if (entity_helmet != null) {
                                            ItemStack item = new ItemStack(entity_helmet,1);
                                            ItemMeta meta = item.getItemMeta();
                                            if (entity_enchant_helmet) meta.addEnchant(Enchantment.BINDING_CURSE, 1, false);
                                            item.setItemMeta(meta);

                                            equipment.setHelmet(item);
                                            equipment.setHelmetDropChance(0);
                                        } else {
                                            equipment.setHelmet(null);
                                        }

                                        if (entity_chestplate != null) {
                                            ItemStack item = new ItemStack(entity_chestplate,1);
                                            ItemMeta meta = item.getItemMeta();
                                            if (entity_enchant_chestplate) meta.addEnchant(Enchantment.BINDING_CURSE, 1, false);
                                            item.setItemMeta(meta);

                                            equipment.setChestplate(item);
                                            equipment.setChestplateDropChance(0);
                                        } else {
                                            equipment.setChestplate(null);
                                        }

                                        if (entity_leggings != null) {
                                            ItemStack item = new ItemStack(entity_leggings,1);
                                            ItemMeta meta = item.getItemMeta();
                                            if (entity_enchant_leggings) meta.addEnchant(Enchantment.BINDING_CURSE, 1, false);
                                            item.setItemMeta(meta);

                                            equipment.setLeggings(item);
                                            equipment.setLeggingsDropChance(0);
                                        } else {
                                            equipment.setLeggings(null);
                                        }

                                        if (entity_boots != null) {
                                            ItemStack item = new ItemStack(entity_boots,1);
                                            ItemMeta meta = item.getItemMeta();
                                            if (entity_enchant_boots) meta.addEnchant(Enchantment.BINDING_CURSE, 1, false);
                                            item.setItemMeta(meta);

                                            equipment.setBoots(item);
                                            equipment.setBootsDropChance(0);
                                        } else {
                                            equipment.setBoots(null);
                                        }

                                        if (entity_offHand != null) {
                                            ItemStack item = new ItemStack(entity_offHand,1);
                                            ItemMeta meta = item.getItemMeta();
                                            if (entity_enchant_offHand) meta.addEnchant(Enchantment.BINDING_CURSE, 1, false);
                                            item.setItemMeta(meta);

                                            equipment.setItemInOffHand(item);
                                            equipment.setItemInOffHandDropChance(0);
                                        } else {
                                            equipment.setItemInOffHand(null);
                                        }

                                        if (entity_mainHand != null) {
                                            ItemStack item = new ItemStack(entity_mainHand,1);
                                            ItemMeta meta = item.getItemMeta();
                                            if (entity_enchant_mainHand) meta.addEnchant(Enchantment.BINDING_CURSE, 1, false);
                                            item.setItemMeta(meta);

                                            equipment.setItemInMainHand(item);
                                            equipment.setItemInMainHandDropChance(0);
                                        } else {
                                            equipment.setItemInMainHand(null);
                                        }
                                    }

                                    // 取得屬性資料
                                    AttributeInstance generic_armor = living_entity.getAttribute(Attribute.GENERIC_ARMOR);
                                    AttributeInstance generic_armor_toughness = living_entity.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS);
                                    AttributeInstance generic_attack_damage = living_entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE);
                                    AttributeInstance generic_follow_range = living_entity.getAttribute(Attribute.GENERIC_FOLLOW_RANGE);
                                    AttributeInstance generic_knockback_resistance = living_entity.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE);
                                    AttributeInstance generic_max_health = living_entity.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                                    AttributeInstance generic_movement_speed = living_entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);

                                    // 設置屬性資料
                                    if (generic_armor != null) generic_armor.setBaseValue(attribute_generic_armor);
                                    if (generic_armor_toughness != null) generic_armor_toughness.setBaseValue(((double) attribute_generic_armor_toughness ) * 1.0d);
                                    if (generic_attack_damage != null) generic_attack_damage.setBaseValue(((double) attribute_generic_attack_damage ) * 1.0d);
                                    if (generic_follow_range != null) generic_follow_range.setBaseValue(((double) attribute_generic_follow_range ) * 1.0d);
                                    if (generic_knockback_resistance != null) generic_knockback_resistance.setBaseValue( (((double) attribute_generic_knockback_resistance ) + 1) / 100);
                                    if (generic_max_health != null) generic_max_health.setBaseValue(((double) attribute_generic_max_health ) * 1.0d);
                                    living_entity.setHealth(((double) attribute_generic_max_health ) * 1.0d);
                                    if (generic_movement_speed != null) generic_movement_speed.setBaseValue(((double) attribute_generic_movement_speed ) / 10);

                                    // 設定一般資料
                                    if (entity_name != null) {
                                        living_entity.setCustomName(entity_name);
                                        living_entity.setCustomNameVisible(true);
                                    }
                                    if (living_entity instanceof BossBar) {
                                        ((BossBar) living_entity).setVisible(entity_bossBar);
                                    }

                                }
/*
                                // 檢查類型
                                switch (entity_type) {
                                    // 殭屍
                                    case ZOMBIE:
                                        Zombie zombie = (Zombie) entity;
                                        break;
                                }
*/
                            }

                            // 生成完成 更新此下次生成時間
                            Value.updateTimeSpawn(ID,(new Date().getTime()) + (spawn_delay * 1000));
                        }

                        res.close(); // 關閉查詢
                    }




/*
                    // 準備生成
                    // 取得下一次要生成的資料
                    ResultSet res = sta.executeQuery("SELECT * FROM `custom-monster_data` WHERE `Time_Spawn` <= '" + (new Date().getTime()) + "' LIMIT 10");
                    while (res.next()) {
                        res.getInt("")
                    }
*/





                    array = null;

                    //res.close(); // 關閉查詢
                    sta.close(); // 關閉連線

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }.runTaskTimer(SuperFreedomSurvive.SYSTEM.Plugin.get(), 0L, 10L);
    }

}
