package com.gerry.pang.order.dto;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * order productg关系表
 * @author system generate 
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TbOrderProductRelationDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "orderId")
    private Integer orderId;

    @ApiModelProperty(value = "productId")
    private Integer productId;

    @ApiModelProperty(value = "num")
    private Integer num;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    
    @ApiModelProperty(value = "更新时间")
    private java.util.Date updateTime;

    @ApiModelProperty(value = "逻辑删除")
    private Boolean isDeleted;

    @ApiModelProperty(value = "版本")
    private Integer version;
}