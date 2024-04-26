package com.kaio.attus.domain.endereco;

import com.kaio.attus.domain.pessoa.Pessoa;
import lombok.Data;

@Data
public class EnderecoResponseDTO extends EnderecoRequestDTO{

    private Integer idEndereco;
    private Pessoa pessoa;
}
