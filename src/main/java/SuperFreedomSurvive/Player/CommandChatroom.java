package SuperFreedomSurvive.Player;

import SuperFreedomSurvive.SYSTEM.MySQL;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

final public class CommandChatroom implements CommandExecutor {

    @Override
    final public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // 確認使用指令的是玩家
        if (sender instanceof Player) {
            // 發出命令的 是玩家
            // 資料狀態 重新獲取為 玩家
            Player player = (Player) sender;

            // 確認 args[0] 是否存在
            if (args.length == 1) {
                // args[0] 有資料
                if (args[0].matches("[a-zA-Z0-9]{10}")) {
                    // 符合

                    try {
                        Connection con = MySQL.getConnection(); // 連線 MySQL
                        Statement sta = con.createStatement(); // 取得控制庫

                        ResultSet res = sta.executeQuery("SELECT * FROM `player-chatroom_cache` WHERE `ID` = '" + args[0] + "' AND `Player` = '" + player.getName() + "' AND `Time` >= '" + (new Date().getTime() - 1000 * 30) + "' LIMIT 1");
                        // 跳到最後一行
                        res.last();
                        // 最後一行 行數 是否 > 0
                        if (res.getRow() > 0) {
                            // 檢查資料
                            // 跳回 初始行 必須使用 next() 才能取得資料
                            res.beforeFirst();
                            // 行數下一行
                            res.next();

                            String Player_Join = res.getString("Player_Join");
                            String ID = res.getString("ID");

                            res.close(); // 關閉查詢

                            res = sta.executeQuery("SELECT * FROM `player-chatroom_data` WHERE `ID` = '" + ID + "' AND `Player` = '" + Player_Join + "' LIMIT 1");
                            // 跳到最後一行
                            res.last();
                            // 最後一行 行數 是否 > 0
                            if (res.getRow() > 0) {
                                // 檢查人數

                                res.close(); // 關閉查詢

                                res = sta.executeQuery("SELECT * FROM `player-chatroom_data` WHERE `ID` = '" + ID + "' LIMIT 19");
                                // 跳到最後一行
                                res.last();
                                // 最後一行 行數 是否 > 0
                                if (res.getRow() >= 18) {
                                    // 檢查人數

                                    res.close(); // 關閉查詢

                                    player.sendMessage("[聊天室]  §c人數已滿");

                                } else {
                                    res.close(); // 關閉查詢

                                    res = sta.executeQuery("SELECT * FROM `player-chatroom_data` WHERE `Player` = '" + player.getName() + "' AND `ID` = '" + ID + "'");
                                    res.last();
                                    // 最後一行 行數 是否 > 0
                                    if (res.getRow() > 0) {
                                        // ! 有資料

                                        res.close(); // 關閉查詢

                                        player.sendMessage("[聊天室]  §c你已經在此聊天室裡");
                                        return true;

                                    } else {

                                        res.close(); // 關閉查詢

                                        res = sta.executeQuery("SELECT * FROM `player-chatroom_data` WHERE `Player` = '" + player.getName() + "' LIMIT 1");
                                        // 跳到最後一行
                                        res.last();
                                        // 最後一行 行數 是否 > 0
                                        if (res.getRow() > 0) {
                                            // 已經有加入聊天室
                                            SuperFreedomSurvive.Player.Chat.chatroom_send(player, "§b[聊天室]  " + player.getName() + " 離開了聊天室");
                                            //player.sendMessage("[聊天室]  預設聊天頻道是公開頻道");

                                        }

                                        res.close(); // 關閉查詢

                                        sta.executeUpdate("DELETE FROM `player-chatroom_data` WHERE `Player` = '" + player.getName() + "' ");
                                        sta.executeUpdate("DELETE FROM `player-chatroom_cache` WHERE `Player_Join` = '" + player.getName() + "' OR `Player` = '" + player.getName() + "'");

                                        sta.executeUpdate("INSERT INTO `player-chatroom_data` (`Player`, `ID`) VALUES ('" + player.getName() + "', '" + ID + "')");
                                        //player.sendMessage("[聊天室]  成功加入");
                                        SuperFreedomSurvive.Player.Chat.chatroom_send(player, "§b[聊天室]  " + player.getName() + " 加入了聊天室");
                                        player.sendMessage("§b[聊天室]  預設聊天頻道是聊天室 發送訊息開頭加上 ` 或是 ~ 則是公開頻道");


                                    }

                                }

                            } else {
                                player.sendMessage("[聊天室]  §c沒有發送請求/請求過時");

                            }

                        } else {
                            player.sendMessage("[聊天室]  §c沒有發送請求/請求過時");

                        }

                        res.close(); // 關閉查詢
                        sta.close(); // 關閉連線

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }


                }

            } else {
                // 錯誤的指令
                player.sendMessage("[聊天室]  §c錯誤的格式");
            }

        }
        return true;
    }
}

