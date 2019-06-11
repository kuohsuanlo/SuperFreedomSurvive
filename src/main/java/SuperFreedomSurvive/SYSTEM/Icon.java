package SuperFreedomSurvive.SYSTEM;

import org.bukkit.Bukkit;
import org.bukkit.util.CachedServerIcon;

import java.io.File;

public class Icon {
    // 取得伺服器圖標

    static public CachedServerIcon Icon = getIcon();

    private static CachedServerIcon getIcon() {
        // 加載顯示在清單的圖像
        try {
            return Bukkit.loadServerIcon(new File("icon.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
