package mfrf.magic_circle.magicutil.nodes.behaviornode;

import java.util.function.Predicate;

import mfrf.magic_circle.entity.barrage.DanmakuEntity;
import mfrf.magic_circle.magicutil.Invoker;
import mfrf.magic_circle.magicutil.MagicNodeBase;
import mfrf.magic_circle.magicutil.MagicStream;
import mfrf.magic_circle.magicutil.Receiver;
import mfrf.magic_circle.magicutil.datastructure.MagicNodePropertyMatrix8By8;
import mfrf.magic_circle.magicutil.nodes.decoratenode.DecorateNodeBase;
import mfrf.magic_circle.registry_lists.Entities;
import mfrf.magic_circle.util.MathUtil;
import net.minecraft.entity.SpawnReason;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class ThrowBehaviorNode extends BehaviorNodeBase {
    protected boolean expressionModified = false;
    protected PositionExpression positionExpression = new PositionExpression();

    public ThrowBehaviorNode(MagicNodeBase leftNode, MagicNodeBase rightNode, Predicate<MagicStream> condition) {
        super(leftNode, rightNode, condition, BehaviorType.THROW);
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
                            int count = (int) Math.min((execute_speed - cooldown) * strength / 20, 50);
                            double splitArgument = cooldown / execute_speed;
                            long countPerShoot = Math.round(count / (splitArgument < 1 ? 1 : splitArgument));

                            if (world instanceof ServerWorld) {
                                ServerWorld serverWorld = (ServerWorld) world;
                                for (int i = 0; i <= duration; i += cooldown) {
                                    for (long l = 0; l < countPerShoot; l++) {
                                        DanmakuEntity spawn = Entities.DANMAKU_ENTITY.get().spawn(serverWorld, null, null, world.getPlayerByUUID(invoker.player), invoker.beginPos, SpawnReason.TRIGGERED, true, true);
                                        if (spawn != null) {
                                            double v = (range / strength) * 1.2;
                                            spawn.setSpeedScale(v >= 20 ? 20 : (float) v);
                                            spawn.setRGBA(streamEigenMatrix.getRGBA());
                                            spawn.setTargetVec(targetVec);
                                            spawn.setRequiredVariables((float) (strength * 1.2f), ((float) duration));
                                            spawn.setPositionExpression(expressionModified ? positionExpression : new PositionExpression(targetVec));
                                        }
                                    }
                                }

                            }

                            return magicStream;
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
        });
        return magic;
    }

    public boolean setPositionExpression(PositionExpression expression) {
        this.positionExpression = expression;
        return (expressionModified = true);
    }

    public void resetPositionExpression() {
        this.positionExpression = new PositionExpression();
        expressionModified = false;
    }

    public class PositionExpression {
        public String x = "0";
        public String y = "0";
        public String z = "0";

        public PositionExpression(String x, String y, String z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public PositionExpression(Vector3f vector3f) {
            this.x = Float.toString(vector3f.x());
            this.y = Float.toString(vector3f.y());
            this.z = Float.toString(vector3f.z());
        }

        public PositionExpression() {
        }
    }

}
