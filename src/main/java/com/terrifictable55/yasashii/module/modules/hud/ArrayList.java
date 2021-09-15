package com.terrifictable55.yasashii.module.modules.hud;

import com.terrifictable55.yasashii.Yasashii;
import com.terrifictable55.yasashii.manager.Setting;
import com.terrifictable55.yasashii.module.Category;
import com.terrifictable55.yasashii.module.Module;
import com.terrifictable55.yasashii.utils.ColourUtils;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;

public class ArrayList extends Module {
    public ArrayList() {
        super("ArrayList", Category.HUD);
    }

    public int GenRainbow() {
        int drgb;
        int color;
        int argb;
        float[] hue = new float[]{(float) (System.currentTimeMillis() % 11520L) / 11520.0f};
        int rgb = Color.HSBtoRGB(hue[0], 1.0f, 1.0f);
        int red = rgb >> 16 & 255;
        int green = rgb >> 8 & 255;
        int blue = rgb & 255;
        color = argb = ColourUtils.toRGBA(red, green, blue, 255);
        return color;
    }

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent event) {
        if (mc.player != null && mc.world != null) {
            if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
                float currY = mc.fontRenderer.FONT_HEIGHT + 2;
                for (Module m : Yasashii.moduleManager.getModules()) {
                    if (m.isToggled()) {
                        mc.fontRenderer.drawStringWithShadow("\u300a" + m.getName() + "\u300b", 2, 5 + currY, GenRainbow()); // y.value.intValue()
                        currY += mc.fontRenderer.FONT_HEIGHT;
                    }
                }
            }
        }
    }
}
