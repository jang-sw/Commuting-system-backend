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
	@PostMapping("/api/dayOff/cancel")
	public ResponseEntity<ResponseDto> cancel( Long dayOffId, HttpServletRequest httpServletRequest ){
		
		return ResponseEntity.ok(
			new ResponseDto(
					dayOffService.cancelDayOff(dayOffId, Long.parseLong(httpServletRequest.getHeader("accountId")))
				)
		);
	}
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
	
	@GetMapping("/api/dayOff/todayDayOff")
	public ResponseEntity<ResponseDto> todayDayOff(HttpServletRequest httpServletRequest){
		ResponseDto responseDto = new ResponseDto(1);
		try {
			DayOffDto.DayOffData data = dayOffService.getTodayDayOff(Long.parseLong(httpServletRequest.getHeader("accountId")));
			responseDto.setData(data);
		} catch (Exception e) {
			responseDto.setResult(-1);
			e.printStackTrace();
		}
		return ResponseEntity.ok(responseDto);
	}
	
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
