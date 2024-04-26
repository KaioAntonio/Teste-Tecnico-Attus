package com.kaio.attus.domain.pessoa;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class PessoaRequestDTO {

    @NotBlank
    @Schema(description = "Nome Completo da Pessoa", example = "Kaio Antônio")
    private String nomeCompleto;

    @NotNull
    @Schema(description = "Data de Nascimento da Pessoa", example = "2002-03-14")
    private LocalDate dataNascimento;
}
