package dev.rafael.todolist.task;

import dev.rafael.todolist.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {
	private final TaskRepository taskRepository;

	public TaskController(TaskRepository taskRepository) {
		this.taskRepository = taskRepository;
	}

	@GetMapping("/verTask")
	public ResponseEntity view(HttpServletRequest request){
		var idUser = (UUID) request.getAttribute("idUser");

		var tasksView = this.taskRepository.findByIdUser(idUser);
		if (tasksView.isEmpty()){
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Não há nenhuma tarefa");
		}

		return ResponseEntity.ok(tasksView);
	}

	@PostMapping("/criarTask")
	public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request){
		var idUser = request.getAttribute("idUser");
		taskModel.setIdUser((UUID) idUser);

		var currentDate = LocalDateTime.now();
		if (currentDate.isAfter(taskModel.getStartAt()) && currentDate.isAfter(taskModel.getEndAt())){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("A data de início/termimo deve ser maior do que a data atual");
		}
		if (taskModel.getStartAt().isAfter(taskModel.getEndAt())){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("A data de início deve ser antes da data de termino");
		}

		var taskCreate = this.taskRepository.save(taskModel);
		return ResponseEntity.status(HttpStatus.CREATED).body(taskCreate);
	}

	@PutMapping("/atualizarTask{id}")
	public ResponseEntity update(@RequestBody TaskModel taskModel, HttpServletRequest request , @PathVariable UUID id){
		var idUser = request.getAttribute("idUser");
		var task = this.taskRepository.findById(id).orElse(null);
		taskModel.setIdUser((UUID) idUser);
		taskModel.setId(id);

		if (task == null){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Tarefa não encontrada/inexistente");
		}

		if (!task.getIdUser().equals(idUser)){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Usuário não tem permissão para alterar a tarefa");
		}

		var taskPutted = this.taskRepository.save(taskModel);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(taskPutted);
	}

	@PatchMapping("/patchTask{id}")
	public ResponseEntity patch(@RequestBody TaskModel taskModel, HttpServletRequest request, @PathVariable UUID id){
		var idUser = request.getAttribute("idUser");
		var task = this.taskRepository.findById(id).orElse(null);

		if (task == null){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Tarefa não encontrada/inexistente");
		}

		if (!task.getIdUser().equals(idUser)){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Usuário não tem permissão para alterar a tarefa");
		}

		Utils.copyNonNullProperties(taskModel, task);
		var taskPatched = this.taskRepository.save(task);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(taskPatched);
	}
}
