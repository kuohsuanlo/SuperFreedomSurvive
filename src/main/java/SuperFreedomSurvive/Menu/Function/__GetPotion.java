package SuperFreedomSurvive.Menu.Function;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;

final public class __GetPotion implements Listener {

    // 選單接口
    // 藥水
    final public static void menu(Player player) {
        // 取消

        /*
        //
        // 顯示容器
        Inventory inv = Bukkit.createInventory(null, 54 , "§z藥水系統");

        ItemStack item;
        ItemMeta meta;


        //   傷害吸收
        inv.setItem(0 , getPotionMeta( "傷害吸收" , PotionEffectType.ABSORPTION , 40 , 20 , 2 , Material.SHIELD ) );
        inv.setItem(1 , getPotionMeta( "傷害吸收" , PotionEffectType.ABSORPTION , 90 , 20 , 4 , Material.SHIELD ) );
        inv.setItem(2 , getPotionMeta( "傷害吸收" , PotionEffectType.ABSORPTION , 200 , 20 , 6 , Material.SHIELD ) );


        //  海洋祝福
        inv.setItem(3 , getPotionMeta( "海洋祝福" , PotionEffectType.CONDUIT_POWER , 1 , 20 , 2 , Material.FISHING_ROD ) );
        inv.setItem(4 , getPotionMeta( "海洋祝福" , PotionEffectType.CONDUIT_POWER , 1 , 50 , 4 , Material.FISHING_ROD ) );
        inv.setItem(5 , getPotionMeta( "海洋祝福" , PotionEffectType.CONDUIT_POWER , 1 , 90 , 6 , Material.FISHING_ROD ) );


        //  海豚悠游
        inv.setItem(6 , getPotionMeta( "海豚的恩惠" , PotionEffectType.DOLPHINS_GRACE , 3 , 20 , 2 , Material.PRISMARINE_SHARD ) );
        inv.setItem(7 , getPotionMeta( "海豚的恩惠" , PotionEffectType.DOLPHINS_GRACE , 5 , 20 , 4 , Material.PRISMARINE_SHARD ) );
        inv.setItem(8 , getPotionMeta( "海豚的恩惠" , PotionEffectType.DOLPHINS_GRACE , 10 , 20 , 6 , Material.PRISMARINE_SHARD ) );


        //  挖掘加速
        inv.setItem(9 , getPotionMeta( "挖掘加速" , PotionEffectType.FAST_DIGGING , 2 , 20 , 2 , Material.GOLDEN_PICKAXE ) );
        inv.setItem(10 , getPotionMeta( "挖掘加速" , PotionEffectType.FAST_DIGGING , 4 , 20 , 4 , Material.GOLDEN_PICKAXE ) );
        inv.setItem(11 , getPotionMeta( "挖掘加速" , PotionEffectType.FAST_DIGGING , 6 , 20 , 6 , Material.GOLDEN_PICKAXE ) );


        //  抗性
        inv.setItem(12 , getPotionMeta( "抗性" , PotionEffectType.DAMAGE_RESISTANCE , 2 , 40 , 2 , Material.DIAMOND_CHESTPLATE ) );
        inv.setItem(13 , getPotionMeta( "抗性" , PotionEffectType.DAMAGE_RESISTANCE , 3 , 40 , 4 , Material.DIAMOND_CHESTPLATE ) );
        inv.setItem(14 , getPotionMeta( "抗性" , PotionEffectType.DAMAGE_RESISTANCE , 4 , 40 , 6 , Material.DIAMOND_CHESTPLATE ) );


        //  防火
        inv.setItem(15 , getPotionMeta( "防火" , PotionEffectType.FIRE_RESISTANCE , 1 , 20 , 2 , Material.BLAZE_POWDER ) );
        inv.setItem(16 , getPotionMeta( "防火" , PotionEffectType.FIRE_RESISTANCE , 1 , 50 , 4 , Material.BLAZE_POWDER ) );
        inv.setItem(17 , getPotionMeta( "防火" , PotionEffectType.FIRE_RESISTANCE , 1 , 90 , 6 , Material.BLAZE_POWDER ) );


        //  發光
        inv.setItem(18 , getPotionMeta( "發光" , PotionEffectType.GLOWING , 1 , 20 , 2 , Material.JACK_O_LANTERN ) );
        inv.setItem(19 , getPotionMeta( "發光" , PotionEffectType.GLOWING , 1 , 40 , 4 , Material.JACK_O_LANTERN ) );
        inv.setItem(20 , getPotionMeta( "發光" , PotionEffectType.GLOWING , 1 , 90 , 6 , Material.JACK_O_LANTERN ) );


        //  生命提升
        inv.setItem(21 , getPotionMeta( "生命提升" , PotionEffectType.HEALTH_BOOST , 5 , 20 , 2 , Material.ENCHANTED_GOLDEN_APPLE ) );
        inv.setItem(22 , getPotionMeta( "生命提升" , PotionEffectType.HEALTH_BOOST , 12 , 20 , 4 , Material.ENCHANTED_GOLDEN_APPLE ) );
        inv.setItem(23 , getPotionMeta( "生命提升" , PotionEffectType.HEALTH_BOOST , 20 , 20 , 6 , Material.ENCHANTED_GOLDEN_APPLE ) );


        //  力量
        inv.setItem(24 , getPotionMeta( "力量" , PotionEffectType.INCREASE_DAMAGE , 2 , 20 , 2 , Material.DIAMOND_SWORD ) );
        inv.setItem(25 , getPotionMeta( "力量" , PotionEffectType.INCREASE_DAMAGE , 5 , 20 , 4 , Material.DIAMOND_SWORD ) );
        inv.setItem(26 , getPotionMeta( "力量" , PotionEffectType.INCREASE_DAMAGE , 10 , 20 , 6 , Material.DIAMOND_SWORD ) );


        //   跳躍提升
        inv.setItem(27 , getPotionMeta( "跳躍提升" , PotionEffectType.JUMP , 3 , 20 , 2 , Material.COOKED_RABBIT ) );
        inv.setItem(28 , getPotionMeta( "跳躍提升" , PotionEffectType.JUMP , 5 , 20 , 4 , Material.COOKED_RABBIT ) );
        inv.setItem(29 , getPotionMeta( "跳躍提升" , PotionEffectType.JUMP , 10 , 20 , 6 , Material.COOKED_RABBIT ) );


        //  夜視
        inv.setItem(30 , getPotionMeta( "夜視" , PotionEffectType.NIGHT_VISION , 1 , 20 , 2 , Material.TORCH ) );
        inv.setItem(31 , getPotionMeta( "夜視" , PotionEffectType.NIGHT_VISION , 1 , 50 , 4 , Material.TORCH ) );
        inv.setItem(32 , getPotionMeta( "夜視" , PotionEffectType.NIGHT_VISION , 1 , 90 , 6 , Material.TORCH ) );


        //  持續恢復
        inv.setItem(33 , getPotionMeta( "持續恢復" , PotionEffectType.REGENERATION , 1 , 20 , 2 , Material.TOTEM_OF_UNDYING ) );
        inv.setItem(34 , getPotionMeta( "持續恢復" , PotionEffectType.REGENERATION , 1 , 50 , 4 , Material.TOTEM_OF_UNDYING ) );
        inv.setItem(35 , getPotionMeta( "持續恢復" , PotionEffectType.REGENERATION , 1 , 90 , 6 , Material.TOTEM_OF_UNDYING ) );


        //  移動加速
        inv.setItem(36 , getPotionMeta( "移動加速" , PotionEffectType.SPEED , 2 , 20 , 2 , Material.DIAMOND_HORSE_ARMOR ) );
        inv.setItem(37 , getPotionMeta( "移動加速" , PotionEffectType.SPEED , 4 , 30 , 4 , Material.DIAMOND_HORSE_ARMOR ) );
        inv.setItem(38 , getPotionMeta( "移動加速" , PotionEffectType.SPEED , 6 , 40 , 6 , Material.DIAMOND_HORSE_ARMOR ) );


        //  水下呼吸
        inv.setItem(39 , getPotionMeta( "水下呼吸" , PotionEffectType.WATER_BREATHING , 1 , 20 , 2 , Material.HEART_OF_THE_SEA ) );
        inv.setItem(40 , getPotionMeta( "水下呼吸" , PotionEffectType.WATER_BREATHING , 1 , 50 , 4 , Material.HEART_OF_THE_SEA ) );
        inv.setItem(41 , getPotionMeta( "水下呼吸" , PotionEffectType.WATER_BREATHING , 1 , 90 , 6 , Material.HEART_OF_THE_SEA ) );




        // --------------------------------------     52     --------------------------------------
        // 返回
        item = new ItemStack ( Material.NETHER_STAR );
        meta = item.getItemMeta();
        meta.setDisplayName( "§r§e返回上一頁" );
        // 寫入資料
        item.setItemMeta(meta);
        // 設置完成
        inv.setItem( 52 , item );

        // --------------------------------------     53     --------------------------------------
        // 關閉
        item = new ItemStack ( Material.END_CRYSTAL );
        meta = item.getItemMeta();
        meta.setDisplayName( "§r§e關閉選單" );
        // 寫入資料
        item.setItemMeta(meta);
        // 設置完成
        inv.setItem( 53 , item );



        // new PotionEffect( new PotionEffectType.ABSORPTION)

        // 寫入到容器頁面
        player.openInventory(inv);



        */


    }

    // 快速給予 ItemStack 接口
    final public static ItemStack getPotionMeta(String name, PotionEffectType potion_effect_type, int LV, int minute, int need, Material material) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§r§e" + name);
        meta.setLore(Arrays.asList(
                ("§r§f - 等級 " + LV + " "),
                ("§r§f - 效果 " + minute + " 分鐘"),
                ("§r§f - 需要 " + need + " 顆綠寶石")
        ));

        // 寫入效果
        //meta.addCustomEffect( potion_effect_type.createEffect( 60 * minute , LV ) , true );

        // 寫入資料
        item.setItemMeta(meta);
        // 返回
        return item;
        /*
        PotionMeta potion = (PotionMeta)item.getItemMeta();
        //meta = ;
        //PURPLE 1
        //RED 2
        //PURPLE 3
        if ( need == 2 ) {
            potion.setColor(Color.fromRGB( 255 , 192 , 203));
        } else if ( need == 4 ) {
            potion.setColor(Color.fromRGB( 238 , 44 , 44));
        } else {
            potion.setColor(Color.fromRGB( 139 , 20 , 20));
        }
        //String original = potion_effect_type.getName();
        potion.setDisplayName("§r§e" + name );
        potion.setLore( Arrays.asList(
                ("§r§f - 等級 " + LV + " "),
                ("§r§f - 效果 " + minute + " 分鐘"),
                ("§r§f - 需要 " + need + " 顆綠寶石")
        ));

        // 寫入效果
        potion.addCustomEffect( potion_effect_type.createEffect( 60 * minute , LV ) , true );


        // 寫入資料
        item.setItemMeta(potion);
        // 返回
        return item;
        */
    }

    /*

    // 阻止玩家拿走物品
    @EventHandler
    final public void onInventory__(InventoryClickEvent event) {
        // 判斷是否為玩家

        if (event.getWhoClicked() instanceof Player) {

            // 是玩家
            Player player = (Player)event.getWhoClicked();

            // 檢查容器名稱
            if ( event.getView().getTitle().equalsIgnoreCase("§z藥水系統") ) {
                // 是沒錯

                event.setCancelled(true);

                // 檢查點擊的是第幾個位置

                    //   傷害吸收
                if (event.getRawSlot() == 0) {
                    addPotionMeta( player , "傷害吸收" , PotionEffectType.ABSORPTION , 20 , 20 , 2 );
                } else if (event.getRawSlot() == 1) {
                    addPotionMeta( player , "傷害吸收" , PotionEffectType.ABSORPTION , 50 , 20 , 4 );
                } else if (event.getRawSlot() == 2) {
                    addPotionMeta( player , "傷害吸收" , PotionEffectType.ABSORPTION , 100 , 20 , 6 );

                    //  海洋祝福
                } else if (event.getRawSlot() == 3) {
                    addPotionMeta( player , "海洋祝福" , PotionEffectType.CONDUIT_POWER , 1 , 20 , 2 );
                } else if (event.getRawSlot() == 4) {
                    addPotionMeta( player , "海洋祝福" , PotionEffectType.CONDUIT_POWER , 1 , 50 , 4 );
                } else if (event.getRawSlot() == 5) {
                    addPotionMeta( player , "海洋祝福" , PotionEffectType.CONDUIT_POWER , 1 , 90 , 6 );

                    //  海豚悠游
                } else if (event.getRawSlot() == 6) {
                    addPotionMeta( player , "海豚悠游" , PotionEffectType.DOLPHINS_GRACE , 3 , 20 , 2 );
                } else if (event.getRawSlot() == 7) {
                    addPotionMeta( player , "海豚悠游" , PotionEffectType.DOLPHINS_GRACE , 5 , 20 , 4 );
                } else if (event.getRawSlot() == 8) {
                    addPotionMeta( player , "海豚悠游" , PotionEffectType.DOLPHINS_GRACE , 10 , 20 , 6 );

                    //  挖掘加速
                } else if (event.getRawSlot() == 9) {
                    addPotionMeta( player , "挖掘加速" , PotionEffectType.FAST_DIGGING , 2 , 20 , 2 );
                } else if (event.getRawSlot() == 10) {
                    addPotionMeta( player , "挖掘加速" , PotionEffectType.FAST_DIGGING , 4 , 20 , 4 );
                } else if (event.getRawSlot() == 11) {
                    addPotionMeta( player , "挖掘加速" , PotionEffectType.FAST_DIGGING , 6 , 20 , 6 );

                    //  抗性
                } else if (event.getRawSlot() == 12) {
                    addPotionMeta( player , "抗性" , PotionEffectType.DAMAGE_RESISTANCE , 2 , 40 , 2 );
                } else if (event.getRawSlot() == 13) {
                    addPotionMeta( player , "抗性" , PotionEffectType.DAMAGE_RESISTANCE , 3 , 40 , 4 );
                } else if (event.getRawSlot() == 14) {
                    addPotionMeta( player , "抗性" , PotionEffectType.DAMAGE_RESISTANCE , 4 , 40 , 6 );

                    //  防火
                } else if (event.getRawSlot() == 15) {
                    addPotionMeta( player , "防火" , PotionEffectType.FIRE_RESISTANCE , 1 , 20 , 2 );
                } else if (event.getRawSlot() == 16) {
                    addPotionMeta( player , "防火" , PotionEffectType.FIRE_RESISTANCE , 1 , 50 , 4 );
                } else if (event.getRawSlot() == 17) {
                    addPotionMeta( player , "防火" , PotionEffectType.FIRE_RESISTANCE , 1 , 90 , 6 );

                    //  發光
                } else if (event.getRawSlot() == 18) {
                    addPotionMeta( player , "發光" , PotionEffectType.GLOWING , 1 , 20 , 2 );
                } else if (event.getRawSlot() == 19) {
                    addPotionMeta( player , "發光" , PotionEffectType.GLOWING , 1 , 50 , 4 );
                } else if (event.getRawSlot() == 20) {
                    addPotionMeta( player , "發光" , PotionEffectType.GLOWING , 1 , 90 , 6 );

                    //  生命提升
                } else if (event.getRawSlot() == 21) {
                    addPotionMeta( player , "生命提升" , PotionEffectType.HEALTH_BOOST , 5 , 20 , 2 );
                } else if (event.getRawSlot() == 22) {
                    addPotionMeta( player , "生命提升" , PotionEffectType.HEALTH_BOOST , 12 , 20 , 4 );
                } else if (event.getRawSlot() == 23) {
                    addPotionMeta( player , "生命提升" , PotionEffectType.HEALTH_BOOST , 20 , 20 , 6 );

                    //  力量
                } else if (event.getRawSlot() == 24) {
                    addPotionMeta( player , "力量" , PotionEffectType.INCREASE_DAMAGE , 2 , 20 , 2 );
                } else if (event.getRawSlot() == 25) {
                    addPotionMeta( player , "力量" , PotionEffectType.INCREASE_DAMAGE , 5 , 20 , 4 );
                } else if (event.getRawSlot() == 26) {
                    addPotionMeta( player , "力量" , PotionEffectType.INCREASE_DAMAGE , 10 , 20 , 6 );

                    //   跳躍提升
                } else if (event.getRawSlot() == 27) {
                    addPotionMeta( player , "跳躍提升" , PotionEffectType.JUMP , 3 , 20 , 2 );
                } else if (event.getRawSlot() == 28) {
                    addPotionMeta( player , "跳躍提升" , PotionEffectType.JUMP , 5 , 20 , 4 );
                } else if (event.getRawSlot() == 29) {
                    addPotionMeta( player , "跳躍提升" , PotionEffectType.JUMP , 10 , 20 , 6 );

                    //  夜視
                } else if (event.getRawSlot() == 30) {
                    addPotionMeta( player , "夜視" , PotionEffectType.NIGHT_VISION , 1 , 20 , 2 );
                } else if (event.getRawSlot() == 31) {
                    addPotionMeta( player , "夜視" , PotionEffectType.NIGHT_VISION , 1 , 50 , 4 );
                } else if (event.getRawSlot() == 32) {
                    addPotionMeta( player , "夜視" , PotionEffectType.NIGHT_VISION , 1 , 90 , 6 );

                    //  持續恢復
                } else if (event.getRawSlot() == 33) {
                    addPotionMeta( player , "持續恢復" , PotionEffectType.REGENERATION , 1 , 20 , 2 );
                } else if (event.getRawSlot() == 34) {
                    addPotionMeta( player , "持續恢復" , PotionEffectType.REGENERATION , 1 , 50 , 4 );
                } else if (event.getRawSlot() == 35) {
                    addPotionMeta( player , "持續恢復" , PotionEffectType.REGENERATION , 1 , 90 , 6 );

                    //  移動加速
                } else if (event.getRawSlot() == 36) {
                    addPotionMeta( player , "移動加速" , PotionEffectType.SPEED , 2 , 20 , 2 );
                } else if (event.getRawSlot() == 37) {
                    addPotionMeta( player , "移動加速" , PotionEffectType.SPEED , 4 , 30 , 4 );
                } else if (event.getRawSlot() == 38) {
                    addPotionMeta( player , "移動加速" , PotionEffectType.SPEED , 6 , 40 , 6 );

                    //  水下呼吸
                } else if (event.getRawSlot() == 39) {
                    addPotionMeta( player , "水下呼吸" , PotionEffectType.WATER_BREATHING , 1 , 20 , 2 );
                } else if (event.getRawSlot() == 40) {
                    addPotionMeta( player , "水下呼吸" , PotionEffectType.WATER_BREATHING , 1 , 50 , 4 );
                } else if (event.getRawSlot() == 41) {
                    addPotionMeta( player , "水下呼吸" , PotionEffectType.WATER_BREATHING , 1 , 90 , 6 );

                } else if ( event.getRawSlot() == 52 ){
                    // 上一頁
                    ServerPlugin.FunctionList.Open.open( player );

                } else if ( event.getRawSlot() == 53 ){
                    // 關閉
                    event.getWhoClicked().closeInventory();

                }

            }


        }

    }
    // 快速給予效果 PotionMeta 接口
    final public static void addPotionMeta( Player player , String name , PotionEffectType potion_effect_type , int LV , int minute , int need ){
        // 檢查玩家身上物資夠不夠
        int emerald_amount = 0;
        PlayerInventory inventory = player.getInventory();
        HashMap all = inventory.all( Material.EMERALD );
        for (Object key : all.keySet()) {
            emerald_amount = emerald_amount + ((ItemStack)all.get(key)).getAmount();
        }
        if ( emerald_amount >= need ) {
            // 足夠
            // 收取綠寶石
            // 一次 1 個
            ItemStack item = new ItemStack ( Material.EMERALD , 1 );
            for (  ; need > 0 ; ){
                player.getInventory().removeItem(item);
                need = need - 1;
            }

            // 給予效果
            player.addPotionEffect( potion_effect_type.createEffect( 60 * minute * 20 , LV ) , true  );

            player.sendMessage("[藥水系統]  已給予 " + name + " 效果  lv " + LV + "  持續 " + minute + " 分鐘");

        } else {
            // 不足夠
            player.sendMessage("[藥水系統]  所需物資不夠! 需要 " + need + " 綠寶石");
        }

    }


    */


}
