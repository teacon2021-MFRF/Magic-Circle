package mfrf.magic_circle.magicutil.nodes.decoratenode;

import mfrf.magic_circle.magicutil.BGMPreferences;
import mfrf.magic_circle.magicutil.BaguaPrefer;
import mfrf.magic_circle.magicutil.MagicNodeBase;
import mfrf.magic_circle.magicutil.MagicStream;
import mfrf.magic_circle.magicutil.datastructure.MagicStreamMatrixNByN;
import net.minecraft.nbt.CompoundNBT;

import java.util.function.Predicate;

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
