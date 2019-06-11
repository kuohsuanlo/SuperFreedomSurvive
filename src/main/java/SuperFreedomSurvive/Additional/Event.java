package SuperFreedomSurvive.Additional;

import SuperFreedomSurvive.SYSTEM.MySQL;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffectType;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

final public class Event implements Listener {
    // 玩家補助系統


    // 快速破壞
    // 點擊方塊事件
    @EventHandler(priority = EventPriority.LOWEST)
    final public void onBlockDamageEvent(BlockDamageEvent event) {
        if (event.isCancelled()) {
            // 被取消

        } else {
            // 沒有被取消

            try {
                // 是否為左鍵類型
                //if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
                if (SuperFreedomSurvive.Prohibited.DamageBlock.Inspection(event.getPlayer(), event.getBlock())) {
                    // 允許
                    // 是否有購買補助效果
                    if (Event.Have(event.getPlayer(), "Function_Rapid-Destruction_Time")) {
                        // 檢查是否在領地內
                        if (SuperFreedomSurvive.Land.Permissions.Is(event.getBlock().getLocation())) {
                            // 檢查是否有編輯權限
                            if (
                                    SuperFreedomSurvive.Land.Permissions.Ownership(event.getPlayer(), event.getBlock().getLocation()) ||
                                            SuperFreedomSurvive.Land.Permissions.LandShare(event.getPlayer(), event.getBlock().getLocation())
                            ) {
                                if (SuperFreedomSurvive.Land.Permissions.Inspection(event.getPlayer(), event.getBlock().getLocation(), "Permissions_BlockBreak")) {
                                    // 有
                                    if (event.getBlock().getType().getHardness() >= 0) {
                                        // 可破壞
                                        // 手中項目不為空
                                        //if ( event.getPlayer().getInventory().getItemInMainHand() != null ) {
                                        //    event.getBlock().breakNaturally( event.getPlayer().getInventory().getItemInMainHand() );
                                        //} else {
                                        // 資料庫裡面是否有數據
                                        if (SuperFreedomSurvive.Block.Data.Have(event.getBlock().getLocation())) {
                                            // 有
                                            /*
                                            // 掉落物
                                            event.getBlock().getLocation().getWorld().dropItem(event.getBlock().getLocation(), ServerPlugin.Definition.UseItem.GetBlock(event.getBlock().getLocation()));

                                            ServerPlugin.Block.Data.Remove(event.getBlock().getLocation()); // 移除資料值
                                            event.getBlock().setType(Material.AIR);

                                            ItemMeta meta = event.getPlayer().getInventory().getItemInMainHand().getItemMeta();

                                            if ( event.getPlayer().getInventory().getItemInMainHand().getType().getMaxDurability() > 0 ) {
                                                if (((Damageable) meta).getDamage() >= event.getPlayer().getInventory().getItemInMainHand().getType().getMaxDurability() - 1) {
                                                    // 空了 爆掉
                                                    event.getPlayer().getInventory().setItemInMainHand(null);
                                                    event.getPlayer().playSound(event.getPlayer().getLocation(), "minecraft:entity.item.break", 1, 1);

                                                } else {
                                                    // 扣耐久
                                                    ((Damageable) meta).setDamage(((Damageable) meta).getDamage() + 1);
                                                    event.getPlayer().getInventory().getItemInMainHand().setItemMeta(meta);

                                                }
                                            }
                                        */

                                            //new BlockBreakEvent(event.getBlock(),event.getPlayer()); // 新增事件
                                            //event.getBlock().breakNaturally();
                                            //ServerPlugin.Definition.UseItem.Get(event.getBlock().getLocation());
                                            //event.getBlock().breakNaturally();
                                        } else {
                                            if (event.getBlock().getType().isInteractable()) {
                                                // 可交互
                                            } else {
                                                // 不可交互
                                                // 直接將方塊破壞
                                                event.getBlock().breakNaturally(event.getItemInHand());
                                                event.getBlock().setType(Material.AIR, false);

                                                ItemMeta meta = event.getPlayer().getInventory().getItemInMainHand().getItemMeta();

                                                if (event.getPlayer().getInventory().getItemInMainHand().getType().getMaxDurability() > 0) {
                                                    if (((Damageable) meta).getDamage() >= event.getPlayer().getInventory().getItemInMainHand().getType().getMaxDurability() - 1) {
                                                        // 空了 爆掉
                                                        event.getPlayer().getInventory().setItemInMainHand(null);
                                                        event.getPlayer().playSound(event.getPlayer().getLocation(), "minecraft:entity.item.break", 1, 1);

                                                    } else {
                                                        // 扣耐久
                                                        ((Damageable) meta).setDamage(((Damageable) meta).getDamage() + 1);
                                                        event.getPlayer().getInventory().getItemInMainHand().setItemMeta(meta);

                                                    }
                                                }
                                            }

                                            //event.getBlock().getLocation().getWorld().dropItem( event.getBlock().getLocation() , new ItemStack(event.getBlock().getType()) );
                                        }
                                        //}
                                        //event.getPlayer().getInventory().addItem(new ItemStack(event.getClickedBlock().getType()));
                                        //event.getClickedBlock().setType(Material.AIR);
                                    }
                                } else {
                                    SuperFreedomSurvive.Land.Display.Message(event.getPlayer(), "§c領地禁止 破壞方塊");
                                }
                            }
                        }
                    }
                    //}
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }


    // 藥水 2倍效果 或 2倍時間
    @EventHandler(priority = EventPriority.LOWEST)
    final public void onEntityPotionEffectEvent(EntityPotionEffectEvent event) {
        if (event.isCancelled()) {
            // 被取消

        } else {
            // 沒有被取消
            if (event.getCause() == EntityPotionEffectEvent.Cause.PLUGIN) {
            } else {
                try {
                    if (event.getEntity() instanceof org.bukkit.entity.Player) {
                        org.bukkit.entity.Player player = (org.bukkit.entity.Player) event.getEntity();

                        // 是否有購買補助效果
                        if (
                                Event.Have(player, "Function_Potion-Duration-Double_Time") ||
                                        Event.Have(player, "Function_Potion-Lv-Double_Time")
                        ) {
                            //event.setCancelled(true);
                            PotionEffectType effectType = event.getModifiedType();
                            int duration = event.getNewEffect().getDuration(); // 持續時間
                            int amplifier = event.getNewEffect().getAmplifier(); // 等級

                            if (Event.Have(player, "Function_Potion-Duration-Double_Time")) {
                                // 持續時間 2 倍
                                duration = duration * 2;
                            }
                            if (Event.Have(player, "Function_Potion-Lv-Double_Time")) {
                                // 效果等級 2 倍
                                amplifier = amplifier * 2 + 1;
                            }

                            // 給予效果
                            player.addPotionEffect(effectType.createEffect(duration, amplifier), true);

                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }


    // 經驗值獲得 2 倍
    @EventHandler(priority = EventPriority.LOWEST)
    final public void onPlayerExpChangeEvent(PlayerExpChangeEvent event) {
        // 是否有購買補助效果
        if (Event.Have(event.getPlayer(), "Function_Obtain-Experience-Double_Time")) {
            event.setAmount(event.getAmount() * 2);

        }
    }


    // 檢查是否有效果中
    static final public boolean Have(org.bukkit.entity.Player player, String type) {
        try {
            Connection con = MySQL.getConnection(); // 連線 MySQL
            Statement sta = con.createStatement(); // 取得控制庫
            // 存入內容
            ResultSet res = sta.executeQuery("SELECT * FROM `player-additional_data` WHERE `" + type + "` > '" + SuperFreedomSurvive.SYSTEM.Time.Get() + "' AND `Player` = '" + player.getName() + "' LIMIT 1");
            res.last();
            // 最後一行 行數 是否 > 0
            if (res.getRow() > 0) {
                // 有資料

                res.close(); // 關閉查詢
                sta.close(); // 關閉連線

                return true;
            } else {

                res.close(); // 關閉查詢
                sta.close(); // 關閉連線

                return false;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
