package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.config.Constant;
import com.example.demo.dto.DayOffDto;
import com.example.demo.entity.DayOffEntity;
import com.example.demo.repo.DayOffRepo;
import com.example.demo.util.CommonUtil;

@Service
public class DayOffService {
	
	@Autowired DayOffRepo dayOffRepo;
	@Autowired CommonUtil commonUtil;
	
	public int createDayOff(DayOffEntity dayOffEntity) {
		try {
			dayOffRepo.save(dayOffEntity);
			return 1;
		}catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public DayOffDto.DayOffHistory getHistData(Long accountId, int page) throws Exception{
		
		DayOffDto.DayOffHistory dayOffHistory 
			= new DayOffDto.DayOffHistory(
					dayOffRepo.findUsedByMonth(accountId)
					, dayOffRepo.findUsedByYear(accountId)
					, dayOffRepo.findByUserWithPage(page, accountId)
					, commonUtil.getMaxPage(dayOffRepo.countByAccountId(accountId), Constant.HISTORY_PAGE_SIZE) 
			);
		
		return dayOffHistory;
	}
}
