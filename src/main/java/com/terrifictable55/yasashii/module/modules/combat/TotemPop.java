package com.terrifictable55.yasashii.module.modules.combat;

import com.terrifictable55.yasashii.module.Category;
import com.terrifictable55.yasashii.module.Module;
import com.mojang.realmsclient.gui.ChatFormatting;
import com.terrifictable55.yasashii.utils.ChatUtils;
import com.terrifictable55.yasashii.utils.Connection;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import java.util.HashMap;

public class TotemPop extends Module {
    public static HashMap<String, Integer> popList;

    public TotemPop() {
        super("TotemPop", Category.COMBAT);
    }


    @Override
    public boolean onPacket(Object packet, Connection.Side side) {
        if (packet instanceof SPacketEntityStatus) {
            final SPacketEntityStatus packet2 = (SPacketEntityStatus) packet;
            if (packet2.getOpCode() == 35) {
                final Entity entity = packet2.getEntity(mc.world);
                pop(entity);
            }

        }
        return true;
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        for (final EntityPlayer player : mc.world.playerEntities) {
            if (player.getHealth() <= 0.0f && popList.containsKey(player.getName())) {
                ChatUtils.message(ChatFormatting.RED + player.getName() + " died after popping " + ChatFormatting.GREEN + popList.get(player.getName()) + ChatFormatting.RED + " totems!");
                popList.remove(player.getName(), popList.get(player.getName()));
            }
        }
    }

    public void pop(Entity entity) {
        if (mc.player == null || mc.world == null)
            return;

        if (popList == null)
            popList = new HashMap<>();
    }

    private void Check(Entity entity) {
        if (popList.get(entity.getName()) != null) {
            int popCounter = popList.get(entity.getName());
            popList.put(entity.getName(), ++popCounter);
            ChatUtils.message(ChatFormatting.RED + entity.getName() + ChatFormatting.RED + " popped " + ChatFormatting.YELLOW + ++popCounter + ChatFormatting.RED + " totems!");
        }
    }

    public static int getpops(Entity entity) {
        if (popList.get(entity.getName()) != null) {
            return popList.get(entity.getName());
        }
        return 0;
    }
}
