package SuperFreedomSurvive.Definition.Item;

import SuperFreedomSurvive.Definition.UseItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.tags.ItemTagType;

import java.util.Arrays;

final public class __land_script_book {
    // 領地腳本之書

    // 定義新資料值
    static org.bukkit.NamespacedKey item_key = UseItem.item_key; // 創建特殊類型資料
    static org.bukkit.NamespacedKey id = SuperFreedomSurvive.SYSTEM.NamespacedKey.getKey("ID"); // 創建特殊類型資料

    static Material use_item = Material.BOOK;


    static final public ItemStack Get() {
        return Get(null);
    }

    static final public ItemStack Get(String ID) {
        ItemStack item = new ItemStack(use_item);
        ItemMeta meta = item.getItemMeta();

        meta.getCustomTagContainer().setCustomTag(item_key, ItemTagType.STRING, "land_script_book"); // 登入key

        meta.setDisplayName("§e領地腳本之書(未完成)");

        if (ID == null) {
            /*
            String text = "";
            String[] RegSNContent = {
                    "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
                    "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
                    "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
                    "_", "+", "-", ".", "~", "/", "=", "[", "]", "(", ")", "<", ">"
            };
            for (int i = 0; i < 10; i++) text += RegSNContent[(int) (Math.random() * RegSNContent.length)];
            meta.getCustomTagContainer().setCustomTag(id, ItemTagType.STRING, text); // 登入key
            */
            meta.setLore(Arrays.asList(
                    ("§r§f編輯腳本並套用在領地中")
            ));
        } else {
            meta.setLore(Arrays.asList(
                    ("§r§f此書編號:" + ID),
                    ("§r§f編輯腳本並套用在領地中")
            ));
        }

        item.setItemMeta(meta); // 寫入資料
        return item; // 設置完成
    }


    // 新增配方
    static final public void Composite() {
        ShapedRecipe recipe = new ShapedRecipe(SuperFreedomSurvive.SYSTEM.NamespacedKey.getKey("land_script_book"), Get());

        recipe.shape(
                " E ",
                "EBE",
                " E "
        );

        recipe.setIngredient('E', Material.EMERALD);
        recipe.setIngredient('B', Material.WRITABLE_BOOK);

        Bukkit.addRecipe(recipe);
    }


    // 合成預覽界面
    static final public void OpenComposite(Player player) {
        // 顯示容器
        Inventory inv = Bukkit.createInventory(null, 36, "§z合成方式");

        inv.setItem(3, new ItemStack(Material.EMERALD));

        inv.setItem(11, new ItemStack(Material.EMERALD));
        inv.setItem(12, new ItemStack(Material.WRITABLE_BOOK));
        inv.setItem(13, new ItemStack(Material.EMERALD));

        inv.setItem(21, new ItemStack(Material.EMERALD));

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

    }


}
