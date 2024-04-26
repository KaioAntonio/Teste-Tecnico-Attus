package com.kaio.attus.domain.endereco;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class EnderecoRequestDTO {

    private String logradouro;
    private String CEP;
    private Integer numero;
    private String cidade;
    private String estado;
    private Boolean principal;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Integer idPessoa;

}
