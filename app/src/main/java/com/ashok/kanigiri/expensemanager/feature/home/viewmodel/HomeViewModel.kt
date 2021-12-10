package com.ashok.kanigiri.expensemanager.feature.home.viewmodel

import android.content.Context
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import androidx.room.Room
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
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMap
import kotlinx.coroutines.launch
import java.sql.Timestamp
import java.time.LocalDate

import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class HomeViewModel @Inject constructor(
    val roomRepository: RoomRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    val previousMonthGraphVisibility = ObservableField<Boolean>()
    val emptyLayoutVisibility = ObservableField<Boolean>()
    val currentMonthGraphVisibility = ObservableField<Boolean>()
    var event = SingleLiveEvent<HomeViewModelEvent>()

    init {
        currentMonthGraphVisibility.set(false)
        previousMonthGraphVisibility.set(false)
    }

    fun updateSalary() {
        event.postValue(HomeViewModelEvent.ShouldUpdateSalary)
    }

    fun insertExpenseMonth() {
        viewModelScope.launch(Dispatchers.IO) {
            roomRepository.getExpenseMonthDao().getLatestExpenseMonth()?.fromDate?.let {
                if (AppUtils.shouldUpdateToNextMonth(it)) {
                    val expenseMonth = ExpenseMonth(
                        createdDate = System.currentTimeMillis(),
                        expenseMonth = AppUtils.getCurrentMonthInInt(),
                        salary = getCurrentMonthSalary(),
                        fromDate = AppUtils.getFirstDayOnMonthInDateFormat(),
                        toDate = AppUtils.getLastDayOfMonthInDateFormat(),
                        totalUtilizedPrice = 0.0
                    )
                    roomRepository.getExpenseMonthDao().insertExpenseMonth(expenseMonth)
                    injectPreviousSelectedCategorys()
                }
            }
        }
    }

    fun injectPreviousSelectedCategorys() {
        viewModelScope.launch(Dispatchers.IO) {
            val previousMonth = roomRepository.getExpenseMonthDao()
                .getPreviousMonthIdFromFromDate(AppUtils.getPreviousExpenseMonthDate())

            previousMonth?.expenseMonthId?.let {
                roomRepository.getCategoryDao().getAllCategorysByExpenseMonthRaw(it).let {
                    val currentExpenseMonth =
                        roomRepository.getExpenseMonthDao().getLatestExpenseMonth()
                    currentExpenseMonth?.let { currentMonth ->
                        val catList = ArrayList<ExpenseCategory>()
                        it.forEach {
                            val cat = ExpenseCategory(
                                expenseCategoryTargetPrice = it.expenseCategoryTargetPrice,
                                totalUtilizedPrice = 0.0,
                                expenseCategoryName = it.expenseCategoryName,
                                createdDate = System.currentTimeMillis().toString(),
                                isSelected = true,
                                expenseMonthId = currentMonth.expenseMonthId
                            )
                            catList.add(cat)
                        }
                        roomRepository.getCategoryDao().insert(catList)
                    }

                }

            }
        }
    }

    fun logout() {
        event.postValue(HomeViewModelEvent.Logout)
    }

    fun getCategorysListForExpenseMonthDate(date: String): LiveData<List<ExpenseGraphModel>> {
        val expenseMonthId =
            roomRepository.getExpenseMonthDao()
                .getPreviousMonthIdFromFromDate(date)?.expenseMonthId
        val data =
            roomRepository.getExpenseDao().getExpensesByCategory(expenseMonthId ?: 0).asLiveData()
        return data
    }

    fun getListOfAllCategorysOrderBy(): LiveData<List<ExpenseCategory>> {
        return roomRepository.getCategoryDao().getAllCategorysOrderByCategoryName()
            .asLiveData(Dispatchers.Main)
    }

    fun getcategoryNameForId(categoryId: Int): String {
        return roomRepository.getCategoryDao().getCategoryName(categoryId)
    }

    fun getCurrentMonthSalary(): Double {
        return roomRepository.getExpenseMonthDao().getLatestExpenseMonth()?.salary ?: 0.0
    }

    fun getPreviousMonthSalary(): Double{
        return roomRepository.getExpenseMonthDao().getPreviousMonthIdFromFromDate(AppUtils.getPreviousExpenseMonthDate())?.salary?:0.0
    }
}

sealed class HomeViewModelEvent {
    object Logout : HomeViewModelEvent()
    object ShouldUpdateSalary : HomeViewModelEvent()
    object PopulateGraphs : HomeViewModelEvent()
}
