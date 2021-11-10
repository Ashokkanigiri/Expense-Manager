package com.ashok.kanigiri.expensemanager.feature.createnewaccount

sealed class CreateAccountViewmodelEvent{
    object NavigateToLoginScreen: CreateAccountViewmodelEvent()
    object NavigateToChooseCategoryScreen: CreateAccountViewmodelEvent()
}
