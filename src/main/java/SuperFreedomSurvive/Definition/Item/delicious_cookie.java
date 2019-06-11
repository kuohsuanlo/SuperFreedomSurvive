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

import java.util.Arrays;

final public class delicious_cookie {
    // 超好吃餅乾


    // 定義新資料值
    static org.bukkit.NamespacedKey item_key = UseItem.item_key; // 創建特殊類型資料
    static Material use_item = Material.COOKIE;


    //
    static final public ItemStack Get() {
        ItemStack item = new ItemStack(use_item, 4);
        ItemMeta meta = item.getItemMeta();

        meta.getCustomTagContainer().setCustomTag(item_key, ItemTagType.STRING, "delicious_cookie"); // 登入key

        meta.setDisplayName("§e超好吃餅乾");
        // 顯示效果說明
        meta.setLore(Arrays.asList(
                ("§r§f效果:回復10HP")
        ));

        item.setItemMeta(meta); // 寫入資料
        return item; // 設置完成
    }


    // 新增配方
    static final public void Composite() {
        ShapedRecipe recipe = new ShapedRecipe(SuperFreedomSurvive.SYSTEM.NamespacedKey.getKey("delicious_cookie"), Get());

        recipe.shape(
                "CCC",
                "CSC",
                "CCC"
        );

        recipe.setIngredient('C', Material.COOKIE);
        recipe.setIngredient('S', Material.CAKE);

        Bukkit.addRecipe(recipe);

    }


    // 合成預覽界面
    static final public void OpenComposite(Player player) {
        // 顯示容器
        Inventory inv = Bukkit.createInventory(null, 36, "§z合成方式");

        inv.setItem(2, new ItemStack(Material.COOKIE));
        inv.setItem(3, new ItemStack(Material.COOKIE));
        inv.setItem(4, new ItemStack(Material.COOKIE));

        inv.setItem(11, new ItemStack(Material.COOKIE));
        inv.setItem(12, new ItemStack(Material.CAKE));
        inv.setItem(13, new ItemStack(Material.COOKIE));

        inv.setItem(20, new ItemStack(Material.COOKIE));
        inv.setItem(21, new ItemStack(Material.COOKIE));
        inv.setItem(22, new ItemStack(Material.COOKIE));

        inv.setItem(15, Get());


        inv.setItem(34, SuperFreedomSurvive.Menu.Open.PreviousPage());
        inv.setItem(35, SuperFreedomSurvive.Menu.Open.TurnOff());
        // 寫入到容器頁面
        player.openInventory(inv);
    }


    // 使用
    // 放置 / 點擊方塊時
    static final public void Use(PlayerInteractEvent event) {

    }


    // 點擊實體時
    static final public void Use(PlayerInteractEntityEvent event) {

    }


    // 使用
    // 食物消耗
    static final public void Use(PlayerItemConsumeEvent event) {

        //event.setCancelled(true); // 禁止改動資料

        ItemStack player_item = event.getItem();
        ItemMeta player_item_meta = player_item.getItemMeta();
        CustomItemTagContainer player_item_tag = player_item_meta.getCustomTagContainer();

        // 是否是   ( 存在  數據 )
        if (player_item_tag.getCustomTag(item_key, ItemTagType.STRING).equals("delicious_cookie")) {

            // 回復生命
            if (event.getPlayer().getHealth() + 10 >= event.getPlayer().getMaxHealth()) {
                event.getPlayer().setHealth(event.getPlayer().getMaxHealth());
            } else {
                event.getPlayer().setHealth(event.getPlayer().getHealth() + 10);
            }

        }
    }

}
