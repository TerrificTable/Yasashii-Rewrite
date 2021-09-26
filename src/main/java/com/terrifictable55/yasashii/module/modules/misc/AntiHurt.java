package com.terrifictable55.yasashii.module.modules.misc;

import com.terrifictable55.yasashii.manager.Setting;
import com.terrifictable55.yasashii.module.Category;
import com.terrifictable55.yasashii.module.Module;
import com.terrifictable55.yasashii.utils.Connection;
import net.minecraft.network.play.server.SPacketUpdateHealth;

public class AntiHurt extends Module {
    Setting mode = rSetting(new Setting("Mode", this, "Packet", "Death", "Packet"));

    public AntiHurt() {
        super("AntiHurt", Category.MISC);
    }

    @Override
    public void onEnable() {
        if (mode.getValString().equalsIgnoreCase("Death")) {
            if (mc.player != null) {
                mc.player.isDead = true;
                mc.gameSettings.keyBindForward.isPressed();
            }
        }
    }

    @Override
    public void onDisable() {
        mc.player.isDead = false;
    }


    @Override
    public boolean onPacket(Object packet, Connection.Side side) {
        if (mode.getValString().equalsIgnoreCase("Packet")) {
            return !(packet instanceof SPacketUpdateHealth);
        }
        return true;
    }
}
