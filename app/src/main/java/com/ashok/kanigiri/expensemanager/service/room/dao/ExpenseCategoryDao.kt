package com.ashok.kanigiri.expensemanager.service.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ashok.kanigiri.expensemanager.service.room.entity.ExpenseCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseCategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ExpenseCategory)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: List<ExpenseCategory>)

    @Query("SELECT * FROM expensecategory")
    fun getAllExpenses(): Flow<List<ExpenseCategory>>

    @Query("SELECT expenseCategoryTargetPrice FROM ExpenseCategory WHERE expenseCategoryId =:categoryId")
    fun getTotalExpensePriceForCategory(categoryId: Int): Double

    @Query("SELECT SUM(expenseCategoryTargetPrice) FROM expensecategory")
    fun getTotalAllotedCategoryPrice(): Double

    @Query("SELECT totalUtilizedPrice from expensecategory WHERE expenseCategoryId =:categoryId")
    fun getUtilizedPriceForCategory(categoryId: String): Double

    @Query("UPDATE expensecategory SET totalUtilizedPrice =:total+totalUtilizedPrice WHERE expenseCategoryId =:categoryId")
    fun updateUtilizedPriceForCategory(categoryId: Int, total: Double)

    @Query("UPDATE expensecategory SET isSelected =:isCategorySelected WHERE expenseCategoryId =:expenseCategoryId")
    fun updateCategoryUpdationStatus(isCategorySelected: Boolean, expenseCategoryId: Int)

    @Query("SELECT * FROM expensecategory WHERE isSelected =:isSelected")
    fun getSelectedCategorys(isSelected: Boolean = true): Flow<List<ExpenseCategory>>

    @Query("UPDATE expensecategory SET expenseCategoryTargetPrice =:total WHERE expenseCategoryId =:categoryId")
    fun updateTargetPriceForCategory(categoryId: Int, total: Double)

    @Query("SELECT expenseCategoryName FROM expensecategory WHERE expenseCategoryId =:categoryId")
    fun getCategoryName(categoryId: Int): String
}