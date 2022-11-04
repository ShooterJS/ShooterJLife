package com.shooterj.design.chain.common.chainrespb;

public class UserExistHandler extends ChainHandler{

    ChainServer server;

    public UserExistHandler(ChainServer server) {
        this.server = server;
    }


    @Override
    public boolean check(String email, String password) {
        if (!server.hasEmail(email)) {
            System.out.println("This email is not registered!");
            return false;
        }
        if (!server.isValidPassword(email, password)) {
            System.out.println("Wrong password!");
            return false;
        }
        return checkNext(email, password);
    }

}
