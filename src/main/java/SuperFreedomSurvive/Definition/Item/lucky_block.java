package SuperFreedomSurvive.Definition.Item;

import SuperFreedomSurvive.Definition.UseBlock;
import SuperFreedomSurvive.Definition.UseItem;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.tags.CustomItemTagContainer;
import org.bukkit.inventory.meta.tags.ItemTagType;

import java.util.Arrays;

final public class lucky_block {

    // 定義新資料值
    static org.bukkit.NamespacedKey item_key = UseItem.item_key; // 創建特殊類型資料
    static org.bukkit.metadata.FixedMetadataValue block_key = UseBlock.block_key("lucky_block"); // 創建特殊類型資料
    static Material use_item = Material.GOLD_BLOCK;


    // 幸運方塊
    static final public ItemStack Get() {
        ItemStack item = new ItemStack(use_item, 1);
        ItemMeta meta = item.getItemMeta();

        meta.getCustomTagContainer().setCustomTag(item_key, ItemTagType.STRING, "lucky_block"); // 登入key

        meta.setDisplayName("§e幸運方塊");
        meta.setLore(Arrays.asList(
                ("§r§f放置並破壞後:機率出現生物或物品")
        ));

        item.setItemMeta(meta); // 寫入資料
        return item; // 設置完成
    }


    // 新增配方
    static final public void Composite() {
        ShapedRecipe recipe = new ShapedRecipe(SuperFreedomSurvive.SYSTEM.NamespacedKey.getKey("lucky_block"), Get());

        recipe.shape(
                "EEE",
                "EBE",
                "EEE"
        );

        recipe.setIngredient('E', Material.GOLD_NUGGET);
        recipe.setIngredient('B', Material.DIAMOND);

        Bukkit.addRecipe(recipe);
    }


    // 合成預覽界面
    static final public void OpenComposite(Player player) {
        // 顯示容器
        Inventory inv = Bukkit.createInventory(null, 36, "§z合成方式");

        inv.setItem(2, new ItemStack(Material.GOLD_NUGGET));
        inv.setItem(3, new ItemStack(Material.GOLD_NUGGET));
        inv.setItem(4, new ItemStack(Material.GOLD_NUGGET));

        inv.setItem(11, new ItemStack(Material.GOLD_NUGGET));
        inv.setItem(12, new ItemStack(Material.DIAMOND));
        inv.setItem(13, new ItemStack(Material.GOLD_NUGGET));

        inv.setItem(20, new ItemStack(Material.GOLD_NUGGET));
        inv.setItem(21, new ItemStack(Material.GOLD_NUGGET));
        inv.setItem(22, new ItemStack(Material.GOLD_NUGGET));

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

        if (event.getPlayer().getInventory().getItemInMainHand() == null) { return; }

        // 檢查是否有權限
        if (SuperFreedomSurvive.Land.Permissions.Inspection(event.getPlayer(), event.getBlock().getLocation(), "Permissions_BlockPlace")) {
            // 有

            ItemStack player_item = event.getPlayer().getInventory().getItemInMainHand();
            ItemMeta player_item_meta = player_item.getItemMeta();
            CustomItemTagContainer player_item_tag = player_item_meta.getCustomTagContainer();

            // 是否是   ( 存在  數據 )
            if (player_item_tag.getCustomTag(item_key, ItemTagType.STRING).equals("lucky_block")) {

                // 使用點擊的面 計算出要生成的位置
                Location location = event.getBlock().getLocation();

                // 檢查是否有權限
                if (SuperFreedomSurvive.Land.Permissions.Inspection(event.getPlayer(), location, "Permissions_BlockPlace")) {
                    // 有

                    // 進行資料的附蓋
                    /*
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

                    SuperFreedomSurvive.Block.Data.Set(block.getLocation(), "lucky_block");

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
