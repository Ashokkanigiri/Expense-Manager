package com.ashok.kanigiri.expensemanager.feature.editexpenses

sealed class EditExpensesViewModelEvent {
    object NavigateToMainActivity: EditExpensesViewModelEvent()
}