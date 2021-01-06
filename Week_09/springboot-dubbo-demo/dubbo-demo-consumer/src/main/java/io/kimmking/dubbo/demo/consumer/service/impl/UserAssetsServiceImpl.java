package io.kimmking.dubbo.demo.consumer.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.dromara.hmily.annotation.HmilyTCC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.kimmking.dubbo.demo.api.dto.UserTransactionDTO;
import io.kimmking.dubbo.demo.api.service.ForeginExchageTranscationService;
import io.kimmking.dubbo.demo.consumer.entity.TbFrozenAssets;
import io.kimmking.dubbo.demo.consumer.entity.TbUserAssets;
import io.kimmking.dubbo.demo.consumer.mapper.TbFrozenAssetsMapper;
import io.kimmking.dubbo.demo.consumer.mapper.TbUserAssetsMapper;
import io.kimmking.dubbo.demo.consumer.service.UserAssetsService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserAssetsServiceImpl implements UserAssetsService {

	@Autowired
	private TbUserAssetsMapper userAssetsMapper;

	@Autowired
	private TbFrozenAssetsMapper frozenAssetsMapper;
	
	@Autowired
	private ForeginExchageTranscationService foreginExchageTranscationService;
	
	@Override
	@HmilyTCC(confirmMethod = "exchageTranscationConfirm", cancelMethod = "exchageTranscationCancle")
	@Transactional(rollbackFor = Exception.class)
	public Boolean exchageTranscation(UserTransactionDTO userTransactionDTO) {
		boolean result = false;
		log.info(">>>>> exchageTranscation start ");
		final Integer fromUserAccount = userTransactionDTO.getFromUserAccount();
		final String fromAssetsType = userTransactionDTO.getFromAssetsType();
		final BigDecimal fromAmount = userTransactionDTO.getFromAmount();
		
		// 查找账号是否存在
		List<TbUserAssets> user = userAssetsMapper.selectByCondition(TbUserAssets.builder().userAccount(fromUserAccount).build());
		if (CollectionUtils.isEmpty(user)) {
			String errMsg = String.format("用户%d不存在", fromUserAccount);
			log.error(errMsg);
			throw new IllegalArgumentException(errMsg);
		}
		List<TbUserAssets> userAssetsList = userAssetsMapper.selectByCondition(TbUserAssets.builder().userAccount(fromUserAccount).assetsType(fromAssetsType).build());
		if (CollectionUtils.isEmpty(userAssetsList)) {
			String errMsg = String.format("用户%d资产%s为0", fromUserAccount, fromAssetsType);
			log.error(errMsg);
			throw new IllegalArgumentException(errMsg);
		}
		
		TbUserAssets oneTypeUserAssets = userAssetsList.get(0);
		if (oneTypeUserAssets.getAmount().compareTo(fromAmount) < 0) {
			String errMsg = String.format("用户%d资产%s小于%f", fromUserAccount, fromAssetsType, fromAmount.floatValue());
			log.error(errMsg);
			throw new IllegalArgumentException(errMsg);
		}
		
		// 冻结金额
		TbFrozenAssets frozenAssets = TbFrozenAssets.builder().userAccount(fromUserAccount).assetsType(fromAssetsType)
				.assetStatus(0).amount(fromAmount).frozenTime(new Date()).build();
		frozenAssetsMapper.insert(frozenAssets);
		
		// from 账户减 amount
		oneTypeUserAssets.setAmount(oneTypeUserAssets.getAmount().subtract(fromAmount));
		userAssetsMapper.updateByUserAccount(oneTypeUserAssets);
		
		// 调用外部交易服务
		boolean transFlag = foreginExchageTranscationService.transaction(userTransactionDTO);
		result = transFlag == true ? true : false;
		log.info("<<<<< exchageTranscation end ");
		return result;
	}

	public Boolean exchageTranscationConfirm(UserTransactionDTO userTransactionDTO) {
		log.info("===== transactionConfirm ===== ");
		final Integer fromUserAccount = userTransactionDTO.getFromUserAccount();
		final String fromAssetsType = userTransactionDTO.getFromAssetsType();
		final BigDecimal fromAmount = userTransactionDTO.getFromAmount();

		final String toAssetsType = userTransactionDTO.getToAssetsType();
		final BigDecimal toAmount = userTransactionDTO.getToAmount();
		// 解冻金额
		TbFrozenAssets frozenAssets = TbFrozenAssets.builder().userAccount(fromUserAccount).assetStatus(0).amount(fromAmount).assetsType(fromAssetsType).build();
		List<TbFrozenAssets> frozenAssetsList = frozenAssetsMapper.selectByCondition(frozenAssets);
		frozenAssetsList.forEach(n -> {
			// 解冻
			n.setAssetStatus(1);
			n.setUnfrozenTime(new Date());
			frozenAssetsMapper.unfrozenAssets(n);
		});

		// 更新账号金额
		List<TbUserAssets> fromUserAssetsList = userAssetsMapper.selectByCondition(TbUserAssets.builder().userAccount(fromUserAccount).assetsType(fromAssetsType).build());
		List<TbUserAssets> userAssetsList = userAssetsMapper.selectByCondition(TbUserAssets.builder().userAccount(fromUserAccount).assetsType(toAssetsType).build());
		if (CollectionUtils.isNotEmpty(userAssetsList)) {
			userAssetsList.forEach(n -> {
				n.setAmount(n.getAmount().add(toAmount));
				userAssetsMapper.updateByUserAccount(n);
			});
		}  else {
			TbUserAssets fromUserAssets = fromUserAssetsList.get(0);
			TbUserAssets newUserAssets = TbUserAssets.builder()
					.username(fromUserAssets.getUsername())
					.userAccount(fromUserAccount)
					.assetsType(toAssetsType)
					.amount(toAmount).build();
			userAssetsMapper.insert(newUserAssets);
		}
		return true;
	}

	public Boolean exchageTranscationCancle(UserTransactionDTO userTransactionDTO) {
		log.info("===== transactionCancle ===== ");
		final Integer fromUserAccount = userTransactionDTO.getFromUserAccount();
		final String fromAssetsType = userTransactionDTO.getFromAssetsType();
		final BigDecimal fromAmount = userTransactionDTO.getFromAmount();
		// 解冻金额
		TbFrozenAssets frozenAssets = TbFrozenAssets.builder().userAccount(fromUserAccount).assetStatus(0).amount(fromAmount).assetsType(fromAssetsType).build();
		List<TbFrozenAssets> frozenAssetsList = frozenAssetsMapper.selectByCondition(frozenAssets);
		frozenAssetsList.forEach(n -> {
			// 解冻
			n.setAssetStatus(1);
			n.setUnfrozenTime(new Date());
			frozenAssetsMapper.unfrozenAssets(n);
		});
		
		// 恢复账号金额
		List<TbUserAssets> userAssetsList = userAssetsMapper.selectByCondition(TbUserAssets.builder().userAccount(fromUserAccount).assetsType(fromAssetsType).build());
		if (CollectionUtils.isNotEmpty(userAssetsList)) {
			userAssetsList.forEach(n -> {
				n.setAmount(n.getAmount().add(fromAmount));
				userAssetsMapper.updateByUserAccount(n);
			});
		}
		return true;
	}

}
