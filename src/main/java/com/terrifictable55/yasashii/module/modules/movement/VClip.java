package com.terrifictable55.yasashii.module.modules.movement;

import com.terrifictable55.yasashii.manager.Setting;
import com.terrifictable55.yasashii.module.Category;
import com.terrifictable55.yasashii.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


@SideOnly(Side.CLIENT)
public class VClip extends Module {
    Setting y;
    public VClip() {
        super("vclip", Category.MOVEMENT);
    }

    @Override
    public void setup() {
        rSetting(y = new Setting("Y", this, 0, 0, 10, true, "y"));
    }

    @SubscribeEvent
    public void toggle() {
        int height = (int) y.getValDouble();
        Minecraft mc = Minecraft.getMinecraft();
        double weight = height;
        Entity target = mc.player.isRiding() ? mc.player.getRidingEntity() : mc.player;
        target.setPosition(target.posX, target.posY + weight, target.posZ);
    }
}
