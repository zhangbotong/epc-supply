package com.cbim.epc.supply.data.vo.resp;

import cn.hutool.core.date.DateUtil;
import com.cbim.epc.supply.data.domain.SupplyAgreement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Kyrie
 * @date 2023/5/19 15:48
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SupplyAgreementVO extends SupplyAgreement {
    /**
     * 有效期
      */
    private String period;

    /**
     * 集采协议状态：有效、过期
     */
    private String status;

    public void setPeriod() {
        this.period = String.format("%s~%s", super.getContractPeriodBefore(), super.getContractPeriodAfter());
    }

    public void setStatus() {
        //比较当前时间和合同期限字符串代表的时间大小，yyyy-MM-dd
        this.status = DateUtil.today().compareTo(super.getContractPeriodAfter()) >= 0 ? "有效" : "过期";
    }
}
