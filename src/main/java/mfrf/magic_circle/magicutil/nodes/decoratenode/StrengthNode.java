package mfrf.magic_circle.magicutil.nodes.decoratenode;

import mfrf.magic_circle.gui.widgets.Argument;
import mfrf.magic_circle.magicutil.MagicStream;
import mfrf.magic_circle.rendering.MagicCircleComponentBase;

import java.util.ArrayList;

public class StrengthNode extends DecorateNodeBase{

    public StrengthNode() {
        super(DecorateType.INVERT);
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
