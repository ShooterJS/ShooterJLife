package com.winway.demo.design.wwabstract;

public class MiaoshaRequestMessageHandler extends AbstractMessageHandler<MiaoshaRequestMessage> {

    public MiaoshaRequestMessageHandler() {
        super(MessageType.MIAOSHA_MESSAGE,MiaoshaRequestMessage.class);
    }



    @Override
    public void handle(MiaoshaRequestMessage message) {
        System.out.println(message.getGoodsRandomName() +"---"+message.getMobile());
    }

}
