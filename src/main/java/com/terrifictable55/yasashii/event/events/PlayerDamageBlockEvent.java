package com.terrifictable55.yasashii.event.events;

import com.terrifictable55.yasashii.event.Event;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class PlayerDamageBlockEvent extends Event {
    private BlockPos BlockPos;
    private EnumFacing Direction;

    public void EventPlayerDamageBlock(BlockPos posBlock, EnumFacing directionFacing) {
        BlockPos = posBlock;
        setDirection(directionFacing);
    }

    public BlockPos getPos() {
        return BlockPos;
    }

    public EnumFacing getDirection() {
        return Direction;
    }

    public void setDirection(EnumFacing direction) {
        Direction = direction;
    }
}
