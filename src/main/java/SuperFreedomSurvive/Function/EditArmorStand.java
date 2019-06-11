package SuperFreedomSurvive.Function;

import SuperFreedomSurvive.Definition.UseItem;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.tags.ItemTagType;
import org.bukkit.util.EulerAngle;

import java.util.Arrays;
import java.util.UUID;

final public class EditArmorStand implements Listener {
    // 編輯盔甲座

    // 蹲下 + 點擊盔甲座
    @EventHandler
    final public void onPlayerInteractAtEntityEvent(PlayerInteractAtEntityEvent event) {
        try {
            if (event.getRightClicked().getType() == EntityType.ARMOR_STAND) {
                if (event.isCancelled()) {

                } else {
                    // 檢查是否有權限
                    if (SuperFreedomSurvive.Land.Permissions.Inspection(event.getPlayer(), event.getRightClicked().getLocation(), "Permissions_Entity_ArmorStand")) {
                        // 有

                        ArmorStand armor_stand = (ArmorStand) event.getRightClicked();

                        // 是否為命名牌
                        if (event.getPlayer().getInventory().getItemInMainHand().getType() == Material.NAME_TAG) {
                            armor_stand.setCustomName(event.getPlayer().getInventory().getItemInMainHand().getI18NDisplayName());
                            armor_stand.setCustomNameVisible(true);
                        } else {
                            // 是否 蹲下 中
                            if (event.getPlayer().isSneaking()) {
                                menu(event.getPlayer(), armor_stand);
                                event.setCancelled(true);
                            }
                        }

                    } else {
                        // 沒有
                        SuperFreedomSurvive.Land.Display.Message(event.getPlayer(), "§c領地禁止 編輯盔甲座");
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    static final public org.bukkit.NamespacedKey uuid_kit = SuperFreedomSurvive.SYSTEM.NamespacedKey.getKey("UUID");


    final public static void menu(Player player, ArmorStand armor_stand) {
        // 顯示容器
        Inventory inv = Bukkit.createInventory(null, 54, "§z進階編輯盔甲座");

        ItemStack item;
        ItemMeta meta;


        // --------------------------------------     0     --------------------------------------
        item = new ItemStack(Material.SIGN);
        meta = item.getItemMeta();
        meta.setDisplayName("§e頭 | 身體 | 腿 | 腳");
        // 寫入資料
        item.setItemMeta(meta);
        // 設置完成
        inv.setItem(0, item);


        inv.setItem(1, armor_stand.getHelmet());
        inv.setItem(2, armor_stand.getChestplate());
        inv.setItem(3, armor_stand.getLeggings());
        inv.setItem(4, armor_stand.getBoots());
/*
        inv.setItem( 1 , armor_stand.getItem( EquipmentSlot.HEAD ) );
        inv.setItem( 2 , armor_stand.getItem( EquipmentSlot.CHEST ) );
        inv.setItem( 3 , armor_stand.getItem( EquipmentSlot.LEGS ) );
        inv.setItem( 4 , armor_stand.getItem( EquipmentSlot.FEET ) );
*/

        // --------------------------------------     5     --------------------------------------
        item = new ItemStack(Material.SIGN);
        meta = item.getItemMeta();
        meta.setDisplayName("§e主手物品(右手)");
        // 寫入資料
        item.setItemMeta(meta);
        // 設置完成
        inv.setItem(5, item);

        inv.setItem(6, armor_stand.getItem(EquipmentSlot.HAND));


        // --------------------------------------     7     --------------------------------------
        item = new ItemStack(Material.SIGN);
        meta = item.getItemMeta();
        meta.setDisplayName("§e副手物品(左手)");
        // 寫入資料
        item.setItemMeta(meta);
        // 設置完成
        inv.setItem(7, item);

        inv.setItem(8, armor_stand.getItem(EquipmentSlot.OFF_HAND));


        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        inv.setItem(9, getItemStack_Boolean(armor_stand.isSmall(), "縮小", Material.GHAST_TEAR));
        inv.setItem(10, getItemStack_Boolean(armor_stand.isVisible(), "不隱形", Material.GLASS));
        inv.setItem(11, getItemStack_Boolean(armor_stand.hasGravity(), "可被移動", Material.OAK_BOAT));
        inv.setItem(12, getItemStack_Boolean(armor_stand.isCustomNameVisible(), "顯示名稱", Material.NAME_TAG));
        inv.setItem(13, getItemStack_Boolean(armor_stand.hasArms(), "顯示手臂", Material.STICK));
        inv.setItem(14, getItemStack_Boolean(armor_stand.hasBasePlate(), "顯示底座", Material.SNOW));


        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        inv.setItem(18, getItemStack_Rotate("盔甲座 旋轉 Y 軸", Material.ARMOR_STAND));

        inv.setItem(20, getItemStack_Rotate("盔甲座 座標 X", Material.ARMOR_STAND));
        inv.setItem(21, getItemStack_Rotate("盔甲座 座標 Y", Material.ARMOR_STAND));
        inv.setItem(22, getItemStack_Rotate("盔甲座 座標 Z", Material.ARMOR_STAND));
        //inv.setItem(20 , getItemStack_Rotate( "盔甲座 旋轉 Z 軸" , Material.ARMOR_STAND ) );


        inv.setItem(27, getItemStack_Rotate("頭 旋轉 X 軸", Material.DIAMOND_HELMET));
        inv.setItem(28, getItemStack_Rotate("頭 旋轉 Y 軸", Material.DIAMOND_HELMET));
        inv.setItem(29, getItemStack_Rotate("頭 旋轉 Z 軸", Material.DIAMOND_HELMET));

        inv.setItem(30, getItemStack_Rotate("左手 旋轉 X 軸", Material.STICK));
        inv.setItem(31, getItemStack_Rotate("左手 旋轉 Y 軸", Material.STICK));
        inv.setItem(32, getItemStack_Rotate("左手 旋轉 Z 軸", Material.STICK));

        inv.setItem(33, getItemStack_Rotate("右手 旋轉 X 軸", Material.STICK));
        inv.setItem(34, getItemStack_Rotate("右手 旋轉 Y 軸", Material.STICK));
        inv.setItem(35, getItemStack_Rotate("右手 旋轉 Z 軸", Material.STICK));


        inv.setItem(36, getItemStack_Rotate("身體 旋轉 X 軸", Material.DIAMOND_CHESTPLATE));
        inv.setItem(37, getItemStack_Rotate("身體 旋轉 Y 軸", Material.DIAMOND_CHESTPLATE));
        inv.setItem(38, getItemStack_Rotate("身體 旋轉 Z 軸", Material.DIAMOND_CHESTPLATE));

        inv.setItem(39, getItemStack_Rotate("左腿 旋轉 X 軸", Material.DIAMOND_LEGGINGS));
        inv.setItem(40, getItemStack_Rotate("左腿 旋轉 Y 軸", Material.DIAMOND_LEGGINGS));
        inv.setItem(41, getItemStack_Rotate("左腿 旋轉 Z 軸", Material.DIAMOND_LEGGINGS));

        inv.setItem(42, getItemStack_Rotate("右腿 旋轉 X 軸", Material.DIAMOND_LEGGINGS));
        inv.setItem(43, getItemStack_Rotate("右腿 旋轉 Y 軸", Material.DIAMOND_LEGGINGS));
        inv.setItem(44, getItemStack_Rotate("右腿 旋轉 Z 軸", Material.DIAMOND_LEGGINGS));


        // --------------------------------------     45     --------------------------------------
        item = new ItemStack(Material.SIGN);
        meta = item.getItemMeta();
        meta.setDisplayName("§eUUID:" + armor_stand.getUniqueId().toString());
        meta.getCustomTagContainer().setCustomTag(uuid_kit, ItemTagType.STRING, armor_stand.getUniqueId().toString());
        // 寫入資料
        item.setItemMeta(meta);
        // 設置完成
        inv.setItem(45, item);


        // --------------------------------------     53     --------------------------------------
        inv.setItem(53, SuperFreedomSurvive.Menu.Open.TurnOff());

        // 寫入到容器頁面
        player.openInventory(inv);
    }

    // 快速給予 ItemStack 接口
    final public static ItemStack getItemStack_Boolean(Boolean status, String name, Material id) {
        ItemStack item = new ItemStack(id);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§r§e" + name);
        if (status) {
            // true
            meta.setLore(Arrays.asList(
                    ("§r§f§l   目前狀態 §a是"),
                    ("§r§f (點擊) 切換設定狀態")
            ));

        } else {
            // false
            meta.setLore(Arrays.asList(
                    ("§r§f§l   目前狀態 §c否"),
                    ("§r§f (點擊) 切換設定狀態")
            ));

        }

        // 寫入資料
        item.setItemMeta(meta);
        // 返回
        return item;
        // 設置完成
        //inv.setItem( 35 , item );
    }

    // 快速給予 ItemStack 接口
    final public static ItemStack getItemStack_Rotate(String name, Material id) {
        ItemStack item = new ItemStack(id);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§r§e" + name);
        meta.setLore(Arrays.asList(
                ("§r§f (左鍵) 增加"),
                ("§r§f (右鍵) 減少"),
                ("§r§f (Shift+點擊) 重置")
        ));

        // 寫入資料
        item.setItemMeta(meta);
        // 返回
        return item;
        // 設置完成
        //inv.setItem( 35 , item );
    }

    @EventHandler
    final public void onInventoryClickEvent(InventoryClickEvent event) {
        // 判斷是否為玩家

        if (event.getWhoClicked() instanceof Player) {
            // 是玩家
            Player player = (Player) event.getWhoClicked();

            // 檢查容器名稱
            if (event.getView().getTitle().equalsIgnoreCase("§z進階編輯盔甲座")) {
                // 是沒錯

                try {
                    Entity entity = Bukkit.getEntity(UUID.fromString(event.getInventory().getItem(45).getItemMeta().getCustomTagContainer().getCustomTag(uuid_kit, ItemTagType.STRING)));
                    if (entity == null) {
                        event.setCurrentItem(new ItemStack(Material.AIR));
                        event.setCursor(new ItemStack(Material.AIR));
                        player.closeInventory();
                        event.setCancelled(true);
                        return;
                    }

                    ArmorStand armor_stand = (ArmorStand) entity;

                    // 檢查是否有權限
                    if (SuperFreedomSurvive.Land.Permissions.Inspection(player, armor_stand.getLocation(), "Permissions_Entity_ArmorStand")) {
                        // 有
                    } else {
                        // 沒有
                        event.setCurrentItem(new ItemStack(Material.AIR));
                        event.setCursor(new ItemStack(Material.AIR));
                        player.closeInventory();
                        event.setCancelled(true);
                        SuperFreedomSurvive.Land.Display.Message(player, "§c領地禁止 編輯盔甲座");
                        return;
                    }

                    if (
                            event.getRawSlot() == 0 ||
                                    event.getRawSlot() == 5 ||
                                    event.getRawSlot() == 7
                    ) {
                        event.setCancelled(true);
                    }

                    if (event.getRawSlot() >= 9 && event.getRawSlot() < 54) {
                        event.setCancelled(true);
                    }

                    if (event.isShiftClick()) {
                        event.setCancelled(true);
                    }


                    //player.sendMessage("點擊"+event.getCurrentItem().getType().name());
                    //player.sendMessage("手持"+event.getCursor().getType().name());


/*
                    if ( armor_stand.getHelmet() == null ) {
                        armor_stand.setHelmet(new ItemStack(Material.AIR));
                    } else if ( armor_stand.getChestplate() == null ) {
                        armor_stand.setChestplate(new ItemStack(Material.AIR));
                    } else if ( armor_stand.getLeggings() == null ) {
                        armor_stand.setLeggings(new ItemStack(Material.AIR));
                    } else if ( armor_stand.getBoots() == null ) {
                        armor_stand.setBoots(new ItemStack(Material.AIR));
                    }
                    */


                    if (event.getRawSlot() == 1) {
                        // 頭
                        if (event.getCurrentItem().isSimilar(armor_stand.getHelmet())) {
                        } else if (event.getCurrentItem().getType() == Material.AIR && armor_stand.getHelmet().getType() == Material.AIR) {
                        } else {
                            event.setCursor(new ItemStack(Material.AIR));
                            menu(player, armor_stand);
                            event.setCancelled(true);
                            return;
                        }

                        if (event.getCurrentItem().isSimilar(armor_stand.getHelmet()) && event.getCursor().getType() == Material.AIR) {
                            if (event.getClick() == ClickType.LEFT) {
                                armor_stand.setHelmet(new ItemStack(Material.AIR));
                            } else if (event.getClick() == ClickType.RIGHT) {
                                armor_stand.setHelmet(armor_stand.getHelmet().asQuantity(armor_stand.getHelmet().getAmount() / 2));
                            }

                        } else if (armor_stand.getHelmet().isSimilar(event.getCurrentItem())) {
                            if (
                                    armor_stand.getHelmet().getItemMeta().getCustomTagContainer().getCustomTag(UseItem.random_key, ItemTagType.STRING) != null ||
                                            event.getCurrentItem().getItemMeta().getCustomTagContainer().getCustomTag(UseItem.random_key, ItemTagType.STRING) != null
                            ) {
                                armor_stand.setHelmet(event.getCursor());
                            } else {
                                if (event.getClick() == ClickType.LEFT) {
                                    armor_stand.setHelmet(armor_stand.getHelmet().add(event.getCursor().getAmount()));
                                } else if (event.getClick() == ClickType.RIGHT) {
                                    armor_stand.setHelmet(armor_stand.getHelmet().add());
                                }
                            }

                        } else if (armor_stand.getHelmet() == null || armor_stand.getHelmet().getType() == Material.AIR) {
                            if (event.getClick() == ClickType.LEFT) {
                                armor_stand.setHelmet(event.getCursor());
                            } else if (event.getClick() == ClickType.RIGHT) {
                                armor_stand.setHelmet(event.getCursor().asOne());
                            }
                        }

                    } else if (event.getRawSlot() == 2) {
                        // 身體
                        if (event.getCurrentItem().isSimilar(armor_stand.getChestplate())) {
                        } else if (event.getCurrentItem().getType() == Material.AIR && armor_stand.getChestplate().getType() == Material.AIR) {
                        } else {
                            event.setCursor(new ItemStack(Material.AIR));
                            menu(player, armor_stand);
                            event.setCancelled(true);
                            return;
                        }

                        if (event.getCurrentItem().isSimilar(armor_stand.getChestplate()) && event.getCursor().getType() == Material.AIR) {
                            if (event.getClick() == ClickType.LEFT) {
                                armor_stand.setChestplate(new ItemStack(Material.AIR));
                            } else if (event.getClick() == ClickType.RIGHT) {
                                armor_stand.setChestplate(armor_stand.getChestplate().asQuantity(armor_stand.getChestplate().getAmount() / 2));
                            }

                        } else if (armor_stand.getChestplate().isSimilar(event.getCurrentItem())) {
                            if (
                                    armor_stand.getChestplate().getItemMeta().getCustomTagContainer().getCustomTag(UseItem.random_key, ItemTagType.STRING) != null ||
                                            event.getCurrentItem().getItemMeta().getCustomTagContainer().getCustomTag(UseItem.random_key, ItemTagType.STRING) != null
                            ) {
                                armor_stand.setChestplate(event.getCursor());
                            } else {
                                if (event.getClick() == ClickType.LEFT) {
                                    armor_stand.setChestplate(armor_stand.getChestplate().add(event.getCursor().getAmount()));
                                } else if (event.getClick() == ClickType.RIGHT) {
                                    armor_stand.setChestplate(armor_stand.getChestplate().add());
                                }
                            }

                        } else if (armor_stand.getChestplate() == null || armor_stand.getChestplate().getType() == Material.AIR) {
                            if (event.getClick() == ClickType.LEFT) {
                                armor_stand.setChestplate(event.getCursor());
                            } else if (event.getClick() == ClickType.RIGHT) {
                                armor_stand.setChestplate(event.getCursor().asOne());
                            }
                        }

                    } else if (event.getRawSlot() == 3) {
                        // 腿
                        if (event.getCurrentItem().isSimilar(armor_stand.getLeggings())) {
                        } else if (event.getCurrentItem().getType() == Material.AIR && armor_stand.getLeggings().getType() == Material.AIR) {
                        } else {
                            event.setCursor(new ItemStack(Material.AIR));
                            menu(player, armor_stand);
                            event.setCancelled(true);
                            return;
                        }

                        if (event.getCurrentItem().isSimilar(armor_stand.getLeggings()) && event.getCursor().getType() == Material.AIR) {
                            if (event.getClick() == ClickType.LEFT) {
                                armor_stand.setLeggings(new ItemStack(Material.AIR));
                            } else if (event.getClick() == ClickType.RIGHT) {
                                armor_stand.setLeggings(armor_stand.getLeggings().asQuantity(armor_stand.getLeggings().getAmount() / 2));
                            }

                        } else if (armor_stand.getLeggings().isSimilar(event.getCurrentItem())) {
                            if (
                                    armor_stand.getLeggings().getItemMeta().getCustomTagContainer().getCustomTag(UseItem.random_key, ItemTagType.STRING) != null ||
                                            event.getCurrentItem().getItemMeta().getCustomTagContainer().getCustomTag(UseItem.random_key, ItemTagType.STRING) != null
                            ) {
                                armor_stand.setLeggings(event.getCursor());
                            } else {
                                if (event.getClick() == ClickType.LEFT) {
                                    armor_stand.setLeggings(armor_stand.getLeggings().add(event.getCursor().getAmount()));
                                } else if (event.getClick() == ClickType.RIGHT) {
                                    armor_stand.setLeggings(armor_stand.getLeggings().add());
                                }
                            }

                        } else if (armor_stand.getLeggings() == null || armor_stand.getLeggings().getType() == Material.AIR) {
                            if (event.getClick() == ClickType.LEFT) {
                                armor_stand.setLeggings(event.getCursor());
                            } else if (event.getClick() == ClickType.RIGHT) {
                                armor_stand.setLeggings(event.getCursor().asOne());
                            }
                        }

                    } else if (event.getRawSlot() == 4) {
                        // 腳
                        if (event.getCurrentItem().isSimilar(armor_stand.getBoots())) {
                        } else if (event.getCurrentItem().getType() == Material.AIR && armor_stand.getBoots().getType() == Material.AIR) {
                        } else {
                            event.setCursor(new ItemStack(Material.AIR));
                            menu(player, armor_stand);
                            event.setCancelled(true);
                            return;
                        }

                        if (event.getCurrentItem().isSimilar(armor_stand.getBoots()) && event.getCursor().getType() == Material.AIR) {
                            if (event.getClick() == ClickType.LEFT) {
                                armor_stand.setBoots(new ItemStack(Material.AIR));
                            } else if (event.getClick() == ClickType.RIGHT) {
                                armor_stand.setBoots(armor_stand.getBoots().asQuantity(armor_stand.getBoots().getAmount() / 2));
                            }

                        } else if (armor_stand.getBoots().isSimilar(event.getCurrentItem())) {
                            if (
                                    armor_stand.getBoots().getItemMeta().getCustomTagContainer().getCustomTag(UseItem.random_key, ItemTagType.STRING) != null ||
                                            event.getCurrentItem().getItemMeta().getCustomTagContainer().getCustomTag(UseItem.random_key, ItemTagType.STRING) != null
                            ) {
                                armor_stand.setBoots(event.getCursor());
                            } else {
                                if (event.getClick() == ClickType.LEFT) {
                                    armor_stand.setBoots(armor_stand.getBoots().add(event.getCursor().getAmount()));
                                } else if (event.getClick() == ClickType.RIGHT) {
                                    armor_stand.setBoots(armor_stand.getBoots().add());
                                }
                            }

                        } else if (armor_stand.getBoots() == null || armor_stand.getBoots().getType() == Material.AIR) {
                            if (event.getClick() == ClickType.LEFT) {
                                armor_stand.setBoots(event.getCursor());
                            } else if (event.getClick() == ClickType.RIGHT) {
                                armor_stand.setBoots(event.getCursor().asOne());
                            }
                        }

                    } else if (event.getRawSlot() == 6) {
                        // 主手
                        if (!event.getCurrentItem().isSimilar(armor_stand.getItem(EquipmentSlot.HAND))) {
                            event.setCursor(new ItemStack(Material.AIR));
                            menu(player, armor_stand);
                            event.setCancelled(true);
                            return;
                        }
                        if (armor_stand.getItem(EquipmentSlot.HAND).getType() == Material.AIR) {
                            if (event.getClick() == ClickType.LEFT) {
                                armor_stand.setItem(EquipmentSlot.HAND, event.getCursor());
                            } else if (event.getClick() == ClickType.RIGHT) {
                                armor_stand.setItem(EquipmentSlot.HAND, event.getCursor().asOne());
                            }

                        } else if (event.getCurrentItem().isSimilar(armor_stand.getItem(EquipmentSlot.HAND)) && event.getCursor().getType() == Material.AIR) {
                            if (event.getClick() == ClickType.LEFT) {
                                armor_stand.setItem(EquipmentSlot.HAND, new ItemStack(Material.AIR));
                            } else if (event.getClick() == ClickType.RIGHT) {
                                armor_stand.setItem(EquipmentSlot.HAND, armor_stand.getItem(EquipmentSlot.HAND).asQuantity(armor_stand.getItem(EquipmentSlot.HAND).getAmount() / 2));
                            }

                        } else if (armor_stand.getItem(EquipmentSlot.HAND).isSimilar(event.getCurrentItem())) {
                            if (
                                    armor_stand.getItem(EquipmentSlot.HAND).getItemMeta().getCustomTagContainer().getCustomTag(UseItem.random_key, ItemTagType.STRING) != null ||
                                            event.getCurrentItem().getItemMeta().getCustomTagContainer().getCustomTag(UseItem.random_key, ItemTagType.STRING) != null
                            ) {
                                armor_stand.setItem(EquipmentSlot.HAND, event.getCursor());
                            } else {
                                if (event.getClick() == ClickType.LEFT) {
                                    armor_stand.setItem(EquipmentSlot.HAND, armor_stand.getItem(EquipmentSlot.HAND).add(event.getCursor().getAmount()));
                                } else if (event.getClick() == ClickType.RIGHT) {
                                    armor_stand.setItem(EquipmentSlot.HAND, armor_stand.getItem(EquipmentSlot.HAND).add());
                                }
                            }

                        } else if (armor_stand.getItem(EquipmentSlot.HAND) == null || armor_stand.getItem(EquipmentSlot.HAND).getType() == Material.AIR) {
                            if (event.getClick() == ClickType.LEFT) {
                                armor_stand.setItem(EquipmentSlot.HAND, event.getCursor());
                            } else if (event.getClick() == ClickType.RIGHT) {
                                armor_stand.setItem(EquipmentSlot.HAND, event.getCursor().asOne());
                            }
                        }

                    } else if (event.getRawSlot() == 8) {
                        // 副手
                        if (!event.getCurrentItem().isSimilar(armor_stand.getItem(EquipmentSlot.OFF_HAND))) {
                            event.setCursor(new ItemStack(Material.AIR));
                            menu(player, armor_stand);
                            event.setCancelled(true);
                            return;
                        }
                        if (armor_stand.getItem(EquipmentSlot.OFF_HAND).getType() == Material.AIR) {
                            if (event.getClick() == ClickType.LEFT) {
                                armor_stand.setItem(EquipmentSlot.OFF_HAND, event.getCursor());
                            } else if (event.getClick() == ClickType.RIGHT) {
                                armor_stand.setItem(EquipmentSlot.OFF_HAND, event.getCursor().asOne());
                            }

                        } else if (event.getCurrentItem().isSimilar(armor_stand.getItem(EquipmentSlot.OFF_HAND)) && event.getCursor().getType() == Material.AIR) {
                            if (event.getClick() == ClickType.LEFT) {
                                armor_stand.setItem(EquipmentSlot.OFF_HAND, new ItemStack(Material.AIR));
                            } else if (event.getClick() == ClickType.RIGHT) {
                                armor_stand.setItem(EquipmentSlot.OFF_HAND, armor_stand.getItem(EquipmentSlot.OFF_HAND).asQuantity(armor_stand.getItem(EquipmentSlot.OFF_HAND).getAmount() / 2));
                            }

                        } else if (armor_stand.getItem(EquipmentSlot.OFF_HAND).isSimilar(event.getCurrentItem())) {
                            if (
                                    armor_stand.getItem(EquipmentSlot.OFF_HAND).getItemMeta().getCustomTagContainer().getCustomTag(UseItem.random_key, ItemTagType.STRING) != null ||
                                            event.getCurrentItem().getItemMeta().getCustomTagContainer().getCustomTag(UseItem.random_key, ItemTagType.STRING) != null
                            ) {
                                armor_stand.setItem(EquipmentSlot.OFF_HAND, event.getCursor());
                            } else {
                                if (event.getClick() == ClickType.LEFT) {
                                    armor_stand.setItem(EquipmentSlot.OFF_HAND, armor_stand.getItem(EquipmentSlot.OFF_HAND).add(event.getCursor().getAmount()));
                                } else if (event.getClick() == ClickType.RIGHT) {
                                    armor_stand.setItem(EquipmentSlot.OFF_HAND, armor_stand.getItem(EquipmentSlot.OFF_HAND).add());
                                }
                            }

                        } else if (armor_stand.getItem(EquipmentSlot.OFF_HAND) == null || armor_stand.getItem(EquipmentSlot.OFF_HAND).getType() == Material.AIR) {
                            if (event.getClick() == ClickType.LEFT) {
                                armor_stand.setItem(EquipmentSlot.OFF_HAND, event.getCursor());
                            } else if (event.getClick() == ClickType.RIGHT) {
                                armor_stand.setItem(EquipmentSlot.OFF_HAND, event.getCursor().asOne());
                            }
                        }

                    }

                    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                    if (event.getRawSlot() == 9) {
                        if (armor_stand.isSmall()) {
                            armor_stand.setSmall(false);
                        } else {
                            armor_stand.setSmall(true);
                        }
                        menu(player, armor_stand);

                    } else if (event.getRawSlot() == 10) {
                        if (armor_stand.isVisible()) {
                            armor_stand.setVisible(false);
                        } else {
                            armor_stand.setVisible(true);
                        }
                        menu(player, armor_stand);

                    } else if (event.getRawSlot() == 11) {
                        if (armor_stand.hasGravity()) {
                            armor_stand.setGravity(false);
                            armor_stand.setCollidable(false);
                            armor_stand.setRemoveWhenFarAway(false);
                        } else {
                            armor_stand.setGravity(true);
                            armor_stand.setCollidable(true);
                            armor_stand.setRemoveWhenFarAway(true);
                        }
                        menu(player, armor_stand);

                    } else if (event.getRawSlot() == 12) {
                        if (armor_stand.isCustomNameVisible()) {
                            armor_stand.setCustomNameVisible(false);
                        } else {
                            armor_stand.setCustomNameVisible(true);
                        }
                        menu(player, armor_stand);

                    } else if (event.getRawSlot() == 13) {
                        if (armor_stand.hasArms()) {
                            armor_stand.setArms(false);
                        } else {
                            armor_stand.setArms(true);
                        }
                        menu(player, armor_stand);

                    } else if (event.getRawSlot() == 14) {
                        if (armor_stand.hasBasePlate()) {
                            armor_stand.setBasePlate(false);
                        } else {
                            armor_stand.setBasePlate(true);
                        }
                        menu(player, armor_stand);

                    }
                    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                    if (event.getRawSlot() == 18) {
                        Location location = armor_stand.getLocation();
                        if (event.isShiftClick()) {
                            location.setYaw(0);
                            armor_stand.teleport(location);
                        } else if (event.isLeftClick()) {
                            location.setYaw(location.getYaw() + 4.5f);
                            armor_stand.teleport(location);
                        } else if (event.isRightClick()) {
                            location.setYaw(location.getYaw() - 4.5f);
                            armor_stand.teleport(location);
                        }

                    } else if (event.getRawSlot() == 20) {
                        Location location = armor_stand.getLocation();
                        if (event.isShiftClick()) {
                            location.setX(player.getLocation().getBlockX() + 0.5);
                            armor_stand.teleport(location);
                        } else if (event.isLeftClick()) {
                            location.setX(location.getX() + 0.1);
                            armor_stand.teleport(location);
                        } else if (event.isRightClick()) {
                            location.setX(location.getX() - 0.1);
                            armor_stand.teleport(location);
                        }

                    } else if (event.getRawSlot() == 21) {
                        Location location = armor_stand.getLocation();
                        if (event.isShiftClick()) {
                            location.setY(player.getLocation().getBlockY() + 0.5);
                            armor_stand.teleport(location);
                        } else if (event.isLeftClick()) {
                            location.setY(location.getY() + 0.1);
                            armor_stand.teleport(location);
                        } else if (event.isRightClick()) {
                            location.setY(location.getY() - 0.1);
                            armor_stand.teleport(location);
                        }

                    } else if (event.getRawSlot() == 22) {
                        Location location = armor_stand.getLocation();
                        if (event.isShiftClick()) {
                            location.setZ(player.getLocation().getBlockZ() + 0.5);
                            armor_stand.teleport(location);
                        } else if (event.isLeftClick()) {
                            location.setZ(location.getZ() + 0.1);
                            armor_stand.teleport(location);
                        } else if (event.isRightClick()) {
                            location.setZ(location.getZ() - 0.1);
                            armor_stand.teleport(location);
                        }

                    }
                    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                    if (event.getRawSlot() == 27) {
                        EulerAngle euler_angle = armor_stand.getHeadPose();
                        if (event.isShiftClick()) {
                            armor_stand.setHeadPose(new EulerAngle(
                                    0,
                                    euler_angle.getY(),
                                    euler_angle.getZ()
                            ));
                        } else if (event.isLeftClick()) {
                            armor_stand.setHeadPose(new EulerAngle(
                                    (euler_angle.getX() * (180 / Math.PI) + 4.5) * (Math.PI / 180),
                                    euler_angle.getY(),
                                    euler_angle.getZ()
                            ));
                        } else if (event.isRightClick()) {
                            armor_stand.setHeadPose(new EulerAngle(
                                    (euler_angle.getX() * (180 / Math.PI) - 4.5) * (Math.PI / 180),
                                    euler_angle.getY(),
                                    euler_angle.getZ()
                            ));
                        }

                    } else if (event.getRawSlot() == 28) {
                        EulerAngle euler_angle = armor_stand.getHeadPose();
                        if (event.isShiftClick()) {
                            armor_stand.setHeadPose(new EulerAngle(
                                    euler_angle.getX(),
                                    0,
                                    euler_angle.getZ()
                            ));
                        } else if (event.isLeftClick()) {
                            armor_stand.setHeadPose(new EulerAngle(
                                    euler_angle.getX(),
                                    (euler_angle.getY() * (180 / Math.PI) + 4.5) * (Math.PI / 180),
                                    euler_angle.getZ()
                            ));
                        } else if (event.isRightClick()) {
                            armor_stand.setHeadPose(new EulerAngle(
                                    euler_angle.getX(),
                                    (euler_angle.getY() * (180 / Math.PI) - 4.5) * (Math.PI / 180),
                                    euler_angle.getZ()
                            ));
                        }

                    } else if (event.getRawSlot() == 29) {
                        EulerAngle euler_angle = armor_stand.getHeadPose();
                        if (event.isShiftClick()) {
                            armor_stand.setHeadPose(new EulerAngle(
                                    euler_angle.getX(),
                                    euler_angle.getY(),
                                    0
                            ));
                        } else if (event.isLeftClick()) {
                            armor_stand.setHeadPose(new EulerAngle(
                                    euler_angle.getX(),
                                    euler_angle.getY(),
                                    (euler_angle.getZ() * (180 / Math.PI) + 4.5) * (Math.PI / 180)
                            ));
                        } else if (event.isRightClick()) {
                            armor_stand.setHeadPose(new EulerAngle(
                                    euler_angle.getX(),
                                    euler_angle.getY(),
                                    (euler_angle.getZ() * (180 / Math.PI) - 4.5) * (Math.PI / 180)
                            ));
                        }

                    }
                    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                    if (event.getRawSlot() == 30) {
                        EulerAngle euler_angle = armor_stand.getLeftArmPose();
                        if (event.isShiftClick()) {
                            armor_stand.setLeftArmPose(new EulerAngle(
                                    0,
                                    euler_angle.getY(),
                                    euler_angle.getZ()
                            ));
                        } else if (event.isLeftClick()) {
                            armor_stand.setLeftArmPose(new EulerAngle(
                                    (euler_angle.getX() * (180 / Math.PI) + 4.5) * (Math.PI / 180),
                                    euler_angle.getY(),
                                    euler_angle.getZ()
                            ));
                        } else if (event.isRightClick()) {
                            armor_stand.setLeftArmPose(new EulerAngle(
                                    (euler_angle.getX() * (180 / Math.PI) - 4.5) * (Math.PI / 180),
                                    euler_angle.getY(),
                                    euler_angle.getZ()
                            ));
                        }

                    } else if (event.getRawSlot() == 31) {
                        EulerAngle euler_angle = armor_stand.getLeftArmPose();
                        if (event.isShiftClick()) {
                            armor_stand.setLeftArmPose(new EulerAngle(
                                    euler_angle.getX(),
                                    0,
                                    euler_angle.getZ()
                            ));
                        } else if (event.isLeftClick()) {
                            armor_stand.setLeftArmPose(new EulerAngle(
                                    euler_angle.getX(),
                                    (euler_angle.getY() * (180 / Math.PI) + 4.5) * (Math.PI / 180),
                                    euler_angle.getZ()
                            ));
                        } else if (event.isRightClick()) {
                            armor_stand.setLeftArmPose(new EulerAngle(
                                    euler_angle.getX(),
                                    (euler_angle.getY() * (180 / Math.PI) - 4.5) * (Math.PI / 180),
                                    euler_angle.getZ()
                            ));
                        }

                    } else if (event.getRawSlot() == 32) {
                        EulerAngle euler_angle = armor_stand.getLeftArmPose();
                        if (event.isShiftClick()) {
                            armor_stand.setLeftArmPose(new EulerAngle(
                                    euler_angle.getX(),
                                    euler_angle.getY(),
                                    0
                            ));
                        } else if (event.isLeftClick()) {
                            armor_stand.setLeftArmPose(new EulerAngle(
                                    euler_angle.getX(),
                                    euler_angle.getY(),
                                    (euler_angle.getZ() * (180 / Math.PI) + 4.5) * (Math.PI / 180)
                            ));
                        } else if (event.isRightClick()) {
                            armor_stand.setLeftArmPose(new EulerAngle(
                                    euler_angle.getX(),
                                    euler_angle.getY(),
                                    (euler_angle.getZ() * (180 / Math.PI) - 4.5) * (Math.PI / 180)
                            ));
                        }

                    }
                    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                    if (event.getRawSlot() == 33) {
                        EulerAngle euler_angle = armor_stand.getRightArmPose();
                        if (event.isShiftClick()) {
                            armor_stand.setRightArmPose(new EulerAngle(
                                    0,
                                    euler_angle.getY(),
                                    euler_angle.getZ()
                            ));
                        } else if (event.isLeftClick()) {
                            armor_stand.setRightArmPose(new EulerAngle(
                                    (euler_angle.getX() * (180 / Math.PI) + 4.5) * (Math.PI / 180),
                                    euler_angle.getY(),
                                    euler_angle.getZ()
                            ));
                        } else if (event.isRightClick()) {
                            armor_stand.setRightArmPose(new EulerAngle(
                                    (euler_angle.getX() * (180 / Math.PI) - 4.5) * (Math.PI / 180),
                                    euler_angle.getY(),
                                    euler_angle.getZ()
                            ));
                        }

                    } else if (event.getRawSlot() == 34) {
                        EulerAngle euler_angle = armor_stand.getRightArmPose();
                        if (event.isShiftClick()) {
                            armor_stand.setRightArmPose(new EulerAngle(
                                    euler_angle.getX(),
                                    0,
                                    euler_angle.getZ()
                            ));
                        } else if (event.isLeftClick()) {
                            armor_stand.setRightArmPose(new EulerAngle(
                                    euler_angle.getX(),
                                    (euler_angle.getY() * (180 / Math.PI) + 4.5) * (Math.PI / 180),
                                    euler_angle.getZ()
                            ));
                        } else if (event.isRightClick()) {
                            armor_stand.setRightArmPose(new EulerAngle(
                                    euler_angle.getX(),
                                    (euler_angle.getY() * (180 / Math.PI) - 4.5) * (Math.PI / 180),
                                    euler_angle.getZ()
                            ));
                        }

                    } else if (event.getRawSlot() == 35) {
                        EulerAngle euler_angle = armor_stand.getRightArmPose();
                        if (event.isShiftClick()) {
                            armor_stand.setRightArmPose(new EulerAngle(
                                    euler_angle.getX(),
                                    euler_angle.getY(),
                                    0
                            ));
                        } else if (event.isLeftClick()) {
                            armor_stand.setRightArmPose(new EulerAngle(
                                    euler_angle.getX(),
                                    euler_angle.getY(),
                                    (euler_angle.getZ() * (180 / Math.PI) + 4.5) * (Math.PI / 180)
                            ));
                        } else if (event.isRightClick()) {
                            armor_stand.setRightArmPose(new EulerAngle(
                                    euler_angle.getX(),
                                    euler_angle.getY(),
                                    (euler_angle.getZ() * (180 / Math.PI) - 4.5) * (Math.PI / 180)
                            ));
                        }

                    }
                    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                    if (event.getRawSlot() == 36) {
                        EulerAngle euler_angle = armor_stand.getBodyPose();
                        if (event.isShiftClick()) {
                            armor_stand.setBodyPose(new EulerAngle(
                                    0,
                                    euler_angle.getY(),
                                    euler_angle.getZ()
                            ));
                        } else if (event.isLeftClick()) {
                            armor_stand.setBodyPose(new EulerAngle(
                                    (euler_angle.getX() * (180 / Math.PI) + 4.5) * (Math.PI / 180),
                                    euler_angle.getY(),
                                    euler_angle.getZ()
                            ));
                        } else if (event.isRightClick()) {
                            armor_stand.setBodyPose(new EulerAngle(
                                    (euler_angle.getX() * (180 / Math.PI) - 4.5) * (Math.PI / 180),
                                    euler_angle.getY(),
                                    euler_angle.getZ()
                            ));
                        }

                    } else if (event.getRawSlot() == 37) {
                        EulerAngle euler_angle = armor_stand.getBodyPose();
                        if (event.isShiftClick()) {
                            armor_stand.setBodyPose(new EulerAngle(
                                    euler_angle.getX(),
                                    0,
                                    euler_angle.getZ()
                            ));
                        } else if (event.isLeftClick()) {
                            armor_stand.setBodyPose(new EulerAngle(
                                    euler_angle.getX(),
                                    (euler_angle.getY() * (180 / Math.PI) + 4.5) * (Math.PI / 180),
                                    euler_angle.getZ()
                            ));
                        } else if (event.isRightClick()) {
                            armor_stand.setBodyPose(new EulerAngle(
                                    euler_angle.getX(),
                                    (euler_angle.getY() * (180 / Math.PI) - 4.5) * (Math.PI / 180),
                                    euler_angle.getZ()
                            ));
                        }

                    } else if (event.getRawSlot() == 38) {
                        EulerAngle euler_angle = armor_stand.getBodyPose();
                        if (event.isShiftClick()) {
                            armor_stand.setBodyPose(new EulerAngle(
                                    euler_angle.getX(),
                                    euler_angle.getY(),
                                    0
                            ));
                        } else if (event.isLeftClick()) {
                            armor_stand.setBodyPose(new EulerAngle(
                                    euler_angle.getX(),
                                    euler_angle.getY(),
                                    (euler_angle.getZ() * (180 / Math.PI) + 4.5) * (Math.PI / 180)
                            ));
                        } else if (event.isRightClick()) {
                            armor_stand.setBodyPose(new EulerAngle(
                                    euler_angle.getX(),
                                    euler_angle.getY(),
                                    (euler_angle.getZ() * (180 / Math.PI) - 4.5) * (Math.PI / 180)
                            ));
                        }

                    }
                    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                    if (event.getRawSlot() == 39) {
                        EulerAngle euler_angle = armor_stand.getLeftLegPose();
                        if (event.isShiftClick()) {
                            armor_stand.setLeftLegPose(new EulerAngle(
                                    0,
                                    euler_angle.getY(),
                                    euler_angle.getZ()
                            ));
                        } else if (event.isLeftClick()) {
                            armor_stand.setLeftLegPose(new EulerAngle(
                                    (euler_angle.getX() * (180 / Math.PI) + 4.5) * (Math.PI / 180),
                                    euler_angle.getY(),
                                    euler_angle.getZ()
                            ));
                        } else if (event.isRightClick()) {
                            armor_stand.setLeftLegPose(new EulerAngle(
                                    (euler_angle.getX() * (180 / Math.PI) - 4.5) * (Math.PI / 180),
                                    euler_angle.getY(),
                                    euler_angle.getZ()
                            ));
                        }

                    } else if (event.getRawSlot() == 40) {
                        EulerAngle euler_angle = armor_stand.getLeftLegPose();
                        if (event.isShiftClick()) {
                            armor_stand.setLeftLegPose(new EulerAngle(
                                    euler_angle.getX(),
                                    0,
                                    euler_angle.getZ()
                            ));
                        } else if (event.isLeftClick()) {
                            armor_stand.setLeftLegPose(new EulerAngle(
                                    euler_angle.getX(),
                                    (euler_angle.getY() * (180 / Math.PI) + 4.5) * (Math.PI / 180),
                                    euler_angle.getZ()
                            ));
                        } else if (event.isRightClick()) {
                            armor_stand.setLeftLegPose(new EulerAngle(
                                    euler_angle.getX(),
                                    (euler_angle.getY() * (180 / Math.PI) - 4.5) * (Math.PI / 180),
                                    euler_angle.getZ()
                            ));
                        }

                    } else if (event.getRawSlot() == 41) {
                        EulerAngle euler_angle = armor_stand.getLeftLegPose();
                        if (event.isShiftClick()) {
                            armor_stand.setLeftLegPose(new EulerAngle(
                                    euler_angle.getX(),
                                    euler_angle.getY(),
                                    0
                            ));
                        } else if (event.isLeftClick()) {
                            armor_stand.setLeftLegPose(new EulerAngle(
                                    euler_angle.getX(),
                                    euler_angle.getY(),
                                    (euler_angle.getZ() * (180 / Math.PI) + 4.5) * (Math.PI / 180)
                            ));
                        } else if (event.isRightClick()) {
                            armor_stand.setLeftLegPose(new EulerAngle(
                                    euler_angle.getX(),
                                    euler_angle.getY(),
                                    (euler_angle.getZ() * (180 / Math.PI) - 4.5) * (Math.PI / 180)
                            ));
                        }

                    }
                    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                    if (event.getRawSlot() == 42) {
                        EulerAngle euler_angle = armor_stand.getRightLegPose();
                        if (event.isShiftClick()) {
                            armor_stand.setRightLegPose(new EulerAngle(
                                    0,
                                    euler_angle.getY(),
                                    euler_angle.getZ()
                            ));
                        } else if (event.isLeftClick()) {
                            armor_stand.setRightLegPose(new EulerAngle(
                                    (euler_angle.getX() * (180 / Math.PI) + 4.5) * (Math.PI / 180),
                                    euler_angle.getY(),
                                    euler_angle.getZ()
                            ));
                        } else if (event.isRightClick()) {
                            armor_stand.setRightLegPose(new EulerAngle(
                                    (euler_angle.getX() * (180 / Math.PI) - 4.5) * (Math.PI / 180),
                                    euler_angle.getY(),
                                    euler_angle.getZ()
                            ));
                        }

                    } else if (event.getRawSlot() == 43) {
                        EulerAngle euler_angle = armor_stand.getRightLegPose();
                        if (event.isShiftClick()) {
                            armor_stand.setRightLegPose(new EulerAngle(
                                    euler_angle.getX(),
                                    0,
                                    euler_angle.getZ()
                            ));
                        } else if (event.isLeftClick()) {
                            armor_stand.setRightLegPose(new EulerAngle(
                                    euler_angle.getX(),
                                    (euler_angle.getY() * (180 / Math.PI) + 4.5) * (Math.PI / 180),
                                    euler_angle.getZ()
                            ));
                        } else if (event.isRightClick()) {
                            armor_stand.setRightLegPose(new EulerAngle(
                                    euler_angle.getX(),
                                    (euler_angle.getY() * (180 / Math.PI) - 4.5) * (Math.PI / 180),
                                    euler_angle.getZ()
                            ));
                        }

                    } else if (event.getRawSlot() == 44) {
                        EulerAngle euler_angle = armor_stand.getRightLegPose();
                        if (event.isShiftClick()) {
                            armor_stand.setRightLegPose(new EulerAngle(
                                    euler_angle.getX(),
                                    euler_angle.getY(),
                                    0
                            ));
                        } else if (event.isLeftClick()) {
                            armor_stand.setRightLegPose(new EulerAngle(
                                    euler_angle.getX(),
                                    euler_angle.getY(),
                                    (euler_angle.getZ() * (180 / Math.PI) + 4.5) * (Math.PI / 180)
                            ));
                        } else if (event.isRightClick()) {
                            armor_stand.setRightLegPose(new EulerAngle(
                                    euler_angle.getX(),
                                    euler_angle.getY(),
                                    (euler_angle.getZ() * (180 / Math.PI) - 4.5) * (Math.PI / 180)
                            ));
                        }

                    }
                    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                    if (event.getRawSlot() == 53) {
                        // 關閉
                        event.getWhoClicked().closeInventory();

                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    event.setCancelled(true);
                }
            }
        }
    }


    @EventHandler
    final public void onInventoryDragEvent(InventoryDragEvent event) {
        if (event.getView().getTitle().equalsIgnoreCase("§z進階編輯盔甲座")) {
            event.setCancelled(true);
        }
    }


}
