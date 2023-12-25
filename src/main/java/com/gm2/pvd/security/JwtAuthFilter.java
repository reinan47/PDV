package com.gm2.pvd.security;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gm2.pvd.dto.ResponseDTO;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class JwtAuthFilter extends OncePerRequestFilter{
	
	private JwtService jwtService;
	
	private CustomUserDetailService userDetailService;
	

	public JwtAuthFilter(JwtService jwtService, CustomUserDetailService userDetailService) {
		this.jwtService = jwtService;
		this.userDetailService = userDetailService;
	}


	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response,
			FilterChain filterChain) throws ServletException, IOException {
		try {
			String authorization = request.getHeader("Authorization");
			
			if(authorization != null && authorization.startsWith("Bearer"))
			{
				String token = authorization.split(" ")[1];
				String userName = jwtService.getUserName(token);
				
				UserDetails user = userDetailService.loadUserByUsername(userName);
				
				
				//cria um usuário que será inserido no contexto do sping security
				UsernamePasswordAuthenticationToken userOfContext =
						new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
				
				//configurando o spring security com uma autenticação WEB
				userOfContext.setDetails(new WebAuthenticationDetailsSource()
						.buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(userOfContext);
				
			}
			filterChain.doFilter(request, response);
		}catch(RuntimeException error) {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			response.getWriter().write(convertObjectToJASON(new ResponseDTO<>("Token inválido!")));
		}
	}
	
	public String convertObjectToJASON(ResponseDTO<?> responseDTO) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(responseDTO);
	}

}
