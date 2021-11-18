package com.ashok.kanigiri.expensemanager.service.di.modules

import android.content.Context
import androidx.room.Room
import com.ashok.kanigiri.expensemanager.ExpenseManagerApplication
import com.ashok.kanigiri.expensemanager.service.ExpenseAppDB
import com.ashok.kanigiri.expensemanager.service.room.dao.ExpenseCategoryDao
import com.ashok.kanigiri.expensemanager.service.room.dao.ExpenseDao
import com.ashok.kanigiri.expensemanager.service.room.dao.ExpenseMonthDao
import com.ashok.kanigiri.expensemanager.service.room.repository.RoomRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun providesRoomDatabase(@ApplicationContext application: Context): ExpenseAppDB{
        return Room.databaseBuilder(application, ExpenseAppDB::class.java, "expense-app-db").allowMainThreadQueries().build()
    }

    @Provides
    @Singleton
    fun providesExpenseCategoryDao(expenseAppDB: ExpenseAppDB): ExpenseCategoryDao{
        return expenseAppDB.expenseCateforyDao()
    }

    @Provides
    @Singleton
    fun providesExpenseDao(expenseAppDB: ExpenseAppDB): ExpenseDao{
        return expenseAppDB.expenseDao()
    }

    @Provides
    @Singleton
    fun providesExpenseMonthDao(expenseAppDB: ExpenseAppDB): ExpenseMonthDao{
        return expenseAppDB.expenseMonthDao()
    }

    @Provides
    @Singleton
    fun providesRoomRepository(expenseCategoryDao: ExpenseCategoryDao, expenseDao: ExpenseDao, expenseMonthDao: ExpenseMonthDao): RoomRepository{
        return RoomRepository(expenseCategoryDao, expenseDao, expenseMonthDao)
    }
}