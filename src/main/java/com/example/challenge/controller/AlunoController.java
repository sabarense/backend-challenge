package com.example.challenge.controller;

import com.example.challenge.dto.AlunoRequestDTO;
import com.example.challenge.exception.AlunoNotFoundException;
import com.example.challenge.exception.FrequenciaInvalidaException;
import com.example.challenge.exception.NotasInvalidasException;
import com.example.challenge.exception.NumeroNotasInvalidoException;
import com.example.challenge.model.Aluno;
import com.example.challenge.repository.AlunoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("alunos")
@Validated
public class AlunoController {

    @Autowired
    private AlunoRepository repository;
    private static final Logger logger = LoggerFactory.getLogger(AlunoController.class);

    @PostMapping
    public ResponseEntity<?> criarAluno(@RequestBody @Valid @NotNull AlunoRequestDTO alunoRequestDTO) {
        logger.info("Recebendo requisição para criar aluno: {}", alunoRequestDTO);

        if (alunoRequestDTO.getNotas().size() != 5) {
            logger.info("O aluno deve ter exatamente 5 notas, mas recebeu: {}", alunoRequestDTO.getNotas().size());
            throw new NumeroNotasInvalidoException("O aluno deve ter exatamente 5 notas.");
        }

        if (alunoRequestDTO.getNotas().stream().anyMatch(nota -> nota < 0 || nota > 10)) {
            logger.info("Nota não pode ser abaixo de 0 ou acima de 10: {}", alunoRequestDTO.getNotas());
            throw new NotasInvalidasException("As notas devem estar entre 0 e 10.");
        }

        if (alunoRequestDTO.getFrequencia() < 0 || alunoRequestDTO.getFrequencia() > 100) {
            logger.info("Frequência fora do intervalo permitido: {}", alunoRequestDTO.getFrequencia());
            throw new FrequenciaInvalidaException("A frequência deve estar entre 0% e 100%.");
        }

        Aluno aluno = new Aluno(null, alunoRequestDTO);
        Aluno alunoSalvo = repository.save(aluno);

        logger.info("Aluno criado com sucesso: {}", alunoSalvo);
        return ResponseEntity.status(201).body(alunoSalvo);
    }

    @GetMapping
    public ResponseEntity<?> listarAlunos() {
        List<Aluno> alunos = repository.findAll();

        if (alunos.isEmpty()) {
            throw new AlunoNotFoundException("Nenhum aluno encontrado.");
        }

        logger.info("Alunos existentes: {}", alunos);
        return ResponseEntity.ok(alunos);
    }

    @GetMapping("/frequencia/{id}")
    public ResponseEntity<?> getAlunoFrequencia(@PathVariable Long id) {
        Aluno aluno = repository.findById(id).orElseThrow(() -> new AlunoNotFoundException("Aluno não encontrado."));
        return ResponseEntity.ok(aluno.getFrequencia());
    }

    @GetMapping("/media-frequencia-turma")
    public ResponseEntity<?> calcularMediaFrequenciaTurma() {
        List<Aluno> alunos = repository.findAll();

        if (alunos.isEmpty()) {
            throw new AlunoNotFoundException("Não há alunos cadastrados para calcular a média de frequência.");
        }

        double mediaFrequencia = alunos.stream()
                .mapToDouble(Aluno::getFrequencia)
                .average()
                .orElse(0.0);

        return ResponseEntity.ok(mediaFrequencia);
    }

    @PutMapping("/notas/{id}")
    public ResponseEntity<?> alterarNota(@PathVariable Long id, @RequestBody AlunoRequestDTO alunoRequestDTO) {
        Aluno aluno = repository.findById(id).orElseThrow(() -> new AlunoNotFoundException("Aluno não encontrado."));

        aluno.setNotas(alunoRequestDTO.getNotas());
        aluno.setFrequencia(alunoRequestDTO.getFrequencia());

        return ResponseEntity.ok(repository.save(aluno));
    }

    @GetMapping("/media-notas/{id}")
    public ResponseEntity<?> getAlunoMedia(@PathVariable Long id) {
        Aluno aluno = repository.findById(id).orElseThrow(() -> new AlunoNotFoundException("Aluno não encontrado."));
        double media = aluno.getNotas().stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElseThrow(() -> new NotasInvalidasException("Não foi possível calcular a média."));

        return ResponseEntity.ok(media);
    }

    @GetMapping("/media-notas-turma-disciplina")
    public ResponseEntity<?> calcularMediaTurma() {
        List<Aluno> alunos = repository.findAll();

        if (alunos.isEmpty()) {
            throw new AlunoNotFoundException("Não há alunos cadastrados para calcular a média das notas da turma.");
        }

        int numeroDisciplinas = alunos.get(0).getNotas().size();

        List<Double> mediaTurma = IntStream.range(0, numeroDisciplinas)
                .mapToDouble(disciplinaIndex -> alunos.stream()
                        .mapToDouble(aluno -> aluno.getNotas().get(disciplinaIndex))
                        .average()
                        .orElseThrow(() -> new NumeroNotasInvalidoException("Número de notas inválido.")))
                .boxed()
                .collect(Collectors.toList());

        return ResponseEntity.ok(mediaTurma);
    }

    @GetMapping("/acima-media-turma")
    public ResponseEntity<?> getAlunosAcimaMediaTurma() {
        List<Aluno> alunos = repository.findAll();
        List<Double> mediaTurma = (List<Double>) calcularMediaTurma().getBody();

        List<Aluno> alunosAcimaMedia = alunos.stream()
                .filter(aluno -> {
                    for (int i = 0; i < aluno.getNotas().size(); i++) {
                        if (aluno.getNotas().get(i) < mediaTurma.get(i)) {
                            return false;
                        }
                    }
                    return true;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(alunosAcimaMedia);
    }

    @GetMapping("/abaixo-frequencia")
    public ResponseEntity<?> getAlunosAbaixoFrequencia() {
        List<Aluno> alunosAbaixoFrequencia = repository.findAll().stream()
                .filter(aluno -> aluno.getFrequencia() < 75)
                .collect(Collectors.toList());

        return ResponseEntity.ok(alunosAbaixoFrequencia);
    }
}
