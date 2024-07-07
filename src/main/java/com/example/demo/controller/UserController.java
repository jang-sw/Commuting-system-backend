package com.example.demo.controller;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ResponseDto;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.UserEntity;
import com.example.demo.service.UserService;
import com.example.demo.util.CryptoUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class UserController {
 
	
	@Autowired UserService userService;
	@Autowired CryptoUtil cryptoUtil;
	
	@GetMapping("/api/account/all")
	public List<UserEntity> getUserList(){
		return userService.findAll();
	}
	
	@PostMapping("/openApi/account/create")
	public ResponseEntity<ResponseDto> createUser( UserDto.CreateRequest user){
		return ResponseEntity.ok(new ResponseDto(userService.createUser(user)));
	}
	
	@PostMapping("/openApi/account/login")
	public ResponseEntity<ResponseDto> login( UserDto.LoginRequest user, HttpServletRequest httpServletRequest){
		ResponseDto responseDto = new ResponseDto();
		UserEntity userEntity = userService.login(user);
		String auth = userEntity != null && userEntity.getAccountId() != null ?
				cryptoUtil.getToken(UUID.randomUUID() + "_"+ userEntity.getAccountId() + "_" + httpServletRequest.getRequestedSessionId()) : null;
		
		if(StringUtils.isBlank(auth)) {
			responseDto.setResult(-1);
			return ResponseEntity.ok(responseDto);
		}
		responseDto.setResult(1);
		responseDto.setData(new UserDto.LoginResponse(userEntity));
		return ResponseEntity.ok().header("Authorization", auth).body(responseDto);
	}
}
