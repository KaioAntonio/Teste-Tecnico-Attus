package com.kaio.attus.domain.endereco;

import com.kaio.attus.domain.pessoa.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {

    Endereco findByPrincipalAndPessoa(Boolean principal, Pessoa pessoa);
}
