package SuperFreedomSurvive.Definition.Block;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

final public class lucky_block {

    // 幸運方塊
    static final public void Use(BlockBreakEvent event) {
        //  當一個方塊被玩家破壞的時候

        event.setCancelled(true);

        event.getBlock().setType(Material.AIR);

        SuperFreedomSurvive.Block.Data.Remove(event.getBlock().getLocation()); // 移除資料值

        Produce(event.getBlock().getLocation());

    }


    // 產生改變
    static final public void Produce(Location location) {
        // 隨機產生事件
        switch ((int) (Math.random() * 5)) {
            // 產生物品
            case 0:
            case 1:
            case 2:
            case 3:
                location.getWorld().dropItem(location, Item_List());
                break;
            case 4:
            case 5:
                location.getWorld().spawnEntity(location, entity_List());
                break;
        }
    }

    static final public EntityType entity_List() {
        switch ((int) (Math.random() * 55)) {
            case 0:
                return EntityType.BLAZE;

            case 1:
                return EntityType.CAVE_SPIDER;
            case 2:
                return EntityType.CHICKEN;
            case 3:
                return EntityType.COD;
            case 4:
                return EntityType.COW;
            case 5:
                return EntityType.CREEPER;

            case 6:
                return EntityType.DONKEY;
            case 7:
                return EntityType.DOLPHIN;
            case 8:
                return EntityType.DROWNED;

            case 9:
                return EntityType.ELDER_GUARDIAN;
            case 10:
                return EntityType.ENDERMAN;
            case 11:
                return EntityType.ENDERMITE;
            case 12:
                return EntityType.EVOKER;

            case 13:
                return EntityType.GHAST;
            case 14:
                return EntityType.GUARDIAN;

            case 15:
                return EntityType.HORSE;
            case 16:
                return EntityType.HUSK;

            case 17:
                return EntityType.LLAMA;

            case 18:
                return EntityType.MAGMA_CUBE;
            case 19:
                return EntityType.MUSHROOM_COW;
            case 20:
                return EntityType.MULE;

            case 21:
                return EntityType.OCELOT;

            case 22:
                return EntityType.PARROT;
            case 23:
                return EntityType.PHANTOM;
            case 24:
                return EntityType.PIG;
            case 25:
                return EntityType.POLAR_BEAR;
            case 26:
                return EntityType.PUFFERFISH;

            case 27:
                return EntityType.RABBIT;

            case 28:
                return EntityType.SALMON;
            case 29:
                return EntityType.SHEEP;
            case 30:
                return EntityType.SHULKER;
            case 31:
                return EntityType.SILVERFISH;
            case 32:
                return EntityType.SKELETON;
            case 33:
                return EntityType.SKELETON_HORSE;
            case 34:
                return EntityType.SLIME;
            case 35:
                return EntityType.SPIDER;
            case 36:
                return EntityType.SQUID;
            case 37:
                return EntityType.STRAY;

            case 38:
                return EntityType.TROPICAL_FISH;
            case 39:
                return EntityType.TURTLE;

            case 40:
                return EntityType.VEX;
            case 41:
                return EntityType.VILLAGER;
            case 42:
                return EntityType.VINDICATOR;

            case 43:
                return EntityType.WITCH;
            case 44:
                return EntityType.WITHER;
            case 45:
                return EntityType.WOLF;

            case 46:
                return EntityType.ZOMBIE;
            case 47:
                return EntityType.ZOMBIE_HORSE;
            case 48:
                return EntityType.PIG_ZOMBIE;
            case 49:
                return EntityType.ZOMBIE_VILLAGER;
        }
        return EntityType.PRIMED_TNT;
    }


    static final public ItemStack Item_List() {

        switch ((int) (Math.random() * 201)) {
            // 礦物
            case 0:
            case 1:
                return new ItemStack(Material.REDSTONE, 2);
            case 2:
            case 3:
                return new ItemStack(Material.LAPIS_LAZULI, 2);
            case 4:
            case 5:
                return new ItemStack(Material.CHARCOAL, 2);
            case 6:
            case 7:
                return new ItemStack(Material.COAL, 2);
            case 8:
            case 9:
                return new ItemStack(Material.IRON_INGOT, 2);
            case 10:
            case 11:
                return new ItemStack(Material.GOLD_INGOT, 2);
            case 12:
            case 13:
                return new ItemStack(Material.DIAMOND, 2);
            case 14:
            case 15:
                return new ItemStack(Material.EMERALD, 2);


            // 礦物 磚
            case 16:
                return new ItemStack(Material.REDSTONE_BLOCK, 1);
            case 17:
                return new ItemStack(Material.LAPIS_BLOCK, 1);
            case 18:
                return new ItemStack(Material.COAL_BLOCK, 1);
            case 19:
                return new ItemStack(Material.IRON_BLOCK, 1);
            case 20:
                return new ItemStack(Material.GOLD_BLOCK, 1);
            case 21:
                return new ItemStack(Material.DIAMOND_BLOCK, 1);
            case 22:
                return new ItemStack(Material.EMERALD_BLOCK, 1);


            // 石頭 泥土 沙子
            case 23:
            case 24:
            case 25:
                return new ItemStack(Material.DIRT, 1);
            case 26:
            case 27:
                return new ItemStack(Material.COBBLESTONE, 1);
            case 28:
            case 29:
                return new ItemStack(Material.SAND, 1);
            case 30:
            case 31:
                return new ItemStack(Material.MELON, 1);
            case 32:
            case 33:
                return new ItemStack(Material.PUMPKIN, 1);
            case 34:
            case 35:
                return new ItemStack(Material.HAY_BLOCK, 1);
            case 36:
            case 37:
                return new ItemStack(Material.CAKE, 1);
            case 38:
            case 39:
                return new ItemStack(Material.PAPER, 1);


            // 木頭
            case 40:
                return new ItemStack(Material.ACACIA_LOG, 1);
            case 41:
                return new ItemStack(Material.BIRCH_LOG, 1);
            case 42:
                return new ItemStack(Material.DARK_OAK_LOG, 1);
            case 43:
                return new ItemStack(Material.JUNGLE_LOG, 1);
            case 44:
                return new ItemStack(Material.OAK_LOG, 1);
            case 45:
                return new ItemStack(Material.SPRUCE_LOG, 1);


            // 樹苗
            case 46:
                return new ItemStack(Material.ACACIA_SAPLING, 1);
            case 47:
                return new ItemStack(Material.BIRCH_SAPLING, 1);
            case 48:
                return new ItemStack(Material.DARK_OAK_SAPLING, 1);
            case 49:
                return new ItemStack(Material.JUNGLE_SAPLING, 1);
            case 50:
                return new ItemStack(Material.OAK_SAPLING, 1);
            case 51:
                return new ItemStack(Material.SPRUCE_SAPLING, 1);


            // 超稀有類
            case 52:
            case 53:
                return new ItemStack(Material.ENCHANTED_GOLDEN_APPLE, 1);


            // 經驗瓶
            case 54:
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
            case 64:
                return new ItemStack(Material.EXPERIENCE_BOTTLE, 1);


            // 武器
            // 劍
            case 65:
            case 66:
                return new ItemStack(Material.DIAMOND_SWORD, 1);
            case 67:
            case 68:
                return new ItemStack(Material.GOLDEN_SWORD, 1);
            case 69:
            case 70:
                return new ItemStack(Material.IRON_SWORD, 1);
            case 71:
            case 72:
                return new ItemStack(Material.STONE_SWORD, 1);
            case 73:
            case 74:
                return new ItemStack(Material.WOODEN_SWORD, 1);


            // 斧
            case 75:
            case 76:
                return new ItemStack(Material.DIAMOND_AXE, 1);
            case 77:
            case 78:
                return new ItemStack(Material.GOLDEN_AXE, 1);
            case 79:
            case 80:
                return new ItemStack(Material.IRON_AXE, 1);
            case 81:
            case 82:
                return new ItemStack(Material.STONE_AXE, 1);
            case 83:
            case 84:
                return new ItemStack(Material.WOODEN_AXE, 1);


            // 鏟
            case 85:
            case 86:
                return new ItemStack(Material.DIAMOND_SWORD, 1);
            case 87:
            case 88:
                return new ItemStack(Material.GOLDEN_SWORD, 1);
            case 89:
            case 90:
                return new ItemStack(Material.IRON_SWORD, 1);
            case 91:
            case 92:
                return new ItemStack(Material.STONE_SWORD, 1);
            case 93:
            case 94:
                return new ItemStack(Material.WOODEN_SWORD, 1);


            // 稿
            case 95:
            case 96:
                return new ItemStack(Material.DIAMOND_PICKAXE, 1);
            case 97:
            case 98:
                return new ItemStack(Material.GOLDEN_PICKAXE, 1);
            case 99:
            case 100:
                return new ItemStack(Material.IRON_PICKAXE, 1);
            case 101:
            case 102:
                return new ItemStack(Material.STONE_PICKAXE, 1);
            case 103:
            case 104:
                return new ItemStack(Material.WOODEN_PICKAXE, 1);


            // 裝備
            // 帽
            case 105:
            case 106:
                return new ItemStack(Material.CHAINMAIL_HELMET, 1);
            case 107:
            case 108:
                return new ItemStack(Material.DIAMOND_HELMET, 1);
            case 109:
            case 110:
                return new ItemStack(Material.GOLDEN_HELMET, 1);
            case 111:
            case 112:
                return new ItemStack(Material.IRON_HELMET, 1);
            case 113:
            case 114:
                return new ItemStack(Material.LEATHER_HELMET, 1);


            // 體
            case 115:
            case 116:
                return new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1);
            case 117:
            case 118:
                return new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
            case 119:
            case 120:
                return new ItemStack(Material.GOLDEN_CHESTPLATE, 1);
            case 121:
            case 122:
                return new ItemStack(Material.IRON_CHESTPLATE, 1);
            case 123:
            case 124:
                return new ItemStack(Material.LEATHER_CHESTPLATE, 1);


            // 褲子
            case 125:
            case 126:
                return new ItemStack(Material.CHAINMAIL_LEGGINGS, 1);
            case 127:
            case 128:
                return new ItemStack(Material.DIAMOND_LEGGINGS, 1);
            case 129:
            case 130:
                return new ItemStack(Material.GOLDEN_LEGGINGS, 1);
            case 131:
            case 132:
                return new ItemStack(Material.IRON_LEGGINGS, 1);
            case 133:
            case 134:
                return new ItemStack(Material.LEATHER_LEGGINGS, 1);


            // 鞋子
            case 135:
            case 136:
                return new ItemStack(Material.CHAINMAIL_BOOTS, 1);
            case 137:
            case 138:
                return new ItemStack(Material.DIAMOND_BOOTS, 1);
            case 139:
            case 140:
                return new ItemStack(Material.GOLDEN_BOOTS, 1);
            case 141:
            case 142:
                return new ItemStack(Material.IRON_BOOTS, 1);
            case 143:
            case 144:
                return new ItemStack(Material.LEATHER_BOOTS, 1);


            // 海龜帽子
            case 145:
            case 146:
                return new ItemStack(Material.TURTLE_HELMET, 1);


            // 弓
            case 147:
            case 148:
            case 149:
            case 150:
            case 151:
            case 152:
            case 153:
            case 154:
            case 155:
            case 156:
                return new ItemStack(Material.BOW, 1);


            // 弓箭
            case 157:
            case 158:
            case 159:
            case 160:
            case 161:
            case 162:
            case 163:
            case 164:
            case 165:
            case 166:
                return new ItemStack(Material.ARROW, 1);
        }

        return new ItemStack(Material.BREAD, 2);
    }

}
