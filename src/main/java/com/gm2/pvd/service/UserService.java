package com.gm2.pvd.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gm2.pvd.Exception.InvalidOperationException;
import com.gm2.pvd.dto.UserDTO;
import com.gm2.pvd.dto.UserResponseDTO;
import com.gm2.pvd.entity.User;
import com.gm2.pvd.repository.UserRepository;
import com.gm2.pvd.security.SecurityConfig;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	private ModelMapper mapper = new ModelMapper();
	
	public List<UserResponseDTO> findAll(){
		return userRepository.findAll().stream().map(user -> 
			new UserResponseDTO(user.getId(), user.getName(), user.getUserName(), user.isEnabled())).collect(Collectors.toList());
	}
	
	public UserDTO save(UserDTO userDTO) {
		userDTO.setPassword(SecurityConfig.passwordEncoder().encode(userDTO.getPassword()));
		User userToSave = mapper.map(userDTO, User.class);
		userRepository.save(userToSave);
		return new UserDTO(userToSave.getId(),userToSave.getName(), userToSave.getUserName(), userToSave.getPassword(),userToSave.isEnabled());
	}
	
	public UserDTO findById(long id) {
		Optional<User> optional = userRepository.findById(id);
		
		if (!optional.isPresent()) {
			throw new InvalidOperationException("Usuário não encontrado!");
		}
		
		User user = optional.get();
		
		return new UserDTO(user.getId(), user.getName(), user.getUserName(), user.getPassword(), user.isEnabled());
	}
	
	public UserDTO update(UserDTO user) {
		user.setPassword(SecurityConfig.passwordEncoder().encode(user.getPassword()));
		Optional<User> userEdit = userRepository.findById(user.getId());
		
		if (!userEdit.isPresent()) {
			throw new InvalidOperationException("Usuário não encontrado!");
		}
		
		userRepository.save(mapper.map(user, User.class));
		
		return new UserDTO(user.getId(), user.getName(), user.getUserName(), user.getPassword(), user.isEnabled());
	}
	
	public void deleteById(long id) {
		userRepository.deleteById(id);
	}
    public User getByUserName(String username){
        return userRepository.findUserByUsername(username);
    }
}
