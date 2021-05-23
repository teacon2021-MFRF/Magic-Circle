package mfrf.magic_circle.magicutil.nodes.behaviornode;

import mfrf.magic_circle.entity.barrage.DanmakuEntity;
import mfrf.magic_circle.magicutil.Invoker;
import mfrf.magic_circle.magicutil.MagicNodeBase;
import mfrf.magic_circle.magicutil.MagicStream;
import mfrf.magic_circle.magicutil.Receiver;
import mfrf.magic_circle.magicutil.datastructure.MagicNodePropertyMatrix8By8;
import mfrf.magic_circle.magicutil.nodes.decoratenode.DecorateNodeBase;
import mfrf.magic_circle.registry_lists.Entities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import org.apache.commons.lang3.RandomUtils;

import java.util.function.Predicate;

public class ThrowBehaviorNode extends BehaviorNodeBase {
    protected boolean needExpression = false;

    public ThrowBehaviorNode(MagicNodeBase leftNode, MagicNodeBase rightNode, Predicate<MagicStream> condition) {
        super(leftNode, rightNode, condition, BehaviorType.THROW);
    }


    @Override
    public MagicStream apply(MagicStream magic) {
        MagicNodeBase lastNode = magic.info.lastNode;

        switch (lastNode.getNodeType()) {
            case BEGIN -> {

                break;
            }
            case FINAL -> {

                break;
            }
            case MODEL -> {

                break;
            }
            case EFFECT -> {

                break;
            }
            case ADDITION -> {

                break;
            }
            case BEHAVIOR -> {

                break;
            }
            case DECORATE -> {
                DecorateNodeBase decorateNode = (DecorateNodeBase) lastNode;
                switch (decorateNode.decorateType) {
                    case POWER -> {
                        needExpression = true;

                        magic.functions.add((magicStream, magicStreamInfo) -> {
                            Invoker invoker = magicStreamInfo.invoker;
                            Receiver receiver = magicStreamInfo.receiver;
                            Vector3f vector3f = receiver.vector3f;
                            World world = magicStreamInfo.receiver.world;
                            MagicNodePropertyMatrix8By8 streamEigenMatrix = magicStream.eigenMatrix;
                            double strength = streamEigenMatrix.getStrength();
                            double range = streamEigenMatrix.getRange();
                            double duration = streamEigenMatrix.getDuration();
                            double execute_speed = streamEigenMatrix.getExecuteSpeed();
                            double cooldown = streamEigenMatrix.getCoolDown();
                            double efficient = streamEigenMatrix.getEfficient();
                            int count = (int) Math.min((execute_speed - cooldown) * strength / 20, 50);
                            double splitArgument = cooldown / execute_speed;
                            long countPerShoot = Math.round(count / (splitArgument < 1 ? 1 : splitArgument));

                            if (world instanceof ServerWorld) {
                                ServerWorld serverWorld = (ServerWorld) world;
                                for (int i = 0; i <= duration; i += cooldown) {
                                    for (long l = 0; l < countPerShoot; l++) {
                                        DanmakuEntity spawn = Entities.DANMAKU_ENTITY.get().spawn(serverWorld, null, null, world.getPlayerByUUID(invoker.player), invoker.beginPos, SpawnReason.TRIGGERED, true, true);
                                        //todo initialize arguments
                                    }
                                }

                            }

                            return magicStream;
                        });
                    }
                    case INVERT -> {

                    }
                    case STRENGTH -> {

                    }
                    case EXPANSION -> {

                    }
                    case CONTINUOUS -> {

                    }
                    case DIAGONALIZE -> {

                    }

                }

                break;
            }
            case RESONANCE -> {

                break;
            }
        }

        return magic;
    }

    public class PositionExpression {
        public String x;
        public String y;
        public String z;
    }

}
