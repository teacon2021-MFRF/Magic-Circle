package mfrf.magic_circle;

import mfrf.magic_circle.interfaces.IMagicalItem;
import mfrf.magic_circle.registry_lists.Blocks;
import mfrf.magic_circle.registry_lists.Items;
import mfrf.magic_circle.util.MagicalItemSimpleImplement;
import net.minecraft.item.ItemGroup;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import javax.annotation.Nullable;

import static mfrf.magic_circle.MagicCircle.MOD_ID;

@Mod(MOD_ID)
public class MagicCircle {
    public static final String MOD_ID = "magic_circle";
    public static final ItemGroup MAGIC_CIRCLE_RESOURCES = new CreativeTab();

    public MagicCircle() {
        MinecraftForge.EVENT_BUS.register(this);
        Items.ITEM_DEFERRED_REGISTER.register(FMLJavaModLoadingContext.get().getModEventBus());
        Blocks.BLOCK_DEFERRED_REGISTER.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static void onSetUpEvent(FMLCommonSetupEvent event) {
        //----------------------------------------------------------------------------------------------------------------
        event.enqueueWork(() -> {
            CapabilityManager.INSTANCE.register(IMagicalItem.class, new Capability.IStorage<IMagicalItem>() {
                @Nullable
                @Override
                public INBT writeNBT(Capability<IMagicalItem> capability, IMagicalItem instance, Direction side) {
                    if (instance instanceof MagicalItemSimpleImplement) {
                        MagicalItemSimpleImplement implement = (MagicalItemSimpleImplement) instance;
                        CompoundNBT compoundNBT = new CompoundNBT();
                        compoundNBT.put("effect_container", implement.getEffectContainer().serializeNBT());
                        compoundNBT.putInt("current_capacity", instance.getManaCapacity());
                        compoundNBT.putInt("current_recover", instance.getManaRecover());
                        compoundNBT.putBoolean("primed", instance.hasPrimed());
                        compoundNBT.putDouble("scale_capacity_if_primed", instance.getScaleCapacityIfPrimed());
                        compoundNBT.putDouble("scale_recover_if_primed", implement.getScaleRecoverIfPrimed());
                        compoundNBT.putInt("mana_current", implement.getMana());
                    }
                    return null;
                }

                @Override
                public void readNBT(Capability<IMagicalItem> capability, IMagicalItem instance, Direction side, INBT nbt) {
                    if (instance instanceof MagicalItemSimpleImplement && nbt instanceof CompoundNBT) {
                        MagicalItemSimpleImplement implement = (MagicalItemSimpleImplement) instance;
                        CompoundNBT compoundNBT = (CompoundNBT) nbt;

                        implement.getEffectContainer().deserializeNBT(compoundNBT.getCompound("effect_container"));
                        implement.setManaCapacity(compoundNBT.getInt("current_capacity"));
                        implement.setManaRecover(compoundNBT.getInt("current_recover"));
                        implement.setHasPrimed(compoundNBT.getBoolean("primed"));
                        implement.setScaleCapacityIfPrimed(compoundNBT.getDouble("scale_capacity_if_primed"));
                        implement.setScaleRecoverIfPrimed(compoundNBT.getDouble("scale_recover_if_primed"));
                        implement.setMana(compoundNBT.getInt("mana_current"));
                    }
                }
            }, MagicalItemSimpleImplement::new);
        });
        //----------------------------------------------------------------------------------------------------------------
    }

}
