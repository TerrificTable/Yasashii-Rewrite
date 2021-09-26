package com.terrifictable55.yasashii.command.commands;

import com.terrifictable55.yasashii.command.Command;
import com.terrifictable55.yasashii.utils.ChatUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.network.play.client.CPacketCreativeInventoryAction;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.Objects;

public class Nbt extends Command {
    public Nbt() {
        super("Nbt", new String[]{"nbt"});
    }

    @Override
    public void runCommand(String s, String[] args) {
        try {
            if (args.length < 1) {
                ChatUtils.error("Invalid syntax.");
                return;
            }
            ItemStack stack = mc.player.inventory.getCurrentItem();
            if (stack.isEmpty()) {
                ChatUtils.error("You must hold an item in your hand.");
                return;
            }
            switch (args[0]) {
                case "add": {
                    if (!mc.player.isCreative()) {
                        ChatUtils.warning("You must be in creative mode.");
                    }
                    if (args.length < 2) {
                        ChatUtils.error("No NBT data provided.");
                        return;
                    }
                    StringBuilder nbt = new StringBuilder(args[1]);
                    for (int i = 2; i < args.length; i++) {
                        nbt.append(" ").append(args[i]);
                    }
                    nbt = new StringBuilder(nbt.toString().replace('&', '\247').replace("\247\247", "&"));
                    try {
                        if (!stack.hasTagCompound()) {
                            stack.setTagCompound(JsonToNBT.getTagFromJson(nbt.toString()));
                        } else {
                            assert stack.getTagCompound() != null;
                            stack.getTagCompound().merge(JsonToNBT.getTagFromJson(nbt.toString()));
                        }
                        updateSlot(36 + mc.player.inventory.currentItem, stack);
                        ChatUtils.message("Item modified.");
                    } catch (NBTException e) {
                        ChatUtils.error("Data tag parsing failed: " + e.getMessage());
                    }
                    break;
                }
                case "set": {
                    if (!mc.player.isCreative()) {
                        ChatUtils.warning("You must be in creative mode.");
                    }
                    if (args.length < 2) {
                        ChatUtils.error("No NBT data provided.");
                        return;
                    }
                    StringBuilder nbt = new StringBuilder(args[1]);
                    for (int i = 2; i < args.length; i++) {
                        nbt.append(" ").append(args[i]);
                    }
                    nbt = new StringBuilder(nbt.toString().replace('&', '\247').replace("\247\247", "&"));
                    try {
                        stack.setTagCompound(JsonToNBT.getTagFromJson(nbt.toString()));
                    } catch (NBTException e) {
                        ChatUtils.error("Data tag parsing failed: " + e.getMessage());
                        return;
                    }
                    updateSlot(36 + mc.player.inventory.currentItem, stack);
                    ChatUtils.message("Item modified.");
                    break;
                }
                case "remove":
                    if (!mc.player.isCreative()) {
                        ChatUtils.warning("You must be in creative mode.");
                    }
                    if (args.length < 2) {
                        ChatUtils.error("No NBT tag specified.");
                        return;
                    }
                    if (args.length > 2) {
                        ChatUtils.warning("Too many arguments.");
                    }
                    String tag = args[1];
                    if (!stack.hasTagCompound() || !Objects.requireNonNull(stack.getTagCompound()).hasKey(tag)) {
                        ChatUtils.error("Item has no NBT tag with name \2477" + args[1] + "\247c.");
                        return;
                    }
                    /**
                    stack.getTagCompound().removeTag(tag);
                    if (stack.getTagCompound().isEmpty()) {
                        stack.setTagCompound(null);
                    } */
                    updateSlot(36 + mc.player.inventory.currentItem, stack);
                    ChatUtils.message("Item modified.");
                    break;
                case "clear":
                    if (!mc.player.isCreative()) {
                        ChatUtils.warning("You must be in creative mode.");
                    }
                    if (args.length > 1) {
                        ChatUtils.warning("Too many arguments.");
                    }
                    if (!stack.hasTagCompound()) {
                        ChatUtils.error("Item has no NBT data.");
                        return;
                    }
                    stack.setTagCompound(null);
                    updateSlot(36 + mc.player.inventory.currentItem, stack);
                    ChatUtils.message("Cleared item's NBT data.");

                    break;
                case "copy":
                    if (args.length > 1) {
                        ChatUtils.warning("Too many arguments.");
                    }
                    if (!stack.hasTagCompound()) {
                        ChatUtils.error("Item has no NBT data.");
                        return;
                    }
                    assert stack.getTagCompound() != null;
                    StringSelection selection = new StringSelection(stack.getTagCompound().toString());
                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    clipboard.setContents(selection, selection);
                    ChatUtils.message("Copied item's NBT data to clipboard.");
                    break;
            }
        } catch (Exception e) {
            ChatUtils.error("Usage: " + getSyntax());
        }
    }

    public static void updateSlot(int slot, ItemStack stack) {
        Objects.requireNonNull(mc.getConnection()).sendPacket(new CPacketCreativeInventoryAction(slot, stack));
    }

    @Override
    public String getDescription() {
        return "Modifies held item's NBT data.";
    }

    @Override
    public String getSyntax() {
        return "nbt <add <dataTag>|set <dataTag>|remove <tagName>|clear|copy>";
    }
}
