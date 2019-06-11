package SuperFreedomSurvive;

import SuperFreedomSurvive.Land.Examine.PermissionBlock;
import SuperFreedomSurvive.Land.Examine.PermissionEntity;
import SuperFreedomSurvive.Land.Examine.PermissionInventory;
import SuperFreedomSurvive.Land.Examine.PermissionPlayer;
import SuperFreedomSurvive.SYSTEM.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collection;
import java.util.Iterator;


final public class Index extends JavaPlugin {

    @Override
    final public void onLoad() {
        // 插件加載中
        getLogger().info("插件加載中");

    }


    @Override
    final public void onEnable() {

        saveDefaultConfig(); // 初始化配置文件

        // MySQL 連線
        SuperFreedomSurvive.SYSTEM.MySQL.connect();
        // 狀況
        if (SuperFreedomSurvive.SYSTEM.MySQL.isConnected()) {
            getLogger().info("MySQL資料庫 已連線");
        } else {
            getLogger().warning("MySQL資料庫 無法連線");
        }


        // 世界全部統一設置
        getServer().getPluginManager().registerEvents(new SuperFreedomSurvive.SERVER.Event(), this);



        // 取得輸入對話系統
        getServer().getPluginManager().registerEvents(new SuperFreedomSurvive.SYSTEM.Input(), this);



        // 傳送玩家系統
        this.getCommand("tpa").setExecutor(new SuperFreedomSurvive.Player.CommandTpa());
        this.getCommand("tpaok").setExecutor(new SuperFreedomSurvive.Player.CommandTpaOk());
        this.getCommand("tpano").setExecutor(new SuperFreedomSurvive.Player.CommandTpaNo());
        getServer().getPluginManager().registerEvents(new SuperFreedomSurvive.Menu.Function.PlayerTransfer(), this);



        // 領地系統
        getServer().getPluginManager().registerEvents(new SuperFreedomSurvive.Land.PlayerMove(), this);
        getServer().getPluginManager().registerEvents(new PermissionBlock(), this);
        getServer().getPluginManager().registerEvents(new PermissionPlayer(), this);
        getServer().getPluginManager().registerEvents(new PermissionInventory(), this);
        getServer().getPluginManager().registerEvents(new PermissionEntity(), this);
        getServer().getPluginManager().registerEvents(new SuperFreedomSurvive.Land.NewLand(), this);
        getServer().getPluginManager().registerEvents(new SuperFreedomSurvive.Land.Display(), this);
        getServer().getPluginManager().registerEvents(new SuperFreedomSurvive.Menu.Function.Land(), this);
        //      領地腳本系統
        getServer().getPluginManager().registerEvents(new SuperFreedomSurvive._Script.Edit(), this);
        getServer().getPluginManager().registerEvents(new SuperFreedomSurvive._Script.Event.AsyncPlayerChat(), this);



        // 選單系統
        getServer().getPluginManager().registerEvents(new SuperFreedomSurvive.Menu.Open(), this);
        //getServer().getPluginManager().registerEvents(new ServerPlugin.Menu.Function.__GetPotion() , this);



        // 聊天室系統
        getServer().getPluginManager().registerEvents(new SuperFreedomSurvive.Menu.Function.Chatroom(), this);
        this.getCommand("chatroom").setExecutor(new SuperFreedomSurvive.Player.CommandChatroom());



        // 禮包系統
        getServer().getPluginManager().registerEvents(new SuperFreedomSurvive.Menu.Function.Kit(), this);
        //this.getCommand("kit").setExecutor(new ServerPlugin.SERVER.CommandKit());



        // 定點系統
        getServer().getPluginManager().registerEvents(new SuperFreedomSurvive.Menu.Function.Warp(), this);



        // 多世界系統
        this.getCommand("world").setExecutor(new SuperFreedomSurvive.World.CommandWorld());
        getServer().getPluginManager().registerEvents(new SuperFreedomSurvive.Menu.Function.World(), this);
        getServer().getPluginManager().registerEvents(new SuperFreedomSurvive.World.Event(), this);
        //      傳送門控制
        getServer().getPluginManager().registerEvents(new SuperFreedomSurvive.World.Portal(), this);



        // 商店
        //      購買頭顱
        getServer().getPluginManager().registerEvents(new SuperFreedomSurvive.Menu.Function.Skull(), this);



        //      技能
        //          全共用持有
        //              兩段式跳躍
        //getServer().getPluginManager().registerEvents(new ServerPlugin.Arms.EveryoneHas.TwoStageJump() , this);


        // 武器系統
        //getServer().getPluginManager().registerEvents(new ServerPlugin.Menu.Function.Arms(), this);
        //      詠唱
        //getServer().getPluginManager().registerEvents(new ServerPlugin.Arms.Singing(), this);
        //      使用武器
        //getServer().getPluginManager().registerEvents(new ServerPlugin.Arms.Use(), this);

        //      詠唱中
        //getServer().getPluginManager().registerEvents(new ServerPlugin.__Arms.PlayerSinging() , this);
        //      重新定向
        //getServer().getPluginManager().registerEvents(new ServerPlugin.__Arms.Allocation() , this);


        // 羈絆系統
        //getServer().getPluginManager().registerEvents(new ServerPlugin.Menu.Function.Pet(), this);


        // 怪物系統
        //getServer().getPluginManager().registerEvents(new ServerPlugin.Menu.Function.Monster(), this);



        // 玩家控制
        //      登入控制
        getServer().getPluginManager().registerEvents(new SuperFreedomSurvive.Player.Login(), this);
        getServer().getPluginManager().registerEvents(new SuperFreedomSurvive.Player.Quit(), this);
        //      指令控制
        getServer().getPluginManager().registerEvents(new SuperFreedomSurvive.Player.Command(), this);
        //      聊天控制
        //getServer().getPluginManager().registerEvents(new ServerPlugin.Player.Chat() , this);
        //      重生點
        getServer().getPluginManager().registerEvents(new SuperFreedomSurvive.Player.Spawn(), this);
        //      個人資料
        getServer().getPluginManager().registerEvents(new SuperFreedomSurvive.Menu.Function.Self(), this);



        // 告示牌系統
        //      執行指令
        getServer().getPluginManager().registerEvents(new SuperFreedomSurvive.Sign.Use(), this);
        getServer().getPluginManager().registerEvents(new SuperFreedomSurvive.Menu.Function.Sign(), this);
        //      編輯告示牌
        getServer().getPluginManager().registerEvents(new SuperFreedomSurvive.Sign.Edit(), this);



        // 補助系統
        getServer().getPluginManager().registerEvents(new SuperFreedomSurvive.Menu.Function.Additional(), this);
        getServer().getPluginManager().registerEvents(new SuperFreedomSurvive.Additional.Event(), this);
        //      小木斧
        getServer().getPluginManager().registerEvents(new SuperFreedomSurvive.Additional.WoodAxe(), this);



        // 功能
        //      伺服器功能一覽/介紹
        getServer().getPluginManager().registerEvents(new SuperFreedomSurvive.Menu.Function.Help(), this);
        //      右鍵鐵軌產生礦車
        //getServer().getPluginManager().registerEvents(new ServerPlugin.SmallFunction.__RightClickRailGenerateMinecart() , this);
        //      顯示玩家血量 怪物血量
        getServer().getPluginManager().registerEvents(new SuperFreedomSurvive.Function.DisplayHealth(), this);
        //      停用顯示訊息
        getServer().getPluginManager().registerEvents(new SuperFreedomSurvive.Function.DisableDisplayMessage(), this);
        //      告示牌色碼轉換
        getServer().getPluginManager().registerEvents(new SuperFreedomSurvive.Function.ColorCodeTransform(), this);
        //      生怪磚生出的動物不得收納 繁殖動物獲得經驗
        getServer().getPluginManager().registerEvents(new SuperFreedomSurvive.Function.SpawnerRestrict(), this);
        //      動物福利
        getServer().getPluginManager().registerEvents(new SuperFreedomSurvive.Function.AnimalWelfare(), this);
        //      編輯盔甲座
        getServer().getPluginManager().registerEvents(new SuperFreedomSurvive.Function.EditArmorStand(), this);
        //      點擊樓梯座下
        getServer().getPluginManager().registerEvents(new SuperFreedomSurvive.Function.StairsSitting(), this);
        //      傷害效果改寫
        getServer().getPluginManager().registerEvents(new SuperFreedomSurvive.Function.RewriteDamage(), this);
        //      附魔重新改寫
        getServer().getPluginManager().registerEvents(new SuperFreedomSurvive.Function.RewriteEnchanted(), this);
        //      恢復效果改寫
        getServer().getPluginManager().registerEvents(new SuperFreedomSurvive.Function.RewriteRstore(), this);
        //      掉落物顯示名稱
        getServer().getPluginManager().registerEvents(new SuperFreedomSurvive.Function.DropItemDisplayName(), this);



        // 伺服器自製
        //      合成選單
        getServer().getPluginManager().registerEvents(new SuperFreedomSurvive.Menu.Function.Composite(), this);
        SuperFreedomSurvive.Definition.Composite.Recipe();
        //      物品
        getServer().getPluginManager().registerEvents(new SuperFreedomSurvive.Definition.UseItem(), this);
        //      方塊
        getServer().getPluginManager().registerEvents(new SuperFreedomSurvive.Definition.UseBlock(), this);



        // 防止外掛
        //      禁止沒有指向怪物卻造成傷害
        getServer().getPluginManager().registerEvents(new SuperFreedomSurvive.Prohibited.DamageCheck(), this);
        //      禁止沒有指向方塊卻挖除 / 放置 / 點擊
        getServer().getPluginManager().registerEvents(new SuperFreedomSurvive.Prohibited.DamageBlock(), this);
        //      禁止自動釣魚
        getServer().getPluginManager().registerEvents(new SuperFreedomSurvive.Prohibited.AutomationFishing(), this);
        //      禁止動物超量
        SuperFreedomSurvive.Prohibited.AnimalExcess.Start();
        //      禁止掛機
        getServer().getPluginManager().registerEvents(new SuperFreedomSurvive.Prohibited.StandingStill(), this);



        // 怪物
        getServer().getPluginManager().registerEvents(new SuperFreedomSurvive.Monster.Menu(), this);
        getServer().getPluginManager().registerEvents(new SuperFreedomSurvive.Monster.Event(), this);



        // 季節
        getServer().getPluginManager().registerEvents(new SuperFreedomSurvive.Climate.Event(), this);




        // 自訂物品
        getServer().getPluginManager().registerEvents(new SuperFreedomSurvive.Menu.Function.Customize(), this);



        // 查看玩家庫存
        this.getCommand("inv").setExecutor(new SuperFreedomSurvive.SERVER.CommandInv());



        //      測試
        this.getCommand("test").setExecutor(new SuperFreedomSurvive.SERVER.Test());


        // 附魔允許額外加入
        try {
            Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //Enchantment.registerEnchantment(ServerPlugin.Definition.Enchantment.Harvesting.HARVESTING);
        //ServerPlugin.Definition.Enchantment.Harvesting.;


        // 註冊世界
        SuperFreedomSurvive.World.List.Add("w");
        SuperFreedomSurvive.World.List.Add("w_nether");
        SuperFreedomSurvive.World.List.Add("w_the_end");


        // 設定世界
        SuperFreedomSurvive.World.Data.Setting(Bukkit.getWorld("w"));
        SuperFreedomSurvive.World.Data.Setting(Bukkit.getWorld("w_nether"));
        SuperFreedomSurvive.World.Data.Setting(Bukkit.getWorld("w_the_end"));



        // 將當前的所有玩家傳送到上次的座標位置
        Collection collection = Bukkit.getServer().getOnlinePlayers();
        Iterator iterator = collection.iterator();
        // 總數
        while (iterator.hasNext()) {
            Player player = ((Player) iterator.next());
            try {
                Connection con = MySQL.getConnection(); // 連線 MySQL
                Statement sta = con.createStatement(); // 取得控制庫

                ResultSet res = sta.executeQuery("SELECT * FROM `player-location_data` WHERE `Player` = '" + player.getName() + "' LIMIT 1");
                // 跳到最後一行
                res.last();
                // 最後一行 行數 是否 > 0
                if (res.getRow() > 0) {
                    // 有資料
                    // 跳回 初始行 必須使用 next() 才能取得資料
                    res.beforeFirst();
                    res.next();

                    String world = res.getString("World");
                    int X = res.getInt("X");
                    int Y = res.getInt("Y");
                    int Z = res.getInt("Z");

                    res.close(); // 關閉查詢

                    // 不適用於觀察者
                    if (player.getGameMode() != GameMode.SPECTATOR) {

                        // 傳送
                        SuperFreedomSurvive.Player.Teleport.Location(
                                player,
                                world,
                                X,
                                Y,
                                Z
                        );

                    }
                }

                res.close(); // 關閉查詢
                sta.close(); // 關閉連線

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }


        // 循環檢查資料
        SuperFreedomSurvive.Loop.Start();
        SuperFreedomSurvive.World.Loop.Start();
        SuperFreedomSurvive.Land.PlayerMove.Start();
        SuperFreedomSurvive.Land.Display.Start();
        SuperFreedomSurvive.Additional.WoodAxe.Start();
        SuperFreedomSurvive.Climate.Loop.start();
        SuperFreedomSurvive.Prohibited.StandingStill.Start();
        SuperFreedomSurvive.Monster.Loop.Start();



        // 插件開始運行/載入完成
        getLogger().info("插件載入完成");


    }


    @Override
    final public void onDisable() {
        // 插件停止運行
        getLogger().info("插件停止");

        SuperFreedomSurvive.Player.Quit.All();

        SuperFreedomSurvive.World.List.Remake();

        //this.getClass().desiredAssertionStatus();

        //HandlerList.unregisterAll(this); // 解除綁定所有事件
        //this.getClassLoader().clearAssertionStatus();


/*
        // 關閉所有世界
        ArrayList<String> array_list = ServerPlugin.World.List.Get();
        for ( int i = 0 ; i < array_list.size() ; ++i ) {
            ServerPlugin.World.Control.Unload( array_list.get(i) , true );
        }
*/

        // MySQL 斷開連線
        SuperFreedomSurvive.SYSTEM.MySQL.disconnect();

        getLogger().info("MySQL資料庫 斷開連線");
    }


    //private static Plugin getPlugin() {
    //   Plugin w = this.get;
    //   return w;
    //}


}