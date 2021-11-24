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

    @Insert
    suspend fun insertExpenses(expense: Expense)

    @Query("SELECT * FROM expense WHERE expenseCategoryId =:expenseCategoryId")
    fun getAllExpensesForACategory(expenseCategoryId: Int): Flow<List<Expense>>

    @Query("SELECT SUM(expensePrice) from expense WHERE expenseCategoryId =:expenseCategoryId")
    fun getUtilizedPriceForCategory(expenseCategoryId: Int): Double

    @Query("SELECT SUM(expensePrice) from expense")
    fun getTotalExpenses(): Flow<Double>

    @Query("DELETE FROM expense WHERE expenseId =:expenseId")
    fun deleteExpense(expenseId: Int)

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

    @Query("SELECT expenseCategoryId, SUM(expensePrice) FROM expense WHERE expenseMonthId =:expenseMonthId GROUP BY expenseCategoryId")
    fun getExpensesByCategory(expenseMonthId: Int): Flow<List<ExpenseGraphModel>>

    @Query("SELECT * FROM expense WHERE expenseMonthId =:expenseMonthId")
    fun getAllExpensesByExpenseMonthId(expenseMonthId: Int): List<Expense>

}