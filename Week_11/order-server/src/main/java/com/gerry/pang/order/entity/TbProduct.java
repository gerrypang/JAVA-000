package com.gerry.pang.order.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * product表
 * @author system generate 
 * @since 2020-12-15 10:41:51
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TbProduct implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String productName;

    /**
     * 状态
     */
    private Integer productStatus;

    /**
     * 库存量
     */
    private Integer storeNum;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 逻辑删除
     */
    private Boolean isDeleted;

    /**
     * 版本
     */
    private Integer version;
}