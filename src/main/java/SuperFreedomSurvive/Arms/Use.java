package SuperFreedomSurvive.Arms;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.meta.tags.ItemTagType;
import SuperFreedomSurvive.Arms.Registers.*;

final public class Use implements Listener {

    static final public org.bukkit.NamespacedKey arms_key = SuperFreedomSurvive.SYSTEM.NamespacedKey.getKey("Arms");

    // 點擊方塊
    @EventHandler
    final public void onVehicleCreateEvent(PlayerInteractEvent event) {
        try {

            if (event.getHand() == EquipmentSlot.HAND) {
                if (event.getItem() == null) { return; }
                // 是否為右鍵類型
                if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    // 是否手上有物品
                    if (event.getItem() != null) {
                        // 檢查類型
                        String tag = event.getItem().getItemMeta().getCustomTagContainer().getCustomTag(arms_key, ItemTagType.STRING);
                        if (tag != null) {

                            /*
                            level_

                            level_initial__ 初級 90%
                            level_middle__ 中級 9%
                            level_advanced__ 高級 0.9%
                            level_excellent__ 超級 0.09%
                            level_apex__ 頂尖級 0.009%
                            level_legendary__ 傳說級 0.0009%
                            level_world__ 世界級 0.00009%
                            level_planetary__ 星球級 0.000009%
                            level_cosmic__ 宇宙級 0.0000009%

                            # 試作品

                             */

                            switch (tag) {
                                case "#level_initial__flame_magic_wand":
                                    // 初級 熔岩魔法仗
                                    flame_magic_wand.level_initial(event);
                                    break;
                                case "#level_initial__ice_magic_wand":
                                    // 初級 冰晶魔法仗
                                    break;
                                case "#level_initial__explosion_magic_wand":
                                    // 初級 爆裂魔法仗
                                    break;
                                case "#level_initial__lightning_magic_wand":
                                    // 初級 雷極魔法仗
                                    break;

                            }
                        }
                    }
                }
            } else if (event.getHand() == EquipmentSlot.OFF_HAND) {
                if (event.getItem() != null) {
                    if (event.getItem().getItemMeta().getCustomTagContainer().getCustomTag(arms_key, ItemTagType.STRING) != null) {
                        event.setCancelled(true);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
