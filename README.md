# Teste-Tecnico-Attus

## 💛 Teste 01 :
### ✅ Situação Problema:

1 - Na daily, seria honesto e direto. Diria: "Percebi que estamos atrás do cronograma para a nova funcionalidade. Após as últimas alterações, ainda temos testes pendentes que precisam de correções. Estou recalculando o tempo necessário para finalizar tudo. Proponho estender o prazo por "tantos" dias adicionais para garantir a entrega com qualidade. Durante esse período, priorizarei os testes e as correções necessárias e manterei a equipe atualizada sobre o progresso diariamente."

2 - Discutiria a situação com meu líder imediato explicando o status atual da implementação complexa em que estou envolvido e como isso se alinha com os prazos acordados. Porém, levaria em consideração a emergia da nova funcionalidade e segueria na implementação dela.

3 - 

Seguiria esses passos:

1. Avaliar a causa do problema: vou tentar analisar, entender o problema e por que aconteceu.
2. Correção: Priorizar a correção dos bugs, trabalhando intensamente com a equipe de QA para garantir que a solução seja efetiva.
3. Testar: Realizar uma bateria de testes para garantir que não haja mais falhas.
4. Deploy: Implementar a correção e monitorar o sistema para qualquer sinal de novos problemas ou falhas não detectadas inicialmente.
5. Revisar o problema: Após resolvar esse problema, eu iria revisar o processo que levou aos erros e implementar medidas para evitar reincidências.

4 - 

Faria algumas considerações:

1. Avaliar o problema que gerou o atraso.
2. Comunicaria antes sobre o atraso e porque.
3. Pediria ajuda para conseguir mitigar o atraso.
4. Manteria atualização frequente com todos os envolvidos para dar seguimento na implementação

## 💛 Teste 02 :
### Redação

**Tema: Os Desafios na Busca pelo Candidato Ideal para Preencher Vagas no Mercado de Trabalho Atual**

A indústria de tecnologia está em constante expansão e evolução, trazendo consigo desafios únicos na contratação de talentos adequados. Neste contexto, encontrar o candidato ideal para ocupar posições nesse mercado torna-se uma tarefa complexa e multifacetada, influenciada por diversos fatores que vão desde a rápida obsolescência das habilidades técnicas até a cultura de inovação constante que empresas de tecnologia exigem.

Um dos principais desafios é a escassez de profissionais qualificados. À medida que novas tecnologias surgem, há um hiato significativo entre as habilidades disponíveis no mercado e as exigidas pelas empresas. Por exemplo, com o surgimento da inteligência artificial e aprendizado de máquina, muitas empresas enfrentam dificuldades para encontrar profissionais que dominem essas competências avançadas. Essa lacuna cria uma competição acirrada entre as empresas, aumentando não apenas a dificuldade de contratação, mas também o custo para atrair e reter esses talentos.

Outro obstáculo é o alinhamento entre as habilidades técnicas do candidato e os valores da empresa. No mercado de tecnologia, não basta que o candidato seja apenas tecnicamente competente; ele também deve se adaptar e contribuir para a cultura de inovação da empresa, que muitas vezes inclui um ritmo acelerado de trabalho e uma constante busca por inovação. Esse alinhamento cultural é crucial para o sucesso a longo prazo do empregado dentro da organização e para o desenvolvimento de produtos e soluções que sejam verdadeiramente inovadores.

Adicionalmente, o processo de seleção em si muitas vezes não está equipado para avaliar adequadamente as habilidades necessárias no campo da tecnologia. Muitos métodos de recrutamento tradicionais falham em capturar a profundidade de competências técnicas ou em identificar a capacidade do candidato de aprender e adaptar-se a novas tecnologias. Isto exige que as empresas invistam em ferramentas de avaliação mais modernas e em treinamento para recrutadores, para que possam melhor identificar e avaliar talentos tecnológicos.

Por fim, a diversidade no local de trabalho, especialmente em tecnologia, é uma questão que ainda precisa de muita atenção. A inclusão de mulheres, minorias étnicas e outros grupos subrepresentados não só contribui para um ambiente de trabalho mais rico e criativo, mas também abre caminhos para um pool de talentos mais vasto e variado.

## 💛 Teste 03 :

### API:

1 - Para conseguir rodar o banco de dados, necessário docker.

O ambiente já possui o docker-compose.yml só utilizar o comando 

`docker-compose up` dentro da pasta Teste-Tecnico-Attus

#### Documentação da API

- **Consultar Pessoa**

```http
  GET /pessoa/consultarPessoas
```

| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `pagina` | `Integer` | **Obrigatório**. Paginação da API |
| `elementoPorPagina` | `Integer` | **Obrigatório**. Quantidade de elementos por página |

- **Criar Pessoa**

```http
  POST /pessoa/criarPessoa
```

| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `nomeCompleto` | `String` | **Obrigatório**. Nome Completo da Pessoa |
| `dataNascimento` | `LocalDate` | **Obrigatório**. Data de Nascimento da Pessoa |

- **Editar Pessoa**

```http
  PUT /pessoa/editarPessoa
```

| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `idPessoa` | `Integer` | **Obrigatório**. Id da Pessoa |
| `nomeCompleto` | `String` | **Obrigatório**. Nome Completo da Pessoa |
| `dataNascimento` | `LocalDate` | **Obrigatório**. Data de Nascimento da Pessoa |

- **Criar Endereço**

```http
  POST /endereco/criarEndereco
```

| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `logradouro` | `String` | **Obrigatório**. Logradouro |
| `cep` | `String` | **Obrigatório**. CEP |
| `numero` | `Integer` | **Obrigatório**. Número da casa |
| `cidade` | `String` | **Obrigatório**. Cidade |
| `estado` | `String` | **Obrigatório**. Estado |
| `principal` | `Boolean` | **Obrigatório**. Endereço principal |
| `idPessoa` | `Integer` | **Obrigatório**. Id da pessoa relacionada |

- **Editar Endereço**

```http
  PUT /endereco/editarEndereco
```

| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `logradouro` | `String` | **Obrigatório**. Logradouro |
| `cep` | `String` | **Obrigatório**. CEP |
| `numero` | `Integer` | **Obrigatório**. Número da casa |
| `cidade` | `String` | **Obrigatório**. Cidade |
| `estado` | `String` | **Obrigatório**. Estado |
| `principal` | `Boolean` | **Obrigatório**. Endereço principal |
| `idPessoa` | `Integer` | **Obrigatório**. Id da pessoa relacionada |
| `idEndereco` | `Integer` | **Obrigatório**. Id do endereço para edição |

#### Testes Unitários:

- Cobertura de 100% da services

![image](https://github.com/KaioAntonio/Teste-Tecnico-Attus/assets/75454785/93bcdee5-7e3b-4179-b7ce-18f02b3ab15e)



