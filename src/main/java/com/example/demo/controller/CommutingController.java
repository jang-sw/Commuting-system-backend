package com.example.demo.controller;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.config.Constant;
import com.example.demo.dto.CommutingDto;
import com.example.demo.dto.ResponseDto;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.CommutingEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.service.CommutingService;
import com.example.demo.service.UserService;
import com.example.demo.util.CryptoUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class CommutingController extends BaseController{
	
	@GetMapping("/api/commute/today")
	public ResponseEntity<ResponseDto> today(HttpServletRequest httpServletRequest){
		ResponseDto responseDto = new ResponseDto(1);
		try {
			responseDto.setData(commutingService.getTodayCommuting(httpServletRequest.getHeader("accountId")));
		} catch (Exception e) {
			responseDto.setResult(-1);
			e.printStackTrace();
		}
		return ResponseEntity.ok(responseDto);
	}
	
	@GetMapping("/api/commute/todayByName")
	public ResponseEntity<ResponseDto> todayByName(HttpServletRequest httpServletRequest, String name){
		ResponseDto responseDto = new ResponseDto(1);
		try { 
			responseDto.setData(userService.getTodayCommutingByName(name));
		} catch (Exception e) {
			responseDto.setResult(-1);
			e.printStackTrace();
		}
		return ResponseEntity.ok(responseDto);
	}
	
	@GetMapping("/api/commute/history")
	public ResponseEntity<ResponseDto> history(HttpServletRequest httpServletRequest, int page){
		ResponseDto responseDto = new ResponseDto(1);
		try {
			List<CommutingDto.CommutingData> hist = commutingService.getCommutingHistory(page, httpServletRequest.getHeader("accountId"));
			Long maxPage = commonUtil.getMaxPage(commutingService.getHistorySize(httpServletRequest.getHeader("accountId")), Constant.HISTORY_PAGE_SIZE);
			responseDto.setData(new CommutingDto.History(hist, maxPage));
		} catch (Exception e) {
			responseDto.setResult(-1);
			e.printStackTrace();
		}
		return ResponseEntity.ok(responseDto);
	}
	
	@PostMapping("/api/commute/clockIn")
	public ResponseEntity<ResponseDto> clockIn(HttpServletRequest httpServletRequest){
		return ResponseEntity.ok(new ResponseDto(commutingService.clockIn(httpServletRequest.getHeader("accountId"))));
	}
	
	@PostMapping("/api/commute/clockOut")
	public ResponseEntity<ResponseDto> clockOut(HttpServletRequest httpServletRequest){
		return ResponseEntity.ok(new ResponseDto(commutingService.clockOut(httpServletRequest.getHeader("accountId"))));
	}
	
	@PostMapping("/api/commute/outing")
	public ResponseEntity<ResponseDto> outing(HttpServletRequest httpServletRequest){
		return ResponseEntity.ok(new ResponseDto(commutingService.updateState(httpServletRequest.getHeader("accountId"), "OUTING")));
	}
	
	@PostMapping("/api/commute/restart")
	public ResponseEntity<ResponseDto> restart(HttpServletRequest httpServletRequest){
		return ResponseEntity.ok(new ResponseDto(commutingService.updateState(httpServletRequest.getHeader("accountId"), "START")));
	}
	
}
