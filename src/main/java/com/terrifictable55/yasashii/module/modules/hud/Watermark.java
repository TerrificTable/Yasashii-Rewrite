package com.terrifictable55.yasashii.module.modules.hud;

import com.terrifictable55.yasashii.module.Category;
import com.terrifictable55.yasashii.module.Module;
import com.terrifictable55.yasashii.util.Refrence;
import com.terrifictable55.yasashii.utils.ColourUtils;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Watermark extends Module {
    public Watermark() {
        super("Watermark", Category.HUD);
    }

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent event) {
        if (mc.player != null && mc.world != null) {
            if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
                mc.fontRenderer.drawStringWithShadow(Refrence.NAME + " " + Refrence.VERSION, 2, 2, ColourUtils.genRainbow());
            }
        }
    }
}
