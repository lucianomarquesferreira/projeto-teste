import { FormControl, FormGroup, Validators } from '@angular/forms';

import { EntidadeBase } from './entidade-base';
import { Pessoa } from './pessoa';

export class Contato extends EntidadeBase {
  pessoa: Pessoa;
  telefone: string;
  email: string;

  constructor() {
    super();
  }
}
