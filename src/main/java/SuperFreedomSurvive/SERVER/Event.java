package SuperFreedomSurvive.SERVER;

import SuperFreedomSurvive.SYSTEM.Icon;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;
import org.bukkit.event.server.ServerListPingEvent;
import java.math.BigDecimal;


// ######　　　　　　　禁止世界全部爆炸　　　　　　 ######
final public class Event implements Listener {
    @EventHandler
    final public void onBlockExplodeEvent(BlockExplodeEvent event) {
        //  未知的 方塊爆炸
        // 禁止事件 改動資料
        event.setCancelled(true);

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    final public void onServerListPingEvent(ServerListPingEvent event) {
        // 訪問伺服器消息
        //event.setMotd("§e飛萌插件生存伺服器 100%繁體圖形介面 0%指令\n§e當前時間: " + Time.getMonth() + " 月 " + Time.getDay() + " 日 " + Math.round( Time.getTime() / 20 ) + " 秒  " + Time.getSeason().getName() );
        event.setMotd("§e長期內部重整中");
        event.setServerIcon( Icon.Icon );
        //event.set

    }

    /*
    @EventHandler
    final public void onPrepareAnvilEvent(PrepareAnvilEvent event){
        // 提醒 修改箱子的名稱
        Player player = (Player)event.getView().getPlayer();

        // 顯示訊息
        player.sendTitle(
                "",
                "小心修改名稱 若取的跟某介面名稱一樣 會無法正常使用容器",
                0,
                50 ,
                5
        );

    }

*/
/*
    @EventHandler
    final public void onEntityExplodeEvent(EntityExplodeEvent event) {
        // 當爆炸發生
        // 禁止事件 改動資料
        event.setCancelled(true);


        List block_list = event.blockList();

        Iterator iterator = block_list.listIterator();



        //
        new BukkitRunnable() {
            @Override
            final public void run() {
                //
                //getLocation​()
                String w  = "";
            }
        }.runTaskTimer( (Plugin) this,20L , 20L );


    }
*/

    @EventHandler
    final public void onPlayerJoinEvent(PlayerJoinEvent event) {
        // 玩家加入遊戲事件
        //event.getPlayer().sendMessage("[公告]  §e本伺服器已經放棄在巴哈招募玩家,請各位推廣吧...");
        event.getPlayer().sendMessage("[提示]  §eShift + F鍵 可以叫出選單");

    }


    // 掉落太深立即造成 -100 傷害
    @EventHandler
    final public void onPlayerMoveEvent(PlayerMoveEvent event) {
        // 玩家移動事件
        // 取得座標


        //int X = new BigDecimal(event.getPlayer().getLocation().getX()).setScale(0, BigDecimal.ROUND_FLOOR).intValue();
        int Y = new BigDecimal(event.getPlayer().getLocation().getY()).setScale(0, BigDecimal.ROUND_FLOOR).intValue();
        //int Z = new BigDecimal(event.getPlayer().getLocation().getZ()).setScale(0, BigDecimal.ROUND_FLOOR).intValue();
        Player player = event.getPlayer();


        // 掉太深 即死
        if (Y < -1000) {
            //if (  player.getHealth() >= 40 ) {
            player.setHealth(0);
            //}
        }


    }


/*
    // 創建傳送門
    @EventHandler
    final public void onPortalCreateEvent(PortalCreateEvent event) {
        //event.setCancelled(true);
    }
*/


    // 使用生怪蛋召喚生物
    @EventHandler
    final public void onCreatureSpawnEvent(CreatureSpawnEvent event) {
        if (event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG) {
            // 是
            if (
                    event.getEntityType() == EntityType.PUFFERFISH ||
                    event.getEntityType() == EntityType.SALMON ||
                    event.getEntityType() == EntityType.COD ||
                    event.getEntityType() == EntityType.TROPICAL_FISH
            ) {

            } else {
                event.setCancelled(true);
            }

        }
    }




/*
    @EventHandler
    final public void onInventoryOpenEvent(InventoryOpenEvent event) {
            Player player = (Player)event.getPlayer();


        ItemStack item = new ItemStack(Material.WRITABLE_BOOK);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName( "000" );
        // 寫入資料
        item.setItemMeta(meta);

        event.getView().getTopInventory().setItem(0,item);

        player.sendMessage(event.getView().getTopInventory().getType().name());

    }
    */
/*
@EventHandler
final public void onInventoryClickEvent(InventoryClickEvent event) {
    try {
        //  點擊容器中的欄位時
        if (event.getWhoClicked() instanceof Player) {
            if (event.getInventory().getType() == InventoryType.ANVIL) {
                // 鐵鉆事件

                //new BukkitRunnable() {
                //    @Override
                //    final public void run() {

                ItemStack item = new ItemStack(Material.WRITABLE_BOOK);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName( "000" );
                // 寫入資料
                item.setItemMeta(meta);

                event.getInventory().setItem(2 , item );
                //    }

                //}.runTaskLater(ServerPlugin.SYSTEM.Plugin.get(), 40L);
            }
        }
    } catch (Exception ex) {
        // 出錯
        //player.sendMessage(ex.getMessage());
    }
}
*/
/*
    // 禁止所有怪物蛋更改生怪磚
    @EventHandler( priority = EventPriority.HIGHEST )
    final public void onPlayerInteractEvent(PlayerInteractEvent event) {
        try {
            // 是否為右鍵類型
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (event.getClickedBlock().getType() == Material.SPAWNER) {
                    event.setCancelled(true);
                }
            }
        } catch (Exception ex) {
        }
    }
*/


    // 點擊存民


}
