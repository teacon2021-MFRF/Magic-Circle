package mfrf.magic_circle.magicutil.nodes.behaviornode;

import mfrf.magic_circle.magicutil.MagicStream;
import mfrf.magic_circle.rendering.MagicCircleComponentBase;

public class RestrictNode extends BehaviorNodeBase {
    public RestrictNode() {
        super(BehaviorType.BEAM);
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
