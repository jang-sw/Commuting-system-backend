package com.example.demo.repo.custom.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.entity.QUserEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.repo.custom.UserRepoCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserRepoCustomImpl implements UserRepoCustom{
	
	final JPAQueryFactory jpaQueryFactory;
	
	
}
