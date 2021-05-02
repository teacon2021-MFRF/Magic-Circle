package mfrf.magic_circle.item;


import mfrf.magic_circle.MagicCircle;
import net.minecraft.item.Item;

public class ItemBase extends Item {
    public ItemBase(Properties p_i48487_1_, String name) {
        super(p_i48487_1_);
        setRegistryName(MagicCircle.MOD_ID, name);
    }
}
