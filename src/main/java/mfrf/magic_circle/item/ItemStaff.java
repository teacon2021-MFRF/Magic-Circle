package mfrf.magic_circle.item;

public abstract class ItemStaff extends ItemBase{

    public ItemStaff(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }

    public abstract int getBasicManaCapacity();
    public abstract int getBasicManaRecover();

}
