package mfrf.magic_circle.magicutil;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.function.BiFunction;
import java.util.function.Function;

public class MagicStream {
    public LinkedList<BiFunction<MagicStream, MagicStreamInfo, MagicStream>> PRIORITY_FIRST = new LinkedList<>();
    public LinkedList<BiFunction<MagicStream, MagicStreamInfo, MagicStream>> PRIORITY_MIDDLE = new LinkedList<>();
    public LinkedList<BiFunction<MagicStream, MagicStreamInfo, MagicStream>> PRIORITY_LAST = new LinkedList<>();


    public static record MagicStreamInfo(Invoker invoker, MagicNodeBase lastNode, Receiver receiver, World world) {
    }

    public enum Priority {
        FIRST, MIDDLE, LAST;
    }
}
