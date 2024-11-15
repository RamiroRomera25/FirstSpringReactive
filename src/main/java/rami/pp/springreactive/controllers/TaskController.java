package rami.pp.springreactive.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rami.pp.springreactive.dtos.TaskDTOPost;
import rami.pp.springreactive.entities.TaskEntity;
import rami.pp.springreactive.services.TaskService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    public Mono<TaskEntity> createTask(@RequestBody TaskDTOPost taskPost) {
        return taskService.createTask(taskPost);
    }

    @GetMapping
    public Flux<TaskEntity> getAllTasks() {
        return taskService.getAllTasks()
                .log(); // El log es solo para observar, no es necesario
    }

    @GetMapping("/{id}")
    public Mono<TaskEntity> getTaskById(@PathVariable String id) {
        return taskService.getTaskById(id);
    }

    // No se pq el ResponseEntity es re dificil de usar
//    @DeleteMapping("/{id}")
//    public Mono<ResponseEntity<TaskEntity>> deleteTask(@PathVariable String id) {
//        return taskService.deleteTask(id) // Llama al servicio para el delete
//            .flatMap(deletedTask -> { // Si puedo hacerle get devuelvo 200
//                return Mono.just(ResponseEntity.ok(deletedTask));
//            })
//            .onErrorResume(e -> {
//                // Si no la encuentra 404
//                return Mono.just(ResponseEntity.notFound().build());
//            });
//    }

    @DeleteMapping("/{id}")
    public Mono<TaskEntity> deleteTask(@PathVariable String id) {
        return taskService.deleteTask(id);
    }
}
