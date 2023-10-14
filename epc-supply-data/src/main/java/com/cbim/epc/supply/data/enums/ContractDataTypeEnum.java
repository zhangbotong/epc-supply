package com.cbim.epc.supply.data.enums;

import lombok.Getter;

/**
 * 合同数据类型枚举
 *
 * @author xiaozp
 * @since 2023/6/8
 */
@Getter
public enum ContractDataTypeEnum {

    IMPORT("import", "历史导入");

    private final String code;

    private final String name;

    ContractDataTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static ContractDataTypeEnum getEnumByCode(String code) {
        for (ContractDataTypeEnum dataTypeEnum : values()) {
            if (dataTypeEnum.getCode().equals(code)) {
                return dataTypeEnum;
            }
        }
        throw new RuntimeException("请传入正确的类型！");
    }
}
