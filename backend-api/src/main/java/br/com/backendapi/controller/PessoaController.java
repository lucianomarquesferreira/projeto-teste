package br.com.backendapi.controller;

import br.com.backendapi.model.Filtro;
import br.com.backendapi.model.Pessoa;
import br.com.backendapi.business.IPessoaBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pessoa")
public class PessoaController {

    @Autowired
    private IPessoaBusiness iPessoaBusiness;

    @GetMapping(value = "/pesquisar")
    public Page<Pessoa> pesquisar(Filtro filtro, Pageable pageable) {
        return this.iPessoaBusiness.pesquisar(filtro, pageable);
    }

    @GetMapping
    public ResponseEntity<List<Pessoa>> listar() {
        return this.iPessoaBusiness.listar();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Pessoa> pesquisarPorId(@PathVariable Long id) {
        return this.iPessoaBusiness.pesquisarPorId(id);
    }


    @PostMapping
    public ResponseEntity<Pessoa> salvar(@RequestBody Pessoa pessoa) throws Exception {
        return this.iPessoaBusiness.salvar(pessoa);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Pessoa> alterar(@RequestBody Pessoa pessoa, @PathVariable Long id) throws Exception {
        return this.iPessoaBusiness.alterar(pessoa, id);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> excluir(@PathVariable Long id) throws Exception {
        return this.iPessoaBusiness.excluir(id);
    }
}
