package SuperFreedomSurvive.Definition.Block;

import org.bukkit.Material;
import org.bukkit.event.block.BlockDamageEvent;

final public class barrier_block {

    // 屏障

    //  當一個方塊被玩家點擊的時候
    static final public void Use(BlockDamageEvent event) {
        event.setCancelled(true);

        // 檢查是否有權限
        if (SuperFreedomSurvive.Land.Permissions.Inspection(event.getPlayer(), event.getBlock().getLocation(), "Permissions_BlockBreak")) {
            // 有
            event.getBlock().setType(Material.AIR);

            //ServerPlugin.Block.Data.Remove(event.getBlock().getLocation()); // 移除資料值

            // 掉落物
            event.getBlock().getLocation().getWorld().dropItem(event.getBlock().getLocation(), SuperFreedomSurvive.Definition.Item.barrier_block.Get(1));

            //event.getPlayer().getInventory().addItem();
        } else {
            // 沒有
            SuperFreedomSurvive.Land.Display.Message(event.getPlayer(), "§c領地禁止 破壞方塊");
        }
    }
}
