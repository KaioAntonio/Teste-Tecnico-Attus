package com.kaio.attus.domain.pessoa;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kaio.attus.domain.endereco.Endereco;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "PESSOAS")
@Data
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PESSOA")
    private Integer idPessoa;

    @Column(name = "NOME_COMPLETO")
    private String nomeCompleto;

    @Column(name = "DATA_NASCIMENTO")
    private LocalDate dataNascimento;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pessoa")
    private Set<Endereco> enderecos;


}
