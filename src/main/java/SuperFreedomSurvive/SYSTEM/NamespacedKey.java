package SuperFreedomSurvive.SYSTEM;

final public class NamespacedKey {

    static public org.bukkit.NamespacedKey getKey(String key_name) {
        // 取得KEY
        return new org.bukkit.NamespacedKey(SuperFreedomSurvive.SYSTEM.Plugin.get(), key_name);
    }
}
