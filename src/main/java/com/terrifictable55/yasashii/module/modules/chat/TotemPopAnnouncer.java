package com.terrifictable55.yasashii.module.modules.chat;

import com.terrifictable55.yasashii.Yasashii;
import com.terrifictable55.yasashii.event.events.PacketEvent;
import com.terrifictable55.yasashii.event.events.TotemPopEvent;
import com.terrifictable55.yasashii.manager.Setting;
import com.terrifictable55.yasashii.manager.friends.Friends;
import com.terrifictable55.yasashii.module.Category;
import com.terrifictable55.yasashii.module.Module;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraftforge.common.MinecraftForge;

import java.util.HashMap;

public class TotemPopAnnouncer extends Module {
    @EventHandler
    public Listener<TotemPopEvent> listListener;
    @EventHandler
    public Listener<PacketEvent.Receive> popListener;
    private HashMap<String, Integer> playerList;
    private boolean isDead;
    Setting greenText;

    @Override
    public void setup() {
        rSetting(greenText = new Setting("GreenText", this, false, "greentext"));
    }

    {

        this.playerList = new HashMap<String, Integer>();
        this.isDead = false;
        final int[] popCounter = {0};
        this.listListener = new Listener<TotemPopEvent>(event -> {
            if (this.playerList == null) {
                this.playerList = new HashMap<String, Integer>();
            }
            if (this.playerList.get(event.getEntity().getName()) == null) {
                this.playerList.put(event.getEntity().getName(), 1);
                if (!(event.getEntity().getName() == mc.player.getName()) && !(Friends.isFriend(event.getEntity().getName()))) {
                    if (greenText.getValBoolean()) {
                        mc.player.sendChatMessage(">" + event.getEntity().getName() + " popped 1 totem");
                    } else {
                        mc.player.sendChatMessage(event.getEntity().getName() + " popped 1 totem");
                    }
                }
            } else if (this.playerList.get(event.getEntity().getName()) != null) {
                popCounter[0] = this.playerList.get(event.getEntity().getName());
                ++popCounter[0];
                this.playerList.put(event.getEntity().getName(), popCounter[0]);
                if (!(event.getEntity().getName() == mc.player.getName()) && !(Friends.isFriend(event.getEntity().getName()))) {
                    if (greenText.getValBoolean()) {
                        mc.player.sendChatMessage(">" + event.getEntity().getName() + " popped " + popCounter[0] + " totems");
                    } else {
                        mc.player.sendChatMessage(event.getEntity().getName() + " popped " + popCounter[0] + " totems");
                    }
                }
            }
            return;
        });
        final SPacketEntityStatus[] packet = new SPacketEntityStatus[1];
        final Entity[] entity = new Entity[1];
        this.popListener = new Listener<PacketEvent.Receive>(event -> {
            if (mc.player != null) {
                if (event.getPacket() instanceof SPacketEntityStatus) {
                    packet[0] = (SPacketEntityStatus) event.getPacket();
                    if (packet[0].getOpCode() == 35) {
                        entity[0] = packet[0].getEntity(mc.world);
                        if (this.selfCheck(entity[0].getName())) {
                            Yasashii.EVENT_BUS.post(new TotemPopEvent(entity[0]));
                        }
                    }
                }
            }
        });
    }

    public TotemPopAnnouncer() {
        super("TotemPopCounter", Category.MISC);
    }

    @Override
    public void onEnable() {
        //BloodHack.EVENT_BUS.subscribe(this);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void onDisable() {
        //BloodHack.EVENT_BUS.unsubscribe(this);
        MinecraftForge.EVENT_BUS.unregister(this);
    }

    @Override
    public void onUpdate() {
        if (!this.isDead && 0.0f >= mc.player.getHealth()) {
            this.isDead = true;
            this.playerList.clear();
            return;
        }
        if (this.isDead && 0.0f < mc.player.getHealth()) {
            this.isDead = false;
        }
        for (final EntityPlayer player : mc.world.playerEntities) {
            if (0.0f >= player.getHealth() && this.selfCheck(player.getName()) && this.playerList.containsKey(player.getName())) {
                if (!(player.getName() == mc.player.getName()) && !(Friends.isFriend(player.getName()))) {
                    if (greenText.getValBoolean()) {
                        mc.player.sendChatMessage(">" + player.getName() + " died after popping " + this.playerList.get(player.getName()) + " totems");
                    } else {
                        mc.player.sendChatMessage(player.getName() + " died after popping " + this.playerList.get(player.getName()) + " totems");
                    }
                }
                this.playerList.remove(player.getName(), this.playerList.get(player.getName()));
            }
        }
    }

    private boolean selfCheck(final String name) {
        return !this.isDead && ((name.equalsIgnoreCase(mc.player.getName())) || !name.equalsIgnoreCase(mc.player.getName()));
    }

}
