package com.shooterj.java.escape;


public enum TrackingEnum {
    INDEX("001", "首页"),
    FORMAT("002", "业态奖金排名"),
    TERMINAL_ANALYSIS("003", "大区奖金排名"),
    BONUS_RANK("004", "办事处奖金排名"),
    PRODUCT("005", "产品中心"),
    TERMINAL("006", "终端中心"),
    PRODUCT_DETAIL("007", "产品明细"),
    TERMINAL_DETAIL("008", "终端明细"),
    HISTORY_FORECAST("009", "历史预测");

    private String code;
    private String name;

    TrackingEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }


}
