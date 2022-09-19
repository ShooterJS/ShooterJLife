package com.shooterj.core.multthread.countdown;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SlowInterfaceMock {

    public String method0(String userid) {
        return "method0" + userid;
    }

    public String method1(String userid) {
        return "method1" + userid;
    }

    public String method2(String userid) {
        return "method2" + userid;
    }

    public String method3(String userid) {
        return "method3" + userid;
    }

    public String method4(String userid) {
        return "method4" + userid;
    }
}
