package com.example.demo.repo.custom;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.dto.DayOffDto;


@Repository
public interface DayOffRepoCustom {

	List<DayOffDto.DayOffData> findByUserWithPage(int page, Long accountId);
	Long countByAccountId(Long accountId);
	
}
