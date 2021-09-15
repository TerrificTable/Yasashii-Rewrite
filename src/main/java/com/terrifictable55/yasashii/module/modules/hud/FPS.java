package com.terrifictable55.yasashii.module.modules.hud;

import com.terrifictable55.yasashii.Yasashii;
import com.terrifictable55.yasashii.manager.Setting;
import com.terrifictable55.yasashii.module.Category;
import com.terrifictable55.yasashii.module.Module;
import com.terrifictable55.yasashii.utils.ColourUtils;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class FPS extends Module {
    public FPS() {
        super("FPS", Category.HUD);
    }

    Setting x;
    Setting y;

    @Override
    public void setup() {
        Yasashii.settingManager.rSetting(x = new Setting("X Position", this, 2, 0.0, 1000, false, "FpsX"));
        Yasashii.settingManager.rSetting(y= new Setting("Y Position", this, 350, 0.0, 1000, false, "FpsY"));
    }


    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent event) {
        float xf = (float) x.getValDouble();
        float yf = (float) y.getValDouble();
        if (mc.player != null && mc.world != null) {
            int FPS = Minecraft.getDebugFPS();
            mc.fontRenderer.drawStringWithShadow("FPS: " + FPS
                    , xf
                    , yf
                    , ColourUtils.genRainbow());
        }
    }
}
