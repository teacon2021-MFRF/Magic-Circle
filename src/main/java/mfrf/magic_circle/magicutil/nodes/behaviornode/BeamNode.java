package mfrf.magic_circle.magicutil.nodes.behaviornode;

import mfrf.magic_circle.gui.widgets.Argument;
import mfrf.magic_circle.magicutil.MagicNodeBase;
import mfrf.magic_circle.magicutil.MagicStream;
import mfrf.magic_circle.magicutil.datastructure.MagicNodePropertyMatrix8By8;
import mfrf.magic_circle.magicutil.nodes.decoratenode.DecorateNodeBase;
import mfrf.magic_circle.rendering.MagicCircleComponentBase;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;

import java.util.ArrayList;

public class BeamNode extends BehaviorNodeBase {
    public BeamNode() {
        super(BehaviorType.BEAM);
    }

    @Override
    public DataContainer apply(MagicStream magic) {

        boolean flag = true;
        MagicNodeBase lastNode = magic.info.lastNode;

        magic.functions.add((magicStream, magicStreamInfo) -> {

            MagicStream.DataContain data = magicStreamInfo.data;
            Vector3f targetVec = data.targetVec;
            World world = data.world;
            MagicNodePropertyMatrix8By8 streamEigenMatrix = data.eigenMatrix;
            double strength = streamEigenMatrix.getStrength();
            double range = streamEigenMatrix.getRange();
            double duration = streamEigenMatrix.getDuration();
            double execute_speed = streamEigenMatrix.getExecuteSpeed();
            double cooldown = streamEigenMatrix.getCoolDown();
            double efficient = streamEigenMatrix.getEfficient();

            switch (lastNode.getNodeType()) {
                case DECORATE: {
                    DecorateNodeBase lastDecorateNode = (DecorateNodeBase) lastNode;

                    switch (lastDecorateNode.decorateType) {

                        case STRENGTH: {

                            break;
                        }

                    }
                    break;
                }
                case BEHAVIOR: {

                    break;
                }
                case BEGIN: {

                    break;
                }
                case EFFECT: {

                    break;
                }
                default: {

                }
            }

            magic.Matrixtimes(this.eigenMatrix);
            return magic;
        });
        return new DataContainer(magic, flag);
    }


    @Override
    public ArrayList<Argument<?>> getArguments() {
        return null;
    }

}
