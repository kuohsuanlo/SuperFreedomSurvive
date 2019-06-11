package SuperFreedomSurvive.Sign.Function;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.DoubleChestInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;

final public class Buy {


    // 買入
    static final public void Use(Player player, Block block) {
        org.bukkit.material.Sign sign = (org.bukkit.material.Sign) block.getState().getData();

        // 條件 後面 或 下面 必須是箱子
        boolean ok = false;
        Block attach_block = null;
        // 檢查類型
        if (block.getType() == Material.SIGN) {
            // 直立的
            // 下方是否有箱子
            attach_block = block.getWorld().getBlockAt(
                    (int) block.getLocation().getX(),
                    (int) block.getLocation().getY() - 1,
                    (int) block.getLocation().getZ()
            );
            if (
                    attach_block.getType() == Material.CHEST ||
                            attach_block.getType() == Material.TRAPPED_CHEST
            ) {
                // 是箱子
                ok = true;
            } else {
                // 不是箱子
                player.sendMessage("[告示牌系統]  §c直立告示牌下方必須是箱子");
            }

        } else if (block.getType() == Material.WALL_SIGN) {
            // 懸掛的
            // 取得面
            if (sign.getFacing() == BlockFace.NORTH) {
                // 0 0 1
                attach_block = block.getWorld().getBlockAt(
                        (int) block.getLocation().getX(),
                        (int) block.getLocation().getY(),
                        (int) block.getLocation().getZ() + 1
                );

            } else if (sign.getFacing() == BlockFace.SOUTH) {
                // 0 0 -1
                attach_block = block.getWorld().getBlockAt(
                        (int) block.getLocation().getX(),
                        (int) block.getLocation().getY(),
                        (int) block.getLocation().getZ() - 1
                );

            } else if (sign.getFacing() == BlockFace.EAST) {
                // -1 0 0
                attach_block = block.getWorld().getBlockAt(
                        (int) block.getLocation().getX() - 1,
                        (int) block.getLocation().getY(),
                        (int) block.getLocation().getZ()
                );

            } else if (sign.getFacing() == BlockFace.WEST) {
                // 1 0 0
                attach_block = block.getWorld().getBlockAt(
                        (int) block.getLocation().getX() + 1,
                        (int) block.getLocation().getY(),
                        (int) block.getLocation().getZ()
                );
            }
            if (attach_block != null) {
                if (
                        attach_block.getType() == Material.CHEST ||
                                attach_block.getType() == Material.TRAPPED_CHEST
                ) {
                    // 是箱子
                    ok = true;
                } else {
                    // 不是箱子
                    player.sendMessage("[告示牌系統]  §c懸掛告示牌必須依附箱子旁");
                }
            }
        }


        if (ok) {
            // 初始化數組
            ArrayList<String> all = new ArrayList<String>();
            org.bukkit.inventory.ItemStack need = null;
            Inventory inv = null;

            // 取得箱子內容
            if (attach_block.getState() instanceof org.bukkit.block.Chest) {
                Chest chest = (Chest) attach_block.getState();
                inv = chest.getInventory();

                // 是否為大箱子
                if (inv instanceof DoubleChestInventory) {
                    DoubleChest doubleChest = (DoubleChest) inv.getHolder();
                    inv = doubleChest.getInventory();

                    // 檢查第 1 格
                    need = inv.getItem(0);

                    // 檢查 2 ~ 最後一格
                    for (int i = 1, s = inv.getSize(); i < s; ++i) {
                        if (inv.getItem(i) == null) { // 為空
                            // 不存入數組
                        } else {
                            if (need != null) {
                                if ((inv.getItem(i)).isSimilar(need)) {
                                    // 跟第一格一樣
                                    // 不存入數組
                                } else {
                                    // 存入數組
                                    all.add(i + "");
                                }
                            } else {
                                // 存入數組
                                all.add(i + "");
                            }
                        }
                    }
                } else {
                    // 檢查第 1 格
                    need = inv.getItem(0);

                    // 檢查 2 ~ 最後一格
                    for (int i = 1, s = inv.getSize(); i < s; ++i) {
                        if (inv.getItem(i) == null) { // 為空
                            // 不存入數組
                        } else {
                            if (need != null) {
                                if ((inv.getItem(i)).isSimilar(need)) {
                                    // 跟第一格一樣
                                    // 不存入數組
                                } else {
                                    // 存入數組
                                    all.add(i + "");
                                }
                            } else {
                                // 存入數組
                                all.add(i + "");
                            }
                        }
                    }
                }
            }


            // 玩家是否有足夠的物品
            if (need != null) {
                int need_amount = need.getAmount();

                // 檢查玩家身上物資夠不夠
                int amount = 0;
                PlayerInventory inventory = player.getInventory();

                for (int i = 0; i < 36; ++i) {
                    if (inventory.getItem(i) != null) {
                        if (inventory.getItem(i).isSimilar(need)) {
                            amount = amount + inventory.getItem(i).getAmount();
                        } else {
                        }
                    }
                }

                if (amount >= need_amount) {
                    // 足夠

                    if (all.size() > 0) {
                        // 隨機數
                        org.bukkit.inventory.ItemStack item = inv.getItem(Integer.parseInt(all.get(0)));

                        // 移除玩家指定數量的物品
                        player.getInventory().removeItem(need);
                        // 給物品
                        player.getInventory().addItem(item);

                        // 移除箱子物品
                        inv.clear(Integer.parseInt(all.get(0)));
                        // 放入物品
                        inv.setItem(Integer.parseInt(all.get(0)), need);

                        player.sendMessage("[告示牌系統]  賣出成功");
                    } else {
                        player.sendMessage("[告示牌系統]  §c箱子已經滿了");
                    }

                } else {
                    // 所需的物資不夠
                    player.sendMessage("[告示牌系統]  §c所需的賣出條件物品不夠");
                    return;
                }


            } else {
                player.sendMessage("[告示牌系統]  §c左上第一格請放置賣出條件物品");
                /*
                if ( all.size() > 0 ) {
                    // 隨機數
                    int random = (int) (Math.random() * all.size());
                    org.bukkit.inventory.ItemStack item = inv.getItem(Integer.parseInt(all.get(random)));

                    // 給物品
                    player.getInventory().addItem(item);

                    // 移除箱子物品
                    inv.clear(Integer.parseInt(all.get(random)));

                    player.sendMessage("[告示牌系統]  買入成功");
                } else {
                    player.sendMessage("[告示牌系統]  §c箱子已經空了");

                }

*/
            }
        }
    }
}
