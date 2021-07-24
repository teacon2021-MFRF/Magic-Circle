package mfrf.magic_circle.magicutil.nodes.behaviornode;

import mfrf.magic_circle.gui.widgets.Argument;
import mfrf.magic_circle.magicutil.MagicStream;
import mfrf.magic_circle.rendering.MagicCircleComponentBase;

import java.util.ArrayList;

public class CircleNode extends BehaviorNodeBase {
    public CircleNode() {
        super(BehaviorType.BEAM);
    }

    @Override
    public DataContainer apply(MagicStream magic) {
        return null;
    }

    @Override
    public ArrayList<Argument<?>> getArguments() {
        return null;
    }

}
