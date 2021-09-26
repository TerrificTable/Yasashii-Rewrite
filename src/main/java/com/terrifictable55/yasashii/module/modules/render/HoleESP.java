package com.terrifictable55.yasashii.module.modules.render;

import com.terrifictable55.yasashii.manager.Setting;
import com.terrifictable55.yasashii.module.Category;
import com.terrifictable55.yasashii.module.Module;
import com.terrifictable55.yasashii.utils.Executer;
import com.terrifictable55.yasashii.utils.TimerUtils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import java.util.ArrayList;
import java.util.List;

import static com.terrifictable55.yasashii.utils.RenderUtil.RenderBlock;

public class HoleESP extends Module {
    Setting Radius = rSetting(new Setting("Radius", this, 8, 0, 32, true));
    Setting Void = rSetting(new Setting("Void", this, .85, 1, 1, .75));
    Setting Bedrock = rSetting(new Setting("Bedrock", this, .55, 1, 1, .75));
    Setting obby = rSetting(new Setting("obby", this, .22, 1, 1, .75));
    Setting Burrow = rSetting(new Setting("Burrow", this, .4, 1, 1, .75));
    Setting OwnHole = rSetting(new Setting("Ignore Own", this, true));
    Setting Timer = rSetting(new Setting("Timer", this, 250, 0, 500, true));
    Setting Mode = rSetting(new Setting("Hole Mode", this, "Outline", BlockEspOptions()));
    Setting LineWidth = rSetting(new Setting("LineWidth", this, 1, 0, 3, false));
    Setting BurrowDetect = rSetting(new Setting("Burrow Detect", this, true));

    Vec3i playerPos;
    TimerUtils timer = new TimerUtils();
    public final List<Hole> holes = new ArrayList<>();

    public HoleESP() {
        super("HoleEsp", Category.COMBAT);
    }


    @Override
    public void onEnable() {
        Executer.init();
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (timer.isDelay((long) Timer.getValDouble())) {
            if (!Mode.getValString().equalsIgnoreCase("None")) {
                playerPos = new Vec3i(mc.player.posX, mc.player.posY, mc.player.posZ);
                this.holes.clear();
                Executer.execute(() -> {
                            if (BurrowDetect.getValBoolean())
                                for (Entity entity : mc.world.loadedEntityList)
                                    if (entity instanceof EntityPlayer) {
                                        EntityPlayer entityPlayer = (EntityPlayer) entity;
                                        if (isInBurrow(entityPlayer)) {
                                            BlockPos b = new BlockPos(entityPlayer);
                                            this.holes.add(new Hole(b.getX(), b.getY(), b.getZ(), b, Hole.HoleTypes.Burrow, false));
                                        }
                                    }

                            for (int x = (int) (playerPos.getX() - Radius.getValDouble()); x < playerPos.getX() + Radius.getValDouble(); x++) {
                                for (int z = (int) (playerPos.getZ() - Radius.getValDouble()); z < playerPos.getZ() + Radius.getValDouble(); z++) {
                                    for (int y = (playerPos.getY() + 6); y > playerPos.getY() - 6; y--) {

                                        final BlockPos blockPos = new BlockPos(x, y, z);

                                        if (OwnHole.getValBoolean() && mc.player.getDistanceSq(blockPos) <= 1)
                                            continue;


                                        Hole.HoleTypes l_Type = isHoleValid(mc.world.getBlockState(blockPos), blockPos);

                                        if (l_Type != Hole.HoleTypes.None) {
                                            if (l_Type == Hole.HoleTypes.Void) {
                                                this.holes.add(new Hole(blockPos.getX(), blockPos.getY(), blockPos.getZ(), blockPos, Hole.HoleTypes.Void, true));
                                                continue;
                                            }
                                            final IBlockState downBlockState = mc.world.getBlockState(blockPos.down());
                                            if (downBlockState.getBlock() == Blocks.AIR) {
                                                final BlockPos downPos = blockPos.down();
                                                l_Type = isHoleValid(downBlockState, blockPos);
                                                if (l_Type != Hole.HoleTypes.None) {
                                                    this.holes.add(new Hole(downPos.getX(), downPos.getY(), downPos.getZ(), downPos, l_Type, true));
                                                }
                                            } else {
                                                this.holes.add(new Hole(blockPos.getX(), blockPos.getY(), blockPos.getZ(), blockPos, l_Type, false));
                                            }
                                        }
                                    }

                                }
                            }
                        }
                );
                timer.setLastMS();
            }
        }
    }

    private boolean isInBurrow(EntityPlayer entityPlayer) {
        Minecraft mc = Minecraft.getMinecraft();
        BlockPos playerPos = new BlockPos(Math.floor(entityPlayer.posX + .5), entityPlayer.posY, Math.floor(entityPlayer.posZ + .5));

        return mc.world.getBlockState(playerPos).getBlock() == Blocks.OBSIDIAN
                || mc.world.getBlockState(playerPos).getBlock() == Blocks.ENDER_CHEST
                || mc.world.getBlockState(playerPos).getBlock() == Blocks.ANVIL;
    }

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        if (!Mode.getValString().equalsIgnoreCase("None")) {
            for (Hole hole : holes) {
                double renderPosX = hole.getX() - mc.getRenderManager().viewerPosX;
                double renderPosY = hole.getY() - mc.getRenderManager().viewerPosY;
                double renderPosZ = hole.getZ() - mc.getRenderManager().viewerPosZ;

                final AxisAlignedBB bb = new AxisAlignedBB(renderPosX, renderPosY, renderPosZ,
                        renderPosX + 1,
                        renderPosY + (hole.isTall() ? 2 : 1),
                        renderPosZ + 1);

                RenderBlock(Mode.getValString(), bb, hole.GetHoleType() == Hole.HoleTypes.Bedrock ? Bedrock.getcolor() : hole.GetHoleType() == Hole.HoleTypes.Obsidian ? obby.getcolor() : hole.GetHoleType() == Hole.HoleTypes.Burrow ? Burrow.getcolor() : Void.getcolor(), LineWidth.getValDouble());

            }
        }
    }

    public static Hole.HoleTypes isHoleValid(IBlockState blockState, BlockPos blockPos) {
        Minecraft mc = Minecraft.getMinecraft();
        if (blockState.getBlock() != Blocks.AIR)
            return Hole.HoleTypes.None;

        if ((blockState.getBlock() == Blocks.AIR) && blockPos.getY() == 0)
            return Hole.HoleTypes.Void;

        if (mc.world.getBlockState(blockPos.up()).getBlock() != Blocks.AIR)
            return Hole.HoleTypes.None;

        if (mc.world.getBlockState(blockPos.up(2)).getBlock() != Blocks.AIR)
            return Hole.HoleTypes.None;

        if (mc.world.getBlockState(blockPos.down()).getBlock() == Blocks.AIR)

            return Hole.HoleTypes.None;


        final BlockPos[] touchingBlocks = new BlockPos[]
                {blockPos.north(), blockPos.south(), blockPos.east(), blockPos.west()};

        // True is Bedrock false if Obby
        boolean AllBedrock = true;

        for (BlockPos touching : touchingBlocks) {
            final IBlockState touchingState = mc.world.getBlockState(touching);
            if ((touchingState.getBlock() != Blocks.AIR) && touchingState.isFullBlock()) {
                if (touchingState.getBlock() == Blocks.OBSIDIAN) {
                    AllBedrock = false;
                    continue;
                }
                if (touchingState.getBlock() != Blocks.BEDROCK)
                    return Hole.HoleTypes.None;
            } else {
                return Hole.HoleTypes.None;
            }
        }
        return AllBedrock ? Hole.HoleTypes.Bedrock : Hole.HoleTypes.Obsidian;
    }

}

class Hole extends Vec3i {
    private final boolean tall;
    private HoleTypes HoleType;

    public enum HoleTypes {
        None,
        Obsidian,
        Bedrock,
        Void,
        Burrow
    }

    public Hole(int x, int y, int z, final BlockPos pos, HoleTypes p_Type, boolean tall) {
        super(x, y, z);
        this.tall = tall;
        SetHoleType(p_Type);
    }

    public boolean isTall() {
        return tall;
    }


    public HoleTypes GetHoleType() {
        return HoleType;
    }

    public void SetHoleType(HoleTypes holeType) {
        HoleType = holeType;
    }
}
