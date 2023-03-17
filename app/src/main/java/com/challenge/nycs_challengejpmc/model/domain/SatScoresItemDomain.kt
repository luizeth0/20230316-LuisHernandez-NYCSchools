package com.challenge.nycs_challengejpmc.model.domain

import com.challenge.nycs_challengejpmc.model.SATScoresItem


data class SatScoresItemDomain(
    val dbn: String? = null,
    val school_name: String? = null,
    val num_of_sat_test_takers: String? = null,
    val sat_critical_reading_avg_score: String? = null,
    val sat_math_avg_score: String? = null,
    val sat_writing_avg_score: String? = null
)

fun List<SATScoresItem>?.mapToDomain(): List<SatScoresItemDomain> =
    this?.map {
        SatScoresItemDomain(
            dbn = it.dbn ?: "DBM Not available",
            school_name = it.school_name ?: "school_name Not available",
            num_of_sat_test_takers = it.num_of_sat_test_takers ?: "num_of_sat_test_takers Not available",
            sat_critical_reading_avg_score = it.sat_critical_reading_avg_score ?: "DBM Not available",
            sat_math_avg_score = it.sat_math_avg_score ?: "sat_math_avg_score Not available",
            sat_writing_avg_score = it.sat_writing_avg_score ?: "sat_writing_avg_score Not available",
            )
    } ?: emptyList()