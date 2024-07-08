package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.service.CommutingService;
import com.example.demo.service.DayOffService;
import com.example.demo.service.UserService;
import com.example.demo.util.CommonUtil;
import com.example.demo.util.CryptoUtil;

@Component
public class BaseController {
	@Autowired protected CommutingService commutingService;
	@Autowired protected UserService userService;
	@Autowired protected DayOffService dayOffService;
	
	@Autowired protected CryptoUtil cryptoUtil;
	@Autowired protected CommonUtil commonUtil;
	
}
