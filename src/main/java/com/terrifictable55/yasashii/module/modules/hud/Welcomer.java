package com.terrifictable55.yasashii.module.modules.hud;

import com.terrifictable55.yasashii.Yasashii;
import com.terrifictable55.yasashii.manager.Setting;
import com.terrifictable55.yasashii.module.Category;
import com.terrifictable55.yasashii.module.Module;
import com.terrifictable55.yasashii.utils.ColourUtils;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Calendar;

public class Welcomer extends Module {
    public Welcomer() {
        super("Welcomer", Category.HUD);
    }
    Setting x;
    Setting y;

    @Override
    public void setup() {
        Yasashii.settingManager.rSetting(x = new Setting("X Position", this, 2, 0.0, 1000, false, "MemoryX"));
        Yasashii.settingManager.rSetting(y= new Setting("Y Position", this, 350, 0.0, 1000, false, "MemoryY"));
    }

    private String WelcomeMessages() {
        final int timeOfDay = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        if (timeOfDay < 12) {
            return "Good Morning, ";
        } else if (timeOfDay < 16) {
            return "Good Afternoon, ";
        } else if (timeOfDay < 21) {
            return "Good Evening, ";
        } else {
            return "Good Night, ";
        }
    }

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent event) {
        float xf = (float) x.getValDouble();
        float yf = (float) y.getValDouble();
        if (mc.player != null && mc.world != null) {
            if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
                mc.fontRenderer.drawStringWithShadow(
                        WelcomeMessages() + mc.getSession().getUsername()
                        , xf
                        , yf
                        , ColourUtils.genRainbow());
            }
        }
    }
}
