package com.cbim.epc.supply.common.enums;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import lombok.Getter;

/**
 * 是否有效
 *
 * @author xiaozp
 * @since 2023/3/28
 */
@Getter
public enum ValidEnum {

    INVALID(0, "无效"),
    VALID(1, "有效");

    private final Integer code;

    private final String name;

    ValidEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static ValidEnum getEnumByCode(Integer code) {
        if (ObjectUtil.isEmpty(code)) {
            return null;
        }
        for (ValidEnum validEnum : values()) {
            if (NumberUtil.equals(validEnum.getCode(), code)) {
                return validEnum;
            }
        }
        throw new RuntimeException("请传入正确的类型！");
    }

}
