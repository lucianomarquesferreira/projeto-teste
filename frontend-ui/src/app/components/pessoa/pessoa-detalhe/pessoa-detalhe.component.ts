import { Contato } from './../../../model/contato';
import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { Pessoa } from './../../../model/pessoa';
import { PessoaService } from './../../../services/pessoa.service';
import Swal, { SweetAlertOptions, SweetAlertResult } from 'sweetalert2';


@Component({
  selector: 'app-pessoa-detalhe',
  templateUrl: './pessoa-detalhe.component.html'
})
export class PessoaDetalheComponent implements OnInit {

  pessoa: Pessoa = new Pessoa();
  erroSalvar: boolean = false;

  constructor(
    private pessoaService: PessoaService,
    private location: Location,
    protected aRoute: ActivatedRoute,
  ) { }

  ngOnInit(): void {
    this.aRoute.params.subscribe(par => {
      const id = par['id'];
      if (id) {
        this.buscarRegistro(id)
      } else {
        this.adicionarContato();
      }
    });
  }

  onVoltar() {
    this.location.back();
  }

  onSalvar() {
    this.validaSalvar();
    if (!this.erroSalvar) {
      if (this.pessoa.contatos.length < 1) {
        this.mensagemAlerta('error', 'Obrigatório informar ao menos um contato. Por favor, verifique.');
      } else {
        this.pessoaService.salvar(this.pessoa).subscribe(res => {
          if (res) {
            this.mensagemAlerta('success', this.pessoa.nome + ' salvo(a) com sucesso.')
            this.buscarRegistro(res.id);
          }
        },
        erro => {this.mensagemAlerta('error', erro);});
      }
    } else {
      this.mensagemAlerta('error', 'Informações obrigatória(s) ou inválida(s). Por favor, verifique.');
    }
  }

  validaSalvar() {
    this.erroSalvar = false;
    if (this.pessoa.nome == null) {
      this.erroSalvar = true;
    } else if (this.pessoa.nome.length < 1) {
      this.erroSalvar = true;
    }

    if (this.pessoa.cpf == null) {
      this.erroSalvar = true;
    } else {
      if (this.pessoa.cpf.length < 1) {
        this.erroSalvar = true;
      } else if (this.pessoa.cpf.length < 11) {
        this.erroSalvar = true;
      }
    }

    if (this.pessoa.dataNascimento == null) {
      this.erroSalvar = true;
    }

    if (this.pessoa.contatos !== null) {
      if (this.pessoa.contatos) {
        this.pessoa.contatos.forEach(contato => {

          if (contato.nome == null) {
            this.erroSalvar = true;
          } else if (contato.nome.length < 1) {
            this.erroSalvar = true;
          }

          if (contato.telefone.length < 1) {
            this.erroSalvar = true;
          }

          if (contato.email == null) {
            this.erroSalvar = true;
          } else if (!contato.email.match("^[_A-Za-z0-9-\+]+(\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\.[A-Za-z0-9]+)*(\.[A-Za-z]{2,})$")) {
            this.erroSalvar = true;
          }
        })
      }
    }
  }

  onExcluir() {
    Swal.fire({
      text: 'Deseja excluir o(a) ' + this.pessoa.nome + '?',
      showDenyButton: false,
      showCancelButton: true,
      confirmButtonText: 'Sim',
      cancelButtonText: 'Não',
    }).then((result) => {
      if (result.isConfirmed) {
        this.pessoaService.excluir(this.pessoa.id).subscribe(ret => {
          this.mensagemAlerta('success', this.pessoa.nome + ' excluído(a) com sucesso.')
          this.onVoltar();
        },
        erro => {});
      }
    })

  }

  buscarRegistro(id: number) {
    this.pessoaService.pesquisarPorId(id).subscribe(res => {
      this.pessoa = res
    })
  }

  adicionarContato() {
    let contato: Contato = new Contato();
    contato.nome = '';
    contato.telefone = '';
    contato.email = '';
    this.pessoa.contatos.push(contato)
  }

  removerContato(index) {
    this.pessoa.contatos.splice(index,1)
  }

  mensagemAlerta(tipo, mensagem) {
    Swal.fire(
      {
        icon: tipo,
        text: mensagem,
        showConfirmButton: tipo == 'error' ? true : false,
        timer: tipo == 'error' ? 10000 : 2000,
      }
    )
  }

}
