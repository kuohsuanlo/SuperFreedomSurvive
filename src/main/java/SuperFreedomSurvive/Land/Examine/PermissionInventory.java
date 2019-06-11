package SuperFreedomSurvive.Land.Examine;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

final public class PermissionInventory implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    final public void onPlayerInteractEvent(PlayerInteractEvent event) {
        //  當玩家與容器交互
        try {

            if ( event.getAction() == Action.PHYSICAL ) {
                return;
            }

            String name = null;
            String directions = null;

            ///////////////////////////////////////////////////////////////////  檢查點擊方塊類型  ///////////////////////////////////////////////////////////////////

            if ( event.getClickedBlock() == null ) { return; }

            name = Type(event.getClickedBlock().getType())[0];
            directions = Type(event.getClickedBlock().getType())[1];

            if (name != null) {
                // 有數據
                // 檢查是否有權限
                if (SuperFreedomSurvive.Land.Permissions.Inspection(event.getPlayer(), event.getClickedBlock().getLocation(), "Permissions_Interact_" + name)) {
                    // 有
                } else {
                    // 沒有
                    SuperFreedomSurvive.Land.Display.Message(event.getPlayer(), "§c領地禁止 使用" + directions);
                    // 禁止事件 改動資料
                    event.setCancelled(true);
                }

            }


            ///////////////////////////////////////////////////////////////////  檢查手持方塊類型  ///////////////////////////////////////////////////////////////////
            if (event.getItem() == null) { return; }

            name = Type(event.getItem().getType())[0];
            directions = Type(event.getItem().getType())[1];

            if (name != null) {
                // 有數據
                // 檢查是否有權限
                if (SuperFreedomSurvive.Land.Permissions.Inspection(event.getPlayer(), event.getClickedBlock().getLocation(), "Permissions_Interact_" + name)) {
                    // 有
                } else {
                    // 沒有
                    SuperFreedomSurvive.Land.Display.Message(event.getPlayer(), "§c領地禁止 使用" + directions);
                    // 禁止事件 改動資料
                    event.setCancelled(true);
                }

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    // 類型檢查
    static final public String[] Type(Material material) {
        if (material == Material.CHEST) {
            // 箱子
            String[] s = {"Chest", "儲物箱"};
            return s;

        } else if (material == Material.TRAPPED_CHEST) {
            // 陷阱儲物箱
            String[] s = {"TrappedChest", "陷阱儲物箱"};
            return s;

        } else if (material == Material.ENDER_CHEST) {
            // 末影箱子
            String[] s = {"EnderChest", "末影儲物箱"};
            return s;

        } else if (material == Material.CRAFTING_TABLE) {
            // 工作臺
            String[] s = {"CraftingTable", "工作臺"};
            return s;

        } else if (material == Material.FURNACE) {
            // 熔爐
            String[] s = {"Furnace", "熔爐"};
            return s;

        } else if (material == Material.HOPPER) {
            // 漏斗
            String[] s = {"Hopper", "漏斗"};
            return s;

            //} else if( event.getClickedBlock().getType() == Material.HOPPER_MINECART ) {
            // 礦車漏斗
            //    name = "HopperMinecart";
            //    directions = "";
        } else if (material == Material.CAULDRON) {
            // 鍋釜
            String[] s = {"Cauldron", "鍋釜"};
            return s;

        } else if (material == Material.BREWING_STAND) {
            // 釀造台
            String[] s = {"BrewingStand", "釀造台"};
            return s;

        } else if (material == Material.ENCHANTING_TABLE) {
            // 附魔台
            String[] s = {"EnchantTable", "附魔台"};
            return s;

        } else if (material == Material.BEACON) {
            // 烽火台
            String[] s = {"Beacon", "烽火台"};
            return s;

        } else if (material.name().matches(".*SHULKER_BOX.*")) {
            // 界伏盒
            String[] s = {"ShulkerBox", "界伏盒"};
            return s;

        } else if (material == Material.JUKEBOX) {
            // 唱片機
            String[] s = {"Jukebox", "唱片機"};
            return s;

        } else if (material == Material.NOTE_BLOCK) {
            // 音符盒
            String[] s = {"Note_Block", "音符盒"};
            return s;

        } else if (material.name().matches(".*ANVIL.*")) {
            // 鐵砧
            String[] s = {"Anvil", "鐵砧"};
            return s;

        } else if (material == Material.PAINTING) {
            // 繪畫
            String[] s = {"Painting", "繪畫"};
            return s;

        } else if (
                material == Material.SIGN ||
                        material == Material.WALL_SIGN
        ) {
            // 告示牌
            String[] s = {"Sign", "告示牌"};
            return s;

        } else if (
                material == Material.FLOWER_POT ||
                        material.name().matches(".*POTTED.*")
        ) {
            // 花盆
            String[] s = {"FlowerPot", "花盆"};
            return s;

        } else if (material == Material.DISPENSER) {
            // 發射器
            String[] s = {"Dispenser", "發射器"};
            return s;

        } else if (material == Material.DROPPER) {
            // 投擲器
            String[] s = {"Dropper", "投擲器"};
            return s;

        } else if (material == Material.LEVER) {
            // 控制桿
            String[] s = {"Lever", "控制桿"};
            return s;

        } else if (material.name().matches(".*PRESSURE_PLATE.*")) {
            // 壓力版
            String[] s = {"PressurePlate", "壓力版"};
            return s;

        } else if (material.name().matches(".*BUTTON.*")) {
            // 按鈕
            String[] s = {"Button", "按鈕"};
            return s;

        } else if (material.name().matches(".*TRAPDOOR.*")) {
            // 地板門
            String[] s = {"Trapdoor", "地板門"};
            return s;

        } else if (material.name().matches(".*FENCE_GATE.*")) {
            // 柵欄門
            String[] s = {"FenceGate", "柵欄門"};
            return s;

        } else if (material == Material.DAYLIGHT_DETECTOR) {
            // 日光感測器
            String[] s = {"DaylightDetector", "日光感測器"};
            return s;

        } else if (material.name().matches(".*DOOR.*")) {
            // 門
            String[] s = {"Door", "門"};
            return s;

        } else if (material == Material.REPEATER) {
            // 紅石中繼器
            String[] s = {"Repeater", "紅石中繼器"};
            return s;

        } else if (material == Material.COMPARATOR) {
            // 紅石比較器
            String[] s = {"Comparator", "紅石比較器"};
            return s;

        } else if (material == Material.SPAWNER) {
            // 紅石比較器
            String[] s = {"Spawner", "刷怪磚"};
            return s;

        }
        String[] s = {null, null};
        return s;

    }


}



