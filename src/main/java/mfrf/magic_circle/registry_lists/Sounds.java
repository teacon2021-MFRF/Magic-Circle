package mfrf.magic_circle.registry_lists;

import mfrf.magic_circle.MagicCircle;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;

public class Sounds {
    public static final DeferredRegister<SoundEvent> REGISTER = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MagicCircle.MOD_ID);

    public static final HashMap<String, RegistryObject<SoundEvent>> PIANO_SOUNDS = registryPianoSounds();

    private static HashMap<String, RegistryObject<SoundEvent>> registryPianoSounds() {
        HashMap<String, RegistryObject<SoundEvent>> stringRegistryObjectHashMap = new HashMap<>();
        for (int i = 0; i <= 60; i++) {
            String name = "piano_" + i;
            stringRegistryObjectHashMap.put(
                    name, REGISTER.register(name, () -> new SoundEvent(new ResourceLocation(MagicCircle.MOD_ID, name)))
            );
        }

        return stringRegistryObjectHashMap;
    }
}
