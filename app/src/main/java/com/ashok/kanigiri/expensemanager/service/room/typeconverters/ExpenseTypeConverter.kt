package com.ashok.kanigiri.expensemanager.service.room.typeconverters

import androidx.room.TypeConverter
import com.ashok.kanigiri.expensemanager.service.room.entity.ExpenseCategory
import com.ashok.kanigiri.expensemanager.service.room.entity.ExpenseTypes
import com.ashok.kanigiri.expensemanager.utils.JsonUtils

class ExpenseTypeConverter {

    @TypeConverter
    fun fromExpenseType(input: String): ExpenseTypes? {
        return JsonUtils.fromJson(input)
    }

    @TypeConverter
    fun toExpenseType(expenseType: ExpenseTypes): String {
        return JsonUtils.toJson(expenseType)
    }
}