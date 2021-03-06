package mfrf.magic_circle.registry_lists;

import mfrf.magic_circle.MagicCircle;
import mfrf.magic_circle.gui.assembly_table.AssemblyTableContainer;
import mfrf.magic_circle.gui.engraver_table.EngraverTableContainer;
import mfrf.magic_circle.gui.research_table.ResearchTableContainer;
import mfrf.magic_circle.knowledges.PlayerKnowledges;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class GuiContainers {
    public static final DeferredRegister<ContainerType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.CONTAINERS, MagicCircle.MOD_ID);

    public static final RegistryObject<ContainerType<ResearchTableContainer>> RESEARCH_TABLE_CONTAINER = REGISTER.register("research_table_container",
            () -> IForgeContainerType.create(
                    (windowId, inv, data) -> new ResearchTableContainer(windowId, inv, data.readBlockPos(), inv.player.getCommandSenderWorld(), PlayerKnowledges.deserializeNBT(data.readAnySizeNbt()))));
    public static final RegistryObject<ContainerType<AssemblyTableContainer>> ASSEMBLY_TABLE_CONTAINER = REGISTER.register("magic_model_assembly_table_container",
            () -> IForgeContainerType.create(
                    (windowId, inv, data) -> new AssemblyTableContainer(windowId, inv, data.readBlockPos(), inv.player.getCommandSenderWorld())));
    public static final RegistryObject<ContainerType<EngraverTableContainer>> ENGRAVER_TABLE_CONTAINER = REGISTER.register("engraver_table_container",
            () -> IForgeContainerType.create(
                    (windowId, inv, data) -> new EngraverTableContainer(windowId, inv, data.readBlockPos(), inv.player.getCommandSenderWorld())));
}
