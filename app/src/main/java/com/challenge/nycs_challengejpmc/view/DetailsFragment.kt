package com.challenge.nycs_challengejpmc.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.challenge.nycs_challengejpmc.databinding.FragmentDetailsBinding
import com.challenge.nycs_challengejpmc.model.domain.SatScoresItemDomain
import com.challenge.nycs_challengejpmc.utils.UIState
import com.challenge.nycs_challengejpmc.viewmodel.SchoolsViewModel


class DetailsFragment : Fragment() {
    // Initialize our binding UI
    private val binding by lazy {
        FragmentDetailsBinding.inflate(layoutInflater)
    }
    // Initialize our viewModel on our fragment
    private val schoolsViewModel by lazy {
        ViewModelProvider(requireActivity())[SchoolsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        schoolsViewModel.schoolSelected?.dbn.let {
            schoolsViewModel.getSatByDbn(it.toString())
        }
        // Bind our views with the data stored in the viewModel
        binding.schoolDetailsName.text = schoolsViewModel.schoolSelected?.school_name
        binding.description.text = schoolsViewModel.schoolSelected?.overview_paragraph
        binding.address.text = schoolsViewModel.schoolSelected?.location?.substringBefore("(")
        binding.email.text = schoolsViewModel.schoolSelected?.schoolEmail
        binding.phone.text = schoolsViewModel.schoolSelected?.phoneNumber
        // use an implicit intent to make a call
        binding.phone.setOnClickListener {
            schoolsViewModel.schoolSelected?.phoneNumber?.let { it1 ->
                val dialIntent = Intent(Intent.ACTION_DIAL)
                dialIntent.data = Uri.parse("tel:$it1")
                context?.startActivity(dialIntent)
            }
        }
        // use an implicit intent tosend a mail
        binding.email.setOnClickListener {
            schoolsViewModel.schoolSelected?.schoolEmail?.let {
                val intent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:$it")
                }
                context?.startActivity(intent)
            }
        }
        //// use an implicit intent to open maps
        val location = schoolsViewModel.schoolSelected?.location?.substringAfterLast("(")
        val latitude = location?.substringBefore(",")
        val longitude = schoolsViewModel.schoolSelected?.location?.substringAfterLast(",")?.substringBefore(")")
        val labelLocation = schoolsViewModel.schoolSelected?.school_name
        binding.address.setOnClickListener {
            schoolsViewModel.schoolSelected?.location?.substringBefore("(")?.let {
                val urlAddress =
                    "http://maps.google.com/maps?q=$latitude,$longitude($labelLocation)&iwloc=A&hl=es"
                val googleIntentUri = Uri.parse(urlAddress)
                val mapIntent = Intent(Intent.ACTION_VIEW, googleIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                context?.startActivity(mapIntent)
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getSATScores()
    }
    // Observe the variable scores of the viewmodel to obtain the values and bind on our view
    private fun getSATScores(){
        schoolsViewModel.scores.observe(viewLifecycleOwner) { state ->
            val viewList: MutableList<SatScoresItemDomain> = mutableListOf()
            when(state) {
                is UIState.LOADING -> {}
                is UIState.SUCCESS<List<SatScoresItemDomain>> -> {
                    state.response.forEach {
                        viewList.add(it)
                    }
                    val score = viewList.firstOrNull {
                        it.dbn == schoolsViewModel.schoolSelected?.dbn
                    }
                    binding.satTakers.text = score?.num_of_sat_test_takers ?: "Not available"
                    binding.scoreMath.text = score?.sat_math_avg_score ?: "Not available"
                    binding.scoreReading.text = score?.sat_critical_reading_avg_score ?: "Not available"
                    binding.scoreWriting.text = score?.sat_writing_avg_score ?: "Not available"
                }
                is UIState.ERROR -> {
                    state.error.localizedMessage?.let {
                        throw Exception("Error: $state")
                    }
                }
            }
        }
    }
}