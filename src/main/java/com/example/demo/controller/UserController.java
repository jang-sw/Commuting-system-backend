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
 
	/**
	 * 관리자 기능 : 이름으로 유저 목록 검색
	 * <pre> /adminApi/account/byName </pre>
	 * @param 
	 * */
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
	/**
	 * 세션체크를 위한 인증용 api
	 * <pre> /api/account/chkUserInfo </pre>
	 * @param user (email, auth, name)
	 * */
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
	/**
	 * 관리자 기능 : 유저 추가
	 * <pre> /adminApi/account/create </pre>
	 * @param 
	 * */
	@PostMapping("/adminApi/account/create")
	public ResponseEntity<ResponseDto> createUser( UserDto.CreateRequest user){
		return ResponseEntity.ok(new ResponseDto(userService.createUser(user)));
	}
	/**
	 * 미인증 API : 로그인
	 * <pre> /openApi/account/login </pre>
	 * @param user (email, pwd)
	 * */
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
	
	/**
	 * 미인증 API : 토큰 refresh
	 * <pre> /openApi/account/refresh </pre>
	 * @param 
	 * */
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
	
	/**
	 * 비밀번호 변경
	 * <pre> /api/account/changePwd </pre>
	 * @param changePwd (newPwd, currentPwd)
	 * */
	@PostMapping("/api/account/changePwd")
	public ResponseEntity<ResponseDto> changePwd(HttpServletRequest httpServletRequest, UserDto.ChangePwd changePwd){
		return ResponseEntity.ok(new ResponseDto(userService.changePwd(Long.parseLong(httpServletRequest.getHeader("accountId")), changePwd)));
	}
	/**
	 * 관리자 기능 : 비밀번호 초기화
	 * <pre> /adminApi/account/resetPwd </pre>
	 * @param 
	 * */
	@PostMapping("/adminApi/account/resetPwd")
	public ResponseEntity<ResponseDto> resetPwd(HttpServletRequest httpServletRequest, Long accountId){
		return ResponseEntity.ok(new ResponseDto(userService.resetPwd(accountId)));
	}
	/**
	 * 관리자 기능 : 유저 데이터 업데이트
	 * <pre> /adminApi/account/update </pre>
	 * @param 
	 * */
	@PostMapping("/adminApi/account/update")
	public ResponseEntity<ResponseDto> updateUser(HttpServletRequest httpServletRequest, UserDto.UpdateUser user){
		return ResponseEntity.ok(new ResponseDto(userService.updateUser(user)));
	}
	/**
	 * 관리자 기능 : 유저 제거
	 * <pre> /adminApi/account/delete </pre>
	 * @param 
	 * */
	@PostMapping("/adminApi/account/delete")
	public ResponseEntity<ResponseDto> deleteUser(HttpServletRequest httpServletRequest, Long accountId){
		return ResponseEntity.ok(new ResponseDto(userService.deleteUser(accountId)));
	}
}
