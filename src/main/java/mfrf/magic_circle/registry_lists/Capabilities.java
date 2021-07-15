package mfrf.magic_circle.registry_lists;

import mfrf.magic_circle.interfaces.IMagicContainerItem;
import mfrf.magic_circle.interfaces.IMagicalItem;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class Capabilities {

    @CapabilityInject(IMagicalItem.class)
    public static Capability<IMagicalItem> MAGICAL_ITEM;

}
