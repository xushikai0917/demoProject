package com.example.nettyproject.netty_basic.outread;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author ：Mark老师
 * @description ：TODO
 */
public class ServerOutReadHandler extends ChannelOutboundHandlerAdapter {
    private static final Logger LOG = LoggerFactory.getLogger(ServerOutReadHandler.class);
    private int count;

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        //super.write(ctx, msg, promise);
    }

    @Override
    public void read(ChannelHandlerContext ctx) throws Exception {
        LOG.info("请求读更多的数据，但是我要休息一下");
        TimeUnit.SECONDS.sleep(5);
        super.read(ctx);
    }
}
