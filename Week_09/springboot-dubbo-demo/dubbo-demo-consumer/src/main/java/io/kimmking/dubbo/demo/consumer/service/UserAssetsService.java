package io.kimmking.dubbo.demo.consumer.service;

import io.kimmking.dubbo.demo.api.dto.UserTransactionDTO;

public interface UserAssetsService {

	Boolean exchageTranscation(UserTransactionDTO userTransactionDTO);

}
