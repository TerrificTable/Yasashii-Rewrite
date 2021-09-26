package com.terrifictable55.yasashii.command.commands;

import com.terrifictable55.yasashii.command.Command;
import com.terrifictable55.yasashii.utils.ChatUtils;

public class WorldBorder extends Command {
    public WorldBorder()
    {
        super("WorldBorder", new String[]{"wb", "worldborder"});
    }


    @Override
    public void runCommand(String s, String[] args)
    {
        try
        {
            final net.minecraft.world.border.WorldBorder worldBorder = this.mc.world.getWorldBorder();
            ChatUtils.message("World border is at:\nMinX: " + worldBorder.minX() + "\nMinZ: " + worldBorder.minZ() + "\nMaxX: " + worldBorder.maxX() + "\nMaxZ: " + worldBorder.maxZ() + "\n");
        }
        catch(Exception e)
        {
            ChatUtils.error("Usage: " + getSyntax());
        }
    }

    @Override
    public String getDescription()
    {
        return "WorldBorder distance";
    }

    @Override
    public String getSyntax()
    {
        return "WorldBorder ";
    }
}
