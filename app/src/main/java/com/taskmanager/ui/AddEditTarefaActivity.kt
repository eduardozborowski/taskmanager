package com.taskmanager.ui

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.taskmanager.databinding.ActivityAddEditTarefaBinding
import com.taskmanager.model.Tarefa
import com.taskmanager.viewmodel.TarefaViewModel
import java.text.SimpleDateFormat
import java.util.*

class AddEditTarefaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddEditTarefaBinding
    private lateinit var tarefaViewModel: TarefaViewModel
    private var tarefaId: Int? = null
    private var dataLimite: Date? = null
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEditTarefaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tarefaViewModel = ViewModelProvider(this).get(TarefaViewModel::class.java)

        if (intent.hasExtra("tarefa_id")) {
            tarefaId = intent.getIntExtra("tarefa_id", -1)
        }

        binding.btnSalvar.setOnClickListener {
            salvarTarefa()
        }

        binding.btnDataLimite.setOnClickListener {
            mostrarDatePicker()
        }
    }

    private fun mostrarDatePicker() {
        val calendar = Calendar.getInstance()
        val datePicker = DatePickerDialog(
            this,
            { _, ano, mes, dia ->
                calendar.set(ano, mes, dia)
                dataLimite = calendar.time
                binding.btnDataLimite.text = dateFormat.format(dataLimite!!)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.show()
    }

    private fun salvarTarefa() {
        val titulo = binding.etTitulo.text.toString()
        val descricao = binding.etDescricao.text.toString()

        if (titulo.isNotEmpty()) {
            val tarefa = Tarefa(
                id = tarefaId ?: 0,
                titulo = titulo,
                descricao = descricao,
                dataLimite = dataLimite,
                concluida = false
            )

            if (tarefaId != null) {
                tarefaViewModel.update(tarefa)
            } else {
                tarefaViewModel.insert(tarefa)
                NotificationHelper.scheduleNotification(this, tarefa)
            }

            finish()
        } else {
            binding.etTitulo.error = "O título é obrigatório"
        }
    }
}
