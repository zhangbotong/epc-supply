package com.cbim.epc.supply.data.enums;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;

/**
 * 价格值类型枚举
 *
 * @author xiaozp
 * @since 2023/7/17
 */
@Getter
public enum PriceValueTypeEnum {

    FIXED("fixed", "固定值"),
    RANGE("range", "范围值");

    private final String code;

    private final String name;

    PriceValueTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static PriceValueTypeEnum getEnumByCode(String code) {
        if (StrUtil.isBlank(code)) {
            return null;
        }
        for (PriceValueTypeEnum priceValueTypeEnum : values()) {
            if (StrUtil.equals(priceValueTypeEnum.getCode(), code)) {
                return priceValueTypeEnum;
            }
        }
        throw new RuntimeException("请传入正确的类型！");
    }
}
