package com.cbim.epc.supply.common.enums;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import lombok.Getter;

/**
 * 发布状态
 *
 * @author xiaozp
 * @since 2023/3/28
 */
@Getter
public enum PublishStatusEnum {

    DRAFT(0, "草稿"),
    PUBLISHED(1, "已发布");

    private final Integer code;

    private final String name;

    PublishStatusEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static PublishStatusEnum getEnumByCode(Integer code) {
        if (ObjectUtil.isEmpty(code)) {
            return null;
        }
        for (PublishStatusEnum publishStatusEnum : values()) {
            if (NumberUtil.equals(publishStatusEnum.getCode(), code)) {
                return publishStatusEnum;
            }
        }
        throw new RuntimeException("请传入正确的类型！");
    }

}
