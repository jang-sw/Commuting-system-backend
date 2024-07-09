package com.example.demo.controller;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.config.Constant;
import com.example.demo.dto.ResponseDto;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.UserEntity;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class UserController extends BaseController{
 
	
	@GetMapping("/adminApi/account/byName")
	public ResponseEntity<ResponseDto> byName(HttpServletRequest httpServletRequest, String name, int page){
		ResponseDto responseDto = new ResponseDto(1);
		try { 
			responseDto.setData(new UserDto.UserList(userService.getUserListWithPage(page - 1,name), commonUtil.getMaxPage(userService.getUserCnt(name), Constant.HISTORY_PAGE_SIZE)));
			
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
		UserDto.Response userEntity = userService.login(user);
		String auth = userEntity != null ?
				cryptoUtil.getToken(UUID.randomUUID() + "_" + userEntity.getAccountId() + "_" + userEntity.getAuth() + "_" + httpServletRequest.getRequestedSessionId()) : null;
		
		if(StringUtils.isBlank(auth)) {
			responseDto.setResult(-1);
			return ResponseEntity.ok(responseDto);
		}
		responseDto.setResult(1);
		responseDto.setData(userEntity);
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
	@PostMapping("/api/account/changePwd")
	public ResponseEntity<ResponseDto> changePwd(HttpServletRequest httpServletRequest, UserDto.ChangePwd changePwd){
		return ResponseEntity.ok(new ResponseDto(userService.changePwd(Long.parseLong(httpServletRequest.getHeader("accountId")), changePwd)));
	}
	@PostMapping("/adminApi/account/resetPwd")
	public ResponseEntity<ResponseDto> resetPwd(HttpServletRequest httpServletRequest, Long accountId){
		return ResponseEntity.ok(new ResponseDto(userService.resetPwd(accountId)));
	}
	@PostMapping("/adminApi/account/update")
	public ResponseEntity<ResponseDto> updateUser(HttpServletRequest httpServletRequest, UserDto.UpdateUser user){
		return ResponseEntity.ok(new ResponseDto(userService.updateUser(user)));
	}
	@PostMapping("/adminApi/account/delete")
	public ResponseEntity<ResponseDto> deleteUser(HttpServletRequest httpServletRequest, Long accountId){
		return ResponseEntity.ok(new ResponseDto(userService.deleteUser(accountId)));
	}
}
