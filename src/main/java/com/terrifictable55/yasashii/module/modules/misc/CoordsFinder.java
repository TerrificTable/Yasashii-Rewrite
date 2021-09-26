package com.terrifictable55.yasashii.module.modules.misc;

import com.terrifictable55.yasashii.manager.Setting;
import com.terrifictable55.yasashii.module.Category;
import com.terrifictable55.yasashii.module.Module;
import com.terrifictable55.yasashii.utils.ChatUtils;
import com.terrifictable55.yasashii.utils.Connection;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.play.server.SPacketEffect;
import net.minecraft.network.play.server.SPacketEntityTeleport;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.util.math.BlockPos;

public class CoordsFinder extends Module {
    public CoordsFinder() {
        super("CoordsFinder", Category.MISC);
    }

    Setting BossDetector = rSetting(new Setting("Boss detector", this, true));
    Setting logLightning = rSetting(new Setting("logLightning", this, true));
    Setting minLightningDist = rSetting(new Setting("minLightningDist", this, 32, 0, 100, true, logLightning, 3));
    Setting logWolf = rSetting(new Setting("logWolf", this, true));
    Setting minWolfDist = rSetting(new Setting("minWolfDist", this, 256, 0, 1024, true, logWolf, 3));
    Setting logPlayer = rSetting(new Setting("logPlayer", this, true));
    Setting minPlayerDist = rSetting(new Setting("minPlayerDist", this, 256, 0, 1024, true, logPlayer, 3));

    private boolean pastDistance(EntityPlayer player, BlockPos pos, double dist) {
        return player.getDistanceSqToCenter(pos) >= Math.pow(dist, 2);
    }


    @Override
    public boolean onPacket(Object packet, Connection.Side side) {
        if (BossDetector.getValBoolean()) {
            if (packet instanceof SPacketEffect) {
                final SPacketEffect sPacketEffect = (SPacketEffect) packet;
                BlockPos pos = new BlockPos(sPacketEffect.getSoundPos().getX(), sPacketEffect.getSoundPos().getY(), sPacketEffect.getSoundPos().getZ());

                switch (sPacketEffect.getSoundType()) {
                    case 1023: {
                        ChatUtils.message("Wither Spawned " + pos.getX() + " Y:" + pos.getY() + " Z:" + pos.getZ());
                        break;
                    }
                    case 1028: {
                        ChatUtils.message("Ender Dragon Defeated " + pos.getX() + " Y:" + pos.getY() + " Z:" + pos.getZ());
                        break;
                    }
                    case 1038: {
                        ChatUtils.message("End Portal Activated " + pos.getX() + " Y:" + pos.getY() + " Z:" + pos.getZ());
                        break;
                    }
                }
            }
        }

        if (logLightning.getValBoolean() && packet instanceof SPacketSoundEffect) {
            SPacketSoundEffect packet2 = (SPacketSoundEffect) packet;

            if (packet2.getSound() != SoundEvents.ENTITY_LIGHTNING_THUNDER) {
                return true;
            }

            BlockPos pos = new BlockPos(packet2.getX(), packet2.getY(), packet2.getZ());

            if (pastDistance(mc.player, pos, minLightningDist.getValDouble())) {
                ChatUtils.warning("Lightning strike At X:" + pos.getX() + " Y:" + pos.getY() + " Z:" + pos.getZ());
            }
        } else if (packet instanceof SPacketEntityTeleport) {
            SPacketEntityTeleport sPacketEntityTeleport = (SPacketEntityTeleport) packet;
            Entity teleporting = mc.world.getEntityByID(sPacketEntityTeleport.getEntityId());
            BlockPos pos = new BlockPos(sPacketEntityTeleport.getX(), sPacketEntityTeleport.getY(), sPacketEntityTeleport.getZ());

            if (logWolf.getValBoolean() && teleporting instanceof EntityWolf) {
                if (pastDistance(mc.player, pos, minWolfDist.getValDouble())) {
                    ChatUtils.warning("Wolf Teleport At X:" + pos.getX() + " Y:" + pos.getY() + " Z:" + pos.getZ());
                }
            } else if (logPlayer.getValBoolean() && teleporting instanceof EntityPlayer) {
                if (pastDistance(mc.player, pos, minPlayerDist.getValDouble())) {
                    ChatUtils.warning(teleporting.getName() + " Teleported to X:" + pos.getX() + " Y:" + pos.getY() + " Z:" + pos.getZ());
                }
            }
        }
        return true;
    }
}
