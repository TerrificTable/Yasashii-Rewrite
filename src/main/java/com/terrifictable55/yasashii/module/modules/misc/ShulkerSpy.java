package com.terrifictable55.yasashii.module.modules.misc;

import com.terrifictable55.yasashii.manager.Setting;
import com.terrifictable55.yasashii.module.Category;
import com.terrifictable55.yasashii.module.Module;
import com.terrifictable55.yasashii.module.ModuleManager;
import com.terrifictable55.yasashii.module.modules.render.NameTags;
import com.terrifictable55.yasashii.utils.RenderUtil;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemShulkerBox;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityShulkerBox;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ShulkerSpy extends Module {
    public ShulkerSpy() {
        super("Shulkerspy", Category.MISC);
    }

    public static ConcurrentHashMap<String, TileEntityShulkerBox> shulkerMap = new ConcurrentHashMap<>();

    public Setting Mode = rSetting(new Setting(" Mode", this, "Dynamic", "Dynamic", "Static"));
    public Setting X = rSetting(new Setting("X", this, 1, 0, 1000, false, Mode, "Static", 2));
    public Setting Y = rSetting(new Setting("Y", this, 1, 0, 1000, false, Mode, "Static", 3));
    public Setting Scale = rSetting(new Setting("Scale", this, 1, 0, 4, false, Mode, "Dynamic", 2));
    public Setting Background = rSetting(new Setting("Background", this, true, ""));
    public Setting BgColor = rSetting(new Setting("BgColor", this, .22, .88, .22, .22, Background, 2));

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        if (Mode.getValString().equalsIgnoreCase("Dynamic"))
            for (Entity object : mc.world.loadedEntityList) {
                if (object instanceof EntityOtherPlayerMP)
                    if (shulkerMap.containsKey(object.getName().toLowerCase())) {
                        IInventory inv = shulkerMap.get(object.getName().toLowerCase());
                        DrawBox((EntityLivingBase) object, inv);
                    }
            }
    }

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent.Text event) {
        if (Mode.getValString().equalsIgnoreCase("Static")) {
            int Players = 0;
            for (Entity object : mc.world.loadedEntityList) {
                if (object instanceof EntityOtherPlayerMP)
                    if (shulkerMap.containsKey(object.getName().toLowerCase())) {
                        IInventory inv = shulkerMap.get(object.getName().toLowerCase());
                        DrawSbox(inv, Players, (EntityLivingBase) object);
                        Players++;
                    }
            }
        }
    }

    private void DrawSbox(IInventory inv, int players, EntityLivingBase object) {
        RenderHelper.enableGUIStandardItemLighting();
        if (Background.getValBoolean()) {
            RenderUtil.drawRectDouble(X.getValDouble(), Y.getValDouble() - (players * 100), X.getValDouble() + 88 + 60, Y.getValDouble() + 50 - (players * 100), BgColor.getcolor()); // background
        }
        mc.fontRenderer.drawStringWithShadow(object.getName() + "'s Shulker", (float) X.getValDouble() + 45, (float) Y.getValDouble() - 10, -1);

        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack itemStack = inv.getStackInSlot(i);
            int offsetX = (int) (X.getValDouble() + (i % 9) * 16);
            int offsetY = (int) (Y.getValDouble() + (i / 9) * 16) - (players * 100);
            mc.getRenderItem().renderItemAndEffectIntoGUI(itemStack, offsetX, offsetY);
            mc.getRenderItem().renderItemOverlayIntoGUI(mc.fontRenderer, itemStack, offsetX, offsetY, null);
        }
        RenderHelper.disableStandardItemLighting();
        mc.getRenderItem().zLevel = 0.0F;
    }

    public void DrawBox(EntityLivingBase e, IInventory inv) {
        int morey = ModuleManager.getModuleByName("NameTags").isToggled() ? -6 : 0;

        double scale = Math.max(Scale.getValDouble() * (mc.player.getDistance(e) / 20), 2);
        for (int i = 0; i < inv.getSizeInventory(); i++) {
            NameTags.drawItem(e.posX, e.posY + e.height + ((0.75) * scale), e.posZ, -2.5 + ((i % 9)), -((i / 9) * 1.2) - morey, scale, inv.getStackInSlot(i));
        }
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        List<Entity> valids = mc.world.getLoadedEntityList()
                .stream()
                .filter(en -> en instanceof EntityOtherPlayerMP)
                .filter(mp -> ((EntityOtherPlayerMP) mp).getHeldItemMainhand().getItem() instanceof ItemShulkerBox)
                .collect(Collectors.toList());


        for (Entity valid : valids) {
            EntityOtherPlayerMP mp = (EntityOtherPlayerMP) valid;
            TileEntityShulkerBox entityBox = new TileEntityShulkerBox();
            ItemShulkerBox shulker = (ItemShulkerBox) mp.getHeldItemMainhand().getItem();
            //entityBox.blockType = shulker.getBlock();
            entityBox.setWorld(mc.world);
            //ItemStackHelper.loadAllItems(Objects.requireNonNull(mp.getHeldItemMainhand().getTagCompound()).getCompoundTag("BlockEntityTag"), entityBox.items);
            entityBox.readFromNBT(mp.getHeldItemMainhand().getTagCompound().getCompoundTag("BlockEntityTag"));
            entityBox.setCustomName(mp.getHeldItemMainhand().hasDisplayName() ? mp.getHeldItemMainhand().getDisplayName() : mp.getName() + "'s current shulker box");
            shulkerMap.put(mp.getName().toLowerCase(), entityBox);
        }
    }
}
