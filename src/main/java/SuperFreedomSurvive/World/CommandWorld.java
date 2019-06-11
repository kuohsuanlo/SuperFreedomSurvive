package SuperFreedomSurvive.World;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

final public class CommandWorld implements CommandExecutor {

    @Override
    final public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender.hasPermission("command.world")) {
            // 有
            // 確認 args[0] 是否存在
            if (args.length == 2) {
                // args[0] 有資料
                if (args[1].matches("[_a-zA-Z0-9-/]{1,40}")) {
                    // 符合
                    if (args[0].equals("delete")) {
                        // 刪除該世界
                        Data.Unload(args[1], false);
                        Data.Delete(args[1]);
                        sender.sendMessage("[多世界系統]  嘗試刪除完成");

                    } else if (args[0].equals("unload")) {
                        // 卸除該世界
                        Data.Unload(args[1], true);
                        sender.sendMessage("[多世界系統]  嘗試卸除完成");

                    } else if (args[0].equals("tp")) {
                        // 傳送到該世界
                        // 確認使用指令的是玩家
                        if (sender instanceof Player) {
                            // 發出命令的 是玩家
                            // 資料狀態 重新獲取為 玩家
                            Player player = (Player) sender;
                            SuperFreedomSurvive.Player.Teleport.SpawnLocation(player, args[1]);
                            sender.sendMessage("[多世界系統]  嘗試傳送完成");
                        }

                    } else {
                        sender.sendMessage("[多世界系統]  §c格式 /world <delete|unload|tp> <world_name>");
                    }
                } else {
                    sender.sendMessage("[多世界系統]  §c不正確的世界名稱");
                }
            } else {
                sender.sendMessage("[多世界系統]  §c格式 /world <delete|unload|tp> <world_name>");
            }

        } else {
            // 沒有
            sender.sendMessage("[多世界系統]  §c沒有權限");

        }


        // 返回 true
        return true;

    }


}
