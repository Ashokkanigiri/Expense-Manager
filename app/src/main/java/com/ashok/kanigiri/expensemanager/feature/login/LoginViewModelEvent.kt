package com.ashok.kanigiri.expensemanager.feature.login

sealed class LoginViewModelEvent {
    object NavigateToMainFragment: LoginViewModelEvent()
}