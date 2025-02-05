package com.example.challenge.controller;

import com.example.challenge.model.Aluno;
import com.example.challenge.repository.AlunoRepository;
import com.example.challenge.dto.AlunoRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("alunos")
@Validated
public class AlunoController {

    @Autowired
    private AlunoRepository repository;

    @PostMapping
    public ResponseEntity<Aluno> criarAluno(@RequestBody @Valid AlunoRequestDTO alunoRequestDTO) {
        Aluno aluno = new Aluno(null, alunoRequestDTO);

        Aluno alunoSalvo = repository.save(aluno);

        return ResponseEntity.status(HttpStatus.CREATED).body(alunoSalvo);
    }

    @GetMapping
    public ResponseEntity<List<Aluno>> listarAlunos() {
        List<Aluno> alunos = repository.findAll();
        return ResponseEntity.ok(alunos);
    }


//    @PutMapping("/{id}/notas")
//    public ResponseEntity<Aluno> alterarNota(@PathVariable Long id, @RequestBody AlunoRequestDTO alunoRequestDTO) {
//
//        Aluno aluno = repository.findById(id).orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
//
//        aluno.setNotas(alunoRequestDTO.notas());
//        aluno.setFrequencia(alunoRequestDTO.frequencia());
//
//        Aluno alunoAtualizado = repository.save(aluno);
//
//        return ResponseEntity.ok(alunoAtualizado);
//    }

}



