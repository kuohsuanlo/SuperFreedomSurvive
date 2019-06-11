//  ############################################# 傳送系統 #############################################  //
package SuperFreedomSurvive.Player;

import SuperFreedomSurvive.SYSTEM.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

final public class CommandTpaNo implements CommandExecutor {
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
                // 檢查字串是否符合規則
                if (args[0].matches("[_a-zA-Z0-9-]{1,16}")) {
                    // 符合

                    // 檢驗 args[0] 是否為 player_me
                    // 阻止 自己 傳送 自己
                    // 檢查字串是否相同
                    if (player_me.getName().equals(args[0])) {
                        // ! 禁止傳送 自己 傳送 自己
                        player_me.sendMessage("[傳送系統]  §a" + args[0] + " §c是你自己...");

                    } else {
                        // 允許繼續執行代碼
                        // 檢查 是否真的 有申請傳送
                        try {
                            Connection con = MySQL.getConnection(); // 連線 MySQL
                            Statement sta = con.createStatement(); // 取得控制庫

                            // 查詢 並返回結果
                            ResultSet res = sta.executeQuery("SELECT * FROM `player-transfer_cache` WHERE `Player` = '" + player_me.getName() + "' AND `Player_Tp` = '" + args[0] + "' AND `Time` >= '" + (new Date().getTime() - 1000 * 30) + "' AND `Result` = 0");
                            // 取得查詢筆數
                            int count = 0;
                            while (res.next()) {
                                ++count;
                            }

                            res.close(); // 關閉查詢

                            // 是否 > 0
                            if (count > 0) {
                                // 有資料

                                // 更動資料
                                sta.executeUpdate("UPDATE `player-transfer_cache` SET `Result` = '2' WHERE `Player` = '" + player_me.getName() + "' AND `Player_Tp` = '" + args[0] + "' AND `Time` >= '" + (new Date().getTime() - 1000 * 30) + "' AND `Result` = '0'");

                                // 向發送請求玩家 顯示訊息
                                Player player_to = Bukkit.getServer().getPlayer(args[0]);
                                // 被傳送玩家是否在線上
                                if (player_to == null) {
                                    // ! 離線
                                    // 指令使用者 發送訊息
                                    player_me.sendMessage("[傳送系統]  §a" + args[0] + " §c不在線上");

                                } else {
                                    // 線上狀態
                                    // 被傳送者 發送訊息
                                    player_to.sendMessage("[傳送系統]  §a" + player_me.getName() + "§f 手動拒絕了你的請求");
                                    // 指令使用者 發送訊息
                                    player_me.sendMessage("[傳送系統]  已拒絕 §a" + args[0]);

                                }


                            } else {
                                // 無資料
                                player_me.sendMessage("[傳送系統]  §a" + args[0] + " §c沒有發送請求/請求過時");
                            }

                            res.close(); // 關閉查詢
                            sta.close(); // 關閉連線

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                    }
                } else {
                    // ! 不符合正規
                    player_me.sendMessage("[傳送系統]  §c請輸入 a~Z_- 的玩家名稱");
                }
            } else {
                // ! args[0] 沒有資料
                player_me.sendMessage("[傳送系統]  §c請輸入要 拒絕傳送 的玩家名稱");
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
