package mfrf.magic_circle.magicutil.nodes.behaviornode;

import mfrf.magic_circle.gui.widgets.Argument;
import mfrf.magic_circle.magicutil.MagicStream;
import mfrf.magic_circle.rendering.MagicCircleComponentBase;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.function.Predicate;

public class TriggerNode extends BehaviorNodeBase {

    private Predicate<Entity> entityPredicate;

    public TriggerNode() {
        super(BehaviorType.BEAM);
    }

    public void setEntityPredicate(Predicate<Entity> entityPredicate) {
        this.entityPredicate = entityPredicate;
    }

    @Override
    public DataContainer apply(MagicStream magic) {
        return null;
    }

    @Override
    public MagicCircleComponentBase<?> getRender() {
        return null;
    }

    @Override
    public ArrayList<Argument<?>> getArguments() {
        return null;
    }

}
