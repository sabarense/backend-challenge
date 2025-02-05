CREATE TABLE alunos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    frequencia DOUBLE NOT NULL
);

CREATE TABLE notas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    aluno_id BIGINT,
    nota DOUBLE,
    FOREIGN KEY (aluno_id) REFERENCES alunos(id)
);

-- Criando alunos iniciais
INSERT INTO alunos (nome, frequencia)
VALUES
    ('João', 80.0);

-- Inserindo as notas de João
INSERT INTO notas (aluno_id, nota)
VALUES
    (1, 7),
    (1, 8),
    (1, 6),
    (1, 9),
    (1, 10);

-- Agora adicionando um segundo aluno

INSERT INTO alunos (nome, frequencia)
VALUES
    ('Maria', 90.0);

-- Inserindo as notas para Maria (id 2)
INSERT INTO notas (aluno_id, nota)
VALUES
    (2, 8),
    (2, 9),
    (2, 7),
    (2, 9),
    (2, 8);
