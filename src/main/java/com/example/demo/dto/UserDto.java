package com.example.demo.dto;

import com.example.demo.entity.UserEntity;

import lombok.Data;
import lombok.NoArgsConstructor;

public class UserDto {

	@Data
	public static class CreateRequest{
		String email;
		String pwd;
		String name;
		String team;
		String position;
		String rank;
	}
	
	@Data
	public static class LoginRequest{
		String email;
		String pwd;
	}
	
	@Data
	@NoArgsConstructor
	public static class Response{
		Long accountId;
		String email;
		String auth;
		String name;
		String team;
		String position;
		String rank;
		
		public Response (UserEntity userEntity) {
			this.accountId = userEntity.getAccountId();
			this.email = userEntity.getEmail();
			this.auth = userEntity.getAuth();
			this.name = userEntity.getName();
			this.team = userEntity.getTeam();
			this.position = userEntity.getPosition();
			this.rank = userEntity.getRank();
		}
	}
	
	@Data
	public static class TodayCommute{
		String email;
		String auth;
		String name;
		String team;
		String position;
		String rank;
		
		//현재 근무 상태
		String state;
		//휴가 구분
		String category;
		
	}
}
