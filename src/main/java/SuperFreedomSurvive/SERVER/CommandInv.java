package SuperFreedomSurvive.SERVER;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Collection;

public class CommandInv implements CommandExecutor {

    @Override
    final public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender.hasPermission("command.inv")) {
            // 有

            // 確認使用指令的是玩家
            if (sender instanceof Player) {
                // 發出命令的 是玩家
                // 資料狀態 重新獲取為 玩家
                Player player_me = (Player) sender;

                // 確認 args[0] 是否存在
                if (args.length == 1) {
                    // args[0] 有資料
                    if (args[0].matches("[_a-zA-Z0-9-]{1,40}")) {
                        // 符合

                        Player player = null;
                        Collection collection = Bukkit.getServer().getOnlinePlayers();
                        for (Object o : collection) {
                            Player player_ = ((Player) o);

                            if (player_.getName().equals(args[0])) {
                                // 是指定的玩家
                                player = player_;
                                break;
                            }
                        }

                        if (player != null) {
                            Inventory inv = Bukkit.createInventory(null, 45, "§z該名玩家的庫存副本");

                            // 裝備
                            inv.setItem( 0 , player.getInventory().getHelmet() );
                            inv.setItem( 1 , player.getInventory().getChestplate() );
                            inv.setItem( 2 , player.getInventory().getLeggings() );
                            inv.setItem( 3 , player.getInventory().getBoots() );

                            // 副手
                            inv.setItem( 5 , player.getInventory().getItemInOffHand() );

                            // 主手
                            inv.setItem( 7 , player.getInventory().getItemInMainHand() );

                            // 庫存
                            for ( int i = 0 , s = 27 ; i < s ; ++i ) {
                                inv.setItem( 9 + i , player.getInventory().getItem( 9 + i ) );
                            }
                            for ( int i = 0 , s = 9 ; i < s ; ++i ) {
                                inv.setItem( 36 + i , player.getInventory().getItem(i) );
                            }

                            player_me.openInventory(inv);


                        } else {
                            sender.sendMessage("[查看庫存]  §c該玩家不存在/沒有上線");
                        }

                    } else {
                        sender.sendMessage("[查看庫存]  §c不正確的玩家名稱");
                    }

                } else {
                    sender.sendMessage("[查看庫存]  §c格式 /inv <player>");
                }

            } else {
                sender.sendMessage("[查看庫存]  §c只能在遊戲中使用");
            }

        } else {
            // 沒有
            sender.sendMessage("[查看庫存]  §c沒有權限");
        }

        // 返回 true
        return true;

    }

}
