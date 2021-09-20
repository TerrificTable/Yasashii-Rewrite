package com.terrifictable55.yasashii.module.modules.hud;

import com.terrifictable55.yasashii.Yasashii;
import com.terrifictable55.yasashii.manager.Setting;
import com.terrifictable55.yasashii.module.Category;
import com.terrifictable55.yasashii.module.Module;
import com.terrifictable55.yasashii.utils.ColourUtils;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Memory extends Module {
    public Memory() {
        super("Menory", Category.HUD);
    }

    Setting x;
    Setting y;

    @Override
    public void setup() {
        Yasashii.settingManager.rSetting(x = new Setting("X Position", this, 2, 0.0, 1000, false, "MemoryX"));
        Yasashii.settingManager.rSetting(y= new Setting("Y Position", this, 350, 0.0, 1000, false, "MemoryY"));
    }

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent event) {
        float xf = (float) x.getValDouble();
        float yf = (float) y.getValDouble();
        if (mc.player != null && mc.world != null) {
            final long K = 1024;
            final long M = K * K;
            final long G = M * K;
            final long T = G * K;

            long usedMemory = Runtime.getRuntime().freeMemory();
            long totalMemory = Runtime.getRuntime().totalMemory();
            double usedmemory = usedMemory / M;
            double totalmemory = totalMemory/M;
            if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
                mc.fontRenderer.drawStringWithShadow(usedmemory + "/" + totalmemory
                        , xf
                        , yf
                        , ColourUtils.genRainbow());
            }
        }
    }
}
