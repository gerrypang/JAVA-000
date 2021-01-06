package io.kimmking.dubbo.demo.provider.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 冻结资产表
 * @author system generate 
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TbFrozenAssetsDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    
    /**
     * 用户账户
     */
    private Integer userAccount;;

    /**
     * 资产类型
     */
    private String assetsType;

    /**
     * 金额
     */
    private java.math.BigDecimal amount;

    /**
     * 资产状态
     */
    private Integer assetStatus;

    /**
     * 冻结时间
     */
    private Date frozenTime;

    /**
     * 解冻时间
     */
    private Date unfrozenTime;

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