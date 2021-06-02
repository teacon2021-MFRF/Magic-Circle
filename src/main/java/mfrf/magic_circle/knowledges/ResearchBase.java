package mfrf.magic_circle.knowledges;

import mfrf.magic_circle.world_saved_data.PlayerKnowledge;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.UUID;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

public abstract class ResearchBase<T extends EntityEvent> {
    protected final String name;
    protected final float difficulty;
    protected final String[] preKnowledesNeed;
    protected final boolean repeatAble;
    private final BiPredicate<T, PlayerKnowledges> canUnlock;
    private final Consumer<PlayerKnowledges> onUnlock;

    protected ResearchBase(String name, float difficulty, String[] preKnowledesNeed, boolean repeatAble, BiPredicate<T, PlayerKnowledges> canUnlock, Consumer<PlayerKnowledges> onUnlock) {
        this.name = name;
        this.difficulty = difficulty;
        this.preKnowledesNeed = preKnowledesNeed;
        this.repeatAble = repeatAble;
        this.canUnlock = canUnlock;
        this.onUnlock = onUnlock;
    }

    @SubscribeEvent
    public void onAction(T event) {
        Entity entity = event.getEntity();
        if (entity instanceof PlayerEntity) {
            PlayerEntity player = ((PlayerEntity) entity);
            UUID uuid = player.getUUID();
            World level = player.level;
            if (level.getRandom().nextFloat() >= difficulty && !level.isClientSide()) {
                PlayerKnowledge playerKnowledge = PlayerKnowledge.get(level);
                PlayerKnowledges playerKnowledges = playerKnowledge.get(uuid);
                if (canUnlock(event, playerKnowledges)) {
                    playerKnowledges.unlock(name);
                    onUnlock.accept(playerKnowledges);
                    playerKnowledge.setDirty();
                }
            }
        }
    }

    public boolean canUnlock(T event, PlayerKnowledges playerKnowledges) {
        return playerKnowledges.hasAllUnlocked(getPreKnowledgesNeed()) && canUnlock.test(event, playerKnowledges) && (this.repeatAble || playerKnowledges.hasUnlocked(this.name));
    }

    public String[] getPreKnowledgesNeed() {
        return preKnowledesNeed;
    }

}
