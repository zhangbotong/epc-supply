package com.cbim.epc.supply.common.enums;


import cn.hutool.core.util.StrUtil;

import java.util.Objects;

/**
 * 值类型
 */
public enum ValueTypeEnum {

    TEXT("text", "文本"),
    NUMBER("number", "数值"),
    DATE("date", "日期");

    ValueTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static ValueTypeEnum getEnumByCode(String code) {
        if (StrUtil.isBlank(code)) {
            return null;
        }
        for (ValueTypeEnum methodEnum : values()) {
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
