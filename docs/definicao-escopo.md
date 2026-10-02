# Sistema de gerenciamento de chamados de TI

### Objetivo Geral

Disponibilizar uma API para organizar o atendimento de chamados de TI, manter as informações relacionadas ao suporte e posteriormente, identificar os usuários e restringir operações administrativas.

### Objetivos especificos

1. Registrar chamados com titulo, descrição, categoria, prioridade e estado
2. Relacionar cada chamado a uma categoria com nome e descrição
3. Relacionar cada chamado ao solicitante e, quando atribuído, a um técnico.
4. Permitir acompanhamento por consultas paginadas, comentários e registros do histórico.
5. Manter cadastros de usuários e categorias.
6. Adicionar senha e login por sessão
7. Atribuir permissões a administrador e cadastro de técnicos com especialidade
8. Utilizar a arquitetura em camadas MVC, além de aplicar relacionamentos existentes.

### Requisitos Funcionais existentes

| ID | Requisito | Critério de aceitação |
| --- | --- | --- |
| RF01 | Cadastrar, consultar, listar, atualizar e excluir usuários com nome, e-mail e tipo | Operações de consulta, criação e atualização retornam DTOs. e-mail duplicado é rejeitado na criação e na atualização. A atualização permite alterar o tipo entre SOLICITANTE, TECNICO e ADMIN, sem verificar chamados anteriormente atribuídos ao usuário como técnico. Exclusões que violem vínculos existentes são rejeitadas com HTTP 409 |
| RF02 | Manter categorias com nome e descrição | CRUD disponível, consulta de ID inexistente retorna HTTP 404 |
| RF03 | Abrir chamado com título, descrição, estado, prioridade, solicitante, categoria e técnico opcional | Solicitante e categoria devem existir. O solicitante pode ser de qualquer tipo de usuário. Quando informado, o técnico deve existir e possuir tipo TECNICO. A data de abertura é preenchida pelo servidor; se o estado inicial for FINALIZADO, a data de resolução também é preenchida |
| RF04 | Consultar chamado por ID e listar chamados com paginação | Resposta contém a página com os resultados |
| RF05 | Pesquisar chamados por trecho do título sem diferenciar maiúsculas | Somente títulos que contêm o trecho são retornados |
| RF06 | Atualizar dados do chamado, incluindo estado, prioridade e relacionamentos | Grava os dados, mantém a data de abertura e ajusta a data de resolução. Permite mudar entre quaisquer estados aceitos, sem sequência obrigatória de transição |
| RF07 | Atribuir, substituir ou remover o técnico do chamado | Aceita técnico existente do tipo TECNICO ou valor nulo |
| RF08 | Excluir chamado individualmente ou excluir todos os chamados | Disponibiliza DELETE por ID e DELETE da coleção de chamados. Não exclui automaticamente comentários e históricos vinculados. Violações de integridade impedem a exclusão e retornam HTTP 409 |
| RF09 | Manter comentários vinculados a um usuário existente, independentemente do tipo, e a um chamado existente | CRUD disponível. A criação registra data e hora do servidor. A atualização permite alterar mensagem, usuário e chamado associados, preservando a data e hora originais |
| RF10 | Listar comentários por chamado, com paginação | O filtro chamadoId restringe a listagem ao chamado informado. a listagem é ordenada por ID. Um chamado inexistente produz página vazia |
| RF11 | Manter registros de histórico com descrição, tipo de evento, data e hora, usuário e chamado | CRUD próprio disponível. Cada registro é criado por requisição específica, com data e hora definidas pelo servidor. A atualização permite alterar descrição, tipo de evento, usuário e chamado, preservando a data e hora originais.  |
| RF12 | Listar históricos por chamado, com paginação | O filtro chamadoId restringe a listagem ao chamado informado. a listagem é ordenada por ID, Um chamado inexistente produz página vazia |
| RF13 | Receber senha no cadastro de cada tipo de usuário | Senha valida é transformada em hash e armazenada |
| RF14 | Criar administrador inicial com email e senha pré configurados | Cria somente um admin na tabela usuarios |
| RF16 | Permitir cadastro público de solicitante | Cadastro cria usuário do tipo solicitante |
|  |  |  |
|  |  |  |

### Requisitos Funcionais previstos

| ID | Requisito | Critério de aceitação |
| --- | --- | --- |
|  |  |  |
|  |  |  |
| RF15 | Não permitir cadastro público de técnico | Usuário do tipo técnico so pode ser cadastrado por admin |
|  |  |  |
| RF17 | Autenticação por email e senha | Credencial valida cria sessão, inválida retorna um erro (sem informar que a conta existe) |
| RF18 | Consultar o usuário conectado | Consulta retorna apenas informações permitidas |
| RF19 | Logout do usuário | Fazer logout impede que o usuário continue acessando a sessão |
| RF20 | Permitir troca da própria senha, após login bem sucedido | Solicitação de senha atual, senha incorreta impede alteração |
| RF21 | Controlar administração de usuários | Próprio administrador para consultas e edição, listagem e exclusão de usuários |
|  |  |  |

### Requisitos Não Funcionais existentes

| ID | Requisito | Critério de aceitação |
| --- | --- | --- |
| RNF01 | Organização em Controller, Service/Impl, Repository, Entity, DTO, Mapper e Exception. | Controllers conduzem, persistência e regras permanecem nas camadas correspondentes |
| RNF02 | Comunicação HTTP com JSON e contratos de entrada/saída separados. | Endpoints retornam DTOs e códigos HTTP compatíveis |
| RNF03 | Persistência em PostgreSQL e uso de transacional. | Operações inválidas não deixam gravações parciais, Chaves estrangeiras preservam vínculos |
| RNF04 | Listagens paginadas com contagem total e ordenação definida | Page/Pageable e countQuery são utilizados nas consultas nativas de listagem. |
| RNF05 | Validação centralizada e erros padronizados. | Testar 400 para entrada/regra inválida, 404 para ausência e 409 para conflito |
| RNF06 | Manter Java 21, Spring Boot, JPA, Maven, PostgreSQL e MapStruct | Projeto compila no ambiente definido, não introduz Spring Security nesta etapa |
| RNF07 | Proteger senhas armazenadas e transmitidas. | Uso de hash |
|  |  |  |

### Requisitos Não Funcionais previstos

| ID | Requisito | Critério de aceitação |
| --- | --- | --- |
|  |  |  |
| RNF08 | Gerenciar sessões de forma controlada | Renovar sessão no login, expirar por inatividade e invalidar no logout ou ao trocar a senha |
| RNF09 | Integrar cookies com origens explícitas do frontend | Validar CORS e cookies, origem não autorizada não recebe acesso com credenciais |
| RNF10 | Conter tentativas repetidas de autenticação | Aplicar limite configurado e resposta, não revelar se o e-mail está cadastrado. |
| RNF11 | Preservar dados e manter testes reproduzíveis | Migrar banco de desenvolvimento sem perder IDs/vínculos |

### Regras de Negócio implementadas

| ID | Regra |
| --- | --- |
| RN01 | Usuário deve possuir nome, e-mail válido e tipo SOLICITANTE, TECNICO ou ADMIN |
| RN02 | O e-mail não pode estar em uso por outro usuário, independentemente de maiúsculas ou minúsculas. A verificação ocorre na criação e na atualização. Na atualização, o próprio usuário é desconsiderado na verificação, permitindo manter seu e-mail atual |
| RN03 | Chamado exige título, descrição, estado, prioridade, solicitante existente e categoria existente. O usuário vinculado como solicitante pode possuir tipo SOLICITANTE, TECNICO ou ADMIN |
| RN04 | O técnico do chamado é opcional. Quando informado, deve existir e possuir tipo TECNICO |
| RN05 | Os estados aceitos são ABERTO, PROCESSANDO e FINALIZADO. As prioridades são BAIXA, MEDIA e ALTA |
| RN06 | A data de abertura é definida pelo servidor na criação e mantida nas atualizações. O estado inicial é informado na requisição e pode ser ABERTO, PROCESSANDO ou FINALIZADO |
| RN07 | Sempre que um chamado é criado ou atualizado com estado FINALIZADO, sua data de resolução é preenchida pelo servidor caso esteja vazia. Se já estiver preenchida e o estado continuar FINALIZADO, ela é mantida. Ao atualizar para outro estado, a data de resolução é removida |
| RN08 | Comentário exige mensagem, usuário existente de qualquer tipo e chamado existente. A data e hora são definidas na criação. A atualização permite mudar o usuário e o chamado associados |
| RN09 | Os usuários aceitos são SOLICITANTE, TECNICO, ADMIN |
| RN10 | Histórico exige descrição, tipo de evento, usuário existente de qualquer tipo e chamado existente. Sua criação depende de requisição específica e não ocorre automaticamente em operações sobre chamados ou comentários. |
| RN11 | Tipos de evento aceitos: CRIACAO, ALTERACAO, ATRIBUICAO, COMENTARIO, RESOLUCAO e REABERTURA. |
| RN12 | Textos mapeados são normalizados com remoção de espaços nas extremidades, e-mails também são normalizados e convertidos para minúsculas |
| RN13 | Usuários referenciados por chamados, comentários ou históricos, categorias referenciadas por chamados e chamados referenciados por comentários ou históricos não são excluídos em cascata. Tentativas de exclusão que violem a integridade de relacionamentos são rejeitadas com HTTP 409. |
| RN14 | A atualização permite alterar o tipo entre SOLICITANTE, TECNICO e ADMIN. Essa operação não verifica nem remove vínculos de chamados anteriormente atribuídos ao usuário como técnico |
| RN13 | A senha pertence a usuario para todos os tipos, tecnico armazena somente os dados específicos (especialidade) e o vínculo ao usuário. |
| RN14 | Cada perfil tecnico pertence a exatamente um usuario(1 para 1). Um usuário possui no máximo um perfil técnico, usuario_id deve ser único e obrigatório. |
| RN15 | Especialidade é obrigatória para tecnico e não deve ser cadastrada para outros tipos de usuário |
|  |  |
|  |  |
|  |  |

### Regras de Negócios previstas

| ID | Regra para a evolução |
| --- | --- |
|  |  |
|  |  |
|  |  |
| RN16 | Somente o admin autenticado pode cadastrar técnico ou alterar o tipo de outro usuário entre solicitante e tecnico. Um solicitante ou tecnico não pode se promover. |
| RN17 | O administrador inicial não é cadastrado pelo fluxo público. A inicialização preserva contas existentes. |
| RN18 | Somente o próprio usuário autenticado pode trocar sua senha, após confirmar a atual. |
|  |  |
|  |  |

### Atores

| Ator | Responsabilidade | Comportamento |
| --- | --- | --- |
| Solicitante | Registrar chamados e acompanhar atendimentos | Login e limite de acesso previsto |
| Técnico | Atuar no atendimento de chamados e registrar informações do suporte | Cadastro exclusivo pelo admin é previsto |
| Administrador | Administrar os cadastros e gerenciar técnicos e solicitantes | Conta inicial e permissões sao previstos |

### Cardinalidades

Usuario 1 para N Chamados como solicitante. Cada chamado exige um solicitante

Usuario 1 para N Chamados como tecnico. Cada chamado pode ter zero ou um tecnico

Categoria 1 para N chamados. Cada chamado tem que ter uma  categoria

Usuario 1 para N Comentario. Cada usuario pode fazer varios comentarios

Usuario 1 para N HistoricoChamado. Cada usuario pode ter varios historicos de chamado

Chamado 1 para N Comentario. Cada comentario deve ter um chamado

Chamado 1 para N HistoricoChamado. Cada historico de chamado deve ter um chamado

Usuario 1 para 1 Tecnico. Cada tecnico deve ser um usuario

### Escopo existente

- CRUD de usuários, categorias, chamados, comentários e históricos, listagens paginadas e busca individual
- Busca de chamados por trecho do título, filtragem de comentários e históricos por chamado
- Atribuição opcional de técnico, classificação por estado/prioridade e datas preenchidas pelo servidor
- Validação de entrada, normalização de texto/e-mail, tratamento de erros e persistência no banco de dados

### Evolução prevista

- [x]  Senha para todos os tipos de usuário
- [x]  Usuário do tipo técnico com especialidade
- [x]  Administrador criado na inicialização
- [ ]  Login e logout
- [ ]  Sessão autenticada
- [ ]  Troca da própria senha
- [ ]  Restrição do cadastro de técnicos ao administrador e proteção das alterações de perfil

### Validações de Entrada

| Informação | Regra de validação |
| --- | --- |
| Nome do usuário | Obrigatório, não pode conter apenas espaços e deve possuir até 150 caracteres |
| E-mail | Obrigatório, deve apresentar formato válido e possuir até 254 caracteres |
| Senha | Obrigatório, deve ter entre 8 e 128 caracteres e não pode ficar em branco |
| Tipo de usuário | Obrigatório: SOLICITANTE, TECNICO ou ADMIN |
| Especialidade | Obrigatório para usuário do tipo TECNICO, não precisa ser informado por SOLICITANTE ao se cadastrar |
| Nome da categoria | Obrigatório, não pode conter apenas espaços e deve possuir até 100 caracteres |
| Descrição da categoria | Obrigatória, não pode conter apenas espaços e deve possuir até 10.000 caracteres |
| Título do chamado | Obrigatório, não pode conter apenas espaços e deve possuir até 200 caracteres |
| Descrição do chamado | Obrigatória, não pode conter apenas espaços e deve possuir até 10.000 caracteres |
| Estado do chamado | Obrigatório: ABERTO, PROCESSANDO ou FINALIZADO |
| Prioridade do chamado | Obrigatória: BAIXA, MEDIA ou ALTA |
| Mensagem do comentário | Obrigatória, não pode conter apenas espaços e deve possuir até 10.000 caracteres |
| Descrição do histórico | Obrigatória, não pode conter apenas espaços e deve possuir até 10.000 caracteres |
| Tipo de evento do histórico | Obrigatório: CRIACAO, ALTERACAO, ATRIBUICAO, COMENTARIO, RESOLUCAO ou REABERTURA |
| IDs de relacionamentos | Devem ser positivos, obrigatórios e referenciar registros existentes, exceto tecnicoId, que pode ser omitido ou receber null |