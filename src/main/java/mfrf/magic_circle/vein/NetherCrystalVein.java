package mfrf.magic_circle.vein;

import mfrf.magic_circle.block.resources.MagicCrystalOre;
import mfrf.magic_circle.registry_lists.Blocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Dimension;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.template.IRuleTestType;
import net.minecraft.world.gen.feature.template.RuleTest;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

public class NetherCrystalVein extends Feature<OreFeatureConfig> {
    public static final RuleTest TEST = new NetherTest();

    public NetherCrystalVein() {
        super(OreFeatureConfig.CODEC);
    }

    @Override
    public boolean place(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random, BlockPos pos, OreFeatureConfig oreFeatureConfig) {
        if (iSeedReader.getLevel().dimension().compareTo(Dimension.NETHER) == 0 && random.nextFloat() < 0.005) {
            int range = random.nextInt(8) + 12;
            int height = random.nextInt(128 - range - 1);
            BlockState blockState = Blocks.ORE_CRYSTAL.get().defaultBlockState();

            for (int i = 0; i <= range; i++) {
                for (int j = 0; j <= i; j++) {
                    for (int k = 0; k <= i; k++) {
                        float v = random.nextFloat() * 2 + 0.5f;
                        int actualHeight = height - (range - i);
                        if(actualHeight >= 4) {
                            setBlock(iSeedReader, pos.offset(j - i / 2, actualHeight, k - i / 2), blockState.setValue(MagicCrystalOre.PURITY, Math.round(v)));
                        }
                    }
                }
            }

        }
        return true;
    }


    private static class NetherTest extends RuleTest {
        private static HashSet<Block> blocks = new HashSet<>();

        public NetherTest() {
            blocks.addAll(Arrays.asList(
                    net.minecraft.block.Blocks.LAVA,
                    net.minecraft.block.Blocks.NETHERRACK,
                    net.minecraft.block.Blocks.GLOWSTONE,
                    net.minecraft.block.Blocks.NETHER_QUARTZ_ORE,
                    net.minecraft.block.Blocks.BASALT,
                    net.minecraft.block.Blocks.POLISHED_BASALT,
                    net.minecraft.block.Blocks.CRIMSON_NYLIUM,
                    net.minecraft.block.Blocks.AIR
            ));
        }

        @Override
        public boolean test(BlockState p_215181_1_, Random p_215181_2_) {
            return blocks.contains(p_215181_1_.getBlock());
        }

        @Override
        protected IRuleTestType<?> getType() {
            return IRuleTestType.BLOCK_TEST;
        }
    }
}
