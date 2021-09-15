package com.terrifictable55.yasashii.module.modules.render;

import com.terrifictable55.yasashii.capes.CapeUtil;
import com.terrifictable55.yasashii.module.Category;
import com.terrifictable55.yasashii.module.Module;

public class CustomCape extends Module {
    Cape cape = Cape.YasashiiCape;
    public CustomCape() {
        super("CustomCape", Category.RENDER);
    }

    @Override
    public void onEnable() {
        if (cape == Cape.YasashiiCape) {
            CapeUtil.startAnimationLoop();
        }
    }

    @Override
    public void onDisable() {
        CapeUtil.stopAnimationLoop();
        CapeUtil.capeTexture = null;
    }

    enum Cape {
        YasashiiCape,
        YasashiiDrop,
        CustomCape
    }
}
