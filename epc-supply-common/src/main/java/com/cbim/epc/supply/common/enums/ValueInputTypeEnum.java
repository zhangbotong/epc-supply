package com.cbim.epc.supply.common.enums;


import cn.hutool.core.util.StrUtil;

/**
 * 输入类型
 */
public enum ValueInputTypeEnum {

    INPUT_TYPE("input", "输入"),
    ENUM_TYPE("enum", "枚举"),
    RANGE_TYPE("range", "区间枚举"),
    NONE("", "");

    ValueInputTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static ValueInputTypeEnum getEnumByCode(String code) {
        if (StrUtil.isBlank(code)) {
            return null;
        }
        for (ValueInputTypeEnum methodEnum : values()) {
            if (methodEnum.getCode().equals(code)) {
                return methodEnum;
            }
        }
        throw new RuntimeException("请传入正确的类型！");
    }

    private String code;

    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
