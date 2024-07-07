package com.example.demo.repo.custom.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.stereotype.Repository;

import com.example.demo.dto.CommutingDto;
import com.example.demo.entity.CommutingEntity;
import com.example.demo.entity.QCommutingEntity;
import com.example.demo.repo.custom.CommutingRepoCustom;import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CommutingRepoCustomImpl implements CommutingRepoCustom{
	
	final JPAQueryFactory jpaQueryFactory;

	@Override
	public Long updateEnd(Long accountId) {
		QCommutingEntity qCommutingEntity = QCommutingEntity.commutingEntity;
		LocalDateTime now = LocalDateTime.now();
		LocalDate today = LocalDate.now();
		LocalDateTime startOfDay = LocalDateTime.of(today, LocalTime.MIDNIGHT);
		LocalDateTime startOfTomorrow = LocalDateTime.of(today.plusDays(1), LocalTime.MIDNIGHT);
		
		return jpaQueryFactory
				.update(qCommutingEntity)
				.set(qCommutingEntity.end, now)
				.where( qCommutingEntity.user.accountId.eq(accountId)
						,qCommutingEntity.start.isNotNull()
						,qCommutingEntity.end.isNull()
						,qCommutingEntity.start.after(startOfDay)
						,qCommutingEntity.start.before(startOfTomorrow)
						)
				.execute();
	}

	@Override
	public Long updateState(Long accountId, String state) {
		QCommutingEntity qCommutingEntity = QCommutingEntity.commutingEntity;
		LocalDate today = LocalDate.now();
		LocalDateTime startOfDay = LocalDateTime.of(today, LocalTime.MIDNIGHT);
		LocalDateTime startOfTomorrow = LocalDateTime.of(today.plusDays(1), LocalTime.MIDNIGHT);
		
		return jpaQueryFactory
				.update(qCommutingEntity)
				.set(qCommutingEntity.state, state)
				.where( qCommutingEntity.user.accountId.eq(accountId)
						,qCommutingEntity.start.isNotNull()
						,qCommutingEntity.end.isNull()
						,qCommutingEntity.start.after(startOfDay)
						,qCommutingEntity.start.before(startOfTomorrow)
						)
				.execute();

	}

	@Override
	public CommutingDto.TodayCommuting findTodayCommuting(Long accountId) {
		QCommutingEntity qCommutingEntity = QCommutingEntity.commutingEntity;
		LocalDate today = LocalDate.now();
		LocalDateTime startOfDay = LocalDateTime.of(today, LocalTime.MIDNIGHT);
		LocalDateTime startOfTomorrow = LocalDateTime.of(today.plusDays(1), LocalTime.MIDNIGHT);
		return jpaQueryFactory.select(Projections.bean(CommutingDto.TodayCommuting.class, qCommutingEntity.state, qCommutingEntity.start, qCommutingEntity.end))
			.from(qCommutingEntity)
			.where(qCommutingEntity.user.accountId.eq(accountId)
					,qCommutingEntity.start.isNotNull()
					,qCommutingEntity.start.after(startOfDay)
					,qCommutingEntity.start.before(startOfTomorrow))
			.fetchFirst();
	}
	
	

}
