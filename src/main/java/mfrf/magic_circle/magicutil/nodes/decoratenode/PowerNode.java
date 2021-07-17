package mfrf.magic_circle.magicutil.nodes.decoratenode;

import mfrf.magic_circle.magicutil.MagicStream;
import mfrf.magic_circle.rendering.MagicCircleComponentBase;

public class PowerNode extends DecorateNodeBase{

    public PowerNode() {
        super(DecorateType.INVERT);
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
