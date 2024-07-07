package com.example.demo.repo.custom;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.dto.CommutingDto;
import com.example.demo.entity.CommutingEntity;
import com.example.demo.entity.UserEntity;


@Repository
public interface CommutingRepoCustom {
	Long updateEnd(Long accountId);
	Long updateState(Long accountId, String state);
	CommutingDto.TodayCommuting findTodayCommuting(Long accountId);
}
