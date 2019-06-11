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

final public class Sign implements Listener {
    final public static void menu(Player player) {
        // 指令告示牌說明
        // 顯示容器
        Inventory inv = Bukkit.createInventory(null, 54, "§z指令告示牌系統");

        ItemStack item;
        ItemMeta meta;


        // --------------------------------------     0     --------------------------------------
        item = new ItemStack(Material.SIGN);
        meta = item.getItemMeta();
        meta.setDisplayName("§r§e傳送");
        meta.setLore(Arrays.asList(
                ("§r§f 第 1 行"),
                ("§r§f     允許: [傳送] [Tp] [tp] [TP]"),
                ("§r§f     使用其中之一都有效果 []不可省略"),
                ("§r§f 第 2 行"),
                ("§r§f     使用: X Y Z"),
                ("§r§f     少一個都不行 只能是整數 按照順序"),
                ("§r§f 第 3 ~ 4 行"),
                ("§r§f     隨意"),
                ("§r§f - 條件"),
                ("§r§f     目標位置只能離告示牌半徑 100 格內"),
                ("§r§f - 注意"),
                ("§r§f     不會去檢查目標位置是否安全")
        ));
        // 寫入資料
        item.setItemMeta(meta);
        // 設置完成
        inv.setItem(0, item);


        // --------------------------------------     1     --------------------------------------
        item = new ItemStack(Material.SIGN);
        meta = item.getItemMeta();
        meta.setDisplayName("§r§e重生點");
        meta.setLore(Arrays.asList(
                ("§r§f 第 1 行"),
                ("§r§f     允許: [重生點] [Spawn] [spawn] [SPAWN]"),
                ("§r§f     使用其中之一都有效果 []不可省略"),
                ("§r§f 第 2 ~ 4 行"),
                ("§r§f     隨意"),
                ("§r§f - 條件"),
                ("§r§f     只能是 直立式告示牌"),
                ("§r§f - 說明"),
                ("§r§f     死亡後會在告示牌位置復活(不論告示牌是否還在)"),
                ("§r§f     使用床可以獲得相同的效果")
        ));
        // 寫入資料
        item.setItemMeta(meta);
        // 設置完成
        inv.setItem(1, item);


        // --------------------------------------     2     --------------------------------------
        item = new ItemStack(Material.SIGN);
        meta = item.getItemMeta();
        meta.setDisplayName("§r§e抽獎 (箱子->玩家)");
        meta.setLore(Arrays.asList(
                ("§r§f 第 1 行"),
                ("§r§f     允許: [抽獎] [Lottery] [lottery] [LOTTERY]"),
                ("§r§f     使用其中之一都有效果 []不可省略"),
                ("§r§f 第 2 ~ 4 行"),
                ("§r§f     隨意"),
                ("§r§f - 條件"),
                ("§r§f     直立式告示牌 下方必須是箱子/大箱子"),
                ("§r§f     懸掛式告示牌 依附方塊必須是箱子/大箱子"),
                ("§r§f     箱子內左上角第一格 該物品為抽獎條件(會依照該格數量)"),
                ("§r§f - 說明"),
                ("§r§f     箱子內除了第一格之外 其他都視為 1 個獎品"),
                ("§r§f     會隨機選擇一格作為獎品(會依照該格數量)"),
                ("§r§f     該格會變成玩家消耗的物品")
        ));
        // 寫入資料
        item.setItemMeta(meta);
        // 設置完成
        inv.setItem(2, item);


        // --------------------------------------     3     --------------------------------------
        item = new ItemStack(Material.SIGN);
        meta = item.getItemMeta();
        meta.setDisplayName("§r§e買入 (玩家->箱子)");
        meta.setLore(Arrays.asList(
                ("§r§f 第 1 行"),
                ("§r§f     允許: [買入] [Buy] [buy] [BUY]"),
                ("§r§f     使用其中之一都有效果 []不可省略"),
                ("§r§f 第 2 ~ 4 行"),
                ("§r§f     隨意"),
                ("§r§f - 條件"),
                ("§r§f     直立式告示牌 下方必須是箱子/大箱子"),
                ("§r§f     懸掛式告示牌 依附方塊必須是箱子/大箱子"),
                ("§r§f     箱子內左上角第一格 該物品為要收取的物品(會依照該格數量)"),
                ("§r§f - 說明"),
                ("§r§f     箱子內除了第一格之外 其他都視為 1 個回饋物品(會依照該格數量)"),
                ("§r§f     會按照順序存放物品"),
                ("§r§f     該格會變成玩家賣出的物品")
        ));
        // 寫入資料
        item.setItemMeta(meta);
        // 設置完成
        inv.setItem(3, item);


        // --------------------------------------     4     --------------------------------------
        item = new ItemStack(Material.SIGN);
        meta = item.getItemMeta();
        meta.setDisplayName("§r§e賣出 (箱子->玩家)");
        meta.setLore(Arrays.asList(
                ("§r§f 第 1 行"),
                ("§r§f     允許: [賣出] [Sell] [sell] [SELL]"),
                ("§r§f     使用其中之一都有效果 []不可省略"),
                ("§r§f 第 2 ~ 4 行"),
                ("§r§f     隨意"),
                ("§r§f - 條件"),
                ("§r§f     直立式告示牌 下方必須是箱子/大箱子"),
                ("§r§f     懸掛式告示牌 依附方塊必須是箱子/大箱子"),
                ("§r§f     箱子內左上角第一格 該物品為買入條件(會依照該格數量)"),
                ("§r§f - 說明"),
                ("§r§f     箱子內除了第一格之外 其他都視為 1 個商品"),
                ("§r§f     會按照順序賣出商品(會依照該格數量)"),
                ("§r§f     該格會變成玩家消耗的物品")
        ));
        // 寫入資料
        item.setItemMeta(meta);
        // 設置完成
        inv.setItem(4, item);


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
            if (event.getView().getTitle().equalsIgnoreCase("§z指令告示牌系統")) {
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
