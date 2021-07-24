package mfrf.magic_circle.magicutil.nodes.effectnode;

import mfrf.magic_circle.gui.widgets.Argument;
import mfrf.magic_circle.magicutil.MagicStream;
import mfrf.magic_circle.rendering.MagicCircleComponentBase;

import java.util.ArrayList;

public class ThunderNode extends EffectNodeBase{
    public ThunderNode() {
        super(EffectType.REDSTONE);
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
