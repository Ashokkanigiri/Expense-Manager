package com.ashok.kanigiri.expensemanager.service.room.dao

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Delete
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

    @Query("DELETE FROM expense WHERE expenseId =:expenseId")
    fun deleteExpense(expenseId: String)

    @Query("SELECT * FROM expense WHERE createdDate BETWEEN :fromDate AND :toDate")
    fun getTotalExpensesForGivenDate(fromDate: String?, toDate: String?): List<Expense>

    @Query("SELECT * FROM expense WHERE createdDate BETWEEN :fromDate AND :toDate ORDER BY expensePrice ASC")
    fun getTotalExpensesInAscendingOrder(fromDate: String?, toDate: String?): List<Expense>

    @Query("SELECT * FROM expense WHERE createdDate BETWEEN :fromDate AND :toDate ORDER BY expensePrice DESC")
    fun getTotalExpensesInDescendingOrder(fromDate: String?, toDate: String?): List<Expense>

    @Query("SELECT * FROM expense WHERE createdDate BETWEEN :fromDate AND :toDate ORDER BY createdDate")
    fun getTotalRecentExpenses(fromDate: String?, toDate: String?): List<Expense>
?
    @Query("SELECT * FROM expense ORDER BY createdDate ASC LIMIT 1")
    fun getminimumCreatedDateExpense(): Expense?

    @Query("SELECT * FROM expense ORDER BY createdDate DESC LIMIT 1")
    fun getMaximumCreatedDateExpense(): Expense?

}