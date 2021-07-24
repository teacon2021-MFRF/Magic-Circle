package mfrf.magic_circle.magicutil.nodes.behaviornode;

import mfrf.magic_circle.gui.engraver_table.EngraverTableScreen;
import mfrf.magic_circle.gui.widgets.Argument;
import mfrf.magic_circle.magicutil.MagicNodeBase;
import mfrf.magic_circle.magicutil.MagicStream;
import mfrf.magic_circle.magicutil.datastructure.MagicNodePropertyMatrix8By8;
import mfrf.magic_circle.util.PositionExpression;
import net.minecraft.nbt.CompoundNBT;

import java.util.ArrayList;

public class ThrowBehaviorNode extends BehaviorNodeBase {
    protected boolean expressionModified = false;
    protected PositionExpression positionExpression = new PositionExpression();

    public ThrowBehaviorNode() {
        super(BehaviorType.THROW);
    }


    @Override
    public DataContainer apply(MagicStream magic) {
//        boolean flag = true;
//        MagicNodeBase lastNode = magic.info.lastNode;
//
//        magic.functions.add((magicStream, magicStreamInfo) -> {
//
//            MagicStream.DataContain data = magicStreamInfo.data;
//            World world = data.world;
//            MagicNodePropertyMatrix8By8 streamEigenMatrix = data.eigenMatrix;
//            double strength = streamEigenMatrix.getStrength();
//            double range = streamEigenMatrix.getRange();
//            double duration = streamEigenMatrix.getDuration();
//            double execute_speed = streamEigenMatrix.getExecuteSpeed();
//            double cooldown = streamEigenMatrix.getCoolDown();
//            double efficient = streamEigenMatrix.getEfficient();
//
//            PositionExpression actualExpression = expressionModified ? positionExpression : new PositionExpression(data.targetVec);
//            int count = (int) Math.min(Math.max(execute_speed - cooldown, 1) * strength / 200, 2000);
//            float danmakuDamage = (float) strength;
//            float speedScale = (float) (strength / (efficient * 10));
//            float danmakuDuration = (float) duration;
//            boolean penetrate_able = false;
//            DanmakuEntity.DanmakuType type = DanmakuEntity.DanmakuType.BULLET;
//            Consumer<LivingDamageEvent> damage_consumer = null;
//            BiConsumer<BlockRayTraceResult, DanmakuEntity> crash_consumer = null;
//
//
//            switch (lastNode.getNodeType()) {
//                case EFFECT: {
//                    EffectNodeBase effectNodeBase = (EffectNodeBase) lastNode;
//                    switch (effectNodeBase.effectType) {
//                        case REDSTONE: {
//                            RedStoneNode lashRedStoneNode = (RedStoneNode) effectNodeBase;
//                            crash_consumer = (blockRayTraceResult, danmakuEntity) -> {
//                                World level = danmakuEntity.level;
//                                BlockPos hitPos = blockRayTraceResult.getBlockPos();
//                                HashSet<Vector3i> vector3is = MathUtil.calculateZone(data.rangeType, data.getRangeX(), data.getRangeY(), data.getRangeZ(), data.getRadius());
//                                vector3is.stream().map(hitPos::offset).map(level::getBlockState).filter(blockState -> blockState.getBlock() == Blocks.REDSTONE_WIRE).forEach(blockState -> {
//                                    if (blockState.getBlock() == Blocks.REDSTONE_WIRE) {
//                                        level.setBlock(hitPos, blockState.setValue(RedstoneWireBlock.POWER, strength > 15 ? 15 : (int) strength), 0);
//                                    }
//                                });
//                            };
//                            break;
//                        }
//                        case LIHUO: {
//                            damage_consumer = event -> {
//                                LivingEntity entityLiving = event.getEntityLiving();
//                                entityLiving.setRemainingFireTicks((int) duration * 3);
//                            };
//                            crash_consumer = (blockRayTraceResult, danmakuEntity) -> {
//                                World level = danmakuEntity.level;
//                                BlockPos hitPos = blockRayTraceResult.getBlockPos();
//                                HashSet<Vector3i> vector3is = MathUtil.calculateZone(data.rangeType, data.getRangeX(), data.getRangeY(), data.getRangeZ(), data.getRadius());
//                                vector3is.stream().map(Vector3i::below).map(hitPos::offset).filter(pos -> !level.getBlockState(pos).isAir()).forEach(pos -> {
//                                    level.setBlock(pos.above(), Blocks.FIRE.defaultBlockState(), 0);
//                                });
//                            };
//                            break;
//                        }
//                        case KANSHUI: {
//                            damage_consumer = event -> {
//                                event.getEntityLiving().addEffect(new EffectInstance(Effects.WATER_BREATHING, (int) duration * 2, (int) strength));
//                            };
//                            break;
//                        }
//                        case KUNDI: {
//                            break;
//                        }
//                        case GENSHAN: {
//                            crash_consumer = (blockRayTraceResult, danmakuEntity) -> {
//                                World level = danmakuEntity.level;
//                                BlockPos hitPos = blockRayTraceResult.getBlockPos();
//                                HashSet<Vector3i> vector3is = MathUtil.calculateZone(data.rangeType, data.getRangeX(), data.getRangeY(), data.getRangeZ(), data.getRadius());
//                                vector3is.stream().map(hitPos::offset).filter(pos -> level.getBlockState(pos).isAir()).forEach(pos -> {
//                                    level.setBlock(pos, Blocks.DIRT.defaultBlockState(), 0);
//                                });
//                            };
//                            break;
//                        }
//                        case THUNDER: {
//                            crash_consumer = (blockRayTraceResult, danmakuEntity) -> {
//                                World level = danmakuEntity.level;
//                                BlockPos hitPos = blockRayTraceResult.getBlockPos();
//                                HashSet<Vector3i> vector3is = MathUtil.calculateZone(data.rangeType, data.getRangeX(), data.getRangeY(), data.getRangeZ(), data.getRadius());
//                                vector3is.stream().filter(vector3i -> level.getRandom().nextFloat() < 0.3).map(hitPos::offset).forEach(pos -> {
//                                    if (!world.isClientSide()) {
//                                        EntityType.LIGHTNING_BOLT.spawn(((ServerWorld) level), null, level.getPlayerByUUID(data.player), pos, SpawnReason.EVENT, true, true);
//                                    }
//                                });
//                            };
//                        }
//                        case DUIZE: {
//                            break;
//                        }
//                        case DRYSKY: {
////                            crash_consumer = (blockRayTraceResult, danmakuEntity) -> {
////
////                                World level = danmakuEntity.level;
////                                BlockPos hitPos = blockRayTraceResult.getBlockPos();
////                                HashSet<Vector3i> vector3is = MathUtil.calculateZone(data.rangeType, data.getRangeX(), data.getRangeY(), data.getRangeZ(), data.getRadius());
////                                vector3is.stream().map(Vector3i::below).map(hitPos::offset).filter(pos -> {
////                                    BlockState blockState = level.getBlockState(pos);
////                                    return !(blockState.isAir() && blockState.hasTileEntity() && blockState.hasLargeCollisionShape() && blockState.getBlock() == Blocks.BEDROCK);
////                                }).forEach(pos -> {
////                                    BlockState blockState = level.getBlockState(pos);
////                                    level.setBlock(pos, Blocks.AIR.defaultBlockState(), 0);
////                                    BlockPos blockPos;
//////                                    do {
//////                                        level.geth
//////                                    }
////                                    level.setBlock(, blockState, 0);
////                                });
////                            };
//                            break;
//                        }
//                        case TAICHI: {
//                            float v = (danmakuDamage + danmakuDuration + speedScale) / 3;
//                            danmakuDamage = v;
//                            danmakuDuration = v;
//                            speedScale = v;
//                            break;
//                        }
//                        case SUNDAE: {
//
//                            penetrate_able = true;
//
//                            break;
//                        }
//                    }
//                }
//                case BEHAVIOR: {
//                    BehaviorNodeBase behaviorNode = (BehaviorNodeBase) lastNode;
//
//                    switch (behaviorNode.getBehaviorType()) {
//                        case BEAM: {
//                            damage_consumer = event -> {
//                                LivingEntity entityLiving = event.getEntityLiving();
//                                entityLiving.addEffect(new EffectInstance(Effects.GLOWING, (int) duration * 16, 1));
//                            };
//                            break;
//                        }
//                        case CIRCLE: {
//                            break;
//                        }
//                        case RADIUS: {
//                            break;
//                        }
//                        case TRIGGER: {
//                            if (data.targetPos != null) {
//                                magicStream.info.data.beginPos = new BlockPos(data.targetPos);
//                            }
//                        }
//                        case RESTRICT: {
//                            break;
//                        }
//                    }
//
//                }
//                case DECORATE: {
//                    DecorateNodeBase decorateNode = (DecorateNodeBase) lastNode;
//
//                    switch (decorateNode.decorateType) {
//
//                        case POWER: {
//                            efficient = Math.sqrt(efficient);
//                            danmakuDamage = (float) Math.pow(danmakuDamage, 2);
//                            break;
//                        }
//                        case INVERT: {
//                            float executeRange = (float) (7.0 + 0.25 * range);
//                            float half = executeRange / 2;
//                            BlockPos offset = data.beginPos;
//                            List<DanmakuEntity> entities = world.getEntities(Entities.DANMAKU_ENTITY.get(), new AxisAlignedBB(offset.offset(-half, -half, -half), offset.offset(half, half, half)), danmakuEntity -> {
//                                String s = danmakuEntity.getEntityData().get(DanmakuEntity.TYPE);
//                                return s.equals(DanmakuEntity.DanmakuType.BULLET.name()) || s.equals(DanmakuEntity.DanmakuType.NULL.name());
//                            });
//                            entities.forEach(DanmakuEntity::remove);
//
//                            break;
//                        }
//                        case STRENGTH: {
//                            speedScale *= 2;
//                            efficient /= 2;
//                            break;
//                        }
//                        case EXPANSION: {
//                            count *= 2;
//                            danmakuDamage /= 2;
//                            break;
//                        }
//                        case CONTINUOUS: {
//                            danmakuDuration *= 1.4;
//                            efficient /= 1.4;
//                            break;
//                        }
//                        case DIAGONALIZE: {
//                            magicStream.info.data.complexityAddition += 20;
//                            efficient /= 2;
//                            danmakuDamage = (float) Math.pow(danmakuDamage, 2);
//                            break;
//                        }
//                    }
//
//                    break;
//                }
//            }
//
//            if (world instanceof ServerWorld) {
//                CompoundNBT compoundNBT = actualExpression.serializeNBT();
//                Random rand = world.getRandom();
//                for (int l = 0; l <= count; l++) {
//                    BlockPos offset = data.beginPos.offset(rand.nextInt((int) range) / 10, rand.nextInt((int) range) / 10, rand.nextInt((int) range) / 10);
//                    DanmakuEntity spawn = Entities.DANMAKU_ENTITY.get().spawn((ServerWorld) world, null, null, data.player == null ? null : world.getPlayerByUUID(data.player), offset, SpawnReason.TRIGGERED, true, true);
//                    spawn.setRGBA(streamEigenMatrix.getRGBA());
//                    spawn.setTargetVec(data.targetVec);
//                    spawn.setRequiredVariables((int) danmakuDamage, (int) danmakuDuration, type);
//                    spawn.setSpeedScale(speedScale);
//                    spawn.setPenetrateAble(penetrate_able);
//
//                    PositionExpression expression = new PositionExpression();
//                    expression.deserializeNBT(compoundNBT);
//                    expression.setAddition(l);
//
//                    spawn.setPositionExpression(expression);
//                    if (damage_consumer != null) {
//                        InGameCaches.onDanmakuAttack.put(spawn.getUUID(), damage_consumer);
//                    }
//                    if (crash_consumer != null) {
//                        InGameCaches.onDanmakuCrash.put(spawn.getUUID(), crash_consumer);
//                    }
//                }
//            }
//            this.eigenMatrix.set(MagicNodePropertyMatrix8By8.INDEX.EFFICIENT, (float) efficient);
//
//            magic.Matrixtimes(this.eigenMatrix);
//
//            return magic;
//        });
//
        return new DataContainer(magic, true);

    }

    public static MagicNodeBase deserializeNBT(CompoundNBT nbt, int layer, MagicNodePropertyMatrix8By8 matrix) {
        boolean expression_modified = nbt.getBoolean("expression_modified");
        CompoundNBT expressionNBT = nbt.getCompound("expression");
        PositionExpression expression = new PositionExpression();
        expression.deserializeNBT(expressionNBT);
        ThrowBehaviorNode throwBehaviorNode = new ThrowBehaviorNode();
        throwBehaviorNode.layer = layer;
        throwBehaviorNode.eigenMatrix = matrix;
        throwBehaviorNode.expressionModified = expression_modified;
        throwBehaviorNode.positionExpression = expression;
        return throwBehaviorNode;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT compoundNBT = super.serializeNBT();
        compoundNBT.putBoolean("expression_modified", expressionModified);
        compoundNBT.put("expression", positionExpression.serializeNBT());
        return compoundNBT;
    }

    @Override
    public ArrayList<Argument<?>> getArguments() {
        ArrayList<Argument<?>> arguments = new ArrayList<>();
        arguments.add(new Argument<>("xPositionExpression", positionExpression::setX, positionExpression::getX, EngraverTableScreen.ArgumentType.STRING));
        arguments.add(new Argument<>("yPositionExpression", positionExpression::setY, positionExpression::getY, EngraverTableScreen.ArgumentType.STRING));
        arguments.add(new Argument<>("zPositionExpression", positionExpression::setZ, positionExpression::getZ, EngraverTableScreen.ArgumentType.STRING));
        return arguments;
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
