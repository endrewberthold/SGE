# Documento de Requisitos Técnicos — [SGE - Sistema de Gestão de Estacionamento]

## 1. Visão Geral

### 1.1 Objetivo da Aplicação

Aplicação web para gestão inteligente de estacionamentos, com controle de entrada e saída de veículos, volumetria operacional e cálculo automatizado de tarifas por hora, diária, quinzena e mensalidade. A plataforma permite gerenciamento financeiro, monitoramento de ocupação e acompanhamento operacional em tempo real.

---

### 1.2 Problema que o Sistema Resolve

Muitos estacionamentos realizam o controle de movimentações de veículos de forma manual ou utilizando processos descentralizados, dificultando o acompanhamento operacional, o cálculo correto de permanência e a gestão financeira. O sistema foi desenvolvido para centralizar e simplificar o cadastro e gerenciamento das entradas e saídas de veículos, oferecendo uma interface limpa, objetiva e intuitiva, além de proporcionar maior controle sobre ocupação, tarifação e fluxo operacional do estacionamento.


---

### 1.3 Público-Alvo

* Administradores
* Usuários internos
* Clientes
* Parceiros
* Visitantes

---

### 1.4 Escopo Inicial (MVP)

O MVP (Produto Mínimo Viável) será focado inicialmente na operação interna do estacionamento, permitindo o gerenciamento completo das movimentações de veículos, controle de permanência, cálculo automático de tarifas e acompanhamento da ocupação em tempo real. Nesta primeira etapa, o sistema será utilizado exclusivamente pela administração do estacionamento, priorizando estabilidade operacional, controle financeiro e padronização dos processos.

Em fases futuras, a plataforma evoluirá para incluir uma área dedicada ao cliente, com autenticação via login social, permitindo acesso a informações como histórico de utilização, custos, tempo de permanência, pendências financeiras, disponibilidade de vagas e contratação de planos de mensalidade diretamente pela aplicação.

### Incluído

* Cadastro de veículos
* Cadastro de clientes
* Registro de entrada e saída de veículos
* Cálculo automático de estadia por hora, diária, quinzena e mensalidade
* Controle de permanência dos veículos
* Visualização de vagas ocupadas e disponíveis
* Histórico de movimentações
* Consulta de clientes e veículos cadastrados
* Controle básico de pagamentos e pendências
* Dashboard operacional com informações em tempo real

### Não Incluído

* Integração
* Aplicativo mobile
* Relatórios avançados

---

# 2. Regras de Negócio

## 2.1 Usuários e Permissões

| Perfil   | Permissões          |
| -------- | ------------------- |
| Admin    | Acesso total        |
| Operador | Operações limitadas |
| Cliente  | Consulta própria    |

---

## 2.2 Fluxos de Negócio

### Fluxo: Cadastro de Funcionário

1. Administrador acessa a área de gerenciamento de funcionários
2. Sistema solicita os dados do novo funcionário
3. Administrador preenche informações como:

    * nome
    * e-mail
    * cargo
    * permissões de acesso

4. Sistema valida os dados informados
5. Conta do funcionário é criada
6. Sistema envia um convite de acesso ou define uma senha inicial
7. Funcionário realiza o primeiro acesso e atualiza sua senha

---

### Fluxo: Login de Funcionário

1. Funcionário acessa o painel administrativo
2. Sistema solicita e-mail e senha
3. Sistema valida as credenciais
4. Sistema verifica o nível de permissão do usuário
5. Funcionário é direcionado ao painel correspondente ao seu perfil

---

## Fluxos de Clientes

### Fluxo: Cadastro de Cliente e Veículos pelo Funcionário

1. Operador acessa a tela de cadastro de clientes
2. Sistema solicita os dados do cliente
3. Operador preenche as informações do cliente
4. Sistema solicita o cadastro de um ou mais veículos vinculados
5. Operador informa os dados dos veículos
6. Sistema valida as informações cadastradas
7. Cliente e veículos são cadastrados e vinculados na plataforma

---

### Fluxo: Cadastro de Cliente pelo Próprio Usuário (Futuro)

1. Cliente acessa a área de cadastro/login da plataforma
2. Sistema apresenta opções de autenticação social
3. Cliente realiza login utilizando um provedor externo
4. Sistema valida os dados recebidos
5. Sistema solicita informações complementares do cliente
6. Cliente realiza o cadastro de um ou mais veículos
7. Sistema valida os dados informados
8. Conta do cliente e veículos são criados e vinculados automaticamente

---

### Fluxo: Login Social do Cliente

1. Cliente acessa a área do usuário
2. Sistema apresenta opções de login social
3. Cliente realiza autenticação via provedor externo
4. Sistema valida as informações recebidas
5. Sistema identifica ou cria a conta do cliente
6. Cliente acessa sua área com:

    * histórico de permanência
    * custos e pagamentos
    * mensalidades
    * veículos cadastrados
    * disponibilidade de vagas

---

### Fluxo: Adição de Novo Veículo para Cliente Existente

1. Cliente ou operador localiza o cadastro do cliente
2. Sistema exibe os veículos vinculados
3. Usuário seleciona a opção de adicionar veículo
4. Sistema solicita os dados do novo veículo
5. Dados são validados
6. Novo veículo é vinculado ao cliente

---

## Fluxos Operacionais do Estacionamento

### Fluxo: Entrada de Veículo

1. Operador informa a placa do veículo
2. Sistema localiza o cliente vinculado
3. Sistema registra data e horário de entrada
4. Veículo passa a constar como estacionado
5. Sistema atualiza a ocupação de vagas

---

### Fluxo: Saída de Veículo

1. Operador informa a placa do veículo
2. Sistema localiza a movimentação ativa
3. Sistema calcula o tempo de permanência
4. Sistema calcula automaticamente o valor da estadia
5. Operador realiza a cobrança
6. Sistema finaliza a movimentação
7. Sistema libera a vaga ocupada

---

## 2.3 Regras e Validações

### Exemplo

* E-mail deve ser único
* Documento deve ser único
* Senha deve conter:

    * 8 caracteres
    * letra maiúscula
    * número

* Clientes podem optar por login social
* Usuário inativo por 90 dias será bloqueado

---

# 3. Requisitos Funcionais

## RF-001 — Autenticação

O sistema deve permitir login utilizando e-mail e senha para administradores. Clientes utilizam login social.

---

## RF-002 — Cadastro de Usuários

O sistema deve permitir CRUD de usuários.

---

## RF-003 — Dashboard

O sistema deve apresentar métricas operacionais em tempo real.

---

## RF-004 — Logs

O sistema deve registrar ações críticas dos usuários.

---

# 4. Requisitos Não Funcionais

## Segurança

* Autenticação JWT/OAuth
* Criptografia de senha
* Controle de permissões
* Proteção contra SQL Injection e XSS

# 5. Próximas implementações

## Tarefas

* Finalizar o módulo de autorização por perfil [*****] x
* Finalizar  o fluxo de estacionamento [*****]
* Implementar CRUD completo de tarifas [****]
* Implementar CRUD completo de veículos [****]
* Criar tratamento global de exceções [****]
* Criar testes automatizados [****]

## Futuro

* Controle fino de permissões (CLIENT só gerencia seus próprios recursos).
* Cadastro de veículos pelo próprio cliente.
* Sistema de reserva de vagas com disponibilidade em tempo real.
* Fluxo de contratação de mensalidades.
* Integração com gateway de pagamento (Stripe, Mercado Pago, etc.).
* Regras para ativação automática após confirmação do pagamento.
* Cancelamentos, reembolsos e notificações.