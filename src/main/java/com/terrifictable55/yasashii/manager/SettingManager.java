package com.terrifictable55.yasashii.manager;

import com.terrifictable55.yasashii.module.Module;

import java.util.ArrayList;

public class SettingManager {
    private ArrayList<Setting> settings;

    public SettingManager() {
        this.settings = new ArrayList<>();
    }

    public void rSetting(Setting in) {
        this.settings.add(in);
    }

    public ArrayList<Setting> getSettings() {
        return this.settings;
    }

    public ArrayList<Setting> getSettingsByMod(Module mod) {
        ArrayList<Setting> out = new ArrayList<>();
        for (Setting s : getSettings()) {
            if (s.getParentMod().equals(mod)) {
                out.add(s);
            }
        }
        return out;
    }

    public Setting getSettingByDisplayName(String name) {
        for (Setting set : getSettings()) {
            if (set.getDisplayName().equalsIgnoreCase(name)) {
                return set;
            }
        }
        System.err.println("[Yasashii] Error Setting NOT found: '" + name +"'!");
        return null;
    }

    public Setting getSettingByID(String id) {
        for (Setting s : getSettings()) {
            if (s.getId().equalsIgnoreCase(id)) {
                return s;
            }
        }
        System.err.println("[Yasashii] Error Setting NOT found: '" + id +"'!");
        return null;
    }
}
