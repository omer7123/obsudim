package com.example.mypsychologist.presentation.exercises.statementProblemsAndTargetFragment

data class StatementProblemsAndTargetScreenState(
    val screenPage: ScreenPage = ScreenPage.CHOICE_SPHERE,

    val choseSphere: String = "",
    val listSphere: List<String>,

    val choseEmotion: String = "",
    val textEmotion: String = "",


    val textTarget: String = "",
)

enum class ScreenPage{
    CHOICE_SPHERE,
    CHOICE_EMOTION,
    CHOICE_TARGET,
}

enum class Emotions{
    ANXIETY, DEPRESSION, ANGER, UNHEALTHY_JEALOUSY
}