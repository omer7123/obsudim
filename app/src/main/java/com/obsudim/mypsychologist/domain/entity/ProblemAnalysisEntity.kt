package com.obsudim.mypsychologist.domain.entity


data class ProblemAnalysisEntity(
    val problemId: String = "",
    val dogmaticRequirement: String = "",
    val dramatization: String = "",
    val lft: String = "",
    val humiliatingRemarks: String = "",

    val flexiblePreference: String = "",
    val perspective: String = "",
    val hft: String = "",
    val unconditionalAcceptance: String = ""
)
fun ProblemAnalysisEntity.getMapOfFilledMembers() =
    hashMapOf(
        ::dogmaticRequirement.name to dogmaticRequirement,
        ::dramatization.name to dramatization,
        ::lft.name to lft,
        ::humiliatingRemarks.name to humiliatingRemarks,
        ::flexiblePreference.name to flexiblePreference,
        ::perspective.name to perspective,
        ::hft.name to hft,
        ::unconditionalAcceptance.name to unconditionalAcceptance
    ).filter { it.value.isNotEmpty() }
