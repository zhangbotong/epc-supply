package com.cbim.epc.supply.data.convert;


import com.cbim.epc.supply.common.mybatis.pojo.PageResult;

import java.util.List;

public interface BaseConvert<V, E> {

    /**
     * entity 转换成 vo
     * @param entity 源对象
     * @return V 转换后的目标对象
     */
    V toVo(E entity);

    /**
     * vo 转换成 entity
     * @param vo 源对象
     * @return V 转换后的目标对象
     */
    E toEntity(V vo);

    /**
     * entity 列表 转换成 vo 列表
     * @param entities 源对象列表
     * @return V 转换后的目标对象列表
     */
    List<V> toVoList(List<E> entities);

    /**
     * vo 列表 转换成 entity 列表
     * @param voList 源对象列表
     * @return V 转换后的目标对象列表
     */
    List<E> toEntities(List<V> voList);

    /**
     * entity 分页 转换成 vo 分页
     * @param page 源对象
     * @return V 转换后的目标对象列表
     */
    PageResult<V> toVoPage(PageResult<E> page);
}