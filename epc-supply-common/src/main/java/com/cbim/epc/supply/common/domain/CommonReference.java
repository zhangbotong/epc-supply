package com.cbim.epc.supply.common.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cbim.epc.supply.common.mybatis.dataobject.BaseDO;
import lombok.*;

/**
 * 引用记录表
 *
 * @author xiaozp
 * @since 2023/4/11
 */
@TableName("epc_common_reference")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonReference extends BaseDO {

    /**
     * 主键-使用雪花算法
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 被引用原表的id
     */
    private Long originalTableId;

    /**
     * 被引用原表的名称
     */
    private String originalTableName;

    /**
     * 引用的id
     */
    private Long referencedId;

    /**
     * 引用的名称
     */
    private String referenceName;

}
