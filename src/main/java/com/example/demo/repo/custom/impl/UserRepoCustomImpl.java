package com.example.demo.repo.custom.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.dto.UserDto;
import com.example.demo.dto.UserDto.Response;
import com.example.demo.dto.UserDto.TodayCommute;
import com.example.demo.entity.QCommutingEntity;
import com.example.demo.entity.QDayOffEntity;
import com.example.demo.entity.QUserEntity;
import com.example.demo.repo.custom.UserRepoCustom;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.transaction.Transactional;
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
				,qCommutingEntity.start.goe(startOfDay)
				,qCommutingEntity.start.loe(startOfTomorrow)
			)
		.leftJoin(qDayOffEntity).on(
				qUserEntity.accountId.eq(qDayOffEntity.user.accountId)
				,qDayOffEntity.start.isNotNull()
				,qDayOffEntity.start.eq(today)
			)
		.where(qUserEntity.name.contains(name.trim()), qUserEntity.delYn.eq("N"))
		.fetch();
		
	}

	@Override
	public List<UserDto.Response> findContainsName(String name) {
		QUserEntity qUserEntity = QUserEntity.userEntity;
		
		return jpaQueryFactory.select(
				Projections.bean(UserDto.Response.class
					, qUserEntity.accountId, qUserEntity.email, qUserEntity.auth, qUserEntity.name, qUserEntity.team, qUserEntity.position, qUserEntity.rank
				))
			.from(qUserEntity)
			.where(qUserEntity.name.contains(name.trim()), qUserEntity.delYn.eq("N"))
			.fetch();
	}

	@Override
	public UserDto.Response findUserById(Long accountId) {
		QUserEntity qUserEntity = QUserEntity.userEntity;
		
		return jpaQueryFactory.select(
				Projections.bean(UserDto.Response.class
					, qUserEntity.accountId, qUserEntity.email, qUserEntity.auth, qUserEntity.name, qUserEntity.team, qUserEntity.position, qUserEntity.rank
				))
			.from(qUserEntity)
			.where(qUserEntity.accountId.eq(accountId), qUserEntity.delYn.eq("N"))
			.fetchFirst();
	}

	@Override
	public Response findByEmailAndPwdAndDelYn(String email, String pwd, String delYn) {
		QUserEntity qUserEntity = QUserEntity.userEntity;
		
		return jpaQueryFactory.select(
				Projections.bean(UserDto.Response.class
					, qUserEntity.accountId, qUserEntity.email, qUserEntity.auth, qUserEntity.name, qUserEntity.team, qUserEntity.position, qUserEntity.rank
				))
			.from(qUserEntity)
			.where(qUserEntity.email.eq(email), qUserEntity.pwd.eq(pwd) ,qUserEntity.delYn.eq("N"))
			.fetchFirst();
	}

	@Override
	public Response findByAccountIdAndPwdAndDelYn(Long accountId, String pwd, String delYn) {
		QUserEntity qUserEntity = QUserEntity.userEntity;
		
		return jpaQueryFactory.select(
				Projections.bean(UserDto.Response.class
					, qUserEntity.accountId, qUserEntity.email, qUserEntity.auth, qUserEntity.name, qUserEntity.team, qUserEntity.position, qUserEntity.rank
				))
			.from(qUserEntity)
			.where(qUserEntity.accountId.eq(accountId), qUserEntity.pwd.eq(pwd) ,qUserEntity.delYn.eq("N"))
			.fetchFirst();
	}

	@Override
	@Transactional
	public long updatePwd(Long accountId, String pwd) {
		QUserEntity qUserEntity = QUserEntity.userEntity;
		
		return jpaQueryFactory.update(qUserEntity)
			.set(qUserEntity.pwd, pwd)
			.where(qUserEntity.accountId.eq(accountId), qUserEntity.delYn.eq("N"))
			.execute();
	}
	
	
}
