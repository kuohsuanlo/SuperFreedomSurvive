package SuperFreedomSurvive.Definition.Item;

import SuperFreedomSurvive.Definition.UseItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.tags.CustomItemTagContainer;
import org.bukkit.inventory.meta.tags.ItemTagType;

final public class _ {
    // 套用模板

    // 定義新資料值
    static org.bukkit.NamespacedKey item_key = UseItem.item_key; // 創建特殊類型資料
    static org.bukkit.NamespacedKey type = SuperFreedomSurvive.SYSTEM.NamespacedKey.getKey("Type"); // 創建特殊類型資料
    static org.bukkit.NamespacedKey durable = SuperFreedomSurvive.SYSTEM.NamespacedKey.getKey("Durable"); // 創建特殊類型資料
    static org.bukkit.NamespacedKey random = SuperFreedomSurvive.SYSTEM.NamespacedKey.getKey("Random"); // 創建特殊類型資料
    static Material use_item = Material.FLINT;


    //
    static final public ItemStack Get() {
        ItemStack item = new ItemStack(use_item);
        ItemMeta meta = item.getItemMeta();
        // ..
        item.setItemMeta(meta); // 寫入資料
        return item; // 設置完成
    }


    // 新增配方
    static final public void Composite() {
        ShapedRecipe recipe = new ShapedRecipe(SuperFreedomSurvive.SYSTEM.NamespacedKey.getKey(""), Get());

        recipe.shape(
                "EEE",
                "EBE",
                "EEE"
        );

        recipe.setIngredient('E', Material.HAY_BLOCK);
        recipe.setIngredient('B', Material.MINECART);

        Bukkit.addRecipe(recipe);
    }


    // 合成預覽界面
    static final public void OpenComposite(Player player) {
        // 顯示容器
        Inventory inv = Bukkit.createInventory(null, 36, "§z合成方式");

        inv.setItem(2, new ItemStack(Material.HAY_BLOCK));
        inv.setItem(3, new ItemStack(Material.HAY_BLOCK));
        inv.setItem(4, new ItemStack(Material.HAY_BLOCK));

        inv.setItem(11, new ItemStack(Material.HAY_BLOCK));
        inv.setItem(12, new ItemStack(Material.MINECART));
        inv.setItem(13, new ItemStack(Material.HAY_BLOCK));

        inv.setItem(20, new ItemStack(Material.HAY_BLOCK));
        inv.setItem(21, new ItemStack(Material.HAY_BLOCK));
        inv.setItem(22, new ItemStack(Material.HAY_BLOCK));

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

        ItemStack player_item = Bukkit.getPlayer(event.getPlayer().getName()).getInventory().getItemInMainHand();
        ItemMeta player_item_meta = player_item.getItemMeta();
        CustomItemTagContainer player_item_tag = player_item_meta.getCustomTagContainer();

        // 是否是   ( 存在  數據 )
        if (player_item_tag.getCustomTag(item_key, ItemTagType.STRING).equals("")) {

            // ..

        }
    }


    // 點擊實體時
    static final public void Use(PlayerInteractEntityEvent event) {

        event.setCancelled(true); // 禁止改動資料

        ItemStack player_item = event.getPlayer().getInventory().getItemInMainHand();
        ItemMeta player_item_meta = player_item.getItemMeta();
        CustomItemTagContainer player_item_tag = player_item_meta.getCustomTagContainer();

        // 是否是   ( 存在  數據 )
        if (player_item_tag.getCustomTag(item_key, ItemTagType.STRING).equals("")) {

            // ..

        }
    }


    // 使用
    // 食物消耗
    static final public void Use(PlayerItemConsumeEvent event) {

        event.setCancelled(true); // 禁止改動資料

        ItemStack player_item = event.getPlayer().getInventory().getItemInMainHand();
        ItemMeta player_item_meta = player_item.getItemMeta();
        CustomItemTagContainer player_item_tag = player_item_meta.getCustomTagContainer();

        // 是否是   ( 存在  數據 )
        if (player_item_tag.getCustomTag(item_key, ItemTagType.STRING).equals("")) {

            // ..

        }
    }


}
