package mfrf.magic_circle.magicutil.nodes.effectnode;

import mfrf.magic_circle.magicutil.MagicStream;
import mfrf.magic_circle.rendering.MagicCircleComponentBase;

public class TaichiNode extends EffectNodeBase{
    public TaichiNode() {
        super(EffectType.REDSTONE);
    }

    @Override
    public DataContainer apply(MagicStream magic) {
        return null;
    }

    @Override
    public MagicCircleComponentBase<?> getRender() {
        return null;
    }
}
