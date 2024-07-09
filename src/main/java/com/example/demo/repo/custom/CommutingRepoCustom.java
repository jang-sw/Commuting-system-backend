package com.example.demo.repo.custom;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.dto.CommutingDto;
import com.example.demo.dto.CommutingDto.CommutingData;


@Repository
public interface CommutingRepoCustom {
	Long updateEnd(Long accountId);
	Long updateState(Long accountId, String state);
	CommutingDto.TodayCommuting findTodayCommuting(Long accountId);
	List<CommutingDto.CommutingData> findByUserWithPage(int page, Long accountId);
	Long countByAccountId(Long accountId);
	List<CommutingDto.CommutingDataWithUser> findByUserWithPageIn(int page, List<Long> accountIds);
	Long countByAccountIdIn(List<Long> accountIds);

}
