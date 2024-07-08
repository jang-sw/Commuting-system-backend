package com.example.demo.dto;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;

public class DayOffDto {

		@Data
		public static class DayOffData{
			
			Long dayOffId;
			String category;
			String reason;
			
			@JsonSerialize(using = LocalDateSerializer.class)
		    @JsonDeserialize(using = LocalDateDeserializer.class)
			@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
			@Column(name = "start")
			LocalDate start;
			
			@JsonSerialize(using = LocalDateSerializer.class)
		    @JsonDeserialize(using = LocalDateDeserializer.class)
			@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
			@Column(name = "end")
			LocalDate end;
		}
		
		@Data
		@AllArgsConstructor
		public static class DayOffHistory{
			Double thisMonthUse;
			Double thisYearUse;
			
			List<DayOffDto.DayOffData> hist;
			Long maxpage;
			
		}
		
		@Data
		public static class UsedDayOff{
			Long thisMonthUse;
			Long thisYearUse;
		}
	
}
