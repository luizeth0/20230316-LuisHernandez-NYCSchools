package com.challenge.nycs_challengejpmc.model.domain

import com.challenge.nycs_challengejpmc.model.SchoolsItem


data class SchoolsItemDomain(
    var dbn: String? = null,
    val school_name: String? = null,
    val phoneNumber: String? = null,
    val location: String? = null,
    val schoolEmail: String? = null,
    val city: String? = null,
    val overview_paragraph: String? = null,
    val website: String? = null,
    val zip: String? = null,
    )

fun List<SchoolsItem>?.mapToDomain(): List<SchoolsItemDomain> =
    this?.map {
        SchoolsItemDomain(
            dbn = it.dbn ?: "DBN not available",
            school_name = it.school_name ?: "Name not available",
            phoneNumber = it.phone_number ?: "Phone not available",
            location = it.location ?: "Location not available",
            schoolEmail = it.school_email ?: "School_email not available",
            city = it.city ?: "City not available",
            overview_paragraph = it.overview_paragraph ?: "Overview not available",
            website = it.website ?: "Website not available",
            zip = it.zip ?: "Zip not available"
        )
    } ?: emptyList()