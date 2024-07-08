package com.example.demo.repo.custom;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.dto.UserDto;


@Repository
public interface UserRepoCustom {

	List<UserDto.TodayCommute> findTodayCommuting(String name);
	List<UserDto.Response> findContainsName(String name);
	UserDto.Response findUserById(Long accountId);

}
