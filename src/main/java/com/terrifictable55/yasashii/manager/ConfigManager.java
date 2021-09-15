package com.terrifictable55.yasashii.manager;

import com.terrifictable55.yasashii.module.Module;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import static com.terrifictable55.yasashii.Yasashii.moduleManager;
import static com.terrifictable55.yasashii.Yasashii.settingManager;

public class ConfigManager {
    public File Yasashii;
    public File Settings;

    public ConfigManager() {
        this.Yasashii = new File("Yasashii");
        if (!this.Yasashii.exists()) {
            this.Yasashii.mkdirs();
        }

        this.Settings = new File("Yasashii" + File.separator + "Settings");
        if (!this.Settings.exists()) {
            this.Settings.mkdirs();
        }
        loadMods();
        loadSettingsList();
        loadBinds();
        loadFramePos();
    }

    public void SaveAll() {
        saveBinds();
        saveMods();
        saveSettingsList();
        saveGUI();
    }

    public void saveBinds() {
        try {
            File file = new File(this.Yasashii.getAbsolutePath(), "Binds.txt");
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            Iterator var3 = moduleManager.getModules().iterator();

            while(var3.hasNext()) {
                Module module = (Module)var3.next();
                out.write(module.getName() + ":" + Keyboard.getKeyName(module.getKey()));
                out.write("\r\n");
            }
            out.close();
        } catch (Exception var5) {
        }
    }


    public void saveGUI() {
        try {
            File file = new File(this.Yasashii.getAbsolutePath(), "FramePositions.txt");
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            /*for(Frame frame: ClickGui.frames){
                out.write(frame.category + ":x:" + frame.getX());
                out.write("\r\n");
                out.write(frame.category + ":y:" + frame.getY());
                out.write("\r\n");
            }*/
            out.close();
        } catch (Exception var5) {
        }
    }

    public void loadFramePos() {
        try {
            File file = new File(this.Yasashii.getAbsolutePath(), "FramePositions.txt");
            FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            String line;
            while ((line = br.readLine()) != null) {
                String curLine = line.trim();
                String name = curLine.split(":")[0];
                String xory = curLine.split(":")[1];
                String pos = curLine.split(":")[2];
                /*for (Frame frame:ClickGui.frames) {
                    if (frame.category.equals(name)){
                        if (xory.contains("x")) {
                            frame.setX(Integer.parseInt(xory));
                        }
                        if (xory.contains("y")) {
                            frame.setY(Integer.parseInt(xory));
                        }
                    }
                }*/
            }
            br.close();
        } catch (Exception var11) {
            var11.printStackTrace();
            saveBinds();
        }
    }

    public void loadBinds() {
        try {
            File file = new File(this.Yasashii.getAbsolutePath(), "Binds.txt");
            FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            String line;
            while ((line = br.readLine()) != null) {
                String curLine = line.trim();
                String name = curLine.split(":")[0];
                String bind = curLine.split(":")[1];
                for (Module m : moduleManager.getModules()) {
                    if (m != null && m.getName().equalsIgnoreCase(name)) {
                        m.setKey(Keyboard.getKeyIndex(bind));
                    }
                }
            }
            br.close();
        } catch (Exception var11) {
            var11.printStackTrace();
            saveBinds();
        }
    }

    public void saveMods() {
        try {
            File file = new File(this.Yasashii.getAbsolutePath(), "EnabledModules.txt");
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            Iterator var3 = moduleManager.getModules().iterator();

            while(var3.hasNext()) {
                Module module = (Module)var3.next();
                if (module.isToggled()) {
                    out.write(module.getName());
                    out.write("\r\n");
                }
            }
            out.close();
        } catch (Exception var5) {
        }
    }

    public void writeCrash(String alah) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd_MM_yyyy-HH_mm_ss");
            Date date = new Date();
            File file = new File(this.Yasashii.getAbsolutePath(), "crashlog-".concat(format.format(date)).concat(".bruh"));
            BufferedWriter outWrite = new BufferedWriter(new FileWriter(file));
            outWrite.write(alah);
            outWrite.close();
        } catch (Exception var6) {
        }
    }

    public void loadMods() {
        try {
            File file = new File(this.Yasashii.getAbsolutePath(), "EnabledModules.txt");
            FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            String line;
            while ((line = br.readLine()) != null) {
                Iterator var6 = moduleManager.getModules().iterator();

                while(var6.hasNext()) {
                    Module m = (Module)var6.next();
                    if (m.getName().equals(line)) {
                        m.toggle();
                    }
                }
            }
            br.close();
        } catch (Exception var8) {
            var8.printStackTrace();
            this.saveMods();
        }
    }

    public void saveSettingsList() {
        File file;
        BufferedWriter out;
        Iterator var3;
        Setting i;
        try {
            file = new File(Settings.getAbsolutePath(), "Number.txt");
            out = new BufferedWriter(new FileWriter(file));
            var3 = settingManager.getSettings().iterator();

            while (var3.hasNext()) {
                i = (Setting)var3.next();
                if (i.isSlider()) {
                    out.write(i.getId() + ":" + i.getValDouble() + ":" + i.getParentMod().getName() + "\r\n");
                }
            }
            out.close();
        } catch (Exception var7) {
        }

        try {
            file = new File(Settings.getAbsolutePath(), "Boolean.txt");
            out = new BufferedWriter(new FileWriter(file));
            var3 = settingManager.getSettings().iterator();

            while (var3.hasNext()) {
                i = (Setting)var3.next();
                if (i.isCheck()) {
                    out.write(i.getId() + ":" + i.getValBoolean() + ":" + i.getParentMod().getName() + "\r\n");
                }
            }
            out.close();
        } catch (Exception var6) {
        }

        try {
            file = new File(Settings.getAbsolutePath(), "String.txt");
            out = new BufferedWriter(new FileWriter(file));
            var3 = settingManager.getSettings().iterator();

            while (var3.hasNext()) {
                i = (Setting)var3.next();
                if (i.isCombo()) {
                    out.write(i.getId() + ":" + i.getValString() + ":" + i.getParentMod().getName() + "\r\n");
                }
            }
            out.close();
        } catch (Exception var5) {
        }

        try {
            file = new File(Settings.getAbsolutePath(), "Color.txt");
            out = new BufferedWriter(new FileWriter(file));
            var3 = settingManager.getSettings().iterator();

            while (var3.hasNext()) {
                i = (Setting)var3.next();
                if (i.isColorPicker()) {
                    out.write(i.getId() + ":" + i.getValColor().getRGB() + ":" + i.getParentMod().getName() + "\r\n");
                }
            }
            out.close();
        } catch (Exception var7) {
        }
    }

    public void loadSettingsList() {
        File file;
        FileInputStream fstream;
        DataInputStream in;
        BufferedReader br;
        String line;
        String curLine;
        String name;
        String isOn;
        String m;
        Setting mod;
        int color;
        try {
            file = new File(Settings.getAbsolutePath(), "Number.txt");
            fstream = new FileInputStream(file.getAbsolutePath());
            in = new DataInputStream(fstream);
            br = new BufferedReader(new InputStreamReader(in));

            while ((line = br.readLine()) != null) {
                curLine = line.trim();
                name = curLine.split(":")[0];
                isOn = curLine.split(":")[1];
                m = curLine.split(":")[2];
                for (Module mm : moduleManager.getModules()) {
                    if (mm != null && mm.getName().equalsIgnoreCase(m)) {
                        mod = settingManager.getSettingByID(name);
                        mod.setValDouble(Double.parseDouble(isOn));
                    }
                }
            }
            br.close();
        } catch (Exception var13) {
            var13.printStackTrace();
            saveSettingsList();
        }

        try {
            file = new File(Settings.getAbsolutePath(), "Color.txt");
            fstream = new FileInputStream(file.getAbsolutePath());
            in = new DataInputStream(fstream);
            br = new BufferedReader(new InputStreamReader(in));

            while ((line = br.readLine()) != null) {
                curLine = line.trim();
                name = curLine.split(":")[0];
                color = Integer.parseInt(curLine.split(":")[1]);
                m = curLine.split(":")[2];
                for (Module mm : moduleManager.getModules()) {
                    if (mm != null && mm.getName().equalsIgnoreCase(m)) {
                        mod = settingManager.getSettingByID(name);
                        mod.setValColor(new Color(color));
                    }
                }
            }
            br.close();
        } catch (Exception var13) {
            var13.printStackTrace();
            saveSettingsList();
        }

        try {
            file = new File(Settings.getAbsolutePath(), "Boolean.txt");
            fstream = new FileInputStream(file.getAbsolutePath());
            in = new DataInputStream(fstream);
            br = new BufferedReader(new InputStreamReader(in));

            while ((line = br.readLine()) != null) {
                curLine = line.trim();
                name = curLine.split(":")[0];
                isOn = curLine.split(":")[1];
                m = curLine.split(":")[2];
                for (Module mm : moduleManager.getModules()) {
                    if (mm != null && mm.getName().equalsIgnoreCase(m)) {
                        mod = settingManager.getSettingByID(name);
                        mod.setValBoolean(Boolean.parseBoolean(isOn));
                    }
                }
            }

            br.close();
        } catch (Exception var12) {
            var12.printStackTrace();
            saveSettingsList();
        }

        try {
            file = new File(Settings.getAbsolutePath(), "String.txt");
            fstream = new FileInputStream(file.getAbsolutePath());
            in = new DataInputStream(fstream);
            br = new BufferedReader(new InputStreamReader(in));

            while ((line = br.readLine()) != null) {
                curLine = line.trim();
                name = curLine.split(":")[0];
                isOn = curLine.split(":")[1];
                m = curLine.split(":")[2];
                for (Module mm : moduleManager.getModules()) {
                    if (mm != null && mm.getName().equalsIgnoreCase(m)) {
                        mod = settingManager.getSettingByID(name);
                        mod.setValString(isOn);
                    }
                }
            }
            br.close();
        } catch (Exception var11) {
            var11.printStackTrace();
            saveSettingsList();
        }
    }
}
