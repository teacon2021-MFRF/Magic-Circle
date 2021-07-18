package mfrf.magic_circle.block.research_table;

import mfrf.magic_circle.Config;
import mfrf.magic_circle.MagicCircle;
import mfrf.magic_circle.block.NamedContainerTileBase;
import mfrf.magic_circle.gui.research_table.ResearchTableContainer;
import mfrf.magic_circle.item.resources.ItemTestPaper;
import mfrf.magic_circle.item.tool.ItemPenAndInk;
import mfrf.magic_circle.json_recipe_configs.JsonConfigItemResearch;
import mfrf.magic_circle.json_recipe_configs.research_test.ResearchTestBase;
import mfrf.magic_circle.knowledges.PlayerKnowledges;
import mfrf.magic_circle.registry_lists.JsonConfigs;
import mfrf.magic_circle.registry_lists.TileEntities;
import mfrf.magic_circle.world_saved_data.PlayerKnowledge;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

public class TileResearchTable extends NamedContainerTileBase {

    private Inventory inventory = new Inventory(3) {
        @Override
        public boolean canPlaceItem(int slot, ItemStack stack) {

            Slot value = Slot.values()[slot];
            switch (value) {
                case TEST_PAPER: {
                    if (stack.getItem() instanceof ItemTestPaper) {
                        if (getItem(slot).isEmpty()) {
                            return super.canPlaceItem(slot, stack);
                        }
                    }
                    return false;
                }
                case PEN_AND_INK: {
                    if (stack.getItem() instanceof ItemPenAndInk) {
                        if (getItem(slot).isEmpty()) {
                            return super.canPlaceItem(slot, stack);
                        }
                    }
                    return false;
                }
                default: {
                    return super.canPlaceItem(slot, stack);
                }
            }

        }

        @Override
        public void setChanged() {
            markDirty();
            super.setChanged();
        }
    };

    public TileResearchTable() {
        super(TileEntities.RESEARCH_TABLE.get());
    }

    @Override
    public CompoundNBT save(CompoundNBT p_189515_1_) {
        CompoundNBT save = super.save(p_189515_1_);
        save.put("inventory", getInventory().createTag());
        return save;
    }

    @Override
    public void load(BlockState p_230337_1_, CompoundNBT p_230337_2_) {
        super.load(p_230337_1_, p_230337_2_);
        INBT inventory = p_230337_2_.get("inventory");
        if (inventory != null) {
            this.getInventory().fromTag((ListNBT) inventory);
        }
    }

    @Override

    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("gui." + MagicCircle.MOD_ID + ".research_table");
    }

    @Nullable
    @Override
    public Container createMenu(int syncID, PlayerInventory inventory, PlayerEntity playerEntity) {
        return new ResearchTableContainer(syncID, inventory, this.worldPosition, this.level, PlayerKnowledge.getOrCreate(this.level).getOrCreate(playerEntity.getUUID()));
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
        markDirty();
    }

    public boolean checkAnswer(String answer, UUID playerId) {
        AtomicBoolean flag = new AtomicBoolean(false);
        ItemStack ink = inventory.getItem(Slot.PEN_AND_INK.ordinal());
        int inkUse = answer.getBytes().length;
        if (!ink.isEmpty() && ink.getItem() instanceof ItemPenAndInk && Config.MAX_USE_PEN_AND_INK.get() - ink.getDamageValue() > inkUse) {
            PlayerKnowledges orCreate = PlayerKnowledge.getOrCreate(level).getOrCreate(playerId);
            ResearchTestBase.Serializer.DataContainer test = ItemTestPaper.getTest(inventory.getItem(Slot.TEST_PAPER.ordinal()));
            if (test != null && test.answer.asPredicate().test(answer)) {
                if (orCreate.hasUnlocked(test.research)) {
                    flag.set(true);
                    orCreate.unlock(test.research);
                }
            } else {
                Stream<JsonConfigItemResearch> stream = level.getRecipeManager().getAllRecipesFor(JsonConfigs.Type.ITEM_RESEARCH_JSONCONFIG_TYPE).stream().filter(jsonConfigItemResearch -> jsonConfigItemResearch.getIngredient().test(inventory.getItem(Slot.ITEM_TO_ANALYSIS.ordinal())));
                if (stream.count() != 0) {
                    Optional<JsonConfigItemResearch> any = stream.findAny();
                    any.ifPresent(jsonConfigItemResearch -> {
                        ResourceLocation questionLocation = jsonConfigItemResearch.getQuestionLocation();
                        if (questionLocation != null) {
                            Optional<ResearchTestBase> optional = level.getRecipeManager().getAllRecipesFor(JsonConfigs.Type.RESEARCH_TEST_JSONCONFIG_TYPE).stream().filter(researchTestBase -> researchTestBase.getId().equals(questionLocation)).findAny();
                            if (optional.isPresent()) {
                                ResearchTestBase.Serializer.DataContainer dataContainer = optional.get().toDataContainer();
                                if (dataContainer.answer.asPredicate().test(answer)) {
                                    if (!orCreate.hasUnlocked(dataContainer.research)) {
                                        orCreate.unlock(dataContainer.research);
                                        flag.set(true);
                                    }
                                }
                            }
                        }
                        if (jsonConfigItemResearch.isRepeatable() || !orCreate.hasUnlocked(jsonConfigItemResearch.getId().toString()) && jsonConfigItemResearch.hasEnoughKnowledge(orCreate.getMath(), orCreate.getPhysical(), orCreate.getBagua(), orCreate.getMystery())) {
                            orCreate.unlockItem(jsonConfigItemResearch.getId().toString());
                            orCreate.addMath(jsonConfigItemResearch.getMathKnowledge());
                            orCreate.addBagua(jsonConfigItemResearch.getBaguaKnowledge());
                            orCreate.addMystery(jsonConfigItemResearch.getMysteryKnowledge());
                            orCreate.addPhysical(jsonConfigItemResearch.getPhysicalKnowledge());
                            flag.set(true);
                        }
                    });
                }
            }

            ink.setDamageValue(ink.getDamageValue() + inkUse);
        }

        return flag.get();
    }

    public enum Slot {
        TEST_PAPER, ITEM_TO_ANALYSIS, PEN_AND_INK,
        ;
    }
}
