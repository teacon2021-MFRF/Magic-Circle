package mfrf.magic_circle.util;

import mfrf.magic_circle.interfaces.IMagicalItem;
import mfrf.magic_circle.magicutil.MagicModelBase;
import mfrf.magic_circle.magicutil.MagicStream;
import mfrf.magic_circle.registry_lists.Capabilities;
import mfrf.magic_circle.world_saved_data.StoredMagicModels;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class MagicalItemSimpleImplement implements IMagicalItem, ICapabilityProvider {

    private MagicalItemContainer magicalItemContainer = new MagicalItemContainer();
    private float manaCapacity;
    private double scaleCapacityIFPrimed;
    private int manaCurrent;
    private float manaRecover;
    private double scaleRecoverIfPrimed;
    private boolean hasPrimed = false;
    private ItemStack stack = ItemStack.EMPTY;
    private ArrayList<String> magics = new ArrayList<>();
    private int maxMagicModelCapacity;

    public MagicalItemSimpleImplement(MagicalItemContainer container, float manaCapacity, double scaleCapacityIFPrimed, int manaCurrent, float manaRecover, double scaleRecoverIfPrimed, int maxMagicModelCapacity, ItemStack stack) {
        this.magicalItemContainer = container;
        this.manaCapacity = manaCapacity;
        this.scaleCapacityIFPrimed = scaleCapacityIFPrimed;
        this.manaCurrent = manaCurrent;
        this.manaRecover = manaRecover;
        this.scaleRecoverIfPrimed = scaleRecoverIfPrimed;
        this.maxMagicModelCapacity = maxMagicModelCapacity;
        if (stack != null) {
            this.stack = stack;
        }
    }

    public MagicalItemSimpleImplement() {
    }


    public CompoundNBT getTag() {
        CompoundNBT magic_circle;
        if (!stack.isEmpty()) {
            magic_circle = stack.getOrCreateTagElement("magic_circle");
        } else {
            magic_circle = new CompoundNBT();
        }
        if (!magic_circle.contains("implement")) {
            magic_circle.put("implement", defaultNBT());
        }
        return magic_circle.getCompound("implement");
    }

    @Override
    public float getManaCapacity() {
        AtomicReference<Float> capacity = new AtomicReference<>(getTag().getFloat("current_capacity"));
        for (MagicalItemContainer.Slot slot : getEffectContainer().slots) {
            slot.itemStack.getCapability(Capabilities.MAGICAL_ITEM).ifPresent(iMagicalItem -> {
                capacity.updateAndGet(v -> (float) (v + iMagicalItem.getManaCapacity()));
            });
        }
        return capacity.get();
    }

    @Override
    public void setManaCapacity(float value) {
        getTag().putFloat("current_capacity", value);
    }

    @Override
    public int getMana() {
        return getTag().getInt("mana_current");
    }

    @Override
    public void setMana(int value) {
        getTag().putInt("mana_current", value);
    }

    @Override
    public float getManaRecovery() {
        AtomicReference<Float> recovery = new AtomicReference<>(getTag().getFloat("mana_recovery"));
        for (MagicalItemContainer.Slot slot : getEffectContainer().slots) {
            slot.itemStack.getCapability(Capabilities.MAGICAL_ITEM).ifPresent(iMagicalItem -> {
                recovery.updateAndGet(v -> (float) (v + iMagicalItem.getManaRecovery()));
            });
        }
        return recovery.get();
    }

    @Override
    public void setManaRecovery(float value) {
        getTag().putFloat("mana_recovery", value);
    }

    @Override
    public boolean hasPrimed() {
        return getTag().getBoolean("primed");
    }

    @Override
    public void setHasPrimed(boolean value) {
        getTag().putBoolean("primed", value);
    }

    @Override
    public MagicalItemContainer getEffectContainer() {
        MagicalItemContainer magicalItemContainer = new MagicalItemContainer();
        magicalItemContainer.deserializeNBT(getTag().getCompound("effect_container"));
        return magicalItemContainer;
    }

    @Override
    public void setScaleCapacityIfPrimed(double value) {
        getTag().putDouble("scale_capacity_if_primed", value);
    }

    @Override
    public double getScaleCapacityIfPrimed() {
        return getTag().getDouble("scale_capacity_if_primed");
    }

    @Override
    public void setScaleRecoverIfPrimed(double value) {
        getTag().putDouble("scale_recovery_if_primed", value);
    }

    @Override
    public double getScaleRecoverIfPrimed() {
        return getTag().getDouble("scale_recovery_if_primed");
    }

    @Override
    public void onPriming(World world, BlockPos pos) {
        setHasPrimed(true);
        setManaCapacity((float) (getTag().getFloat("current_capacity") * getScaleCapacityIfPrimed()));
        setManaRecovery((float) (getTag().getFloat("current_recovery") * getScaleRecoverIfPrimed()));
    }

    @Override
    public ArrayList<String> magics() {
        ArrayList<String> strings = new ArrayList<>();
        for (INBT inbt : getTag().getList("magics", Constants.NBT.TAG_STRING)) {
            StringNBT stringNBT = (StringNBT) inbt;
            strings.add(stringNBT.getAsString());
        }

        MagicalItemContainer effectContainer = getEffectContainer();
        for (int i = 0; i < effectContainer.slots.size(); i++) {
            ItemStack itemStack = effectContainer.slots.get(i).itemStack;
            LazyOptional<IMagicalItem> capability = itemStack.getCapability(Capabilities.MAGICAL_ITEM);
            if (!itemStack.isEmpty() && capability.isPresent()) {
                AtomicBoolean flag = new AtomicBoolean(false);
                capability.ifPresent(iMagicalItem -> flag.set(iMagicalItem.magics().size() > 0));
                if (flag.get())
                    strings.add("inslot_" + i);
            }
        }
        return strings;
    }

    @Override
    public boolean removeMagic(String name) {
        return getTag().getList("magics", Constants.NBT.TAG_STRING).removeIf(inbt -> {
            StringNBT stringNBT = (StringNBT) inbt;
            return stringNBT.getAsString().equals(name);
        });
    }

    @Override
    public int getMaxMagicCapacity() {
        return getTag().getInt("max_magic_model_capacity");
    }

    @Override
    public void setMaxMagicCapacity(int value) {
        getTag().putInt("max_magic_model_capacity", value);
    }


    @Override
    public boolean addMagic(String magicModelName) {
        CompoundNBT tag = getTag();

        ListNBT magics = tag.getList("magics", Constants.NBT.TAG_STRING);
        if (magics.size() < getMaxMagicCapacity()) {
            StringNBT stringNBT = StringNBT.valueOf("normal_" + magicModelName);
            magics.add(stringNBT);
            return true;
        }
        return false;
    }

    @Override
    public void onTick() {
        int mana = getMana();
        float manaCapacity = getManaCapacity();
        if (mana < manaCapacity) {
            float manaRecovery = getManaRecovery();
            float v = manaCapacity - mana;
            if (v >= manaRecovery) {
                setMana(Math.round(v + manaRecovery));
            } else {
                setMana(Math.round(manaCapacity));
            }
        }
    }

    @Override
    public String getSelectedMagic() {
        CompoundNBT tag = getTag();
        if (tag.contains("selected_magic")) {
            String selected_magic = tag.getString("selected_magic");
            if (magics().contains(selected_magic)) {
                return selected_magic;
            } else {
                ArrayList<String> magics = magics();
                if (!magics.isEmpty()) {
                    setSelectedMagic(magics.get(0));
                    return getSelectedMagic();
                }
            }
        } else {
            ArrayList<String> magics = magics();
            if (!magics.isEmpty()) {
                setSelectedMagic(magics.get(0));
                return getSelectedMagic();
            }
        }
        return null;
    }

    @Override
    public void setSelectedMagic(String selectedMagic) {
        ArrayList<String> magics = magics();
        if (!magics.isEmpty() && magics.contains(selectedMagic)) {
            getTag().putString("selected_magic", selectedMagic);
        }
    }

    @Override
    public void executeMagic(boolean executeAll, World world, UUID uuid, MagicStream.MagicStreamInfo stream) {
        if (!executeAll) {
            String selectedMagic = getSelectedMagic();
            String prefix = selectedMagic.substring(0, 7);
            String name = selectedMagic.substring(7);
            if (prefix.matches("inslot_")) {
                int i = Integer.parseInt(name);
                getEffectContainer().slots.get(i).itemStack.getCapability(Capabilities.MAGICAL_ITEM).ifPresent(iMagicalItem -> {
                    iMagicalItem.executeMagic(true, world, uuid, stream);
                });
            } else if (prefix.matches("normal_")) {
                CachedEveryThingForClient.executeMap.get(uuid).put(name, 0);
                MagicModelBase magicModelBase = StoredMagicModels.getOrCreate(world).request(uuid, name);
                if (magicModelBase != null) {
                    magicModelBase.invoke(new MagicStream(stream));
                }
            }
        } else {
            for (String magic : magics) {
                setSelectedMagic(magic);
                executeMagic(false, world, uuid, stream);
            }
        }
    }

    @Override
    public void setEffectContainer(CompoundNBT nbt) {
        getTag().put("effect_container", nbt);
    }

    public MagicalItemSimpleImplement copy(ItemStack stack, CompoundNBT nbt) {
        MagicalItemSimpleImplement implement = new MagicalItemSimpleImplement(
                getEffectContainer().clone(),
                getManaCapacity(),
                getScaleCapacityIfPrimed(),
                getMana(),
                getManaRecovery(),
                getScaleRecoverIfPrimed(),
                getMaxMagicCapacity(),
                stack);
        return implement;
    }

    public CompoundNBT defaultNBT() {
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.put("effect_container", magicalItemContainer.serializeNBT());
        compoundNBT.putFloat("current_capacity", manaCapacity);
        compoundNBT.putFloat("current_recovery", manaRecover);
        compoundNBT.putBoolean("primed", hasPrimed);
        compoundNBT.putDouble("scale_capacity_if_primed", scaleCapacityIFPrimed);
        compoundNBT.putDouble("scale_recover_if_primed", scaleRecoverIfPrimed);
        compoundNBT.putInt("mana_current", manaCurrent);
        compoundNBT.putInt("max_magic_model_capacity", maxMagicModelCapacity);

        ListNBT magics = new ListNBT();
        for (String magic : this.magics) {
            CompoundNBT compoundNBT1 = new CompoundNBT();
            compoundNBT1.putString("name", magic);
            magics.add(compoundNBT1);
        }
        compoundNBT.put("magics", magics);

        return compoundNBT;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == Capabilities.MAGICAL_ITEM) {
            return LazyOptional.of(() -> this).cast();
        }
        return LazyOptional.empty();
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap) {
        return ICapabilityProvider.super.getCapability(cap);
    }
}
