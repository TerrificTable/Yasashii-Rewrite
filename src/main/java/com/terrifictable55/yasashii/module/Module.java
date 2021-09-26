package com.terrifictable55.yasashii.module;

import com.terrifictable55.yasashii.Yasashii;
import com.terrifictable55.yasashii.manager.Setting;
import com.terrifictable55.yasashii.utils.Connection;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public class Module {
    protected Minecraft mc = Minecraft.getMinecraft();


    private String name, displayName;
    private Category category;
    private boolean toggled;
    private Integer key;
    protected int tickDelay;
    boolean drawn;

    public Module(String name, Category category) {
        this.name = name;
        this.category = category;
        toggled = false;
        key = Keyboard.KEY_NONE;
        this.drawn = true;
        setup();
    }


    public void registerSettings() {
        selfSettings();
    }

    public void onEnable() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void onDisable() {
        MinecraftForge.EVENT_BUS.unregister(this);
    }

    public boolean onPacket(Object packet, Connection.Side side) {
        return true;
    }

    public boolean onDisablePacket(Object packet, Connection.Side side) {
        return true;
    }

    @SubscribeEvent
    public void gameTickEvent(TickEvent event) {
        if (this.isToggled()) {
            onUpdate();
        }
    }

    public void onUpdate() {
    }

    public void selfSettings() {
    }

    public Setting rSetting(Setting setting) {
        Yasashii.settingManager.rSetting(setting);
        return setting;
    }

    public void onToggle() {
    }

    public void toggle() {
        toggled = !toggled;
        onToggle();
        if (toggled) {
            onEnable();
        } else {
            onDisable();
        }
    }

    public ArrayList<String> BlockEspOptions() {
        ArrayList<String> BlockOptions = new ArrayList<>();
        BlockOptions.add("Outline");
        BlockOptions.add("Full");
        BlockOptions.add("Flat");
        BlockOptions.add("FlatOutline");
        BlockOptions.add("Beacon");
        BlockOptions.add("Xspot");
        BlockOptions.add("Tracer");
        BlockOptions.add("Shape");
        BlockOptions.add("None");
        return BlockOptions;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public boolean isToggled() {
        return toggled;
    }

    public String getDisplayName() {
        return displayName == null ? name : displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setDrawn(boolean d) {
        this.drawn = d;
    }

    public void setup() {
    }
}
