package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.UserEntity;
import com.example.demo.repo.UserRepo;

@Service
public class UserService {

	@Autowired UserRepo userRepo;
	
	public List<UserEntity> findAll() {
		return this.userRepo.findAllNotDeleted();
	}
	
}
