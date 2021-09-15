package com.terrifictable55.yasashii.module.modules.movement;

import com.terrifictable55.yasashii.manager.Setting;
import com.terrifictable55.yasashii.module.Category;
import com.terrifictable55.yasashii.module.Module;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Velocity extends Module {
    Setting h, v;
    public Velocity() {
        super("Velocity", Category.COMBAT);
    }

    @Override
    public void setup() {
        rSetting(h = new Setting("Horizontal", this, 0, 0, 100, true, "h"));
        rSetting(v = new Setting("Vertical", this, 0, 0, 100, true, "v"));
    }

    @SubscribeEvent
    public void onLivingUpdate(LivingUpdateEvent e) {
        if (mc.player == null) {
            return;
        }
        float horizontal = (float) h.getValDouble();
        float vertical = (float) v.getValDouble();

        if (mc.player.hurtTime == mc.player.maxHurtTime && mc.player.maxHurtTime > 0) {
            mc.player.motionX *= horizontal / 100;
            mc.player.motionY *= vertical / 100;
            mc.player.motionZ *= horizontal / 100;
        }
    }
}
