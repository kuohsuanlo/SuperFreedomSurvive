package SuperFreedomSurvive.Function;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

final public class ColorCodeTransform implements Listener {
    // 告示牌色碼轉換
    @EventHandler
    final public void onSignChangeEvent(SignChangeEvent event) {
        if (event.isCancelled()) {
            // 已經被取消了
        } else {
            // 沒有被取消

            String[] lines = event.getLines();
            for (int i = 0, s = lines.length; i < s; ++i) {
                event.setLine(i, Conversion((lines[i])));
            }
        }
    }

    // 鐵占色碼轉換
    @EventHandler
    final public void onInventoryClickEvent(InventoryClickEvent event) {
        if (event.isCancelled()) {
            // 已經被取消了
        } else {
            // 沒有被取消
            try {
                //  點擊容器中的欄位時
                if (event.getWhoClicked() instanceof Player) {
                    if (event.getInventory().getType() == InventoryType.ANVIL) {
                        //if (event.getRawSlot() == 0) {
                        // 點擊結果
                        if (event.getInventory().getItem(0) != null) {
                            if (event.getInventory().getItem(0).getType() == Material.CHEST) {
                            } else if (event.getInventory().getItem(0).getType() == Material.TRAPPED_CHEST) {
                            } else if (event.getInventory().getItem(0).getType() == Material.ENDER_CHEST) {
                            } else {
                                ItemStack item = event.getCurrentItem();
                                if (item == null) return;
                                ItemMeta meta = item.getItemMeta();

                                if (meta == null) {
                                    return;
                                }

                                meta.setDisplayName(Conversion(meta.getDisplayName()));

                                item.setItemMeta(meta); // 寫入資料
                                event.setCurrentItem(item); // 設置完成
                            }
                        }
                        //}
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // 轉換接口
    static final public String Conversion(String text) {
        return text.replace("&", "§");
    }

    static final public String CancelConversion(String text) {
        return text.replace("§", "&");
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
