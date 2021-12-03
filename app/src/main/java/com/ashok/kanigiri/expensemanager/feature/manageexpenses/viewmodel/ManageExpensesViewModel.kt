package com.ashok.kanigiri.expensemanager.feature.manageexpenses.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashok.kanigiri.expensemanager.feature.manageexpenses.view.ManageExpensesAdapter
import com.ashok.kanigiri.expensemanager.service.room.entity.ExpenseCategory
import com.ashok.kanigiri.expensemanager.service.room.entity.ExpenseMonth
import com.ashok.kanigiri.expensemanager.service.room.repository.RoomRepository
import com.ashok.kanigiri.expensemanager.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManageExpensesViewModel @Inject constructor( val roomRepository: RoomRepository): ViewModel() {

    val adapter = ManageExpensesAdapter(this)
    val createNewExpense = SingleLiveEvent<ExpenseCategory>()
    val navigateToExpenseList = SingleLiveEvent<ExpenseCategory>()
    var selectedAdapterPosition = 0
    val event = SingleLiveEvent<ManageExpensesViewmodelEvent>()

    fun setAdapter(): ManageExpensesAdapter{
        return adapter
    }

    fun loadAdapter(list: List<ExpenseCategory>){
        adapter.submitList(list)
    }

    fun openCreateExpenseBottomSheet(expenseCategory: ExpenseCategory, adapterPosition: Int){
        createNewExpense.postValue(expenseCategory)
        selectedAdapterPosition = adapterPosition
    }

    fun navigateToExpenseListFragment(expenseCategory: ExpenseCategory){
        navigateToExpenseList.postValue(expenseCategory)
    }

    fun getCurrentExpenseMonth(): ExpenseMonth?{
        return roomRepository.getExpenseMonthDao().getLatestExpenseMonth()
    }

    fun editExpenseCategory(expenseCategoryId: Int){
        event.postValue(ManageExpensesViewmodelEvent.OnEditExpenseCategoryClicked(expenseCategoryId))
    }

    fun showDeleteExpenseDialog(expenseCategoryId: Int){
        event.postValue(ManageExpensesViewmodelEvent.ShowDeleteCategoryDialog(expenseCategoryId))
    }

    fun deleteCategory(expenseCategoryId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            roomRepository.getExpenseDao().deleteAllExpensesForACategory(expenseCategoryId)
            roomRepository.getCategoryDao().deleteCategory(expenseCategoryId)
        }
    }
}

sealed class ManageExpensesViewmodelEvent(){
    data class OnEditExpenseCategoryClicked(val expenseCategoryId: Int): ManageExpensesViewmodelEvent()
    data class ShowDeleteCategoryDialog(val expenseCategoryId: Int): ManageExpensesViewmodelEvent()
}