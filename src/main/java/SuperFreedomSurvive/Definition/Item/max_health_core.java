package SuperFreedomSurvive.Definition.Item;

import SuperFreedomSurvive.Definition.UseItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.tags.ItemTagType;

import java.util.Arrays;

final public class max_health_core {
    // 生命核心

    // 定義新資料值
    static org.bukkit.NamespacedKey item_key = UseItem.item_key; // 創建特殊類型資料
    static Material use_item = Material.NETHER_STAR;


    //
    static final public ItemStack Get() {
        ItemStack item = new ItemStack(use_item);
        ItemMeta meta = item.getItemMeta();

        meta.getCustomTagContainer().setCustomTag(item_key, ItemTagType.STRING, "max_health_core"); // 登入key

        meta.setDisplayName("§d生命核心(未完成)");
        meta.setLore(Arrays.asList(
                ("§r§f在鐵砧與裝備一起合成:裝備最大HP+5"),
                ("§r§f同件裝備上限40HP")
        ));

        item.setItemMeta(meta); // 寫入資料
        return item; // 設置完成
    }


    // 新增配方
    static final public void Composite() {
        ShapedRecipe recipe = new ShapedRecipe(SuperFreedomSurvive.SYSTEM.NamespacedKey.getKey("max_health_core"), Get());

        recipe.shape(
                "EEE",
                "EEE",
                "EEE"
        );

        recipe.setIngredient('E', Material.NETHER_STAR);

        Bukkit.addRecipe(recipe);
    }


    // 合成預覽界面
    static final public void OpenComposite(Player player) {
        // 顯示容器
        Inventory inv = Bukkit.createInventory(null, 36, "§z合成方式");

        inv.setItem(2, new ItemStack(Material.NETHER_STAR));
        inv.setItem(3, new ItemStack(Material.NETHER_STAR));
        inv.setItem(4, new ItemStack(Material.NETHER_STAR));

        inv.setItem(11, new ItemStack(Material.NETHER_STAR));
        inv.setItem(12, new ItemStack(Material.NETHER_STAR));
        inv.setItem(13, new ItemStack(Material.NETHER_STAR));

        inv.setItem(20, new ItemStack(Material.NETHER_STAR));
        inv.setItem(21, new ItemStack(Material.NETHER_STAR));
        inv.setItem(22, new ItemStack(Material.NETHER_STAR));

        inv.setItem(15, Get());


        inv.setItem(34, SuperFreedomSurvive.Menu.Open.PreviousPage());
        inv.setItem(35, SuperFreedomSurvive.Menu.Open.TurnOff());
        // 寫入到容器頁面
        player.openInventory(inv);
    }


    // 使用
    // 鐵鉆事件
    static final public void Use(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
/*
        // 是放入
        if (event.getSlot() == 1) {
            if ( event.getCursor().isSimilar( Get() ) ) {
                player.sendMessage("2");

            }
        } else if (event.getInventory().getItem(1) != null) {
            if ( event.getInventory().getItem(1).isSimilar( Get() ) ) {
                // 是
                player.sendMessage("1");

            }
        } else {
            //event.getInventory().setItem(2 , null );
            //return;
        }

        // 檢查前頭是否為支持的裝備
         if (
                event.getInventory().getItem(0).getType() == Material.LEATHER_HELMET ||
                event.getInventory().getItem(0).getType() == Material.LEATHER_CHESTPLATE ||
                event.getInventory().getItem(0).getType() == Material.LEATHER_LEGGINGS ||
                event.getInventory().getItem(0).getType() == Material.LEATHER_BOOTS ||

                event.getInventory().getItem(0).getType() == Material.CHAINMAIL_HELMET ||
                event.getInventory().getItem(0).getType() == Material.CHAINMAIL_CHESTPLATE ||
                event.getInventory().getItem(0).getType() == Material.CHAINMAIL_LEGGINGS ||
                event.getInventory().getItem(0).getType() == Material.CHAINMAIL_BOOTS ||

                event.getInventory().getItem(0).getType() == Material.IRON_HELMET ||
                event.getInventory().getItem(0).getType() == Material.IRON_CHESTPLATE ||
                event.getInventory().getItem(0).getType() == Material.IRON_LEGGINGS ||
                event.getInventory().getItem(0).getType() == Material.IRON_BOOTS ||

                event.getInventory().getItem(0).getType() == Material.GOLDEN_HELMET ||
                event.getInventory().getItem(0).getType() == Material.GOLDEN_CHESTPLATE ||
                event.getInventory().getItem(0).getType() == Material.GOLDEN_LEGGINGS ||
                event.getInventory().getItem(0).getType() == Material.GOLDEN_BOOTS ||

                event.getInventory().getItem(0).getType() == Material.DIAMOND_HELMET ||
                event.getInventory().getItem(0).getType() == Material.DIAMOND_CHESTPLATE ||
                event.getInventory().getItem(0).getType() == Material.DIAMOND_LEGGINGS ||
                event.getInventory().getItem(0).getType() == Material.DIAMOND_BOOTS
         ){
             player.sendMessage("3");
            // 符合
             ItemStack item = event.getInventory().getItem(0);
             ItemMeta meta = item.getItemMeta();

             Collection collection = meta.getAttributeModifiers(Attribute.GENERIC_MAX_HEALTH);
             if ( collection != null ) {
                 player.sendMessage(collection.size()+"");

             } else {
                 player.sendMessage("9");

             }
             */
/*
             Iterator iterator = collection.iterator();
             AttributeModifier attribute = (AttributeModifier) iterator.next();
             player.sendMessage(attribute.getAmount()+"");
             */

/*
             Multimap
             meta.setAttributeModifiers(Attribute.GENERIC_MAX_HEALTH,new AttributeModifier("",5,AttributeModifier.Operation.ADD_NUMBER));
             */
/*
             if ( collection.h == null ) {

                 player.sendMessage("4");
                 //multimap.put(Attribute.GENERIC_MAX_HEALTH , 5);
             }
*/
        //meta.setAttributeModifiers();
/*

             event.getInventory().setItem(2 , item );

         } else {
             // 不符合
             player.sendMessage("5");
             player.sendMessage(event.getInventory().getItem(0).getType().name());
             event.getInventory().setItem(2 , null );
             return;

         }
*/
    }
}
