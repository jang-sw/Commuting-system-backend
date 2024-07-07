package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.UserEntity;
import com.example.demo.service.UserService;

@RestController
public class UserController {
 
	
	@Autowired UserService UserService;
	
	@GetMapping("getUserList")
	public List<UserEntity> getUserList(){
		return UserService.findAll();
	}
}
