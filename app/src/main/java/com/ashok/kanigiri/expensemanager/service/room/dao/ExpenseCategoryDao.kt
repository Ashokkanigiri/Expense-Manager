package com.ashok.kanigiri.expensemanager.service.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ashok.kanigiri.expensemanager.service.room.BaseDao
import com.ashok.kanigiri.expensemanager.service.room.entity.ExpenseCategory

@Dao
interface ExpenseCategoryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: ExpenseCategory)

    @Query("SELECT * FROM expensecategory")
    fun getAllExpenses(): LiveData<List<ExpenseCategory>>

    @Query("SELECT expenseCategoryTargetPrice FROM ExpenseCategory WHERE expenseCategoryId =:categoryId")
    fun getTotalExpensePriceForCategory(categoryId: String): Double
}