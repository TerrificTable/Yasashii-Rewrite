package com.terrifictable55.yasashii.module.modules.chat;

import com.terrifictable55.yasashii.module.Category;
import com.terrifictable55.yasashii.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextFormatting;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Scarfchat extends Module {

    private final File dir;
    private final File dataFile;

    public Scarfchat() {

        super("Spammer", Category.MISC);
        dir = new File(Minecraft.getMinecraft().mcDataDir, "Yasashii");
        if (!dir.exists()) {
            dir.mkdir();
        }
        dataFile = new File(dir, "spammer.txt");
        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onEnable() {

        super.onEnable();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(this.dataFile));
            String spam = reader.readLine();
            reader.close();
            String m = "";
            for (int i = 0; i < spam.length(); i++) {
                mc.player.sendChatMessage("hi");
                if (i % 2 == 0) {
                    m += spam.charAt(i);
                    m = m + TextFormatting.BLUE;
                }
            }
            mc.player.sendChatMessage(m);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
