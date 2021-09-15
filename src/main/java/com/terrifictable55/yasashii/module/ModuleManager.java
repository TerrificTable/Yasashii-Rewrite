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
import com.terrifictable55.yasashii.module.modules.render.*;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {

    public ArrayList<Module> modules;

    public ModuleManager() {
        (modules = new ArrayList<Module>()).clear();
        //Chat
        this.modules.add(new ChatPrefix());
        this.modules.add(new ChatSuffix());
        this.modules.add(new Scarfchat());
        this.modules.add(new TotemPopAnnouncer());

        //Combat
        this.modules.add(new AutoArmor());
        this.modules.add(new AutoTotem());
        this.modules.add(new BedAura());
        this.modules.add(new BowSpam());
        this.modules.add(new CrystalAura());

        //Hud
        this.modules.add(new ArmorHud());
        this.modules.add(new com.terrifictable55.yasashii.module.modules.hud.ArrayList());
        this.modules.add(new ClickGUI());
        this.modules.add(new FPS());
        this.modules.add(new Memory());
        this.modules.add(new Ping());
        this.modules.add(new Watermark());
        this.modules.add(new Welcomer());

        //Misc
        this.modules.add(new AutoSuicide());
        this.modules.add(new DiscordRPC());
        this.modules.add(new EntityAlert());
        this.modules.add(new PortalGodMode());
        this.modules.add(new WeaknessAlert());

        //Movement
        this.modules.add(new HoleTP());
        this.modules.add(new Sonic());
        this.modules.add(new Sprint());
        this.modules.add(new Timer());
        this.modules.add(new VClip());
        this.modules.add(new Velocity());

        //Player
        this.modules.add(new AutoLog());
        this.modules.add(new FakePlayer());

        //Render
        this.modules.add(new BlockVision());
        this.modules.add(new ChunkOverlay());
        // this.modules.add(new CustomCape());
        // this.modules.add(new EntityESP());
        this.modules.add(new Fullbright());
        this.modules.add(new HoleESP());
        // this.modules.add(new ItemESP());

    }

    public ArrayList<Module> getModules() {
        return modules;
    }

    public Module getModuleByName(String name) {
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
