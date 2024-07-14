package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.config.Constant;
import com.example.demo.dto.DayOffDto;
import com.example.demo.entity.DayOffEntity;
import com.example.demo.repo.DayOffRepo;
import com.example.demo.util.CommonUtil;

import jakarta.transaction.Transactional;

@Service
public class DayOffService {
	
	@Autowired DayOffRepo dayOffRepo;
	@Autowired CommonUtil commonUtil;
	
	/**
	 * 휴가 생성
	 * 
	 * @param
	 * @return 1(성공), -1(실패)
	 * */
	public int createDayOff(DayOffEntity dayOffEntity) {
		try {
			dayOffRepo.save(dayOffEntity);
			return 1;
		}catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * 휴가 취소
	 * 
	 * @param
	 * @return 1(성공), -1(실패)
	 * */
	@Transactional
	public int cancelDayOff(Long dayOffId, Long accountId) {
		try {
			return dayOffRepo.deleteByDayOffIdAndUser(dayOffId, accountId) > 0 ? 1 : -1;
		}catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * 오늘 휴가일정 불러오기
	 * 
	 * @param
	 * @return 휴가 정보
	 * */
	public DayOffDto.DayOffData getTodayDayOff(Long accountId) throws Exception{
		
		return dayOffRepo.finfTodayByAccountId(accountId);
		
	}
	
	/**
	 * 휴가 기록 불러오기
	 * 
	 * @param
	 * @return 연중 사용일, 월중 사용일, 사용 기록, 사용 기록 최대 page
	 * */
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
	
	/**
	 * 사용자 목록으로 휴가 기록 불러오기
	 * 
	 * @param
	 * @return 사용 기록, 사용 기록 최대 page
	 * */
	public DayOffDto.DayOffHistoryWithUser getHistDataAdmin(List<Long> accountIds, int page) throws Exception{
		DayOffDto.DayOffHistoryWithUser dayOffHistory 
			= new DayOffDto.DayOffHistoryWithUser(
					 dayOffRepo.findByUserWithPageIn(page, accountIds)
					, commonUtil.getMaxPage(dayOffRepo.countByAccountIdIn(accountIds), Constant.HISTORY_PAGE_SIZE) 
			);
		
		return dayOffHistory;
	}
}
