package SuperFreedomSurvive.Definition.Block;

import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

final public class custom_spawner_block {

    static final public void Use(BlockBreakEvent event) {
        //  當一個方塊被玩家破壞的時候

        event.setCancelled(true);

        event.getBlock().setType(Material.AIR);

        SuperFreedomSurvive.Block.Data.Remove(event.getBlock().getLocation()); // 移除資料值
        SuperFreedomSurvive.Monster.Data.Remove(event.getBlock().getLocation()); // 移除資料值

        // 掉落物
        event.getBlock().getLocation().getWorld().dropItem(event.getBlock().getLocation(), SuperFreedomSurvive.Definition.Item.custom_spawner_block.Get());

    }


    static final public void Use(PlayerInteractEvent event) {
        if ( event.getPlayer().isSneaking() ) return;

        //  當一個方塊被玩家右鍵點擊的時候
        event.setCancelled(true);

        SuperFreedomSurvive.Monster.Menu.open(event.getPlayer(),event.getClickedBlock().getLocation()); // 呼叫頁面

    }

}
