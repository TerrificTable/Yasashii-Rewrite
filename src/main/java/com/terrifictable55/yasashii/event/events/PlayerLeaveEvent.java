package com.terrifictable55.yasashii.event.events;

import com.terrifictable55.yasashii.event.Event;

public class PlayerLeaveEvent extends Event {
    private final String name;

    public PlayerLeaveEvent(String n) {
        super();
        name = n;
    }

    public String getName() {
        return name;
    }
}
