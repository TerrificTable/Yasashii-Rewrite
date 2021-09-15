package com.terrifictable55.yasashii.command;

public class Command {
    private final String command;
    private final String[] usage;

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
}
