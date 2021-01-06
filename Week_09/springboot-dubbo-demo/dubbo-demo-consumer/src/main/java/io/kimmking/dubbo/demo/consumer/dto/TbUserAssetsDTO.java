package io.kimmking.dubbo.demo.consumer.dto;

import java.io.Serializable;

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
public class TbUserAssetsDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    
    private Integer userAccount;
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
    private java.math.BigDecimal amount;

    /**
     * 数据更新时间
     */
    private java.util.Date updateTime;

    /**
     * 数据逻辑删除
     */
    private Integer version;

    /**
     * 数据版本
     */
    private Boolean deleted;
}