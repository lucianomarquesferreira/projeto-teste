CREATE TABLE IF NOT EXISTS pessoa (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    nome VARCHAR(60) NOT NULL,
    cpf VARCHAR(11) NOT NULL,
    data_nascimento DATE NOT NULL
);

COMMENT ON TABLE pessoa is 'Cadastro de Pessoa';
COMMENT ON COLUMN pessoa.id is 'Código Sequencial da Tabela';
COMMENT ON COLUMN pessoa.nome is 'Nome da Pessoa';
COMMENT ON COLUMN pessoa.cpf is 'CPF da Pessoa';
COMMENT ON COLUMN pessoa.data_nascimento is 'Data de Nascimento da Pessoa';


CREATE TABLE IF NOT EXISTS contato (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    pessoa_id BIGINT,
    CONSTRAINT pessoa_id FOREIGN KEY (pessoa_id) REFERENCES pessoa(id),
    nome VARCHAR(60) NOT NULL,
    telefone VARCHAR(11) NOT NULL,
    email VARCHAR(50) NOT NULL
);

COMMENT ON TABLE contato is 'Cadastro de Contato';
COMMENT ON COLUMN contato.id is 'Código Sequencial da Tabela';
COMMENT ON COLUMN contato.pessoa_id is 'Código da Pessoa Responsável pelo Contato';
COMMENT ON COLUMN contato.nome is 'Nome do Contato';
COMMENT ON COLUMN contato.telefone is 'Telefone do Contato';
COMMENT ON COLUMN contato.email is 'Email do Contato';

