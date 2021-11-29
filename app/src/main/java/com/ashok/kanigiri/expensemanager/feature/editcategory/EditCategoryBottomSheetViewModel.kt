package com.ashok.kanigiri.expensemanager.feature.editcategory

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashok.kanigiri.expensemanager.service.room.entity.ExpenseCategory
import com.ashok.kanigiri.expensemanager.service.room.entity.ExpenseMonth
import com.ashok.kanigiri.expensemanager.service.room.repository.RoomRepository
import com.ashok.kanigiri.expensemanager.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class EditCategoryBottomSheetViewModel @Inject constructor(val roomRepository: RoomRepository) :
    ViewModel() {

    var categoryId: Int = 0
    val event = SingleLiveEvent<EditCategoryBottomSheetViewModelEvent>()
    val categoryNameObserver = ObservableField<String>()
    val categoryTargetPriceObserver = ObservableField<String>()
    val errorDialogVisibility = ObservableField<Boolean>()
    val totalExpensesInCategory = ObservableField<Double>()
    val getTotalAllocatedPrice: Double = runBlocking(Dispatchers.IO) {
        (roomRepository.getCategoryDao().getTotalAllotedCategoryPrice())
    }

    init {
        errorDialogVisibility.set(true)
    }

    fun onEditButtonClicked() {
        event.postValue(EditCategoryBottomSheetViewModelEvent.OnEditButtonClicked)
    }

    fun updateCategoryDetails() {
        val nullCheck =
            categoryNameObserver.get()?.trim() != null && categoryTargetPriceObserver.get()
                ?.trim() != null
        val targetPriceUpperBounds = (categoryTargetPriceObserver.get()?.trim()
            ?.toDouble()) ?: 0.0 <= getMaximumTargetPriceForCategory()
        val targetPriceLowerBounds = (categoryTargetPriceObserver.get()?.trim()?.toDouble()
            ?: 0.0) > totalExpensesInCategory.get() ?: 0.0
        when {
            nullCheck && targetPriceLowerBounds && targetPriceUpperBounds -> {
                viewModelScope.launch {
                    roomRepository.getCategoryDao().editExpenseCategory(
                        categoryId = categoryId,
                        total = (categoryTargetPriceObserver.get()?.toDoubleOrNull()) ?: 0.0,
                        categoryName = categoryNameObserver?.get() ?: ""
                    )
                }
                event.postValue(EditCategoryBottomSheetViewModelEvent.DismissDialog)
            }

            nullCheck && !targetPriceLowerBounds && targetPriceUpperBounds ->{
                event.postValue(EditCategoryBottomSheetViewModelEvent.ShowMinimumAmountToBeSetDialog)
            }
            nullCheck && targetPriceLowerBounds && !targetPriceUpperBounds ->{
                event.postValue(EditCategoryBottomSheetViewModelEvent.ShowTargetAmoutExceededError)
            }
        }
    }

    fun getCurrentExpenseMonth(): ExpenseMonth? {
        return roomRepository.getExpenseMonthDao().getLatestExpenseMonth()
    }

    fun getCategoryDetails(): ExpenseCategory {
        return roomRepository.getCategoryDao().getCategory(categoryId)
    }

    fun getMaximumTargetPriceForCategory(): Double {
        return getTotalAllocatedPrice - (getCategoryDetails()?.expenseCategoryTargetPrice ?: 0.0)
    }
}

sealed class EditCategoryBottomSheetViewModelEvent() {
    object OnEditButtonClicked : EditCategoryBottomSheetViewModelEvent()
    object DismissDialog : EditCategoryBottomSheetViewModelEvent()
    object ShowTargetAmoutExceededError : EditCategoryBottomSheetViewModelEvent()
    object ShowMinimumAmountToBeSetDialog : EditCategoryBottomSheetViewModelEvent()
}