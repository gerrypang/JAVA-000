package com.gerry.pang.order.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
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
public class TbProductDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "productName")
    private String productName;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private Byte productStatus;

    /**
     * 库存量
     */
    @ApiModelProperty(value = "库存量")
    private Integer storeNum;

    /**
     * 下单量
     */
    @ApiModelProperty(value = "下单量")
    private Integer orderNum;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private java.util.Date createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private java.util.Date updateTime;

    /**
     * 逻辑删除
     */
    @ApiModelProperty(value = "逻辑删除")
    private Boolean isDeleted;

    /**
     * 版本
     */
    @ApiModelProperty(value = "版本")
    private Integer version;
}