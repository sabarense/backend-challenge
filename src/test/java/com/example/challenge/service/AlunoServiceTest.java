package com.example.challenge.service;

import com.example.challenge.dto.AlunoRequestDTO;
import com.example.challenge.exception.AlunoNotFoundException;
import com.example.challenge.model.Aluno;
import com.example.challenge.repository.AlunoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AlunoServiceTest {

    @Mock
    private AlunoRepository alunoRepository;

    @InjectMocks
    private AlunoService alunoService;

    private AlunoRequestDTO alunoRequestDTO;

    @BeforeEach
    public void setup() {
        alunoRequestDTO = new AlunoRequestDTO();
        alunoRequestDTO.setNotas(List.of(7.5, 8.0, 9.0, 6.5, 7.0));
        alunoRequestDTO.setFrequencia(85.00);
    }

    @Test
    public void testCriarAluno() {
        Aluno aluno = new Aluno(1L, alunoRequestDTO);

        when(alunoRepository.save(aluno)).thenReturn(aluno);

        Aluno alunoCriado = alunoService.criarAluno(alunoRequestDTO);
        assertEquals(aluno.getId(), alunoCriado.getId());
    }

    @Test
    public void testListarAlunos() {
        Aluno aluno = new Aluno(1L, alunoRequestDTO);

        when(alunoRepository.findAll()).thenReturn(List.of(aluno));

        List<Aluno> alunos = alunoService.listarAlunos();
        assertEquals(1, alunos.size());
    }

    @Test
    public void testCalcularMediaAluno() {
        Aluno aluno = new Aluno(1L, alunoRequestDTO);

        when(alunoRepository.findById(1L)).thenReturn(java.util.Optional.of(aluno));

        double media = alunoService.calcularMediaNotasAluno(1L);
        assertEquals(7.8, media, 0.1);
    }

    @Test
    public void testAlunoNotFound() {
        when(alunoRepository.findById(999L)).thenReturn(java.util.Optional.empty());

        assertThrows(AlunoNotFoundException.class, () -> alunoService.calcularMediaNotasAluno(999L));
    }
}
