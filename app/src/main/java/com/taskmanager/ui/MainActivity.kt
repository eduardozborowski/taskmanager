package com.taskmanager.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.taskmanager.adapter.TarefaAdapter
import com.taskmanager.databinding.ActivityMainBinding
import com.taskmanager.model.Tarefa
import com.taskmanager.viewmodel.TarefaViewModel

class MainActivity : AppCompatActivity(), TarefaAdapter.OnItemClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var tarefaViewModel: TarefaViewModel
    private lateinit var adapter: TarefaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = TarefaAdapter(this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        tarefaViewModel = ViewModelProvider(this).get(TarefaViewModel::class.java)
        tarefaViewModel.allTarefas.observe(this) { tarefas ->
            tarefas?.let { adapter.setTarefas(it) }
        }

        binding.fabAddTarefa.setOnClickListener {
            val intent = Intent(this, AddEditTarefaActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onItemClick(tarefa: Tarefa) {
        val intent = Intent(this, AddEditTarefaActivity::class.java)
        intent.putExtra("tarefa_id", tarefa.id)
        startActivity(intent)
    }

    override fun onCheckboxClick(tarefa: Tarefa) {
        tarefaViewModel.update(tarefa)
    }

    override fun onDeleteClick(tarefa: Tarefa) {
        AlertDialog.Builder(this)
            .setTitle("Excluir Tarefa")
            .setMessage("Tem certeza de que deseja excluir esta tarefa?")
            .setPositiveButton("Sim") { _, _ ->
                tarefaViewModel.delete(tarefa)
            }
            .setNegativeButton("Não", null)
            .show()
    }
}
