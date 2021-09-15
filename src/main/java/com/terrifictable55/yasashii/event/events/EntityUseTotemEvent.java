package com.terrifictable55.yasashii.event.events;

import com.terrifictable55.yasashii.event.Event;
import net.minecraft.entity.Entity;

public class EntityUseTotemEvent extends Event {
    private final Entity entity;

    public EntityUseTotemEvent(Entity entity) {
        super();
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }
}
