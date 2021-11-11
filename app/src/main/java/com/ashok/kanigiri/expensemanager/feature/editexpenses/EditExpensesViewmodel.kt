package com.ashok.kanigiri.expensemanager.feature.editexpenses

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ashok.kanigiri.expensemanager.service.room.entity.ExpenseCategory
import com.ashok.kanigiri.expensemanager.service.room.repository.RoomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import android.widget.SeekBar
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class EditExpensesViewmodel @Inject constructor(private val roomRepository: RoomRepository) :
    ViewModel() {
    val adapter = EditExpensesAdapter(this)
    var progressMap = mutableMapOf<String, Int>()
    var totalExpenses = ObservableField<Int>()
    var salary = 46000

    fun setAdapter(): EditExpensesAdapter {
        return adapter
    }

    fun getExpenseList(): LiveData<List<ExpenseCategory>> {
        return roomRepository.getCategoryDao().getSelectedCategorys()
    }

    fun createAccount() {
        Log.d("kmfkf", "PROGRESS MAP : ${progressMap}")
    }

    fun calculateTotalExpenses(){
        totalExpenses.set(progressMap.values.sum() )
    }

    fun updateTargetPriceForCategory(categoryId: Int, targetPrice: Double){
        viewModelScope.launch (Dispatchers.IO){
            roomRepository.getCategoryDao().updateTargetPriceForCategory(categoryId, targetPrice)
        }
    }

    fun getTargetPriceForCategory(categoryId: Int): Double{
        return roomRepository.getCategoryDao().getTotalExpensePriceForCategory(categoryId)
    }

}