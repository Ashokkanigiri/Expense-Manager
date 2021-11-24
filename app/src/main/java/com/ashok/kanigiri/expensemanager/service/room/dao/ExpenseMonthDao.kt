package com.ashok.kanigiri.expensemanager.service.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ashok.kanigiri.expensemanager.service.room.entity.ExpenseMonth
import org.jetbrains.annotations.NotNull

@Dao
interface ExpenseMonthDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpenseMonth(expenseMonth: ExpenseMonth)

    @Query("SELECT * FROM expensemonth ORDER BY createdDate  DESC LIMIT 1")
    fun getLatestExpenseMonth(): ExpenseMonth?

    @Query("SELECT * FROM expensemonth")
    suspend fun getAllExpenseMonthsData(): List<ExpenseMonth>

    @Query("UPDATE expensemonth SET totalUtilizedPrice =:createdExpense+totalUtilizedPrice WHERE expenseMonthId =:expenseMonthId")
    suspend fun updateUtilizedPriceForExpenseMonth(createdExpense: Double, expenseMonthId: Int)
}