package SuperFreedomSurvive.World;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.event.world.WorldLoadEvent;

final public class Event implements Listener {

    // 世界事件
    @EventHandler(priority = EventPriority.HIGHEST)
    final public void onWorldLoadEvent(WorldLoadEvent event) {

        try {

            //event.getWorld().setAutoSave(false); // 不使自動儲存
            event.getWorld().setKeepSpawnInMemory(true); // 不使用記憶體緩存

            Data.Setting(event.getWorld()); // 設定資料

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    final public void onWorldInitEvent(WorldInitEvent event) {

        try {

            //event.getWorld().setAutoSave(false); // 不使自動儲存
            event.getWorld().setKeepSpawnInMemory(true); // 不使用記憶體緩存

            Data.Setting(event.getWorld()); // 設定資料

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

/*
    // 區塊事件
    @EventHandler( priority = EventPriority.HIGHEST )
    final public void onChunkLoadEvent(ChunkLoadEvent event){

        event.getWorld().setKeepSpawnInMemory( true ); // 不使用記憶體緩存

        ServerPlugin.World.Control.Setting( event.getWorld() ); // 設定資料


    }
*/


}
