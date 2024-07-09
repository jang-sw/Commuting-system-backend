package com.example.demo.dto;

import java.util.List;

import com.example.demo.entity.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class UserDto {

	@Data
	public static class CreateRequest{
		String email;
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
	public static class ChangePwd{
		String newPwd;
		String currentPwd;
	}
	@Data
	public static class ChkData{
		String email;
		String auth;
		String name;
	}
	@Data
	public static class UpdateUser{
		Long accountId;
		String name;
		String team;
		String position;
		String email;
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
	@AllArgsConstructor
	public static class UserList{
		List<UserDto.Response> list;
		Long maxPage;
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
