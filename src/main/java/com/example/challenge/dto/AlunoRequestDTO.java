package com.example.challenge.dto;

import lombok.Data;

import javax.validation.constraints.*;
import java.util.List;

@Data

public class AlunoRequestDTO {

    @NotNull(message = "Notas não podem ser nulas")
    @NotBlank(message = "O nome do aluno não pode estar vazio")
    private String nome;

    @Size(min = 5, max = 5, message = "Deve haver exatamente 5 notas")
    private List<@Min(0) @Max(10) Double> notas;

    @NotNull(message = "Frequência não pode ser nula")
    @Min(value = 0, message = "A frequência não pode ser menor que 0")
    @Max(value = 100, message = "A frequência não pode ser maior que 100")
    private Double frequencia;

    public AlunoRequestDTO() {
    }

    public AlunoRequestDTO(Double frequencia, List<Double> notas, String nome) {
        this.frequencia = frequencia;
        this.notas = notas;
        this.nome = nome;
    }

    public @Size(min = 5, max = 5, message = "Deve haver exatamente 5 notas") List<@Min(0) @Max(10) Double> getNotas() {
        return notas;
    }

    public void setNotas(@Size(min = 5, max = 5, message = "Deve haver exatamente 5 notas") List<@Min(0) @Max(10) Double> notas) {
        this.notas = notas;
    }

    public @NotBlank(message = "O nome do aluno não pode estar vazio") String getNome() {
        return nome;
    }

    public void setNome(@NotBlank(message = "O nome do aluno não pode estar vazio") String nome) {
        this.nome = nome;
    }

    public @Min(0) @Max(100) Double getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(@Min(0) @Max(100) Double frequencia) {
        this.frequencia = frequencia;
    }
}
