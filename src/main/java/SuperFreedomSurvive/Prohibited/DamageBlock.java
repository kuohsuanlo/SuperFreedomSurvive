package SuperFreedomSurvive.Prohibited;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;

final public class DamageBlock implements Listener {


    @EventHandler(priority = EventPriority.HIGHEST)
    final public void onBlockBreakEvent(BlockBreakEvent event) {
        //  當一個方塊被玩家破壞的時候

        if ( event.getPlayer() == null ) {
            return;
        }
        if ( event.getBlock() == null ) {
            return;
        }

        if (Inspection(event.getPlayer(), event.getBlock())) {
            // 允許
        } else {
            // 禁止
            event.setCancelled(true);
        }
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    final public void onBlockDamageEvent(BlockDamageEvent event) {
        //  方塊被玩家損壞的事件

        if ( event.getPlayer() == null ) {
            return;
        }
        if ( event.getBlock() == null ) {
            return;
        }

        if (Inspection(event.getPlayer(), event.getBlock())) {
            // 允許
        } else {
            // 禁止
            event.setCancelled(true);
        }
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    final public void onBlockFertilizeEvent(BlockFertilizeEvent event) {
        //  骨粉改變，加快生長速度

        if (Inspection(event.getPlayer(), event.getBlock())) {
            // 允許
        } else {
            // 禁止
            event.setCancelled(true);
        }
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    final public void onBlockIgniteEvent(BlockIgniteEvent event) {
        //  當一個方塊被點燃

        if ( event.getPlayer() == null ) {
            return;
        }
        if ( event.getBlock() == null ) {
            return;
        }

        if (Inspection(event.getPlayer(), event.getBlock())) {
            // 允許
        } else {
            // 禁止
            event.setCancelled(true);
        }
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    final public void onBlockPlaceEvent(BlockPlaceEvent event) {
        //  當一個方塊被玩家放置的時候

        if ( event.getPlayer() == null ) {
            return;
        }
        if ( event.getBlock() == null ) {
            return;
        }

        if (Inspection(event.getPlayer(), event.getBlock())) {
            // 允許
        } else {
            // 禁止
            event.setCancelled(true);
        }
    }


    static final public boolean Inspection(Player player, Block block) {
        try {
            if (block != null || player != null) {
                // 檢查放置位置是否離玩家5格內
                if (
                        block.getLocation().getBlockX() >= player.getLocation().getBlockX() - 5 && block.getLocation().getBlockX() <= player.getLocation().getBlockX() + 5 &&
                        block.getLocation().getBlockY() >= player.getLocation().getBlockY() - 5 && block.getLocation().getBlockY() <= player.getLocation().getBlockY() + 5 &&
                        block.getLocation().getBlockZ() >= player.getLocation().getBlockZ() - 5 && block.getLocation().getBlockZ() <= player.getLocation().getBlockZ() + 5
                ) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

}
