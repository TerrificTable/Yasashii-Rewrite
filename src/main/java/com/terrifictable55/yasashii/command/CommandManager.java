package com.terrifictable55.yasashii.command;

import com.terrifictable55.yasashii.Yasashii;
import com.terrifictable55.yasashii.command.commands.Bind;
import com.terrifictable55.yasashii.command.commands.Help;
import com.terrifictable55.yasashii.command.commands.Prefix;
import com.terrifictable55.yasashii.command.commands.Toggle;
import com.terrifictable55.yasashii.utils.ChatUtils;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.HashSet;

public class CommandManager {
    public static HashSet<Command> commands = new HashSet<>();
    public static ArrayList<Command> command = new ArrayList<Command>();
    private volatile static CommandManager instance;

    public static char cmdPrefix = '@';

    public static void init() {
        commands.clear();
        commands.add(new Bind());
        commands.add(new Help());
        commands.add(new Prefix());
        commands.add(new Toggle());
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void chatEvent(ClientChatEvent event) {
        String[] args = event.getMessage().split(" ");
        if (event.getMessage().startsWith(Yasashii.prefix)) {
            event.setCanceled(true);
            for (Command c : commands) {
                if (args[0].equalsIgnoreCase(Yasashii.prefix + c.getCommand())) {
                    c.onCommand(args);
                }
            }
        }
    }
    public void runCommands(String s) {
        String readString = s.trim().substring(Character.toString(cmdPrefix).length()).trim();
        boolean commandResolved = false;
        boolean hasArgs = readString.trim().contains(" ");
        String commandName = hasArgs ? readString.split(" ")[0] : readString.trim();
        String[] args = hasArgs ? readString.substring(commandName.length()).trim().split(" ") : new String[0];

        for (Command command : commands) {
            if (command.getCommand().trim().equalsIgnoreCase(commandName.trim())) {
                command.runCommand(readString, args);
                commandResolved = true;
                break;
            }
        }

        if (!commandResolved) {
            ChatUtils.error("Cannot resolve internal command: \u00a7c" + commandName);
        }
    }


    public static CommandManager getInstance() {
        if (instance == null)
            instance = new CommandManager();
        return instance;
    }
}
