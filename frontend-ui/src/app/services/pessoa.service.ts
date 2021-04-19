import { HttpClient, HttpErrorResponse, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

import { BACKEND_API } from './../app.api';
import { Datatable } from './../model/datatable';
import { Pessoa } from './../model/pessoa';

@Injectable({
  providedIn: 'root'
})
export class PessoaService {
  url = BACKEND_API;
  headers: HttpHeaders = new HttpHeaders();

  constructor(private http: HttpClient) {
    this.url += '/' + 'pessoa',
    this.headers = this.headers.set('Content-Type', 'application/json;charset=UTF-8');
  }

  listar(): Observable<any> {
    return this.http.get<any>(this.url)
      .pipe(catchError(this.obtemErro));
  }

  pesquisarPorId(id: number): Observable<Pessoa> {
    return this.http.get<Pessoa>( `${this.url}/${id}`).pipe(catchError(this.obtemErro));
  }

  pesquisar(parametros: any) {
    if ( !(parametros instanceof HttpParams) ) {parametros = this.removeParametrosNull(parametros); }

    return this.http.get(`${ this.url }/pesquisar`, { params: parametros })
      .pipe(map(resultado => {
              const data = JSON.parse(JSON.stringify(resultado));
              const datatable = new Datatable();
              datatable.registros = data.content;
              datatable.totalRegistros = data.totalElements;
              datatable.numeroPagina = data.number;
              datatable.tamanhoPagina = data.size;
              return datatable;
            }),catchError(this.obtemErro)
      );
}


  salvar(pessoa: Pessoa): Observable<Pessoa> {
    if ( !pessoa.id ) {
        return this.http.post<Pessoa>(this.url, JSON.stringify(pessoa), { headers: this.headers })
          .pipe(catchError(this.obtemErro));
    } else {
        return this.http.put<Pessoa>( `${this.url}/${pessoa.id}`, JSON.stringify(pessoa), { headers: this.headers })
            .pipe(catchError(this.obtemErro));
    }
  }

  excluir(id: number): Observable<any> {
    return this.http.delete( `${this.url}/${id}`)
      .pipe(catchError(this.obtemErro));
  }

  public removeParametrosNull(data: any): any {
    const parametros = {};
    Object.keys(data).forEach(
      key =>  data[key] !== null ? parametros[key] = data[key] : key);
    return parametros;
}

  obtemErro(erro: HttpErrorResponse) {
    return throwError(erro.error.message);
  }

}
