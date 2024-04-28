package com.kaio.attus.controller;

import com.kaio.attus.config.exception.RegraDeNegocioException;
import com.kaio.attus.domain.endereco.EnderecoRequestDTO;
import com.kaio.attus.domain.endereco.EnderecoResponseDTO;
import com.kaio.attus.domain.endereco.EnderecoService;
import com.kaio.attus.domain.paginacao.PageDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.PositiveOrZero;

@RestController
@Validated
@RequestMapping("/endereco")
@RequiredArgsConstructor
public class EnderecoController {

    private final EnderecoService enderecoService;

    @Operation(summary = "Criar um endereço", description = "Cria um endereço")
    @PostMapping("/criarEndereco")
    public ResponseEntity<EnderecoResponseDTO> criarEndereco(@RequestBody @Valid EnderecoRequestDTO endereco)
            throws RegraDeNegocioException {
        return new ResponseEntity<>(enderecoService.criarEndereco(endereco), HttpStatus.CREATED);
    }

    @Operation(summary = "Consultar endereços", description = "Consulta um ou mais endereços")
    @GetMapping("/consultarEndereco")
    public ResponseEntity<PageDTO<EnderecoResponseDTO>> consultarEndereco(
            @Parameter(description = "Paginação da API.")
            @RequestParam(defaultValue = "0") @PositiveOrZero Integer pagina,
            @Parameter(description = "Quantidade de elementos por página.")
            @RequestParam(defaultValue = "15") @Min(1) @PositiveOrZero Integer elementosPorPagina
    ) {
        return new ResponseEntity<>(enderecoService.consultarEnderecos(pagina,elementosPorPagina), HttpStatus.OK);
    }

    @Operation(summary = "Editar um endereço", description = "Edita os dados de um endereço")
    @PutMapping("/editarEndereco")
    public ResponseEntity<EnderecoResponseDTO> criarEndereco(
            @Parameter(description = "Id do Endereco.")
            @RequestParam(defaultValue = "1") @PositiveOrZero Integer idEndereco,
            @RequestBody @Valid EnderecoRequestDTO endereco)
            throws RegraDeNegocioException {
        return new ResponseEntity<>(enderecoService.editarEndereco(idEndereco,endereco), HttpStatus.OK);
    }

}
