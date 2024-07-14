package com.example.demo.controller;

import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.DayOffDto;
import com.example.demo.dto.ResponseDto;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.DayOffEntity;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class DayOffController extends BaseController{
	
	/**
	 * 휴가 일정 생성
	 * <pre> /api/dayOff/create </pre>
	 *  
	 * @param user (구분, 사유, 시작일, 종료일)
	 * */
	@PostMapping("/api/dayOff/create")
	public ResponseEntity<ResponseDto> create( DayOffDto.DayOffData user, HttpServletRequest httpServletRequest ){
		
		return ResponseEntity.ok(
			new ResponseDto(
					dayOffService.createDayOff(
						new DayOffEntity(Long.parseLong(httpServletRequest.getHeader("accountId"))
							, user.getCategory(), user.getReason(), user.getStart(), user.getEnd())
					)
				)
		);
	}
	
	/**
	 * 휴가 취소
	 * <pre> /api/dayOff/cancel </pre>
	 * @param 
	 * */
	@PostMapping("/api/dayOff/cancel")
	public ResponseEntity<ResponseDto> cancel( Long dayOffId, HttpServletRequest httpServletRequest ){
		
		return ResponseEntity.ok(
			new ResponseDto(
					dayOffService.cancelDayOff(dayOffId, Long.parseLong(httpServletRequest.getHeader("accountId")))
				)
		);
	}
	
	/**
	 * 휴가 기록 리스트 불러오기
	 * <pre> /api/dayOff/history </pre>
	 * @param 
	 * */
	@GetMapping("/api/dayOff/history")
	public ResponseEntity<ResponseDto> dayOffHistory(HttpServletRequest httpServletRequest, int page){
		ResponseDto responseDto = new ResponseDto(1);
		try {
			DayOffDto.DayOffHistory data = dayOffService.getHistData(Long.parseLong(httpServletRequest.getHeader("accountId")), page - 1);
			responseDto.setData(data);
		} catch (Exception e) {
			responseDto.setResult(-1);
			e.printStackTrace();
		}
		return ResponseEntity.ok(responseDto);
	}
	
	/**
	 * 관리자 기능 : 이름으로 휴가 기록 검색하기
	 * <pre> /adminApi/dayOff/history </pre>
	 * @param 
	 * */
	@GetMapping("/adminApi/dayOff/history")
	public ResponseEntity<ResponseDto> dayOffHistoryByUser(HttpServletRequest httpServletRequest, int page, String name){
		ResponseDto responseDto = new ResponseDto(1);
		try {DayOffDto.DayOffHistoryWithUser data = dayOffService.getHistDataAdmin(userService.getUserList(name)
					.stream().map(UserDto.Response::getAccountId).collect(Collectors.toList()), page - 1);
			responseDto.setData(data);
		} catch (Exception e) {
			responseDto.setResult(-1);
			e.printStackTrace();
		}
		return ResponseEntity.ok(responseDto);
	}
}
