package com.obsudim.mypsychologist.di

import javax.inject.Inject

interface ApiUrlProvider {
    var url: String
}

class ApiUrlProviderImpl @Inject constructor() : ApiUrlProvider {

    override var url =

        "https://xn--b1afb6bcb.xn--d1acsjd4h.tech/"
}