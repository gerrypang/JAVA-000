package io.kimmking.dubbo.demo.api.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class UserTransactionDTO {
	
	private Integer fromUserAccount;
	private String fromAssetsType;
	private BigDecimal fromAmount;
	
	private Integer toUserAccount;
	private String toAssetsType;
	private BigDecimal toAmount;
	
}
