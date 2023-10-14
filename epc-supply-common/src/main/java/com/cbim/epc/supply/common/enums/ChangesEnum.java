package com.cbim.epc.supply.common.enums;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import lombok.Getter;

/**
 * 是否变更
 *
 * @author xiaozp
 * @since 2023/3/28
 */
@Getter
public enum ChangesEnum {

    UNCHANGED(0, "未变更"),
    CHANGED(1, "已变更");

    private final Integer code;

    private final String name;

    ChangesEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static ChangesEnum getEnumByCode(Integer code) {
        if (ObjectUtil.isEmpty(code)) {
            return null;
        }
        for (ChangesEnum changesEnum : values()) {
            if (NumberUtil.equals(changesEnum.getCode(), code)) {
                return changesEnum;
            }
        }
        throw new RuntimeException("请传入正确的类型！");
    }
}
