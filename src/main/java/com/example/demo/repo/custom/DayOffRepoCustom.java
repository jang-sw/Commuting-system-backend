package com.example.demo.repo.custom;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.dto.DayOffDto;
import com.example.demo.entity.UserEntity;


@Repository
public interface DayOffRepoCustom {

	List<DayOffDto.DayOffData> findByUserWithPage(int page, Long accountId);
	Long countByAccountId(Long accountId);
	DayOffDto.DayOffData finfTodayByAccountId(Long accountId);
	Long deleteByDayOffIdAndUser(Long dayOffId, Long accountId);
	Long countByAccountIdIn(List<Long> accountIds);
	List<DayOffDto.DayOffDataWithUser> findByUserWithPageIn(int page, List<Long> accountIds);
}
