package mfrf.magic_circle.gui.assembly_table;

import mfrf.magic_circle.block.magic_assemby_table.TileMagicModelAssemblyTable;
import mfrf.magic_circle.gui.ContainerBase;
import mfrf.magic_circle.interfaces.IMagicalItem;
import mfrf.magic_circle.registry_lists.Capabilities;
import mfrf.magic_circle.registry_lists.GuiContainers;
import mfrf.magic_circle.util.MagicalItemContainer;
import mfrf.magic_circle.util.MagicalItemSimpleImplement;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;

import java.util.ArrayList;

public class AssemblyTableContainer extends ContainerBase {
    private static Coordinates[] coordinatesMap = {
            new Coordinates(142, 25),
            new Coordinates(212, 58),
            new Coordinates(233, 117),
            new Coordinates(212, 174),
            new Coordinates(143, 207),
            new Coordinates(73, 176),
            new Coordinates(51, 117),
            new Coordinates(72, 58)
    };
    private final TileMagicModelAssemblyTable table;

    public AssemblyTableContainer(int id, PlayerInventory playerInventory, BlockPos pos, World world) {
        super(GuiContainers.ASSEMBLY_TABLE_CONTAINER.get(), id);
        this.table = ((TileMagicModelAssemblyTable) world.getBlockEntity(pos));

        putInventories(1, 1, 18, 18, 1, 4, 0, playerInventory);
        putInventories(1, 19, 18, 18, 3, 1, 4, playerInventory);
        putInventories(19, 19, 18, 18, 1, 1, 7, playerInventory);
        putInventories(37, 37, 18, 18, 1, 1, 8, playerInventory);

        putInventories(283, 1, -18, 18, 1, 4, 9, playerInventory);
        putInventories(283, 19, 18, 18, 3, 1, 13, playerInventory);
        putInventories(265, 19, 18, 18, 1, 1, 16, playerInventory);
        putInventories(247, 37, 18, 18, 1, 1, 17, playerInventory);

        putInventories(1, 233, 18, 18, 1, 4, 18, playerInventory);
        putInventories(1, 215, 18, -18, 3, 1, 22, playerInventory);
        putInventories(19, 215, 18, 18, 1, 1, 25, playerInventory);
        putInventories(37, 197, 18, 18, 1, 1, 26, playerInventory);

        putInventories(283, 233, -18, 18, 1, 4, 27, playerInventory);
        putInventories(283, 215, 18, -18, 3, 1, 31, playerInventory);
        putInventories(266, 215, 18, 18, 1, 1, 34, playerInventory);
        putInventories(247, 197, 16, 16, 1, 1, 35, playerInventory);
//        addSlot(new Slot(table.inventory,0,142,25));
//        addSlot(new Slot(table.inventory,1,212,58));
//        addSlot(new Slot(table.inventory,2,233,117));
//        addSlot(new Slot(table.inventory,3,212,174));
//        addSlot(new Slot(table.inventory,4,143,207));
//        addSlot(new Slot(table.inventory,5,73,168));
//        addSlot(new Slot(table.inventory,6,51,117));
//        addSlot(new Slot(table.inventory,7,72,58));

        addSlot(new Slot(table.inventory, 0, 143, 118) {
            private ArrayList<Slot> slots = null;

            @Override
            public boolean mayPlace(ItemStack p_75214_1_) {
                return false;
            }

            @Override
            public boolean mayPickup(PlayerEntity p_82869_1_) {
                return false;
            }

            @Override
            public void setChanged() {
                super.setChanged();
            }
        });

        LazyOptional<IMagicalItem> capability = table.inventory.getItem(0).getCapability(Capabilities.MAGICAL_ITEM);
        if (capability.isPresent()) {
            IMagicalItem iMagicalItem = capability.orElseGet(() -> new MagicalItemSimpleImplement());
            MagicalItemContainer items = iMagicalItem.getEffectContainer();

            for (int i = 0; i < items.slotCount; i++) {
                Coordinates coordinates = coordinatesMap[i];

                int finalI = i;
                addSlot(new Slot(items, finalI, coordinates.x, coordinates.y) {
                    @Override
                    public boolean mayPlace(ItemStack p_75214_1_) {
                        return p_75214_1_.getCapability(Capabilities.MAGICAL_ITEM).isPresent();
                    }

                    @Override
                    public void set(ItemStack p_75215_1_) {
                        super.set(p_75215_1_);
                        table.inventory.getItem(0).getCapability(Capabilities.MAGICAL_ITEM).ifPresent(iMagicalItem -> {
                            iMagicalItem.setEffectContainer(items.serializeNBT());
                        });
                        table.setChanged();
                    }

                    @Override
                    public ItemStack onTake(PlayerEntity p_190901_1_, ItemStack p_190901_2_) {
                        ItemStack stack = super.onTake(p_190901_1_, p_190901_2_);
                        table.inventory.getItem(0).getCapability(Capabilities.MAGICAL_ITEM).ifPresent(iMagicalItem -> {
                            iMagicalItem.setEffectContainer(items.serializeNBT());
                        });
                        table.setChanged();
                        return stack;
                    }
                });
            }
        }


    }

    @Override
    public boolean stillValid(PlayerEntity p_75145_1_) {
        return true;
    }

    private static class Coordinates {
        public int x;
        public int y;

        public Coordinates(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
