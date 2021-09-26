package com.terrifictable55.yasashii.module.modules.misc;

import com.terrifictable55.yasashii.manager.MessageManager;
import com.terrifictable55.yasashii.module.Category;
import com.terrifictable55.yasashii.module.Module;
import com.terrifictable55.yasashii.utils.Connection;
import com.terrifictable55.yasashii.utils.Wrapper;
import net.minecraft.network.play.client.CPacketTabComplete;
import joptsimple.internal.Strings;
import net.minecraft.network.play.server.SPacketTabComplete;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PluginGetter extends Module {
    public PluginGetter() {
        super("PluginsGetter", Category.MISC);
    }

    @Override
    public void onEnable() {
        if (mc.player == null) {
            return;
        }
        Wrapper.INSTANCE.sendPacket(new CPacketTabComplete("/", null, false));
        super.onEnable();
    }


    public boolean onPacket(Object packet, Connection.Side side) {
        if (packet instanceof SPacketTabComplete) {
            SPacketTabComplete s3APacketTabComplete = (SPacketTabComplete) packet;

            List<String> plugins = new ArrayList<>();
            String[] commands = s3APacketTabComplete.getMatches();

            for (String s : commands) {
                String[] command = s.split(":");

                if (command.length > 1) {
                    String pluginName = command[0].replace("/", "");

                    if (!plugins.contains(pluginName)) {
                        plugins.add(pluginName);
                    }
                }
            }

            Collections.sort(plugins);

            if (!plugins.isEmpty()) {
                MessageManager.sendMessagePrefix("Plugins \u00a77(\u00a78" + plugins.size() + "\u00a77): \u00a79" + Strings.join(plugins.toArray(new String[0]), "\u00a77, \u00a79"));
            } else {
                MessageManager.sendMessagePrefix("No plugins found.");
            }
            this.toggle();
        }
        return true;
    }
}
