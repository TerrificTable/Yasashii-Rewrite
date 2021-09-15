package com.terrifictable55.yasashii.module.modules.player;


import com.terrifictable55.yasashii.manager.Setting;
import com.terrifictable55.yasashii.module.Category;
import com.terrifictable55.yasashii.module.Module;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Set;

public class AutoLog extends Module {
    Setting h;

    public AutoLog() {
        super("Auto Log", Category.PLAYER);
    }

    @Override
    public void setup() {
        rSetting(h = new Setting("Health", this, 15, 1, 20, true, "h"));
    }

    @SubscribeEvent
    public void onTick(TickEvent.RenderTickEvent e) {
        float health = (float) h.getValDouble();

        if (mc.player.getHealth() < health) {
            mc.world.sendQuittingDisconnectingPacket();
            mc.loadWorld(null);
            mc.displayGuiScreen(new GuiMainMenu());
            toggle();
        }
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}