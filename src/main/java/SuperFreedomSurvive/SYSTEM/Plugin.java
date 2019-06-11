package SuperFreedomSurvive.SYSTEM;

import org.bukkit.Bukkit;

public class Plugin {

    public static org.bukkit.plugin.Plugin get() {
        return Bukkit.getPluginManager().getPlugin("SuperFreedomSurvive"); // 取得插件
    }

}
