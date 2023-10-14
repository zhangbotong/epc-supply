package com.cbim.epc.supply.common.enums;

import lombok.Getter;

/**
 * 操作类型
 */
@Getter
public enum OperationTypeEnum {

    EDIT("edit", "编辑"),
    COPY("copy", "复制");

    private final String code;

    private final String name;

    OperationTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static OperationTypeEnum getEnumByCode(String code) {
        for (OperationTypeEnum typeEnum : values()) {
            if (typeEnum.getCode().equals(code)) {
                return typeEnum;
            }
        }
        throw new RuntimeException("请传入正确的类型！");
    }
}
