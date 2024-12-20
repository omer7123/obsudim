package com.example.mypsychologist.ui.authentication.boardFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.mypsychologist.R
import com.example.mypsychologist.databinding.FragmentBoardBinding
import com.example.mypsychologist.extensions.toDp


class BoardFragment : Fragment() {
    private var _binding: FragmentBoardBinding? = null
    private val binding get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBoardBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPager()
        initListener()

    }

    private fun initListener() {
        binding.nextBtn.setOnClickListener {
            val currentItem = binding.pager2.currentItem
            val totalItems = binding.pager2.adapter?.itemCount ?: 0

            if(currentItem < totalItems - 1){
                binding.pager2.setCurrentItem(currentItem + 1, true)
            }else{
                findNavController().navigate(R.id.action_boardFragment_to_main_fragment)
            }
        }

        binding.backBtn.setOnClickListener {
            val currentItem = binding.pager2.currentItem
            binding.pager2.setCurrentItem(currentItem - 1, true)
        }
    }

    private fun initViewPager() {
        binding.pager2.adapter = BoardAdapter()

        binding.pager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                val numberOfPage = getString(
                    R.string.board_current_max,
                    (position + 2),
                    binding.pager2.adapter?.itemCount?.plus(1)
                )
                binding.numTv.text = numberOfPage

                val layoutParams = binding.nextBtn.layoutParams as ViewGroup.MarginLayoutParams

                when{
                    position == 0 -> {
                        binding.backBtn.visibility = View.GONE
                        layoutParams.marginStart = 16.toDp(requireContext())
                        binding.nextBtn.layoutParams = layoutParams
                        binding.nextBtn.icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_next)
                    }
                    binding.pager2.adapter?.itemCount == position + 1 -> {
                        binding.nextBtn.text = getString(R.string.here_goes)
                        binding.nextBtn.icon = null
                    }
                    else->{
                        binding.backBtn.isVisible = true
                        layoutParams.marginStart = 10.toDp(requireContext())
                        binding.nextBtn.layoutParams = layoutParams
                        binding.nextBtn.text = getString(R.string.next)
                        binding.nextBtn.icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_next)
                    }
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}