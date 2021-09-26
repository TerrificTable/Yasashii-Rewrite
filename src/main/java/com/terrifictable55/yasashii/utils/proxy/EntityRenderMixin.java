package com.terrifictable55.yasashii.utils.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.resources.IResourceManager;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EntityRenderMixin extends EntityRenderer {
    public static boolean Camswitch = true;

    public EntityRenderMixin(Minecraft mcIn, IResourceManager resourceManagerIn) {
        super(mcIn, resourceManagerIn);
    }


    @SubscribeEvent
    public void hurtCameraEffect(float partialTicks) {
        if (Camswitch) {
            ///super.hurtCameraEffect(partialTicks);
        }
    }
}
