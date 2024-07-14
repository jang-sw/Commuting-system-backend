package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CommutingDto;
import com.example.demo.entity.CommutingEntity;
import com.example.demo.repo.CommutingRepo;

import jakarta.transaction.Transactional;

@Service
public class CommutingService {

	@Autowired CommutingRepo commutingRepo;
	
	
	/**
	 * 근무상태 시작으로 업데이트
	 * 
	 * @param
	 * @return 1(성공), -1(에러), -2(이미 오늘 등록함)
	 * */
	public int clockIn(String accountId) {
		try {
			if(commutingRepo.findTodayCommuting(Long.parseLong(accountId)) != null ) {
				return -2;
			}
			CommutingEntity commutingEntity = new CommutingEntity(Long.parseLong(accountId), "START", LocalDateTime.now());
			commutingRepo.save(commutingEntity);
					
			return 1;
		}catch(Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * 오늘 근무 정보 불러오기
	 * 
	 * @param
	 * @return 오늘 근무 상태
	 * */
	public CommutingDto.TodayCommuting getTodayCommuting(String accountId) throws Exception{
		return commutingRepo.findTodayCommuting(Long.parseLong(accountId));
	}
	
	/**
	 * 근무 기록 불러오기 
	 * 
	 * @param 
	 * @return 근무 기록
	 * */
	public List<CommutingDto.CommutingData> getCommutingHistory(int page, Long accountId) throws Exception{
		return commutingRepo.findByUserWithPage(page, accountId);
	}
	
	/**
	 * 근무기록 전체 갯수 불러오기
	 * 
	 * @param 
	 * @return 근무기록 전체 갯수
	 * */
	public Long getHistorySize(Long accountId) throws Exception{
		return commutingRepo.countByAccountId(accountId);
	}
	
	/**
	 * 여러 계정의 근무 기록 불러오기
	 * 
	 * @param 
	 * @return 근무 기록
	 * */
	public List<CommutingDto.CommutingDataWithUser> getCommutingHistoryIn(int page, List<Long> accountIds) throws Exception{
		return commutingRepo.findByUserWithPageIn(page, accountIds);
	}
	
	/**
	 * 여러 계정의 근무 기록 전체 갯수 불러오기
	 * 
	 * @param 
	 * @return 근무기록 전체 갯수
	 * */
	public Long getHistorySizeIn(List<Long> accountIds) throws Exception{
		return commutingRepo.countByAccountIdIn(accountIds);
	}
	
	/**
	 * 근무 상태 종료로 업데이트
	 * 
	 * @param
	 * @return 1(성공), -1(에러)
	 * */
	@Transactional
	public int clockOut(String accountId) {
		try {
			return commutingRepo.updateEnd(Long.parseLong(accountId)) > 0 ? 1 : -1;
		}catch(Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * 근무 상태 업데이트
	 * 
	 * @param
	 * @return 1(성공), -1(에러)
	 * */
	@Transactional
	public int updateState(String accountId, String state) {
		try {
			return commutingRepo.updateState(Long.parseLong(accountId), state) > 0 ? 1 : -1;
		}catch(Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
}
