package dev.rafael.todolist.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "tb_users")
public class UserModel {

	@Id
	@GeneratedValue(generator = "UUID")
	private UUID id;

	@Column(unique = true) //Não funciona em tabela já criada, sendo nesse caso necessário fazer a alteração via pgAdmin ou dropar a tabela e recria-la pelo Spring
	private String username;
	private String name;
	private String password;

	@CreationTimestamp
	private LocalDateTime createdAt;
}
