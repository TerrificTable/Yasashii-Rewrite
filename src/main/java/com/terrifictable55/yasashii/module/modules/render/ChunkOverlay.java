package com.terrifictable55.yasashii.module.modules.render;

import com.terrifictable55.yasashii.event.events.RenderWorldEvent;
import com.terrifictable55.yasashii.module.Category;
import com.terrifictable55.yasashii.module.Module;
import com.terrifictable55.yasashii.utils.RenderUtil;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;

public class ChunkOverlay extends Module {
    public ChunkOverlay() {
        super("ChunkOverlay", Category.RENDER);
    }
    @EventHandler
    Listener<RenderWorldEvent> event = new Listener<RenderWorldEvent>(e -> {
    });
}
