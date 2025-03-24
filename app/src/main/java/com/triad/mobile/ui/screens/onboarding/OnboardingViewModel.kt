package com.triad.mobile.ui.screens.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.triad.mobile.data.source.local.preferences.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * ViewModel для экрана онбординга
 */
@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val userPreferences: UserPreferences
) : ViewModel() {
    
    /**
     * Отмечает, что пользователь завершил онбординг
     */
    fun markOnboardingComplete() {
        viewModelScope.launch {
            try {
                userPreferences.setOnboardingCompleted(true)
                Timber.d("Онбординг отмечен как завершенный")
            } catch (e: Exception) {
                Timber.e(e, "Ошибка при сохранении статуса онбординга")
            }
        }
    }
} 