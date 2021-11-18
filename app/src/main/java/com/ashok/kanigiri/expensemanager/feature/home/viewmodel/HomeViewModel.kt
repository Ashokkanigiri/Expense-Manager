package com.ashok.kanigiri.expensemanager.feature.home.viewmodel

import android.content.Context
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashok.kanigiri.expensemanager.service.SharedPreferenceService
import com.ashok.kanigiri.expensemanager.service.room.entity.ExpenseCategory
import com.ashok.kanigiri.expensemanager.service.room.entity.ExpenseMonth
import com.ashok.kanigiri.expensemanager.service.room.repository.RoomRepository
import com.ashok.kanigiri.expensemanager.utils.AppUtils
import com.ashok.kanigiri.expensemanager.utils.SingleLiveEvent
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import java.sql.Timestamp

import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor( val roomRepository: RoomRepository, @ApplicationContext private val context: Context): ViewModel() {

    val getTotalExpenses = roomRepository.getExpenseDao().getTotalExpenses()
    //2 Way binding
    var etSalary = ObservableField<String>()
    var event = SingleLiveEvent<HomeViewModelEvent>()

    init {
        insertExpenseMonth()
    }

    private fun insertExpenseMonth() {
        if(AppUtils.shouldUpdateToNextMonth(roomRepository.getExpenseMonthDao().getLatestExpenseMonth().fromDate)){
            val expenseMonth = ExpenseMonth(
                createdDate = (Timestamp(System.currentTimeMillis().toLong())).toString(),
                expenseMonth = AppUtils.getCurrentMonthInInt(),
                salary = SharedPreferenceService.getUserLoginModel(context)?.salary?.toDouble() ?: 0.0,
                fromDate = AppUtils.getFirstDayOnMonthInDateFormat(),
                toDate = AppUtils.getLastDayOfMonthInDateFormat(),
                totalUtilizedPrice = 0.0
            )
            roomRepository.getExpenseMonthDao().insertExpenseMonth(expenseMonth)
        }
    }

    fun updateSalary(){
        SharedPreferenceService.putUserSalary(context, etSalary.get()?.toInt()?:0)
        event.postValue(HomeViewModelEvent.IsSalaryUpdated)
    }

    fun logout(){
        event.postValue(HomeViewModelEvent.Logout)
    }

}

sealed class HomeViewModelEvent{
    object IsSalaryUpdated: HomeViewModelEvent()
    object Logout: HomeViewModelEvent()
}
