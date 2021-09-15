package com.terrifictable55.yasashii.event.events;

import com.terrifictable55.yasashii.event.Event;
import net.minecraft.client.gui.GuiScreen;

public class GuiScreenDisplayEvent extends Event {
    private final GuiScreen guiScreen;

    public GuiScreenDisplayEvent(GuiScreen screen) {
        super();
        guiScreen = screen;
    }

    public GuiScreen getScreen() {
        return guiScreen;
    }
}
