package com.terrifictable55.yasashii.module.modules.misc;

import com.terrifictable55.yasashii.manager.Setting;
import com.terrifictable55.yasashii.module.Category;
import com.terrifictable55.yasashii.module.Module;
import com.terrifictable55.yasashii.utils.Connection;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Objects;

public class AntiCrash extends Module {
    public AntiCrash() {
        super("AntiCrash", Category.MISC);
    }

    Setting slime = rSetting(new Setting("slime", this, true));
    Setting offhand = rSetting(new Setting("offhand", this, true));
    Setting Sound = rSetting(new Setting("Sound", this, true));

    @Override
    public boolean onPacket(Object packet, Connection.Side side) {
        if (side == Connection.Side.IN && packet instanceof SPacketSoundEffect && offhand.getValBoolean()) {
            return ((SPacketSoundEffect) packet).getSound() != SoundEvents.ITEM_ARMOR_EQUIP_GENERIC;
        }
        if (packet instanceof SPacketSoundEffect && Sound.getValBoolean()) {
            final SPacketSoundEffect packet2 = (SPacketSoundEffect) packet;
            return packet2.getCategory() != SoundCategory.PLAYERS || packet2.getSound() != SoundEvents.ITEM_ARMOR_EQUIP_GENERIC;
        }
        return true;
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (Objects.nonNull(mc.world) && slime.getValBoolean()) {
            mc.world.loadedEntityList.forEach(e -> {
                if (e instanceof EntitySlime) {
                    EntitySlime slime = (EntitySlime) e;
                    if (slime.getSlimeSize() > 4) {
                        mc.world.removeEntity(e);
                    }
                }
            });
        }
    }
}
