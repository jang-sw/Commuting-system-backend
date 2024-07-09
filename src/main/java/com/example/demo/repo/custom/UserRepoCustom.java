package com.example.demo.repo.custom;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.dto.UserDto;


@Repository
public interface UserRepoCustom {

	List<UserDto.TodayCommute> findTodayCommuting(String name);
	List<UserDto.Response> findContainsName(String name);
	UserDto.Response findUserById(Long accountId);
	UserDto.Response findByEmailAndPwdAndDelYn(String email, String pwd, String delYn);
	UserDto.Response findByAccountIdAndPwdAndDelYn(Long accountId, String pwd, String delYn);
	long updatePwd(Long accountId, String pwd);
}
