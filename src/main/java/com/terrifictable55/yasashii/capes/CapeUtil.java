package com.terrifictable55.yasashii.capes;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;

import javax.imageio.ImageIO;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.UUID;

public class CapeUtil {
    final static ArrayList<String> final_uuid_list = get_uuids();
    static ArrayList<ResourceLocation> capeTexts = new ArrayList<>();
    static Iterator<ResourceLocation> iterator;
    public static ResourceLocation capeTexture;
    static int currentCape = 0;
    private static Thread thread;

    static {
        try {
            capeTexts.add(Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation("yasashii/capes", new DynamicTexture(ImageIO.read(new URL("https://i.imgur.com/W1T3gFG.png")))));
            capeTexts.add(Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation("yasashii/capes", new DynamicTexture(ImageIO.read(new URL("https://i.imgur.com/P0plzkt.png")))));
            capeTexts.add(Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation("yasashii/capes", new DynamicTexture(ImageIO.read(new URL("https://i.imgur.com/iAEjyYs.png")))));
        }catch (Exception e){}
        iterator = capeTexts.iterator();
    }

    public static ArrayList<String> get_uuids() {
        try {
            URL url = new URL("https://pastebin.com/zZUyjUHq");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            final ArrayList<String> uuid_list = new ArrayList<>();

            String s;

            while ((s = reader.readLine()) != null) {
                uuid_list.add(s);
            }

            return uuid_list;
        } catch (Exception ignored) {
            return null;
        }
    }

    public static void startAnimationLoop(){
        thread = new Thread(()-> {
            while (true){
                nextCapeTex();
                try {
                    Thread.sleep(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    public static void stopAnimationLoop(){
        thread.stop();
    }

    private static void nextCapeTex(){
        currentCape++;
        if(currentCape == capeTexts.size())
            currentCape = 0;
        capeTexture = capeTexts.get(currentCape);
    }

    public static boolean is_uuid_valid(UUID uuid) {
        for (String u : Objects.requireNonNull(final_uuid_list)) {
            if (u.equals(uuid.toString())) {
                return true;
            }
        }
        return false;
    }

    public static void setCape(DynamicTexture texture){
        capeTexture = Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation("yasashii/capes", texture);
    }

    public static ResourceLocation getCape(){
        return capeTexture;
    }
}
