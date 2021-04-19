package br.com.backendapi.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Filtro {

    private Long id;

    private String nome;

    private String cpf;

    private String dataNascimento;
}
