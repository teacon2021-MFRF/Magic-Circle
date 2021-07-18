package mfrf.magic_circle.magicutil.nodes.decoratenode;

import mfrf.magic_circle.magicutil.MagicNodeBase;
import mfrf.magic_circle.magicutil.datastructure.MagicStreamMatrixNByN;
import net.minecraft.nbt.CompoundNBT;

public abstract class DecorateNodeBase extends MagicNodeBase {
    public final DecorateType decorateType;

    public DecorateNodeBase(DecorateType decorateType) {
        super(NodeType.DECORATE);
        this.decorateType = decorateType;
    }

    public static MagicNodeBase deserializeNBT(CompoundNBT nbt, int layer, MagicStreamMatrixNByN matrix) {
        return null;
    }

    public enum DecorateType {
        STRENGTH, CONTINUOUS, EXPANSION, INVERT, POWER, DIAGONALIZE;
    }
}
