package SuperFreedomSurvive.Player;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.HashMap;

final public class Pay {

    // 是否有足夠的數量
    static final public boolean Have(Player player, int need) {
        return Have(player, need, Material.EMERALD);
    }

    // 是否有足夠的數量
    static final public boolean Have(Player player, int need, Material material) {
        // 檢查玩家身上物資夠不夠
        int emerald_amount = 0;
        PlayerInventory inventory = player.getInventory();
        HashMap all = inventory.all(material);
        for (Object key : all.keySet()) {
            emerald_amount = emerald_amount + ((ItemStack) all.get(key)).getAmount();
        }

        return emerald_amount >= need;
    }


    // 收取綠寶石
    static final public boolean Recover(Player player, int need) {
        return Recover(player, need, Material.EMERALD);
    }

    // 收取
    static final public boolean Recover(Player player, int need, Material material) {

        for (int i = 0, s = player.getInventory().getSize(); i < s; i++) {
            if (player.getInventory().getItem(i) != null) {
                if (player.getInventory().getItem(i).getType() == material) {
                    if (need > 0) {
                        int amount = player.getInventory().getItem(i).getAmount();
                        if (amount > need) {
                            player.getInventory().setItem(i, new ItemStack(material, amount - need));
                            need = 0;
                        } else {
                            player.getInventory().setItem(i, null);
                            need = need - amount;
                        }
                    }
                }
            }
        }
        return need == 0;
    }
}
