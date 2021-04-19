import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

import { Datatable } from './../../model/datatable';
import { PessoaService } from './../../services/pessoa.service';

@Component({
  selector: 'app-pessoa',
  templateUrl: './pessoa.component.html'
})
export class PessoaComponent implements OnInit {

  datatable: Datatable = new Datatable();
  formulario: FormGroup;

  constructor(
    private pessoaService: PessoaService,
    private formBuilder: FormBuilder,
    private router: Router,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.formulario = this.formBuilder.group({
      id: '',
      nome: '',
      dataNascimento: '',
      cpf: '',
      size: this.datatable.tamanhoPagina,
      page: this.datatable.numeroPagina
    })

    this.onPesquisar();
  }

  onNovo(){
    this.router.navigate(['novo'], { relativeTo: this.route });
  }

  onPesquisar() {
    this.onMudarPagina({ offset: 0 });
  }

  onMudarPagina(pagina) {
    this.formulario.controls.page.setValue(pagina.offset);
    this.pesquisarRegistros();
  }

  pesquisarRegistros() {
    this.pessoaService.pesquisar(this.formulario.value)
      .subscribe(res => {
        if ( res ) {
          this.datatable.registros = res.registros;
          this.datatable.numeroPagina = res.numeroPagina;
          this.datatable.tamanhoPagina = res.tamanhoPagina;
          this.datatable.totalRegistros = res.totalRegistros;
        }
      });
  }

  onSelecionar(event) {
    this.router.navigate([event.selected[0].id, 'editar'], {relativeTo: this.route})
  }

}
