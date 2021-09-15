package com.terrifictable55.yasashii.event.events;

import com.terrifictable55.yasashii.event.Event;
import net.minecraft.util.math.BlockPos;

public class DestroyBlockEvent extends Event {
    BlockPos pos;

    public DestroyBlockEvent(BlockPos blockPos) {
        super();
        pos = blockPos;
    }

    public BlockPos getBlockPos() {
        return pos;
    }
}
