package com.example.mypsychologist.ui.core.delegateItems

interface DelegateItem {
    fun content(): Any
    fun id(): Int
    fun compareToOther(other: DelegateItem): Boolean
}