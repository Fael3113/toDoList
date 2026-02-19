package dev.rafael.todolist.controller;

import dev.rafael.todolist.model.TaskModel;
import dev.rafael.todolist.service.TaskService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {
	private final TaskService taskService;

	public TaskController(TaskService taskService) {
		this.taskService = taskService;
	}

	@GetMapping("/verTask")
	public ResponseEntity view(HttpServletRequest request){
		var tasksView = this.taskService.listar(request);
		return ResponseEntity.ok(tasksView);

	}

	@PostMapping("/criarTask")
	public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request){
		var taskCreated = this.taskService.createTask(taskModel, request);
		return ResponseEntity.status(HttpStatus.CREATED).body(taskCreated);

	}

	@PutMapping("/atualizarTask{id}")
	public ResponseEntity update(@RequestBody TaskModel taskModel, HttpServletRequest request , @PathVariable UUID id) {
		var taskUpdated = this.taskService.update(taskModel, request, id);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(taskUpdated);

	}

	@PatchMapping("/patchTask{id}")
	public ResponseEntity patch(@RequestBody TaskModel taskModel, HttpServletRequest request, @PathVariable UUID id){
		try {
			var taskPatched = this.taskService.patch(taskModel, request, id);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(taskPatched);
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
}
