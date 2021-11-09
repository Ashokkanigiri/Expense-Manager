package com.ashok.kanigiri.expensemanager.feature.welcome

sealed class WelcomeViewModelEvent {
    object NavigateToMainFragment: WelcomeViewModelEvent()
}