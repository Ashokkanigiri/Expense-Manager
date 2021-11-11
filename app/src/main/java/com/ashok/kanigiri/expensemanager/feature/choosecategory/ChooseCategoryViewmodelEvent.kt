package com.ashok.kanigiri.expensemanager.feature.choosecategory

sealed class ChooseCategoryViewmodelEvent {
    object OpenCreateExpenseDialog: ChooseCategoryViewmodelEvent()
    object NavigateToEditExpenses: ChooseCategoryViewmodelEvent()
}