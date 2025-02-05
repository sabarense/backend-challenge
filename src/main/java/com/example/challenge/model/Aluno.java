package com.example.challenge.model;

import com.example.challenge.dto.AlunoRequestDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "alunos")
@Getter
@Setter
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @ElementCollection
    @CollectionTable(name = "notas", joinColumns = @JoinColumn(name = "aluno_id"))
    @Column(name = "nota")
    private List<Double> notas;

    private Double frequencia;

    public Aluno() {
    }
    public Aluno(Long id, AlunoRequestDTO alunoRequestDTO){
        this.id = id;
        this.nome = alunoRequestDTO.nome();
        this.notas = alunoRequestDTO.notas();
        this.frequencia = alunoRequestDTO.frequencia();
    }
}
