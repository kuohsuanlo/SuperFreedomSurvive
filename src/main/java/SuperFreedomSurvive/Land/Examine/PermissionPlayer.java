package SuperFreedomSurvive.Land.Examine;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import com.destroystokyo.paper.event.player.PlayerLaunchProjectileEvent;
import com.destroystokyo.paper.event.player.PlayerReadyArrowEvent;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;

final public class PermissionPlayer implements Listener {
    // 控制所有 在領地內的破壞 / 放置 / 物品使用 / 傷害


    @EventHandler(priority = EventPriority.HIGHEST)
    final public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent event) {
        //  當玩家聊天時觸發這事件
        PlayerPermissionsExamination(event, event, event.getPlayer(), "AsyncPlayerChat", "發送聊天訊息");
    }

    /*
        @EventHandler( priority = EventPriority.HIGHEST )
        final public void onPlayerAnimationEvent(PlayerAnimationEvent event){
            //  玩家動作事件
            PlayerPermissionsExamination( event , event , event.getPlayer() , "PlayerAnimation" , "使用動作" );
        }
    */
    @EventHandler(priority = EventPriority.HIGHEST)
    final public void onPlayerArmorStandManipulateEvent(PlayerArmorStandManipulateEvent event) {
        //  當玩家與裝甲架交互並且進行交換, 取回或放置物品時觸發本事件

        // 檢查是否有權限
        if (SuperFreedomSurvive.Land.Permissions.Inspection(event.getPlayer(), event.getRightClicked().getLocation(), "Permissions_PlayerArmorStandManipulate")) {
            // 有
        } else {
            // 沒有
            // 禁止事件 改動資料
            event.setCancelled(true);
            if (!event.getPlayer().isSneaking()) {
                SuperFreedomSurvive.Land.Display.Message(event.getPlayer(), "§c領地禁止 盔甲座裝備拿取/放置");
            }
        }

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    final public void onPlayerBedEnterEvent(PlayerBedEnterEvent event) {
        //  玩家準備躺到床上時觸發此事件
        PlayerPermissionsExamination(event, event, event.getPlayer(), "PlayerBedEnter", "使用床");
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    final public void onPlayerBucketFillEvent(PlayerBucketFillEvent event) {
        //  水桶裝滿水事件

        // 檢查是否有權限
        if (SuperFreedomSurvive.Land.Permissions.Inspection(event.getPlayer(), event.getBlockClicked().getLocation(), "Permissions_PlayerBucket")) {
            // 有
        } else {
            // 沒有
            SuperFreedomSurvive.Land.Display.Message(event.getPlayer(), "§c領地禁止 使用桶子");
            // 禁止事件 改動資料
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    final public void onPlayerBucketEmptyEvent(PlayerBucketEmptyEvent event) {
        //  玩家用完一只桶後觸發此事件

        // 檢查是否有權限
        if (SuperFreedomSurvive.Land.Permissions.Inspection(event.getPlayer(), event.getBlockClicked().getLocation(), "Permissions_PlayerBucketEmpty")) {
            // 有
        } else {
            // 沒有
            SuperFreedomSurvive.Land.Display.Message(event.getPlayer(), "§c領地禁止 使用桶子");
            // 禁止事件 改動資料
            event.setCancelled(true);
        }
    }

/*
    @EventHandler( priority = EventPriority.HIGHEST )
    final public void onPlayerBucketEmptyEvent(PlayerBucketEmptyEvent event){
        //  水桶裝滿水事件
        int X = (int)event.getBlockClicked().getLocation().getX();
        int Y = (int)event.getBlockClicked().getLocation().getY();
        int Z = (int)event.getBlockClicked().getLocation().getZ();
        String world = event.getBlockClicked().getWorld().getName();

        // 檢查是否有權限
        if ( ServerPlugin.Land.Permissions.Inspection( event.getPlayer() , world , X , Y , Z , "Permissions_PlayerBucket" ) ){
            // 有
        } else {
            // 沒有
            ServerPlugin.Land.Display.Message( event.getPlayer() ,"§c領地禁止 使用桶子" );
            // 禁止事件 改動資料
            event.setCancelled(true);
        }
    }
*/


    @EventHandler(priority = EventPriority.HIGHEST)
    final public void onPlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent event) {
        //  這個事件是,當一個玩家執行一個命令的時候將會被觸發(也就是在聊天框裏面輸入信息以/開頭的時候，算作命令，就會觸發此事件)
        PlayerPermissionsExamination(event, event, event.getPlayer(), "PlayerCommandPreprocess", "執行命令");
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    final public void onPlayerDropItemEvent(PlayerDropItemEvent event) {
        //  玩家丟出物品事件
        PlayerPermissionsExamination(event, event, event.getPlayer(), "PlayerDropItem", "丟棄物品");
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    final public void onPlayerEditBookEvent(PlayerEditBookEvent event) {
        //  當玩家編輯或簽名書與筆時觸發
        PlayerPermissionsExamination(event, event, event.getPlayer(), "PlayerEditBook", "編輯書本");
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    final public void onPlayerEggThrowEvent(PlayerEggThrowEvent event) {
        //  玩家拋出雞蛋時觸發本事件，雞蛋可能孵化

        // 檢查是否有權限
        if (SuperFreedomSurvive.Land.Permissions.Inspection(event.getPlayer(), event.getEgg().getLocation(), "Permissions_PlayerEggThrow")) {
            // 有
        } else {
            // 沒有
            SuperFreedomSurvive.Land.Display.Message(event.getPlayer(), "§c領地禁止 孵化雞蛋");
            // 禁止事件 改動資料
            event.setHatching(true);
        }

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    final public void onPlayerExpChangeEvent(PlayerExpChangeEvent event) {
        //  當玩家經驗值發生變化時調用此事件

        // 檢查是否有權限
        if (SuperFreedomSurvive.Land.Permissions.Inspection(event.getPlayer(), event.getPlayer().getLocation(), "Permissions_PlayerExpChange")) {
            // 有
        } else {
            // 沒有
            SuperFreedomSurvive.Land.Display.Message(event.getPlayer(), "§c領地禁止 獲得經驗值");
            // 禁止事件 改動資料
            event.setAmount(0);
        }

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    final public void onPlayerFishEvent(PlayerFishEvent event) {
        //  當玩家釣魚時觸發本事件
        PlayerPermissionsExamination(event, event, event.getPlayer(), "PlayerFish", "釣魚");
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    final public void onPlayerInteractEntityEvent(PlayerInteractEntityEvent event) {
        //  當玩家點擊一個實體 時調用此事件


        // 是否為特定實體 需要額外檢查
        if (event.getRightClicked().getType() == EntityType.ITEM_FRAME) {
            // 物品展示框
            // 檢查是否有權限
            if (SuperFreedomSurvive.Land.Permissions.Inspection(event.getPlayer(), event.getRightClicked().getLocation(), "Permissions_Entity_ItemFrame_Use")) {
                // 有
            } else {
                // 沒有
                SuperFreedomSurvive.Land.Display.Message(event.getPlayer(), "§c領地禁止 使用物品展示框");
                // 禁止事件 改動資料
                event.setCancelled(true);
            }

        } else {
            // 局面方式檢查
            // 檢查是否有權限
            if (SuperFreedomSurvive.Land.Permissions.Inspection(event.getPlayer(), event.getRightClicked().getLocation(), "Permissions_PlayerInteractEntity")) {
                // 有
            } else {
                // 沒有
                SuperFreedomSurvive.Land.Display.Message(event.getPlayer(), "§c領地禁止 點擊實體");
                // 禁止事件 改動資料
                event.setCancelled(true);
            }
        }

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    final public void onPlayerItemConsumeEvent(PlayerItemConsumeEvent event) {
        //  當玩家消耗完物品時, 此事件將觸發 例如:(食物, 藥水, 牛奶桶)
        PlayerPermissionsExamination(event, event, event.getPlayer(), "PlayerItemConsume", "食物/藥水消耗");
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    final public void onPlayerInteractEvent(PlayerInteractEvent event) {
        // 丟出經驗瓶
        // 與對像或空中交互
        try {

            if (event.getItem() == null) { return; }

            if (event.getItem().getType() == Material.EXPERIENCE_BOTTLE) {
                PlayerPermissionsExamination(event, event, event.getPlayer(), "PlayerItemConsume", "食物/藥水消耗");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    final public void onPlayerItemMendEvent(PlayerItemMendEvent event) {
        // 表示玩家通过装备上的经验修补修复装备耐久时触发该事件
        PlayerPermissionsExamination(event, event, event.getPlayer(), "PlayerItemMend", "經驗修補耐久");
    }

    /*
    @EventHandler( priority = EventPriority.HIGHEST )
    final public void onPlayerMoveEvent(PlayerMoveEvent event){
        //  玩家移动事件

        // 檢查是否有權限
        if ( ServerPlugin.Land.Permissions.Inspection( event.getPlayer() , event.getTo() , "Permissions_PlayerMove" ) ){
            // 有
        } else {
            // 沒有
            ServerPlugin.Land.Display.Message( event.getPlayer() ,"§c領地禁止 移動位置" );
            // 禁止事件 改動資料
            event.setCancelled(true);
        }

    }
    */

    @EventHandler(priority = EventPriority.HIGHEST)
    final public void onPlayerPickupArrowEvent(PlayerPickupArrowEvent event) {
        //  當玩家從地上撿起箭時觸發本事件

        // 檢查是否有權限
        if (SuperFreedomSurvive.Land.Permissions.Inspection(event.getPlayer(), event.getArrow().getLocation(), "Permissions_PlayerPickupArrow")) {
            // 有
        } else {
            // 沒有
            SuperFreedomSurvive.Land.Display.Message(event.getPlayer(), "§c領地禁止 拾取弓箭");
            // 禁止事件 改動資料
            event.setCancelled(true);
        }

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    final public void onPlayerPortalEvent(PlayerPortalEvent event) {
        //  玩家將要被傳送門傳送的事件
        PlayerPermissionsExamination(event, event, event.getPlayer(), "PlayerPortal", "使用傳送門");
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    final public void onPlayerShearEntityEvent(PlayerShearEntityEvent event) {
        //  玩家剪羊毛時調用此事件

        // 檢查是否有權限
        if (SuperFreedomSurvive.Land.Permissions.Inspection(event.getPlayer(), event.getEntity().getLocation(), "Permissions_PlayerShearEntity")) {
            // 有
        } else {
            // 沒有
            SuperFreedomSurvive.Land.Display.Message(event.getPlayer(), "§c領地禁止 修剪羊毛");
            // 禁止事件 改動資料
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    final public void onPlayerTeleportEvent(PlayerTeleportEvent event) {
        //  玩家傳送事件
        // 是否為末影珍珠傳送
        if (event.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL) {
            PlayerPermissionsExamination(event, event, event.getPlayer(), "PlayerTeleport", "傳送");
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    final public void onPlayerJumpEvent(PlayerJumpEvent event) {
        //  玩家跳躍時
        PlayerPermissionsExamination(event, event, event.getPlayer(), "PlayerJump", "跳躍");
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    final public void onPlayerToggleFlightEvent(PlayerToggleFlightEvent event) {
        //  玩家切換飛行狀態則調用此事件

        if (event.isFlying()) {
            // 檢查是否有權限
            if (SuperFreedomSurvive.Land.Permissions.Inspection(event.getPlayer(), event.getPlayer().getLocation(), "Permissions_PlayerToggleFlight")) {
                // 有
            } else {
                // 沒有
                SuperFreedomSurvive.Land.Display.Message(event.getPlayer(), "§c領地禁止 飛行");
                // 禁止事件 改動資料
                event.setCancelled(true);
            }
        }

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    final public void onPlayerToggleSneakEvent(PlayerToggleSneakEvent event) {
        //  玩家切換潛行狀態則調用此事件
        PlayerPermissionsExamination(event, event, event.getPlayer(), "PlayerToggleSneak", "潛行");
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    final public void onPlayerToggleSprintEvent(PlayerToggleSprintEvent event) {
        //  玩家切換疾跑狀態時調用此事件
        PlayerPermissionsExamination(event, event, event.getPlayer(), "PlayerToggleSprint", "奔跑");
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    final public void onPlayerVelocityEvent(PlayerVelocityEvent event) {
        //  玩家改變移動速度時
        PlayerPermissionsExamination(event, event, event.getPlayer(), "PlayerVelocity", "移動變速");
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    final public void onInventoryClickEvent(InventoryClickEvent event) {
        //  點擊容器中的欄位時
        if (event.getWhoClicked() instanceof Player) {
            // 是玩家
            Player player = (Player) event.getWhoClicked();

            if (event.getView().getTitle().matches("§z.*")) {

            } else if (
                    event.getInventory().getType() == InventoryType.CRAFTING ||
                            event.getInventory().getType() == InventoryType.MERCHANT ||
                            event.getInventory().getType() == InventoryType.WORKBENCH
            ) {

            } else {
                if (event.getInventory().getLocation() != null) {
                    // 檢查是否有權限
                    if (SuperFreedomSurvive.Land.Permissions.Inspection(player, event.getInventory().getLocation(), "Permissions_PlayerInventory")) {
                        // 有
                    } else {
                        // 沒有
                        SuperFreedomSurvive.Land.Display.Message(player, "§c領地禁止 拿取/放入物品到容器中");
                        // 禁止事件 改動資料
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    final public void onPlayerInteractEvent_(PlayerInteractEvent event) {
        // 吃蛋糕的時候
        try {

            if (event.getClickedBlock() == null) { return; }

            if (event.getClickedBlock().getType() == Material.CAKE) {
                // 檢查是否有權限
                if (SuperFreedomSurvive.Land.Permissions.Inspection(event.getPlayer(), event.getClickedBlock().getLocation(), "Permissions_PlayerEatCake")) {
                    // 有
                } else {
                    // 沒有
                    event.setCancelled(true);
                    SuperFreedomSurvive.Land.Display.Message(event.getPlayer(), "§c領地禁止 食用蛋糕");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    final public void onPlayerReadyArrowEvent(PlayerReadyArrowEvent event) {
        //  射出弓箭
        PlayerPermissionsExamination(event, event, event.getPlayer(), "PlayerReadyArrow", "射出弓箭");
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    final public void onPlayerLaunchProjectileEvent(PlayerLaunchProjectileEvent event) {
        //  丟出投射物
        PlayerPermissionsExamination(event, event, event.getPlayer(), "PlayerLaunchProjectile", "丟出投射物");
    }


    final public void PlayerPermissionsExamination(PlayerEvent event, Cancellable cancel, Player player, String permissions, String event_name) {
        // 檢查玩家權限
        // 共用形式的判斷


        // 檢查是否有權限
        if (SuperFreedomSurvive.Land.Permissions.Inspection(event.getPlayer(), player.getPlayer().getLocation(), "Permissions_" + permissions)) {
            // 有
        } else {
            // 沒有
            SuperFreedomSurvive.Land.Display.Message(event.getPlayer(), "§c領地禁止 " + event_name);
            // 禁止事件 改動資料
            cancel.setCancelled(true);
        }

    }


}
