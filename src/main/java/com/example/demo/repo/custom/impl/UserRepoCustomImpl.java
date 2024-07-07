package com.example.demo.repo.custom.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.dto.UserDto;
import com.example.demo.dto.UserDto.TodayCommute;
import com.example.demo.entity.QCommutingEntity;
import com.example.demo.entity.QDayOffEntity;
import com.example.demo.entity.QUserEntity;
import com.example.demo.repo.custom.UserRepoCustom;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserRepoCustomImpl implements UserRepoCustom{
	
	final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<TodayCommute> findTodayCommuting(String name) {
		QUserEntity qUserEntity = QUserEntity.userEntity;
		QCommutingEntity qCommutingEntity = QCommutingEntity.commutingEntity;
		QDayOffEntity qDayOffEntity = QDayOffEntity.dayOffEntity;
		LocalDate today = LocalDate.now();
		LocalDateTime startOfDay = LocalDateTime.of(today, LocalTime.MIDNIGHT);
		LocalDateTime startOfTomorrow = LocalDateTime.of(today.plusDays(1), LocalTime.MIDNIGHT);
		
		return jpaQueryFactory.select(
			Projections.bean(UserDto.TodayCommute.class
				, qUserEntity.email, qUserEntity.auth, qUserEntity.name, qUserEntity.team, qUserEntity.position, qUserEntity.position
				, qCommutingEntity.state, qDayOffEntity.category
			))
		.from(qUserEntity)
		.leftJoin(qCommutingEntity).on(
				qUserEntity.accountId.eq(qCommutingEntity.user.accountId)
				,qCommutingEntity.start.isNotNull()
				,qCommutingEntity.start.after(startOfDay)
				,qCommutingEntity.start.before(startOfTomorrow)
			)
		.leftJoin(qDayOffEntity).on(
				qUserEntity.accountId.eq(qDayOffEntity.user.accountId)
				,qDayOffEntity.start.isNotNull()
				,qDayOffEntity.start.after(startOfDay)
				,qDayOffEntity.start.before(startOfTomorrow)
			)
		.where(qUserEntity.name.contains(name.trim()))
		.fetch();
		
	}
	
	
}
