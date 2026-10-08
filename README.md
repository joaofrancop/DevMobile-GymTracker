# GymTracker

Aplicativo Android desenvolvido para a disciplina **Desenvolvimento Mobile**.

Gerencia treinos com persistência local em **SQLite** e operações **CRUD** (criar, listar, editar e excluir).

## Funcionalidades

- Tela inicial com apresentação do app
- Agenda de treinos com listagem dinâmica
- Formulário para cadastro e edição de treinos
- Exclusão com diálogo de confirmação
- Dados salvos localmente (`gymtracker.db`)

## Telas (Activities)

1. `MainActivity` — boas-vindas e acesso à agenda
2. `AgendaActivity` — lista de treinos
3. `NovoEventoActivity` — cadastro / edição

Código do módulo avaliativo em `app/src/main/java/com/faculdade/gymtracker/aula/`.

## Requisitos

- Android Studio (recomendado)
- JDK 17
- `minSdk` 24 · `targetSdk` 34
- Kotlin

## Como executar

1. Abra a pasta do projeto no Android Studio.
2. Aguarde o Gradle sincronizar.
3. Execute no emulador ou dispositivo (**Run** ▶).

Via terminal (com `JAVA_HOME` configurado):

```bash
./gradlew assembleDebug
```

## Estrutura principal

```
app/src/main/
  java/com/faculdade/gymtracker/
    MainActivity.kt
    aula/
      AgendaActivity.kt
      NovoEventoActivity.kt
      BancoHelper.kt
      Treino.kt
  res/layout/
    activity_main.xml
    activity_agenda.xml
    activity_novo_evento.xml
    item_treino.xml
```

## Repositório

[DevMobile-GymTracker](https://github.com/joaofrancop/DevMobile-GymTracker)
