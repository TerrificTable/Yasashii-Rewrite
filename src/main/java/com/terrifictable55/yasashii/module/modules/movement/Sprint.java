package com.terrifictable55.yasashii.module.modules.movement;

import com.terrifictable55.yasashii.module.Category;
import com.terrifictable55.yasashii.module.Module;

public class Sprint extends Module {

    public Sprint() {
        super("Sprint", Category.MOVEMENT);
    }

    public void onUpdate() {
        try {
            if (mc.gameSettings.keyBindForward.isKeyDown() && !(mc.player.collidedHorizontally)) {
                if (!mc.player.isSprinting()) {
                    mc.player.setSprinting(true);
                }
            }
        } catch (Exception ignored) {
        }
    }
}
