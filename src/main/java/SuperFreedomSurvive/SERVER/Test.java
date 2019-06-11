package SuperFreedomSurvive.SERVER;

import SuperFreedomSurvive.Additional.sendMIDI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;

import java.io.File;
import java.util.ArrayList;

final public class Test implements CommandExecutor {

    @Override
    final public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        //ServerPlugin.Player.Delete.All( player );

        if (args.length > 0) {


            //ItemStack item = player.getInventory().getItemInMainHand();
            //ItemMeta meta = item.getItemMeta();


            if (args[0].equals("a")) {
                sendMIDI send_midi = new sendMIDI(new File("./midi/" + "0" + ".mid"), new ArrayList<Player>(Bukkit.getServer().getOnlinePlayers()) , true , 1F );
                if ( send_midi.isOk() ) {
                    send_midi.setReplay(true);
                    send_midi.start();
                }

            } else if (args[0].equals("b")) {
                sendMIDI send_midi = new sendMIDI(new File("./midi/" + "0" + ".mid"), new ArrayList<Player>(Bukkit.getServer().getOnlinePlayers()) , false , 1F );
                if ( send_midi.isOk() ) {
                    send_midi.setReplay(true);
                    send_midi.start();
                }

            }
        }
        return true;
    }
}