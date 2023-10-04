package com.example.mypsychologist.ui

import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

open class AutoClearedValue<T : Any>(fragment: Fragment) : ReadWriteProperty<Fragment, T> {
    var value: T? = null

    init {
        fragment.viewLifecycleOwnerLiveData.observe(fragment) { viewLifecycleOwner ->
            viewLifecycleOwner?.lifecycle?.addObserver(object : DefaultLifecycleObserver {
                override fun onDestroy(owner: LifecycleOwner) {
                    value = null
                }
            })
        }
    }

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: T) {
        this.value = value
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        return value ?: throw IllegalStateException(
            "Attempted to call AutoClearedValue after OnDestroyView() or before onViewCreated()"
        )
    }
}

fun <T : Any> Fragment.autoCleared() = AutoClearedValue<T>(this)