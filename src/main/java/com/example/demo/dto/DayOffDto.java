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
import lombok.NoArgsConstructor;

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
		public static class DayOffDataWithUser{
			
			Long dayOffId;
			String category;
			String reason;
			String name;
			String team;
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
		@NoArgsConstructor
		public static class DayOffHistory{
			Double thisMonthUse;
			Double thisYearUse;
			
			List<DayOffDto.DayOffData> hist;
			Long maxPage;
			
			public DayOffHistory(List<DayOffDto.DayOffData> hist,Long maxPage ) {
				this.hist = hist;
				this.maxPage = maxPage;
			}
			
		}
		@Data
		@AllArgsConstructor
		@NoArgsConstructor
		public static class DayOffHistoryWithUser{
	
			List<DayOffDto.DayOffDataWithUser> hist;
			Long maxPage;
			
		}
		@Data
		public static class UsedDayOff{
			Long thisMonthUse;
			Long thisYearUse;
		}
	
}
