package dev.rafael.todolist.user;

import at.favre.lib.crypto.bcrypt.BCrypt;
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
	private final UserRepository userRepository;

	public UserController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@GetMapping("/verUser")
	public ResponseEntity view(UserModel userModel){
		var userView = this.userRepository.findAll();
		return ResponseEntity.status(HttpStatus.FOUND).body(userView);
	}

	@PostMapping("/criarUser")
	//@RequestBody é um indicativo para entrada de dados no compartimento body do postman
	public ResponseEntity create(@RequestBody UserModel userModel){
		var user = this.userRepository.findByUsername(userModel.getUsername());

		if (user != null){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já existe");
		}

		var passwordHashred = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());
		userModel.setPassword(passwordHashred);

		var userCreated = this.userRepository.save(userModel);
		return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
	}
}
