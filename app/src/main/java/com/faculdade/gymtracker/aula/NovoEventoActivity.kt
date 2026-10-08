package com.faculdade.gymtracker.aula

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.faculdade.gymtracker.R

// Tela de cadastro e edicao de treinos
class NovoEventoActivity : AppCompatActivity() {

    private lateinit var banco: BancoHelper
    private var treinoId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_novo_evento)

        banco = BancoHelper(this)

        val btnBackForm = findViewById<ImageButton>(R.id.btnBackForm)
        val tvTituloFormulario = findViewById<TextView>(R.id.tvTituloFormulario)
        val etNomeEvento = findViewById<EditText>(R.id.etNomeEvento)
        val etDataEvento = findViewById<EditText>(R.id.etDataEvento)
        val etLocalEvento = findViewById<EditText>(R.id.etLocalEvento)
        val etDescricaoEvento = findViewById<EditText>(R.id.etDescricaoEvento)
        val btnSalvarEvento = findViewById<Button>(R.id.btnSalvarEvento)

        // Se receber o ID, esta em modo de edicao
        treinoId = intent.getIntExtra("EXTRA_ID", -1)
        val isEdicao = (treinoId != -1)

        if (isEdicao) {
            tvTituloFormulario.text = "Editar Treino"
            etNomeEvento.setText(intent.getStringExtra("EXTRA_NOME"))
            etDataEvento.setText(intent.getStringExtra("EXTRA_DATA"))
            etLocalEvento.setText(intent.getStringExtra("EXTRA_LOCAL"))
            etDescricaoEvento.setText(intent.getStringExtra("EXTRA_DESCRICAO"))
            btnSalvarEvento.text = "ATUALIZAR TREINO"
        }

        btnBackForm.setOnClickListener {
            finish()
        }

        btnSalvarEvento.setOnClickListener {
            val nome = etNomeEvento.text.toString().trim()
            val data = etDataEvento.text.toString().trim()
            val local = etLocalEvento.text.toString().trim()
            val descricao = etDescricaoEvento.text.toString().trim()

            if (nome.isEmpty()) {
                etNomeEvento.error = "Informe o nome do treino"
                return@setOnClickListener
            }

            val dataFinal = if (data.isNotEmpty()) data else "Não informada"
            val localFinal = if (local.isNotEmpty()) local else "Academia"

            if (isEdicao) {
                val treinoAtualizado = Treino(
                    id = treinoId,
                    nome = nome,
                    data = dataFinal,
                    local = localFinal,
                    descricao = descricao
                )
                banco.atualizar(treinoAtualizado)
                Toast.makeText(this, "Treino atualizado!", Toast.LENGTH_SHORT).show()
            } else {
                val novoTreino = Treino(
                    nome = nome,
                    data = dataFinal,
                    local = localFinal,
                    descricao = descricao
                )
                banco.inserir(novoTreino)
                Toast.makeText(this, "Treino salvo com sucesso!", Toast.LENGTH_SHORT).show()
            }

            finish()
        }
    }
}
