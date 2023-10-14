package com.cbim.epc.supply.data.enums;

import lombok.Getter;

@Getter
public enum BusinessType {

    MATERIAL("物料管理");

    private final String name;

    BusinessType(String name) {
        this.name = name;
    }
}
