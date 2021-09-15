package com.terrifictable55.yasashii.event.events;

import com.terrifictable55.yasashii.event.Event;
import net.minecraft.entity.MoverType;

public class PlayerMoveEvent extends Event {
    public double x;
    public double y;
    public double z;
    MoverType type;

    public PlayerMoveEvent(MoverType moverType, double xx, double yy, double zz) {
        super();
        type = moverType;
        x = xx;
        y = yy;
        z = zz;
    }

    public MoverType getType() {
        return type;
    }

    public double getX() {
        return x;
    }

    public void setX(double xx) {
        x = xx;
    }

    public double getY() {
        return y;
    }

    public void setY(double yy) {
        y = yy;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double zz) {
        z = zz;
    }
}
