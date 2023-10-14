package com.cbim.epc.supply.data.domain;

import lombok.Data;

/**
 * 文件信息
 *
 * @author xiaozp
 * @since 2023/4/4
 */
@Data
public class FileInfo {

    /**
     * 文件唯一id
     */
    private String id;

    /**
     * 文件名称
     */
    private String name;

    /**
     * 文件类型
     */
    private String type;

    /**
     * 是否为主图 0：否 1：是
     */
    private int isMain;

    /**
     * 文件的地址
     */
    private String url;
}
