package dev.rafael.todolist.service;

import dev.rafael.todolist.exceptions.TaskInvalidDateException;
import dev.rafael.todolist.exceptions.TaskListEmptyException;
import dev.rafael.todolist.exceptions.TaskNotFoundException;
import dev.rafael.todolist.exceptions.UnauthorizedUserException;
import dev.rafael.todolist.model.TaskModel;
import dev.rafael.todolist.repository.TaskRepository;
import dev.rafael.todolist.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class TaskService {
	private final TaskRepository taskRepository;

	public TaskService(TaskRepository taskRepository) {
		this.taskRepository = taskRepository;
	}

	public TaskModel createTask(TaskModel taskModel, HttpServletRequest request){
		var idUser = request.getAttribute("idUser");
		taskModel.setIdUser((UUID) idUser);

		var currentDate = LocalDateTime.now();
		if (currentDate.isAfter(taskModel.getStartAt()) && currentDate.isAfter(taskModel.getEndAt())){
			throw new TaskInvalidDateException("A data de início/término deve ser maior do que a data atual");
		}
		if (taskModel.getStartAt().isAfter(taskModel.getEndAt())){
			throw new TaskInvalidDateException("A data de início deve ser antes da data de termino");
		}

		return this.taskRepository.save(taskModel);
	}

	public List<TaskModel> listar(HttpServletRequest request){
		var idUser = (UUID) request.getAttribute("idUser");

		var tasksView = this.taskRepository.findByIdUser(idUser);
		if (tasksView.isEmpty()){
			throw new TaskListEmptyException();
		}

		return tasksView;
	}

	public TaskModel update(TaskModel taskModel, HttpServletRequest request, UUID id){
		var idUser = request.getAttribute("idUser");
		var task = this.taskRepository.findById(id).orElse(null);
		taskModel.setIdUser((UUID) idUser);
		taskModel.setId(id);

		if (task == null){
			throw new TaskNotFoundException();
		}

		if (!task.getIdUser().equals(idUser)){
			throw new UnauthorizedUserException();
		}

		return this.taskRepository.save(taskModel);
	}

	public TaskModel patch(TaskModel taskModel, HttpServletRequest request, UUID id){
		var idUser = request.getAttribute("idUser");
		var task = this.taskRepository.findById(id).orElse(null);

		if (task == null){
			throw new TaskNotFoundException();
		}

		if (!task.getIdUser().equals(idUser)){
			throw new UnauthorizedUserException();
		}

		Utils.copyNonNullProperties(taskModel, task);
		return this.taskRepository.save(task);
	}
}
