package com.kaio.attus.domain.endereco;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaio.attus.config.exception.RegraDeNegocioException;
import com.kaio.attus.domain.paginacao.PageDTO;
import com.kaio.attus.domain.pessoa.Pessoa;
import com.kaio.attus.domain.pessoa.PessoaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;
    private final PessoaService pessoaService;
    private final ObjectMapper objectMapper;

    public EnderecoResponseDTO criarEndereco(EnderecoRequestDTO enderecoRequestDTO) throws RegraDeNegocioException {
        Pessoa pessoa = pessoaService.findById(enderecoRequestDTO.getIdPessoa());
        Endereco endereco = objectMapper.convertValue(enderecoRequestDTO, Endereco.class);
        endereco.setPessoa(pessoa);
        validarEnderecoPrincipal(enderecoRequestDTO, pessoa);
        return objectMapper.convertValue(enderecoRepository.save(endereco), EnderecoResponseDTO.class);
    }

    public PageDTO<EnderecoResponseDTO> consultarEnderecos(Integer pagina, Integer elementosPorPagina) {

        Pageable solicitacaoPagina = PageRequest.of(pagina, elementosPorPagina);
        Page<Endereco> enderecos = enderecoRepository.findAll(solicitacaoPagina);
        List<EnderecoResponseDTO> enderecoResponseDTOS = enderecos.getContent().stream()
                .map(x -> objectMapper.convertValue(x, EnderecoResponseDTO.class))
                .toList();

        return new PageDTO<>(enderecos.getTotalElements(),
                enderecos.getTotalPages(),
                pagina,
                elementosPorPagina,
                enderecoResponseDTOS);
    }

    public EnderecoResponseDTO editarEndereco(Integer id, EnderecoRequestDTO enderecoRequestDTO) throws RegraDeNegocioException {
        Pessoa pessoa = pessoaService.findById(enderecoRequestDTO.getIdPessoa());
        Endereco endereco = findById(id);
        Endereco enderecoAtualizado = objectMapper.convertValue(enderecoRequestDTO, Endereco.class);
        endereco.setPessoa(pessoa);
        endereco.setCep(enderecoAtualizado.getCep());
        endereco.setCidade(enderecoAtualizado.getCidade());
        endereco.setEstado(enderecoAtualizado.getEstado());
        endereco.setLogradouro(enderecoAtualizado.getLogradouro());
        endereco.setNumero(enderecoAtualizado.getNumero());

        if(enderecoAtualizado.getPrincipal()) {
            Endereco enderecoPrincipal =
                    enderecoRepository.findByPrincipalAndPessoa(true, pessoa);
            if(enderecoPrincipal != null) {
                enderecoPrincipal.setPrincipal(false);
            }
        }
        endereco.setPrincipal(enderecoAtualizado.getPrincipal());

        enderecoRepository.save(endereco);
        return objectMapper.convertValue(endereco, EnderecoResponseDTO.class);
    }

    public Endereco findById(Integer id) throws RegraDeNegocioException {
        return objectMapper.convertValue(enderecoRepository.findById(id).orElseThrow(
                () -> new RegraDeNegocioException("Endereco não foi encontrado!")), Endereco.class);
    }

    private void validarEnderecoPrincipal(EnderecoRequestDTO enderecoRequestDTO, Pessoa pessoa)
            throws RegraDeNegocioException {
        if (enderecoRequestDTO.getPrincipal()) {
            if (enderecoRepository.findByPrincipalAndPessoa(true, pessoa) != null) {
                throw new RegraDeNegocioException("Já existe um endereço principal para essa Pessoa, " +
                        "edite antes para poder cadastrar um novo endereço principal");
            }
        }
    }

}
