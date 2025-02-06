package com.example.challenge.controller;

import com.example.challenge.dto.AlunoRequestDTO;
import com.example.challenge.exception.AlunoNotFoundException;
import com.example.challenge.model.Aluno;
import com.example.challenge.service.AlunoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("alunos")
@Validated
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    private static final Logger logger = LoggerFactory.getLogger(AlunoController.class);

    @PostMapping
    public ResponseEntity<?> criarAluno(@RequestBody @Valid @NotNull AlunoRequestDTO alunoRequestDTO) {
        logger.info("Recebendo requisição para criar aluno: {}", alunoRequestDTO);
        Aluno alunoSalvo = alunoService.criarAluno(alunoRequestDTO);
        logger.info("Aluno criado com sucesso: {}", alunoSalvo);
        return ResponseEntity.status(201).body(alunoSalvo);
    }

    @GetMapping
    public ResponseEntity<?> listarAlunos() {
        List<Aluno> alunos = alunoService.listarAlunos();
        logger.info("Alunos existentes: {}", alunos);
        return ResponseEntity.ok(alunos);
    }

    @GetMapping("/frequencia/{id}")
    public ResponseEntity<?> getAlunoFrequencia(@PathVariable Long id) {
        Aluno aluno = alunoService.listarAlunos().stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new AlunoNotFoundException("Aluno não encontrado."));
        return ResponseEntity.ok(aluno.getFrequencia());
    }

    @GetMapping("/media-frequencia-turma")
    public ResponseEntity<?> calcularMediaFrequenciaTurma() {
        double mediaFrequencia = alunoService.calcularMediaFrequenciaTurma();
        return ResponseEntity.ok(mediaFrequencia);
    }

    @PutMapping("/notas/{id}")
    public ResponseEntity<?> alterarNota(@PathVariable Long id, @RequestBody AlunoRequestDTO alunoRequestDTO) {
        Aluno alunoAtualizado = alunoService.alterarNota(id, alunoRequestDTO);
        return ResponseEntity.ok(alunoAtualizado);
    }

    @GetMapping("/media-notas/{id}")
    public ResponseEntity<?> getAlunoMedia(@PathVariable Long id) {
        double media = alunoService.calcularMediaNotasAluno(id);
        return ResponseEntity.ok(media);
    }

    @GetMapping("/media-notas-turma-disciplina")
    public ResponseEntity<?> calcularMediaTurma() {
        List<Double> mediaTurma = alunoService.calcularMediaTurma();
        return ResponseEntity.ok(mediaTurma);
    }

    @GetMapping("/acima-media-turma")
    public ResponseEntity<?> getAlunosAcimaMediaTurma() {
        List<Aluno> alunosAcimaMedia = alunoService.getAlunosAcimaMediaTurma();
        return ResponseEntity.ok(alunosAcimaMedia);
    }

    @GetMapping("/abaixo-frequencia")
    public ResponseEntity<?> getAlunosAbaixoFrequencia() {
        List<Aluno> alunosAbaixoFrequencia = alunoService.getAlunosAbaixoFrequencia();
        return ResponseEntity.ok(alunosAbaixoFrequencia);
    }
}
