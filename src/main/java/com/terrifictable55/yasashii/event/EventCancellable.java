package com.terrifictable55.yasashii.event;

import net.minecraft.client.Minecraft;

public class EventCancellable {
    private final Era era_switch = Era.EVENT_PRE;
    private final float partial_ticks;

    public EventCancellable() {
        partial_ticks = Minecraft.getMinecraft().getRenderPartialTicks();
    }

    public Era get_era() {
        return era_switch;
    }

    public float get_partial_ticks() {
        return partial_ticks;
    }

    public enum Era {
        EVENT_PRE,
        EVENT_PERI,
        EVENT_POST
    }

}
