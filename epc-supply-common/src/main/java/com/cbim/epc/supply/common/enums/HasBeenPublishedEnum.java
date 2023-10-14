package com.cbim.epc.supply.common.enums;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import lombok.Getter;

/**
 * 是否进行发布过
 */
@Getter
public enum HasBeenPublishedEnum {

    NO_PUBLISH(0, "没有发布过"),
    PUBLISHED(1, "发布过");

    private final Integer code;

    private final String name;

    HasBeenPublishedEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static HasBeenPublishedEnum getEnumByCode(Integer code) {
        if (ObjectUtil.isEmpty(code)) {
            return null;
        }
        for (HasBeenPublishedEnum publishStatusEnum : values()) {
            if (NumberUtil.equals(publishStatusEnum.getCode(), code)) {
                return publishStatusEnum;
            }
        }
        throw new RuntimeException("请传入正确的类型！");
    }

}
