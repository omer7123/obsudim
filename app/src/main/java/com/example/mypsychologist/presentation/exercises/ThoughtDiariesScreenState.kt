package com.example.mypsychologist.presentation.exercises

sealed interface ThoughtDiariesScreenState {
    object Init: ThoughtDiariesScreenState
    object Error: ThoughtDiariesScreenState
    object Loading: ThoughtDiariesScreenState
    class Data(val records: HashMap<String, String>): ThoughtDiariesScreenState
}