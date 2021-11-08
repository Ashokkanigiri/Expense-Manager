package com.ashok.kanigiri.expensemanager.service.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(item: T)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(item: List<T>)
}