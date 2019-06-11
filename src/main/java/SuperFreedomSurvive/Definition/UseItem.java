package SuperFreedomSurvive.Definition;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import SuperFreedomSurvive.Definition.Item.*;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.tags.ItemTagType;

final public class UseItem implements Listener {
    // 使用物品

    static final public org.bukkit.NamespacedKey item_key = SuperFreedomSurvive.SYSTEM.NamespacedKey.getKey("DefinitionItem");
    static final public org.bukkit.NamespacedKey type_key = SuperFreedomSurvive.SYSTEM.NamespacedKey.getKey("Type");
    static final public org.bukkit.NamespacedKey durable_key = SuperFreedomSurvive.SYSTEM.NamespacedKey.getKey("Durable");
    static final public org.bukkit.NamespacedKey random_key = SuperFreedomSurvive.SYSTEM.NamespacedKey.getKey("Random");
    static final public org.bukkit.NamespacedKey time_key = SuperFreedomSurvive.SYSTEM.NamespacedKey.getKey("Time");


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // 點擊方塊
    @EventHandler()
    final public void onPlayerInteractEvent(PlayerInteractEvent event) {
        try {

            if (event.getHand() == EquipmentSlot.HAND) {

                // 是否為右鍵類型
                if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    // 是否手上有物品
                    // 檢查類型

                    if (event.getPlayer().getInventory().getItemInMainHand().getItemMeta() == null) {
                        return;
                    }

                    String tag = event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getCustomTagContainer().getCustomTag(item_key, ItemTagType.STRING);
                    if (tag != null) {

                        switch (tag) {
                            case "storag_animal_egg":
                            case "storag_have_animal_egg":
                                // 動物收納蛋
                                storag_animal_egg.Use(event);
                                break;
                            case "experience_collect_bottle":
                            case "experience_bottle":
                                // 經驗值回收瓶
                                experience_collect_bottle.Use(event);
                                break;

                        }
                    }
                }
            } else {

                if (event.getItem() == null) {
                    return;
                }
                if (event.getItem().getItemMeta() == null) {
                    return;
                }
                if (event.getItem().getItemMeta().getCustomTagContainer().getCustomTag(item_key, ItemTagType.STRING) == null) {
                    return;
                } else {
                    event.setCancelled(true);
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //}
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @EventHandler(ignoreCancelled = true)
    final public void onBlockPlaceEvent(BlockPlaceEvent event) {
        //  當一個方塊被玩家放置的時候

        //if (event.getBlock() == null) { return; }

        // 檢查類型

        if (event.getItemInHand().getItemMeta() == null) {
            return;
        }

        String tag = event.getItemInHand().getItemMeta().getCustomTagContainer().getCustomTag(item_key, ItemTagType.STRING);
        if (tag != null) {
            switch (tag) {
                case "lucky_block":
                    // 幸運方塊
                    lucky_block.Use(event);
                    break;
                case "spawner_block":
                    // 生怪磚
                    spawner_block.Use(event);
                    break;
                case "custom_spawner_block":
                    // 自訂怪物生怪磚
                    custom_spawner_block.Use(event);
                    break;
            }
        }
    }



    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // 點擊實體
    @EventHandler(ignoreCancelled = true)
    final public void onPlayerInteractEntityEvent(PlayerInteractEntityEvent event) {
        try {
            // 是否為手點擊
            if (event.getHand() == EquipmentSlot.HAND) {
                // 是否手上有物品
                    if (event.getPlayer().getInventory().getItemInMainHand().getItemMeta() == null) { return; }

                    // 檢查類型
                    String tag = event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getCustomTagContainer().getCustomTag(item_key, ItemTagType.STRING);
                    if (tag != null) {
                        // 檢查是否有權限
                        if (SuperFreedomSurvive.Land.Permissions.Inspection(event.getPlayer(), event.getRightClicked().getLocation(), "Permissions_PlayerInteractEntity")) {
                            // 有
                            switch (tag) {
                                case "storag_animal_egg":
                                case "storag_have_animal_egg":
                                    // 動物收納蛋
                                    storag_animal_egg.Use(event);
                                    break;


                            }
                        }
                    }
            } else if (event.getHand() == EquipmentSlot.OFF_HAND) {
                if (event.getPlayer().getInventory().getItemInOffHand().getItemMeta() == null)
                    return;

                if (event.getPlayer().getInventory().getItemInOffHand().getItemMeta().getCustomTagContainer().getCustomTag(item_key, ItemTagType.STRING) != null) {
                    event.setCancelled(true);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @EventHandler(ignoreCancelled = true)
    final public void onPlayerItemConsumeEvent(PlayerItemConsumeEvent event) {
        //  當玩家消耗完物品時, 此事件將觸發 例如:(食物, 藥水, 牛奶桶)
        try {
            // 是否手上有物品
            //if (event.getItem() != null) {

            // 檢查類型
            String tag = event.getItem().getItemMeta().getCustomTagContainer().getCustomTag(item_key, ItemTagType.STRING);
            if (tag != null) {
                // 檢查是否有權限
                if (SuperFreedomSurvive.Land.Permissions.Inspection(event.getPlayer(), event.getPlayer().getLocation(), "Permissions_PlayerItemConsume")) {
                    // 有
                    switch (tag) {
                        case "delicious_cookie":
                            // 超好吃餅乾
                            delicious_cookie.Use(event);
                            break;


                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // 檢查合成時是否有隨機字串 // 鐵鉆事件
    @EventHandler(ignoreCancelled = true)
    final public void onInventoryClickEvent(InventoryClickEvent event) {
        try {
            //  點擊容器中的欄位時
            if (event.getWhoClicked() instanceof Player) {
                if (event.getInventory().getType() == InventoryType.WORKBENCH) {
                    if (event.getRawSlot() == 0) {

                        if (event.getCurrentItem().getItemMeta() == null) { return; }

                        if (event.getCurrentItem().getItemMeta().getCustomTagContainer().getCustomTag(random_key, ItemTagType.STRING) != null) {
                            ItemStack item = event.getCurrentItem();
                            ItemMeta meta = item.getItemMeta();
                            String text = "";
                            String[] RegSNContent = {
                                    "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
                                    "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
                                    "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
                                    "_", "+", "-", ".", "~", "/", "=", "[", "]", "(", ")", "<", ">"
                            };
                            for (int i = 0; i < 10; i++)
                                text += RegSNContent[(int) (Math.random() * RegSNContent.length)];
                            meta.getCustomTagContainer().setCustomTag(random_key, ItemTagType.STRING, text); // 確保不可疊加

                            item.setItemMeta(meta); // 寫入資料
                            event.setCurrentItem(item); // 設置完成

                        }
                    }
                } else if (event.getInventory().getType() == InventoryType.ANVIL) {
                    // 鐵鉆事件

                    // 生命核心
                    max_health_core.Use(event);

                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
/*
    @EventHandler(ignoreCancelled = true)
    final public void onPlayerInteractEvent(PlayerInteractEvent event) {
        // 丟出經驗瓶
        // 與對像或空中交互
        try {

            if (event.getItem() == null) { return; }

            if (event.getItem().getType() == Material.EXPERIENCE_BOTTLE) {
                if (event.getItem().getItemMeta() == null) { return; }
                if (event.getItem().getItemMeta().getCustomTagContainer() == null) { return; }
                if (event.getItem().getItemMeta().getCustomTagContainer().getCustomTag(item_key, ItemTagType.STRING) == null) {
                    return;
                } else {
                    event.setCancelled(true);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    */
/*
    @EventHandler
    final public void onPlayerEditBookEvent(PlayerEditBookEvent event) {
        //  當玩家編輯或簽署書籍和羽毛球項目時調用
        if (event.isCancelled()) {
            // 被取消

        } else {
            // 沒有被取消
            try {
                // 是否手上有物品
                if (event.getPlayer().getInventory().getItemInMainHand() != null) {
                    // 檢查類型
                    String tag = event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getCustomTagContainer().getCustomTag(item_key, ItemTagType.STRING);
                    if (tag != null) {
                        switch (tag) {
                            case "land_script_book":
                                // 領地腳本之書
                                land_script_book.Use(event);
                                break;

                        }
                    }
                }

            } catch (Exception ex) {
                // 出錯
                //player.sendMessage(ex.getMessage());
            }
        }
    }
*/

/*
    @EventHandler
    final public void onCreatureSpawnEvent( event) {

        Bukkit.getServer().getLogger().warning( "10" );

    }

    @EventHandler
    final public void onEntitySpawnEvent(EntitySpawnEvent event) {
        Bukkit.getServer().getLogger().warning( "11" );

    }
*/


/*
    // 取得方塊物品
    static final public ItemStack GetBlock(Location location) {
        ///if (ServerPlugin.Block.Data.Have(location)) {
            // 有
            // 取得數據
            switch (ServerPlugin.Block.Data.Get(location)) {
                case "lucky_block":
                    // 幸運方塊
                    return lucky_block.Get();
                case "spawner_block":
                    // 生怪磚
                    return spawner_block.Get();

            }
            return null;
        //}
        //return null;
    }
*/

}
