package com.example.demo.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_commuting")
@Data
@DynamicInsert
@DynamicUpdate 
@NoArgsConstructor
public class CommutingEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "commuting_id")
	Long commutingId;

	@Column(name = "state")
	String state;
  
	@JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	@Column(name = "start")
	LocalDateTime start;
	
	@JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	@Column(name = "end")
	LocalDateTime end;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "account_id")
	UserEntity user;
	
	public CommutingEntity (Long accountId, String state, LocalDateTime time) {
		if(user == null)user = new UserEntity();
		this.user.setAccountId(accountId);
		this.state = state;
		if(state.equals("START")) this.start = time;
		else if(state.equals("END")) this.end = time;
	}
	public CommutingEntity (Long commutingId, Long accountId, String state, LocalDateTime time) {
		if(user == null)user = new UserEntity();
		this.user.setAccountId(accountId);
		this.commutingId = commutingId;
		this.state = state;
		if(state.equals("START")) this.start = time;
		else if(state.equals("END")) this.end = time;
	}
	public CommutingEntity (Long commutingId, Long accountId, String state) {
		if(user == null)user = new UserEntity();
		this.user.setAccountId(accountId);
		this.state = state;
		this.commutingId = commutingId;
	}
}
