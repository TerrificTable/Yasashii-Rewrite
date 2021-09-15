package com.terrifictable55.yasashii.module.modules.combat;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.terrifictable55.yasashii.manager.Setting;
import com.terrifictable55.yasashii.manager.friends.Friends;
import com.terrifictable55.yasashii.module.Category;
import com.terrifictable55.yasashii.module.Module;
import com.terrifictable55.yasashii.util.Messages;
import com.terrifictable55.yasashii.utils.ColourUtils;
import com.terrifictable55.yasashii.utils.HackTessellator;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemBed;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.tileentity.TileEntityBed;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Comparator;
import java.util.List;

public class BedAura extends Module {
    private int playerHotbarSlot = -1;
    private int lastHotbarSlot = -1;
    private EntityPlayer closestTarget;
    private String lastTickTargetName;
    private int bedSlot = -1;
    private BlockPos placeTarget;
    private float rotVar;
    private int blocksPlaced;
    private double diffXZ;
    private boolean firstRun;
    private boolean nowTop = false;
    Setting range;
    Setting placedelay;
    Setting announceUsage;
    Setting placeesp;

    public BedAura() {
        super("BedAura", Category.COMBAT);
    }

    @Override
    public void setup() {
        rSetting(range = new Setting("Range", this, 7, 0, 9, true, "range"));
        rSetting(placedelay = new Setting("Place Delay", this, 15, 8, 20, true, "placedelay"));
        rSetting(announceUsage = new Setting("Announce Usage", this, true, "announceUsage"));
        rSetting(placeesp = new Setting("Place ESP", this, true, "placeesp"));
    }

    public static boolean isLiving(Entity e) {
        return e instanceof EntityLivingBase;
    }

    @Override
    public void onEnable() {
        if (mc.player == null) {
            this.toggle();
            return;
        }

        MinecraftForge.EVENT_BUS.register(this);
        firstRun = true;

        blocksPlaced = 0;

        playerHotbarSlot = mc.player.inventory.currentItem;
        lastHotbarSlot = -1;


    }

    @Override
    public void onDisable() {

        if (mc.player == null) {
            return;
        }

        MinecraftForge.EVENT_BUS.unregister(this);

        if (lastHotbarSlot != playerHotbarSlot && playerHotbarSlot != -1) {
            mc.player.inventory.currentItem = playerHotbarSlot;
        }

        playerHotbarSlot = -1;
        lastHotbarSlot = -1;

        if (announceUsage.getValBoolean()) {
            Messages.sendMessagePrefix(TextFormatting.BLUE + "[" + TextFormatting.GOLD + "BedAura" + TextFormatting.BLUE + "]" + ChatFormatting.RED + " Disabled" + ChatFormatting.RESET + "!");
        }

        blocksPlaced = 0;


    }

    @Override
    public void onUpdate() {

        if (mc.player == null) {
            return;
        }
        if (mc.player.dimension == 0) {
            Messages.sendMessagePrefix("You are in the overworld!");
            this.toggle();
        }
        try {
            findClosestTarget();
        } catch (NullPointerException npe) {
        }
        if (closestTarget == null && mc.player.dimension != 0) {
            if (firstRun) {
                firstRun = false;
                if (announceUsage.getValBoolean()) {
                    Messages.sendMessagePrefix(TextFormatting.BLUE + "[" + TextFormatting.GOLD + "BedAura" + TextFormatting.BLUE + "]" + TextFormatting.WHITE + " enabled, " + TextFormatting.WHITE + "waiting for target.");
                }
            }
        }

        if (firstRun && closestTarget != null && mc.player.dimension != 0) {
            firstRun = false;
            lastTickTargetName = closestTarget.getName();
            if (announceUsage.getValBoolean()) {
                Messages.sendMessagePrefix(TextFormatting.BLUE + "[" + TextFormatting.GOLD + "BedAura" + TextFormatting.BLUE + "]" + TextFormatting.WHITE + " enabled" + TextFormatting.WHITE + ", target: " + ChatFormatting.BLUE + lastTickTargetName);
            }
        }

        if (closestTarget != null && lastTickTargetName != null) {
            if (!lastTickTargetName.equals(closestTarget.getName())) {
                lastTickTargetName = closestTarget.getName();
                if (announceUsage.getValBoolean()) {
                    Messages.sendMessagePrefix(TextFormatting.BLUE + "[" + TextFormatting.GOLD + "BedAura" + TextFormatting.BLUE + "]" + TextFormatting.WHITE + " New target: " + ChatFormatting.BLUE + lastTickTargetName);
                }
            }
        }

        try {
            diffXZ = mc.player.getPositionVector().distanceTo(closestTarget.getPositionVector());
        } catch (NullPointerException npe) {

        }

        try {
            if (closestTarget != null) {
                //placeTarget = new BlockPos(closestTarget.getPositionVector().add(1,1,0));
                nowTop = false;
                rotVar = 90;

                BlockPos block1 = placeTarget;
                if (!canPlaceBed(block1)) {
                    //placeTarget = new BlockPos(closestTarget.getPositionVector().add(-1, 1, 0));
                    rotVar = -90;
                    nowTop = false;
                }
                BlockPos block2 = placeTarget;
                if (!canPlaceBed(block2)) {
                    //placeTarget = new BlockPos(closestTarget.getPositionVector().add(0, 1, 1));
                    rotVar = 180;
                    nowTop = false;
                }
                BlockPos block3 = placeTarget;
                if (!canPlaceBed(block3)) {
                    //placeTarget = new BlockPos(closestTarget.getPositionVector().add(0, 1, -1));
                    rotVar = 0;
                    nowTop = false;
                }
                BlockPos block4 = placeTarget;
                if (!canPlaceBed(block4)) {
                    //placeTarget = new BlockPos(closestTarget.getPositionVector().add(0, 2, -1));
                    rotVar = 0;
                    nowTop = true;
                }
                BlockPos blockt1 = placeTarget;
                if (nowTop && !canPlaceBed(blockt1)) {
                    //placeTarget = new BlockPos(closestTarget.getPositionVector().add(-1, 2, 0));
                    rotVar = -90;
                }
                BlockPos blockt2 = placeTarget;
                if (nowTop && !canPlaceBed(blockt2)) {
                    //placeTarget = new BlockPos(closestTarget.getPositionVector().add(0, 2, 1));
                    rotVar = 180;
                }
                BlockPos blockt3 = placeTarget;
                if (nowTop && !canPlaceBed(blockt3)) {
                    //placeTarget = new BlockPos(closestTarget.getPositionVector().add(1, 2, 0));
                    rotVar = 90;
                }
            }

            mc.world.loadedTileEntityList.stream()
                    .filter(e -> e instanceof TileEntityBed)
                    .filter(e -> mc.player.getDistance(e.getPos().getX(), e.getPos().getY(), e.getPos().getZ()) <= range.getValDouble())
                    .sorted(Comparator.comparing(e -> mc.player.getDistance(e.getPos().getX(), e.getPos().getY(), e.getPos().getZ())))
                    .forEach(bed -> {
                        if (mc.player.dimension != 0) {
                            mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(bed.getPos(), EnumFacing.UP, EnumHand.OFF_HAND, 0, 0, 0));
                        }
                    });

            if ((mc.player.ticksExisted % placedelay.getValDouble() == 0) && closestTarget != null) {
                this.findBeds();

                mc.player.ticksExisted++;
                this.doDaMagic();
            }


        } catch (NullPointerException npe) {
            npe.printStackTrace();
        }
    }

    private void doDaMagic() {
        if (diffXZ <= range.getValDouble()) {
            for (int i = 0; i < 9; i++) {
                if (bedSlot != -1) {
                    break;
                }
                ItemStack stack = mc.player.inventory.getStackInSlot(i);
                if (stack.getItem() instanceof ItemBed) {
                    bedSlot = i;
                    if (i != -1) {
                        mc.player.inventory.currentItem = bedSlot;
                    }
                    break;
                }
            }
            bedSlot = -1;
            if (blocksPlaced == 0 && mc.player.inventory.getStackInSlot(mc.player.inventory.currentItem).getItem() instanceof ItemBed) {
                mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SNEAKING));
                mc.player.connection.sendPacket(new CPacketPlayer.Rotation(rotVar, 0, mc.player.onGround));
                placeBlock(new BlockPos(this.placeTarget), EnumFacing.DOWN);
                mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                blocksPlaced = 1;
                nowTop = false;
            }

            blocksPlaced = 0;
        }
    }

    private void findBeds() {
        if (mc.currentScreen == null || !(mc.currentScreen instanceof GuiContainer)) {
            if (mc.player.inventory.getStackInSlot(0).getItem() != Items.BED) {
                for (int i = 9; i < 36; ++i) {
                    if (mc.player.inventory.getStackInSlot(i).getItem() == Items.BED) {
                        mc.playerController.windowClick(mc.player.inventoryContainer.windowId, i, 0, ClickType.SWAP, mc.player);
                        break;
                    }

                }

            }
        }
    }

    private boolean canPlaceBed(BlockPos pos) {
        return (mc.world.getBlockState(pos).getBlock() == Blocks.AIR || mc.world.getBlockState(pos).getBlock() == Blocks.BED) &&
                mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(pos)).isEmpty();

    }

    private void findClosestTarget() {
        List<EntityPlayer> playerList = mc.world.playerEntities;
        closestTarget = null;
        for (EntityPlayer target : playerList) {
            if (target == mc.player) {
                continue;
            }
            if (Friends.isFriend(target.getName())) {
                continue;
            }
            if (!isLiving(target)) {
                continue;
            }
            if ((target).getHealth() <= 0) {
                continue;
            }
            if (closestTarget == null) {
                closestTarget = target;
                continue;
            }
            if (mc.player.getDistance(target) < mc.player.getDistance(closestTarget)) {
                closestTarget = target;
            }
        }
    }

    private void placeBlock(BlockPos pos, EnumFacing side) {
        BlockPos neighbour = pos.offset(side);
        EnumFacing opposite = side.getOpposite();
        Vec3d hitVec = new Vec3d(neighbour).add(new Vec3d(opposite.getDirectionVec()).scale(0.5));
        mc.playerController.processRightClickBlock(mc.player, mc.world, neighbour, opposite, hitVec, EnumHand.MAIN_HAND);
    }

    @SubscribeEvent
    public void render(RenderWorldLastEvent event) {
        if (placeTarget == null || mc.world == null || closestTarget == null) return;
        if (placeesp.getValBoolean()) {
            try {
                float posx = placeTarget.getX();
                float posy = placeTarget.getY();
                float posz = placeTarget.getZ();
                HackTessellator.prepare("lines");
                HackTessellator.draw_cube_line(posx, posy, posz, ColourUtils.genRainbow(), "all");
                if (rotVar == 90) {
                    HackTessellator.draw_cube_line(posx - 1, posy, posz, ColourUtils.genRainbow(), "all");
                }
                if (rotVar == 0) {
                    HackTessellator.draw_cube_line(posx, posy, posz + 1, ColourUtils.genRainbow(), "all");
                }
                if (rotVar == -90) {
                    HackTessellator.draw_cube_line(posx + 1, posy, posz, ColourUtils.genRainbow(), "all");
                }
                if (rotVar == 180) {
                    HackTessellator.draw_cube_line(posx, posy, posz - 1, ColourUtils.genRainbow(), "all");
                }
            } catch (Exception ignored) {
            }
            HackTessellator.release();
        }
    }
}
