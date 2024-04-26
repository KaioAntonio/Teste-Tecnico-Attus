package com.kaio.attus.controller;

import com.kaio.attus.config.exception.RegraDeNegocioException;
import com.kaio.attus.domain.paginacao.PageDTO;
import com.kaio.attus.domain.pessoa.PessoaRequestDTO;
import com.kaio.attus.domain.pessoa.PessoaResposeDTO;
import com.kaio.attus.domain.pessoa.PessoaService;
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
@RequestMapping("/pessoa")
@RequiredArgsConstructor
public class PessoaController {

    private final PessoaService pessoaService;

    @Operation(summary = "Criar uma pessoa", description = "Cria uma pessoa")
    @PostMapping("/criarPessoa")
    public ResponseEntity<PessoaResposeDTO> criarPessoa(@RequestBody @Valid PessoaRequestDTO pessoa){
        return new ResponseEntity<>(pessoaService.criarPessoa(pessoa), HttpStatus.CREATED);
    }

    @Operation(summary = "Consulta pessoas", description = "Consulta uma ou mais pessoas.")
    @GetMapping("/consultarPessoas")
    public ResponseEntity<PageDTO<PessoaResposeDTO>> criarPessoa(
            @Parameter(description = "Paginação da API.")
            @RequestParam(defaultValue = "0") @PositiveOrZero Integer pagina,
            @Parameter(description = "Quantidade de elementos por página.")
            @RequestParam(defaultValue = "15") @Min(1) @PositiveOrZero Integer elementosPorPagina
    ){
        return new ResponseEntity<>(pessoaService.consultarPessoas(pagina,
                elementosPorPagina), HttpStatus.OK);
    }

    @Operation(summary = "Editar pessoa", description = "Edita os dados de uma pessoa")
    @PutMapping("/editarPessoa")
    public ResponseEntity<PessoaResposeDTO> editarPessoa(
            @Parameter(description = "Id da Pessoa.")
            @RequestParam(defaultValue = "1") @PositiveOrZero Integer idPessoa,
            @RequestBody @Valid PessoaRequestDTO pessoa
    ) throws RegraDeNegocioException {
        return new ResponseEntity<>(pessoaService.editarPessoa(idPessoa, pessoa), HttpStatus.OK);
    }

}
