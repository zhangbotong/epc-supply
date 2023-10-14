package com.cbim.epc.supply.data.enums;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;

/**
 * 元数据发布状态
 */
@Getter
public enum PublishTypeEnum {

    RELEASE("release", "发布"),
    DRAFT("draft", "草稿");

    private final String code;

    private final String name;

    PublishTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static PublishTypeEnum getEnumByCode(String code) {
        for (PublishTypeEnum selectTypeEnum : values()) {
            if (StrUtil.equals(selectTypeEnum.getCode(), code)) {
                return selectTypeEnum;
            }
        }
        throw new RuntimeException("请传入正确的类型！");
    }

}