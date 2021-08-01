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

public class EndCrystalVein extends Feature<OreFeatureConfig> {

    public static final RuleTest TEST = new EndTest();

    public EndCrystalVein() {
        super(OreFeatureConfig.CODEC);
    }

    @Override
    public boolean place(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random, BlockPos pos, OreFeatureConfig oreFeatureConfig) {
        if (iSeedReader.getLevel().dimension().compareTo(Dimension.END) == 0) {
            if (random.nextFloat() < 0.002) {
                int range = random.nextInt(4) + 8;
                int height = random.nextInt(255 - range);
                BlockState blockState = Blocks.ORE_CRYSTAL.get().defaultBlockState();


                for (int i = 0; i <= range; i++) {
                    for (int j = 0; j <= i; j++) {
                        for (int k = 0; k <= i; k++) {
                            float v = random.nextFloat() * 2 + 2;
                            setBlock(iSeedReader, pos.offset(j - i / 2, height - range + i, k - i / 2), blockState.setValue(MagicCrystalOre.PURITY, Math.round(v)));
                        }
                    }
                }
                for (int i = 0; i <= range; i++) {
                    for (int j = 0; j <= i; j++) {
                        for (int k = 0; k <= i; k++) {
                            float v = random.nextFloat() * 2 + 2;
                            setBlock(iSeedReader, pos.offset(j - i / 2, height + range - i, k - i / 2), blockState.setValue(MagicCrystalOre.PURITY, Math.round(v)));
                        }
                    }
                }
            }
        }
        return true;
    }


    private static class EndTest extends RuleTest {
        private static HashSet<Block> blocks = new HashSet<>();

        public EndTest() {
            blocks.addAll(Arrays.asList(
                    net.minecraft.block.Blocks.AIR,
                    net.minecraft.block.Blocks.END_STONE
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
