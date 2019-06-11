package SuperFreedomSurvive.Definition.Block;

import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;

final public class spawner_block {

    static final public void Use(BlockBreakEvent event) {
        //  當一個方塊被玩家破壞的時候

        event.setCancelled(true);

        event.getBlock().setType(Material.AIR);

        SuperFreedomSurvive.Block.Data.Remove(event.getBlock().getLocation()); // 移除資料值

        // 掉落物
        event.getBlock().getLocation().getWorld().dropItem(event.getBlock().getLocation(), SuperFreedomSurvive.Definition.Item.spawner_block.Get());

    }

}
