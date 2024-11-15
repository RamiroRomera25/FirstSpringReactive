package rami.pp.springreactive.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTOPost {
    private String toDo;

    private LocalDate estimatedDueDate;
}
