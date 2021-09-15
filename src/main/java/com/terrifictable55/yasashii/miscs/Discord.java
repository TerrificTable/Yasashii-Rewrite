package com.terrifictable55.yasashii.miscs;

import com.terrifictable55.yasashii.miscs.discordhelper.DiscordEventHandlers;
import com.terrifictable55.yasashii.miscs.discordhelper.DiscordRPC;
import com.terrifictable55.yasashii.miscs.discordhelper.DiscordRichPresence;


public class Discord {
    public static String discordID = "886513253197447208";
    public static DiscordRichPresence discordRichP = new DiscordRichPresence();

    public static DiscordRPC discordRPC = DiscordRPC.INSTANCE;


    public static void startRPC() {
        DiscordEventHandlers eventHandlers = new DiscordEventHandlers();
        eventHandlers.disconnected = ((var1, var2) -> System.out.println("Discord RPC disconnected, var1:" + var1 + ", var2: " + var2));


        discordRPC.Discord_Initialize(discordID, eventHandlers, true, null);

        discordRichP.startTimestamp = System.currentTimeMillis() / 1000L;
        discordRichP.details = "Developed by TerrificTable55";
        discordRichP.largeImageKey = "logo";
        discordRichP.largeImageText = "Yasashii Client";
        discordRichP.state = null;
        discordRPC.Discord_UpdatePresence(discordRichP);
    }

    public static void stopRPC() {
        discordRPC.Discord_Shutdown();
        discordRPC.Discord_ClearPresence();
    }
}
