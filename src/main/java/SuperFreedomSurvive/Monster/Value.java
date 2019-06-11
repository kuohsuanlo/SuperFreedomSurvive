package SuperFreedomSurvive.Monster;

import SuperFreedomSurvive.SYSTEM.MySQL;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Value {
    // 數值

















    // 檢查數值是否都有齊全
    static public boolean isNoProblem(String ID) {
        if (
                getEntityType(ID) != null &&

                        getSpawnX(ID) != -2147483647 &&
                        getSpawnY(ID) != -2147483647 &&
                        getSpawnZ(ID) != -2147483647 &&
                        getSpawnDelay(ID) > 0 &&
                        getSpawnAmount(ID) > 0 &&
                        getSpawnPlayerDistance(ID) > 0 &&
                        getSpawnRange(ID) > 0 &&

                        getAttribute(ID,"GENERIC_ARMOR") > -1 &&
                        getAttribute(ID,"GENERIC_ARMOR_TOUGHNESS") > -1 &&
                        getAttribute(ID,"GENERIC_ATTACK_DAMAGE") > -1 &&
                        getAttribute(ID,"GENERIC_FOLLOW_RANGE") > -1 &&
                        getAttribute(ID,"GENERIC_KNOCKBACK_RESISTANCE") > -1 &&
                        getAttribute(ID,"GENERIC_KNOCKBACK_RESISTANCE") > -1 &&
                        getAttribute(ID,"GENERIC_MOVEMENT_SPEED") > -1
        ) {
            return true;
        }
        return false;
    }




























    // 取得是否啟用
    static public boolean getEnable(String ID) {
        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫

            // 檢查是否資料符合
            ResultSet res = sta.executeQuery("SELECT (Enable) FROM `custom-monster_data` WHERE `ID` = '" + ID + "' LIMIT 1");
            // 跳到最後一行
            res.last();
            // 最後一行 行數 是否 > 0
            if (res.getRow() > 0) {
                // 有資料
                boolean b = res.getBoolean("Enable");

                res.close(); // 關閉查詢
                sta.close(); // 關閉連線

                return b;

            }

            res.close(); // 關閉查詢
            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    // 設置是否啟用
    static public void setEnable(String ID) {

        boolean type = true;

        if (getEnable((ID))) type = false;

        setEnable(ID , type);
    }

    // 設置是否啟用
    static public void setEnable(String ID,boolean enable) {
        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫
            ResultSet res;

            // 做額外換算
            if (enable) {
                // 啟用
                res = sta.executeQuery("SELECT * FROM `custom-monster_data` WHERE `ID` = '" + ID + "' LIMIT 1");
                // 跳到最後一行
                res.last();
                // 最後一行 行數 是否 > 0
                if (res.getRow() > 0) {
                    // 有資料
                    // 跳回 初始行 必須使用 next() 才能取得資料
                    res.beforeFirst();
                    res.next();

                    int spawn_x = res.getInt("Spawn_X");
                    int spawn_y = res.getInt("Spawn_Y");
                    int spawn_z = res.getInt("Spawn_Z");
                    int spawn_player_distance = res.getInt("Spawn_Player_Distance");
                    int min_x = spawn_x - spawn_player_distance;
                    int min_y = spawn_y - spawn_player_distance;
                    int min_z = spawn_z - spawn_player_distance;
                    int max_x = spawn_x + spawn_player_distance;
                    int max_y = spawn_y + spawn_player_distance;
                    int max_z = spawn_z + spawn_player_distance;

                    res.close(); // 關閉查詢

                    if (min_x < -32768) min_x = -32768;
                    if (min_x > 32767) min_x = 32767;
                    if (min_y < 0) min_y = 0;
                    if (min_y > 255) min_y = 255;
                    if (min_z < -32768) min_z = -32768;
                    if (min_z > 32767) min_z = 32767;

                    if (max_x < -32768) max_x = -32768;
                    if (max_x > 32767) max_x = 32767;
                    if (max_y < 0) max_y = 0;
                    if (max_y > 255) max_y = 255;
                    if (max_z < -32768) max_z = -32768;
                    if (max_z > 32767) max_z = 32767;

                    sta.executeUpdate("UPDATE `custom-monster_data` SET " +
                            " `Enable` = '1' ," +
                            " `Spawn_Min_X` = '" + min_x + "' ," +
                            " `Spawn_Min_Y` = '" + min_y + "' ," +
                            " `Spawn_Min_Z` = '" + min_z + "' ," +
                            " `Spawn_Max_X` = '" + max_x + "' ," +
                            " `Spawn_Max_Y` = '" + max_y + "' ," +
                            " `Spawn_Max_Z` = '" + max_z + "' " +
                            "  WHERE `ID` = '" + ID + "'");


                }
                res.close(); // 關閉查詢

            } else {
                // 停用
                sta.executeUpdate("UPDATE `custom-monster_data` SET " +
                        " `Enable` = '0' ," +
                        " `Spawn_Min_X` = NULL ," +
                        " `Spawn_Min_Y` = NULL ," +
                        " `Spawn_Min_Z` = NULL ," +
                        " `Spawn_Max_X` = NULL ," +
                        " `Spawn_Max_Y` = NULL ," +
                        " `Spawn_Max_Z` = NULL " +
                        "  WHERE `ID` = '" + ID + "'");
            }

            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
































    // 設置怪物類型
    static public void updateTimeSpawn(String ID,long time) {
        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫

            sta.executeUpdate("UPDATE `custom-monster_data` SET `Time_Spawn` = '" + time + "' WHERE `ID` = '" + ID + "'");

            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }




































    // 取得怪物類型
    static public EntityType getEntityType(String ID) {
        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫

            // 檢查是否資料符合
            ResultSet res = sta.executeQuery("SELECT (Entity_Type) FROM `custom-monster_data` WHERE `ID` = '" + ID + "' LIMIT 1");
            // 跳到最後一行
            res.last();
            // 最後一行 行數 是否 > 0
            if (res.getRow() > 0) {
                // 有資料
                int e = res.getInt("Entity_Type");

                res.close(); // 關閉查詢
                sta.close(); // 關閉連線

                return conversionEntityType(e);
            }

            res.close(); // 關閉查詢
            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    // 設置怪物類型
    static public void setEntityType(String ID,EntityType entity_type) {

        int type = -1;

        if (entity_type == null) {
            type = 0;
        } else {
            type = conversionEntityType(entity_type);
        }

        if ( type > -1 ) {
            try {
                Connection con = MySQL.getConnection(); // 連線 MySQL
                Statement sta = con.createStatement(); // 取得控制庫

                sta.executeUpdate("UPDATE `custom-monster_data` SET `Entity_Type` = '" + type + "' WHERE `ID` = '" + ID + "'");

                sta.close(); // 關閉連線

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    static public EntityType conversionEntityType(int e) {
        switch ( e ) {
            case 0:
                return null;
            case 1:
                return EntityType.ZOMBIE;
            case 2:
                return EntityType.PIG_ZOMBIE;
            case 3:
                return EntityType.CREEPER;
            case 4:
                return EntityType.ELDER_GUARDIAN;
            case 5:
                return EntityType.WITHER_SKELETON;
            case 6:
                return EntityType.STRAY;
            case 7:
                return EntityType.HUSK;
            case 8:
                return EntityType.EVOKER;
            case 9:
                return EntityType.VEX;
            case 10:
                return EntityType.VINDICATOR;
            case 11:
                return EntityType.ILLUSIONER;
            case 12:
                return EntityType.SKELETON;
            case 13:
                return EntityType.SPIDER;
            case 14:
                return EntityType.GIANT;
            case 15:
                return EntityType.SLIME;
            case 16:
                return EntityType.GHAST;
            case 17:
                return EntityType.ENDERMAN;
            case 18:
                return EntityType.CAVE_SPIDER;
            case 19:
                return EntityType.SILVERFISH;
            case 20:
                return EntityType.BLAZE;
            case 21:
                return EntityType.MAGMA_CUBE;
            case 22:
                return EntityType.WITHER;
            case 23:
                return EntityType.WITCH;
            case 24:
                return EntityType.ENDERMITE;
            case 25:
                return EntityType.GUARDIAN;
            case 26:
                return EntityType.SHULKER;
            case 27:
                return EntityType.LIGHTNING;
            case 28:
                return EntityType.IRON_GOLEM;
            case 29:
                return EntityType.SNOWMAN;
            default:
                return null;
        }
    }
    static public int conversionEntityType(EntityType e) {
        switch (e) {
            case ZOMBIE:
                return 1;
            case PIG_ZOMBIE:
                return 2;
            case CREEPER:
                return 3;
            case ELDER_GUARDIAN:
                return 4;
            case WITHER_SKELETON:
                return 5;
            case STRAY:
                return 6;
            case HUSK:
                return 7;
            case EVOKER:
                return 8;
            case VEX:
                return 9;
            case VINDICATOR:
                return 10;
            case ILLUSIONER:
                return 11;
            case SKELETON:
                return 12;
            case SPIDER:
                return 13;
            case GIANT:
                return 14;
            case SLIME:
                return 15;
            case GHAST:
                return 16;
            case ENDERMAN:
                return 17;
            case CAVE_SPIDER:
                return 18;
            case SILVERFISH:
                return 19;
            case BLAZE:
                return 20;
            case MAGMA_CUBE:
                return 21;
            case WITHER:
                return 22;
            case WITCH:
                return 23;
            case ENDERMITE:
                return 24;
            case GUARDIAN:
                return 25;
            case SHULKER:
                return 26;
            case LIGHTNING:
                return 27;
            case IRON_GOLEM:
                return 28;
            case SNOWMAN:
                return 29;
            default:
                return -1;
        }
    }

































    // 取得怪物頭盔
    static public Material getHelmet(String ID) {
        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫

            // 檢查是否資料符合
            ResultSet res = sta.executeQuery("SELECT (Entity_Helmet) FROM `custom-monster_data` WHERE `ID` = '" + ID + "' LIMIT 1");
            // 跳到最後一行
            res.last();
            // 最後一行 行數 是否 > 0
            if (res.getRow() > 0) {
                // 有資料
                Material material = conversionHelmet(res.getInt("Entity_Helmet"));

                res.close(); // 關閉查詢
                sta.close(); // 關閉連線

                return material;
            }

            res.close(); // 關閉查詢
            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    // 設定怪物頭盔
    static public void setHelmet(String ID,Material material) {
        Value.setEnable(ID,false); // 取消啟用

        int type = -1;

        if (material == null) {
            type = 0;
        } else {
            type = conversionHelmet(material);
        }

        if ( type > -1 ) {
            try {
                Connection con = MySQL.getConnection(); // 連線 MySQL
                Statement sta = con.createStatement(); // 取得控制庫

                sta.executeUpdate("UPDATE `custom-monster_data` SET `Entity_Helmet` = '" + type + "' WHERE `ID` = '" + ID + "'");

                sta.close(); // 關閉連線

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    static public Material conversionHelmet(int e) {
        switch ( e ) {
            case 0:
                return null;
            case 1:
                return Material.LEATHER_HELMET;
            case 2:
                return Material.CHAINMAIL_HELMET;
            case 3:
                return Material.IRON_HELMET;
            case 4:
                return Material.GOLDEN_HELMET;
            case 5:
                return Material.DIAMOND_HELMET;
            case 6:
                return Material.TURTLE_HELMET;
        }
        return null;
    }
    static public int conversionHelmet(Material e) {
        switch (e) {
            case LEATHER_HELMET:
                return 1;
            case CHAINMAIL_HELMET:
                return 2;
            case IRON_HELMET:
                return 3;
            case GOLDEN_HELMET:
                return 4;
            case DIAMOND_HELMET:
                return 5;
            case TURTLE_HELMET:
                return 6;
        }
        return -1;
    }





    // 取得怪物胸甲
    static public Material getChestplate(String ID) {
        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫

            // 檢查是否資料符合
            ResultSet res = sta.executeQuery("SELECT (Entity_Chestplate) FROM `custom-monster_data` WHERE `ID` = '" + ID + "' LIMIT 1");
            // 跳到最後一行
            res.last();
            // 最後一行 行數 是否 > 0
            if (res.getRow() > 0) {
                // 有資料
                Material material = conversionChestplate(res.getInt("Entity_Chestplate"));

                res.close(); // 關閉查詢
                sta.close(); // 關閉連線

                return material;
            }

            res.close(); // 關閉查詢
            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    // 設定怪物胸甲
    static public void setChestplate(String ID,Material material) {
        Value.setEnable(ID,false); // 取消啟用

        int type = -1;

        if (material == null) {
            type = 0;
        } else {
            type = conversionChestplate(material);
        }

        if ( type > -1 ) {
            try {
                Connection con = MySQL.getConnection(); // 連線 MySQL
                Statement sta = con.createStatement(); // 取得控制庫

                sta.executeUpdate("UPDATE `custom-monster_data` SET `Entity_Chestplate` = '" + type + "' WHERE `ID` = '" + ID + "'");

                sta.close(); // 關閉連線

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    static public Material conversionChestplate(int e) {
        switch ( e ) {
            case 0:
                return null;
            case 1:
                return Material.LEATHER_CHESTPLATE;
            case 2:
                return Material.CHAINMAIL_CHESTPLATE;
            case 3:
                return Material.IRON_CHESTPLATE;
            case 4:
                return Material.GOLDEN_CHESTPLATE;
            case 5:
                return Material.DIAMOND_CHESTPLATE;
        }
        return null;
    }
    static public int conversionChestplate(Material e) {
        switch (e) {
            case LEATHER_CHESTPLATE:
                return 1;
            case CHAINMAIL_CHESTPLATE:
                return 2;
            case IRON_CHESTPLATE:
                return 3;
            case GOLDEN_CHESTPLATE:
                return 4;
            case DIAMOND_CHESTPLATE:
                return 5;
        }
        return -1;
    }






    // 取得怪物護腿
    static public Material getLeggings(String ID) {
        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫

            // 檢查是否資料符合
            ResultSet res = sta.executeQuery("SELECT (Entity_Leggings) FROM `custom-monster_data` WHERE `ID` = '" + ID + "' LIMIT 1");
            // 跳到最後一行
            res.last();
            // 最後一行 行數 是否 > 0
            if (res.getRow() > 0) {
                // 有資料
                Material material = conversionLeggings(res.getInt("Entity_Leggings"));

                res.close(); // 關閉查詢
                sta.close(); // 關閉連線

                return material;
            }

            res.close(); // 關閉查詢
            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    // 設定怪物護腿
    static public void setLeggings(String ID,Material material) {
        Value.setEnable(ID,false); // 取消啟用

        int type = -1;

        if (material == null) {
            type = 0;
        } else {
            type = conversionLeggings(material);
            switch (material) {
            }
        }

        if ( type > -1 ) {
            try {
                Connection con = MySQL.getConnection(); // 連線 MySQL
                Statement sta = con.createStatement(); // 取得控制庫

                sta.executeUpdate("UPDATE `custom-monster_data` SET `Entity_Leggings` = '" + type + "' WHERE `ID` = '" + ID + "'");

                sta.close(); // 關閉連線

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    static public Material conversionLeggings(int e) {
        switch ( e ) {
            case 0:
                return null;
            case 1:
                return Material.LEATHER_LEGGINGS;
            case 2:
                return Material.CHAINMAIL_LEGGINGS;
            case 3:
                return Material.IRON_LEGGINGS;
            case 4:
                return Material.GOLDEN_LEGGINGS;
            case 5:
                return Material.DIAMOND_LEGGINGS;
        }
        return null;
    }
    static public int conversionLeggings(Material e) {
        switch (e) {
            case LEATHER_LEGGINGS:
                return 1;
            case CHAINMAIL_LEGGINGS:
                return 2;
            case IRON_LEGGINGS:
                return 3;
            case GOLDEN_LEGGINGS:
                return 4;
            case DIAMOND_LEGGINGS:
                return 5;
        }
        return -1;
    }






    // 取得怪物靴子
    static public Material getBoots(String ID) {
        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫

            // 檢查是否資料符合
            ResultSet res = sta.executeQuery("SELECT (Entity_Boots) FROM `custom-monster_data` WHERE `ID` = '" + ID + "' LIMIT 1");
            // 跳到最後一行
            res.last();
            // 最後一行 行數 是否 > 0
            if (res.getRow() > 0) {
                // 有資料
                Material material = conversionBoots(res.getInt("Entity_Boots"));

                res.close(); // 關閉查詢
                sta.close(); // 關閉連線

                return material;

            }

            res.close(); // 關閉查詢
            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    // 設定怪物靴子
    static public void setBoots(String ID,Material material) {
        Value.setEnable(ID,false); // 取消啟用

        int type = -1;

        if (material == null) {
            type = 0;
        } else {
            type = conversionBoots(material);
        }

        if ( type > -1 ) {
            try {
                Connection con = MySQL.getConnection(); // 連線 MySQL
                Statement sta = con.createStatement(); // 取得控制庫

                sta.executeUpdate("UPDATE `custom-monster_data` SET `Entity_Boots` = '" + type + "' WHERE `ID` = '" + ID + "'");

                sta.close(); // 關閉連線

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    static public Material conversionBoots(int e) {
        switch ( e ) {
            case 0:
                return null;
            case 1:
                return Material.LEATHER_BOOTS;
            case 2:
                return Material.CHAINMAIL_BOOTS;
            case 3:
                return Material.IRON_BOOTS;
            case 4:
                return Material.GOLDEN_BOOTS;
            case 5:
                return Material.DIAMOND_BOOTS;
        }
        return null;
    }
    static public int conversionBoots(Material e) {
        switch (e) {
            case LEATHER_BOOTS:
                return 1;
            case CHAINMAIL_BOOTS:
                return 2;
            case IRON_BOOTS:
                return 3;
            case GOLDEN_BOOTS:
                return 4;
            case DIAMOND_BOOTS:
                return 5;
        }
        return -1;
    }



























    // 取得怪物副手物品
    static public Material getOffHand(String ID) {
        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫

            // 檢查是否資料符合
            ResultSet res = sta.executeQuery("SELECT (Entity_OffHand) FROM `custom-monster_data` WHERE `ID` = '" + ID + "' LIMIT 1");
            // 跳到最後一行
            res.last();
            // 最後一行 行數 是否 > 0
            if (res.getRow() > 0) {
                // 有資料
                Material material = conversionHandItem( res.getInt("Entity_OffHand") );

                res.close(); // 關閉查詢
                sta.close(); // 關閉連線

                return material;

            }

            res.close(); // 關閉查詢
            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    // 取得怪物主手物品
    static public Material getMainHand(String ID) {
        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫

            // 檢查是否資料符合
            ResultSet res = sta.executeQuery("SELECT (Entity_MainHand) FROM `custom-monster_data` WHERE `ID` = '" + ID + "' LIMIT 1");
            // 跳到最後一行
            res.last();
            // 最後一行 行數 是否 > 0
            if (res.getRow() > 0) {
                // 有資料
                Material material = conversionHandItem( res.getInt("Entity_MainHand") );

                res.close(); // 關閉查詢
                sta.close(); // 關閉連線

                return material;

            }

            res.close(); // 關閉查詢
            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


    // 設定怪物副手物品
    static public void setOffHand(String ID,Material material) {
        Value.setEnable(ID,false); // 取消啟用

        int type = -1;

        if (material == null) {
            type = 0;
        } else {
            type = conversionHandItem(material);
        }

        if ( type > -1 ) {
            try {
                Connection con = MySQL.getConnection(); // 連線 MySQL
                Statement sta = con.createStatement(); // 取得控制庫

                sta.executeUpdate("UPDATE `custom-monster_data` SET `Entity_OffHand` = '" + type + "' WHERE `ID` = '" + ID + "'");

                sta.close(); // 關閉連線

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    // 設定怪物主手物品
    static public void setMainHand(String ID,Material material) {
        Value.setEnable(ID,false); // 取消啟用

        int type = -1;

        if (material == null) {
            type = 0;
        } else {
            type = conversionHandItem(material);
        }

        if ( type > -1 ) {
            try {
                Connection con = MySQL.getConnection(); // 連線 MySQL
                Statement sta = con.createStatement(); // 取得控制庫

                sta.executeUpdate("UPDATE `custom-monster_data` SET `Entity_MainHand` = '" + type + "' WHERE `ID` = '" + ID + "'");

                sta.close(); // 關閉連線

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }













    static public int conversionHandItem(Material material) {
        switch ( material ) {
            case WOODEN_AXE:
                return 1;
            case WOODEN_HOE:
                return 2;
            case WOODEN_PICKAXE:
                return 3;
            case WOODEN_SHOVEL:
                return 4;
            case WOODEN_SWORD:
                return 5;

            case STONE_AXE:
                return 6;
            case STONE_HOE:
                return 7;
            case STONE_PICKAXE:
                return 8;
            case STONE_SHOVEL:
                return 9;
            case STONE_SWORD:
                return 10;

            case IRON_AXE:
                return 11;
            case IRON_HOE:
                return 12;
            case IRON_PICKAXE:
                return 13;
            case IRON_SHOVEL:
                return 14;
            case IRON_SWORD:
                return 15;

            case GOLDEN_AXE:
                return 16;
            case GOLDEN_HOE:
                return 17;
            case GOLDEN_PICKAXE:
                return 18;
            case GOLDEN_SHOVEL:
                return 19;
            case GOLDEN_SWORD:
                return 20;

            case DIAMOND_AXE:
                return 21;
            case DIAMOND_HOE:
                return 22;
            case DIAMOND_PICKAXE:
                return 23;
            case DIAMOND_SHOVEL:
                return 24;
            case DIAMOND_SWORD:
                return 25;

            case SHIELD:
                return 26;
            case TRIDENT:
                return 27;
            case TOTEM_OF_UNDYING:
                return 28;
            case BOW:
                return 29;
            case ARROW:
                return 30;
        }
        return 0;
    }
    static public Material conversionHandItem(int type) {
        switch ( type ) {
            case 0:
                return null;
            case 1:
                return Material.WOODEN_AXE;
            case 2:
                return Material.WOODEN_HOE;
            case 3:
                return Material.WOODEN_PICKAXE;
            case 4:
                return Material.WOODEN_SHOVEL;
            case 5:
                return Material.WOODEN_SWORD;

            case 6:
                return Material.STONE_AXE;
            case 7:
                return Material.STONE_HOE;
            case 8:
                return Material.STONE_PICKAXE;
            case 9:
                return Material.STONE_SHOVEL;
            case 10:
                return Material.STONE_SWORD;

            case 11:
                return Material.IRON_AXE;
            case 12:
                return Material.IRON_HOE;
            case 13:
                return Material.IRON_PICKAXE;
            case 14:
                return Material.IRON_SHOVEL;
            case 15:
                return Material.IRON_SWORD;

            case 16:
                return Material.GOLDEN_AXE;
            case 17:
                return Material.GOLDEN_HOE;
            case 18:
                return Material.GOLDEN_PICKAXE;
            case 19:
                return Material.GOLDEN_SHOVEL;
            case 20:
                return Material.GOLDEN_SWORD;

            case 21:
                return Material.DIAMOND_AXE;
            case 22:
                return Material.DIAMOND_HOE;
            case 23:
                return Material.DIAMOND_PICKAXE;
            case 24:
                return Material.DIAMOND_SHOVEL;
            case 25:
                return Material.DIAMOND_SWORD;

            case 26:
                return Material.SHIELD;
            case 27:
                return Material.TRIDENT;
            case 28:
                return Material.TOTEM_OF_UNDYING;
            case 29:
                return Material.BOW;
            case 30:
                return Material.ARROW;
        }
        return null;
    }


























    // 取得怪物名稱
    static public String getEntityName(String ID) {
        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫

            // 檢查是否資料符合
            ResultSet res = sta.executeQuery("SELECT (Entity_Name) FROM `custom-monster_data` WHERE `ID` = '" + ID + "' LIMIT 1");
            // 跳到最後一行
            res.last();
            // 最後一行 行數 是否 > 0
            if (res.getRow() > 0) {
                // 有資料
                String name = res.getString("Entity_Name");

                res.close(); // 關閉查詢
                sta.close(); // 關閉連線

                return name;

            }

            res.close(); // 關閉查詢
            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    // 設置怪物名稱
    static public void setEntityName(String ID,String name) {
        Value.setEnable(ID,false); // 取消啟用

        if ( name != null ) {
            try {
                Connection con = MySQL.getConnection(); // 連線 MySQL
                Statement sta = con.createStatement(); // 取得控制庫

                sta.executeUpdate("UPDATE `custom-monster_data` SET `Entity_Name` = '" + name + "' WHERE `ID` = '" + ID + "'");

                sta.close(); // 關閉連線

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            try {
                Connection con = MySQL.getConnection(); // 連線 MySQL
                Statement sta = con.createStatement(); // 取得控制庫

                sta.executeUpdate("UPDATE `custom-monster_data` SET `Entity_Name` = NULL WHERE `ID` = '" + ID + "'");

                sta.close(); // 關閉連線

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
















    // 取得是否使用附魔特效
    static public boolean getEnchant(String ID,String name) {
        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫

            // 檢查是否資料符合
            ResultSet res = sta.executeQuery("SELECT (Entity_Enchant_" + name + ") FROM `custom-monster_data` WHERE `ID` = '" + ID + "' LIMIT 1");
            // 跳到最後一行
            res.last();
            // 最後一行 行數 是否 > 0
            if (res.getRow() > 0) {
                // 有資料
                boolean b = res.getBoolean("Entity_Enchant_" + name);

                res.close(); // 關閉查詢
                sta.close(); // 關閉連線

                return b;

            }

            res.close(); // 關閉查詢
            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
    // 設定是否使用附魔特效
    static public void setEnchant(String ID,String name) {
        Value.setEnable(ID,false); // 取消啟用

        int type = 1;

        if (getEnchant(ID,name)) type = 0;

        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫

            sta.executeUpdate("UPDATE `custom-monster_data` SET `Entity_Enchant_" + name + "` = '" + type + "' WHERE `ID` = '" + ID + "'");

            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

















    // 取得怪物頭盔 是否使用附魔特效
    static public boolean getHelmetHasEnchant(String ID) {
        return getEnchant(ID,"Helmet");
    }
    // 設定怪物頭盔 是否使用附魔特效
    static public void setHelmetHasEnchant(String ID) {
        setEnchant(ID,"Helmet");
    }


    // 取得怪物胸甲 是否使用附魔特效
    static public boolean getChestplateHasEnchant(String ID) {
        return getEnchant(ID,"Chestplate");
    }
    // 設定怪物胸甲 是否使用附魔特效
    static public void setChestplateHasEnchant(String ID) {
        setEnchant(ID,"Chestplate");
    }


    // 取得怪物護腿 是否使用附魔特效
    static public boolean getLeggingsHasEnchant(String ID) {
        return getEnchant(ID,"Leggings");
    }
    // 設定怪物護腿 是否使用附魔特效
    static public void setLeggingsHasEnchant(String ID) {
        setEnchant(ID,"Leggings");
    }


    // 取得怪物靴子 是否使用附魔特效
    static public boolean getBootsHasEnchant(String ID) {
        return getEnchant(ID,"Boots");
    }
    // 設定怪物靴子 是否使用附魔特效
    static public void setBootsHasEnchant(String ID) {
        setEnchant(ID,"Boots");
    }























    // 取得怪物副手物品 是否使用附魔特效
    static public boolean getOffHandHasEnchant(String ID) {
        return getEnchant(ID,"OffHand");
    }
    // 取得怪物主手物品 是否使用附魔特效
    static public boolean getMainHandHasEnchant(String ID) {
        return getEnchant(ID,"MainHand");
    }


    // 設定怪物副手物品 是否使用附魔特效
    static public void setOffHandHasEnchant(String ID) {
        setEnchant(ID,"OffHand");
    }
    // 設定怪物主手物品 是否使用附魔特效
    static public void setMainHandHasEnchant(String ID) {
        setEnchant(ID,"MainHand");
    }






















/*
    // 取得怪物生命
    static public int getHP(String ID) {
        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫

            // 檢查是否資料符合
            ResultSet res = sta.executeQuery("SELECT (Health) FROM `custom-monster_data` WHERE `ID` = '" + ID + "' LIMIT 1");
            // 跳到最後一行
            res.last();
            // 最後一行 行數 是否 > 0
            if (res.getRow() > 0) {
                // 有資料
                int hp = res.getInt("Health");

                res.close(); // 關閉查詢
                sta.close(); // 關閉連線

                return hp;

            }

            res.close(); // 關閉查詢
            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return -1;
    }
    // 設定怪物生命
    static public void setHP(String ID,int hp) {
        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫

            sta.executeUpdate("UPDATE `custom-monster_data` SET `Health` = '" + hp + "' WHERE `ID` = '" + ID + "'");

            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
*/























    // 取得怪物生成X座標
    static public int getSpawnX(String ID) {
        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫

            // 檢查是否資料符合
            ResultSet res = sta.executeQuery("SELECT (Spawn_X) FROM `custom-monster_data` WHERE `ID` = '" + ID + "' AND `Spawn_X` IS NOT NULL LIMIT 1");
            // 跳到最後一行
            res.last();
            // 最後一行 行數 是否 > 0
            if (res.getRow() > 0) {
                // 有資料
                int X = res.getInt("Spawn_X");

                res.close(); // 關閉查詢
                sta.close(); // 關閉連線

                return X;

            }

            res.close(); // 關閉查詢
            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return -2147483647;
    }
    // 設定怪物生成X座標
    static public void setSpawnX(String ID,int X) {
        Value.setEnable(ID,false); // 取消啟用

        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫

            if ( X == -2147483647 ) {
                sta.executeUpdate("UPDATE `custom-monster_data` SET `Spawn_X` = NULL WHERE `ID` = '" + ID + "'");
            } else {
                sta.executeUpdate("UPDATE `custom-monster_data` SET `Spawn_X` = '" + X + "' WHERE `ID` = '" + ID + "'");
            }

            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // 取得怪物生成Y座標
    static public int getSpawnY(String ID) {
        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫

            // 檢查是否資料符合
            ResultSet res = sta.executeQuery("SELECT (Spawn_Y) FROM `custom-monster_data` WHERE `ID` = '" + ID + "' AND `Spawn_Y` IS NOT NULL LIMIT 1");
            // 跳到最後一行
            res.last();
            // 最後一行 行數 是否 > 0
            if (res.getRow() > 0) {
                // 有資料
                int Y = res.getInt("Spawn_Y");

                res.close(); // 關閉查詢
                sta.close(); // 關閉連線

                return Y;

            }

            res.close(); // 關閉查詢
            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return -2147483647;
    }
    // 設定怪物生成Y座標
    static public void setSpawnY(String ID,int Y) {
        Value.setEnable(ID,false); // 取消啟用

        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫

            if ( Y == -2147483647 ) {
                sta.executeUpdate("UPDATE `custom-monster_data` SET `Spawn_Y` = NULL WHERE `ID` = '" + ID + "'");
            } else {
                sta.executeUpdate("UPDATE `custom-monster_data` SET `Spawn_Y` = '" + Y + "' WHERE `ID` = '" + ID + "'");
            }

            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // 取得怪物生成Z座標
    static public int getSpawnZ(String ID) {
        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫

            // 檢查是否資料符合
            ResultSet res = sta.executeQuery("SELECT (Spawn_Z) FROM `custom-monster_data` WHERE `ID` = '" + ID + "' AND `Spawn_Z` IS NOT NULL LIMIT 1");
            // 跳到最後一行
            res.last();
            // 最後一行 行數 是否 > 0
            if (res.getRow() > 0) {
                // 有資料
                int Z = res.getInt("Spawn_Z");

                res.close(); // 關閉查詢
                sta.close(); // 關閉連線

                return Z;

            }

            res.close(); // 關閉查詢
            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return -2147483647;
    }
    // 設定怪物生成Z座標
    static public void setSpawnZ(String ID,int Z) {
        Value.setEnable(ID,false); // 取消啟用

        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫

            if ( Z == -2147483647 ) {
                sta.executeUpdate("UPDATE `custom-monster_data` SET `Spawn_Z` = NULL WHERE `ID` = '" + ID + "'");
            } else {
                sta.executeUpdate("UPDATE `custom-monster_data` SET `Spawn_Z` = '" + Z + "' WHERE `ID` = '" + ID + "'");
            }

            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // 取得怪物生成數量
    static public int getSpawnAmount(String ID) {
        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫

            // 檢查是否資料符合
            ResultSet res = sta.executeQuery("SELECT (Spawn_Amount) FROM `custom-monster_data` WHERE `ID` = '" + ID + "' LIMIT 1");
            // 跳到最後一行
            res.last();
            // 最後一行 行數 是否 > 0
            if (res.getRow() > 0) {
                // 有資料
                int amount = res.getInt("Spawn_Amount");

                res.close(); // 關閉查詢
                sta.close(); // 關閉連線

                return amount;

            }

            res.close(); // 關閉查詢
            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return -1;
    }
    // 設定怪物生成數量
    static public void setSpawnAmount(String ID,int amount) {
        Value.setEnable(ID,false); // 取消啟用

        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫

            sta.executeUpdate("UPDATE `custom-monster_data` SET `Spawn_Amount` = '" + amount + "' WHERE `ID` = '" + ID + "'");

            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    // 取得怪物生成玩家距離多少內觸發
    static public int getSpawnPlayerDistance(String ID) {
        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫

            // 檢查是否資料符合
            ResultSet res = sta.executeQuery("SELECT (Spawn_Player_Distance) FROM `custom-monster_data` WHERE `ID` = '" + ID + "' LIMIT 1");
            // 跳到最後一行
            res.last();
            // 最後一行 行數 是否 > 0
            if (res.getRow() > 0) {
                // 有資料
                int amount = res.getInt("Spawn_Player_Distance");

                res.close(); // 關閉查詢
                sta.close(); // 關閉連線

                return amount;

            }

            res.close(); // 關閉查詢
            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }
    // 設定怪物生成玩家距離多少內觸發
    static public void setSpawnPlayerDistance(String ID,int amount) {
        Value.setEnable(ID,false); // 取消啟用

        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫

            sta.executeUpdate("UPDATE `custom-monster_data` SET `Spawn_Player_Distance` = '" + amount + "' WHERE `ID` = '" + ID + "'");

            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    // 取得怪物生成的座標並隨機的範圍大小
    static public int getSpawnRange(String ID) {
        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫

            // 檢查是否資料符合
            ResultSet res = sta.executeQuery("SELECT (Spawn_Range) FROM `custom-monster_data` WHERE `ID` = '" + ID + "' LIMIT 1");
            // 跳到最後一行
            res.last();
            // 最後一行 行數 是否 > 0
            if (res.getRow() > 0) {
                // 有資料
                int amount = res.getInt("Spawn_Range");

                res.close(); // 關閉查詢
                sta.close(); // 關閉連線

                return amount;

            }

            res.close(); // 關閉查詢
            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }
    // 設定怪物生成的座標並隨機的範圍大小
    static public void setSpawnRange(String ID,int amount) {
        Value.setEnable(ID,false); // 取消啟用

        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫

            sta.executeUpdate("UPDATE `custom-monster_data` SET `Spawn_Range` = '" + amount + "' WHERE `ID` = '" + ID + "'");

            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // 取得怪物生成延遲(秒)
    static public int getSpawnDelay(String ID) {
        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫

            // 檢查是否資料符合
            ResultSet res = sta.executeQuery("SELECT (Spawn_Delay) FROM `custom-monster_data` WHERE `ID` = '" + ID + "' LIMIT 1");
            // 跳到最後一行
            res.last();
            // 最後一行 行數 是否 > 0
            if (res.getRow() > 0) {
                // 有資料
                int amount = res.getInt("Spawn_Delay");

                res.close(); // 關閉查詢
                sta.close(); // 關閉連線

                return amount;

            }

            res.close(); // 關閉查詢
            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }
    // 設定怪物生成延遲(秒)
    static public void setSpawnDelay(String ID,int v) {
        Value.setEnable(ID,false); // 取消啟用

        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫

            sta.executeUpdate("UPDATE `custom-monster_data` SET `Spawn_Delay` = '" + v + "' WHERE `ID` = '" + ID + "'");

            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }




























    // 取得位置
    static public String getWorld(String ID) {
        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫

            // 檢查是否資料符合
            ResultSet res = sta.executeQuery("SELECT (World) FROM `custom-monster_data` WHERE `ID` = '" + ID + "' LIMIT 1");
            // 跳到最後一行
            res.last();
            // 最後一行 行數 是否 > 0
            if (res.getRow() > 0) {
                // 有資料
                String world = res.getString("World");

                res.close(); // 關閉查詢
                sta.close(); // 關閉連線

                return world;

            }

            res.close(); // 關閉查詢
            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    static public int getX(String ID) {
        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫

            // 檢查是否資料符合
            ResultSet res = sta.executeQuery("SELECT (X) FROM `custom-monster_data` WHERE `ID` = '" + ID + "' LIMIT 1");
            // 跳到最後一行
            res.last();
            // 最後一行 行數 是否 > 0
            if (res.getRow() > 0) {
                // 有資料
                int X = res.getInt("X");

                res.close(); // 關閉查詢
                sta.close(); // 關閉連線

                return X;

            }

            res.close(); // 關閉查詢
            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }
    static public int getY(String ID) {
        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫

            // 檢查是否資料符合
            ResultSet res = sta.executeQuery("SELECT (Y) FROM `custom-monster_data` WHERE `ID` = '" + ID + "' LIMIT 1");
            // 跳到最後一行
            res.last();
            // 最後一行 行數 是否 > 0
            if (res.getRow() > 0) {
                // 有資料
                int Y = res.getInt("Y");

                res.close(); // 關閉查詢
                sta.close(); // 關閉連線

                return Y;

            }

            res.close(); // 關閉查詢
            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }
    static public int getZ(String ID) {
        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫

            // 檢查是否資料符合
            ResultSet res = sta.executeQuery("SELECT (Z) FROM `custom-monster_data` WHERE `ID` = '" + ID + "' LIMIT 1");
            // 跳到最後一行
            res.last();
            // 最後一行 行數 是否 > 0
            if (res.getRow() > 0) {
                // 有資料
                int Z = res.getInt("Z");

                res.close(); // 關閉查詢
                sta.close(); // 關閉連線

                return Z;

            }

            res.close(); // 關閉查詢
            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }



















    // 取得是否顯示BOSS血條
    static public boolean getBossBar(String ID) {
        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫

            // 檢查是否資料符合
            ResultSet res = sta.executeQuery("SELECT (Entity_BossBar) FROM `custom-monster_data` WHERE `ID` = '" + ID + "' LIMIT 1");
            // 跳到最後一行
            res.last();
            // 最後一行 行數 是否 > 0
            if (res.getRow() > 0) {
                // 有資料
                boolean b = res.getBoolean("Entity_BossBar");

                res.close(); // 關閉查詢
                sta.close(); // 關閉連線

                return b;

            }

            res.close(); // 關閉查詢
            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
    // 設置是否顯示BOSS血條
    static public void setBossBar(String ID) {
        Value.setEnable(ID,false); // 取消啟用

        int type = 1;

        if (getBossBar((ID))) type = 0;

        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫

            sta.executeUpdate("UPDATE `custom-monster_data` SET `Entity_BossBar` = '" + type + "' WHERE `ID` = '" + ID + "'");

            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }




























    // 取得屬性值
    static public int getAttribute(String ID,String attribute) {
        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫

            // 檢查是否資料符合
            ResultSet res = sta.executeQuery("SELECT (Attribute_" + attribute + ") FROM `custom-monster_data` WHERE `ID` = '" + ID + "' AND `Attribute_" + attribute + "` IS NOT NULL LIMIT 1");
            // 跳到最後一行
            res.last();
            // 最後一行 行數 是否 > 0
            if (res.getRow() > 0) {
                // 有資料
                int v = res.getInt("Attribute_" + attribute);

                res.close(); // 關閉查詢
                sta.close(); // 關閉連線

                return v;

            }

            res.close(); // 關閉查詢
            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return -1;
    }
    // 設定屬性值
    static public void setAttribute(String ID,String attribute,int v) {
        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫

            if ( v < 0 ) {
                sta.executeUpdate("UPDATE `custom-monster_data` SET `Attribute_" + attribute + "` = NULL WHERE `ID` = '" + ID + "'");
            } else {
                sta.executeUpdate("UPDATE `custom-monster_data` SET `Attribute_" + attribute + "` = '" + v + "' WHERE `ID` = '" + ID + "'");
            }

            sta.close(); // 關閉連線

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}