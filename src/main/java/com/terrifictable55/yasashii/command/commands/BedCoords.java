package com.terrifictable55.yasashii.command.commands;

import com.terrifictable55.yasashii.command.Command;
import com.terrifictable55.yasashii.utils.ChatUtils;

public class BedCoords extends Command {
    public BedCoords()
    {
        super("BedCoords", new String[]{"bd", "bedcoords"});
    }



    @Override
    public void runCommand(String s, String[] args)
    {
        try
        {
            ChatUtils.message(mc.player.getBedLocation().toString());
        }
        catch(Exception e)
        {
            ChatUtils.error("Usage: " + getSyntax());
        }
    }

    @Override
    public String getDescription()
    {
        return "BedCoords";
    }

    @Override
    public String getSyntax()
    {
        return "BedCoords ";
    }
}
