package com.obsudim.mypsychologist.presentation.gamification

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.domain.entity.gamificationEntity.CurrentScoreEntity
import com.obsudim.mypsychologist.domain.entity.gamificationEntity.WeeklyScoresEntity
import com.obsudim.mypsychologist.domain.entity.priofileEntity.UserDataEntity
import com.obsudim.mypsychologist.domain.useCase.gamificationUseCases.GetCurrentScoreUseCase
import com.obsudim.mypsychologist.domain.useCase.gamificationUseCases.GetWeeklyScoresUseCase
import com.obsudim.mypsychologist.domain.useCase.profile.GetInfoMeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class GamificationViewModel(
    private val getCurrentScoreUseCase: GetCurrentScoreUseCase,
    private val getWeeklyScoresUseCase: GetWeeklyScoresUseCase,
    private val getUserInfoUseCase: GetInfoMeUseCase
) : ViewModel() {

    private val _screenState: MutableStateFlow<GamificationScreenState> = MutableStateFlow(
        GamificationScreenState.Initial
    )
    val screenState: StateFlow<GamificationScreenState> = _screenState

    private val _currentScoreStatus: MutableLiveData<CurrentScoreStatus> = MutableLiveData()

    private val _weeklyScoresStatus: MutableLiveData<WeeklyScoresStatus> = MutableLiveData()

    private val _userInfoStatus: MutableLiveData<UserInfoStatus> = MutableLiveData()

    fun loadData() {
        _screenState.value = GamificationScreenState.Loading
        viewModelScope.launch {
            launch {
                getCurrentScoreUseCase.invoke().collect { currentScoreResource ->
                    handleCurrentScoreResult(currentScoreResource)
                }
            }
            launch {
                getWeeklyScoresUseCase.invoke().collect { weeklyScoresResource ->
                    handleWeeklyScoresResult(weeklyScoresResource)
                }
            }
            launch {
                getUserInfoUseCase.invoke().collect { userInfoResource ->
                    handleUserInfoResult(userInfoResource)
                }
            }
        }
    }

    private fun handleCurrentScoreResult(result: Resource<CurrentScoreEntity>) {
        when (result) {
            is Resource.Error -> {
                _currentScoreStatus.value = CurrentScoreStatus.Error(result.msg.toString())
                checkAndUpdateContentState()
            }
            Resource.Loading -> {
                _currentScoreStatus.value = CurrentScoreStatus.Loading
            }
            is Resource.Success -> {
                _currentScoreStatus.value = CurrentScoreStatus.Success(result.data)
                checkAndUpdateContentState()
            }
        }
    }

    private fun handleWeeklyScoresResult(result: Resource<WeeklyScoresEntity>) {
        when (result) {
            is Resource.Error -> {
                _weeklyScoresStatus.value = WeeklyScoresStatus.Error(result.msg.toString())
                checkAndUpdateContentState()
            }
            Resource.Loading -> {
                _weeklyScoresStatus.value = WeeklyScoresStatus.Loading
            }
            is Resource.Success -> {
                _weeklyScoresStatus.value = WeeklyScoresStatus.Success(result.data)
                checkAndUpdateContentState()
            }
        }
    }

    private fun handleUserInfoResult(result: Resource<UserDataEntity>) {
        when (result) {
            is Resource.Error -> {
                _userInfoStatus.value = UserInfoStatus.Error(result.msg.toString())
                checkAndUpdateContentState()
            }
            Resource.Loading -> {
                _userInfoStatus.value = UserInfoStatus.Loading
            }
            is Resource.Success -> {
                _userInfoStatus.value = UserInfoStatus.Success(result.data)
                checkAndUpdateContentState()
            }
        }
    }

    private fun checkAndUpdateContentState() {
        val userInfo = (_userInfoStatus.value as? UserInfoStatus.Success)?.data
            ?: return

        val currentScore = when (val status = _currentScoreStatus.value) {
            is CurrentScoreStatus.Success -> status.data
            else -> null
        }

        val weeklyScores = when (val status = _weeklyScoresStatus.value) {
            is WeeklyScoresStatus.Success -> status.data
            else -> null
        }

        _screenState.value = GamificationScreenState.Content(
            currentScore = currentScore,
            weeklyScores = weeklyScores,
            userInfo = userInfo
        )
    }

    sealed class CurrentScoreStatus {
        data object Loading : CurrentScoreStatus()
        data class Success(val data: CurrentScoreEntity) : CurrentScoreStatus()
        data class Error(val message: String) : CurrentScoreStatus()
    }

    sealed class WeeklyScoresStatus {
        data object Loading : WeeklyScoresStatus()
        data class Success(val data: WeeklyScoresEntity) : WeeklyScoresStatus()
        data class Error(val message: String) : WeeklyScoresStatus()
    }

    sealed class UserInfoStatus {
        data object Loading : UserInfoStatus()
        data class Success(val data: UserDataEntity) : UserInfoStatus()
        data class Error(val message: String) : UserInfoStatus()
    }

    class Factory @Inject constructor(
        private val getCurrentScoreUseCase: GetCurrentScoreUseCase,
        private val getWeeklyScoresUseCase: GetWeeklyScoresUseCase,
        private val getUserInfoUseCase: GetInfoMeUseCase
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return GamificationViewModel(
                getCurrentScoreUseCase, getWeeklyScoresUseCase, getUserInfoUseCase
            ) as T
        }
    }
}