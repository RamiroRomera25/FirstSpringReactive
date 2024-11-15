package rami.pp.springreactive.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rami.pp.springreactive.dtos.TaskDTOPost;
import rami.pp.springreactive.entities.TaskEntity;
import rami.pp.springreactive.enums.TaskStatus;
import rami.pp.springreactive.repositories.TaskRepository;
import rami.pp.springreactive.services.TaskService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Lo que hago aca con las subscripciones es completamente innecesario
     * es simplemente para ver el comportamiento
     */
    public Mono<TaskEntity> createTask(TaskDTOPost taskPost) {
        TaskEntity taskEntity = modelMapper.map(taskPost, TaskEntity.class);
        taskEntity.setId(UUID.randomUUID());
        taskEntity.setIsActive(true);
        taskEntity.setCreateDate(LocalDateTime.now());
        taskEntity.setStatus(TaskStatus.TODO);

        return taskRepository.save(taskEntity)
            .doOnSubscribe(subscription -> {
                System.out.println("\033[1;32m\n\n[SUSCRIPCIÓN] Suscrito al flujo de creación de tarea\n\n\033[0m");
            })
            .doOnTerminate(() -> {
                System.out.println("\033[1;33m[TERMINADO] Flujo de tarea terminado\033[0m");
            })
            .doOnSuccess(task -> {
                System.out.println("\033[1;34m[TAREA GUARDADA] Tarea guardada con éxito: " + task.getId() + "\033[0m");
            })
            .doOnError(error -> {
                System.out.println("\033[1;31m[ERROR] Hubo un problema al guardar tarea: " + error.getMessage() + "\033[0m");
            });
    }

    public Flux<TaskEntity> getAllTasks() {
        return taskRepository.findAll();
    }

    public Mono<TaskEntity> getTaskById(String id) {
        return taskRepository.findById(UUID.fromString(id));
    }

    public Flux<TaskEntity> getTasksByStatus(TaskStatus taskStatus) {
        return taskRepository.findAllByStatus(taskStatus);
    }

    public Mono<TaskEntity> deleteTask(String id) {
        return taskRepository.softDeleteById(UUID.fromString(id));
    }

}
