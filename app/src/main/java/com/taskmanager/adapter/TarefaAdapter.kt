package com.taskmanager.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.taskmanager.databinding.ItemTarefaBinding
import com.taskmanager.model.Prioridade
import com.taskmanager.model.Tarefa
import java.text.SimpleDateFormat
import java.util.*

class TarefaAdapter(private val listener: OnItemClickListener) : RecyclerView.Adapter<TarefaAdapter.TarefaViewHolder>() {

    private var tarefas = emptyList<Tarefa>()
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    inner class TarefaViewHolder(val binding: ItemTarefaBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(tarefas[position])
                }
            }

            binding.btnDelete.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onDeleteClick(tarefas[position])
                }
            }
        }

        fun bind(tarefa: Tarefa) {
            binding.cbConcluida.setOnCheckedChangeListener(null)
            binding.cbConcluida.isChecked = tarefa.concluida

            binding.tvTitulo.text = tarefa.titulo
            binding.tvDescricao.text = tarefa.descricao
            binding.tvDataLimite.text = tarefa.dataLimite?.let { dateFormat.format(it) } ?: "Sem data limite"

            // Exibir a prioridade
            binding.tvPrioridade.text = tarefa.prioridade.name.capitalize()

            // Alterar a cor com base na prioridade
            val corPrioridade = when (tarefa.prioridade) {
                Prioridade.ALTA -> Color.RED
                Prioridade.MEDIA -> Color.YELLOW
                Prioridade.BAIXA -> Color.GREEN
            }
            binding.tvPrioridade.setTextColor(corPrioridade)

            // Reatribuir o listener
            binding.cbConcluida.setOnCheckedChangeListener { _, isChecked ->
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val tarefaAtualizada = tarefas[position].copy(concluida = isChecked)
                    listener.onCheckboxClick(tarefaAtualizada)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TarefaViewHolder {
        val binding = ItemTarefaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TarefaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TarefaViewHolder, position: Int) {
        val tarefaAtual = tarefas[position]
        holder.bind(tarefaAtual)
    }

    override fun getItemCount(): Int = tarefas.size

    fun setTarefas(tarefas: List<Tarefa>) {
        this.tarefas = tarefas
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(tarefa: Tarefa)
        fun onCheckboxClick(tarefa: Tarefa)
        fun onDeleteClick(tarefa: Tarefa)
    }
}
