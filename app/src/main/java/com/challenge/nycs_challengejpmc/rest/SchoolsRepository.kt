package com.challenge.nycs_challengejpmc.rest

import com.challenge.nycs_challengejpmc.model.domain.SatScoresItemDomain
import com.challenge.nycs_challengejpmc.model.domain.SchoolsItemDomain
import com.challenge.nycs_challengejpmc.model.domain.mapToDomain
import com.challenge.nycs_challengejpmc.utils.FailureResponse
import com.challenge.nycs_challengejpmc.utils.NullResponse
import com.challenge.nycs_challengejpmc.utils.UIState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface SchoolsRepository{
    suspend fun getSchools(): Flow<UIState<List<SchoolsItemDomain>>>
    suspend fun getSatScoresByDbn(dbn: String): Flow<UIState<List<SatScoresItemDomain>>>
}


class SchoolsRepositoryImpl @Inject constructor(
    private val schoolsAPI: SchoolsAPI
): SchoolsRepository {
    override suspend fun getSchools(): Flow<UIState<List<SchoolsItemDomain>>> = flow {
        emit(UIState.LOADING)
        try {
            val response = schoolsAPI.getSchools()
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(UIState.SUCCESS(it.mapToDomain()))
                }?: throw NullResponse()
            } else throw FailureResponse(response.body()?.toString())
        } catch (e: Exception) {
            emit(UIState.ERROR(e))
        }
    }

    override suspend fun getSatScoresByDbn(dbn: String): Flow<UIState<List<SatScoresItemDomain>>> = flow {
        emit(UIState.LOADING)
        try {
            val response = schoolsAPI.getSatScores(dbn)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(UIState.SUCCESS(it.mapToDomain()))
                } ?: throw NullResponse()
            } else throw FailureResponse(response.body()?.toString())
        } catch (e: Exception) {
            emit(UIState.ERROR(e))
        }
    }
}