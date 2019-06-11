//  ############################################# 傳送系統 #############################################  //
package SuperFreedomSurvive.Player;

import SuperFreedomSurvive.SYSTEM.MySQL;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

final public class CommandTpa implements CommandExecutor {
    static ConsoleCommandSender console = Bukkit.getConsoleSender();

    @Override
    final public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        // 確認使用指令的是玩家
        if (sender instanceof Player) {
            // 發出命令的 是玩家
            // 資料狀態 重新獲取為 玩家
            Player player_me = (Player) sender;

            // 確認 args[0] 是否存在
            if (args.length == 1) {
                // args[0] 有資料
                if (args[0].matches("[_a-zA-Z0-9-]{1,16}")) {
                    // 符合

                    // 檢驗 args[0] 是否為 player_me
                    // 阻止 自己 傳送 自己
                    // 檢查字串是否符合規則
                    // 檢查字串是否相同
                    if (player_me.getName().equals(args[0])) {
                        // ! 禁止傳送 自己 傳送 自己
                        player_me.sendMessage("[傳送系統]  §a" + args[0] + " §c是你自己...");

                    } else {
                        // 允許使用傳送

                        // 確認 線上 狀態中
                        Player player_to = (Bukkit.getServer().getPlayer(args[0]));
                        if (player_to == null) {
                            // ! 不存在
                            player_me.sendMessage("[傳送系統]  §c玩家 §a" + args[0] + " §c不在線上");
                            //player_me.sendMessage("[傳送系統] " + player_me.getAddress() + " ");

                        } else {
                            // 線上 中

                            //寫入資料庫 供 CommandTpaNo & CommandTpaOk 使用
                            // 操作MySQL
                            try {
                                Connection con = MySQL.getConnection(); // 連線 MySQL
                                Statement sta = con.createStatement(); // 取得控制庫


                                //檢查是否已經有請求在等候
                                ResultSet res = sta.executeQuery("SELECT * FROM `player-transfer_cache` WHERE `Player` = '" + args[0] + "' AND `Player_Tp` = '" + player_me.getName() + "' AND `Time` >= '" + (new Date().getTime() - 1000 * 30) + "' AND `Result` = 0");
                                // 取得查詢筆數
                                int count = 0;
                                while (res.next()) {
                                    ++count;
                                }

                                res.close(); // 關閉查詢

                                // 是否 > 0
                                if (count > 0) {
                                    // ! 有資料
                                    // 對 使用指令 的玩家 發出提示
                                    player_me.sendMessage("[傳送系統]  §c傳送到 §a" + args[0] + " §c的請求 目前已經存在");

                                } else {
                                    // 無資料
                                    // 插入一個項目
                                    sta.executeUpdate("INSERT INTO `player-transfer_cache` (`Player_Tp`, `Player`, `Time`) VALUES ('" + player_me.getName() + "', '" + args[0] + "', " + new Date().getTime() + ")");

                                    // 對 使用指令 的玩家 發出提示
                                    player_me.sendMessage("[傳送系統]  已對玩家 §a" + args[0] + "§f 發送傳送請求");

                                    // 對 要傳送   的玩家 發出 可點擊文字  [傳送系統]  玩家 A 希望傳送到你的座標位置   (接受)  (拒絕)
                                    // 發送特殊訊息
                                    TextComponent text = new TextComponent("[傳送系統]  玩家 §a" + player_me.getName() + "§f 希望傳送到你的座標位置 ");

                                    TextComponent text_y = new TextComponent(" (接受) ");
                                    text_y.setColor(ChatColor.GREEN);
                                    text_y.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("點擊 允許此傳送(有效30秒內)").create()));
                                    text_y.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpaok " + player_me.getName()));

                                    TextComponent text_n = new TextComponent(" (拒絕) ");
                                    text_n.setColor(ChatColor.RED);
                                    text_n.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("點擊 拒絕此傳送 對方會看到被拒絕訊息(有效30秒內)").create()));
                                    text_n.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpano " + player_me.getName()));

                                    Bukkit.getPlayer(args[0]).sendMessage(text, text_y, text_n);
                                    //Bukkit.dispatchCommand( console , "tellraw " + args[0] + " [{\"text\":\"[傳送系統]  玩家 §a" + player_me.getName() + "§f 希望傳送到你的座標位置 \"},{\"text\":\" (接受) \",\"color\":\"green\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/tpaok " + player_me.getName() + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"點擊 允許此傳送(有效30秒)\",\"color\":\"green\"}]}}},{\"text\":\" (拒絕) \",\"color\":\"red\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/tpano " + player_me.getName() + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"點擊 拒絕此傳送 對方會看到被拒絕訊息(有效30秒)\",\"color\":\"red\"}]}}}]" );


                                    // 釋放 MySQL command_tpa 記憶體
                                    // 刪除時間超過 30 秒的全部數據
                                    sta.executeUpdate("DELETE FROM `player-transfer_cache` WHERE `Time` <= '" + (new Date().getTime() - 1000 * 30) + "'");

                                }

                                res.close(); // 關閉查詢
                                sta.close(); // 關閉連線

                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                } else {
                    // ! 不符合正規
                    player_me.sendMessage("[傳送系統]  §c請輸入 a~Z_- 的玩家名稱");
                }
            } else {
                // ! args[0] 沒有資料
                player_me.sendMessage("[傳送系統]  §c請輸入要 傳送過去 的玩家名稱");
                return false;
            }
        } else {
            // ! 發出命令的人 不是玩家
            sender.sendMessage("[傳送系統]  §c此指令只能在遊戲中使用");
        }

        // 返回 true
        return true;
    }


}
