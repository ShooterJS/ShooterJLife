package rpc.provider.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import rpc.provider.service.UserServiceImpl;

/**
 * 自定义的业务处理器
 */
public class UserServiceHandler extends ChannelInboundHandlerAdapter {

    //当客户端读取数据时,该方法会被调用
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        //注意:  客户端将来发送请求的时候会传递一个参数:  UserService#sayHello#are you ok
        //1.判断当前的请求是否符合规则
        if (msg.toString().startsWith("UserService")) {
            //2.如果符合规则,调用实现类货到一个result
            UserServiceImpl service = new UserServiceImpl();
            String result = service.sayHello(msg.toString().substring(msg.toString().lastIndexOf("#") + 1));
            //3.把调用实现类的方法获得的结果写到客户端
            ctx.writeAndFlush(result);
        }


    }
}
