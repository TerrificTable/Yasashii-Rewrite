package com.terrifictable55.yasashii.command.commands;

import com.terrifictable55.yasashii.command.Command;
import com.terrifictable55.yasashii.module.modules.misc.GuiPeek;
import com.terrifictable55.yasashii.module.modules.misc.ShulkerSpy;
import com.terrifictable55.yasashii.utils.ChatUtils;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemShulkerBox;
import net.minecraft.item.ItemStack;

public class Peek extends Command {
    public Peek() {
        super("Peek", new String[]{"peek"});
    }

    public void runCommand(String s, String[] args) {
        try {

            if (args[0] != null) {
                String name = args[0].toLowerCase();
                if (!ShulkerSpy.shulkerMap.containsKey(name.toLowerCase())) {
                    ChatUtils.error("have not seen this player hold a shulkerbox. Check your spelling.");
                    return;
                }
                IInventory inv = ShulkerSpy.shulkerMap.get(name.toLowerCase());
                new Thread(() -> {
                    try {
                        Thread.sleep(100L);
                    } catch (InterruptedException ignored) {
                    }
                    mc.player.displayGUIChest(inv);
                }).start();

            } else {
                if (!(mc.player.getHeldItemMainhand().getItem() instanceof ItemShulkerBox)) {
                    ChatUtils.error("You Have to hold a shulker box");
                }

                ItemStack itemStack = mc.player.getHeldItemMainhand();
                if (itemStack.getItem() instanceof ItemShulkerBox) {
                    ChatUtils.message("Opening your shulker box.");
                    GuiPeek.Peekcode(itemStack, mc);
                }
            }

        } catch (Exception e) {
            ChatUtils.error("Usage: " + getSyntax());
        }
    }

    public String getDescription() {
        return "Peek into shukler!";
    }

    public String getSyntax() {
        return "Peek <Name>";
    }
}
