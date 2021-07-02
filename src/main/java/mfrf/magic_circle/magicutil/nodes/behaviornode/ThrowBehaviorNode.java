package mfrf.magic_circle.magicutil.nodes.behaviornode;

import java.util.List;
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
import mfrf.magic_circle.util.PositionExpression;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
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
                case BEGIN: {

                    break;
                }
                case FINAL: {

                    break;
                }
                case MODEL: {

                    break;
                }
                case EFFECT: {

                    break;
                }
                case ADDITION: {

                    break;
                }
                case BEHAVIOR: {

                    break;
                }
                case DECORATE: {
                    DecorateNodeBase decorateNode = (DecorateNodeBase) lastNode;
                    double minCount = Math.min((execute_speed - cooldown) * strength / 20, 50);
                    double splitArgument = cooldown / execute_speed;

                    switch (decorateNode.decorateType) {

                        case POWER: {

                        }
                        case INVERT: {
                            float _range = (float) (7.0 + 0.25 * range);
                            float half = _range / 2;
                            BlockPos offset = invoker.beginPos;
                            List<DanmakuEntity> entities = world.getEntities(Entities.DANMAKU_ENTITY.get(), new AxisAlignedBB(offset.offset(-half, -half, -half), offset.offset(half, half, half)), danmakuEntity -> {
                                String s = danmakuEntity.getEntityData().get(DanmakuEntity.TYPE);
                                return s.equals(DanmakuEntity.DanmakuType.BULLET.name()) || s.equals(DanmakuEntity.DanmakuType.NULL.name());
                            });
                            entities.forEach(DanmakuEntity::remove);

                            return magicStream;
                        }
                        case STRENGTH: {
                            int count = (int) minCount;
                            long countPerShoot = Math.round(count / (splitArgument < 1 ? 1 : splitArgument));
                            if (world instanceof ServerWorld) {
                                ServerWorld serverWorld = (ServerWorld) world;
                                for (int i = 0; i <= duration; i += cooldown) {
                                    for (long l = 0; l < countPerShoot; l++) {
                                        DanmakuEntity spawn = Entities.DANMAKU_ENTITY.get().spawn(serverWorld, null, null, world.getPlayerByUUID(invoker.player), invoker.beginPos, SpawnReason.TRIGGERED, true, true);
                                        if (spawn != null) {
                                            double v = (range / strength) * 1.2;
                                            spawn.setSpeedScale((float) v);
                                            spawn.setRGBA(streamEigenMatrix.getRGBA());
                                            spawn.setTargetVec(targetVec);
                                            spawn.setRequiredVariables((float) (strength * 1.2f), ((float) duration), DanmakuEntity.DanmakuType.BULLET);
                                            spawn.setPositionExpression(expressionModified ? positionExpression : new PositionExpression(targetVec));
                                        }
                                    }
                                }

                            }

                            return magicStream;
                        }
                        case EXPANSION: {

                            int count = (int) (minCount * 1.25);
                            long countPerShoot = Math.round(count / (splitArgument < 1 ? 1 : splitArgument));

                            if (world instanceof ServerWorld) {
                                ServerWorld serverWorld = (ServerWorld) world;
                                for (int i = 0; i <= duration; i += cooldown) {
                                    for (long l = 0; l < countPerShoot; l++) {
                                        DanmakuEntity spawn = Entities.DANMAKU_ENTITY.get().spawn(serverWorld, null, null, world.getPlayerByUUID(invoker.player), invoker.beginPos, SpawnReason.TRIGGERED, true, true);
                                        if (spawn != null) {
                                            spawn.setRGBA(streamEigenMatrix.getRGBA());
                                            spawn.setTargetVec(targetVec);
                                            spawn.setRequiredVariables((float) (strength) * 0.6f, ((float) duration * 1.3f), DanmakuEntity.DanmakuType.BULLET);
                                            spawn.setPositionExpression(expressionModified ? positionExpression : new PositionExpression(targetVec));
                                        }
                                    }
                                }

                            }

                            return magicStream;
                        }
                        case CONTINUOUS: {
                            int count = (int) minCount;
                            long countPerShoot = Math.round(count / (splitArgument < 1 ? 1 : splitArgument));

                            if (world instanceof ServerWorld) {
                                ServerWorld serverWorld = (ServerWorld) world;
                                for (int i = 0; i <= duration; i += cooldown) {
                                    for (long l = 0; l < countPerShoot; l++) {
                                        DanmakuEntity spawn = Entities.DANMAKU_ENTITY.get().spawn(serverWorld, null, null, world.getPlayerByUUID(invoker.player), invoker.beginPos, SpawnReason.TRIGGERED, true, true);
                                        if (spawn != null) {
                                            double v = (range / strength) * 1.2;
                                            spawn.setRGBA(streamEigenMatrix.getRGBA());
                                            spawn.setTargetVec(targetVec);
                                            spawn.setRequiredVariables((float) (strength), ((float) duration * 1.3f), DanmakuEntity.DanmakuType.BULLET);
                                            spawn.setPositionExpression(expressionModified ? positionExpression : new PositionExpression(targetVec));
                                        }
                                    }
                                }

                            }

                            return magicStream;
                        }
                        case DIAGONALIZE: {

                        }

                    }

                    break;
                }
                case RESONANCE: {

                    break;
                }
            }

            return magic;
        });
        return magic;
    }

    @Override
    public MagicStream applyWithRender(MagicStream magicStream) {
//        magicStream.renders.add((lastRender, stream) -> {
//
//        });
        return magicStream;
    }

    public boolean setPositionExpression(PositionExpression expression) {
        this.positionExpression = expression;
        return (expressionModified = true);
    }

    public void resetPositionExpression() {
        this.positionExpression = new PositionExpression();
        expressionModified = false;
    }


}
