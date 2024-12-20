package com.example.mypsychologist.ui.authentication.boardFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mypsychologist.R
import com.example.mypsychologist.databinding.PagerBoardBinding

class BoardAdapter : RecyclerView.Adapter<BoardAdapter.ViewHolder>() {

    private val titles: Array<Int> = arrayOf(
        R.string.main,
        R.string.theory,
        R.string.practice,
        R.string.notes,
        R.string.tests,
        R.string.result_of_tests
    )
    private val images: Array<Int> = arrayOf(
        R.drawable.ic_board_2,
        R.drawable.ic_board_3,
        R.drawable.ic_board_4,
        R.drawable.ic_board_5,
        R.drawable.ic_board_6,
        R.drawable.ic_board_7,
    )

    private val describe: Array<Int> = arrayOf(
        R.string.board_desc2,
        R.string.board_desc3,
        R.string.board_desc4,
        R.string.board_desc5,
        R.string.board_desc6,
        R.string.board_desc7
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            PagerBoardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemCount(): Int {
        return titles.size
    }

    inner class ViewHolder(private val binding: PagerBoardBinding) :
        RecyclerView.ViewHolder(binding.getRoot()) {

        fun onBind(position: Int) {
            binding.titleTv.text = itemView.context.getString(titles[position])
            binding.imageIv.setImageResource(images[position])
            binding.descFirstTv.text = itemView.context.getString(describe[position])
        }
    }
}