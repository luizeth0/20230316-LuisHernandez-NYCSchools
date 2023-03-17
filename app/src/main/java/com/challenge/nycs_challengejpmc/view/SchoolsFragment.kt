package com.challenge.nycs_challengejpmc.view

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.challenge.nycs_challengejpmc.R
import com.challenge.nycs_challengejpmc.databinding.FragmentSchoolsBinding
import com.challenge.nycs_challengejpmc.model.domain.SchoolsItemDomain
import com.challenge.nycs_challengejpmc.utils.UIState
import com.challenge.nycs_challengejpmc.viewmodel.SchoolsViewModel

class SchoolsFragment : Fragment() {
    private var search: String =""

    private val binding by lazy {
        FragmentSchoolsBinding.inflate(layoutInflater)
    }

    private val schoolAdapter by lazy {
        SchoolAdapter {
            schoolsViewModel.schoolSelected = it
            findNavController().navigate(R.id.action_navigation_schools_to_navigation_details)
        }
    }

    private val schoolsViewModel by lazy {
        ViewModelProvider(requireActivity())[SchoolsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)

        binding.rvSchools.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = schoolAdapter
        }
        getSchools()

        return binding.root
    }

    private fun getSchools() {
        schoolsViewModel.schools.observe(viewLifecycleOwner) { state ->
            val viewList: MutableList<SchoolsItemDomain> = mutableListOf()
            when(state) {
                is UIState.LOADING -> {}
                is UIState.SUCCESS<List<SchoolsItemDomain>> -> {
                    state.response.forEach {
                        viewList.add(it)
                    }
                    schoolAdapter.updateItems(viewList)
                }
                is UIState.ERROR -> {
                    state.error.localizedMessage?.let {
                        throw Exception("Error: $state")
                    }
                }
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)
        super.onCreateOptionsMenu(menu, inflater)
        val searchItem = menu.findItem(R.id.action_search)
        searchItem.also {
            it.isVisible = true
        }
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    search = it
                    schoolAdapter.filter(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    search = it
                    schoolAdapter.filter(it)
                }
                return true
            }
        })
    }

}