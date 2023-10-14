package com.cbim.epc.supply.common.enums;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import lombok.Getter;

/**
 * 几何类型
 *
 * @author xiaozp
 * @since 2023/3/28
 */
@Getter
public enum GeometryTypeEnum {

    INSTANCE(1, "实例"),
    CLASS(2, "类型");

    private final Integer code;

    private final String name;

    GeometryTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static GeometryTypeEnum getEnumByCode(Integer code) {
        if (ObjectUtil.isEmpty(code)) {
            return null;
        }
        for (GeometryTypeEnum geometryTypeEnum : values()) {
            if (NumberUtil.equals(geometryTypeEnum.getCode(), code)) {
                return geometryTypeEnum;
            }
        }
        throw new RuntimeException("请传入正确的类型！");
    }

}
