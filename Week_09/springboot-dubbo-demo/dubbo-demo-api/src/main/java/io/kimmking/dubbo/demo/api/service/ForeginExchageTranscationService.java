package io.kimmking.dubbo.demo.api.service;

import org.dromara.hmily.annotation.Hmily;

import io.kimmking.dubbo.demo.api.dto.UserTransactionDTO;

public interface ForeginExchageTranscationService {
	@Hmily
	public Boolean transaction(UserTransactionDTO userTransactionDTO);
}
