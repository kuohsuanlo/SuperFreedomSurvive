package SuperFreedomSurvive.Definition;

import SuperFreedomSurvive.Definition.Item.*;

final public class Composite {

    static final public void Recipe() {
        // 合成配方登記

        try {

            storag_animal_egg.Composite();
            // 魔法餅乾
            delicious_cookie.Composite();
            // 幸運方塊
            lucky_block.Composite();
            // 屏障
            barrier_block.Composite();
            // 生怪磚
            spawner_block.Composite();
            // 自訂怪物生怪磚
            custom_spawner_block.Composite();
            // 附魔金蘋果
            enchanted_golden_apple.Composite();
            // 經驗值回收瓶
            experience_collect_bottle.Composite();
            // 生命核心
            max_health_core.Composite();





        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }
}