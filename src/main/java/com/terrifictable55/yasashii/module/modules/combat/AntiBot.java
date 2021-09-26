package com.terrifictable55.yasashii.module.modules.combat;

import com.mojang.authlib.GameProfile;
import com.terrifictable55.yasashii.manager.Setting;
import com.terrifictable55.yasashii.module.Category;
import com.terrifictable55.yasashii.module.Module;
import com.terrifictable55.yasashii.utils.Connection;
import com.terrifictable55.yasashii.utils.Utils;
import com.terrifictable55.yasashii.utils.Wrapper;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.server.SPacketSpawnPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class AntiBot extends Module {
    public static ArrayList<EntityBot> bots = new ArrayList<>();

    public Setting level = rSetting(new Setting("level", this, 0, 0, 6, false));
    public Setting tick = rSetting(new Setting("tick", this, 0, 0, 999, true));

    public Setting ifInAir = rSetting(new Setting("InAir", this, false));
    public Setting ifGround = rSetting(new Setting("OnGround", this, false));
    public Setting ifZeroHealth = rSetting(new Setting("ZeroHealth", this, false));
    public Setting ifInvisible = rSetting(new Setting("Invisible", this, false));
    public Setting ifEntityId = rSetting(new Setting("EntityId", this, false));
    public Setting ifTabName = rSetting(new Setting("OutTabName", this, false));
    public Setting ifPing = rSetting(new Setting("PingCheck", this, false));
    public Setting remove = rSetting(new Setting("RemoveBots", this, false));
    public Setting gwen = rSetting(new Setting("Gwen", this, false));

    public AntiBot() {
        super("AntiBot", Category.COMBAT);
    }

    @Override
    public void onEnable() {
        bots.clear();
        super.onEnable();
    }


    @Override
    public boolean onPacket(Object packet, Connection.Side side) {
        if (gwen.getValBoolean()) {
            for (Object entity : mc.world.loadedEntityList) {
                if (packet instanceof SPacketSpawnPlayer) {
                    SPacketSpawnPlayer spawn = (SPacketSpawnPlayer) packet;
                    double posX = spawn.getX() / 32.0D;
                    double posY = spawn.getY() / 32.0D;
                    double posZ = spawn.getZ() / 32.0D;

                    double difX = mc.player.posX - posX;
                    double difY = mc.player.posY - posY;
                    double difZ = mc.player.posZ - posZ;

                    double dist = Math.sqrt(difX * difX + difY * difY + difZ * difZ);
                    if ((dist <= 17.0D) && (posX != mc.player.posX) && (posY != mc.player.posY) && (posZ != mc.player.posZ)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @SubscribeEvent
    public void onClientTick(ClientTickEvent event) {
        if (tick.getValDouble() > 0.0) {
            bots.clear();
        }
        for (Object object : mc.world.loadedEntityList) {
            if (object instanceof EntityLivingBase) {
                EntityLivingBase entity = (EntityLivingBase) object;
                if (!(entity instanceof EntityPlayerSP) && entity instanceof EntityPlayer) {
                    EntityPlayer bot = (EntityPlayer) entity;
                    if (!isBotBase(bot)) {
                        int ailevel = (int) level.getValDouble();
                        boolean isAi = ailevel > 0.0;
                        if (isAi && botPercentage(bot) > ailevel) {
                            addBot(bot);
                        } else if (!isAi && botCondition(bot)) {
                            addBot(bot);
                        }
                    } else {
                        addBot(bot);
                        if (remove.getValBoolean()) {
                            mc.world.removeEntity(bot);
                        }
                    }
                }
            }
        }
    }

    void addBot(EntityPlayer player) {
        if (!isBot(player)) {
            bots.add(new EntityBot(player));
        }
    }

    public static boolean isBot(EntityPlayer player) {
        for (EntityBot bot : bots) {
            if (bot.getName().equals((player.getName()))) {
                if (player.isInvisible() != bot.isInvisible()) {
                    return player.isInvisible();
                }
                return true;
            } else {
                if (bot.getId() == player.getEntityId()
                        || bot.getUuid().equals(player.getGameProfile().getId())) {
                    return true;
                }
            }
        }
        return false;
    }

    boolean botCondition(EntityPlayer bot) {
        if (tick.getValDouble() > 0.0 && bot.ticksExisted < tick.getValDouble()) {
            return true;
        }
        if (ifInAir.getValBoolean()
                && bot.isInvisible()
                && bot.motionY == 0.0
                && bot.posY > mc.player.posY + 1.0
                && Utils.isBlockMaterial(new BlockPos(bot).down(), Blocks.AIR)) {
            return true;
        }
        if (ifGround.getValBoolean()
                && bot.motionY == 0.0
                && !bot.collidedVertically
                && bot.onGround
                && bot.posY % 1.0 != 0.0
                && bot.posY % 0.5 != 0.0) {
            return true;
        }
        if (ifZeroHealth.getValBoolean() && bot.getHealth() <= 0) {
            return true;
        }
        if (ifInvisible.getValBoolean() && bot.isInvisible()) {
            return true;
        }
        if (ifEntityId.getValBoolean() && bot.getEntityId() >= 1000000000) {
            return true;
        }
        if (ifTabName.getValBoolean()) {
            boolean isTabName = false;
            for (NetworkPlayerInfo npi : Objects.requireNonNull(Wrapper.INSTANCE.mc().getConnection()).getPlayerInfoMap()) {
                npi.getGameProfile();
                if (npi.getGameProfile().getName().contains(bot.getName())) {
                    isTabName = true;
                }
            }
            return !isTabName;
        }
        return false;
    }

    int botPercentage(EntityPlayer bot) {
        int percentage = 0;
        if (tick.getValDouble() > 0.0 && bot.ticksExisted < tick.getValDouble()) {
            percentage++;
        }
        if (ifInAir.getValBoolean()
                && bot.isInvisible()
                && bot.posY > mc.player.posY + 1.0
                && Utils.isBlockMaterial(new BlockPos(bot).down(), Blocks.AIR)) {
            percentage++;
        }
        if (ifGround.getValBoolean()
                && bot.motionY == 0.0
                && !bot.collidedVertically
                && bot.onGround
                && bot.posY % 1.0 != 0.0
                && bot.posY % 0.5 != 0.0) {
            percentage++;
        }
        if (ifZeroHealth.getValBoolean() && bot.getHealth() <= 0) {
            percentage++;
        }
        if (ifInvisible.getValBoolean() && bot.isInvisible()) {
            percentage++;
        }
        if (ifEntityId.getValBoolean() && bot.getEntityId() >= 1000000000) {
            percentage++;
        }
        if (ifTabName.getValBoolean()) {
            boolean isTabName = false;
            for (NetworkPlayerInfo npi : Objects.requireNonNull(Wrapper.INSTANCE.mc().getConnection()).getPlayerInfoMap()) {
                npi.getGameProfile();
                if (npi.getGameProfile().getName().contains(bot.getName())) {
                    isTabName = true;
                }
            }
            if (!isTabName) {
                percentage++;
            }
        }
        return percentage;
    }

    boolean isBotBase(EntityPlayer bot) {
        if (isBot(bot)) {
            return true;
        }
        bot.getGameProfile();
        GameProfile botProfile = bot.getGameProfile();
        bot.getUniqueID();
        if (botProfile.getName() == null) {
            return true;
        }
        String botName = botProfile.getName();
        return botName.contains("Body #") || botName.contains("NPC")
                || botName.equalsIgnoreCase(Utils.getEntityNameColor(bot));
    }

    public static class EntityBot {

        private final String name;
        private final int id;
        private final UUID uuid;
        private final boolean invisible;

        public EntityBot(EntityPlayer player) {
            this.name = String.valueOf(player.getGameProfile().getName());
            this.id = player.getEntityId();
            this.uuid = player.getGameProfile().getId();
            this.invisible = player.isInvisible();
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public UUID getUuid() {
            return uuid;
        }

        public boolean isInvisible() {
            return invisible;
        }

    }
}
