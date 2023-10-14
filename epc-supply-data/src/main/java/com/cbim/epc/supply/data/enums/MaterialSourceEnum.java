package com.cbim.epc.supply.data.enums;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import lombok.Getter;

/**
 * 物料来源
 *
 * @author xiaozp
 * @since 2023/3/28
 */
@Getter
public enum MaterialSourceEnum {

    CURRENT_MINING(1, "当期集采"),
    OVERDUE_MINING(2, "过期集采"),
    MARKET_SOURCING(3, "市场寻源"),
    PROJECT_PROCUREMENT(4, "项目采购");

    private final Integer code;

    private final String name;

    MaterialSourceEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static MaterialSourceEnum getEnumByCode(Integer code) {
        if (ObjectUtil.isEmpty(code)) {
            return null;
        }
        for (MaterialSourceEnum publishStatusEnum : values()) {
            if (NumberUtil.equals(publishStatusEnum.getCode(), code)) {
                return publishStatusEnum;
            }
        }
        throw new RuntimeException("请传入正确的类型！");
    }

}
