package com.ashok.kanigiri.expensemanager.feature.editexpenses

import com.ashok.kanigiri.expensemanager.feature.choosecategory.ChooseCategoryViewmodelEvent

sealed class EditExpensesViewModelEvent {
    object NavigateToMainActivity: EditExpensesViewModelEvent()
    object ShowSalaryLimitReachedSnackbar: EditExpensesViewModelEvent()
    object HandleCancelButtonClicked: EditExpensesViewModelEvent()
}