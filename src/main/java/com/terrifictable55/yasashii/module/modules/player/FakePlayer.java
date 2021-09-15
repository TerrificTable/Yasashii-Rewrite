package com.terrifictable55.yasashii.module.modules.player;

import com.mojang.authlib.GameProfile;
import com.terrifictable55.yasashii.module.Category;
import com.terrifictable55.yasashii.module.Module;
import net.minecraft.client.entity.EntityOtherPlayerMP;

public class FakePlayer extends Module {
    private EntityOtherPlayerMP _fakePlayer;

    public FakePlayer() {
        super("FakePlayer", Category.PLAYER);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        _fakePlayer = null;

        if (mc.world == null) {
            this.toggle();
            return;
        }
        _fakePlayer = new EntityOtherPlayerMP(mc.world, new GameProfile(mc.player.getUniqueID(), "TerrificTable"));
        mc.world.addEntityToWorld(_fakePlayer.getEntityId(), _fakePlayer);
        _fakePlayer.attemptTeleport(mc.player.posX, mc.player.posY, mc.player.posZ);
    }

    @Override
    public void onDisable() {
        if (!(mc.world == null)) {
            mc.world.removeEntity(_fakePlayer);
        }
    }
}
