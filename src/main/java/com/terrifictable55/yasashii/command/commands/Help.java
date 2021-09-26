package com.terrifictable55.yasashii.command.commands;

import com.terrifictable55.yasashii.Yasashii;
import com.terrifictable55.yasashii.command.Command;
import com.terrifictable55.yasashii.command.CommandManager;
import com.terrifictable55.yasashii.manager.MessageManager;
import com.terrifictable55.yasashii.utils.ChatUtils;

public class Help extends Command {
    public Help()
    {
        super("help", new String[]{"help"});
    }

    @Override
    public void runCommand(String s, String[] args)
    {
        for(Command cmd: CommandManager.commands)
        {
            if(cmd != this) {
                ChatUtils.message(cmd.getSyntax().replace("<", "<\2479").replace(">", "\2477>") + " - " + cmd.getDescription());
            }
        }
    }

    public String getDescription()
    {
        return "Lists all commands.";
    }

    @Override
    public String getSyntax()
    {
        return "help";
    }
}
