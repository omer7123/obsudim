package com.obsudim.mypsychologist.presentation.diagnostics.hostTestFragment

import com.obsudim.mypsychologist.domain.entity.diagnosticEntity.TestHostEntity

sealed interface HostTestScreenState {
    data object Initial : HostTestScreenState
    data class Content(
        val data: TestHostEntity
    ) : HostTestScreenState

    data object Loading : HostTestScreenState
    data object Error : HostTestScreenState
}