package com.obsudim.mypsychologist.presentation.gamification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.domain.entity.gamificationEntity.CurrentScoreEntity
import com.obsudim.mypsychologist.domain.entity.gamificationEntity.WeeklyScoresEntity
import com.obsudim.mypsychologist.domain.entity.priofileEntity.UserDataEntity
import com.obsudim.mypsychologist.domain.useCase.gamificationCases.GetCurrentScoreUseCase
import com.obsudim.mypsychologist.domain.useCase.gamificationCases.GetWeeklyScoresUseCase
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
    val currentScoreStatus: LiveData<CurrentScoreStatus> = _currentScoreStatus

    private val _weeklyScoresStatus: MutableLiveData<WeeklyScoresStatus> = MutableLiveData()
    val weeklyScoresStatus: LiveData<WeeklyScoresStatus> = _weeklyScoresStatus

    private val _userInfoStatus: MutableLiveData<UserInfoStatus> = MutableLiveData()
    val userInfoStatus: LiveData<UserInfoStatus> = _userInfoStatus

    fun loadData() {
        _screenState.value = GamificationScreenState.Loading
        viewModelScope.launch {
            getCurrentScoreUseCase().collect { currentScoreResource ->
                handleCurrentScoreResult(currentScoreResource)
            }

            getWeeklyScoresUseCase().collect { weeklyScoresResource ->
                handleWeeklyScoresResult(weeklyScoresResource)
            }

            getUserInfoUseCase().collect { userInfoResource ->
                handleUserInfoResult(userInfoResource)
            }
        }
    }

    private fun handleCurrentScoreResult(result: Resource<CurrentScoreEntity>) {
        when (result) {
            is Resource.Error -> {
                _currentScoreStatus.value = CurrentScoreStatus.Error(result.msg.toString())
                if (_screenState.value is GamificationScreenState.Loading ||
                    _screenState.value is GamificationScreenState.Initial) {
                    _screenState.value = GamificationScreenState.Error(result.msg.toString())
                }
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
                if (_screenState.value is GamificationScreenState.Loading ||
                    _screenState.value is GamificationScreenState.Initial) {
                    _screenState.value = GamificationScreenState.Error(result.msg.toString())
                }
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
                if (_screenState.value is GamificationScreenState.Loading ||
                    _screenState.value is GamificationScreenState.Initial) {
                    _screenState.value = GamificationScreenState.Error(result.msg.toString())
                }
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
        val currentScore = (_currentScoreStatus.value as? CurrentScoreStatus.Success)?.data
        val weeklyScores = (_weeklyScoresStatus.value as? WeeklyScoresStatus.Success)?.data
        val userInfo = (_userInfoStatus.value as? UserInfoStatus.Success)?.data

        if (currentScore != null && weeklyScores != null && userInfo != null) {
            _screenState.value = GamificationScreenState.Content(
                currentScore = currentScore,
                weeklyScores = weeklyScores,
                userInfo = userInfo
            )
        }
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