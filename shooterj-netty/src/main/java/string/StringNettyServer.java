package string;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * 作者:ShooterJ
 * 邮箱:prd-jiangshuai@winwayworld.com
 * 日期:2021/12/6
 */
public class StringNettyServer {

    public static void main(String[] args) throws InterruptedException {
        //创建NioEventLoopGroup两个实例 bossGroup workGroup
        NioEventLoopGroup bossgroup = new NioEventLoopGroup();
        NioEventLoopGroup workergroup = new NioEventLoopGroup();

        //创建bootstrap，初始化组件，监听套接字通道NioServerSocketChannel
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        //设置组,第一个bossGroup负责连接, workerGroup负责连接之后的io处理
        serverBootstrap.group(bossgroup, workergroup)
                //channel方法指定服务器监听的通道类型
                .channel(NioServerSocketChannel.class)
                //设置channel handler , 每一个客户端连接后,给定一个监听器进行处理
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        //传输通道
                        ChannelPipeline pipeline = ch.pipeline();
                        //在通道上添加对通道的处理器 , 该处理器可能还是一个监听器
                        pipeline.addLast(new StringEncoder());
                        pipeline.addLast(new StringDecoder());
                        //监听器队列上添加我们自己的处理方式..
                        pipeline.addLast(new SimpleChannelInboundHandler<String>() {
                            @Override
                            protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
                                System.out.println(msg);
                            }
                        });
                    }
                });

        //bind监听端口
        ChannelFuture future = serverBootstrap.bind(8000).sync();
        System.out.println("tcp server start success..");

        //阻塞等待，直到服务器的channel关闭
        future.channel().closeFuture().sync();

    }
}
