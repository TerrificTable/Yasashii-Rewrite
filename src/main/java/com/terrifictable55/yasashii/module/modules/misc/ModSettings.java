package com.terrifictable55.yasashii.module.modules.misc;

import com.terrifictable55.yasashii.manager.Setting;
import com.terrifictable55.yasashii.module.Category;
import com.terrifictable55.yasashii.module.Module;

public class ModSettings extends Module {
    public static Setting Spherelines;
    public static Setting SphereDist;
    public static Setting Rendernonsee;
    public static Setting ShowErrors;
    public static Setting GuiSpeed;

    public ModSettings() {
        super("ModSettings", Category.MISC);
        rSetting(Spherelines = new Setting("Shapelines", this, 10, 0, 20, true, ""));
        rSetting(SphereDist = new Setting("ShapeDist", this, 10, 0, 20, true, ""));
        rSetting(GuiSpeed = new Setting("GuiSpeed", this, 20, 0, 50, true, ""));
        rSetting(Rendernonsee = new Setting("Unseen Render", this, false, ""));
        rSetting(ShowErrors = new Setting("ShowErrors", this, false, ""));
    }
}
