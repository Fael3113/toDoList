package dev.rafael.todolist.controller;

import at.favre.lib.crypto.bcrypt.BCrypt;
import dev.rafael.todolist.model.UserModel;
import dev.rafael.todolist.repository.UserRepository;
import dev.rafael.todolist.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
/*
	Tutoriais:
	@Autowired
	private IUserRepository userRepository;
*/
	//Boa Prática:
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/verUser")
	public ResponseEntity view(UserModel userModel, HttpServletRequest request){
		var userView = this.userService.listar(request);
		return ResponseEntity.status(HttpStatus.FOUND).body(userView);
	}

	@PostMapping("/criarUser")
	//@RequestBody é um indicativo para entrada de dados no compartimento body do postman
	public ResponseEntity create(@RequestBody UserModel userModel){
		var userCreated = this.userService.createUser(userModel);
		return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
	}
}
