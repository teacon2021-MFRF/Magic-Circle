package mfrf.magic_circle.magicutil.nodes.effectnode;

import mfrf.magic_circle.magicutil.MagicNodeBase;
import mfrf.magic_circle.magicutil.datastructure.MagicStreamMatrixNByN;
import net.minecraft.nbt.CompoundNBT;

public abstract class EffectNodeBase extends MagicNodeBase {

    public final EffectType effectType;

    public EffectNodeBase(EffectType effectType) {
        super(NodeType.EFFECT);
        this.effectType = effectType;
    }

    public static MagicNodeBase deserializeNBT(CompoundNBT nbt, int layer, MagicStreamMatrixNByN matrix) {
        return null;
    }

    public enum EffectType {
        REDSTONE, LIHUO, KANSHUI, KUNDI, GENSHAN, THUNDER, DUIZE, DRYSKY, SUNDAE, TAICHI;
    }
}
