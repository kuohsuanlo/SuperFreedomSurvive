package SuperFreedomSurvive.Definition.Item;

import SuperFreedomSurvive.Definition.UseItem;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.*;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.tags.CustomItemTagContainer;
import org.bukkit.inventory.meta.tags.ItemTagType;

import java.util.Arrays;
import java.util.Date;

final public class storag_animal_egg {

    // 定義新資料值
    static org.bukkit.NamespacedKey item_key = UseItem.item_key; // 創建特殊類型資料
    static org.bukkit.NamespacedKey type = UseItem.type_key;
    static org.bukkit.NamespacedKey durable = UseItem.durable_key;
    static org.bukkit.NamespacedKey random = UseItem.random_key;
    static org.bukkit.NamespacedKey time = UseItem.time_key;
    static Material use_item = Material.FLINT;


    static final public ItemStack Get() {
        return Get(20);
    }

    // 動物收納蛋
    static final public ItemStack Get(int loss) {
        ItemStack item = new ItemStack(use_item, 1);
        ItemMeta meta = item.getItemMeta();
        //meta.addItemFlags( ItemFlag.HIDE_ENCHANTS ); // 隱藏附魔屬性
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES); // 顯示耐久
        //meta.addEnchant(Enchantment.ARROW_INFINITE , 1 , false ); // 設定附魔類型

        meta.getCustomTagContainer().setCustomTag(item_key, ItemTagType.STRING, "storag_animal_egg"); // 登入key
        // 耐久度 20
        meta.getCustomTagContainer().setCustomTag(durable, ItemTagType.INTEGER, loss); // 耐久度
        meta.getCustomTagContainer().setCustomTag(type, ItemTagType.STRING, ""); // 創建特殊類型資料

        String text = "";
        String[] RegSNContent = {
                "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
                "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
                "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
                "_", "+", "-", ".", "~", "/", "=", "[", "]", "(", ")", "<", ">"
        };
        for (int i = 0; i < 10; i++) text += RegSNContent[(int) (Math.random() * RegSNContent.length)];
        meta.getCustomTagContainer().setCustomTag(random, ItemTagType.STRING, text); // 確保不可疊加

        meta.setDisplayName("§e動物收納蛋");
        meta.setLore(Arrays.asList(
                ("§r§f耐久度:" + meta.getCustomTagContainer().getCustomTag(durable, ItemTagType.INTEGER) + " / 20"),
                ("§r§f可將動物重製回生怪蛋的狀態"),
                ("§r§f生怪磚生出的動物不可使用")
        ));
        item.setItemMeta(meta); // 寫入資料
        return item; // 設置完成
    }


    // 新增配方
    static final public void Composite() {

        ShapedRecipe recipe = new ShapedRecipe(SuperFreedomSurvive.SYSTEM.NamespacedKey.getKey("storag_animal_egg"), Get());
        recipe.shape(
                "EEE",
                "EBE",
                "EEE"
        );
        recipe.setIngredient('E', Material.WHEAT);
        recipe.setIngredient('B', Material.MINECART);

        Bukkit.addRecipe(recipe);

    }


    // 合成預覽界面
    static final public void OpenComposite(Player player) {
        // 顯示容器
        Inventory inv = Bukkit.createInventory(null, 36, "§z合成方式");


        inv.setItem(2, new ItemStack(Material.WHEAT));
        inv.setItem(3, new ItemStack(Material.WHEAT));
        inv.setItem(4, new ItemStack(Material.WHEAT));

        inv.setItem(11, new ItemStack(Material.WHEAT));
        inv.setItem(12, new ItemStack(Material.MINECART));
        inv.setItem(13, new ItemStack(Material.WHEAT));

        inv.setItem(20, new ItemStack(Material.WHEAT));
        inv.setItem(21, new ItemStack(Material.WHEAT));
        inv.setItem(22, new ItemStack(Material.WHEAT));

        inv.setItem(15, Get());


        inv.setItem(34, SuperFreedomSurvive.Menu.Open.PreviousPage());
        inv.setItem(35, SuperFreedomSurvive.Menu.Open.TurnOff());
        // 寫入到容器頁面
        player.openInventory(inv);

    }


    // 使用
    // 放置 / 點擊方塊時
    static final public void Use(PlayerInteractEvent event) {

        event.setCancelled(true); // 禁止放置

        if ( event.getClickedBlock() == null ) { return; }

        ItemStack player_item = event.getPlayer().getInventory().getItemInMainHand();
        ItemMeta player_item_meta = player_item.getItemMeta();
        CustomItemTagContainer player_item_tag = player_item_meta.getCustomTagContainer();

        // 是否是收納蛋類型 ( 存在動物數據 )
        if (player_item_tag.getCustomTag(item_key, ItemTagType.STRING).equals("storag_have_animal_egg")) {
            // 檢查是否有權限
            if (SuperFreedomSurvive.Land.Permissions.Inspection(event.getPlayer(), event.getClickedBlock().getLocation(), "Permissions_Entity_Egg_Place")) {
                // 有


                // 檢查經過時間
                if (player_item_tag.getCustomTag(time, ItemTagType.LONG) == null) {
                    return;
                }
                if (player_item_tag.getCustomTag(time, ItemTagType.LONG) > new Date().getTime() - 500) {
                    return;
                }


                if (event.getClickedBlock().getType() == Material.SPAWNER) {

                    if (SuperFreedomSurvive.Block.Data.Get(event.getClickedBlock().getLocation()).equals("spawner_block")) {
                        // 點擊的是生怪磚
                        // 空了 爆掉
                        event.getPlayer().getInventory().setItemInMainHand(null);
                        event.getPlayer().playSound(event.getPlayer().getLocation(), "minecraft:entity.item.break", 1, 1);

                        CreatureSpawner spawner = (CreatureSpawner) event.getClickedBlock().getState();
                        spawner.setSpawnedType(EntityType.valueOf(player_item_tag.getCustomTag(type, ItemTagType.STRING)));
                        spawner.update();

                    }
                } else {

                    // 點擊的是一般方塊

                    // 檢查註冊表中是否有
                    boolean have = false;
                    switch (player_item_tag.getCustomTag(type, ItemTagType.STRING)) {
                        case "RABBIT":
                            have = true;
                            break;

                        case "POLAR_BEAR":
                            have = true;
                            break;

                        case "WOLF":
                            have = true;
                            break;

                        case "CHICKEN":
                            have = true;
                            break;

                        case "COW":
                            have = true;
                            break;

                        case "HORSE":
                            have = true;
                            break;

                        case "PARROT":
                            have = true;
                            break;

                        case "OCELOT":
                            have = true;
                            break;

                        case "PIG":
                            have = true;
                            break;

                        case "SHEEP":
                            have = true;
                            break;

                        case "MUSHROOM_COW":
                            have = true;
                            break;

                        case "DOLPHIN":
                            have = true;
                            break;
                    }


                    // 是否在剛剛有更改資料
                    if (have) {
                        // 有

                        // 使用點擊的面 計算出要生成的位置
                        Location location;
                        if (event.getBlockFace() == BlockFace.DOWN) {
                            location = new Location(event.getClickedBlock().getLocation().getWorld(), event.getClickedBlock().getLocation().getX() + 0.5, event.getClickedBlock().getLocation().getY() - 1, event.getClickedBlock().getLocation().getZ() + 0.5);
                        } else if (event.getBlockFace() == BlockFace.UP) {
                            location = new Location(event.getClickedBlock().getLocation().getWorld(), event.getClickedBlock().getLocation().getX() + 0.5, event.getClickedBlock().getLocation().getY() + 1, event.getClickedBlock().getLocation().getZ() + 0.5);
                        } else if (event.getBlockFace() == BlockFace.NORTH) {
                            location = new Location(event.getClickedBlock().getLocation().getWorld(), event.getClickedBlock().getLocation().getX() + 0.5, event.getClickedBlock().getLocation().getY(), event.getClickedBlock().getLocation().getZ() + 0.5 - 1);
                        } else if (event.getBlockFace() == BlockFace.SOUTH) {
                            location = new Location(event.getClickedBlock().getLocation().getWorld(), event.getClickedBlock().getLocation().getX() + 0.5, event.getClickedBlock().getLocation().getY(), event.getClickedBlock().getLocation().getZ() + 0.5 + 1);
                        } else if (event.getBlockFace() == BlockFace.EAST) {
                            location = new Location(event.getClickedBlock().getLocation().getWorld(), event.getClickedBlock().getLocation().getX() + 0.5 + 1, event.getClickedBlock().getLocation().getY(), event.getClickedBlock().getLocation().getZ() + 0.5);
                        } else if (event.getBlockFace() == BlockFace.WEST) {
                            location = new Location(event.getClickedBlock().getLocation().getWorld(), event.getClickedBlock().getLocation().getX() + 0.5 - 1, event.getClickedBlock().getLocation().getY(), event.getClickedBlock().getLocation().getZ() + 0.5);
                        } else {
                            location = new Location(event.getClickedBlock().getLocation().getWorld(), event.getClickedBlock().getLocation().getX(), event.getClickedBlock().getLocation().getY(), event.getClickedBlock().getLocation().getZ());
                        }


                        // 召喚動物
                        LivingEntity living_entity = (LivingEntity) event.getPlayer().getWorld().spawnEntity(location, EntityType.valueOf(player_item_tag.getCustomTag(type, ItemTagType.STRING)));
                        living_entity.setHealth(2.0); // 更改生命
                        living_entity.setCustomName(""); // 更改顯示名稱

                        // 是羊
                        if (living_entity instanceof Sheep) {
                            ((Sheep) living_entity).setSheared(true);
                        }

                        // 是否有資料
                        //if (  player_item_tag.getCustomTag( data , ItemTagType.STRING ) != null ) {
                        //    NBTEntity nbt_entity = new NBTEntity(living_entity);
                        //}


                        // 耐久計算
                        if (player_item_tag.getCustomTag(durable, ItemTagType.INTEGER) - 1 <= 0) {
                            // 空了 爆掉
                            event.getPlayer().getInventory().setItemInMainHand(null);
                            event.getPlayer().playSound(event.getPlayer().getLocation(), "minecraft:entity.item.break", 1, 1);

                        } else {
                            // 還有耐久

                                /*
                                ItemStack new_item = new ItemStack(use_item);
                                ItemMeta new_item_meta = new_item.getItemMeta();
                                CustomItemTagContainer new_item_tag = new_item_meta.getCustomTagContainer();


                                new_item_tag.setCustomTag(item_key, ItemTagType.STRING, "storag_animal_egg"); // 登入key
                                new_item_tag.setCustomTag(type, ItemTagType.STRING, ""); // 為空
                                new_item_tag.setCustomTag(durable, ItemTagType.INTEGER, player_item_tag.getCustomTag(durable, ItemTagType.INTEGER) - 1); // 耐久 - 1
                                new_item_tag.setCustomTag(random, ItemTagType.STRING, player_item_tag.getCustomTag(random, ItemTagType.STRING)); // 繼承原先的random

                                new_item_meta.setDisplayName("§e動物收納蛋");

                                new_item_meta.setLore(Arrays.asList(
                                        ("§r§f耐久度:" + (player_item_tag.getCustomTag(durable, ItemTagType.INTEGER) - 1) + " / 20"),
                                        ("§r§f可將動物重製回生怪蛋的狀態"),
                                        ("§r§f生怪磚生出的動物不可使用")
                                ));

                                new_item.setItemMeta(new_item_meta); // 寫入資料

                                */
                            event.getPlayer().getInventory().setItemInMainHand(Get(player_item_tag.getCustomTag(durable, ItemTagType.INTEGER) - 1)); // 設置完成

                            event.getPlayer().playSound(event.getPlayer().getLocation(), "minecraft:item.chorus_fruit.teleport", 1, 1); // 播放音效


                        }
                    }
                }
            } else {
                // 沒有
                SuperFreedomSurvive.Land.Display.Message(event.getPlayer(), "§c領地禁止 收納蛋放出");
            }
        }
    }


    // 點擊實體時
    static final public void Use(PlayerInteractEntityEvent event) {

        event.setCancelled(true); // 禁止改動資料

        ItemStack player_item = event.getPlayer().getInventory().getItemInMainHand();
        ItemMeta player_item_meta = player_item.getItemMeta();
        CustomItemTagContainer player_item_tag = player_item_meta.getCustomTagContainer();

        // 是否是收納蛋類型 ( 不存在動物數據 )
        if (player_item_tag.getCustomTag(item_key, ItemTagType.STRING).equals("storag_animal_egg")) {

            // 不能存在類型
            if (player_item_tag.getCustomTag(type, ItemTagType.STRING).equals("")) {
                // 檢查是否有權限
                if (SuperFreedomSurvive.Land.Permissions.Inspection(event.getPlayer(), event.getRightClicked().getLocation(), "Permissions_Entity_Egg_Storag")) {
                    // 有

                    if (event.getRightClicked() instanceof Ageable) {

                    } else {
                        return;
                    }

                    // 是否有不可收納標籤 (海豚除外)
                    if (event.getRightClicked().getType().isAlive() && event.getRightClicked().getType() != EntityType.DOLPHIN) {
                        if (((Ageable) event.getRightClicked()).getAgeLock()) {
                            return;
                        }
                    }

                    // 開始收納動物 是否在清單內
                    boolean have = false;
                    switch (event.getRightClicked().getType()) {
                        case RABBIT:
                            //兔子 RABBIT_SPAWN_EGG
                            player_item.setType(Material.RABBIT_SPAWN_EGG);
                            player_item_tag.removeCustomTag(type); // 移除type
                            player_item_tag.setCustomTag(type, ItemTagType.STRING, "RABBIT");
                            player_item_meta.setDisplayName("§e兔子收納蛋");
                            have = true;
                            break;

                        case POLAR_BEAR:
                            //北極熊 POLAR_BEAR_SPAWN_EGG
                            player_item.setType(Material.POLAR_BEAR_SPAWN_EGG);
                            player_item_tag.removeCustomTag(type); // 移除type
                            player_item_tag.setCustomTag(type, ItemTagType.STRING, "POLAR_BEAR");
                            player_item_meta.setDisplayName("§e北極熊收納蛋");
                            have = true;
                            break;

                        case WOLF:
                            //狼 WOLF_SPAWN_EGG
                            player_item.setType(Material.WOLF_SPAWN_EGG);
                            player_item_tag.removeCustomTag(type); // 移除type
                            player_item_tag.setCustomTag(type, ItemTagType.STRING, "WOLF");
                            player_item_meta.setDisplayName("§e狼收納蛋");
                            have = true;
                            break;

                        case CHICKEN:
                            //雞 CHICKEN_SPAWN_EGG
                            player_item.setType(Material.CHICKEN_SPAWN_EGG);
                            player_item_tag.removeCustomTag(type); // 移除type
                            player_item_tag.setCustomTag(type, ItemTagType.STRING, "CHICKEN");
                            player_item_meta.setDisplayName("§e雞收納蛋");
                            have = true;
                            break;

                        case COW:
                            //牛 COW_SPAWN_EGG
                            player_item.setType(Material.COW_SPAWN_EGG);
                            player_item_tag.removeCustomTag(type); // 移除type
                            player_item_tag.setCustomTag(type, ItemTagType.STRING, "COW");
                            player_item_meta.setDisplayName("§e牛收納蛋");
                            have = true;
                            break;

                        case HORSE:
                            //馬 HORSE_SPAWN_EGG
                            player_item.setType(Material.HORSE_SPAWN_EGG);
                            player_item_tag.removeCustomTag(type); // 移除type
                            player_item_tag.setCustomTag(type, ItemTagType.STRING, "HORSE");
                            player_item_meta.setDisplayName("§e馬收納蛋");
                            have = true;
                            break;

                        case PARROT:
                            //鸚鵡 PARROT_SPAWN_EGG
                            player_item.setType(Material.PARROT_SPAWN_EGG);
                            player_item_tag.removeCustomTag(type); // 移除type
                            player_item_tag.setCustomTag(type, ItemTagType.STRING, "PARROT");
                            player_item_meta.setDisplayName("§e鸚鵡收納蛋");
                            have = true;
                            break;

                        case OCELOT:
                            //山貓 OCELOT_SPAWN_EGG
                            player_item.setType(Material.OCELOT_SPAWN_EGG);
                            player_item_tag.removeCustomTag(type); // 移除type
                            player_item_tag.setCustomTag(type, ItemTagType.STRING, "OCELOT");
                            player_item_meta.setDisplayName("§e山貓收納蛋");
                            have = true;
                            break;

                        case PIG:
                            //豬 PIG_SPAWN_EGG
                            player_item.setType(Material.PIG_SPAWN_EGG);
                            player_item_tag.removeCustomTag(type); // 移除type
                            player_item_tag.setCustomTag(type, ItemTagType.STRING, "PIG");
                            player_item_meta.setDisplayName("§e豬收納蛋");
                            have = true;
                            break;

                        case SHEEP:
                            //洋 SHEEP_SPAWN_EGG
                            player_item.setType(Material.SHEEP_SPAWN_EGG);
                            player_item_tag.removeCustomTag(type); // 移除type
                            player_item_tag.setCustomTag(type, ItemTagType.STRING, "SHEEP");
                            player_item_meta.setDisplayName("§e羊收納蛋");
                            have = true;
                            break;

                        case MUSHROOM_COW:
                            //蘑菇牛 MOOSHROOM_SPAWN_EGG
                            player_item.setType(Material.MOOSHROOM_SPAWN_EGG);
                            player_item_tag.removeCustomTag(type); // 移除type
                            player_item_tag.setCustomTag(type, ItemTagType.STRING, "MUSHROOM_COW");
                            player_item_meta.setDisplayName("§e蘑菇牛收納蛋");
                            have = true;
                            break;

                        case DOLPHIN:
                            //海豚 DOLPHIN_SPAWN_EGG
                            player_item.setType(Material.DOLPHIN_SPAWN_EGG);
                            player_item_tag.removeCustomTag(type); // 移除type
                            player_item_tag.setCustomTag(type, ItemTagType.STRING, "DOLPHIN");
                            player_item_meta.setDisplayName("§e海豚收納蛋");
                            have = true;
                            break;
                    }

                    // 是否在剛剛有更改資料
                    if (have) {

                        event.getRightClicked().remove(); // 移除動物

                        //NBTEntity nbt_entity = new NBTEntity(event.getRightClicked());

                        player_item_tag.removeCustomTag(item_key); // 移除key
                        player_item_tag.setCustomTag(item_key, ItemTagType.STRING, "storag_have_animal_egg"); // 登入key
                        player_item_tag.setCustomTag(time, ItemTagType.LONG, new Date().getTime()); // 登入key
                        //player_item_tag.setCustomTag( data , ItemTagType.STRING , nbt_entity.asNBTString() ); // 存入資料

                        player_item.setItemMeta(player_item_meta); // 寫入資料

                        event.getPlayer().getInventory().setItemInMainHand(player_item); // 設置完成

                        event.getPlayer().playSound(event.getPlayer().getLocation(), "minecraft:item.chorus_fruit.teleport", 1, 1); // 播放音效
                    }

                } else {
                    // 沒有
                    SuperFreedomSurvive.Land.Display.Message(event.getPlayer(), "§c領地禁止 收納蛋收納");
                }
            }
        }
    }


    // 使用
    // 食物消耗
    static final public void Use(PlayerItemConsumeEvent event) {


    }

}
