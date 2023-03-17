package com.challenge.nycs_challengejpmc.rest

import com.challenge.nycs_challengejpmc.model.SATScoresItem
import com.challenge.nycs_challengejpmc.model.SchoolsItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SchoolsAPI {

    /*
    * Declare a suspend function to retrieve schools using an HTTP GET request
    */
    @GET(SCHOOL_PATH)
    suspend fun getSchools():Response<List<SchoolsItem>>

    @GET(SAT_PATH)
    suspend fun getSatScores(
        @Query("dbn") dbn: String?
    ): Response<List<SATScoresItem>>

    /*
    * Define a companion object to store constants for the base URL and endpoints path
    */
    companion object {
        //https://data.cityofnewyork.us/resource/s3k6-pzi2.json
        const val BASE_URL = "https://data.cityofnewyork.us/resource/"
        private const val SCHOOL_PATH = "s3k6-pzi2.json"
        private const val SAT_PATH = "f9bf-2cp4.json"
    }
}