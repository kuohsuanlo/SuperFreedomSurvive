package SuperFreedomSurvive.Menu.Function;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class Help implements Listener {
    // 伺服器功能一覽/介紹

    final public static void menu(Player player) {
        // 顯示容器
        Inventory inv = Bukkit.createInventory(null, 54, "§z功能一覽/介紹");

        ItemStack item;
        ItemMeta meta;


        // --------------------------------------     0     --------------------------------------
        item = new ItemStack(Material.WOODEN_HOE);
        meta = item.getItemMeta();
        meta.setDisplayName("§e圈領地用");
        meta.setLore(Arrays.asList(
                ("§r§f - 行為: 左鍵 -> 右鍵 -> 選單 -> 領地系統 -> 創建領地"),
                ("§r§f - 手持: 顯示附近的領地範圍 層數0優先")
        ));
        // 寫入資料
        item.setItemMeta(meta);
        // 設置完成
        inv.setItem(0, item);

        // --------------------------------------     1     --------------------------------------
        item = new ItemStack(Material.WOODEN_SHOVEL);
        meta = item.getItemMeta();
        meta.setDisplayName("§e圈子領地用");
        meta.setLore(Arrays.asList(
                ("§r§f - 行為: 左鍵 -> 右鍵 -> 選單 -> 領地系統 -> 創建子領地"),
                ("§r§f - 手持: 顯示附近的領地範圍 層數20優先")
        ));
        // 寫入資料
        item.setItemMeta(meta);
        // 設置完成
        inv.setItem(1, item);

        // --------------------------------------     2     --------------------------------------
        item = new ItemStack(Material.ENDER_EYE);
        meta = item.getItemMeta();
        meta.setDisplayName("§e前往更多世界 / 自己的專屬世界");
        meta.setLore(Arrays.asList(
                ("§r§f - 行為: 選單 -> 多世界系統"),
                ("§r§f   你可以: 花費綠寶石 購買個人世界"),
                ("§r§f           查看/前往 伺服器提供的世界")
        ));
        // 寫入資料
        item.setItemMeta(meta);
        // 設置完成
        inv.setItem(2, item);

        // --------------------------------------     3     --------------------------------------
        item = new ItemStack(Material.LIGHT_GRAY_BANNER);
        meta = item.getItemMeta();
        meta.setDisplayName("§e設置 / 傳送定點");
        meta.setLore(Arrays.asList(
                ("§r§f - 行為: 選單 -> 定點系統"),
                ("§r§f   你可以: 設置個人定點 並隨時傳送到該位置"),
                ("§r§f           查看/前往 其他人公開的定點位置"),
                ("§r§f           設置公開定點 讓其他人傳送到此位置")
        ));
        // 寫入資料
        item.setItemMeta(meta);
        // 設置完成
        inv.setItem(3, item);

        // --------------------------------------     4     --------------------------------------
        item = new ItemStack(Material.SIGN);
        meta = item.getItemMeta();
        meta.setDisplayName("§e更改告示牌內容");
        meta.setLore(Arrays.asList(
                ("§r§f - 行為: Shift+右鍵 點擊告示牌"),
                ("§r§f   你可以: 編輯/更改內容")
        ));
        // 寫入資料
        item.setItemMeta(meta);
        // 設置完成
        inv.setItem(4, item);

        // --------------------------------------     5     --------------------------------------
        item = new ItemStack(Material.ARMOR_STAND);
        meta = item.getItemMeta();
        meta.setDisplayName("§e進階編輯盔甲座");
        meta.setLore(Arrays.asList(
                ("§r§f - 行為: Shift+右鍵 點擊盔甲座"),
                ("§r§f   你可以: 進階編輯/更改屬性/改變動作")
        ));
        // 寫入資料
        item.setItemMeta(meta);
        // 設置完成
        inv.setItem(5, item);

        // --------------------------------------     6     --------------------------------------
        item = new ItemStack(Material.ENCHANTING_TABLE);
        meta = item.getItemMeta();
        meta.setDisplayName("§e機率附魔出更高的等級");
        meta.setLore(Arrays.asList(
                ("§r§f - 行為: 使用附魔台 附魔物品/裝備/武器"),
                ("§r§f - 條件: 消耗    ~ 10 Exp 機率上升 1   倍"),
                ("§r§f              10 ~ 20 Exp          3.5 倍"),
                ("§r§f              20 ~    Exp          7   倍"),
                ("§r§f         基本 最高可達到 Lv 18"),
                ("§r§f         少數            Lv 20"),
                ("§r§f         Lv 18 原始機率為 0.000000129140  %"),
                ("§r§f         Lv 20            0.0000027487790 %")
        ));
        // 寫入資料
        item.setItemMeta(meta);
        // 設置完成
        inv.setItem(6, item);

        // --------------------------------------     7     --------------------------------------
        item = new ItemStack(Material.ELYTRA);
        meta = item.getItemMeta();
        meta.setDisplayName("§e領地永久飛行");
        meta.setLore(Arrays.asList(
                ("§r§f - 行為: 在自己領地內可以飛行"),
                ("§r§f         有共用權限領地可飛行"),
                ("§r§f         領地開放允許飛行")
        ));
        // 寫入資料
        item.setItemMeta(meta);
        // 設置完成
        inv.setItem(7, item);

        // --------------------------------------     8     --------------------------------------
        item = new ItemStack(Material.SNOW);
        meta = item.getItemMeta();
        meta.setDisplayName("§e季節!!");
        meta.setLore(Arrays.asList(
                ("§r§f - 行為: 夏季早上沒戴帽子 會中暑"),
                ("§r§f         冬季衣物穿不夠 會凍傷"),
                ("§r§f         2、3、4   春季"),
                ("§r§f         5、6、7   夏季"),
                ("§r§f         8、9、10  秋季  農作物生長速度慢8倍"),
                ("§r§f         11、12、1 冬季  農作物不生長"),
                ("§r§f"),
                ("§r§f         夏季 不會下雨"),
                ("§r§f         8月  整月下雨"),
                ("§r§f         12月 整月下雪"),
                ("§r§f         8~11月 雷頻繁")
        ));
        // 寫入資料
        item.setItemMeta(meta);
        // 設置完成
        inv.setItem(8, item);











        // --------------------------------------     9     --------------------------------------
        item = new ItemStack(Material.NAME_TAG);
        meta = item.getItemMeta();
        meta.setDisplayName("§e自由改暱稱");
        meta.setLore(Arrays.asList(
                ("§r§f - 行為: 選單 -> 個人資料 -> 顯示名稱 -> 左鍵點擊"),
                ("§r§f         修改成你想要的暱稱 (支持使用&顏色代碼)")
        ));
        // 寫入資料
        item.setItemMeta(meta);
        // 設置完成
        inv.setItem(9, item);

        // --------------------------------------     10     --------------------------------------
        item = new ItemStack(Material.WHITE_WOOL);
        meta = item.getItemMeta();
        meta.setDisplayName("§e修剪羊毛掉落超加倍");
        meta.setLore(Arrays.asList(
                ("§r§f - 行為: 修剪羊毛"),
                ("§r§f         會掉落比原本多更多的羊毛(白)")
        ));
        // 寫入資料
        item.setItemMeta(meta);
        // 設置完成
        inv.setItem(10, item);

        // --------------------------------------     11     --------------------------------------
        item = new ItemStack(Material.EXPERIENCE_BOTTLE);
        meta = item.getItemMeta();
        meta.setDisplayName("§e繁殖經驗值超加倍");
        meta.setLore(Arrays.asList(
                ("§r§f - 行為: 繁殖動物"),
                ("§r§f         會掉落比原本多更多的經驗值")
        ));
        // 寫入資料
        item.setItemMeta(meta);
        // 設置完成
        inv.setItem(11, item);














        // --------------------------------------     52     --------------------------------------
        inv.setItem(52, SuperFreedomSurvive.Menu.Open.PreviousPage());


        // --------------------------------------     53     --------------------------------------
        inv.setItem(53, SuperFreedomSurvive.Menu.Open.TurnOff());


        // 寫入到容器頁面
        player.openInventory(inv);

    }
    // 阻止玩家拿走物品
    @EventHandler
    final public void onInventoryClickEvent(InventoryClickEvent event) {
        // 判斷是否為玩家

        if (event.getWhoClicked() instanceof Player) {
            // 是玩家
            Player player = (Player) event.getWhoClicked();

            // 檢查容器名稱
            if (event.getView().getTitle().equalsIgnoreCase("§z功能一覽/介紹")) {
                // 是沒錯

                event.setCancelled(true);

                if (event.getRawSlot() == 52) {
                    // 上一頁
                    SuperFreedomSurvive.Menu.Open.open(player);

                } else if (event.getRawSlot() == 53) {
                    // 關閉
                    event.getWhoClicked().closeInventory();

                }
            }
        }
    }
}
