package com.gm2.pvd.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
	
	private long id;
	
	@NotBlank(message = "Campo name obrigatório")
	private String name;
	
	@NotBlank(message = "Campo userName obrigatório")
	private String userName;
	
	@NotBlank(message = "Campo password obrigatório")
	private String password;
	
	private boolean isEnabled;
	
}
