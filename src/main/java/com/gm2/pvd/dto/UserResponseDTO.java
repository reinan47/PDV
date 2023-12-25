package com.gm2.pvd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
	
	private long id;
	
	private String name;
	
	private String userName;
		
	private boolean isEnabled;
	
}
