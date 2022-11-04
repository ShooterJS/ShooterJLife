package com.shooterj.design.chain.common.chainrespb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Test {

    static ChainServer server;
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    static void init() {
        server = new ChainServer();
        server.register("admin", "admin");
        server.register("user", "user");
        ChainHandler chainHandler = ChainHandler.link(new ThrottlingHandler(2),
                new UserExistHandler(server),
                new RoleCheckHandler());
        server.setChainHandler(chainHandler);
    }

    public static void main(String[] args) throws IOException {
        init();
        boolean success = false;
        do {
            System.out.println("enter email: ");
            String email = reader.readLine();
            System.out.println("input password: ");
            String password = reader.readLine();
            success = server.loginIn(email, password);
        } while (!success);
    }

}
