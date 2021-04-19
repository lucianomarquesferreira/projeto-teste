import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { PessoaDetalheComponent } from './components/pessoa/pessoa-detalhe/pessoa-detalhe.component';
import { PessoaComponent } from './components/pessoa/pessoa.component';

const routes: Routes = [
  {
    path: '',
    component: PessoaComponent
  },
  {
    path: 'novo',
    component: PessoaDetalheComponent
  },
  {
      path: ':id/editar',
      component: PessoaDetalheComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
