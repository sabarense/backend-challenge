package com.example.challenge.controller;

import com.example.challenge.dto.AlunoRequestDTO;
import com.example.challenge.model.Aluno;
import com.example.challenge.service.AlunoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AlunoControllerTest {

    @Mock
    private AlunoService alunoService;

    @InjectMocks
    private AlunoController alunoController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(alunoController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testCriarAluno() throws Exception {
        AlunoRequestDTO alunoRequestDTO = new AlunoRequestDTO();
        alunoRequestDTO.setNotas(List.of(7.5, 8.0, 9.0, 6.5, 7.0));
        alunoRequestDTO.setFrequencia(85.00);

        Aluno aluno = new Aluno(1L, alunoRequestDTO);

        when(alunoService.criarAluno(alunoRequestDTO)).thenReturn(aluno);

        mockMvc.perform(post("/alunos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(alunoRequestDTO)))
                .andExpect(status().isCreated());
    }
}
