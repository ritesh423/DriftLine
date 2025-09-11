package com.riteshapps.driftline.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.riteshapps.driftline.R
import com.riteshapps.driftline.databinding.FragmentHomeBinding
import com.riteshapps.driftline.utils.ProductAdapter
import com.riteshapps.driftline.utils.ProductRepository
import com.riteshapps.driftline.viewmodels.HomeViewModel


class HomeFragment : Fragment() {
    private lateinit var binding : FragmentHomeBinding

    private lateinit var viewModel: HomeViewModel
    private lateinit var adapter: ProductAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        adapter = ProductAdapter(emptyList())
        binding.rvProducts.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvProducts.adapter = adapter

        viewModel.products.observe(viewLifecycleOwner) { list ->
            adapter.updateList(list)
        }

        viewModel.fetchProducts()
    }

}
