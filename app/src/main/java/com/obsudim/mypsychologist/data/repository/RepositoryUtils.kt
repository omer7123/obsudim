package com.obsudim.mypsychologist.data.repository

import com.obsudim.mypsychologist.core.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

inline fun <M, E> Flow<Resource<M>>.checkResource(
    crossinline transform: (M) -> E
): Flow<Resource<E>> {
    return this.map {
        when (it) {
            is Resource.Error -> Resource.Error(it.msg.toString(), null)
            Resource.Loading -> Resource.Loading
            is Resource.Success -> Resource.Success(transform(it.data)!!)
        }
    }
}
