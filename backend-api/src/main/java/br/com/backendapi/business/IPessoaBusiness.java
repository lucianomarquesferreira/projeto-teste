package br.com.backendapi.business;

import br.com.backendapi.model.Filtro;
import br.com.backendapi.model.Pessoa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IPessoaBusiness {

    Page<Pessoa> pesquisar(Filtro filtro, Pageable pageable);

    ResponseEntity<List<Pessoa>> listar();

    ResponseEntity<Pessoa> pesquisarPorId(Long id);

    ResponseEntity<Pessoa> salvar(Pessoa pessoa) throws Exception;

    ResponseEntity<Pessoa> alterar(Pessoa pessoa, Long id) throws Exception;

    ResponseEntity<?> excluir(Long id) throws Exception;


}
