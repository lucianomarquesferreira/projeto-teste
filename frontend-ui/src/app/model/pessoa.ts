import { FormArray, FormControl, FormGroup, Validators } from '@angular/forms';

import { Contato } from './contato';
import { EntidadeBase } from './entidade-base';

export class Pessoa extends EntidadeBase {
  cpf: string;
  dataNascimento: Date;
  contatos: Contato[];

  constructor() {
    super();
    this.contatos = []
  }

}
