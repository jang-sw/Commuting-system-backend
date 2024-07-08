package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.DayOffDto;
import com.example.demo.dto.ResponseDto;
import com.example.demo.entity.DayOffEntity;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class DayOffController extends BaseController{
	
	@PostMapping("/api/dayOff/create")
	public ResponseEntity<ResponseDto> createUser( DayOffDto.DayOffData user, HttpServletRequest httpServletRequest ){
		
		return ResponseEntity.ok(
			new ResponseDto(
					dayOffService.createDayOff(
						new DayOffEntity(Long.parseLong(httpServletRequest.getHeader("accountId"))
							, user.getCategory(), user.getReason(), user.getStart(), user.getEnd())
					)
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
	
	@GetMapping("/adminApi/dayOff/history")
	public ResponseEntity<ResponseDto> dayOffHistoryByUser(HttpServletRequest httpServletRequest, int page, Long accountId){
		ResponseDto responseDto = new ResponseDto(1);
		try {
			DayOffDto.DayOffHistory data = dayOffService.getHistData(accountId, page - 1);
			responseDto.setData(data);
		} catch (Exception e) {
			responseDto.setResult(-1);
			e.printStackTrace();
		}
		return ResponseEntity.ok(responseDto);
	}
}
