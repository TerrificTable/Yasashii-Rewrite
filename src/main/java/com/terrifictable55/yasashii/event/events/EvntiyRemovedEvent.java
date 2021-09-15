package com.terrifictable55.yasashii.event.events;

import com.terrifictable55.yasashii.event.Event;
import net.minecraft.entity.Entity;

public class EvntiyRemovedEvent extends Event {
    private Entity entity;

    public void EventEntityRemoved(Entity entity) {
        this.entity = entity;
    }

    public Entity get_entity() {
        return this.entity;
    }
}
