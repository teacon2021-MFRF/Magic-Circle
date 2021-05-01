package mfrf.magic_circle.magicutil;

import net.minecraft.world.World;

public abstract class MagicNodeBase {
    protected int manaReviseAdd;
    protected double manaReviseMultiply;
    protected int complexityAdd;
    protected double complexityMultiply;
    protected int nestedLayer;
    protected MagicMatrix6By6 eigenMatrix;

    /**
     * @param manaReviseAdd      Correct of mana use, would add to base value after manaReviseMultiply calculate complete.
     * @param manaReviseMultiply Correct of mana use, would multiply to base value.
     * @param complexityAdd      Same as manaReviseAdd
     * @param complexityMultiply Same as manaReviseMultiply
     * @param nestedLayer        NestedLayer of magicModel
     * @param eigenMatrix        EigenMatrix that this node abstracted.
     */
    public MagicNodeBase(int manaReviseAdd, double manaReviseMultiply, int complexityAdd, double complexityMultiply, int nestedLayer, MagicMatrix6By6 eigenMatrix) {
        this.manaReviseAdd = manaReviseAdd;
        this.manaReviseMultiply = manaReviseMultiply;
        this.complexityAdd = complexityAdd;
        this.complexityMultiply = complexityMultiply;
        this.nestedLayer = nestedLayer;
        this.eigenMatrix = eigenMatrix;
    }

    public abstract MagicModelBase apply(MagicModelBase magic, World world, Invoker invoker, Receiver receiver);

    public int getManaReviseAdd() {
        return manaReviseAdd;
    }

    public double getManaReviseMultiply() {
        return manaReviseMultiply;
    }

    public int getComplexityAdd() {
        return complexityAdd;
    }

    public double getComplexityMultiply() {
        return complexityMultiply;
    }

    public int getNestedLayer() {
        return nestedLayer;
    }

    public MagicMatrix6By6 getEigenMatrix() {
        return eigenMatrix;
    }
}
