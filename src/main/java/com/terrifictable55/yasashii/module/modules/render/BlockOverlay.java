package com.terrifictable55.yasashii.module.modules.render;

import com.terrifictable55.yasashii.manager.Setting;
import com.terrifictable55.yasashii.module.Category;
import com.terrifictable55.yasashii.module.Module;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static com.terrifictable55.yasashii.utils.RenderUtil.RenderBlock;
import static com.terrifictable55.yasashii.utils.RenderUtil.Standardbb;

public class BlockOverlay extends Module {
    Setting OverlayColor = rSetting(new Setting("OverlayColor", this, 0, 1, 1, false, ""));
    Setting Mode = rSetting(new Setting("Hole Mode", this, "Outline", ""));
    Setting LineWidth = rSetting(new Setting("LineWidth", this, 1, 0, 3, false, ""));

    public BlockOverlay() {
        super("BlockOverlay", Category.RENDER);
    }

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        if (mc.objectMouseOver == null)
            return;

        if (Block.getIdFromBlock(mc.world.getBlockState(mc.objectMouseOver.getBlockPos()).getBlock()) == 0)
            return;

        if (mc.objectMouseOver.typeOfHit == RayTraceResult.Type.BLOCK) {
            BlockPos blockPos = mc.objectMouseOver.getBlockPos();
            RenderBlock(Mode.getValString(), Standardbb(blockPos), OverlayColor.getColorRgb(), LineWidth.getValDouble());
        }
    }
}
