package com.example.mypsychologist.di

import javax.inject.Inject

interface ApiUrlProvider {
    var url: String
}

class ApiUrlProviderImpl @Inject constructor() : ApiUrlProvider {

    override var url =
        "сервер.психолог.демо-стенд.рф/"
}