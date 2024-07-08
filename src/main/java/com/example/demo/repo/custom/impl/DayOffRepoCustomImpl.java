package com.example.demo.repo.custom.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.example.demo.config.Constant;
import com.example.demo.dto.DayOffDto;
import com.example.demo.entity.QDayOffEntity;
import com.example.demo.repo.custom.DayOffRepoCustom;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class DayOffRepoCustomImpl implements DayOffRepoCustom{
	
	final JPAQueryFactory jpaQueryFactory;

	@Override						
	public List<DayOffDto.DayOffData> findByUserWithPage(int page, Long accountId) {
		QDayOffEntity qDayOffEntity = QDayOffEntity.dayOffEntity;
		OrderSpecifier<?> orderSpecifier = new OrderSpecifier<>(Order.DESC, qDayOffEntity.dayOffId);

		PageRequest pageRequest = PageRequest.of(page, Constant.HISTORY_PAGE_SIZE);
		
		return jpaQueryFactory.select(
					Projections.bean(DayOffDto.DayOffData.class, qDayOffEntity.dayOffId ,qDayOffEntity.category, qDayOffEntity.reason, qDayOffEntity.start, qDayOffEntity.end))
				.from(qDayOffEntity)
				.where(qDayOffEntity.user.accountId.eq(accountId))
		        .offset(pageRequest.getOffset())
		        .limit(pageRequest.getPageSize())
		        .orderBy(orderSpecifier)
		        .fetch();
	}

	@Override
	public Long countByAccountId(Long accountId) {
		QDayOffEntity qDayOffEntity = QDayOffEntity.dayOffEntity;

		return jpaQueryFactory.select(qDayOffEntity.count())
			.from(qDayOffEntity)
			.where(qDayOffEntity.user.accountId.eq(accountId))
			.fetchFirst();
	}

	@Override
	public DayOffDto.DayOffData finfTodayByAccountId(Long accountId) {
		QDayOffEntity qDayOffEntity = QDayOffEntity.dayOffEntity;
		LocalDate today = LocalDate.now();
		
		return jpaQueryFactory.select(
				Projections.bean(DayOffDto.DayOffData.class, qDayOffEntity.dayOffId ,qDayOffEntity.category, qDayOffEntity.reason, qDayOffEntity.start, qDayOffEntity.end))
				.from(qDayOffEntity)
				.where(
					qDayOffEntity.user.accountId.eq(accountId)
					,qDayOffEntity.start.loe(today)
					,qDayOffEntity.end.goe(today)
				).fetchFirst();
	}


}
