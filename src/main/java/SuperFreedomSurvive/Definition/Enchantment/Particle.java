package SuperFreedomSurvive.Definition.Enchantment;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import java.util.ArrayList;
import java.util.List;

final public class Particle extends EnchantmentWrapper {
    // 粒子

    final static public Enchantment PARTICLE = new Particle("particle");

    public Particle(String name) {
        super(name);
    }

    //
    @Override
    final public Enchantment getEnchantment() {
        return PARTICLE;
        //return Enchantment.getByKey(getKey());
    }

    static final public EnchantmentStorageMeta addList(EnchantmentStorageMeta meta) {
        List<String> list = new ArrayList<String>();

        if (meta.getLore() != null) {
            list = meta.getLore();
        }
        list.add(0, "§f收穫");

        meta.setLore(list);
        return meta;
    }

    // 附魔最大等級
    @Override
    final public int getMaxLevel() {
        return 1;
    }

    // 附魔最初等級
    @Override
    final public int getStartLevel() {
        return 1;
    }

    // 獲取適合的物品目標
    @Override
    final public EnchantmentTarget getItemTarget() {
        return null;
    }

    // 此物品是否可附魔
    @Override
    final public boolean canEnchantItem(ItemStack item) {
        return true;
    }

    // 取得名稱
    @Override
    final public String getName() {
        return "harvesting";
    }

    // 是否為珍貴類型
    @Override
    final public boolean isTreasure() {
        return false;
    }

    // 是否有詛咒
    @Override
    final public boolean isCursed() {
        return false;
    }

    // 跟另一個附魔 是否有衝突
    @Override
    final public boolean conflictsWith(Enchantment other) {
        return false;
    }

}
