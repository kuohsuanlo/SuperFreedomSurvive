package SuperFreedomSurvive.Sign;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;

final public class Use implements Listener {
    // 使用告示牌

    @EventHandler(priority = EventPriority.HIGHEST)
    final public void onPlayerInteractEvent(PlayerInteractEvent event) {
        // 是否被取消
        if (event.isCancelled()) {
            // 被取消
        } else {
            try {
                // 沒被取消
                if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    // 是右手點擊
                    if (
                            event.getClickedBlock().getType() == Material.SIGN ||
                                    event.getClickedBlock().getType() == Material.WALL_SIGN
                    ) {
                        // 玩家點擊告示牌
                        // 取得字串內容
                        Sign sign = (Sign) event.getClickedBlock().getState();
                        // 取得告示牌內容

                        String[] text = sign.getLines();
                        // 第一行 是否有資料
                        if (text[0] != null) {
                            // 有
                            // 判斷執行之類型
                            switch (text[0]) {
                                case "[Tp]":
                                case "[tp]":
                                case "[TP]":
                                case "[傳送]":
                                    // 調用 Tp
                                    SuperFreedomSurvive.Sign.Function.Tp.Use(event.getPlayer(), event.getClickedBlock());
                                    break;

                                case "[Lottery]":
                                case "[lottery]":
                                case "[LOTTERY]":
                                case "[抽獎]":
                                    // 調用 Lottery
                                    SuperFreedomSurvive.Sign.Function.Lottery.Use(event.getPlayer(), event.getClickedBlock());
                                    break;

                                case "[Spawn]":
                                case "[spawn]":
                                case "[SPAWN]":
                                case "[重生點]":
                                    // 調用 Spawn
                                    SuperFreedomSurvive.Sign.Function.Spawn.Use(event.getPlayer(), event.getClickedBlock());
                                    break;

                                case "[Buy]":
                                case "[buy]":
                                case "[BUY]":
                                case "[買入]":
                                    // 調用 Buy
                                    SuperFreedomSurvive.Sign.Function.Buy.Use(event.getPlayer(), event.getClickedBlock());
                                    break;

                                case "[Sell]":
                                case "[sell]":
                                case "[SELL]":
                                case "[賣出]":
                                    // 調用 Sell
                                    SuperFreedomSurvive.Sign.Function.Sell.Use(event.getPlayer(), event.getClickedBlock());
                                    break;

                            }
                        }
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    final public void onInventoryMoveItemEvent(InventoryMoveItemEvent event) {
        // 是否被取消
        if (event.isCancelled()) {
            // 被取消
        } else {
            // 沒被取消
            World world = event.getSource().getLocation().getWorld();
            Block block;

            // 檢查是否為箱子
            block = world.getBlockAt(
                    event.getSource().getLocation().getBlockX(),
                    event.getSource().getLocation().getBlockY(),
                    event.getSource().getLocation().getBlockZ()
            );

        }
    }
}
