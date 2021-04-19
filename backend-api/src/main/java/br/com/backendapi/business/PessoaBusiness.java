package br.com.backendapi.business;

import br.com.backendapi.exception.ExcecaoGenerica;
import br.com.backendapi.model.Contato;
import br.com.backendapi.model.Filtro;
import br.com.backendapi.model.Pessoa;
import br.com.backendapi.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PessoaBusiness implements IPessoaBusiness {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Override
    public ResponseEntity<List<Pessoa>> listar() {
        return ResponseEntity.ok().body(this.pessoaRepository.findAll());
    }

    @Override
    public ResponseEntity<Pessoa> pesquisarPorId(Long id) {
        return this.pessoaRepository.findById(id)
                .map(pessoa -> ResponseEntity.ok().body(pessoa))
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public Page<Pessoa> pesquisar(Filtro filtro, Pageable pageable) {
        return this.pessoaRepository.pesquisar(filtro, pageable);
    }

    @Override
    public ResponseEntity<Pessoa> salvar(Pessoa pessoa) throws Exception {
        return ResponseEntity.ok().body(this.pessoaRepository.save(this.validaSalvar(pessoa)));
    }

    @Override
    public ResponseEntity<Pessoa> alterar(Pessoa pessoa, Long id) throws Exception {
        if (this.pessoaRepository.findById(id) != null) {
            pessoa.setId(id);
            return this.salvar(pessoa);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<?> excluir(Long id) throws Exception {
        return this.pessoaRepository.findById(id)
                .map(record -> {
                    this.pessoaRepository.deleteById(id);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }

    private Pessoa validaSalvar(Pessoa pessoa) throws ExcecaoGenerica {

        if (pessoa.getNome().isEmpty())
            throw new ExcecaoGenerica("Nome da Pessoa é obrigatório. Por favor, verifique");

        if (pessoa.getCpf().isEmpty())
            throw new ExcecaoGenerica("CPF da Pessoa é obrigatório. Por favor, verifique");

        if (!this.validaCpf(pessoa.getCpf()))
            throw new ExcecaoGenerica("CPF da Pessoa inválido. Por favor, verifique");

        if(pessoa.getDataNascimento() == null)
            throw new ExcecaoGenerica("Data de Nascimento da Pessoa é Obrigatório. Por favor, verifique");

        if (pessoa.getDataNascimento().compareTo(LocalDate.now()) > 0)
            throw new ExcecaoGenerica("Data de Nascimento da Pessoa não deve ser futura. Por favor, verifique");

        if (pessoa.getContatos().isEmpty())
            throw new ExcecaoGenerica("Obrigatório informar ao menos um contato. Por favor, verifique");

        for(Contato contato : pessoa.getContatos()) {
            if (contato.getNome().isEmpty())
                throw new ExcecaoGenerica("Nome do Contato é Obrigatório. Por favor, verifique");

            if (contato.getEmail().isEmpty())
                throw new ExcecaoGenerica("Email do Contato é Obrigatório. Por favor, verifique");

            if (!contato.getEmail().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"))
                throw new ExcecaoGenerica("Email do Contato inválido. Por favor, verifique");

            if (contato.getTelefone().isEmpty())
                throw new ExcecaoGenerica("Telefone do Contato é Obrigatório. Por favor, verifique");
        }

        return pessoa;
    }

    private boolean validaCpf(String cpf) {

        if (cpf.length() != 11)
            return(false);

        char cpfValida;
        int sm, i, r, num, peso;

        sm = 0;
        peso = 10;
        for (i=0; i<9; i++) {
            num = (int)(cpf.charAt(i) - 48);
            sm = sm + (num * peso);
            peso = peso - 1;
        }

        sm = 0;
        peso = 11;
        for(i=0; i<10; i++) {
            num = (int)(cpf.charAt(i) - 48);
            sm = sm + (num * peso);
            peso = peso - 1;
        }

        r = 11 - (sm % 11);
        if ((r == 10) || (r == 11))
            cpfValida = '0';
        else cpfValida = (char)(r + 48);

        if ((cpfValida == cpf.charAt(10)))
            return(true);
        else return(false);
    }

}
