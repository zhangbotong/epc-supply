package com.cbim.epc.supply.data.domain;

import lombok.Data;

/**
 * 合同相关单位信息
 *
 * @author xiaozp
 * @since 2023/6/8
 */
@Data
public class ContractInstitution {

    /**
     * 单位名称
     */
    private String institutionName;

    /**
     * 单位编码
     */
    private String institutionCode;

    /**
     * 单位地址
     */
    private String institutionAddress;

    /**
     * 联系人姓名
     */
    private String personName;

    /**
     * 联系人电话
     */
    private String personPhone;

    /**
     * 联系人邮箱
     */
    private String personEmail;

    /**
     * 内部标识：0-不是内部的；1-是内部的
     */
    private Integer internalFlag;


}
