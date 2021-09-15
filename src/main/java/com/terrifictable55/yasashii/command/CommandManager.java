package com.terrifictable55.yasashii.command;

import com.terrifictable55.yasashii.Yasashii;
import com.terrifictable55.yasashii.command.commands.Bind;
import com.terrifictable55.yasashii.command.commands.Help;
import com.terrifictable55.yasashii.command.commands.Prefix;
import com.terrifictable55.yasashii.command.commands.Toggle;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashSet;

public class CommandManager {
    public static HashSet<Command> commands = new HashSet<>();

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
}
