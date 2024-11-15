package rami.pp.springreactive.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import rami.pp.springreactive.entities.TaskEntity;
import rami.pp.springreactive.enums.TaskStatus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface TaskRepository extends ReactiveCrudRepository<TaskEntity, UUID> {
    Flux<TaskEntity> findAllByStatus(TaskStatus status);

    default Mono<TaskEntity> softDeleteById(UUID id) {
        Mono<TaskEntity> monoTaskEntity = findById(id);
        return monoTaskEntity.flatMap(task -> {
            task.setIsActive(false);
            return save(task);
        });
    }
}
