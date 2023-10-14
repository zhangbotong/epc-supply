package com.cbim.epc.supply.common.mybatis.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 分页结果
 *
 * @author xiaozp
 */
@Data
@Accessors(chain = true)
public final class PageResult<T> implements Serializable {

    /**
     * 数据
     */
    private List<T> list;

    /**
     * 总量
     */
    private Long total;

    /**
     * 每页大小
     */
    private long size;

    /**
     * 当前页
     */
    private long current;

    /**
     * 当前分页总页数
     */
    private Long pages;

    public PageResult() {
    }

    public PageResult(List<T> list, Long total) {
        this.list = list;
        this.total = total;
    }

    public PageResult(Long total) {
        this.list = new ArrayList<>();
        this.total = total;
    }


    public static <T> PageResult<T> empty() {
        return new PageResult<>(0L);
    }

    public static <T> PageResult<T> empty(Long total) {
        return new PageResult<>(total);
    }


}