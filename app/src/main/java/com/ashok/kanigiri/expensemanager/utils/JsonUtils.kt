package com.ashok.kanigiri.expensemanager.utils

import com.ashok.kanigiri.expensemanager.service.room.entity.ExpenseTypes
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

object JsonUtils {

    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    fun toJson(item: ExpenseTypes): String{
        val jsonAdapter = moshi.adapter<ExpenseTypes>(ExpenseTypes::class.java)
        return jsonAdapter.toJson(item)
    }

     fun  fromJson(input: String): ExpenseTypes?{
        val jsonAdapter = moshi.adapter<ExpenseTypes>(ExpenseTypes::class.java)
        return jsonAdapter.fromJson(input)
    }
}