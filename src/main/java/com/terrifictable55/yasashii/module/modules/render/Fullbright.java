package com.terrifictable55.yasashii.module.modules.render;

import com.terrifictable55.yasashii.module.Category;
import com.terrifictable55.yasashii.module.Module;

public class Fullbright extends Module {
    public Fullbright() {
        super("Fullbright", Category.RENDER);
    }

    @Override
    public void onEnable() {
        mc.gameSettings.gammaSetting = 100;
    }

    @Override
    public void onDisable() {
        mc.gameSettings.gammaSetting = 1;
    }
}
