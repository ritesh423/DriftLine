package com.riteshapps.driftline.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.riteshapps.driftline.databinding.FragmentSearchBinding
import com.riteshapps.driftline.utils.ProductAdapter
import com.riteshapps.driftline.viewmodels.HomeViewModel
import androidx.core.widget.addTextChangedListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter: ProductAdapter
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        adapter = ProductAdapter(emptyList())
        binding.rvSearchResults.layoutManager = LinearLayoutManager(requireContext())
        binding.rvSearchResults.adapter = adapter

        // Observe products once
        viewModel.products.observe(viewLifecycleOwner) { list ->
            adapter.updateList(list)
        }


        // Add search listener
//        binding.etSearch.addTextChangedListener { editable ->
////            println(("Ritesh Debug" + ":" + editable?.toString()))
//            println("Ritesh Debug ${editable?.toString()}")
//            val query = editable?.toString() ?: ""
//            println("Ritesh Debug ${viewModel.products.value}")
//            val allProducts = viewModel.products.value ?: emptyList()
//            val filtered = allProducts.filter { it.title.contains(query, ignoreCase = true) }
//            adapter.updateList(filtered)
//        }

        viewModel.fetchProducts()


        binding.searchView.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main)

                private var searchJob: Job? = null

                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    searchJob?.cancel()
                    searchJob = coroutineScope.launch {
                        newText?.let {
                            delay(500)
                            if (it.isEmpty()) {
                                viewModel.resetSearch()
                            } else {
                                viewModel.searchProducts(it)
                            }
                        }
                    }
                    return false
                }
            }
        )

    }
}
