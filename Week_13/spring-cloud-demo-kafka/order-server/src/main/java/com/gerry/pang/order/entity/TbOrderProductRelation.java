package com.gerry.pang.order.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * order productg关系表
 * 
 * @author system generate
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TbOrderProductRelation implements Serializable {
	private static final long serialVersionUID = 1L;

    private Integer id;
	
	private Integer orderId;

	private Integer productId;
	
	private Integer num;

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