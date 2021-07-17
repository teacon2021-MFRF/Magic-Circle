package mfrf.magic_circle.magicutil.nodes.behaviornode;

import mfrf.magic_circle.magicutil.Invoker;
import mfrf.magic_circle.magicutil.MagicNodeBase;
import mfrf.magic_circle.magicutil.MagicStream;
import mfrf.magic_circle.magicutil.Receiver;
import mfrf.magic_circle.magicutil.datastructure.MagicNodePropertyMatrix8By8;
import mfrf.magic_circle.magicutil.nodes.decoratenode.DecorateNodeBase;
import mfrf.magic_circle.rendering.MagicCircleComponentBase;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;

public class DomainNode extends BehaviorNodeBase {
    public DomainNode() {
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
