package mfrf.magic_circle.magicutil.nodes.behaviornode;

import mfrf.magic_circle.magicutil.Invoker;
import mfrf.magic_circle.magicutil.MagicNodeBase;
import mfrf.magic_circle.magicutil.MagicStream;
import mfrf.magic_circle.magicutil.Receiver;
import mfrf.magic_circle.magicutil.datastructure.MagicNodePropertyMatrix8By8;
import mfrf.magic_circle.magicutil.nodes.decoratenode.DecorateNodeBase;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;

import java.util.function.Predicate;

public class BeamNode extends BehaviorNodeBase {
    public BeamNode(MagicNodeBase leftNode, MagicNodeBase rightNode, Predicate<MagicStream> condition) {
        super(leftNode, rightNode, condition, BehaviorType.BEAM);
    }

    @Override
    public returnDataContainer apply(MagicStream magic) {

        boolean flag = true;
        MagicNodeBase lastNode = magic.info.lastNode;

        magic.functions.add((magicStream, magicStreamInfo) -> {

            Invoker invoker = magicStreamInfo.invoker;
            Receiver receiver = magicStreamInfo.receiver;
            Vector3f targetVec = receiver.vector3;
            World world = magicStreamInfo.receiver.world;
            MagicNodePropertyMatrix8By8 streamEigenMatrix = magicStream.eigenMatrix;
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

            return magic;
        });
        return new returnDataContainer(magic, flag);
    }

}
