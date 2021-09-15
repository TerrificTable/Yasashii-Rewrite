package com.terrifictable55.yasashii.module.modules.chat;

import com.terrifictable55.yasashii.Yasashii;
import com.terrifictable55.yasashii.module.Category;
import com.terrifictable55.yasashii.module.Module;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ChatSuffix extends Module {
    public ChatSuffix() {
        super("ChatSuffix", Category.CHAT);
    }

    @SubscribeEvent
    public void onChat(ClientChatEvent event) {
        String suffix = "  <<Yasashii>> ";
        if (event.getMessage().startsWith("/")) return;
        if (event.getMessage().startsWith(Yasashii.prefix)) return;
        event.setMessage(event.getMessage() + suffix);
    }
}
