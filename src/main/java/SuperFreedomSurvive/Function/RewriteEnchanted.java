package SuperFreedomSurvive.Function;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;

import java.util.Map;

final public class RewriteEnchanted implements Listener {
    // 附魔重新改寫

    // 成功附加ItemStack時調用（當在附魔台中）
    @EventHandler
    final public void onEnchantItemEvent(EnchantItemEvent event) {
        if (event.isCancelled()) {

        } else {
            // 沒被取消

            try {

                Map<Enchantment, Integer> map = event.getEnchantsToAdd();
                if (map == null) {
                    return;
                }

                for (Map.Entry<Enchantment, Integer> entry : map.entrySet()) {

                    if (entry.getKey().getKey().getKey().equals(Enchantment.ARROW_DAMAGE.getKey().getKey())) {
                        ////////////////////////////////////// 強力       //////////////////////////////////////
                        // 倍率 0.4
                        Random_4(event, entry);

                    } else if (entry.getKey().getKey().getKey().equals(Enchantment.ARROW_FIRE.getKey().getKey())) {
                        ////////////////////////////////////// 火焰       //////////////////////////////////////

                    } else if (entry.getKey().getKey().getKey().equals(Enchantment.ARROW_INFINITE.getKey().getKey())) {
                        ////////////////////////////////////// 無限       //////////////////////////////////////

                    } else if (entry.getKey().getKey().getKey().equals(Enchantment.ARROW_KNOCKBACK.getKey().getKey())) {
                        ////////////////////////////////////// 衝擊       //////////////////////////////////////
                        // 倍率 0.3
                        Random_3(event, entry);

                    } else if (entry.getKey().getKey().getKey().equals(Enchantment.BINDING_CURSE.getKey().getKey())) {
                        ////////////////////////////////////// 綁定詛咒   //////////////////////////////////////

                    } else if (entry.getKey().getKey().getKey().equals(Enchantment.CHANNELING.getKey().getKey())) {
                        ////////////////////////////////////// 喚雷       //////////////////////////////////////

                    } else if (entry.getKey().getKey().getKey().equals(Enchantment.DAMAGE_ALL.getKey().getKey())) {
                        ////////////////////////////////////// 鋒利       //////////////////////////////////////
                        // 倍率 0.4
                        Random_4(event, entry);

                    } else if (entry.getKey().getKey().getKey().equals(Enchantment.DAMAGE_ARTHROPODS.getKey().getKey())) {
                        ////////////////////////////////////// 截肢剋星   //////////////////////////////////////
                        // 倍率 0.4
                        Random_4(event, entry);

                    } else if (entry.getKey().getKey().getKey().equals(Enchantment.DAMAGE_UNDEAD.getKey().getKey())) {
                        ////////////////////////////////////// 不死剋星   //////////////////////////////////////
                        // 倍率 0.4
                        Random_4(event, entry);

                    } else if (entry.getKey().getKey().getKey().equals(Enchantment.DEPTH_STRIDER.getKey().getKey())) {
                        ////////////////////////////////////// 深海漫遊   //////////////////////////////////////
                        // 倍率 0.3
                        Random_3(event, entry);

                    } else if (entry.getKey().getKey().getKey().equals(Enchantment.DIG_SPEED.getKey().getKey())) {
                        ////////////////////////////////////// 效率       //////////////////////////////////////
                        // 倍率 0.3
                        Random_3(event, entry);

                    } else if (entry.getKey().getKey().getKey().equals(Enchantment.DURABILITY.getKey().getKey())) {
                        ////////////////////////////////////// 耐久       //////////////////////////////////////
                        // 倍率 0.3
                        Random_3(event, entry);

                    } else if (entry.getKey().getKey().getKey().equals(Enchantment.FIRE_ASPECT.getKey().getKey())) {
                        ////////////////////////////////////// 燃燒       //////////////////////////////////////
                        // 倍率 0.3
                        Random_3(event, entry);

                    } else if (entry.getKey().getKey().getKey().equals(Enchantment.FROST_WALKER.getKey().getKey())) {
                        ////////////////////////////////////// 冰霜行者   //////////////////////////////////////

                    } else if (entry.getKey().getKey().getKey().equals(Enchantment.IMPALING.getKey().getKey())) {
                        ////////////////////////////////////// 魚叉       //////////////////////////////////////
                        // 倍率 0.4
                        Random_4(event, entry);

                    } else if (entry.getKey().getKey().getKey().equals(Enchantment.KNOCKBACK.getKey().getKey())) {
                        ////////////////////////////////////// 擊退       //////////////////////////////////////
                        // 倍率 0.3
                        Random_3(event, entry);

                    } else if (entry.getKey().getKey().getKey().equals(Enchantment.LOOT_BONUS_BLOCKS.getKey().getKey())) {
                        ////////////////////////////////////// 幸運       //////////////////////////////////////
                        // 倍率 0.3
                        Random_3(event, entry);

                    } else if (entry.getKey().getKey().getKey().equals(Enchantment.LOOT_BONUS_MOBS.getKey().getKey())) {
                        ////////////////////////////////////// 掠奪       //////////////////////////////////////
                        // 倍率 0.4
                        Random_4(event, entry);

                    } else if (entry.getKey().getKey().getKey().equals(Enchantment.LOYALTY.getKey().getKey())) {
                        ////////////////////////////////////// 忠臣       //////////////////////////////////////
                        // 倍率 0.3
                        Random_3(event, entry);

                    } else if (entry.getKey().getKey().getKey().equals(Enchantment.LUCK.getKey().getKey())) {
                        ////////////////////////////////////// 海洋祝福   //////////////////////////////////////
                        // 倍率 0.3
                        Random_3(event, entry);

                    } else if (entry.getKey().getKey().getKey().equals(Enchantment.LURE.getKey().getKey())) {
                        ////////////////////////////////////// 魚餌       //////////////////////////////////////
                        // 倍率 0.3
                        Random_3(event, entry);

                    } else if (entry.getKey().getKey().getKey().equals(Enchantment.MENDING.getKey().getKey())) {
                        ////////////////////////////////////// 修補       //////////////////////////////////////

                    } else if (entry.getKey().getKey().getKey().equals(Enchantment.OXYGEN.getKey().getKey())) {
                        ////////////////////////////////////// 水中呼吸   //////////////////////////////////////
                        // 倍率 0.3
                        Random_3(event, entry);

                    } else if (entry.getKey().getKey().getKey().equals(Enchantment.PROTECTION_ENVIRONMENTAL.getKey().getKey())) {
                        ////////////////////////////////////// 保護       //////////////////////////////////////
                        // 倍率 0.4
                        Random_4(event, entry);

                    } else if (entry.getKey().getKey().getKey().equals(Enchantment.PROTECTION_EXPLOSIONS.getKey().getKey())) {
                        ////////////////////////////////////// 爆炸保護   //////////////////////////////////////
                        // 倍率 0.4
                        Random_4(event, entry);

                    } else if (entry.getKey().getKey().getKey().equals(Enchantment.PROTECTION_FALL.getKey().getKey())) {
                        ////////////////////////////////////// 輕盈       //////////////////////////////////////
                        // 倍率 0.4
                        Random_4(event, entry);

                    } else if (entry.getKey().getKey().getKey().equals(Enchantment.PROTECTION_FIRE.getKey().getKey())) {
                        ////////////////////////////////////// 火焰保護   //////////////////////////////////////
                        // 倍率 0.4
                        Random_4(event, entry);

                    } else if (entry.getKey().getKey().getKey().equals(Enchantment.PROTECTION_PROJECTILE.getKey().getKey())) {
                        ////////////////////////////////////// 投射物保護 //////////////////////////////////////
                        // 倍率 0.4
                        Random_4(event, entry);

                    } else if (entry.getKey().getKey().getKey().equals(Enchantment.RIPTIDE.getKey().getKey())) {
                        ////////////////////////////////////// 波濤       //////////////////////////////////////
                        // 倍率 0.3
                        Random_3(event, entry);

                    } else if (entry.getKey().getKey().getKey().equals(Enchantment.SILK_TOUCH.getKey().getKey())) {
                        ////////////////////////////////////// 絲綢之觸   //////////////////////////////////////

                    } else if (entry.getKey().getKey().getKey().equals(Enchantment.SWEEPING_EDGE.getKey().getKey())) {
                        ////////////////////////////////////// 橫掃之刃   //////////////////////////////////////
                        // 倍率 0.3
                        Random_3(event, entry);

                    } else if (entry.getKey().getKey().getKey().equals(Enchantment.THORNS.getKey().getKey())) {
                        ////////////////////////////////////// 尖刺       //////////////////////////////////////
                        // 倍率 0.3
                        Random_3(event, entry);

                    } else if (entry.getKey().getKey().getKey().equals(Enchantment.VANISHING_CURSE.getKey().getKey())) {
                        ////////////////////////////////////// 消失詛咒   //////////////////////////////////////

                    } else if (entry.getKey().getKey().getKey().equals(Enchantment.WATER_WORKER.getKey().getKey())) {
                        ////////////////////////////////////// 親水性     //////////////////////////////////////

                    }

                }


                if (map == null) {
                    return;
                }
                for (Map.Entry<Enchantment, Integer> entry : map.entrySet()) {
                    // 超過 6 等 強制消失詛咒
                    if (entry.getValue() >= 6) {
                        map.put(Enchantment.VANISHING_CURSE, 1);

                    }
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }

    // 倍率 0.3
    static final public void Random_3(EnchantItemEvent event, Map.Entry<Enchantment, Integer> entry) {
        int exp = event.getExpLevelCost();
        double level = 1;
        if (exp <= 10) {
            level = 1;
        } else if (exp > 10 && exp <= 20) {
            level = 3.2;
        } else if (exp > 20) {
            level = 7.4;
        }

        double random = Math.random() * 100;
        if (random < 0.000000129140 * level) {
            event.getEnchantsToAdd().put(entry.getKey(), 18);
        } else if (random < 0.000000430467 * level) {
            event.getEnchantsToAdd().put(entry.getKey(), 17);
        } else if (random < 0.000001434890 * level) {
            event.getEnchantsToAdd().put(entry.getKey(), 16);
        } else if (random < 0.000004782969 * level) {
            event.getEnchantsToAdd().put(entry.getKey(), 15);
        } else if (random < 0.00001594323 * level) {
            event.getEnchantsToAdd().put(entry.getKey(), 14);
        } else if (random < 0.0000531441 * level) {
            event.getEnchantsToAdd().put(entry.getKey(), 13);
        } else if (random < 0.000177147 * level) {
            event.getEnchantsToAdd().put(entry.getKey(), 12);
        } else if (random < 0.00059049 * level) {
            event.getEnchantsToAdd().put(entry.getKey(), 11);
        } else if (random < 0.0019683 * level) {
            event.getEnchantsToAdd().put(entry.getKey(), 10);
        } else if (random < 0.006561 * level) {
            event.getEnchantsToAdd().put(entry.getKey(), 9);
        } else if (random < 0.02187 * level) {
            event.getEnchantsToAdd().put(entry.getKey(), 8);
        } else if (random < 0.0729 * level) {
            event.getEnchantsToAdd().put(entry.getKey(), 7);
        } else if (random < 0.243 * level) {
            event.getEnchantsToAdd().put(entry.getKey(), 6);
        } else if (random < 0.81 * level) {
            event.getEnchantsToAdd().put(entry.getKey(), 5);
        } else if (random < 2.7 * level) {
            event.getEnchantsToAdd().put(entry.getKey(), 4);
        } else if (random < 9 * level) {
            event.getEnchantsToAdd().put(entry.getKey(), 3);
        } else if (random < 30 * level) {
            event.getEnchantsToAdd().put(entry.getKey(), 2);
        } else {
            event.getEnchantsToAdd().put(entry.getKey(), 1);
        }

    }

    // 倍率 0.4
    static final public void Random_4(EnchantItemEvent event, Map.Entry<Enchantment, Integer> entry) {
        int exp = event.getExpLevelCost();
        double level = 1;
        if (exp <= 10) {
            level = 1;
        } else if (exp > 10 && exp <= 20) {
            level = 3.2;
        } else if (exp > 20) {
            level = 7.4;
        }

        double random = Math.random() * 100;
        if (random < 0.0000027487790 * level) {
            event.getEnchantsToAdd().put(entry.getKey(), 20);
        } else if (random < 0.0000068719476 * level) {
            event.getEnchantsToAdd().put(entry.getKey(), 19);
        } else if (random < 0.0000171798691 * level) {
            event.getEnchantsToAdd().put(entry.getKey(), 18);
        } else if (random < 0.0000429496729 * level) {
            event.getEnchantsToAdd().put(entry.getKey(), 17);
        } else if (random < 0.0001073741824 * level) {
            event.getEnchantsToAdd().put(entry.getKey(), 16);
        } else if (random < 0.000268435456 * level) {
            event.getEnchantsToAdd().put(entry.getKey(), 15);
        } else if (random < 0.00067108864 * level) {
            event.getEnchantsToAdd().put(entry.getKey(), 14);
        } else if (random < 0.0016777216 * level) {
            event.getEnchantsToAdd().put(entry.getKey(), 13);
        } else if (random < 0.004194304 * level) {
            event.getEnchantsToAdd().put(entry.getKey(), 12);
        } else if (random < 0.01048576 * level) {
            event.getEnchantsToAdd().put(entry.getKey(), 11);
        } else if (random < 0.0262144 * level) {
            event.getEnchantsToAdd().put(entry.getKey(), 10);
        } else if (random < 0.065536 * level) {
            event.getEnchantsToAdd().put(entry.getKey(), 9);
        } else if (random < 0.16384 * level) {
            event.getEnchantsToAdd().put(entry.getKey(), 8);
        } else if (random < 0.4096 * level) {
            event.getEnchantsToAdd().put(entry.getKey(), 7);
        } else if (random < 1.024 * level) {
            event.getEnchantsToAdd().put(entry.getKey(), 6);
        } else if (random < 2.56 * level) {
            event.getEnchantsToAdd().put(entry.getKey(), 5);
        } else if (random < 6.4 * level) {
            event.getEnchantsToAdd().put(entry.getKey(), 4);
        } else if (random < 16 * level) {
            event.getEnchantsToAdd().put(entry.getKey(), 3);
        } else if (random < 40 * level) {
            event.getEnchantsToAdd().put(entry.getKey(), 2);
        } else {
            event.getEnchantsToAdd().put(entry.getKey(), 1);
        }

    }


}
