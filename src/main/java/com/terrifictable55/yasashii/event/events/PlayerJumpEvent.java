package com.terrifictable55.yasashii.event.events;

import com.terrifictable55.yasashii.event.Event;
import net.minecraft.entity.player.EntityPlayer;

public class PlayerJumpEvent extends Event {
    EntityPlayer player;

    public PlayerJumpEvent(EntityPlayer player) {
        this.player = player;
    }
}
