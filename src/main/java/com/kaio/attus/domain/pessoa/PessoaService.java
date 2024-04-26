package com.kaio.attus.domain.pessoa;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaio.attus.config.exception.RegraDeNegocioException;
import com.kaio.attus.domain.paginacao.PageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PessoaService {

    private final PessoaRepository pessoaRepository;
    private final ObjectMapper objectMapper;

    public PessoaResposeDTO criarPessoa(PessoaRequestDTO pessoaRequestDTO) {
        Pessoa pessoa =  objectMapper.convertValue(pessoaRequestDTO, Pessoa.class);
        return objectMapper.convertValue(pessoaRepository.save(pessoa), PessoaResposeDTO.class);
    }
    
    public Pessoa findById(Integer id) throws RegraDeNegocioException {
        return objectMapper.convertValue(pessoaRepository.findById(id).orElseThrow(
                () -> new RegraDeNegocioException("Pessoa não foi encontrada!")), Pessoa.class);
    }

    public PageDTO<PessoaResposeDTO> consultarPessoas(Integer pagina, Integer elementosPorPagina) {

        Pageable solicitacaoPagina = PageRequest.of(pagina, elementosPorPagina);
        Page<Pessoa> pessoas = pessoaRepository.findAll(solicitacaoPagina);
        List<PessoaResposeDTO> pessoaResposeDTOS = pessoas.getContent().stream()
                .map(x -> objectMapper.convertValue(x, PessoaResposeDTO.class))
                .toList();

        return new PageDTO<>(pessoas.getTotalElements(),
                pessoas.getTotalPages(),
                pagina,
                elementosPorPagina,
                pessoaResposeDTOS);
    }

    public PessoaResposeDTO editarPessoa(Integer id, PessoaRequestDTO pessoaRequestDTO) throws RegraDeNegocioException {
        Pessoa pessoa = findById(id);
        Pessoa pessoaAtualizada = objectMapper.convertValue(pessoaRequestDTO, Pessoa.class);

        pessoa.setNomeCompleto(pessoaAtualizada.getNomeCompleto());
        pessoa.setDataNascimento(pessoaAtualizada.getDataNascimento());

        pessoaRepository.save(pessoa);
        return objectMapper.convertValue(pessoa, PessoaResposeDTO.class);

    }
}
