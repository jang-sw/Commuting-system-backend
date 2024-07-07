package com.example.demo.dto;

import com.example.demo.entity.UserEntity;

import lombok.Data;

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
	public static class LoginResponse{
		Long accountId;
		String email;
		String auth;
		String name;
		String team;
		String position;
		String rank;
		
		public LoginResponse (UserEntity userEntity) {
			this.accountId = userEntity.getAccountId();
			this.email = userEntity.getEmail();
			this.auth = userEntity.getAuth();
			this.name = userEntity.getName();
			this.team = userEntity.getTeam();
			this.position = userEntity.getPosition();
			this.rank = userEntity.getRank();
		}
	}
}
