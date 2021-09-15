package com.terrifictable55.yasashii.module.modules.chat;

import com.terrifictable55.yasashii.Yasashii;
import com.terrifictable55.yasashii.module.Category;
import com.terrifictable55.yasashii.module.Module;
import net.minecraft.client.gui.GuiChat;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

public class ChatPrefix extends Module {
    public ChatPrefix() {
        super("ChatPrefix", Category.CHAT);
    }

    @SubscribeEvent
    public void onKeyPress(InputEvent.KeyInputEvent event) {
        if (mc.currentScreen == null && Keyboard.isKeyDown(Keyboard.KEY_EQUALS)) {
            mc.displayGuiScreen(new GuiChat(Yasashii.prefix));
        }
    }
}
