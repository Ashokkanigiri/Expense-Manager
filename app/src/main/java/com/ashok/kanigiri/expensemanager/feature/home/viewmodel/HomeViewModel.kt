package com.ashok.kanigiri.expensemanager.feature.home.viewmodel

import android.content.Context
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.ashok.kanigiri.expensemanager.service.SharedPreferenceService
import com.ashok.kanigiri.expensemanager.service.room.entity.ExpenseCategory
import com.ashok.kanigiri.expensemanager.service.room.entity.ExpenseGraphModel
import com.ashok.kanigiri.expensemanager.service.room.entity.ExpenseMonth
import com.ashok.kanigiri.expensemanager.service.room.repository.RoomRepository
import com.ashok.kanigiri.expensemanager.utils.AppUtils
import com.ashok.kanigiri.expensemanager.utils.SingleLiveEvent
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.Timestamp

import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val roomRepository: RoomRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    val getTotalExpenses = roomRepository.getExpenseDao().getTotalExpenses().asLiveData(Dispatchers.IO)

    //2 Way binding
    var etSalary = ObservableField<String>()
    var event = SingleLiveEvent<HomeViewModelEvent>()

    init {
        insertExpenseMonth()
    }

    private fun insertExpenseMonth() {
        viewModelScope.launch (Dispatchers.IO){
            roomRepository.getExpenseMonthDao().getLatestExpenseMonth()?.fromDate?.let {
                if (AppUtils.shouldUpdateToNextMonth(it)) {
                    val expenseMonth = ExpenseMonth(
                        createdDate = (Timestamp(System.currentTimeMillis())).toString(),
                        expenseMonth = AppUtils.getCurrentMonthInInt(),
                        salary = SharedPreferenceService.getUserLoginModel(context)?.salary?.toDouble()
                            ?: 0.0,
                        fromDate = AppUtils.getFirstDayOnMonthInDateFormat(),
                        toDate = AppUtils.getLastDayOfMonthInDateFormat(),
                        totalUtilizedPrice = 0.0
                    )
                    roomRepository.getExpenseMonthDao().insertExpenseMonth(expenseMonth)
                }
            }
        }
    }

    fun logout() {
        event.postValue(HomeViewModelEvent.Logout)
    }

    fun getListOfSelectedCategorysForExpenseMonth(): LiveData<List<ExpenseGraphModel>> {
        val expenseMonthId = roomRepository.getExpenseMonthDao().getLatestExpenseMonth()?.expenseMonthId
        val data = roomRepository.getExpenseDao().getExpensesByCategory(expenseMonthId ?: 0).asLiveData()
        return data
    }

    fun getcategoryNameForId(categoryId: Int): String {
        return roomRepository.getCategoryDao().getCategoryName(categoryId)
    }

    fun getCurrentMonthSalary(): Double {
        return roomRepository.getExpenseMonthDao().getLatestExpenseMonth()?.salary ?: 0.0
    }

    fun loadSalaryFromCurrentMonth(): Double {
        return roomRepository.getExpenseMonthDao().getLatestExpenseMonth()?.salary ?: 0.0
    }
}

sealed class HomeViewModelEvent {
    object Logout : HomeViewModelEvent()
}
