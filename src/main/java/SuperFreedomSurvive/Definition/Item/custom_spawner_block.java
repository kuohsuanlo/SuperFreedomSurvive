package SuperFreedomSurvive.Definition.Item;

import SuperFreedomSurvive.Definition.UseBlock;
import SuperFreedomSurvive.Definition.UseItem;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.tags.CustomItemTagContainer;
import org.bukkit.inventory.meta.tags.ItemTagType;

import java.util.Arrays;

final public class custom_spawner_block {
    // 生怪磚

    // 定義新資料值
    static org.bukkit.NamespacedKey item_key = UseItem.item_key; // 創建特殊類型資料
    static org.bukkit.metadata.FixedMetadataValue block_key = UseBlock.block_key("custom_spawner_block"); // 創建特殊類型資料
    static Material use_item = Material.SPAWNER;


    //
    static final public ItemStack Get() {
        ItemStack item = new ItemStack(use_item, 1);
        ItemMeta meta = item.getItemMeta();

        // 附魔特效
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addEnchant(Enchantment.BINDING_CURSE, 1, false);

        meta.getCustomTagContainer().setCustomTag(item_key, ItemTagType.STRING, "custom_spawner_block"); // 登入key

        meta.setDisplayName("§e自訂怪物生怪磚");
        meta.setLore(Arrays.asList(
                ("§r§f僅能在個人世界放置/使用")
        ));

        item.setItemMeta(meta); // 寫入資料
        return item; // 設置完成
    }


    // 新增配方
    static final public void Composite() {
        ShapedRecipe recipe = new ShapedRecipe(SuperFreedomSurvive.SYSTEM.NamespacedKey.getKey("custom_spawner_block"), Get());

        recipe.shape(
                "EEE",
                "EDE",
                "EEE"
        );

        recipe.setIngredient('E', Material.DIAMOND_BLOCK);
        recipe.setIngredient('D', Material.EMERALD_BLOCK);

        Bukkit.addRecipe(recipe);
    }


    // 合成預覽界面
    static final public void OpenComposite(Player player) {
        // 顯示容器
        Inventory inv = Bukkit.createInventory(null, 36, "§z合成方式");

        inv.setItem(2, new ItemStack(Material.DIAMOND_BLOCK));
        inv.setItem(3, new ItemStack(Material.DIAMOND_BLOCK));
        inv.setItem(4, new ItemStack(Material.DIAMOND_BLOCK));

        inv.setItem(11, new ItemStack(Material.DIAMOND_BLOCK));
        inv.setItem(12, new ItemStack(Material.EMERALD_BLOCK));
        inv.setItem(13, new ItemStack(Material.DIAMOND_BLOCK));

        inv.setItem(20, new ItemStack(Material.DIAMOND_BLOCK));
        inv.setItem(21, new ItemStack(Material.DIAMOND_BLOCK));
        inv.setItem(22, new ItemStack(Material.DIAMOND_BLOCK));

        inv.setItem(15, Get());


        inv.setItem(34, SuperFreedomSurvive.Menu.Open.PreviousPage());
        inv.setItem(35, SuperFreedomSurvive.Menu.Open.TurnOff());
        // 寫入到容器頁面
        player.openInventory(inv);
    }


    // 使用
    // 放置 / 點擊方塊時
    static final public void Use(BlockPlaceEvent event) {

        //event.setCancelled(true); // 禁止放置

        //if (event.getBlock() == null) { return; }
        //if (event.getPlayer().getInventory().getItemInMainHand() == null) { return; }
        if ( SuperFreedomSurvive.World.Data.getOwner(event.getBlock().getWorld().getName()) == null ) {
            event.getPlayer().sendMessage("[多世界系統]  §c此世界禁止放置此方塊");
            event.setCancelled(true);
            return;
        }

        // 檢查是否有權限
        if (SuperFreedomSurvive.Land.Permissions.Inspection(event.getPlayer(), event.getBlock().getLocation(), "Permissions_BlockPlace")) {
            // 有

            ItemStack player_item = event.getPlayer().getInventory().getItemInMainHand();
            ItemMeta player_item_meta = player_item.getItemMeta();
            CustomItemTagContainer player_item_tag = player_item_meta.getCustomTagContainer();

            // 是否是   ( 存在  數據 )
            if (player_item_tag.getCustomTag(item_key, ItemTagType.STRING).equals("custom_spawner_block")) {

                // 使用點擊的面 計算出要生成的位置
                Location location = event.getBlock().getLocation();

                // 檢查是否有權限
                if (SuperFreedomSurvive.Land.Permissions.Inspection(event.getPlayer(), location, "Permissions_BlockPlace")) {
                    // 有
/*
                    // 進行資料的附蓋
                    // 將手上方塊數量 - 1
                    if (event.getPlayer().getInventory().getItemInMainHand().getAmount() == 1) {
                        event.getPlayer().getInventory().setItemInMainHand(null);
                    } else {
                        event.getPlayer().getInventory().getItemInMainHand().setAmount(event.getPlayer().getInventory().getItemInMainHand().getAmount() - 1);
                    }
*/
                    // 放置方塊 寫入特殊數據
                    Block block = event.getPlayer().getWorld().getBlockAt(location);
                    block.setType(use_item);
                    CreatureSpawner spawner = (CreatureSpawner) block.getState();
                    spawner.setSpawnCount(0);
                    spawner.setDelay(2147483647);
                    spawner.setSpawnedType(EntityType.LIGHTNING);
                    spawner.update();


                    SuperFreedomSurvive.Block.Data.Set(block.getLocation(), "custom_spawner_block");
                    SuperFreedomSurvive.Monster.Data.Add(block.getLocation());

                } else {
                    // 沒有
                    SuperFreedomSurvive.Land.Display.Message(event.getPlayer(), "§c領地禁止 放置方塊");
                }
            }
        }
    }


    // 點擊實體時
    static final public void Use(PlayerInteractEntityEvent event) {

    }


    // 使用
    // 食物消耗
    static final public void Use(PlayerItemConsumeEvent event) {

    }


}
