package com.terrifictable55.yasashii.utils;

import com.terrifictable55.yasashii.module.Module;
import com.terrifictable55.yasashii.module.ModuleManager;

public class EventsHander {
    public static boolean isInit = false;

    public boolean onPacket(Object packet, Connection.Side side) {
        boolean suc = true;
        try {
            for (Module m : ModuleManager.getEnabledmodules()) {
                if (!isInit)
                    suc &= m.onDisablePacket(packet, side);
                if (Wrapper.INSTANCE.world() == null) {
                    continue;
                }
                suc &= m.onPacket(packet, side);
            }

        } catch (RuntimeException ex) {
            cow("PacketError", ex);
        }
        return suc;
    }

    private void cow(String Evnt, RuntimeException ex) {
        ex.printStackTrace();
        ChatUtils.error("RuntimeException: " + Evnt);
        ChatUtils.error(ex.toString());
        Wrapper.INSTANCE.copy(ex.toString());
    }
}
