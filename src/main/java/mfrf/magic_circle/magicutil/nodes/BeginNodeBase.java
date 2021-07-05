package mfrf.magic_circle.magicutil.nodes;

import mfrf.magic_circle.magicutil.MagicModelBase;
import mfrf.magic_circle.magicutil.MagicNodeBase;
import mfrf.magic_circle.magicutil.MagicStream;
import mfrf.magic_circle.magicutil.datastructure.MagicStreamMatrixNByN;
import net.minecraft.nbt.CompoundNBT;

public class BeginNodeBase extends MagicNodeBase {
    public type invokerType;
    public type receiverType;

    public BeginNodeBase(type invokerType, type reciverType) {
        super(NodeType.BEGIN);
        this.invokerType = invokerType;
        this.receiverType = reciverType;
    }

    public static MagicNodeBase deserializeNBT(CompoundNBT nbt, int layer, MagicStreamMatrixNByN matrix) {
        return null;
    }

    @Override
    public returnDataContainer apply(MagicStream magic) {
        return new returnDataContainer(magic, true);
    }

    public enum type {
        PLAYER, BLOCK, EQUIPMENT, CHAIN, DIMENSION, ENTITY, DROP_ENTITY, WEATHER, TIME, AUTO;
    }
}
