# GymTracker

App Android da disciplina Desenvolvimento Mobile.

Armazena treinos no SQLite com CRUD: incluir, listar, editar e excluir.

## Funcionalidades

- Tela inicial
- Agenda com listagem de treinos
- Formulario de cadastro e edicao
- Exclusao com confirmacao
- Banco local `gymtracker.db`

## Telas

1. `MainActivity`: entrada do app
2. `AgendaActivity`: lista de treinos
3. `NovoEventoActivity`: formulario

Codigo em `app/src/main/java/com/faculdade/gymtracker/aula/`.

## Requisitos

- Android Studio
- JDK 17
- minSdk 24, targetSdk 34
- Kotlin

## Executar

1. Abrir o projeto no Android Studio.
2. Sincronizar o Gradle.
3. Rodar no emulador ou aparelho.

Terminal (com JAVA_HOME configurado):

```bash
./gradlew assembleDebug
```

## Estrutura

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

Repositorio: https://github.com/joaofrancop/DevMobile-GymTracker
