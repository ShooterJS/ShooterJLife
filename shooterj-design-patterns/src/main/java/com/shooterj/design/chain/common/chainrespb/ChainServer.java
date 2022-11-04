package com.shooterj.design.chain.common.chainrespb;

import java.util.HashMap;
import java.util.Map;

public class ChainServer {
    private Map<String, String> users = new HashMap<>();
    ChainHandler chainHandler;

    public void setChainHandler(ChainHandler chainHandler) {
        this.chainHandler = chainHandler;
    }

    public boolean hasEmail(String email) {
        return users.containsKey(email);
    }

    public boolean isValidPassword(String email, String password) {
        return users.get(email).equals(password);
    }

    public void register(String email, String admin_pass) {
        users.put(email, admin_pass);
    }

    public boolean loginIn(String email, String password) {
        if (chainHandler.check(email, password)) {
            System.out.println("Authorization have been successful!");
            return true;
        }
        return false;
    }

}
