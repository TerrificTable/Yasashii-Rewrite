package com.terrifictable55.yasashii.module.modules.misc;

import com.terrifictable55.yasashii.miscs.Discord;
import com.terrifictable55.yasashii.module.Category;
import com.terrifictable55.yasashii.module.Module;

public class DiscordRPC extends Module {
    public DiscordRPC() {
        super("DiscordRPC", Category.MISC);
    }

    public void onEnable() {
        super.onEnable();
        Discord.startRPC();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        Discord.stopRPC();

    }
}
