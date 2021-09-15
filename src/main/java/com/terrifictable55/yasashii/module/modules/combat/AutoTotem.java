package com.terrifictable55.yasashii.module.modules.combat;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.terrifictable55.yasashii.manager.Setting;
import com.terrifictable55.yasashii.module.Category;
import com.terrifictable55.yasashii.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class AutoTotem extends Module {
    public int totems;
    Setting count;
    @Override
    public void setup() {
        rSetting(count = new Setting("Render Count", this, false, "count"));
    }
    public AutoTotem() {
        super("Auto Totem", Category.COMBAT);
    }

    @SubscribeEvent
    public void onTick(TickEvent.RenderTickEvent e) {

        totems = mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.TOTEM_OF_UNDYING).mapToInt(ItemStack::getCount).sum();

        if (mc.player.getItemStackFromSlot(EntityEquipmentSlot.OFFHAND).getItem() == Items.TOTEM_OF_UNDYING) return;

        final int slot = this.getItemSlot();

        if (slot != -1) {
            mc.playerController.windowClick(mc.player.inventoryContainer.windowId, slot, 0, ClickType.PICKUP, mc.player);
            mc.playerController.windowClick(mc.player.inventoryContainer.windowId, 45, 0, ClickType.PICKUP, mc.player);
            mc.playerController.windowClick(mc.player.inventoryContainer.windowId, slot, 0, ClickType.PICKUP, mc.player);
            mc.playerController.updateController();
        }
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    private int getItemSlot() {
        for (int i = 0; i < 36; i++) {
            final Item item = mc.player.inventory.getStackInSlot(i).getItem();
            if (item == Items.TOTEM_OF_UNDYING) {
                if (i < 9) {
                    i += 36;
                }
                return i;
            }
        }
        return -1;
    }

    private int getTotemCount() {
        Item item = mc.player.getHeldItemOffhand().getItem();
        if (item == Items.TOTEM_OF_UNDYING) {
            return totems + 1;
        } else {
            return totems;
        }
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent event) {
        if (count.getValBoolean()) {
            FontRenderer fr = Minecraft.getMinecraft().fontRenderer;
            ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());

            if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
                int y = sr.getScaledHeight() - 15;

                fr.drawString(getTotemCount() + " Totems", sr.getScaledWidth() - fr.getStringWidth(String.valueOf(getTotemCount())) - 100, y, 0x814CCC);
                y += fr.FONT_HEIGHT;
            }
        }
    }

    public String getArraylistInfo() {
        return ChatFormatting.GRAY + " " + getTotemCount();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
