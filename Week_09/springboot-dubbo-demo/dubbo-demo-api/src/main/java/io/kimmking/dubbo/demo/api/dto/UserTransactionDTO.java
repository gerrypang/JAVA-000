package io.kimmking.dubbo.demo.api.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class UserTransactionDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer fromUserAccount;
	private String fromAssetsType;
	private BigDecimal fromAmount;

	private Integer toUserAccount;
	private String toAssetsType;
	private BigDecimal toAmount;

}
