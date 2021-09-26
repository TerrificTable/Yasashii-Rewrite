package com.terrifictable55.yasashii.module.modules.player;

import com.terrifictable55.yasashii.manager.Setting;
import com.terrifictable55.yasashii.module.Category;
import com.terrifictable55.yasashii.module.Module;
import com.terrifictable55.yasashii.utils.Connection;
import com.terrifictable55.yasashii.utils.proxy.EntityRenderMixin;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.server.SPacketTimeUpdate;
import net.minecraft.potion.Potion;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.util.Objects;

public class NoEffect extends Module {
    Setting hurtcam = rSetting( new Setting("hurtcam", this, false));
    Setting Levitate = rSetting( new Setting("Levitate", this, false));
    Setting weather = rSetting( new Setting("weather", this, false));
    Setting Time = rSetting( new Setting("Time", this, 0, 0, 18000, true));
    Setting Settime = rSetting( new Setting("Settime", this, false));
    Setting fire = rSetting( new Setting("fire", this, false));
    Setting push = rSetting( new Setting("push", this, false));
    Setting NoVoid = rSetting( new Setting("NoVoid", this, false));

    static public Setting NoScreenEvents;      //DONE

    public NoEffect() {
        super("NoEffect", Category.PLAYER);
    }

    @Override
    public void setup() {
        rSetting(NoScreenEvents = new Setting("NoScreenEvents", this, false));
    }

    @Override
    public boolean onPacket(Object packet, Connection.Side side) {
        return !(packet instanceof SPacketTimeUpdate) || !Settime.getValBoolean();
    }


    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {

        EntityRenderMixin.Camswitch = !hurtcam.getValBoolean();

        if (weather.getValBoolean()) {
            mc.world.getWorldInfo().setRaining(false);
            mc.world.setRainStrength(0.0f);
            mc.world.getWorldInfo().setThunderTime(0);
            mc.world.getWorldInfo().setThundering(false);
        }
        if (push.getValBoolean())
            mc.player.entityCollisionReduction = 1.0F;

        if (Levitate.getValBoolean())
            if (mc.player.isPotionActive(Objects.requireNonNull(Potion.getPotionById(25))))
                mc.player.removeActivePotionEffect(Potion.getPotionById(25));

        if (NoVoid.getValBoolean()) {
            if (mc.player.posY <= 0.5D) {
                mc.player.moveVertical = 10.0F;
                mc.player.jump();
            }
            mc.player.motionY += .1;
        }

    }

    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        if (mc.player.isBurning() && fire.getValBoolean()) {
            mc.player.connection.sendPacket(new CPacketPlayer(mc.player.onGround));
            event.getEntityLiving().extinguish();
            mc.player.setFire(0);

        }
        if (Settime.getValBoolean()) {
            mc.world.setWorldTime((long) Time.getValDouble());
        }
    }
}
