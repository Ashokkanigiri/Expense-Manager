package com.ashok.kanigiri.expensemanager.service.models

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import com.ashok.kanigiri.expensemanager.BR
import java.io.Serializable


data class UserLoginModel(
    var name: String,
    var dateOfBirth: Long,
    var salary: String
)