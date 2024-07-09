package com.example.demo.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import lombok.AllArgsConstructor;
import lombok.Data;

public class CommutingDto {

	@Data
	public static class TodayCommuting{
		String state;
		@JsonSerialize(using = LocalDateTimeSerializer.class)
	    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
		LocalDateTime start;
		@JsonSerialize(using = LocalDateTimeSerializer.class)
	    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
		LocalDateTime end;
	}
	
	@Data
	@AllArgsConstructor
	public static class Today{ 
		CommutingDto.TodayCommuting commuting;
		DayOffDto.DayOffData dayOff;
		
	}
	
	@Data
	@AllArgsConstructor
	public static class History{
		List<CommutingDto.CommutingData> hist;
		long maxPage;
	}
	@Data
	@AllArgsConstructor
	public static class HistoryWithUser{
		List<CommutingDto.CommutingDataWithUser> hist;
		long maxPage;
	}
	@Data
	public static class CommutingData{
		Long commutingId;
		
		String state;
		@JsonSerialize(using = LocalDateTimeSerializer.class)
	    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
		LocalDateTime start;
		
		@JsonSerialize(using = LocalDateTimeSerializer.class)
	    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
		LocalDateTime end;
		
	}
	
	@Data
	public static class CommutingDataWithUser{
		Long commutingId;
		String name;
		String position;
		String team;
		String email;
		String state;
		@JsonSerialize(using = LocalDateTimeSerializer.class)
	    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
		LocalDateTime start;
		
		@JsonSerialize(using = LocalDateTimeSerializer.class)
	    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
		LocalDateTime end;
		
	}
}
