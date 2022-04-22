package com.example.nettyproject.netty_http.server.autossl;

import cn.tuling.nettyhttp.server.BusiHandler;
import com.example.nettyproject.netty_http.server.BusiHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.ssl.OptionalSslHandler;
import io.netty.handler.ssl.SslContext;

/**
 * @author Mark老师
 * 类说明：
 */
public class AutoSSLServerHandlerInit extends ChannelInitializer<SocketChannel> {

    private final SslContext sslCtx;

    public AutoSSLServerHandlerInit(SslContext sslCtx) {
        this.sslCtx = sslCtx;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline ph = ch.pipeline();

        /*根据客户端的访问来决定是否启用SSL*/
        ph.addLast(new OptionalSslHandler(sslCtx));
        /*把应答报文 编码*/
        ph.addLast("encoder",new HttpResponseEncoder());
        /*把请求报文 解码*/
        ph.addLast("decoder",new HttpRequestDecoder());


        /*聚合http为一个完整的报文*/
        ph.addLast("aggregator",
                new HttpObjectAggregator(10*1024*1024));
        /*把应答报文 压缩,非必要*/
        ph.addLast("compressor",new HttpContentCompressor());
        ph.addLast(new BusiHandler());


    }
}
