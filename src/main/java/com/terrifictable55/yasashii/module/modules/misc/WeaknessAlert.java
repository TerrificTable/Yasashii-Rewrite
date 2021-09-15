package com.terrifictable55.yasashii.module.modules.misc;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.terrifictable55.yasashii.manager.MessageManager;
import com.terrifictable55.yasashii.module.Category;
import com.terrifictable55.yasashii.module.Module;
import net.minecraft.init.MobEffects;

public class WeaknessAlert extends Module {
    private boolean hasAnnounced = false;

    public WeaknessAlert() {
        super("WeaknessAlert", Category.MISC);
    }

    public void onUpdate() {
        if (mc.world != null && mc.player != null) {
            if (mc.player.isPotionActive(MobEffects.WEAKNESS) && !hasAnnounced) {
                hasAnnounced = true;
                MessageManager.sendMessagePrefix(ChatFormatting.GRAY + "[" + ChatFormatting.LIGHT_PURPLE + "WeaknessDetect" + ChatFormatting.GRAY + "] " + ChatFormatting.WHITE + "Hey" + ChatFormatting.GRAY + ", " + ChatFormatting.AQUA + mc.getSession().getUsername() + ChatFormatting.GRAY + "," + ChatFormatting.WHITE + " unlucky move mate" + ChatFormatting.GRAY + ", " + ChatFormatting.WHITE + "you now have " + ChatFormatting.RED + "weakness");
            }
            if (!mc.player.isPotionActive(MobEffects.WEAKNESS) && hasAnnounced) {
                hasAnnounced = false;
                MessageManager.sendMessagePrefix(ChatFormatting.GRAY + "[" + ChatFormatting.LIGHT_PURPLE + "WeaknessDetect" + ChatFormatting.GRAY + "] " + ChatFormatting.WHITE + "Phew" + ChatFormatting.GRAY + ", " + ChatFormatting.AQUA + mc.getSession().getUsername() + ChatFormatting.GRAY + "," + ChatFormatting.WHITE + " that was close" + ChatFormatting.GRAY + ", " + ChatFormatting.WHITE + "you no longer have " + ChatFormatting.RED + "weakness");
            }
        }
    }
}
