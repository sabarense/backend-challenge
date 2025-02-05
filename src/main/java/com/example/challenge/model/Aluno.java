package com.example.challenge.model;

import com.example.challenge.dto.AlunoRequestDTO;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "alunos")
@Data
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

    public Aluno(Long id, AlunoRequestDTO alunoRequestDTO) {
        this.id = id;
        this.nome = alunoRequestDTO.getNome();
        this.notas = alunoRequestDTO.getNotas();
        this.frequencia = alunoRequestDTO.getFrequencia();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Double> getNotas() {
        return notas;
    }

    public void setNotas(List<Double> notas) {
        this.notas = notas;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(Double frequencia) {
        this.frequencia = frequencia;
    }

    @Override
    public String toString() {
        return "Aluno{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", notas=" + notas +
                ", frequencia=" + frequencia +
                '}';
    }
}
