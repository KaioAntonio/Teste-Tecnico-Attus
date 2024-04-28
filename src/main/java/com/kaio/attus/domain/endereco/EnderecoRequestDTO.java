package com.kaio.attus.domain.endereco;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class EnderecoRequestDTO {

    @NotBlank
    @Schema(description = "Logradouro", example = "Avenida principal")
    private String logradouro;

    @NotBlank
    @Schema(description = "CEP", example = "49700-000")
    private String cep;

    @NotNull
    @Schema(description = "Número da casa", example = "10")
    private Integer numero;

    @NotNull
    @Schema(description = "Cidade", example = "Aracaju")
    private String cidade;

    @NotBlank
    @Schema(description = "Estado", example = "Sergipe")
    private String estado;

    @NotNull
    @Schema(description = "Endereço principal", example = "true")
    private Boolean principal;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    @Schema(description = "Id da pessoa", example = "1")
    private Integer idPessoa;

}
