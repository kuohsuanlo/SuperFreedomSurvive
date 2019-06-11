package SuperFreedomSurvive.Sign.Function;

import org.bukkit.Material;
import org.bukkit.block.*;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.inventory.DoubleChestInventory;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.regex.Pattern;

final public class __Fill {
    // 填充

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
            // 檢查


            // 取得字串內容
            Sign sign_ = (Sign) block.getState();
            // 取得告示牌內容
            String[] text = sign_.getLines();

            // 0 [填充]
            // 1 (座標),(座標),(座標)
            // 2 (座標),(座標),(座標)

            // 檢查格式是否正確
            if (text[1] == null) {
                // 告知
                player.sendMessage("[告示牌系統]  §c不正確的座標數量 第二行因該是 X Y Z");
                return;

            } else if (text[2] == null) {
                // 告知
                player.sendMessage("[告示牌系統]  §c不正確的座標數量 第三行因該是 X Y Z");
                return;

            }

            // 檢查資料是否正確
            int S_X = 0;
            int S_Y = 0;
            int S_Z = 0;
            // 正則切割
            Pattern p = Pattern.compile("\\s");
            String[] str = p.split(text[1]);
            if (str.length == 3) {
                for (int i = 0, s = str.length; i < s; ++i) {
                    // 檢查是否為整數 或 負數
                    if (
                            str[i].matches("[0-9]{1,8}") ||
                                    str[i].matches("[\\-0-9]{1,9}")
                    ) {
                        // 正確
                    } else {
                        // 不正確
                        player.sendMessage("[告示牌系統]  §c第二行 不正確的座標值 X Y Z 只能是整數數字");
                        return;
                    }
                }
                S_X = Integer.parseInt(str[0]);
                S_Y = Integer.parseInt(str[1]);
                S_Z = Integer.parseInt(str[2]);
                // 檢查目的是否與告示牌位置 100 格內
                if (
                        Math.abs(block.getX() - S_X) < 100 &&
                                Math.abs(block.getY() - S_Y) < 100 &&
                                Math.abs(block.getZ() - S_Z) < 100
                ) {
                    // 是
                } else {
                    player.sendMessage("[告示牌系統]  §c第二行 座標只能在距離告示牌 半徑 100 格內");
                    return;
                }
            }


            // 檢查資料是否正確
            int E_X = 0;
            int E_Y = 0;
            int E_Z = 0;
            // 正則切割
            p = Pattern.compile("\\s");
            str = p.split(text[2]);
            if (str.length == 3) {
                for (int i = 0, s = str.length; i < s; ++i) {
                    // 檢查是否為整數 或 負數
                    if (
                            str[i].matches("[0-9]{1,8}") ||
                                    str[i].matches("[\\-0-9]{1,9}")
                    ) {
                        // 正確
                    } else {
                        // 不正確
                        player.sendMessage("[告示牌系統]  §c第三行 不正確的座標值 X Y Z 只能是整數數字");
                        return;
                    }
                }
                E_X = Integer.parseInt(str[0]);
                E_Y = Integer.parseInt(str[1]);
                E_Z = Integer.parseInt(str[2]);
                // 檢查目的是否與告示牌位置 100 格內
                if (
                        Math.abs(block.getX() - E_X) < 100 &&
                                Math.abs(block.getY() - E_Y) < 100 &&
                                Math.abs(block.getZ() - E_Z) < 100
                ) {
                    // 是
                } else {
                    player.sendMessage("[告示牌系統]  §c第三行 座標只能在距離告示牌 半徑 100 格內");
                    return;
                }
            }


            String ID = null;
            // 是否有領地
            if (SuperFreedomSurvive.Land.Permissions.Is(block.getLocation())) {
                // 有
                // 檢查座標位置是否有領地
                if (SuperFreedomSurvive.Land.Permissions.Is(block.getWorld().getName(), S_X, S_Y, S_Z)) {
                    // 是否有權限
                    if (SuperFreedomSurvive.Land.Permissions.Ownership(player, block.getWorld().getName(), S_X, S_Y, S_Z)) {

                        ID = SuperFreedomSurvive.Land.Permissions.ID(block.getWorld().getName(), S_X, S_Y, S_Z);

                    } else {
                        player.sendMessage("[告示牌系統]  §c第二行座標位置你沒有權限");
                        return;

                    }

                } else {
                    player.sendMessage("[告示牌系統]  §c第二行座標位置沒有領地");
                    return;

                }
                ////////////////////////////////////////////////////
                if (SuperFreedomSurvive.Land.Permissions.Is(block.getWorld().getName(), E_X, E_Y, E_Z)) {
                    // 是否有權限
                    //if ( ServerPlugin.Land.Permissions.Ownership( player , block.getWorld().getName() , E_X , E_Y , E_Z ) ) {

                    if (ID.equals(SuperFreedomSurvive.Land.Permissions.ID(block.getWorld().getName(), E_X, E_Y, E_Z))) {

                        // 檢查是否有包含到其他領地


                    } else {
                        player.sendMessage("[告示牌系統]  §c第三行座標領地與第二行座標領地非同一個");
                        return;

                    }
/*
                    } else {
                        player.sendMessage("[告示牌系統]  §c第三行座標位置你沒有權限/與第二行座標領地非同一個");
                        return;

                    }
                    */
                } else {
                    player.sendMessage("[告示牌系統]  §c第三行座標位置沒有領地");
                    return;

                }

            } else {
                // 無
                player.sendMessage("[告示牌系統]  §c必須在領地內");
                return;

            }

            // 檢查範圍


            // 檢查總塊數


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

                }

                // 箱子內是否有足夠的物品


            }
        }
        //sta.executeQuery
        //sta.executeQuery
        //sta.executeQuery
        //sta.executeQuery
        //sta.executeQuery
        //sta.executeQuery
        //sta.executeQuery
        //sta.executeQuery
    }
}
