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

final public class barrier_block {


    // 屏障

    // 定義新資料值
    static org.bukkit.NamespacedKey item_key = UseItem.item_key; // 創建特殊類型資料
    static Material use_item = Material.BARRIER;


    static final public ItemStack Get() {
        return Get(3);
    }

    //
    static final public ItemStack Get(int amount) {
        ItemStack item = new ItemStack(use_item, amount);
        /*
        ItemMeta meta = item.getItemMeta();

        //meta.getCustomTagContainer().setCustomTag( item_key , ItemTagType.STRING , "barrier_block" ); // 登入key

        meta.setDisplayName("§f屏障");

        item.setItemMeta(meta); // 寫入資料
        */
        return item; // 設置完成
    }


    // 新增配方
    static final public void Composite() {
        ShapedRecipe recipe = new ShapedRecipe(SuperFreedomSurvive.SYSTEM.NamespacedKey.getKey("barrier_block"), Get());

        recipe.shape(
                "OOO",
                "OOO",
                "OOO"
        );

        recipe.setIngredient('O', Material.OBSIDIAN);

        Bukkit.addRecipe(recipe);
    }


    // 合成預覽界面
    static final public void OpenComposite(Player player) {
        // 顯示容器
        Inventory inv = Bukkit.createInventory(null, 36, "§z合成方式");

        inv.setItem(2, new ItemStack(Material.OBSIDIAN));
        inv.setItem(3, new ItemStack(Material.OBSIDIAN));
        inv.setItem(4, new ItemStack(Material.OBSIDIAN));

        inv.setItem(11, new ItemStack(Material.OBSIDIAN));
        inv.setItem(12, new ItemStack(Material.OBSIDIAN));
        inv.setItem(13, new ItemStack(Material.OBSIDIAN));

        inv.setItem(20, new ItemStack(Material.OBSIDIAN));
        inv.setItem(21, new ItemStack(Material.OBSIDIAN));
        inv.setItem(22, new ItemStack(Material.OBSIDIAN));

        inv.setItem(15, Get());


        inv.setItem(34, SuperFreedomSurvive.Menu.Open.PreviousPage());
        inv.setItem(35, SuperFreedomSurvive.Menu.Open.TurnOff());
        // 寫入到容器頁面
        player.openInventory(inv);
    }


    // 使用
    // 放置 / 點擊方塊時
    static final public void Use(PlayerInteractEvent event) {
/*
        event.setCancelled(true); // 禁止放置

        ItemStack player_item = Bukkit.getPlayer(event.getPlayer().getName()).getInventory().getItemInMainHand();
        ItemMeta player_item_meta = player_item.getItemMeta();
        CustomItemTagContainer player_item_tag = player_item_meta.getCustomTagContainer();

        // 是否是   ( 存在  數據 )
        if (player_item_tag.getCustomTag(item_key, ItemTagType.STRING).equals("barrier_block")) {

            // 使用點擊的面 計算出要生成的位置
            Location location;
            if (event.getBlockFace() == BlockFace.DOWN) {
                location = new Location(event.getClickedBlock().getLocation().getWorld(), event.getClickedBlock().getLocation().getX(), event.getClickedBlock().getLocation().getY() - 1, event.getClickedBlock().getLocation().getZ());
            } else if (event.getBlockFace() == BlockFace.UP) {
                location = new Location(event.getClickedBlock().getLocation().getWorld(), event.getClickedBlock().getLocation().getX(), event.getClickedBlock().getLocation().getY() + 1, event.getClickedBlock().getLocation().getZ());
            } else if (event.getBlockFace() == BlockFace.NORTH) {
                location = new Location(event.getClickedBlock().getLocation().getWorld(), event.getClickedBlock().getLocation().getX(), event.getClickedBlock().getLocation().getY(), event.getClickedBlock().getLocation().getZ() - 1);
            } else if (event.getBlockFace() == BlockFace.SOUTH) {
                location = new Location(event.getClickedBlock().getLocation().getWorld(), event.getClickedBlock().getLocation().getX(), event.getClickedBlock().getLocation().getY(), event.getClickedBlock().getLocation().getZ() + 1);
            } else if (event.getBlockFace() == BlockFace.EAST) {
                location = new Location(event.getClickedBlock().getLocation().getWorld(), event.getClickedBlock().getLocation().getX() + 1, event.getClickedBlock().getLocation().getY(), event.getClickedBlock().getLocation().getZ());
            } else if (event.getBlockFace() == BlockFace.WEST) {
                location = new Location(event.getClickedBlock().getLocation().getWorld(), event.getClickedBlock().getLocation().getX() - 1, event.getClickedBlock().getLocation().getY(), event.getClickedBlock().getLocation().getZ());
            } else {
                location = null;
            }


            // 進行資料的附蓋
            // 將手上方塊數量 - 1
            if (event.getPlayer().getInventory().getItemInMainHand().getAmount() == 1) {
                event.getPlayer().getInventory().setItemInMainHand(null);
            } else {
                event.getPlayer().getInventory().getItemInMainHand().setAmount(event.getPlayer().getInventory().getItemInMainHand().getAmount() - 1);
            }

            // 放置方塊 寫入特殊數據
            Block block = event.getPlayer().getWorld().getBlockAt(location);
            block.setType(use_item);

            //ServerPlugin.Block.Data.Set(block.getLocation(), "barrier_block");
        }
        */
    }


    // 點擊實體時
    static final public void Use(PlayerInteractEntityEvent event) {

    }


    // 使用
    // 食物消耗
    static final public void Use(PlayerItemConsumeEvent event) {

    }


}
