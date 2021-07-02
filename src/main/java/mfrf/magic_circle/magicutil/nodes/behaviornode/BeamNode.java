package mfrf.magic_circle.magicutil.nodes.behaviornode;

import mfrf.magic_circle.entity.barrage.DanmakuEntity;
import mfrf.magic_circle.magicutil.Invoker;
import mfrf.magic_circle.magicutil.MagicNodeBase;
import mfrf.magic_circle.magicutil.MagicStream;
import mfrf.magic_circle.magicutil.Receiver;
import mfrf.magic_circle.magicutil.datastructure.MagicNodePropertyMatrix8By8;
import mfrf.magic_circle.magicutil.nodes.decoratenode.DecorateNodeBase;
import mfrf.magic_circle.registry_lists.Entities;
import mfrf.magic_circle.util.PositionExpression;
import net.minecraft.entity.SpawnReason;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.function.Predicate;

public class BeamNode extends BehaviorNodeBase {
    public BeamNode(MagicNodeBase leftNode, MagicNodeBase rightNode, Predicate<MagicStream> condition) {
        super(leftNode, rightNode, condition, BehaviorType.BEAM);
    }

    @Override
    public MagicStream apply(MagicStream magic) {

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
                case ADDITION: {

                    break;
                }
                default: {

                }
            }

            return magic;
        });
        return magic;
    }

    @Override
    public MagicStream applyWithRender(MagicStream magicStream) {
        return null;
    }
}
