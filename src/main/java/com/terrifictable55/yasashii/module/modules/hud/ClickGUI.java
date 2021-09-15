package com.terrifictable55.yasashii.module.modules.hud;

import com.terrifictable55.yasashii.clickgui.ClickGui;
import com.terrifictable55.yasashii.module.Category;
import com.terrifictable55.yasashii.module.Module;

public class ClickGUI extends Module {
    public ClickGUI() {
        super("ClickGUI", Category.HUD);
    }

    @Override
    public void onEnable() {
        if (mc.player != null && mc.world != null) {
            mc.displayGuiScreen(new ClickGui());
            toggle();
        }
    }
}
