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
    fun insertExpenseMonth(expenseMonth: ExpenseMonth)

    @Query("SELECT * FROM expensemonth ORDER BY expenseMonthId != null DESC LIMIT 1")
    fun getLatestExpenseMonth(): ExpenseMonth?
}