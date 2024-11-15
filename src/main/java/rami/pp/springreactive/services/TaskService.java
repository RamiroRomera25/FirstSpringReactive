package rami.pp.springreactive.services;

import org.springframework.stereotype.Service;
import rami.pp.springreactive.dtos.TaskDTOPost;
import rami.pp.springreactive.entities.TaskEntity;
import rami.pp.springreactive.enums.TaskStatus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public interface TaskService {
    Mono<TaskEntity> createTask(TaskDTOPost taskPost);

    Flux<TaskEntity> getAllTasks();

    Mono<TaskEntity> getTaskById(String id);

    Flux<TaskEntity> getTasksByStatus(TaskStatus taskStatus);

    Mono<TaskEntity> deleteTask(String id);
}
