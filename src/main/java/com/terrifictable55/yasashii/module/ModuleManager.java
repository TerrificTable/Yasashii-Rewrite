package com.terrifictable55.yasashii.module;

import com.terrifictable55.yasashii.Yasashii;
import com.terrifictable55.yasashii.module.modules.chat.ChatPrefix;
import com.terrifictable55.yasashii.module.modules.chat.ChatSuffix;
import com.terrifictable55.yasashii.module.modules.chat.Scarfchat;
import com.terrifictable55.yasashii.module.modules.chat.TotemPopAnnouncer;
import com.terrifictable55.yasashii.module.modules.combat.*;
import com.terrifictable55.yasashii.module.modules.hud.*;
import com.terrifictable55.yasashii.module.modules.misc.*;
import com.terrifictable55.yasashii.module.modules.movement.*;
import com.terrifictable55.yasashii.module.modules.player.AutoLog;
import com.terrifictable55.yasashii.module.modules.player.FakePlayer;
import com.terrifictable55.yasashii.module.modules.player.NoEffect;
import com.terrifictable55.yasashii.module.modules.render.*;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {

    public static ArrayList<Module> modules = new ArrayList<>();
    public static ArrayList<Module> toggledModules = new ArrayList<>();

    public ModuleManager() {
        (modules = new ArrayList<Module>()).clear();
        //Chat
        modules.add(new ChatPrefix());
        modules.add(new ChatSuffix());
        modules.add(new Scarfchat());
        //modules.add(new TotemPopAnnouncer());

        //Combat
        modules.add(new AntiBot());
        modules.add(new AutoArmor());
        modules.add(new AutoTotem());
        modules.add(new BedAura());
        modules.add(new BowSpam());
        modules.add(new CrystalAura());
        modules.add(new TotemPop());

        //Hud
        modules.add(new ArmorHud());
        modules.add(new com.terrifictable55.yasashii.module.modules.hud.ArrayList());
        modules.add(new ClickGUI());
        modules.add(new FPS());
        modules.add(new Memory());
        modules.add(new Ping());
        modules.add(new Watermark());
        modules.add(new Welcomer());

        //Misc
        modules.add(new AntiCheat());
        modules.add(new AntiCheat());
        modules.add(new AntiHurt());
        modules.add(new AutoSuicide());
        modules.add(new CoordsFinder());
        modules.add(new DiscordRPC());
        modules.add(new EntityAlert());
        modules.add(new GuiPeek());
        modules.add(new ModSettings());
        modules.add(new PluginGetter());
        modules.add(new PortalGodMode());
        modules.add(new ShulkerSpy());
        modules.add(new WeaknessAlert());

        //Movement
        modules.add(new HoleTP());
        modules.add(new Sonic());
        modules.add(new Sprint());
        modules.add(new Timer());
        modules.add(new VClip());
        modules.add(new Velocity());

        //Player
        modules.add(new AutoLog());
        modules.add(new FakePlayer());
        modules.add(new NoEffect());

        //Render
        modules.add(new BlockOverlay());
        modules.add(new BlockVision());
        modules.add(new ChunkOverlay());
        // this.modules.add(new CustomCape());
        // this.modules.add(new EntityESP());
        modules.add(new Fullbright());
        modules.add(new HoleESP());
        // this.modules.add(new ItemESP());
        modules.add(new NameTags());

    }

    public ArrayList<Module> getModules() {
        return modules;
    }

    public static ArrayList<Module> getEnabledmodules() {
        return toggledModules;
    }

    public static Module getModuleByName(String name) {
        return modules.stream().filter(module -> module.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
    public static List<Module> getModulesByCategory(Category c){
        List<Module> modules = new ArrayList<Module>();

        for(Module m : Yasashii.moduleManager.modules) {
            if(m.getCategory() == c)
                modules.add(m);
        }
        return modules;
    }
}
