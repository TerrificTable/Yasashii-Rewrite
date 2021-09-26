package com.terrifictable55.yasashii.command;

import net.minecraft.client.Minecraft;

public abstract class Command {
    protected static Minecraft mc = Minecraft.getMinecraft();
    private final String command;
    private final String[] usage;
    public abstract String getDescription();
    public abstract String getSyntax();

    public Command(String name, String[] usage) {
        this.command = name;
        this.usage = usage;
    }

    public static void sendClientMessage(String s) {
    }

    public void onCommand(String[] args) {
    }

    public String getCommand() {
        return command;
    }

    public String[] getUsage() {
        return usage;
    }

    public void runCommand(String s, String[] args) {

    }
}
