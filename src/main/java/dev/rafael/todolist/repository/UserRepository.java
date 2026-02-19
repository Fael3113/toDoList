package dev.rafael.todolist.repository;

import dev.rafael.todolist.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserModel, UUID> {
	UserModel findByUsername(String username);
}
