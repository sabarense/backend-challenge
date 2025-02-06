package com.example.challenge.service;

import com.example.challenge.dto.AlunoRequestDTO;
import com.example.challenge.exception.AlunoNotFoundException;
import com.example.challenge.exception.FrequenciaInvalidaException;
import com.example.challenge.exception.NotasInvalidasException;
import com.example.challenge.exception.NumeroNotasInvalidoException;
import com.example.challenge.model.Aluno;
import com.example.challenge.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository repository;

    public Aluno criarAluno(AlunoRequestDTO alunoRequestDTO) {
        if (alunoRequestDTO.getNotas().size() != 5) {
            throw new NumeroNotasInvalidoException("O aluno deve ter exatamente 5 notas.");
        }

        if (alunoRequestDTO.getNotas().stream().anyMatch(nota -> nota < 0 || nota > 10)) {
            throw new NotasInvalidasException("As notas devem estar entre 0 e 10.");
        }

        if (alunoRequestDTO.getFrequencia() < 0 || alunoRequestDTO.getFrequencia() > 100) {
            throw new FrequenciaInvalidaException("A frequência deve estar entre 0% e 100%.");
        }

        Aluno aluno = new Aluno(null, alunoRequestDTO);
        return repository.save(aluno);
    }

    public List<Aluno> listarAlunos() {
        List<Aluno> alunos = repository.findAll();
        if (alunos.isEmpty()) {
            throw new AlunoNotFoundException("Nenhum aluno encontrado.");
        }
        return alunos;
    }

    public double calcularMediaFrequenciaTurma() {
        List<Aluno> alunos = repository.findAll();
        if (alunos.isEmpty()) {
            throw new AlunoNotFoundException("Não há alunos cadastrados para calcular a média de frequência.");
        }

        return alunos.stream()
                .mapToDouble(Aluno::getFrequencia)
                .average()
                .orElse(0.0);
    }

    public Aluno alterarNota(Long id, AlunoRequestDTO alunoRequestDTO) {
        Aluno aluno = repository.findById(id).orElseThrow(() -> new AlunoNotFoundException("Aluno não encontrado."));
        aluno.setNotas(alunoRequestDTO.getNotas());
        aluno.setFrequencia(alunoRequestDTO.getFrequencia());
        return repository.save(aluno);
    }

    public double calcularMediaNotasAluno(Long id) {
        Aluno aluno = repository.findById(id).orElseThrow(() -> new AlunoNotFoundException("Aluno não encontrado."));
        return aluno.getNotas().stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElseThrow(() -> new NotasInvalidasException("Não foi possível calcular a média."));
    }

    public List<Double> calcularMediaTurma() {
        List<Aluno> alunos = repository.findAll();
        if (alunos.isEmpty()) {
            throw new AlunoNotFoundException("Não há alunos cadastrados para calcular a média das notas da turma.");
        }

        int numeroDisciplinas = alunos.get(0).getNotas().size();

        return IntStream.range(0, numeroDisciplinas)
                .mapToDouble(disciplinaIndex -> alunos.stream()
                        .mapToDouble(aluno -> aluno.getNotas().get(disciplinaIndex))
                        .average()
                        .orElseThrow(() -> new NumeroNotasInvalidoException("Número de notas inválido.")))
                .boxed()
                .collect(Collectors.toList());
    }

    public List<Aluno> getAlunosAcimaMediaTurma() {
        List<Aluno> alunos = repository.findAll();
        List<Double> mediaTurma = calcularMediaTurma();

        return alunos.stream()
                .filter(aluno -> {
                    for (int i = 0; i < aluno.getNotas().size(); i++) {
                        if (aluno.getNotas().get(i) < mediaTurma.get(i)) {
                            return false;
                        }
                    }
                    return true;
                })
                .collect(Collectors.toList());
    }

    public List<Aluno> getAlunosAbaixoFrequencia() {
        return repository.findAll().stream()
                .filter(aluno -> aluno.getFrequencia() < 75)
                .collect(Collectors.toList());
    }
}
