package com.gm2.pvd.dto;

import java.util.List;

import java.util.Arrays;

import lombok.Getter;

public class ResponseDTO<T> {
	
	@Getter
	private List<String> messeges;
	public ResponseDTO(List<String> messeges) {
		this.messeges = messeges;
	}
	
	public ResponseDTO(String messeges) {
		this.messeges = Arrays.asList(messeges);
	}
	
}
