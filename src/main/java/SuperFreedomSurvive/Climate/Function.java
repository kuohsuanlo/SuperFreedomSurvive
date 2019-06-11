package SuperFreedomSurvive.Climate;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;

public class Function {

    // 進行隨機天氣抽選
    public static boolean getRandomWeather() {
        if ( Time.getCalendar().isAlwaysWeather() ) {
            // 整天下雨
            return true;

        } else if ( Time.getCalendar().isAlwaysNoWeather() ) {
            // 永不下雨
            return false;

        } else {
            if ( (int) (Math.random() * 2) == 1 ) {
                return true;
            } else {
                return false;
            }
        }
    }









    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void chunkBiomeConversionSeason( Chunk chunk ) {
        //int X = ChunkX * 16;
        //int Z = ChunkZ * 16;
        World world = chunk.getWorld();
        Biome biome = null;
        Block block = null;
        // 檢查當前季節
        switch (Time.getSeason()) {
            case SPRING:
                // 春季
                for (int i = 0; i < 16 ; ++i ) {
                    for (int ii = 0; ii < 16; ++ii) {

                        block = chunk.getBlock( i , 0 , ii );

                        switch (block.getBiome()) {
                            case SWAMP:
                            case DESERT_LAKES:
                            case FROZEN_RIVER:
                            case RIVER:
                                // 河流
                                biome = Biome.RIVER;
                                break;

                            case OCEAN:
                            case FROZEN_OCEAN:
                            case WARM_OCEAN:
                            case COLD_OCEAN:
                            case LUKEWARM_OCEAN:
                                // 海洋
                                biome = Biome.OCEAN;
                                break;

                            case DEEP_OCEAN:
                            case DEEP_WARM_OCEAN:
                            case DEEP_COLD_OCEAN:
                            case DEEP_FROZEN_OCEAN:
                            case DEEP_LUKEWARM_OCEAN:
                                // 深海
                                biome = Biome.DEEP_OCEAN;
                                break;

                            case SNOWY_MOUNTAINS:
                            case MOUNTAIN_EDGE:
                            case WOODED_MOUNTAINS:
                            case GRAVELLY_MOUNTAINS:
                            case TAIGA_MOUNTAINS:
                            case SNOWY_TAIGA_MOUNTAINS:
                            case MODIFIED_GRAVELLY_MOUNTAINS:
                            case SAVANNA_PLATEAU:
                            case WOODED_BADLANDS_PLATEAU:
                            case BADLANDS_PLATEAU:
                            case SHATTERED_SAVANNA_PLATEAU:
                            case MODIFIED_BADLANDS_PLATEAU:
                            case MOUNTAINS:
                                // 山地
                                biome = Biome.MOUNTAINS;
                                break;

                            case MODIFIED_WOODED_BADLANDS_PLATEAU:
                            case TAIGA:
                            case SNOWY_TAIGA:
                            case GIANT_TREE_TAIGA:
                            case BIRCH_FOREST:
                            case BIRCH_FOREST_HILLS:
                            case DARK_FOREST:
                            case GIANT_SPRUCE_TAIGA:
                            case TALL_BIRCH_FOREST:
                            case JUNGLE:
                            case MODIFIED_JUNGLE:
                            case MODIFIED_JUNGLE_EDGE:
                            case JUNGLE_EDGE:
                            case FOREST:
                            case FLOWER_FOREST:
                                // 森林/叢林
                                biome = Biome.FLOWER_FOREST;
                                break;

                            case DESERT_HILLS:
                            case WOODED_HILLS:
                            case SNOWY_TAIGA_HILLS:
                            case GIANT_TREE_TAIGA_HILLS:
                            case SWAMP_HILLS:
                            case TALL_BIRCH_HILLS:
                            case DARK_FOREST_HILLS:
                            case GIANT_SPRUCE_TAIGA_HILLS:
                            case JUNGLE_HILLS:
                            case TAIGA_HILLS:
                                // 丘陵
                                biome = Biome.JUNGLE_HILLS;
                                break;

                            case DESERT:
                            case SNOWY_TUNDRA:
                            case MUSHROOM_FIELDS:
                            case SAVANNA:
                            case BADLANDS:
                            case ICE_SPIKES:
                            case SHATTERED_SAVANNA:
                            case ERODED_BADLANDS	:
                            case PLAINS:
                            case SUNFLOWER_PLAINS:
                            case THE_VOID:
                                //平原
                                biome = Biome.SUNFLOWER_PLAINS;
                                break;

                            case MUSHROOM_FIELD_SHORE:
                            case SNOWY_BEACH:
                            case STONE_SHORE:
                            case BEACH:
                                //海岸
                                biome = Biome.MUSHROOM_FIELD_SHORE;
                                break;

                            default:
                                break;
                        }
                        // 替換
                        if ( biome != null ) {
                            block.setBiome(biome);
                        }
                    }
                }
                break;
            /////////////////////////////////////////////////////////////////////////
            case SUMMER:
                // 夏季
                for (int i = 0; i < 16 ; ++i ) {
                    for (int ii = 0; ii < 16; ++ii) {

                        block = chunk.getBlock( i , 0 , ii );

                        switch (block.getBiome()) {
                            case SWAMP:
                            case DESERT_LAKES:
                            case FROZEN_RIVER:
                            case RIVER:
                                // 河流
                                biome = Biome.RIVER;
                                break;

                            case OCEAN:
                            case FROZEN_OCEAN:
                            case WARM_OCEAN:
                            case COLD_OCEAN:
                            case LUKEWARM_OCEAN:
                                // 海洋
                                biome = Biome.OCEAN;
                                break;

                            case DEEP_OCEAN:
                            case DEEP_WARM_OCEAN:
                            case DEEP_COLD_OCEAN:
                            case DEEP_FROZEN_OCEAN:
                            case DEEP_LUKEWARM_OCEAN:
                                // 深海
                                biome = Biome.DEEP_OCEAN;
                                break;

                            case SNOWY_MOUNTAINS:
                            case MOUNTAIN_EDGE:
                            case WOODED_MOUNTAINS:
                            case GRAVELLY_MOUNTAINS:
                            case TAIGA_MOUNTAINS:
                            case SNOWY_TAIGA_MOUNTAINS:
                            case MODIFIED_GRAVELLY_MOUNTAINS:
                            case SAVANNA_PLATEAU:
                            case WOODED_BADLANDS_PLATEAU:
                            case BADLANDS_PLATEAU:
                            case SHATTERED_SAVANNA_PLATEAU:
                            case MODIFIED_BADLANDS_PLATEAU:
                            case MOUNTAINS:
                                // 山地
                                biome = Biome.SAVANNA_PLATEAU;
                                break;

                            case MODIFIED_WOODED_BADLANDS_PLATEAU:
                            case TAIGA:
                            case SNOWY_TAIGA:
                            case GIANT_TREE_TAIGA:
                            case BIRCH_FOREST:
                            case BIRCH_FOREST_HILLS:
                            case DARK_FOREST:
                            case GIANT_SPRUCE_TAIGA:
                            case TALL_BIRCH_FOREST:
                            case JUNGLE:
                            case MODIFIED_JUNGLE:
                            case MODIFIED_JUNGLE_EDGE:
                            case JUNGLE_EDGE:
                            case FOREST:
                            case FLOWER_FOREST:
                                // 森林/叢林
                                biome = Biome.FOREST;
                                break;

                            case DESERT_HILLS:
                            case WOODED_HILLS:
                            case SNOWY_TAIGA_HILLS:
                            case GIANT_TREE_TAIGA_HILLS:
                            case SWAMP_HILLS:
                            case TALL_BIRCH_HILLS:
                            case DARK_FOREST_HILLS:
                            case GIANT_SPRUCE_TAIGA_HILLS:
                            case JUNGLE_HILLS:
                            case TAIGA_HILLS:
                                // 丘陵
                                biome = Biome.DESERT_HILLS;
                                break;

                            case DESERT:
                            case SNOWY_TUNDRA:
                            case MUSHROOM_FIELDS:
                            case SAVANNA:
                            case BADLANDS:
                            case ICE_SPIKES:
                            case SHATTERED_SAVANNA:
                            case ERODED_BADLANDS	:
                            case PLAINS:
                            case SUNFLOWER_PLAINS:
                            case THE_VOID:
                                //平原
                                biome = Biome.DESERT;
                                break;

                            case MUSHROOM_FIELD_SHORE:
                            case SNOWY_BEACH:
                            case STONE_SHORE:
                            case BEACH:
                                //海岸
                                biome = Biome.BEACH;
                                break;

                            default:
                                break;
                        }
                        // 替換
                        if ( biome != null ) {
                            block.setBiome(biome);
                        }
                    }
                }
                break;
            /////////////////////////////////////////////////////////////////////////
            case FALL:
                // 秋季
                for (int i = 0; i < 16 ; ++i ) {
                    for (int ii = 0; ii < 16; ++ii) {

                        block = chunk.getBlock( i , 0 , ii );

                        switch (block.getBiome()) {
                            case SWAMP:
                            case DESERT_LAKES:
                            case FROZEN_RIVER:
                            case RIVER:
                                // 河流
                                biome = Biome.RIVER;
                                break;

                            case OCEAN:
                            case FROZEN_OCEAN:
                            case WARM_OCEAN:
                            case COLD_OCEAN:
                            case LUKEWARM_OCEAN:
                                // 海洋
                                biome = Biome.COLD_OCEAN;
                                break;

                            case DEEP_OCEAN:
                            case DEEP_WARM_OCEAN:
                            case DEEP_COLD_OCEAN:
                            case DEEP_FROZEN_OCEAN:
                            case DEEP_LUKEWARM_OCEAN:
                                // 深海
                                biome = Biome.DEEP_COLD_OCEAN;
                                break;

                            case SNOWY_MOUNTAINS:
                            case MOUNTAIN_EDGE:
                            case WOODED_MOUNTAINS:
                            case GRAVELLY_MOUNTAINS:
                            case TAIGA_MOUNTAINS:
                            case SNOWY_TAIGA_MOUNTAINS:
                            case MODIFIED_GRAVELLY_MOUNTAINS:
                            case SAVANNA_PLATEAU:
                            case WOODED_BADLANDS_PLATEAU:
                            case BADLANDS_PLATEAU:
                            case SHATTERED_SAVANNA_PLATEAU:
                            case MODIFIED_BADLANDS_PLATEAU:
                            case MOUNTAINS:
                                // 山地
                                biome = Biome.GRAVELLY_MOUNTAINS;
                                break;

                            case MODIFIED_WOODED_BADLANDS_PLATEAU:
                            case TAIGA:
                            case SNOWY_TAIGA:
                            case GIANT_TREE_TAIGA:
                            case BIRCH_FOREST:
                            case BIRCH_FOREST_HILLS:
                            case DARK_FOREST:
                            case GIANT_SPRUCE_TAIGA:
                            case TALL_BIRCH_FOREST:
                            case JUNGLE:
                            case MODIFIED_JUNGLE:
                            case MODIFIED_JUNGLE_EDGE:
                            case JUNGLE_EDGE:
                            case FOREST:
                            case FLOWER_FOREST:
                                // 森林/叢林
                                biome = Biome.TAIGA;
                                break;

                            case DESERT_HILLS:
                            case WOODED_HILLS:
                            case SNOWY_TAIGA_HILLS:
                            case GIANT_TREE_TAIGA_HILLS:
                            case SWAMP_HILLS:
                            case TALL_BIRCH_HILLS:
                            case DARK_FOREST_HILLS:
                            case GIANT_SPRUCE_TAIGA_HILLS:
                            case JUNGLE_HILLS:
                            case TAIGA_HILLS:
                                // 丘陵
                                biome = Biome.DARK_FOREST_HILLS;
                                break;

                            case DESERT:
                            case SNOWY_TUNDRA:
                            case MUSHROOM_FIELDS:
                            case SAVANNA:
                            case BADLANDS:
                            case ICE_SPIKES:
                            case SHATTERED_SAVANNA:
                            case ERODED_BADLANDS	:
                            case PLAINS:
                            case SUNFLOWER_PLAINS:
                            case THE_VOID:
                                //平原
                                biome = Biome.PLAINS;
                                break;

                            case MUSHROOM_FIELD_SHORE:
                            case SNOWY_BEACH:
                            case STONE_SHORE:
                            case BEACH:
                                //海岸
                                biome = Biome.STONE_SHORE;
                                break;

                            default:
                                break;
                        }
                        // 替換
                        if ( biome != null ) {
                            block.setBiome(biome);
                        }
                    }
                }
                break;
            /////////////////////////////////////////////////////////////////////////
            case WINTER:
                // 冬季
                for (int i = 0; i < 16 ; ++i ) {
                    for (int ii = 0; ii < 16; ++ii) {

                        block = chunk.getBlock( i , 0 , ii );

                        switch (block.getBiome()) {
                            case SWAMP:
                            case DESERT_LAKES:
                            case FROZEN_RIVER:
                            case RIVER:
                                // 河流
                                biome = Biome.FROZEN_RIVER;
                                break;

                            case OCEAN:
                            case FROZEN_OCEAN:
                            case WARM_OCEAN:
                            case COLD_OCEAN:
                            case LUKEWARM_OCEAN:
                                // 海洋
                                biome = Biome.FROZEN_OCEAN;
                                break;

                            case DEEP_OCEAN:
                            case DEEP_WARM_OCEAN:
                            case DEEP_COLD_OCEAN:
                            case DEEP_FROZEN_OCEAN:
                            case DEEP_LUKEWARM_OCEAN:
                                // 深海
                                biome = Biome.DEEP_FROZEN_OCEAN;
                                break;

                            case SNOWY_MOUNTAINS:
                            case MOUNTAIN_EDGE:
                            case WOODED_MOUNTAINS:
                            case GRAVELLY_MOUNTAINS:
                            case TAIGA_MOUNTAINS:
                            case SNOWY_TAIGA_MOUNTAINS:
                            case MODIFIED_GRAVELLY_MOUNTAINS:
                            case SAVANNA_PLATEAU:
                            case WOODED_BADLANDS_PLATEAU:
                            case BADLANDS_PLATEAU:
                            case SHATTERED_SAVANNA_PLATEAU:
                            case MODIFIED_BADLANDS_PLATEAU:
                            case MOUNTAINS:
                                // 山地
                                biome = Biome.SNOWY_TAIGA_MOUNTAINS;
                                break;

                            case MODIFIED_WOODED_BADLANDS_PLATEAU:
                            case TAIGA:
                            case SNOWY_TAIGA:
                            case GIANT_TREE_TAIGA:
                            case BIRCH_FOREST:
                            case BIRCH_FOREST_HILLS:
                            case DARK_FOREST:
                            case GIANT_SPRUCE_TAIGA:
                            case TALL_BIRCH_FOREST:
                            case JUNGLE:
                            case MODIFIED_JUNGLE:
                            case MODIFIED_JUNGLE_EDGE:
                            case JUNGLE_EDGE:
                            case FOREST:
                            case FLOWER_FOREST:
                                // 森林/叢林
                                biome = Biome.SNOWY_TAIGA;
                                break;

                            case DESERT_HILLS:
                            case WOODED_HILLS:
                            case SNOWY_TAIGA_HILLS:
                            case GIANT_TREE_TAIGA_HILLS:
                            case SWAMP_HILLS:
                            case TALL_BIRCH_HILLS:
                            case DARK_FOREST_HILLS:
                            case GIANT_SPRUCE_TAIGA_HILLS:
                            case JUNGLE_HILLS:
                            case TAIGA_HILLS:
                                // 丘陵
                                biome = Biome.SNOWY_TAIGA_HILLS;
                                break;

                            case DESERT:
                            case SNOWY_TUNDRA:
                            case MUSHROOM_FIELDS:
                            case SAVANNA:
                            case BADLANDS:
                            case ICE_SPIKES:
                            case SHATTERED_SAVANNA:
                            case ERODED_BADLANDS	:
                            case PLAINS:
                            case SUNFLOWER_PLAINS:
                            case THE_VOID:
                                //平原
                                biome = Biome.SNOWY_TUNDRA;
                                break;

                            case MUSHROOM_FIELD_SHORE:
                            case SNOWY_BEACH:
                            case STONE_SHORE:
                            case BEACH:
                                //海岸
                                biome = Biome.SNOWY_BEACH;
                                break;

                            default:
                                break;
                        }
                        // 替換
                        if ( biome != null ) {
                            block.setBiome(biome);
                        }

                    }
                }
                break;
            /////////////////////////////////////////////////////////////////////////
            default:
                break;
        }
        world.refreshChunk( chunk.getX() , chunk.getZ() ); // 重新發送區塊給所有玩家
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



}
/*

河流	RIVER	7
沙漠湖泊	DESERT_LAKES	130
沼澤	SWAMP	6
寒凍河流	FROZEN_RIVER	11



溫和海洋	LUKEWARM_OCEAN	45
溫暖海洋	WARM_OCEAN	44
寒冷海洋	COLD_OCEAN	46
寒凍海洋	FROZEN_OCEAN	10

海洋	OCEAN	0



溫和深海	DEEP_LUKEWARM_OCEAN	48
溫暖深海	DEEP_WARM_OCEAN	47
寒冷深海	DEEP_COLD_OCEAN	49
寒凍深海	DEEP_FROZEN_OCEAN	50

深海	DEEP_OCEAN	24



山地	MOUNTAINS	3
莽原高地	SAVANNA_PLATEAU	36
礫質山地	GRAVELLY_MOUNTAINS	131
冰雪針葉林山地	SNOWY_TAIGA_MOUNTAINS	158

山地邊緣	MOUNTAIN_EDGE	20
疏林山地	WOODED_MOUNTAINS	34
針葉林山地	TAIGA_MOUNTAINS	133
礫質山地+	MODIFIED_GRAVELLY_MOUNTAINS	162
疏林惡地高地	WOODED_BADLANDS_PLATEAU	38
冰雪山地	SNOWY_MOUNTAINS	13
惡地高地	BADLANDS_PLATEAU	39
零碎莽原高地	SHATTERED_SAVANNA_PLATEAU	164
特化惡地高地	MODIFIED_BADLANDS_PLATEAU	167



繁花森林	FLOWER_FOREST	132
森林	FOREST	4
針葉林	TAIGA	5
冰雪針葉林	SNOWY_TAIGA	30

黑森林	DARK_FOREST	29
叢林	JUNGLE	21
樺木森林	BIRCH_FOREST	27158
巨木針葉林	GIANT_TREE_TAIGA	32
樺木森林丘陵	BIRCH_FOREST_HILLS	28
巨杉針葉林	GIANT_SPRUCE_TAIGA	160
特化叢林	MODIFIED_JUNGLE	149
高樺木森林	TALL_BIRCH_FOREST	155
叢林邊緣變種	MODIFIED_JUNGLE_EDGE	151
叢林邊緣	JUNGLE_EDGE	23
特化疏林惡地高地	MODIFIED_WOODED_BADLANDS_PLATEAU	166



叢林丘陵	JUNGLE_HILLS	22
沙漠丘陵	DESERT_HILLS	17
黑森林丘陵	DARK_FOREST_HILLS	157
冰雪針葉林丘陵	SNOWY_TAIGA_HILLS	31

針葉林丘陵	TAIGA_HILLS	19
疏林丘陵	WOODED_HILLS	18
巨木針葉林丘陵	GIANT_TREE_TAIGA_HILLS	33
沼澤丘陵	SWAMP_HILLS	134
高樺木丘陵	TALL_BIRCH_HILLS	156
巨杉針葉林丘陵	GIANT_SPRUCE_TAIGA_HILLS	161



向日葵平原	SUNFLOWER_PLAINS	129
沙漠	DESERT	2
平原	PLAINS	1
冰雪凍原	SNOWY_TUNDRA	12

虛空	THE_VOID	127
莽原	SAVANNA	35
惡地	BADLANDS	37
冰刺	ICE_SPIKES	140
蘑菇地	MUSHROOM_FIELDS	14
零碎莽原	SHATTERED_SAVANNA	163
侵蝕惡地	ERODED_BADLANDS	165



磨菇地海岸	MUSHROOM_FIELD_SHORE	15
沙灘	BEACH	16
石岸	STONE_SHORE	25
冰雪沙灘	SNOWY_BEACH	26

*/