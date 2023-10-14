package com.cbim.epc.supply.common.base.service;

import cn.hutool.core.lang.func.Func1;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;

/**
 * 基础服务接口
 *
 * @author xiaozp
 * @since 2023/04/26
 */
public interface BaseService<T> extends IService<T> {

    /**
     * 通过主键将传入的实体进行更新 <br/>
     * 注意：如果字段未设置或者设置为null的话，也都会进行update操作
     *
     * @param id 主键id
     * @param updatedEntity 更新实体
     */
    void updateFields(Serializable id, T updatedEntity);

    /**
     * 通过主键将传入字段为设置为null
     *
     * @param id 主键id
     * @param fields 属性字段
     * @param <U> /
     */
    @SuppressWarnings("unchecked")
    <U> void updateFieldsToNull(Serializable id, Func1<T, U>... fields);
}