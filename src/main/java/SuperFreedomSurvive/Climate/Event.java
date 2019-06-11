package SuperFreedomSurvive.Climate;

import org.bukkit.WeatherType;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.WorldLoadEvent;

public class Event implements Listener {
    // 所有共同世界

    // 玩家登入事件
    @EventHandler
    final public void onPlayerJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        player.sendTitle(
                "",
                "§e" + Time.getMonth() + " 月 " + Time.getDay() + " 日 " + Math.round( Time.getTime() / 20 ) + " 秒  " + Time.getSeason().getName(),
                0,
                90,
                10
        );

        if ( event.getPlayer().getWorld().getEnvironment() == World.Environment.NORMAL ) {
            // 是否顯示降雨
            if (Time.getWeather()) {
                player.setPlayerWeather(WeatherType.DOWNFALL);
            } else {
                player.setPlayerWeather(WeatherType.CLEAR);
            }
        }
    }

    // 世界加載區塊時
    @EventHandler(priority = EventPriority.HIGHEST)
    final public void onChunkLoadEvent(ChunkLoadEvent event) {
        World world = event.getWorld();

        // 世界類型是否為主世界
        if ( world.getEnvironment() == World.Environment.NORMAL ) {
            // 轉換區塊為當前季節
            Function.chunkBiomeConversionSeason(event.getChunk());
        }
    }

    // 世界加載時
    @EventHandler(priority = EventPriority.HIGHEST)
    final public void onWorldLoadEvent(WorldLoadEvent event) {
        World world = event.getWorld();

        // 世界類型是否不為判定時間的世界
        if ( world != Time.world ) {
            // 將世界時間調整為基準時間相同
            world.setFullTime( Time.world.getFullTime() );
        }
    }

    // 世界天氣變化時
    @EventHandler(priority = EventPriority.HIGHEST)
    final public void onWeatherChangeEvent(WeatherChangeEvent event) {
        //event.getHandlers().

        //event.setCancelled(true);
/*
        World world = event.getWorld();

        // 檢查是否是依照判斷依據的世界
        if (world == Time.world) {

        } else {

        }

        Bukkit.getLogger().info(world.getName() + ":");
        if ( event.toWeatherState()) {
            Bukkit.getLogger().info("    start");
        } else {
            Bukkit.getLogger().info("    end");

        }
*/
        //event.setCancelled(true);

        //Time.setWeather(event.toWeatherState());
/*
        // 保持統一
        if (Time.getWeather()) {
            event.getWorld().setStorm(true);

        } else {
            event.getWorld().setStorm(false);

        }

 */
    }

    @EventHandler( priority = EventPriority.HIGHEST )
    final public void onBlockGrowEvent(BlockGrowEvent event){
        //  當一個方塊在世界中自然生長(如小麥生長)
        switch (Time.getSeason()) {
            case SPRING:
                // 春季
                break;

            case SUMMER:
                // 夏季
                break;

            case FALL:
                // 秋季
                // 1/8 機率生長
                if ((int) (Math.random() * 8) != 1 ) {
                    event.setCancelled(true);
                }
                break;

            case WINTER:
                // 冬季
                // 完全不生長
                event.setCancelled(true);
                break;

            default:
                break;

        }
    }




}
