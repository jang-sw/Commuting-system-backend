package com.example.demo.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto implements Serializable {
	
	private static final long serialVersionUID = -4136681180966716688L;
	int result;
	Object data;
	
	public ResponseDto(int result) {
		this.result = result;
	}
}
