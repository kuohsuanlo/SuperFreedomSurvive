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

final public class experience_collect_bottle {

    // 經驗值回收瓶

    // 定義新資料值
    static org.bukkit.NamespacedKey item_key = UseItem.item_key; // 創建特殊類型資料
    static Material use_item = Material.GLASS_BOTTLE;


    //
    static final public ItemStack Get() {
        ItemStack item = new ItemStack(use_item, 3);
        ItemMeta meta = item.getItemMeta();

        meta.getCustomTagContainer().setCustomTag(item_key, ItemTagType.STRING, "experience_collect_bottle"); // 登入key

        meta.setDisplayName("§e經驗回收瓶");
        meta.setLore(Arrays.asList(
                ("§r§f右鍵使用"),
                ("§r§f把自己的經驗裝入瓶子中"),
                ("§r§f一次固定裝入1000EXP")
        ));

        item.setItemMeta(meta); // 寫入資料
        return item; // 設置完成
    }


    static final public ItemStack Get2() {
        ItemStack item = new ItemStack(Material.EXPERIENCE_BOTTLE);
        ItemMeta meta = item.getItemMeta();

        meta.getCustomTagContainer().setCustomTag(item_key, ItemTagType.STRING, "experience_bottle"); // 登入key

        meta.setDisplayName("§e經驗瓶");
        meta.setLore(Arrays.asList(
                ("§r§f右鍵使用"),
                ("§r§f裡面裝有1000EXP")
        ));

        item.setItemMeta(meta); // 寫入資料
        return item; // 設置完成
    }


    // 新增配方
    static final public void Composite() {
        ShapedRecipe recipe = new ShapedRecipe(SuperFreedomSurvive.SYSTEM.NamespacedKey.getKey("experience_collect_bottle"), Get());

        recipe.shape(
                " S ",
                "G G",
                " G "
        );

        recipe.setIngredient('G', Material.GLASS);
        recipe.setIngredient('S', Material.STICK);

        Bukkit.addRecipe(recipe);
    }


    // 合成預覽界面
    static final public void OpenComposite(Player player) {
        // 顯示容器
        Inventory inv = Bukkit.createInventory(null, 36, "§z合成方式");

        inv.setItem(3, new ItemStack(Material.STICK));

        inv.setItem(11, new ItemStack(Material.GLASS));
        inv.setItem(13, new ItemStack(Material.GLASS));

        inv.setItem(21, new ItemStack(Material.GLASS));

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

        // 檢查是否有權限
        if (SuperFreedomSurvive.Land.Permissions.Inspection(event.getPlayer(), event.getPlayer().getLocation(), "Permissions_PlayerItemConsume")) {
            // 有

            ItemStack player_item = event.getPlayer().getInventory().getItemInMainHand();
            //ItemStack player_item =  event.getItem();
            if (player_item != null) {
                ItemMeta player_item_meta = player_item.getItemMeta();
                CustomItemTagContainer player_item_tag = player_item_meta.getCustomTagContainer();

                // 是否是   ( 存在  數據 )
                if (player_item_tag.getCustomTag(item_key, ItemTagType.STRING).equals("experience_collect_bottle")) {

                    // 檢測玩家經驗是否足夠

                    int exp = 0;
                    int level = event.getPlayer().getLevel();

                    if (level <= 16) {
                        exp += (int) (Math.pow(level, 2) + 6 * level);
                    } else if (level <= 31) {
                        exp += (int) (2.5 * Math.pow(level, 2) - 40.5 * level + 360.0);
                    } else {
                        exp += (int) (4.5 * Math.pow(level, 2) - 162.5 * level + 2220.0);
                    }

                    if (level <= 15) {
                        exp += Math.round((2 * level + 7) * event.getPlayer().getExp());
                    } else if (level <= 30) {
                        exp += Math.round((5 * level - 38) * event.getPlayer().getExp());
                    } else {
                        exp += Math.round((9 * level - 158) * event.getPlayer().getExp());
                    }

                    if (exp >= 1000) {
                        // 足夠

                        // 扣除經驗值
                        event.getPlayer().setExp(0);
                        event.getPlayer().setLevel(0);

                        event.getPlayer().giveExp(exp - 1000);

                        // 將手上瓶子數量 - 1
                        if (event.getPlayer().getInventory().getItemInMainHand().getAmount() == 1) {
                            event.getPlayer().getInventory().setItemInMainHand(null);
                        } else {
                            event.getPlayer().getInventory().getItemInMainHand().setAmount(event.getPlayer().getInventory().getItemInMainHand().getAmount() - 1);
                        }

                        // 加入物品 經驗瓶
                        event.getPlayer().getInventory().addItem(Get2());

                        // 音效
                        event.getPlayer().playSound(event.getPlayer().getLocation(), "minecraft:entity.item.pickup", 1, 1);

                    }


                } else if (player_item_tag.getCustomTag(item_key, ItemTagType.STRING).equals("experience_bottle")) {

                    // 將手上瓶子數量 - 1
                    if (event.getPlayer().getInventory().getItemInMainHand().getAmount() == 1) {
                        event.getPlayer().getInventory().setItemInMainHand(null);
                    } else {
                        event.getPlayer().getInventory().getItemInMainHand().setAmount(event.getPlayer().getInventory().getItemInMainHand().getAmount() - 1);
                    }

                    event.getPlayer().giveExp(1000);

                    // 音效
                    event.getPlayer().playSound(event.getPlayer().getLocation(), "minecraft:entity.experience_orb.pickup", 1, 1);


                }

            }
        }
    }


    // 點擊實體時
    static final public void Use(PlayerInteractEntityEvent event) {

        event.setCancelled(true); // 禁止放置

    }


    // 使用
    // 食物消耗
    static final public void Use(PlayerItemConsumeEvent event) {

        event.setCancelled(true); // 禁止放置

    }


}
