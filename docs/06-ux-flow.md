# Easy Diet — Fluxo de UX para Dispositivos Móveis

## Objetivo deste documento

Este documento define o **fluxo de experiência do usuário do aplicativo Easy Diet para dispositivos móveis**.

Ele descreve como os usuários interagem com o sistema desde o primeiro acesso até o uso diário, traduzindo a lógica do backend em uma experiência móvel clara e intuitiva.

O aplicativo foi projetado com **prioridade para dispositivos móveis**, com a intenção de ser publicado em:
- Apple App Store (iOS)
- Google Play Store (Android)

---

## Princípios de UX

A experiência do usuário do Easy Diet segue estes princípios:

- Carga cognitiva mínima
- Sem cálculos manuais
- Feedback imediato após as ações
- Flexibilidade sem infringir regras
- Comunicação clara, porém não técnica

O usuário controla as **escolhas**, o sistema controla o **equilíbrio**.

---

## 1. Primeiro Acesso e Integração

### Objetivo
Coletar as informações mínimas necessárias para gerar uma dieta personalizada.

### Etapas
1. Tela de boas-vindas
2. Inserção de dados pessoais

- Sexo

- Idade

- Altura

- Peso
3. Seleção de objetivo

- Ganho de massa muscular

- Definição muscular
4. Seleção do tipo de dieta
5. Seleção dos dias de treino
6. Seleção das refeições por dia

### Observações sobre a experiência do usuário
- Não é necessário criar uma conta inicialmente
- Cada etapa é objetiva e simples
- Indicação clara do progresso

---

## 2. Geração da dieta

Após o cadastro:

- O aplicativo envia os dados coletados para o servidor
- Os mecanismos de nutrição e planejamento semanal geram o primeiro plano
- O usuário é redirecionado para a **Visão Semanal**

Este processo de geração é automático e transparente.

---

## 3. Visão Semanal (Tela Inicial)

### Descrição
A visão semanal é a **tela principal do aplicativo**.

### Conteúdo
- Semana atual (segunda a domingo)
- Cada dia mostra:

- Indicador de treino ou descanso

- Meta diária de calorias

Exemplo:
SEG 🏋️ 2700 kcal
TER 🏋️ 2700 kcal
QUA 😴 2300 kcal

### Interação
- Tocar em um dia abre a **Visão Diária**

---

## 4. Visão Diária

### Descrição
Exibe o plano alimentar completo para o dia selecionado.

### Conteúdo
- Lista de refeições
- Cada refeição mostra:

- Nome da refeição
- Alimentos e quantidades

### Notas de UX
- Layout limpo e legível
- Sem números de macronutrientes exibidos por padrão
- Indicadores visuais para ajustes automáticos

---

## 5. Edição de Refeições

### Descrição
Os usuários podem editar as refeições livremente.

### Ações Suportadas
- Substituir um alimento
- Alterar a quantidade
- Remover um alimento
- Adicionar um novo alimento

### Comportamento do Usuário
- Somente alimentos compatíveis são exibidos
- Opções inválidas são bloqueadas ou explicadas
- Alterações acionam recálculo automático

O usuário nunca vê fórmulas ou cálculos.

---

## 6. Feedback de Ajuste Automático

Sempre que o sistema ajusta o plano:

- Uma mensagem discreta é exibida
- Exemplo:

> “Ajustamos seu plano para manter o equilíbrio.”

Isso gera confiança sem sobrecarregar o usuário.

---

## 7. Gestão Alimentar

### Base de Dados Alimentares
- Base de dados oficial de alimentos (somente leitura)
- Filtrada por tipo de dieta

### Alimentos Personalizados
- Os usuários podem criar alimentos personalizados
- Os alimentos personalizados são reutilizáveis
- Não afetam a base de dados global

---

## 8. Acompanhamento Semanal

### Descrição
Uma vez por semana, o aplicativo solicita ao usuário que:

- Insira o peso atual

### Comportamento do Sistema
- O progresso é avaliado automaticamente
- As metas de calorias podem ser ajustadas
- A semana seguinte reflete o ajuste

---

## 9. Alteração de Dieta ou Meta

Se o usuário alterar:
- Tipo de dieta
- Meta
- Rotina de treino

O sistema:
- Gera um novo plano semanal
- Preserva os dados históricos
- Comunica claramente a alteração

---

## 10. Visualização do Histórico

### Conteúdo
- Semanas anteriores
- Progressão de peso
- Tipos de dieta ativos ao longo do tempo

Esta visualização auxilia no acompanhamento a longo prazo Sem rastreamento manual.

---

## Considerações sobre a Plataforma

A experiência do usuário (UX) foi projetada para:
- Seguir as Diretrizes de Interface Humana do iOS
- Seguir os princípios do Material Design do Android
- Ser responsiva e otimizada para toque
- Evitar elementos desnecessários e excesso de informações

---

## Resumo

A UX do Easy Diet prioriza:

- Simplicidade
- Confiança
- Flexibilidade
- Automação

Os usuários sentem que têm controle sobre o que comem, enquanto o sistema garante consistência e progresso nos bastidores.