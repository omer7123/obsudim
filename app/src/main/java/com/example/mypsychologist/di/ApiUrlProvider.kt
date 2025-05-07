package com.example.mypsychologist.di

import javax.inject.Inject

interface ApiUrlProvider {
    var url: String
}

class ApiUrlProviderImpl @Inject constructor() : ApiUrlProvider {

    override var url =
        "https://d311-107-181-151-123.ngrok-free.app/"
}