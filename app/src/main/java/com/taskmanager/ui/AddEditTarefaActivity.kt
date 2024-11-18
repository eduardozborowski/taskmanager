package com.taskmanager.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.taskmanager.databinding.ActivityAddEditTarefaBinding
import com.taskmanager.model.Prioridade
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
    private var prioridadeSelecionada: Prioridade = Prioridade.MEDIA

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEditTarefaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tarefaViewModel = ViewModelProvider(this).get(TarefaViewModel::class.java)

        if (intent.hasExtra("tarefa_id")) {
            tarefaId = intent.getIntExtra("tarefa_id", -1)
        }

        // Configurar o Spinner de prioridades
        val prioridades = Prioridade.values()
        val spinnerAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            prioridades.map { it.name.capitalize() }
        )
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerPrioridade.adapter = spinnerAdapter

        binding.spinnerPrioridade.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: View?, position: Int, id: Long
            ) {
                prioridadeSelecionada = prioridades[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                prioridadeSelecionada = Prioridade.MEDIA
            }
        }

        // Se estiver editando uma tarefa existente
        if (tarefaId != null && tarefaId != -1) {
            tarefaViewModel.getTarefaById(tarefaId!!).observe(this) { tarefa ->
                tarefa?.let {
                    binding.etTitulo.setText(it.titulo)
                    binding.etDescricao.setText(it.descricao)
                    dataLimite = it.dataLimite
                    binding.btnDataLimite.text = dataLimite?.let { dateFormat.format(it) } ?: "Selecionar Data"
                    binding.spinnerPrioridade.setSelection(prioridades.indexOf(it.prioridade))
                }
            }
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
                concluida = false,
                prioridade = prioridadeSelecionada
            )

            if (tarefaId != null && tarefaId != -1) {
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
