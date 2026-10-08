# Guia de Defesa do Codigo - GymTracker
Material de apoio para apresentacao academica - Desenvolvimento Mobile

---

## 1. Como o Codigo Esta Organizado

O projeto foi organizado em pacotes separados para que cada parte fique facil de localizar:

```text
com.faculdade.gymtracker/
│
├── MainActivity.kt                 <- Tela inicial (boas-vindas)
│
└── aula/                           <- Modulo avaliativo (codigo da aula)
    ├── Treino.kt                   (Data Class / Modelo de Dados)
    ├── BancoHelper.kt              (SQLiteOpenHelper - CRUD Completo)
    ├── AgendaActivity.kt           (Listagem Dinamica, Ciclo onResume, Exclusao)
    └── NovoEventoActivity.kt       (Formulario de Cadastro e Edicao)
```

---

## 2. Perguntas Tipicas e Respostas

### Pergunta 1: Onde esta o Banco de Dados e como ele e criado?
**Resposta:**
O banco e gerenciado pela classe `BancoHelper` dentro do pacote `aula`, herdando de `SQLiteOpenHelper`. O arquivo se chama `gymtracker.db`. Na primeira execucao do aplicativo, o Android executa o metodo `onCreate(db: SQLiteDatabase)`, executando o comando SQL `CREATE TABLE treinos (...)` com chave primaria auto-incrementada.

---

### Pergunta 2: Como funciona o CRUD (Inserir, Listar, Atualizar e Deletar)?
**Resposta:**
As 4 operacoes estao centralizadas na classe `BancoHelper.kt`:
1. **Create (Inserir):** Metodo `inserir(treino: Treino)`. Usa `ContentValues` com chave-valor e chama `db.insert("treinos", null, valores)`.
2. **Read (Listar):** Metodo `listar()`. Executa a query `SELECT ... FROM treinos ORDER BY id DESC`, percorre o `Cursor` com `moveToFirst()` e `moveToNext()`, retornando uma lista de objetos `Treino`.
3. **Update (Atualizar):** Metodo `atualizar(treino: Treino)`. Executa `db.update("treinos", valores, "id = ?", arrayOf(treino.id.toString()))`.
4. **Delete (Excluir):** Metodo `excluir(id: Int)`. Executa `db.delete("treinos", "id = ?", arrayOf(id.toString()))`.

---

### Pergunta 3: Como a tela sabe se e para cadastrar um novo ou editar um existente?
**Resposta:**
A mesma tela `NovoEventoActivity` atende aos dois casos. Ao clicar no botao de edicao de um treino na lista, a `AgendaActivity` passa o ID do treino pelo Intent via `putExtra("EXTRA_ID", treino.id)`. Na `NovoEventoActivity`, se o `EXTRA_ID` for `-1`, a tela trata como novo cadastro chamando `banco.inserir()`. Se for diferente de `-1`, preenche os campos com os dados existentes e executa `banco.atualizar()`.

---

### Pergunta 4: Por que a lista atualiza sozinha ao salvar ou editar um treino?
**Resposta:**
Porque a chamada `carregarTreinosDoBanco()` esta no metodo `onResume()` do ciclo de vida da `AgendaActivity`. Esse metodo e acionado sempre que a tela volta a ficar visivel apos fechar a tela de cadastro/edicao com `finish()`.

---

### Pergunta 5: Por que existe o pacote `aula`?
**Resposta:**
Para agrupar o codigo do trabalho avaliativo (modelo, SQLite, listagem e formulario) separado da tela inicial `MainActivity`, facilitando localizar cada parte durante a defesa.

---

## 3. Onde alterar se o professor pedir mudancas ao vivo

| Solicitacao do Professor | Arquivo | O que alterar |
| :--- | :--- | :--- |
| Criar novo campo na tabela | `aula/Treino.kt` e `aula/BancoHelper.kt` | Adicionar propriedade na classe, no SQL do `onCreate` e no `ContentValues` |
| Mudar ordenacao da lista | `aula/BancoHelper.kt` | No metodo `listar()`, mudar `ORDER BY id DESC` para `ORDER BY nome ASC` |
| Alterar validacao do formulario | `aula/NovoEventoActivity.kt` | No evento de clique do `btnSalvarEvento` |
| Exibir aviso na tela | Qualquer Activity | Usar `Toast.makeText(this, "Texto", Toast.LENGTH_SHORT).show()` |
