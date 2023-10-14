package com.cbim.epc.supply.common.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 *
 */
@TableName(value = "epc_common_log")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class CommonLog implements Serializable {
    /**
     *
     */
    private String requestUri;

    /**
     *
     */
    @TableId
    private String requestId;

    /**
     *
     */
    private String requestType;

    /**
     *
     */
    private String requestMethod;

    /**
     *
     */
    private String requestParam;

    /**
     *
     */
    private String requestResult;

    /**
     *
     */
    private Integer resultCode;

    /**
     *
     */
    private Long costTime;

    /**
     *
     */
    private Date requestTime;

    /**
     *
     */
    private String userId;

    /**
     *
     */
    private String resultDetails;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public String toString() {
        return "CommonLog.CommonLogBuilder(requestUri=" + this.requestUri +
                ", requestId=" + this.requestId +
                ", requestType=" + this.requestType +
                ", requestMethod=" + this.requestMethod +
                ", requestParam=" + this.requestParam +
                ", requestResult=" + this.requestResult +
                ", resultCode=" + this.resultCode +
                ", costTime=" + this.costTime +
                ", requestTime=" + this.requestTime +
                ", userId=" + this.userId +  ")";
    }

}