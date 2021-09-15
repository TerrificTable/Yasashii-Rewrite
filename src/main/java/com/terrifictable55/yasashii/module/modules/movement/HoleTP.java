package com.terrifictable55.yasashii.module.modules.movement;

import com.terrifictable55.yasashii.module.Category;
import com.terrifictable55.yasashii.module.Module;

public class HoleTP extends Module {
    public HoleTP() {
        super("HoleTP", Category.MOVEMENT);
    }

    public void onUpdate() {
        if (mc.player.onGround) {
            --mc.player.motionY;
        }
    }
}
