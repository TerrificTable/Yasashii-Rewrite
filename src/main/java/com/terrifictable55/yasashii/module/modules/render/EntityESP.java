package com.terrifictable55.yasashii.module.modules.render;

import com.terrifictable55.yasashii.manager.Setting;
import com.terrifictable55.yasashii.module.Category;
import com.terrifictable55.yasashii.module.Module;
import com.terrifictable55.yasashii.utils.RenderUtil;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class EntityESP extends Module {
    Setting r, g, b, a;
    public EntityESP() {
        super("EntityESP", Category.RENDER);
    }

    @Override
    public void setup() {
        rSetting(r = new Setting("Red", this, 0xcc, 0, 255, true, ""));
        rSetting(g = new Setting("Green", this, 0xcc, 0, 255, true, ""));
        rSetting(b = new Setting("Blue", this, 0xcc, 0, 255, true, ""));
        rSetting(a = new Setting("Alpha", this, 0xcc, 0, 255, true, ""));
    }

    @SubscribeEvent
    public void onTick(TickEvent event) {
        float rf = (float) r.getValDouble();
        float gf = (float) g.getValDouble();
        float bf = (float) b.getValDouble();
        float af = (float) a.getValDouble();
        AxisAlignedBB bb = mc.player.getRenderBoundingBox();
        RenderUtil.drawBoundingBox(bb, 15f, rf, gf, bf, af);
    }
}
