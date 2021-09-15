package com.terrifictable55.yasashii.command.commands;

import com.terrifictable55.yasashii.Yasashii;
import com.terrifictable55.yasashii.command.Command;
import com.terrifictable55.yasashii.manager.MessageManager;

public class Help extends Command {
    public Help() {
        super("Help", new String[]{"h", "help"});
    }

    @Override
    public void onCommand(String[] args) {
        MessageManager.sendMessagePrefix("Prefix == " + Yasashii.prefix);
        MessageManager.sendMessagePrefix("  ");
        MessageManager.sendMessagePrefix("Bind <Module> <KeyBind> -> bind a module to the given key");
        MessageManager.sendMessagePrefix("Prefix <prefix> -> Change Prefix (doesnt work)");
        MessageManager.sendMessagePrefix("Help -> This here");
        MessageManager.sendMessagePrefix("Toggle <Module> -> Show toggled state of module");
    }
}
