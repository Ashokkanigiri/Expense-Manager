package com.ashok.kanigiri.expensemanager.service.room.dao

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ashok.kanigiri.expensemanager.service.room.entity.Expense

@Dao
interface ExpenseDao {

    @Insert
    fun insertExpenses(expense: Expense)

    @Insert
    fun insertExpenses(expense: List<Expense>)

    @Query("SELECT * FROM expense")
    fun getAllExpenses(): LiveData<List<Expense>>

    @Query("SELECT * FROM expense WHERE expenseCategoryId =:expenseCategoryId")
    fun getAllExpensesForACategory(expenseCategoryId: Int): LiveData<List<Expense>>

    @Query("SELECT SUM(expensePrice) from expense WHERE expenseCategoryId =:expenseCategoryId")
    fun getUtilizedPriceForCategory(expenseCategoryId: Int): Double

    @Query("SELECT SUM(expensePrice) from expense")
    fun getTotalExpenses(): LiveData<Double>
}