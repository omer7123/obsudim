package com.example.mypsychologist.ui.psychologist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mypsychologist.databinding.PsychologistItemBinding
import com.example.mypsychologist.domain.entity.PsychologistCard

class PsychologistCardsAdapter(
    private val items: List<Pair<String, PsychologistCard>>,
    private val onClick: (String) -> Unit
) :
    RecyclerView.Adapter<PsychologistCardsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        PsychologistItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        onClick
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position].first, items[position].second)
    }

    override fun getItemCount() = items.size

    class ViewHolder(
        private val binding: PsychologistItemBinding,
        private val onClick: (String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(id: String, card: PsychologistCard) {
            binding.apply {
                liter.text = card.name[0].toString().uppercase()
                name.text = card.name
                specialization.text = card.specialization
            }
            itemView.setOnClickListener {
                onClick(id)
            }
        }
    }
}