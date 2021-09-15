package com.terrifictable55.yasashii.util;

import com.terrifictable55.yasashii.util.font.CFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import org.lwjgl.input.Keyboard;

public class Wrapper {
    final static Minecraft mc = Minecraft.getMinecraft();
    private static CFontRenderer fontRenderer;

    public static Minecraft GetMC() {
        return mc;
    }

    public static EntityPlayerSP GetPlayer() {
        return mc.player;
    }

    public static int getKey(String keyname) {
        return Keyboard.getKeyIndex(keyname.toUpperCase());
    }

    public static CFontRenderer getFontRenderer() {
        return fontRenderer;
    }
}
