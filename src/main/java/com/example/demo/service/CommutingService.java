package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.demo.config.Constant;
import com.example.demo.dto.CommutingDto;
import com.example.demo.entity.CommutingEntity;
import com.example.demo.repo.CommutingRepo;

import jakarta.transaction.Transactional;

@Service
public class CommutingService {

	@Autowired CommutingRepo commutingRepo;
	
	
	
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
	 
	public CommutingDto.TodayCommuting getTodayCommuting(String accountId) throws Exception{
		return commutingRepo.findTodayCommuting(Long.parseLong(accountId));
	}
	public List<CommutingDto.CommutingData> getCommutingHistory(int page, String accountId) throws Exception{
		
		
		return commutingRepo.findByUserWithPage(page, Long.parseLong(accountId));
	}
	public Long getHistorySize(String accountId) throws Exception{
		return commutingRepo.countByAccoundId(Long.parseLong(accountId));
	}
	@Transactional
	public int clockOut(String accountId) {
		try {
			return commutingRepo.updateEnd(Long.parseLong(accountId)) > 0 ? 1 : -1;
		}catch(Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
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
