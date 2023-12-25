package com.gm2.pvd.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {

	@NotBlank(message = "campo username obrigatório")
	private String userName;
	@NotBlank(message = "campo senha obrigatório")
	private String password;
}
