package string;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import java.time.LocalDateTime;

/**
 * 作者:ShooterJ
 * 邮箱:prd-jiangshuai@winwayworld.com
 * 日期:2021/12/6
 */
public class StringNettyClient {



    public static void main(String[] args) throws InterruptedException {
        //客户端的启动辅助类
        Bootstrap bootstrap = new Bootstrap();
        //线程池的实例
        NioEventLoopGroup group = new NioEventLoopGroup();
        //添加到组中
        bootstrap.group(group)
                //channel方法指定通道类型
                .channel(NioSocketChannel.class)
                //通道初始化了
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ch.pipeline().addLast(new StringEncoder());
                    }
                });

        Channel channel = bootstrap.connect("127.0.0.1", 8000).channel();
        while (true) {
            channel.writeAndFlush(LocalDateTime.now() + "Hello Netty World");
            Thread.sleep(2000);
        }
    }
}
