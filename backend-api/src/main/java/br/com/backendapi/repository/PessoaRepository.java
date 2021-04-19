package br.com.backendapi.repository;

import br.com.backendapi.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long>, PessoaRepositoryCustom {

    boolean existsByNomeAndDataNascimento(String nome, LocalDate dataNascimento);

    Pessoa findFirstById(Long id);
}
