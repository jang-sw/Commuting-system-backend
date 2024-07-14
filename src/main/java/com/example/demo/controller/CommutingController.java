package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.config.Constant;
import com.example.demo.dto.CommutingDto;
import com.example.demo.dto.DayOffDto;
import com.example.demo.dto.ResponseDto;
import com.example.demo.dto.UserDto;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class CommutingController extends BaseController{
	
	/**
	 * 오늘 나의 근무 정보 불러오기
	 *  <pre> /api/commute/today </pre>
	 * @param 
	 * */
	@GetMapping("/api/commute/today")
	public ResponseEntity<ResponseDto> today(HttpServletRequest httpServletRequest){
		ResponseDto responseDto = new ResponseDto(1);
		try {
			DayOffDto.DayOffData dayOff = dayOffService.getTodayDayOff(Long.parseLong(httpServletRequest.getHeader("accountId")));
			CommutingDto.TodayCommuting commuting = commutingService.getTodayCommuting(httpServletRequest.getHeader("accountId"));
			responseDto.setData(new CommutingDto.Today(commuting, dayOff));
			
		} catch (Exception e) {
			responseDto.setResult(-1);
			e.printStackTrace();
		}
		return ResponseEntity.ok(responseDto);
	}
	
	/**
	 * 이름으로 근무 정보 검색하기
	 *  <pre> /api/commute/todayByName </pre>
	 * @param 
	 * */
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
	
	/**
	 * 근무 기록 검색하기
	 * <pre> /api/commute/history </pre>
	 * @param 
	 * */
	@GetMapping("/api/commute/history")
	public ResponseEntity<ResponseDto> history(HttpServletRequest httpServletRequest, int page){
		ResponseDto responseDto = new ResponseDto(1);
		try {
			List<CommutingDto.CommutingData> hist = commutingService.getCommutingHistory(page - 1, Long.parseLong(httpServletRequest.getHeader("accountId")));
			Long maxPage = commonUtil.getMaxPage(commutingService.getHistorySize(Long.parseLong(httpServletRequest.getHeader("accountId"))), Constant.HISTORY_PAGE_SIZE);
			responseDto.setData(new CommutingDto.History(hist, maxPage));
		} catch (Exception e) {
			responseDto.setResult(-1);
			e.printStackTrace();
		}
		return ResponseEntity.ok(responseDto);
	}
	
	/**
	 * 관리자 기능 : 이름으로 근무 기록 검색하기
	 * <pre> /adminApi/commute/history </pre>
	 * @param 
	 * */
	@GetMapping("/adminApi/commute/history")
	public ResponseEntity<ResponseDto> historyByUser(HttpServletRequest httpServletRequest, int page, String name){
		ResponseDto responseDto = new ResponseDto(1);
		try {
			List<Long> accountIds = userService.getUserList(name)
			.stream().map(UserDto.Response::getAccountId).collect(Collectors.toList());
			List<CommutingDto.CommutingDataWithUser> hist = commutingService.getCommutingHistoryIn(page - 1, accountIds);
			Long maxPage = commonUtil.getMaxPage(commutingService.getHistorySizeIn(accountIds), Constant.HISTORY_PAGE_SIZE);
			responseDto.setData(new CommutingDto.HistoryWithUser(hist, maxPage));
		} catch (Exception e) {
			responseDto.setResult(-1);
			e.printStackTrace();
		}
		return ResponseEntity.ok(responseDto);
	}
	
	/**
	 * 근무 상태 변경 (근무중)
	 * <pre> /api/commute/clockIn </pre>
	 * @param 
	 * */
	@PostMapping("/api/commute/clockIn")
	public ResponseEntity<ResponseDto> clockIn(HttpServletRequest httpServletRequest){
		return ResponseEntity.ok(new ResponseDto(commutingService.clockIn(httpServletRequest.getHeader("accountId"))));
	}
	
	/**
	 * 근무 상태 변경 (퇴근)
	 * <pre> /api/commute/clockOut </pre>
	 * @param 
	 * */
	@PostMapping("/api/commute/clockOut")
	public ResponseEntity<ResponseDto> clockOut(HttpServletRequest httpServletRequest){
		return ResponseEntity.ok(new ResponseDto(commutingService.clockOut(httpServletRequest.getHeader("accountId"))));
	}
	
	/**
	 * 근무 상태 변경 (외출)
	 * <pre> /api/commute/outing </pre>
	 * @param 
	 * */
	@PostMapping("/api/commute/outing")
	public ResponseEntity<ResponseDto> outing(HttpServletRequest httpServletRequest){
		return ResponseEntity.ok(new ResponseDto(commutingService.updateState(httpServletRequest.getHeader("accountId"), "OUTING")));
	}
	
	/**
	 * 근무 상태 변경 (외출 복귀)
	 * <pre> /api/commute/restart </pre>
	 * @param 
	 * */
	@PostMapping("/api/commute/restart")
	public ResponseEntity<ResponseDto> restart(HttpServletRequest httpServletRequest){
		return ResponseEntity.ok(new ResponseDto(commutingService.updateState(httpServletRequest.getHeader("accountId"), "START")));
	}
	
}
