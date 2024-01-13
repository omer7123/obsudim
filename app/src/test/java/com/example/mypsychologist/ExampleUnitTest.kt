package com.example.mypsychologist

import com.example.mypsychologist.domain.useCase.DASSConclusionUseCase
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

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
}