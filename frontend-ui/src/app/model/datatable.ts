export class Datatable {
  totalRegistros: number;
  numeroPagina: number;
  tamanhoPagina: number;
  registros: any[];

  constructor() {
    this.numeroPagina = 0;
    this.tamanhoPagina = 10;
    this.totalRegistros = 0;
    this.registros = [];
  }
}
