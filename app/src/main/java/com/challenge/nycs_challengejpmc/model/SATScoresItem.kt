package com.challenge.nycs_challengejpmc.model

data class SATScoresItem(
    val dbn: String? = null,
    val school_name: String? = null,
    val num_of_sat_test_takers: String? = null,
    val sat_critical_reading_avg_score: String? = null,
    val sat_math_avg_score: String? = null,
    val sat_writing_avg_score: String? = null
)
