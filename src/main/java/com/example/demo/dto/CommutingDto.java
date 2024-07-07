package com.example.demo.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

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
}
