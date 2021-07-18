package mfrf.magic_circle.item.resources;

import net.minecraft.item.MusicDiscItem;
import net.minecraft.util.SoundEvent;

import java.util.function.Supplier;

public class FinalSongRecord extends MusicDiscItem {
    public FinalSongRecord(int comparatorValue, Supplier<SoundEvent> soundSupplier, Properties builder) {
        super(comparatorValue, soundSupplier, builder);
    }
}
