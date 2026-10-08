package com.faculdade.gymtracker.aula

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.faculdade.gymtracker.R

// Tela de listagem da agenda de treinos
class AgendaActivity : AppCompatActivity() {

    private lateinit var banco: BancoHelper
    private lateinit var layoutListaTreinos: LinearLayout
    private lateinit var tvSemTreinos: TextView
    private lateinit var tvStreakValor: TextView
    private lateinit var tvStreakDesc: TextView
    private lateinit var tvUltimoTreino: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agenda)

        banco = BancoHelper(this)

        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        val btnSobre = findViewById<ImageButton>(R.id.btnSobre)
        val btnNovoEvento = findViewById<Button>(R.id.btnNovoEvento)

        layoutListaTreinos = findViewById(R.id.layoutListaTreinos)
        tvSemTreinos = findViewById(R.id.tvSemTreinos)
        tvStreakValor = findViewById(R.id.tvStreakValor)
        tvStreakDesc = findViewById(R.id.tvStreakDesc)
        tvUltimoTreino = findViewById(R.id.tvUltimoTreino)

        btnBack.setOnClickListener {
            finish()
        }

        // Dialog sobre o app
        btnSobre.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("GymTracker - Sobre")
                .setMessage("Trabalho da disciplina Desenvolvimento Mobile.\n\nAgenda de treinos com SQLite e CRUD.")
                .setPositiveButton("Fechar", null)
                .show()
        }

        // Abre a tela de cadastro
        btnNovoEvento.setOnClickListener {
            val intent = Intent(this, NovoEventoActivity::class.java)
            startActivity(intent)
        }
    }

    // Atualiza a lista sempre que voltar para esta tela
    override fun onResume() {
        super.onResume()
        carregarTreinosDoBanco()
    }

    // Busca os treinos no SQLite e exibe na tela
    private fun carregarTreinosDoBanco() {
        layoutListaTreinos.removeAllViews()

        val treinos = banco.listar()
        val total = treinos.size

        tvStreakValor.text = "$total ${if (total == 1) "treino" else "treinos"}"

        if (treinos.isEmpty()) {
            tvStreakDesc.text = "Nenhum registro no banco."
            tvUltimoTreino.text = "Sem treinos"
            tvSemTreinos.visibility = View.VISIBLE
            return
        }

        tvStreakDesc.text = "Total de $total treino(s) na agenda."
        tvUltimoTreino.text = "Ultimo cadastro: ${treinos.first().nome}"
        tvSemTreinos.visibility = View.GONE

        val inflater = LayoutInflater.from(this)

        for (treino in treinos) {
            val viewItem = inflater.inflate(R.layout.item_treino, layoutListaTreinos, false)

            val tvItemNome = viewItem.findViewById<TextView>(R.id.tvItemNome)
            val tvItemData = viewItem.findViewById<TextView>(R.id.tvItemData)
            val tvItemLocal = viewItem.findViewById<TextView>(R.id.tvItemLocal)
            val tvItemDescricao = viewItem.findViewById<TextView>(R.id.tvItemDescricao)
            val btnItemEditar = viewItem.findViewById<ImageButton>(R.id.btnItemEditar)
            val btnItemExcluir = viewItem.findViewById<ImageButton>(R.id.btnItemExcluir)

            tvItemNome.text = treino.nome
            tvItemData.text = treino.data
            tvItemLocal.text = treino.local

            if (treino.descricao.isNotEmpty()) {
                tvItemDescricao.text = treino.descricao
                tvItemDescricao.visibility = View.VISIBLE
            } else {
                tvItemDescricao.visibility = View.GONE
            }

            // Abre a tela em modo de edicao passando os dados
            fun abrirEdicaoTreino() {
                val intent = Intent(this, NovoEventoActivity::class.java).apply {
                    putExtra("EXTRA_ID", treino.id)
                    putExtra("EXTRA_NOME", treino.nome)
                    putExtra("EXTRA_DATA", treino.data)
                    putExtra("EXTRA_LOCAL", treino.local)
                    putExtra("EXTRA_DESCRICAO", treino.descricao)
                }
                startActivity(intent)
            }

            viewItem.setOnClickListener {
                abrirEdicaoTreino()
            }

            btnItemEditar.setOnClickListener {
                abrirEdicaoTreino()
            }

            // Exclusao com dialogo de confirmacao
            btnItemExcluir.setOnClickListener {
                AlertDialog.Builder(this)
                    .setTitle("Excluir Treino")
                    .setMessage("Deseja remover \"${treino.nome}\"?")
                    .setPositiveButton("Sim") { _, _ ->
                        banco.excluir(treino.id)
                        Toast.makeText(this, "Treino excluído!", Toast.LENGTH_SHORT).show()
                        carregarTreinosDoBanco()
                    }
                    .setNegativeButton("Cancelar", null)
                    .show()
            }

            layoutListaTreinos.addView(viewItem)
        }
    }
}
