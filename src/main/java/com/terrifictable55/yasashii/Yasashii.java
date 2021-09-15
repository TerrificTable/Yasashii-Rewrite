package com.terrifictable55.yasashii;

import com.terrifictable55.yasashii.capes.CapeUtil;
import com.terrifictable55.yasashii.command.CommandManager;
import com.terrifictable55.yasashii.manager.ConfigManager;
import com.terrifictable55.yasashii.manager.SettingManager;
import com.terrifictable55.yasashii.module.Module;
import com.terrifictable55.yasashii.module.ModuleManager;
import com.terrifictable55.yasashii.util.Refrence;
import com.terrifictable55.yasashii.util.font.CFontRenderer;
import me.zero.alpine.bus.EventBus;
import me.zero.alpine.bus.EventManager;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

@Mod(modid = Refrence.MOD_ID, name = Refrence.NAME, version = Refrence.VERSION)
public class Yasashii {
    public static final String name = Refrence.NAME;
    public static final String version = Refrence.VERSION;
    public static String prefix = ">";
    public static ConfigManager configManager;
    public static ModuleManager moduleManager;
    public static SettingManager settingManager;
    public static final EventBus EVENT_BUS = new EventManager();
    protected Minecraft mc2;
    public static CFontRenderer fontRenderer;
    @Mod.Instance
    private static Yasashii INSTANCE;

    public Yasashii() {
        INSTANCE = this;
    }

    public static Yasashii getInstance() {
        return INSTANCE;
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Display.setTitle(name + " " + version);
    }

    @Mod.EventHandler
    public void Init(FMLInitializationEvent event) throws IllegalAccessException {
        settingManager = new SettingManager();
        moduleManager = new ModuleManager();
        configManager = new ConfigManager();
        CommandManager.init();
        MinecraftForge.EVENT_BUS.register(new CommandManager());
        MinecraftForge.EVENT_BUS.register(this);
        // CapeUtil.startAnimationLoop();
    }

    @SubscribeEvent
    public void onKeyPress(InputEvent.KeyInputEvent event) {
        for (Module m: moduleManager.getModules()) {
            if (Keyboard.isKeyDown(m.getKey())) {
                m.toggle();
            }
        }
    }
}
