package com.challenge.nycs_challengejpmc.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.challenge.nycs_challengejpmc.model.domain.SatScoresItemDomain
import com.challenge.nycs_challengejpmc.model.domain.SchoolsItemDomain
import com.challenge.nycs_challengejpmc.rest.SchoolsRepository
import com.challenge.nycs_challengejpmc.utils.NullResponse
import com.challenge.nycs_challengejpmc.utils.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SchoolsViewModel @Inject constructor(
    private val schoolsRepository: SchoolsRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): ViewModel() {

    var schoolSelected: SchoolsItemDomain? = null

    private val _schools: MutableLiveData<UIState<List<SchoolsItemDomain>>> = MutableLiveData(UIState.LOADING)
    val schools: MutableLiveData<UIState<List<SchoolsItemDomain>>> get() = _schools

    private val _scores: MutableLiveData<UIState<List<SatScoresItemDomain>>> = MutableLiveData(UIState.LOADING)
    val scores: MutableLiveData<UIState<List<SatScoresItemDomain>>> get() = _scores

    init {
        getSchools()
    }

    fun getSchools(){
        viewModelScope.launch(ioDispatcher) {
            schoolsRepository.getSchools().collect {
                _schools.postValue(it)
            }
        }
    }

    fun getSatByDbn(dbn: String) {
        viewModelScope.launch {
            schoolsRepository.getSatScoresByDbn(dbn).collect {
                _scores.postValue(it)
            }
        }
    }

}