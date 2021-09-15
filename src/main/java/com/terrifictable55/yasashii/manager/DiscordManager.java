package com.terrifictable55.yasashii.manager;

import com.terrifictable55.yasashii.miscs.discordhelper.DiscordEventHandlers;
import com.terrifictable55.yasashii.miscs.discordhelper.DiscordRPC;
import com.terrifictable55.yasashii.miscs.discordhelper.DiscordRichPresence;
import net.minecraft.client.Minecraft;

public class DiscordManager {
    public static final Minecraft mc = Minecraft.getMinecraft();
    public static String details;
    public static String state;
    public static DiscordRichPresence rpc;

    public static void startup() {
        System.out.println("Starting Discord Rpc");
        DiscordRPC lib = DiscordRPC.INSTANCE;
        String applicationId = "719038221807386665";
        String steamId = "";
        DiscordEventHandlers handlers = new DiscordEventHandlers();
        handlers.ready = (user) -> System.out.println("Ready!");
        lib.Discord_Initialize(applicationId, handlers, true, steamId);
        DiscordRichPresence presence = new DiscordRichPresence();
        presence.startTimestamp = System.currentTimeMillis() / 1000; // epoch second
        lib.Discord_UpdatePresence(presence);
        presence.largeImageKey = "image";
        presence.largeImageText = "https://discord.gg/salhack Join The SalHack Development Discord!";
        new Thread(() ->
        {
            while (!Thread.currentThread().isInterrupted()) {
                lib.Discord_RunCallbacks();
                lib.Discord_UpdatePresence(presence);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ignored) {
                }
            }
        }, "RPC-Callback-Handler").start();
    }

    public static void shutdown() {
        System.out.println("[BloodHack] Discord RPC Shutting Down!");
        DiscordRPC.INSTANCE.Discord_Shutdown();
    }
}
