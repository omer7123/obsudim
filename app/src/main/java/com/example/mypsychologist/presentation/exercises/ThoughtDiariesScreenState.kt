package com.example.mypsychologist.presentation.exercises

sealed interface ThoughtDiariesScreenState {
    data object Init: ThoughtDiariesScreenState
    data object Error: ThoughtDiariesScreenState
    data object Loading: ThoughtDiariesScreenState
    class Data(val records: HashMap<String, String>): ThoughtDiariesScreenState
}