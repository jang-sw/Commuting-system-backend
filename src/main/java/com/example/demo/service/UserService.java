package com.example.demo.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.example.demo.config.Constant;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.UserEntity;
import com.example.demo.repo.UserRepo;
import com.example.demo.util.CryptoUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Clock;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.DefaultClock;

@Service
public class UserService {

	@Autowired UserRepo userRepo;
	@Autowired CryptoUtil cryptoUtil;

	
	public int createUser(UserDto.CreateRequest user) {
		try {
			user.setPwd(cryptoUtil.encodeSHA512(user.getPwd()));
			this.userRepo.save(new UserEntity(user));
			return 1;
		} catch(DataIntegrityViolationException dive) {
			 if(dive.getMessage().contains("Duplicate entry")) {
				 return -2;
			 } else {
				 dive.printStackTrace();
				 return -1;
			 }
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public UserEntity login(UserDto.LoginRequest user) {
		return userRepo.findByEmailAndPwdAndDelYn(user.getEmail(), cryptoUtil.encodeSHA512(user.getPwd()), "N");
	}
	
	public String refreshToken(String authToken, String accountId, String sessionId) {
		try {
			Clock clock = new DefaultClock() {
	            @Override
	            public java.util.Date now() {
	                return new java.util.Date(0);
	            }
	        };
	        Claims claims = Jwts.parser()
	                .setSigningKey(Constant.SECRET_KEY)
	                .setClock(clock)  
	                .parseClaimsJws(authToken)
	                .getBody();

			String id = claims.getSubject();
			String d = cryptoUtil.AESDecrypt((String) claims.get("d"));
			
			
			if(id != null && accountId.equals(d.split("_")[1]) && ((new Date().getTime() - claims.getExpiration().getTime()) / (60000 * 1440)) <= 1) {
				UserDto.Response user = userRepo.findUserById(Long.parseLong(accountId));
				if(user == null) return null;
				return cryptoUtil.getToken(UUID.randomUUID() + "_"+ accountId + "_" + user.getAuth() + "_"+ sessionId);
	        } 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<UserDto.TodayCommute> getTodayCommutingByName(String name) throws Exception{
		return userRepo.findTodayCommuting(name);
	}
	public List<UserDto.Response> getUserList(String name) throws Exception{
		return userRepo.findContainsName(name);
	}
	public UserDto.Response getUser(Long accountId){
		return userRepo.findUserById(accountId);
	}
}
