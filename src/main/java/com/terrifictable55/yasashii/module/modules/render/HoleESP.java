package com.terrifictable55.yasashii.module.modules.render;

import com.terrifictable55.yasashii.event.events.RenderWorldEvent;
import com.terrifictable55.yasashii.manager.Setting;
import com.terrifictable55.yasashii.module.Category;
import com.terrifictable55.yasashii.module.Module;
import com.terrifictable55.yasashii.utils.CrystalUtil;
import com.terrifictable55.yasashii.utils.RenderUtil;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

import java.awt.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class HoleESP extends Module {
    Setting rangeS, r, g, b, a;
    private final BlockPos[] surroundOffset = {
            new BlockPos(0, -1, 0), // down
            new BlockPos(0, 0, -1), // north
            new BlockPos(1, 0, 0), // east
            new BlockPos(0, 0, 1), // south
            new BlockPos(-1, 0, 0) // west
    };

    public HoleESP() {
        super("HoleESP", Category.RENDER);
    }

    @Override
    public void setup() {
        rSetting(rangeS = new Setting("Range", this, 7, 0, 9, true, "range"));
        rSetting(r = new Setting("Red", this, 255, 0, 255, true, "r"));
        rSetting(g = new Setting("Green", this, 255, 0, 255, true, "g"));
        rSetting(b = new Setting("Blue", this, 255, 0, 255, true, "b"));
        rSetting(a = new Setting("Aplha", this, 255, 0, 255, true, "a"));
    }
    private final ConcurrentHashMap<BlockPos, Boolean> safeHoles = new ConcurrentHashMap<>();
    //@Override
    @EventHandler
    Listener<RenderWorldEvent> eventListener = new Listener<>(e -> {
        if (mc.player == null || safeHoles == null) {
            return;
        }
        try {
            if (safeHoles.isEmpty()) {
                return;
            }
            for (BlockPos pos : safeHoles.keySet()) {
                float rf = (float) r.getValDouble();
                float gf = (float) g.getValDouble();
                float bf = (float) b.getValDouble();
                float af = (float) a.getValDouble();
                RenderUtil.drawBoundingBox(new AxisAlignedBB(pos), 2, rf, gf, bf, af);
            }
        } catch (Exception ignored) {

        }
    });

    @Override
    public void onUpdate() {
        if (mc.player == null && mc.world == null) return;
        try {
            int range = (int) Math.ceil(rangeS.getValDouble());

            List<BlockPos> blockPosList = CrystalUtil.getSphere(getPlayerPos(), range, range, false, true, 0);

            for (BlockPos pos : blockPosList) {

                // block gotta be air
                if (!mc.world.getBlockState(pos).getBlock().equals(Blocks.AIR)) {
                    continue;
                }

                // block 1 above gotta be air
                if (!mc.world.getBlockState(pos.add(0, 1, 0)).getBlock().equals(Blocks.AIR)) {
                    continue;
                }

                // block 2 above gotta be air
                if (!mc.world.getBlockState(pos.add(0, 2, 0)).getBlock().equals(Blocks.AIR)) {
                    continue;
                }

                boolean isSafe = true;
                boolean isBedrock = true;

                for (BlockPos offset : surroundOffset) {
                    Block block = mc.world.getBlockState(pos.add(offset)).getBlock();
                    if (block != Blocks.BEDROCK) {
                        isBedrock = false;
                    }
                    if (block != Blocks.BEDROCK && block != Blocks.OBSIDIAN && block != Blocks.ENDER_CHEST && block != Blocks.ANVIL) {
                        isSafe = false;
                        break;
                    }
                }

                if (isSafe) {
                    safeHoles.put(pos, isBedrock);
                }
            }
        } catch (Exception bruh) {
        }
    }

    public BlockPos getPlayerPos() {
        return mc.player.getPosition();
    }
}
