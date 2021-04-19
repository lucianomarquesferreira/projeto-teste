package br.com.backendapi.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "contato")
public class Contato extends EntidadeBase {

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "email")
    private String email;
}
