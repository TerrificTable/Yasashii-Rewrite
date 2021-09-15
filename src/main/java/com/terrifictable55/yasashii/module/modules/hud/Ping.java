package com.terrifictable55.yasashii.module.modules.hud;

import com.terrifictable55.yasashii.Yasashii;
import com.terrifictable55.yasashii.manager.Setting;
import com.terrifictable55.yasashii.module.Category;
import com.terrifictable55.yasashii.module.Module;
import com.terrifictable55.yasashii.utils.ColourUtils;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Objects;

public class Ping extends Module {
    public Ping() {
        super("Ping", Category.HUD);
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
            if (!mc.isSingleplayer()) {
                long ping = Objects.requireNonNull(mc.getCurrentServerData()).pingToServer;
                if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
                    mc.fontRenderer.drawStringWithShadow(String.valueOf(ping)
                            , xf
                            , yf
                            , ColourUtils.genRainbow());
                }
            }
        }
    }
}
