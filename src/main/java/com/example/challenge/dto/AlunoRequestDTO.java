package com.example.challenge.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.List;
@Getter
@Setter

public record AlunoRequestDTO(
//       @NotBlank(message = "O nome do aluno não pode estar vazio")
        String nome,

//       @NotNull(message = "A lista de notas não pode ser nula")
//       @Size(min = 5, max = 5, message = "Deve haver exatamente 5 notas")
//       List<@Min(0) @Max(10)
        List<Double> notas,

//        @NotNull(message = "A frequência não pode ser nula")
//        @Min(value = 0, message = "A frequência não pode ser negativa")
//        @Max(value = 100, message = "A frequência não pode ser superior a 100")
        Double frequencia
) {
}
