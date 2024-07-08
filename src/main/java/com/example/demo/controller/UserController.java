package com.example.demo.controller;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ResponseDto;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.UserEntity;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class UserController extends BaseController{
 
	
	@GetMapping("/adminApi/account/byName")
	public ResponseEntity<ResponseDto> byName(HttpServletRequest httpServletRequest, String name){
		ResponseDto responseDto = new ResponseDto(1);
		try { 
			responseDto.setData(userService.getUserList(name));
		} catch (Exception e) {
			responseDto.setResult(-1);
			e.printStackTrace();
		}
		return ResponseEntity.ok(responseDto);
	}
	@GetMapping("/api/account/chkUserInfo")
	public ResponseEntity<ResponseDto> chkUserInfo(HttpServletRequest httpServletRequest, UserDto.ChkData user){
		ResponseDto responseDto = new ResponseDto(1);
		try { 
			responseDto.setData(userService.chKUserData(Long.parseLong(httpServletRequest.getHeader("accountId")), user.getEmail(), user.getAuth(), user.getName()));
		} catch (Exception e) {
			responseDto.setResult(-1);
			e.printStackTrace();
		}
		return ResponseEntity.ok(responseDto);
	}
	@PostMapping("/openApi/account/create")
	public ResponseEntity<ResponseDto> createUser( UserDto.CreateRequest user){
		return ResponseEntity.ok(new ResponseDto(userService.createUser(user)));
	}
	
	@PostMapping("/openApi/account/login")
	public ResponseEntity<ResponseDto> login( UserDto.LoginRequest user, HttpServletRequest httpServletRequest){
		ResponseDto responseDto = new ResponseDto();
		UserEntity userEntity = userService.login(user);
		String auth = userEntity != null ?
				cryptoUtil.getToken(UUID.randomUUID() + "_" + userEntity.getAccountId() + "_" + userEntity.getAuth() + "_" + httpServletRequest.getRequestedSessionId()) : null;
		
		if(StringUtils.isBlank(auth)) {
			responseDto.setResult(-1);
			return ResponseEntity.ok(responseDto);
		}
		responseDto.setResult(1);
		responseDto.setData(new UserDto.Response(userEntity));
		return ResponseEntity.ok().header("Authorization", auth).body(responseDto);
	}
	
	@PostMapping("/openApi/account/refresh")
	public ResponseEntity<ResponseDto> refresh(HttpServletRequest httpServletRequest){
		String newToken = userService.refreshToken(httpServletRequest.getHeader("Authorization"), httpServletRequest.getHeader("accountId"), httpServletRequest.getRequestedSessionId());
		ResponseDto responseDto = new ResponseDto();
		if(StringUtils.isBlank(newToken)) {
			responseDto.setResult(-1);
			return ResponseEntity.ok(responseDto);
		}	
		responseDto.setResult(1);
		return ResponseEntity.ok().header("Authorization", newToken).body(responseDto);
	}
	
	
}
