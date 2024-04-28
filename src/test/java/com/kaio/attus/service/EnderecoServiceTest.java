package com.kaio.attus.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kaio.attus.config.exception.RegraDeNegocioException;
import com.kaio.attus.domain.endereco.Endereco;
import com.kaio.attus.domain.endereco.EnderecoRepository;
import com.kaio.attus.domain.endereco.EnderecoRequestDTO;
import com.kaio.attus.domain.endereco.EnderecoResponseDTO;
import com.kaio.attus.domain.endereco.EnderecoService;
import com.kaio.attus.domain.paginacao.PageDTO;
import com.kaio.attus.domain.pessoa.Pessoa;
import com.kaio.attus.domain.pessoa.PessoaRequestDTO;
import com.kaio.attus.domain.pessoa.PessoaResposeDTO;
import com.kaio.attus.domain.pessoa.PessoaService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnderecoServiceTest {

    @InjectMocks
    private EnderecoService enderecoService;

    @Mock
    private EnderecoRepository enderecoRepository;
    @Mock
    private PessoaService pessoaService;

    private ObjectMapper objectMapper = new ObjectMapper();

    private static final Integer pagina = 0;
    private static final Integer elementosPorPagina = 15;
    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(enderecoService, "objectMapper", objectMapper);
    }

    @Test
    public void quandoTentarCriarEnderecoRetornarSucesso() throws RegraDeNegocioException {

        Endereco endereco = mockEndereco();

        EnderecoRequestDTO enderecoRequestDTO = mockEnderecoRequestDTO();

        when(pessoaService.findById(anyInt())).thenReturn(mockPessoa());
        when(enderecoRepository.save(any())).thenReturn(endereco);

        EnderecoResponseDTO enderecoResponseDTO = enderecoService.criarEndereco(enderecoRequestDTO);

        assertNotNull(enderecoResponseDTO);
        assertEquals(endereco.getIdEndereco(), enderecoResponseDTO.getIdEndereco());
        assertEquals(endereco.getNumero(), enderecoResponseDTO.getNumero());
        assertEquals(endereco.getCep(), enderecoResponseDTO.getCep());
        assertEquals(endereco.getEstado(), enderecoResponseDTO.getEstado());
        assertEquals(endereco.getCidade(), enderecoResponseDTO.getCidade());
        assertEquals(endereco.getPrincipal(), enderecoResponseDTO.getPrincipal());
        assertEquals(endereco.getPessoa(), enderecoResponseDTO.getPessoa());
        assertEquals(endereco.getLogradouro(), enderecoResponseDTO.getLogradouro());
    }

    @Test
    public void quandoTentarConsultarEnderecosRetornarSucesso() {
        Endereco endereco = mockEndereco();
        List<Endereco> enderecos = new ArrayList<>();
        enderecos.add(endereco);
        Page<Endereco> enderecoPage = new PageImpl<>(enderecos);
        when(enderecoRepository.findAll(any(Pageable.class))).thenReturn(enderecoPage);

        PageDTO<EnderecoResponseDTO> enderecosResponseDTO =
                enderecoService.consultarEnderecos(pagina, elementosPorPagina);

        assertNotNull(enderecosResponseDTO);
        assertEquals(1, enderecosResponseDTO.getElementos().size());
    }

    @Test
    public void quandoTentarBuscarPorIdRetornarSucesso() throws RegraDeNegocioException {
        Endereco enderecoMock = mockEndereco();
        when(enderecoRepository.findById(anyInt())).thenReturn(Optional.of(enderecoMock));

        Endereco endereco = enderecoService.findById(anyInt());

        assertNotNull(endereco);
        assertEquals(enderecoMock.getIdEndereco(), endereco.getIdEndereco());
        assertEquals(enderecoMock.getNumero(), endereco.getNumero());
        assertEquals(enderecoMock.getCep(), endereco.getCep());
        assertEquals(enderecoMock.getEstado(), endereco.getEstado());
        assertEquals(enderecoMock.getCidade(), endereco.getCidade());
        assertEquals(enderecoMock.getPrincipal(), endereco.getPrincipal());
        assertEquals(enderecoMock.getPessoa(), endereco.getPessoa());
        assertEquals(enderecoMock.getLogradouro(), endereco.getLogradouro());
    }

    @Test
    public void quandoTentarBuscarPorIdRetornarNaoEcontrado() {
        when(enderecoRepository.findById(anyInt())).thenReturn(Optional.empty());

        RegraDeNegocioException exception = assertThrows(RegraDeNegocioException.class,
                () -> enderecoService.findById(anyInt()));

        assertEquals(exception.getMessage(), "Endereco não foi encontrado!");
    }

    @Test
    public void quandoTentarCriarEnderecoComEnderecoPrincipalJaExistenteRetornarExcecao() throws RegraDeNegocioException {
        Endereco endereco = mockEndereco();

        EnderecoRequestDTO enderecoRequestDTO = mockEnderecoRequestDTO();
        Pessoa pessoa = mockPessoa();
        Set<Endereco> enderecoSet = new HashSet<>();
        enderecoSet.add(endereco);
        pessoa.setEnderecos(enderecoSet);
        when(pessoaService.findById(anyInt())).thenReturn(pessoa);
        when(enderecoRepository.findByPrincipalAndPessoa(true, pessoa)).thenReturn(endereco);

        RegraDeNegocioException exception = assertThrows(RegraDeNegocioException.class,
                () -> enderecoService.criarEndereco(enderecoRequestDTO));

        assertEquals(exception.getMessage(), "Já existe um endereço principal para essa Pessoa, " +
                "edite antes para poder cadastrar um novo endereço principal");
    }

    @Test
    public void quandoTentarEditarEnderecoRetornarSucesso() throws RegraDeNegocioException {
        Endereco enderecoMock = mockEndereco();
        enderecoMock.setLogradouro("Rua de cima");
        EnderecoRequestDTO enderecoRequestDTO = mockEnderecoRequestDTO();
        when(pessoaService.findById(anyInt())).thenReturn(mockPessoa());
        when(enderecoRepository.findById(anyInt())).thenReturn(Optional.of(enderecoMock));
        when(enderecoRepository.save(any())).thenReturn(enderecoMock);

        EnderecoResponseDTO enderecoResponseDTO = enderecoService.editarEndereco(1, enderecoRequestDTO);

        assertNotNull(enderecoResponseDTO);
        assertEquals(enderecoResponseDTO.getIdEndereco(), enderecoMock.getIdEndereco());
        assertNotEquals(enderecoResponseDTO.getLogradouro(), enderecoMock.getLogradouro());

    }

    @Test
    public void quandoTentarEditarEnderecoPrincipalMudarOAntigoParaFalseRetornarSucesso()
            throws RegraDeNegocioException {
        Endereco enderecoMock = mockEndereco();
        enderecoMock.setLogradouro("Rua de cima");
        EnderecoRequestDTO enderecoRequestDTO = mockEnderecoRequestDTO();
        when(pessoaService.findById(anyInt())).thenReturn(mockPessoa());
        when(enderecoRepository.findById(anyInt())).thenReturn(Optional.of(enderecoMock));
        when(enderecoRepository.save(any())).thenReturn(enderecoMock);
        when(enderecoRepository.findByPrincipalAndPessoa(anyBoolean(), any())).thenReturn(enderecoMock);


        EnderecoResponseDTO enderecoResponseDTO = enderecoService.editarEndereco(1, enderecoRequestDTO);

        assertNotNull(enderecoResponseDTO);
        assertEquals(enderecoResponseDTO.getIdEndereco(), enderecoMock.getIdEndereco());
        assertNotEquals(enderecoResponseDTO.getLogradouro(), enderecoMock.getLogradouro());

    }


    private static EnderecoRequestDTO mockEnderecoRequestDTO() {
        EnderecoRequestDTO enderecoRequestDTO = new EnderecoRequestDTO();
        enderecoRequestDTO.setCep("49700000");
        enderecoRequestDTO.setPrincipal(true);
        enderecoRequestDTO.setCidade("Capela");
        enderecoRequestDTO.setEstado("Sergipe");
        enderecoRequestDTO.setLogradouro("Rua principal");
        enderecoRequestDTO.setNumero(10);
        enderecoRequestDTO.setIdPessoa(1);
        return enderecoRequestDTO;
    }

    private static Endereco mockEndereco() {
        Endereco endereco = new Endereco();
        endereco.setIdEndereco(1);
        endereco.setCep("49700000");
        endereco.setPrincipal(true);
        endereco.setCidade("Capela");
        endereco.setEstado("Sergipe");
        endereco.setLogradouro("Rua principal");
        endereco.setNumero(10);
        endereco.setPessoa(mockPessoa());
        return endereco;
    }


    private static Pessoa mockPessoa() {
        Pessoa pessoa = new Pessoa();
        pessoa.setIdPessoa(1);
        pessoa.setDataNascimento(LocalDate.of(2002, 3,14));
        pessoa.setNomeCompleto("Kaio Antônio");
        return pessoa;
    }
}
