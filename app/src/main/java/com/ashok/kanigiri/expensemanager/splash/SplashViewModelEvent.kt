package com.ashok.kanigiri.expensemanager.splash

sealed class SplashViewModelEvent {
    object NavigateToMainFragment: SplashViewModelEvent()
}