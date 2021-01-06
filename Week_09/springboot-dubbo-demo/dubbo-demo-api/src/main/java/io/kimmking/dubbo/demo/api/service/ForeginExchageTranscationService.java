package io.kimmking.dubbo.demo.api.service;

import io.kimmking.dubbo.demo.api.dto.UserTransactionDTO;

public interface ForeginExchageTranscationService {
	
	public Boolean transaction(UserTransactionDTO userTransactionDTO);
}
