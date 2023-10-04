package com.example.mypsychologist.ui.psychologist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mypsychologist.databinding.ClientRequestItemBinding
import com.example.mypsychologist.domain.entity.ClientRequestEntity

class ClientRequestsAdapter(
    private val items: List<ClientRequestEntity>,
    private val onClick: (String) -> Unit
) : RecyclerView.Adapter<ClientRequestsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ClientRequestItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        onClick
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    class ViewHolder(
        private val binding: ClientRequestItemBinding,
        private val onClick: (String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(request: ClientRequestEntity) {
            binding.apply {
                liter.text = request.name[0].uppercase()
                name.text = request.name
                specialization.text = request.text
            }

            itemView.setOnClickListener {
                onClick(request.clientId)
            }
        }

    }
}