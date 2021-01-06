package io.kimmking.dubbo.demo.consumer.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 账户表
 * @author system generate 
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TbUserAssets implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private Integer id;
    
    /**
     * 用户账户
     */
    private Integer userAccount;;

    /**
     * 用户名
     */
    private String username;

    /**
     * 资产类型
     */
    private String assetsType;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 数据更新时间
     */
    private Date updateTime;
    
    /**
     * 数据创建时间
     */
    private Date createTime;

    /**
     * 数据逻辑删除
     */
    private Integer version;

    /**
     * 数据版本
     */
    private Boolean deleted;
}