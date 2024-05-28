package com.example.mypsychologist.di

import javax.inject.Inject

interface ApiUrlProvider {
    var url: String
}

class ApiUrlProviderImpl @Inject constructor() : ApiUrlProvider {

    override var url =
        "https://xn--b1afb6bcb.xn--c1ajjlbco7a.xn----gtbbcb4bjf2ak.xn--p1ai/"
}