package com.example.mypsychologist.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mypsychologist.databinding.PsychologistItemBinding
import com.example.mypsychologist.domain.entity.ClientCardEntity

class ClientCardsAdapter(
    private val items: List<ClientCardEntity>,
    private val onClick: (String) -> Unit
) : RecyclerView.Adapter<ClientCardsAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            PsychologistItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onClick
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    class ViewHolder(
        private val binding: PsychologistItemBinding,
        private val onClick: (String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(client: ClientCardEntity) {
            binding.apply {
                liter.text = client.name[0].toString().uppercase()
                name.text = client.name
                specialization.text = client.request
            }

            itemView.setOnClickListener {
                onClick(client.id)
            }
        }
    }
}