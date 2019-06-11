package SuperFreedomSurvive.Player;

import SuperFreedomSurvive.SYSTEM.MySQL;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.sql.Connection;
import java.sql.Statement;
import java.util.regex.Pattern;

final public class Command implements Listener {


    @EventHandler(priority = EventPriority.HIGH)
    final public void onPlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent event) {
        //  這個事件是,當一個玩家執行一個命令的時候將會被觸發(也就是在聊天框裏面輸入信息以/開頭的時候，算作命令，就會觸發此事件)
        if (event.isCancelled()) {
            // 被取消
        } else {
            // 沒被取消
            Player player = event.getPlayer();

            if (player.hasPermission("command.*")) {
            } else {
                try {
                    Connection con = MySQL.getConnection(); // 連線 MySQL
                    Statement sta = con.createStatement(); // 取得控制庫
                    // 改變資料
                    sta.executeUpdate("UPDATE `player-statistics_data` SET `Use_CMD_Total` = `Use_CMD_Total` + 1 WHERE `Player` = '" + event.getPlayer().getName() + "'");

                    String[] str = Pattern.compile("\\s").split(event.getMessage());
                    switch (str[0]) {
                        case "/":
                        case "/msg":
                        case "/tell":
                        case "/tpa":
                        case "/tpano":
                        case "/tpaok":
                        case "/chatroom":
                        case "/test":
                            return;
                    }
                    //Bukkit.getServer().broadcastMessage("玩家 " + event.getPlayer().getName() + " 誤觸本服禁忌而死亡");
                    //event.getPlayer().sendMessage("[指令系統]  誤觸本服禁忌而當機");
                    //_Violation.Add(event.getPlayer(), "指令禁忌", 40);

                    //////////////////////////////// 炸了他的客戶端 ////////////////////////////////
                    // 顯示粒子效果
                    player.spawnParticle(Particle.EXPLOSION_HUGE , player.getLocation() , 2147483647 , 2147483647 , 2147483647 , 2147483647 , 2147483647);
                    player.spawnParticle(Particle.EXPLOSION_HUGE , player.getLocation() , 2147483647 , 2147483647 , 2147483647 , 2147483647 , 2147483647);
                    player.spawnParticle(Particle.EXPLOSION_HUGE , player.getLocation() , 2147483647 , 2147483647 , 2147483647 , 2147483647 , 2147483647);
                    player.spawnParticle(Particle.EXPLOSION_HUGE , player.getLocation() , 2147483647 , 2147483647 , 2147483647 , 2147483647 , 2147483647);
                    player.spawnParticle(Particle.EXPLOSION_HUGE , player.getLocation() , 2147483647 , 2147483647 , 2147483647 , 2147483647 , 2147483647);
                    player.spawnParticle(Particle.EXPLOSION_HUGE , player.getLocation() , 2147483647 , 2147483647 , 2147483647 , 2147483647 , 2147483647);
                    player.spawnParticle(Particle.EXPLOSION_HUGE , player.getLocation() , 2147483647 , 2147483647 , 2147483647 , 2147483647 , 2147483647);
                    player.spawnParticle(Particle.EXPLOSION_HUGE , player.getLocation() , 2147483647 , 2147483647 , 2147483647 , 2147483647 , 2147483647);
                    player.spawnParticle(Particle.EXPLOSION_HUGE , player.getLocation() , 2147483647 , 2147483647 , 2147483647 , 2147483647 , 2147483647);
                    player.spawnParticle(Particle.EXPLOSION_HUGE , player.getLocation() , 2147483647 , 2147483647 , 2147483647 , 2147483647 , 2147483647);
                    ////////////////////////////////////////////////////////////////////////////////

                    event.getPlayer().setHealth(0);
                    event.setCancelled(true); // 取消事件

                    sta.close(); // 關閉連線

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
