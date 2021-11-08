package com.ashok.kanigiri.expensemanager.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashok.kanigiri.expensemanager.splash.SplashViewModelEvent
import com.ashok.kanigiri.expensemanager.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : ViewModel() {

    val event: MutableLiveData<Event<SplashViewModelEvent>> =
        MutableLiveData<Event<SplashViewModelEvent>>()

    private val SPLASH_TIMER: Long = 500

    init {
        initTimer()
    }

    fun initTimer() {
        viewModelScope.launch(Dispatchers.Main) {
            delay(SPLASH_TIMER)
            event.postValue(Event(SplashViewModelEvent.NavigateToMainFragment))
        }
    }
}