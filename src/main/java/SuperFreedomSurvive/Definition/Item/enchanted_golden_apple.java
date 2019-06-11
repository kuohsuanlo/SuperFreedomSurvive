package SuperFreedomSurvive.Definition.Item;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

final public class enchanted_golden_apple {


    // 附魔金蘋果
    static final public ItemStack Get() {
        ItemStack item = new ItemStack(Material.ENCHANTED_GOLDEN_APPLE);
        /*
        ItemMeta meta = item.getItemMeta();

        item.setItemMeta(meta); // 寫入資料
        */
        return item; // 設置完成
    }


    // 新增配方
    static final public void Composite() {
        ShapedRecipe recipe = new ShapedRecipe(SuperFreedomSurvive.SYSTEM.NamespacedKey.getKey("enchanted_golden_apple"), Get());

        recipe.shape(
                "GGG",
                "GAG",
                "GGG"
        );

        recipe.setIngredient('A', Material.GOLDEN_APPLE);
        recipe.setIngredient('G', Material.GOLD_BLOCK);

        Bukkit.addRecipe(recipe);
    }


    // 合成預覽界面
    static final public void OpenComposite(Player player) {
        // 顯示容器
        Inventory inv = Bukkit.createInventory(null, 36, "§z合成方式");

        inv.setItem(2, new ItemStack(Material.GOLD_BLOCK));
        inv.setItem(3, new ItemStack(Material.GOLD_BLOCK));
        inv.setItem(4, new ItemStack(Material.GOLD_BLOCK));

        inv.setItem(11, new ItemStack(Material.GOLD_BLOCK));
        inv.setItem(12, new ItemStack(Material.GOLDEN_APPLE));
        inv.setItem(13, new ItemStack(Material.GOLD_BLOCK));

        inv.setItem(20, new ItemStack(Material.GOLD_BLOCK));
        inv.setItem(21, new ItemStack(Material.GOLD_BLOCK));
        inv.setItem(22, new ItemStack(Material.GOLD_BLOCK));

        inv.setItem(15, Get());


        inv.setItem(34, SuperFreedomSurvive.Menu.Open.PreviousPage());
        inv.setItem(35, SuperFreedomSurvive.Menu.Open.TurnOff());
        // 寫入到容器頁面
        player.openInventory(inv);
    }


}
