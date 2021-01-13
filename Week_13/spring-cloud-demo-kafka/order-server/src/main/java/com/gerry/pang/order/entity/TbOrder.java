package com.gerry.pang.order.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * order表
 * @author system generate 
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TbOrder implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    
    private Integer orderStatus;

    private Integer userId;

    private Date createTime;
    
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