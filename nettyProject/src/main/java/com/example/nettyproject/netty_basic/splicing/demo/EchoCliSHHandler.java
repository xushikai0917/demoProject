package com.example.nettyproject.netty_basic.splicing.demo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 作者：Mark
 * 类说明：
 */
public class EchoCliSHHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private AtomicInteger counter = new AtomicInteger(0);

    /*** 客户端读取到网络数据后的处理*/
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        System.out.println("client Accept["+msg.toString(CharsetUtil.UTF_8)
                +"] and the counter is:"+counter.incrementAndGet());
    }

    /*** 客户端被通知channel活跃后，做事*/
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //ByteBuf msg = null;
        String request = "Mark,Zhuge,Fox,Zhouyu,Loulan"
                + System.getProperty("line.separator");
        final ByteBufAllocator alloc = ctx.alloc();
        ByteBuf msg = null;
        /*我们希望服务器接受到100个这样的报文*/
        for(int i=0;i<100;i++){
            ByteBuf byteBuf = alloc.buffer();
            msg = alloc.buffer(request.length());
            msg.writeBytes(request.getBytes());
            ctx.writeAndFlush(msg);
        }
    }

    /*** 发生异常后的处理*/
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
