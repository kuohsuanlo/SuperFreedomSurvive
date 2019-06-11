package SuperFreedomSurvive.Monster;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.tags.ItemTagType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Menu implements Listener {
    // 怪物 編輯介面


    static final public org.bukkit.NamespacedKey id_key = SuperFreedomSurvive.SYSTEM.NamespacedKey.getKey("ID");


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    static public void open(Player player, Location location) {
        String ID = Data.GetID(location);
        open(player,ID);
    }
    static public void open(Player player, String ID) {

        Location location = Data.GetLocation(ID);
        if ( location == null ) { return; }
        // 檢查是否有權限
        if (SuperFreedomSurvive.Land.Permissions.Inspection(player, location.getWorld().getName() , location.getBlockX() , location.getBlockY() , location.getBlockZ() , "Permissions_Interact_Spawner")) {
            // 有

            // 開啟編輯介面
            Inventory inv = Bukkit.createInventory(null, 54, "§z編輯怪物磚選單");

            ItemStack item;
            ItemMeta meta;


            // --------------------------------------     0     --------------------------------------
            // 怪物類型
            EntityType entity_type = Value.getEntityType(ID);
            if (entity_type == null) {
                item = new ItemStack(Material.EGG);
                meta = item.getItemMeta();
                meta.setDisplayName("§b怪物類型");
                meta.setLore(Arrays.asList(
                        ("§r§e   [未定義]"),
                        ("§r§f (點擊) 更改怪物類型")
                ));
                // 寫入資料
                item.setItemMeta(meta);
                // 設置完成
                inv.setItem(0, item);

            } else {

                List<String> list = new ArrayList<>();
                item = new ItemStack(Material.EGG);
                meta = item.getItemMeta();
                meta.setDisplayName("§b怪物類型");

                switch (entity_type) {
                    case ZOMBIE:
                        item = new ItemStack(Material.ZOMBIE_SPAWN_EGG);
                        list.add("§r§e   [殭屍]");
                        break;

                    case PIG_ZOMBIE:
                        item = new ItemStack(Material.ZOMBIE_PIGMAN_SPAWN_EGG);
                        list.add("§r§e   [殭屍豬人]");
                        break;

                    case CREEPER:
                        item = new ItemStack(Material.CREEPER_SPAWN_EGG);
                        list.add("§r§e   [苦力怕]");
                        break;

                    case ELDER_GUARDIAN:
                        item = new ItemStack(Material.ELDER_GUARDIAN_SPAWN_EGG);
                        list.add("§r§e   [遠古深海守衛]");
                        break;

                    case WITHER_SKELETON:
                        item = new ItemStack(Material.WITHER_SKELETON_SPAWN_EGG);
                        list.add("§r§e   [凋零骷髏]");
                        break;

                    case STRAY:
                        item = new ItemStack(Material.STRAY_SPAWN_EGG);
                        list.add("§r§e   [流髑]");
                        break;

                    case HUSK:
                        item = new ItemStack(Material.HUSK_SPAWN_EGG);
                        list.add("§r§e   [屍殼]");
                        break;

                    case EVOKER:
                        item = new ItemStack(Material.EVOKER_SPAWN_EGG);
                        list.add("§r§e   [喚魔者]");
                        break;

                    case VEX:
                        item = new ItemStack(Material.VEX_SPAWN_EGG);
                        list.add("§r§e   [惱鬼]");
                        break;

                    case VINDICATOR:
                        item = new ItemStack(Material.VINDICATOR_SPAWN_EGG);
                        list.add("§r§e   [衛道士]");
                        break;

                    case ILLUSIONER:
                        item = new ItemStack(Material.EVOKER_SPAWN_EGG);
                        list.add("§r§e   [換魔者]");
                        break;

                    case SKELETON:
                        item = new ItemStack(Material.SKELETON_SPAWN_EGG);
                        list.add("§r§e   [骷髏]");
                        break;

                    case SPIDER:
                        item = new ItemStack(Material.SPIDER_SPAWN_EGG);
                        list.add("§r§e   [蜘蛛]");
                        break;

                    case GIANT:
                        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                        meta.addEnchant(Enchantment.BINDING_CURSE, 1, false);
                        item = new ItemStack(Material.ZOMBIE_SPAWN_EGG);
                        list.add("§r§e   [巨人]");
                        break;

                    case SLIME:
                        item = new ItemStack(Material.SLIME_SPAWN_EGG);
                        list.add("§r§e   [史萊姆]");
                        break;

                    case GHAST:
                        item = new ItemStack(Material.GHAST_SPAWN_EGG);
                        list.add("§r§e   [地獄幽靈]");
                        break;

                    case ENDERMAN:
                        item = new ItemStack(Material.ENDERMAN_SPAWN_EGG);
                        list.add("§r§e   [終界使者]");
                        break;

                    case CAVE_SPIDER:
                        item = new ItemStack(Material.CAVE_SPIDER_SPAWN_EGG);
                        list.add("§r§e   [洞穴蜘蛛]");
                        break;

                    case SILVERFISH:
                        item = new ItemStack(Material.SILVERFISH_SPAWN_EGG);
                        list.add("§r§e   [蠹魚]");
                        break;

                    case BLAZE:
                        item = new ItemStack(Material.BLAZE_SPAWN_EGG);
                        list.add("§r§e   [烈焰使者]");
                        break;

                    case MAGMA_CUBE:
                        item = new ItemStack(Material.MAGMA_CUBE_SPAWN_EGG);
                        list.add("§r§e   [熔岩史萊姆]");
                        break;

                    case WITHER:
                        item = new ItemStack(Material.WITHER_SKELETON_SPAWN_EGG);
                        list.add("§r§e   [凋零怪]");
                        break;

                    case WITCH:
                        item = new ItemStack(Material.WITCH_SPAWN_EGG);
                        list.add("§r§e   [女巫]");
                        break;

                    case ENDERMITE:
                        item = new ItemStack(Material.ENDERMITE_SPAWN_EGG);
                        list.add("§r§e   [終界蟎]");
                        break;

                    case GUARDIAN:
                        item = new ItemStack(Material.GUARDIAN_SPAWN_EGG);
                        list.add("§r§e   [深海守衛]");
                        break;

                    case SHULKER:
                        item = new ItemStack(Material.SHULKER_SPAWN_EGG);
                        list.add("§r§e   [界伏蚌]");
                        break;

                    case LIGHTNING:
                        item = new ItemStack(Material.GOLD_NUGGET);
                        list.add("§r§e   [閃電]");
                        break;

                    case IRON_GOLEM:
                        item = new ItemStack(Material.IRON_BLOCK);
                        list.add("§r§e   [鐵巨人]");
                        break;

                    case SNOWMAN:
                        item = new ItemStack(Material.SNOW_BLOCK);
                        list.add("§r§e   [雪人]");
                        break;

                    default:
                        break;
                }
                list.add("§r§f (點擊) 更改怪物類型");

                meta.setLore(list);
                // 寫入資料
                item.setItemMeta(meta);
                // 設置完成
                inv.setItem(0, item);

            }





            // --------------------------------------     2     --------------------------------------
            {
                item = new ItemStack(Material.GHAST_TEAR);
                meta = item.getItemMeta();
                meta.setDisplayName("§e設置顯示頭盔");
                List<String> list = new ArrayList<>();
                Material helmet = Value.getHelmet(ID);

                if (helmet == null) {
                    item = new ItemStack(Material.GHAST_TEAR);
                    list.add("§r§e   無設定");

                } else {
                    item = new ItemStack(helmet);
                }

                list.add("§r§f (點擊) 更改怪物頭盔");

                if ( Value.getHelmetHasEnchant(ID) ) {

                    meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    meta.addEnchant(Enchantment.BINDING_CURSE, 1, false);

                    list.add("§r§f (Shift+點擊) 關閉附魔特效");
                } else {
                    list.add("§r§f (Shift+點擊) 啟用附魔特效");
                }

                meta.setLore(list);
                // 寫入資料
                item.setItemMeta(meta);
                // 設置完成
                inv.setItem(2, item);
            }

            // --------------------------------------     3     --------------------------------------
            {
                item = new ItemStack(Material.GHAST_TEAR);
                meta = item.getItemMeta();
                meta.setDisplayName("§e設置顯示胸甲");
                List<String> list = new ArrayList<>();
                Material chestplate = Value.getChestplate(ID);

                if (chestplate == null) {
                    item = new ItemStack(Material.GHAST_TEAR);
                    list.add("§r§e   無設定");

                } else {
                    item = new ItemStack(chestplate);
                }

                list.add("§r§f (點擊) 更改怪物胸甲");

                if ( Value.getChestplateHasEnchant(ID) ) {

                    meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    meta.addEnchant(Enchantment.BINDING_CURSE, 1, false);

                    list.add("§r§f (Shift+點擊) 關閉附魔特效");
                } else {
                    list.add("§r§f (Shift+點擊) 啟用附魔特效");
                }

                meta.setLore(list);
                // 寫入資料
                item.setItemMeta(meta);
                // 設置完成
                inv.setItem(3, item);
            }

            // --------------------------------------     4     --------------------------------------
            {
                item = new ItemStack(Material.GHAST_TEAR);
                meta = item.getItemMeta();
                meta.setDisplayName("§e設置顯示護腿");
                List<String> list = new ArrayList<>();
                Material leggings = Value.getLeggings(ID);

                if (leggings == null) {
                    item = new ItemStack(Material.GHAST_TEAR);
                    list.add("§r§e   無設定");

                } else {
                    item = new ItemStack(leggings);
                }

                list.add("§r§f (點擊) 更改怪物護腿");

                if ( Value.getLeggingsHasEnchant(ID) ) {

                    meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    meta.addEnchant(Enchantment.BINDING_CURSE, 1, false);

                    list.add("§r§f (Shift+點擊) 關閉附魔特效");
                } else {
                    list.add("§r§f (Shift+點擊) 啟用附魔特效");
                }

                meta.setLore(list);
                // 寫入資料
                item.setItemMeta(meta);
                // 設置完成
                inv.setItem(4, item);
            }

            // --------------------------------------     5     --------------------------------------
            {
                item = new ItemStack(Material.GHAST_TEAR);
                meta = item.getItemMeta();
                meta.setDisplayName("§e設置顯示靴子");
                List<String> list = new ArrayList<>();
                Material boots = Value.getBoots(ID);

                if (boots == null) {
                    item = new ItemStack(Material.GHAST_TEAR);
                    list.add("§r§e   無設定");

                } else {
                    item = new ItemStack(boots);
                }

                list.add("§r§f (點擊) 更改怪物靴子");

                if ( Value.getBootsHasEnchant(ID) ) {

                    meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    meta.addEnchant(Enchantment.BINDING_CURSE, 1, false);

                    list.add("§r§f (Shift+點擊) 關閉附魔特效");
                } else {
                    list.add("§r§f (Shift+點擊) 啟用附魔特效");
                }

                meta.setLore(list);
                // 寫入資料
                item.setItemMeta(meta);
                // 設置完成
                inv.setItem(5, item);
            }




            // --------------------------------------     7     --------------------------------------
            {
                item = new ItemStack(Material.GHAST_TEAR);
                meta = item.getItemMeta();
                meta.setDisplayName("§e設置副手物品");
                List<String> list = new ArrayList<>();
                Material off_hand = Value.getOffHand(ID);

                if (off_hand == null) {
                    item = new ItemStack(Material.GHAST_TEAR);
                    list.add("§r§e   無設定");

                } else {
                    item = new ItemStack(off_hand);
                }

                list.add("§r§f (點擊) 更改副手物品");

                if ( Value.getOffHandHasEnchant(ID) ) {

                    meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    meta.addEnchant(Enchantment.BINDING_CURSE, 1, false);

                    list.add("§r§f (Shift+點擊) 關閉附魔特效");
                } else {
                    list.add("§r§f (Shift+點擊) 啟用附魔特效");
                }

                meta.setLore(list);
                // 寫入資料
                item.setItemMeta(meta);
                // 設置完成
                inv.setItem(7, item);
            }

            // --------------------------------------     8     --------------------------------------
            {
                item = new ItemStack(Material.GHAST_TEAR);
                meta = item.getItemMeta();
                meta.setDisplayName("§e設置主手物品");
                List<String> list = new ArrayList<>();
                Material main_hand = Value.getMainHand(ID);

                if (main_hand == null) {
                    item = new ItemStack(Material.GHAST_TEAR);
                    list.add("§r§e   無設定");

                } else {
                    item = new ItemStack(main_hand);
                }

                list.add("§r§f (點擊) 更改主手物品");

                if ( Value.getMainHandHasEnchant(ID) ) {

                    meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    meta.addEnchant(Enchantment.BINDING_CURSE, 1, false);

                    list.add("§r§f (Shift+點擊) 關閉附魔特效");
                } else {
                    list.add("§r§f (Shift+點擊) 啟用附魔特效");
                }

                meta.setLore(list);
                // 寫入資料
                item.setItemMeta(meta);
                // 設置完成
                inv.setItem(8, item);
            }






            // --------------------------------------     9     --------------------------------------
            {
                item = new ItemStack(Material.NAME_TAG);
                meta = item.getItemMeta();
                meta.setDisplayName("§e設置怪物名稱");
                List<String> list = new ArrayList<>();
                String name = Value.getEntityName(ID);

                if (name == null) {
                    list.add("§r§e   無設定");

                } else {
                    list.add("§r§e   [" + name + "§e]");

                }
                list.add("§r§f (點擊) 更改名稱");
                list.add("§r§f (Shift+右鍵) 重設");

                meta.setLore(list);
                // 寫入資料
                item.setItemMeta(meta);
                // 設置完成
                inv.setItem(9, item);
            }


            // --------------------------------------     10     --------------------------------------
            {
                item = new ItemStack(Material.DISPENSER);
                meta = item.getItemMeta();
                meta.setDisplayName("§b最大生成數量");
                List<String> list = new ArrayList<>();
                int amount = Value.getSpawnAmount(ID);

                if (amount < 1) {
                    list.add("§r§e   無設定");

                } else {
                    list.add("§r§e   [" + amount + "§e]");

                }
                list.add("§r§f (點擊) 更改數值");
                list.add("§r§f (Shift+右鍵) 重設");

                meta.setLore(list);
                // 寫入資料
                item.setItemMeta(meta);
                // 設置完成
                inv.setItem(10, item);
            }


            // --------------------------------------     11     --------------------------------------
            {
                item = new ItemStack(Material.COMPARATOR);
                meta = item.getItemMeta();
                meta.setDisplayName("§b玩家離生成位置距離多少內觸發生成");
                List<String> list = new ArrayList<>();
                int amount = Value.getSpawnPlayerDistance(ID);

                if (amount < 1) {
                    list.add("§r§e   無設定");

                } else {
                    list.add("§r§e   [" + amount + "§e]");

                }
                list.add("§r§f (點擊) 更改數值");
                list.add("§r§f (Shift+右鍵) 重設");

                meta.setLore(list);
                // 寫入資料
                item.setItemMeta(meta);
                // 設置完成
                inv.setItem(11, item);
            }


            // --------------------------------------     12     --------------------------------------
            {
                item = new ItemStack(Material.REPEATER);
                meta = item.getItemMeta();
                meta.setDisplayName("§b生成延遲 (秒)");
                List<String> list = new ArrayList<>();
                int delay = Value.getSpawnDelay(ID);

                if (delay < 1) {
                    list.add("§r§e   無設定");

                } else {
                    list.add("§r§e   [" + delay + "§e] 秒");

                }
                list.add("§r§f (點擊) 更改數值");
                list.add("§r§f (Shift+右鍵) 重設");

                meta.setLore(list);
                // 寫入資料
                item.setItemMeta(meta);
                // 設置完成
                inv.setItem(12, item);
            }


            // --------------------------------------     13     --------------------------------------
            {
                item = new ItemStack(Material.ENDER_PEARL);
                meta = item.getItemMeta();
                meta.setDisplayName("§e是否顯示BOSS血條");
                List<String> list = new ArrayList<>();
                boolean boss_bar = Value.getBossBar(ID);

                if (boss_bar) {
                    item = new ItemStack(Material.ENDER_EYE);
                    list.add("§r§a   是");

                } else {
                    item = new ItemStack(Material.ENDER_PEARL);
                    list.add("§r§c   否");

                }
                list.add("§r§f (點擊) 更改數值");

                meta.setLore(list);
                // 寫入資料
                item.setItemMeta(meta);
                // 設置完成
                inv.setItem(13, item);
            }





















            // --------------------------------------     14     --------------------------------------
            {
                item = new ItemStack(Material.SPAWNER);
                meta = item.getItemMeta();
                meta.setDisplayName("§b設置生成 X座標");
                List<String> list = new ArrayList<>();
                int X = Value.getSpawnX(ID);

                if (X <= -2147483647) {
                    list.add("§r§e   無設定");

                } else {
                    list.add("§r§e   [" + X + "§e]");

                }
                list.add("§r§f (點擊) 更改數值");
                list.add("§r§f (Shift+右鍵) 重設");

                meta.setLore(list);
                // 寫入資料
                item.setItemMeta(meta);
                // 設置完成
                inv.setItem(14, item);
            }


            // --------------------------------------     15     --------------------------------------
            {
                item = new ItemStack(Material.SPAWNER);
                meta = item.getItemMeta();
                meta.setDisplayName("§b設置生成 Y座標");
                List<String> list = new ArrayList<>();
                int Y = Value.getSpawnY(ID);

                if (Y <= -2147483647) {
                    list.add("§r§e   無設定");

                } else {
                    list.add("§r§e   [" + Y + "§e]");

                }
                list.add("§r§f (點擊) 更改數值");
                list.add("§r§f (Shift+右鍵) 重設");

                meta.setLore(list);
                // 寫入資料
                item.setItemMeta(meta);
                // 設置完成
                inv.setItem(15, item);
            }


            // --------------------------------------     16     --------------------------------------
            {
                item = new ItemStack(Material.SPAWNER);
                meta = item.getItemMeta();
                meta.setDisplayName("§b設置生成 Z座標");
                List<String> list = new ArrayList<>();
                int Z = Value.getSpawnZ(ID);

                if (Z <= -2147483647) {
                    list.add("§r§e   無設定");

                } else {
                    list.add("§r§e   [" + Z + "§e]");

                }
                list.add("§r§f (點擊) 更改數值");
                list.add("§r§f (Shift+右鍵) 重設");

                meta.setLore(list);
                // 寫入資料
                item.setItemMeta(meta);
                // 設置完成
                inv.setItem(16, item);
            }


            // --------------------------------------     17     --------------------------------------
            {
                item = new ItemStack(Material.SPAWNER);
                meta = item.getItemMeta();
                meta.setDisplayName("§b以座標為中心點 X/Z半徑內隨機生成範圍");
                List<String> list = new ArrayList<>();
                int range = Value.getSpawnRange(ID);

                if (range < 1) {
                    list.add("§r§e   無設定");

                } else {
                    list.add("§r§e   [" + range + "§e]");

                }
                list.add("§r§f (點擊) 更改數值");
                list.add("§r§f (Shift+右鍵) 重設");

                meta.setLore(list);
                // 寫入資料
                item.setItemMeta(meta);
                // 設置完成
                inv.setItem(17, item);
            }






















            // --------------------------------------     18     --------------------------------------
            {
                item = new ItemStack(Material.DIAMOND_CHESTPLATE);
                meta = item.getItemMeta();
                meta.setDisplayName("§b設置護甲值");
                List<String> list = new ArrayList<>();
                int hp = Value.getAttribute(ID,"GENERIC_ARMOR");

                if (hp < 0) {
                    list.add("§r§e   無設定");

                } else {
                    list.add("§r§e   [" + hp + "§e]");

                }
                list.add("§r§f (點擊) 更改數值");
                list.add("§r§f (Shift+右鍵) 重設");

                meta.setLore(list);
                // 寫入資料
                item.setItemMeta(meta);
                // 設置完成
                inv.setItem(18, item);
            }


            // --------------------------------------     19     --------------------------------------
            {
                item = new ItemStack(Material.GOLDEN_CHESTPLATE);
                meta = item.getItemMeta();
                meta.setDisplayName("§b設置護甲耐久性值");
                List<String> list = new ArrayList<>();
                int hp = Value.getAttribute(ID,"GENERIC_ARMOR_TOUGHNESS");

                if (hp < 0) {
                    list.add("§r§e   無設定");

                } else {
                    list.add("§r§e   [" + hp + "§e]");

                }
                list.add("§r§f (點擊) 更改數值");
                list.add("§r§f (Shift+右鍵) 重設");

                meta.setLore(list);
                // 寫入資料
                item.setItemMeta(meta);
                // 設置完成
                inv.setItem(19, item);
            }


            // --------------------------------------     20     --------------------------------------
            {
                item = new ItemStack(Material.DIAMOND_SWORD);
                meta = item.getItemMeta();
                meta.setDisplayName("§b設置攻擊傷害");
                List<String> list = new ArrayList<>();
                int hp = Value.getAttribute(ID,"GENERIC_ATTACK_DAMAGE");

                if (hp < 0) {
                    list.add("§r§e   無設定");

                } else {
                    list.add("§r§e   [" + hp + "§e]");

                }
                list.add("§r§f (點擊) 更改數值");
                list.add("§r§f (Shift+右鍵) 重設");

                meta.setLore(list);
                // 寫入資料
                item.setItemMeta(meta);
                // 設置完成
                inv.setItem(20, item);
            }


            // --------------------------------------     21     --------------------------------------
            {
                item = new ItemStack(Material.PLAYER_HEAD);
                meta = item.getItemMeta();
                meta.setDisplayName("§b設置跟隨玩家的範圍");
                List<String> list = new ArrayList<>();
                int hp = Value.getAttribute(ID,"GENERIC_FOLLOW_RANGE");

                if (hp < 0) {
                    list.add("§r§e   無設定");

                } else {
                    list.add("§r§e   [" + hp + "§e]");

                }
                list.add("§r§f (點擊) 更改數值");
                list.add("§r§f (Shift+右鍵) 重設");

                meta.setLore(list);
                // 寫入資料
                item.setItemMeta(meta);
                // 設置完成
                inv.setItem(21, item);
            }


            // --------------------------------------     22     --------------------------------------
            {
                item = new ItemStack(Material.SHIELD);
                meta = item.getItemMeta();
                meta.setDisplayName("§b設置擊退抵抗力");
                List<String> list = new ArrayList<>();
                int hp = Value.getAttribute(ID,"GENERIC_KNOCKBACK_RESISTANCE");

                if (hp < 0) {
                    list.add("§r§e   無設定");

                } else {
                    list.add("§r§e   [" + hp + "§e]");

                }
                list.add("§r§f (點擊) 更改數值");
                list.add("§r§f (Shift+右鍵) 重設");

                meta.setLore(list);
                // 寫入資料
                item.setItemMeta(meta);
                // 設置完成
                inv.setItem(22, item);
            }


            // --------------------------------------     23     --------------------------------------
            {
                item = new ItemStack(Material.REDSTONE);
                meta = item.getItemMeta();
                meta.setDisplayName("§b設置最大生命值");
                List<String> list = new ArrayList<>();
                int hp = Value.getAttribute(ID,"GENERIC_MAX_HEALTH");

                if (hp < 0) {
                    list.add("§r§e   無設定");

                } else {
                    list.add("§r§e   [" + hp + "§e]");

                }
                list.add("§r§f (點擊) 更改數值");
                list.add("§r§f (Shift+右鍵) 重設");

                meta.setLore(list);
                // 寫入資料
                item.setItemMeta(meta);
                // 設置完成
                inv.setItem(23, item);
            }


            // --------------------------------------     24     --------------------------------------
            {
                item = new ItemStack(Material.DIAMOND_HORSE_ARMOR);
                meta = item.getItemMeta();
                meta.setDisplayName("§b設置移動速度");
                List<String> list = new ArrayList<>();
                int hp = Value.getAttribute(ID,"GENERIC_MOVEMENT_SPEED");

                if (hp < 0) {
                    list.add("§r§e   無設定");

                } else {
                    list.add("§r§e   [" + hp + "§e]");

                }
                list.add("§r§f (點擊) 更改數值");
                list.add("§r§f (Shift+右鍵) 重設");

                meta.setLore(list);
                // 寫入資料
                item.setItemMeta(meta);
                // 設置完成
                inv.setItem(24, item);
            }























            // --------------------------------------     45     --------------------------------------
            inv.setItem(45, getIDItemStack(ID));


            // --------------------------------------     46     --------------------------------------
            {
                item = new ItemStack(Material.TORCH);
                meta = item.getItemMeta();
                meta.setDisplayName("§e是否啟用");
                List<String> list = new ArrayList<>();
                boolean range = Value.getEnable(ID);

                if (range) {
                    item = new ItemStack(Material.REDSTONE_TORCH);
                    list.add("§r§a   是");

                } else {
                    item = new ItemStack(Material.TORCH);
                    list.add("§r§c   否");

                }
                list.add("§r§f (點擊) 更改數值");

                meta.setLore(list);
                // 寫入資料
                item.setItemMeta(meta);
                // 設置完成
                inv.setItem(46, item);
            }




            // --------------------------------------     53     --------------------------------------
            inv.setItem(53, SuperFreedomSurvive.Menu.Open.TurnOff());


            player.openInventory(inv);
        }
    }
    // 阻止玩家拿走物品
    @EventHandler
    final public void onInventoryClickEvent(InventoryClickEvent event) {
        // 判斷是否為玩家

        if (event.getWhoClicked() instanceof Player) {
            // 是玩家
            Player player = (Player) event.getWhoClicked();

            // 檢查容器名稱
            if (event.getView().getTitle().equalsIgnoreCase("§z編輯怪物磚選單")) {
                // 是沒錯

                event.setCancelled(true);

                if (event.getClickedInventory() == null) { return; }
                if (event.getClickedInventory().getItem(45) == null) { return; }
                if (event.getClickedInventory().getItem(45).getItemMeta() == null) { return; }
                if (event.getClickedInventory().getItem(45).getItemMeta().getCustomTagContainer().getCustomTag(id_key, ItemTagType.STRING) == null) { return; }
                String ID = event.getClickedInventory().getItem(45).getItemMeta().getCustomTagContainer().getCustomTag(id_key, ItemTagType.STRING);

                Location location = Data.GetLocation(ID);
                if (location == null) {
                    return;
                }
                // 檢查是否有權限
                if (SuperFreedomSurvive.Land.Permissions.Inspection(player, location.getWorld().getName(), location.getBlockX(), location.getBlockY(), location.getBlockZ(), "Permissions_Interact_Spawner")) {
                    // 有

                    switch (event.getRawSlot()) {
                        case 0:
                            Menu_EntityType(player, ID);
                            break;

                        case 2:
                            if ( event.isShiftClick() ) {
                                Value.setHelmetHasEnchant(ID);
                                open(player,ID);
                            } else {
                                Menu_Helmet(player, ID);
                            }
                            break;
                        case 3:
                            if ( event.isShiftClick() ) {
                                Value.setChestplateHasEnchant(ID);
                                open(player,ID);
                            } else {
                                Menu_Chestplate(player, ID);
                            }
                            break;
                        case 4:
                            if ( event.isShiftClick() ) {
                                Value.setLeggingsHasEnchant(ID);
                                open(player,ID);
                            } else {
                                Menu_Leggings(player, ID);
                            }
                            break;
                        case 5:
                            if ( event.isShiftClick() ) {
                                Value.setBootsHasEnchant(ID);
                                open(player,ID);
                            } else {
                                Menu_Boots(player, ID);
                            }
                            break;



                        case 7:
                            if ( event.isShiftClick() ) {
                                Value.setOffHandHasEnchant(ID);
                                open(player,ID);
                            } else {
                                Menu_OffHand(player, ID);
                            }
                            break;
                        case 8:
                            if ( event.isShiftClick() ) {
                                Value.setMainHandHasEnchant(ID);
                                open(player,ID);
                            } else {
                                Menu_MainHand(player, ID);
                            }
                            break;


                        case 9:
                            // 改名
                            if (event.isShiftClick() && event.isRightClick()) {
                                Value.setEntityName(ID , null); // 重製
                                open(player,ID);

                            } else {
                                player.closeInventory();
                                player.sendMessage("[自訂怪物磚]  §a請輸入要更改的名稱  支持使用&顏色代碼 最多40字");

                                SuperFreedomSurvive.SYSTEM.Input.setRegistry(player,
                                        new BukkitRunnable() {
                                            int time = 1200;
                                            String message = null;

                                            @Override
                                            final public void run() {
                                                if (time < 0) {
                                                    player.sendMessage("[自訂怪物磚]  §c等待輸入太久了 自動取消");
                                                    cancel();
                                                    return;
                                                }
                                                message = SuperFreedomSurvive.SYSTEM.Input.getMessage(player);

                                                if (message != null) {
                                                    // 有資料

                                                    message = SuperFreedomSurvive.Function.ColorCodeTransform.Conversion(message);

                                                    if (message.length() > 40) {
                                                        message = message.substring(0, 40);
                                                    }

                                                    Location location = Data.GetLocation(ID);
                                                    if (location == null) {
                                                        return;
                                                    }
                                                    // 檢查是否有權限
                                                    if (SuperFreedomSurvive.Land.Permissions.Inspection(player, location.getWorld().getName(), location.getBlockX(), location.getBlockY(), location.getBlockZ(), "Permissions_Interact_Spawner")) {
                                                        // 有

                                                        Value.setEnable(ID, false); // 取消啟用
                                                        Value.setEntityName(ID, message);
                                                        player.sendMessage("[自訂怪物磚]  設置成功");
                                                        open(player, ID);

                                                    } else {
                                                        player.sendMessage("[自訂怪物磚]  §c沒有權限");
                                                    }

                                                    cancel();
                                                    return;
                                                }
                                                --time;
                                            }
                                        }.runTaskTimer(SuperFreedomSurvive.SYSTEM.Plugin.get(), 0L, 1L)
                                );
                            }
                            break;


                        case 10:
                            // 最大生成數量
                            if (event.isShiftClick() && event.isRightClick()) {
                                Value.setEnable(ID,false); // 取消啟用
                                Value.setSpawnAmount(ID , 0); // 重製
                                open(player,ID);

                            } else {
                                player.closeInventory();
                                player.sendMessage("[自訂怪物磚]  §a請輸入要更改的數值  範圍 1 ~ 9");

                                SuperFreedomSurvive.SYSTEM.Input.setRegistry(player,
                                        new BukkitRunnable() {
                                            int time = 1200;
                                            String message = null;

                                            @Override
                                            final public void run() {
                                                if (time < 0) {
                                                    player.sendMessage("[自訂怪物磚]  §c等待輸入太久了 自動取消");
                                                    cancel();
                                                    return;
                                                }
                                                message = SuperFreedomSurvive.SYSTEM.Input.getMessage(player);

                                                if (message != null) {
                                                    // 有資料

                                                    if (message.matches("[0-9]{1,9}")) {

                                                        int amount = Integer.parseInt(message);

                                                        if (amount > 0 && amount < 10) {

                                                            Location location = Data.GetLocation(ID);
                                                            if (location == null) {
                                                                return;
                                                            }
                                                            // 檢查是否有權限
                                                            if (SuperFreedomSurvive.Land.Permissions.Inspection(player, location.getWorld().getName(), location.getBlockX(), location.getBlockY(), location.getBlockZ(), "Permissions_Interact_Spawner")) {
                                                                // 有

                                                                Value.setEnable(ID, false); // 取消啟用
                                                                Value.setSpawnAmount(ID, amount);
                                                                player.sendMessage("[自訂怪物磚]  設置成功");
                                                                open(player, ID);

                                                            } else {
                                                                player.sendMessage("[自訂怪物磚]  §c沒有權限");
                                                            }
                                                        } else {
                                                            player.sendMessage("[自訂怪物磚]  §c數值必須 1 ~ 9");
                                                            open(player, ID);
                                                        }
                                                    } else {
                                                        player.sendMessage("[自訂怪物磚]  §c只能是整數數字/超出範圍");
                                                        open(player, ID);
                                                    }

                                                    cancel();
                                                    return;
                                                }
                                                --time;
                                            }
                                        }.runTaskTimer(SuperFreedomSurvive.SYSTEM.Plugin.get(), 0L, 1L)
                                );
                            }
                            break;


                        case 11:
                            // 生成玩家距離多少內觸發
                            if (event.isShiftClick() && event.isRightClick()) {
                                Value.setEnable(ID,false); // 取消啟用
                                Value.setSpawnPlayerDistance(ID , 0); // 重製
                                open(player,ID);

                            } else {
                                player.closeInventory();
                                player.sendMessage("[自訂怪物磚]  §a請輸入要更改的數值  範圍 1 ~ 32");

                                SuperFreedomSurvive.SYSTEM.Input.setRegistry(player,
                                        new BukkitRunnable() {
                                            int time = 1200;
                                            String message = null;

                                            @Override
                                            final public void run() {
                                                if (time < 0) {
                                                    player.sendMessage("[自訂怪物磚]  §c等待輸入太久了 自動取消");
                                                    cancel();
                                                    return;
                                                }
                                                message = SuperFreedomSurvive.SYSTEM.Input.getMessage(player);

                                                if (message != null) {
                                                    // 有資料

                                                    if (message.matches("[0-9]{1,9}")) {

                                                        int amount = Integer.parseInt(message);

                                                        if (amount > 0 && amount < 33) {

                                                            Location location = Data.GetLocation(ID);
                                                            if (location == null) {
                                                                return;
                                                            }
                                                            // 檢查是否有權限
                                                            if (SuperFreedomSurvive.Land.Permissions.Inspection(player, location.getWorld().getName(), location.getBlockX(), location.getBlockY(), location.getBlockZ(), "Permissions_Interact_Spawner")) {
                                                                // 有

                                                                Value.setEnable(ID, false); // 取消啟用
                                                                Value.setSpawnPlayerDistance(ID, amount);
                                                                player.sendMessage("[自訂怪物磚]  設置成功");
                                                                open(player, ID);

                                                            } else {
                                                                player.sendMessage("[自訂怪物磚]  §c沒有權限");
                                                            }
                                                        } else {
                                                            player.sendMessage("[自訂怪物磚]  §c數值必須 1 ~ 32");
                                                            open(player, ID);
                                                        }
                                                    } else {
                                                        player.sendMessage("[自訂怪物磚]  §c只能是整數數字/超出範圍");
                                                        open(player, ID);
                                                    }

                                                    cancel();
                                                    return;
                                                }
                                                --time;
                                            }
                                        }.runTaskTimer(SuperFreedomSurvive.SYSTEM.Plugin.get(), 0L, 1L)
                                );
                            }
                            break;



                        case 12:
                            // 生成延遲(秒)
                            if (event.isShiftClick() && event.isRightClick()) {
                                Value.setEnable(ID,false); // 取消啟用
                                Value.setSpawnDelay(ID , 0); // 重製
                                open(player,ID);

                            } else {
                                player.closeInventory();
                                player.sendMessage("[自訂怪物磚]  §a請輸入要更改的數值  範圍 10 ~ 9,999");

                                SuperFreedomSurvive.SYSTEM.Input.setRegistry(player,
                                        new BukkitRunnable() {
                                            int time = 1200;
                                            String message = null;

                                            @Override
                                            final public void run() {
                                                if (time < 0) {
                                                    player.sendMessage("[自訂怪物磚]  §c等待輸入太久了 自動取消");
                                                    cancel();
                                                    return;
                                                }
                                                message = SuperFreedomSurvive.SYSTEM.Input.getMessage(player);

                                                if (message != null) {
                                                    // 有資料

                                                    if (message.matches("[0-9]{1,9}")) {

                                                        int v = Integer.parseInt(message);

                                                        if (v > 9 && v < 10000) {

                                                            Location location = Data.GetLocation(ID);
                                                            if (location == null) {
                                                                return;
                                                            }
                                                            // 檢查是否有權限
                                                            if (SuperFreedomSurvive.Land.Permissions.Inspection(player, location.getWorld().getName(), location.getBlockX(), location.getBlockY(), location.getBlockZ(), "Permissions_Interact_Spawner")) {
                                                                // 有

                                                                Value.setEnable(ID, false); // 取消啟用
                                                                Value.setSpawnDelay(ID, v);
                                                                player.sendMessage("[自訂怪物磚]  設置成功");
                                                                open(player, ID);

                                                            } else {
                                                                player.sendMessage("[自訂怪物磚]  §c沒有權限");
                                                            }
                                                        } else {
                                                            player.sendMessage("[自訂怪物磚]  §c數值必須 10 ~ 9,999");
                                                            open(player, ID);
                                                        }
                                                    } else {
                                                        player.sendMessage("[自訂怪物磚]  §c只能是整數數字/超出範圍");
                                                        open(player, ID);
                                                    }

                                                    cancel();
                                                    return;
                                                }
                                                --time;
                                            }
                                        }.runTaskTimer(SuperFreedomSurvive.SYSTEM.Plugin.get(), 0L, 1L)
                                );
                            }
                            break;



                        case 13:
                            Value.setBossBar(ID);
                            open(player,ID);
                            break;












                        case 14:
                            // 設置生成X座標
                            if (event.isShiftClick() && event.isRightClick()) {
                                Value.setEnable(ID,false); // 取消啟用
                                Value.setSpawnX( ID , -2147483647 ); // 重製
                                open(player,ID);

                            } else {
                                player.closeInventory();
                                player.sendMessage("[自訂怪物磚]  §a請輸入要更改的數值  範圍 -32,768 ~ 32,767  且距離生怪磚 100 格內");

                                SuperFreedomSurvive.SYSTEM.Input.setRegistry(player,
                                        new BukkitRunnable() {
                                            int time = 1200;
                                            String message = null;

                                            @Override
                                            final public void run() {
                                                if (time < 0) {
                                                    player.sendMessage("[自訂怪物磚]  §c等待輸入太久了 自動取消");
                                                    cancel();
                                                    return;
                                                }
                                                message = SuperFreedomSurvive.SYSTEM.Input.getMessage(player);

                                                if (message != null) {
                                                    // 有資料

                                                    if (
                                                            message.matches("[0-9]{1,9}") ||
                                                                    message.matches("[\\-0-9]{1,9}")
                                                    ) {

                                                        int X = Integer.parseInt(message);

                                                        if (X >= -32768 && X <= 32767) {

                                                            if (X >= Value.getX(ID) - 100 && X <= Value.getX(ID) + 100) {

                                                                Location location = Data.GetLocation(ID);
                                                                if (location == null) {
                                                                    return;
                                                                }
                                                                // 檢查是否有權限
                                                                if (SuperFreedomSurvive.Land.Permissions.Inspection(player, location.getWorld().getName(), location.getBlockX(), location.getBlockY(), location.getBlockZ(), "Permissions_Interact_Spawner")) {
                                                                    // 有

                                                                    Value.setEnable(ID, false); // 取消啟用
                                                                    Value.setSpawnX(ID, X);
                                                                    player.sendMessage("[自訂怪物磚]  設置成功");
                                                                    open(player, ID);

                                                                } else {
                                                                    player.sendMessage("[自訂怪物磚]  §c沒有權限");
                                                                }
                                                            } else {
                                                                player.sendMessage("[自訂怪物磚]  §c只能距離生怪磚 100 格內");
                                                                open(player, ID);
                                                            }
                                                        } else {
                                                            player.sendMessage("[自訂怪物磚]  §c只能是整數數字/超出範圍");
                                                            open(player, ID);
                                                        }
                                                    } else {
                                                        player.sendMessage("[自訂怪物磚]  §c只能是整數數字/超出範圍");
                                                        open(player, ID);
                                                    }

                                                    cancel();
                                                    return;
                                                }
                                                --time;
                                            }
                                        }.runTaskTimer(SuperFreedomSurvive.SYSTEM.Plugin.get(), 0L, 1L)
                                );
                            }
                            break;

                        case 15:
                            // 設置生成Y座標
                            if (event.isShiftClick() && event.isRightClick()) {
                                Value.setEnable(ID,false); // 取消啟用
                                Value.setSpawnY( ID , -2147483647 ); // 重製
                                open(player,ID);

                            } else {
                                player.closeInventory();
                                player.sendMessage("[自訂怪物磚]  §a請輸入要更改的數值  範圍 0 ~ 255  且距離生怪磚 100 格內");

                                SuperFreedomSurvive.SYSTEM.Input.setRegistry(player,
                                        new BukkitRunnable() {
                                            int time = 1200;
                                            String message = null;

                                            @Override
                                            final public void run() {
                                                if (time < 0) {
                                                    player.sendMessage("[自訂怪物磚]  §c等待輸入太久了 自動取消");
                                                    cancel();
                                                    return;
                                                }
                                                message = SuperFreedomSurvive.SYSTEM.Input.getMessage(player);

                                                if (message != null) {
                                                    // 有資料

                                                    if (message.matches("[0-9]{1,9}")) {

                                                        int Y = Integer.parseInt(message);

                                                        if (Y >= 0 && Y <= 255) {

                                                            if (Y >= Value.getY(ID) - 100 && Y <= Value.getY(ID) + 100) {

                                                                Location location = Data.GetLocation(ID);
                                                                if (location == null) {
                                                                    return;
                                                                }
                                                                // 檢查是否有權限
                                                                if (SuperFreedomSurvive.Land.Permissions.Inspection(player, location.getWorld().getName(), location.getBlockX(), location.getBlockY(), location.getBlockZ(), "Permissions_Interact_Spawner")) {
                                                                    // 有

                                                                    Value.setEnable(ID, false); // 取消啟用
                                                                    Value.setSpawnY(ID, Y);
                                                                    player.sendMessage("[自訂怪物磚]  設置成功");
                                                                    open(player, ID);

                                                                } else {
                                                                    player.sendMessage("[自訂怪物磚]  §c沒有權限");
                                                                }
                                                            } else {
                                                                player.sendMessage("[自訂怪物磚]  §c只能距離生怪磚 100 格內");
                                                                open(player, ID);
                                                            }
                                                        } else {
                                                            player.sendMessage("[自訂怪物磚]  §c只能是整數數字/超出範圍");
                                                            open(player, ID);
                                                        }
                                                    } else {
                                                        player.sendMessage("[自訂怪物磚]  §c只能是整數數字/超出範圍");
                                                        open(player, ID);
                                                    }

                                                    cancel();
                                                    return;
                                                }
                                                --time;
                                            }
                                        }.runTaskTimer(SuperFreedomSurvive.SYSTEM.Plugin.get(), 0L, 1L)
                                );
                            }
                            break;

                        case 16:
                            // 設置生成Z座標
                            if (event.isShiftClick() && event.isRightClick()) {
                                Value.setEnable(ID,false); // 取消啟用
                                Value.setSpawnZ( ID , -2147483647 ); // 重製
                                open(player,ID);

                            } else {
                                player.closeInventory();
                                player.sendMessage("[自訂怪物磚]  §a請輸入要更改的數值  範圍 -32,768 ~ 32,767  且距離生怪磚 100 格內");

                                SuperFreedomSurvive.SYSTEM.Input.setRegistry(player,
                                        new BukkitRunnable() {
                                            int time = 1200;
                                            String message = null;

                                            @Override
                                            final public void run() {
                                                if (time < 0) {
                                                    player.sendMessage("[自訂怪物磚]  §c等待輸入太久了 自動取消");
                                                    cancel();
                                                    return;
                                                }
                                                message = SuperFreedomSurvive.SYSTEM.Input.getMessage(player);

                                                if (message != null) {
                                                    // 有資料

                                                    if (
                                                            message.matches("[0-9]{1,9}") ||
                                                                    message.matches("[\\-0-9]{1,9}")
                                                    ) {

                                                        int Z = Integer.parseInt(message);

                                                        if (Z >= -32768 && Z <= 32767) {

                                                            if (Z >= Value.getZ(ID) - 100 && Z <= Value.getZ(ID) + 100) {

                                                                Location location = Data.GetLocation(ID);
                                                                if (location == null) {
                                                                    return;
                                                                }
                                                                // 檢查是否有權限
                                                                if (SuperFreedomSurvive.Land.Permissions.Inspection(player, location.getWorld().getName(), location.getBlockX(), location.getBlockY(), location.getBlockZ(), "Permissions_Interact_Spawner")) {
                                                                    // 有

                                                                    Value.setEnable(ID, false); // 取消啟用
                                                                    Value.setSpawnZ(ID, Z);
                                                                    player.sendMessage("[自訂怪物磚]  設置成功");
                                                                    open(player, ID);

                                                                } else {
                                                                    player.sendMessage("[自訂怪物磚]  §c沒有權限");
                                                                }
                                                            } else {
                                                                player.sendMessage("[自訂怪物磚]  §c只能距離生怪磚 100 格內");
                                                                open(player, ID);
                                                            }
                                                        } else {
                                                            player.sendMessage("[自訂怪物磚]  §c只能是整數數字/超出範圍");
                                                            open(player, ID);
                                                        }
                                                    } else {
                                                        player.sendMessage("[自訂怪物磚]  §c只能是整數數字/超出範圍");
                                                        open(player, ID);
                                                    }

                                                    cancel();
                                                    return;
                                                }
                                                --time;
                                            }
                                        }.runTaskTimer(SuperFreedomSurvive.SYSTEM.Plugin.get(), 0L, 1L)
                                );
                            }
                            break;


                        case 17:
                            // 以座標為中心點 半徑內隨機生成範圍
                            if (event.isShiftClick() && event.isRightClick()) {
                                Value.setEnable(ID,false); // 取消啟用
                                Value.setSpawnRange(ID , 0); // 重製
                                open(player,ID);

                            } else {
                                player.closeInventory();
                                player.sendMessage("[自訂怪物磚]  §a請輸入要更改的數值  範圍 1 ~ 32");

                                SuperFreedomSurvive.SYSTEM.Input.setRegistry(player,
                                        new BukkitRunnable() {
                                            int time = 1200;
                                            String message = null;

                                            @Override
                                            final public void run() {
                                                if (time < 0) {
                                                    player.sendMessage("[自訂怪物磚]  §c等待輸入太久了 自動取消");
                                                    cancel();
                                                    return;
                                                }
                                                message = SuperFreedomSurvive.SYSTEM.Input.getMessage(player);

                                                if (message != null) {
                                                    // 有資料

                                                    if (message.matches("[0-9]{1,9}")) {

                                                        int range = Integer.parseInt(message);

                                                        if (range > 0 && range < 32) {

                                                            Location location = Data.GetLocation(ID);
                                                            if (location == null) {
                                                                return;
                                                            }
                                                            // 檢查是否有權限
                                                            if (SuperFreedomSurvive.Land.Permissions.Inspection(player, location.getWorld().getName(), location.getBlockX(), location.getBlockY(), location.getBlockZ(), "Permissions_Interact_Spawner")) {
                                                                // 有

                                                                Value.setEnable(ID, false); // 取消啟用
                                                                Value.setSpawnRange(ID, range);
                                                                player.sendMessage("[自訂怪物磚]  設置成功");
                                                                open(player, ID);

                                                            } else {
                                                                player.sendMessage("[自訂怪物磚]  §c沒有權限");
                                                            }
                                                        } else {
                                                            player.sendMessage("[自訂怪物磚]  §c數值必須 1 ~ 32");
                                                            open(player, ID);
                                                        }
                                                    } else {
                                                        player.sendMessage("[自訂怪物磚]  §c只能是整數數字/超出範圍");
                                                        open(player, ID);
                                                    }

                                                    cancel();
                                                    return;
                                                }
                                                --time;
                                            }
                                        }.runTaskTimer(SuperFreedomSurvive.SYSTEM.Plugin.get(), 0L, 1L)
                                );
                            }
                            break;





































                        case 18:
                            // 護甲值
                            if (event.isShiftClick() && event.isRightClick()) {
                                Value.setEnable(ID,false); // 取消啟用
                                Value.setAttribute(ID,"GENERIC_ARMOR", -1); // 重製
                                open(player,ID);

                            } else {
                                player.closeInventory();
                                player.sendMessage("[自訂怪物磚]  §a請輸入要更改的數值  範圍 0 ~ 30");

                                SuperFreedomSurvive.SYSTEM.Input.setRegistry(player,
                                        new BukkitRunnable() {
                                            int time = 1200;
                                            String message = null;

                                            @Override
                                            final public void run() {
                                                if (time < 0) {
                                                    player.sendMessage("[自訂怪物磚]  §c等待輸入太久了 自動取消");
                                                    cancel();
                                                    return;
                                                }
                                                message = SuperFreedomSurvive.SYSTEM.Input.getMessage(player);

                                                if (message != null) {
                                                    // 有資料

                                                    if (message.matches("[0-9]{1,9}")) {

                                                        int v = Integer.parseInt(message);

                                                        if (v > -1 && v < 31) {

                                                            Location location = Data.GetLocation(ID);
                                                            if (location == null) {
                                                                return;
                                                            }
                                                            // 檢查是否有權限
                                                            if (SuperFreedomSurvive.Land.Permissions.Inspection(player, location.getWorld().getName(), location.getBlockX(), location.getBlockY(), location.getBlockZ(), "Permissions_Interact_Spawner")) {
                                                                // 有

                                                                Value.setEnable(ID, false); // 取消啟用
                                                                Value.setAttribute(ID, "GENERIC_ARMOR", v);
                                                                player.sendMessage("[自訂怪物磚]  設置成功");
                                                                open(player, ID);

                                                            } else {
                                                                player.sendMessage("[自訂怪物磚]  §c沒有權限");
                                                            }
                                                        } else {
                                                            player.sendMessage("[自訂怪物磚]  §c數值必須 0 ~ 30");
                                                            open(player, ID);
                                                        }
                                                    } else {
                                                        player.sendMessage("[自訂怪物磚]  §c只能是整數數字/超出範圍");
                                                        open(player, ID);
                                                    }

                                                    cancel();
                                                    return;
                                                }
                                                --time;
                                            }
                                        }.runTaskTimer(SuperFreedomSurvive.SYSTEM.Plugin.get(), 0L, 1L)
                                );
                            }
                            break;


                        case 19:
                            // 護甲耐久性值
                            if (event.isShiftClick() && event.isRightClick()) {
                                Value.setEnable(ID,false); // 取消啟用
                                Value.setAttribute(ID,"GENERIC_ARMOR_TOUGHNESS", -1); // 重製
                                open(player,ID);

                            } else {
                                player.closeInventory();
                                player.sendMessage("[自訂怪物磚]  §a請輸入要更改的數值  範圍 0 ~ 20");

                                SuperFreedomSurvive.SYSTEM.Input.setRegistry(player,
                                        new BukkitRunnable() {
                                            int time = 1200;
                                            String message = null;

                                            @Override
                                            final public void run() {
                                                if (time < 0) {
                                                    player.sendMessage("[自訂怪物磚]  §c等待輸入太久了 自動取消");
                                                    cancel();
                                                    return;
                                                }
                                                message = SuperFreedomSurvive.SYSTEM.Input.getMessage(player);

                                                if (message != null) {
                                                    // 有資料

                                                    if (message.matches("[0-9]{1,9}")) {

                                                        int v = Integer.parseInt(message);

                                                        if (v > -1 && v < 21) {

                                                            Location location = Data.GetLocation(ID);
                                                            if (location == null) {
                                                                return;
                                                            }
                                                            // 檢查是否有權限
                                                            if (SuperFreedomSurvive.Land.Permissions.Inspection(player, location.getWorld().getName(), location.getBlockX(), location.getBlockY(), location.getBlockZ(), "Permissions_Interact_Spawner")) {
                                                                // 有

                                                                Value.setEnable(ID, false); // 取消啟用
                                                                Value.setAttribute(ID, "GENERIC_ARMOR_TOUGHNESS", v);
                                                                player.sendMessage("[自訂怪物磚]  設置成功");
                                                                open(player, ID);

                                                            } else {
                                                                player.sendMessage("[自訂怪物磚]  §c沒有權限");
                                                            }
                                                        } else {
                                                            player.sendMessage("[自訂怪物磚]  §c數值必須 0 ~ 20");
                                                            open(player, ID);
                                                        }
                                                    } else {
                                                        player.sendMessage("[自訂怪物磚]  §c只能是整數數字/超出範圍");
                                                        open(player, ID);
                                                    }

                                                    cancel();
                                                    return;
                                                }
                                                --time;
                                            }
                                        }.runTaskTimer(SuperFreedomSurvive.SYSTEM.Plugin.get(), 0L, 1L)
                                );
                            }
                            break;


                        case 20:
                            // 攻擊傷害
                            if (event.isShiftClick() && event.isRightClick()) {
                                Value.setEnable(ID,false); // 取消啟用
                                Value.setAttribute(ID,"GENERIC_ATTACK_DAMAGE", -1); // 重製
                                open(player,ID);

                            } else {
                                player.closeInventory();
                                player.sendMessage("[自訂怪物磚]  §a請輸入要更改的數值  範圍 0 ~ 1,000,000 (1 = 1HP)");

                                SuperFreedomSurvive.SYSTEM.Input.setRegistry(player,
                                        new BukkitRunnable() {
                                            int time = 1200;
                                            String message = null;

                                            @Override
                                            final public void run() {
                                                if (time < 0) {
                                                    player.sendMessage("[自訂怪物磚]  §c等待輸入太久了 自動取消");
                                                    cancel();
                                                    return;
                                                }
                                                message = SuperFreedomSurvive.SYSTEM.Input.getMessage(player);

                                                if (message != null) {
                                                    // 有資料

                                                    if (message.matches("[0-9]{1,9}")) {

                                                        int v = Integer.parseInt(message);

                                                        if (v > -1 && v < 1000001) {

                                                            Location location = Data.GetLocation(ID);
                                                            if (location == null) {
                                                                return;
                                                            }
                                                            // 檢查是否有權限
                                                            if (SuperFreedomSurvive.Land.Permissions.Inspection(player, location.getWorld().getName(), location.getBlockX(), location.getBlockY(), location.getBlockZ(), "Permissions_Interact_Spawner")) {
                                                                // 有

                                                                Value.setEnable(ID, false); // 取消啟用
                                                                Value.setAttribute(ID, "GENERIC_ATTACK_DAMAGE", v);
                                                                player.sendMessage("[自訂怪物磚]  設置成功");
                                                                open(player, ID);

                                                            } else {
                                                                player.sendMessage("[自訂怪物磚]  §c沒有權限");
                                                            }
                                                        } else {
                                                            player.sendMessage("[自訂怪物磚]  §c數值必須 0 ~ 1,000,000");
                                                            open(player, ID);
                                                        }
                                                    } else {
                                                        player.sendMessage("[自訂怪物磚]  §c只能是整數數字/超出範圍");
                                                        open(player, ID);
                                                    }

                                                    cancel();
                                                    return;
                                                }
                                                --time;
                                            }
                                        }.runTaskTimer(SuperFreedomSurvive.SYSTEM.Plugin.get(), 0L, 1L)
                                );
                            }
                            break;


                        case 21:
                            // 跟隨玩家的範圍
                            if (event.isShiftClick() && event.isRightClick()) {
                                Value.setEnable(ID,false); // 取消啟用
                                Value.setAttribute(ID,"GENERIC_FOLLOW_RANGE", -1); // 重製
                                open(player,ID);

                            } else {
                                player.closeInventory();
                                player.sendMessage("[自訂怪物磚]  §a請輸入要更改的數值  範圍 0 ~ 32 (方塊)");

                                SuperFreedomSurvive.SYSTEM.Input.setRegistry(player,
                                        new BukkitRunnable() {
                                            int time = 1200;
                                            String message = null;

                                            @Override
                                            final public void run() {
                                                if (time < 0) {
                                                    player.sendMessage("[自訂怪物磚]  §c等待輸入太久了 自動取消");
                                                    cancel();
                                                    return;
                                                }
                                                message = SuperFreedomSurvive.SYSTEM.Input.getMessage(player);

                                                if (message != null) {
                                                    // 有資料

                                                    if (message.matches("[0-9]{1,9}")) {

                                                        int v = Integer.parseInt(message);

                                                        if (v > -1 && v < 33) {

                                                            Location location = Data.GetLocation(ID);
                                                            if (location == null) {
                                                                return;
                                                            }
                                                            // 檢查是否有權限
                                                            if (SuperFreedomSurvive.Land.Permissions.Inspection(player, location.getWorld().getName(), location.getBlockX(), location.getBlockY(), location.getBlockZ(), "Permissions_Interact_Spawner")) {
                                                                // 有

                                                                Value.setEnable(ID, false); // 取消啟用
                                                                Value.setAttribute(ID, "GENERIC_FOLLOW_RANGE", v);
                                                                player.sendMessage("[自訂怪物磚]  設置成功");
                                                                open(player, ID);

                                                            } else {
                                                                player.sendMessage("[自訂怪物磚]  §c沒有權限");
                                                            }
                                                        } else {
                                                            player.sendMessage("[自訂怪物磚]  §c數值必須 0 ~ 32");
                                                            open(player, ID);
                                                        }
                                                    } else {
                                                        player.sendMessage("[自訂怪物磚]  §c只能是整數數字/超出範圍");
                                                        open(player, ID);
                                                    }

                                                    cancel();
                                                    return;
                                                }
                                                --time;
                                            }
                                        }.runTaskTimer(SuperFreedomSurvive.SYSTEM.Plugin.get(), 0L, 1L)
                                );
                            }
                            break;


                        case 22:
                            // 擊退抵抗力
                            if (event.isShiftClick() && event.isRightClick()) {
                                Value.setEnable(ID,false); // 取消啟用
                                Value.setAttribute(ID,"GENERIC_KNOCKBACK_RESISTANCE", -1); // 重製
                                open(player,ID);

                            } else {
                                player.closeInventory();
                                player.sendMessage("[自訂怪物磚]  §a請輸入要更改的數值  範圍 0 ~ 100 (%)");

                                SuperFreedomSurvive.SYSTEM.Input.setRegistry(player,
                                        new BukkitRunnable() {
                                            int time = 1200;
                                            String message = null;

                                            @Override
                                            final public void run() {
                                                if (time < 0) {
                                                    player.sendMessage("[自訂怪物磚]  §c等待輸入太久了 自動取消");
                                                    cancel();
                                                    return;
                                                }
                                                message = SuperFreedomSurvive.SYSTEM.Input.getMessage(player);

                                                if (message != null) {
                                                    // 有資料

                                                    if (message.matches("[0-9]{1,9}")) {

                                                        int v = Integer.parseInt(message);

                                                        if (v > -1 && v < 101) {

                                                            Location location = Data.GetLocation(ID);
                                                            if (location == null) {
                                                                return;
                                                            }
                                                            // 檢查是否有權限
                                                            if (SuperFreedomSurvive.Land.Permissions.Inspection(player, location.getWorld().getName(), location.getBlockX(), location.getBlockY(), location.getBlockZ(), "Permissions_Interact_Spawner")) {
                                                                // 有

                                                                Value.setEnable(ID, false); // 取消啟用
                                                                Value.setAttribute(ID, "GENERIC_KNOCKBACK_RESISTANCE", v);
                                                                player.sendMessage("[自訂怪物磚]  設置成功");
                                                                open(player, ID);

                                                            } else {
                                                                player.sendMessage("[自訂怪物磚]  §c沒有權限");
                                                            }
                                                        } else {
                                                            player.sendMessage("[自訂怪物磚]  §c數值必須 0 ~ 100");
                                                            open(player, ID);
                                                        }
                                                    } else {
                                                        player.sendMessage("[自訂怪物磚]  §c只能是整數數字/超出範圍");
                                                        open(player, ID);
                                                    }

                                                    cancel();
                                                    return;
                                                }
                                                --time;
                                            }
                                        }.runTaskTimer(SuperFreedomSurvive.SYSTEM.Plugin.get(), 0L, 1L)
                                );
                            }
                            break;


                        case 23:
                            // 最大生命值
                            if (event.isShiftClick() && event.isRightClick()) {
                                Value.setEnable(ID,false); // 取消啟用
                                Value.setAttribute(ID,"GENERIC_MAX_HEALTH", -1); // 重製
                                open(player,ID);

                            } else {
                                player.closeInventory();
                                player.sendMessage("[自訂怪物磚]  §a請輸入要更改的數值  範圍 1 ~ 1,000,000");

                                SuperFreedomSurvive.SYSTEM.Input.setRegistry(player,
                                        new BukkitRunnable() {
                                            int time = 1200;
                                            String message = null;

                                            @Override
                                            final public void run() {
                                                if (time < 0) {
                                                    player.sendMessage("[自訂怪物磚]  §c等待輸入太久了 自動取消");
                                                    cancel();
                                                    return;
                                                }
                                                message = SuperFreedomSurvive.SYSTEM.Input.getMessage(player);

                                                if (message != null) {
                                                    // 有資料

                                                    if (message.matches("[0-9]{1,9}")) {

                                                        int v = Integer.parseInt(message);

                                                        if (v > 0 && v < 1000001) {

                                                            Location location = Data.GetLocation(ID);
                                                            if (location == null) {
                                                                return;
                                                            }
                                                            // 檢查是否有權限
                                                            if (SuperFreedomSurvive.Land.Permissions.Inspection(player, location.getWorld().getName(), location.getBlockX(), location.getBlockY(), location.getBlockZ(), "Permissions_Interact_Spawner")) {
                                                                // 有

                                                                Value.setEnable(ID, false); // 取消啟用
                                                                Value.setAttribute(ID, "GENERIC_MAX_HEALTH", v);
                                                                player.sendMessage("[自訂怪物磚]  設置成功");
                                                                open(player, ID);

                                                            } else {
                                                                player.sendMessage("[自訂怪物磚]  §c沒有權限");
                                                            }
                                                        } else {
                                                            player.sendMessage("[自訂怪物磚]  §c數值必須 1 ~ 1,000,000");
                                                            open(player, ID);
                                                        }
                                                    } else {
                                                        player.sendMessage("[自訂怪物磚]  §c只能是整數數字/超出範圍");
                                                        open(player, ID);
                                                    }

                                                    cancel();
                                                    return;
                                                }
                                                --time;
                                            }
                                        }.runTaskTimer(SuperFreedomSurvive.SYSTEM.Plugin.get(), 0L, 1L)
                                );
                            }
                            break;


                        case 24:
                            // 移動速度
                            if (event.isShiftClick() && event.isRightClick()) {
                                Value.setEnable(ID,false); // 取消啟用
                                Value.setAttribute(ID,"GENERIC_MOVEMENT_SPEED", -1); // 重製
                                open(player,ID);

                            } else {
                                player.closeInventory();
                                player.sendMessage("[自訂怪物磚]  §a請輸入要更改的數值  範圍 0 ~ 128 (10 = 4.3塊/秒)");

                                SuperFreedomSurvive.SYSTEM.Input.setRegistry(player,
                                        new BukkitRunnable() {
                                            int time = 1200;
                                            String message = null;

                                            @Override
                                            final public void run() {
                                                if (time < 0) {
                                                    player.sendMessage("[自訂怪物磚]  §c等待輸入太久了 自動取消");
                                                    cancel();
                                                    return;
                                                }
                                                message = SuperFreedomSurvive.SYSTEM.Input.getMessage(player);

                                                if (message != null) {
                                                    // 有資料

                                                    if (message.matches("[0-9]{1,9}")) {

                                                        int v = Integer.parseInt(message);

                                                        if (v > -1 && v < 129) {

                                                            Location location = Data.GetLocation(ID);
                                                            if (location == null) {
                                                                return;
                                                            }
                                                            // 檢查是否有權限
                                                            if (SuperFreedomSurvive.Land.Permissions.Inspection(player, location.getWorld().getName(), location.getBlockX(), location.getBlockY(), location.getBlockZ(), "Permissions_Interact_Spawner")) {
                                                                // 有

                                                                Value.setEnable(ID, false); // 取消啟用
                                                                Value.setAttribute(ID, "GENERIC_MOVEMENT_SPEED", v);
                                                                player.sendMessage("[自訂怪物磚]  設置成功");
                                                                open(player, ID);

                                                            } else {
                                                                player.sendMessage("[自訂怪物磚]  §c沒有權限");
                                                            }
                                                        } else {
                                                            player.sendMessage("[自訂怪物磚]  §c數值必須 0 ~ 128");
                                                            open(player, ID);
                                                        }
                                                    } else {
                                                        player.sendMessage("[自訂怪物磚]  §c只能是整數數字/超出範圍");
                                                        open(player, ID);
                                                    }

                                                    cancel();
                                                    return;
                                                }
                                                --time;
                                            }
                                        }.runTaskTimer(SuperFreedomSurvive.SYSTEM.Plugin.get(), 0L, 1L)
                                );
                            }
                            break;




















                        case 46:
                            // 啟用 檢查所有數值是否正確
                            if ( Value.isNoProblem(ID) ) {
                                Value.setEnable(ID);
                                open(player, ID);
                            } else {
                                player.sendMessage("[自訂怪物磚]  §c請確認所有藍色標題的數值都有輸入");
                            }
                            break;










                        case 53:
                            player.closeInventory();
                            break;


                    }
                }
            }
        }
    }







    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////








    static public void Menu_EntityType(Player player,String ID) {

        Location location = Data.GetLocation(ID);
        if ( location == null ) { return; }
        // 檢查是否有權限
        if (SuperFreedomSurvive.Land.Permissions.Inspection(player, location.getWorld().getName() , location.getBlockX() , location.getBlockY() , location.getBlockZ() , "Permissions_Interact_Spawner")) {
            // 有

            // 開啟編輯介面
            Inventory inv = Bukkit.createInventory(null, 45, "§z選擇怪物類型");

            inv.setItem(0, getReplaceItemStack(ID,Material.GHAST_TEAR ,"§e無"));
            inv.setItem(1, getReplaceItemStack(ID,Material.ZOMBIE_SPAWN_EGG ,"§e殭屍"));
            inv.setItem(2, getReplaceItemStack(ID,Material.ZOMBIE_PIGMAN_SPAWN_EGG ,"§e殭屍豬人"));
            inv.setItem(3, getReplaceItemStack(ID,Material.CREEPER_SPAWN_EGG ,"§e苦力怕"));
            inv.setItem(4, getReplaceItemStack(ID,Material.ELDER_GUARDIAN_SPAWN_EGG ,"§e遠古深海守衛"));
            inv.setItem(5, getReplaceItemStack(ID,Material.WITHER_SKELETON_SPAWN_EGG ,"§e凋零骷髏"));
            inv.setItem(6, getReplaceItemStack(ID,Material.STRAY_SPAWN_EGG ,"§e流髑"));
            inv.setItem(7, getReplaceItemStack(ID,Material.HUSK_SPAWN_EGG ,"§e屍殼"));
            inv.setItem(8, getReplaceItemStack(ID,Material.EVOKER_SPAWN_EGG ,"§e喚魔者"));
            inv.setItem(9, getReplaceItemStack(ID,Material.VEX_SPAWN_EGG ,"§e惱鬼"));
            inv.setItem(10, getReplaceItemStack(ID,Material.VINDICATOR_SPAWN_EGG ,"§e衛道士"));
            inv.setItem(11, getReplaceItemStack(ID,Material.EVOKER_SPAWN_EGG ,"§e換魔者"));
            inv.setItem(12, getReplaceItemStack(ID,Material.SKELETON_SPAWN_EGG ,"§e骷髏"));
            inv.setItem(13, getReplaceItemStack(ID,Material.SPIDER_SPAWN_EGG ,"§e蜘蛛"));
            {
                ItemStack item = getReplaceItemStack(ID, Material.ZOMBIE_SPAWN_EGG, "§e巨人");
                ItemMeta meta = item.getItemMeta();
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                meta.addEnchant(Enchantment.BINDING_CURSE, 1, false);
                item.setItemMeta(meta);

                inv.setItem(14, item);
            }
            inv.setItem(15, getReplaceItemStack(ID,Material.SLIME_SPAWN_EGG ,"§e史萊姆"));
            inv.setItem(16, getReplaceItemStack(ID,Material.GHAST_SPAWN_EGG ,"§e地獄幽靈"));
            inv.setItem(17, getReplaceItemStack(ID,Material.ENDERMAN_SPAWN_EGG ,"§e終界使者"));
            inv.setItem(18, getReplaceItemStack(ID,Material.CAVE_SPIDER_SPAWN_EGG ,"§e洞穴蜘蛛"));
            inv.setItem(19, getReplaceItemStack(ID,Material.SILVERFISH_SPAWN_EGG ,"§e蠹魚"));
            inv.setItem(20, getReplaceItemStack(ID,Material.BLAZE_SPAWN_EGG ,"§e烈焰使者"));
            inv.setItem(21, getReplaceItemStack(ID,Material.MAGMA_CUBE_SPAWN_EGG ,"§e熔岩史萊姆"));
            inv.setItem(22, getReplaceItemStack(ID,Material.WITHER_SKELETON_SPAWN_EGG ,"§e凋零怪"));
            inv.setItem(23, getReplaceItemStack(ID,Material.WITCH_SPAWN_EGG ,"§e女巫"));
            inv.setItem(24, getReplaceItemStack(ID,Material.ENDERMITE_SPAWN_EGG ,"§e終界蟎"));
            inv.setItem(25, getReplaceItemStack(ID,Material.GUARDIAN_SPAWN_EGG ,"§e深海守衛"));
            inv.setItem(26, getReplaceItemStack(ID,Material.SHULKER_SPAWN_EGG ,"§e界伏蚌"));
            inv.setItem(27, getReplaceItemStack(ID,Material.GOLD_NUGGET ,"§e閃電"));
            inv.setItem(28, getReplaceItemStack(ID,Material.IRON_BLOCK ,"§e鐵巨人"));
            inv.setItem(29, getReplaceItemStack(ID,Material.SNOW_BLOCK ,"§e雪人"));


            // --------------------------------------     18     --------------------------------------
            inv.setItem(36, getIDItemStack(ID));

            // --------------------------------------     25     --------------------------------------
            inv.setItem(43, SuperFreedomSurvive.Menu.Open.PreviousPage());

            // --------------------------------------     26     --------------------------------------
            inv.setItem(44, SuperFreedomSurvive.Menu.Open.TurnOff());


            player.openInventory(inv);
        }
    }

    // 阻止玩家拿走物品
    @EventHandler
    final public void onInventoryClickEvent_(InventoryClickEvent event) {
        // 判斷是否為玩家

        if (event.getWhoClicked() instanceof Player) {
            // 是玩家
            Player player = (Player) event.getWhoClicked();

            // 檢查容器名稱
            if (event.getView().getTitle().equalsIgnoreCase("§z選擇怪物類型")) {
                // 是沒錯

                event.setCancelled(true);

                if (event.getClickedInventory() == null) { return; }
                if (event.getClickedInventory().getItem(36) == null) { return; }
                if (event.getClickedInventory().getItem(36).getItemMeta() == null) { return; }
                if (event.getClickedInventory().getItem(36).getItemMeta().getCustomTagContainer().getCustomTag(id_key, ItemTagType.STRING) == null) { return; }
                String ID = event.getClickedInventory().getItem(36).getItemMeta().getCustomTagContainer().getCustomTag(id_key, ItemTagType.STRING);

                Location location = Data.GetLocation(ID);

                if (location == null) { return; }
                // 檢查是否有權限
                if (SuperFreedomSurvive.Land.Permissions.Inspection(player, location.getWorld().getName(), location.getBlockX(), location.getBlockY(), location.getBlockZ(), "Permissions_Interact_Spawner")) {
                    // 有

                    if (location == null) { return; }
                    if (location.getWorld() == null) { return; }
                    if (location.getWorld().getBlockAt(location).getType() != Material.SPAWNER) { return; }

                    CreatureSpawner block = (CreatureSpawner) location.getWorld().getBlockAt(location).getState();

                    switch (event.getRawSlot()) {
                        case 0:
                            Value.setEnable(ID,false); // 取消啟用
                            Value.setEntityType(ID, null);

                            block.setSpawnCount(0);
                            block.setDelay(2147483647);
                            block.setSpawnedType(EntityType.LIGHTNING);
                            block.update();
                            open(player, ID);
                            break;

                        case 1:
                            Value.setEntityType(ID, EntityType.ZOMBIE);
                            break;

                        case 2:
                            Value.setEntityType(ID, EntityType.PIG_ZOMBIE);
                            break;

                        case 3:
                            Value.setEntityType(ID, EntityType.CREEPER);
                            break;

                        case 4:
                            Value.setEntityType(ID, EntityType.ELDER_GUARDIAN);
                            break;

                        case 5:
                            Value.setEntityType(ID, EntityType.WITHER_SKELETON);
                            break;

                        case 6:
                            Value.setEntityType(ID, EntityType.STRAY);
                            break;

                        case 7:
                            Value.setEntityType(ID, EntityType.HUSK);
                            break;

                        case 8:
                            Value.setEntityType(ID, EntityType.EVOKER);
                            break;

                        case 9:
                            Value.setEntityType(ID, EntityType.VEX);
                            break;

                        case 10:
                            Value.setEntityType(ID, EntityType.VINDICATOR);
                            break;

                        case 11:
                            Value.setEntityType(ID, EntityType.ILLUSIONER);
                            break;

                        case 12:
                            Value.setEntityType(ID, EntityType.SKELETON);
                            break;

                        case 13:
                            Value.setEntityType(ID, EntityType.SPIDER);
                            break;

                        case 14:
                            Value.setEntityType(ID, EntityType.GIANT);
                            break;

                        case 15:
                            Value.setEntityType(ID, EntityType.SLIME);
                            break;

                        case 16:
                            Value.setEntityType(ID, EntityType.GHAST);
                            break;

                        case 17:
                            Value.setEntityType(ID, EntityType.ENDERMAN);
                            break;

                        case 18:
                            Value.setEntityType(ID, EntityType.CAVE_SPIDER);
                            break;

                        case 19:
                            Value.setEntityType(ID, EntityType.SILVERFISH);
                            break;

                        case 20:
                            Value.setEntityType(ID, EntityType.BLAZE);
                            break;

                        case 21:
                            Value.setEntityType(ID, EntityType.MAGMA_CUBE);
                            break;

                        case 22:
                            Value.setEntityType(ID, EntityType.WITHER);
                            break;

                        case 23:
                            Value.setEntityType(ID, EntityType.WITCH);
                            break;

                        case 24:
                            Value.setEntityType(ID, EntityType.ENDERMITE);
                            break;

                        case 25:
                            Value.setEntityType(ID, EntityType.GUARDIAN);
                            break;

                        case 26:
                            Value.setEntityType(ID, EntityType.SHULKER);
                            break;

                        case 27:
                            Value.setEntityType(ID, EntityType.LIGHTNING);
                            break;

                        case 28:
                            Value.setEntityType(ID, EntityType.IRON_GOLEM);
                            break;

                        case 29:
                            Value.setEntityType(ID, EntityType.SNOWMAN);
                            break;

                        case 43:
                            open(player, ID);
                            break;

                        case 44:
                            player.closeInventory();
                            break;
                    }

                    if (event.getRawSlot() >= 1 && event.getRawSlot() <= 29) {
                        block.setSpawnCount(0);
                        block.setDelay(2147483647);
                        block.setSpawnedType(EntityType.ENDER_SIGNAL);
                        block.update();
                        open(player, ID);
                    }

                }
            }
        }
    }



    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    static public void Menu_Helmet(Player player,String ID) {

        Location location = Data.GetLocation(ID);
        if ( location == null ) { return; }
        // 檢查是否有權限
        if (SuperFreedomSurvive.Land.Permissions.Inspection(player, location.getWorld().getName() , location.getBlockX() , location.getBlockY() , location.getBlockZ() , "Permissions_Interact_Spawner")) {
            // 有

            // 開啟編輯介面
            Inventory inv = Bukkit.createInventory(null, 18, "§z選擇怪物頭盔類型");

            inv.setItem(0, getReplaceItemStack(ID,Material.GHAST_TEAR ,"§e無"));
            inv.setItem(1, getReplaceItemStack(ID,Material.LEATHER_HELMET ,null));
            inv.setItem(2, getReplaceItemStack(ID,Material.CHAINMAIL_HELMET ,null));
            inv.setItem(3, getReplaceItemStack(ID,Material.IRON_HELMET ,null));
            inv.setItem(4, getReplaceItemStack(ID,Material.GOLDEN_HELMET ,null));
            inv.setItem(5, getReplaceItemStack(ID,Material.DIAMOND_HELMET ,null));
            inv.setItem(6, getReplaceItemStack(ID,Material.TURTLE_HELMET ,null));


            // --------------------------------------     9     --------------------------------------
            inv.setItem(9, getIDItemStack(ID));

            // --------------------------------------     16     --------------------------------------
            inv.setItem(16, SuperFreedomSurvive.Menu.Open.PreviousPage());

            // --------------------------------------     17     --------------------------------------
            inv.setItem(17, SuperFreedomSurvive.Menu.Open.TurnOff());

            player.openInventory(inv);
        }
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    static public void Menu_Chestplate(Player player,String ID) {

        Location location = Data.GetLocation(ID);
        if ( location == null ) { return; }
        // 檢查是否有權限
        if (SuperFreedomSurvive.Land.Permissions.Inspection(player, location.getWorld().getName() , location.getBlockX() , location.getBlockY() , location.getBlockZ() , "Permissions_Interact_Spawner")) {
            // 有

            // 開啟編輯介面
            Inventory inv = Bukkit.createInventory(null, 18, "§z選擇怪物胸甲類型");

            inv.setItem(0, getReplaceItemStack(ID,Material.GHAST_TEAR ,"§e無"));
            inv.setItem(1, getReplaceItemStack(ID,Material.LEATHER_CHESTPLATE ,null));
            inv.setItem(2, getReplaceItemStack(ID,Material.CHAINMAIL_CHESTPLATE ,null));
            inv.setItem(3, getReplaceItemStack(ID,Material.IRON_CHESTPLATE ,null));
            inv.setItem(4, getReplaceItemStack(ID,Material.GOLDEN_CHESTPLATE ,null));
            inv.setItem(5, getReplaceItemStack(ID,Material.DIAMOND_CHESTPLATE ,null));


            // --------------------------------------     9     --------------------------------------
            inv.setItem(9, getIDItemStack(ID));

            // --------------------------------------     16     --------------------------------------
            inv.setItem(16, SuperFreedomSurvive.Menu.Open.PreviousPage());

            // --------------------------------------     17     --------------------------------------
            inv.setItem(17, SuperFreedomSurvive.Menu.Open.TurnOff());

            player.openInventory(inv);
        }
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    static public void Menu_Leggings(Player player,String ID) {

        Location location = Data.GetLocation(ID);
        if ( location == null ) { return; }
        // 檢查是否有權限
        if (SuperFreedomSurvive.Land.Permissions.Inspection(player, location.getWorld().getName() , location.getBlockX() , location.getBlockY() , location.getBlockZ() , "Permissions_Interact_Spawner")) {
            // 有

            // 開啟編輯介面
            Inventory inv = Bukkit.createInventory(null, 18, "§z選擇怪物護腿類型");

            inv.setItem(0, getReplaceItemStack(ID,Material.GHAST_TEAR ,"§e無"));
            inv.setItem(1, getReplaceItemStack(ID,Material.LEATHER_LEGGINGS ,null));
            inv.setItem(2, getReplaceItemStack(ID,Material.CHAINMAIL_LEGGINGS ,null));
            inv.setItem(3, getReplaceItemStack(ID,Material.IRON_LEGGINGS ,null));
            inv.setItem(4, getReplaceItemStack(ID,Material.GOLDEN_LEGGINGS ,null));
            inv.setItem(5, getReplaceItemStack(ID,Material.DIAMOND_LEGGINGS ,null));


            // --------------------------------------     9     --------------------------------------
            inv.setItem(9, getIDItemStack(ID));

            // --------------------------------------     16     --------------------------------------
            inv.setItem(16, SuperFreedomSurvive.Menu.Open.PreviousPage());

            // --------------------------------------     17     --------------------------------------
            inv.setItem(17, SuperFreedomSurvive.Menu.Open.TurnOff());

            player.openInventory(inv);
        }
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    static public void Menu_Boots(Player player,String ID) {

        Location location = Data.GetLocation(ID);
        if ( location == null ) { return; }
        // 檢查是否有權限
        if (SuperFreedomSurvive.Land.Permissions.Inspection(player, location.getWorld().getName() , location.getBlockX() , location.getBlockY() , location.getBlockZ() , "Permissions_Interact_Spawner")) {
            // 有

            // 開啟編輯介面
            Inventory inv = Bukkit.createInventory(null, 18, "§z選擇怪物靴子類型");

            inv.setItem(0, getReplaceItemStack(ID,Material.GHAST_TEAR ,"§e無"));
            inv.setItem(1, getReplaceItemStack(ID,Material.LEATHER_BOOTS ,null));
            inv.setItem(2, getReplaceItemStack(ID,Material.CHAINMAIL_BOOTS ,null));
            inv.setItem(3, getReplaceItemStack(ID,Material.IRON_BOOTS ,null));
            inv.setItem(4, getReplaceItemStack(ID,Material.GOLDEN_BOOTS ,null));
            inv.setItem(5, getReplaceItemStack(ID,Material.DIAMOND_BOOTS ,null));


            // --------------------------------------     9     --------------------------------------
            inv.setItem(9, getIDItemStack(ID));

            // --------------------------------------     16     --------------------------------------
            inv.setItem(16, SuperFreedomSurvive.Menu.Open.PreviousPage());

            // --------------------------------------     17     --------------------------------------
            inv.setItem(17, SuperFreedomSurvive.Menu.Open.TurnOff());

            player.openInventory(inv);
        }
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // 阻止玩家拿走物品
    @EventHandler
    final public void onInventoryClickEvent__(InventoryClickEvent event) {
        // 判斷是否為玩家

        if (event.getWhoClicked() instanceof Player) {
            // 是玩家
            Player player = (Player) event.getWhoClicked();

            // 檢查容器名稱
            if (event.getView().getTitle().equalsIgnoreCase("§z選擇怪物頭盔類型")) {
                // 是沒錯

                event.setCancelled(true);

                if ( event.getClickedInventory() == null ) { return; }
                if ( event.getClickedInventory().getItem(9) == null ) { return; }
                if ( event.getClickedInventory().getItem(9).getItemMeta() == null ) { return; }
                if ( event.getClickedInventory().getItem(9).getItemMeta().getCustomTagContainer().getCustomTag(id_key,ItemTagType.STRING) == null ) { return; }
                String ID = event.getClickedInventory().getItem(9).getItemMeta().getCustomTagContainer().getCustomTag(id_key,ItemTagType.STRING);

                Location location = Data.GetLocation(ID);
                if ( location == null ) { return; }
                // 檢查是否有權限
                if (SuperFreedomSurvive.Land.Permissions.Inspection(player, location.getWorld().getName() , location.getBlockX() , location.getBlockY() , location.getBlockZ() , "Permissions_Interact_Spawner")) {
                    // 有

                    switch (event.getRawSlot()) {
                        case 0:
                            Value.setHelmet(ID, null);
                            open(player, ID);
                            break;
                        case 1:
                            Value.setHelmet(ID, Material.LEATHER_HELMET);
                            open(player, ID);
                            break;
                        case 2:
                            Value.setHelmet(ID, Material.CHAINMAIL_HELMET);
                            open(player, ID);
                            break;
                        case 3:
                            Value.setHelmet(ID, Material.IRON_HELMET);
                            open(player, ID);
                            break;
                        case 4:
                            Value.setHelmet(ID, Material.GOLDEN_HELMET);
                            open(player, ID);
                            break;
                        case 5:
                            Value.setHelmet(ID, Material.DIAMOND_HELMET);
                            open(player, ID);
                            break;
                        case 6:
                            Value.setHelmet(ID, Material.TURTLE_HELMET);
                            open(player, ID);
                            break;
                        case 16:
                            open(player, ID);
                            break;
                        case 17:
                            player.closeInventory();
                            break;
                    }
                }




            } else if (event.getView().getTitle().equalsIgnoreCase("§z選擇怪物胸甲類型")) {
                // 是沒錯

                event.setCancelled(true);

                if ( event.getClickedInventory() == null ) { return; }
                if ( event.getClickedInventory().getItem(9) == null ) { return; }
                if ( event.getClickedInventory().getItem(9).getItemMeta() == null ) { return; }
                if ( event.getClickedInventory().getItem(9).getItemMeta().getCustomTagContainer().getCustomTag(id_key,ItemTagType.STRING) == null ) { return; }
                String ID = event.getClickedInventory().getItem(9).getItemMeta().getCustomTagContainer().getCustomTag(id_key,ItemTagType.STRING);

                Location location = Data.GetLocation(ID);
                if ( location == null ) { return; }
                // 檢查是否有權限
                if (SuperFreedomSurvive.Land.Permissions.Inspection(player, location.getWorld().getName() , location.getBlockX() , location.getBlockY() , location.getBlockZ() , "Permissions_Interact_Spawner")) {
                    // 有

                    switch (event.getRawSlot()) {
                        case 0:
                            Value.setChestplate(ID, null);
                            open(player, ID);
                            break;
                        case 1:
                            Value.setChestplate(ID, Material.LEATHER_CHESTPLATE);
                            open(player, ID);
                            break;
                        case 2:
                            Value.setChestplate(ID, Material.CHAINMAIL_CHESTPLATE);
                            open(player, ID);
                            break;
                        case 3:
                            Value.setChestplate(ID, Material.IRON_CHESTPLATE);
                            open(player, ID);
                            break;
                        case 4:
                            Value.setChestplate(ID, Material.GOLDEN_CHESTPLATE);
                            open(player, ID);
                            break;
                        case 5:
                            Value.setChestplate(ID, Material.DIAMOND_CHESTPLATE);
                            open(player, ID);
                            break;
                        case 16:
                            open(player, ID);
                            break;
                        case 17:
                            player.closeInventory();
                            break;
                    }
                }




            } else if (event.getView().getTitle().equalsIgnoreCase("§z選擇怪物護腿類型")) {
                // 是沒錯

                event.setCancelled(true);

                if ( event.getClickedInventory() == null ) { return; }
                if ( event.getClickedInventory().getItem(9) == null ) { return; }
                if ( event.getClickedInventory().getItem(9).getItemMeta() == null ) { return; }
                if ( event.getClickedInventory().getItem(9).getItemMeta().getCustomTagContainer().getCustomTag(id_key,ItemTagType.STRING) == null ) { return; }
                String ID = event.getClickedInventory().getItem(9).getItemMeta().getCustomTagContainer().getCustomTag(id_key,ItemTagType.STRING);

                Location location = Data.GetLocation(ID);
                if ( location == null ) { return; }
                // 檢查是否有權限
                if (SuperFreedomSurvive.Land.Permissions.Inspection(player, location.getWorld().getName() , location.getBlockX() , location.getBlockY() , location.getBlockZ() , "Permissions_Interact_Spawner")) {
                    // 有

                    switch (event.getRawSlot()) {
                        case 0:
                            Value.setLeggings(ID, null);
                            open(player, ID);
                            break;
                        case 1:
                            Value.setLeggings(ID, Material.LEATHER_LEGGINGS);
                            open(player, ID);
                            break;
                        case 2:
                            Value.setLeggings(ID, Material.CHAINMAIL_LEGGINGS);
                            open(player, ID);
                            break;
                        case 3:
                            Value.setLeggings(ID, Material.IRON_LEGGINGS);
                            open(player, ID);
                            break;
                        case 4:
                            Value.setLeggings(ID, Material.GOLDEN_LEGGINGS);
                            open(player, ID);
                            break;
                        case 5:
                            Value.setLeggings(ID, Material.DIAMOND_LEGGINGS);
                            open(player, ID);
                            break;
                        case 16:
                            open(player, ID);
                            break;
                        case 17:
                            player.closeInventory();
                            break;
                    }
                }




            } else if (event.getView().getTitle().equalsIgnoreCase("§z選擇怪物靴子類型")) {
                // 是沒錯

                event.setCancelled(true);

                if ( event.getClickedInventory() == null ) { return; }
                if ( event.getClickedInventory().getItem(9) == null ) { return; }
                if ( event.getClickedInventory().getItem(9).getItemMeta() == null ) { return; }
                if ( event.getClickedInventory().getItem(9).getItemMeta().getCustomTagContainer().getCustomTag(id_key,ItemTagType.STRING) == null ) { return; }
                String ID = event.getClickedInventory().getItem(9).getItemMeta().getCustomTagContainer().getCustomTag(id_key,ItemTagType.STRING);

                Location location = Data.GetLocation(ID);
                if ( location == null ) { return; }
                // 檢查是否有權限
                if (SuperFreedomSurvive.Land.Permissions.Inspection(player, location.getWorld().getName() , location.getBlockX() , location.getBlockY() , location.getBlockZ() , "Permissions_Interact_Spawner")) {
                    // 有

                    switch (event.getRawSlot()) {
                        case 0:
                            Value.setBoots(ID, null);
                            open(player, ID);
                            break;
                        case 1:
                            Value.setBoots(ID, Material.LEATHER_BOOTS);
                            open(player, ID);
                            break;
                        case 2:
                            Value.setBoots(ID, Material.CHAINMAIL_BOOTS);
                            open(player, ID);
                            break;
                        case 3:
                            Value.setBoots(ID, Material.IRON_BOOTS);
                            open(player, ID);
                            break;
                        case 4:
                            Value.setBoots(ID, Material.GOLDEN_BOOTS);
                            open(player, ID);
                            break;
                        case 5:
                            Value.setBoots(ID, Material.DIAMOND_BOOTS);
                            open(player, ID);
                            break;
                        case 16:
                            open(player, ID);
                            break;
                        case 17:
                            player.closeInventory();
                            break;
                    }
                }
            }
        }
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    static public void Menu_OffHand(Player player,String ID) {

        Location location = Data.GetLocation(ID);
        if ( location == null ) { return; }
        // 檢查是否有權限
        if (SuperFreedomSurvive.Land.Permissions.Inspection(player, location.getWorld().getName() , location.getBlockX() , location.getBlockY() , location.getBlockZ() , "Permissions_Interact_Spawner")) {
            // 有

            // 開啟編輯介面
            Inventory inv = Bukkit.createInventory(null, 45, "§z選擇怪物副手物品");

            inv.setItem(0, getReplaceItemStack(ID,Material.GHAST_TEAR ,"§e無"));
            inv.setItem(1, getReplaceItemStack(ID,Material.WOODEN_AXE,null));
            inv.setItem(2, getReplaceItemStack(ID,Material.WOODEN_HOE,null));
            inv.setItem(3, getReplaceItemStack(ID,Material.WOODEN_PICKAXE,null));
            inv.setItem(4, getReplaceItemStack(ID,Material.WOODEN_SHOVEL,null));
            inv.setItem(5, getReplaceItemStack(ID,Material.WOODEN_SWORD,null));
            inv.setItem(6, getReplaceItemStack(ID,Material.STONE_AXE,null));
            inv.setItem(7, getReplaceItemStack(ID,Material.STONE_HOE,null));
            inv.setItem(8, getReplaceItemStack(ID,Material.STONE_PICKAXE,null));
            inv.setItem(9, getReplaceItemStack(ID,Material.STONE_SHOVEL,null));
            inv.setItem(10, getReplaceItemStack(ID,Material.STONE_SWORD,null));
            inv.setItem(11, getReplaceItemStack(ID,Material.IRON_AXE,null));
            inv.setItem(12, getReplaceItemStack(ID,Material.IRON_HOE,null));
            inv.setItem(13, getReplaceItemStack(ID,Material.IRON_PICKAXE,null));
            inv.setItem(14, getReplaceItemStack(ID,Material.IRON_SHOVEL,null));
            inv.setItem(15, getReplaceItemStack(ID,Material.IRON_SWORD,null));
            inv.setItem(16, getReplaceItemStack(ID,Material.GOLDEN_AXE,null));
            inv.setItem(17, getReplaceItemStack(ID,Material.GOLDEN_HOE,null));
            inv.setItem(18, getReplaceItemStack(ID,Material.GOLDEN_PICKAXE,null));
            inv.setItem(19, getReplaceItemStack(ID,Material.GOLDEN_SHOVEL,null));
            inv.setItem(20, getReplaceItemStack(ID,Material.GOLDEN_SWORD,null));
            inv.setItem(21, getReplaceItemStack(ID,Material.DIAMOND_AXE,null));
            inv.setItem(22, getReplaceItemStack(ID,Material.DIAMOND_HOE,null));
            inv.setItem(23, getReplaceItemStack(ID,Material.DIAMOND_PICKAXE,null));
            inv.setItem(24, getReplaceItemStack(ID,Material.DIAMOND_SHOVEL,null));
            inv.setItem(25, getReplaceItemStack(ID,Material.DIAMOND_SWORD,null));
            inv.setItem(26, getReplaceItemStack(ID,Material.SHIELD,null));
            inv.setItem(27, getReplaceItemStack(ID,Material.TRIDENT,null));
            inv.setItem(28, getReplaceItemStack(ID,Material.TOTEM_OF_UNDYING,null));
            inv.setItem(29, getReplaceItemStack(ID,Material.BOW,null));
            inv.setItem(30, getReplaceItemStack(ID,Material.ARROW,null));


            // --------------------------------------     36     --------------------------------------
            inv.setItem(36, getIDItemStack(ID));

            // --------------------------------------     43     --------------------------------------
            inv.setItem(43, SuperFreedomSurvive.Menu.Open.PreviousPage());

            // --------------------------------------     44     --------------------------------------
            inv.setItem(44, SuperFreedomSurvive.Menu.Open.TurnOff());


            player.openInventory(inv);
        }
    }
    static public void Menu_MainHand(Player player,String ID) {

        Location location = Data.GetLocation(ID);
        if ( location == null ) { return; }
        // 檢查是否有權限
        if (SuperFreedomSurvive.Land.Permissions.Inspection(player, location.getWorld().getName() , location.getBlockX() , location.getBlockY() , location.getBlockZ() , "Permissions_Interact_Spawner")) {
            // 有

            // 開啟編輯介面
            Inventory inv = Bukkit.createInventory(null, 45, "§z選擇怪物主手物品");

            inv.setItem(0, getReplaceItemStack(ID,Material.GHAST_TEAR ,"§e無"));
            inv.setItem(1, getReplaceItemStack(ID,Material.WOODEN_AXE,null));
            inv.setItem(2, getReplaceItemStack(ID,Material.WOODEN_HOE,null));
            inv.setItem(3, getReplaceItemStack(ID,Material.WOODEN_PICKAXE,null));
            inv.setItem(4, getReplaceItemStack(ID,Material.WOODEN_SHOVEL,null));
            inv.setItem(5, getReplaceItemStack(ID,Material.WOODEN_SWORD,null));
            inv.setItem(6, getReplaceItemStack(ID,Material.STONE_AXE,null));
            inv.setItem(7, getReplaceItemStack(ID,Material.STONE_HOE,null));
            inv.setItem(8, getReplaceItemStack(ID,Material.STONE_PICKAXE,null));
            inv.setItem(9, getReplaceItemStack(ID,Material.STONE_SHOVEL,null));
            inv.setItem(10, getReplaceItemStack(ID,Material.STONE_SWORD,null));
            inv.setItem(11, getReplaceItemStack(ID,Material.IRON_AXE,null));
            inv.setItem(12, getReplaceItemStack(ID,Material.IRON_HOE,null));
            inv.setItem(13, getReplaceItemStack(ID,Material.IRON_PICKAXE,null));
            inv.setItem(14, getReplaceItemStack(ID,Material.IRON_SHOVEL,null));
            inv.setItem(15, getReplaceItemStack(ID,Material.IRON_SWORD,null));
            inv.setItem(16, getReplaceItemStack(ID,Material.GOLDEN_AXE,null));
            inv.setItem(17, getReplaceItemStack(ID,Material.GOLDEN_HOE,null));
            inv.setItem(18, getReplaceItemStack(ID,Material.GOLDEN_PICKAXE,null));
            inv.setItem(19, getReplaceItemStack(ID,Material.GOLDEN_SHOVEL,null));
            inv.setItem(20, getReplaceItemStack(ID,Material.GOLDEN_SWORD,null));
            inv.setItem(21, getReplaceItemStack(ID,Material.DIAMOND_AXE,null));
            inv.setItem(22, getReplaceItemStack(ID,Material.DIAMOND_HOE,null));
            inv.setItem(23, getReplaceItemStack(ID,Material.DIAMOND_PICKAXE,null));
            inv.setItem(24, getReplaceItemStack(ID,Material.DIAMOND_SHOVEL,null));
            inv.setItem(25, getReplaceItemStack(ID,Material.DIAMOND_SWORD,null));
            inv.setItem(26, getReplaceItemStack(ID,Material.SHIELD,null));
            inv.setItem(27, getReplaceItemStack(ID,Material.TRIDENT,null));
            inv.setItem(28, getReplaceItemStack(ID,Material.TOTEM_OF_UNDYING,null));
            inv.setItem(29, getReplaceItemStack(ID,Material.BOW,null));
            inv.setItem(30, getReplaceItemStack(ID,Material.ARROW,null));


            // --------------------------------------     36     --------------------------------------
            inv.setItem(36, getIDItemStack(ID));

            // --------------------------------------     43     --------------------------------------
            inv.setItem(43, SuperFreedomSurvive.Menu.Open.PreviousPage());

            // --------------------------------------     44     --------------------------------------
            inv.setItem(44, SuperFreedomSurvive.Menu.Open.TurnOff());


            player.openInventory(inv);
        }
    }


    // 阻止玩家拿走物品
    @EventHandler
    final public void onInventoryClickEvent___(InventoryClickEvent event) {
        // 判斷是否為玩家

        if (event.getWhoClicked() instanceof Player) {
            // 是玩家
            Player player = (Player) event.getWhoClicked();

            // 檢查容器名稱
            if (event.getView().getTitle().equalsIgnoreCase("§z選擇怪物副手物品")) {
                // 是沒錯

                event.setCancelled(true);

                if (event.getClickedInventory() == null) {
                    return;
                }
                if (event.getClickedInventory().getItem(36) == null) {
                    return;
                }
                if (event.getClickedInventory().getItem(36).getItemMeta() == null) {
                    return;
                }
                if (event.getClickedInventory().getItem(36).getItemMeta().getCustomTagContainer().getCustomTag(id_key, ItemTagType.STRING) == null) {
                    return;
                }
                String ID = event.getClickedInventory().getItem(36).getItemMeta().getCustomTagContainer().getCustomTag(id_key, ItemTagType.STRING);

                Location location = Data.GetLocation(ID);
                if (location == null) {
                    return;
                }
                // 檢查是否有權限
                if (SuperFreedomSurvive.Land.Permissions.Inspection(player, location.getWorld().getName(), location.getBlockX(), location.getBlockY(), location.getBlockZ(), "Permissions_Interact_Spawner")) {
                    // 有

                    switch (event.getRawSlot()) {
                        case 0:
                        case 1:
                        case 2:
                        case 3:
                        case 4:
                        case 5:
                        case 6:
                        case 7:
                        case 8:
                        case 9:
                        case 10:
                        case 11:
                        case 12:
                        case 13:
                        case 14:
                        case 15:
                        case 16:
                        case 17:
                        case 18:
                        case 19:
                        case 20:
                        case 21:
                        case 22:
                        case 23:
                        case 24:
                        case 25:
                        case 26:
                        case 27:
                        case 28:
                        case 29:
                        case 30:
                            Value.setOffHand( ID , Value.conversionHandItem(event.getRawSlot()) );
                            open(player, ID);
                            break;
                        case 43:
                            open(player, ID);
                            break;
                        case 44:
                            player.closeInventory();
                            break;
                    }
                }





            } else if (event.getView().getTitle().equalsIgnoreCase("§z選擇怪物主手物品")) {
                // 是沒錯

                event.setCancelled(true);

                if (event.getClickedInventory() == null) {
                    return;
                }
                if (event.getClickedInventory().getItem(36) == null) {
                    return;
                }
                if (event.getClickedInventory().getItem(36).getItemMeta() == null) {
                    return;
                }
                if (event.getClickedInventory().getItem(36).getItemMeta().getCustomTagContainer().getCustomTag(id_key, ItemTagType.STRING) == null) {
                    return;
                }
                String ID = event.getClickedInventory().getItem(36).getItemMeta().getCustomTagContainer().getCustomTag(id_key, ItemTagType.STRING);

                Location location = Data.GetLocation(ID);
                if (location == null) {
                    return;
                }
                // 檢查是否有權限
                if (SuperFreedomSurvive.Land.Permissions.Inspection(player, location.getWorld().getName(), location.getBlockX(), location.getBlockY(), location.getBlockZ(), "Permissions_Interact_Spawner")) {
                    // 有

                    switch (event.getRawSlot()) {
                        case 0:
                        case 1:
                        case 2:
                        case 3:
                        case 4:
                        case 5:
                        case 6:
                        case 7:
                        case 8:
                        case 9:
                        case 10:
                        case 11:
                        case 12:
                        case 13:
                        case 14:
                        case 15:
                        case 16:
                        case 17:
                        case 18:
                        case 19:
                        case 20:
                        case 21:
                        case 22:
                        case 23:
                        case 24:
                        case 25:
                        case 26:
                        case 27:
                        case 28:
                        case 29:
                        case 30:
                            Value.setMainHand( ID , Value.conversionHandItem(event.getRawSlot()) );
                            open(player, ID);
                            break;
                        case 43:
                            open(player, ID);
                            break;
                        case 44:
                            player.closeInventory();
                            break;
                    }
                }
            }
        }
    }





    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////








    static public ItemStack getReplaceItemStack(String ID,Material material,String name) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (name != null) meta.setDisplayName(name);
        meta.setLore(Arrays.asList(
                ("§r§f (點擊) 更換")
        ));
        meta.getCustomTagContainer().setCustomTag(id_key, ItemTagType.STRING, ID); // 創建特殊類型資料
        // 寫入資料
        item.setItemMeta(meta);
        // 設置完成
        return item;
    }




    static public ItemStack getIDItemStack(String ID) {
        ItemStack item = new ItemStack(Material.MAP, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§bID:" + ID);
        meta.getCustomTagContainer().setCustomTag(id_key, ItemTagType.STRING, ID); // 創建特殊類型資料
        // 寫入資料
        item.setItemMeta(meta);
        // 設置完成
        return item;
    }
}
