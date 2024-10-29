package com.example.mypsychologist

import com.example.mypsychologist.data.converters.toModels
import com.example.mypsychologist.data.model.ProblemAnalysisModel
import com.example.mypsychologist.domain.entity.ProblemAnalysisEntity
import com.example.mypsychologist.extensions.containsKeys
import com.example.mypsychologist.extensions.sum
import org.junit.Assert.*
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTestModel {

    @Test
    fun selected_list_items_sum_isCorrect() {
        val list = listOf(1,1,0,0,2,1,1,0,1,0,1,2,1,1,0,0,1,0,2,0,0)
        val selectedItems = arrayOf(1, 3, 6, 8, 14, 18, 19)

        assertEquals(5, list.sum(selectedItems))
    }

    @Test
    fun anxiety_score_isCorrect() {
        val actual = DASSConclusionUseCase.Anxiety(listOf(1,1,0,0,2,1,1,0,1,0,1,2,1,1,0,0,1,0,2,0,0)).score()

        assertEquals(5, actual)
    }

    @Test
    fun problem_analysis_converter_isCorrect(){
        val actual = ProblemAnalysisEntity(
            problemId = "1",
            dogmaticRequirement = "мяу",
            flexiblePreference = "гав",
            lft = "мур",
            hft = "гав-гав"
            ).toModels()
        val expected = listOf(
            ProblemAnalysisModel("1", "мяу", "гав"),
            ProblemAnalysisModel("1", "мур", "гав-гав"),
        )
        assertEquals(expected, actual)
    }

    @Test
    fun contains_keys_isCorrect() {
        val map = mapOf(Pair("a", "b"), Pair("c", "d"))
        val actual = map.containsKeys("a", "c")

        assertTrue(actual)
    }

    @Test
    fun not_contains_keys_isCorrect() {
        val map = mapOf(Pair("a", "b"), Pair("c", "d"))
        val actual = map.containsKeys("a", "d")

        assertFalse(actual)
    }
}