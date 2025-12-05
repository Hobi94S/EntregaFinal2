# 🎟️ Sistema de Gestão de Eventos

> **Projeto de Conclusão da Disciplina de Programação Orientada a Objetos** > **Instituição:** Unifacisa  
> **Ano:** 2025  
> **Professor:** David

---

## 📖 Sobre o Projeto

Este projeto consiste em um sistema desenvolvido em **Java** para o gerenciamento de eventos corporativos e acadêmicos. O software permite o cadastro de diferentes tipos de eventos (Presenciais e Online), controle de inscrições de participantes (Pagantes e Convidados) e geração de relatórios e comprovantes.

O foco principal do desenvolvimento foi a aplicação prática dos pilares da **Programação Orientada a Objetos (POO)**, utilizando interface gráfica nativa via `JOptionPane` com formatação HTML para uma melhor experiência de usuário.

---

## ⚙️ Funcionalidades

O sistema conta com um menu interativo que oferece as seguintes opções:

* **Cadastrar Evento Presencial:** Registra título, data, local e define uma capacidade máxima de lotação.
* **Cadastrar Evento Online:** Registra título, data, plataforma, link de acesso e senha.
* **Inscrever Participante:** Permite associar participantes aos eventos cadastrados.
    * Validação de lotação para eventos presenciais.
    * Distinção entre **Pagante** (registra valor) e **Convidado** (registra empresa).
* **Listar Eventos:** Exibe todos os eventos com formatação visual.
* **Ver Participantes:** Gera uma lista de presença detalhada de um evento específico.
* **Gerar Comprovante:** Cria um comprovante visual (simulando um ingresso) com as informações cruciais do evento.
* **Relatório de Lotação:** Dashboard visual indicando a ocupação dos eventos presenciais (Verde = Disponível / Vermelho = Lotado).

---

## 🧩 Estrutura do Projeto e Diagrama de Classes

O projeto foi arquitetado utilizando hierarquia de classes e interfaces para garantir a extensibilidade e organização do código.

### 📂 Estrutura de Arquivos

```text
src/
├── Main.java              # Classe principal (Menu e Lógica de Execução)
├── Detalhes.java          # Interface (Contrato para exibição de dados)
├── Evento.java            # Classe Abstrata (Base para eventos)
├── EventoPresencial.java  # Subclasse de Evento
├── EventoOnline.java      # Subclasse de Evento
├── Participante.java      # Classe Abstrata (Base para pessoas)
├── Pagante.java           # Subclasse de Participante
└── Convidado.java         # Subclasse de Participante
