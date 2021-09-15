package com.terrifictable55.yasashii.module.modules.misc;

import com.terrifictable55.yasashii.module.Category;
import com.terrifictable55.yasashii.module.Module;

public class AutoSuicide extends Module {
    public AutoSuicide() {
        super("SeK0", Category.MISC);
    }

    public void onEnable() {
        mc.player.sendChatMessage("/kill");
        this.toggle();
    }
}
