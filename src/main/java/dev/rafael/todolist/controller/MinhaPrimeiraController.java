package dev.rafael.todolist.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/primeiraRota") //Não é completamente necessário, mas é uma boa prática com o intuito de:
// agrupar endpoint, evitar repetir prefixos e aplicar configurações comuns
//localhost:8080/primeiraRota
public class MinhaPrimeiraController {

	//localhost:8080/primeiraRota/primeiraMensagem
	@GetMapping("/primeiraMensagem")
	public String primeiraMensagem(){
		return "Olá";
	}
}