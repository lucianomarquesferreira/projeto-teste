package br.com.backendapi.repository;

import br.com.backendapi.model.Filtro;
import br.com.backendapi.model.Pessoa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PessoaRepositoryCustom {

    Page<Pessoa> pesquisar(Filtro filtro, Pageable pageable);
}
