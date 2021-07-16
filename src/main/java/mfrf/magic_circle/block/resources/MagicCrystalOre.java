package mfrf.magic_circle.block.resources;

import mfrf.magic_circle.Config;
import mfrf.magic_circle.block.BlockBase;
import mfrf.magic_circle.magicutil.BaguaPrefer;
import mfrf.magic_circle.registry_lists.Capabilities;
import mfrf.magic_circle.registry_lists.Items;
import mfrf.magic_circle.util.MagicalItemContainer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.Stream;

public class MagicCrystalOre extends BlockBase {

    public static IntegerProperty PURITY = IntegerProperty.create("purity", 1, 5);

    public MagicCrystalOre(Properties p_i48440_1_) {
        super(p_i48440_1_);
    }


    @Override
    public void playerWillDestroy(World world, BlockPos pos, BlockState blockState, PlayerEntity playerEntity) {
        super.playerWillDestroy(world, pos, blockState, playerEntity);
        if (!world.isClientSide()) {
            HashSet<BlockPos> linked = new HashSet<>();
            find(pos, world, linked);
//            linked.remove(pos);
            double purity = Math.round(linked.stream().map(world::getBlockState).mapToInt(state -> state.getValue(PURITY)).average().orElseGet(() -> 0));
            int size = linked.size() + world.getRandom().nextInt((int) purity + 1);
            for (BlockPos blockPos : linked) {
                world.destroyBlock(blockPos, true);
            }
            int v = Math.round(size / (float) purity);


            ItemStack itemStack = new ItemStack(Items.MAGIC_CRYSTAL.get(), 1);
            CompoundNBT magic_circle = itemStack.getOrCreateTagElement("magic_circle");
            magic_circle.putInt("size", size);
            magic_circle.putDouble("purity", purity);
            magic_circle.put("bagua_prefer", new BaguaPrefer(RANDOM.nextInt(v), RANDOM.nextInt(v), RANDOM.nextInt(v), RANDOM.nextInt(v), RANDOM.nextInt(v), RANDOM.nextInt(v), RANDOM.nextInt(v), RANDOM.nextInt(v)).serializeNBT());

            itemStack.getCapability(Capabilities.MAGICAL_ITEM).ifPresent(
                    iMagicalItem -> {
                        iMagicalItem.setManaCapacity(size * Config.SIZE_MANA_CAPACITY_SCALE.get());
                        iMagicalItem.setMana(RANDOM.nextInt((int) iMagicalItem.getManaCapacity()));
                        iMagicalItem.setManaRecovery((float) (purity * Config.PURITY_MANA_RECOVERY_SCALE.get()));
                        iMagicalItem.setScaleCapacityIfPrimed(Config.SIZE_MANA_CAPACITY_PRIMED_SCALE.get());
                        iMagicalItem.setScaleRecoverIfPrimed(Config.PURITY_MANA_RECOVERY_PRIMED_SCALE.get());
                        iMagicalItem.setMaxMagicCapacity(Math.round((float) purity));

                        int slotCount = RANDOM.nextInt(Math.round((float) purity));
                        ArrayList<MagicalItemContainer.Slot> slots = new ArrayList<>();
                        for (int i = 0; i < slotCount; i++) {
                            slots.add(new MagicalItemContainer.Slot(RANDOM.nextInt(Math.round((float) purity)), ItemStack.EMPTY));
                        }

                        iMagicalItem.setEffectContainer(new MagicalItemContainer(slots.toArray(new MagicalItemContainer.Slot[]{})).serializeNBT());
                    }
            );

            ItemEntity itemEntity = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), itemStack);
            itemEntity.blocksBuilding = true;
            itemEntity.fireImmune();
            itemEntity.hasImpulse = false;
            world.addFreshEntity(itemEntity);

            itemEntity.setOwner(playerEntity.getUUID());
        }
    }

    public void find(BlockPos pos, World world, HashSet<BlockPos> set) {
        if (!set.contains(pos)) {
            set.add(pos);
            for (Direction value : Direction.values()) {
                BlockPos offset = pos.offset(value.getNormal());
                if (world.getBlockState(offset).getBlock() instanceof MagicCrystalOre) {
                    find(offset, world, set);
                }
            }
        }
    }

    @Override
    public float getShadeBrightness(BlockState p_220080_1_, IBlockReader p_220080_2_, BlockPos p_220080_3_) {
        return 1;
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> p_206840_1_) {
        super.createBlockStateDefinition(p_206840_1_);
        p_206840_1_.add(PURITY);
    }
}
