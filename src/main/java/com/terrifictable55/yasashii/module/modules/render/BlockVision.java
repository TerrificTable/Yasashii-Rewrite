package com.terrifictable55.yasashii.module.modules.render;

import com.terrifictable55.yasashii.module.Category;
import com.terrifictable55.yasashii.module.Module;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BlockVision extends Module {
    public BlockVision() {
        super("BlockVision", Category.RENDER);
    }

    @SubscribeEvent
    public void onRenderBlockOverlayEvent(RenderBlockOverlayEvent event) {
        if (event.getOverlayType() == RenderBlockOverlayEvent.OverlayType.BLOCK)
            event.setCanceled(true);
    }
}
