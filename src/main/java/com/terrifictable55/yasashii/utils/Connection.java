package com.terrifictable55.yasashii.utils;

import com.terrifictable55.yasashii.manager.MessageManager;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import java.util.Objects;

public class Connection extends ChannelDuplexHandler {

    private final EventsHander eventHandler;

    public Connection(EventsHander eventHandler) {
        this.eventHandler = eventHandler;
        try {
            ChannelPipeline pipeline = Objects.requireNonNull(Wrapper.INSTANCE.mc().getConnection()).getNetworkManager().channel().pipeline();
            pipeline.addBefore("packet_handler", "PacketHandler", this);
        } catch (Exception exception) {
            MessageManager.sendMessagePrefix("Connection: Error on attaching");
            exception.printStackTrace();
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object packet) throws Exception {
        if (!eventHandler.onPacket(packet, Side.IN)) {
            return;
        }
        super.channelRead(ctx, packet);
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object packet, ChannelPromise promise) throws Exception {
        if (!eventHandler.onPacket(packet, Side.OUT)) {
            return;
        }
        super.write(ctx, packet, promise);
    }

    public enum Side {
        IN,
        OUT
    }
}
