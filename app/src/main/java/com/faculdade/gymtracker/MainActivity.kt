package com.faculdade.gymtracker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.faculdade.gymtracker.aula.AgendaActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btnComecar).setOnClickListener {
            startActivity(Intent(this, AgendaActivity::class.java))
        }
    }
}
