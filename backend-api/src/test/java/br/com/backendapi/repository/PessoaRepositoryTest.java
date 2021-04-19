package br.com.backendapi.repository;

import br.com.backendapi.model.Contato;
import br.com.backendapi.model.Pessoa;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PessoaRepositoryTest {

    @Autowired
    private PessoaRepository pessoaRepository;


    @Test
    public void salvar() {
        Pessoa pessoa1 = new Pessoa();
        pessoa1.setId(1L);
        pessoa1.setNome("Jose da Silva Menezes");
        pessoa1.setCpf("40574834036");
        pessoa1.setDataNascimento(LocalDate.parse("1979-03-01"));
        pessoa1.setContatos(new ArrayList<>());

        Contato contato1 = new Contato();
        contato1.setId(1L);
        contato1.setNome("Maria da Silva");
        contato1.setTelefone("11999525263");
        contato1.setEmail("maria@maria.com");
        pessoa1.getContatos().add(contato1);

        Contato contato2 = new Contato();
        contato2.setId(2L);
        contato2.setNome("Mateus Marinho");
        contato2.setTelefone("11998219525");
        contato2.setEmail("mateus@mateus.com");
        pessoa1.getContatos().add(contato2);

        assertEquals(2, pessoa1.getContatos().size());

        this.pessoaRepository.save(pessoa1);
        Pessoa pessoa5 = this.pessoaRepository.findFirstById(5L);
        assertNull(pessoa5);
    }

}