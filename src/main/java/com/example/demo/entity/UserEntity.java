package com.example.demo.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.example.demo.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_user")
@Data
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate 
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "account_id", nullable = false, unique = true)
	Long accountId;

	@Column(name = "name")
	String name;

	@Column(name = "pwd")
	String pwd;

	@Column(name = "email")
	String email;
  
	@Column(name = "team")
	String team;
  
	@Column(name = "position") 
	String position;
	
	@Column(name = "rank")
	String rank;
	
	@Column(name = "del_yn")
	String delYn;
	
	@Column(name = "auth")
	String auth;
	
	@JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	@Column(name = "created")
	LocalDateTime created;
	
	@JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	@Column(name = "updated")
	LocalDateTime updated;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	List<DayOffEntity> dayOffList;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user") 
	List<CommutingEntity> commutingList;
	
	public UserEntity(UserDto.CreateRequest user) {
		this.email = user.getEmail();
		this.pwd = user.getPwd();
		this.name = user.getName();
		this.team = user.getTeam();
		this.position = user.getPosition();
		this.rank = user.getRank();
	}
	public UserEntity(Long accountId) {
		this.accountId = accountId;
	}
	
}
