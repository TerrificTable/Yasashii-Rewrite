package com.terrifictable55.yasashii.module.modules.combat;

import com.terrifictable55.yasashii.module.Category;
import com.terrifictable55.yasashii.module.Module;
import net.minecraft.item.ItemBow;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.math.BlockPos;

public class BowSpam extends Module {
    public BowSpam() {
        super("BowSpam", Category.COMBAT);
    }

    @Override
    public void onUpdate() {
        if (mc.player != null && mc.world != null) {
            if (mc.player.getHeldItemMainhand().getItem() instanceof ItemBow
                    && mc.player.isHandActive()
                    && mc.player.getItemInUseMaxCount() >= 3) {
                mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM,
                        BlockPos.ORIGIN, mc.player.getHorizontalFacing()));

                mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(mc.player.getActiveHand()));
                mc.player.stopActiveHand();
            }
        }
    }
}
