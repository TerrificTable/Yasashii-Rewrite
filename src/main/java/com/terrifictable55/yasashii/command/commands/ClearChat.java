package com.terrifictable55.yasashii.command.commands;

import com.terrifictable55.yasashii.command.Command;
import com.terrifictable55.yasashii.utils.ChatUtils;

public class ClearChat extends Command {
    public ClearChat()
    {
        super("Clear",new String[]{"c", "clear"});
    }

    @Override
    public void runCommand(String s, String[] args)
    {
        try
        {
            mc.ingameGUI.getChatGUI().clearChatMessages(true);
            ChatUtils.message("Cleared Chat");
        }
        catch(Exception e)
        {
            ChatUtils.error("Usage: " + getSyntax());
        }
    }


    public String getDescription()
    {
        return "Clears Chat";
    }

    @Override
    public String getSyntax()
    {
        return "Clear";
    }
}
