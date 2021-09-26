package com.terrifictable55.yasashii.command.commands;

import com.terrifictable55.yasashii.command.Command;
import com.terrifictable55.yasashii.manager.MessageManager;

import static com.terrifictable55.yasashii.Yasashii.prefix;

public class Prefix extends Command {
    public Prefix() {
        super("Prefix", new String[]{"p", "prefix"});
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public String getSyntax() {
        return "prefix <prefix>";
    }

    @Override
    public void onCommand(String[] args) {
        if (args.length > 1) {
            try {
                prefix = args[1];
                MessageManager.sendMessagePrefix("New Prefix: " + prefix);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

