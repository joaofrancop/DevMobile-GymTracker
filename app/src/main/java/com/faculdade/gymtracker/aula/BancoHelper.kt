package com.faculdade.gymtracker.aula

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// Gerenciador do banco de dados SQLite local
class BancoHelper(context: Context) :
    SQLiteOpenHelper(context, "gymtracker.db", null, 1) {

    // Cria a tabela no banco
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE treinos (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nome TEXT NOT NULL,
                data TEXT,
                local TEXT,
                descricao TEXT,
                favorito INTEGER DEFAULT 0
            )
            """.trimIndent()
        )
    }

    // Atualiza a versao do banco se necessario
    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
        db.execSQL("DROP TABLE IF EXISTS treinos")
        onCreate(db)
    }

    // Insere um novo treino
    fun inserir(treino: Treino): Long {
        val db = writableDatabase
        val valores = ContentValues().apply {
            put("nome", treino.nome)
            put("data", treino.data)
            put("local", treino.local)
            put("descricao", treino.descricao)
            put("favorito", if (treino.favorito) 1 else 0)
        }
        val idGerado = db.insert("treinos", null, valores)
        db.close()
        return idGerado
    }

    // Lista todos os treinos ordenados pelos mais recentes
    fun listar(): List<Treino> {
        val lista = mutableListOf<Treino>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT id, nome, data, local, descricao, favorito FROM treinos ORDER BY id DESC", null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                val nome = cursor.getString(cursor.getColumnIndexOrThrow("nome"))
                val data = cursor.getString(cursor.getColumnIndexOrThrow("data"))
                val local = cursor.getString(cursor.getColumnIndexOrThrow("local"))
                val descricao = cursor.getString(cursor.getColumnIndexOrThrow("descricao"))
                val favorito = cursor.getInt(cursor.getColumnIndexOrThrow("favorito")) == 1

                lista.add(
                    Treino(
                        id = id,
                        nome = nome,
                        data = data,
                        local = local,
                        descricao = descricao,
                        favorito = favorito
                    )
                )
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return lista
    }

    // Atualiza os dados de um treino existente
    fun atualizar(treino: Treino): Int {
        val db = writableDatabase
        val valores = ContentValues().apply {
            put("nome", treino.nome)
            put("data", treino.data)
            put("local", treino.local)
            put("descricao", treino.descricao)
            put("favorito", if (treino.favorito) 1 else 0)
        }
        val linhasAfetadas = db.update("treinos", valores, "id = ?", arrayOf(treino.id.toString()))
        db.close()
        return linhasAfetadas
    }

    // Exclui um treino pelo ID
    fun excluir(id: Int): Int {
        val db = writableDatabase
        val linhasAfetadas = db.delete("treinos", "id = ?", arrayOf(id.toString()))
        db.close()
        return linhasAfetadas
    }
}
