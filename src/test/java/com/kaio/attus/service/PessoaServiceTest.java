package com.kaio.attus.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kaio.attus.config.exception.RegraDeNegocioException;
import com.kaio.attus.domain.paginacao.PageDTO;
import com.kaio.attus.domain.pessoa.Pessoa;
import com.kaio.attus.domain.pessoa.PessoaRepository;
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
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PessoaServiceTest {

    @InjectMocks
    private PessoaService pessoaService;

    @Mock
    private PessoaRepository pessoaRepository;

    private static final Integer pagina = 0;
    private static final Integer elementosPorPagina = 15;

    private ObjectMapper objectMapper = new ObjectMapper();
    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(pessoaService, "objectMapper", objectMapper);
    }

    @Test
    public void quandoTentarCriarPessoaRetornarSucesso() {
        Pessoa pessoa = mockPessoa();
        PessoaRequestDTO pessoaRequestDTO = mockPessoaRequestDTO(pessoa);

        when(pessoaRepository.save(any())).thenReturn(pessoa);

        PessoaResposeDTO pessoaResposeDTO = pessoaService.criarPessoa(pessoaRequestDTO);

        assertNotNull(pessoaResposeDTO);
        assertEquals(pessoa.getNomeCompleto(), pessoaResposeDTO.getNomeCompleto());
        assertEquals(pessoa.getDataNascimento(), pessoaResposeDTO.getDataNascimento());
    }

    @Test
    public void quandoTentarConsultarPessoasRetornarSucesso() {
        Pessoa pessoa = mockPessoa();
        List<Pessoa> pessoaList = new ArrayList<>();
        pessoaList.add(pessoa);
        Page<Pessoa> pessoaPage = new PageImpl<>(pessoaList);
        when(pessoaRepository.findAll(any(Pageable.class))).thenReturn(pessoaPage);

        PageDTO<PessoaResposeDTO> pessoaResposeDTO = pessoaService.consultarPessoas(pagina, elementosPorPagina);

        assertNotNull(pessoaResposeDTO);
        assertEquals(1, pessoaResposeDTO.getElementos().size());
    }

    @Test
    public void quandoTentarBuscarPorIdRetornarSucesso() throws RegraDeNegocioException {
        Pessoa pessoaMock = mockPessoa();
        when(pessoaRepository.findById(anyInt())).thenReturn(Optional.of(pessoaMock));

        Pessoa pessoa = pessoaService.findById(anyInt());

        assertNotNull(pessoa);
        assertEquals(pessoaMock.getNomeCompleto(), pessoa.getNomeCompleto());
        assertEquals(pessoaMock.getDataNascimento(), pessoa.getDataNascimento());
    }

    @Test
    public void quandoTentarBuscarPorIdRetornarNaoEcontrado() {
        when(pessoaRepository.findById(anyInt())).thenReturn(Optional.empty());

        RegraDeNegocioException exception = assertThrows(RegraDeNegocioException.class,
                () -> pessoaService.findById(anyInt()));

        assertEquals(exception.getMessage(), "Pessoa não foi encontrada!");
    }


    @Test
    public void quandoTentarEditarPessoaRetornarSucesso() throws RegraDeNegocioException {
        Pessoa pessoaMock = mockPessoa();
        pessoaMock.setNomeCompleto("Alex");
        PessoaRequestDTO pessoaRequestDTO = mockPessoaRequestDTO(pessoaMock);
        when(pessoaRepository.findById(anyInt())).thenReturn(Optional.of(pessoaMock));
        when(pessoaRepository.save(any())).thenReturn(pessoaMock);

        PessoaResposeDTO pessoaResposeDTO = pessoaService.editarPessoa(1, pessoaRequestDTO);

        assertNotNull(pessoaResposeDTO);
        assertNotEquals(pessoaResposeDTO.getNomeCompleto(), pessoaMock.getNomeCompleto());
        assertEquals(pessoaResposeDTO.getDataNascimento(), pessoaMock.getDataNascimento());

    }


    private static Pessoa mockPessoa() {
        Pessoa pessoa = new Pessoa();

        pessoa.setDataNascimento(LocalDate.of(2002, 3,14));
        pessoa.setNomeCompleto("Kaio Antônio");
        return pessoa;
    }

    private static PessoaRequestDTO mockPessoaRequestDTO(Pessoa pessoa) {
        PessoaRequestDTO pessoaRequestDTO = new PessoaRequestDTO();
        pessoaRequestDTO.setDataNascimento(LocalDate.of(2002,3,14));
        pessoa.setNomeCompleto("Kaio Antônio");
        return pessoaRequestDTO;
    }

}
