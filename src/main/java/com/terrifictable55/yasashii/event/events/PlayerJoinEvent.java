package com.terrifictable55.yasashii.event.events;

import com.terrifictable55.yasashii.event.Event;

public class PlayerJoinEvent extends Event {
    private final String name;

    public PlayerJoinEvent(String n) {
        super();
        name = n;
    }

    public String getName() {
        return name;
    }
}
