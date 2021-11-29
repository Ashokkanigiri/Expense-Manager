package com.ashok.kanigiri.expensemanager.service.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ashok.kanigiri.expensemanager.service.room.entity.ExpenseCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseCategoryDao {

    @Query("SELECT * FROM expensecategory")
    fun getAllExpenseCategorys(): Flow<List<ExpenseCategory>>

    @Query("SELECT * FROM expensecategory WHERE expenseMonthId =:expenseMonthId")
    fun getAllCategorysByExpenseMonth(expenseMonthId: Int): Flow<List<ExpenseCategory>>

    @Query("SELECT * FROM expensecategory ORDER BY expenseCategoryName")
    fun getAllCategorysOrderByCategoryName(): Flow<List<ExpenseCategory>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ExpenseCategory)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: List<ExpenseCategory>)

    @Query("SELECT expenseCategoryTargetPrice FROM ExpenseCategory WHERE expenseCategoryId =:categoryId")
    fun getTotalExpensePriceForCategory(categoryId: Int): Double

    @Query("SELECT SUM(expenseCategoryTargetPrice) FROM expensecategory")
    suspend fun getTotalAllotedCategoryPrice(): Double?

    @Query("UPDATE expensecategory SET totalUtilizedPrice =:total+totalUtilizedPrice WHERE expenseCategoryId =:categoryId")
    suspend fun updateUtilizedPriceForCategory(categoryId: Int, total: Double)

    @Query("UPDATE expensecategory SET expenseCategoryTargetPrice =:total WHERE expenseCategoryId =:categoryId")
    suspend fun updateTargetPriceForCategory(categoryId: Int, total: Double)

    @Query("UPDATE expensecategory SET expenseCategoryTargetPrice =:total, expenseCategoryName =:categoryName WHERE expenseCategoryId =:categoryId")
    suspend fun editExpenseCategory(categoryId: Int, total: Double, categoryName: String)

    @Query("SELECT expenseCategoryName FROM expensecategory WHERE expenseCategoryId =:categoryId")
    fun getCategoryName(categoryId: Int): String

    @Query("DELETE FROM expensecategory WHERE expenseCategoryId =:expenseCategoryId")
    suspend fun deleteCategory(expenseCategoryId: Int)

    @Query("DELETE FROM expensecategory WHERE expenseCategoryName =:expenseCategoryName")
    suspend fun deleteCategoryByName(expenseCategoryName: String)

    @Query("SELECT * FROM expensecategory WHERE expenseCategoryId =:categoryId")
    fun getCategory(categoryId: Int): ExpenseCategory

}