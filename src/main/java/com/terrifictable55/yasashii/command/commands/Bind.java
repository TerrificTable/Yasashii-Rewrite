package com.terrifictable55.yasashii.command.commands;


import com.mojang.realmsclient.gui.ChatFormatting;
import com.terrifictable55.yasashii.Yasashii;
import com.terrifictable55.yasashii.command.Command;
import com.terrifictable55.yasashii.manager.MessageManager;
import com.terrifictable55.yasashii.module.Module;
import org.lwjgl.input.Keyboard;

public class Bind extends Command {
    public Bind() {
        super("bind", new String[]{"b", "bind"});
    }

    @Override
    public void onCommand(String[] args) {
        if (args.length > 2) {
            try {
                for (Module m : Yasashii.moduleManager.getModules()) {
                    if (m.getName().equalsIgnoreCase(args[1])) {
                        try {
                            m.setKey(Keyboard.getKeyIndex(args[2].toUpperCase()));
                            MessageManager.sendMessagePrefix(ChatFormatting.AQUA + m.getName() + ChatFormatting.WHITE + " is now binded to " + ChatFormatting.RED + args[2].toUpperCase() + ChatFormatting.GRAY + " (" + ChatFormatting.WHITE + Keyboard.getKeyIndex(args[2].toUpperCase() + "") + ChatFormatting.GRAY + ")");
                        } catch (Exception e) {
                            MessageManager.sendMessagePrefix(ChatFormatting.RED + m.getName() + ChatFormatting.WHITE + " Something went wrong :(");

                            e.printStackTrace();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
