package mfrf.magic_circle.magicutil.nodes.effectnode;

import java.util.function.Predicate;

import mfrf.magic_circle.magicutil.BGMPreferences;
import mfrf.magic_circle.magicutil.BaguaPrefer;
import mfrf.magic_circle.magicutil.MagicNodeBase;
import mfrf.magic_circle.magicutil.MagicStream;
import mfrf.magic_circle.magicutil.datastructure.MagicStreamMatrixNByN;
import net.minecraft.nbt.CompoundNBT;

public abstract class EffectNodeBase extends MagicNodeBase {

    public final EffectType effectType;

    public EffectNodeBase(float strengthModify, float rangeModify, float durationModify, float executeSpeedModify, float coolDownModify, float efficientModify, float weaknessModify, float shrinkModify, float brevityModify, float relayModify, float heatupModify, float wasteModify, BaguaPrefer prefer, BGMPreferences bgmPreferences, EffectType effectType) {
        super(NodeType.EFFECT, strengthModify, rangeModify, durationModify, executeSpeedModify, coolDownModify, efficientModify, weaknessModify, shrinkModify, brevityModify, relayModify, heatupModify, wasteModify, prefer, bgmPreferences);
        this.effectType = effectType;
    }

    public static MagicNodeBase deserializeNBT(CompoundNBT nbt, int layer, MagicStreamMatrixNByN matrix) {
        return null;
    }

    public enum EffectType {
        REDSTONE, LIHUO, KANSHUI, KUNDI, GENSHAN, THUNDER, DUIZE, DRYSKY, SUNDAE, TAICHI;
    }
}
