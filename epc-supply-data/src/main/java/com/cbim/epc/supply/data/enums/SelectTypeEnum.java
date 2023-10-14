package com.cbim.epc.supply.data.enums;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;

/**
 * 选择类型
 *
 * @author xiaozp
 * @since 2023/4/20
 */
@Getter
public enum SelectTypeEnum {

    RADIO("radio", "单选"),
    CHECK("check", "多选");

    private final String code;

    private final String name;

    SelectTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static SelectTypeEnum getEnumByCode(String code) {
        if (StrUtil.isBlank(code)) {
            return null;
        }
        for (SelectTypeEnum selectTypeEnum : values()) {
            if (StrUtil.equals(selectTypeEnum.getCode(), code)) {
                return selectTypeEnum;
            }
        }
        throw new RuntimeException("请传入正确的类型！");
    }

}