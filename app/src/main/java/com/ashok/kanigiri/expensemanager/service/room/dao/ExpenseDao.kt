package com.ashok.kanigiri.expensemanager.service.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.ashok.kanigiri.expensemanager.service.room.entity.Expense
import com.ashok.kanigiri.expensemanager.service.room.entity.ExpenseGraphModel
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {

    @Query("SELECT * FROM expense WHERE expenseCategoryId =:expenseCategoryId")
    fun getAllExpensesForACategory(expenseCategoryId: Int): Flow<List<Expense>>

    @Query("SELECT * FROM expense WHERE expenseCategoryId =:expenseCategoryId AND expenseMonthId =:expenseMonthId")
    fun getAllExpensesForACategoryForExpenseMonth(expenseCategoryId: Int, expenseMonthId: Int): Flow<List<Expense>>

    @Query("SELECT SUM(expensePrice) from expense")
    fun getTotalExpenses(): Flow<Double>

    @Query("SELECT expenseCategoryId, SUM(expensePrice) FROM expense WHERE expenseMonthId =:expenseMonthId GROUP BY expenseCategoryId")
    fun getExpensesByCategory(expenseMonthId: Int): Flow<List<ExpenseGraphModel>>

    @Insert
    suspend fun insertExpenses(expense: Expense)

    @Query("SELECT SUM(expensePrice) from expense WHERE expenseCategoryId =:expenseCategoryId")
    suspend fun getUtilizedPriceForCategory(expenseCategoryId: Int): Double?

    @Query("DELETE FROM expense WHERE expenseId =:expenseId")
    suspend fun deleteExpense(expenseId: Int)

    @Query("SELECT * FROM expense WHERE expenseMonthId =:expenseMonthId")
    suspend fun getAllExpensesByExpenseMonthId(expenseMonthId: Int): List<Expense>
}