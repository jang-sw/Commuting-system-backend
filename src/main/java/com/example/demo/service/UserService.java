package com.example.demo.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.UserEntity;
import com.example.demo.repo.UserRepo;
import com.example.demo.util.CryptoUtil;

@Service
public class UserService {

	@Autowired UserRepo userRepo;
	@Autowired CryptoUtil cryptoUtil;
	
	public List<UserEntity> findAll() {
		return this.userRepo.findAllNotDeleted();
	}
	
	public int createUser(UserDto.CreateRequest user) {
		try {
			user.setPwd(cryptoUtil.encodeSHA512(user.getPwd()));
			this.userRepo.save(new UserEntity(user));
			return 1;
		} catch(DataIntegrityViolationException dive) {
			 if(dive.getMessage().contains("Duplicate entry")) {
				 return -2;
			 } else {
				 dive.printStackTrace();
				 return -1;
			 }
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public UserEntity login(UserDto.LoginRequest user) {
		return userRepo.findByEmailAndPwdAndDelYn(user.getEmail(), cryptoUtil.encodeSHA512(user.getPwd()), "N");
	}
}
