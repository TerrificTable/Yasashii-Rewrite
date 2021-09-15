package com.terrifictable55.yasashii.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class Messages {
    private static final EntityPlayerSP player = Minecraft.getMinecraft().player;
    public static String prefix = TextFormatting.BLUE + "[" + TextFormatting.GOLD + "Yasashii" + TextFormatting.BLUE + "]" + TextFormatting.WHITE;

    public static void sendRawMessage(String message) {
        player.sendMessage(new TextComponentString(message));
    }

    public static void sendMessagePrefix(String message) {
        sendRawMessage(prefix + " " + message);
    }
}
