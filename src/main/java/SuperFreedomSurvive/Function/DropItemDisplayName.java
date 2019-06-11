package SuperFreedomSurvive.Function;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemMergeEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.inventory.ItemStack;

public class DropItemDisplayName implements Listener {
    // 掉落物顯示名稱

    // 掉落的物品合併
    @EventHandler(priority = EventPriority.HIGHEST,ignoreCancelled = true)
    final public void onItemMergeEvent(ItemMergeEvent event) {
        ItemStack item_S = event.getEntity().getItemStack();
        ItemStack item_T = event.getTarget().getItemStack();

        item_S.add( item_T.getAmount() );

        Location location = event.getTarget().getLocation();
        World world = location.getWorld();

        event.getTarget().remove();
        event.getEntity().remove();

        Item item = world.dropItem(location,item_S);

        if ( item_S.getItemMeta() == null || item_S.getItemMeta().getDisplayName().length() > 0) {
            event.getEntity().setCustomName(item_S.getItemMeta().getDisplayName() + " §r§fx " + item_S.getAmount());
        } else {
            event.getEntity().setCustomName("x " + item_S.getAmount());
        }

        item.setCustomNameVisible(true);


        /*
        if ( item.getItemMeta() == null || item.getItemMeta().getDisplayName().length() > 0) {
            event.getEntity().setCustomName(item.getItemMeta().getDisplayName() + " §r§fx " + item.getAmount() + item.getAmount());
        } else {
            event.getEntity().setCustomName("1 x " + item.getAmount());
        }
        event.getEntity().setCustomNameVisible(true);
        */

    }


    // 當物品掉落
    @EventHandler(priority = EventPriority.HIGHEST,ignoreCancelled = true)
    final public void onItemSpawnEvent(ItemSpawnEvent event) {
        ItemStack item = event.getEntity().getItemStack();

        if ( item.getItemMeta() == null || item.getItemMeta().getDisplayName().length() > 0) {
            event.getEntity().setCustomName(item.getItemMeta().getDisplayName() + " §r§fx " + item.getAmount());
        } else {
            event.getEntity().setCustomName("x " + item.getAmount());
        }
        event.getEntity().setCustomNameVisible(true);

    }


}
