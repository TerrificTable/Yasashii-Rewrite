package com.terrifictable55.yasashii.command.commands;

import com.terrifictable55.yasashii.Yasashii;
import com.terrifictable55.yasashii.command.Command;
import com.terrifictable55.yasashii.manager.MessageManager;
import com.terrifictable55.yasashii.module.Module;
import net.minecraft.util.text.TextFormatting;

public class Toggle extends Command {
    public Toggle() {
        super("Toggle", new String[]{"t", "toggle"});
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public String getSyntax() {
        return "toggle <Module>";
    }

    @Override
    public void onCommand(String[] args) {
        if (args.length > 1) {
            try {
                for (Module m : Yasashii.moduleManager.getModules()) {
                    if (m.getName().equalsIgnoreCase(args[1])) {
                        m.toggle();
                        if (m.isToggled()) {
                            MessageManager.sendMessagePrefix(TextFormatting.AQUA + m.getName() + TextFormatting.WHITE + " is now " + TextFormatting.GREEN + "ON");
                        } else {
                            MessageManager.sendMessagePrefix(TextFormatting.AQUA + m.getName() + TextFormatting.WHITE + " is now " + TextFormatting.RED + "OFF");
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
